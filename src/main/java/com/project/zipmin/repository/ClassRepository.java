package com.project.zipmin.repository;

import com.project.zipmin.entity.Class;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, Integer> {
	
	// 상세 조회
	Class findById(int id);
	
	// 목록 조회
	Page<Class> findAll(Pageable pageable);
	Page<Class> findAllByCategory(String category, Pageable pageable);
	Page<Class> findAllByStatus(int approval, Pageable pageable);
	Page<Class> findAllByApproval(int approval, Pageable pageable);
	Page<Class> findAllByTitleContainingIgnoreCase(String keyword, Pageable pageable);
	Page<Class> findAllByCategoryAndApproval(String category, int approval, Pageable pageable);
	Page<Class> findAllByCategoryAndStatus(String category, int status, Pageable pageable);
	Page<Class> findAllByApprovalAndStatus(int approval, int status, Pageable pageable);
	Page<Class> findAllByStatusAndTitleContainingIgnoreCase(int status, String keyword, Pageable pageable);
	Page<Class> findAllByCategoryAndTitleContainingIgnoreCase(String category, String keyword, Pageable pageable);
	Page<Class> findAllByApprovalAndTitleContainingIgnoreCase(int approval, String keyword, Pageable pageable);
	Page<Class> findAllByCategoryAndApprovalAndStatus(String category, int approval, int status, Pageable pageable);
	Page<Class> findAllByCategoryAndStatusAndTitleContainingIgnoreCase(String category, int status, String keyword, Pageable pageable);
	Page<Class> findAllByCategoryAndApprovalAndTitleContainingIgnoreCase(String category, int approval, String keyword, Pageable pageable);
	Page<Class> findAllByApprovalAndStatusAndTitleContainingIgnoreCase(int approval, int status, String keyword, Pageable pageable);
	Page<Class> findAllByCategoryAndApprovalAndStatusAndTitleContainingIgnoreCase(String category, int approval, int status, String keyword, Pageable pageable);
	
	Page<Class> findAllByUserIdAndStatus(int userId, int status, Pageable pageable);
	Page<Class> findAllByUserIdAndApprovalAndStatus(int userId, int approval, int status, Pageable pageable);
	
	Page<Class> findAllByIdIn(List<Integer> ids, Pageable pageable);
	Page<Class> findAllByIdInAndStatus(List<Integer> ids, int status, Pageable pageable);
	Page<Class> findAllByIdInAndTitleContainingIgnoreCase(List<Integer> ids, String keyword, Pageable pageable);
	Page<Class> findAllByIdInAndStatusAndTitleContainingIgnoreCase(List<Integer> ids, int status, String keyword, Pageable pageable);
	
	
	// 기타
	boolean existsByTitleAndUserId(String title, int userId);
	
	
	
	
	
	// 사용자의 클래스 목록 조회
	Page<Class> findAllByUserId(int userId, Pageable pageable);
	Page<Class> findAllByUserIdAndApproval(int userId, Integer approval, Pageable pageable);
	
	Page<Class> findAllByUserIdAndApprovalAndNoticedateAfter(int userId, Integer approval, Date day, Pageable pageable);
	Page<Class> findAllByUserIdAndApprovalNotAndNoticedateAfterOrUserIdAndApprovalAndEventdateAfter(int userId1, Integer approval1, Date day1, int userId2, Integer approval2, Calendar day2, Pageable pageable);
	
	Page<Class> findAllByUserIdAndApprovalAndNoticedateBefore(int userId, Integer approval, Date day, Pageable pageable);
	Page<Class> findAllByUserIdAndApprovalNotOrUserIdAndApprovalAndEventdateBefore(int userId1, Integer approval1, int userId2, Integer approval2, Calendar day, Pageable pageable);
	
	
	
	
	Page<Class> findAllByIdInAndNoticedateAfter(List<Integer> ids, Date now, Pageable pageable);
	Page<Class> findByIdInAndNoticedateBefore(List<Integer> ids, Date now, Pageable pageable);

	List<Class> findAllByIdIn(List<Integer> ids);

}
