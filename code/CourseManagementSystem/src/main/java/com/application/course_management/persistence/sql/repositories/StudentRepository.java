package com.application.course_management.persistence.sql.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.course_management.persistence.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	public List<Student> findAll();
	public Optional<Student> findByUserName(String userName);
	public Optional<Student> findById(int studentId);
	public List<Student> findAllByGroupId(int groupId);
}
