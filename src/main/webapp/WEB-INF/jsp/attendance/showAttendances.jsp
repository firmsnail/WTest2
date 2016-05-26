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
	<link href="${pageContext.request.contextPath}/resources/static/css/common/dataTables.bootstrap.css" rel="stylesheet">
	<script>
	    $(document).ready(function() {
	        $('#dataTables-example').DataTable({
	                responsive: false
	        });
	        $('[data-toggle="tooltip"]').tooltip(); 
	    });
    </script>
</head>

<body>

    <div id="wrapper">

        <jsp:include page="../common/nav.jsp" />
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Attendances</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            All Attendances
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        	<div>
                        		<a href="#" data-toggle="tooltip" title="Import attendance records from paper. It is not implemented.">
									<button type="button" class="btn btn-success disabled">Import Attendance Records</button>
								</a>
                        	</div>
                        	<br>
                            <div class="dataTable_wrapper">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                        	<th>Attendance Date</th>
                                        	<th>Attendance Time</th>
                                            <th>Leave Time</th>
                                            <th>Attendance Department</th>
                                            <th>Attendance Person</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach var="attendance" items="${attendances}" varStatus="status">
											<tr <c:choose><c:when test="${status.index % 2 == 0}">class="odd"</c:when><c:otherwise>class="even"</c:otherwise></c:choose>>
												<td><fmt:formatDate value="${attendance.attendanceDate}" pattern="yyyy-MM-dd"/></td>
												<td><fmt:formatDate value="${attendance.attendanceTime}" pattern="yyyy-MM-dd hh:mm"/></td>
												<td><fmt:formatDate value="${attendance.leaveTime}" pattern="yyyy-MM-dd hh:mm"/></td>
												<td><a href="/department/showOneDepartment?departmentId=${attendance.attendanceDepartment.departmentId}">${attendance.attendanceDepartment.departmentName}</a></td>
												<td><a href="/user/showOneEmployee?personId=${attendance.attendancePerson.personId}">${attendance.attendancePerson.firstName} ${attendance.attendancePerson.lastName}</a></td>
												<!-- <td><a href="/hire/showOneDismission?dismissionId=${dismission.dismissionId }"><i class="fa fa-search fa-fw"></i> See Details</a></td>-->
												
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
