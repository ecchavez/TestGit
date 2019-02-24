<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
String grid=request.getParameter("queGrid");
String from=request.getParameter("from");

%>
		<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
		<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
		<script src="<%=basePath %>/kendo/js/console.js"></script>
		<script type="text/javascript">var contexPath="<%=basePath %>";</script>
		<script src="<%=basePath %>/js/cierreVenta/monitorCotPedJQ.js"></script>
		<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
		<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
		<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />

		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title><%=grid%></title>
		<script>
           
              $(document).ready(function () {
                cargaGridCierre('<%=grid%>');
         });

</script>
		<style>
.disabled {
	text-decoration: line-through;
	color: red
}
</style>
	</head>


	<body>
		<form action="" id="paramCierre">
		<input type="hidden" id="screenFrom" value=<%=from%> />
		<table width="306" border="0" align="left">
										<tr align="center">
											<td colspan="3" align="left">
<div id="cotizaciones" >
	<div id="gridCotizaciones"></div>
</div>
<div id="pedidos" >
	<div id="gridPedidos"></div>
</div>  
<div id="nombres" style="visibility:hidden">
	<div id="gridNombres"></div>
</div>
<div id="segNombres" >
	<div id="gridSegNombres"></div>
</div> 
<div id="apPat" >
	<div id="gridApPaternos"></div>
</div>
<div id="apMat">
	<div id="gridApMaternos"></div>
</div>  
<div id="equipos">
	<div id="gridEquipos"></div>
</div>                                                 
                          </td>
										</tr>
										
										<tr>
											<td width="185" align="left">
											<input type="button" class="k-button" id="aceptarParam" value="Aceptar" onclick="aceptarParamsCierre('<%=grid%>')"/>
											<input type="button" class="k-button" id="cancelarParam" value="Cancelar" onClick="cancelarParamsCierre()"/></td>
											<td width="71" align="left">&nbsp;</td>
											<td width="36" align="left">&nbsp;
												
											</td>
										</tr> 
									</table>
		
		</form>
	</body>

</html>