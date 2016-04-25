package com.worksap.stm2016.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	
	@ManyToMany
	@JoinTable(name="ss1604c187_rd4.skill", joinColumns = {@JoinColumn(name = "skill_id")},
			inverseJoinColumns = {@JoinColumn(name = "plan_id")})
	List<Skill> planSkillList;
	
	@Column(name = "\"invalidDate\"")
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date invalidDate;

	@Column(name = "status")
	Integer status;
}
