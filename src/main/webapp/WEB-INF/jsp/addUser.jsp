
<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>

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
                    You can just registered as a short-term employee, for more details, please check <a href="/help" class="alert-link">help</a>.
                </div>
                
                
                
	            <div class="widget wred">
	              <div class="widget-head">
	                <i class="fa fa-lock"></i> Register 
	              </div>
	              <div class="widget-content">
		                <div class="padd">
		                  
			                  <form class="form-horizontal" action="/addAct">
			                       <!-- Select box -->
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
		                           <!-- Username -->
		                           <div class="form-group">
			                             <label class="control-label col-lg-3" for="username">Username</label>
			                             <div class="col-lg-9">
			                               		<input type="text" class="form-control" id="username" name="username">
			                             </div>
		                           </div>
		                           <!-- Password -->
		                           <div class="form-group">
			                             <label class="control-label col-lg-3" for="password">Password</label>
			                             <div class="col-lg-9">
			                               		<input type="password" class="form-control" id="password" name="password">
			                             </div>
		                           </div>
		                           <!-- Name -->
		                           <div class="form-group">
			                             <label class="control-label col-lg-3" for="firstName">First Name</label>
			                             <div class="col-lg-9">
			                               		<input type="text" class="form-control" id="firstName" name="firstName">
			                             </div>
		                           </div>
		                           <div class="form-group">
			                             <label class="control-label col-lg-3" for="lastName">Last Name</label>
			                             <div class="col-lg-9">
			                               		<input type="text" class="form-control" id="lastName" name="lastName">
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
			                  </form>
		
		                </div>
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
