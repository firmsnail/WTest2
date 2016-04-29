package com.worksap.stm2016.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserCreateForm {
	
	@Min(1)
	@Max(5)
	private Long role;
	
	@NotEmpty
	private String userName;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	private String confirmPassword;
	
	@NotEmpty
	private String email;
	
	@NotEmpty
	private String firstName;
	
	@NotEmpty
	private String lastName;

}
