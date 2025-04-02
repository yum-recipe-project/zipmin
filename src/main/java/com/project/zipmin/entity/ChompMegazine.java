package com.project.zipmin.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "CHOMP_MEGAZINE")
public class ChompMegazine {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_chomp_megazine_id")
	@SequenceGenerator(name = "seq_chomp_megazine_id", sequenceName = "SEQ_CHOMP_MEGAZINE_ID", allocationSize = 1)
	private int id;
	private Date postdate;
	private String content;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHOMP_ID")
	private Chomp chomp;
}
