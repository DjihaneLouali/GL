package studentmanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

// Strategy: In-Memory Storage
public class MemoryStorage implements StorageStrategy {
    private List<Student> memoryData = new ArrayList<>();

    @Override
    public void saveStudents(List<Student> students) {
        memoryData.clear();
        memoryData.addAll(students);
        System.out.println("✅ Data saved to memory");
    }

    @Override
    public List<Student> loadStudents() {
        System.out.println("📂 Data loaded from memory");
        return new ArrayList<>(memoryData);
    }
}