package com.project.zipmin.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.ChompEvent;

@Repository
public interface ChompEventRepository extends JpaRepository<ChompEvent, Integer> {

	@Query("SELECT e FROM ChompEvent e JOIN FETCH e.chomp c WHERE e.id = :id")
	Optional<ChompEvent> findById(@Param("id") int id);
}
