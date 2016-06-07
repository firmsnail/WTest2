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
                    <h1 class="page-header">One Recruiting Plan</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Recruiting Plan
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
                        			<c:choose>
                        				<c:when test="${currentUser == null or currentUser.user.role.roleId == 5 }">
                        					<strong>Recruiting Plan Maker</strong>: ${plan.planMaker.firstName } ${plan.planMaker.lastName }
                        				</c:when>
                        				<c:otherwise>
                        					<strong>Recruiting Plan Maker</strong>: <a href="/user/profile?userId=${plan.planMaker.personId }">${plan.planMaker.firstName } ${plan.planMaker.lastName }</a>
                        				</c:otherwise>
                        			</c:choose>
                        		</div>
                        		<div>
                        			<strong>Reason</strong>: ${plan.reason }
                        		</div>
                        		<div>
                        			<strong>Staffing Plan Number</strong>: ${plan.planNum }
                        		</div>
                        		<div>
                        			<strong>Submit Date</strong>: <fmt:formatDate value="${plan.submitDate}" pattern="yyyy-MM-dd"/>
                        		</div>
                        		<div>
                        			<strong>Expect Date</strong>: <fmt:formatDate value="${plan.expectDate}" pattern="yyyy-MM-dd"/>
                        		</div>
                        		<div>
                        			<strong>Invalid Date</strong>: <fmt:formatDate value="${plan.invalidDate}" pattern="yyyy-MM-dd"/>
                        		</div>
                        		<div>
                        			<strong>Status</strong>: 
                        			<c:choose>
										<c:when test="${plan.status == 1}">
											<span class="label label-warning">Pending Verify By HR Manager</span>
										</c:when>
										<c:when test="${plan.status == 2}">
											<span class="label label-warning">Verified</span>
										</c:when>
										<c:when test="${plan.status == 3}">
											<span class="label label-primary">Recruiting</span>
										</c:when>
										<c:when test="${plan.status == 5}">
											<span class="label label-danger">Denied</span>
										</c:when>
										<c:otherwise>
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
