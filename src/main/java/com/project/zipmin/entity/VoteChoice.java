package com.project.zipmin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "VOTE_CHOICE")
public class VoteChoice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_vote_choice_id")
	@SequenceGenerator(name = "seq_vote_choice_id", sequenceName = "SEQ_VOTE_CHOICE_ID", allocationSize = 1)
	private int id;
	
	private String choice;
	
	// private int vote_id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VOTE_ID")
	private Vote vote;
}
