package com.project.zipmin.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Megazine;

@Repository
public interface MegazineRepository extends JpaRepository<Megazine, Integer> {
	
	Optional<Megazine> findById(int id);
	Optional<Megazine> findByChompId(int chompId);
	Page<Megazine> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
	
}
