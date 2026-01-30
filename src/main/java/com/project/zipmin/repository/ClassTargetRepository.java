package com.project.zipmin.repository;

import com.project.zipmin.entity.ClassTarget;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassTargetRepository extends JpaRepository<ClassTarget, Integer> {
	
	// 목록 조회
	List<ClassTarget> findAllByClasssId(int classId);
	
}
