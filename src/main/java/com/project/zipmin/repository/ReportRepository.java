package com.project.zipmin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {

	// Read
	Report findById(int id);
	List<Report> findAllByTablenameAndRecodenum(String tablename, int recodenum);
	Report findByTablenameAndRecodenumAndUserId(String tablename, int recodenum, int userId);

	// etc
	int countByTablenameAndRecodenum(String tablename, int recodenum);
	boolean existsByTablenameAndRecodenumAndUserId(String tablename, int recodenum, int userId);
	
}
