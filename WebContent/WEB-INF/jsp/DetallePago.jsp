<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
<%
		String path = request.getContextPath();
		String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
		String regi=request.getParameter("regi");
		String from=request.getParameter("from");
		String fromMonitorOrigenPrin=request.getParameter("fromMonitorPrinci");
		fromMonitorOrigenPrin = fromMonitorOrigenPrin==null ? "" : fromMonitorOrigenPrin;
		//String conceptoReferencia = request.getParameter("conceptoReferencia");
		
%>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title>Detalle de Pagos </title>

		<script src="<%=basePath%>/js/jquery-1.7.2.js"></script>
		<script src="<%=basePath%>/kendo/js/kendo.web.min.js"></script>
		<script src="jquery-1.7.1.js"></script>
		<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />
<script>
	var contexPath="<%=basePath%>";
</script>
		<script src="<%=basePath%>/js/cierreVenta/monitorPagosJQ.js"></script>
		<script src="<%=basePath%>/js/cierreVenta/monitorCotPedJQ.js"></script>
		<script src="<%=basePath%>/js/utils.js"></script>
		<script src="<%=basePath%>/kendo/js/console.js"></script>
		
		<script>
		
		$(document).ready(function(){
		
		 $("#fchPago").kendoDatePicker({
 			format: "dd/MM/yyyy",
 			width: 20,
 			min: new Date(1, 0, 1900)
		});
		if(window.parent.dataItemPago!=null)
		{
			$('#pedido').val(window.parent.dataItemPago.vblen).attr('disabled', 'true');
			$('#folio_reg').val(window.parent.dataItemPago.fregi).attr('disabled', 'true');
			$('#idCliente').val(window.parent.dataItemPago.kunnr).attr('disabled', 'true');
			$('#nomCliente').val(window.parent.dataItemPago.kunnrtx).attr('disabled', 'true');
			$('#dptoCasa').val(window.parent.dataItemPago.dpto).attr('disabled', 'true');
			cargaDatosDetallePagos();
		}
		
		
		var OrigenMonitorPrin = "<%=fromMonitorOrigenPrin%>";
		var regiFregi = "<%=regi%>";
		
		if (OrigenMonitorPrin!==''){
			$('#pedido').val(window.parent.dataItemPagoMonitorHead.vblen).attr('disabled', 'true');
			$('#folio_reg').val(window.parent.dataItemPagoMonitorHead.fregi).attr('disabled', 'true');
			$('#idCliente').val(window.parent.dataItemPagoMonitorHead.kunnr).attr('disabled', 'true');
			$('#nomCliente').val(window.parent.dataItemPagoMonitorHead.kunnrtx).attr('disabled', 'true');
			$('#dptoCasa').val(window.parent.dataItemPagoMonitorHead.dpto).attr('disabled', 'true');
			cargaDatosDetallePagosFromMonitor(regiFregi);
		}
						
	                 
});	
</script>
	</head>
	<body>
		<form name="detPagos" id="detPagos" method="post" action="">
		<input type="hidden" id="regi" value="<%=regi%>"/>
		<input type="hidden" id="fromDetalle" value="<%=from%>"/>
			<table cellpadding=""
				cellspacing="0" border="0">
				
					<tr>
						<!-- ============ RIGHT COLUMN (CONTENT) ============== -->
						<td valign="top">
                
                <table align="center">
													<tr>
													  <td colspan="6"><b>Informacion del cliente</b></td>
												  </tr>
													<tr>
														<td width="95">
															Folio Reg</td>
														<td width="228"><input type="text" id="folio_reg" name="folio_reg"
																class="k-textbox" maxlength="30" /></td>
														<td width="83">Pedido</td>
														<td colspan="3"><input type="text" id="pedido" name="pedido"
																class="k-textbox" maxlength="15"
																onKeyPress="captureEnteros(this,event);" /></td>
													</tr>
													<tr>
														<td>Cliente</td>
														<td><input id="idCliente" name="idCliente" class="k-textbox" /></td>
														<td>Dpto/ Casa </td>
														<td colspan="3"><input id="dptoCasa" name="dptoCasa" class="k-textbox" /></td>
													</tr>
													<tr>
													  <td>Nombre</td>
													  <td colspan="3"><input style="width:300px" id="nomCliente" name="nomCliente" class="k-textbox" /></td>
													  <td width="14">&nbsp;</td>
													  <td width="496">&nbsp;</td>
												  </tr>
												
							                     <% if(from.equals("visualizaPago")){ %>
							                         <tr>
													  <td colspan="6"><b>Detalle de pagos</b></td>
												     </tr>
							                     <%} %>					
							<% if(from.equals("modificaPago")){ %>
							                      <tr>
													  <td colspan="6"><b>Registrar pagos</b></td>
												  </tr>
													<tr>
													  <td>Fecha Registro </td>
													  <td colspan="2"><input id="fchPago" name="fchPago"	/></td>
													  <td colspan="2">Hora Registro </td>
													  <td><input type="text" id="hrPago" name="hrPago"
																class="k-textbox" size="50" maxlength="100"
																 /></td>
												  </tr>
													<tr>
													  <td>Monto</td>
													  <td colspan="2"><input type="text" id="monto" name="monto"
																class="k-textbox" size="50" maxlength="100"
																onKeyPress=" return captureEnteros(event);"  /></td>
													  <td colspan="2">Referencia</td>
													  <td><input type="text" id="refer" name="refer"
																class="k-textbox" size="50" maxlength="100"
																/></td>
												  </tr>
													<tr>
													  <td>Folio Oper</td>
													  <td colspan="2"><input type="hidden" id="archivo" name="archivo"
																class="k-textbox" size="50" maxlength="100"
																/>
														<input type="text" id="folioOper" name="folioOper" 	class="k-textbox" size="50" maxlength="100" />		
													</td>
													  <td colspan="2"></td>
													  <td></td>
												  </tr>
													<tr>
													  <td colspan="6"> <input type="button" class="k-button" value="Agregar" onclick="agregaPago(document.getElementById('pedido').value)"/>
												      <input type="button" class="k-button" value="Modificar" onclick="actualizaItem()"/>													    <input type="button" class="k-button" value="Borrar" onclick="quitPago()"/></td>
												  </tr>
												  <%} %>
													<tr>
														<td colspan="6"><div id="dg_Detalles" style="width: 890px;height: 200px"></div></td>
													</tr>
											<% if(from.equals("modificaPago")){ %>
													<tr>
														<td colspan="6" align="left"><input type="button" class="k-button" value="Guardar" onclick="guardaActualizacion()"/>
													    <input type="button" class="k-button" value="Cancelar" onclick="cierraDetalle()"/></td>
													</tr>
													<%} %>
													<tr>
													 <td colspan="6"></td>
													</tr>
													<tr>
													 <td colspan="6"><div id="mensajes_main">Mensajes de la aplicacion</div></td>
													</tr>
												
											</table>        
                       
                       		<div id="winDetPago"></div>

						

					  </td>
					</tr>

			</table>
		</form>
        
	</body>
</html>
