package com.project.zipmin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Megazine;

@Repository
public interface MegazineRepository extends JpaRepository<Megazine, Integer> {
	
	Optional<Megazine> findById(@Param("id") int id);
	Optional<Megazine> findByChompId(int chompId);
	
}
