package studentmanagementsystem.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Strategy: File Storage
public class FileStorage implements StorageStrategy {
    private static final String FILE_NAME = "students.dat";

    @Override
    public void saveStudents(List<Student> students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
            System.out.println("✅ Data saved to file: " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("❌ Error saving to file: " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Student> loadStudents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            List<Student> students = (List<Student>) ois.readObject();
            System.out.println("📂 Data loaded from file: " + FILE_NAME);
            return students;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("📂 No file found, returning empty list");
            return new ArrayList<>();
        }
    }
}