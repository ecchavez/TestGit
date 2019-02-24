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
<title>Confirmacion</title>

</head>
<body>
  
   <div id="datosConfirm">
   <table align="center">
   
   <tr>
    <td colspan="2" align="center"><a><font size="+1">Autorizacion</font></a></td>
  </tr>
  <tr>
    <td colspan="2">Si se Acepta ,se actualizaran los cambios realizados para el cliente </td>
  </tr>
  <tr>
  <td>
  Usr
  </td>
  <td>
  <input type="text" id="usr" name="usr"/>
  </td>
  </tr>
  <tr>
  <td>
  Pwd
  </td>
  <td>
  <input type="password" id="pwd"  name="pwd" />
  </td>
  </tr>
  <tr>
  <td>
   <input type="button" id="save"  name="save" class="k-button" value="Aceptar" onclick="validaAutorizacion()"/>
  </td>
  <td>
  <input type="button" id="cancelar"  name="cancelar" class="k-button" value="Cancelar" onclick="cancelarCart('autorizacion')"/>
  </td>
  </tr>
  </table>
 </div>

  
        
  </body>
</html>
