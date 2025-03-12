package com.project.zipmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
