package com.application.course_management.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.course_management.persistence.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	public List<Course> findAll();
	public Optional<Course> findById(int courseId);
	public Optional<Course> findByName(String name);
	public List<Course> findAllByTeacherId(int teacherId);
}
