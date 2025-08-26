package com.project.zipmin.repository;

import com.project.zipmin.entity.Class;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, Integer> {
	
	Page<Class> findAll(Pageable pageable);
	Page<Class> findAllByTitleContainingIgnoreCase(String keyword, Pageable pageable);
	
	Page<Class> findAllByNoticedateAfter(Date now, Pageable pageable);
	Page<Class> findAllByTitleContainingIgnoreCaseAndNoticedateAfter(String keyword, Date now, Pageable pageable);
	
	
	Page<Class> findAllByNoticedateBefore(Date now, Pageable pageable);
	Page<Class> findAllByTitleContainingIgnoreCaseAndNoticedateBefore(String keyword, Date now, Pageable pageable);
	
	Page<Class> findAllByCategory(String category, Pageable pageable);
	Page<Class> findAllByCategoryAndTitleContainingIgnoreCase(String category, String keyword, Pageable pageable);
	
	Page<Class> findAllByCategoryAndNoticedateAfter(String category, Date now, Pageable pageable);
	Page<Class> findAllByCategoryAndTitleContainingIgnoreCaseAndNoticedateAfter(String category, String keyword, Date now, Pageable pageable);
	
	
	Page<Class> findAllByCategoryAndNoticedateBefore(String category, Date now, Pageable pageable);
	Page<Class> findAllByCategoryAndTitleContainingIgnoreCaseAndNoticedateBefore(String category, String keyword, Date now, Pageable pageable);
	
	
	
	
	
	
	
	
	
	// 나의 쿠킹클래스에 사용 (수정 필요)
	Page<Class> findByUserId(int userId, Pageable pageable);
	Page<Class> findByUserIdAndNoticedateAfter(int userId, Date now, Pageable pageable);
	Page<Class> findByUserIdAndNoticedateBefore(int userId, Date now, Pageable pageable);
}
