package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.Comment;


public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	// 상세 조회
	Comment findById(int id);
	
	// 목록 조회
	Page<Comment> findAll(Pageable pageable);
	Page<Comment> findAllByUserId(int userId, Pageable pageable);
	Page<Comment> findAllByTablename(String tablename, Pageable pageable);
	Page<Comment> findAllByTablenameAndRecodenum(String tablename, int recodenum, Pageable pageable);
	Page<Comment> findAllByTablenameAndRecodenumAndCommentIsNull(String tablename, int recodenum, Pageable pageable);
	Page<Comment> findAllByContentContainingIgnoreCase(String keyword, Pageable pageable);
	Page<Comment> findAllByTablenameAndContentContainingIgnoreCase(String tablename, String keyword, Pageable pageable);

	// 기타
    int countByTablenameAndRecodenum(String tablename, int recodenum);
}
