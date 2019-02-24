<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<%
String path = request.getContextPath(),Val=request.getParameter("id"),Act=request.getParameter("act");
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>
<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
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
if('<%=Act%>'=='up')
{
document.getElementById('divEliminar').style.visibility='hidden';
document.getElementById('datosConfirm').style.visibility='visible';
}
else{
document.getElementById('divEliminar').style.visibility='visible';
document.getElementById('datosConfirm').style.visibility='hidden';
}
});	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Confirmacion</title>

</head>
<body>
 
  <div id="divEliminar">
  <table align="center">
  <tr>
    <td colspan="2" align="center"><a><font size="+1">Confirmaci&oacute;n de eliminaci&oacute;n</font></a></td>
  </tr>
   <tr>
    <td colspan="2">¿Esta seguro que desea eliminar al cliente? <%=Val%><br></td>
  </tr>
   <tr>
  <td align="center"><input type="button" class="k-button" name="borrar" id="borrar" value="Borrar" onClick="confirma()"/></td>
  <td align="center"><input type="button" class="k-button" name="cacelar" id="cancelar" value="Cancelar" onClick="cancelarCart('windowConfirmBorrado')"/></td>  
  </tr>
   </table>
   </div>
   <div id="datosConfirm"></div>
 
  
        
  </body>
</html>
