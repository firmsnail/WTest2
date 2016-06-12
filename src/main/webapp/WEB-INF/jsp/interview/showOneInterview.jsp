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
                    <h1 class="page-header"><spring:message code="one-interview" /></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <spring:message code="interview" />
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
                        			<strong><spring:message code="interviewer" /></strong>: <a href="/user/profile?userId=${interview.interviewer.personId }">${interview.interviewer.firstName } ${interview.interviewer.lastName }</a>
                        		</div>
                        		<div>
                        			<strong><spring:message code="interviewee" /></strong>: <a href="/user/profile?userId=${interview.interviewee.personId }">${interview.interviewee.firstName } ${interview.interviewee.lastName }</a>
                        		</div>
                        		<div>
                        			<strong><spring:message code="recruiting-plan" /></strong>: <a href="/plan/showOneRecruitingPlan?planId=${interview.planForInterview.planId }">${interview.planForInterview.planId }</a>
                        		</div>
                        		<div>
                        			<strong><spring:message code="interview-staffing-requirement" /></strong>: <a href="/requirement/showOneStaffRequirement?requirementId=${interview.requirementForInterview.staffRequirementId }">${interview.requirementForInterview.staffRequirementId }</a>
                        		</div>
                        		<div>
                        			<strong><spring:message code="turns" /></strong>: ${interview.turns }
                        		</div>
                        		<div>
                        			<strong><spring:message code="status" /></strong>: 
                        			<c:choose>
										<c:when test="${interview.status == 1}">		<!-- INTERVIEW_PENDING_SCHEDULE -->
											<span class="label label-warning"><spring:message code="pending-schedule" /></span>
										</c:when>
										<c:when test="${interview.status == 2}">		<!-- INTERVIEW_INTERVIEWING -->
											<span class="label label-primary"><spring:message code="interviewing" /></span>
										</c:when>
										<c:when test="${interview.status == 4}">		<!-- INTERVIEW_PASSED -->
											<span class="label label-success"><spring:message code="passed" /></span>
										</c:when>
										<c:otherwise>
											<span class="label label-danger"><spring:message code="failed" /></span>
										</c:otherwise>
									</c:choose>
                        		</div>
                        		<div>
                        			<strong><spring:message code="update-time" /></strong>: <fmt:formatDate value="${interview.updateTime}" pattern="yyyy-MM-dd hh:mm"/>
                        		</div>
                        		<div>
                        			<strong><spring:message code="interview-time" /></strong>: 
                        			<c:choose>
                        				<c:when test="${interview.status == 2}">
                        					<fmt:formatDate value="${interview.interviewTime}" pattern="yyyy-MM-dd hh:mm"/>
                        				</c:when>
                        				<c:otherwise>
                        					N/A
                        				</c:otherwise>
                        			</c:choose>
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
