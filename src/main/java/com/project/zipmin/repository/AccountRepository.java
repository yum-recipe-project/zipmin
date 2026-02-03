package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	// 상세 조회
	Account findById(int id);
	Account findByUserId(int userId);
	
	// 목록 조회
	Page<Account> findAll(Pageable pageable);
	Page<Account> findAllByUserNameContainingIgnoreCase(String keyword, Pageable pageable);
	Page<Account> findAllByUserUsernameContainingIgnoreCase(String keyword, Pageable pageable);
	
}
