import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class DatabaseApp extends Application {
    
    private Connection connection;
    private volatile boolean connectionSuccess = false;
    
    @Override
    public void start(Stage primaryStage) {
        // Initial UI with connection status
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");
        
        Label statusLabel = new Label("🔄 Connecting to database...");
        ProgressIndicator progress = new ProgressIndicator();
        layout.getChildren().addAll(statusLabel, progress);
        
        Scene scene = new Scene(layout, 500, 300);
        primaryStage.setTitle("Database App - Connecting");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Connect in background
        new Thread(() -> {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Connection parameters - MODIFY THESE FOR YOUR SYSTEM
                String url = "jdbc:mysql://localhost:3306/JavaTest?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                String user = "root";  // Change to your MySQL username
                String password = "";   // Change to your MySQL password
                
                connection = DriverManager.getConnection(url, user, password);
                 connectionSuccess = true;
                
                // Create table
                String createTableSQL = """
                    CREATE TABLE IF NOT EXISTS users (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(100) NOT NULL,
                        email VARCHAR(100) NOT NULL UNIQUE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                    )
                """;
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute(createTableSQL);
                }
                
                Platform.runLater(() -> {
                    statusLabel.setText("✅ Connected successfully!");
                    progress.setVisible(false);
                    buildMainUI(primaryStage);
                });
                
            } catch (ClassNotFoundException e) {
                Platform.runLater(() -> {
                    statusLabel.setText("❌ MySQL JDBC Driver not found!");
                    progress.setVisible(false);
                    showAlert("Driver Error", "Please add mysql-connector-java.jar to classpath");
                });
            } catch (SQLException e) {
                connectionSuccess = false;
                System.err.println("Connection error: " + e.getMessage());
                
                Platform.runLater(() -> {
                    String errorMsg = getErrorMessage(e);
                    statusLabel.setText("❌ Connection failed: " + errorMsg);
                    progress.setVisible(false);
                    
                    Button retryBtn = new Button("Retry Connection");
                    retryBtn.setOnAction(ev -> {
                        retryBtn.setDisable(true);
                        statusLabel.setText("🔄 Retrying...");
                        progress.setVisible(true);
                        // Remove retry button and restart
                        ((VBox) primaryStage.getScene().getRoot()).getChildren().remove(retryBtn);
                        start(primaryStage);
                    });
                    ((VBox) primaryStage.getScene().getRoot()).getChildren().add(retryBtn);
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    statusLabel.setText("❌ Error: " + e.getMessage());
                    progress.setVisible(false);
                });
            }
        }).start();
    }
    
    private String getErrorMessage(SQLException e) {
        return switch (e.getErrorCode()) {
            case 1045 -> "Access denied. Check username/password.";
            case 1049 -> "Database 'JavaTest' doesn't exist. Create it first.";
            case 0 -> "Can't connect to MySQL. Is MySQL running?";
            default -> e.getMessage();
        };
    }
    
    private void buildMainUI(Stage primaryStage) {
        Label titleLabel = new Label("User Management");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        TextField nameField = new TextField();
        nameField.setPromptText("Enter name");
        
        TextField emailField = new TextField();
        emailField.setPromptText("Enter email");
        
        Button addButton = new Button("Add User");
        Button viewButton = new Button("View Users");
        Button deleteButton = new Button("Delete User");
        
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(300);
        
        addButton.setOnAction(e -> {
            if (validateInput(nameField, emailField)) {
                addUser(nameField.getText(), emailField.getText(), outputArea);
                nameField.clear();
                emailField.clear();
            }
        });
        
        viewButton.setOnAction(e -> viewUsers(outputArea));
        
        deleteButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Delete User");
            dialog.setHeaderText("Enter User ID to delete");
            dialog.showAndWait().ifPresent(idText -> {
                try {
                    deleteUser(Integer.parseInt(idText), outputArea);
                } catch (NumberFormatException ex) {
                    outputArea.appendText("Invalid ID format!\n");
                }
            });
        });
        
        VBox layout = new VBox(10, titleLabel, nameField, emailField, 
                               addButton, viewButton, deleteButton, outputArea);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");
        
        Scene scene = new Scene(layout, 500, 500);
        primaryStage.setTitle("User Management System");
        primaryStage.setScene(scene);
        
        // Add cleanup on close
        primaryStage.setOnCloseRequest(e -> closeConnection());
    }
    
    private boolean validateInput(TextField nameField, TextField emailField) {
        if (nameField.getText().trim().isEmpty()) {
            showAlert("Error", "Name cannot be empty!");
            return false;
        }
        if (emailField.getText().trim().isEmpty() || !emailField.getText().contains("@")) {
            showAlert("Error", "Valid email is required!");
            return false;
        }
        return true;
    }
    
    private void addUser(String name, String email, TextArea outputArea) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            int rowsInserted = pstmt.executeUpdate();
            
            if (rowsInserted > 0) {
                Platform.runLater(() -> outputArea.appendText("✓ User added successfully!\n"));
            }
        } catch (SQLException e) {
            String error = e.getMessage().contains("Duplicate entry") ? 
                          "✗ Email already exists!\n" : 
                          "✗ Error adding user: " + e.getMessage() + "\n";
            Platform.runLater(() -> outputArea.appendText(error));
        }
    }
    
    private void viewUsers(TextArea outputArea) {
        String sql = "SELECT * FROM users ORDER BY id";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            StringBuilder output = new StringBuilder();
            output.append("=== User List ===\n\n");
            
            boolean hasUsers = false;
            while (rs.next()) {
                hasUsers = true;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Timestamp created = rs.getTimestamp("created_at");
                
                output.append(String.format("ID: %d | Name: %s | Email: %s | Created: %s\n",
                              id, name, email, created.toString()));
            }
            
            if (!hasUsers) {
                output.append("No users found.\n");
            }
            
            final String finalOutput = output.toString();
            Platform.runLater(() -> {
                outputArea.clear();
                outputArea.appendText(finalOutput);
            });
            
        } catch (SQLException e) {
            Platform.runLater(() -> outputArea.appendText("Error fetching users: " + e.getMessage() + "\n"));
        }
    }
    
    private void deleteUser(int id, TextArea outputArea) {
        String sql = "DELETE FROM users WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            
            if (rowsDeleted > 0) {
                Platform.runLater(() -> outputArea.appendText("✓ User deleted successfully!\n"));
            } else {
                Platform.runLater(() -> outputArea.appendText("✗ User with ID " + id + " not found.\n"));
            }
        } catch (SQLException e) {
            Platform.runLater(() -> outputArea.appendText("Error deleting user: " + e.getMessage() + "\n"));
        }
    }
    
    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
    
    private void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}