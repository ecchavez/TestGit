<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 

response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>
		<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
		<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
		<script src="<%=basePath %>/kendo/js/console.js"></script>
		<script type="text/javascript">var contexPath="<%=basePath %>";</script>
        <script src="<%=basePath %>/js/reportes/estadocuenta/EstadoCuenta.js"></script>
		<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
		<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
		<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />

		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title>Cliente</title>
		<script>
			$(document).ready(function () {
				var dataSourceGridClienteReporte = new kendo.data.DataSource({
			        data: [],	    	    	        
			        pageSize: 50,
			        messages: {
			            empty: "No hay registros que mostrar"
			        }
			    });

				$("#gridclientesReporte").kendoGrid( {
					 dataSource : dataSourceGridClienteReporte,
					 selectable:"row",
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
					 columns : [ {
						field : "id_car_cli",
						title : "Id Cliente",
						width: 100
							
					}, {
						field : "nombre1",
						title : "Nombre",
						sortable: true,
						width: 110
					}, {
						field : "nombre2",
						title : "Segundo Nombre",
						width: 110
					}, {
						field : "app_pat",
						title : "Apellido",
						width: 100
					}, {
						field : "app_mat",
						title : "Segundo Apellido",
						width: 100
					}, {
						field : "fch_ncm",
						title : "F.Nac",
						width: 100
					}]
	            });	
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
		<form action="" id="GridBusquedaClienteReporteForm">
		<table width="306" border="0" cellpadding="1" cellspacing="1" align="left">
			<tr align="center">
                <td colspan="2" align="left"><a class="k-button" id="seleccionCarCli" onclick="dialogoParamsClienteRegistroReporte()">Seleccionar</a></td>
			</tr>		
			<tr align="center">
				<td colspan="2" align="left">
					<div id="gridClienteReporteWrapper">					
						<div id="gridclientesReporte" style="height: 330px; width:650px"></div>
					</div> 
				</td>
			</tr>
			<tr>
				<td colspan="2" align="left">
					<input type="button" class="k-button" id="aceptarParam" value="Aceptar" onclick="addNomClienteParams()"/>&nbsp;
					<input type="button" class="k-button" id="cancelarParam" value="Cancelar" onClick="cancelarNomClienteParams()"/>
				</td>
			</tr> 
			<tr>
			   <td colspan="2"><div id="mensajes_main">Mensajes de la aplicacion</div></td>
			</tr>			
		</table>
		<div id="windowParamsBusquedaClienteRegistroReporte"></div>
		</form>
	</body>
</html>