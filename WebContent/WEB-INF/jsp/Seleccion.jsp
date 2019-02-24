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
		<script type="text/javascript">var contexPath="<%=basePath %>";</script>
		<script src="<%=basePath %>/js/cierreVenta/monitorCotPedJQ.js"></script>
		<script src="<%=basePath %>/js/cierreVenta/matchCode.js"></script>
		<script src="<%=basePath %>/js/clientes/ClientesSAPJQ.js"></script>
		<script src="<%=basePath %>/js/buttons/buttons_state.js"></script>
		<script src="<%=basePath %>/js/contratos/ContratosJQ.js"></script>
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
                    cargaBusq();
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
		<form action="" id="cierreVenta">
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
									<table width="306" border="0" align="center">
										<tr>
											<td width="112">
												Cotizaci&oacute;n
											</td>
											<td width="144">
												<input type="text" id="cotizacion" name="cotizacion"
													class="k-textbox" onmouseout="validaField('cot')" />
											</td>
											<td width="36" align="left">
												<a id="button_Cot" href="#"
													onMouseOver="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/add_16x16.png'); return true;"
													onMouseOut="changeImages('button_Layer_1', '<%=basePath %>/images/icons/add_16x16.png'); return true;"
													onMouseDown="changeImages('button_Layer_1', '<%=basePath %>/images/icons_press/add_16x16.png'); return true;"
													onMouseUp="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/add_16x16.png); return true;"
													onClick="openCriterios('cot')"><img id="button_Cot"
														name="button_Cot"
														src="<%=basePath %>/images/icons/add_24x24.png" border="0"
														alt="Agregar cotizaciones">
												</a>
											</td>
										</tr>
										<tr>
											<td>
												Pedido
											</td>
											<td>
												<input type="text" id="pedido" name="pedido"
													class="k-textbox" onmouseout="validaField('ped')" />
											</td>
											<td align="left">
												<a id="button_Ped" href="#"
													onMouseOver="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/add_16x16.png'); return true;"
													onMouseOut="changeImages('button_Layer_1', '<%=basePath %>/images/icons/add_16x16.png'); return true;"
													onMouseDown="changeImages('button_Layer_1', '<%=basePath %>/images/icons_press/add_16x16.png'); return true;"
													onMouseUp="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/add_16x16.png); return true;"
													onClick="openCriterios('ped')"><img id="button_Ped"
														name="button_Ped"
														src="<%=basePath %>/images/icons/add_24x24.png" border="0"
														alt="Agregar pedidos">
												</a>
											</td>
										</tr>
										<tr>
											<td>
												<input type="radio" name="cotPed" id="cot" value="0" />
												Cotizaciones
											</td>
											<td>
												<input type="radio" name="cotPed" id="ped" value="1" />
												Pedidos
											</td>
											<td align="left">&nbsp;
												
											</td>
										</tr>
										<tr>
											<td>
												Id Cliente
											</td>
											<td>
												<input type="text" id="clientes" name="clientes" class="k-textbox" />
											</td>
											<td align="left">
												<a href="#"
													onMouseOver="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/add_16x16.png'); return true;"
													onMouseOut="changeImages('button_Layer_1', '<%=basePath %>/images/icons/add_16x16.png'); return true;"
													onMouseDown="changeImages('button_Layer_1', '<%=basePath %>/images/icons_press/add_16x16.png'); return true;"
													onMouseUp="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/add_16x16.png); return true;"
													onClick="openCriterios('cli')"><img name="button_Layer_1"
														src="<%=basePath %>/images/icons/add_24x24.png" border="0"
														alt="Agregar Ids clientes">
												</a>
											</td>
										</tr>
										<tr align="center">
											<td align="left">
												Fase
											</td>
											<td align="left">
												<input id="fase" name="fase" class="k-textbox" />
											</td>
										</tr>
										<tr align="center">
											<td align="left">
												Equipo
											</td>
											<td align="left">
												<input id="equipo" name="equipo" class="k-textbox" />
											</td>
											<td align="left">
												<a id="button_Equ" href="#"
													onMouseOver="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/add_16x16.png'); return true;"
													onMouseOut="changeImages('button_Layer_1', '<%=basePath %>/images/icons/add_16x16.png'); return true;"
													onMouseDown="changeImages('button_Layer_1', '<%=basePath %>/images/icons_press/add_16x16.png'); return true;"
													onMouseUp="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/add_16x16.png); return true;"
													onClick="openCriterios('equ')"><img id="button_Equ"
														name="button_Equ"
														src="<%=basePath %>/images/icons/add_24x24.png" border="0"
														alt="Agregar equipos">
												</a>
											</td>
										</tr>
										<tr>
											<td>&nbsp;
												
											</td>
											<td align="center">
												<span style="visibility: visible;"><a href="#"
													onMouseOver="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/lens_24x24.png'); return true;"
													onMouseOut="changeImages('button_Layer_1', '<%=basePath %>/images/icons/lens_24x24.png'); return true;"
													onMouseDown="changeImages('button_Layer_1', '<%=basePath %>/images/icons_press/lens_24x24.png'); return true;"
													onMouseUp="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/lens_24x24.png'); return true;"
													onClick="BuscarCotPed()"><img name="button_Layer_1"
															src="<%=basePath %>/images/icons/lens_24x24.png"
															border="0" alt="Buscar Cotizaciones y Pedidos">
												</a> <a href="#"
													onMouseOver="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/cancel_24x24.png'); return true;"
													onMouseOut="changeImages('button_Layer_1', '<%=basePath %>/images/icons/cancel_24x24.png'); return true;"
													onMouseDown="changeImages('button_Layer_1', '<%=basePath %>/images/icons_press/cancel_24x24.png'); return true;"
													onMouseUp="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/cancel_24x24.png); return true;"
													onClick=""><img name="button_Layer_1"
															src="<%=basePath %>/images/icons/cancel_24x24.png"
															border="0" alt="Cancelar Cotizaciones y Pedidos">
												</a>
												</span>
											</td>
											<td align="left">&nbsp;
												
											</td>
										</tr>
									</table>
								</td>
								<td width="16%">
									<div id="gridsCriterios">
										
												<div id="divCotizaciones" style="visibility: hidden">
													<div id="gridCotizaciones">
													</div>
													<input type="button" value="Aceptar" id="aceptaCotizacion"
														onclick="aceptarCriterios('cot')">
													<input type="button" value="Cancelar"
														id="cancelaCotizacion" onclick="cancelarCriterios('cot')">
												</div>
												<div id="divPedidos" style="visibility: hidden">
													<div id="gridPedidos">
													</div>
													<input type="button" value="Aceptar" id="aceptaPedido"
														onclick="aceptarCriterios('ped')">
													<input type="button" value="Cancelar" id="cancelaPedido"
														onclick="cancelarCriterios('ped')">
												</div>
												<div id="divClientes" style="visibility: hidden">
													<div id="windowMatch">
													</div>
												</div>
												<div id="divEquipos" style="visibility: hidden">
													<div id="gridEquipos">
													</div>
													<input type="button" value="Aceptar" id="aceptaEquipo"
														onclick="aceptarCriterios('equ')">
													<input type="button" value="Cancelar" id="cancelaEquipo"
														onclick="cancelarCriterios('equ')">
												</div>

									</div>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div id="results" style="visibility: hidden">
										<table>
											<tr align="center">
												<input type="button" id="abrirCot" value="Abrir cot."
													onclick="" />
												<input type="button" id="regPago" value="Registra Pago"
													onclick="registraPagoBtn()" />
												<input type="button" id="verCliente" value="Ver Cliente"
													onclick="verDatosCliente()" />
												<input type="button" id="edoCuenta" value="Edo. Cuenta"
													onclick="" />
											</tr>
											<tr>
												<div id="dg_FindCotsPeds" style="width: 900px"></div>
											</tr>
										</table>
									</div>
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

			<div id="windowRegCliente"></div>
			<div id="windowRegPago"></div>
			
			<div id="windowImpresion"></div>

			<div id="buttomBar">
				<div class="console" />
				</div>
		</form>
	</body>

</html>