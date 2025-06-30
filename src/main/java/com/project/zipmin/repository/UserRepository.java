package com.project.zipmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByUsername(String username);
	
	
	
	boolean existsByUsername(String username);
	User findByNameAndTel(String name, String tel);
	
}
