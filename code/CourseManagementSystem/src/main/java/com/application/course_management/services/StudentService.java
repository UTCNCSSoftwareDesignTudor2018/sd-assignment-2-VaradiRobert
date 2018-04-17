package com.application.course_management.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.course_management.persistence.entities.Course;
import com.application.course_management.persistence.entities.Enrollment;
import com.application.course_management.persistence.entities.Exam;
import com.application.course_management.persistence.entities.Group;
import com.application.course_management.persistence.entities.Student;
import com.application.course_management.persistence.repositories.StudentRepository;

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
	@Autowired
	private ExamService examService;
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

	public void sendEnrollmentRequest(String userName, String courseName) {
		Optional<Student> optionalStudent = studentRepository.findByUserName(userName);
		Student student = optionalStudent.get();
		Course course = courseService.getCourseByName(courseName);
		enrollmentService.sendEnrollmentRequest(student.getIdentityCardNumber(), course.getId());
	}

	public void unenrollFromCourse(String userName, String courseName) {
		Optional<Student> optionalStudent = studentRepository.findByUserName(userName);
		Student student = optionalStudent.get();
		Course course = courseService.getCourseByName(courseName);
		enrollmentService.unenrollStudent(student.getIdentityCardNumber(), course.getId());
	}

	public Group getGroup(String userName) {
		Optional<Student> optionalStudent = studentRepository.findByUserName(userName);
		Student student = optionalStudent.get();
		Group group = student.getGroup();
		group.setStudents(studentRepository.findAllByGroupId(group.getId()));
		return group;
	}

	public List<Exam> getExams(String userName) {
		List<Enrollment> enrollments = getEnrollments(userName);
		List<Exam> exams = new ArrayList<Exam>();
		for(Enrollment enrollment : enrollments) {
			exams.add(examService.getExamByCourseId(enrollment.getCourse().getId()));
		}
		return exams;
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
		return enrollments;
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

	public void cancelEnrollmentRequest(String loggedInUserName, String courseName) {
		Optional<Student> optionalStudent = studentRepository.findByUserName(loggedInUserName);
		Student student = optionalStudent.get();
		Course course = courseService.getCourseByName(courseName);
		enrollmentService.cancelEnrollment(student.getIdentityCardNumber(), course.getId());
	}
}
