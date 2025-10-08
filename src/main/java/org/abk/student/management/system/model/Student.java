package org.abk.student.management.system.model;

/**
 * Represents a student in the Student Management System.
 * <p>
 * This class encapsulates student information including ID, name, marks,
 * and calculated rank based on performance.
 * <p>
 * Invariants: <br/>
 * - id must not be null or empty <br/>
 * - name must not be null or empty <br/>
 * - mark must be in range [0.0, 10.0] <br/>
 * - rank is automatically calculated based on mark
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public class Student {
    private String id;
    private String name;
    private double mark;
    private StudentRank rank;

    public Student(String id, String name, double mark) {
        setId(id);
        setName(name);
        setMark(mark);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }

        this.id = id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be null or empty");
        }

        this.name = name.trim();
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        if (mark < 0.0 || mark > 10.0) {
            throw new IllegalArgumentException("Mark must be between 0.0 and 10.0");
        }

        this.mark = mark;
        this.rank = StudentRank.fromMark(mark);
    }

    public StudentRank getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Student student = (Student) obj;
        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    // DEBUGGING ONLY
    @Override
    public String toString() {
        return String.format("Student[id=%s, name=%s, mark=%.2f, rank=%s]", id, name, mark, rank);
    }
}
