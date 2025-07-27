package com.project.zipmin.repository;

import com.project.zipmin.entity.ClassApply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassApplyRepository extends JpaRepository<ClassApply, Integer> {
	
	List<ClassApply> findByClasssId(int classId);
	
	// 수정할수도
	List<ClassApply> findByUserId(int userId);
	
	boolean existsByClasssIdAndUserId(int classId, int userId);
	
}
