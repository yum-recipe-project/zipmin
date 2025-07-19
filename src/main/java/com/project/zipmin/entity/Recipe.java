package com.project.zipmin.entity;

import org.hibernate.annotations.Formula;

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
@Table(name = "RECIPE")
public class Recipe {
	
	@Id
	@GeneratedValue(generator = "seq_recipe_id")
	@SequenceGenerator(name = "seq_recipe_id", sequenceName = "SEQ_RECIPE_ID", allocationSize = 1)
	private int id;
	
	private String image;
	private String title;
	private String introduce;
	private String cooklevel;
	private String cooktime;
	private String spicy;
	private String portion;
	private String tip;
	private String youtube;
	
	// private String userId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@Formula("(SELECT COUNT(*) FROM likes l WHERE l.recodenum = id AND l.tablename = 'recipe')")
	private int likecount;
	
	@Formula("(SELECT NVL(AVG(r.score), 0) FROM review r WHERE r.recipe_id = id)")
	private Double score;
}
