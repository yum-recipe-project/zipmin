package com.project.zipmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Chomp;

@Repository
public interface ChompRepository extends JpaRepository<Chomp, Integer> {
	
	@Query("SELECT c FROM Chomp c LEFT JOIN FETCH c.chompMegazine")
	List<Chomp> findAll();
	
	
}
