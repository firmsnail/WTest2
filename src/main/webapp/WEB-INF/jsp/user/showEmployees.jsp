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
	<script src="${pageContext.request.contextPath}/resources/static/js/common/select2.full.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/bootstrap-datepicker.js"></script>
	<link href="${pageContext.request.contextPath}/resources/static/css/common/dataTables.bootstrap.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/static/css/common/select2.min.css" rel="stylesheet">
	<link href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.2.0/css/datepicker.min.css" rel="stylesheet"   media="screen">
	<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
	
	<style>
		.datepicker.dropdown-menu {
			z-index: 10002 !important;
		}
	</style>
	<script>
	    $(document).ready(function() {
	    	//$(".select2").select2();
	    	
	        $('#dataTables-example').DataTable({
	                responsive: false
	        });
	        $('[data-toggle="tooltip"]').tooltip();
	        
	        $('#empModal').on('shown.bs.modal', function (event) {
	        	
	        	var pId, pName, depId, salary, endDate;
	        	if (typeof(event.relatedTarget) == "undefined") {
	        		pId = $(this).data('pid');
	        		pName = $(this).data('pname');
		        	depId = $(this).data('depid');
		        	salary = $(this).data('salary');
		        	endDate = $(this).data('edate');
	        	} else {
	        		var target = $(event.relatedTarget);
	        		pId = target.data('pid');
	        		pName = target.data('pname');
	        		depId = target.data('depid');
	        		salary = target.data('salary');
	        		endDate = target.data('edate');
	        	}
	        	var modal = $(this);
	        	modal.find('.modal-body input#employeeId').val(pId);
	        	modal.find('.modal-body input#employeeName').val(pName);
	        	modal.find('.modal-body select#depId').val(depId);
	        	modal.find('.modal-body input#salary').val(salary);
	        	modal.find('.modal-body input#strEndDate').val(endDate);
	        	//alert(modal.find('.modal-body input#strEndDate').val());
	        	//modal.find('.modal-body input#strEndDate').attr("value", endDate);
	        });
	        
	        $(".table-striped").find('tr[data-target]').on('dblclick', function(){
	            $('#empModal').data('pid',$(this).data('pid'));
	            $('#empModal').data('pname',$(this).data('pname'));
	            $('#empModal').data('depid',$(this).data('depid'));//.modal('show');
	            $('#empModal').data('salary', $(this).data('salary'));
	            $('#empModal').data('edate', $(this).data('edate')).modal('show');
	        });
	        $('#empForm').validate({
	        	rules: {
	        		salary: {
	        			required: true,
	        			min: 1,
	        			max: 1000
	        		},
		    		strEndDate: {
		    			required: true
		    		}
	        	}
	        });
	        
	        $("#strEndDate").datepicker({
		    	startDate: new Date(),
		        //defaultDate: "+1w",
		        //changeMonth: true,
		        //numberOfMonths: 1,
		        
		        //todayHighlight: true,
		        autoclose: true,
		        format: "yyyy-mm-dd"
		    }).on('show', function(e) {
		    	var cur = $(this).val();
		    	$("#strEndDate").datepicker('update', cur);
		    });
	        
	    });
    </script>
</head>

<body>
	<div class="modal fade" id="empModal" tabindex="-1" role="dialog" aria-labelledby="empModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="empModalLabel"><spring:message code="update" /></h4>
				</div>
				<div class="modal-body">
					<form role="form" id="empForm" action="/hr-manager/editOneEmployee" method="POST">
						<div class="form-group">
							<input type="text" class="hidden" id="employeeId" name="employeeId" value=""></input>
						</div>
	                    <div class="form-group">
	                    	<label for="employeeName" class="control-label"><spring:message code="employee-name" />: </label>
	                    	<input type="text" class="form-control" readonly="readonly" id="employeeName" value=""></input>
	                    </div>
	                    <div class="form-group">
	                    	<label for="department" class="control-label"><spring:message code="department" />:</label>
	                    	<select class="form-control" name="departmentId" id="depId">
	                    		<c:forEach var="dept" items="${departments}">
	                    			<option value="${dept.departmentId }">${dept.departmentName }</option>
	                    		</c:forEach>
			                </select>
	                    </div>
	                    <div class="form-group">
	                    	<label for="salary" class="control-label"><spring:message code="salary" />:</label>
	                        <input type="text" class="form-control" id="salary" name="salary" />
	                    </div>
	                    <div class="form-group">
	                    	<label for="strEndDate" class="control-label"><spring:message code="end-date" />:</label>
	                    	<input type="text" class="form-control" id="strEndDate" name="strEndDate" />
	                    </div>
	                    <div class="form-group">
	                    	<button type="submit" class="btn btn-success"><spring:message code="update" /></button>
	                    </div>
	                    <br />
					    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	                </form>
				</div>
				<div class="modal-footer">
					<!--
					<button type="button" class="btn btn-success" data-toggle="modal" data-target="interviewModal" data-whatever="@mdo">Schedule</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					-->
				</div>
			</div>
		</div>
	</div>
	
    <div id="wrapper">

        <jsp:include page="../common/nav.jsp" />
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><spring:message code="employees" /></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                	
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <spring:message code="all-employees" />
                        </div>
						
                        <div class="panel-body">
                        	<div class="addButton" <c:if test="${currentUser == null or currentUser.role.roleId != 1 }">hidden="hidden"</c:if>>
                        		<a href="/hr-manager/addUser">
									<button type="button" class="btn btn-success btn-lg"><spring:message code="add-employee" /></button>
								</a>
                        	</div>
                        	<br>
                        	
                            <div class="dataTable_wrapper">

                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th><spring:message code="employee-name" /></th>
                                            <th><spring:message code="role" /></th>
                                            <th><spring:message code="age" /></th>
                                            <th><spring:message code="gender" /></th>
                                            <th><spring:message code="department" /></th>
                                            <th><spring:message code="resign-tendency" /></th>
                                            <c:if test="${currentUser.user.role.roleId == 1}">
                                            	<th><spring:message code="operation" /></th>
                                            </c:if>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="employee" items="${employees}" varStatus="status">
											<tr <c:choose><c:when test="${status.index % 2 == 0}">class="odd table-striped"</c:when><c:otherwise>class="even table-striped"</c:otherwise></c:choose> <c:if test="${currentUser.user.role.roleId == 1 and employee.role.roleId == 5 }">data-target="#empModal" data-pid="${employee.personId }" data-pname="${employee.firstName } ${employee.lastName }" <c:choose><c:when test="${employee.department != null}">data-depid="${employee.department.departmentId}" data-salary="${employee.salary }" data-edate="${employee.endDate}"</c:when><c:otherwise>data-depid="" data-salary="" data-edate=""</c:otherwise></c:choose></c:if>>
												<td><a href="/user/showOneEmployee?personId=${employee.personId}">${employee.firstName} ${employee.lastName}</a></td>
												<td>${employee.role.roleName}</td>
												<td>
													<c:choose>
														<c:when test="${employee.age != null}">
															${employee.age}
														</c:when>
														<c:otherwise>
															<spring:message code="unknown" />
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${employee.gender != null and employee.gender==1}">
															<spring:message code="female" />
														</c:when>
														<c:when test="${employee.gender != null and employee.gender==2}">
															<spring:message code="male" />
														</c:when>
														<c:otherwise>
															<spring:message code="unknown" />
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${employee.department != null}">
															<a href="/department/showOneDepartment?departmentId=${employee.department.departmentId}">${employee.department.departmentName}</a>
														</c:when>
														<c:otherwise>
															<spring:message code="none" />
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<a href="#" data-toggle="tooltip" title="Predict whether the employee will resign recently, which can reduce the loss of human resources. It is not implemented.">
														<span class="label label-success disabled"><spring:message code="resign-tendency" /></span>
													</a>
												</td>
												<c:if test="${currentUser.user.role.roleId == 1}">
	                                            	<td>
	                                            		<c:if test="${employee.role.roleId == 5 }">
	                                            			<i class="fa fa-edit btn-danger" data-toggle="modal" data-target="#empModal" data-pid="${employee.personId }" data-pname="${employee.firstName } ${employee.lastName }" <c:choose><c:when test="${employee.department != null}">data-depid="${employee.department.departmentId}" data-salary="${employee.salary }" data-edate="${employee.endDate}"</c:when><c:otherwise>data-depid="" data-salary="" data-edate=""</c:otherwise></c:choose>></i>
	                                            		</c:if>
	                                            	</td>
	                                            </c:if>
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
