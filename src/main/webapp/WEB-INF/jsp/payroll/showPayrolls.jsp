<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../common/header.jsp" />
	<link href="${pageContext.request.contextPath}/resources/static/css/common/dataTables.bootstrap.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/static/css/common/select2.min.css" rel="stylesheet">
	<link href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.2.0/css/datepicker.min.css" rel="stylesheet">
	<!-- <link href="${pageContext.request.contextPath}/resources/static/css/common/datepicker3.css" rel="stylesheet">-->

	<script src="${pageContext.request.contextPath}/resources/static/js/common/jquery.dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/dataTables.bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/select2.full.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/bootstrap-datepicker.js"></script>
	<script>
	    $(document).ready(function() {
	    	//Initialize Select2 Elements
		    $(".select2").select2();
	    	
	        $('#dataTables-example').DataTable({
	                responsive: false
	        });
	        
	        $( "#from" ).datepicker({
		        autoclose: true,
		        format: "yyyy-mm-dd"
		    }).on('changeDate', function(ev){
		    	//alert(ev.date);
		    	$("#to").datepicker("setStartDate", ev.date);
		    });
	        
	        $("#to").datepicker({
	        	autoclose: true,
	        	format: "yyyy-mm-dd"
	        }).on('changeDate', function(ev){
	        	//alert(ev.date);
	        	$("#from").datepicker("setEndDate", ev.date);
	        });
	        
	        if ($("#from").val() != "") {
	        	$("#to").datepicker("setStartDate", $("#from").datepicker('getDate'));
	        }
	        if ($("#to").val() != "") {
	        	$("#from").datepicker("setEndDate", $("#to").datepicker('getDate'));
	        }
	        
	    });
	    function downloadReport() {
	    	$.ajax({
	    		type: 'GET',
	    		url: "/payroll/downloadPayrolls",
	    		dataType: 'json',
	    		success: function(data) {
	    			
	    		}
	    	});
	    }
    </script>
</head>

<body>

    <div id="wrapper">

        <jsp:include page="../common/nav.jsp" />
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><spring:message code="payrolls" /></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <spring:message code="payrolls" />
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<c:if test="${currentUser.user.role.roleId == 1 or currentUser.user.role.roleId == 3 }">
	                        	<a href="/payroll/downloadPayrolls">
									<button type="button" class="btn btn-success"><spring:message code="gen-payroll-report" /></button>
								</a>
								<br>
							</c:if>
                        	<form name="payrollsForm" method="GET"  <c:choose><c:when test="${currentUser.user.role.roleId == 5 }">action="/payroll/showPayrollsByPerson"</c:when><c:otherwise>action="/payroll/showPayrolls"</c:otherwise></c:choose>  >
								<div class="form-group-sm">
									<div class="row">
										<div class="col-xs-2">
											<label class="control-label"> <spring:message code="from-date" />:</label>
											<input type="text" class="form-control" id="from" name="strStartDate" value="${curStartDate}" />
										</div>
										<div class="col-xs-2">
											<label class="control-label"> <spring:message code="to-date" />:</label>
											<input type="text" class="form-control" id="to" name="strEndDate" value="${curEndDate}" />
										</div>
										<c:if test="${currentUser.user.role.roleId != 5 }">
											<div class="col-xs-2">
												<label class="control-label"><spring:message code="department" />:</label>
												<select class="form-control select2" name="departmentId">
													<option selected="selected"></option>
													<c:forEach var="department" items="${allDepts}" varStatus="status">
														<option value="${department.departmentId}" <c:if test="${curDept != null and department.departmentId == curDept.departmentId}">selected="selected"</c:if>>
															${department.departmentName}
														</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-xs-3">
												<label><spring:message code="employee" />:</label>
												<select class="form-control select2" name="employeeId">
													<option selected="selected"></option>
													<c:forEach var="employee" items="${allEmployees}" varStatus="status">
														<option value="${employee.personId}" <c:if test="${curEmployee != null and employee.personId == curEmployee.personId}">selected="selected"</c:if>>
															${employee.firstName} ${employee.lastName} ID:${employee.personId }
														</option>
													</c:forEach>
								                </select>
											</div>
										</c:if>
										<br>
										<div class="col-xs-2">
											<button type="submit" class="btn btn-success"><spring:message code="query" /></button>
										</div>
									</div>
								</div>
							</form>
                        	<br>
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th><spring:message code="issue-date" /></th>
                                            <th><spring:message code="department" /></th>
                                            <th><spring:message code="employee" /></th>
                                            <th><spring:message code="payroll.salary" /></th>
                                            <th><spring:message code="normal-attendances" /></th>
                                            <th><spring:message code="un-normal-attendances" /></th>
                                            <th><spring:message code="payroll.amount" /></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="payroll" items="${payrolls}" varStatus="status">
											<tr <c:choose><c:when test="${status.index % 2 == 0}">class="odd"</c:when><c:otherwise>class="even"</c:otherwise></c:choose>>
												<td><fmt:formatDate value="${payroll.issueDate}" pattern="yyyy-MM-dd"/></td>
												<td><a href="/department/showOneDepartment?departmentId=${payroll.payrollDepartment.departmentId}">${payroll.payrollDepartment.departmentName}</a></td>
												<td><a href="/user/showOneEmployee?personId=${payroll.payrollEmployee.personId}">${payroll.payrollEmployee.firstName} ${payroll.payrollEmployee.lastName}</a></td>
												<th>${payroll.baseSalary }</th>
												<th>
													<c:choose>
														<c:when test="${payroll.normalAttends == null }">
															0
														</c:when>
														<c:otherwise>
															${payroll.normalAttends }
														</c:otherwise>
													</c:choose>
												</th>
												<th>
													<c:choose>
														<c:when test="${payroll.normalAttends == null }">
															0
														</c:when>
														<c:otherwise>
															${payroll.unNormalAttends }
														</c:otherwise>
													</c:choose>
												</th>
												<td>${payroll.amount}USD</td>
											</tr>
                                    	</c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->

</body>

</html>
