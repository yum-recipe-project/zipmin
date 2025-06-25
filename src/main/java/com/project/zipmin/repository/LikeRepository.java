package com.project.zipmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.zipmin.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Integer> {
	
	// 특정 사용자가 좋아요 눌렀는지 여부 확인
    boolean existsByUserIdAndTablenameAndRecodenum(int userId, String tablename, int recodenum);

	// 특정 테이블의 특정 레코드에 좋아요 개수 조회
	long countByTablenameAndRecodenum(String tablename, int recodenum);
}
