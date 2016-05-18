<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<link href="${pageContext.request.contextPath}/resources/static/css/common/AdminLTE.min.css" rel="stylesheet">
	<jsp:include page="../common/header.jsp" />
	<script src="${pageContext.request.contextPath}/resources/static/js/common/jquery.dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/dataTables.bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/static/css/common/dataTables.bootstrap.css" rel="stylesheet">
	
	<script>
	    $(document).ready(function() {
	        $('#dataTables-example').DataTable({
	                responsive: false
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
                    <h1 class="page-header">User Profile</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div <c:choose><c:when test="${currentUser.user.personId == curUserId}">class="col-md-3"</c:when><c:otherwise>class="col-md-9"</c:otherwise></c:choose> >

					<!-- Profile Image -->
					<div class="box box-primary">
						<div class="box-body box-profile">
							<img class="profile-user-img img-responsive img-circle" src="http://www.gravatar.com/avatar/00000000000000000000000000000000?d=mm&f=y" alt="User profile picture">
							
							<h3 class="profile-username text-center">${currentUser.user.firstName} ${currentUser.user.lastName}</h3>
							
							<p class="text-muted text-center">
								<c:choose>
									<c:when test="${currentUser.user.role.roleId == 1}">
										HR Manager
									</c:when>
									<c:when test="${currentUser.user.role.roleId == 2}">
										Recruiter
									</c:when>
									<c:when test="${currentUser.user.role.roleId == 3}">
										C&B Specialist
									</c:when>
									<c:when test="${currentUser.user.role.roleId == 4}">
										Staff Team Manager
									</c:when>
									<c:otherwise>
										Short-term Employee
									</c:otherwise>
								</c:choose>
							</p>

						</div>
						<!-- /.box-body -->
					</div>
					<!-- /.box -->
					
					<!-- About Me Box -->
					<div class="box box-primary">
						<div class="box-header with-border">
							<h3 class="box-title">About Me</h3>
						</div>
						<!-- /.box-header -->
						<div class="box-body">
							<strong><i class="fa fa-home margin-r-5"></i> Department</strong>
              				<p class="text-muted">
              					<c:choose>
									<c:when test="${currentUser.user.department != null}">
										<a href="/department/showOneDepartment">
											${currentUser.user.department.departmentName}
										</a>
										${currentUser.user.email}
									</c:when>
									<c:otherwise>
										Unknown
									</c:otherwise>
								</c:choose>
              				</p>
              				<hr>
							<strong><i class="fa fa-user-md margin-r-5"></i> Gender</strong>
							<p class="text-muted">
								<c:choose>
									<c:when test="${currentUser.user.gender == 1}">
										Female
									</c:when>
									<c:when test="${currentUser.user.gender == 2}">
										Male
									</c:when>
									<c:otherwise>
										Unknown
									</c:otherwise>
								</c:choose>
							</p>
							<hr>
							<strong><i class="fa fa-child margin-r-5"></i> Age</strong>
							<p class="text-muted">
								<c:choose>
									<c:when test="${currentUser.user.age != null}">
										${currentUser.user.age}
									</c:when>
									<c:otherwise>
										Unknown
									</c:otherwise>
								</c:choose>
							</p>
							<hr>
							<strong><i class="fa fa-map-marker margin-r-5"></i> Location</strong>
              				<p class="text-muted">
              					<c:choose>
									<c:when test="${currentUser.user.address != null}">
										${currentUser.user.address}
									</c:when>
									<c:otherwise>
										Unknown
									</c:otherwise>
								</c:choose>
              				</p>
              				<hr>
              				<strong><i class="fa fa-mobile margin-r-5"></i> Mobile Phone</strong>
              				<p class="text-muted">
              					<c:choose>
									<c:when test="${currentUser.user.phone != null}">
										${currentUser.user.phone}
									</c:when>
									<c:otherwise>
										Unknown
									</c:otherwise>
								</c:choose>
              				</p>
              				<hr>
              				<strong><i class="fa fa-envelope-o margin-r-5"></i> Email</strong>
              				<p class="text-muted">
              					<c:choose>
									<c:when test="${currentUser.user.email != null}">
										${currentUser.user.email}
									</c:when>
									<c:otherwise>
										Unknown
									</c:otherwise>
								</c:choose>
              				</p>
              				<hr>
              				<strong><i class="fa fa-money margin-r-5"></i> Salary</strong>
              				<p class="text-muted">
              					<c:choose>
									<c:when test="${currentUser.user.salary != null}">
										${currentUser.user.salary}
									</c:when>
									<c:otherwise>
										Unknown
									</c:otherwise>
								</c:choose>
              				</p>
              				<hr>
              				<strong><i class="fa fa-lightbulb-o margin-r-5"></i> Status</strong>
              				<p class="text-muted">
              					<c:choose>
									<c:when test="${currentUser.user.status != null}">
										<c:choose>
											<c:when test="${currentUser.user.status == 0}">
												Registered
											</c:when>
											<c:when test="${currentUser.user.status == 1}">
												Candidate
											</c:when>
											<c:when test="${currentUser.user.status == 2}">
												Working
											</c:when>
											<c:otherwise>
												Dismiss
											</c:otherwise>
										</c:choose>
										${currentUser.user.salary}
									</c:when>
									<c:otherwise>
										Unknown
									</c:otherwise>
								</c:choose>
              				</p>
              				<hr>
              				<strong><i class="fa fa-clock-o margin-r-5"></i> Period</strong>
              				<p class="text-muted">
              					<c:choose>
									<c:when test="${currentUser.user.startDate != null}">
										<fmt:formatDate value="${currentUser.user.startDate}" pattern="yyyy-MM-dd"/> ~ <fmt:formatDate value="${currentUser.user.endDate}" pattern="yyyy-MM-dd"/>
									</c:when>
									<c:otherwise>
										Unknown
									</c:otherwise>
								</c:choose>
              				</p>
              				<hr>
							<strong><i class="fa fa-pencil margin-r-5"></i> Skills</strong>
							<p>
								<c:choose>
									<c:when test="${skills != null and fn:length(skills) > 0}">
										<c:forEach var="skill" items="${skills}">
											<span class="label label-success">skill.skillName</span>
										</c:forEach>
									</c:when>
									<c:otherwise>
										None
									</c:otherwise>
								</c:choose>
							</p>
						</div>
						<!-- /.box-body -->
					</div>
					<!-- /.box -->
				</div>
				<!-- /.col -->
		        <div class="col-md-9" <c:if test="${currentUser.user.personId != curUserId}">hidden="hidden"</c:if>>
		          <div class="nav-tabs-custom">
		            <ul class="nav nav-tabs">
		              <li class="active"><a href="#timeline" data-toggle="tab">Notification</a></li>
		              <li><a href="#settings" data-toggle="tab">Settings</a></li>
		            </ul>
		            <div class="tab-content">
		              <div class="active tab-pane" id="timeline">
		                <!-- The timeline -->
		                <ul class="timeline timeline-inverse">
		                  <!-- timeline time label -->
		                  <li class="time-label">
		                        <span class="bg-red">
		                          10 Feb. 2014
		                        </span>
		                  </li>
		                  <!-- /.timeline-label -->
		                  <!-- timeline item -->
		                  <li>
		                    <i class="fa fa-envelope bg-blue"></i>
		
		                    <div class="timeline-item">
		                      <span class="time"><i class="fa fa-clock-o"></i> 12:05</span>
		
		                      <h3 class="timeline-header"><a href="#">Support Team</a> sent you an email</h3>
		
		                      <div class="timeline-body">
		                        Etsy doostang zoodles disqus groupon greplin oooj voxy zoodles,
		                        weebly ning heekya handango imeem plugg dopplr jibjab, movity
		                        jajah plickers sifteo edmodo ifttt zimbra. Babblely odeo kaboodle
		                        quora plaxo ideeli hulu weebly balihoo...
		                      </div>
		                      <div class="timeline-footer">
		                        <a class="btn btn-primary btn-xs">Read more</a>
		                        <a class="btn btn-danger btn-xs">Delete</a>
		                      </div>
		                    </div>
		                  </li>
		                  <!-- END timeline item -->
		                  <!-- timeline item -->
		                  <li>
		                    <i class="fa fa-user bg-aqua"></i>
		
		                    <div class="timeline-item">
		                      <span class="time"><i class="fa fa-clock-o"></i> 5 mins ago</span>
		
		                      <h3 class="timeline-header no-border"><a href="#">Sarah Young</a> accepted your friend request
		                      </h3>
		                    </div>
		                  </li>
		                  <!-- END timeline item -->
		                  <!-- timeline item -->
		                  <li>
		                    <i class="fa fa-comments bg-yellow"></i>
		
		                    <div class="timeline-item">
		                      <span class="time"><i class="fa fa-clock-o"></i> 27 mins ago</span>
		
		                      <h3 class="timeline-header"><a href="#">Jay White</a> commented on your post</h3>
		
		                      <div class="timeline-body">
		                        Take me to your leader!
		                        Switzerland is small and neutral!
		                        We are more like Germany, ambitious and misunderstood!
		                      </div>
		                      <div class="timeline-footer">
		                        <a class="btn btn-warning btn-flat btn-xs">View comment</a>
		                      </div>
		                    </div>
		                  </li>
		                  <!-- END timeline item -->
		                  <!-- timeline time label -->
		                  <li class="time-label">
		                        <span class="bg-green">
		                          3 Jan. 2014
		                        </span>
		                  </li>
		                  <!-- /.timeline-label -->
		                  <!-- timeline item -->
		                  <li>
		                    <i class="fa fa-camera bg-purple"></i>
		
		                    <div class="timeline-item">
		                      <span class="time"><i class="fa fa-clock-o"></i> 2 days ago</span>
		
		                      <h3 class="timeline-header"><a href="#">Mina Lee</a> uploaded new photos</h3>
		
		                      <div class="timeline-body">
		                        <img src="http://placehold.it/150x100" alt="..." class="margin">
		                        <img src="http://placehold.it/150x100" alt="..." class="margin">
		                        <img src="http://placehold.it/150x100" alt="..." class="margin">
		                        <img src="http://placehold.it/150x100" alt="..." class="margin">
		                      </div>
		                    </div>
		                  </li>
		                  <!-- END timeline item -->
		                  <li>
		                    <i class="fa fa-clock-o bg-gray"></i>
		                  </li>
		                </ul>
		              </div>
		              <!-- /.tab-pane -->
		
		              <div class="tab-pane" id="settings">
							<springForm:form class="form-horizontal" action="/user/edit" modelAttribute="user" method="POST">
								<springForm:errors path="" element="div" />
								
								<!-- text input -->
								<div class="form-group">
									  <label for="" class="col-sm-2 control-label">Total Requirement</label>
									  <div class="col-sm-10">
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
		              <!-- /.tab-pane -->
		            </div>
		            <!-- /.tab-content -->
		          </div>
		          <!-- /.nav-tabs-custom -->
		        </div>
		        <!-- /.col -->
            </div>
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->

</body>

</html>
