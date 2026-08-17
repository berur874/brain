import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Rentals extends Application {
    
    private ClientRMI server;
    
    private ObservableList<String> customers = FXCollections.observableArrayList();
    private ObservableList<String> genres = FXCollections.observableArrayList();
    private ObservableList<String> movies = FXCollections.observableArrayList();
    private ObservableList<String> borrowed = FXCollections.observableArrayList();
    private ObservableList<String> returned = FXCollections.observableArrayList();
    
    private ComboBox<String> customerCombo = new ComboBox<>();
    private ComboBox<String> genreCombo = new ComboBox<>();
    private ComboBox<String> moviesCombo = new ComboBox<>();
    private ListView<String> borrowedList = new ListView<>();
    private ListView<String> returnedList = new ListView<>();
    
    @Override
    public void start(Stage stage) throws Exception {
        // Connect to RMI server
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            server = (ClientRMI) registry.lookup("MovieRental");
            System.out.println("✅ Connected to RMI Server");
        } catch (Exception e) {
            showAlert("Error", "Cannot connect to server!\nMake sure server is running.");
            return;
        }
        
        // Load data
        loadData();
        
        // Build UI
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(10);
        root.setHgap(10);
        root.setAlignment(Pos.CENTER);
        
        Text customerLabel = new Text("Customer:");
        Text genreLabel = new Text("Genre:");
        Text moviesLabel = new Text("Movies:");
        Text borrowedLabel = new Text("Borrowed:");
        Text returnedLabel = new Text("Returned:");
        
        customerCombo.setItems(customers);
        genreCombo.setItems(genres);
        moviesCombo.setDisable(true);
        
        Button rentBtn = new Button("Rent");
        Button returnBtn = new Button("Return");
        Button refreshBtn = new Button("Refresh");
        
        genreCombo.setOnAction(e -> updateMovies());
        rentBtn.setOnAction(e -> rentMovie());
        returnBtn.setOnAction(e -> returnMovie());
        refreshBtn.setOnAction(e -> loadData());
        
        root.add(customerLabel, 0, 0);
        root.add(customerCombo, 1, 0);
        root.add(genreLabel, 0, 1);
        root.add(genreCombo, 1, 1);
        root.add(moviesLabel, 0, 2);
        root.add(moviesCombo, 1, 2);
        root.add(rentBtn, 1, 3);
        root.add(borrowedLabel, 0, 4);
        root.add(borrowedList, 1, 4);
        root.add(returnedLabel, 0, 5);
        root.add(returnedList, 1, 5);
        root.add(returnBtn, 1, 6);
        root.add(refreshBtn, 1, 7);
        
        // Styling
        String btnStyle = "-fx-background-color: darkblue; -fx-text-fill: white; -fx-font-size: 12pt;";
        rentBtn.setStyle(btnStyle);
        returnBtn.setStyle(btnStyle);
        refreshBtn.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 12pt;");
        
        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("Movie Rental - RMI Client");
        stage.setScene(scene);
        stage.show();
    }
    
    private void loadData() {
        try {
            customers.clear();
            customers.addAll(server.getCustomers());
            customerCombo.setItems(customers);
            
            genres.clear();
            genres.addAll(server.getAllGenres());
            genreCombo.setItems(genres);
            
            borrowed.clear();
            borrowed.addAll(server.getBorrowedMovies());
            borrowedList.setItems(borrowed);
            
            returned.clear();
            returned.addAll(server.getReturnedMovies());
            returnedList.setItems(returned);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load data");
        }
    }
    
    private void updateMovies() {
        String genre = genreCombo.getValue();
        if (genre == null) {
            moviesCombo.setDisable(true);
            return;
        }
        try {
            movies.clear();
            movies.addAll(server.getMoviesByGenre(genre));
            moviesCombo.setItems(movies);
            moviesCombo.setDisable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void rentMovie() {
        String customer = customerCombo.getValue();
        String movie = moviesCombo.getValue();
        if (customer == null || movie == null) {
            showAlert("Error", "Select customer and movie");
            return;
        }
        try {
            if (server.rentMovie(customer, movie)) {
                showAlert("Success", "Movie rented!");
                loadData();
            } else {
                showAlert("Error", "Movie already rented");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void returnMovie() {
        String movie = borrowedList.getSelectionModel().getSelectedItem();
        if (movie == null) {
            showAlert("Error", "Select a movie to return");
            return;
        }
        try {
            if (server.returnMovie(movie)) {
                showAlert("Success", "Movie returned!");
                loadData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}