<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>
<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>
<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/cierreVenta/monitorPagosJQ.js"></script>
<script src="<%=basePath %>/js/cierreVenta/monitorCotPedJQ.js"></script>
<script src="<%=basePath %>/js/buttons/buttons_state.js"></script>
<script src="<%=basePath %>/js/contratos/ContratosJQ.js"></script>
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo/styles/examples.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>/kendo/styles/main.css" rel="stylesheet" type="text/css"/>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Parámetros de selección Monitor de Pagos</title>
<script>
           
       $(document).ready(function () {
            $("#fch_ini").kendoDatePicker({
 			format: "dd/MM/yyyy",
 			min: new Date(1990, 0, 1)
			}); 
			 $("#fch_fin").kendoDatePicker({
 			format: "dd/MM/yyyy",
 			min: new Date(1990, 0, 1)
			}); 
               	cargaMonitor();
         });

</script>
<style> 
.disabled{text-decoration:line-through;color:red}
</style> 
</head>


<body onload="preloadImages()">
<form action="" id="monitorPagos" >
  <table width="870" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td><table width="899" border="0" align="center" cellpadding="0" cellspacing="0">
      </table></td>
    </tr>
    <tr>
      <td height="400"><table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="23">&nbsp;</td>
        </tr>
        <tr>
          <td><table width="299" border="0" align="center">
            <tr>
              <td width="50"> Fecha Inicio </td>
              <td width="199"><input id="fch_ini" name="fch_ini" style="width: 150px;" /></td>
              <td width="36" align="center">&nbsp;</td>
            </tr>
            <tr>
              <td>Fecha Fin</td>
              <td><input id="fch_fin" name="fch_fin" style="width: 150px;" /></td>
              <td align="center">&nbsp;</td>
            </tr>
            <tr>
              <td> Id Cliente </td>
              <td><input id="clientes" name="clientes" maxlength="10" type="tel" class="k-textbox"  /></td>
              <td align="center"><a href="#"
				onMouseOver="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/add_16x16.png'); return true;"
			    onMouseOut="changeImages('button_Layer_1', '<%=basePath %>/images/icons/add_16x16.png'); return true;"
			    onMouseDown="changeImages('button_Layer_1', '<%=basePath %>/images/icons_press/add_16x16.png'); return true;"
				onMouseUp="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/add_16x16.png); return true;"
				onClick="openMatch()"><img name="button_Layer_1" src="<%=basePath %>/images/icons/add_24x24.png" border="0" alt="Agregar clientes"></a></td>
            </tr>
            <tr align="center">
              <td align="left">Pedido</td>
              <td align="left"><input id="pedido" name="pedido" class="k-textbox"  /></td>
              <td align="center"><a id="button_Ped"  href="#"
				onMouseOver="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/add_16x16.png'); return true;"
			    onMouseOut="changeImages('button_Layer_1', '<%=basePath %>/images/icons/add_16x16.png'); return true;"
			    onMouseDown="changeImages('button_Layer_1', '<%=basePath %>/images/icons_press/add_16x16.png'); return true;"
				onMouseUp="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/add_16x16.png); return true;"
				onClick="openCriteriosMonitor('ped')"><img id="button_Ped" name="button_Ped" src="<%=basePath %>/images/icons/add_24x24.png" border="0" alt="Agregar pedidos"></a></td>
            </tr>
            <tr align="center">
              <td align="left">Folio Registro</td>
              <td align="left"><input id="registro" name="registro" class="k-textbox"  /></td>
              <td align="center"><a id="button_Reg"  href="#"
				onMouseOver="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/add_16x16.png'); return true;"
			    onMouseOut="changeImages('button_Layer_1', '<%=basePath %>/images/icons/add_16x16.png'); return true;"
			    onMouseDown="changeImages('button_Layer_1', '<%=basePath %>/images/icons_press/add_16x16.png'); return true;"
				onMouseUp="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/add_16x16.png); return true;"
				onClick="openCriteriosMonitor('reg')"><img id="button_Reg" name="button_Reg" src="<%=basePath %>/images/icons/add_24x24.png" border="0" alt="Agregar registros"></a></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td align="center"><span style="visibility: visible; "> <a href="#"
				onMouseOver="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/lens_24x24.png'); return true;"
			    onMouseOut="changeImages('button_Layer_1', '<%=basePath %>/images/icons/lens_24x24.png'); return true;"
			    onMouseDown="changeImages('button_Layer_1', '<%=basePath %>/images/icons_press/lens_24x24.png'); return true;"
				onMouseUp="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/lens_24x24.png'); return true;"
				onClick="BuscarPagos()"><img name="button_Layer_1" src="<%=basePath %>/images/icons/lens_24x24.png" border="0" alt="Buscar Cotizaciones y Pedidos"></a> <a href="#"
				onMouseOver="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/cancel_24x24.png'); return true;"
			    onMouseOut="changeImages('button_Layer_1', '<%=basePath %>/images/icons/cancel_24x24.png'); return true;"
			    onMouseDown="changeImages('button_Layer_1', '<%=basePath %>/images/icons_press/cancel_24x24.png'); return true;"
				onMouseUp="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/cancel_24x24.png); return true;"
				onClick=""><img name="button_Layer_1" src="<%=basePath %>/images/icons/cancel_24x24.png" border="0" alt="Buscar Cotizaciones y Pedidos"></a></span></td>
              <td align="left">&nbsp;</td>
            </tr>
          </table></td>
          <td><div id="gridCriteriosMonitor">
            <table width="200" border="0">
              <tr>
                <td><div id="divClientes" style="visibility: hidden">
						<div id="windowMatch"></div>
				</div>
                  <div id="divPedidosMonitor" style="visibility: hidden">
                    <div id="gridPedidosMonitor"></div>
                    <input type="button" value="Aceptar" id="aceptaPedidoMonitor" onclick="aceptarCriteriosMonitor('ped')">
                    <input type="button" value="Cancelar" id="cancelaPedidoMonitor" onclick="cancelarCriteriosMonitor('ped')">
                  </div>
                  <div id="divRegistrosMonitor" style="visibility: hidden">
                    <div id="gridRegistrosMonitor"></div>
                    <input type="button" value="Aceptar" id="aceptaRegistroMonitor" onclick="aceptarCriteriosMonitor('reg')">
                    <input type="button" value="Cancelar" id="cancelaRegistroMonitor" onclick="cancelarCriteriosMonitor('reg')">
                  </div></td>
              </tr>
            </table>
          </div></td>
        </tr>
        <tr>
          <td height="19">&nbsp;</td>
        </tr>
        <tr>
          <td colspan="2"><div id="resultsPagos" style="visibility: hidden">
            <table>
              <tr align="center">
                <input type="button" id="detalle" value="Detalle" onclick="openDetallePago()"/>
                <input type="button" id="contrato" value="Contrato" onclick="impresion(2)"/>
                <input type="button" id="talonario" value="Talonario" onclick="impresion(3)"/>
                <input type="button" id="pagare" value="Pagare" onclick="impresion(4)"/>
              </tr>
              <tr>
                <div id="dg_PagosRegistrados" style="width:800px"></div>
              </tr>
            </table>
          </div></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
  </table>
  <div id="windowDetallePago"></div>  
<div id="windowImpresion"></div>


<div id="buttomBar">
  	<div class="console"/>
</div>
</form>
</body>

</html>