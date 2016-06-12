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
                    <h1 class="page-header"><spring:message code="dismissions" /></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <spring:message code="all-dismissions" />
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<div class="addButton" <c:if test="${currentUser == null or currentUser.role.roleId != 5 }">hidden="hidden"</c:if>>
                        		<a href="/short-term-employee/addDismission">
									<button type="button" class="btn btn-success btn-lg"><spring:message code="apply-dismission" /></button>
								</a>
                        	</div>
                        	<div class="addButton" <c:if test="${currentUser == null or currentUser.role.roleId != 4 }">hidden="hidden"</c:if>>
                        		<a href="/team-manager/addDismission">
									<button type="button" class="btn btn-success btn-lg"><spring:message code="add-dismission" /></button>
								</a>
                        	</div>
                        	<br>
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                        	<th><spring:message code="expect-date" /></th>
                                            <th><spring:message code="submit-date" /></th>
                                            <th><spring:message code="department" /></th>
                                            <th><spring:message code="dismission-person" /></th>
                                            <!-- <th>Details</th>  -->
                                            <th><spring:message code="status" /></th>
                                            <th><spring:message code="operation" /></th>
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
																	<span class="label label-warning"><spring:message code="pending" /></span>
																</c:when>
																<c:when test="${dismission.status == 6}">		<!-- DISMISSION_REJECT -->
																	<span class="label label-danger"><spring:message code="denied" /></span>
																</c:when>
																<c:otherwise>				<!-- Approved -->
																	<span class="label label-success"><spring:message code="approved" /></span>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 3}">
															<c:choose>
																<c:when test="${dismission.status == 3}">		<!-- DISMISSION_CB_SPECIALIST_PROCESSING -->
																	<span class="label label-warning"><spring:message code="pending" /></span>
																</c:when>
																<c:otherwise>				<!-- Finished -->
																	<span class="label label-success"><spring:message code="finished" /></span>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 4}">
															<c:choose>
																<c:when test="${dismission.status == 1}">		<!-- DISMISSION_TEAM_MANAGER_PROCESSING -->
																	<span class="label label-warning"><spring:message code="pending" /></span>
																</c:when>
																<c:when test="${dismission.status == 5}">		<!-- DISMISSION_TEAM_MANAGER_REJECT -->
																	<span class="label label-danger"><spring:message code="denied" /></span>
																</c:when>
																<c:otherwise>				<!-- Finished -->
																	<span class="label label-success"><spring:message code="approved" /></span>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 5}">
															<c:choose>
																<c:when test="${dismission.status == 4}">		<!-- DISMISSION_FINISH -->
																	<span class="label label-success"><spring:message code="approved" /></span>
																</c:when>
																<c:when test="${dismission.status == 5 or dismission.status == 6}">		<!-- DISMISSION_REJECT -->
																	<span class="label label-danger"><spring:message code="denied" /></span>
																</c:when>
																<c:otherwise>
																	<span class="label label-warning"><spring:message code="pending" /></span>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<spring:message code="unknown" />
														</c:otherwise>
													</c:choose>
												</td>
												<td>
												
													<c:choose>
														<c:when test="${currentUser.user.role.roleId == 1}">		<!-- for hr manager -->
															<c:choose>
																<c:when test="${dismission.status == 2}">		<!-- DISMISSION_HR_MANAGER_PROCESSING -->
																	<a href="/hr-manager/aprroveOneDismission?dismissionId=${dismission.dismissionId }"><button type="button" class="btn btn-success"><spring:message code="approve" /></button></a>
																	<a href="/hr-manager/rejectOneDismission?dismissionId=${dismission.dismissionId }"><button type="button" class="btn btn-danger"><spring:message code="reject" /></button></a>
																</c:when>
																<c:otherwise>
																	<button type="button" class="btn btn-success disabled"><spring:message code="approve" /></button>
																	<button type="button" class="btn btn-danger disabled"><spring:message code="reject" /></button>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 3}">
															<c:choose>
																<c:when test="${dismission.status == 3}">		<!-- DISMISSION_CB_SPECIALIST_PROCESSING -->
																	<a href="/cb-specialist/processOneDismission?dismissionId=${dismission.dismissionId }"><button type="button" class="btn btn-success"><spring:message code="process" /></button></a>
																</c:when>
																<c:otherwise>
																	<button type="button" class="btn btn-success disabled"><spring:message code="process" /></button>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 4}">
															<c:choose>
																<c:when test="${dismission.status == 1}">		<!-- DISMISSION_TEAM_MANAGER_PROCESSING -->
																	<a href="/team-manager/aprroveOneDismission?dismissionId=${dismission.dismissionId }"><button type="button" class="btn btn-success"><spring:message code="approve" /></button></a>
																	<a href="/team-manager/rejectOneDismission?dismissionId=${dismission.dismissionId }"><button type="button" class="btn btn-danger"><spring:message code="reject" /></button></a>
																</c:when>
																<c:otherwise>
																	<button type="button" class="btn btn-success disabled"><spring:message code="approve" /></button>
																	<button type="button" class="btn btn-danger disabled"><spring:message code="reject" /></button>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 5}">
															<c:choose>
																<c:when test="${dismission.status == 1}">		<!-- DISMISSION_TEAM_MANAGER_PROCESSING -->
																	<a href="/short-term-employee/deleteOneDismission?dismissionId=${dismission.dismissionId }"><button type="button" class="btn btn-danger"><spring:message code="delete" /></button></a>
																</c:when>
																<c:otherwise>
																	<button type="button" class="btn btn-danger disabled"><spring:message code="delete" /></button>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<spring:message code="unknown" />
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
