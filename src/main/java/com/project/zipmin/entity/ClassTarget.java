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
@Table(name = "CLASS_TARGET")
public class ClassTarget {
	
	@Id
	@GeneratedValue(generator = "SEQ_CLASS_TARGET_ID")
	@SequenceGenerator(name = "SEQ_CLASS_TARGET_ID", sequenceName = "SEQ_CLASS_TARGET_ID", allocationSize = 1)
	private int id;
	
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLASS_ID")
	private Class classs;
	
}