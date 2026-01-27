package com.project.zipmin.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.zipmin.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
	
	// Read
	Recipe findById(int id);
	
	// Read List
	Page<Recipe> findAll(Pageable pageable);
	Page<Recipe> findAllByIdIn(List<Integer> ids, Pageable pageable);
	Page<Recipe> findAllByUserId(int userId, Pageable pageable);
	Page<Recipe> findAllByTitleContainingIgnoreCase(String keyword, Pageable pageable);
	Page<Recipe> findAllDistinctByCategoryList_TagIn(Collection<String> tags, Pageable pageable);
    Page<Recipe> findAllDistinctByCategoryList_TagInAndTitleContainingIgnoreCase(Collection<String> tags, String keyword, Pageable pageable);
    
    // Etc
    int countByUserId(int userId);
}
