package com.project.zipmin.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
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
@Table(name = "COMMENTS")
public class Comment {
	
	@Id
	@GeneratedValue(generator = "seq_comments_id")
	@SequenceGenerator(name = "seq_comments_id", sequenceName = "SEQ_COMMENTS_ID", allocationSize = 1)
	private int id;
	
	private Date postdate;
	private String content;
	private String tablename;
	private int recodenum;
	
	// private int user_id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	// private int comm_id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMM_ID")
	private Comment comment;

	@Builder.Default
	@OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
	@OrderBy("id ASC")
	private List<Comment> subcomment = new ArrayList<Comment>();
	
	@Formula("(SELECT COUNT(*) FROM likes l WHERE l.recodenum = id AND l.tablename = 'comments')")
	private int likecount;
	
	@Formula("(SELECT COUNT(*) FROM report r WHERE r.recodenum = id AND r.tablename = 'comments')")
	private int reportcount;
	
	@PrePersist
    public void prePersist() {
        if (this.postdate == null) {
            this.postdate = new Date();
        }
    }
	
}
