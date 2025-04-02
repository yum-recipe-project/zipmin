package com.project.zipmin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "CHOMP_VOTE_CHOICE")
public class ChompVoteChoice {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_chomp_vote_choice_id")
	@SequenceGenerator(name = "seq_chomp_vote_choice_id", sequenceName = "SEQ_VOTE_CHOICE_ID", allocationSize = 1)
	private int id;
	private int vote_id;
	private int choice;
}
