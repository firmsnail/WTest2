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
	<script src="${pageContext.request.contextPath}/resources/static/js/common/jquery.dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/dataTables.bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/static/css/common/dataTables.bootstrap.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/static/css/common/select2.min.css" rel="stylesheet">
	<link href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.2.0/css/datepicker.min.css" rel="stylesheet">
	<!-- <link href="${pageContext.request.contextPath}/resources/static/css/common/datepicker3.css" rel="stylesheet">-->

	<script src="${pageContext.request.contextPath}/resources/static/js/common/select2.full.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/bootstrap-datepicker.js"></script>
	
	
	<script>
	    $(document).ready(function() {
	    	//Initialize Select2 Elements
		    $(".select2").select2();
	    	
	        $('#dataTables-example').DataTable({
	                responsive: false
	        });
	        $('[data-toggle="tooltip"]').tooltip();
	        
	        $( "#from" ).datepicker({
		        autoclose: true,
		        format: "yyyy-mm-dd"
		    }).on('changeDate', function(ev){
		    	$("#to").datepicker("setStartDate", ev.date);
		    });
	        
	        $("#to").datepicker({
	        	autoclose: true,
	        	format: "yyyy-mm-dd"
	        }).on('changeDate', function(ev){
	        	$("#from").datepicker("setEndDate", ev.date);
	        });
	        
	        if ($("#from").val() != "") {
	        	$("#to").datepicker("setStartDate", $("#from").datepicker('getDate'));
	        }
	        if ($("#to").val() != "") {
	        	$("#from").datepicker("setEndDate", $("#to").datepicker('getDate'));
	        }
	    });
    </script>
</head>

<body>

    <div id="wrapper">

        <jsp:include page="../common/nav.jsp" />
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><spring:message code="attendances" /></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <spring:message code="all-attendances" />
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<c:if test="${currentUser.user.role.roleId == 3 }">
	                        	<div>
	                        		<a href="#" data-toggle="tooltip" title="Import attendance records from paper. It is not implemented.">
										<button type="button" class="btn btn-success disabled"><spring:message code="import-attendance-records" /></button>
									</a>
	                        	</div>
	                        	<br>
	                        </c:if>
                        	<form name="attendancesForm" method="GET"  <c:choose><c:when test="${currentUser.user.role.roleId == 5 }">action="/attendance/showAttendancesByPerson"</c:when><c:otherwise>action="/attendance/showAttendances"</c:otherwise></c:choose>  >
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
												<select class="form-control select2" name="personId">
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
                                        	<th><spring:message code="attendance-date" /></th>
                                        	<th><spring:message code="attendance-time" /></th>
                                            <th><spring:message code="leave-time" /></th>
                                            <th><spring:message code="attendance-department" /></th>
                                            <th><spring:message code="attendance-person" /></th>
                                            <th><spring:message code="attendance.type" /></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="attendance" items="${attendances}" varStatus="status">
											<tr <c:choose><c:when test="${status.index % 2 == 0}">class="odd"</c:when><c:otherwise>class="even"</c:otherwise></c:choose>>
												<td><fmt:formatDate value="${attendance.attendanceDate}" pattern="yyyy-MM-dd"/></td>
												<td><fmt:formatDate value="${attendance.attendanceTime}" pattern="yyyy-MM-dd hh:mm"/></td>
												<td><fmt:formatDate value="${attendance.leaveTime}" pattern="yyyy-MM-dd hh:mm"/></td>
												<td><a href="/department/showOneDepartment?departmentId=${attendance.attendanceDepartment.departmentId}">${attendance.attendanceDepartment.departmentName}</a></td>
												<td><a href="/user/showOneEmployee?personId=${attendance.attendancePerson.personId}">${attendance.attendancePerson.firstName} ${attendance.attendancePerson.lastName}</a></td>
												<td>
													<c:choose>
														<c:when test="${attendance.type == 1}">
															<span class="label label-success"><spring:message code="attendance.normal" /></span>
														</c:when>
														<c:when test="${attendance.type == 2 }">
															<span class="label label-warning"><spring:message code="attendance.attend-late" /></span>
														</c:when>
														<c:when test="${attendance.type == 3 }">
															<span class="label label-warning"><spring:message code="attendance.leave-early" /></span>
														</c:when>
														<c:when test="${attendance.type == 4 }">
															<span class="label label-warning"><spring:message code="attendance.attendance-not-record" /></span>
														</c:when>
														<c:otherwise>
															<span class="label label-warning"><spring:message code="attendance.leave-not-record" /></span>
														</c:otherwise>
													</c:choose>
												</td>
												
												<!-- 
													private Integer type;	//1: Normal, 2: AttendanceLate, 3: LeaveEarly, 4: AttendanceNotRecord, 5: LeaveNotRecord
													final static public Integer ATTENDANCE_NORMAL = 1;
													final static public Integer ATTENDANCE_ATTEND_LATE = 2;
													final static public Integer ATTENDANCE_LEAVE_EARLY = 3;
													final static public Integer ATTENDANCE_ATTEND_NOT_RECORD = 4;
													final static public Integer ATTENDANCE_LEAVE_NOT_RECORD = 5;
												 -->
												
												<!-- <td><a href="/hire/showOneDismission?dismissionId=${dismission.dismissionId }"><i class="fa fa-search fa-fw"></i> See Details</a></td>-->
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
