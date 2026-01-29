package com.project.zipmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.VoteRecord;

@Repository
public interface VoteRecordRepository extends JpaRepository<VoteRecord, Integer> {
	
	// 상세 조회
	VoteRecord findByUserIdAndChompId(int userId, int chompId);
	
	// 기타
	int countByChompId(int chompId);
	int countByChompIdAndChoiceId(int chompId, int choiceId);
	boolean existsByUserIdAndChompId(int userId, int chompId);
	
}
