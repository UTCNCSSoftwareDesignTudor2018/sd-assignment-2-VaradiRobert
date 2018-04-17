package com.application.course_management.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	@RequestMapping(value = "/groups/list", method = RequestMethod.POST)
	public ModelAndView listGroups(@RequestBody int groupNumber) {
		List<Group> groups = teacherService.getAllGroups();
		Group group = teacherService.getGroupByNumber(groupNumber);
		ModelAndView mv = new ModelAndView("groups");
		mv.addObject("students", group.getStudents());
		mv.addObject("groups", groups);
		return mv;
	}
}