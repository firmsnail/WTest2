<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="Short-term Employees Management System">
	<meta name="author" content="GuXuanzhi">
	<link href="${pageContext.request.contextPath}/resources/static/css/common/bootstrap.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/static/css/font-awesome/css/font-awesome.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/static/css/common/style.css" rel="stylesheet">
</head>

<body>

    <div class="admin-form">
	  <div class="container">
		<div class="row">
			<div class="pull-right">
		        <a href="?lang=en" class="btn">English</a>
		        <a href="?lang=cn" class="btn">Chinese</a>
		    </div>
		</div>
	    <div class="row">
	      <div class="col-md-12">
	        <!-- Widget starts -->
	            <div class="widget worange">
	              <!-- Widget head -->
	              <div class="widget-head">
	                <i class="fa fa-user"></i> Login 
	              </div>
	
	              <div class="widget-content">
	              		<div class="alert alert-danger alert-dismissable" <c:if test="${loginerr != true}">hidden="hidden"</c:if> >
		                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
		                    Username or password is incorrect, please check it!
		                </div>
		                <div class="padd">
		                  <!-- Login form -->
		                  <form class="loginForm form-horizontal" action="/login" method='POST'>
		                  	<!-- Select box -->
	                        <!-- 
	                        <div class="form-group">
	                          <label class="control-label col-lg-3">Role</label>
	                          <div class="col-lg-9">                               
	                              <select class="form-control" name="role">
		                              <option value="1">HR Manager</option>
		                              <option value="2">Recruiter</option>
		                              <option value="3">C&#38;B Specialist</option>
		                              <option value="4">Staffing Team Manager</option>
		                              <option value="5">Short-term Employee</option>
	                              </select>
	                          </div>
	                        </div>
	                        -->
		                    <!-- Email -->
		                    <div class="form-group">
		                      <label class="control-label col-lg-3" for="inputUsername"><spring:message code="username" /></label>
		                      <div class="col-lg-9">
		                        <input type="text" class="form-control" id="inputUsername" name="username" placeholder="Username">
		                      </div>
		                    </div>
		                    <!-- Password -->
		                    <div class="form-group">
		                      <label class="control-label col-lg-3" for="inputPassword">Password</label>
		                      <div class="col-lg-9">
		                        <input type="password" class="form-control" id="inputPassword" name="password" placeholder="Password">
		                      </div>
		                    </div>
		                    <!-- Remember me checkbox and sign in button -->
		                    
		                    <div class="form-group">
								<div class="col-lg-9 col-lg-offset-3">
			                      <div class="checkbox">
			                        <label>
			                          <input type="checkbox" name="remember-me" id="remember-me" value="true"> Remember me
			                        </label>
									</div>
								</div>
							</div>
							
	                        <div class="col-lg-9 col-lg-offset-2">
								<button type="submit" class="btn btn-danger">Sign in</button>
								<button type="reset" class="btn btn-default">Reset</button>
							</div>
		                    <br />
		                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		                  </form>
						  
						</div>
		            </div>
	              
	                <div class="widget-foot">
	                  Not Registred?<a href="/register">Register here</a>
	                  <a class="col-lg-offset-4" href="/help">Help</a>
	                </div>
	            </div>  
	      </div>
	    </div>
	  </div> 
	</div>
		
			
	
	<!-- JS -->
	<script src="${pageContext.request.contextPath}/resources/static/js/common/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="${pageContext.request.contextPath}/resources/static/js/common/bootstrap.min.js"></script>


</body>

</html>