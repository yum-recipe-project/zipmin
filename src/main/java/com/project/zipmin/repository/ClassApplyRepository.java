package com.project.zipmin.repository;

import com.project.zipmin.entity.ClassApply;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassApplyRepository extends JpaRepository<ClassApply, Integer> {
	
	Page<ClassApply> findByClasssId(int classId, Pageable page);
	Page<ClassApply> findByClasssIdAndSelected(int classId, Integer selected, Pageable page);
	
	
	Page<ClassApply> findByUserId(Integer userId, Pageable pageable);
	Page<ClassApply> findByUserIdAndClasss_EventdateBefore(Integer userId, Date now, Pageable pageable);
	Page<ClassApply> findByUserIdAndClasss_EventdateAfter(Integer userId, Date now, Pageable pageable);
	
	
	List<ClassApply> findAllByUserId(Integer userId);
	List<ClassApply> findAllByUserIdAndSelectedAndAttend(Integer userId, Integer selected, Integer attend);

	boolean existsByClasssIdAndUserId(int classId, int userId);
	
}
