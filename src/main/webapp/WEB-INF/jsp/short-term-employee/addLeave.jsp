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
	<link href="${pageContext.request.contextPath}/resources/static/css/common/daterangepicker-bs3.css" rel="stylesheet">

	<script src="${pageContext.request.contextPath}/resources/static/js/common/select2.full.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.2/moment.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/daterangepicker.js"></script>

	<script>
	  $(function () {
	    //Initialize Select2 Elements
	    $(".select2").select2();
	    $('#reservation').daterangepicker();
	    $('#reservation').on('apply.daterangepicker', function(ev, picker) {
	    	$('#startDate').val(picker.startDate.format('YYYY-MM-DD'));
	    	$('#endDate').val(picker.endDate.format('YYYY-MM-DD'));
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
                    <h1 class="page-header"><spring:message code="ask-leaving" /></h1>
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
				            			
							              <springForm:form class="form-horizontal col-md-12" action="/short-term-employee/addLeave" modelAttribute="leave" method="POST">
								              	<springForm:errors path="" element="div" />
								              	
								              	<div class="form-group">
									                <label><spring:message code="date-range" /></label>
									                <i class="fa fa-calendar"></i>
									                <div>
										                  <springForm:input path="dateRange" name="dateRange" type="text" class="form-control pull-right" id="reservation" />
										                  <springForm:input path="startDate" name="startDate" type="text" class="form-control pull-right hidden" id="startDate"/>
										            	  <springForm:input path="endDate" name="endDate" type="text" class="form-control pull-right hidden" id="endDate"/>
									                </div>
									                <!-- /.input group -->
									            </div>

								                <div class="form-group">
								                	  <label><spring:message code="reason" /></label>
									                  <div>
									                  		<springForm:textarea path="reason" type="text" class="form-control" id="reason" name="reason" />
															<div class="alert-danger">
																<springForm:errors path="reason" cssClass="error"/>
															</div>
									                  </div>
								                </div>
								                
					                            <div class="form-group">
				                                   <div class="col-lg-9 ">
						                           		<button type="submit" class="btn btn-success"><spring:message code="apply" /></button>
						                           		<button type="reset" class="btn btn-danger"><spring:message code="reset" /></button>
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
