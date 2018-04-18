package com.application.course_management.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.course_management.persistence.entities.Course;
import com.application.course_management.persistence.entities.Enrollment;
import com.application.course_management.persistence.entities.Group;
import com.application.course_management.persistence.entities.Report;
import com.application.course_management.persistence.entities.Student;
import com.application.course_management.persistence.entities.Teacher;
import com.application.course_management.persistence.sql.repositories.TeacherRepository;

@Service
public class TeacherService {
	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private EnrollmentService enrollmentService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private ReportService reportService;
	public void removeStudentFromCourse(int studentId, int courseId) {
		Course course = courseService.getCourseById(courseId);
		Student student = studentService.getStudentById(studentId);
		enrollmentService.removeStudent(student.getIdentityCardNumber(), course.getId());
	}
	
	public void acceptStudentEnrollmentRequest(int studentId, int courseId) {
		Course course = courseService.getCourseById(courseId);
		Student student = studentService.getStudentById(studentId);
		enrollmentService.acceptEnrollment(student.getIdentityCardNumber(), course.getId());
	}

	public void declineStudentEnrollmentRequest(int studentId, int courseId) {
		Course course = courseService.getCourseById(courseId);
		Student student = studentService.getStudentById(studentId);
		enrollmentService.declineEnrollment(student.getIdentityCardNumber(), course.getId());
	}
	
	public void gradeStudent(String studentName, String courseName, double grade) {
		Course course = courseService.getCourseByName(courseName);
		Student student = studentService.getStudentByUserName(studentName);
		Enrollment enrollment = enrollmentService.getEnrollmentByStudentIdAndCourseId(student.getIdentityCardNumber(), course.getId());
		enrollment.setGrade(grade);
		enrollmentService.saveEnrollment(enrollment);
	}

	public List<Student> getStudentsEnrolledTo(String courseName) {
		Course course = courseService.getCourseByName(courseName);
		List<Enrollment> enrollments = course.getEnrollments();
		List<Student> students = new ArrayList<Student>();
		for(Enrollment enrollment : enrollments) {
			students.add(enrollment.getStudent());
		}
		return students;
	}

	public List<Course> getTaughtCourses(String userName) {
		Teacher teacher = getTeacherByUserName(userName);
		return courseService.getCoursesByTeacherId(teacher.getId());
	}
	
	public Teacher getTeacherById(int teacherId) {
		Optional<Teacher> teacher = teacherRepository.findById(teacherId);
		return teacher.get();
	}
	
	public Teacher getTeacherByUserName(String teacherName) {
		Optional<Teacher> teacher = teacherRepository.findByUserName(teacherName);
		return teacher.get();
	}

	public List<Group> getAllGroups() {
		List<Group> groups = groupService.getAll();
		return groups;
	}
	
	public List<Enrollment> getTaughtCoursesEnrollments(String loggedInUserName) {
		Optional<Teacher> optionalTeacher = teacherRepository.findByUserName(loggedInUserName);
		Teacher teacher = optionalTeacher.get();
		List<Enrollment> allEnrollments = new ArrayList<Enrollment>();
		List<Course> courses = teacher.getCourses();
		for(Course c : courses) {
			List<Enrollment> enrollments = c.getEnrollments();
			for(Enrollment e : enrollments) {
				allEnrollments.add(e);
			}
		}
		return allEnrollments;
	}
	
	public Group getGroupByNumber(int groupNumber) {
		return groupService.getByGroupNumber(groupNumber);
	}
	
	public void generateReport(Teacher teacher, String reportType, String reportName) {
		reportService.generateReport(reportType, reportName, teacher);
	}
	
	public List<Report> getReports(Teacher teacher, String reportType) {
		return reportService.getReports(teacher, reportType);
	}
}
