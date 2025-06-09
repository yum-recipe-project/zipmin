package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Chomp;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;


@Repository
public interface ChompRepository extends JpaRepository<Chomp, Integer> {
	Page<Chomp> findByCategory(String category, Pageable pageable);
	
	
	
	
	
	@Query("""
		SELECT c FROM Chomp c
		LEFT JOIN c.chompVote v
		LEFT JOIN c.chompMegazine m
		LEFT JOIN c.chompEvent e
		WHERE c.category = :category
		AND (
		  :status = '전체' OR
		  (
		    :status = '진행중' AND (
		      (v IS NOT NULL AND v.opendate <= CURRENT_DATE AND v.closedate >= CURRENT_DATE) OR
		      (e IS NOT NULL AND e.opendate <= CURRENT_DATE AND e.closedate >= CURRENT_DATE)
		    )
		  ) OR
		  (
		    :status = '진행종료' AND (
		      (v IS NOT NULL AND v.opendate < CURRENT_DATE) OR
		      (e IS NOT NULL AND e.opendate < CURRENT_DATE)
		    )
		  )
		)
	""")
	Page<Chomp> findByCategoryAndStatus(@Param("category") String category, @Param("status") String status, Pageable pageable);

}
