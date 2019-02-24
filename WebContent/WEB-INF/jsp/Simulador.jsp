
<%@page import="mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
UsuarioDto user = (UsuarioDto) request.getSession().getAttribute("usrSession");
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Simulador</title>

<%@ page session="true" %> 
		
<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/js/autoNumeric.js"></script>
<script src="<%=basePath %>/js/formaters/Formater.js"></script>
<script src="<%=basePath %>/js/utils.js"></script>

<script type="text/javascript">
	var contexPath="<%=basePath %>";
	var hdr_base;
</script>

<script src="<%=basePath %>/js/cotizador/CotizadorJQ.js"></script>
<script src="<%=basePath %>/js/tickets/RegistroTickets.js"></script>

<script>
	
	$(document).ready(function() { 
	    var winse = $("#winsubequipos");
	    var windprint = $("#printCotizador");
	    windprint.data("kendoWindow");
	    
	    winse.data("kendoWindow");
		winse.kendoWindow({
			width: "500px",
			height: "300px",
			title: "Sub-equipos disponibles",
			actions: ["Close"],
			modal: true,
			resizable: false,
			visible: false,
			close: onCloseWinSubEqu
		});
		
		winse.kendoWindow({
			width: "500px",
			height: "300px",
			title: "Imprime Cotizacion",
			actions: ["Close"],
			modal: true,
			resizable: false,
			visible: false
		});
		
	    $("#dg_equipos").kendoGrid({
	          autoBind: true,
	          height: "130px",							  
			  filterable: false,
			  sortable: false,				  
			  pageable: false,
              resizable: true,
              scrollable: true,
              selectable: "row"            				  
		  });  
		  
		  $("#dg_detallepagos").kendoGrid({
	          height: "330px",							  
			  filterable: false,
			  sortable: false,				  
			  pageable: false,
              resizable: true,
              scrollable: true							  
		  });  	
		  
		  var  subEquipoDS = new kendo.data.DataSource({		     
		     pageSize: 10
		  });
		 
		  $("#dg_subequipos").kendoGrid({							  
			  filterable: false,
			  height: 150,
			  sortable: false,				  
			  pageable: false,
	          resizable: true,
	          scrollable: true,
	          selectable: "row",
	          dataSource: subEquipoDS										  
		 }); 	 
		
		$("#claveClienteTicket").val('<%= request.getParameter("idCarCliente") == null || request.getParameter("idCarCliente").trim().equals("null")?"":request.getParameter("idCarCliente")%>');
		$("#nomClienteTicket").html('<%= request.getParameter("nombreCte") == null || request.getParameter("nombreCte").trim().equals("null")?"":request.getParameter("nombreCte")%>');
		
			
		if($("#from_is").val()=="ext")  
		{			
			$("#btn_cte").attr("disabled","disabled");
			getCotizacionGuardado();
		}
		else if($("#from_is").val()=="cancelar")  
		{
			$("#btn_cte").attr("disabled","disabled");
			getCotizacionBase("cancelar");
		} 
		else if($("#from_is").val()=="traspasar") 
		{
			$("#btn_cte").attr("disabled","disabled");
			getCotizacionBase("traspasar");
		}
		else if($("#from_is").val()=="base") 
		{
			getCotizacionBase("base");
		}
		    
	    
	    $("#equipo").autoNumeric({aSep: ',', aDec: '.', aSign:'$'});
		$("#descm").autoNumeric({aSep: ',', aDec: '.', aSign:'$'});
		$("#descmdes").autoNumeric({aSep: ',', aDec: '.', aSign:'$'});
		$("#subequipos").autoNumeric({aSep: ',', aDec: '.', aSign:'$'});
		$("#total").autoNumeric({aSep: ',', aDec: '.', aSign:'$'});
		$("#anticipo").autoNumeric({aSep: ',', aDec: '.', aSign:'$'});
		$("#enganche").autoNumeric({aSep: ',', aDec: '.', aSign:'$'});
		$("#diferido").autoNumeric({aSep: ',', aDec: '.', aSign:'$'});
		$("#pago_final").autoNumeric({aSep: ',', aDec: '.', aSign:'$'});
		$("#gastos_admin").autoNumeric({aSep: ',', aDec: '.', aSign:'$'});
	});
	
	function openWinSubEquipos()
	{	    
		if($("#claveClienteTicket").val()!="")
		{
		    var winse = $("#winsubequipos").data("kendoWindow");
			winse.open();
			winse.center();
		}
		else
		{
			window.parent.$("#mensajes_main").html("Seleccione un cliente para continuar el proceso de cotizacion");
		}
	}
	
	function setFocusDisable()
	{
		if(simulador)
		{
			$("#activasimulador").focus();	
		}
	}
	
	/*function openWinSubEquipos()
	{	    
	    var winse = $("#winsubequipos").data("kendoWindow");
		winse.open();
		winse.center();
	}*/
	
	
	   
</script>

<link href="<%=basePath %>/kendo_ant/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo_ant/styles/examples.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>/kendo_ant/styles/mainb.css" rel="stylesheet" type="text/css"/>

<style type="text/css">
	/*headers*/

.k-grid th.k-header,
.k-grid th.k-header .k-link
{
    font-size: 8px;
}

/*rows*/

.k-grid-content>table>tbody>tr
{
    font-size: 8px;
}


/*.k-grid-content>table>tbody>.k-alt
{
    background:#cfc;
}*/

/*selection*/

/*.k-grid table tr.k-state-selected
{
    background: #f99;
    color: #fff;
}*/
</style>

</head>
<body style="background-color:transparent">
<table width="490" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="left"><span style="font-size: 12px;">Bienvenido(a) <%= user.getUsuario()%></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="390" style="vertical-align:top"><div id="panel" class="k-block k-shadow" style="margin-bottom: 2px; margin-top: 2px; margin-left: 2px; margin-right: 2px">
              <div class="k-header k-shadow" style="font: bold">DATOS GENERALES</div><table border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="64">Cotizacion</td>
              <td width="105">
                <input name="id_cotiz_z" type="text" class="k-textbox" id="id_cotiz_z" style="width:70px; height:10px; font-size: 8px;"></td>
              <td width="29">&nbsp;</td>
              <td width="35"><input type="text" name="numl" id="numl" style="width:20px; height:10px; font-size: 8px; visibility:hidden" class="k-textbox" disabled onFocus="setFocusDisable()"></td>
              <td width="45">&nbsp;</td>
              <td width="35"><!-- input type="text" name="textfield5" id="textfield5" style="width:50px; height:10px; font-size: 8px;" class="k-textbox" disabled onFocus="setFocusDisable()"--></td>
            </tr>
            <tr>
              <td>Cliente</td>
              <td><!--input type="text" name="id_car_cli" id="id_car_cli" style="width:70px; height:10px; font-size: 8px;" class="k-textbox" value="P000000111"--><input type="text" id="claveClienteTicket" name="claveClienteTicket" class="k-textbox" size="50" maxlength="100" onkeypress="return letrasnumeros(event)" style="width:70px; height:10px; font-size: 8px;" disabled="disabled"/>
              <input type="button" class="k-button" value="..." onclick="dialogoGridBusquedaClienteRegistroTicket('cotizador')" style="width:25px; height:18px; font-size: 9px;" id="btn_cte"/></td>
              <td colspan="4"><div id="nomClienteTicket" style="width:160px; height:10px; font-size: 8px;"></div></td>
            </tr>
            <tr>
              <td>Unidad</td>
              <td><input type="text" name="depto" id="depto" style="width:70px; height:10px; font-size: 8px;" class="k-textbox" disabled onFocus="setFocusDisable()"></td>
              <td colspan="4"><div id="descr" style="font-size: 8px;"></div></td>
            </tr>
          </table></div></td>
          <td width="241" style="vertical-align:top"><table border="0" align="right" cellpadding="0" cellspacing="0" width="100%">
            <tr>
              <td width="100" align="right">Fecha</td>
              <td width="79" align="right">
                <input name="fechaiv" type="text" class="k-textbox" id="fechaiv" onFocus="setFocusDisable()" style="width:70px; height:10px; font-size: 8px;" disabled>
              </td>
            </tr>
            <tr>
              <td align="right">Reservado</td>
              <td align="right"><input type="text" name="textfield9" id="textfield9" style="width:70px; height:10px; font-size: 8px;" class="k-textbox" disabled onFocus="setFocusDisable()"></td>
            </tr>
            <tr>
              <td align="right">Hora</td>
              <td align="right"><input type="text" name="textfield10" id="textfield10" style="width:70px; height:10px; font-size: 8px;" class="k-textbox" disabled onFocus="setFocusDisable()"></td>
            </tr>
            <tr>
              <td align="right"></td>
              <td align="right"><input type="hidden" name="datub" id="datub" style="width:70px; height:10px; font-size: 8px;" class="k-textbox" disabled onFocus="setFocusDisable()"></td>
            </tr>
            <tr>
              <td height="19" align="right"></td>
              <td align="right"><input type="hidden" name="datab" id="datab" style="width:70px; height:10px; font-size: 8px;" class="k-textbox" disabled></td>
            </tr>
          </table></td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td ><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td style="vertical-align:top"><div id="panel" class="k-block k-shadow" style="margin-bottom: 2px; margin-top: 2px; margin-left: 2px; margin-right: 2px"><div class="k-header k-shadow">TOTALES</div><table border="0" cellpadding="0" cellspacing="0" width="200">
          
          <tr>
            <td width="76">Precio</td>
            <td width="78"><input type="text" name="equipo" id="equipo" style="width:70px; height:10px; font-size: 8px;" class="k-textbox" disabled onFocus="setFocusDisable()"></td>
            <td width="23"><input type="text" name="m2tot" id="m2tot" style="width:20px; height:10px; font-size: 8px;" class="k-textbox" disabled onFocus="setFocusDisable()"></td>
            <td width="23">M2</td>
          </tr>
          <tr>
            <td>Descuento</td>
            <td><input type="text" name="descm" id="descm" style="width:70px; height:10px; font-size: 8px;" class="k-textbox" disabled onFocus="setFocusDisable()"></td>
            <td><input type="text" name="descp" id="descp" style="width:20px; height:10px; font-size: 8px;" class="k-textbox" disabled onKeyUp="validaPorcDesc(event)" onBlur="validaPorcDesc(event)" tabindex=1></td>
            <td>%</td>
          </tr>
          <tr>
            <td>Precio c/desc</td>
            <td><input type="text" name="descmdes" id="descmdes" style="width:70px; height:10px; font-size: 8px;" class="k-textbox" disabled onFocus="setFocusDisable()"></td>
            <td colspan="2">&nbsp;</td>
            </tr>
          <tr>
            <td>Adicionales</td>
            <td><input type="text" name="subequipos" id="subequipos" style="width:70px; height:10px; font-size: 8px;" class="k-textbox" disabled onFocus="setFocusDisable()"></td>
            <td colspan="2">&nbsp;</td>
            </tr>
          <tr>
            <td>Total</td>
            <td><input type="text" name="total" id="total" style="width:70px; height:10px; font-size: 8px;" class="k-textbox" disabled onFocus="setFocusDisable()"></td>
            <td colspan="2">&nbsp;</td>
            </tr>
          <tr>
            <td>Apartado</td>
            <td><input type="text" name="anticipo" id="anticipo" style="width:70px; height:10px; font-size: 8px;" class="k-textbox" disabled onFocus="setFocusDisable()"></td>
            <td colspan="2">&nbsp;</td>
            </tr>
          <tr>
            <td>Enganche</td>
            <td><input type="text" name="enganche" id="enganche" style="width:70px; height:10px; font-size: 8px;" class="k-textbox" disabled onFocus="setFocusDisable()"></td>
            <td><input type="text" name="penganche" id="penganche" style="width:20px; height:10px; font-size: 8px;" class="k-textbox"disabled onKeyUp="validaPorcDesc(event)" onBlur="validaPorcDesc(event)" tabindex=2></td>
            <td>%</td>
          </tr>
          <tr>
            <td>Diferido</td>
            <td><input type="text" name="diferido" id="diferido" style="width:70px; height:10px; font-size: 8px;" class="k-textbox" disabled onFocus="setFocusDisable()"></td>
            <td><input type="text" name="pdiferido" id="pdiferido" style="width:20px; height:10px; font-size: 8px;" class="k-textbox" disabled onKeyUp="validaPorcDesc(event)" onBlur="validaPorcDesc(event)" tabindex=3></td>
            <td>%</td>
          </tr>
          <tr>
            <td>Pago Final</td>
            <td><input type="text" name="pago_final" id="pago_final" style="width:70px; height:10px; font-size: 8px;" class="k-textbox" disabled onFocus="setFocusDisable()"></td>
            <td><input type="text" name="ppago_final" id="ppago_final" style="width:20px; height:10px; font-size: 8px;" class="k-textbox" disabled></td>
            <td>%</td>
          </tr>
          <tr>
            <td>Cuota Única</td>
            <td><input type="text" name="gastos_admin" id="gastos_admin" style="width:70px; height:10px; font-size: 8px;" class="k-textbox" disabled onFocus="setFocusDisable()"></td>
            <td colspan="2">&nbsp;</td>
            </tr>
          <tr>
            <td></td>
            <td></td>
            <td colspan="2"></td>
          </tr>
        </table>
        </div></td>
        <td width="389" style="vertical-align:top"><div id="panel" class="k-block k-shadow" style="margin-bottom: 2px; margin-top: 2px; margin-left: 2px; margin-right: 2px">
              <div class="k-header k-shadow" style="font: bold">COMPONENTES DE VIVIENDA</div><table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td height="24"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="19">Est</td>
                <td width="40"><input type="text" name="nest" id="nest" style="width:30px; height:10px; font-size: 8px;" onFocus="setFocusDisable()" class="k-textbox" disabled tabindex=5></td>
                <td width="31"><input type="button" name="btn_est" id="btn_est" value="+" class="k-button" disabled onclick="openWinSubEquipos(),obtieneSubEquipos('E')" tabindex=6></td>
                <td width="26">Bod</td>
                <td width="40"><input type="text" name="nbod" id="nbod" style="width:30px; height:10px; font-size: 8px;" onFocus="setFocusDisable()" class="k-textbox" disabled tabindex=7></td>
                <td width="8"><input type="button" name="btn_bod" id="btn_bod" value="+" class="k-button" disabled onclick="openWinSubEquipos(),obtieneSubEquipos('B')" tabindex=8></td>
                <td align="right"><input type="button" name="btn_del" id="btn_del" value="Elimina" class="k-button" onclick="quitarSubEquiposValida()" tabindex=9></td>
              </tr>
            </table>
            </td>
            </tr>
          <tr>
            <td valign="top">            	
            	<table id="dg_equipos" style="width:30px">
                <thead>
                    <tr>                   
                        <th data-field="" width="10px"></th>
                        <th data-field="tipo" width='20px'>Tipo</th>
                        <th data-field="id_equnrx" width='110px'>Descripcion</th>
                        <th data-field="netwr" width='70px'>Precio</th>
                        <th data-field="id_equnr" width='110px'>Equipo</th>
                    </tr>
                </thead>
                <tbody>                   
                </tbody>
            </table>
		    </td>
            </tr>
          <tr>
            <td><table width="100%" border="0" cellpadding="0" cellspacing="0">              
              <tr>
                <td colspan="3" align="center"><table border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="133"><input type="radio" name="rdo_tpago" id="rdo_tpago" value="1" onClick="selectDatosByPago('CH','init')" checked="checked" tabindex=10 style="width: 10px; height: 10px;">
                      Credito Hipotecario</td>
                    <td width="106"><input type="radio" name="rdo_tpago" id="rdo_tpago3" value="2" onClick="selectDatosByPago('FO','init')" tabindex=11 style="width: 10px; height: 10px;">
                      Financiamiento</td>
                    <td width="86"><input type="radio" name="rdo_tpago" id="rdo_tpago2" value="3" onClick="selectDatosByPago('CO','init')" tabindex=12 style="width: 10px; height: 10px;">
                      Contado</td>
                    </tr>
                </table></td>
                </tr>
              </table></td>
            </tr>
          </table>
        </div></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><div id="panel" class="k-block k-shadow" style="margin-bottom: 2px; margin-top: 2px; margin-left: 2px; margin-right: 2px">
              <div class="k-header k-shadow" style="font: bold">DETALLES PAGO</div><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td rowspan="3" valign="top">
        	<table id="dg_detallepagos" tabindex=14 width="100px">
                <thead>
                    <tr>  
                        <th data-field="" width="20px">&nbsp;</th>
                        <th data-field="conse" width='30px'>Concepto</th>
                        <th data-field="concex" width='90px'>Descripción</th>
                        <th data-field="flim" width='70px'>Fecha</th>
                        <th data-field="total" width='80px'>Costo</th>
                        <th data-field="sim1" width='60px'>Opcion1</th>
                        <!-- th data-field="sim2" width='60px'>Opcion2</th -->
                    </tr>
                </thead>
                <tbody>                   
                </tbody>
            </table>
		</td>
        <td height="191" valign="top"><table border="0" align="right" cellpadding="0" cellspacing="0">
          <tr>
            <td align="center"><img src="<%=basePath %>/images/icons/semaforop.png" id="estatus_simulador" name="estatus_simulador" title="PASO"/><!--div id="estatus_simulador" align="center" style="background-color:rgb(15,99,30);"> PASO </div--></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td align="center"><input class="k-button" type="button" name="activasimulador" id="activasimulador" value="Activar" title="Activar" onClick="simuladorActDes()" style="width: 60px"></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="23" align="right"><input type="button" name="btn_print" id="btn_print" value="Imprimir" class="k-button" style="width: 60px" onclick="setAdminCotizacion('print')" disabled="disabled"></td>
      </tr>
      <tr>
        <td height="24" align="right"><input type="button" name="btn_save" id="btn_save" value="Guardar" class="k-button" onclick="setAdminCotizacion('guardar')" style="width: 60px"></td>
      </tr>
    </table></div></td>
  </tr>
</table>
<div id="winsubequipos">
	<table width="467" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td height="200" colspan="2" valign="top">    
    	<div class="weather" id="tab_se">
    		<table id="dg_subequipos" >
                <thead>
                    <tr>                   
                        <th data-field="" width="20px">&nbsp;</th>
                        <th data-field="tipo" width='30px'>Tipo</th>
                        <th data-field="id_equnrx" width='100px'>Descripción</th>
                        <th data-field="netwr" width='70px'>Precio</th>
                        <th data-field="id_equnr" width='100px'>Equipo</th>
                    </tr>
                </thead>
                <tbody>                   
                </tbody>
            </table>
    	</div>
    </td>
  </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="201" align="center"><input type="button" name="agregar" id="agregar" value="Agregar" class="k-button" onclick="enviaSubEquiposValida()"/></td>
    <td width="216" align="center"><input type="button" name="cancelar" id="cancelar" value="Cancelar" class="k-button" onclick="closeWinSubEqu()"/></td>
  </tr>
</table> 

<div id="windowGridBusquedaClienteRegistroTicket"></div>
<input type="hidden" id="id_eln" value="<%= request.getParameter("idequ_get") %>"/>
<input type="hidden" id="from_is" value="<%= request.getParameter("from") %>"/>
<input type="hidden" id="idCotizacion" value ="<%=request.getParameter("idCotizacion") %>"/>
<input type="hidden" id="idCotizacionZ" value ="<%=request.getParameter("idCotizacionZ") %>"/>
<input type="hidden" id="idCliente" value ="<%= request.getParameter("idCliente") %>"/>
<input type="hidden" id="idCarCliente" value ="<%= request.getParameter("idCarCliente") %>"/>
<input type="hidden" id="idPedido" value ="<%= request.getParameter("idPedido") %>"/>
<input type="hidden" id="nombreCte" value ="<%= request.getParameter("nombreCte") %>"/>
<input type="hidden" id="datosEquipos" value ="<%= request.getParameter("datos_equipos") %>"/>
<input type="hidden" id="equipoSelect" value ="<%= request.getParameter("equipo_select") %>"/>
</div>

<iframe id="uploadIFrame" scrolling="no" frameborder="0" hidefocus="true" style="text-align: center;vertical-align: top; border-style: none; margin: 0px; width: 0px; height: 0px;" src="<%=basePath %>/ImprimeSimulador.htm"></iframe>

</body>
</html>