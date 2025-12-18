package studentmanagementsystem.model;

import java.util.List;

public interface GradingStrategy {
    double calculateAverage(String studentId, List<Grade> grades, List<SimpleCourse> courses);
    String getStrategyName();
}