package com.application.course_management.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.course_management.persistence.entities.Exam;
import com.application.course_management.persistence.repositories.ExamRepository;

@Service
public class ExamService {
	@Autowired
	private ExamRepository examRepository;
	public Exam getExam(int examId) {
		Optional<Exam> exam = examRepository.findById(examId);
		return exam.get();
	}

	public Exam getExamByCourseId(int courseId) {
		Optional<Exam> exam = examRepository.findByCourseId(courseId);
		return exam.get();
	}
}
