package com.application.course_management.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.course_management.persistence.entities.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
	public List<Enrollment> findAll();
	public List<Enrollment> findAllByCourseId(int courseId);
	public List<Enrollment> findAllByStudentId(int studentId);
	public Optional<Enrollment> findByStudentIdAndCourseId(int studentId, int courseId);
}
