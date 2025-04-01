package com.project.zipmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.ChompVoteChoice;

@Repository
public interface ChompVoteChoiceRepository extends JpaRepository<ChompVoteChoice, Integer> {

}
