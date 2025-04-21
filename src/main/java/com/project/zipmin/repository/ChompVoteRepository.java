package com.project.zipmin.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.ChompVote;

@Repository
public interface ChompVoteRepository extends JpaRepository<ChompVote, Integer> {
	
	@Query("SELECT v FROM ChompVote v JOIN FETCH v.chomp c WHERE v.id = :id")
	Optional<ChompVote> findById(@Param("id") int id);
}
