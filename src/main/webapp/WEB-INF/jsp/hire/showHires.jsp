<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	    });
    </script>
</head>

<body>

    <div id="wrapper">

        <jsp:include page="../common/nav.jsp" />
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Hires</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            All Hires
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<br>
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>Submit Date</th>
                                            <th>Department</th>
                                            <th>Hire Person</th>
                                            <th>Recruiter</th>
                                            <th>Details</th>
                                            <th>Status</th>
                                            <th>Operation</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="hire" items="${hires}" varStatus="status">
											<tr <c:choose><c:when test="${status.index % 2 == 0}">class="odd"</c:when><c:otherwise>class="even"</c:otherwise></c:choose>>
												<td><fmt:formatDate value="${requirement.submitDate}" pattern="yyyy-MM-dd"/></td>
												<td><a href="/department/showOneDepartment?departmentId=${hire.hireDepartment.departmentId}">${hire.hireDepartment.departmentName}</a></td>
												<td><a href="/user/showOneEmployee?personId=${hire.hirePerson.personId}">${hire.hirePerson.firstName} ${hire.hirePerson.lastName}</a></td>
												<td><a href="/user/showOneEmployee?personId=${hire.hireRecruiter.personId}">${hire.hireRecruiter.firstName} ${hire.hireRecruiter.lastName}</a></td>
												<td><a href="/hire/showOneHire?hireId=${hire.hireId }"><i class="fa fa-search fa-fw"></i> See Details</a></td>
												<td>
													<c:choose>
														<c:when test="${currentUser.user.role.roleId == 1}">		<!-- for hr manager -->
															<c:when test="${hire.status == 2}">		<!-- HIRE_HR_MANAGER_PROCESSING -->
																<span class="label label-warning">Pending</span>
															</c:when>
															<c:when test="${hire.status == 4}">		<!-- HIRE_REJECT -->
																<span class="label label-danger">Denied</span>
															</c:when>
															<c:otherwise>				<!-- Approved -->
																<span class="label label-success">Approved</span>
															</c:otherwise>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 2}">
															<c:when test="${hire.status == 1}">		<!-- HIRE_RECRUITER_PROCESSING -->
																<span class="label label-warning">Pending</span>
															</c:when>
															<c:when test="${hire.status == 2}">		<!-- REQUIREMENTS_PENDING_RECRUITE -->
																<span class="label label-warning">Pending Verify</span>
															</c:when>
															<c:when test="${hire.status == 4}">		<!-- HIRE_REJECT -->
																<span class="label label-danger">Denied</span>
															</c:when>
															<c:otherwise>				<!-- Finished -->
																<span class="label label-success">Finished</span>
															</c:otherwise>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 4}">
															<c:when test="${hire.status == 1}">		<!-- HIRE_RECRUITER_PROCESSING -->
																<span class="label label-warning">Pending</span>
															</c:when>
															<c:when test="${hire.status == 2}">		<!-- REQUIREMENTS_PENDING_RECRUITE -->
																<span class="label label-warning">Pending Verify</span>
															</c:when>
															<c:when test="${hire.status == 4}">		<!-- HIRE_REJECT -->
																<span class="label label-danger">Denied</span>
															</c:when>
															<c:otherwise>				<!-- Finished -->
																<span class="label label-success">Finished</span>
															</c:otherwise>
														</c:when>
														<c:otherwise>
															Unknown
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when test="${currentUser.user.role.roleId == 1}">		<!-- for hr manager -->
															<c:when test="${hire.status == 2}">		<!-- HIRE_HR_MANAGER_PROCESSING -->
																<a href="/hr-manager/aprroveOneHire?hireId=${hire.hireId }"><button type="button" class="btn btn-success">Approve</button></a>
																<a href="/hr-manager/rejectOneHire?hireId=${hire.hireId }"><button type="button" class="btn btn-danger">Reject</button></a>
															</c:when>
															<c:otherwise>
																<button type="button" class="btn btn-success disabled">Approve</button>
																<button type="button" class="btn btn-danger disabled">Reject</button>
															</c:otherwise>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 2}">
															<c:when test="${hire.status == 1}">		<!-- HIRE_RECRUITER_PROCESSING -->
																<button type="button" class="btn btn-success">Approve</button>
																<button type="button" class="btn btn-danger">Reject</button>
															</c:when>
															<c:otherwise>
																<button type="button" class="btn btn-success disabled">Approve</button>
																<button type="button" class="btn btn-danger disabled">Reject</button>
															</c:otherwise>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 4}">
															<c:when test="${hire.status == 1}">		<!-- REQUIREMENTS_RECRUITING -->
																<button type="button" class="btn btn-danger">Delete</button>
															</c:when>
															<c:otherwise>
																<button type="button" class="btn btn-danger disabled">Delete</button>
															</c:otherwise>
														</c:when>
														<c:otherwise>
															Unknown
														</c:otherwise>
													</c:choose>
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
