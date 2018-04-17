package com.application.course_management.persistence.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "teachers")
public class Teacher extends User {
	@OneToMany(mappedBy = "teacher")
	private List<Course> courses;
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public List<Course> getCourses() {
		return courses;
	}
}
