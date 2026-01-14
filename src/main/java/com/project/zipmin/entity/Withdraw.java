package com.project.zipmin.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "WITHDRAW")
public class Withdraw {
    
    @Id
    @GeneratedValue(generator = "seq_withdraw_id")
    @SequenceGenerator(name = "seq_withdraw_id", sequenceName = "SEQ_WITHDRAW_ID", allocationSize = 1)
    private int id; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private UserAccount account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADMIN_ID")
    private User admin;

    private int point;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date claimdate;

    private int status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date settledate;

}
