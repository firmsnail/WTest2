<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../common/header.jsp" />
	<link href="${pageContext.request.contextPath}/resources/static/css/common/select2.min.css" rel="stylesheet">
	<link href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.2.0/css/datepicker.min.css" rel="stylesheet">
	<!--<link href="${pageContext.request.contextPath}/resources/static/css/common/datepicker3.css" rel="stylesheet">-->

	<script src="${pageContext.request.contextPath}/resources/static/js/common/select2.full.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/bootstrap-datepicker.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/skill/skill.js"></script>

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
	  });
	</script>
	
</head>

<body>
	<div class="modal fade" id="skillModal" tabindex="-1" role="dialog" aria-labelledby="skillModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="skillModalLabel">New Skill</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="skill-name" class="control-label">Skill Name:</label>
						<input type="text" class="form-control" id="skill-name">
					</div>
					<div class="form-group">
						<label for="skill-description" class="control-label">Description:</label>
						<textarea class="form-control" id="skill-description"></textarea>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<a type="button" class="btn btn-primary" onclick="addSkill('${_csrf.parameterName}', '${_csrf.token}')">Add</a>
				</div>
			</div>
		</div>
	</div>
	<div id="wrapper">
		<jsp:include page="../common/nav.jsp" />
		<div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Create Requirement</h1>
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
							              <springForm:form class="form-horizontal col-md-12" action="/team-manager/addRequirement" modelAttribute="requirement" method="POST">
								              	<springForm:errors path="" element="div" />
								              	
								              	<!-- department -->
												<div class="form-group" hidden="hidden">
													<label class="control-label col-lg-3" for="departmentId">Department</label>
													<div class="col-lg-9">
														<springForm:input path="departmentId" type="text" class="form-control" id="departmentId" name="departmentId" value="${currentUser.user.department.departmentId}" />
														<div class="alert-danger">
															<!-- <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button> -->
															<springForm:errors path="departmentId" cssClass="error"/>
														</div>
													</div>
												</div>
								              	
								                <!-- text input -->
								                <div class="form-group">
									                  <label>Total Requirement</label>
									                  <div>
									                  		<springForm:input path="requireNum" type="text" class="form-control" id="requireNum" name="requireNum" />
															<div class="alert-danger">	
																<springForm:errors path="requireNum" cssClass="error"/>
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
								                	  <label>Skills</label>
								                	  <button type="button" class="btn btn-success" data-toggle="modal" data-target="#skillModal">New Skill</button>
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
