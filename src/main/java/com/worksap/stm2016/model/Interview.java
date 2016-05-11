package com.worksap.stm2016.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;

import lombok.Data;

@Data
@Table(name="ss1604c187_rd4.interview")
@Entity
public class Interview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "interview_id")
	private Long interviewId;
	
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false, name = "interviewer_id", referencedColumnName = "person_id")
	private Person interviewer;
	
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false, name = "interviewee_id", referencedColumnName = "person_id")
	private Person interviewee;
	
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false, name = "plan_id", referencedColumnName = "plan_id")
	private RecruitingPlan planForInterview;
	
	@Column(name = "status")
	Integer status;
	
	@Column(name = "turns")
	Integer turns;
	
	@Column(name = "\"interviewTime\"")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonDeserialize(using = DateDeserializer.class)
	Date interviewTime;
	
	@Column(name = "\"updateTime\"")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonDeserialize(using = DateDeserializer.class)
	Date updateTime;
	
}
