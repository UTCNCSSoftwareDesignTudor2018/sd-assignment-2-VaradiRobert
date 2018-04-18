package com.application.course_management.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.application.course_management.persistence.entities.Enrollment;
import com.application.course_management.persistence.entities.Student;
import com.application.course_management.services.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired
	private StudentService studentService;
	private String loggedInUserName = "ujarrete";
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView studentMainMenu() {
		ModelAndView mv = new ModelAndView("student-main-menu");
		return mv;
	}
	@RequestMapping(value = "/welcome-page", method = RequestMethod.GET)
	public ModelAndView teacherWelcomePage() {
		ModelAndView mv = new ModelAndView("welcome-page");
		Student student = studentService.getStudentByUserName(loggedInUserName);
		mv.addObject("fullName", student.getFirstName() + " " + student.getLastName());
		return mv;
	}
	@RequestMapping(value = "/student-sidebar", method = RequestMethod.GET)
	public ModelAndView teacherSidebar() {
		ModelAndView mv = new ModelAndView("student-sidebar");
		Student student = studentService.getStudentByUserName(loggedInUserName);
		mv.addObject("fullName", student.getFirstName() + " " + student.getLastName());
		return mv;
	}
	@RequestMapping(value = "/group", method = RequestMethod.GET)
	public ModelAndView viewGroup() {
		Student student = studentService.getStudentByUserName(loggedInUserName);
		ModelAndView mv = new ModelAndView("group");
		mv.addObject("group", student.getGroup());
		return mv;
	}
	@RequestMapping(value = "/courses/list", method = RequestMethod.GET)
	public ModelAndView listCourses() {
		List<Enrollment> enrollments = studentService.getEnrollments(loggedInUserName);
		ModelAndView mv = new ModelAndView("student-courses");
		mv.addObject("enrollments", enrollments);
		return mv;
	}
	@RequestMapping(value = "enrollments/cancel-enrollment-request", method = RequestMethod.POST)
	public ModelAndView cancelEnrollmentRequest(HttpServletRequest request) {
		String studentId = request.getParameter("studentId");
		String courseId = request.getParameter("courseId");
		studentService.cancelEnrollmentRequest(Integer.parseInt(studentId), Integer.parseInt(courseId));
		return new ModelAndView("basic");
	}
	@RequestMapping(value = "enrollments/send-enrollment-request", method = RequestMethod.POST)
	public ModelAndView sendEnrollmentRequest(HttpServletRequest request) {
		String studentId = request.getParameter("studentId");
		String courseId = request.getParameter("courseId");
		studentService.sendEnrollmentRequest(Integer.parseInt(studentId), Integer.parseInt(courseId));
		return new ModelAndView("basic");
	}
	@RequestMapping(value = "enrollments/unenrol-from-course", method = RequestMethod.POST)
	public ModelAndView unenrolFromCourse(HttpServletRequest request) {
		String studentId = request.getParameter("studentId");
		String courseId = request.getParameter("courseId");
		studentService.unenrollFromCourse(Integer.parseInt(studentId), Integer.parseInt(courseId));
		return new ModelAndView("basic");
	}
	@RequestMapping(value = "exams/list", method = RequestMethod.GET)
	public ModelAndView listExams() {
		ModelAndView mv = new ModelAndView("exams");
		List<Enrollment> enrollments = studentService.getAcceptedEnrollments(loggedInUserName);
		mv.addObject("enrollments", enrollments);
		return mv;
	}
	@RequestMapping(value = "profile/show", method = RequestMethod.GET)
	public ModelAndView showProfile() {
		Student student = studentService.getProfile(loggedInUserName);
		ModelAndView mv = new ModelAndView("student-profile");
		mv.addObject("student", student);
		return mv;
	}
	@RequestMapping(value = "profile/modify", method = RequestMethod.POST)
	public ModelAndView modifyProfile(HttpServletRequest request) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String password = request.getParameter("password");
		String passwordAgain = request.getParameter("passwordAgain");
		String personalNumericalCode = request.getParameter("personalNumericalCode");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		studentService.updateProfile(loggedInUserName, password, passwordAgain, email, firstName, lastName, phone, address, personalNumericalCode);
		return new ModelAndView("welcome-page");
	}
}
