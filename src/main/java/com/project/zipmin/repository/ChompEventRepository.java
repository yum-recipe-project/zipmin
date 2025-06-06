package com.project.zipmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.ChompEvent;

@Repository
public interface ChompEventRepository extends JpaRepository<ChompEvent, Integer> {

}
