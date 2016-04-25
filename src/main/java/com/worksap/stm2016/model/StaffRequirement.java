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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	
	@ManyToOne(optional = true)
	@JoinColumn(nullable = false, name = "department_id", referencedColumnName = "department_id")
	@JsonIgnore
	private Department stfrqDepartment;
	
	@Column(name = "\"requireNum\"", nullable = false)
	Integer requireNum;
	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name="ss1604c187_rd4.stfrq_skill", joinColumns = {@JoinColumn(name = "skill_id")},
			inverseJoinColumns = {@JoinColumn(name = "stfrq_id")})
	List<Skill> stfrqSkillList;
	
	@Column(name = "\"expectDate\"")
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date expectDate;

	@Column(name = "status")
	Integer status;
}
