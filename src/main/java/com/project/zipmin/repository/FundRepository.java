package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.zipmin.entity.Fund;

public interface FundRepository extends JpaRepository<Fund, Integer> {
	
	Page<Fund> findByFundeeId(int userId, Pageable pageable);
	
    @Query("SELECT SUM(f.point) FROM Fund f WHERE f.fundee.id = :userId")
    Integer sumPointByFundeeId(@Param("userId") Integer userId);
	

}
