package com.project.zipmin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Integer> {
	
	Optional<Like> findByTablenameAndRecodenumAndUserId(String tablename, int recodenum, int userId);
	
    boolean existsByTablenameAndRecodenumAndUserId(String tablename, int recodenum, int userId);

	int countByTablenameAndRecodenum(String tablename, int recodenum);
	
}
