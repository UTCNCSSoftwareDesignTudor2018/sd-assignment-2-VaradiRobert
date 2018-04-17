package com.application.course_management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.application.course_management.persistence.entities.Group;
import com.application.course_management.persistence.repositories.GroupRepository;

@Service
@Configurable
public class GroupService {
	@Autowired
	private GroupRepository groupRepository;
	public Group getByGroupId(int groupId) {
		Optional<Group> group = groupRepository.findById(groupId);
		return group.get();
	}

	public List<Group> getAll() {
		return groupRepository.findAll();
	}
	
	public Group getByGroupNumber(int groupNumber) {
		Optional<Group> group = groupRepository.findByNumber(groupNumber);
		return group.get();
	}
}
