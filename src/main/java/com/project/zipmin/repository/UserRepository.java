package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.zipmin.entity.Role;
import com.project.zipmin.entity.User;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	
	Page<User> findAll(Pageable pageable);
	Page<User> findAllByUsernameContainingIgnoreCase(String keyword, Pageable pageable);
	Page<User> findAllByNameContainingIgnoreCase(String keyword, Pageable pageable);
	Page<User> findAllByNicknameContainingIgnoreCase(String keyword, Pageable pageable);
	
	Page<User> findAllByRoleIn(Collection<Role> roles, Pageable pageable);
	Page<User> findAllByRoleInAndUsernameContainingIgnoreCase(Collection<Role> roles, String keyword, Pageable pageable);
	Page<User> findAllByRoleInAndNameContainingIgnoreCase(Collection<Role> roles, String keyword, Pageable pageable);
	Page<User> findAllByRoleInAndNicknameContainingIgnoreCase(Collection<Role> roles, String keyword, Pageable pageable);

	List<User> findAllByIdIn(List<Integer> idList);
	
	Optional<User> findByUsername(String username);
	Optional<User> findByNameAndTel(String name, String tel);
	Optional<User> findByUsernameAndEmail(String username, String email);
	
	boolean existsByUsername(String username);
	boolean existsByTel(String tel);
	boolean existsByEmail(String email);
	
}
