package com.project.zipmin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.zipmin.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Integer> {
	
	Optional<Like> findByTablenameAndRecodenumAndUserId(String tablename, int recodenum, int userId);
	
    boolean existsByTablenameAndRecodenumAndUserId(String tablename, int recodenum, int userId);

	long countByTablenameAndRecodenum(String tablename, int recodenum);
 
	
}
