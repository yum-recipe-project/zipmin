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

    // 출금 신청자 (users.id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    // 출금 계좌 (user_account.id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private UserAccount account;

    // 출금 처리 관리자 (users.id, role='ADMIN')
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADMIN_ID")
    private User admin;

    private int requestPoint; // 출금 신청 포인트

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate; // 출금 신청일

    private int status; // 출금 상태 (0: 대기, 1: 완료)

    @Temporal(TemporalType.TIMESTAMP)
    private Date completeDate; // 출금 완료일

}
