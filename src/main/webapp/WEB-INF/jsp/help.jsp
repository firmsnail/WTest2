<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="./common/header.jsp" />
	<link href="${pageContext.request.contextPath}/resources/static/css/common/timeline.css" rel="stylesheet">
	
	<link href="${pageContext.request.contextPath}/resources/static/css/common/morris.css" rel="stylesheet">
	
</head>

<body>

    <div id="wrapper">

        <jsp:include page="./common/nav.jsp" />
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Help</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="col-lg-12">
                <div>
                	<p>
                		This is a powerful management system for HR Dept. people whose company has many "short-term" employees. It is mainly designed to improve the management efficiency for "short-term" employees(we consider the employees whoes work period doesn't longer than 6 months as "short-term" employees), and reduce the costs to manage them.
The users of the system contains five different roles: <strong>1. HR Manager, 2. Recruiter, 3. C&B Specialist, 4. Staffing Team Manager, 5.Short-term Manager.</strong>
                	</p>
                	<div>
                		<strong>For test</strong>, the system provides some accounts to use.
						<ul>
							<li>
								<strong>HR Manager</strong><br>
								UserName: admin<br>
								Password: admin<br>
							</li>
							<li>
								<strong>Recruiter</strong><br>
								UserName: recruiter<br>
								Password: recruiter<br>
							</li>
							<li>
								<strong>C&B Specialist</strong><br>
								UserName: specialist<br>
								Password: specialist<br>
							</li>
							<li>
								<strong>Staffing Team Manager</strong><br>
								UserName: teammanager<br>
								Password: teammanager<br>
							</li>
							<li>
								<strong>Short-term Employee</strong><br>
								UserName: employee<br>
								Password: employee<br>
							</li>
							
						</ul>
                	</div>
                </div>
            </div>
            
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->


</body>

</html>