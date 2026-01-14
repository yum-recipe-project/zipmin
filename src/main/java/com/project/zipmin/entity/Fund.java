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
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FUND")
public class Fund {

    @Id
    @GeneratedValue(generator = "seq_fund_id")
    @SequenceGenerator(name = "seq_fund_id", sequenceName = "SEQ_FUND_ID", allocationSize = 1)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FUNDER_ID")
    private User funder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FUNDEE_ID")
    private User fundee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECIPE_ID")
    private Recipe recipe;

    private int point;
    private Date funddate;

    // 출금 상태 (0: 미출금, 1: 출금 완료 등)
    // private int status;

    // 후원 생성 시 funddate 기본값 설정
    @PrePersist
    public void prePersist() {
        if (this.funddate == null) {
            this.funddate = new Date();
        }
    }
}
