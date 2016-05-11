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
@Table(name="ss1604c187_rd4.leave")
@Entity
public class Leave {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "leave_id")
	private Long leaveId;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false, name = "department_id", referencedColumnName = "department_id")
	private Department leaveDepartment;
	
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false, name = "person_id", referencedColumnName = "person_id")
	private Person leavePerson;
	
	@Column(name = "reason")
	private String reason;
	
	@Column(name = "\"startDate\"")
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date startDate;
	
	@Column(name = "\"endDate\"")
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date endDate;
	
	@Column(name = "status")
	private String status;

}
