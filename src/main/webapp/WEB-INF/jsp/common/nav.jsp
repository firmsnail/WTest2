<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Navigation -->
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="index.html">Short-term Employee Management System</a>
    </div>

	<script>
	    function formSubmit() {
	        document.getElementById("logoutForm").submit();
	    }
		$(document).ready(function() {
	    	
	    	//Initialize Select2 Elements
		    $(".select2").select2();
	        $('#dataTables-example').DataTable({
	                responsive: false
	        });
			function myrefresh() {
	        	
	        	$.ajax({
	        		url: "/user/ajaxNotify",
	        		dataType: 'json',
	        		success: function(data) {
	        			$("#notify-num").remove()
	        			if (data > 0) {
	        				$("#notifyA").append('<span class=\"label label-danger\" id=\"notify-num\">'+data+'</span>')
	        			}
	        		}
	        	});
	        	
	        }
	        setInterval(myrefresh, 5000); //指定时间刷新一次 
	        
	    });
	</script>
    <ul class="nav navbar-top-links navbar-right">
        
        <!-- /.dropdown -->
        <li <c:if test="${currentUser != null}">class="hidden"</c:if> >
            <a class="dropdown-toggle" data-toggle="dropdown" href="/user/profile?userId=${currentUser.user.personId }" id="notifyA">
                <i class="fa fa-bell"></i>
                <!-- <span class="label label-danger" id="notify-num">4</span>-->
            </a>
        </li>
        <!-- /.dropdown -->
        <li <c:if test="${currentUser != null}">class="hidden"</c:if> >
        	<a href="/login">Log In</a>
        </li>
        
        <li class="dropdown <c:if test="${currentUser == null}">hidden</c:if>" >
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-user fa-fw"></i>  <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li><a href="/user/profile?userId=${currentUser.user.personId}"><i class="fa fa-user fa-fw"></i> User Profile</a>
                </li>
                <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
                </li>
                <li class="divider"></li>
                <li>
                	<a href="javascript:formSubmit()"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                	<form id="logoutForm" action="/logout" method="post">
					    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
                </li>
            </ul>
            <!-- /.dropdown-user -->
        </li>
        <!-- /.dropdown -->
    </ul>
    <!-- /.navbar-top-links -->

    <div class="navbar-default sidebar" role="navigation">
        <div class="sidebar-nav navbar-collapse">
            <ul class="nav" id="side-menu">
                <li class="sidebar-search">
                    <div class="input-group custom-search-form">
                        <input type="text" class="form-control" placeholder="Search...">
                        <span class="input-group-btn">
                        <button class="btn btn-default" type="button">
                            <i class="fa fa-search"></i>
                        </button>
                    </span>
                    </div>
                    <!-- /input-group -->
                </li>
                <li>
                    <a href="/index"><i class="fa fa-dashboard fa-fw"></i> Dashboard</a>
                </li>
                
                <li <c:if test="${currentUser == null or currentUser.user.role.roleId < 1 or currentUser.user.role.roleId > 3 }">class="hidden"</c:if> >
                    <a href="/department/showDepartments"><i class="fa fa-home fa-fw"></i> Departments</a>
                </li>
                
                <li <c:if test="${currentUser == null or currentUser.user.role.roleId < 1 or currentUser.user.role.roleId > 3 }">class="hidden"</c:if> >
                    <a href="/user/showEmployees"><i class="fa fa-users fa-fw"></i> Employees</a>
                </li>
                
                <li <c:if test="${currentUser == null or (currentUser.user.role.roleId != 1 and currentUser.user.role.roleId != 2 and currentUser.user.role.roleId != 4)}">class="hidden"</c:if> >
                    <a href="/requirement/showStaffRequirements"><i class="fa fa-users fa-fw"></i> Staffing Requirements</a>
                </li>
                
                <li <c:if test="${currentUser == null or currentUser.user.role.roleId != 2}">class="hidden"</c:if> >
                    <a href="/recruiter/showAnalyzeRequirments"><i class="fa fa-users fa-fw"></i> Analyze Requirements</a>
                </li>
                
                <li <c:if test="${currentUser == null or (currentUser.user.role.roleId != 1 and currentUser.user.role.roleId != 2 and currentUser.user.role.roleId != 5)}">class="hidden"</c:if> >
                    <a href="/plan/showRecruitingPlans"><i class="fa fa-users fa-fw"></i> Recruiting Plans</a>
                </li>
                
                <li <c:if test="${currentUser == null or (currentUser.user.role.roleId != 2 and currentUser.user.role.roleId != 4 and currentUser.user.role.roleId != 5)}">class="hidden"</c:if> >
                    <a href="/interview/showInterviews"><i class="fa fa-users fa-fw"></i> Interviews</a>
                </li>
                
                <li <c:if test="${currentUser == null or (currentUser.user.role.roleId != 1 and currentUser.user.role.roleId != 2 and currentUser.user.role.roleId != 4)}">class="hidden"</c:if> >
                    <a href="/hire/showHires"><i class="fa fa-users fa-fw"></i> Hires</a>
                </li>
                
                <li <c:if test="${currentUser == null or currentUser.user.role.roleId == 1 or currentUser.user.role.roleId == 3}">class="hidden"</c:if>>
                	<a href="/applicant/showApplicants"><i class="fa fa-users fa-fw"></i>
                		<c:choose>
                			<c:when test="${currentUser.user.role.roleId == 5 }">
                				Applications
                			</c:when>
                			<c:otherwise>
                				Applicants
                			</c:otherwise>
                		</c:choose>
                	</a>
                </li>
               
                <li <c:if test="${currentUser == null or currentUser.user.role.roleId == 2}">class="hidden"</c:if> >
                    <a href="/dismission/showDismissions"><i class="fa fa-users fa-fw"></i> Dismissions</a>
                </li>
                
                <li <c:if test="${currentUser == null or currentUser.user.role.roleId == 2 or currentUser.user.role.roleId == 1}">class="hidden"</c:if> >
                    <a href="/leave/showLeaves"><i class="fa fa-users fa-fw"></i> Leaves</a>
                </li>
                
                <li <c:if test="${currentUser == null or currentUser.user.role.roleId != 1}">class="hidden"</c:if> >
                    <a href="/hr-manager/analyzeEmployeeStructure"><i class="fa fa-users fa-fw"></i> Analyze Employee Structure</a>
                </li>
                
                <li <c:if test="${currentUser == null or currentUser.user.role.roleId != 1}">class="hidden"</c:if> >
                    <a href="/hr-manager/analyzePayrollStructure"><i class="fa fa-users fa-fw"></i> Analyze Payroll Structure</a>
                </li>
                
                <li>
                    <a href="/help"><i class="fa fa-question-circle fa-fw"></i> Help</a>
                </li>
                
            </ul>
        </div>
        <!-- /.sidebar-collapse -->
    </div>
    <!-- /.navbar-static-side -->
</nav>