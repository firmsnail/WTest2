package com.worksap.stm2016.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;

import lombok.Data;

@Data
@Table(name="ss1604c187_rd4.hire")
@Entity
public class Hire {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hire_id")
	private Long hireId;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false, name = "department_id", referencedColumnName = "department_id")
	private Department hireDepartment;
	
	@OneToOne(optional = false)
	@JoinColumn(nullable = false, name = "person_id", referencedColumnName = "person_id")
	private Person hirePerson;
	
	@Column(name = "commment")
	private String comment;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "hrmanager_id", referencedColumnName = "person_id")
	@JsonIgnore
	private Person hireHRManager;
	@Column(name = "\"hrmComments\"")
	private String hrManagerComments;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "recruiter_id", referencedColumnName = "person_id")
	@JsonIgnore
	private Person hireRecruiter;
	@Column(name = "\"recruiterComments\"")
	private String recruiterComments;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "plan_id", referencedColumnName = "plan_id")
	@JsonIgnore
	private RecruitingPlan hirePlan;

	@Column(name = "\"hireDate\"")
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date hireDate;
	
	@Column(name = "\"submitDate\"")
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date submitDate;
	
	@Column(name = "salary")
	private Double salary;

	@Column(name = "period")
	private Integer period;	//months
	
	@Column(name = "status")
	Integer status;
}
