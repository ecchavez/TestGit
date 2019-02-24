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
		//String numPagos=request.getParameter("numPagos");
		//numPagos = numPagos ==null ? "" : numPagos;
		String fromMonitorOrigenPrin=request.getParameter("fromMonitorPrinci");
		fromMonitorOrigenPrin = fromMonitorOrigenPrin==null ? "" : fromMonitorOrigenPrin;
		
		String fromURI = request.getParameter("uriActualizaGrid");
		//String conceptoReferencia = request.getParameter("conceptoReferencia");
		
%>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title>Detalle de Pagos </title>

		<script src="<%=basePath%>/js/jquery-1.7.2.js"></script>
		<script src="<%=basePath%>/kendo/js/kendo.web.min.js"></script>
		<script src="<%=basePath %>/kendo/js/cultures/kendo.culture.es-EC.min.js"></script>
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

		kendo.culture("es-EC");
		
		$(document).ready(function(){

		$.ajaxSetup({async:false}); 	
		
		 $("#fchPago").kendoDatePicker({
 			format: "dd/MM/yyyy",
 			width: 20,
 			min: new Date(1, 0, 1900)
		});
		 
		 var windowConfirmaBorradoMonitor = $("#winConfirmaEliminaPagoMoni");
	  		
		    if (!windowConfirmaBorradoMonitor.data("kendoWindow")) {
		    	windowConfirmaBorradoMonitor.kendoWindow({
			        height: "120px",
			        title: "Confirmacion de Borrado",
			        modal: true,
			        visible: false,
			        width: "350px"
			    });
 		}
		
			var windowAvisoNoEdicionElimina = $("#winAvisoNoEdicionElimina");
		  		
			    if (!windowAvisoNoEdicionElimina.data("kendoWindow")) {
			    	windowAvisoNoEdicionElimina.kendoWindow({
				        height: "120px",
				        title: "Aviso importante",
				        modal: true,
				        visible: false,
				        width: "350px"
				    });
	 		}
		    
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
		
		var dg_PagosHeadx = window.parent.$("#dg_PagosRegistradosMonitor").data("kendoGrid");
		var selectedRowPagoHeadx = dg_PagosHeadx.dataItem(dg_PagosHeadx.tbody.find("tr.k-state-selected"));
		//alert(selectedRowPagoHeadx.id_equnr);
		$('#idFaseEquipoX').val(selectedRowPagoHeadx.id_equnr);

		//Declaración de dropdownlist para Concepto
		 $("#ddlConceptos").kendoDropDownList({
				optionLabel: "Seleccione un concepto ...",
				dataTextField: "valor",
		        dataValueField: "id_val",
		        index: 0
		 });
		 
		 //Instancia de dropdownlist para Concepto
	 	 ddlistConceptos = $("#ddlConceptos").data("kendoDropDownList");

	 	 //Declaración de dropdownlist para Medios
		 $("#ddlMedios").kendoDropDownList({
				optionLabel: "Seleccione un medio ...",
				dataTextField: "valor",
		        dataValueField: "id_val",
		        index: 0
		 });
		 //Instancia de dropdownlist para Medios
	 	 ddlistMedios = $("#ddlMedios").data("kendoDropDownList");	

	 	
	 	 setConceptos();
	 	 setMedios();
	 	$.ajaxSetup({async:true});
		 
});	

	function cargaDatos(horaF, fechaPagoF, montoF, referF, folioOperF, conceptoF, medioPagoF, observacionesF) {
		//alert("entra a cargaDatos_2() ... "); 
		$('#hrPago').val(horaF);
		$("#fchPago").val(fechaPagoF);
		$('#monto').val(montoF);
		$('#refer').val(referF);
		$('#folioOper').val(folioOperF);
		$("#ddlConceptos").data("kendoDropDownList").value(conceptoF);
		$("#ddlMedios").data("kendoDropDownList").value(medioPagoF);
		$('#observaciones').val(observacionesF);		
	}

	//Recupera Conceptos
	function setConceptos(){
		var idCatalogo = 'CONCEPTO';
		//Para llenar dropdownlist para Concepto
		ddlistConceptos.dataSource.data([]);
	    $.ajax({  
		    type: "POST",
		    async: false,
		    url:  "<%=basePath%>/ObtieneCatalogos.htm",  
		    data: "idCatalogo="+ idCatalogo,
		    success: function(response){
					if(response.mensaje == "SUCCESS"){
						var objCatalogosList= response.objCatalogosList;
						ddlistConceptos.dataSource.data(objCatalogosList);
					}else{
						alert("No se encontraron datos");
						$('#ddlConceptos').val("");
						ddlistConceptos.dataSource.data([]);
					}
		    },
		    error: function(e){  
			    	alert('Error: ' + e);  
		   	} 
		});
	}

	//Recupera Medios
	function setMedios(){
		var idCatalogo = 'MED_PAGO';
		//Para llenar dropdownlist para Medio
		ddlistMedios.dataSource.data([]);
	    $.ajax({  
		    type: "POST",
		    async:  false,
		    url:  "<%=basePath%>/ObtieneCatalogos.htm",  
		    data: "idCatalogo="+ idCatalogo,
		    success: function(response){
					if(response.mensaje == "SUCCESS"){
						var objCatalogosList= response.objCatalogosList;
						ddlistMedios.dataSource.data(objCatalogosList);
					}else{
						alert("No se encontraron datos");
						$('#ddlMedios').val("");
						ddlistMedios.dataSource.data([]);
					}
		    },
		    error: function(e){  
			    	alert('Error: ' + e);  
		   	} 
		});
	}			
		
</script>
	</head>
	<body>
	
		<form name="detPagos" id="detPagos" method="post"  action="">
		<input type="hidden" id="regi" value="<%=regi%>"/>
		<input type="hidden" id="fromDetalle" value="<%=from%>"/>
		<input type="hidden" id="fromURIActualiza" name="fromURIActualiza" value="<%= fromURI%>" />
		<input type="hidden" id="NumPagos"/>
		<table cellpadding=""
				cellspacing="0" border="0">
				
					<tr>
						<!-- ============ RIGHT COLUMN (CONTENT) ============== -->
						<td valign="top">
                
                <table align="center">
													<tr>
													  <td colspan="6"><b>Informaci&oacute;n del cliente</b></td>
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
													  <td><input id="fchPago" name="fchPago"/></td>
													  <td>Referencia</td>
													  <td><input type="text" id="refer" name="refer"
																class="k-textbox" size="50" maxlength="16"/>
													  </td>
												  </tr>
													<tr>
													<td>Monto</td>
													  <td><input type="text" id="monto" name="monto"
																class="k-textbox" size="50" maxlength="100"
																onKeyPress=" return captureEnteros(event);"  />
													  </td>
													  <td>Concepto </td>
													  <td><input id="ddlConceptos" class="k-select" style="width: 150px"/></td>
												  </tr>
												  <tr>
												  	  <td>Folio Oper</td>
													  <td>
													     <input type="hidden" id="archivo"  value="NA" name="archivo" class="k-textbox" size="50" maxlength="100"/>
													      <input type="text" id="folioOper" name="folioOper" class="k-textbox" size="50" maxlength="15"/>  
													   </td>
												  	  <td>Medio de Pago </td>
													  <td><input id="ddlMedios" class="k-select" style="width: 150px"/></td>	
													  
												  </tr>
												  <tr>
												  	<td colspan="2">&nbsp;</td>
												  	<td>Observaciones</td>
													  <td><input type="text" id="observaciones" name="observaciones" onkeyup="this.value=this.value.toUpperCase();"
																class="k-textbox" maxlength="132" style="width:400px"/></td>
												  </tr>
												  
												  <tr>
													  <td colspan="6"> <input type="button" class="k-button" id="btnAgregar" value="Agregar" onclick="agregaPago(document.getElementById('pedido').value)"/>
													  <input type="button" class="k-button" value="Modificar" id="btnModificar" onclick="actualizaItem()"/>	
													  <input type="button" class="k-button" value="Borrar" id="btnBorrar" onclick="validaEliminarPagoMonitor()"/></td>
												  </tr>
												  <%} %>
													<tr>
														<td colspan="6"><div id="dg_Detalles" style="width: 890px;height: 280px"></div></td>
													</tr>
											<% if(from.equals("modificaPago")){ %>
													<tr>
														<td colspan="6" align="left"><input type="button" class="k-button" value="Guardar" onclick="guardaActualizacion()"/>
													    <input type="button" class="k-button" value="Cancelar" onclick="cierraDetalleModificaPago()"/></td>
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
							<div id="winConfirmaEliminaPagoMoni">
							  <table width="295" border="0">
							      <tr>
							        <td align="center"><b>&iquest;Confime si desea eliminar el pago?</b></td>
							      </tr>
							      <tr>
							        <td>&nbsp;</td>
							      </tr>
							      <tr>
							        <td align="center">
							          <input class="k-button" type="button" name="button" id="button" value="Aceptar" onClick="quitPago();">
							          <input class="k-button" type="button" name="button" id="button" value="Cancelar" onClick="cierraBorradoModificaDetalleMonitor();">
							        </td>
							      </tr>
							      <tr>
							        <td>&nbsp;</td>
							      </tr>
							  </table>
							  </div>
							  <div id="winAvisoNoEdicionElimina">
							  <table width="295" border="0">
							      <tr>
							        <td align="center"><b>No es posible modificar/eliminar este pago.</b></td>
							      </tr>
							      <tr>
							        <td>&nbsp;</td>
							      </tr>
							      <tr>
							        <td align="center">
							            <input class="k-button" type="button" name="button" id="button" value="Aceptar" onClick="cierraAvisoDeBorradoPagosPS();">
							        </td>
							      </tr>
							      <tr>
							        <td>&nbsp;<input type="hidden" id="idFaseEquipoX" name="idFaseEquipoX" value="" /></td>
							      </tr>
							  </table>
							  </div>

					  </td>
					</tr>

			</table>
		</form>
        
	</body>
</html>
