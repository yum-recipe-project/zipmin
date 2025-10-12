package com.project.zipmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Memo;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Integer> {
    List<Memo> findAllByUserId(Integer userId);
}
