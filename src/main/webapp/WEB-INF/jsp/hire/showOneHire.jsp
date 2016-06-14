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
                    <h1 class="page-header"><spring:message code="one-hire" /></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <spring:message code="hire" />
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
                        			<strong><spring:message code="hire-department" /></strong>: <a href="/department/showOneDepartment?departmentId=${hire.hireDepartment.departmentId }">${hire.hireDepartment.departmentName }</a>
                        		</div>
                        		<div>
                        			<strong><spring:message code="hire-person" /></strong>: <a href="/user/profile?userId=${hire.hirePerson.personId }">${hire.hirePerson.firstName } ${hire.hirePerson.lastName }</a>
                        		</div>
                        		<div>
                        			<strong><spring:message code="hire-recruiting-plan" /></strong>: <a href="/plan/showOneRecruitingPlan?planId=${hire.hirePlan.planId }">${hire.hirePlan.planId }</a>
                        		</div>
                        		<div>
                        			<strong><spring:message code="hire-staffing-requirement" /></strong>: <a href="/requirement/showOneStaffRequirement?requirementId=${hire.requirementForHire.staffRequirementId }">${hire.requirementForHire.staffRequirementId }</a>
                        		</div>
                        		<div>
                        			<strong><spring:message code="salary" /></strong>: ${hire.salary } <spring:message code="USD-Day" />
                        		</div>
                        		<div>
                        			<strong><spring:message code="period" /></strong>: ${hire.period } <spring:message code="months" />
                        		</div>
                        		<div>
                        			<strong><spring:message code="status" /></strong>: 
                        			<c:choose>
										<c:when test="${hire.status == 1}">		<!-- HIRE_RECRUITER_PROCESSING -->
											<span class="label label-warning"><spring:message code="recruiter-processing" /></span>
										</c:when>
										<c:when test="${hire.status == 2}">		<!-- REQUIREMENTS_PENDING_RECRUITE -->
											<span class="label label-warning"><spring:message code="HR-manager-processing" /></span>
										</c:when>
										<c:when test="${hire.status == 4}">		<!-- HIRE_REJECT -->
											<span class="label label-danger"><spring:message code="denied-by-recruiter" /></span>
										</c:when>
										<c:when test="${hire.status == 5}">		<!-- HIRE_REJECT -->
											<span class="label label-danger"><spring:message code="denied-by-HR-manager" /></span>
										</c:when>
										<c:otherwise>				<!-- Finished -->
											<span class="label label-success"><spring:message code="finished" /></span>
										</c:otherwise>
									</c:choose>
                        		</div>
                        		<div>
                        			<strong><spring:message code="submit-date" /></strong>: <fmt:formatDate value="${hire.submitDate}" pattern="yyyy-MM-dd"/>
                        		</div>
                        		<div>
                        			<strong><spring:message code="hire-date" /></strong>: 
                        			<c:choose>
                        				<c:when test="${hire.status == 3}">
                        					<fmt:formatDate value="${hire.hireDate}" pattern="yyyy-MM-dd"/>
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
