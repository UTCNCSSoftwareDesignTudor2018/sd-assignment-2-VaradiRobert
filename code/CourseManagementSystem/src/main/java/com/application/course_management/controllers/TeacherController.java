package com.application.course_management.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.application.course_management.persistence.entities.Course;
import com.application.course_management.persistence.entities.Group;
import com.application.course_management.persistence.entities.Report;
import com.application.course_management.persistence.entities.Teacher;
import com.application.course_management.services.ReportService;
import com.application.course_management.services.TeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
	@Autowired
	private TeacherService teacherService;
	private String loggedInUserName = "gmcginny5";
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
	public ModelAndView acceptEnrollment(HttpServletRequest request) {
		String studentId = request.getParameter("studentId");
		String courseId = request.getParameter("courseId");
		teacherService.acceptStudentEnrollmentRequest(Integer.parseInt(studentId), Integer.parseInt(courseId));
		return new ModelAndView("welcome-page");
	}
	@RequestMapping(value = "/courses/student/decline-enrollment", method = RequestMethod.POST)
	public RedirectView declineEnrollment(HttpServletRequest request) {
		String studentId = request.getParameter("studentId");
		String courseId = request.getParameter("courseId");
		teacherService.declineStudentEnrollmentRequest(Integer.parseInt(studentId), Integer.parseInt(courseId));
		return new RedirectView("welcome-page");
	}
	@RequestMapping(value = "/courses/student/delete-student", method = RequestMethod.POST)
	public RedirectView deleteStudent(HttpServletRequest request) {
		String studentId = request.getParameter("studentId");
		String courseId = request.getParameter("courseId");
		teacherService.removeStudentFromCourse(Integer.parseInt(studentId), Integer.parseInt(courseId));
		return new RedirectView("welcome-page");
	}
	@RequestMapping(value = "/courses/student/grade", method = RequestMethod.POST)
	@ResponseBody
	public String gradeStudent(HttpServletRequest request) {
		String studentId = request.getParameter("studentId");
		String courseId = request.getParameter("courseId");
		String grade = request.getParameter("grade");
		teacherService.gradeStudent(Integer.parseInt(studentId), Integer.parseInt(courseId), Double.parseDouble(grade));
		return "OK";
	}
	@RequestMapping(value = "/reports/generate/all-students", method = RequestMethod.POST)
	@ResponseBody
	public String generateAllEnrolledStudentsAndGradesReport(HttpServletRequest request) {
		String reportName = request.getParameter("reportName");
		Teacher t = teacherService.getTeacherByUserName(loggedInUserName);
		teacherService.generateReport(t, ReportService.ALL_STUDENTS_AND_GRADES_REPORT, reportName);
		return "OK";
	}
	@RequestMapping(value = "/reports/generate/passing-students", method = RequestMethod.POST)
	@ResponseBody
	public String generatePassingStudentsReport(HttpServletRequest request) {
		String reportName = request.getParameter("reportName");
		Teacher t = teacherService.getTeacherByUserName(loggedInUserName);
		teacherService.generateReport(t, ReportService.PASSING_STUDENTS_REPORT, reportName);
		return "OK";
	}
	@RequestMapping(value = "/reports/generate/failing-students", method = RequestMethod.POST)
	@ResponseBody
	public String generateFailingStudentsReport(HttpServletRequest request) {
		String reportName = request.getParameter("reportName");
		Teacher t = teacherService.getTeacherByUserName(loggedInUserName);
		teacherService.generateReport(t, ReportService.FAILING_STUDENTS_REPORT, reportName);
		return "OK";
	}
	@RequestMapping(value = "/reports/show/all-enrolled-students-and-grades-report", method = RequestMethod.GET)
	public ModelAndView showAllEnrolledStudentsAndGradesReport() {
		Teacher t = teacherService.getTeacherByUserName(loggedInUserName);
		List<Report> reports = teacherService.getReports(t, ReportService.ALL_STUDENTS_AND_GRADES_REPORT);
		ModelAndView mv = new ModelAndView("students-and-grades-reports");
		mv.addObject("reports", reports);
		mv.addObject("title", "All Enrolled Students and Grades Reports");
		return mv;
	}
	@RequestMapping(value = "/reports/show/passing-students-report", method = RequestMethod.GET)
	public ModelAndView showPassingStudentsReport() {
		Teacher t = teacherService.getTeacherByUserName(loggedInUserName);
		List<Report> reports = teacherService.getReports(t, ReportService.PASSING_STUDENTS_REPORT);
		ModelAndView mv = new ModelAndView("passing-students");
		mv.addObject("reports", reports);
		mv.addObject("title", "Passing Students Reports");
		return mv;
	}
	@RequestMapping(value = "/reports/show/failing-students-report", method = RequestMethod.GET)
	public ModelAndView showFailingStudentsReport() {
		Teacher t = teacherService.getTeacherByUserName(loggedInUserName);
		List<Report> reports = teacherService.getReports(t, ReportService.FAILING_STUDENTS_REPORT);
		ModelAndView mv = new ModelAndView("failing-students");
		mv.addObject("reports", reports);
		mv.addObject("title", "Failing Students Reports");
		return mv;
	}
}