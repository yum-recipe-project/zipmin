package com.project.zipmin.entity;

import java.util.Date;

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
@Table(name = "REVIEW")
public class Review {
	@Id
	@GeneratedValue(generator = "seq_review_id")
	@SequenceGenerator(name = "seq_review_id", sequenceName = "SEQ_REVIEW_ID", allocationSize = 1)
	private int id;
	private Date postdate;
	private int score;
	private String content;
	
	// private int recipeId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECIPE_ID")
	private Recipe recipe;
	
	// private int userId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
}
