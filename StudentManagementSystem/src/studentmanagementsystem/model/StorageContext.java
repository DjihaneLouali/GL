package studentmanagementsystem.model;

import java.util.List;

// Strategy Pattern Context
public class StorageContext {
    private StorageStrategy strategy;

    public StorageContext(StorageStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(StorageStrategy strategy) {
        this.strategy = strategy;
    }

    public void saveData(List<Student> students) {
        strategy.saveStudents(students);
    }

    public List<Student> loadData() {
        return strategy.loadStudents();
    }
}