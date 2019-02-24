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
		<script type="text/javascript">var contexPath="<%=basePath %>";</script>
		<script src="<%=basePath %>/js/clientes/ClientesJQ.js"></script>
		<script src="<%=basePath %>/js/reportes/comisiones/Comisiones.js"></script>
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
				$("#equipoReportefin").kendoComboBox({
					dataTextField: "id_equnrx",
					dataValueField: "id_equnr",
					dataSource: [],
					filter: "contains",
					//change: onChangeEquipoRegistroReporteFin,
					suggest: true
			    });	 	 
			
			fillComboRegistroReporte("faces");
		    
         });

        function getFiltroRepComision()
  		{
  			window.parent.executeFindBusquedaCatalogoReporteFilter($("#faseReporte").val(),$("#equipoReporte").val(),$("#equipoReportefin").val());
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
									<div id="txt_desc_fase1"></div>&nbsp;
								</td>
							</tr>
							<tr>
								<td>Equipo inicial</td>
								<td>
									<select id="equipoReporte" name="equipoReporte" style="width: 5cm;">
									</select>
								</td>
								<td>
									<div id="txt_desc_equ1"></div>&nbsp;
								</td>
							</tr>
							<tr>
								<td>Equipo final</td>
								<td>
									<select id="equipoReportefin" name="equipoReportefin" style="width: 5cm;">
									</select>
								</td>
								<td>
									<div id="txt_desc_equfin1"></div>&nbsp;
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
			<tr>
				
			</tr>
			<tr>
				<td align="left">
					<input type="button" class="k-button" id="aceptarParam" value="Aceptar" onclick="getFiltroRepComision()"/>&nbsp;
					<input type="button" class="k-button" id="cancelarParam" value="Cancelar" onClick="cancelarGridFiltroBusquedaCriteriosReporteEstadoCuenta()"/>
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