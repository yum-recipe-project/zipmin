package com.project.zipmin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {
	
	Optional<Report> findByTablenameAndRecodenumAndUserId(String tablename, int recodenum, int userId);
	
	boolean existsByTablenameAndRecodenumAndUserId(String tablename, int recodenum, int userId);
	
}
