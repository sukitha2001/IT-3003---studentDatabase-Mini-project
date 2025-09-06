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

    public void showMenu() {
        initialize();

        while (true) {
            displayMenu();

            int choice = validator.getMenuChoice("Enter your choice: ", 1, 8);

            try {
                switch (choice) {
                    case 1 -> studentService.addStudent();
                    case 2 -> studentService.viewAllStudents();
                    case 3 -> studentService.updateStudent();
                    case 4 -> studentService.deleteStudent();
                    case 5 -> {
                        exitApplication();
                        return;
                    }
                }

                // Pause before showing menu again
                pauseForUser();

            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
                pauseForUser();
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("    STUDENT DATABASE MENU");
        System.out.println("=".repeat(40));
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Exit");
        System.out.println("=".repeat(40));
    }


    private void pauseForUser() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }


    private void exitApplication() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("Shutting down application...");


        try {
            studentService.close();
            DatabaseConnection.closeConnection();
            scanner.close();
        } catch (Exception e) {
            System.err.println("Error during shutdown: " + e.getMessage());
        }

        System.out.println("Thank you for using Student Database Management System!");
        System.out.println("=".repeat(40));
    }


    private void setupShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nShutdown signal received...");
            DatabaseConnection.closeConnection();
        }));
    }

    public static void main(String[] args) {
        try {
            StudentDatabaseApp app = new StudentDatabaseApp();
            app.setupShutdownHook();
            app.showMenu();

        } catch (Exception e) {
            System.err.println("Critical error occurred: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}