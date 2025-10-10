package com.project.zipmin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.User;
import com.project.zipmin.entity.UserAccount;


@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
	
    Optional<UserAccount> findByUser(User user);
    Optional<UserAccount> findByUserId(Integer userId);
	
}
