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
                    <h1 class="page-header">Recruiting Plans</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            All Recruiting Plans
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<div class="addButton" <c:if test="${currentUser == null or currentUser.role.roleId != 2 }">hidden="hidden"</c:if>>
                        		<a href="/recruiter/addPlan">
									<button type="button" class="btn btn-success btn-lg">Make Recruiting Plans</button>
								</a>
                        	</div>
                        	<br>
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>Expect Date</th>
                                            <th>Submit Date</th>
                                            <th>Invalid Date</th>
                                            <th>Department</th>
                                            <th>Total requirement</th>
                                            <th>Detail</th>
                                            <th>Status</th>
                                            <th>Operation</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="plan" items="${plans}" varStatus="status">
											<tr <c:choose><c:when test="${status.index % 2 == 0}">class="odd"</c:when><c:otherwise>class="even"</c:otherwise></c:choose>>
												<td><fmt:formatDate value="${plan.expectDate}" pattern="yyyy-MM-dd"/></td>
												<td><fmt:formatDate value="${plan.submitDate}" pattern="yyyy-MM-dd"/></td>
												<td><fmt:formatDate value="${plan.invalidDate}" pattern="yyyy-MM-dd"/></td>
												<td><a href="/user/showOneEmployee?personId=${plan.planMaker.personId}">${plan.planMaker.firstName} ${plan.planMaker.lastName}</a></td>
												<td>${plan.planNum}</td>
												<td><a href="/plan/showOneRecruitingPlan?planId=${plan.planId }"><i class="fa fa-search fa-fw"></i> See Details</a></td>
												<td>
													<c:choose>
														<c:when test="${currentUser.user.role.roleId == 1}">		<!-- for hr manager -->
															<c:choose>
																<c:when test="${plan.status == 1}">		<!-- PLAN_PENDING_VERIFY -->
																	<span class="label label-warning">Pending</span>
																</c:when>
																<c:when test="${plan.status == 5}">		<!-- PLAN_REJECT -->
																	<span class="label label-danger">Denied</span>
																</c:when>
																<c:otherwise>				<!-- Approved -->
																	<span class="label label-success">Approved</span>
																</c:otherwise>
															</c:choose>
														</c:when>
														
														<c:when test="${currentUser.user.role.roleId == 2}">
															<c:choose>
																<c:when test="${plan.status == 1}">		<!-- PLAN_PENDING_VERIFY -->
																	<span class="label label-warning">Pending Verify</span>
																</c:when>
																<c:when test="${plan.status == 2}">		<!-- PLAN_VERIFIED -->
																	<span class="label label-warning">Pending Recruit</span>
																</c:when>
																<c:when test="${plan.status == 3}">		<!-- PLAN_RECRUITING -->
																	<span class="label label-primary">Recruiting</span>
																</c:when>
																<c:when test="${plan.status == 4}">		<!-- PLAN_FINISH -->
																	<span class="label label-success">Finished</span>
																</c:when>
																<c:when test="${plan.status == 5}">		<!-- PLAN_REJECT -->
																	<span class="label label-danger">Denied</span>
																</c:when>
																<c:otherwise>				<!-- Finished -->
																	Unknown
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 5}">
															<span class="label label-primary">Recruiting</span>
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
																<c:when test="${plan.status == 1}">		<!-- PLAN_PENDING_VERIFY -->
																	<a href="/hr-manager/aprroveOnePlan?planId=${plan.planId }"><button type="button" class="btn btn-success">Approve</button></a>
																	<a href="/hr-manager/rejectOnePlan?planId=${plan.planId }"><button type="button" class="btn btn-danger">Reject</button></a>
																</c:when>
																<c:otherwise>
																	<button type="button" class="btn btn-success disabled">Approve</button>
																	<button type="button" class="btn btn-danger disabled">Reject</button>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 2}">
															<c:choose>
																<c:when test="${plan.status == 1}">		<!-- PLAN_PENDING_VERIFY -->
																	<a href="/recruiter/deleteOnePlan?planId=${plan.planId }"><button type="button" class="btn btn-danger">Delete</button></a>
																</c:when>
																<c:otherwise>
																	<button type="button" class="btn btn-danger disabled">Delete</button>
																</c:otherwise>
															</c:choose>
															<c:choose>
																<c:when test="${plan.status == 2}">		<!-- PLAN_VERIFIED -->
																	<a href="/recruiter/postOnePlan?planId=${plan.planId }"><button type="button" class="btn btn-danger">Post</button></a>
																</c:when>
																<c:otherwise>
																	<button type="button" class="btn btn-danger disabled">Post</button>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 5}">
															<button type="button" class="btn btn-primary">Apply</button>
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
