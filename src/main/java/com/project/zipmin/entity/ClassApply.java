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
	@GeneratedValue(generator = "seq_class_apply_id")
	@SequenceGenerator(name = "seq_class_apply_id", sequenceName = "SEQ_CLASS_APPLY_ID", allocationSize = 1)
	private int id;
	
	private Date applydate;
	private String reason;
	private String question;
	private int selected;
	private int attend;
	
	// private int classId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLASS_ID")
	private Class classs;
	
	// private int userId;
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