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
@Table(name = "CLASSES")
public class Class {
	
	@Id
	@GeneratedValue(generator = "SEQ_CLASSES_ID")
	@SequenceGenerator(name = "SEQ_CLASSES_ID", sequenceName = "SEQ_CLASSES_ID", allocationSize = 1)
	private int id;
	
	private String title;
	private String category;
	private String image;
	private String introduce;
	private String place;
	private Date postdate;
	private Date eventdate;
	private Date starttime;
	private Date endtime;
	private Date noticedate;
	private int headcount;
	private String need;
	private int approval;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@Formula("(SELECT COUNT(*) FROM class_apply ca WHERE ca.class_id = id)")
	private int applycount;
	
	@PrePersist
    public void prePersist() {
        if (this.postdate == null) {
            this.postdate = new Date();
        }
        if (this.approval == 0) {
            this.approval = 2;
        }
    }
	
}