package studentmanagementsystem.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewController implements Initializable {

    @FXML private Button addStudents_Btn;
    @FXML private Button addStudents_addBtn;
    @FXML private TableColumn<?, ?> addStudents_col_course;
    @FXML private TableColumn<?, ?> addStudents_col_email;
    @FXML private TableColumn<?, ?> addStudents_col_firstName;
    @FXML private TableColumn<?, ?> addStudents_col_gender;
    @FXML private TableColumn<?, ?> addStudents_col_id;
    @FXML private TableColumn<?, ?> addStudents_col_lastName;
    @FXML private ComboBox<?> addStudents_course;
    @FXML private Button addStudents_deleteBtn;
    @FXML private TextField addStudents_email;
    @FXML private TextField addStudents_fiestName;
    @FXML private AnchorPane addStudents_form;
    @FXML private ComboBox<?> addStudents_gender;
    @FXML private TextField addStudents_id;
    @FXML private TextField addStudents_lastName;
    @FXML private Button addStudents_saveBtn;
    @FXML private TextField addStudents_search;
    @FXML private TableView<?> addStudents_tableView;
    @FXML private Button addStudents_updateBtn;
    @FXML private TextField availableCourse_Credit;
    @FXML private Button availableCourse_addBtn;
    @FXML private TableColumn<?, ?> availableCourse_col_course;
    @FXML private TableColumn<?, ?> availableCourse_col_credit;
    @FXML private TableColumn<?, ?> availableCourse_col_teacher;
    @FXML private TextField availableCourse_course;
    @FXML private Button availableCourse_deleteBtn;
    @FXML private Button availableCourse_saveBtn;
    @FXML private TableView<?> availableCourse_tableView;
    @FXML private TextField availableCourse_teacher;
    @FXML private Button availableCourse_updateBtn;
    @FXML private Button availablecourses_Btn;
    @FXML private Button close;
    @FXML private Button home_Btn;
    @FXML private AnchorPane main_form;
    @FXML private Button minimize;
    @FXML private Button studentGrade_Btn;

    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization if needed
    


	     
	    }
	    
	    
	

}
