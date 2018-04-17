package com.application.course_management.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.course_management.persistence.entities.Exam;

public interface ExamRepository extends JpaRepository<Exam, Integer>{
	public List<Exam> findAll();
	public Optional<Exam> findById(int examId);
	public Optional<Exam> findByCourseId(int courseId);
}
