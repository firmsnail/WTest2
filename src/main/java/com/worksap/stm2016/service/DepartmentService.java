package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.modelForm.DepartmentForm;

public interface DepartmentService {
	public List<Department> findAll();
	public Page<Department> findAll(Pageable pageable);
	public Department save(Department dept);
	public Department findOne(Long id);
	public Department findByDepartmentName(String departmentName);
	public Department create(DepartmentForm department);
	public List<Department> findByManagerIsNot(Person manager);
}
