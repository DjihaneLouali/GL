package studentmanagementsystem.model;

import java.util.List;

// Strategy Pattern: Different ways to save/load data
public interface StorageStrategy {
    void saveStudents(List<Student> students);
    List<Student> loadStudents();
}