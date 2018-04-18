package com.application.course_management.services.reports;

import java.net.UnknownHostException;
import java.util.List;

import com.application.course_management.persistence.entities.Report;
import com.application.course_management.persistence.entities.Teacher;
import com.application.course_management.persistence.nosql.dao.PassingStudentsReportDAO;

public class PassingStudentsReportService {
	private PassingStudentsReportDAO dao;
	public PassingStudentsReportService() {
		dao = new PassingStudentsReportDAO();
	}
	public List<Report> getReports(Teacher teacher) {
		try {
			return dao.getReports(teacher);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public void generateReport(Teacher teacher, String reportName) {
		try {
			dao.storeReport(teacher, reportName);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
