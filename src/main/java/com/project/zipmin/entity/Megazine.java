package com.project.zipmin.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Table(name = "MEGAZINE")
public class Megazine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_megazine_id")
	@SequenceGenerator(name = "seq_megazine_id", sequenceName = "SEQ_MEGAZINE_ID", allocationSize = 1)
	private int id;
	
	private String title;
	
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
	private Date postdate;
	private String content;
	
	// private int chomp_id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHOMP_ID")
	private Chomp chomp;
	
}