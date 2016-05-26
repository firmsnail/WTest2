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
		        
		        <div class="error-content">
		          <h1 class="headline text-yellow"> Oops!</h1>
		          <h1><i class="fa fa-warning text-yellow"></i> Permission Denied!</h1>
		
		          <p>
		            You have no permission to access this page.
		            Meanwhile, you may <a href="/">return to home-page</a> or try reading the <a href="/help">help page</a> to know more.
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
