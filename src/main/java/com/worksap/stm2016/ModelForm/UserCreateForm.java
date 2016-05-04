package com.worksap.stm2016.modelForm;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserCreateForm {
	
	
	@Min(value = 2)
	@Max(value = 5)
	//@Digits(fraction = 0, integer = 1, message = "Please enter a valid role.")
	//@TypeMismatch(message="ff")
	private Long role;
	
	@NotEmpty
	@Size(max=255)
	private String userName;
	
	@NotEmpty
	@Size(max=255)
	private String password;
	
	@NotEmpty
	@Size(max=255)
	private String confirmPassword;

	//@Pattern(regexp = "/w*/@/w*/./w*", message="Please enter valid email.")
	//@NotEmpty(message="Email password can't be empty.")
	@Email(message="Please enter a valid email.")
	private String email;
	
	@NotEmpty
	@Size(max=255)
	private String firstName;
	
	@NotEmpty
	@Size(max=255)
	private String lastName;

}
