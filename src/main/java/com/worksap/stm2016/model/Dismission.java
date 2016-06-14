package com.worksap.stm2016.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;

import lombok.Data;

@Data
@Table(name="ss1604c187_rd4.dismission")
@Entity
public class Dismission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dismission_id")
	private Long dismissionId;

	@ManyToOne(optional = false)
	@JoinColumn(nullable = false, name = "department_id", referencedColumnName = "department_id")
	private Department dismissionDepartment;
	
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false, name = "person_id", referencedColumnName = "person_id")
	private Person dismissionPerson;
	
	@Column(name = "commment")
	private String comment;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "hrmanager_id", referencedColumnName = "person_id")
	@JsonIgnore
	private Person dismissionHRManager;
	@Column(name = "\"hrmComments\"")
	private String hrManagerComments;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "cbspecialist_id", referencedColumnName = "person_id")
	@JsonIgnore
	private Person dismissionCBSpecialist;
	@Column(name = "\"cbComments\"")
	private String cbComments;
	
	@Column(name = "\"expectDate\"")
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date expectDate;
	
	@Column(name = "\"submitDate\"")
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date submitDate;
	
	@Column(name = "\"dismissionDate\"")
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date dismissionDate;

	@Column(name = "status")
	Integer status;
}
