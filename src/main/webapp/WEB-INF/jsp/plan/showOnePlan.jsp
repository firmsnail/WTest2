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
                    <h1 class="page-header"><spring:message code="one-recruiting-plan" /></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <spring:message code="recruiting-plan" />
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
                        					<strong><spring:message code="recruiting-plan-maker" /></strong>: ${plan.planMaker.firstName } ${plan.planMaker.lastName }
                        				</c:when>
                        				<c:otherwise>
                        					<strong><spring:message code="recruiting-plan-maker" /></strong>: <a href="/user/profile?userId=${plan.planMaker.personId }">${plan.planMaker.firstName } ${plan.planMaker.lastName }</a>
                        				</c:otherwise>
                        			</c:choose>
                        		</div>
                        		<div>
                        			<strong><spring:message code="reason" /></strong>: ${plan.reason }
                        		</div>
                        		<div>
                        			<strong><spring:message code="staffing-plan-number" /></strong>: ${plan.planNum }
                        		</div>
                        		<div>
                        			<strong><spring:message code="submit-date" /></strong>: <fmt:formatDate value="${plan.submitDate}" pattern="yyyy-MM-dd"/>
                        		</div>
                        		<div>
                        			<strong><spring:message code="expect-date" /></strong>: <fmt:formatDate value="${plan.expectDate}" pattern="yyyy-MM-dd"/>
                        		</div>
                        		<div>
                        			<strong><spring:message code="invalid-date" /></strong>: <fmt:formatDate value="${plan.invalidDate}" pattern="yyyy-MM-dd"/>
                        		</div>
                        		<div>
                        			<strong><spring:message code="status" /></strong>: 
                        			<c:choose>
										<c:when test="${plan.status == 1}">
											<span class="label label-warning"><spring:message code="pending-verify-by-HR-manager" /></span>
										</c:when>
										<c:when test="${plan.status == 2}">
											<span class="label label-warning"><spring:message code="verified" /></span>
										</c:when>
										<c:when test="${plan.status == 3}">
											<span class="label label-primary"><spring:message code="recruiting" /></span>
										</c:when>
										<c:when test="${plan.status == 5}">
											<span class="label label-danger"><spring:message code="denied" /></span>
										</c:when>
										<c:otherwise>
											<span class="label label-success"><spring:message code="finished" /></span>
										</c:otherwise>
									</c:choose>
                        		</div>
                        		
                        		<div>
                        			<strong><spring:message code="skills" /></strong>: 
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
