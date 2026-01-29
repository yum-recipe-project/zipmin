package com.project.zipmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.FridgeMemo;

@Repository
public interface MemoRepository extends JpaRepository<FridgeMemo, Integer> {
    List<FridgeMemo> findAllByUserId(Integer userId);
}
