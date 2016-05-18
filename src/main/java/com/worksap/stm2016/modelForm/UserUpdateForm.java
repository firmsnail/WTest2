package com.worksap.stm2016.modelForm;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserUpdateForm {
	
	//@Pattern(regexp = "/w*/@/w*/./w*", message="Please enter valid email.")
	//@NotEmpty(message="Email password can't be empty.")
	@Email(message="Please enter a valid email.")
	private String email;
	
	@Min(value = 0)
	@Max(value = 2)
	private Integer gender;
	
	@Min(value = 18)
	@Max(value = 60)
	private Integer age;
	
	private String address;
	
	private String phone;
	
	private List<Long> skills = new ArrayList<Long>();
	
	@Size(max=255)
	private String password;
	
	@Size(max=255)
	private String confirmPassword;


}
