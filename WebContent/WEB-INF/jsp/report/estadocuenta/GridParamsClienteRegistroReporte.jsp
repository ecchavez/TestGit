<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 

response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>
		<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
		<script src="<%=basePath %>/js/jquery.blockUI.js"></script>
		<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
		<script src="<%=basePath %>/kendo/js/console.js"></script>
		<script src="<%=basePath %>/js/clientes/ClientesJQ.js"></script>
        <script src="<%=basePath %>/js/reportes/estadocuenta/EstadoCuenta.js"></script>
		<script src="<%=basePath %>/js/utils.js"></script>
		<script type="text/javascript">var contexPath="<%=basePath %>";</script>
		<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
		<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
		<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />

		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title>Parámetros de selección</title>
		<script>
           
              $(document).ready(function () {
                   cargaGrid();
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
		<form action="" id="paramSeleccionCarCli">
		
			<table width="343" height="186" border="0" align="center">
										<tr>
											<td width="152">
												Nombre</td>
											<td width="154">
												<input type="text" id="nomTxt" style="text-transform: uppercase" onblur="javascript:this.value=this.value.toUpperCase();"  class="k-textbox" onkeypress="return captureNombres(event)"/>
											</td>
										</tr>
										<tr>
											<td>
												Segundo Nombre</td>
											<td>
												<input type="text" id="nom2Txt" style="text-transform: uppercase" onblur="javascript:this.value=this.value.toUpperCase();" class="k-textbox" onkeypress="return captureNombres(event)" />
											</td>
										</tr>
										<tr>
											<td>
												Apellido Paterno
											</td>
											<td><input type="text" id="apPatTxt" style="text-transform: uppercase" onblur="javascript:this.value=this.value.toUpperCase();" style="text-transform: uppercase" onblur="javascript:this.value=this.value.toUpperCase();" class="k-textbox" onkeypress="return captureNombres(event)" /></td>
										</tr>
										<tr>
											<td>
												Apellido Materno</td>
											<td>
												<input type="text" id="apMatTxt" style="text-transform: uppercase" onblur="javascript:this.value=this.value.toUpperCase();" class="k-textbox" onkeypress="return captureNombres(event)" />
											</td>
										</tr>
										<tr>
											<td>
												<input  type="button" class="k-button" id="aceptarCriterios" value="Aceptar" onclick="armaEstructuraCriterioClientesReporte()"/>
											</td>
											<td>
												<input  type="button" class="k-button" id="cancelarCriterios" value="Cancelar" onclick="cancelarGridBusquedaCriteriosClienteReporte()"/>
											</td>											
										</tr>
										<tr>
										   <td colspan="2"><div id="mensajes_main">Mensajes de la aplicacion</div></td>
										</tr>
									</table>
			
            <div id="windowParamsCarCli"></div>

			
		</form>
		
	</body>

</html>