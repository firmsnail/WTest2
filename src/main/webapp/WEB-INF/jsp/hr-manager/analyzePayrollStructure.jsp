<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html lang="en">
<head>
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
                    <h1 class="page-header">Analyzing the Structure of Payroll</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
            	<div class="col-lg-12">
            		<a href="#" data-toggle="tooltip" title="Recommend strategies about the structure of short-term employees, which could improve the profits for the company. It is not implemented.">
						<button type="button" class="btn btn-success disabled">Recommend Strategies</button>
					</a>
            	</div>
            </div>
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
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->

</body>

</html>
