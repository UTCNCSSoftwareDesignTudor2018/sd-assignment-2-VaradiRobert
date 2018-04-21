package com.application.course_management.persistence.reports;

import java.util.List;

import com.application.course_management.persistence.entities.Course;
import com.application.course_management.persistence.entities.Report;

public class FailingStudentsReport extends Report {
	private List<Course> courses;
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public List<Course> getCourses() {
		return courses;
	}
}
