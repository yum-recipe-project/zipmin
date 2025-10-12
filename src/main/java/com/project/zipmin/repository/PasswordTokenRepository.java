package com.project.zipmin.repository;

import com.project.zipmin.entity.PasswordToken;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Integer> {
	
	// Optional<PasswordToken> findTopByTokenHashOrderByIdDesc(String tokenHash);
	Optional<PasswordToken> findByToken(String token);
	
}
