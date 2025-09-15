package com.project.zipmin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "RECIPE_CATEGORY")
public class RecipeCategory {
	
	@Id
	@GeneratedValue(generator = "seq_recipe_category_id")
	@SequenceGenerator(name = "seq_recipe_category_id", sequenceName = "SEQ_RECIPE_CATEGORY_ID", allocationSize = 1)
	private int id;
	
	private String type;
	private String tag;
	
	// private int recipeId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECIPE_ID")
	private Recipe recipe;
	
}
