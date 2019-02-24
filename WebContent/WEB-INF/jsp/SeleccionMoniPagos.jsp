<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>
		<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
		<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
		<script src="<%=basePath %>/kendo/js/console.js"></script>
		<script src="<%=basePath %>/js/cierreVenta/monitorCotPedJQ.js"></script>
		<script src="<%=basePath %>/js/cierreVenta/monitorPagosJQ.js"></script>
		<script src="<%=basePath %>/js/utils.js"></script>
		<script type="text/javascript">var contexPath="<%=basePath %>";</script>
		<script src="<%=basePath %>/js/buttons/buttons_state.js"></script>
		<link href="<%=basePath %>/kendo/styles/kendo.common.min.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=basePath %>/kendo/styles/examples.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath %>/kendo/styles/main.css" rel="stylesheet"
			type="text/css" />

		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title>Parámetros de selección</title>
		<script>
         $(document).ready(function () {
             $("#fch_ini").kendoDatePicker({
 			format: "dd/MM/yyyy",
 			width: 20,
 			min: new Date(1, 0, 1900)
		});
			 $("#fch_fin").kendoDatePicker({
 			format: "dd/MM/yyyy",
 			width: 20,
 			min: new Date(1, 0, 1900)
		});
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
		<form action="" id="paramSeleccionCliSap">
		
			<table width="870" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td>
						<table width="899" border="0" align="center" cellpadding="0"
							cellspacing="0">

						</table>
					</td>
				</tr>

				<tr>
					<td height="400">
						<table width="100%" height="100%" border="0" align="center"
							cellpadding="0" cellspacing="0">

							<tr>
								<td width="84%"> 
								 <table width="306" border="0" align="left">
										 <tr>
											<td width="112">Fecha Inicio </td>
											<td width="144"><input id="fch_ini" name="fch_ini" style="width: 150px;" /></td>
											<td width="36" align="left">&nbsp;</td>
										</tr>
								 		<tr>
											<td width="112">
												Fecha Fin</td>
											<td width="144"><input id="fch_fin" name="fch_fin" style="width: 150px;" /></td>
											<td width="36" align="left">&nbsp;</td>
										</tr>
										<tr>
											<td width="112">Id Cliente </td>
											<td width="144"><input id="clientes" name="clientes" maxlength="10" type="tel" class="k-textbox"  /></td>
											<td width="36" align="left"><input type="button" class="k-button" value="..." onClick="openMatch()"/></td>
										</tr>
										<tr>
											<td>Pedido</td>
											<td><input id="pedido" name="pedido" class="k-textbox"  /></td>
											<td align="left"><input type="button" class="k-button" value="..." onClick="openParamMoniPagos('Pedidos')"/></td>
										</tr>
										<tr>
											<td>
												Folio Registro
											</td>
											<td><input id="registro" name="registro" class="k-textbox"  /></td>
											<td align="left"><input type="button" class="k-button" value="..." onClick="openParamMoniPagos('Registros')"/></td>
										</tr>
										<tr>
											<td>
											<input  type="button" class="k-button" id="aceptarCriterios" value="Aceptar" onclick="BuscarPagos()"/>
											</td>
											<td>
											<input  type="button" class="k-button" id="cancelarCriterios" value="Cancelar" onclick="cancelarSelec()"/>
											</td>
											
											<td align="left">&nbsp;
												
											</td>
										</tr>
									</table>
								</td>
								
							</tr>
							
							<tr>
								<td>&nbsp;
									
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>&nbsp;
						
					</td>
				</tr>
			</table>

			
			<div id="windowMatch"></div>
			<div id="windowParamsMoniPagos"></div>

			<div id="buttomBar">
				<div class="console" />
				</div>
		</form>
	</body>

</html>