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
                    <h1 class="page-header">Create Departments</h1>
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
							              <springForm:form class="form-horizontal col-md-12" action="/hr-manager/addDept" modelAttribute="department" method="POST">
								              	<springForm:errors path="" element="div" />
								                <!-- text input -->
								                <div class="form-group">
									                  <label>Department Name</label>
									                  <div>
									                  		<springForm:input path="departmentName" type="text" class="form-control" id="departmentName" name="departmentName" />
															<div class="alert-danger">	
																<springForm:errors path="departmentName" cssClass="error"/>
															</div>
									                  </div>
								                </div>
				
								                <!-- textarea -->
								                <div class="form-group">
								                	  <label>Description</label>
									                  <div>
									                  		<springForm:textarea path="description" type="text" class="form-control" id="description" name="description" />
															<div class="alert-danger">
																<springForm:errors path="description" cssClass="error"/>
															</div>
									                  </div>
								                </div>
								                
								                <div class="form-group">
										                <label>Manager</label>
										                <springForm:select path="managerId" class="form-control select2" name="managerId" style="width: 100%;">
										                	  <option selected="selected"></option>
										                	  <c:forEach var="manager" items="${managers}" varStatus="status">
										                	  		<option value="${manager.personId}">
										                	  			${manager.firstName} ${manager.lastName} ID:${manager.personId}
										                	  		</option>
										                	  </c:forEach>
										                </springForm:select>
										                <div class="alert-danger">
										                	<springForm:errors path="managerId" cssClass="error"/>
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
