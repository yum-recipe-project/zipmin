package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Withdraw;

@Repository
public interface WithdrawRepository extends JpaRepository<Withdraw, Integer> {
	
	Page<Withdraw> findAll(Pageable pageable);
	Page<Withdraw> findAllByUserUsernameContainingIgnoreCase(String keyword, Pageable pageable);
	Page<Withdraw> findAllByUserNameContainingIgnoreCase(String keyword, Pageable pageable);
	
	Page<Withdraw> findAllByStatus(int status, Pageable pageable);
	Page<Withdraw> findAllByStatusAndUserUsernameContainingIgnoreCase(int status, String keyword, Pageable pageable);
	Page<Withdraw> findAllByStatusAndUserNameContainingIgnoreCase(int status, String keyword, Pageable pageable);
	
	Page<Withdraw> findAllByUserId(int userId, Pageable pageable);
	
}
