package com.application.course_management.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.course_management.persistence.entities.Course;
import com.application.course_management.persistence.entities.Enrollment;
import com.application.course_management.persistence.entities.Group;
import com.application.course_management.persistence.entities.Student;
import com.application.course_management.persistence.sql.repositories.StudentRepository;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private GroupService groupService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private EnrollmentService enrollmentService;
	public void createProfile(String userName, String password, String passwordAgain, String email, String firstName,
			String lastName, String phone, String address, String personalNumericalCode) {
		Student student = new Student();
		student.setUserName(userName);
		if(password.equals(passwordAgain)) {
			student.setPassword(password);
		}
		student.setEmail(email);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setPhone(phone);
		student.setAddress(address);
		student.setPersonalNumericalCode(personalNumericalCode);
		student.setIdentityCardNumber((int)studentRepository.count() + 1);
		List<Group> groups = groupService.getAll();
		Group groupToEnroll = groups.get(0);
		int minimumStudentCount = groups.get(0).getStudents().size();
		int numberOfGroups = groups.size();
		for(int i = 1; i < numberOfGroups; i++) {
			int studentCount = groups.get(i).getStudents().size();
			if(studentCount < minimumStudentCount) {
				minimumStudentCount = studentCount;
				groupToEnroll = groups.get(i);
			}
		}
		student.setGroup(groupToEnroll);
		studentRepository.save(student);	
		List<Course> courses = courseService.getAll();
		for(Course c : courses) {
			Enrollment enrollment = new Enrollment();
			enrollment.setStudent(student);
			enrollment.setCourse(c);
			enrollment.setStatus(EnrollmentService.STATUS_UNENROLLED);
			enrollmentService.saveEnrollment(enrollment);
		}
	}

	public Student getProfile(String userName) {
		Optional<Student> student = studentRepository.findByUserName(userName);
		return student.get();
	}

	public void updateProfile(String userName, String password, String passwordAgain, String email, String firstName,
			String lastName, String phone, String address, String personalNumericalCode) {
		Optional<Student> optionalStudent = studentRepository.findByUserName(userName);
		Student student = optionalStudent.get();
		if(password.equals(passwordAgain) && !password.equals("")) {
			student.setPassword(password);
		}
		student.setEmail(email);
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setPhone(phone);
		student.setAddress(address);
		studentRepository.save(student);
	}

	public void sendEnrollmentRequest(int studentId, int courseId) {
		Optional<Student> optionalStudent = studentRepository.findById(studentId);
		Student student = optionalStudent.get();
		Course course = courseService.getCourseById(courseId);
		enrollmentService.sendEnrollmentRequest(student.getIdentityCardNumber(), course.getId());
	}

	public void unenrollFromCourse(int studentId, int courseId) {
		Optional<Student> optionalStudent = studentRepository.findById(studentId);
		Student student = optionalStudent.get();
		Course course = courseService.getCourseById(courseId);
		enrollmentService.unenrollStudent(student.getIdentityCardNumber(), course.getId());
	}

	public Group getGroup(String userName) {
		Optional<Student> optionalStudent = studentRepository.findByUserName(userName);
		Student student = optionalStudent.get();
		Group group = student.getGroup();
		group.setStudents(studentRepository.findAllByGroupId(group.getId()));
		return group;
	}

	public List<Course> getCourses(String userName) {
		List<Enrollment> enrollments = getEnrollments(userName);
		List<Course> courses = new ArrayList<Course>();
		for(Enrollment enrollment : enrollments) {
			courses.add(enrollment.getCourse());
		}
		return courses;
	}

	public List<Enrollment> getEnrollments(String userName) {
		Optional<Student> optionalStudent = studentRepository.findByUserName(userName);
		Student student = optionalStudent.get();
		List<Enrollment> enrollments = enrollmentService.getEnrollments(student.getIdentityCardNumber());
		List<Course> courses = courseService.getAll();
		if(courses.size() > enrollments.size()) {
			int nextId = enrollmentService.getNextEnrollmentId();
			for(Course c : courses) {
				boolean found = false;
				for(Enrollment e : enrollments) {
					if(e.getCourse().equals(c)) {
						found = true;
					}
				}
				if(!found) {
					Enrollment newEnrollment = new Enrollment();
					newEnrollment.setCourse(c);
					newEnrollment.setStudent(student);
					newEnrollment.setStatus(EnrollmentService.STATUS_UNENROLLED);
					newEnrollment.setGrade(-1);
					newEnrollment.setId(nextId++);
					enrollmentService.saveEnrollment(newEnrollment);
				}
			}
			enrollments = enrollmentService.getEnrollments(student.getIdentityCardNumber());
		}
		return enrollments;
	}

	public List<Enrollment> getAcceptedEnrollments(String userName) {
		Optional<Student> optionalStudent = studentRepository.findByUserName(userName);
		Student student = optionalStudent.get();
		return enrollmentService.getAcceptedEnrollments(student.getId());
	}
	
	public Student getStudentByUserName(String studentName) {
		Optional<Student> student = studentRepository.findByUserName(studentName);
		return student.get();
	}

	public List<Student> getStudentsByGroupId(int groupId) {
		return studentRepository.findAllByGroupId(groupId);
	}

	public Student getStudentById(int studentId) {
		Optional<Student> student = studentRepository.findById(studentId);
		return student.get();
	}

	public List<Student> getByEnrollments(List<Enrollment> enrollments) {
		List<Student> students = new ArrayList<Student>();
		for(Enrollment e : enrollments) {
			students.add(e.getStudent());
		}
		return students;
	}

	public void cancelEnrollmentRequest(int studentId, int courseId) {
		Optional<Student> optionalStudent = studentRepository.findById(studentId);
		Student student = optionalStudent.get();
		Course course = courseService.getCourseById(courseId);
		enrollmentService.cancelEnrollment(student.getIdentityCardNumber(), course.getId());
	}
}
