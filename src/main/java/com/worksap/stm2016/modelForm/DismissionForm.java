package com.worksap.stm2016.modelForm;


import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class DismissionForm {
	
	@NotNull
	@Min(0)
	private Long employeeId;
	
	@NotEmpty
	private String comment;

	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date expectDate;
	

}
