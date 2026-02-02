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
@Table(name = "WITHDRAW")
public class Withdraw {
    
    @Id
    @GeneratedValue(generator = "SEQ_WITHDRAW_ID")
    @SequenceGenerator(name = "SEQ_WITHDRAW_ID", sequenceName = "SEQ_WITHDRAW_ID", allocationSize = 1)
    private int id; 

    private int point;
    private int status;
    private Date claimdate;
    private Date settledate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private UserAccount account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADMIN_ID")
    private User admin;
    
    @PrePersist
    public void prePersist() {
    	if (this.claimdate == null) {
    		this.claimdate = new Date();
    	}
    	if (this.settledate == null) {
    		this.settledate = new Date();
    	}
    }

}
