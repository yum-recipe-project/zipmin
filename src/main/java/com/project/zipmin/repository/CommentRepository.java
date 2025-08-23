package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.Comment;


public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	// 목록 조회
	Page<Comment> findAllByTablenameAndRecodenumAndCommentIsNull(String tablename, Integer recodenum, Pageable pageable);
	
	Page<Comment> findAll(Pageable pageable);
	Page<Comment> findAllByContentContainingIgnoreCase(String keyword, Pageable pageable);
	
	Page<Comment> findAllByTablename(String tablename, Pageable pageable);
	Page<Comment> findAllByTablenameAndContentContainingIgnoreCase(String tablename, String keyword, Pageable pageable);
	

	
    Page<Comment> findByUserId(int userId, Pageable pageable);
    int countByTablenameAndRecodenum(String tablename, Integer recodenum);
}
