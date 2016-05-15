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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name="ss1604c187_rd4.test0")
@Entity
public class Test0 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "t_id")
	Integer t_id;
	
	@Column(name = "reason")
	private String reason;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="t0", fetch = FetchType.LAZY)
	List<Test1> t1List;
}
