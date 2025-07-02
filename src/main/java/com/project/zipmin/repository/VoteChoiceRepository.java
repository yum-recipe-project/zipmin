package com.project.zipmin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Vote;
import com.project.zipmin.entity.VoteChoice;

@Repository
public interface VoteChoiceRepository extends JpaRepository<VoteChoice, Integer> {
	
	// @EntityGraph(attributePaths = {"vote"})
	List<VoteChoice> findByVoteId(int voteId);
	
	
	void deleteAllByVoteId(int voteId);
	
}
