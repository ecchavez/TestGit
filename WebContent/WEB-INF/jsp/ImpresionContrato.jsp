<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Cache-Control" Content="no-cache">
<meta http-equiv="Pragma" Content="no-cache">
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>
<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script> 
    <script src="source/kendo.all.js"></script>

<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />


<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/contratos/ContratosJQ.js"></script>




<title>Impresion</title>
</head>
<body>
<!--<input id="idPedido" name="idPedido" class="k-textbox" onKeyPress="captureEnteros(this,event);"/>-->
<!--<input type="button" class="k-button" name="btn" id="btn" value="Contrato" onClick="impresion()"/>-->
<!--<div id="windowLogErrores">-->
</body>
</html>