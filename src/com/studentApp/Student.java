package com.studentApp;


public class Student {
    private int id;
    private String name;
    private String email;
    private int age;
    private String course;
    private double gpa;

    // Default constructor
    public Student() {}

    // Constructor without ID (for new students)
    public Student(String name, String email, int age, String course, double gpa) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.course = course;
        this.gpa = gpa;
    }

    // Constructor with ID (for existing students)
    public Student(int id, String name, String email, int age, String course, double gpa) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.course = course;
        this.gpa = gpa;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getAge() { return age; }
    public String getCourse() { return course; }
    public double getGpa() { return gpa; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setAge(int age) { this.age = age; }
    public void setCourse(String course) { this.course = course; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%s', email='%s', age=%d, course='%s', gpa=%.2f}",
                id, name, email, age, course, gpa);
    }

    public boolean isValid() {
        return name != null && !name.trim().isEmpty() &&
                email != null && !email.trim().isEmpty() &&
                age > 0 && age < 150 &&
                course != null && !course.trim().isEmpty() &&
                gpa >= 0.0 && gpa <= 4.0;
    }
}