package com.application.course_management.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.course_management.persistence.entities.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
	public List<Teacher> findAll();
	public Optional<Teacher> findById(int teacherId);
	public Optional<Teacher> findByUserName(String userName);
}
