package com.studentApp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class StudentDAO {

    public boolean addStudent(Student student) {
        if (!student.isValid()) {
            System.err.println("Invalid student data!");
            return false;
        }

        String sql = "INSERT INTO students (name, email, age, course, gpa) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setInt(3, student.getAge());
            stmt.setString(4, student.getCourse());
            stmt.setDouble(5, student.getGpa());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Set the generated ID back to the student object
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        student.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                System.err.println("Error: Email already exists!");
            } else {
                System.err.println("Error adding student: " + e.getMessage());
            }
        }
        return false;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getString("course"),
                        rs.getDouble("gpa")
                );
                students.add(student);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
        }

        return students;
    }

    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getInt("age"),
                            rs.getString("course"),
                            rs.getDouble("gpa")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error searching student: " + e.getMessage());
        }

        return null;
    }


    public boolean updateStudent(Student student) {
        if (!student.isValid()) {
            System.err.println("Invalid student data!");
            return false;
        }

        String sql = "UPDATE students SET name = ?, email = ?, age = ?, course = ?, gpa = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setInt(3, student.getAge());
            stmt.setString(4, student.getCourse());
            stmt.setDouble(5, student.getGpa());
            stmt.setInt(6, student.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                System.err.println("Error: Email already exists!");
            } else {
                System.err.println("Error updating student: " + e.getMessage());
            }
        }

        return false;
    }

    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
        }

        return false;
    }

    public boolean studentExists(int id) {
        String sql = "SELECT 1 FROM students WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println("Error checking student existence: " + e.getMessage());
        }

        return false;
    }


    public int getTotalStudents() {
        String sql = "SELECT COUNT(*) as count FROM students";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (SQLException e) {
            System.err.println("Error counting students: " + e.getMessage());
        }

        return 0;
    }
}