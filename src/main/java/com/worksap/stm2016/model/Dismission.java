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

	@ManyToOne(optional = true)
	@JoinColumn(nullable = false, name = "department_id", referencedColumnName = "department_id")
	private Department dismissionDepartment;
	
	@OneToOne(optional = true)
	@JoinColumn(nullable = false, name = "person_id", referencedColumnName = "person_id")
	private Person dismissionPerson;
	
	@Column(name = "commment")
	private String comment;
	
	@Column(name = "\"dismissionDate\"")
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = DateDeserializer.class)
	private Date dismissionDate;

	@Column(name = "status")
	Integer status;
}
