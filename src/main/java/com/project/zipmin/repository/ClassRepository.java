package com.project.zipmin.repository;

import com.project.zipmin.entity.Class;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, Integer> {
	
	Page<Class> findByUserId(int userId, Pageable pageable);
	Page<Class> findByUserIdAndEventdateAfter(int userId, Date now, Pageable pageable);
	Page<Class> findByUserIdAndEventdateBefore(int userId, Date now, Pageable pageable);
	
//	Page<Class> findByIdIn(List<Integer> ids, Pageable pageable);
//	Page<Class> findByIdInAndEventdateBefore(List<Integer> ids, Date now, Pageable pageable);
//	Page<Class> findByIdInAndEventdateAfter(List<Integer> ids, Date now, Pageable pageable);

}
