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
@Table(name = "FRIDGE_MEMO")
public class Memo {
	
	@Id
	@GeneratedValue(generator = "seq_fridge_memo_id")
	@SequenceGenerator(name = "seq_fridge_memo_id", sequenceName = "SEQ_FRIDGE_MEMO_ID", allocationSize = 1)
	private int id;
	
	private String name;
	private int amount;
	private String unit;
	private String note;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	
}
