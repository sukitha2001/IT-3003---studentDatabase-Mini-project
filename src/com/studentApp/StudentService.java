package com.studentApp;

import java.util.List;
import java.util.Scanner;


public class StudentService {
    private StudentDAO studentDAO;
    private InputValidator validator;
    private Scanner scanner;

    public StudentService() {
        this.studentDAO = new StudentDAO();
        this.scanner = new Scanner(System.in);
        this.validator = new InputValidator(scanner);
    }

    //adding a new student
    public void addStudent() {
        System.out.println("\n--- Add New Student ---");

        try {
            String name = validator.getStringInput("Enter student name: ");
            String email = validator.getEmailInput("Enter email: ");
            int age = validator.getAgeInput("Enter age");
            String course = validator.getStringInput("Enter course: ");
            double gpa = validator.getGpaInput("Enter GPA (0.00-4.00): ");

            Student student = new Student(name, email, age, course, gpa);

            if (studentDAO.addStudent(student)) {
                System.out.println("Student added successfully with ID: " + student.getId());
            } else {
                System.out.println("Failed to add student.");
            }

        } catch (Exception e) {
            System.err.println("Error adding student: " + e.getMessage());
        }
    }

    //viewing all students

    public void viewAllStudents() {
        System.out.println("\n--- All Students ---");

        List<Student> students = studentDAO.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found in the database.");
            return;
        }

        displayStudentTable(students);
        System.out.println("\nTotal students: " + students.size());
    }


    //Updating Student Details
    public void updateStudent() {
        int id = validator.getIntInput("\nEnter student ID to update: ");

        Student existingStudent = studentDAO.getStudentById(id);
        if (existingStudent == null) {
            System.out.println("Student with ID " + id + " not found.");
            return;
        }

        System.out.println("\nCurrent student details:");
        displayStudentDetails(existingStudent);

        System.out.println("\nEnter new details (press Enter to keep current value):");

        // Get updated values
        String name = validator.getOptionalStringInput("New name [" + existingStudent.getName() + "]: ");
        if (name.isEmpty()) name = existingStudent.getName();

        String email = validator.getOptionalStringInput("New email [" + existingStudent.getEmail() + "]: ");
        if (email.isEmpty()) {
            email = existingStudent.getEmail();
        } else if (!InputValidator.isValidEmail(email)) {
            System.out.println("Invalid email format. Keeping current email.");
            email = existingStudent.getEmail();
        }

        String ageStr = validator.getOptionalStringInput("New age [" + existingStudent.getAge() + "]: ");
        int age = existingStudent.getAge();
        if (!ageStr.isEmpty()) {
            try {
                age = Integer.parseInt(ageStr);
                if (age < 1 || age > 150) {
                    System.out.println("Invalid age. Keeping current age.");
                    age = existingStudent.getAge();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid age format. Keeping current age.");
            }
        }

        String course = validator.getOptionalStringInput("New course [" + existingStudent.getCourse() + "]: ");
        if (course.isEmpty()) course = existingStudent.getCourse();

        String gpaStr = validator.getOptionalStringInput("New GPA [" + existingStudent.getGpa() + "]: ");
        double gpa = existingStudent.getGpa();
        if (!gpaStr.isEmpty()) {
            try {
                gpa = Double.parseDouble(gpaStr);
                if (gpa < 0.0 || gpa > 4.0) {
                    System.out.println("Invalid GPA. Keeping current GPA.");
                    gpa = existingStudent.getGpa();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid GPA format. Keeping current GPA.");
            }
        }

        // Create updated student
        Student updatedStudent = new Student(id, name, email, age, course, gpa);

        if (studentDAO.updateStudent(updatedStudent)) {
            System.out.println("Student updated successfully!");
            displayStudentDetails(updatedStudent);
        } else {
            System.out.println("Failed to update student.");
        }
    }

    //Delete Student
    public void deleteStudent() {
        int id = validator.getIntInput("\nEnter student ID to delete: ");

        Student student = studentDAO.getStudentById(id);
        if (student == null) {
            System.out.println("Student with ID " + id + " not found.");
            return;
        }

        System.out.println("\nStudent to be deleted:");
        displayStudentDetails(student);

        if (validator.getConfirmation("\nAre you sure you want to delete this student?")) {
            if (studentDAO.deleteStudent(id)) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Failed to delete student.");
            }
        } else {
            System.out.println("Delete operation cancelled.");
        }
    }



     //Display a student's details

    private void displayStudentDetails(Student student) {
        System.out.println("\n--- Student Details ---");
        System.out.println("ID: " + student.getId());
        System.out.println("Name: " + student.getName());
        System.out.println("Email: " + student.getEmail());
        System.out.println("Age: " + student.getAge());
        System.out.println("Course: " + student.getCourse());
        System.out.printf("GPA: %.2f%n", student.getGpa());
    }

    private void displayStudentTable(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students to display.");
            return;
        }

        // Header
        System.out.printf("%-5s %-20s %-25s %-5s %-20s %-6s%n",
                "ID", "Name", "Email", "Age", "Course", "GPA");
        System.out.println("-".repeat(85));

        // Student rows
        for (Student student : students) {
            System.out.printf("%-5d %-20s %-25s %-5d %-20s %-6.2f%n",
                    student.getId(),
                    truncate(student.getName(), 20),
                    truncate(student.getEmail(), 25),
                    student.getAge(),
                    truncate(student.getCourse(), 20),
                    student.getGpa());
        }
    }


    private String truncate(String str, int length) {
        if (str == null) return "";
        return str.length() <= length ? str : str.substring(0, length - 3) + "...";
    }


    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}