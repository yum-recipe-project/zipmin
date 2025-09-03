package com.project.zipmin.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.zipmin.entity.Chomp;
import com.project.zipmin.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
	
	Page<Recipe> findAll(Pageable pageable);
	Page<Recipe> findAllByTitleContainingIgnoreCase(String keyword, Pageable pageable);
	
	Page<Recipe> findDistinctByCategoryList_Tag(String tag, Pageable pageable);
	Page<Recipe> findDistinctByCategoryList_TagAndTitleContainingIgnoreCase(String tag, String keyword, Pageable pageable);
	
	// 카테고리 여러개 (OR)
	Page<Recipe> findDistinctByCategoryList_TagIn(Collection<String> tags, Pageable pageable);
    Page<Recipe> findDistinctByCategoryList_TagInAndTitleContainingIgnoreCase(Collection<String> tags, String keyword, Pageable pageable);

}
