<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% 
	session.invalidate(); 
%>
<html>
	<head>
	</head>
	<body >
		<script>
			top.document.location = '<%= request.getContextPath()%>/Login.htm';
		</script>
	</body>
</html>
