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
@Table(name = "CLASS_TUTOR")
public class ClassTutor {
	
	@Id
	@GeneratedValue(generator = "seq_class_tutor_id")
	@SequenceGenerator(name = "seq_class_tutor_id", sequenceName = "SEQ_CLASS_TUTOR_ID", allocationSize = 1)
	private int id;
	
	private String image;
	private String name;
	private String career;
	
	// private int classId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLASS_ID")
	private Class classs;
	
}