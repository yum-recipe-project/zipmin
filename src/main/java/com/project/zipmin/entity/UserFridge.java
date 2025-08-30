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
@Table(name = "USER_FRIDGE")
public class UserFridge {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_fridge_id")
	@SequenceGenerator(name = "seq_user_fridge_id", sequenceName = "SEQ_USER_FRIDGE_ID", allocationSize = 1)
	private int id;
	
	private int amount;
	private String unit;
	private Date expdate;
	
	// private int fridgeId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FRIDGE_ID")
	private Fridge fridge;	
	
	// private int userId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	
}
