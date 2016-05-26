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
	    });
    </script>
</head>

<body>

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
											<tr <c:choose><c:when test="${status.index % 2 == 0}">class="odd"</c:when><c:otherwise>class="even"</c:otherwise></c:choose>>
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
													<a href="#" data-toggle="tooltip" title="Predict whether the employee will resign recently. It is not implemented.">
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
