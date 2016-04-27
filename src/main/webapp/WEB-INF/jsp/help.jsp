<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="./common/header.jsp" />
	<link href="${pageContext.request.contextPath}/resources/static/css/common/timeline.css" rel="stylesheet">
	
	<link href="${pageContext.request.contextPath}/resources/static/css/common/morris.css" rel="stylesheet">
	
</head>

<body>

    <div id="wrapper">

        <jsp:include page="./common/nav.jsp" />
        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Help</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <div class="col-lg-12">
                <div>
                	Can I help you?
                </div>
            </div>
            
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->


</body>

</html>