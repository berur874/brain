import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Text text1 = new Text("Name:");
        Text text2 = new Text("Registered:");
        TextField textField = new TextField();
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Genre 1", "Genre 2", "Genre 3");

        Button button1 = new Button("Save");
        Button button2 = new Button("Remove");

        GridPane gridPane = new GridPane();

        gridPane.setMinSize(600, 400);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(text1, 0, 0);      // row 0, col 0
        gridPane.add(textField, 1, 0);   // row 0, col 1
        gridPane.add(text2, 0, 2);       // row 1, col 0
        gridPane.add(comboBox, 1, 2);    // row 1, col 1
        gridPane.add(button1, 1, 1);     // row 2, col 0
        gridPane.add(button2, 1, 3);     // row 2, col 1

        button1.setStyle("-fx-background-color: darkslateblue: -fx-text-fill: white; -fx-font-size:13pt;");
        button2.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white; -fx-font-size:130t;");

        text1.setStyle("-fx-font: normal bold 20px 'serif';");
        text2.setStyle("-fx-font: normal bold 20px 'serif';");
        gridPane.setStyle("-fx-background-color: beige;");

        Scene scene = new Scene(gridPane, 600, 400);

        // Step C: prepare Stage
        stage.setTitle("Movie Library System");
        stage.setScene(scene);
        stage.show();
    }

    // Step D: main method with launch()
    public static void main(String[] args) {
        launch(args);
    }
}
