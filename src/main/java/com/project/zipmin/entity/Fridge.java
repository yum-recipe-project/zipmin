package com.project.zipmin.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "FRIDGE")
public class Fridge {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_fridge_id")
	@SequenceGenerator(name = "seq_fridge_id", sequenceName = "SEQ_FRIDGE_ID", allocationSize = 1)
	private int id;
	
	private String image;
	private String name;
	private int amount;
	private String unit;
	private Date expdate;
	private String category;
	
	// private int userId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	
}
