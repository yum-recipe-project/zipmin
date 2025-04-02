package com.project.zipmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.ChompVoteRecord;

@Repository
public interface ChompVoteRecordRepository extends JpaRepository<ChompVoteRecord, Integer> {

}
