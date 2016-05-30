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
	        
	        $('#deptModal').on('shown.bs.modal', function (event) {
	        	//var button = $(event.relatedTarget);
	        	var deptId = $(this).data('deptid');
	        	//alert(deptId);
	        	var deptName = $(this).data('deptname');
	        	var managerId = $(this).data('managerid');
	        	var managerName = $(this).data('managername');
	        	var modal = $(this);
	        	modal.find('.modal-body input#deptId').val(deptId);
	        	modal.find('.modal-body input#deptName').val(deptName);
	        	if (managerId != "") {
	        		var len = modal.find(".modal-body select#managerId option[value="+managerId+"]").length;
	        		if (len <= 0) {
	        			modal.find('.modal-body select#managerId')[0].options.add(new Option(managerName, managerId));
	        		}
	        	}
	        	modal.find('.modal-body select#managerId').val(managerId);
	        });
	        $(".table-striped").find('tr[data-target]').on('dblclick', function(){
	        	//alert('here');
	        	//alert($(this).data('deptid'));
	        	//alert($(this).data('deptname'));
	        	//alert($(this).data('managerid'));
	        	//alert($(this).data('managername'));
	            $('#deptModal').data('deptid',$(this).data('deptid'));
	             $('#deptModal').data('deptname',$(this).data('deptname'));
	             $('#deptModal').data('managerid',$(this).data('managerid'));
	             $('#deptModal').data('managername',$(this).data('managername')).modal('show');
	        });
	        
	    });
    </script>
</head>

<body>

	<div class="modal fade" id="deptModal" tabindex="-1" role="dialog" aria-labelledby="deptModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="deptModalLabel">Update</h4>
				</div>
				<div class="modal-body">
					<form role="form" id="deptForm" action="/hr-manager/editOneDepartment" method="POST">
						<div class="form-group">
							<input type="text" class="hidden" id="deptId" name="departmentId" value=""></input>
						</div>
	                    <div class="form-group">
	                    	<label for="deptName" class="control-label">Department Name: </label>
	                    	<input type="text" class="form-control" readonly="readonly" id="deptName" value=""></input>
	                    </div>
	                    <div class="form-group">
	                    	<label for="manager" class="control-label">Manager:</label>
	                    	<select class="form-control" name="managerId" id="managerId">
	                    		<c:forEach var="manager" items="${managers}">
	                    			<option value="${manager.personId }">${manager.firstName } ${manager.lastName }</option>
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
                    <h1 class="page-header">Departments</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            All Departments
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<div class="addButton" <c:if test="${currentUser == null or currentUser.role.roleId != 1 }">hidden="hidden"</c:if>>
                        		<a href="/hr-manager/addDept">
									<button type="button" class="btn btn-success btn-lg">Create Department</button>
								</a>
                        	</div>
                        	<br>
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>Department Name</th>
                                            <th>Description</th>
                                            <th>Manager</th>
                                            <th>Total Employees</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="department" items="${departments}" varStatus="status">
											<tr <c:choose><c:when test="${status.index % 2 == 0}">class="odd table-striped"</c:when><c:otherwise>class="even table-striped"</c:otherwise></c:choose> <c:if test="${currentUser.user.role.roleId == 1  and (department.manager == null or (department.manager != null and department.manager.personId != currentUser.user.personId))}">data-target="#deptModal" data-deptid="${department.departmentId }" data-deptname="${department.departmentName }" <c:choose><c:when test="${department.manager != null }">data-managerid="${department.manager.personId }" data-managername="${department.manager.firstName } ${department.manager.lastName }"</c:when><c:otherwise>data-managerid="" data-managername=""</c:otherwise></c:choose></c:if> >
												<td><a href="/department/showOneDepartment?departmentId=${department.departmentId}">${department.departmentName }</a></td>
												<td>${department.description }</td>
												<td>
													<c:choose>
														<c:when test="${department.manager != null}">
															<a href="/user/showOneEmployee?personId=${department.manager.personId}">${department.manager.firstName} ${department.manager.lastName}</a>
														</c:when>
														<c:otherwise>
															none
														</c:otherwise>
													</c:choose>
												</td>
												<td>${fn:length(department.employees)}</td>
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
