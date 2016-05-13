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
@Table(name="ss1604c187_rd4.payroll")
@Entity
public class Payroll {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payroll_id")
	private Long payrollId;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false, name = "department_id", referencedColumnName = "department_id")
	private Department payrollDepartment;
	
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false, name = "employee_id", referencedColumnName = "person_id")
	private Person payrollEmployee;
	
	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "comment")
	private String comment;
	
	@Column(name = "\"issueDate\"")
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date issueDate;
	

}
