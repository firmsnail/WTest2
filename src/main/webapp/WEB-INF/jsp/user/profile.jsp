<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<link href="${pageContext.request.contextPath}/resources/static/css/common/AdminLTE.min.css" rel="stylesheet">
	<jsp:include page="../common/header.jsp" />
	<script src="${pageContext.request.contextPath}/resources/static/js/common/jquery.dataTables.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/dataTables.bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/static/js/common/select2.full.min.js"></script>
	
	<link href="${pageContext.request.contextPath}/resources/static/css/common/dataTables.bootstrap.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/static/css/common/select2.min.css" rel="stylesheet">
	
	
	<script>
	    $(document).ready(function() {
	    	
	    	//Initialize Select2 Elements
		    $(".select2").select2();
	        $('#dataTables-example').DataTable({
	                responsive: false
	        });
	       	/*var url = document.location.toString();
	       	if (url.match('#')) {
	       		var tabName = url.split('#')[1];
	       		if (tabName == "settings") {
	       			$('#li-timeline').removeClass('active');
	       			$('#timeline').removeClass('active');
	       			$('#li-settings').addClass('active');
	       			$('#settings').addClass('active');
	       		} else {
	       			$('#li-settings').removeClass('active');
	       			$('#settings').removeClass('active');
	       			$('#li-timeline').addClass('actvie');
	       			$('#timeline').addClass('active');
	       		}
	       	}*/
	       	var isSetting = $('#isSet').val();
	       	if (isSetting == "true") {
	       		$('#li-timeline').removeClass('active');
       			$('#timeline').removeClass('active');
       			$('#li-settings').addClass('active');
       			$('#settings').addClass('active');
	       	}
	    });
	    
    </script>
</head>

<body>

    <div id="wrapper">

        <jsp:include page="../common/nav.jsp" />
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header"><spring:message code="profile" /></h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div <c:choose><c:when test="${currentUser.user.personId == curUserId}">class="col-md-3"</c:when><c:otherwise>class="col-md-9"</c:otherwise></c:choose> >

					<!-- Profile Image -->
					<div class="box box-primary">
						<div class="box-body box-profile">
							<img class="profile-user-img img-responsive img-circle" src="http://www.gravatar.com/avatar/00000000000000000000000000000000?d=mm&f=y" alt="User profile picture">
							
							<h3 class="profile-username text-center">${user.firstName} ${user.lastName}</h3>
							
							<p class="text-muted text-center">
								<c:choose>
									<c:when test="${user.role.roleId == 1}">
										<spring:message code="role.hrm" />
									</c:when>
									<c:when test="${user.role.roleId == 2}">
										<spring:message code="role.recruiter" />
									</c:when>
									<c:when test="${user.role.roleId == 3}">
										<spring:message code="role.specialist" />
									</c:when>
									<c:when test="${user.role.roleId == 4}">
										<spring:message code="role.team-manager" />
									</c:when>
									<c:otherwise>
										<spring:message code="role.short-term-employee" />
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
							<h3 class="box-title"><spring:message code="about-me" /></h3>
						</div>
						<!-- /.box-header -->
						<div class="box-body">
							<strong><i class="fa fa-home margin-r-5"></i> <spring:message code="department" /></strong>
              				<p class="text-muted">
              					<c:choose>
									<c:when test="${user.department != null}">
										<c:choose>
											<c:when test="${currentUser == null or currentUser.user.role.roleId == 5 }">
												${user.department.departmentName}
											</c:when>
											<c:otherwise>
												<a href="/department/showOneDepartment?departmentId=${user.department.departmentId }">
													${user.department.departmentName}
												</a>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<spring:message code="unknown" />
									</c:otherwise>
								</c:choose>
              				</p>
              				<hr>
							<strong><i class="fa fa-user-md margin-r-5"></i> <spring:message code="gender" /></strong>
							<p class="text-muted">
								<c:choose>
									<c:when test="${user.gender == 1}">
										<spring:message code="female" />
									</c:when>
									<c:when test="${user.gender == 2}">
										<spring:message code="male" />
									</c:when>
									<c:otherwise>
										<spring:message code="unknown" />
									</c:otherwise>
								</c:choose>
							</p>
							<hr>
							<strong><i class="fa fa-child margin-r-5"></i> <spring:message code="age" /></strong>
							<p class="text-muted">
								<c:choose>
									<c:when test="${user.age != null}">
										${user.age}
									</c:when>
									<c:otherwise>
										<spring:message code="unknown" />
									</c:otherwise>
								</c:choose>
							</p>
							<hr>
							<strong><i class="fa fa-map-marker margin-r-5"></i> <spring:message code="location" /></strong>
              				<p class="text-muted">
              					<c:choose>
									<c:when test="${user.address != null}">
										${user.address}
									</c:when>
									<c:otherwise>
										<spring:message code="unknown" />
									</c:otherwise>
								</c:choose>
              				</p>
              				<hr>
              				<strong><i class="fa fa-mobile margin-r-5"></i> Mobile Phone</strong>
              				<p class="text-muted">
              					<c:choose>
									<c:when test="${user.phone != null}">
										${user.phone}
									</c:when>
									<c:otherwise>
										<spring:message code="unknown" />
									</c:otherwise>
								</c:choose>
              				</p>
              				<hr>
              				<strong><i class="fa fa-envelope-o margin-r-5"></i> <spring:message code="email" /></strong>
              				<p class="text-muted">
              					<c:choose>
									<c:when test="${user.email != null}">
										${user.email}
									</c:when>
									<c:otherwise>
										<spring:message code="unknown" />
									</c:otherwise>
								</c:choose>
              				</p>
              				<hr>
              				<strong><i class="fa fa-money margin-r-5"></i> <spring:message code="salary" /></strong>
              				<p class="text-muted">
              					<c:choose>
              						<c:when test="${user.salary == null or (currentUser.user.role.roleId == 5 and currentUser.user.personId != user.personId) }">
              							<spring:message code="unknown" />
              						</c:when>
									<c:otherwise>
										${user.salary}
									</c:otherwise>
								</c:choose>
              				</p>
              				<hr>
              				<strong><i class="fa fa-lightbulb-o margin-r-5"></i> <spring:message code="status" /></strong>
              				<p class="text-muted">
              					<c:choose>
									<c:when test="${user.status != null}">
										<c:choose>
											<c:when test="${user.status == 0}">
												<spring:message code="registered" />
											</c:when>
											<c:when test="${user.status == 1}">
												<spring:message code="candidate" />
											</c:when>
											<c:when test="${user.status == 2}">
												<spring:message code="working" />
											</c:when>
											<c:otherwise>
												<spring:message code="dismiss" />
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<spring:message code="unknown" />
									</c:otherwise>
								</c:choose>
              				</p>
              				<hr>
              				<strong><i class="fa fa-clock-o margin-r-5"></i> <spring:message code="period" /></strong>
              				<p class="text-muted">
              					<c:choose>
									<c:when test="${user.startDate != null}">
										<fmt:formatDate value="${user.startDate}" pattern="yyyy-MM-dd"/> ~ <fmt:formatDate value="${user.endDate}" pattern="yyyy-MM-dd"/>
									</c:when>
									<c:otherwise>
										<spring:message code="unknown" />
									</c:otherwise>
								</c:choose>
              				</p>
              				<hr>
							<strong><i class="fa fa-pencil margin-r-5"></i> <spring:message code="skills" /></strong>
							<p>
								<c:choose>
									<c:when test="${curSkillsOb != null and fn:length(curSkillsOb) > 0}">
										<c:forEach var="skill" items="${curSkillsOb}">
											<span class="label label-success">${skill.skillName}</span>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<spring:message code="none" />
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
		          	<div hidden="hidden">
		          		<input type="text" name="isSet" id="isSet" value="${isSet}"></input>
		          	</div>
		          	
		            <ul class="nav nav-tabs">
		              <li class="active" id="li-timeline"><a href="#timeline" data-toggle="tab"><spring:message code="notification" /></a></li>
		              <li id="li-settings"><a href="#settings" data-toggle="tab"><spring:message code="settings" /></a></li>
		            </ul>
		            <div class="tab-content">
			              <div class="active tab-pane" id="timeline">
							<c:choose>
								<c:when test="${notifications == null or fn:length(notifications) == 0 }">
									<h2><spring:message code="no-notification" /></h2>
								</c:when>
								<c:otherwise>
									<ul class="timeline timeline-inverse">
										<c:forEach var="notification" items="${notifications}" varStatus="status">
				                	  		<li>
				                	  			<c:set var="showColor" value="bg-blue" />
				                	  			<c:choose>
				                	  				<c:when test="${isRead[notification.notificationId] == true}">
				                	  					<c:set var="showColor" value="bg-default" />
				                	  				</c:when>
				                	  				<c:when test="${notification.urgency == 1 }">
				                	  					<c:set var="showColor" value="bg-blue" />
				                	  				</c:when>
				                	  				<c:when test="${notification.urgency == 2 }">
				                	  					<c:set var="showColor" value="bg-yellow" />
				                	  				</c:when>
				                	  				<c:otherwise>
				                	  					<c:set var="showColor" value="bg-red" />
				                	  				</c:otherwise>
				                	  			</c:choose>
				                	  		<!-- 
				                	  		
				                	  		final static public Integer NOTIFICATION_TYPE_REQUIREMENT = 1;
											final static public Integer NOTIFICATION_TYPE_PLAN = 2;
											final static public Integer NOTIFICATION_TYPE_HIRE = 3;
											final static public Integer NOTIFICATION_TYPE_DISMISSION = 4;
											final static public Integer NOTIFICATION_TYPE_LEAVE = 5;
											final static public Integer NOTIFICATION_TYPE_INTERVIEW = 6;
				                	  		
				                	  		 -->
				                	  			
				                	  			<c:choose>
			                	  					<c:when test="${notification.type == 1}">
			                	  						<i class="fa fa-users ${showColor }"></i>
			                	  					</c:when>
			                	  					<c:when test="${notification.type == 2}">
			                	  						<i class="fa fa-list ${showColor }"></i>
			                	  					</c:when>
			                	  					<c:when test="${notification.type == 3}">
			                	  						<i class="fa fa-user ${showColor }"></i>
			                	  					</c:when>
			                	  					<c:when test="${notification.type == 4}">
			                	  						<i class="fa fa-fire ${showColor }"></i>
			                	  					</c:when>
			                	  					<c:when test="${notification.type == 5}">
			                	  						<i class="fa fa-send-o ${showColor }"></i>
			                	  					</c:when>
			                	  					<c:otherwise>
			                	  						<i class="fa fa-skype ${showColor }"></i>
			                	  					</c:otherwise>
			                	  				</c:choose>
				                	  			
				                	  			<div class="timeline-item">
				                	  				<span class="time"><i class="fa fa-clock-o"></i> <fmt:formatDate value="${notification.issueTime}" pattern="yyyy-MM-dd hh:mm"/></span>
				                	  				<h3 class="timeline-header">
				                	  					<c:choose>
					                	  					<c:when test="${notification.type == 1}">
					                	  						<spring:message code="staffing-requirement-notification" />
					                	  					</c:when>
					                	  					<c:when test="${notification.type == 2}">
					                	  						<spring:message code="recruiting-plan-notification" />
					                	  					</c:when>
					                	  					<c:when test="${notification.type == 3}">
					                	  						<spring:message code="hire-notification" />
					                	  					</c:when>
					                	  					<c:when test="${notification.type == 4}">
					                	  						<spring:message code="dismission-request-notification" />
					                	  					</c:when>
					                	  					<c:when test="${notification.type == 5}">
					                	  						<spring:message code="leaving-request-notification" />
					                	  					</c:when>
					                	  					<c:otherwise>
					                	  						<spring:message code="interview-notification" />
					                	  					</c:otherwise>
					                	  				</c:choose>
				                	  				</h3>
				                	  				<div class="timeline-body">
					                	  				<c:choose>
					                	  					<c:when test="${notification.status == 1}">
					                	  						<strong>
					                	  							${notification.content }
					                	  						</strong>
					                	  					</c:when>
					                	  					<c:otherwise>
					                	  						${notification.content }
					                	  					</c:otherwise>
					                	  				</c:choose>
					                	  			</div>
					                	  			<div class="timeline-footer">
					                	  				<a class="btn btn-primary btn-xs" href="${notification.url }"><spring:message code="view-details" /></a>
					                	  			</div>
				                	  			</div>
				                	  		</li>
					                	</c:forEach>
									</ul>
								</c:otherwise>
							</c:choose>
			              </div>
			              <!-- /.tab-pane -->
		
		              <div class="tab-pane" id="settings">
							<springForm:form class="form-horizontal" action="/user/edit" modelAttribute="userForm" method="POST">
								<springForm:errors path="" element="div" />
								
								<div class="form-group">
		                             <label class="control-label col-sm-2" for="firstName"><spring:message code="first-name" /></label>
		                             <div class="col-sm-10">
		                               		<springForm:input path="firstName" type="text" class="form-control" id="firstName" name="firstName" value="${user.firstName }"/>
		                               		<div class="alert-danger">
		                               			<springForm:errors path="firstName" cssClass="error"/>
		                               		</div>
		                             </div>
	                           </div>
	                           
	                           <div class="form-group">
		                             <label class="control-label col-sm-2" for="lastName"><spring:message code="last-name" /></label>
		                             <div class="col-sm-10">
		                               		<springForm:input path="lastName" type="text" class="form-control" id="lastName" name="lastName" value="${user.lastName }"/>
		                               		<div class="alert-danger">	
		                               			<springForm:errors path="lastName" cssClass="error"/>
		                               		</div>
		                             </div>
	                           </div>
								
								<!-- text input -->
								<div class="form-group">
									  <label for="email" class="col-sm-2 control-label"><spring:message code="email" /></label>
									  <div class="col-sm-10">
											<springForm:input path="email" type="text" class="form-control" id="email" name="email" value="${user.email }"/>
											<div class="alert-danger">
												<springForm:errors path="email" cssClass="error"/>
											</div>
									  </div>
								</div>
								
								<div class="form-group">
									  <label for="gender" class="col-sm-2 control-label"><spring:message code="gender" /></label>
									  <div class="col-sm-10">
											<springForm:select path="gender" class="form-control select2" name="gender" value="${user.gender }" style="width: 100%;">
							                	  <option <c:if test="${user.gender != 1  and user.gender != 2}">selected="selected"</c:if>></option>
							                	  <option value=1 <c:if test="${user.gender == 1}">selected="selected"</c:if>>Female</option>
							                	  <option value=2 <c:if test="${user.gender == 2}">selected="selected"</c:if>>Male</option>
							                </springForm:select>
											
											<div class="alert-danger">
												<springForm:errors path="gender" cssClass="error"/>
											</div>
									  </div>
								</div>
								
								<div class="form-group">
									  <label for="age" class="col-sm-2 control-label"><spring:message code="age" /></label>
									  <div class="col-sm-10">
											<springForm:input path="age" type="text" class="form-control" id="age" name="age" value="${user.age }"/>
											<div class="alert-danger">
												<springForm:errors path="age" cssClass="error"/>
											</div>
									  </div>
								</div>
								
								<div class="form-group">
									  <label for="address" class="col-sm-2 control-label"><spring:message code="address" /></label>
									  <div class="col-sm-10">
											<springForm:input path="address" type="text" class="form-control" id="address" name="address" value="${user.address }"/>
											<div class="alert-danger">
												<springForm:errors path="address" cssClass="error"/>
											</div>
									  </div>
								</div>
								
								<div class="form-group">
									  <label for="phone" class="col-sm-2 control-label"><spring:message code="mobile-phone" /></label>
									  <div class="col-sm-10">
											<springForm:input path="phone" type="text" class="form-control" id="phone" name="phone" value="${user.phone }"/>
											<div class="alert-danger">
												<springForm:errors path="phone" cssClass="error"/>
											</div>
									  </div>
								</div>
								
								<div class="form-group">
									  <label for="skills" class="col-sm-2 control-label"><spring:message code="skills" /></label>
									  <div class="col-sm-10">
											<springForm:select path="skills" class="form-control select2" multiple="multiple" data-placeholder="Select skills" name="skills" style="width: 100%;">
												  <c:forEach var="skill" items="${allSkills}" varStatus="status">
							                	  		<c:set var="contains" value="false" />
														<c:forEach var="item" items="${curSkills}">
															  <c:if test="${item == skill.skillId}">
															  	<c:set var="contains" value="true" />
															  </c:if>
														</c:forEach>
							                	  		<option style="background: green" value="${skill.skillId}" <c:if test="${contains}">selected="selected"</c:if> >
							                	  			<!-- ${skill.skillName}-->
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
									  <label for="password" class="col-sm-2 control-label"><spring:message code="password" /></label>
									  <div class="col-sm-10">
											<springForm:input path="password" type="text" class="form-control" id="password" name="password"/>
											<div class="alert-danger">
												<springForm:errors path="password" cssClass="error"/>
											</div>
									  </div>
								</div>
								<div class="form-group">
									  <label for="confirmPassword" class="col-sm-2 control-label"><spring:message code="conf-password" /></label>
									  <div class="col-sm-10">
											<springForm:input path="confirmPassword" type="text" class="form-control" id="confirmPassword" name="confirmPassword"/>
											<div class="alert-danger">
												<springForm:errors path="confirmPassword" cssClass="error"/>
											</div>
									  </div>
								</div>
								
								<div class="form-group">
								   <div class="col-sm-offset-2 col-sm-10">
										<button type="submit" class="btn btn-danger"><spring:message code="submit" /></button>
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
