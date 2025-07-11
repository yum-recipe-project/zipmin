package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Chomp;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;


@Repository
public interface ChompRepository extends JpaRepository<Chomp, Integer> {
	Page<Chomp> findAll(Pageable pageable);
	Page<Chomp> findByCategory(String category, Pageable pageable);
}
