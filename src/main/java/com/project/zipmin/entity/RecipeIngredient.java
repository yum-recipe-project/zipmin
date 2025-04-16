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
@Table(name = "RECIPE_INGREDIENT")
public class RecipeIngredient {
	@Id
	@GeneratedValue(generator = "seq_recipe_ingredient_id")
	@SequenceGenerator(name = "seq_recipe_ingredient_id", sequenceName = "SEQ_RECIPE_INGREDIENT_ID", allocationSize = 1)
	private int id;
	private String name;
	private int amount;
	private String unit;
	private String note;
	
	// private String recipeId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECIPE_ID")
	private Recipe recipe;
}
