package com.application.course_management.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.course_management.persistence.entities.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {
	public Optional<Group> findById(int groupId);
	public Optional<Group> findByNumber(int groupNumber);
	public List<Group> findAll();
}
