package com.worksap.stm2016.modelForm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PlanForm {
	
	private String reason;
	
	@NotEmpty
	private List<Long> requirements = new ArrayList<Long>();
	
	@NotEmpty
	private List<Long> skills = new ArrayList<Long>();
	
	@Min(1)
	@Max(1000)
	private Integer planNum;
	
	@Future @NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date expectDate;
	
	@Future @NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date invalidDate;

}
