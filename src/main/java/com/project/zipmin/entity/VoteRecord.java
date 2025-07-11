package com.project.zipmin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "VOTE_RECORD")
public class VoteRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_vote_record_id")
	@SequenceGenerator(name = "seq_vote_record_id", sequenceName = "SEQ_VOTE_RECORD_ID", allocationSize = 1)
	private int id;
	
	// private int vote_id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vote_id")
	Vote vote;
	
	// private int user_id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	User user;
	
	// private int choice_id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHOICE_ID")
	VoteChoice choice;
}
