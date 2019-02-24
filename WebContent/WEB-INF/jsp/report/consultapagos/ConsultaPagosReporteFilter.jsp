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
		<script src="<%=basePath %>/kendo/js/cultures/kendo.culture.es-EC.min.js"></script>
		<script type="text/javascript">var contexPath="<%=basePath %>";</script>
		<script src="<%=basePath %>/js/clientes/ClientesJQ.js"></script>
		<script src="<%=basePath %>/js/reportes/consultapagos/ConsultaPagos.js"></script>
		<script src="<%=basePath %>/js/utils.js"></script>
		<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
		<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
		<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />

		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title><%=grid%></title>
		<script>

		 kendo.culture("es-EC");
           
         $(document).ready(function () {
         
	         cargaGrid('<%=grid%>');

			$("#idVendedorFiltroReporte").kendoDropDownList({
		        dataTextField: "via_conx",
		        dataValueField: "via_con",
		        optionLabel: " "
		    });	 
			$("#faseReporte").kendoComboBox({
				dataTextField: "text",
				dataValueField: "id",
				dataSource: [],
				filter: "contains",
				change: onChangeFacesRegistroReporte,
				suggest: true
		    });	 
			$("#equipoReporte").kendoComboBox({
				dataTextField: "id_equnrx",
				dataValueField: "id_equnr",
				dataSource: [],
				filter: "contains",
				//change: onChangeEquipoRegistroReporte,
				suggest: true
		    });
			$("#medioPago").kendoComboBox({
				dataTextField: "valor",
				dataValueField: "id_val",
				dataSource: [],
				filter: "contains",
				//change: onChangeEquipoRegistroReporte,
				suggest: true
		    });
			var start= $("#fechaIni").kendoDatePicker({
		 			format: "dd/MM/yyyy",
		 			width: 20,
		 			min: new Date(1, 0, 1900),
		 			change: startChange
			}).data("kendoDatePicker");
			var end= $("#fechaFin").kendoDatePicker({
	 			format: "dd/MM/yyyy",
	 			width: 20,
	 			min: new Date(1, 0, 1900),
				change: endChange
			}).data("kendoDatePicker");

			fillComboRegistroReporte("faces");	

	         function startChange() {
			     var startDate = start.value(),
			     endDate = end.value();
			
			     if (startDate) {
			         startDate = new Date(startDate);
			         startDate.setDate(startDate.getDate());
			         end.min(startDate);
			     } else if (endDate) {
			         start.max(new Date(endDate));
			     } else {
			         endDate = new Date();
			         start.max(endDate);
			         end.min(endDate);
			     }
			 }
			
			 function endChange() {
			     var endDate = end.value(),
			     startDate = start.value();
			
			     if (endDate) {
			         endDate = new Date(endDate);
			         endDate.setDate(endDate.getDate());
			         start.max(endDate);
			     } else if (startDate) {
			         end.min(new Date(startDate));
			     } else {
			         endDate = new Date();
			         start.max(endDate);
			         end.min(endDate);
			     }
			 }

	         start.max(end.value());
	         end.min(start.value());				
		    
         });

         function getFiltroReporteEdoCta()
         {
			 var fechaIni=$("#fechaIni").val();
			 var fechaFin=$("#fechaFin").val();
			 var medioPago=$("#medioPago").data("kendoComboBox");
			 
        	 window.parent.executeFindBusquedaCatalogoReporteFilter($("#claveClienteReporte").val(), $("#faseReporte").val(), $("#equipoReporte").val(), fechaIni, fechaFin, medioPago.value());
         }


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
						<legend><b> B&uacute;squeda por Cliente </b></legend>
						<table width="306" border="0" align="left">
							<tr>
								<td nowrap="nowrap">Cliente</td>
								<td nowrap="nowrap">
									<input type="text" id="claveClienteReporte" readonly="readonly" name="claveClienteReporte" value="" size="25" maxlength="30" class="k-textbox">&nbsp;&nbsp;&nbsp;
									<input type="button" id="" name="" class="k-button" onclick="dialogoGridBusquedaReporteEstadoCuenta()" value="...">
								</td>
								<td nowrap="nowrap">
									<div id="nomClienteReporte"></div>&nbsp;
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
								<td>
									<select id="faseReporte" name="faseReporte" style="width: 5cm;">
									</select>
								</td>
								<td>
									<div id="txt_desc_fase"></div>&nbsp;
								</td>
							</tr>
							<tr>
								<td>Equipo</td>
								<td>
									<select id="equipoReporte" name="equipoReporte" style="width: 5cm;">
									</select>
								</td>
								<td>
									<div id="txt_desc_equ"></div>&nbsp;
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
					<fieldset>
						<legend><b> B&uacute;squeda por Fecha de Registro</b></legend>
						<table width="306" border="0" align="left">
							<tr>
								<td>F. Registro</td>
								<td>
									<input id="fechaIni" name="fechaIni" style="width: 2.5cm;"/> a <input id="fechaFin" name="fechaFin" style="width: 2.5cm;"/>
								</td>

							</tr>

						</table>
					</fieldset>
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
					<fieldset>
						<legend><b> B&uacute;squeda por Medio de Pago</b></legend>
						<table width="306" border="0" align="left">

							<tr>
								<td>Medio de Pago</td>
								<td>
									<select id="medioPago" name="medioPago" style="width: 5cm;">
									</select>
								</td>
								<td>
									<div id="txt_med_pag"></div>&nbsp;
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
			
			<tr>
				<td align="left">
					<input type="button" class="k-button" id="aceptarParam" value="Aceptar" onclick="getFiltroReporteEdoCta()"/>&nbsp;
					<input type="button" class="k-button" id="cancelarParam" value="Cancelar" onClick="window.parent.cancelarGridFiltroBusquedaCriteriosReporteEstadoCuenta()"/>
				</td>
			</tr> 
			<tr>
				<td>
					<div id="mensajes_main">Mensajes de la aplicacion</div>
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
		<div id="windowGridBusquedaClienteRegistroReporte"></div>
	</body>

</html>