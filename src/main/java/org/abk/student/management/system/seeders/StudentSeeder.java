package org.abk.student.management.system.seeders;

import org.abk.student.management.system.models.StudentRank;
import org.abk.student.management.system.services.StudentService;
import org.abk.student.management.system.utils.ColorUtil;
import org.abk.student.management.system.utils.InputUtil;

public record StudentSeeder(StudentService studentService) {
    public boolean promptSeedSampleData() {
        ColorUtil.printBanner("WELCOME");
        ColorUtil.printBlankLine();
        ColorUtil.printHeader("INITIAL CONFIGURATION");

        return InputUtil.readConfirmation("Would you like to load sample student data?");
    }

    public void promptSeedMethod() {
        ColorUtil.printBlankLine();
        ColorUtil.printInfo("Choose dataset type:");
        System.out.println("1. Default");
        System.out.println("2. Small");
        System.out.println("3. Balanced");
        System.out.println("4. Custom");

        ColorUtil.printBlankLine();
        int choice = InputUtil.readMenuChoice("Enter choice: ", 4);

        switch (choice) {
            case 1 -> seedDefaultStudents();
            case 2 -> seedSmallDataset();
            case 3 -> seedBalancedDataset();
            default -> seedCustomDataset();
        }

        displaySeededDataSummary();
    }

    public void seedDefaultStudents() {
        ColorUtil.printInfo("Loading sample student data...");

        int successCount = 0;

        try {
            if (studentService.addStudent("S001", "Alice Johnson", 9.5)) successCount++;
            if (studentService.addStudent("S002", "Fiona Green", 9.8)) successCount++;
            if (studentService.addStudent("S003", "Laura Palmer", 9.2)) successCount++;

            if (studentService.addStudent("S004", "Bob Smith", 8.2)) successCount++;
            if (studentService.addStudent("S005", "George Wilson", 7.8)) successCount++;
            if (studentService.addStudent("S006", "Julia Roberts", 8.9)) successCount++;
            if (studentService.addStudent("S007", "Oscar Wilde", 8.5)) successCount++;

            if (studentService.addStudent("S008", "Charlie Brown", 7.0)) successCount++;
            if (studentService.addStudent("S009", "Diana Prince", 6.8)) successCount++;
            if (studentService.addStudent("S010", "Kevin Hart", 7.2)) successCount++;
            if (studentService.addStudent("S011", "Mike Ross", 6.5)) successCount++;

            if (studentService.addStudent("S012", "Ethan Hunt", 6.2)) successCount++;
            if (studentService.addStudent("S013", "Hannah Lee", 5.5)) successCount++;
            if (studentService.addStudent("S014", "Nina Simone", 5.8)) successCount++;

            if (studentService.addStudent("S015", "Ian Malcolm", 4.2)) successCount++;

            ColorUtil.printSuccess("Sample data loaded: " + successCount + " students added!");
        } catch (Exception e) {
            ColorUtil.printError("Error loading sample data: " + e.getMessage());
        }
    }

    public void seedSmallDataset() {
        ColorUtil.printInfo("Loading small dataset (5 students)...");

        int successCount = 0;

        try {
            if (studentService.addStudent("S001", "Alice Johnson", 9.5)) successCount++;
            if (studentService.addStudent("S002", "Bob Smith", 8.2)) successCount++;
            if (studentService.addStudent("S003", "Charlie Brown", 7.0)) successCount++;
            if (studentService.addStudent("S004", "Diana Prince", 6.2)) successCount++;
            if (studentService.addStudent("S005", "Ethan Hunt", 4.5)) successCount++;

            ColorUtil.printSuccess("Small dataset loaded: " + successCount + " students");
        } catch (Exception e) {
            ColorUtil.printError("Error loading small dataset: " + e.getMessage());
        }
    }

    public void seedBalancedDataset() {
        ColorUtil.printInfo("Loading balanced dataset (equal ranks)...");

        int successCount = 0;

        try {
            if (studentService.addStudent("S001", "Excellent Student 1", 9.9)) successCount++;
            if (studentService.addStudent("S002", "Excellent Student 2", 9.5)) successCount++;
            if (studentService.addStudent("S003", "Excellent Student 3", 9.3)) successCount++;
            if (studentService.addStudent("S004", "Excellent Student 4", 9.1)) successCount++;
            if (studentService.addStudent("S005", "Excellent Student 5", 9.0)) successCount++;

            if (studentService.addStudent("S006", "Very Good Student 1", 8.8)) successCount++;
            if (studentService.addStudent("S007", "Very Good Student 2", 8.5)) successCount++;
            if (studentService.addStudent("S008", "Very Good Student 3", 8.2)) successCount++;
            if (studentService.addStudent("S009", "Very Good Student 4", 7.9)) successCount++;
            if (studentService.addStudent("S010", "Very Good Student 5", 7.5)) successCount++;

            if (studentService.addStudent("S011", "Good Student 1", 7.4)) successCount++;
            if (studentService.addStudent("S012", "Good Student 2", 7.2)) successCount++;
            if (studentService.addStudent("S013", "Good Student 3", 7.0)) successCount++;
            if (studentService.addStudent("S014", "Good Student 4", 6.8)) successCount++;
            if (studentService.addStudent("S015", "Good Student 5", 6.5)) successCount++;

            if (studentService.addStudent("S016", "Medium Student 1", 6.4)) successCount++;
            if (studentService.addStudent("S017", "Medium Student 2", 6.0)) successCount++;
            if (studentService.addStudent("S018", "Medium Student 3", 5.7)) successCount++;
            if (studentService.addStudent("S019", "Medium Student 4", 5.3)) successCount++;
            if (studentService.addStudent("S020", "Medium Student 5", 5.0)) successCount++;

            if (studentService.addStudent("S021", "Fail Student 1", 4.8)) successCount++;
            if (studentService.addStudent("S022", "Fail Student 2", 4.0)) successCount++;
            if (studentService.addStudent("S023", "Fail Student 3", 3.2)) successCount++;
            if (studentService.addStudent("S024", "Fail Student 4", 2.5)) successCount++;
            if (studentService.addStudent("S025", "Fail Student 5", 1.0)) successCount++;

            ColorUtil.printSuccess("Balanced dataset loaded: " + successCount + " students");
        } catch (Exception e) {
            ColorUtil.printError("Error loading balanced dataset: " + e.getMessage());
        }
    }

    public void seedCustomDataset() {
        int count = InputUtil.readInt("Enter dataset size: ");

        ColorUtil.printInfo("Generating " + count + " students for testing...");

        int successCount = 0;
        String[] firstNames = {
                "Alice", "Bob", "Charlie", "Diana", "Ethan", "Fiona",
                "George", "Hannah", "Ian", "Julia", "Kevin", "Laura",
                "Mike", "Nina", "Oscar", "Paul", "Quinn", "Rachel",
                "Steve", "Tina", "Uma", "Victor", "Wendy", "Xavier",
                "Yara", "Zack"
        };

        String[] lastNames = {
                "Anderson", "Brown", "Clark", "Davis", "Evans", "Fisher",
                "Garcia", "Harris", "Irwin", "Johnson", "King", "Lee",
                "Martinez", "Nelson", "O'Brien", "Parker", "Quinn", "Roberts",
                "Smith", "Taylor", "Underwood", "Vazquez", "Wilson", "Young"
        };

        try {
            for (int i = 1; i <= count; i++) {
                String id = String.format("S%04d", i);
                String firstName = firstNames[i % firstNames.length];
                String lastName = lastNames[(i / firstNames.length) % lastNames.length];
                String name = firstName + " " + lastName;

                double mark = generateRealisticMark(i);

                if (studentService.addStudent(id, name, mark)) {
                    successCount++;
                }

                if (count > 100 && i % 100 == 0) {
                    System.out.print(".");
                }
            }

            if (count > 100) {
                System.out.println();
            }

            ColorUtil.printSuccess("Large dataset loaded: " + successCount + " students");
        } catch (Exception e) {
            ColorUtil.printError("Error generating large dataset: " + e.getMessage());
        }
    }

    private double generateRealisticMark(int index) {
        // Use sine wave for variation + some randomness
        double base = 7.0; // Average mark
        double variation = 2.5 * Math.sin(index * Math.PI / 10);
        double random = (index * 37) % 10 / 10.0; // Pseudo-random 0-1

        double mark = base + variation + random;

        // Clamp to valid range
        mark = Math.max(0.0, Math.min(10.0, mark));

        // Round to 1 decimal place
        return Math.round(mark * 10.0) / 10.0;
    }

    private void displaySeededDataSummary() {
        ColorUtil.printBlankLine();
        ColorUtil.printInfo("Data Summary:");

        for (StudentRank rank : StudentRank.values()) {
            int count = studentService.countStudentsByRank(rank);
            if (count > 0) {
                System.out.println("  • " + ColorUtil.colorizedRank(rank) +
                        ": " + count + " student" + (count > 1 ? "s" : ""));
            }
        }
    }
}
