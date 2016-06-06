package com.worksap.stm2016.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.modelForm.DepartmentForm;
import com.worksap.stm2016.repository.DepartmentRepository;
import com.worksap.stm2016.repository.PersonRepository;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.utils.CommonUtils;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private PersonRepository personRepository;

	@Override
	public List<Department> findAll() {
		// TODO Auto-generated method stub
		return (List<Department>) departmentRepository.findAll();
	}

	@Override
	public Page<Department> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return departmentRepository.findAll(pageable);
	}

	@Override
	public Department save(Department department) {
		// TODO Auto-generated method stub
		return departmentRepository.save(department);
	}

	@Override
	public Department findOne(Long id) {
		// TODO Auto-generated method stub
		return departmentRepository.findOne(id);
	}

	@Override
	public Department findByDepartmentName(String departmentName) {
		return departmentRepository.findByDepartmentName(departmentName);
	}

	@Override
	public Department create(DepartmentForm department) {
		Department dept = new Department();
		dept.setDepartmentName(department.getDepartmentName());
		if (department.getDescription() != null && department.getDescription().length() > 0) {
			dept.setDescription(department.getDescription());
		}
		if (department.getManagerId() != null) {
			Person manager = personRepository.findOne(department.getManagerId());
			if (manager != null) {
				dept.setManager(manager);
			}
		}
		dept = departmentRepository.save(dept);
		if (department.getManagerId() != null) {
			Person manager = personRepository.findOne(department.getManagerId());
			if (manager != null) {
				manager.setDepartment(dept);
				manager.setStatus(CommonUtils.EMPLOYEE_WORKING);
				manager = personRepository.findOne(department.getManagerId());
			}
		}
		return dept;
	}

	@Override
	public List<Department> findByManagerIsNot(Person manager) {
		return departmentRepository.findByManagerIsNot(manager);
	}
	

}
