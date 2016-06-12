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
                	<c:choose>
						<c:when test="${currentUser.user.role.roleId == 5}">
							<h1 class="page-header"><spring:message code="applications" /></h1>
						</c:when>
						<c:otherwise>
							<h1 class="page-header"><spring:message code="applicants" /></h1>
						</c:otherwise>
					</c:choose>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                        	<c:choose>
								<c:when test="${currentUser.user.role.roleId == 5}">
									<spring:message code="all-applications" />
								</c:when>
								<c:otherwise>
									<spring:message code="all-applicants" />
								</c:otherwise>
							</c:choose>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<br>
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th><spring:message code="apply-date" /></th>
                                            <c:if test="${currentUser.user.role.roleId != 5 }">
                                            	<th><spring:message code="applicant" /></th>
                                            </c:if>
                                            <th><spring:message code="recruiting-plan" /></th>
                                            <th><spring:message code="status" /></th>
                                            <c:if test="${currentUser.user.role.roleId != 5 }">
                                            	<th><spring:message code="operation" /></th>
                                            </c:if>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="applicant" items="${applicants}" varStatus="status">
											<tr <c:choose><c:when test="${status.index % 2 == 0}">class="odd"</c:when><c:otherwise>class="even"</c:otherwise></c:choose>>
												<td><fmt:formatDate value="${applicant.applyDate}" pattern="yyyy-MM-dd"/></td>
												<c:if test="${currentUser.user.role.roleId != 5 }">
													<td><a href="/user/showOneEmployee?personId=${applicant.applicant.personId}">${applicant.applicant.firstName} ${applicant.applicant.lastName}</a></td>
												</c:if>
												<td><a href="/plan/showOneRecruitingPlan?planId=${applicant.planForApplicant.planId }"><i class="fa fa-search fa-fw"></i> <spring:message code="plan-details" /></a></td>
												<td>
													<c:choose>
														<c:when test="${currentUser.user.role.roleId == 2 or currentUser.user.role.roleId == 4}">
															<c:choose>
																<c:when test="${applicant.status == 1}">		<!-- APPLY_PENDING_FILTER -->
																	<span class="label label-warning"><spring:message code="pending-filter" /></span>
																</c:when>
																<c:when test="${applicant.status == 2}">		<!-- APPLY_PASS_FILTER -->
																	<span class="label label-success"><spring:message code="filter-passed" /></span>
																</c:when>
																<c:otherwise>
																	<spring:message code="unknown" />
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 5}">
															<c:choose>
																<c:when test="${applicant.status == 1}">		<!-- APPLY_PENDING_FILTER -->
																	<span class="label label-warning"><spring:message code="pending-filter" /></span>
																</c:when>
																<c:when test="${applicant.status == 2}">		<!-- APPLY_PASS_FILTER -->
																	<span class="label label-primary"><spring:message code="filter-passed" /></span>
																</c:when>
																<c:when test="${applicant.status == 4}">		<!-- APPLY_CHOOSED -->
																	<span class="label label-success"><spring:message code="choosed" /></span>
																</c:when>
																<c:otherwise>
																	<span class="label label-danger"><spring:message code="failed" /></span>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<spring:message code="unknown" />
														</c:otherwise>
													</c:choose>
												</td>
												<c:if test="${currentUser.user.role.roleId != 5 }">
													<td>
														<c:choose>
															<c:when test="${currentUser.user.role.roleId == 2}">
																<c:choose>
																	<c:when test="${applicant.status == 1}">		<!-- APPLY_PENDING_FILTER -->
																		<a href="/recruiter/passOneApplicant?applicantId=${applicant.applicantId }"><button type="button" class="btn btn-success"><spring:message code="pass" /></button></a>
																		<a href="/recruiter/failOneApplicant?applicantId=${applicant.applicantId }"><button type="button" class="btn btn-danger"><spring:message code="fail" /></button></a>
																	</c:when>
																	<c:otherwise>
																		<button type="button" class="btn btn-success disabled"><spring:message code="pass" /></button>
																		<button type="button" class="btn btn-danger disabled"><spring:message code="fail" /></button>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:when test="${currentUser.user.role.roleId == 4}">
																<a href="/team-manager/chooseOneApplicant?applicantId=${applicant.applicantId }"><button type="button" class="btn btn-success"><spring:message code="choose" /></button></a>
															</c:when>
															<c:otherwise>
																<spring:message code="unknown" />
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
