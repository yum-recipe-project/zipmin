package com.project.zipmin.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.zipmin.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Integer> {
	
	@Query("SELECT COUNT(l) FROM Like l WHERE l.tablename = :tablename AND l.recodenum = :recodenum")
    long countByTablenameAndRecodenum(
    		@Param("tablename") String tablename,
    		@Param("recodenum") int recodenum);
	
}
