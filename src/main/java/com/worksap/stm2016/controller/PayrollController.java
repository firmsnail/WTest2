package com.worksap.stm2016.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Payroll;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.PayrollService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.RoleService;
import com.worksap.stm2016.utils.CommonUtils;

@Controller
@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'C&B-SPECIALIST', 'SHORT-TERM-EMPLOYEE')")
@RequestMapping(value = "/payroll")
public class PayrollController {
	
	@Autowired
	PayrollService payrollService;
	@Autowired
	PersonService personService;
	@Autowired
	DepartmentService departmentService;
	@Autowired
	RoleService roleService;
	
	@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'C&B-SPECIALIST')")
	@RequestMapping(value={"/showPayrolls"},  method = RequestMethod.GET)
	public String showPayrolls(Long departmentId, Long employeeId, String strStartDate, String strEndDate, Model model, HttpServletRequest request, HttpServletResponse  response) throws ParseException {
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null, endDate = null;
		if (strStartDate != null && !strStartDate.isEmpty()) {
			startDate = df.parse(strStartDate);
		}
		if (strEndDate != null && !strEndDate.isEmpty()) {
			endDate = df.parse(strEndDate);
		}
		
		List<Payroll> payrolls = null;
		if (employeeId != null) {
			Person cUser = personService.findById(employeeId);
			if (cUser != null) {
				if (startDate != null && endDate != null) {
					payrolls = payrollService.findByPersonAndStartDateAndEndDate(cUser, startDate, endDate);
				} else if (startDate != null) {
					payrolls = payrollService.findByPersonAndStartDate(cUser, startDate);
				} else if (endDate != null) {
					payrolls = payrollService.findByPersonAndEndDate(cUser, endDate);
				} else {
					payrolls = payrollService.findByPerson(cUser);
				}
				System.out.println("curEmployee: " + cUser.getPersonId());
				model.addAttribute("curEmployee", cUser);
			}
		} else if (departmentId != null){
				Department department = departmentService.findOne(departmentId);
				if (department != null) {
					if (startDate != null && endDate != null) {
						payrolls = payrollService.findByDepartmentAndStartDateAndEndDate(department, startDate, endDate);
					} else if (startDate != null) {
						payrolls = payrollService.findByDepartmentAndStartDate(department, startDate);
					} else if (endDate != null) {
						payrolls = payrollService.findByDepartmentAndEndDate(department, endDate);
					} else {
						payrolls = payrollService.findByDepartment(department);
					}
					System.out.println("curDept: " + department.getDepartmentId());
					model.addAttribute("curDept", department);
				}
		} else {
			if (startDate != null && endDate != null) {
				payrolls = payrollService.findByStartDateAndEndDate(startDate, endDate);
			} else if (startDate != null) {
				payrolls = payrollService.findByStartDate(startDate);
			} else if (endDate != null) {
				payrolls = payrollService.findByEndDate(endDate);
			} else {
				payrolls = payrollService.findAll();
			}
		}
		if (payrolls != null) {
			model.addAttribute("payrolls", payrolls);
			genPayrollsReport(payrolls, request, response);
		}
		List<Department> allDepts = departmentService.findAll();
		model.addAttribute("allDepts", allDepts);
		Role shortRole = roleService.findOne(CommonUtils.ROLE_SHORT_TERM_EMPLOYEE);
		List<Person> allEmployees = personService.findByRoleAndStatus(shortRole, CommonUtils.EMPLOYEE_WORKING);
		model.addAttribute("allEmployees", allEmployees);
		if (startDate != null) {
			df=new SimpleDateFormat("yyyy-MM-dd");
			String curStartDate = df.format(startDate);
			model.addAttribute("curStartDate", curStartDate);
		}
		if (endDate != null) {
			df=new SimpleDateFormat("yyyy-MM-dd");
			String curEndDate = df.format(endDate);
			model.addAttribute("curEndDate", curEndDate);
		}
		return "payroll/showPayrolls";
	}
	
	
	@RequestMapping(value={"/downloadPayrolls"},  method = RequestMethod.GET)
	public void downloadPayrolls(HttpServletRequest request, HttpServletResponse response) {
		String CPath = request.getSession().getServletContext().getRealPath("");
		String fileName = CPath+ "/WEB-INF/files/Payroll Report.xlsx";
        response.setContentType("multipart/form-data");  
        response.setHeader("Content-Disposition", "attachment;fileName="  
                + new String("Payroll Report.xlsx")); 
        InputStream in;
		try {
			in = new FileInputStream(fileName);
			OutputStream os;
			os = response.getOutputStream();
			byte[] b = new byte[1024 * 1024];  
	        int length;  
	        while ((length = in.read(b)) > 0) {  
	            os.write(b, 0, length);  
	        }  
	        in.close();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	private void genPayrollsReport(List<Payroll> payrolls, HttpServletRequest request, HttpServletResponse response) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Payroll Report");

        CellStyle titleStyle = workbook.createCellStyle();
        XSSFFont titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontName("IMPACT");
        titleFont.setItalic(true);
        titleFont.setFontHeightInPoints((short) 30);
        
        titleStyle.setFont(titleFont);
        
        Row rowH = sheet.createRow(0);					//File title
        Cell cellTile = rowH.createCell(1);
        cellTile.setCellStyle(titleStyle);
        cellTile.setCellValue("Payrolls Report");
        
        CellStyle divStyle = workbook.createCellStyle();
        XSSFFont divFont = workbook.createFont();
        divFont.setBold(true);
        divFont.setFontHeightInPoints((short) 16);			//Divided title
        divStyle.setFont(divFont);
        rowH = sheet.createRow(1);
        Cell cellDiv = rowH.createCell(1);
        cellDiv.setCellStyle(divStyle);
        cellDiv.setCellValue("Divided By Payroll.");
        
        CellStyle headStyle = workbook.createCellStyle();
        XSSFFont headFont = sheet.getWorkbook().createFont();		//Table (head)
        headFont.setBold(true);;
        headStyle.setFont(headFont);
        rowH = sheet.createRow(2);
        Cell headName = rowH.createCell(1);
        headName.setCellStyle(headStyle);
        headName.setCellValue("Employee Name");
        Cell headID = rowH.createCell(2);
        headID.setCellStyle(headStyle);
        headID.setCellValue("Employee ID");
        Cell headValue = rowH.createCell(3);
        headValue.setCellStyle(headStyle);
        headValue.setCellValue("Amount(USD)");;
        
        int rowCount = 3;
        for (Payroll payroll : payrolls) {
        	Row row = sheet.createRow(++rowCount);
        	Cell employeeName = row.createCell(1);
        	employeeName.setCellValue(payroll.getPayrollEmployee().getFirstName() + " " + payroll.getPayrollEmployee().getLastName());
        	Cell employeeID = row.createCell(2);
        	employeeID.setCellValue(payroll.getPayrollEmployee().getPersonId());
        	Cell amount = row.createCell(3);
        	amount.setCellValue(payroll.getAmount());
        }
        String CPath = request.getSession().getServletContext().getRealPath("");
        System.out.printf("contextPath: " + CPath);
        try (FileOutputStream outputStream = new FileOutputStream(CPath+ "/WEB-INF/files/Payroll Report.xlsx")) {
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
  
	}

	@PreAuthorize("hasAnyAuthority('SHORT-TERM-EMPLOYEE')")
	@RequestMapping(value={"/showPayrollsByPerson"},  method = RequestMethod.GET)
	public String showPayrollsByPerson(String strStartDate, String strEndDate, Model model) throws ParseException {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null, endDate = null;
		if (strStartDate != null && !strStartDate.isEmpty()) {
			startDate = df.parse(strStartDate);
		}
		if (strEndDate != null && !strEndDate.isEmpty()) {
			endDate = df.parse(strEndDate);
		}
		
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Payroll> payrolls = null;
		if (startDate != null && endDate != null) {
			payrolls = payrollService.findByPersonAndStartDateAndEndDate(curUser.getUser(), startDate, endDate);
		} else if (startDate != null) {
			payrolls = payrollService.findByPersonAndStartDate(curUser.getUser(), startDate);
		} else if (endDate != null) {
			payrolls = payrollService.findByPersonAndEndDate(curUser.getUser(), endDate);
		} else {
			payrolls = payrollService.findByPerson(curUser.getUser());
		}
		if (startDate != null) {
			df=new SimpleDateFormat("yyyy-MM-dd");
			String curStartDate = df.format(startDate);
			model.addAttribute("curStartDate", curStartDate);
		}
		if (endDate != null) {
			df=new SimpleDateFormat("yyyy-MM-dd");
			String curEndDate = df.format(endDate);
			model.addAttribute("curEndDate", curEndDate);
		}
		model.addAttribute("payrolls", payrolls);
		return "payroll/showPayrolls";
	}
	
}
