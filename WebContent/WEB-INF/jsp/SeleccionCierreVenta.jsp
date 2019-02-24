<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
String from=request.getParameter("from");
%>
		<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
		<script src="<%=basePath %>/js/jquery.blockUI.js"></script>
		
		<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
		<script src="<%=basePath %>/kendo/js/console.js"></script>
		<script type="text/javascript">var contexPath="<%=basePath %>";</script>
		<script src="<%=basePath %>/js/cierreVenta/monitorCotPedJQ.js"></script>
		<script src="<%=basePath %>/js/clientes/ClientesSAPJQ.js"></script>
		<script src="<%=basePath %>/js/utils.js"></script>
		<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
        <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
	    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />

		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title>Parámetros de selección</title>
		<script>
           
         $(document).ready(function () {
               var temp_aux="<input type=\"checkbox\" id=\"checkCliente\" />";
               
               var dataSourceFindClie = new kendo.data.DataSource( {
				   data : [ ],
				   pageSize : 50
			   });
			
			
				$("#dg_clientes").empty();
				$("#dg_clientes").kendoGrid( {
					 dataSource : dataSourceFindClie,
					 change: onChangeCtes,
					 selectable: "row",
					 resizable: true,							
					 sortable: true,
					 reorderable: true,
					 height:250,
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
					 columns : [ 
					{
						  title: "", 
						   template: temp_aux,  
						   width:30
					},
	                 {
						field : "idCliente",
						title : "Id Cliente"
						
					 },
					{
						field : "nombre",
						title : "Nombre"
					
					}, {
						field : "snombre",
						title : "Segundo Nombre"
						
					}, {
						field : "aapat",
						title : "Apellido"
						
					}, {
						field : "amat",
						title : "Segundo Apellido"
						
					}, 
					{
						field : "dptoCasa",
						title : "Dpto"
						
					},
					{
						field : "fchNac",
						title : "F Nacimiento"
						
					}
					,{
						field : "mail",
						title : "Correo"
						
					}]
					}).data("kendoGrid");

			  
			  
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
<form action="" id="selecCierre">
<div>
<input type="hidden" id="screenFrom" name="screenFrom" value="<%=from%>" />
<table width="620" border="0" cellpadding="0" cellspacing="0">
  <% if(from.equals("pagoIni") || from.equals("cotizador")){%>
  <tr>
    <td width="113" height="31">Cotizaci&oacute;n</td>
    <td width="151"><input type="text" id="cotizacion" onkeyup="this.value=this.value.toUpperCase();" name="cotizacion" class="k-textbox" /></td>
    <td width="29"><input type="button" id="cotBtn" value="..." class="k-button" onClick="openParamsCierre('Cotizacion')"/></td>
    <td width="21">&nbsp;</td>
    <td width="101">&nbsp;</td>
    <td width="151">&nbsp;</td>
    <td width="34">&nbsp;</td>
  </tr>
  <%} %>
  
  <% if(from.equals("pagoParc") || from.equals("monitorPagoPrin") ){%>
  <tr>
    <td width="113" height="31">Pedido</td>
    <td width="151"><input type="text" id="pedido" onkeyup="this.value=this.value.toUpperCase();" name="pedido" class="k-textbox"  /></td>
    <td width="29"><input type="button" id="pedBtn" class="k-button" value="..." onClick="openParamsCierre('Pedido')" /></td>
    <td width="21">&nbsp;</td>
    <td width="101">&nbsp;</td>
    <td width="151">&nbsp;</td>
    <td width="34">&nbsp;</td>
  </tr>
  <%} %>
  <tr>
     <td height="31">Nombre</td>
     <td><input type="text" id="nombre"  onkeyup="this.value=this.value.toUpperCase();" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
     <td><input type="button" class="k-button" value="..." onClick="openParamsCierre('Nombre')" /></td>
     <td>&nbsp;</td>
     <td>Apellido Materno</td>
     <td><input type="text" id="apMaterno" onkeyup="this.value=this.value.toUpperCase();" class="k-textbox" onKeyPress="return captureNombres(event);" /></td>
	 <td><input type="button" class="k-button" value="..." onClick="openParamsCierre('ApMaterno')"/></td>
  </tr>
  <tr>
    <td height="31">Segundo Nombre</td>
    <td><input type="text" id="nombre2" onkeyup="this.value=this.value.toUpperCase();" class="k-textbox" onKeyPress="return captureNombres(event);" /></td>
	<td><input type="button" class="k-button" value="..." onClick="openParamsCierre('SegNombre')"/></td>
	<td>&nbsp;</td>
    <td>Fase</td>
    <td><input id="fase" onkeyup="this.value=this.value.toUpperCase();" name="fase" class="k-textbox" /></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="32">Apellido Paterno</td>
    <td><input type="text" id="apPaterno"  onkeyup="this.value=this.value.toUpperCase();" class="k-textbox" onKeyPress="return captureNombres(event);" /></td>
	<td><input type="button" class="k-button" value="..." onClick="openParamsCierre('ApPaterno')"/></td>
	<td>&nbsp;</td>
    <td>Equipo</td>
    <td><input id="equipo" name="equipo" onkeyup="this.value=this.value.toUpperCase();" class="k-textbox" /></td>
    <td><input type="button" class="k-button" value="..." onClick="openParamsCierre('Equipo')" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="7">
    </td>
    </tr>
</table>
<table  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>
       <div id="resultsClientes">
			<table>
			   <tr>
                  <td>
			         <div id="dg_clientes" style="width:900px"/>
                  </td>
               </tr>
	        </table>
	   </div>
    </td>
  </tr>
</table>
<table width="376" border="0">
  <tr>
     <td width="102">&nbsp;</td>
     <td width="258">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2"><input type="button" class="k-button" value="Aceptar" onclick="BuscarClientes()" />
    <input type="button" class="k-button" value="Cancelar" onclick="cancelarSelecCierre()" />
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2"><div id="mensajes_main">Mensajes de la aplicacion</div></td>
    </tr>
</table>
</div>
<div id="windowParamsCierre"></div>
<div id="windowRegCliente"></div>
</form>
</body>
</html>