package com.application.course_management.persistence.reports.factory;

import com.application.course_management.persistence.entities.Report;

public class ReportFactory {
	public static final String ALL_ENROLLED_STUDENTS_AND_GRADES_REPORT = "all_enrolled_students_and_grades_report";
	public static final String ALL_STUDENTS_BY_GROUPS_REPORT = "all_students_by_groups_report";
	public static final String FAILING_STUDENTS_REPORT = "failing_students_report";
	public static final String PASSING_STUDENTS_REPORT = "passing_students_report";
	
	public Report createReport(String reportType, String reportName, int teacherId) {
		return null;
	}
}
