package com.project.zipmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.VoteChoice;

@Repository
public interface VoteChoiceRepository extends JpaRepository<VoteChoice, Integer> {
	
	List<VoteChoice> findByChompId(int chompId);
	
	
	void deleteAllByChompId(int chompId);
	
}
