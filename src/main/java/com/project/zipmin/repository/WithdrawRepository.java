package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Withdraw;

@Repository
public interface WithdrawRepository extends JpaRepository<Withdraw, Integer> {
	
	Page<Withdraw> findByUserId(int userId, Pageable pageable);
	
}
