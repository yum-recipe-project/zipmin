package com.project.zipmin.entity;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
	private String category;
	private String title;
	
	@OneToOne(mappedBy = "chomp", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ChompVote chompVote;
	@OneToOne(mappedBy = "chomp", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ChompMegazine chompMegazine;
	@OneToOne(mappedBy = "chomp", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ChompEvent chompEvent;
	
	
	
//    @Transient
//    private String status;
//
//    public String getStatus() {
//        Date now = new Date();
//
//        if (chompVote != null && chompVote.getOpendate() != null && chompVote.getClosedate() != null) {
//            if (!now.before(chompVote.getOpendate()) && !now.after(chompVote.getClosedate())) {
//                return "VOTE_OPEN";
//            }
//        }
//
//        if (chompEvent != null && chompEvent.getOpendate() != null && chompEvent.getClosedate() != null) {
//            if (!now.before(chompEvent.getOpendate()) && !now.after(chompEvent.getClosedate())) {
//                return "EVENT_OPEN";
//            }
//        }
//
//        return "CLOSED";
//    }
}
