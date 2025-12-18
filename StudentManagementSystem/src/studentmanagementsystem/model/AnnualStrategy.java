package studentmanagementsystem.model;

import java.util.List;

public class AnnualStrategy implements GradingStrategy {
    
    @Override
    public double calculateAverage(String studentId, List<Grade> grades, List<SimpleCourse> courses) {
        Semester1Strategy s1 = new Semester1Strategy();
        Semester2Strategy s2 = new Semester2Strategy();
        
        double sem1Avg = s1.calculateAverage(studentId, grades, courses);
        double sem2Avg = s2.calculateAverage(studentId, grades, courses);
        
        // Simple average of both semesters
        if (sem1Avg == 0 && sem2Avg == 0) return 0.0;
        if (sem1Avg == 0) return sem2Avg;
        if (sem2Avg == 0) return sem1Avg;
        
        return (sem1Avg + sem2Avg) / 2.0;
    }
    
    @Override
    public String getStrategyName() {
        return "Annual Average";
    }
}