package com.project.zipmin.entity;

import java.util.Date;

import org.hibernate.annotations.Formula;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "REVIEW")
public class Review {
	
	@Id
	@GeneratedValue(generator = "SEQ_REVIEW_ID")
	@SequenceGenerator(name = "SEQ_REVIEW_ID", sequenceName = "SEQ_REVIEW_ID", allocationSize = 1)
	private int id;
	
	private Date postdate;
	private int score;
	private String content;
	private String tablename;
	private int recodenum;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@Formula("(SELECT COUNT(*) FROM likes l WHERE l.recodenum = id AND l.tablename = 'review')")
	private int likecount;
	
	@Formula("(SELECT COUNT(*) FROM report r WHERE r.recodenum = id AND r.tablename = 'review')")
	private int reportcount;
	
	@PrePersist
	public void prePersist() {
		if (this.postdate == null) {
			this.postdate = new Date();
		}
	}
	
}
