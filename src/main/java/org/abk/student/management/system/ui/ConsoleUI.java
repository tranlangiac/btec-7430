package org.abk.student.management.system.ui;

import org.abk.student.management.system.algorithms.searching.BinarySearchStrategy;
import org.abk.student.management.system.algorithms.searching.LinearSearchStrategy;
import org.abk.student.management.system.algorithms.searching.SearchStrategy;
import org.abk.student.management.system.algorithms.sorting.BubbleSortStrategy;
import org.abk.student.management.system.algorithms.sorting.MergeSortStrategy;
import org.abk.student.management.system.algorithms.sorting.QuickSortStrategy;
import org.abk.student.management.system.algorithms.sorting.SortStrategy;
import org.abk.student.management.system.models.Student;
import org.abk.student.management.system.models.StudentRank;
import org.abk.student.management.system.services.StudentService;
import org.abk.student.management.system.utils.*;

import java.util.Comparator;
import java.util.List;

/**
 * Console-based user interface for the Student Management System.
 * Handles all user interactions and display operations.
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public class ConsoleUI {
    private final StudentService studentService;
    private boolean running;

    public ConsoleUI(StudentService studentService) {
        this.studentService = studentService;
        this.running = true;
    }

    public void start() {
        while (running) {
            displayMainMenu();
            handleMainMenuChoice();
        }

        displayGoodbye();
    }

    private void displayLogo() {
        ColorUtil.clearScreen();
        ColorUtil.printBanner("STUDENT MANAGEMENT SYSTEM");
        ColorUtil.printBlankLine();
    }

    private void displayMainMenu() {
        displayLogo();
        ColorUtil.printHeader("MAIN MENU");

        String[] options = {
                "Add Student",
                "Edit Student",
                "Delete Student",
                "Display All Students",
                "Search Student",
                "Sort Students",
                "View Statistics",
                "View Ranking Table",
                "Algorithm Comparison",
                "Exit"
        };

        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }

        ColorUtil.printSeparator();
        ColorUtil.printBlankLine();
    }

    private void handleMainMenuChoice() {
        int choice = InputUtil.readMenuChoice("Enter your choice: ", 10);
        ColorUtil.clearScreen();
        displayLogo();

        switch (choice) {
            case 1 -> addStudent();
            case 2 -> editStudent();
            case 3 -> deleteStudent();
            case 4 -> displayAllStudents();
            case 5 -> searchStudent();
            case 6 -> sortStudents();
            case 7 -> viewStatistics();
            case 8 -> viewRankingTable();
            case 9 -> algorithmComparison();
            case 10 -> exitApplication();
        }
    }

    private void addStudent() {
        ColorUtil.printHeader("ADD NEW STUDENT");

        try {
            String id = InputUtil.readStudentId("Enter Student ID: ");

            if (studentService.studentExists(id)) {
                ColorUtil.printError("Student ID already exists!");
                ColorUtil.printSeparator();
                InputUtil.pressEnterToContinue();
                return;
            }

            String name = InputUtil.readStudentName("Enter Student Name: ");
            double mark = InputUtil.readStudentMark("Enter Student Mark (0.0-10.0): ");

            boolean success = studentService.addStudent(id, name, mark);

            if (success) {
                Student student = studentService.findStudentById(id);
                ColorUtil.printSuccess("Student added successfully!");
                ColorUtil.printBlankLine();
                ColorUtil.printInfo("Student Details:");
                TableDisplayUtil.displayStudent(student);
            } else {
                ColorUtil.printError("Failed to add student.");
            }

        } catch (Exception e) {
            ColorUtil.printError("Error: " + e.getMessage());
        }

        InputUtil.pressEnterToContinue();
    }

    private void editStudent() {
        ColorUtil.printHeader("EDIT STUDENT");

        if (studentService.isEmpty()) {
            ColorUtil.printWarning("No students in the system.");
            InputUtil.pressEnterToContinue();
            return;
        }

        String id = InputUtil.readStudentId("Enter Student ID to edit: ");
        Student student = studentService.findStudentById(id);
        ColorUtil.printBlankLine();

        if (student == null) {
            ColorUtil.printError("Student not found!");
            InputUtil.pressEnterToContinue();
            return;
        }

        ColorUtil.printInfo("Current student information:");
        TableDisplayUtil.displayStudent(student);
        ColorUtil.printBlankLine();

        ColorUtil.printInfo("What would you like to edit?");
        System.out.println("1. Name");
        System.out.println("2. Mark");
        System.out.println("3. Both");
        System.out.println("4. Cancel");

        ColorUtil.printBlankLine();
        int choice = InputUtil.readMenuChoice("Enter choice: ", 4);

        try {
            switch (choice) {
                case 1 -> {
                    String newName = InputUtil.readStudentName("Enter new name: ");
                    if (studentService.updateStudentName(id, newName)) {
                        ColorUtil.printSuccess("Name updated successfully!");
                    }
                }
                case 2 -> {
                    double newMark = InputUtil.readStudentMark("Enter new mark (0.0-10.0): ");
                    if (studentService.updateStudentMark(id, newMark)) {
                        ColorUtil.printSuccess("Mark updated successfully!");
                    }
                }
                case 3 -> {
                    String newName = InputUtil.readStudentName("Enter new name: ");
                    double newMark = InputUtil.readStudentMark("Enter new mark (0.0-10.0): ");
                    if (studentService.updateStudent(id, newName, newMark)) {
                        ColorUtil.printSuccess("Student updated successfully!");
                    }
                }
                case 4 -> {
                    ColorUtil.printInfo("Edit cancelled.");
                    InputUtil.pressEnterToContinue();
                    return;
                }
            }

            Student updated = studentService.findStudentById(id);
            ColorUtil.printBlankLine();
            ColorUtil.printInfo("Updated student information:");
            TableDisplayUtil.displayStudent(updated);

        } catch (Exception e) {
            ColorUtil.printError("Error: " + e.getMessage());
        }

        InputUtil.pressEnterToContinue();
    }

    private void deleteStudent() {
        ColorUtil.printHeader("DELETE STUDENT");

        if (studentService.isEmpty()) {
            ColorUtil.printWarning("No students in the system.");
            InputUtil.pressEnterToContinue();
            return;
        }

        String id = InputUtil.readStudentId("Enter Student ID to delete: ");
        Student student = studentService.findStudentById(id);

        if (student == null) {
            ColorUtil.printError("Student not found!");
            InputUtil.pressEnterToContinue();
            return;
        }

        ColorUtil.printBlankLine();
        ColorUtil.printInfo("Student to delete:");
        TableDisplayUtil.displayStudent(student);
        ColorUtil.printBlankLine();

        boolean confirm = InputUtil.readConfirmation("Are you sure you want to delete this student?");

        if (confirm) {
            if (studentService.deleteStudent(id)) {
                ColorUtil.printSuccess("Student deleted successfully!");
            } else {
                ColorUtil.printError("Failed to delete student.");
            }
        } else {
            ColorUtil.printInfo("Delete cancelled.");
        }

        InputUtil.pressEnterToContinue();
    }

    private void displayAllStudents() {
        ColorUtil.printHeader("ALL STUDENTS");

        if (studentService.isEmpty()) {
            ColorUtil.printWarning("No students in the system.");
            InputUtil.pressEnterToContinue();
            return;
        }

        List<Student> students = studentService.findAllStudents();
        TableDisplayUtil.displayStudents(students);

        ColorUtil.printBlankLine();
        ColorUtil.printInfo("Total: " + students.size() + " student(s)");

        InputUtil.pressEnterToContinue();
    }

    private void searchStudent() {
        ColorUtil.printHeader("SEARCH STUDENT");

        if (studentService.isEmpty()) {
            ColorUtil.printWarning("No students in the system.");
            InputUtil.pressEnterToContinue();
            return;
        }

        ColorUtil.printInfo("Search by:");
        System.out.println("1. Student ID");
        System.out.println("2. Student Name");
        System.out.println("3. Student Rank");
        System.out.println("4. Back to Main Menu");

        ColorUtil.printBlankLine();
        int choice = InputUtil.readMenuChoice("Enter choice: ", 4);

        switch (choice) {
            case 1 -> searchById();
            case 2 -> searchByName();
            case 3 -> searchByRank();
            case 4 -> {}
        }
    }

    private void searchById() {
        String id = InputUtil.readStudentId("Enter Student ID: ");
        Student student = studentService.findStudentById(id);

        if (student != null) {
            ColorUtil.printSuccess("Student found!");
            ColorUtil.printBlankLine();
            TableDisplayUtil.displayStudent(student);
        } else {
            ColorUtil.printError("Student not found!");
        }

        InputUtil.pressEnterToContinue();
    }

    private void searchByName() {
        String nameQuery = InputUtil.readNonEmptyString("Enter name (or part of name): ");
        List<Student> students = studentService.findStudentsByName(nameQuery);

        if (!students.isEmpty()) {
            ColorUtil.printSuccess("Found " + students.size() + " student(s)!");
            ColorUtil.printBlankLine();
            TableDisplayUtil.displayStudents(students);
        } else {
            ColorUtil.printError("No students found with that name.");
        }

        InputUtil.pressEnterToContinue();
    }

    private void searchByRank() {
        ColorUtil.printBlankLine();
        ColorUtil.printInfo("Select rank:");
        StudentRank[] ranks = StudentRank.values();
        for (int i = 0; i < ranks.length; i++) {
            System.out.println((i + 1) + ". " + ranks[i].getDisplayName());
        }

        ColorUtil.printBlankLine();
        int choice = InputUtil.readMenuChoice("Enter choice: ", ranks.length);
        StudentRank selectedRank = ranks[choice - 1];

        List<Student> students = studentService.findStudentsByRank(selectedRank);

        if (!students.isEmpty()) {
            ColorUtil.printSuccess("Found " + students.size() + " student(s) with rank " +
                    ColorUtil.colorizedRank(selectedRank) + "!");
            ColorUtil.printBlankLine();
            TableDisplayUtil.displayStudents(students);
        } else {
            ColorUtil.printWarning("No students found with that rank.");
        }

        InputUtil.pressEnterToContinue();
    }

    private void sortStudents() {
        ColorUtil.printHeader("SORT STUDENTS");

        if (studentService.isEmpty()) {
            ColorUtil.printWarning("No students in the system.");
            InputUtil.pressEnterToContinue();
            return;
        }

        ColorUtil.printInfo("Sort by:");
        System.out.println("1. Student ID");
        System.out.println("2. Student Name");
        System.out.println("3. Student Mark");
        System.out.println("4. Student Rank");
        System.out.println("5. Back to Main Menu");

        ColorUtil.printBlankLine();
        int criteria = InputUtil.readMenuChoice("Enter choice: ", 5);
        if (criteria == 5) {
            InputUtil.pressEnterToContinue();
            return;
        }

        ColorUtil.printBlankLine();
        ColorUtil.printInfo("Choose sorting algorithm:");
        System.out.println("1. Bubble Sort (O(n²) - Simple)");
        System.out.println("2. Quick Sort (O(n log n) - Fast)");
        System.out.println("3. Merge Sort (O(n log n) - Stable)");

        ColorUtil.printBlankLine();
        int algorithm = InputUtil.readMenuChoice("Enter choice: ", 3);

        ColorUtil.printBlankLine();
        ColorUtil.printInfo("Choose sorting order:");
        System.out.println("1. Ascending (A-Z, 0-10)");
        System.out.println("2. Descending (Z-A, 10-0)");

        ColorUtil.printBlankLine();
        int sortOrder = InputUtil.readMenuChoice("Enter choice: ", 2);
        boolean ascending = sortOrder == 1;

        SortStrategy strategy = switch (algorithm) {
            case 1 -> new BubbleSortStrategy();
            case 2 -> new QuickSortStrategy();
            default -> new MergeSortStrategy();
        };

        ColorUtil.printBlankLine();
        ColorUtil.printInfo("Sorting with " + strategy.getAlgorithmName() + "...");

        List<Student> sorted = switch (criteria) {
            case 1 -> studentService.sortStudentsById(strategy, ascending);
            case 2 -> studentService.sortStudentsByName(strategy, ascending);
            case 3 -> studentService.sortStudentsByMark(strategy, ascending);
            default -> studentService.sortStudentsByRank(strategy, ascending);
        };

        ColorUtil.printSuccess("Sorted successfully!");
        ColorUtil.printBlankLine();
        TableDisplayUtil.displayStudents(sorted);

        ColorUtil.printBlankLine();
        ColorUtil.printInfo("Performance: " + strategy);

        InputUtil.pressEnterToContinue();
    }

    private void viewStatistics() {
        ColorUtil.printHeader("STUDENT STATISTICS");

        if (studentService.isEmpty()) {
            ColorUtil.printWarning("No students in the system.");
            InputUtil.pressEnterToContinue();
            return;
        }

        TableDisplayUtil.displayStatistics(studentService);

        ColorUtil.printBlankLine();
        ColorUtil.printInfo("Students by rank:");
        TableDisplayUtil.displayStudentsByRank(studentService.findAllStudents());

        InputUtil.pressEnterToContinue();
    }

    private void viewRankingTable() {
        ColorUtil.printHeader("STUDENT RANKING TABLE");
        TableDisplayUtil.displayRankingTable();
        InputUtil.pressEnterToContinue();
    }

    private void algorithmComparison() {
        ColorUtil.printHeader("ALGORITHM COMPARISON");

        if (studentService.isEmpty()) {
            ColorUtil.printWarning("No students in the system to compare.");
            InputUtil.pressEnterToContinue();
            return;
        }

        ColorUtil.printInfo("Choose comparison type:");
        System.out.println("1. Sorting Algorithms");
        System.out.println("2. Searching Algorithms");
        System.out.println("3. Back to Main Menu");

        ColorUtil.printBlankLine();
        int choice = InputUtil.readMenuChoice("Enter choice: ", 3);
        InputUtil.pressEnterToContinue();

        switch (choice) {
            case 1 -> compareSortingAlgorithms();
            case 2 -> compareSearchingAlgorithms();
            case 3 -> {}
        }
    }

    private void compareSortingAlgorithms() {
        List<Student> students = studentService.findAllStudents();

        SortStrategy[] strategies = {
                new BubbleSortStrategy(),
                new QuickSortStrategy(),
                new MergeSortStrategy()
        };

        Comparator<Student> comparator = Comparator.comparingDouble(Student::getMark).reversed();

        SortComparisonUtil.runComprehensiveBenchmark(students, comparator, strategies);

        InputUtil.pressEnterToContinue();
    }

    private void compareSearchingAlgorithms() {
        String id = InputUtil.readStudentId("Enter Student ID to search for: ");
        Student target = studentService.findStudentById(id);

        if (target == null) {
            ColorUtil.printWarning("Student not found. Will demonstrate search with this ID anyway.");
            target = new Student(id, "Test", 5.0);
        }

        List<Student> students = studentService.sortStudentsById(new QuickSortStrategy(), true);

        SearchStrategy[] strategies = {
                new LinearSearchStrategy(),
                new BinarySearchStrategy()
        };

        Comparator<Student> comparator = Comparator.comparing(Student::getId);

        SearchComparisonUtil.runComprehensiveBenchmark(students, target, comparator, true, strategies);

        InputUtil.pressEnterToContinue();
    }

    private void exitApplication() {
        boolean confirm = InputUtil.readConfirmation("Are you sure you want to exit?");

        if (confirm) {
            running = false;
        }
    }

    private void displayGoodbye() {
        InputUtil.clearScreen();
        ColorUtil.printBanner("THANK YOU");
        ColorUtil.printSuccess("Thank you for using Student Management System!");
        ColorUtil.printInfo("Developed by Soft Development ABK");
        ColorUtil.printBlankLine();
    }
}
