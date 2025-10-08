package org.abk.student.management.system.shared.util;

import org.abk.student.management.system.model.Student;
import org.abk.student.management.system.model.StudentRank;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import org.abk.student.management.system.services.StudentService;

import java.util.List;

/**
 * Utility class for displaying data in formatted ASCII tables with color support.
 * Uses the AsciiTable library for clean console output and Jansi for colors.
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public class TableDisplayUtil {
    private TableDisplayUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static void displayStudents(List<Student> students) {
        if (students == null || students.isEmpty()) {
            ColorUtil.printWarning("No students to display.");
            return;
        }

        AsciiTable table = new AsciiTable();

        table.addRule();
        table.addRow("ID", "Name", "Mark", "Rank");
        table.addRule();

        for (Student student : students) {
            table.addRow(
                student.getId(),
                student.getName(),
                student.getMark(),
                student.getRank()
            );
        }
        table.addRule();

        table.getRenderer().setCWC(new CWC_LongestLine());

        table.setTextAlignment(TextAlignment.LEFT);

        System.out.println(table.render());
    }

    public static void displayStudent(Student student) {
        if (student == null) {
            ColorUtil.printWarning("No student to display.");
            return;
        }

        AsciiTable table = new AsciiTable();

        table.addRule();
        table.addRow("ID", "Name", "Mark", "Rank");
        table.addRule();
        table.addRow(
            student.getId(),
            student.getName(),
            student.getMark(),
            student.getRank()
        );
        table.addRule();

        table.getRenderer().setCWC(new CWC_LongestLine());
        table.setTextAlignment(TextAlignment.LEFT);

        System.out.println(table.render());
    }

    public static void displayRankingTable() {
        AsciiTable table = new AsciiTable();

        table.addRule();
        table.addRow("Mark Range", "Rank");
        table.addRule();

        for (StudentRank rank : StudentRank.values()) {
            table.addRow(
                    rank.getMarkRange(),
                    rank
            );
        }
        table.addRule();

        table.getRenderer().setCWC(new CWC_LongestLine());
        table.setTextAlignment(TextAlignment.CENTER);

        System.out.println(table.render());
    }

    public static void displayStudentsByRank(List<Student> students) {
        if (students == null || students.isEmpty()) {
            ColorUtil.printWarning("No students to display.");
            return;
        }

        for (StudentRank rank : StudentRank.values()) {
            List<Student> rankStudents = students.stream()
                    .filter(s -> s.getRank() == rank)
                    .toList();

            if (!rankStudents.isEmpty()) {
                System.out.println(ColorUtil.colorizedRank(rank) +
                        " (" + rankStudents.size() + " students):");

                AsciiTable table = new AsciiTable();
                table.addRule();
                table.addRow("ID", "Name", "Mark");
                table.addRule();

                for (Student student : rankStudents) {
                    table.addRow(
                            student.getId(),
                            student.getName(),
                            student.getMark()
                    );
                }
                table.addRule();

                table.getRenderer().setCWC(new CWC_LongestLine());
                table.setTextAlignment(TextAlignment.LEFT);

                System.out.println(table.render());
            }
        }
    }

    public static void displayStatistics(StudentService studentService) {
        if (studentService == null || studentService.isEmpty()) {
            ColorUtil.printWarning("No students to display statistics.");
            return;
        }

        int totalStudents = studentService.size();
        double averageMark = studentService.calculateAverageMark();
        double highestMark = studentService.getHighestMark();
        double lowestMark = studentService.getLowestMark();

        int failCount = studentService.findStudentsByRank(StudentRank.FAIL).size();
        int mediumCount = studentService.findStudentsByRank(StudentRank.MEDIUM).size();
        int goodCount = studentService.findStudentsByRank(StudentRank.GOOD).size();
        int veryGoodCount = studentService.findStudentsByRank(StudentRank.VERY_GOOD).size();
        int excellentCount = studentService.findStudentsByRank(StudentRank.EXCELLENT).size();

        AsciiTable table = new AsciiTable();

        table.addRule();
        table.addRow("Statistic", "Value");
        table.addRule();
        table.addRow("Total Students", totalStudents);
        table.addRow("Average Mark", String.format("%.1f", averageMark));
        table.addRow("Highest Mark", String.format("%.1f", highestMark));
        table.addRow("Lowest Mark", String.format("%.1f", lowestMark));
        table.addRule();
        table.addRow(StudentRank.FAIL, failCount);
        table.addRow(StudentRank.MEDIUM, mediumCount);
        table.addRow(StudentRank.GOOD, goodCount);
        table.addRow(StudentRank.VERY_GOOD, veryGoodCount);
        table.addRow(StudentRank.EXCELLENT, excellentCount);
        table.addRule();

        table.getRenderer().setCWC(new CWC_LongestLine());

        System.out.println(table.render());
    }
}
