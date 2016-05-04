<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../common/header.jsp" />
	<link href="${pageContext.request.contextPath}/resources/static/css/common/select2.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/static/js/common/select2.full.min.js"></script>
	<script>
	  $(function () {
	    //Initialize Select2 Elements
	    $(".select2").select2();
	  });
	</script>
</head>

<body>
	<div id="wrapper">
		<jsp:include page="../common/nav.jsp" />
		<div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Add Employees</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
	                      <!-- 
	                      <div class="panel-heading">
	                            Create Department
	                      </div>
	                      -->
	                      <br>
				          <div class="box box-warning">
				            <!-- /.box-header -->
				            <div class="box-body">
				            	<div class="row">
				            		<div class="col-md-12">
							              <springForm:form class="form-horizontal col-md-12" action="/hr-manager/addUser" modelAttribute="user">
						                       <springForm:errors path="" element="div" />
					                           <!-- role -->
					                           <div class="form-group">
						                             <label>Role</label>
						                             <div>
						                               		<springForm:select path="role" class="form-control select2" name="role" style="width: 100%;">
											                	  <option value=2 selected="selected">RECRUITER</option>
											                	  <option value=3 selected="selected">C&B-SPECIALIST</option>
											                	  <option value=4 selected="selected">TEAM-MANAGER</option>
											                	  <option value=5 selected="selected">SHORT-TERM-EMPLOYEE</option>
											                </springForm:select>
						                               		<div class="alert-danger">
						                               			<!-- <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button> -->
						                               			<springForm:errors path="role" cssClass="error"/>
						                               		</div>
						                             </div>
					                           </div>
					                           <div class="form-group">
						                             <label>Username</label>
						                             <div>
						                               		<springForm:input path="userName" type="text" class="form-control" id="userName" name="userName" />
						                               		<div class="alert-danger">
						                               			<springForm:errors path="userName" cssClass="error"/>
						                               		</div>
						                             </div>
					                           </div>
					                           <!-- Password -->
					                           <div class="form-group">
						                             <label>Password</label>
						                             <div>
						                               		<springForm:input path="password" type="password" class="form-control" id="password" name="password" />
						                               		<div class="alert-danger">	
						                               			<springForm:errors path="password" cssClass="error"/>
						                               		</div>
						                             </div>
					                           </div>
					                           <div class="form-group">
						                             <label>Confirm Password</label>
						                             <div>
						                               		<springForm:input path="confirmPassword" type="password" class="form-control" id="confirmPassword" name="confirmPassword" />
						                               		<div class="alert-danger">
						                               			<springForm:errors path="confirmPassword" cssClass="error"/>
						                               		</div>
						                             </div>
					                           </div>
					                           <!-- Email -->
					                           <div class="form-group">
						                             <label>Email</label>
						                             <div>
						                               		<springForm:input path="email" type="text" class="form-control" id="email" name="email" />
						                               		<div class="alert-danger">	
						                               			<springForm:errors path="email" cssClass="error"/>
						                               		</div>
						                             </div>
					                           </div>
					                           <!-- Name -->
					                           <div class="form-group">
						                             <label>First Name</label>
						                             <div>
						                               		<springForm:input path="firstName" type="text" class="form-control" id="firstName" name="firstName" />
						                               		<div class="alert-danger">
						                               			<springForm:errors path="firstName" cssClass="error"/>
						                               		</div>
						                             </div>
					                           </div>
					                           <div class="form-group">
						                             <label>Last Name</label>
						                             <div>
						                               		<springForm:input path="lastName" type="text" class="form-control" id="lastName" name="lastName" />
						                               		<div class="alert-danger">	
						                               			<springForm:errors path="lastName" cssClass="error"/>
						                               		</div>
						                             </div>
					                           </div>
					                           <div class="form-group">
										                <label>Department</label>
										                <springForm:select path="departmentId" class="form-control select2" name="departmentId" style="width: 100%;">
										                	  <option selected="selected"></option>
										                	  <c:forEach var="department" items="${departments}" varStatus="status">
										                	  		<option value="${department.departmentId}">
										                	  			${department.departmentName}
										                	  		</option>
										                	  </c:forEach>
										                </springForm:select>
										                <div class="alert-danger">
										                	<springForm:errors path="departmentId" cssClass="error"/>
										                </div>
									           </div>
					                           <br />
					                           <div class="form-group">
				                                   <div class="col-lg-9">
						                           		<button type="submit" class="btn btn-danger">Add</button>
						                           		<button type="reset" class="btn btn-success">Reset</button>
						                           </div>
				                               </div>
											   <br />
					                    	   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						                  </springForm:form>
					              </div>
					        	</div>
				            </div>
				            <!-- /.box-body -->
				          </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /#page-wrapper -->
	</div>
	
</body>

</html>
