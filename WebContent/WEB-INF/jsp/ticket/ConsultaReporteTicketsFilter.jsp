<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
String grid=request.getParameter("queGrid");

response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>		<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
		<script src="<%=basePath %>/js/jquery.blockUI.js"></script>
		<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
		<script src="<%=basePath %>/kendo/js/console.js"></script>
		<script type="text/javascript">var contexPath="<%=basePath %>";</script>
		<script src="<%=basePath %>/js/clientes/ClientesJQ.js"></script>
		<script src="<%=basePath %>/js/tickets/RegistroTickets.js"></script>
		<script src="<%=basePath %>/js/utils.js"></script>
		<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
		<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
		<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />

		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title><%=grid%></title>
		<script>
           
         $(document).ready(function () {
         
	         cargaGrid('<%=grid%>');
			$("#estatus").kendoComboBox({
				dataTextField: "ticket_estatus",
				dataValueField: "ticket_estatus",
				dataSource: [
				{ ticket_estatus: "ABIERTO" },
		        { ticket_estatus: "EN PROCESO" },
		        { ticket_estatus: "EJECUTADO" },
				{ ticket_estatus: "RECHAZADO" },
				{ ticket_estatus: "CERRADO" },
				],
				filter: "contains",
				//change: onChangeAreaRegistroTicket,
				suggest: true
		    });
			$("#idVendedorFiltroTicket").kendoDropDownList({
		        dataTextField: "via_conx",
		        dataValueField: "via_con",
		        optionLabel: " "
		    });
			$("#idArea").kendoComboBox({
			dataTextField: "id_ticket_areax",
			dataValueField: "id_ticket_area",
			dataSource: [],
			filter: "contains",
			//change: onChangeAreaRegistroTicket,
			suggest: true
	    	});	  
			$("#idFase").kendoComboBox({
				dataTextField: "text",
				dataValueField: "id",
				dataSource: [],
				filter: "contains",
				change: onChangeFacesRegistroTicket,
				suggest: true
		    });	 
			$("#idEquipo").kendoComboBox({
				dataTextField: "id_equnrx",
				dataValueField: "id_equnr",
				dataSource: [],
				filter: "contains",
				//change: onChangeEquipoRegistroTicket,
				suggest: true
		    });	 


		    fechaInicialFiltroCatalogoTicket = $("#fechaInicialFiltroCatalogoTicket").kendoDatePicker({
		    	change: fechaInicialFiltroTicketEvent,
		    	format: "dd/MM/yyyy"
		    });

		    fechaFinalFiltroCatalogoTicket = $("#fechaFinalFiltroCatalogoTicket").kendoDatePicker({
		    	change: fechaFinalFiltroTicketEvent,
		    	format: "dd/MM/yyyy"
		    });    

			fillComboRegistroTicket("areas");
			fillComboRegistroTicket("faces");
		    
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
		<table align="center">
			<tr>
				<td>
					<fieldset>
						<legend><b> B&uacute;squeda por Ticket </b></legend>
						<table width="306" border="0" align="left">
							<tr>
								<td>#Ticket</td>
								<td><input type="text" id="idTicketInicial" name="idTicketInicial" value="" onkeypress="return captureEnteros(event)" size="25" maxlength="10" class="k-textbox"></td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>Fecha Ticket</td>
								<td><input type="text" id="fechaInicialFiltroCatalogoTicket" name="fechaInicialFiltroCatalogoTicket" value="" size="25" maxlength="30"></td>
								<td>a</td>
								<td><input type="text" id="fechaFinalFiltroCatalogoTicket" name="fechaFinalFiltroCatalogoTicket" value="" size="25" maxlength="30"></td>
							</tr>
							<tr valign="bottom">
								<td>Area</td>
								<td colspan="3">
									<select id="idArea" name="idArea" style="width: 7cm;">
									</select>
									<input type="hidden" id="nomAreaTicket">
								</td>
							</tr>
							<tr valign="bottom">
								<td>Estatus</td>
								<td colspan="3">
									<select id="estatus" name="estatus" style="width: 7cm;">
									</select>
									<input type="hidden" id="nomEstatusTicket">
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
					<fieldset>
						<legend><b> B&uacute;squeda por Equipo </b></legend>
						<table width="306" border="0" align="left">
							<tr>
								<td>Fase</td>
								<td colspan="2">
									<select id="idFase" name="idFase" style="width: 7cm;">
									</select>
									<input type="hidden" id="txt_desc_fase">
								</td>
							</tr>
							<tr>
								<td>Equipo</td>
								<td colspan="2">
									<select id="idEquipo" name="idEquipo" style="width: 7cm;">
									</select>
									<input type="hidden" id="txt_desc_equ">
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td align="left">
					<input type="button" class="k-button" id="aceptarParam" value="Aceptar" onclick="executeReporteTicketViciosFilter()"/>&nbsp;
					<input type="button" class="k-button" id="cancelarParam" value="Cancelar" onClick="cancelarGridFiltroBusquedaCriteriosClienteTicket()"/><select id="idArea" name="idArea" style="width: 7cm; visibility:hidden;">
									</select>
				</td>
			</tr> 
		</table>
		<table>
			<tr align="center">
				<td colspan="3" align="left">
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
			</table>
			<div id="windowParamsCarCli"></div>		
		</form>
		<div id="windowGridBusquedaClienteRegistroTicket"></div>
	</body>

</html>