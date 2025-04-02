package com.project.zipmin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "CHOMP_VOTE_RECORD")
public class ChompVoteRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_chomp_vote_record_id")
	@SequenceGenerator(name = "seq_chomp_vote_record_id", sequenceName = "SEQ_CHOMP_VOTE_RECORD_ID", allocationSize = 1)
	private int id;
	private int vote_id;
	private String user_id;
	private int option_id;
}
