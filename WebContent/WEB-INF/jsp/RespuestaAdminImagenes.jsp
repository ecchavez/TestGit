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
<script src="jquery-1.7.1.js"></script>
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script>
		$(document).ready(function(){	    
		  
		});	
</script>

</head>
  <body>
  	<c:if test="${responseDatosMapingActionDto.responseUbicacionDatosMapaDto.mensaje eq 'FAULT'}">
  		<script>
  		    window.parent.$("#mensajes_main").html('Error: <c:out value="${responseDatosMapingActionDto.responseUbicacionDatosMapaDto.descripcion}"/>');
  			window.parent.closeMapingWindow();
  		</script>
  	</c:if> 
  	<c:if test="${responseDatosMapingActionDto.responseUbicacionDatosMapaDto.mensaje eq 'SUCCESS'}">
  		<script>
  		    window.parent.$("#mensajes_main").html('<c:out value="${responseDatosMapingActionDto.responseUbicacionDatosMapaDto.descripcion}"/>');
  			window.parent.closeMapingWindow();
  		</script>
  	</c:if> 
  </body>
</html>
