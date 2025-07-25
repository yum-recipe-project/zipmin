package com.project.zipmin.entity;

import java.util.Date;

import org.hibernate.annotations.Formula;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Table(name = "GUIDE")
public class Guide {
	@Id
	@GeneratedValue(generator = "seq_guide_id")
	@SequenceGenerator(name = "seq_guide_id", sequenceName = "SEQ_GUIDE_ID", allocationSize = 1)
	private int id;
	private String title;
	private String subtitle;
	private String category;
	private Date postdate;
	private String content;
	
	@Formula("(SELECT COUNT(*) FROM likes l WHERE l.recodenum = id AND l.tablename = 'guide')")
	private int likecount;
	
}