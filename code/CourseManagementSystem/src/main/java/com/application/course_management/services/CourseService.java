package com.application.course_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.course_management.persistence.entities.Course;
import com.application.course_management.persistence.repositories.CourseRepository;

@Service
public class CourseService {
	@Autowired
	private CourseRepository courseRepository;
	public Course getCourse(int courseId) {
		Optional<Course> course =  courseRepository.findById(courseId);
		return course.get();
	}

	public Course getCourseByName(String courseName) {
		Optional<Course> course = courseRepository.findByName(courseName);
		return course.get();
	}

	public List<Course> getCoursesByTeacherId(int teacherId) {
		List<Course> courses = courseRepository.findAllByTeacherId(teacherId);
		return courses;
	}

	public List<Course> getAll() {
		return courseRepository.findAll();
	}
}
