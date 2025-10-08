package org.abk.student.management.system.repository;

import org.abk.student.management.system.model.Student;
import org.abk.student.management.system.model.StudentRank;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the StudentRepository ADT using ArrayList.
 * <p>
 * This implementation uses an ArrayList as the underlying data structure
 * to store Student objects. It maintains the invariant that all student IDs
 * are unique within the collection.
 * <p>
 * Time Complexity: <br/>
 * - insert(): O(n) - due to uniqueness check <br/>
 * - remove(): O(n) - linear search + removal <br/>
 * - update(): O(n) - linear search + update <br/>
 * - find(): O(n) - linear search <br/>
 * - getAll(): O(n) - creates defensive copy <br/>
 * - size(): O(1) <br/>
 * - isEmpty(): O(1) <br/>
 * - clear(): O(1) <br/>
 * - exists(): O(n) - linear search <br/>
 * - findByRank(): O(n) - filters all elements
 * <p>
 * Space Complexity: O(n) where n is the number of students
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public class InMemoryStudentRepository implements StudentRepository {
    private final List<Student> students;

    public InMemoryStudentRepository() {
        this.students = new ArrayList<>();
    }

    @Override
    public boolean insert(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        if (exists(student.getId())) {
            return false;
        }

        students.add(student);
        return true;
    }

    @Override
    public boolean remove(String studentId) {
        validateStudentId(studentId);

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(studentId)) {
                students.remove(i);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean update(Student student) {
        if (student == null) throw new IllegalArgumentException("Student cannot be null");

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(student.getId())) {
                students.set(i, student);
                return true;
            }
        }

        return false;
    }

    @Override
    public Student find(String studentId) {
        validateStudentId(studentId);

        for (Student student : students) {
            if (student.getId().equals(studentId)) {
                return student;
            }
        }

        return null;
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students);
    }

    @Override
    public int size() {
        return students.size();
    }

    @Override
    public boolean isEmpty() {
        return students.isEmpty();
    }

    @Override
    public boolean exists(String studentId) {
        validateStudentId(studentId);

        return find(studentId) != null;
    }

    @Override
    public List<Student> findByRank(StudentRank rank) {
        if (rank == null) {
            throw new IllegalArgumentException("Rank cannot be null");
        }

        return students.stream()
                .filter(student -> student.getRank() == rank)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        InMemoryStudentRepository that = (InMemoryStudentRepository) obj;
        return students.equals(that.students);
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }

    // DEBUGGING ONLY
    @Override
    public String toString() {
        if (isEmpty()) {
            return "StudentRepository[empty]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("StudentRepository[size=").append(size()).append("]:\n");

        for (Student student : students) {
            sb.append(" ").append(student.toString()).append("\n");
        }

        return sb.toString();
    }

    private void validateStudentId(String studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }

        if (studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be empty");
        }
    }
}
