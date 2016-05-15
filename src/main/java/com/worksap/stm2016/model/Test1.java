package com.worksap.stm2016.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name="ss1604c187_rd4.test1")
@Entity
public class Test1 {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tt_id")
	Integer tt_id;
	
	@Column(name = "comment")
	private String comment;
	
	@ManyToOne(optional = true)
	@JoinColumn(nullable = true, name = "t_id", referencedColumnName = "t_id")
	private Test0 t0;
}
