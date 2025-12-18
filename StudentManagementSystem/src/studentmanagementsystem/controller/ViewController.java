package studentmanagementsystem.controller;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import studentmanagementsystem.model.*;

public class ViewController implements Initializable, Observer {

    // ==================== STUDENT FORM ====================
    @FXML private Button addStudents_Btn;
    @FXML private Button addStudents_addBtn;
    @FXML private TableColumn<Student, String> addStudents_col_course;
    @FXML private TableColumn<Student, String> addStudents_col_email;
    @FXML private TableColumn<Student, String> addStudents_col_firstName;
    @FXML private TableColumn<Student, String> addStudents_col_gender;
    @FXML private TableColumn<Student, String> addStudents_col_id;
    @FXML private TableColumn<Student, String> addStudents_col_lastName;
    @FXML private VBox addStudents_courseCheckBoxContainer;
    @FXML private Button addStudents_deleteBtn;
    @FXML private TextField addStudents_email;
    @FXML private TextField addStudents_fiestName;
    @FXML private AnchorPane addStudents_form;
    @FXML private ComboBox<String> addStudents_gender;
    @FXML private TextField addStudents_id;
    @FXML private TextField addStudents_lastName;
    @FXML private Button addStudents_saveBtn;
    @FXML private TextField addStudents_search;
    @FXML private TableView<Student> addStudents_tableView;
    @FXML private Button addStudents_updateBtn;

    // ==================== COURSE FORM ====================
    @FXML private TextField availableCourse_Credit;
    @FXML private Button availableCourse_addBtn;
    @FXML private TableColumn<SimpleCourse, String> availableCourse_col_course;
    @FXML private TableColumn<SimpleCourse, Integer> availableCourse_col_credit;
    @FXML private TableColumn<SimpleCourse, String> availableCourse_col_teacher;
    @FXML private TableColumn<SimpleCourse, Integer> availableCourse_col_semester;
    @FXML private TextField availableCourse_course;
    @FXML private Button availableCourse_deleteBtn;
    @FXML private Button availableCourse_saveBtn;
    @FXML private ComboBox<Integer> availableCourse_semester;
    @FXML private TableView<SimpleCourse> availableCourse_tableView;
    @FXML private TextField availableCourse_teacher;
    @FXML private Button availableCourse_updateBtn;

    // ==================== GRADE FORM ====================
    @FXML private ComboBox<String> studentGrade_studentSelect;
    @FXML private ComboBox<Integer> studentGrade_semesterSelect;
    @FXML private ComboBox<String> studentGrade_courseSelect;
    @FXML private TextField studentGrade_midterm;
    @FXML private TextField studentGrade_final;
    @FXML private Label studentGrade_courseGrade;
    @FXML private Button studentGrade_addBtn;
    @FXML private Button studentGrade_updateBtn;
    @FXML private Button studentGrade_deleteBtn;
    @FXML private Button studentGrade_saveBtn;
    @FXML private TableView<Grade> studentGrade_tableView;
    @FXML private TableColumn<Grade, String> studentGrade_col_course;
    @FXML private TableColumn<Grade, Double> studentGrade_col_midterm;
    @FXML private TableColumn<Grade, Double> studentGrade_col_final;
    @FXML private TableColumn<Grade, Double> studentGrade_col_grade;
    @FXML private TableColumn<Grade, Integer> studentGrade_col_coef;
    @FXML private TableColumn<Grade, String> studentGrade_col_status;
    @FXML private Label studentGrade_sem1Avg;
    @FXML private Label studentGrade_sem1Status;
    @FXML private Label studentGrade_sem2Avg;
    @FXML private Label studentGrade_sem2Status;
    @FXML private Label studentGrade_annualAvg;
    @FXML private Label studentGrade_totalCourses;
    @FXML private Label studentGrade_totalCoef;
    @FXML private Label studentGrade_passedCourses;
    @FXML private Label studentGrade_overallStatus;
    @FXML private ComboBox<String> studentGrade_strategySelect;

    // ==================== NAVIGATION ====================
    @FXML private Button availablecourses_Btn;
    @FXML private Button close;
    @FXML private Button home_Btn;
    @FXML private AnchorPane main_form;
    @FXML private Button minimize;
    @FXML private Button studentGrade_Btn;
    @FXML private AnchorPane home_form;
    @FXML private AnchorPane availableCourse_form;
    @FXML private AnchorPane studentGrade_form;
    @FXML private Label totalStudentsLabel;
    @FXML private Label totalCoursesLabel;

    // ==================== DATA ====================
    private DatabaseManager db;
    private StorageContext storageContext;
    private ObservableList<Student> studentList;
    private ObservableList<SimpleCourse> courseList;
    private ObservableList<Grade> gradeList;
    private List<CheckBox> courseCheckBoxes = new ArrayList<>();
    private GradingStrategy currentStrategy;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("✅ Student Management System loaded!");
        
        db = DatabaseManager.getInstance();
        db.addObserver(this);
        
        storageContext = new StorageContext(new FileStorage());
        loadDataFromFile();
        
        setupStudentTable();
        setupCourseTable();
        setupGradeTable();
        setupComboBoxes();
        setupGradeComboBoxes();
        
        loadStudentData();
        loadCourseData();
        
        setupButtonActions();
        updateHomeStats();
        
        if (home_form != null) {
            showForm(home_form);
        }
        
        Platform.runLater(() -> {
            Stage stage = (Stage) main_form.getScene().getWindow();
            if (stage != null) {
                stage.setOnCloseRequest(event -> saveDataOnExit());
            }
        });
    }

    // ==================== TABLE SETUP ====================

    private void setupStudentTable() {
        addStudents_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        addStudents_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        addStudents_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addStudents_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addStudents_col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        addStudents_col_course.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCoursesAsString())
        );
        
        studentList = FXCollections.observableArrayList();
        addStudents_tableView.setItems(studentList);
    }

    private void setupCourseTable() {
        availableCourse_col_course.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableCourse_col_teacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        availableCourse_col_credit.setCellValueFactory(new PropertyValueFactory<>("credits"));
        availableCourse_col_semester.setCellValueFactory(new PropertyValueFactory<>("semester"));
        
        courseList = FXCollections.observableArrayList();
        availableCourse_tableView.setItems(courseList);
    }
    
    private void setupGradeTable() {
        studentGrade_col_course.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        studentGrade_col_midterm.setCellValueFactory(new PropertyValueFactory<>("midtermScore"));
        studentGrade_col_final.setCellValueFactory(new PropertyValueFactory<>("finalScore"));
        studentGrade_col_grade.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getCourseGrade()).asObject()
        );
        
        studentGrade_col_coef.setCellValueFactory(cellData -> {
            String courseName = cellData.getValue().getCourseName();
            for (SimpleCourse course : db.getAllCourses()) {
                if (course.getName().equals(courseName)) {
                    return new javafx.beans.property.SimpleIntegerProperty(course.getCredits()).asObject();
                }
            }
            return new javafx.beans.property.SimpleIntegerProperty(0).asObject();
        });
        
        studentGrade_col_status.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().isPassed() ? "✅" : "❌")
        );
        
        gradeList = FXCollections.observableArrayList();
        studentGrade_tableView.setItems(gradeList);
    }

    private void setupComboBoxes() {
        addStudents_gender.setItems(FXCollections.observableArrayList("Male", "Female"));
        
        if (availableCourse_semester != null) {
            availableCourse_semester.setItems(FXCollections.observableArrayList(1, 2));
        }
        
        refreshCourseDropdown();
    }
    
    private void setupGradeComboBoxes() {
        if (studentGrade_semesterSelect != null) {
            studentGrade_semesterSelect.setItems(FXCollections.observableArrayList(1, 2));
            studentGrade_semesterSelect.setOnAction(e -> loadCoursesForGrade());
        }
        
        if (studentGrade_studentSelect != null) {
            studentGrade_studentSelect.setOnAction(e -> onStudentSelected());
        }
        
        if (studentGrade_strategySelect != null) {
            studentGrade_strategySelect.setItems(FXCollections.observableArrayList(
                "Semester 1", "Semester 2", "Annual Average"
            ));
            studentGrade_strategySelect.setValue("Semester 1");
            currentStrategy = new Semester1Strategy();
            studentGrade_strategySelect.setOnAction(e -> onStrategyChanged());
        }
        
        if (studentGrade_midterm != null) {
            studentGrade_midterm.textProperty().addListener((obs, old, newVal) -> calculateCourseGrade());
        }
        if (studentGrade_final != null) {
            studentGrade_final.textProperty().addListener((obs, old, newVal) -> calculateCourseGrade());
        }
        
        loadStudentsForGrade();
    }

    private void refreshCourseDropdown() {
        if (addStudents_courseCheckBoxContainer != null) {
            addStudents_courseCheckBoxContainer.getChildren().clear();
            courseCheckBoxes.clear();
            
            for (SimpleCourse course : db.getAllCourses()) {
                CheckBox checkBox = new CheckBox(course.getName() + " (Coef: " + course.getCredits() + ")");
                checkBox.setStyle("-fx-text-fill: #2d658c; -fx-font-size: 13px;");
                courseCheckBoxes.add(checkBox);
                addStudents_courseCheckBoxContainer.getChildren().add(checkBox);
            }
            
            System.out.println("✅ Course checkboxes refreshed: " + courseCheckBoxes.size());
        }
    }

    private void loadStudentData() {
        studentList.clear();
        studentList.addAll(db.getAllStudents());
    }

    private void loadCourseData() {
        courseList.clear();
        courseList.addAll(db.getAllCourses());
    }

    private void setupButtonActions() {
        addStudents_addBtn.setOnAction(e -> addStudent());
        addStudents_updateBtn.setOnAction(e -> updateStudent());
        addStudents_deleteBtn.setOnAction(e -> deleteStudent());
        addStudents_saveBtn.setOnAction(e -> saveToFile());
        
        availableCourse_addBtn.setOnAction(e -> addCourse());
        availableCourse_updateBtn.setOnAction(e -> updateCourse());
        availableCourse_deleteBtn.setOnAction(e -> deleteCourse());
        availableCourse_saveBtn.setOnAction(e -> saveToFile());
        
        if (home_Btn != null && home_form != null) {
            home_Btn.setOnAction(e -> showForm(home_form));
        }
        if (addStudents_Btn != null && addStudents_form != null) {
            addStudents_Btn.setOnAction(e -> showForm(addStudents_form));
        }
        if (availablecourses_Btn != null && availableCourse_form != null) {
            availablecourses_Btn.setOnAction(e -> showForm(availableCourse_form));
        }
        if (studentGrade_Btn != null) {
            studentGrade_Btn.setOnAction(e -> showForm(studentGrade_form));
        }
        
        addStudents_tableView.getSelectionModel().selectedItemProperty()
            .addListener((obs, oldVal, newVal) -> selectStudent(newVal));
        
        availableCourse_tableView.getSelectionModel().selectedItemProperty()
            .addListener((obs, oldVal, newVal) -> selectCourse(newVal));
        
        if (studentGrade_addBtn != null) {
            studentGrade_addBtn.setOnAction(e -> addGrade());
        }
        if (studentGrade_updateBtn != null) {
            studentGrade_updateBtn.setOnAction(e -> updateGrade());
        }
        if (studentGrade_deleteBtn != null) {
            studentGrade_deleteBtn.setOnAction(e -> deleteGrade());
        }
        if (studentGrade_saveBtn != null) {
            studentGrade_saveBtn.setOnAction(e -> saveToFile());
        }
        
        studentGrade_tableView.getSelectionModel().selectedItemProperty()
            .addListener((obs, oldVal, newVal) -> selectGrade(newVal));
    }

    // ==================== NAVIGATION ====================

    private void showForm(AnchorPane formToShow) {
        if (home_form != null) home_form.setVisible(false);
        if (addStudents_form != null) addStudents_form.setVisible(false);
        if (availableCourse_form != null) availableCourse_form.setVisible(false);
        if (studentGrade_form != null) studentGrade_form.setVisible(false);
        
        if (formToShow != null) {
            formToShow.setVisible(true);
            
            if (formToShow == studentGrade_form) {
                loadStudentsForGrade();
            }
        }
    }

    // ==================== STUDENT CRUD ====================

    private void addStudent() {
        try {
            String id = addStudents_id.getText();
            String firstName = addStudents_fiestName.getText();
            String lastName = addStudents_lastName.getText();
            String gender = addStudents_gender.getValue();
            String email = addStudents_email.getText();
            
            List<String> selectedCourses = new ArrayList<>();
            for (CheckBox cb : courseCheckBoxes) {
                if (cb.isSelected()) {
                    String courseName = cb.getText().split(" \\(")[0];
                    selectedCourses.add(courseName);
                }
            }

            if (id.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                showAlert("Error", "Please fill all required fields!");
                return;
            }
            
            if (db.getStudentById(id) != null) {
                showAlert("Error", "Student ID already exists!");
                return;
            }
            
            if (selectedCourses.isEmpty()) {
                showAlert("Error", "Please select at least one course!");
                return;
            }

            Student student = new Student(id, firstName, lastName, gender, email, selectedCourses);
            db.addStudent(student);
            loadStudentData();
            updateHomeStats();
            clearStudentForm();
            showAlert("Success", "Student added with " + selectedCourses.size() + " course(s)!");
            
        } catch (Exception e) {
            showAlert("Error", "Failed to add student: " + e.getMessage());
        }
    }

    private void updateStudent() {
        Student selected = addStudents_tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a student to update!");
            return;
        }

        try {
            String id = addStudents_id.getText();
            String firstName = addStudents_fiestName.getText();
            String lastName = addStudents_lastName.getText();
            String gender = addStudents_gender.getValue();
            String email = addStudents_email.getText();
            
            List<String> selectedCourses = new ArrayList<>();
            for (CheckBox cb : courseCheckBoxes) {
                if (cb.isSelected()) {
                    String courseName = cb.getText().split(" \\(")[0];
                    selectedCourses.add(courseName);
                }
            }

            if (selectedCourses.isEmpty()) {
                showAlert("Error", "Please select at least one course!");
                return;
            }

            Student updatedStudent = new Student(id, firstName, lastName, gender, email, selectedCourses);
            db.updateStudent(selected.getId(), updatedStudent);
            loadStudentData();
            clearStudentForm();
            showAlert("Success", "Student updated successfully!");
            
        } catch (Exception e) {
            showAlert("Error", "Failed to update student: " + e.getMessage());
        }
    }

    private void deleteStudent() {
        Student selected = addStudents_tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a student to delete!");
            return;
        }

        db.deleteStudent(selected.getId());
        loadStudentData();
        updateHomeStats();
        clearStudentForm();
        showAlert("Success", "Student deleted successfully!");
    }

    private void selectStudent(Student student) {
        if (student != null) {
            addStudents_id.setText(student.getId());
            addStudents_fiestName.setText(student.getFirstName());
            addStudents_lastName.setText(student.getLastName());
            addStudents_gender.setValue(student.getGender());
            addStudents_email.setText(student.getEmail());
            
            for (CheckBox cb : courseCheckBoxes) {
                String courseName = cb.getText().split(" \\(")[0];
                cb.setSelected(student.getCourses().contains(courseName));
            }
        }
    }

    private void clearStudentForm() {
        addStudents_id.clear();
        addStudents_fiestName.clear();
        addStudents_lastName.clear();
        addStudents_gender.setValue(null);
        addStudents_email.clear();
        
        for (CheckBox cb : courseCheckBoxes) {
            cb.setSelected(false);
        }
    }

    // ==================== COURSE CRUD ====================

    private void addCourse() {
        try {
            String courseName = availableCourse_course.getText();
            String teacher = availableCourse_teacher.getText();
            String creditText = availableCourse_Credit.getText();
            Integer semester = availableCourse_semester.getValue();

            if (courseName.isEmpty() || teacher.isEmpty() || creditText.isEmpty() || semester == null) {
                showAlert("Error", "Please fill all fields!");
                return;
            }

            int credits = Integer.parseInt(creditText);

            SimpleCourse course = new SimpleCourse(courseName, teacher, credits, semester);
            db.addCourse(course);
            loadCourseData();
            updateHomeStats();
            refreshCourseDropdown();
            clearCourseForm();
            showAlert("Success", "Course added!");
            
        } catch (NumberFormatException e) {
            showAlert("Error", "Coefficient must be a number!");
        } catch (Exception e) {
            showAlert("Error", "Failed to add course: " + e.getMessage());
        }
    }

    private void updateCourse() {
        SimpleCourse selected = availableCourse_tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a course to update!");
            return;
        }

        try {
            String courseName = availableCourse_course.getText();
            String teacher = availableCourse_teacher.getText();
            String creditText = availableCourse_Credit.getText();
            Integer semester = availableCourse_semester.getValue();

            if (courseName.isEmpty() || teacher.isEmpty() || creditText.isEmpty() || semester == null) {
                showAlert("Error", "Please fill all fields!");
                return;
            }

            int credits = Integer.parseInt(creditText);

            SimpleCourse updatedCourse = new SimpleCourse(courseName, teacher, credits, semester);
            db.updateCourse(selected.getName(), updatedCourse);
            loadCourseData();
            refreshCourseDropdown();
            clearCourseForm();
            showAlert("Success", "Course updated!");
            
        } catch (NumberFormatException e) {
            showAlert("Error", "Coefficient must be a number!");
        } catch (Exception e) {
            showAlert("Error", "Failed to update course: " + e.getMessage());
        }
    }

    private void deleteCourse() {
        SimpleCourse selected = availableCourse_tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a course to delete!");
            return;
        }

        db.deleteCourse(selected.getName());
        loadCourseData();
        updateHomeStats();
        refreshCourseDropdown();
        clearCourseForm();
        showAlert("Success", "Course deleted!");
    }

    private void selectCourse(SimpleCourse course) {
        if (course != null) {
            availableCourse_course.setText(course.getName());
            availableCourse_teacher.setText(course.getTeacher());
            availableCourse_Credit.setText(String.valueOf(course.getCredits()));
            availableCourse_semester.setValue(course.getSemester());
        }
    }

    private void clearCourseForm() {
        availableCourse_course.clear();
        availableCourse_teacher.clear();
        availableCourse_Credit.clear();
        availableCourse_semester.setValue(null);
    }

    // ==================== HOME STATS ====================

    private void updateHomeStats() {
        if (totalStudentsLabel != null) {
            totalStudentsLabel.setText(String.valueOf(db.getAllStudents().size()));
        }
        if (totalCoursesLabel != null) {
            totalCoursesLabel.setText(String.valueOf(db.getAllCourses().size()));
        }
    }

    // ==================== FILE STORAGE ====================

    private void saveToFile() {
        storageContext.setStrategy(new FileStorage());
        storageContext.saveData(db.getAllStudents());
        saveCourses();
        saveGrades();
        showAlert("Success", "All data saved to file!");
    }
    
    private void loadDataFromFile() {
        try {
            storageContext.setStrategy(new FileStorage());
            List<Student> loadedStudents = storageContext.loadData();
            for (Student student : loadedStudents) {
                db.addStudent(student);
            }
            
            loadCourses();
            loadGrades();
            
            System.out.println("✅ Data loaded from file");
        } catch (Exception e) {
            System.out.println("ℹ️ Starting fresh, no previous data");
        }
    }
    
    private void saveDataOnExit() {
        storageContext.setStrategy(new FileStorage());
        storageContext.saveData(db.getAllStudents());
        saveCourses();
        saveGrades();
        System.out.println("✅ Data auto-saved on exit");
    }
    
    private void saveCourses() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("courses.dat"));
            oos.writeObject(new ArrayList<>(db.getAllCourses()));
            oos.close();
            System.out.println("✅ Courses saved");
        } catch (Exception e) {
            System.err.println("❌ Error saving courses: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    private void loadCourses() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("courses.dat"));
            List<SimpleCourse> courses = (List<SimpleCourse>) ois.readObject();
            ois.close();
            
            for (SimpleCourse course : courses) {
                db.addCourse(course);
            }
            System.out.println("✅ Courses loaded: " + courses.size());
        } catch (Exception e) {
            System.out.println("ℹ️ No previous courses");
        }
    }
    
    private void saveGrades() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("grades.dat"));
            oos.writeObject(new ArrayList<>(db.getAllGrades()));
            oos.close();
            System.out.println("✅ Grades saved");
        } catch (Exception e) {
            System.err.println("❌ Error saving grades: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    private void loadGrades() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("grades.dat"));
            List<Grade> grades = (List<Grade>) ois.readObject();
            ois.close();
            
            for (Grade grade : grades) {
                db.addGrade(grade);
            }
            System.out.println("✅ Grades loaded: " + grades.size());
        } catch (Exception e) {
            System.out.println("ℹ️ No previous grades");
        }
    }

    // ==================== OBSERVER ====================

    @Override
    public void update(String message) {
        System.out.println("📢 Observer: " + message);
        
        if (message.equals("REFRESH_COURSES")) {
            refreshCourseDropdown();
        }
    }
    
    // ==================== GRADE MANAGEMENT ====================
    
    private void loadStudentsForGrade() {
        if (studentGrade_studentSelect != null) {
            ObservableList<String> students = FXCollections.observableArrayList();
            for (Student student : db.getAllStudents()) {
                students.add(student.getId() + " - " + student.getFirstName() + " " + student.getLastName());
            }
            studentGrade_studentSelect.setItems(students);
        }
    }
    
    private void loadCoursesForGrade() {
        if (studentGrade_courseSelect == null || studentGrade_semesterSelect == null) return;
        
        Integer selectedSemester = studentGrade_semesterSelect.getValue();
        if (selectedSemester == null) return;
        
        ObservableList<String> courses = FXCollections.observableArrayList();
        for (SimpleCourse course : db.getAllCourses()) {
            if (course.getSemester() == selectedSemester) {
                courses.add(course.getName());
            }
        }
        studentGrade_courseSelect.setItems(courses);
    }
    
    private void onStudentSelected() {
        String selected = studentGrade_studentSelect.getValue();
        if (selected == null) return;
        
        String studentId = selected.split(" - ")[0];
        loadGradesForStudent(studentId);
        updateGradeStatistics(studentId);
    }
    
    private void loadGradesForStudent(String studentId) {
        gradeList.clear();
        gradeList.addAll(db.getGradesByStudent(studentId));
    }
    
    private void calculateCourseGrade() {
        try {
            if (studentGrade_midterm == null || studentGrade_final == null) return;
            
            String midtermText = studentGrade_midterm.getText();
            String finalText = studentGrade_final.getText();
            
            if (midtermText.isEmpty() || finalText.isEmpty()) {
                studentGrade_courseGrade.setText("0.00/20");
                return;
            }
            
            double midterm = Double.parseDouble(midtermText);
            double finalExam = Double.parseDouble(finalText);
            
            double courseGrade = (midterm * 0.4) + (finalExam * 0.6);
            studentGrade_courseGrade.setText(String.format("%.2f/20", courseGrade));
            
        } catch (NumberFormatException e) {
            studentGrade_courseGrade.setText("0.00/20");
        }
    }
    
    private void addGrade() {
        try {
            String selected = studentGrade_studentSelect.getValue();
            if (selected == null) {
                showAlert("Error", "Please select a student!");
                return;
            }
            
            String studentId = selected.split(" - ")[0];
            Integer semester = studentGrade_semesterSelect.getValue();
            String courseName = studentGrade_courseSelect.getValue();
            
            if (semester == null || courseName == null) {
                showAlert("Error", "Please select semester and course!");
                return;
            }
            
            double midterm = Double.parseDouble(studentGrade_midterm.getText());
            double finalExam = Double.parseDouble(studentGrade_final.getText());
            
            if (midterm < 0 || midterm > 20 || finalExam < 0 || finalExam > 20) {
                showAlert("Error", "Grades must be between 0 and 20!");
                return;
            }
            
            if (db.getGrade(studentId, courseName, semester) != null) {
                showAlert("Error", "Grade already exists! Use Update.");
                return;
            }
            
            Grade grade = new Grade(studentId, courseName, semester, midterm, finalExam);
            db.addGrade(grade);
            loadGradesForStudent(studentId);
            updateGradeStatistics(studentId);
            clearGradeForm();
            showAlert("Success", "Grade added successfully!");
            
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid grade values!");
        } catch (Exception e) {
            showAlert("Error", "Failed to add grade: " + e.getMessage());
        }
    }
    
    private void updateGrade() {
        try {
            Grade selected = studentGrade_tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Error", "Please select a grade to update!");
                return;
            }
            
            double midterm = Double.parseDouble(studentGrade_midterm.getText());
            double finalExam = Double.parseDouble(studentGrade_final.getText());
            
            if (midterm < 0 || midterm > 20 || finalExam < 0 || finalExam > 20) {
                showAlert("Error", "Grades must be between 0 and 20!");
                return;
            }
            
            Grade updatedGrade = new Grade(selected.getStudentId(), selected.getCourseName(), 
                                          selected.getSemester(), midterm, finalExam);
            db.updateGrade(selected.getStudentId(), selected.getCourseName(), 
                          selected.getSemester(), updatedGrade);
            loadGradesForStudent(selected.getStudentId());
            updateGradeStatistics(selected.getStudentId());
            clearGradeForm();
            showAlert("Success", "Grade updated!");
            
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid grade values!");
        } catch (Exception e) {
            showAlert("Error", "Failed to update grade: " + e.getMessage());
        }
    }
    
    private void deleteGrade() {
        Grade selected = studentGrade_tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a grade to delete!");
            return;
        }
        
        db.deleteGrade(selected.getStudentId(), selected.getCourseName(), selected.getSemester());
        loadGradesForStudent(selected.getStudentId());
        updateGradeStatistics(selected.getStudentId());
        clearGradeForm();
        showAlert("Success", "Grade deleted!");
    }
    
    private void selectGrade(Grade grade) {
        if (grade != null) {
            studentGrade_semesterSelect.setValue(grade.getSemester());
            loadCoursesForGrade();
            studentGrade_courseSelect.setValue(grade.getCourseName());
            studentGrade_midterm.setText(String.valueOf(grade.getMidtermScore()));
            studentGrade_final.setText(String.valueOf(grade.getFinalScore()));
        }
    }
    
    private void clearGradeForm() {
        if (studentGrade_semesterSelect != null) studentGrade_semesterSelect.setValue(null);
        if (studentGrade_courseSelect != null) studentGrade_courseSelect.setValue(null);
        if (studentGrade_midterm != null) studentGrade_midterm.clear();
        if (studentGrade_final != null) studentGrade_final.clear();
        if (studentGrade_courseGrade != null) studentGrade_courseGrade.setText("0.00/20");
    }
    
    private void onStrategyChanged() {
        String strategy = studentGrade_strategySelect.getValue();
        if (strategy == null) return;
        
        switch (strategy) {
            case "Semester 1":
                currentStrategy = new Semester1Strategy();
                break;
            case "Semester 2":
                currentStrategy = new Semester2Strategy();
                break;
            case "Annual Average":
                currentStrategy = new AnnualStrategy();
                break;
        }
        
        String selected = studentGrade_studentSelect.getValue();
        if (selected != null) {
            String studentId = selected.split(" - ")[0];
            updateGradeStatistics(studentId);
        }
    }
    
    private void updateGradeStatistics(String studentId) {
        List<Grade> allGrades = db.getGradesByStudent(studentId);
        List<SimpleCourse> allCourses = db.getAllCourses();
        
        Semester1Strategy s1Strategy = new Semester1Strategy();
        Semester2Strategy s2Strategy = new Semester2Strategy();
        AnnualStrategy annualStrategy = new AnnualStrategy();
        
        double sem1Avg = s1Strategy.calculateAverage(studentId, allGrades, allCourses);
        double sem2Avg = s2Strategy.calculateAverage(studentId, allGrades, allCourses);
        double annualAvg = annualStrategy.calculateAverage(studentId, allGrades, allCourses);
        
        studentGrade_sem1Avg.setText(String.format("%.2f/20", sem1Avg));
        studentGrade_sem1Status.setText(sem1Avg >= 10.0 ? "✅" : (sem1Avg == 0 ? "—" : "❌"));
        
        studentGrade_sem2Avg.setText(String.format("%.2f/20", sem2Avg));
        studentGrade_sem2Status.setText(sem2Avg >= 10.0 ? "✅" : (sem2Avg == 0 ? "—" : "❌"));
        
        studentGrade_annualAvg.setText(String.format("%.2f/20", annualAvg));
        
        int totalCourses = allGrades.size();
        int passedCourses = 0;
        int totalCoef = 0;
        
        for (Grade grade : allGrades) {
            if (grade.isPassed()) passedCourses++;
            for (SimpleCourse course : allCourses) {
                if (course.getName().equals(grade.getCourseName())) {
                    totalCoef += course.getCredits();
                    break;
                }
            }
        }
        
        studentGrade_totalCourses.setText(String.valueOf(totalCourses));
        studentGrade_totalCoef.setText(String.valueOf(totalCoef));
        studentGrade_passedCourses.setText(passedCourses + "/" + totalCourses);
        
        if (annualAvg >= 10.0 && sem1Avg >= 10.0 && sem2Avg >= 10.0) {
            studentGrade_overallStatus.setText("PASS ✅");
            studentGrade_overallStatus.setTextFill(javafx.scene.paint.Color.web("#2ca772"));
        } else if (annualAvg == 0) {
            studentGrade_overallStatus.setText("—");
            studentGrade_overallStatus.setTextFill(javafx.scene.paint.Color.web("#888"));
        } else {
            studentGrade_overallStatus.setText("FAIL ❌");
            studentGrade_overallStatus.setTextFill(javafx.scene.paint.Color.web("#c51f32"));
        }
    }

    // ==================== WINDOW CONTROLS ====================

    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    // ==================== UTILITY ====================

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}