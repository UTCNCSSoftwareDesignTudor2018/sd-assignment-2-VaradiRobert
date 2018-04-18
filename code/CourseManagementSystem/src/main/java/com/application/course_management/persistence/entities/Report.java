package com.application.course_management.persistence.entities;

import java.sql.Date;
import java.util.List;

public class Report {
	private List<Enrollment> enrollments;
	private Teacher teacher;
	private Date date;
	public List<Enrollment> getEnrollments() {
		return enrollments;
	}
	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
