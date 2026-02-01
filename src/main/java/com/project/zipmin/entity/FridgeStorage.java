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
@Table(name = "FRIDGE_STORAGE")
public class FridgeStorage {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FRIDGE_STORAGE_ID")
	@SequenceGenerator(name = "SEQ_FRIDGE_STORAGE_ID", sequenceName = "SEQ_FRIDGE_STORAGE_ID", allocationSize = 1)
	private int id;
	
	private int amount;
	private String unit;
	private Date expdate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FRIDGE_ID")
	private Fridge fridge;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	
}
