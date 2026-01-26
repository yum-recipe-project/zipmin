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
@Table(name = "GUIDE")
public class Guide {
	
	@Id
	@GeneratedValue(generator = "SEQ_GUIDE_ID")
	@SequenceGenerator(name = "SEQ_GUIDE_ID", sequenceName = "SEQ_GUIDE_ID", allocationSize = 1)
	private int id;
	private String title;
	private String subtitle;
	private String category;
	private Date postdate;
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@Formula("(SELECT COUNT(*) FROM likes l WHERE l.recodenum = id AND l.tablename = 'guide')")
	private int likecount;
	
	@PrePersist
    public void prePersist() {
        if (this.postdate == null) {
            this.postdate = new Date();
        }
	}
	
}



