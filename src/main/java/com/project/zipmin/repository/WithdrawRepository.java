package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Withdraw;

@Repository
public interface WithdrawRepository extends JpaRepository<Withdraw, Integer> {
	
	Page<Withdraw> findByUserId(int userId, Pageable pageable);

    // 모든 출금 내역 + 사용자 정보(fetch join)
    @Query(value = "SELECT w FROM Withdraw w JOIN FETCH w.user",
           countQuery = "SELECT COUNT(w) FROM Withdraw w")
    Page<Withdraw> findAllWithUser(Pageable pageable);
	
}
