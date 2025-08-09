package com.project.zipmin.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
	
	Optional<Event> findByChompId(int chompId);
	
	Optional<Event> findById(int id);
	
	Page<Event> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
}
