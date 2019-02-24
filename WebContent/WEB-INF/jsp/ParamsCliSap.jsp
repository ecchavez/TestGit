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
		<script src="<%=basePath %>/js/clientes/ClientesSAPJQ.js"></script>
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
              
              cargaGridSap('<%=grid%>');
                   
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
<div id="cliZ" style="visibility:hidden">
	<div id="gridCliZ"></div>
</div>
<div id="cliSap" style="visibility:hidden">
	<div id="gridCliSap"></div>
</div>  
<div id="nom" style="visibility:hidden">
	<div id="gridNom"></div>
</div> 
<div id="nom2" style="visibility:hidden">
	<div id="gridNom2"></div>
</div> 
<div id="apPat" style="visibility:hidden">
	<div id="gridApPat"></div>
</div>
<div id="apMat" style="visibility:hidden">
	<div id="gridApMat"></div>
</div>
<div id="telCa" style="visibility:hidden">
	<div id="gridTelCa"></div>
</div>
<div id="telCel" style="visibility:hidden">
	<div id="gridTelCel"></div>
</div>
<div id="mail" style="visibility:hidden">
	<div id="gridMail"></div>
</div>                                                
                                            
                                            
                                            
                                            
                                            </td>
										</tr>
										<tr>
											<td width="112" align="left">
											<input type="button" class="k-button" id="aceptarParam" value="Aceptar" onclick="aceptarParamsSap('<%=grid%>')"/></td>
											<td width="144" align="left"><input type="button" class="k-button" id="cancelarParam" value="Cancelar" onClick="cancelarParamsSap()"/></td>
											<td width="36" align="left">&nbsp;
												
											</td>
										</tr> 
									</table>

			<div id="windowParamsCliSap"></div>
		
		</form>
	</body>

</html>