package com.project.zipmin.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.VoteChoice;

@Repository
public interface VoteChoiceRepository extends JpaRepository<VoteChoice, Integer> {
	
	// 목록 조회
	List<VoteChoice> findAllByChompId(int chompId);
	
	// 기타
	void deleteAllByChompId(int chompId);
	void deleteAllByChompIdAndIdNotIn(int chompId, Collection<Integer> ids);
}
