package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Chomp;
import com.project.zipmin.entity.Guide;

@Repository
public interface KitchenRepository extends JpaRepository<Guide, Integer> {
	Page<Guide> findAll(Pageable pageable);
	Page<Guide> findAllByCategory(String category, Pageable pageable);
}
