package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Memo;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Integer> {
	Page<Memo> findAll(Pageable pageable);
	Page<Memo> findAllByUserId(Integer userId, Pageable pageable);
}
