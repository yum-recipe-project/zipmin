package com.project.zipmin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.VoteRecord;

@Repository
public interface VoteRecordRepository extends JpaRepository<VoteRecord, Integer> {
	
	Optional<VoteRecord> findByUserIdAndVoteId(int userId, int voteId);
	
	long countByChoiceId(int choiceId);
	long countByVoteId(int voteId);
	
	boolean existsByUserIdAndVoteId(int userId, int voteId);
	
	
	
    void deleteByUserIdAndVoteId(int userId, int voteId);

}
