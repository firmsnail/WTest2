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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name="ss1604c187_rd4.department")
@Entity
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "department_id")
	private Long departmentId;
	
	@Column(name = "\"deptName\"", nullable = false)
	String departmentName;
	
	@Column(name = "description")
	private String description;
	
	@OneToOne(optional = true, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "manager_id", referencedColumnName = "person_id")
	Person manager;
	
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy="department", fetch = FetchType.LAZY)
	List<Person> employees;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="stfrqDepartment", fetch = FetchType.LAZY)
	@OrderBy("expectDate ASC, submitDate ASC")
	List<StaffRequirement> staffRequirementList;
	
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy="hireDepartment", fetch = FetchType.LAZY)
	List<Hire> hireList;
	
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy="dismissionDepartment", fetch = FetchType.LAZY)
	List<Dismission> dismissionList;
	
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy="attendanceDepartment", fetch = FetchType.LAZY)
	List<Attendance> attendanceList;
	
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy="leaveDepartment", fetch = FetchType.LAZY)
	List<Leave> leaveList;
}
