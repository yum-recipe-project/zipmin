package com.project.zipmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Chomp;

@Repository
public interface ChompRepository extends JpaRepository<Chomp, Integer> {

}
