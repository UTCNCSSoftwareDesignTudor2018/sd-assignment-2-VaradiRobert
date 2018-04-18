package com.application.course_management.persistence.nosql.dao;

public class ReportDAO {
	private static final String DRIVER = "mongodb";
	private static final String HOST = "localhost";
	private static final int PORT = 27017;
	private static final String DB_NAME = "course_management_reports";
	public String getURI() {
		return DRIVER + "://" + HOST + ":" + PORT;
	}
	public String getDBName() {
		return DB_NAME;
	}
}
