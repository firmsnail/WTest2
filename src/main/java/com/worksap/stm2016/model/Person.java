package com.worksap.stm2016.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name="person")
@Entity
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "\"personId\"")
	private Integer personId;
	
	@Column(name = "\"firstName\"")
	private String firstName;
	@Column(name = "\"lastName\"")
	private String lastName;
	@Column(name = "\"userName\"", unique=true)
	private String userName;
	@Column(name = "password")
	private String password;
}
