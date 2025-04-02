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
@Table(name = "CHOMP_EVENT")
public class ChompEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_chomp_event_id")
	@SequenceGenerator(name = "seq_chomp_event_id", sequenceName = "SEQ_CHOMP_EVENT_ID", allocationSize = 1)
	private int id;
	
	// 여기에 내용 추가되어야 함
	
	private int chomp_id;
}
