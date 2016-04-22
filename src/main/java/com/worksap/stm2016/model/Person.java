package com.worksap.stm2016.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", targetEntity = Notification.class, fetch = FetchType.EAGER)
	private List<Notification> nottificationList;
}
