package com.worksap.stm2016.modelForm;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class DepartmentForm {
	
	@NotEmpty
	@Size(max=255)
	String departmentName;
	
	String description;
	
	@Min(1)
	Long managerId;

}
