package org.abk.student.management.system.repository;

import org.abk.student.management.system.model.Student;
import org.abk.student.management.system.model.StudentRank;

import java.util.List;

/**
 * StudentRepository Abstract Data Type (ADT) Interface
 * <p>
 * This interface defines the formal specification for a collection of Student objects. <br/>
 * It provides operations for managing students in the system.
 * <p>
 * Formal Specification: <br/>
 * - Domain: Collection of Student objects with unique IDs <br/>
 * - Operations: insert, remove, update, find, getAll, size, isEmpty, clear
 * <p>
 * Invariants: <br/>
 * - No two students can have the same ID <br/>
 * - Size must always be >= 0 <br/>
 * - All students in the collection must be non-null
 *
 * @author Soft Development ABK
 * @version 1.0
 */
public interface StudentRepository {
    boolean insert(Student student);
    boolean remove(String studentId);
    boolean update(Student student);
    Student find(String studentId);
    List<Student> findAll();
    int size();
    boolean isEmpty();
    boolean exists(String studentId);
    List<Student> findByRank(StudentRank rank);
}
