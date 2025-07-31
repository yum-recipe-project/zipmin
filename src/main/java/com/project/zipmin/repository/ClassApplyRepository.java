package com.project.zipmin.repository;

import com.project.zipmin.entity.ClassApply;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassApplyRepository extends JpaRepository<ClassApply, Integer> {
	
	Page<ClassApply> findByClasssId(int classId, Pageable page);
	Page<ClassApply> findByClasssIdAndSelected(int classId, int selected, Pageable page);
	
	@Query("SELECT a.classs.id FROM ClassApply a WHERE a.user.id = :userId")
	List<Integer> findClasssIdsByUserId(Integer userId);

	boolean existsByClasssIdAndUserId(int classId, int userId);
	
}
