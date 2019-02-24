<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Nuevo Cliente</title>

<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>

<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo/styles/examples.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>/kendo/styles/main.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script>
		$(document).ready(function(){	    
		  
		});	
</script>

</head>
  <body>
  	<c:if test="${objectDatosImagen.mensaje eq 'FAULT'}">
  		<script>
  		    window.parent.$("#mensajes_main").html('Error: <c:out value="${objectDatosImagen.descripcion}"/>');
  			window.parent.closeMapingWindow();
  		</script>
  	</c:if> 
  	<c:if test="${objectDatosImagen.mensaje eq 'SUCCESS'}">
  		<script>
  		    window.parent.$("#mensajes_main").html('<c:out value="${objectDatosImagen.descripcion}"/>');
  			window.parent.closeMapingWindow();
  		</script>
  	</c:if> 
  	<div id="buttomBar">
  	<div class="console"/>
  </div>
  </body>
</html>
