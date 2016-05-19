<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
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
	      <div class="col-lg-12">
	        <!-- Widget starts -->
	        	<div class="alert alert-danger alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                    You can only register as a short-term employee, for more details, please check <a href="/help" class="alert-link">help</a>.
                </div>
                
	            <div class="widget wred">
	              <div class="widget-head">
	                <i class="fa fa-lock"></i> Register 
	              </div>
	              <div class="widget-content">
		                <div class="padd">
		                  
			                  <springForm:form class="form-horizontal" action="/registerAct" modelAttribute="user">
			                       <springForm:errors path="" element="div" />
		                           <!-- role -->
		                           <div class="form-group" hidden="hidden">
			                             <label class="control-label col-lg-3" for="role">Role</label>
			                             <div class="col-lg-9">
			                               		<springForm:input path="role" type="text" class="form-control" id="role" name="role" value="5" />
			                               		<div class="alert-danger">
			                               			<!-- <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button> -->
			                               			<springForm:errors path="role" cssClass="error"/>
			                               		</div>
			                             </div>
		                           </div>
		                           <div class="form-group">
			                             <label class="control-label col-lg-3" for="userName">Username</label>
			                             <div class="col-lg-9">
			                               		<springForm:input path="userName" type="text" class="form-control" id="userName" name="userName" />
			                               		<div class="alert-danger">	
			                               			<springForm:errors path="userName" cssClass="error"/>
			                               		</div>
			                             </div>
		                           </div>
		                           <!-- Password -->
		                           <div class="form-group">
			                             <label class="control-label col-lg-3" for="password">Password</label>
			                             <div class="col-lg-9">
			                               		<springForm:input path="password" type="password" class="form-control" id="password" name="password" />
			                               		<div class="alert-danger">	
			                               			<springForm:errors path="password" cssClass="error"/>
			                               		</div>
			                             </div>
		                           </div>
		                           <div class="form-group">
			                             <label class="control-label col-lg-3" for="confirmPassword">Confirm Password</label>
			                             <div class="col-lg-9">
			                               		<springForm:input path="confirmPassword" type="password" class="form-control" id="confirmPassword" name="confirmPassword" />
			                               		<div class="alert-danger">
			                               			<springForm:errors path="confirmPassword" cssClass="error"/>
			                               		</div>
			                             </div>
		                           </div>
		                           <!-- Email -->
		                           <!-- 
		                           <div class="form-group">
			                             <label class="control-label col-lg-3" for="email">Email</label>
			                             <div class="col-lg-9">
			                               		<springForm:input path="email" type="text" class="form-control" id="email" name="email" />
			                               		<div class="alert-danger">	
			                               			<springForm:errors path="email" cssClass="error"/>
			                               		</div>
			                             </div>
		                           </div>
		                           -->
		                           <!-- Name -->
		                           <div class="form-group">
			                             <label class="control-label col-lg-3" for="firstName">First Name</label>
			                             <div class="col-lg-9">
			                               		<springForm:input path="firstName" type="text" class="form-control" id="firstName" name="firstName" />
			                               		<div class="alert-danger">
			                               			<springForm:errors path="firstName" cssClass="error"/>
			                               		</div>
			                             </div>
		                           </div>
		                           <div class="form-group">
			                             <label class="control-label col-lg-3" for="lastName">Last Name</label>
			                             <div class="col-lg-9">
			                               		<springForm:input path="lastName" type="text" class="form-control" id="lastName" name="lastName" />
			                               		<div class="alert-danger">	
			                               			<springForm:errors path="lastName" cssClass="error"/>
			                               		</div>
			                             </div>
		                           </div>
		                           <br />
		                           <div class="form-group">
	                                   <div class="col-lg-9 col-lg-offset-3">
			                           		<button type="submit" class="btn btn-danger">Register</button>
			                           		<button type="reset" class="btn btn-success">Reset</button>
			                           </div>
	                               </div>
								   <br />
		                    	   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			                  </springForm:form>
		
		                </div>
	              </div>
	              <div class="widget-foot">
	                  Already Registred? <a href="/login">Login</a>
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
