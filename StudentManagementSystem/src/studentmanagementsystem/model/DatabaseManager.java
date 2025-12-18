package studentmanagementsystem.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Singleton Pattern: Only one instance of database
public class DatabaseManager {
    private static DatabaseManager instance;
    private List<Student> students;
    private List<SimpleCourse> courses;
    private List<Grade> grades;
    private List<Observer> observers;

    // Private constructor
    private DatabaseManager() {
        students = new ArrayList<>();
        courses = new ArrayList<>();
        grades = new ArrayList<>();
        observers = new ArrayList<>();
    }

    // Singleton getInstance method
    public static DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }

    // ==================== STUDENT CRUD ====================

    public void addStudent(Student student) {
        students.add(student);
        notifyObservers("Student added: " + student.getFirstName());
    }

    public void updateStudent(String id, Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                students.set(i, updatedStudent);
                notifyObservers("Student updated: " + updatedStudent.getFirstName());
                break;
            }
        }
    }

    public void deleteStudent(String id) {
        students.removeIf(s -> s.getId().equals(id));
        notifyObservers("Student deleted: " + id);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Student getStudentById(String id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // ==================== COURSE CRUD ====================

    public void addCourse(SimpleCourse course) {
        courses.add(course);
        notifyObservers("Course added: " + course.getName());
        notifyObservers("REFRESH_COURSES");
    }

    public void updateCourse(String oldCourseName, SimpleCourse updatedCourse) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getName().equals(oldCourseName)) {
                courses.set(i, updatedCourse);
                notifyObservers("Course updated: " + updatedCourse.getName());
                notifyObservers("REFRESH_COURSES");
                break;
            }
        }
    }

    public void deleteCourse(String courseName) {
        courses.removeIf(c -> c.getName().equals(courseName));
        notifyObservers("Course deleted: " + courseName);
        notifyObservers("REFRESH_COURSES");
    }

    public List<SimpleCourse> getAllCourses() {
        return new ArrayList<>(courses);
    }

    public SimpleCourse getCourseByName(String name) {
        return courses.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    // ==================== GRADE CRUD ====================

    public void addGrade(Grade grade) {
        grades.add(grade);
        notifyObservers("Grade added for student: " + grade.getStudentId());
    }

    public void updateGrade(String studentId, String courseName, int semester, Grade updatedGrade) {
        for (int i = 0; i < grades.size(); i++) {
            Grade g = grades.get(i);
            if (g.getStudentId().equals(studentId) && 
                g.getCourseName().equals(courseName) && 
                g.getSemester() == semester) {
                grades.set(i, updatedGrade);
                notifyObservers("Grade updated for: " + studentId);
                break;
            }
        }
    }

    public void deleteGrade(String studentId, String courseName, int semester) {
        grades.removeIf(g -> g.getStudentId().equals(studentId) && 
                             g.getCourseName().equals(courseName) && 
                             g.getSemester() == semester);
        notifyObservers("Grade deleted for: " + studentId);
    }

    public Grade getGrade(String studentId, String courseName, int semester) {
        return grades.stream()
                .filter(g -> g.getStudentId().equals(studentId) && 
                            g.getCourseName().equals(courseName) && 
                            g.getSemester() == semester)
                .findFirst()
                .orElse(null);
    }

    public List<Grade> getGradesByStudent(String studentId) {
        return grades.stream()
                .filter(g -> g.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }

    public List<Grade> getAllGrades() {
        return new ArrayList<>(grades);
    }

    // ==================== OBSERVER PATTERN ====================

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}