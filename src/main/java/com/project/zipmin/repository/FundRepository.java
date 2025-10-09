package com.project.zipmin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.Fund;
import com.project.zipmin.entity.Report;

public interface FundRepository extends JpaRepository<Fund, Integer> {
	
	
}
