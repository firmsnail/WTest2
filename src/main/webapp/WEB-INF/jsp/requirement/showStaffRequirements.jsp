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
	    function delRequirement(tokName, tokValue, requirementId) {
	        var url = "/team-manager/delRequirement?"+tokName+"="+tokValue;
	        var params = {requirementId:requirementId};
	        $.post(url, params, function (data) {
	        	switch(data){
	    			case 'success':
	    				window.location.reload(true); 
	    			break;
	    		}
	        });
	    }
    </script>
</head>

<body>

    <div id="wrapper">

        <jsp:include page="../common/nav.jsp" />
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><spring:message code="staffing-requirements" /></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <spring:message code="all-staffing-requirements" />
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<div class="addButton" <c:if test="${currentUser == null or currentUser.role.roleId != 4 }">hidden="hidden"</c:if>>
                        		<a href="/team-manager/addRequirement">
									<button type="button" class="btn btn-success btn-lg"><spring:message code="create-staffing-requirements" /></button>
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
                                            <th><spring:message code="total-requirement" /></th>
                                            <th><spring:message code="details" /></th>
                                            <th><spring:message code="status" /></th>
                                            <th><spring:message code="operation" /></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="requirement" items="${requirements}" varStatus="status">
											<tr <c:choose><c:when test="${status.index % 2 == 0}">class="odd"</c:when><c:otherwise>class="even"</c:otherwise></c:choose>>
												<td><fmt:formatDate value="${requirement.expectDate}" pattern="yyyy-MM-dd"/></td>
												<td><fmt:formatDate value="${requirement.submitDate}" pattern="yyyy-MM-dd"/></td>
												<td><a href="/department/showOneDepartment?departmentId=${requirement.stfrqDepartment.departmentId}">${requirement.stfrqDepartment.departmentName}</a></td>
												<td>${requirement.requireNum}</td>
												<td><a href="/requirement/showOneStaffRequirement?requirementId=${requirement.staffRequirementId }"><i class="fa fa-search fa-fw"></i> <spring:message code="see-details" /></a></td>
												<td>
													<c:choose>
														<c:when test="${currentUser.user.role.roleId == 1}">		<!-- for hr manager -->
															<c:choose>
																<c:when test="${requirement.status == 1}">		<!-- REQUIREMENTS_HR_MANAGER_PROCESSING -->
																	<span class="label label-warning"><spring:message code="pending" /></span>
																</c:when>
																<c:when test="${requirement.status == 6}">		<!-- REQUIREMENTS_REJECT -->
																	<span class="label label-danger"><spring:message code="denied" /></span>
																</c:when>
																<c:otherwise>				<!-- Approved -->
																	<span class="label label-success"><spring:message code="approved" /></span>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 2}">
															<c:choose>
																<c:when test="${requirement.status == 2}">		<!-- REQUIREMENTS_RECRUITER_PROCESSING -->
																	<span class="label label-warning"><spring:message code="pending" /></span>
																</c:when>
																<c:when test="${requirement.status == 3}">		<!-- REQUIREMENTS_PENDING_RECRUITE -->
																	<span class="label label-warning"><spring:message code="pending-recruit" /></span>
																</c:when>
																<c:when test="${requirement.status == 4}">		<!-- REQUIREMENTS_RECRUITING -->
																	<span class="label label-primary"><spring:message code="recruiting" /></span>
																</c:when>
																<c:when test="${requirement.status == 6}">		<!-- REQUIREMENTS_REJECT -->
																	<span class="label label-danger"><spring:message code="denied" /></span>
																</c:when>
																<c:otherwise>				<!-- Finished -->
																	<span class="label label-success"><spring:message code="finished" /></span>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 4}">
															<c:choose>
																<c:when test="${requirement.status == 6}">		<!-- REQUIREMENTS_REJECT -->
																	<span class="label label-danger"><spring:message code="denied" /></span>
																</c:when>
																<c:when test="${requirement.status == 5}">		<!-- REQUIREMENTS_FINISH -->
																	<span class="label label-success"><spring:message code="finished" /></span>
																</c:when>
																<c:when test="${requirement.status == 4}">		<!-- REQUIREMENTS_RECRUITING -->
																	<span class="label label-primary"><spring:message code="recruiting" /></span>
																</c:when>
																<c:otherwise>				<!-- Pending -->
																	<span class="label label-primary"><spring:message code="processing" /></span>
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
																<c:when test="${requirement.status == 1}">		<!-- REQUIREMENTS_HR_MANAGER_PROCESSING -->
																	<a href="/hr-manager/aprroveOneRequirement?requirementId=${requirement.staffRequirementId }"><button type="button" class="btn btn-success"><spring:message code="approve" /></button></a>
																	<a href="/hr-manager/rejectOneRequirement?requirementId=${requirement.staffRequirementId }"><button type="button" class="btn btn-danger"><spring:message code="reject" /></button></a>
																</c:when>
																<c:otherwise>
																	<button type="button" class="btn btn-success disabled"><spring:message code="approve" /></button>
																	<button type="button" class="btn btn-danger disabled"><spring:message code="reject" /></button>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 2}">
															<c:choose>
																<c:when test="${requirement.status == 2}">		<!-- REQUIREMENTS_RECRUITER_PROCESSING -->
																	<a href="/recruiter/processOneRequirement?requirementId=${requirement.staffRequirementId }"><button type="button" class="btn btn-success"><spring:message code="process" /></button></a>
																</c:when>
																<c:otherwise>
																	<button type="button" class="btn btn-success disabled"><spring:message code="process" /></button>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 4}">
															<c:choose>
																<c:when test="${requirement.status == 1}">		<!-- REQUIREMENTS_RECRUITING -->
																	<button type="button" class="btn btn-danger" onclick="delRequirement('${_csrf.parameterName}', '${_csrf.token}', '${requirement.staffRequirementId}')"><spring:message code="delete" /></button>
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
