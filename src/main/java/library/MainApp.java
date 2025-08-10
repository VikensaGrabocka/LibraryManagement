package library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Initialize database
            DatabaseInitializer.initialize();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/Menu.fxml"))); // adjust path
            Scene scene = new Scene(root);
            primaryStage.setTitle("Biblioteka \"Grabocka\" ");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

