package org.abk.student.management.system.services;

import org.abk.student.management.system.adt.StudentList;
import org.abk.student.management.system.adt.StudentListImpl;
import org.abk.student.management.system.algorithms.sorting.SortStrategy;
import org.abk.student.management.system.models.Student;
import org.abk.student.management.system.models.StudentRank;
import org.abk.student.management.system.utils.ValidationUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for managing student operations.
 * <p>
 * This class provides business logic for the Student Management System,
 * coordinating between the UI layer and the data structure layer (ADT).
 * <p>
 * Responsibilities: <br/>
 * - Student CRUD operations (Create, Read, Update, Delete) <br/>
 * - Validation and business rules <br/>
 * - Sorting and searching coordination <br/>
 * - Statistics and reporting
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public record StudentService(StudentList studentList) {
    public StudentService() {
        this(new StudentListImpl());
    }

    public StudentService {
        if (studentList == null) {
            throw new IllegalArgumentException("StudentList cannot be null");
        }

    }

    public boolean addStudent(String id, String name, double mark) {
        if (studentList.exists(id)) {
            return false;
        }

        Student student = new Student(id, name, mark);
        return studentList.insert(student);
    }

    public Student findStudentById(String id) {
        ValidationUtil.validateId(id);
        return studentList.find(id);
    }

    public List<Student> findAllStudents() {
        return studentList.findAll();
    }

    public List<Student> findStudentsByRank(StudentRank rank) {
        if (rank == null) {
            throw new IllegalArgumentException("Rank cannot be null");
        }

        return studentList.findByRank(rank);
    }

    public List<Student> findStudentsByName(String nameQuery) {
        if (nameQuery == null || nameQuery.trim().isEmpty()) {
            throw new IllegalArgumentException("Name query cannot be null or empty");
        }

        String query = nameQuery.trim().toLowerCase();
        return studentList.findAll().stream()
                .filter(s -> s.getName().toLowerCase().contains(query))
                .collect(Collectors.toList());
    }

    public int size() {
        return studentList.size();
    }

    public boolean isEmpty() {
        return studentList.isEmpty();
    }

    public boolean studentExists(String id) {
        ValidationUtil.validateId(id);
        return studentList.exists(id);
    }

    public boolean updateStudent(String id, String newName, double newMark) {
        Student existingStudent = studentList.find(id);
        if (existingStudent == null) {
            return false;
        }

        Student updatedStudent = new Student(id, newName, newMark);
        return studentList.update(updatedStudent);
    }

    public boolean updateStudentMark(String id, double newMark) {
        ValidationUtil.validateId(id);
        ValidationUtil.validateMark(newMark);

        Student student = studentList.find(id);
        if (student == null) {
            return false;
        }

        Student updatedStudent = new Student(id, student.getName(), newMark);
        return studentList.update(updatedStudent);
    }

    public boolean updateStudentName(String id, String newName) {
        ValidationUtil.validateId(id);
        ValidationUtil.validateName(newName);

        Student student = studentList.find(id);
        if (student == null) {
            return false;
        }

        Student updatedStudent = new Student(id, newName, student.getMark());
        return studentList.update(updatedStudent);
    }

    public boolean deleteStudent(String id) {
        ValidationUtil.validateId(id);
        return studentList.remove(id);
    }

    public List<Student> sortStudents(SortStrategy sortStrategy, Comparator<Student> comparator) {
        if (sortStrategy == null) {
            throw new IllegalArgumentException("Sort strategy cannot be null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }

        List<Student> students = new ArrayList<>(studentList.findAll());
        sortStrategy.sort(students, comparator);
        return students;
    }

    private List<Student> sortStudents(SortStrategy sortStrategy, Comparator<Student> comparator, boolean ascending) {
        if (!ascending) {
            comparator = comparator.reversed();
        }

        return sortStudents(sortStrategy, comparator);
    }

    public List<Student> sortStudentsById(SortStrategy sortStrategy, boolean ascending) {
        return sortStudents(sortStrategy, Comparator.comparing(Student::getId), ascending);
    }

    public List<Student> sortStudentsByName(SortStrategy sortStrategy, boolean ascending) {
        return sortStudents(sortStrategy, Comparator.comparing(Student::getName), ascending);
    }

    public List<Student> sortStudentsByMark(SortStrategy sortStrategy , boolean ascending) {
        return sortStudents(sortStrategy, Comparator.comparing(Student::getMark), ascending);
    }

    public List<Student> sortStudentsByRank(SortStrategy sortStrategy , boolean ascending) {
        return sortStudents(sortStrategy, Comparator.comparing(Student::getRank), ascending);
    }

    public double calculateAverageMark() {
        List<Student> students = studentList.findAll();
        if (students.isEmpty()) {
            return 0.0;
        }

        return students.stream()
                .mapToDouble(Student::getMark)
                .average()
                .orElse(0.0);
    }

    public double getHighestMark() {
        return studentList.findAll().stream()
                .mapToDouble(Student::getMark)
                .max()
                .orElse(0.0);
    }

    public double getLowestMark() {
        return studentList.findAll().stream()
                .mapToDouble(Student::getMark)
                .min()
                .orElse(0.0);
    }

    public int countStudentsByRank(StudentRank rank) {
        return (int) studentList.findAll().stream()
                .filter(s -> s.getRank() == rank)
                .count();
    }
}
