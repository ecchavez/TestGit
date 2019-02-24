<%@page import="mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto"%>
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>
	<head>
	<%
	UsuarioDto user = (UsuarioDto) request.getSession().getAttribute("usrSession");
	String path = request.getContextPath();
	String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
	
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1); 
	%>
	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Registro Tickets</title>
		
		<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
		<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
		<script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js"></script>   
	
		<script type="text/javascript">
				var contexPath="<%=basePath %>";
		 </script> 
		 <script src="<%=basePath %>/js/escritorio/EscritorioJQ.js"></script>      
		 <script src="<%=basePath %>/js/tickets/RegistroTickets.js"></script>      
		 <script src="<%=basePath %>/js/utils.js"></script>      
		<script>
		var open_menu=0;
		
		$(document).ready(function() {
			$("#mensajes_main").html('<%=request.getParameter("paramMensaje")==null?"":request.getParameter("paramMensaje")%>');
			$("#sample-bar").jixedbar({    
				showOnTop: true,    
				transparent: true,    
				opacity: 0.5,    
				slideSpeed: "slow",    
				roundedCorners: false,    
				roundedButtons: false,    
				menuFadeSpeed: "slow",    
				tooltipFadeSpeed: "fast",    
				tooltipFadeOpacity: 0.5
			});
	
			 $("#dg_vicios").kendoGrid({							  
				  filterable: false,
				  height: 120,
				  sortable: false,				  
				  pageable: false,
				  resizable:  true,
				  scrollable: true,
				  selectable: "row",
				  //dataSource: subEquipoDS										  
			 });
			 
			 $("#estatus").kendoComboBox({
				dataTextField: "ticket_txt",
				dataValueField: "ticket_val",
				dataSource: [
				//{ ticket_estatus: "ABIERTO" },
		        { ticket_txt: "APROBAR", ticket_val: "APROBAR"},
		        { ticket_txt: "CONFIRMAR", ticket_val: "CONFIRMAR"},
				{ ticket_txt: "EJECUTADO", ticket_val: "EJECUTADO"},
				{ ticket_txt: "CERRAR", ticket_val: "CERRAR" }],
				filter: "contains",
				change: onChangeEstausProcesoTicket,
				suggest: true
		    }); 	
	
			cargaValoresProceso();

			//fechat = $("#fechat").kendoDatePicker({
			fechat = $("#fechat").kendoDateTimePicker({
		    	//value:new Date(),
                interval: 1,
                format: "dd/MM/yyyy HH:mm:00",
                min: new Date()
		    	//value:new Date(),
		    	//change: fechaInicialFechat,
		    	//format: "dd/MM/yyyy"
		    });
		    
		});
						
			function valida_openclose()
			{
				if(open_menu==0)
				{
					open_menu=1;
					var cssObj = {      
					'left':'0px'    
					}    
					$("#my-scrollable-div").css(cssObj);
				}
				else
				{
					open_menu=0;
					var cssObj = {      
					'left':'-200px'    
					}    
					$("#my-scrollable-div").css(cssObj);
				}
					
			}
	</script>
		<link href="<%=basePath %>/jixedbar/default/jx.stylesheet.css" type="text/css"  rel="stylesheet" />
		<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
		<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
		<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />
		 
		<style type="text/css">
		  
			.k-grid {
				width: 90%;
			}
		
		</style>
	
	</head>
	
	<body topmargin="0">
	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
		<td>&nbsp;</td>
	  </tr>
	  <tr>
		<td valign="top"><div id="area_trabajo">
		  <div id="registroTicket" style="height: 510px; width:950px">
			<form id="procesoTicketForm" name="procesoTicketForm" action="" method="post">
			  <table border="0">
				<tbody>
				  <tr>
					<td width="120" align="right">Ticket:</td>
					<td width="113"><div id="idTicket"></div></td>
					<td width="262">&nbsp;</td>
					<td width="46" align="right">&Aacute;rea</td>
					<td width="136">CONSTRUCCI&Oacute;N&nbsp;</td>
					<td width="236">&nbsp;</td>
				  </tr>
				  <tr>
					<td align="right"> Fase:</td>
					<td><div id="idFase"></div></td>
					<td>&nbsp;</td>
					<td align="right">Cliente:</td>
					<td><div id="cliente"></div></td>
					<td>&nbsp;</td>
				  </tr>
				  <tr>
					<td align="right"> Equipo:</td>
					<td><div id="idEquipo"></div></td>
					<td>&nbsp;</td>
					<td align="right">Email:</td>
					<td><div id="correo"></div></td>
					<td><div id="txt_uts_desc"></div></td>
				  </tr>
				  <tr>
					<td align="right" colspan="2">&nbsp;</td>
					<td align="right"></td>
					<td align="right">Tel&eacute;fono:</td>
					<td align="left"><div id="telefono"></div></td>
					<td align="right">&nbsp;</td>
				  </tr>
				  <tr>
					<td align="right" colspan="2">&nbsp;</td>
					<td align="right"></td>
					<td align="right">CCP:</td>
					<td align="left"><div id="ccp"></div></td>
					<td align="right">&nbsp;</td>
				  </tr>
				  <tr valign="top">
					<td align="right">Vicios Ocultos:</td>
					<td align="center" colspan="2">
					  <div id="prioridad"></div></td>
					<td colspan="2" align="center">&nbsp;</td>
					<td>&nbsp;</td>
				  </tr>
				  <tr valign="top">
					<td align="right">&nbsp;</td>
					<td colspan="5"><table id="dg_vicios" cellpadding="0" cellspacing="0">
					  <thead>
						<tr>
						  <th data-field="tipo" style="text-align:center" width="23px"># Cvo</th>
						  <th data-field="id_equnrx" style="text-align:center" width="110px"> Zona /&Aacute;rea</th>
						  <th data-field="netwr" style="text-align:center" width="165px"> Observaciones/ Descripci&oacute;n</th>
						  <th data-field="id_foto" style="text-align:center" width="20px"> Foto</th>
						  <th data-field="id_estatus" style="text-align:center" width="70px"> Estatus</th>
						  <th data-field="id_motivo_rechazo" style="text-align:center" width="110px">Motivo de Rechazo</th>
						</tr>
					  </thead>
					  <tbody>
					  </tbody>
					</table></td>
				  </tr>
				  <tr valign="top">
					<td align="right">&nbsp;</td>
					<td colspan="5">&nbsp;</td>
				  </tr>
				  <tr valign="middle">
					<td align="right">Recibida por:</td>
					<td><div id="recibido"></div></td>
					<td>&nbsp;</td>
					<td>Estatus:</td>
					<td><select id="estatus" name="estatus"></select></td>
					<td>&nbsp;</td>
				  </tr>
				  <tr valign="middle">
					<td align="right">Asignada a:</td>
					<td><span style="text-align:left"><div id="asignado"></div></span></td>
					<td>&nbsp;</td>
					<td><div id="labelPDF">PDF:</div></td>
					<td colspan="2"><input type="file" name="fileData" id="fileData"></td>
				  </tr>
				  <tr valign="middle">
					<td align="right">Aprobado por:</td>
					<td><div id="aprobado"></div></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				  </tr>
				  <tr valign="middle">
					<td align="right">Fecha de atenci&oacute;n:</td>
					<td><input type="text" id="fechat" name="fechat" value="" size="30" maxlength="30" readonly></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				  </tr>
				  <tr valign="top">
					<td align="right">&nbsp;</td>
					<td colspan="5">&nbsp;</td>
				  </tr>
                  <tr>
                            <td align="right">Asunto:</td>
                            <td colspan="4">
                                <div id="asunto"></div>
                            </td>
                            <td>&nbsp;
                                
                            </td>
						</tr>
				  <tr valign="top">
					<td align="right">Observaciones:<br>
				    <a href="#" onclick="dialogoParamsVerHistorial();return false;">Historial</a></td>
					<td colspan="5"><textarea name="observaciones" cols="82" rows="5" id="observaciones" style="text-transform: uppercase; width:17.99cm; font-size: 10px !important; font-family:Century Gothic" onblur="javascript:this.value=this.value.toUpperCase().trim();"></textarea></td>

				  </tr>
				  <tr valign="top">
					<td colspan="6"><input type="button" class="k-button" onclick="procesoTicketConstruccion()" value="Aceptar"/>&nbsp;
				    <input type="button" class="k-button" onClick="cancelProcesoVicio()" value="Cancelar"/></td>
				  </tr>
				</tbody>
			  </table>
          <input type="hidden" id="idTicketHidden" name="idTicket">
          <input type="hidden" id="idFaseHidden" name="idFase">
          <input type="hidden" id="aprovadoHidden" name="aprobado">
          <input type="hidden" id="asignadoHidden" name="asignado">
          <input type="hidden" id="recibidoHidden" name="recibido">
          <input type="hidden" id="idEquipoHidden" name="idEquipo">
          <input type="hidden" id="clienteHidden" name="cliente">
          <input type="hidden" id="prioridadHidden" name="prioridad">
          <input type="hidden" id="asuntoHidden" name="asunto">
			</form>
		  </div>
		</div></td>
	  </tr>
	</table>
	</div>
	
	<div id="windowVerHistorial"></div>

<div id="sample-bar" >
   <ul>
      <li title=""><div id="base_estatus" style="width:17px; height:20px; vertical-align:middle">>>></div></li>
   </ul>
   <span class="jx-separator-left"></span>
   <div id="mensajes_main">Mensajes de la aplicacion</div>
   <span class="jx-separator-right"></span>
</div>
	
	</body>
	</html>