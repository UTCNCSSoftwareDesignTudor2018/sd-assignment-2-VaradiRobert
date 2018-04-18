package com.application.course_management.persistence.reports;

import java.util.List;

import com.application.course_management.persistence.entities.Group;
import com.application.course_management.persistence.entities.Report;

public class AllStudentsByGroupsReport extends Report {
	private List<Group> groups;

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
	public List<Group> getGroups() {
		return groups;
	}
	
}
