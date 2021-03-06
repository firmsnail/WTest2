package com.worksap.stm2016.modelForm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class RequirementForm {
	
	@NotNull
	@Min(0)
	private Long departmentId;
	
	private String reason;
	
	@NotEmpty
	private List<Long> skills = new ArrayList<Long>();
	
	@NotNull
	@Min(1)
	@Max(1000)
	private Integer requireNum;
	
	@Future @NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date expectDate;

}
