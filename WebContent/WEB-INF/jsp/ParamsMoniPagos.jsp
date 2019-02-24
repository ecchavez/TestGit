<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
String grid=request.getParameter("queGrid");
%>
		<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
		<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
		<script src="<%=basePath %>/kendo/js/console.js"></script>
		<script type="text/javascript">var contexPath="<%=basePath %>";</script>
		<script src="<%=basePath %>/js/buttons/buttons_state.js"></script>
		<script src="<%=basePath %>/js/cierreVenta/monitorPagosJQ.js"></script>
		<link href="<%=basePath %>/kendo/styles/kendo.common.min.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=basePath %>/kendo/styles/examples.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath %>/kendo/styles/main.css" rel="stylesheet"
			type="text/css" />

		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title><%=grid%></title>
		<script>
           
              $(document).ready(function () {
           	    cargaGridMonitor('<%=grid%>');
         });

</script>
		<style>
.disabled {
	text-decoration: line-through;
	color: red
}
</style>
	</head>


	<body onload="preloadImages()">
		<form action="" id="paramSeleccionCarCli">
		<table width="306" border="0" align="left">
										<tr align="center">
											<td colspan="3" align="left">
<div id="pedidos" style="visibility:hidden">
	<div id="gridPedidos"></div>
</div>
<div id="registros" style="visibility:hidden">
	<div id="gridRegistros"></div>
</div>                                                 
                          </td>
										</tr>
										<tr>
											<td width="112" align="left">
											<input type="button" class="k-button" id="aceptarParam" value="Aceptar" onclick="aceptarParamsMonitor('<%=grid%>')"/></td>
											<td width="144" align="left"><input type="button" class="k-button" id="cancelarParam" value="Cancelar" onClick="cancelarParamsMonitor()"/></td>
											<td width="36" align="left">&nbsp;
												
											</td>
										</tr> 
									</table>
		
		</form>
	</body>

</html>