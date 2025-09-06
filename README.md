# Student Database Management System

A console-based Java application using JDBC to manage student information in a MySQL database.

## Features

- Add new students with name, email, age, course, and GPA
- View all students in a formatted table
- Update student information
- Delete students with confirmation
- Input validation and error handling
- Duplicate email prevention

## Technical Requirements

- **Database System:** MySQL
- **Java Version:** Java 8 or later
- **Dependencies:** MySQL JDBC Driver (mysql-connector-java)

## Prerequisites

1. **Java Development Kit (JDK) 8 or later**
   - Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)

2. **MySQL Server**
   - Download and install from [MySQL Official Website](https://dev.mysql.com/downloads/mysql/)
   - Make note of your root password during installation

3. **MySQL JDBC Driver**
   - Download `mysql-connector-java-8.0.33.jar` (or latest version) from [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)

## Setup Instructions

### 1. Database Setup

1. **Start MySQL Server**
   - On Windows: Start MySQL service from Services or MySQL Workbench

2. **Create Database**
   ```sql
   mysql -u root -p
   CREATE DATABASE student_db;
   EXIT;
   ```

3. **Update Database Credentials**
   - Open `StudentDatabaseApp.java`
   - Modify these lines with your MySQL credentials:
   ```java
   private static final String USERNAME = "root"; // Your MySQL username
   private static final String PASSWORD = "your_password"; // Your MySQL password
   ```

### 2. Compilation and Execution

1. **Download the MySQL JDBC Driver**
   - Place `mysql-connector-java-8.0.33.jar` in your project directory

2. **Compile the Java Application**
   ```bash
   javac -cp ".:mysql-connector-java-8.0.33.jar" StudentDatabaseApp.java
   ```

3. **Run the Application**
   ```bash
   java -cp ".:mysql-connector-java-8.0.33.jar" StudentDatabaseApp
   ```
