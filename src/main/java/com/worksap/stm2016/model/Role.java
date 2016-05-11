package com.worksap.stm2016.model;

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
@Table(name="ss1604c187_rd4.role")
@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long roleId;
	
	@Column(name = "\"roleName\"", nullable = false)
	String roleName;
	
	@Column(name = "description")
	private String description;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="role", fetch = FetchType.LAZY)
	List<Person> employees;
}
