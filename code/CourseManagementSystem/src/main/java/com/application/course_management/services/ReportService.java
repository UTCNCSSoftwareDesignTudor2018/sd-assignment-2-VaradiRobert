package com.application.course_management.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.course_management.persistence.entities.Group;
import com.application.course_management.persistence.entities.Report;
import com.application.course_management.persistence.entities.Teacher;
import com.application.course_management.services.reports.AllEnrolledStudentsAndGradesReportService;
import com.application.course_management.services.reports.AllStudentsByGroupsReportService;
import com.application.course_management.services.reports.FailingStudentsReportService;
import com.application.course_management.services.reports.PassingStudentsReportService;

@Service
public class ReportService {
	private AllEnrolledStudentsAndGradesReportService allStudentsAndGradesReportService = new AllEnrolledStudentsAndGradesReportService();
	private AllStudentsByGroupsReportService allStudentsByGroupsReportService = new AllStudentsByGroupsReportService();
	private FailingStudentsReportService failingStudentsReportService = new FailingStudentsReportService();
	private PassingStudentsReportService passingStudentsReportService = new PassingStudentsReportService();
	@Autowired
	private GroupService groupService;
	public static final String ALL_STUDENTS_AND_GRADES_REPORT = "all_enrolled_students_and_grades";
	public static final String ALL_STUDENTS_BY_GROUPS_REPORT = "all_students_by_groups";
	public static final String FAILING_STUDENTS_REPORT = "failing_students";
	public static final String PASSING_STUDENTS_REPORT = "passing_students";
	public void generateReport(String reportType, String reportName, Teacher teacher) {
		List<Group> groups = groupService.getAll();
		switch(reportType) {
			case ALL_STUDENTS_AND_GRADES_REPORT: allStudentsAndGradesReportService.generateReport(teacher, reportName); break;
			case ALL_STUDENTS_BY_GROUPS_REPORT: allStudentsByGroupsReportService.generateReport(teacher, groups, reportName); break;
			case FAILING_STUDENTS_REPORT: failingStudentsReportService.generateReport(teacher, reportName); break;
			case PASSING_STUDENTS_REPORT: passingStudentsReportService.generateReport(teacher, reportName); break;
		}
	}
	
	public List<Report> getReports(Teacher teacher, String reportType) {
		switch(reportType) {
			case ALL_STUDENTS_AND_GRADES_REPORT: return allStudentsAndGradesReportService.getReports(teacher);
			case ALL_STUDENTS_BY_GROUPS_REPORT: return allStudentsByGroupsReportService.getReports(teacher);
			case FAILING_STUDENTS_REPORT: return failingStudentsReportService.getReports(teacher);
			case PASSING_STUDENTS_REPORT: return passingStudentsReportService.getReports(teacher);
			default: return null;
		}
	}
}
