package com.project.zipmin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.zipmin.entity.Comment;
import com.project.zipmin.entity.Fund;
import com.project.zipmin.entity.Guide;

public interface FundRepository extends JpaRepository<Fund, Integer> {
	
	Page<Fund> findByFundeeId(int userId, Pageable pageable);
//	 @Query("SELECT f FROM Fund f JOIN FETCH f.funder JOIN FETCH f.recipe WHERE f.fundee.id = :userId")
//	 Page<Fund> findByFundeeIdWithDetails(@Param("userId") Integer userId, Pageable pageable);

}
