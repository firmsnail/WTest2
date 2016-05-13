<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../common/header.jsp" />
	<link href="${pageContext.request.contextPath}/resources/static/css/common/select2.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/static/css/common/datepicker3.css" rel="stylesheet">

	<script src="${pageContext.request.contextPath}/resources/static/js/common/select2.full.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/bootstrap-datepicker.js"></script>

	<script>
	  $(function () {
	    //Initialize Select2 Elements
	    $(".select2").select2();
	    
	    $( "#expectDate" ).datepicker({
	    	startDate: new Date(),
	        //defaultDate: "+1w",
	        //changeMonth: true,
	        //numberOfMonths: 1,
	        autoclose: true,
	        format: "yyyy-mm-dd"
	    });
	    
	    $( "#invalidDate" ).datepicker({
	    	startDate: new Date(),
	        //defaultDate: "+1w",
	        //changeMonth: true,
	        //numberOfMonths: 1,
	        autoclose: true,
	        format: "yyyy-mm-dd"
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
                    <h1 class="page-header">Create Plan</h1>
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
							              <springForm:form class="form-horizontal col-md-12" action="/recruiter/addPlan" modelAttribute="plan" method="POST">
								              	<springForm:errors path="" element="div" />
								              	
								                <!-- text input -->
								                <div class="form-group">
									                  <label>Total Requirement</label>
									                  <div>
									                  		<springForm:input path="planNum" type="text" class="form-control" id="planNum" name="planNum" />
															<div class="alert-danger">	
																<springForm:errors path="planNum" cssClass="error"/>
															</div>
									                  </div>
								                </div>
								                
								                <div class="form-group">
									                  <label>Expect Date</label>
									                  <div>
									                  		<springForm:input path="expectDate" type="text" class="form-control" id="expectDate" name="expectDate" />
															<div class="alert-danger">	
																<springForm:errors path="expectDate" cssClass="error"/>
															</div>
									                  </div>
								                </div>
								                
								                <div class="form-group">
									                  <label>Invalid Date</label>
									                  <div>
									                  		<springForm:input path="invalidDate" type="text" class="form-control" id="invalidDate" name="invalidDate" />
															<div class="alert-danger">	
																<springForm:errors path="invalidDate" cssClass="error"/>
															</div>
									                  </div>
								                </div>
								                
								                <div class="form-group">
								                	  <label>Skills</label>
									                  <div>
									                  		<springForm:select path="skills" class="form-control select2" multiple="multiple" data-placeholder="Select skills" name="skills" style="width: 100%;">
											                	  <c:forEach var="skill" items="${chooseSkills}" varStatus="status">
											                	  		<option value="${skill.skillId}">
											                	  			${skill.skillName}
											                	  		</option>
											                	  </c:forEach>
											                </springForm:select>
						                               		<div class="alert-danger">
						                               			<!-- <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button> -->
						                               			<springForm:errors path="skills" cssClass="error"/>
						                               		</div>
									                  </div>
								                </div>
								                
								                <div class="form-group">
								                	  <label>Staffing Requirements</label>
									                  <div>
									                  		<springForm:select path="requirements" class="form-control select2" multiple="multiple" data-placeholder="Select requirements" name="requirements" style="width: 100%;">
											                	  <c:forEach var="requirement" items="${chooseRequirements}" varStatus="status">
											                	  		<option value="${requirement.staffRequirementId}">
											                	  			ID:${requirement.staffRequirementId}
											                	  		</option>
											                	  </c:forEach>
											                </springForm:select>
						                               		<div class="alert-danger">
						                               			<!-- <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button> -->
						                               			<springForm:errors path="requirements" cssClass="error"/>
						                               		</div>
									                  </div>
								                </div>
								                
								                <div class="form-group">
								                	  <label>Reason</label>
									                  <div>
									                  		<springForm:textarea path="reason" type="text" class="form-control" id="reason" name="reason" />
															<div class="alert-danger">
																<springForm:errors path="reason" cssClass="error"/>
															</div>
									                  </div>
								                </div>
								                
					                            <div class="form-group">
				                                   <div class="col-lg-9 ">
						                           		<button type="submit" class="btn btn-danger">Create</button>
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
