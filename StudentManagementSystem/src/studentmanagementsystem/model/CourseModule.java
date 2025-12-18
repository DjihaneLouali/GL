package studentmanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

// Composite: Course Module (group of courses)
public class CourseModule implements CourseComponent {
    private String name;
    private List<CourseComponent> courses;

    public CourseModule(String name) {
        this.name = name;
        this.courses = new ArrayList<>();
    }

    public void addCourse(CourseComponent course) {
        courses.add(course);
    }

    public void removeCourse(CourseComponent course) {
        courses.remove(course);
    }

    @Override
    public String getName() { 
        return name; 
    }

    @Override
    public int getCredits() {
        return courses.stream().mapToInt(CourseComponent::getCredits).sum();
    }

    @Override
    public void displayInfo() {
        System.out.println("Module: " + name + " (Total Credits: " + getCredits() + ")");
        for (CourseComponent course : courses) {
            System.out.print("  - ");
            course.displayInfo();
        }
    }
}
