package com.application.course_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.course_management.persistence.entities.Enrollment;
import com.application.course_management.persistence.repositories.EnrollmentRepository;

@Service
public class EnrollmentService {
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	public static final String STATUS_REQUEST = "Request";
	public static final String STATUS_ACCEPTED = "Accepted";
	public static final String STATUS_DECLINED = "Declined";
	public static final String STATUS_DELETED = "Deleted";
	public static final String STATUS_UNENROLLED = "Unenrolled";
	public static final String STATUS_CANCELLED = "Cancelled";
	public List<Enrollment> getEnrollments(int studentId) {
		List<Enrollment> enrollments = enrollmentRepository.findAllByStudentId(studentId);
		return enrollments;
	}

	public void unenrollStudent(int identityCardNumber, int courseId) {
		Optional<Enrollment> optionalEnrollment = enrollmentRepository.findByStudentIdAndCourseId(identityCardNumber, courseId);
		Enrollment enrollment = optionalEnrollment.get();
		enrollment.setStatus(STATUS_UNENROLLED);
		enrollmentRepository.save(enrollment);
	}

	public void acceptEnrollment(int identityCardNumber, int courseId) {
		Optional<Enrollment> optionalEnrollment = enrollmentRepository.findByStudentIdAndCourseId(identityCardNumber, courseId);
		Enrollment enrollment = optionalEnrollment.get();
		enrollment.setStatus(STATUS_ACCEPTED);
		enrollmentRepository.save(enrollment);
	}

	public List<Enrollment> getEnrollmentsByCourseId(int courseId) {
		List<Enrollment> enrollments = enrollmentRepository.findAllByCourseId(courseId);
		return enrollments;
	}
	
	public Enrollment getEnrollmentByStudentIdAndCourseId(int studentId, int courseId) {
		Optional<Enrollment> enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId);
		return enrollment.get();
	}

	public void sendEnrollmentRequest(int identityCardNumber, int courseId) {
		Optional<Enrollment> optionalEnrollment = enrollmentRepository.findByStudentIdAndCourseId(identityCardNumber, courseId);
		Enrollment enrollment = optionalEnrollment.get();
		enrollment.setStatus(STATUS_REQUEST);
		enrollmentRepository.save(enrollment);
	}

	public void declineEnrollment(int identityCardNumber, int courseId) {
		Optional<Enrollment> optionalEnrollment = enrollmentRepository.findByStudentIdAndCourseId(identityCardNumber, courseId);
		Enrollment enrollment = optionalEnrollment.get();
		enrollment.setStatus(STATUS_DECLINED);
		enrollmentRepository.save(enrollment);
	}

	public void saveEnrollment(Enrollment enrollment) {
		enrollment.setGrade(-1);
		enrollmentRepository.save(enrollment);
	}

	public void cancelEnrollment(int identityCardNumber, int courseId) {
		Optional<Enrollment> optionalEnrollment = enrollmentRepository.findByStudentIdAndCourseId(identityCardNumber, courseId);
		Enrollment enrollment = optionalEnrollment.get();
		enrollment.setStatus(STATUS_CANCELLED);
		enrollmentRepository.save(enrollment);
	}

	public void removeStudent(int identityCardNumber, int courseId) {
		Optional<Enrollment> optionalEnrollment = enrollmentRepository.findByStudentIdAndCourseId(identityCardNumber, courseId);
		Enrollment enrollment = optionalEnrollment.get();
		enrollment.setStatus(STATUS_DELETED);
		enrollmentRepository.save(enrollment);
	}
}
