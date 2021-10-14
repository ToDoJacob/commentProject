<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jsp page</title>
</head>
<body>
	<%
		String userId = request.getParameter("user");
	%>
	
	<script>
		console.log('user정보: <%=userId%>');
		document.write('<%=userId%>');
	</script>
</body>
</html>