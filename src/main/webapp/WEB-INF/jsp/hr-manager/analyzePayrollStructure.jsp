<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<link href="${pageContext.request.contextPath}/resources/static/css/common/AdminLTE.min.css" rel="stylesheet">
	<jsp:include page="../common/header.jsp" />
	<script src="${pageContext.request.contextPath}/resources/static/js/common/jquery.dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/dataTables.bootstrap.min.js"></script>
	<script type="text/javascript" src="http://cdn.hcharts.cn/highcharts/highcharts.js">
	<script src="//cdn.bootcss.com/highcharts/4.2.5/modules/exporting.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/payroll/analyzePayroll.js"></script>
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
                    <h1 class="page-header"><spring:message code="analyzing-payroll-structure" /></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
            	<div class="col-lg-12">
            		<a href="#" data-toggle="tooltip" title="Recommend strategies about the structure of short-term employees, which could improve the profits for the company. It is not implemented.">
						<button type="button" class="btn btn-success disabled"><spring:message code="recommend-strategies" /></button>
					</a>
            	</div>
            </div>
            
            <br>
            <div class="row">
            	<div class="col-lg-12">
            		<strong><spring:message code="total" />: </strong>${total } <spring:message code="USD" />
            	</div>
            </div>
            <br>
            <div class="row">
            	<div class="col-lg-12">
            		<div class="nav-tabs-custom">
            			<ul class="nav nav-tabs">
							<li class="active"><a href="#charts" data-toggle="tab"><spring:message code="by-charts" /></a></li>
							<li><a href="#tables" data-toggle="tab"><spring:message code="by-tables" /></a></li>
			            </ul>.
			            <div class="tab-content">
			            	<div class="active tab-pane" id="charts">
			            		<!-- /.row -->
					            <div class="row">
					                <div class="col-lg-6">
					            		<div id="analyzePayrollByPeriod"></div>
					                </div>
					
					                <div class="col-lg-6">
					                    <div id="analyzePayrollBySkill"></div>
					                </div>
					                <!-- /.col-lg-6 -->
					            </div>
					            
					            <!-- /.row -->
					            <br>
					            <br>
					            <div class="row">
					                <div class="col-lg-6">
					                    <div id="analyzePayrollByGender"></div>
					                </div>
					                <!-- /.col-lg-6 -->
					                <div class="col-lg-6">
					                    <div id="analyzePayrollByAge"></div>
					                </div>
					                <!-- /.col-lg-6 -->
					            </div>
					            <!-- /.row -->
					            <br>
					            <br>
					            <div class="row">
					                <div class="col-lg-6">
					                    <div id="analyzePayrollByDepartment"></div>
					                </div>
					            </div>
			            	</div>
			            	<div class="tab-pane" id="tables">
			            		<div class="row">
			            			<div class="col-lg-6">
			            				<div class="box">
							            <div class="box-header">
							              <h3 class="box-title"><spring:message code="distribution-by-period" /></h3>
							            </div>
							            <!-- /.box-header -->
							            <div class="box-body table-responsive no-padding">
							              <table class="table table-hover">
												<tr>
												  <th><spring:message code="category" /></th>
												  <th><spring:message code="amount-USD" /></th>
												  <th><spring:message code="ratio" /></th>
												</tr>
												<c:forEach var="kind" items="${periodKinds }" varStatus="status">
													<tr>
												 	<td>${kind }</td>
												 	<td>${periodAmounts[status.index]}</td>
												 	<td>${periodRatios[status.index]}</td>
												 <tr>
												</c:forEach>
							              </table>
							            </div>
							            <!-- /.box-body -->
							          </div>
							          <!-- /.box -->
			            			</div>
			            			<div class="col-lg-6">
			            				<div class="box">
							            <div class="box-header">
							              <h3 class="box-title"><spring:message code="distribution-by-skill" /></h3>
							            </div>
							            <!-- /.box-header -->
							            <div class="box-body table-responsive no-padding">
							              <table class="table table-hover">
							                	<tr>
												  <th><spring:message code="category" /></th>
												  <th><spring:message code="amount-USD" /></th>
												  <th><spring:message code="ratio" /></th>
												</tr>
												<c:forEach var="kind" items="${skillKinds }" varStatus="status">
													<tr>
													 	<td>${kind }</td>
													 	<td>${skillAmounts[status.index]}</td>
													 	<td>${skillRatios[status.index]}</td>
													 <tr>
												</c:forEach>
							              </table>
							            </div>
							            <!-- /.box-body -->
							          </div>
							          <!-- /.box -->
			            			</div>
			            		</div>
			            		<div class="row">
			            			<div class="col-lg-6">
			            				<div class="box">
							            <div class="box-header">
							              <h3 class="box-title"><spring:message code="distribution-by-gender" /></h3>
							            </div>
							            <!-- /.box-header -->
							            <div class="box-body table-responsive no-padding">
							              <table class="table table-hover">
							                	<tr>
												  <th><spring:message code="category" /></th>
												  <th><spring:message code="amount-USD" /></th>
												  <th><spring:message code="ratio" /></th>
												</tr>
												<c:forEach var="kind" items="${genderKinds }" varStatus="status">
													<tr>
													 	<td>${kind }</td>
													 	<td>${genderAmounts[status.index]}</td>
													 	<td>${genderRatios[status.index]}</td>
													 <tr>
												</c:forEach>
							              </table>
							            </div>
							            <!-- /.box-body -->
							          </div>
							          <!-- /.box -->
			            			</div>
			            			<div class="col-lg-6">
			            				<div class="box">
							            <div class="box-header">
							              <h3 class="box-title"><spring:message code="distribution-by-age" /></h3>
							            </div>
							            <!-- /.box-header -->
							            <div class="box-body table-responsive no-padding">
							              <table class="table table-hover">
							                	<tr>
												  <th><spring:message code="category" /></th>
												  <th><spring:message code="amount-USD" /></th>
												  <th><spring:message code="ratio" /></th>
												</tr>
												<c:forEach var="kind" items="${ageKinds }" varStatus="status">
													<tr>
													 	<td>${kind }</td>
													 	<td>${ageAmounts[status.index]}</td>
													 	<td>${ageRatios[status.index]}</td>
													 <tr>
												</c:forEach>
							              </table>
							            </div>
							            <!-- /.box-body -->
							          </div>
							          <!-- /.box -->
			            			</div>
			            		</div>
			            		<div class="row">
			            			<div class="col-lg-6">
			            				<div class="box">
							            <div class="box-header">
							              <h3 class="box-title"><spring:message code="distribution-by-department" /></h3>
							            </div>
							            <!-- /.box-header -->
							            <div class="box-body table-responsive no-padding">
							              <table class="table table-hover">
							                	<tr>
												  <th><spring:message code="category" /></th>
												  <th><spring:message code="amount-USD" /></th>
												  <th><spring:message code="ratio" /></th>
												</tr>
												<c:forEach var="kind" items="${departmentKinds }" varStatus="status">
													<tr>
													 	<td>${kind }</td>
													 	<td>${departmentAmounts[status.index]}</td>
													 	<td>${departmentRatios[status.index]}</td>
													 <tr>
												</c:forEach>
							              </table>
							            </div>
							            <!-- /.box-body -->
							          </div>
							          <!-- /.box -->
			            			</div>
			            		</div>
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
