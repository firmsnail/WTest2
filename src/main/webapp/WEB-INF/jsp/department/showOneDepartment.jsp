<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../common/header.jsp" />
	<link href="${pageContext.request.contextPath}/resources/static/css/common/select2.min.css" rel="stylesheet">
	<link href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.2.0/css/datepicker.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/static/css/common/dataTables.bootstrap.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/static/css/common/select2.min.css" rel="stylesheet">
	<link href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.2.0/css/datepicker.min.css" rel="stylesheet">
	
	<script src="${pageContext.request.contextPath}/resources/static/js/common/select2.full.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/jquery.dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/dataTables.bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/bootstrap-datepicker.js"></script>
	<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
	<style>
		.datepicker.dropdown-menu {
			z-index: 10002 !important;
		}
	</style>
	<script>
	    $(document).ready(function() {
	    	
	    	$(".select2").select2();
	    	
	        $('#dataTables-example').DataTable({
	                responsive: false
	        });
	        
	        $('[data-toggle="tooltip"]').tooltip();
	        
	        $('#fireModal').on('shown.bs.modal', function (event) {
	        	  var button = $(event.relatedTarget) // Button that triggered the modal
	        	  var empId = button.data('empid') // Extract info from data-* attributesdata-interviewId
	        	  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
	        	  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
	        	  var modal = $(this)
	        	  //alert(curId);
	        	  //modal.find('.modal-title').text('New message to ' + recipient)
	        	  modal.find('.modal-body input#employeeId').val(empId);
	        });
	        
	        $('#fireForm').validate({
	        	rules: {
	        		reason: {
	        			required: true
	        		},
	        		expectDate: {
		    			required: true
		    		}
	        	}
	        });
	        
	        var aWeekAfterDate = new Date();
		    aWeekAfterDate.setDate(aWeekAfterDate.getDate()+7);
		    
		    $( "#expectDate" ).datepicker({
		    	startDate: aWeekAfterDate,

		        autoclose: true,
		        format: "yyyy-mm-dd"
		    });
	    });
    </script>
</head>

<body>

	<div class="modal fade" id="fireModal" tabindex="-1" role="dialog" aria-labelledby="fireModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="fireModalLabel"><spring:message code="submit" /></h4>
				</div>
				<div class="modal-body">
					<form role="form" id="fireForm" action="/team-manager/fireOneEmployee" method="POST">
						<div class="form-group hidden">
							<input type="text" class="hidden" id="employeeId" name="employeeId" value=""></input>
						</div>
	                    <div class="form-group">
	                    	<label for="expectDate" class="control-label"><spring:message code="expect-date" />: </label>
	                    	<input type="text" class="form-control" id="expectDate" name="expectDate" />
	                    </div>
	                    <div class="form-group">
	                    	<label for="reason" class="control-label"><spring:message code="reason" />:</label>
	                        <textarea class="form-control" id="reason" name="reason" ></textarea>
	                    </div>
	                    <div class="form-group">
	                    	<button type="submit" class="btn btn-success"><spring:message code="submit" /></button>
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
                    <h1 class="page-header"><spring:message code="one-department" /></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            ${department.departmentName } <spring:message code="department" />
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<!-- 
                        	<div class="addButton" <c:if test="${currentUser == null or currentUser.role.roleId != 1 }">hidden="hidden"</c:if>>
                        		<a href="/hr-manager/addDept">
									<button type="button" class="btn btn-success btn-lg">Create Department</button>
								</a>
                        	</div>
                        	-->
                        	<div class="col-lg-3 row">
                        		<div>
                        			<strong><spring:message code="department-name" /></strong>: ${department.departmentName }
                        		</div>
                        		<div>
                        			<strong><spring:message code="description" /></strong>: ${department.description }
                        		</div>
                        		<div>
                        			<strong><spring:message code="manager" /></strong>: <a href="/user/profile?userId=${department.manager.personId}">${department.manager.firstName } ${department.manager.lastName }</a>
                        		</div>
                        	</div>
                        	<br>
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
                                            <c:if test="${currentUser != null and currentUser.user.status == 2 and currentUser.user.department.departmentId == department.departmentId }">
                                            	<th><spring:message code="operation" /></th>
                                            </c:if>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="employee" items="${employees}" varStatus="status">
											<tr <c:choose><c:when test="${status.index % 2 == 0}">class="odd"</c:when><c:otherwise>class="even"</c:otherwise></c:choose>>
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
												<c:if test="${currentUser != null and currentUser.user.status == 2 and currentUser.user.department.departmentId == department.departmentId }">
		                                            <td>	
		                                            	<c:choose>
		                                            		<c:when test="${employee.role.roleId == 4 or disMap[employee.personId] == true}">
		                                            			<button type="button" class="btn btn-danger disabled"><spring:message code="fire" /></button>
		                                            		</c:when>
		                                            		<c:otherwise>
		                                            			<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#fireModal" data-empid="${employee.personId }"><spring:message code="fire" /></button>
		                                            		</c:otherwise>
		                                            	</c:choose>
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
