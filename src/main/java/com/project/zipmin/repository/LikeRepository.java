package com.project.zipmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Integer> {
	
	// Read
	Like findById(int id);
	Like findByTablenameAndRecodenumAndUserId(String tablename, int recodenum, int userId);
	
	// Read List
	List<Like> findAllByTablenameAndUserId(String tablename, int userId);
	List<Like> findAllByTablenameAndRecodenum(String tablename, int recodenum);
	
	// Etc
	int countByTablenameAndRecodenum(String tablename, int recodenum);
    boolean existsByTablenameAndRecodenumAndUserId(String tablename, int recodenum, int userId);
	
}