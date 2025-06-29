package com.project.zipmin.entity;

import java.util.Date;

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
@Table(name = "EVENT")
public class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_event_id")
	@SequenceGenerator(name = "seq_event_id", sequenceName = "SEQ_EVENT_ID", allocationSize = 1)
	private int id;
	
	private String title;
	private Date opendate;
	private Date closedate;
	private String content;
	
	// private int chomp_id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHOMP_ID")
	private Chomp chomp;
	
}
