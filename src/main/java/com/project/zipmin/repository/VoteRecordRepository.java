package com.project.zipmin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.VoteRecord;

@Repository
public interface VoteRecordRepository extends JpaRepository<VoteRecord, Integer> {
	
	Optional<VoteRecord> findByUserIdAndChompId(int userId, int chompId);
	
	int countByChompIdAndChoiceId(int chompId, int choiceId);
	int countByChompId(int chompId);
	
	boolean existsByUserIdAndChompId(int userId, int chompId);
	
	
	

}
