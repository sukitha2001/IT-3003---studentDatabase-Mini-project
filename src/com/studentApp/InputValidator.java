package com.studentApp;

import java.util.Scanner;
import java.util.regex.Pattern;


public class InputValidator {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");

    private Scanner scanner;

    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }


    public int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }


    public int getIntInput(String prompt, int min, int max) {
        while (true) {
            int value = getIntInput(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.printf("Please enter a value between %d and %d.%n", min, max);
        }
    }


    public double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }


    public double getGpaInput(String prompt) {
        while (true) {
            double gpa = getDoubleInput(prompt);
            if (gpa >= 0.0 && gpa <= 4.0) {
                return gpa;
            }
            System.out.println("GPA must be between 0.00 and 4.00.");
        }
    }


    public String getStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }


    public String getOptionalStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }


    public String getEmailInput(String prompt) {
        while (true) {
            String email = getStringInput(prompt);
            if (isValidEmail(email)) {
                return email;
            }
            System.out.println("Please enter a valid email address.");
        }
    }


    public int getAgeInput(String prompt) {
        return getIntInput(prompt + " (1-150): ", 1, 150);
    }


    public boolean getConfirmation(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/N): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no") || input.isEmpty()) {
                return false;
            }

            System.out.println("Please enter 'y' for yes or 'n' for no.");
        }
    }


    public int getMenuChoice(String prompt, int min, int max) {
        return getIntInput(prompt, min, max);
    }


    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }


    public static boolean isValidName(String name) {
        return name != null && name.trim().length() > 0 &&
                name.matches("^[a-zA-Z\\s]+$");
    }


    public static boolean isValidCourse(String course) {
        return course != null && course.trim().length() > 0 &&
                course.matches("^[a-zA-Z0-9\\s]+$");
    }
}