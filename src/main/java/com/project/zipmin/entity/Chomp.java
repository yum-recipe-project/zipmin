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
@Table(name = "CHOMP")
public class Chomp {
	
	@Id
	@GeneratedValue(generator = "seq_chomp_id")
	@SequenceGenerator(name = "seq_chomp_id", sequenceName = "SEQ_CHOMP_ID", allocationSize = 1)
	private int id;
	
	private String title;
	private Date opendate;
	private Date closedate;
	private String content;
	private String category;
	
	@Formula("(SELECT COUNT(*) FROM comments c WHERE c.recodenum = id AND c.tablename = category)")
	private int commentcount;
	
}
