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
<script src="<%=basePath %>/js/cierreVenta/monitorCotPedJQ.js"></script>
<script src="<%=basePath %>/js/cierreVenta/matchCodeJQ.js"></script>
<script src="<%=basePath %>/js/utils.js"></script>
<script src="<%=basePath %>/js/buttons/buttons_state.js"></script>
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo/styles/examples.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>/kendo/styles/main.css" rel="stylesheet" type="text/css"/>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Catalogo de Clientes</title>
<script>
              $(document).ready(function () {
               $("#menu").kendoMenu();
               $("#gridCliFind").data("kendoGrid");
               $("#dg_clientesIds").data("kendoGrid");
                $("#dg_clientesFind").kendoGrid({
					 height:250
				 });
				  
				  if(window.parent.dataGridCliSource!=null)
              	 {
              	 $("#dg_clientesIds").empty();
              	 $("#dg_clientesIds").kendoGrid( {
					 dataSource : window.parent.dataGridCliSource,
					 height:250,
					 selectable:"row",
					 sortable: true,
	                 resizable: true,
	                 pageable: true,
					 change: onChangeClienteId,
	                 columns : [ 
	                 {
						field : "id",
						title : "Id Cliente",
						width: 100
					 },
					{
						field : "nom",
						title : "Nombre",
						width: 100
					}, {
						field : "apPat",
						title : "ApPaterno",
						width: 100
					}, {
						field : "apMat",
						title : "ApMaterno",
						width: 100
					}]
					}).data("kendoGrid");
				 
              	 }else{
	              	$("#dg_clientesIds").kendoGrid({
					   resizable: true,
				        pageable: true,
				        height:250,
				        change: onChangeClienteId,
				        columns: [
							{ 
							field: "id", title:"Id's Cliente" , width:50
						},
						{ 
							field: "nom", title:"Nombre" , width: 50
						},
						{ 
							field: "apPat", title:"ApPaterno" , width: 50
						},
						{ 
							field: "apMat", title:"ApMaterno" , width: 50
						}],
			          }); 
	              	}
				
				 cargaBusqueda();
              	 
              	
              });
</script>
</head>


<body onload="preloadImages()">
	
<table width="100%" style="height:100%; " cellpadding="10" cellspacing="0" border="0" >
  <tbody>
    <tr>
      <!-- ============ HEADER SECTION ============== -->
     
    </tr>
    <tr>
      <!-- ============ RIGHT COLUMN (CONTENT) ============== -->
      <td valign="top"  height="550">
      <table width="870" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><table width="899" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="929" height="19">
              <a href="#"
				onMouseOver="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/lens_24x24.png'); return true;"
			    onMouseOut="changeImages('button_Layer_1', '<%=basePath %>/images/icons/lens_24x24.png'); return true;"
			    onMouseDown="changeImages('button_Layer_1', '<%=basePath %>/images/icons_press/lens_24x24.png'); return true;"
				onMouseUp="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/lens_24x24.png'); return true;"
				onClick="buscarClientes()">
				<img name="button_Layer_1" src="<%=basePath %>/images/icons/lens_24x24.png" border="0" alt="Buscar Cartera de Clientes"></a>
              <!-- input name="button_user" id="button_user" type="button" onClick="openWinAddUser('nuevo')" --></td>
              
              <td width="63" height="19"><!-- img alt="Eliminar" src="" onClick="adminUsuarios('upUser')" --></td>
            </tr>
          </table></td>
          </tr>
         
        <tr>
          <td height="400">
          <table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td height="23">&nbsp;</td>
                </tr>
              <tr>
                <td colspan="4"><div id="gridCliFind">
                </div></td>
                </tr>
                <tr>
                <td height="19">&nbsp;</td>
                </tr>
                 <tr>
               <td width="303"><div id="cnv_clientesIds">
        <div id="dg_clientesIds" style="width: 450px">
      </div> 
  </div></td>
          <td width="75"><table border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="41"><img alt="Agregar" src="<%=basePath %>/images/icons/round_left_arrow_24x24.png" onclick="agregaId()"></td>
              </tr>
            <tr>
              <td>&nbsp;</td>
              </tr>
            <tr>
              <td><img alt="Quitar" src="<%=basePath %>/images/icons/round_right_arrow_24x24.png" onClick="quitarId()"></td>
              </tr>
          </table></td>
          <td width="521"><div id="cnv_clientesFind" style="width: 500px">
          <div id="dg_clientesFind">
            </div>
        </div></td>
                </tr>
                 <tr>
                   <td>
					<input type="button" value="Aceptar" id="aceptaCliente" 	onclick="aceptarCriterios('cli')">
					<input type="button" value="Cancelar" id="cancelaCliente" onclick="cancelarCriterios('cli')">
				   </td>
                 </tr>
             </table>           
            </td>
          </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        </table>
      </td>
    </tr>
   
  </tbody>
</table>


<div id="windowRegCliente"></div> 



<div id="buttomBar">
  	<div class="console"/>
</div>
</body>

</html>