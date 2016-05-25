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
                    <h1 class="page-header">Staffing Requirements</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            All Staffing Requirements
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<div class="addButton" <c:if test="${currentUser == null or currentUser.role.roleId != 4 }">hidden="hidden"</c:if>>
                        		<a href="/team-manager/addRequirement">
									<button type="button" class="btn btn-success btn-lg">Create Staffing Requirements</button>
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
                                            <th>Total requirement</th>
                                            <th>Detail</th>
                                            <th>Status</th>
                                            <th>Operation</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="requirement" items="${requirements}" varStatus="status">
											<tr <c:choose><c:when test="${status.index % 2 == 0}">class="odd"</c:when><c:otherwise>class="even"</c:otherwise></c:choose>>
												<td><fmt:formatDate value="${requirement.expectDate}" pattern="yyyy-MM-dd"/></td>
												<td><fmt:formatDate value="${requirement.submitDate}" pattern="yyyy-MM-dd"/></td>
												<td><a href="/department/showOneDepartment?departmentId=${requirement.stfrqDepartment.departmentId}">${requirement.stfrqDepartment.departmentName}</a></td>
												<td>${requirement.requireNum}</td>
												<td><a href="/requirement/showOneStaffRequirement?requirementId=${requirement.staffRequirementId }"><i class="fa fa-search fa-fw"></i> See Details</a></td>
												<td>
													<c:choose>
														<c:when test="${currentUser.user.role.roleId == 1}">		<!-- for hr manager -->
															<c:choose>
																<c:when test="${requirement.status == 1}">		<!-- REQUIREMENTS_HR_MANAGER_PROCESSING -->
																	<span class="label label-warning">Pending</span>
																</c:when>
																<c:when test="${requirement.status == 6}">		<!-- REQUIREMENTS_REJECT -->
																	<span class="label label-danger">Denied</span>
																</c:when>
																<c:otherwise>				<!-- Approved -->
																	<span class="label label-success">Approved</span>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 2}">
															<c:choose>
																<c:when test="${requirement.status == 2}">		<!-- REQUIREMENTS_RECRUITER_PROCESSING -->
																	<span class="label label-warning">Pending</span>
																</c:when>
																<c:when test="${requirement.status == 3}">		<!-- REQUIREMENTS_PENDING_RECRUITE -->
																	<span class="label label-warning">Pending Recruit</span>
																</c:when>
																<c:when test="${requirement.status == 4}">		<!-- REQUIREMENTS_RECRUITING -->
																	<span class="label label-primary">Recruiting</span>
																</c:when>
																<c:when test="${requirement.status == 6}">		<!-- REQUIREMENTS_REJECT -->
																	<span class="label label-danger">Denied</span>
																</c:when>
																<c:otherwise>				<!-- Finished -->
																	<span class="label label-success">Finished</span>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 4}">
															<c:choose>
																<c:when test="${requirement.status == 6}">		<!-- REQUIREMENTS_REJECT -->
																	<span class="label label-danger">Denied</span>
																</c:when>
																<c:when test="${requirement.status == 5}">		<!-- REQUIREMENTS_FINISH -->
																	<span class="label label-success">Finished</span>
																</c:when>
																<c:when test="${requirement.status == 4}">		<!-- REQUIREMENTS_RECRUITING -->
																	<span class="label label-primary">Recruiting</span>
																</c:when>
																<c:otherwise>				<!-- Pending -->
																	<span class="label label-primary">Processing</span>
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
																<c:when test="${requirement.status == 1}">		<!-- REQUIREMENTS_HR_MANAGER_PROCESSING -->
																	<a href="/hr-manager/aprroveOneRequirement?requirementId=${requirement.staffRequirementId }"><button type="button" class="btn btn-success">Approve</button></a>
																	<a href="/hr-manager/rejectOneRequirement?requirementId=${requirement.staffRequirementId }"><button type="button" class="btn btn-danger">Reject</button></a>
																</c:when>
																<c:otherwise>
																	<button type="button" class="btn btn-success disabled">Approve</button>
																	<button type="button" class="btn btn-danger disabled">Reject</button>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 2}">
															<c:choose>
																<c:when test="${requirement.status == 2}">		<!-- REQUIREMENTS_RECRUITER_PROCESSING -->
																	<a href="/recruiter/processOneRequirement?requirementId=${requirement.staffRequirementId }"><button type="button" class="btn btn-success">Process</button></a>
																</c:when>
																<c:otherwise>
																	<button type="button" class="btn btn-success disabled">Process</button>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 4}">
															<c:choose>
																<c:when test="${requirement.status == 1}">		<!-- REQUIREMENTS_RECRUITING -->
																	<button type="button" class="btn btn-danger" onclick="delRequirement('${_csrf.parameterName}', '${_csrf.token}', '${requirement.staffRequirementId}')">Delete</button>
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
