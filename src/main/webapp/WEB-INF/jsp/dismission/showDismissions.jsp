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
                    <h1 class="page-header">Dismissions</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            All Dismissions
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<div class="addButton" <c:if test="${currentUser == null or currentUser.role.roleId != 5 }">hidden="hidden"</c:if>>
                        		<a href="/short-term-employee/addDismission">
									<button type="button" class="btn btn-success btn-lg">Apply Dismission</button>
								</a>
                        	</div>
                        	<div class="addButton" <c:if test="${currentUser == null or currentUser.role.roleId != 4 }">hidden="hidden"</c:if>>
                        		<a href="/team-manager/addDismission">
									<button type="button" class="btn btn-success btn-lg">Add Dismission</button>
								</a>
                        	</div>
                        	<br>
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                        	<th>Expect Date</th>
                                            <th>Submit Date</th>
                                            <th>Department</th>
                                            <th>Dismission Person</th>
                                            <!-- <th>Details</th>  -->
                                            <th>Status</th>
                                            <th>Operation</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="dismission" items="${dismissions}" varStatus="status">
											<tr <c:choose><c:when test="${status.index % 2 == 0}">class="odd"</c:when><c:otherwise>class="even"</c:otherwise></c:choose>>
												<td><fmt:formatDate value="${dismission.expectDate}" pattern="yyyy-MM-dd"/></td>
												<td><fmt:formatDate value="${dismission.submitDate}" pattern="yyyy-MM-dd"/></td>
												<td><a href="/department/showOneDepartment?departmentId=${dismission.dismissionDepartment.departmentId}">${dismission.dismissionDepartment.departmentName}</a></td>
												<td><a href="/user/showOneEmployee?personId=${dismission.dismissionPerson.personId}">${dismission.dismissionPerson.firstName} ${dismission.dismissionPerson.lastName}</a></td>
												<!-- <td><a href="/hire/showOneDismission?dismissionId=${dismission.dismissionId }"><i class="fa fa-search fa-fw"></i> See Details</a></td> -->
												<td>
													<c:choose>
														<c:when test="${currentUser.user.role.roleId == 1}">		<!-- for hr manager -->
															<c:choose>
																<c:when test="${dismission.status == 2}">		<!-- DISMISSION_HR_MANAGER_PROCESSING -->
																	<span class="label label-warning">Pending</span>
																</c:when>
																<c:when test="${dismission.status == 6}">		<!-- DISMISSION_REJECT -->
																	<span class="label label-danger">Denied</span>
																</c:when>
																<c:otherwise>				<!-- Approved -->
																	<span class="label label-success">Approved</span>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 3}">
															<c:choose>
																<c:when test="${dismission.status == 3}">		<!-- DISMISSION_CB_SPECIALIST_PROCESSING -->
																	<span class="label label-warning">Pending</span>
																</c:when>
																<c:otherwise>				<!-- Finished -->
																	<span class="label label-success">Finished</span>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 4}">
															<c:choose>
																<c:when test="${dismission.status == 1}">		<!-- DISMISSION_TEAM_MANAGER_PROCESSING -->
																	<span class="label label-warning">Pending</span>
																</c:when>
																<c:when test="${dismission.status == 5}">		<!-- DISMISSION_TEAM_MANAGER_REJECT -->
																	<span class="label label-danger">Denied</span>
																</c:when>
																<c:otherwise>				<!-- Finished -->
																	<span class="label label-success">Approved</span>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 5}">
															<c:choose>
																<c:when test="${dismission.status == 4}">		<!-- DISMISSION_FINISH -->
																	<span class="label label-success">Approved</span>
																</c:when>
																<c:when test="${dismission.status == 5 or dismission.status == 6}">		<!-- DISMISSION_REJECT -->
																	<span class="label label-danger">Denied</span>
																</c:when>
																<c:otherwise>
																	<span class="label label-warning">Pending</span>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															Unknown
														</c:otherwise>
													</c:choose>
												</td>
												<td>
												
													<c:choose>
														<c:when test="${currentUser.user.role.roleId == 1}">		<!-- for hr manager -->
															<c:choose>
																<c:when test="${dismission.status == 2}">		<!-- DISMISSION_HR_MANAGER_PROCESSING -->
																	<a href="/hr-manager/aprroveOneDismission?dismissionId=${dismission.dismissionId }"><button type="button" class="btn btn-success">Approve</button></a>
																	<a href="/hr-manager/rejectOneDismission?dismissionId=${dismission.dismissionId }"><button type="button" class="btn btn-danger">Reject</button></a>
																</c:when>
																<c:otherwise>
																	<button type="button" class="btn btn-success disabled">Approve</button>
																	<button type="button" class="btn btn-danger disabled">Reject</button>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 3}">
															<c:choose>
																<c:when test="${dismission.status == 3}">		<!-- DISMISSION_CB_SPECIALIST_PROCESSING -->
																	<a href="/cb-specialist/processOneDismission?dismissionId=${dismission.dismissionId }"><button type="button" class="btn btn-success">Process</button></a>
																</c:when>
																<c:otherwise>
																	<button type="button" class="btn btn-success disabled">Process</button>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 4}">
															<c:choose>
																<c:when test="${dismission.status == 1}">		<!-- DISMISSION_TEAM_MANAGER_PROCESSING -->
																	<a href="/team-manager/aprroveOneDismission?dismissionId=${dismission.dismissionId }"><button type="button" class="btn btn-success">Approve</button></a>
																	<a href="/team-manager/rejectOneDismission?dismissionId=${dismission.dismissionId }"><button type="button" class="btn btn-danger">Reject</button></a>
																</c:when>
																<c:otherwise>
																	<button type="button" class="btn btn-success disabled">Approve</button>
																	<button type="button" class="btn btn-danger disabled">Reject</button>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 5}">
															<c:choose>
																<c:when test="${dismission.status == 1}">		<!-- DISMISSION_TEAM_MANAGER_PROCESSING -->
																	<a href="/short-term-employee/deleteOneDismission?dismissionId=${dismission.dismissionId }"><button type="button" class="btn btn-danger">Delete</button></a>
																</c:when>
																<c:otherwise>
																	<button type="button" class="btn btn-danger disabled">Delete</button>
																</c:otherwise>
															</c:choose>
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
