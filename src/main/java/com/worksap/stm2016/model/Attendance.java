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
@Table(name="ss1604c187_rd4.attendance")
@Entity
public class Attendance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "attendance_id")
	private Long attendanceId;

	@ManyToOne(optional = true)
	@JoinColumn(nullable = false, name = "department_id", referencedColumnName = "department_id")
	private Department attendanceDepartment;
	
	@ManyToOne(optional = true)
	@JoinColumn(nullable = false, name = "person_id", referencedColumnName = "person_id")
	private Person attendancePerson;
	
	@Column(name = "type")
	private Integer type;	//1: Normal, 2: AttendanceLate, 3: LeaveEarly, 4: AttendanceNotRecord, 5: LeaveNotRecord
	
	@Column(name = "\"attendanceDate\"")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date attendanceDate;
	
	@Column(name = "\"attendanceTime\"")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date attendanceTime;
	
	@Column(name = "\"leaveTime\"")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date leaveTime;

}
