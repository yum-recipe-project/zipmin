package com.project.zipmin.repository;

import com.project.zipmin.entity.ClassTarget;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassTargetRepository extends JpaRepository<ClassTarget, Integer> {
	
	List<ClassTarget> findByClasssId(int classId);
	
}
