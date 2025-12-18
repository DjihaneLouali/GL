package studentmanagementsystem.model;

import java.util.List;

public class Semester1Strategy implements GradingStrategy {
    
    @Override
    public double calculateAverage(String studentId, List<Grade> grades, List<SimpleCourse> courses) {
        double totalPoints = 0.0;
        int totalCoefficients = 0;
        
        // Filter grades for semester 1 only
        for (Grade grade : grades) {
            if (grade.getStudentId().equals(studentId) && grade.getSemester() == 1) {
                // Find course coefficient
                for (SimpleCourse course : courses) {
                    if (course.getName().equals(grade.getCourseName())) {
                        double courseGrade = grade.getCourseGrade();
                        int coefficient = course.getCredits();
                        
                        totalPoints += courseGrade * coefficient;
                        totalCoefficients += coefficient;
                        break;
                    }
                }
            }
        }
        
        if (totalCoefficients == 0) return 0.0;
        return totalPoints / totalCoefficients;
    }
    
    @Override
    public String getStrategyName() {
        return "Semester 1";
    }
}