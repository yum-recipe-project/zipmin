package com.project.zipmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.ChompVote;

@Repository
public interface ChompVoteRepository extends JpaRepository<ChompVote, Integer> {
	
}
