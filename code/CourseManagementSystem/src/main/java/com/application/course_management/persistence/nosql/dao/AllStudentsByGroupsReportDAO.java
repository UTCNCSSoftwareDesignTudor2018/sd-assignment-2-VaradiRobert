package com.application.course_management.persistence.nosql.dao;

import java.net.UnknownHostException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.application.course_management.persistence.entities.Group;
import com.application.course_management.persistence.entities.Report;
import com.application.course_management.persistence.entities.Student;
import com.application.course_management.persistence.entities.Teacher;
import com.application.course_management.persistence.reports.AllStudentsByGroupsReport;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class AllStudentsByGroupsReportDAO extends ReportDAO {
	private static final String COLLECTION_NAME = "all_students_by_groups_reports";
	public void storeReport(Teacher teacher, List<Group> groups, String reportName) throws UnknownHostException {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(getURI()));
		DB mongoDatabase = mongoClient.getDB(getDBName());
		DBCollection databaseCollection = mongoDatabase.getCollection(COLLECTION_NAME);
		DBObject dbObject = new BasicDBObject("reportName", reportName)
				.append("teacherId", teacher.getId())
				.append("teacherName", teacher.getFirstName() + " " + teacher.getLastName());
		List<DBObject> dbGroupObjects = new ArrayList<DBObject>();
		for(Group g : groups) {
			List<Student> students = g.getStudents();
			DBObject dbGroupObject = new BasicDBObject("groupNumber", g.getNumber())
					.append("numberOfStudents", students.size());
			List<DBObject> dbStudentObjects = new ArrayList<DBObject>();
			for(Student s : students) {
				DBObject dbStudentObject = new BasicDBObject("studentName", s.getFirstName() + " " + s.getLastName())
						.append("address", s.getAddress())
						.append("email", s.getEmail())
						.append("phone", s.getPhone());
				dbStudentObjects.add(dbStudentObject);
			}
			dbGroupObject = ((BasicDBObject)dbGroupObject).append("students", dbGroupObjects);
			dbGroupObjects.add(dbGroupObject);
		}
		dbObject = ((BasicDBObject)dbObject).append("courses", dbGroupObjects)
				.append("date", new Date(Calendar.getInstance().getTime().getTime()));
		databaseCollection.insert(dbObject);
		mongoClient.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Report> getReports(Teacher teacher) throws UnknownHostException {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(getURI()));
		DB mongoDatabase = mongoClient.getDB(getDBName());
		DBCollection databaseCollection = mongoDatabase.getCollection(COLLECTION_NAME);
		DBObject query = new BasicDBObject("teacherId", teacher.getId());
		List<Report> reports = new ArrayList<Report>();
		DBCursor cursor = databaseCollection.find(query);
		while(cursor.hasNext()) {
			DBObject dbo = cursor.next();
			Report report = new AllStudentsByGroupsReport();
			report.setTeacher(teacher);
			report.setDate((Date)dbo.get("date"));
			List<Group> groups = new ArrayList<Group>();
			List<DBObject> dboGroups = (List<DBObject>)dbo.get("groups");
			for(DBObject dboGroup : dboGroups) {
				List<DBObject> dboStudents = (List<DBObject>)dboGroup.get("students");
				List<Student> students = new ArrayList<Student>();
				for(DBObject dboStudent : dboStudents) {
					Student student = new Student();
					String fullName = (String)dboStudent.get("studentName");
					String[] names = fullName.split(" ");
					student.setFirstName(names[0]);
					student.setLastName(names[1]);
					student.setAddress((String)dboStudent.get("address"));
					student.setEmail((String)dboStudent.get("email"));
					student.setPhone((String)dboStudent.get("phone"));
					students.add(student);
				}
				Group group = new Group();
				group.setNumber(Integer.parseInt(dboGroup.get("groupNumber").toString()));
				group.setStudents(students);
				groups.add(group);
			}
			((AllStudentsByGroupsReport)report).setGroups(groups);
			report.setName((String)dbo.get("reportName"));
			reports.add(report);
		}
		return reports;
	}
}
