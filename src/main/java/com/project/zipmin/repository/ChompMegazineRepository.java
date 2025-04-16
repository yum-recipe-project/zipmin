package com.project.zipmin.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.ChompMegazine;

@Repository
public interface ChompMegazineRepository extends JpaRepository<ChompMegazine, Integer> {
	
	@Query("SELECT m FROM ChompMegazine m JOIN FETCH m.chomp c WHERE m.id = :id")
	Optional<ChompMegazine> findById(@Param("id") int id);
}
