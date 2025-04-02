package com.project.zipmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.ChompMegazine;

@Repository
public interface ChompMegazineRepository extends JpaRepository<ChompMegazine, Integer> {

}
