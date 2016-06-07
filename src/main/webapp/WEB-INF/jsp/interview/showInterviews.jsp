<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../common/header.jsp" />
	<script src="${pageContext.request.contextPath}/resources/static/js/common/jquery.dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/dataTables.bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/bootstrap-datetimepicker.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/static/css/common/dataTables.bootstrap.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/static/css/common/bootstrap-datetimepicker.min.css" rel="stylesheet"  media="screen">
	<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
	<script>
	    $(document).ready(function() {
	        $('#dataTables-example').DataTable({
	                responsive: false
	        });
	        
	        $('[data-toggle="tooltip"]').tooltip(); 
	        
	        $('.interviewTime').datetimepicker({
	            //language:  'fr',
	            format: 'yyyy-mm-dd hh:ii',
	            weekStart: 1,
	            todayBtn:  1,
	    		autoclose: 1,
	    		todayHighlight: 1,
	    		startView: 2,
	    		forceParse: 0,
	            showMeridian: 1
	        });
	        
	        $('#interviewModal').on('shown.bs.modal', function (event) {
	        	  var button = $(event.relatedTarget) // Button that triggered the modal
	        	  var curId = button.data('interviewid') // Extract info from data-* attributesdata-interviewId
	        	  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
	        	  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
	        	  var modal = $(this)
	        	  //alert(curId);
	        	  //modal.find('.modal-title').text('New message to ' + recipient)
	        	  modal.find('.modal-body input#interview').val(curId);
	        });
	        
	        $('#hireModal').on('shown.bs.modal', function (event) {
	        	  var button = $(event.relatedTarget) // Button that triggered the modal
	        	  var curId = button.data('interviewid') // Extract info from data-* attributesdata-interviewId
	        	  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
	        	  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
	        	  var modal = $(this)
	        	  //alert(curId);
	        	  //modal.find('.modal-title').text('New message to ' + recipient)
	        	  modal.find('.modal-body input#interview').val(curId);
	        });
	        
	        $('#hireForm').validate({
	        	rules: {
	        		salary: {
	        			required: true,
	        			min: 1,
	        			max: 1000
	        		}
	        	}
	        });
	    });
    </script>
</head>

<body>

	<div class="modal fade" id="interviewModal" tabindex="-1" role="dialog" aria-labelledby="interviewModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="interviewModalLabel">Schedule Interview</h4>
				</div>
				<div class="modal-body">
					<form role="form" action="/recruiter/scheduleOneInterview" method="POST">
						<div class="form-group">
							<input type="text" class="hidden" id="interview" name="interviewId" value=""></input>
						</div>
						<div class="form-group">
	                    	<label for="interviewTime" class="control-label">Interview Time:</label>
	                    	<div class="input-group date interviewTime col-md-5" data-link-field="interviewTime">
			                    <input class="form-control" type="text" value="" readonly>
			                    <span class="input-group-addon" hidden="hidden"><span class="glyphicon glyphicon-remove"></span></span>
			                </div>
	                        <input type="text" class="form-control hidden" name="interviewTime" id="interviewTime" placeholder="choose a time">
	                    </div>
	                    <div class="form-group">
	                    	<button type="submit" class="btn btn-success">Schedule</button>
	                    </div>
	                    <br />
					    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	                </form>
				</div>
				<div class="modal-footer">
					<!--
					<button type="button" class="btn btn-success" data-toggle="modal" data-target="interviewModal" data-whatever="@mdo">Schedule</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					-->
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="hireModal" tabindex="-1" role="dialog" aria-labelledby="hireModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="hireModalLabel">Hire</h4>
				</div>
				<div class="modal-body">
					<form role="form" id="hireForm" action="/team-manager/passOneInterview" method="POST">
						<div class="form-group">
							<input type="text" class="hidden" id="interview" name="interviewId" value=""></input>
						</div>
	                    <div class="form-group">
	                    	<label for="salary" class="control-label">Salary:</label>
	                        <input type="text" class="form-control" id="salary" name="salary" >
	                    </div>
	                    <div class="form-group">
	                    	<label for="period" class="control-label">Period:</label>
	                    	<select class="form-control" name="period">
	                    		  <option value=1 selected="selected">1 Month</option>
			                	  <option value=2>2 Months</option>
			                	  <option value=3>3 Months</option>
			                	  <option value=4>4 Months</option>
			                	  <option value=5>5 Months</option>
			                	  <option value=6>6 Months</option>
			                </select>
	                    </div>
	                    <div class="form-group">
	                    	<button type="submit" class="btn btn-success">Hire</button>
	                    </div>
	                    <br />
					    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	                </form>
				</div>
				<div class="modal-footer">
					<!--
					<button type="button" class="btn btn-success" data-toggle="modal" data-target="interviewModal" data-whatever="@mdo">Schedule</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					-->
				</div>
			</div>
		</div>
	</div>

    <div id="wrapper">

        <jsp:include page="../common/nav.jsp" />
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                	<h1 class="page-header">Interviews</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
							All Interviews
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<br>
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                        	<c:choose>
                                        		<c:when test="${currentUser.user.role.roleId ==2}">
                                        			<th>Update Time</th>
                                        		</c:when>
                                        		<c:otherwise>
                                        			<th>Interview Time</th>
                                        		</c:otherwise>
                                        	</c:choose>
                                            <th>Interviewee</th>
                                            <th>Interviewer</th>
                                            <th>Recruiting Plan</th>
                                            <th>Turns</th>
                                            <th>Details</th>
                                            <th>Status</th>
                                            <c:if test="${currentUser.user.role.roleId != 5}">
                                            	<th>Operation</th>
                                            </c:if>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="interview" items="${interviews}" varStatus="status">
											<tr <c:choose><c:when test="${status.index % 2 == 0}">class="odd"</c:when><c:otherwise>class="even"</c:otherwise></c:choose>>
											
												<c:choose>
	                                        		<c:when test="${currentUser.user.role.roleId ==2}">
	                                        			<td><fmt:formatDate value="${interview.updateTime}" pattern="yyyy-MM-dd hh:mm"/></td>
	                                        		</c:when>
	                                        		<c:otherwise>
	                                        			<c:choose>
	                                        				<c:when test="${interview.status == 2 }">
	                                        					<td><fmt:formatDate value="${interview.interviewTime}" pattern="yyyy-MM-dd hh:mm"/></td>
	                                        				</c:when>
	                                        				<c:otherwise>
	                                        					<td>N/A</td>
	                                        				</c:otherwise>
	                                        			</c:choose>
	                                        		</c:otherwise>
	                                        	</c:choose>
												<td><a href="/user/showOneEmployee?personId=${interview.interviewee.personId}">${interview.interviewee.firstName} ${interview.interviewee.lastName}</a></td>
												<td><a href="/user/showOneEmployee?personId=${interview.interviewer.personId}">${interview.interviewer.firstName} ${interview.interviewer.lastName}</a></td>
												<td><a href="/plan/showOneRecruitingPlan?planId=${interview.planForInterview.planId }"><i class="fa fa-search fa-fw"></i> Plan Details</a></td>
												<td>${interview.turns}</td>
												<td><a href="/interview/showOneInterview?interviewId=${interview.interviewId }"><i class="fa fa-search fa-fw"></i> See Details</a></td>
											
												<td>
													<c:choose>
														<c:when test="${currentUser.user.role.roleId == 2 or currentUser.user.role.roleId == 4}">
															<c:choose>
																<c:when test="${interview.status == 1}">		<!-- INTERVIEW_PENDING_SCHEDULE -->
																	<span class="label label-warning">Pending Schedule</span>
																</c:when>
																<c:when test="${interview.status == 2}">		<!-- INTERVIEW_INTERVIEWING -->
																	<span class="label label-success">Interviewing</span>
																</c:when>
																<c:otherwise>
																	Unknown
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:when test="${currentUser.user.role.roleId == 5}">
															<c:choose>
																<c:when test="${interview.status == 1}">		<!-- INTERVIEW_PENDING_SCHEDULE -->
																	<span class="label label-warning">Pending Schedule</span>
																</c:when>
																<c:when test="${interview.status == 2}">		<!-- INTERVIEW_INTERVIEWING -->
																	<span class="label label-primary">Interviewing</span>
																</c:when>
																<c:when test="${interview.status == 4}">		<!-- INTERVIEW_PASSED -->
																	<span class="label label-success">Passed</span>
																</c:when>
																<c:otherwise>
																	<span class="label label-danger">Failed</span>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															Unknown
														</c:otherwise>
													</c:choose>
												</td>
												<c:if test="${currentUser.user.role.roleId != 5 }">
													<td>
														<c:choose>
															<c:when test="${currentUser.user.role.roleId == 2}">
																<c:choose>
																	<c:when test="${interview.status == 1}">		<!-- APPLY_PENDING_FILTER -->
																		<button type="button" class="btn btn-success" data-toggle="modal" data-target="#interviewModal" data-interviewid="${interview.interviewId }">Schedule</button>
																		<a href="#" data-toggle="tooltip" title="Automatically schedule interviews. It is not implemented.">
																			<button type="button" class="btn btn-success disabled">Auto Schedule</button>
																		</a>
																	</c:when>
																	<c:otherwise>
																		<button type="button" class="btn btn-success disabled">Schedule</button>
																		<a href="#" data-toggle="tooltip" title="Automatically schedule interviews. It is not implemented.">
																			<button type="button" class="btn btn-success disabled">Auto Schedule</button>
																		</a>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:when test="${currentUser.user.role.roleId == 4}">
																<!-- TODO Hire-->
																<c:choose>
																	<c:when test="${interview.status == 2 }">
																		<a href="/team-manager/addOneInterview?interviewId=${interview.interviewId }"><button type="button" class="btn btn-primary">More</button></a>
																		<button type="button" class="btn btn-success" data-toggle="modal" data-target="#hireModal" data-interviewid="${interview.interviewId }">Hire</button>
																		<!-- <a href="/team-manager/passOneInterview?interviewId=${interview.interviewId }"><button type="button" class="btn btn-success">Hire</button></a>-->
																		<a href="/team-manager/failOneInterview?interviewId=${interview.interviewId }"><button type="button" class="btn btn-danger">Fail</button></a>
																	</c:when>
																	<c:otherwise>
																		<button type="button" class="btn btn-primary disabled">More</button>
																		<button type="button" class="btn btn-success disabled">Hire</button>
																		<button type="button" class="btn btn-danger disabled">Fail</button>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																Unknown
															</c:otherwise>
														</c:choose>
													</td>
												</c:if>
											</tr>
                                    	</c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->

</body>

</html>
