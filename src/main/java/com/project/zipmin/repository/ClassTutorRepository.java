package com.project.zipmin.repository;

import com.project.zipmin.entity.ClassTutor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassTutorRepository extends JpaRepository<ClassTutor, Integer> {
	
	List<ClassTutor> findByClasssId(int classId);
	
}
