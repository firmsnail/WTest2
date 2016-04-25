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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;

import lombok.Data;

@Data
@Table(name="ss1604c187_rd4.person")
@Entity
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "person_id")
	private Long personId;
	
	@Column(name = "\"firstName\"", nullable=false)
	private String firstName;
	@Column(name = "\"lastName\"", nullable=false)
	private String lastName;
	@Column(name = "\"userName\"", unique=true, nullable=false)
	private String userName;
	@Column(name = "password", nullable=false)
	private String password;
	@Column(name = "email", unique=true)
	private String email;
	
	@Column(name = "gender")
	private Integer gender;
	
	@Column(name = "age")
	private Integer age;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "phone")
	private String phone;
	
	@ManyToOne(optional = true)
	@JoinColumn(nullable = false, name = "department_id", referencedColumnName = "department_id")
	@JsonIgnore
	Department department;

	@ManyToOne(optional=true)
	@JoinColumn(nullable = false, name = "role_id", referencedColumnName = "role_id")
	@JsonIgnore
	Role role;
	
	@Column(name = "\"startTime\"")
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date startTime;
	
	@Column(name = "\"endTime\"")
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date endTime;
	
	@Column(name="salary")
	private Double salary;
	
	@Column(name="status")
	private Integer status;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", targetEntity = Notification.class, fetch = FetchType.LAZY)
	private List<Notification> nottificationList;
	
	@ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "users")
	private List<Skill> skillList;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "interviewer", targetEntity = Interview.class, fetch = FetchType.LAZY)
	private List<Interview> interviewList;		// for interviewer
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "interviewee", targetEntity = Interview.class, fetch = FetchType.LAZY)
	private List<Interview> interviewedList;		// for interviewee
	
	@OneToOne(cascade = CascadeType.REFRESH, mappedBy = "hirePerson", targetEntity = Hire.class, fetch = FetchType.LAZY)
	private Hire hire;
	
	@OneToOne(cascade = CascadeType.REFRESH, mappedBy = "dismissionPerson", targetEntity = Dismission.class, fetch = FetchType.LAZY)
	private Dismission dismission;
	
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "attendancePerson", targetEntity = Attendance.class, fetch = FetchType.LAZY)
	private List<Attendance> attendanceList;
	
	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "leavePerson", targetEntity = Leave.class, fetch = FetchType.LAZY)
	private List<Leave> leaveList;
}
