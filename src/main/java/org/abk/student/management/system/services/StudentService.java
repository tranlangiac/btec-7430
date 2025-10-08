package org.abk.student.management.system.services;

import org.abk.student.management.system.repository.StudentRepository;
import org.abk.student.management.system.repository.InMemoryStudentRepository;
import org.abk.student.management.system.algorithms.sorting.SortStrategy;
import org.abk.student.management.system.model.Student;
import org.abk.student.management.system.model.StudentRank;
import org.abk.student.management.system.shared.util.ValidationUtil;

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
public record StudentService(StudentRepository studentRepository) {
    public StudentService() {
        this(new InMemoryStudentRepository());
    }

    public StudentService {
        if (studentRepository == null) {
            throw new IllegalArgumentException("StudentRepository cannot be null");
        }
    }

    public boolean addStudent(String id, String name, double mark) {
        if (studentRepository.exists(id)) {
            return false;
        }

        Student student = new Student(id, name, mark);
        return studentRepository.insert(student);
    }

    public Student findStudentById(String id) {
        ValidationUtil.validateId(id);
        return studentRepository.find(id);
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> findStudentsByRank(StudentRank rank) {
        if (rank == null) {
            throw new IllegalArgumentException("Rank cannot be null");
        }

        return studentRepository.findByRank(rank);
    }

    public List<Student> findStudentsByName(String nameQuery) {
        if (nameQuery == null || nameQuery.trim().isEmpty()) {
            throw new IllegalArgumentException("Name query cannot be null or empty");
        }

        String query = nameQuery.trim().toLowerCase();
        return studentRepository.findAll().stream()
                .filter(s -> s.getName().toLowerCase().contains(query))
                .collect(Collectors.toList());
    }

    public int size() {
        return studentRepository.size();
    }

    public boolean isEmpty() {
        return studentRepository.isEmpty();
    }

    public boolean studentExists(String id) {
        ValidationUtil.validateId(id);
        return studentRepository.exists(id);
    }

    public boolean updateStudent(String id, String newName, double newMark) {
        Student existingStudent = studentRepository.find(id);
        if (existingStudent == null) {
            return false;
        }

        Student updatedStudent = new Student(id, newName, newMark);
        return studentRepository.update(updatedStudent);
    }

    public boolean updateStudentMark(String id, double newMark) {
        ValidationUtil.validateId(id);
        ValidationUtil.validateMark(newMark);

        Student student = studentRepository.find(id);
        if (student == null) {
            return false;
        }

        Student updatedStudent = new Student(id, student.getName(), newMark);
        return studentRepository.update(updatedStudent);
    }

    public boolean updateStudentName(String id, String newName) {
        ValidationUtil.validateId(id);
        ValidationUtil.validateName(newName);

        Student student = studentRepository.find(id);
        if (student == null) {
            return false;
        }

        Student updatedStudent = new Student(id, newName, student.getMark());
        return studentRepository.update(updatedStudent);
    }

    public boolean deleteStudent(String id) {
        ValidationUtil.validateId(id);
        return studentRepository.remove(id);
    }

    public List<Student> sortStudents(SortStrategy sortStrategy, Comparator<Student> comparator) {
        if (sortStrategy == null) {
            throw new IllegalArgumentException("Sort strategy cannot be null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }

        List<Student> students = new ArrayList<>(studentRepository.findAll());
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
        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) {
            return 0.0;
        }

        return students.stream()
                .mapToDouble(Student::getMark)
                .average()
                .orElse(0.0);
    }

    public double getHighestMark() {
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getMark)
                .max()
                .orElse(0.0);
    }

    public double getLowestMark() {
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getMark)
                .min()
                .orElse(0.0);
    }

    public int countStudentsByRank(StudentRank rank) {
        return (int) studentRepository.findAll().stream()
                .filter(s -> s.getRank() == rank)
                .count();
    }
}
