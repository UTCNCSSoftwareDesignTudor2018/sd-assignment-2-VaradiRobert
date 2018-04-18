package com.application.course_management.persistence.nosql.dao;

import java.net.UnknownHostException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import com.application.course_management.persistence.entities.Course;
import com.application.course_management.persistence.entities.Enrollment;
import com.application.course_management.persistence.entities.Report;
import com.application.course_management.persistence.entities.Student;
import com.application.course_management.persistence.entities.Teacher;
import com.application.course_management.persistence.reports.FailingStudentsReport;
import com.application.course_management.services.EnrollmentService;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class FailingStudentsReportDAO extends ReportDAO {
	private static final String COLLECTION_NAME = "failing_students_reports";
	public void storeReport(Teacher teacher, String reportName) throws UnknownHostException {
		MongoClient mongoClient = new MongoClient(new MongoClientURI(getURI()));
		DB mongoDatabase = mongoClient.getDB(getDBName());
		DBCollection databaseCollection = mongoDatabase.getCollection(COLLECTION_NAME);
		DBObject dbObject = new BasicDBObject("reportName", reportName)
				.append("teacherId", teacher.getId())
				.append("teacherName", teacher.getFirstName() + " " + teacher.getLastName());
		List<DBObject> dbCourseObjects = new ArrayList<DBObject>();
		List<Course> courses = teacher.getCourses();
		for(Course c : courses) {
			List<Enrollment> enrollments = c.getEnrollments().stream().filter(e -> {
				return e.getStatus().equals(EnrollmentService.STATUS_ACCEPTED) && e.getGrade() < 5.0;
			}).collect(Collectors.toList());
			DBObject dbCourseObject = new BasicDBObject("courseName", c.getName())
					.append("numberOfEnrolledStudents", enrollments.size());
			List<DBObject> dbEnrollmentObjects = new ArrayList<DBObject>();
			for(Enrollment e : enrollments) {
				DBObject dbEnrollmentObject = new BasicDBObject("studentName", e.getStudent().getFirstName() + " " + e.getStudent().getLastName())
						.append("grade", e.getGrade() == -1 ? "Not Recorded" : e.getGrade())
						.append("address", e.getStudent().getAddress())
						.append("email", e.getStudent().getEmail())
						.append("phone", e.getStudent().getPhone());
				dbEnrollmentObjects.add(dbEnrollmentObject);
			}
			dbCourseObject = ((BasicDBObject)dbCourseObject).append("enrollments", dbEnrollmentObjects);
			dbCourseObjects.add(dbCourseObject);
		}
		dbObject = ((BasicDBObject)dbObject).append("courses", dbCourseObjects).append("date", new Date(Calendar.getInstance().getTime().getTime()));
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
			Report report = new FailingStudentsReport();
			report.setTeacher(teacher);
			report.setDate((Date)dbo.get("date"));
			List<Enrollment> enrollments = new ArrayList<Enrollment>();
			List<DBObject> dboCourses = (List<DBObject>)dbo.get("courses");
			for(DBObject dboCourse : dboCourses) {
				List<DBObject> dboEnrollments = (List<DBObject>)dboCourse.get("enrollments");
				Course course = new Course();
				course.setName((String)dboCourse.get("courseName"));
				for(DBObject dboEnrollment : dboEnrollments) {
					Student student = new Student();
					String fullName = (String)dboEnrollment.get("studentName");
					String[] names = fullName.split(" ");
					student.setFirstName(names[0]);
					student.setLastName(names[1]);
					String gradeString = (String)dboEnrollment.get("grade");
					double grade = (gradeString.equals("Not Recorded") ? -1 : Double.parseDouble(gradeString));
					student.setAddress((String)dboEnrollment.get("address"));
					student.setEmail((String)dboEnrollment.get("email"));
					student.setPhone((String)dboEnrollment.get("phone"));
					Enrollment enrollment = new Enrollment();
					enrollment.setCourse(course);
					enrollment.setGrade(grade);
					enrollment.setStudent(student);
					enrollments.add(enrollment);
				}
			}
			((FailingStudentsReport)report).setEnrollments(enrollments);
			report.setName((String)dbo.get("reportName"));
			reports.add(report);
		}
		return reports;
	}
}
