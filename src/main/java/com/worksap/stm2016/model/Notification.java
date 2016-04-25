package com.worksap.stm2016.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Table(name="ss1604c187_rd4.notification")
@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_id")
	private Long notificationId;
	
	@Column(name = "type")
	private Integer type;
	
	@Column(name = "content")
	private String content;
	
	@ManyToOne(optional = false)
	@JoinColumn(nullable = false, name = "person_id", referencedColumnName = "person_id")
	@JsonIgnore
	private Person owner;
}
