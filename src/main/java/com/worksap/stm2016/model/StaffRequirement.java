package com.worksap.stm2016.model;

import java.util.ArrayList;
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

//import org.codehaus.jackson.annotate.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;

import lombok.Data;

@Data
@Table(name="ss1604c187_rd4.staff_requirement")
@Entity
public class StaffRequirement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stfrq_id")
	private Long staffRequirementId;
	
	@Column(name = "reason")
	private String reason;
	
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false, name = "department_id", referencedColumnName = "department_id")
	@JsonIgnore
	private Department stfrqDepartment;
	
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "plan_id", referencedColumnName = "plan_id")
	@JsonIgnore
	private RecruitingPlan recruitingPlan;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "hrmanager_id", referencedColumnName = "person_id")
	@JsonIgnore
	private Person hrManager;
	@Column(name = "\"hrmComments\"")
	private String hrManagerComments;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "recruiter_id", referencedColumnName = "person_id")
	@JsonIgnore
	private Person recruiter;
	@Column(name = "\"recruiterComments\"")
	private String recruiterComments;
	
	@Column(name = "\"requireNum\"", nullable = false)
	Integer requireNum;
	@Column(name = "\"hiredNum\"", nullable = false)
	Integer hiredNum;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "requirementForHire", targetEntity = Hire.class, fetch = FetchType.LAZY)
	private List<Hire> hires;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "requirementForInterview", targetEntity = Interview.class, fetch = FetchType.LAZY)
	private List<Interview> interviews;
	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name="ss1604c187_rd4.stfrq_skill", joinColumns = {@JoinColumn(name = "skill_id")},
			inverseJoinColumns = {@JoinColumn(name = "stfrq_id")})
	@JsonIgnore
	List<Skill> stfrqSkillList = new ArrayList<Skill>();
	
	@Column(name = "\"expectDate\"")
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date expectDate;
	
	@Column(name = "\"submitDate\"")
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date submitDate;

	@Column(name = "status")
	Integer status;

	
}
