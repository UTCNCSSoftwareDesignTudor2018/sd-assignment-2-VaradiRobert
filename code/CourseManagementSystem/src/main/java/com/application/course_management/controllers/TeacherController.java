package com.application.course_management.controllers;

import java.util.List;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.application.course_management.persistence.entities.Course;
import com.application.course_management.persistence.entities.Group;
import com.application.course_management.persistence.entities.Teacher;
import com.application.course_management.services.TeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
	@Autowired
	private TeacherService teacherService;
	private String loggedInUserName = "shawthorn0";
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView teacherMainMenu() {
		ModelAndView mv = new ModelAndView("teacher-main-menu");
		Teacher teacher = teacherService.getTeacherByUserName(loggedInUserName);
		mv.addObject("fullName", teacher.getFirstName() + " " + teacher.getLastName());
		return mv;
	}
	@RequestMapping(value = "/welcome-page", method = RequestMethod.GET)
	public ModelAndView teacherWelcomePage() {
		ModelAndView mv = new ModelAndView("welcome-page");
		Teacher t = teacherService.getTeacherByUserName(loggedInUserName);
		mv.addObject("fullName", t.getFirstName() + " " + t.getLastName());
		return mv;
	}
	@RequestMapping(value = "/teacher-sidebar", method = RequestMethod.GET)
	public ModelAndView teacherSidebar() {
		ModelAndView mv = new ModelAndView("teacher-sidebar");
		Teacher teacher = teacherService.getTeacherByUserName(loggedInUserName);
		mv.addObject("fullName", teacher.getFirstName() + " " + teacher.getLastName());
		return mv;
	}
	@RequestMapping(value = "/groups/list", method = RequestMethod.GET)
	public ModelAndView listGroups() {
		List<Group> groups = teacherService.getAllGroups();
		ModelAndView mv = new ModelAndView("groups");
		mv.addObject("groups", groups);
		return mv;
	}
	@RequestMapping(value = "/courses/list", method = RequestMethod.GET)
	public ModelAndView listCourses() {
		List<Course> courses = teacherService.getTaughtCourses(loggedInUserName);
		ModelAndView mv = new ModelAndView("enrollments");
		mv.addObject("courses", courses);
		return mv;
	}
	@RequestMapping(value = "/courses/student/accept-enrollment", method = RequestMethod.POST)
	public ModelAndView acceptEnrollment(@RequestBody Request request) {
		teacherService.acceptStudentEnrollmentRequest(Integer.parseInt(request.getAttribute("studentId").toString()), Integer.parseInt(request.getAttribute("courseId").toString()));
		List<Course> courses = teacherService.getTaughtCourses(loggedInUserName);
		ModelAndView mv = new ModelAndView("enrollments");
		mv.addObject("courses", courses);
		return mv;
	}
	@RequestMapping(value = "/courses/student/decline-enrollment", method = RequestMethod.POST)
	public ModelAndView declineEnrollment(@RequestBody Request request) {
		teacherService.declineStudentEnrollmentRequest(Integer.parseInt(request.getAttribute("studentId").toString()), Integer.parseInt(request.getAttribute("courseId").toString()));
		List<Course> courses = teacherService.getTaughtCourses(loggedInUserName);
		ModelAndView mv = new ModelAndView("enrollments");
		mv.addObject("courses", courses);
		return mv;
	}
	@RequestMapping(value = "/courses/student/delete-student", method = RequestMethod.POST)
	public ModelAndView deleteStudent(@RequestBody Request request) {
		teacherService.removeStudentFromCourse(Integer.parseInt(request.getAttribute("studentId").toString()), Integer.parseInt(request.getAttribute("courseId").toString()));
		List<Course> courses = teacherService.getTaughtCourses(loggedInUserName);
		ModelAndView mv = new ModelAndView("enrollments");
		mv.addObject("courses", courses);
		return mv;
	}
}