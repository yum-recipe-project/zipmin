package com.project.zipmin.repository;

import com.project.zipmin.entity.ClassSchedule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Integer> {
	
	List<ClassSchedule> findByClasssId(int classId);
	
}
