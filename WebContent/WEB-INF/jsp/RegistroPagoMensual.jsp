<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
		String path = request.getContextPath();
		String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
		String fromOrigen=request.getParameter("from");
		String fromURI = request.getParameter("uriActualizaGrid");
		//String conceptoReferencia=request.getParameter("conceptoReferencia");
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registro de Comprobantes de Pagos Mensuales</title>
<script type="text/javascript">
	var contexPath="<%=basePath%>";
</script>
<script src="<%=basePath%>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath%>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/kendo/js/cultures/kendo.culture.es-EC.min.js"></script>
<script src="<%=basePath %>/js/cierreVenta/monitorCotPedJQ.js"></script>
<script src="<%=basePath %>/js/cierreVenta/monitorPagosJQ.js"></script>
<script src="<%=basePath %>/js/clientes/ClientesSAPJQ.js"></script>
<script src="<%=basePath %>/js/contratos/ContratosJQ.js"></script>
<script src="<%=basePath %>/js/utils.js"></script>
<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />

		<script>
		kendo.culture("es-EC");
		
		var fromOrigen='<%= fromOrigen%>';
		$(document).ready(function(){
			$("#fchPago").kendoDatePicker({
 			format: "dd/MM/yyyy",
 			min: new Date(1990, 0, 1)
			}); 
			
			var windowConfirmaBorrado = $("#winConfirmaEliminaPago");
  		
  		    if (!windowConfirmaBorrado.data("kendoWindow")) {
			    windowConfirmaBorrado.kendoWindow({
			        height: "120px",
			        title: "Confirmacion de Borrado",
			        modal: true,
			        visible: false,
			        width: "350px"
			    });
    		}
		
			
			$("#gridPagos").kendoGrid({
					     height:330,
				        resizable: true,							
					 sortable: true,
					 reorderable: true,
					 pageable: {
                         input: true,
                         numeric: false,
                         pageSizes: [50,100,150],
                         messages: { 
							display: "{1} de {2} registros",
							page: "Pagina",
							of: "de",
							itemsPerPage: "registros por pagina"
						  }
                    	},	
				        scrollable:{
						   virtual:true
						},
				        columns: [
				        {
						  title: "",  width:30
						},
				        { 
							title:"Consecutivo" , width:50
						},
							{ 
							title:"Fecha de Pago" , width:50
						},
						/*{ 
							title:"Hora de Pago" , width: 50
						},*/
						{ 
							title:"Concepto" , width:50
						},
						{ 
							title:"Medio de Pago" , width:50
						},						
						{ 
							title:"Monto" , width: 50
						},
						{ 
							title:"Referencia" , width: 50
						},
						{ 
							title:"Folio Operacion" , width: 50
						},
						{ 
							title:"Observaciones" , width: 300
						}						
						],
			          }); 
		if(fromOrigen=="pagoIni")
		{
			$('#fregi').val("$000001").attr('disabled', 'true');
			$('#pedido').val(window.parent.dataItemCotizacion.idCotizacion).attr('disabled', 'true');
			$('#idCliente').val(window.parent.dataItemCotizacion.idCliente).attr('disabled', 'true');
			$('#nomCliente').val(window.parent.dataItemCotizacion.nombre+" "+window.parent.dataItemCotizacion.snombre+" "+window.parent.dataItemCotizacion.aapat+" "+window.parent.dataItemCotizacion.amat).attr('disabled', 'true');
			$('#dptoCasa').val(window.parent.dataItemCotizacion.dptoCasa).attr('disabled', 'true');
			var dg_PagosHeadx = window.parent.$("#dg_FindCotsPeds").data("kendoGrid");			
			var selectedRowPagoHeadx = dg_PagosHeadx.dataItem(dg_PagosHeadx.tbody.find("tr.k-state-selected"));

			if(selectedRowPagoHeadx != undefined)
			{
				$('#idFaseEquipoX').val(selectedRowPagoHeadx.dpto);
			}
			else
			{
				$('#idFaseEquipoX').val('<%= request.getParameter("depto_get") %>');
			}	
					
			//alert("e:" + $('#idFaseEquipoX').val());
			//alert ("JL" + selectedRowPagoHeadx.id_equnr);				
		}
		else if(fromOrigen=="pagoParc")
		{
			$('#fregi').val("$000001").attr('disabled', 'true');
			$('#pedido').val(window.parent.dataItemCotizacion.idPedido).attr('disabled', 'true');
			$('#idCliente').val(window.parent.dataItemCotizacion.idCliente).attr('disabled', 'true');
			$('#nomCliente').val(window.parent.dataItemCotizacion.nombre+" "+window.parent.dataItemCotizacion.snombre+" "+window.parent.dataItemCotizacion.aapat+" "+window.parent.dataItemCotizacion.amat).attr('disabled', 'true');
			$('#dptoCasa').val(window.parent.dataItemCotizacion.dptoCasa).attr('disabled', 'true');
			
		}else if(fromOrigen=="fromMonitor")
		{
			$('#fregi').val("$000001").attr('disabled', 'true');
			$('#pedido').val(window.parent.dataItemCotizacion.idPedido).attr('disabled', 'true');
			$('#idCliente').val(window.parent.dataItemCotizacion.idCliente).attr('disabled', 'true');
			$('#nomCliente').val(window.parent.dataItemCotizacion.nombre+" "+window.parent.dataItemCotizacion.snombre+" "+window.parent.dataItemCotizacion.aapat+" "+window.parent.dataItemCotizacion.amat).attr('disabled', 'true');
			$('#dptoCasa').val(window.parent.dataItemCotizacion.dptoCasa).attr('disabled', 'true');
		
		}else if(fromOrigen=="fromMonitor2")
		{
			//alert("entrando");
			$('#fregi').val("$000001").attr('disabled', 'true');
			$('#pedido').val(window.parent.dataItemPagoMonitorHead.vblen).attr('disabled', 'true');
			$('#idCliente').val(window.parent.dataItemPagoMonitorHead.kunnr).attr('disabled', 'true');
			$('#nomCliente').val(window.parent.dataItemPagoMonitorHead.kunnrtx).attr('disabled', 'true');
			$('#dptoCasa').val(window.parent.dataItemPagoMonitorHead.dpto).attr('disabled', 'true');
			var dg_PagosHeadx = window.parent.$("#dg_PagosRegistradosMonitor").data("kendoGrid");			
			var selectedRowPagoHeadx = dg_PagosHeadx.dataItem(dg_PagosHeadx.tbody.find("tr.k-state-selected"));
			$('#idFaseEquipoX').val(selectedRowPagoHeadx.id_equnr);
			//alert ("JL" + selectedRowPagoHeadx.id_equnr);				
		}
			
			
});	
</script>
	</head>

<body>
<form name="regPagos" id="regPagos" method="post" action="">
  
  <div>
  <input type="hidden" id="fromR" name="fromR" value="<%= fromOrigen%>" />
  <input type="hidden" id="fromURIActualiza" name="fromURIActualiza" value="<%= fromURI%>" />
     <table width="533" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="32">Folio Registro</td>
        <td><input type="text" id="fregi" name="fregi" class="k-textbox" maxlength="15" />
        </td>
        <td>
          <%if(fromOrigen.equals("pagoIni")){ %>
              Cotizaci&oacute;n
          <%}else{ %>
              Pedido
          <%} %>
        </td>
        <td><input type="text" id="pedido" name="pedido" class="k-textbox" maxlength="15" />
        </td>
      </tr>
      <tr>
        <td height="32">Dpto/ Casa</td>
        <td><input id="dptoCasa" name="dptoCasa" class="k-textbox" /></td>
        <td>Cliente</td>
        <td><input type="text" id="idCliente" name="idCliente" class="k-textbox" maxlength="30"  /></td>
      </tr>
      <tr>
        <td height="28">Nombre</td>
        <td colspan="3"><input type="text" id="nomCliente" name="nomCliente" class="k-textbox" style="width: 390px" maxlength="100" /></td>
       </tr>
      <tr>
        <td>&nbsp;<input type="hidden" id="idFaseEquipoX" name="idFaseEquipoX" value="" /></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td colspan="4">
          <input type="button" id="agregar" class="k-button" value="Agregar" onclick="agregaPago(document.getElementById('pedido').value)"/>
          <input type="button" id="quitar" class="k-button" value="Borrar" onclick="validaEliminarPago()"/>
        </td>
       </tr>
      <tr>
        <td colspan="4">&nbsp;</td>
      </tr>
    </table>
     <table border="0">
       <tr>
         <td>
           <div id="gridPagos"  style="width:1100px"/>
         </td>
       </tr>
     </table>
     <table width="481">
        <tr>
           <td>&nbsp;
            
           </td>
        </tr>
        <tr>
           <td align="left">
           <input type="button" class="k-button" onclick="registrarPagos()" value="Guardar"/>
<input type="button" class="k-button" onclick="cancelarRegistro()" value="Cancelar"/>

           </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><div id="mensajes_main">Mensajes de la aplicacion</div></td>
        </tr>
     </table>
  </div>
  <div id="winDetPago"></div>
  <div id="windowRegCliente"></div>
  <div id="winConfirmaEliminaPago">
  <table width="295" border="0">
      <tr>
        <td align="center"><b>&iquest;Confirme si desea eliminar el pago?</b></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td align="center">
          <input class="k-button" type="button" name="button" id="button" value="Aceptar" onClick="eliminaPago();">
          <input class="k-button" type="button" name="button" id="button" value="Cancelar" onClick="closeBorraPagoWindow();">
        </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
  </table>
  </div>
</form>
</body>
</html>