package com.studentApp;

import java.util.Scanner;


public class StudentDatabaseApp {
    private StudentService studentService;
    private Scanner scanner;
    private InputValidator validator;

    public StudentDatabaseApp() {
        this.scanner = new Scanner(System.in);
        this.validator = new InputValidator(scanner);
        this.studentService = new StudentService();
    }

    private void initialize() {
        System.out.println("=".repeat(50));
        System.out.println("  Student Database Management System");
        System.out.println("=".repeat(50));

        // Test database connection
        if (!DatabaseConnection.testConnection()) {
            System.err.println("Failed to connect to database. Please check your configuration.");
            System.exit(1);
        }

        // Initialize database tables
        DatabaseConnection.initializeDatabase();

        System.out.println("Application initialized successfully!");
    }
}