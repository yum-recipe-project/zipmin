package com.project.zipmin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
	
	@EntityGraph(attributePaths = {"chomp"})
	Optional<Vote> findByChompId(int chompId);

}
