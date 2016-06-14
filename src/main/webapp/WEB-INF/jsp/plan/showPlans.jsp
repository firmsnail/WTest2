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
                    <h1 class="page-header"><spring:message code="recruiting-plans" /></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <spring:message code="all-recruiting-plans" />
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<div class="addButton" <c:if test="${currentUser == null or currentUser.role.roleId != 2 }">hidden="hidden"</c:if>>
                        		<a href="/recruiter/addPlan">
									<button type="button" class="btn btn-success "><spring:message code="make-recruiting-plan" /></button>
								</a>
								<a href="#" data-toggle="tooltip" title="Recommend recruiting plans to recruiters. It is not implemented.">
									<button type="button" class="btn btn-success disabled"><spring:message code="generate-recruiting-plan" /></button>
								</a>
                        	</div>
                        	<br>
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th><spring:message code="expect-date" /></th>
                                            <th><spring:message code="submit-date" /></th>
                                            <th><spring:message code="invalid-date" /></th>
                                            <c:if test="${currentUser != null and currentUser.user.role.roleId == 1 }">
                                            	<th><spring:message code="plan-maker" /></th>
                                            </c:if>
                                            <th><spring:message code="total-requirement" /></th>
                                            <th><spring:message code="details" /></th>
                                            <th><spring:message code="status" /></th>
                                            <th><spring:message code="operation" /></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="plan" items="${plans}" varStatus="status">
											<tr <c:choose><c:when test="${status.index % 2 == 0}">class="odd"</c:when><c:otherwise>class="even"</c:otherwise></c:choose>>
												<td><fmt:formatDate value="${plan.expectDate}" pattern="yyyy-MM-dd"/></td>
												<td><fmt:formatDate value="${plan.submitDate}" pattern="yyyy-MM-dd"/></td>
												<td><fmt:formatDate value="${plan.invalidDate}" pattern="yyyy-MM-dd"/></td>
												<c:if test="${currentUser != null and currentUser.user.role.roleId == 1 }">
													<td><a href="/user/showOneEmployee?personId=${plan.planMaker.personId}">${plan.planMaker.firstName} ${plan.planMaker.lastName}</a></td>
												</c:if>
												<td>${plan.planNum}</td>
												<td><a href="/plan/showOneRecruitingPlan?planId=${plan.planId }"><i class="fa fa-search fa-fw"></i> <spring:message code="see-details" /></a></td>
												<td>
													<c:choose>
														<c:when test="${currentUser.user.role.roleId == 1}">		<!-- for hr manager -->
															<c:choose>
																<c:when test="${plan.status == 1}">		<!-- PLAN_PENDING_VERIFY -->
																	<span class="label label-warning"><spring:message code="pending" /></span>
																</c:when>
																<c:when test="${plan.status == 5}">		<!-- PLAN_REJECT -->
																	<span class="label label-danger"><spring:message code="denied" /></span>
																</c:when>
																<c:otherwise>				<!-- Approved -->
																	<span class="label label-success"><spring:message code="approved" /></span>
																</c:otherwise>
															</c:choose>
														</c:when>
														
														<c:when test="${currentUser.user.role.roleId == 2}">
															<c:choose>
																<c:when test="${plan.status == 1}">		<!-- PLAN_PENDING_VERIFY -->
																	<span class="label label-warning"><spring:message code="pending-verify" /></span>
																</c:when>
																<c:when test="${plan.status == 2}">		<!-- PLAN_VERIFIED -->
																	<span class="label label-warning"><spring:message code="pending-recruit" /></span>
																</c:when>
																<c:when test="${plan.status == 3}">		<!-- PLAN_RECRUITING -->
																	<span class="label label-primary"><spring:message code="recruiting" /></span>
																</c:when>
																<c:when test="${plan.status == 4}">		<!-- PLAN_FINISH -->
																	<span class="label label-success"><spring:message code="finished" /></span>
																</c:when>
																<c:when test="${plan.status == 5}">		<!-- PLAN_REJECT -->
																	<span class="label label-danger"><spring:message code="denied" /></span>
																</c:when>
																<c:otherwise>				<!-- Finished -->
																	<spring:message code="unknown" />
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 5}">
															<span class="label label-primary"><spring:message code="recruiting" /></span>
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
																<c:when test="${plan.status == 1}">		<!-- PLAN_PENDING_VERIFY -->
																	<a href="/hr-manager/aprroveOnePlan?planId=${plan.planId }"><button type="button" class="btn btn-success"><spring:message code="approve" /></button></a>
																	<a href="/hr-manager/rejectOnePlan?planId=${plan.planId }"><button type="button" class="btn btn-danger"><spring:message code="reject" /></button></a>
																</c:when>
																<c:otherwise>
																	<button type="button" class="btn btn-success disabled"><spring:message code="approve" /></button>
																	<button type="button" class="btn btn-danger disabled"><spring:message code="reject" /></button>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 2}">
															<c:choose>
																<c:when test="${plan.status == 1}">		<!-- PLAN_PENDING_VERIFY -->
																	<a href="/recruiter/deleteOnePlan?planId=${plan.planId }"><button type="button" class="btn btn-danger"><spring:message code="delete" /></button></a>
																</c:when>
																<c:otherwise>
																	<button type="button" class="btn btn-danger disabled"><spring:message code="delete" /></button>
																</c:otherwise>
															</c:choose>
															<c:choose>
																<c:when test="${plan.status == 2}">		<!-- PLAN_VERIFIED -->
																	<a href="/recruiter/postOnePlan?planId=${plan.planId }"><button type="button" class="btn btn-success"><spring:message code="post" /></button></a>
																</c:when>
																<c:otherwise>
																	<button type="button" class="btn btn-success disabled"><spring:message code="post" /></button>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 5}">
															<c:choose>
																<c:when test="${isApply[plan.planId] == false and currentUser.user.status != 2}">		<!-- PLAN_VERIFIED -->
																	<a href="/short-term-employee/applyOnePlan?planId=${plan.planId }"><button type="button" class="btn btn-success"><spring:message code="apply" /></button></a>
																</c:when>
																<c:otherwise>
																	<button type="button" class="btn btn-success disabled"><spring:message code="apply" /></button>
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
