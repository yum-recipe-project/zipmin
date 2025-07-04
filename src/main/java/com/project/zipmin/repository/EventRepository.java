package com.project.zipmin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
	
	Optional<Event> findByChompId(int chompId);
	
	@EntityGraph(attributePaths = {"chomp"})
	Optional<Event> findById(@Param("id") int id);
}
