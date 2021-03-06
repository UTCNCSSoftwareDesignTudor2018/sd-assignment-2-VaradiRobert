package com.application.course_management.persistence.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "courses")
public class Course {
	@Id
	private int id;
	@Column(name = "name")
	@NotNull
	private String name;
	@Column(name = "credits")
	@NotNull
	private int credits;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id")
	private Teacher teacher;
	@OneToMany(mappedBy = "course")
	private List<Enrollment> enrollments;
	@Column(name = "exam_date")
	private Date examDate;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}
	public List<Enrollment> getEnrollments() {
		return enrollments;
	}
	public Date getExamDate() {
		return examDate;
	}
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}
	@Override
	public boolean equals(Object object) {
		if(object == null) {
			return false;
		}
		if(this == object) {
			return true;
		}
		Course course = (Course)object;
		return course.getName().equals(name) && course.getCredits() == credits;
	}
}
