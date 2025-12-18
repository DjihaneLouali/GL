package studentmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
       
        System.out.println("Looking for view.fxml...");
        
     // here i had the problem of finding the view file , so i tried both ways .
        URL fxmlLocation = getClass().getResource("view.fxml");
        System.out.println("Found at: " + fxmlLocation);
        
        if (fxmlLocation == null) {
            System.out.println("ERROR: view.fxml NOT FOUND!");
            System.out.println("Current package: " + getClass().getPackage().getName());
            System.out.println("Class location: " + getClass().getResource(""));
            
            
            fxmlLocation = getClass().getResource("/view.fxml");
            System.out.println("Trying with /view.fxml: " + fxmlLocation);
            
            if (fxmlLocation == null) {
                System.out.println("Still not found. Check your bin folder!");
                return;
            }
        }
        
        Parent root = FXMLLoader.load(fxmlLocation);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Student Management System");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}