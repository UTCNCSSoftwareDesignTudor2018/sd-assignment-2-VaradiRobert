package com.application.course_management.persistence.reports;

import java.util.List;

import com.application.course_management.persistence.entities.Enrollment;
import com.application.course_management.persistence.entities.Report;

public class PassingStudentsReport extends Report {
	private List<Enrollment> enrollments;

	public List<Enrollment> getEnrollments() {
		return enrollments;
	}

	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}
}
