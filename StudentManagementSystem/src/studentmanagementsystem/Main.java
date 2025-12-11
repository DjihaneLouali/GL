package studentmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // The leading "/" is important
        Parent root = FXMLLoader.load(getClass().getResource("/view.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Student Management System");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
