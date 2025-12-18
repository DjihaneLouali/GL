package studentmanagementsystem.model;

import java.io.Serializable;

public class SimpleCourse implements CourseComponent, Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String teacher;
    private int credits;
    private int semester; // 1 or 2

    public SimpleCourse(String name, String teacher, int credits, int semester) {
        this.name = name;
        this.teacher = teacher;
        this.credits = credits;
        this.semester = semester;
    }

    @Override
    public String getName() { return name; }

    @Override
    public int getCredits() { return credits; }

    public String getTeacher() { return teacher; }
    
    public int getSemester() { return semester; }

    public void setName(String name) { this.name = name; }
    public void setTeacher(String teacher) { this.teacher = teacher; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setSemester(int semester) { this.semester = semester; }

    @Override
    public void displayInfo() {
        System.out.println("Course: " + name + ", Teacher: " + teacher + 
                         ", Credits: " + credits + ", Semester: " + semester);
    }
}