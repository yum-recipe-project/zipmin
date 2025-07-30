package com.project.zipmin.repository;

import com.project.zipmin.entity.Class;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, Integer> {
	
	Page<Class> findByUserId(int userId, Pageable pageable);
	Page<Class> findByUserIdAndEventdateAfter(int userId, Date now, Pageable pageable);
	Page<Class> findByUserIdAndEventdateBefore(int userId, Date now, Pageable pageable);
	
}
