package com.project.zipmin.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Formula;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
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
	private Date postdate;
	private String cooklevel;
	private String cooktime;
	private String spicy;
	private String portion;
	private String tip;
	
	// private int userId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<RecipeCategory> categoryList = new HashSet<>();
	
	@Formula("(SELECT COUNT(*) FROM comments c WHERE c.recodenum = id AND c.tablename = 'recipe')")
	private int commentcount;
	
	@Formula("(SELECT COUNT(*) FROM likes l WHERE l.recodenum = id AND l.tablename = 'recipe')")
	private int likecount;
	
	@Formula("(SELECT COUNT(*) FROM report r WHERE r.recodenum = id AND r.tablename = 'recipe')")
	private int reportcount;
	
	@Formula("(SELECT COUNT(*) FROM review r WHERE r.recipe_id = id)")
	private int reviewcount;
	
	@Formula("(SELECT NVL(ROUND(AVG(r.score), 1), 0) FROM review r WHERE r.recipe_id = id)")
	private double reviewscore;
	
	@PrePersist
    public void prePersist() {
        if (this.postdate == null) {
            this.postdate = new Date();
        }
    }
}
