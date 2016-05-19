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
                    <h1 class="page-header">One Staffing Requirement</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Staffing Requirement
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<!-- 
                        	<div class="addButton" <c:if test="${currentUser == null or currentUser.role.roleId != 1 }">hidden="hidden"</c:if>>
                        		<a href="/hr-manager/addDept">
									<button type="button" class="btn btn-success btn-lg">Create Department</button>
								</a>
                        	</div>
                        	-->
                        	<div class="col-lg-3 row">
                        		<div>
                        			<strong>Request Department Name</strong>: <a href="/department/showOneDepartment?departmentId=${requirement.stfrqDepartment.departmentId }">${requirement.stfrqDepartment.departmentName }</a>
                        		</div>
                        		<div>
                        			<strong>Reason</strong>: ${requirement.reason }
                        		</div>
                        		<div>
                        			<strong>Require Number</strong>: ${requirement.requireNum }
                        		</div>
                        		<div>
                        			<strong>Submit Date</strong>: <fmt:formatDate value="${requirement.submitDate}" pattern="yyyy-MM-dd"/>
                        		</div>
                        		<div>
                        			<strong>Expect Date</strong>: <fmt:formatDate value="${requirement.expectDate}" pattern="yyyy-MM-dd"/>
                        		</div>
                        		<div>
                        			<strong>Status</strong>: 
                        			<c:choose>
										<c:when test="${requirement.status == 1}">		
											<span class="label label-warning">Processing By HR Manager</span>
										</c:when>
										<c:when test="${requirement.status == 2}">		
											<span class="label label-warning">Processing By Recruiter</span>
										</c:when>
										<c:when test="${requirement.status == 3}">		
											<span class="label label-warning">Pending Recruit</span>
										</c:when>
										<c:when test="${requirement.status == 4}">
											<span class="label label-primary">Recruiting</span>
										</c:when>
										<c:when test="${requirement.status == 6}">		<!-- REQUIREMENTS_REJECT -->
											<span class="label label-danger">Denied</span>
										</c:when>
										<c:otherwise>				<!-- Finished -->
											<span class="label label-success">Finished</span>
										</c:otherwise>
									</c:choose>
                        		</div>
                        		<div>
                        			<strong>Skills</strong>: 
                        			<c:forEach var="skill" items="${skills}" varStatus="status">
                        				${skill.skillName }
                        			</c:forEach>
                        		</div>
                        	</div>
                        	<br>
                        	<br>
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
