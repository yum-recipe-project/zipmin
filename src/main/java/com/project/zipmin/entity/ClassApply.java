package com.project.zipmin.entity;

import java.util.Date;

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
@Table(name = "CLASS_APPLY")
public class ClassApply {
	
	@Id
	@GeneratedValue(generator = "SEQ_CLASS_APPLY_ID")
	@SequenceGenerator(name = "SEQ_CLASS_APPLY_ID", sequenceName = "SEQ_CLASS_APPLY_ID", allocationSize = 1)
	private int id;
	
	private Date applydate;
	private String reason;
	private String question;
	private int selected;
	private int attend;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLASS_ID")
	private Class classs;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@PrePersist
    public void prePersist() {
		if (this.applydate == null) {
            this.applydate = new Date();
        }
        if (this.selected == 0) {
            this.selected = 2;
        }
        if (this.attend == 0) {
        	this.attend = 2;
        }
    }
	
}