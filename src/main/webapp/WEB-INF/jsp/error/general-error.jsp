<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<link href="${pageContext.request.contextPath}/resources/static/css/common/AdminLTE.min.css" rel="stylesheet">
	<jsp:include page="../common/header.jsp" />
</head>

<body>

    <div id="wrapper">

        <jsp:include page="../common/nav.jsp" />
        <div id="page-wrapper">
            <!-- Main content -->
		    <section class="content">
		      <div class="error-page">
		        <!-- 
		        <c:choose>
		        	<c:when test="${currentUser == null }">
		        		is null
		        	</c:when>
		        	<c:otherwise>
		        		is not null
		        	</c:otherwise>
		        </c:choose>
		        -->
		        
		        <div class="error-content">
		          <h1 class="headline text-red"> Oops!</h1>
		          <h1><i class="fa fa-warning text-red"></i> Something went wrong!</h1>
					<p>${currentUser }</p>
		          <p>
		            Something went wrong!
		            Please check your operation, and you may try reading the <a href="/help">help page</a> to know more.
		          </p>
		
		        </div>
		        <!-- /.error-content -->
		      </div>
		      <!-- /.error-page -->
		    </section>
		    <!-- /.content -->
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->

</body>

</html>
