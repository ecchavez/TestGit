<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<%
String path = request.getContextPath(),Val=request.getParameter("id"),Act=request.getParameter("act");
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>
<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/js/jquery.blockUI.js"></script>
<script src="<%=basePath %>/js/utils.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>
<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />
<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/clientes/ClientesJQ.js"></script>
<script>
$(document).ready(function(){

});	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aviso de Registro de visita</title>

</head>

<body>
<form>
  <table width="309" border="0" align="center">
    <tr>
      <td width="333" height="46" align="center"><b><font size="+1">Visita registrada con ex&iacute;to</font></b></td>
    </tr>
    <tr>
      <td align="center"><input type="button" id="cancelar"  name="cancelar" class="k-button" value="Aceptar" onclick="cerrarAvisoVisita()"/></td>
    </tr>
  </table>
   
</form>
</body>
</html>