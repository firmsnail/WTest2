<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../common/header.jsp" />
	<script src="${pageContext.request.contextPath}/resources/static/js/common/jquery.dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/dataTables.bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/static/css/common/dataTables.bootstrap.css" rel="stylesheet">
	<script>
	    $(document).ready(function() {
	        $('#dataTables-example').DataTable({
	                responsive: false
	        });
	        $('[data-toggle="tooltip"]').tooltip();
	        
	        $('#empModal').on('shown.bs.modal', function (event) {
	        	//var button = $(event.relatedTarget);
	        	var pId = $(this).data('pid');
	        	var pName = $(this).data('pname');
	        	var depId = $(this).data('depid');
	        	var modal = $(this);
	        	modal.find('.modal-body input#employeeId').val(pId);
	        	modal.find('.modal-body input#employeeName').val(pName);
	        	modal.find('.modal-body select#depId').val(depId);
	        });
	        $(".table-striped").find('tr[data-target]').on('dblclick', function(){
	            $('#empModal').data('pid',$(this).data('pid'));
	             $('#empModal').data('pname',$(this).data('pname'));
	             $('#empModal').data('depid',$(this).data('depid')).modal('show');
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
					<h4 class="modal-title" id="empModalLabel">Update</h4>
				</div>
				<div class="modal-body">
					<form role="form" id="empForm" action="/hr-manager/editOneEmployee" method="POST">
						<div class="form-group">
							<input type="text" class="hidden" id="employeeId" name="employeeId" value=""></input>
						</div>
	                    <div class="form-group">
	                    	<label for="employeeName" class="control-label">Employee Name: </label>
	                    	<input type="text" class="form-control" readonly="readonly" id="employeeName" value=""></input>
	                    </div>
	                    <div class="form-group">
	                    	<label for="department" class="control-label">Department:</label>
	                    	<select class="form-control" name="departmentId" id="depId">
	                    		<c:forEach var="dept" items="${departments}">
	                    			<option value="${dept.departmentId }">${dept.departmentName }</option>
	                    		</c:forEach>
			                </select>
	                    </div>
	                    <div class="form-group">
	                    	<button type="submit" class="btn btn-success">Update</button>
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
                    <h1 class="page-header">Employees</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                	
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            All Employees
                        </div>
						
                        <div class="panel-body">
                        	<div class="addButton" <c:if test="${currentUser == null or currentUser.role.roleId != 1 }">hidden="hidden"</c:if>>
                        		<a href="/hr-manager/addUser">
									<button type="button" class="btn btn-success btn-lg">Add Employees</button>
								</a>
                        	</div>
                        	<br>
                        	
                            <div class="dataTable_wrapper">

                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>Employee Name</th>
                                            <th>Role</th>
                                            <th>Age</th>
                                            <th>Gender</th>
                                            <th>Department</th>
                                            <th>Resign Tendency</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="employee" items="${employees}" varStatus="status">
											<tr <c:choose><c:when test="${status.index % 2 == 0}">class="odd table-striped"</c:when><c:otherwise>class="even table-striped"</c:otherwise></c:choose> <c:if test="${currentUser.user.role.roleId == 1 and employee.role.roleId == 5 }">data-target="#empModal" data-pid="${employee.personId }" data-pname="${employee.firstName } ${employee.lastName }" <c:choose><c:when test="${employee.department != null}">data-depid="${employee.department.departmentId}"</c:when><c:otherwise>data-depid=""</c:otherwise></c:choose></c:if>>
												<td><a href="/user/showOneEmployee?personId=${employee.personId}">${employee.firstName} ${employee.lastName}</a></td>
												<td>${employee.role.roleName}</td>
												<td>
													<c:choose>
														<c:when test="${employee.age != null}">
															${employee.age}
														</c:when>
														<c:otherwise>
															Unknown
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${employee.gender != null and employee.gender==1}">
															Female
														</c:when>
														<c:when test="${employee.gender != null and employee.gender==2}">
															Male
														</c:when>
														<c:otherwise>
															Unknown
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${employee.department != null}">
															<a href="/department/showOneDepartment?departmentId=${employee.department.departmentId}">${employee.department.departmentName}</a>
														</c:when>
														<c:otherwise>
															None
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<a href="#" data-toggle="tooltip" title="Predict whether the employee will resign recently, which can reduce the loss of human resources. It is not implemented.">
														<span class="label label-success disabled">Resign Tendency</span>
													</a>
												</td>
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
