package com.worksap.stm2016.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;

import lombok.Data;

@Data
@Table(name="ss1604c187_rd4.recruiting_plan")
@Entity
public class RecruitingPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plan_id")
	private Long planId;
	
	@Column(name = "reason")
	private String reason;
	
	@Column(name = "\"planNum\"", nullable = false)
	Integer planNum;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "hrmanager_id", referencedColumnName = "person_id")
	@JsonIgnore
	private Person planHRManager;
	@Column(name = "\"hrmComments\"")
	private String hrManagerComments;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "recruiter_id", referencedColumnName = "person_id")
	@JsonIgnore
	private Person planMaker;
	
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "recruitingPlan", targetEntity = StaffRequirement.class, fetch = FetchType.LAZY)
	private List<StaffRequirement> requirements;
	
	@ManyToMany
	@JoinTable(name="ss1604c187_rd4.plan_skill", joinColumns = {@JoinColumn(name = "skill_id")},
			inverseJoinColumns = {@JoinColumn(name = "plan_id")})
	List<Skill> planSkillList;
	
	@Column(name = "\"invalidDate\"")
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date invalidDate;

	@Column(name = "status")
	Integer status;
}
