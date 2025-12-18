package studentmanagementsystem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private List<String> courses; 

    public Student(String id, String firstName, String lastName, String gender, String email, List<String> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.courses = courses != null ? new ArrayList<>(courses) : new ArrayList<>();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<String> getCourses() { return new ArrayList<>(courses); }
    public void setCourses(List<String> courses) { 
        this.courses = courses != null ? new ArrayList<>(courses) : new ArrayList<>(); 
    }
    
    // Helper method to get courses as a comma-separated string for display
    public String getCoursesAsString() {
        return courses.isEmpty() ? "No courses" : String.join(", ", courses);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", courses=" + courses +
                '}';
    }
}