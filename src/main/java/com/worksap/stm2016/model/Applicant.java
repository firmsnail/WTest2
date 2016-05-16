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
@Table(name="ss1604c187_rd4.applicant")
@Entity
public class Applicant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "applicant_id")
	private Long applicantId;
	
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false, name = "person_id", referencedColumnName = "person_id")
	private Person applicant;
	
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false, name = "plan_id", referencedColumnName = "plan_id")
	private RecruitingPlan planForApplicant;
	
	@Column(name = "status")
	Integer status;
	
	@Column(name = "\"applyDate\"")
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = DateDeserializer.class)
	Date applyDate;
	
}
