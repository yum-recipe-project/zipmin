package com.project.zipmin.entity;

import java.util.Date;

import org.hibernate.annotations.Formula;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
@Table(name = "CHOMP")
public class Chomp {
	
	@Id
	@GeneratedValue(generator = "seq_chomp_id")
	@SequenceGenerator(name = "seq_chomp_id", sequenceName = "SEQ_CHOMP_ID", allocationSize = 1)
	private int id;
	
	private String title;
	private Date opendate;
	private Date closedate;
	private String content;
	private String image;
	private String category;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@Formula("(SELECT COUNT(*) FROM comments c WHERE c.recodenum = id AND c.tablename = category)")
	private int commentcount;
	
	@Formula("(SELECT COUNT(*) FROM vote_record vr WHERE vr.chomp_id = id)")
	private int recordcount;

	@PrePersist
    public void prePersist() {
        if (this.closedate == null) {
            this.closedate = new Date();
        }
    }
}
