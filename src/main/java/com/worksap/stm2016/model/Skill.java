package com.worksap.stm2016.model;

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
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name="ss1604c187_rd4.skill")
@Entity
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "skill_id")
	private Long skillId;
	
	@Column(name = "\"skillName\"", nullable = false)
	String skillName;
	
	@Column(name = "description")
	private String description;
	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name="ss1604c187_rd4.skill_person", joinColumns = {@JoinColumn(name = "person_id")},
			inverseJoinColumns = {@JoinColumn(name = "skill_id")})
	List<Person> users;
	
	@ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "stfrqSkillList")
	private List<StaffRequirement> stfrqList;
	
	@ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "planSkillList")
	private List<RecruitingPlan> planList;
	
	
}
