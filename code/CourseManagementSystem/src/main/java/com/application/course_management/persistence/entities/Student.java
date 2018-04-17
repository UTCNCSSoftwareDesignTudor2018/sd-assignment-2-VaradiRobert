package com.application.course_management.persistence.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "students")
public class Student extends User {
	@Column(name = "identity_card_number")
	@NotNull
	private int identityCardNumber;
	@Column(name = "personal_numerical_code")
	@NotNull
	private String personalNumericalCode;
	@Column(name = "address")
	@NotNull
	private String address;
	@ManyToOne
	@JoinColumn(name = "group_id", nullable = false)
	private Group group;
	@OneToMany(mappedBy = "student")
	private List<Enrollment> enrollments;
	public int getIdentityCardNumber() {
		return identityCardNumber;
	}
	public void setIdentityCardNumber(int l) {
		this.identityCardNumber = l;
	}
	public String getPersonalNumericalCode() {
		return personalNumericalCode;
	}
	public void setPersonalNumericalCode(String personalNumericalCode) {
		this.personalNumericalCode = personalNumericalCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Enrollment> getEnrollments() {
		return enrollments;
	}
	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public Group getGroup() {
		return group;
	}
}
