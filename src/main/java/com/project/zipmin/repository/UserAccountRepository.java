package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.UserAccount;


@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
	
	// 상세 조회
	UserAccount findById(int id);
	UserAccount findByUserId(int userId);
	
	// 목록 조회
	Page<UserAccount> findAll(Pageable pageable);
	Page<UserAccount> findAllByUserNameContainingIgnoreCase(String keyword, Pageable pageable);
	Page<UserAccount> findAllByUserUsernameContainingIgnoreCase(String keyword, Pageable pageable);
	
}
