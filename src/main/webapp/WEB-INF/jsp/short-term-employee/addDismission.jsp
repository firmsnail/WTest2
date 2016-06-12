<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../common/header.jsp" />
	<link href="${pageContext.request.contextPath}/resources/static/css/common/select2.min.css" rel="stylesheet">
	<link href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.2.0/css/datepicker.min.css" rel="stylesheet">
	<!-- <link href="${pageContext.request.contextPath}/resources/static/css/common/datepicker3.css" rel="stylesheet"> -->

	<script src="${pageContext.request.contextPath}/resources/static/js/common/select2.full.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/bootstrap-datepicker.js"></script>

	<script>
	  $(function () {
	    //Initialize Select2 Elements
	    $(".select2").select2();
	    
	    var aWeekAfterDate = new Date();
	    aWeekAfterDate.setDate(aWeekAfterDate.getDate()+7);
	    
	    $( "#expectDate" ).datepicker({
	    	startDate: aWeekAfterDate,

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
                    <h1 class="page-header"><spring:message code="apply-dismission" /></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
	                      <br>
				          <div class="box box-warning">
				            <!-- /.box-header -->
				            <div class="box-body">
				            	<div class="row">
				            		<div class="col-md-12">
							              <springForm:form class="form-horizontal col-md-12" action="/short-term-employee/addDismission" modelAttribute="dismission" method="POST">
								              	<springForm:errors path="" element="div" />
								              	
								              	<!-- employee -->
												<div class="form-group" hidden="hidden">
													<label class="control-label col-lg-3" for="employeeId">Short-Term Employee</label>
													<div class="col-lg-9">
														<springForm:input path="employeeId" type="text" class="form-control" id="employeeId" name="employeeId" value="${currentUser.user.personId}" />
														<div class="alert-danger">
															<!-- <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button> -->
															<springForm:errors path="employeeId" cssClass="error"/>
														</div>
													</div>
												</div>
								              	
								                <div class="form-group">
									                  <label><spring:message code="expect-date" /></label>
									                  <div>
									                  		<springForm:input path="expectDate" type="text" class="form-control" id="expectDate" name="expectDate" />
															<div class="alert-danger">	
																<springForm:errors path="expectDate" cssClass="error"/>
															</div>
									                  </div>
								                </div>
								                
								                <div class="form-group">
								                	  <label><spring:message code="reason" /></label>
									                  <div>
									                  		<springForm:textarea path="comment" type="text" class="form-control" id="comment" name="comment" />
															<div class="alert-danger">
																<springForm:errors path="comment" cssClass="error"/>
															</div>
									                  </div>
								                </div>
								                
					                            <div class="form-group">
				                                   <div class="col-lg-9 ">
						                           		<button type="submit" class="btn btn-danger"><spring:message code="apply" /></button>
						                           		<button type="reset" class="btn btn-success"><spring:message code="reset" /></button>
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
