package studentmanagementsystem.model;

import java.io.Serializable;

public class Grade implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String studentId;
    private String courseName;
    private int semester;
    private double midtermScore;
    private double finalScore;

    public Grade(String studentId, String courseName, int semester, double midtermScore, double finalScore) {
        this.studentId = studentId;
        this.courseName = courseName;
        this.semester = semester;
        this.midtermScore = midtermScore;
        this.finalScore = finalScore;
    }

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public double getMidtermScore() { return midtermScore; }
    public void setMidtermScore(double midtermScore) { this.midtermScore = midtermScore; }

    public double getFinalScore() { return finalScore; }
    public void setFinalScore(double finalScore) { this.finalScore = finalScore; }

    // Calculate course grade (40% midterm + 60% final)
    public double getCourseGrade() {
        return (midtermScore * 0.4) + (finalScore * 0.6);
    }

    // Check if passed (grade >= 10)
    public boolean isPassed() {
        return getCourseGrade() >= 10.0;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "studentId='" + studentId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", semester=" + semester +
                ", midterm=" + midtermScore +
                ", final=" + finalScore +
                ", grade=" + getCourseGrade() +
                '}';
    }
}