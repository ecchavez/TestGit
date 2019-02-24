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
String fromCatalogoCar= request.getParameter("from");

response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1);

String idTicket = "";
idTicket=request.getParameter("idTicket");
%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Consulta de Tickets</title>
    
    <script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
    <script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
    <script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js"></script>
    <script src="<%=basePath %>/js/jquery.blockUI.js"></script>
    <script src="<%=basePath %>/kendo/js/console.js"></script>   

    <script type="text/javascript">
            var contexPath="<%=basePath %>";
            var idTicket="<%=idTicket %>";
     </script> 
     <script src="<%=basePath %>/js/escritorio/EscritorioJQ.js"></script>  
     <script src="<%=basePath %>/js/clientes/ClientesJQ.js"></script>
	 <script src="<%=basePath %>/js/clientes/ClientesSAPJQ.js"></script>
	 <script src="<%=basePath %>/js/utils.js"></script>
     <script src="<%=basePath %>/js/tickets/RegistroTickets.js"></script>
         
    <script>
	var open_menu=0;
	var ticketsDataExport= [];
    
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
		
		var dataSourceConsultaCatalogoTickets = new kendo.data.DataSource({
		        data: [],	    	    	        
		        pageSize: 50,
		        messages: {
		            empty: "No hay registros que mostrar"
		        }
		    });
		    
		$("#GridConsultaCatalogoTickets").kendoGrid( {
					 dataSource : dataSourceConsultaCatalogoTickets,
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
						field : "idTicket",
						title : "#Ticket",
						width: 60
					}, {
						field : "idFase",
						title : "Fase",
						width: 30
					}, {
						field : "areatx",
						title : "Area",
						width: 70
					}, {
						field : "idEquipo",
						title : "Equipo",
						width: 90
					}, {
						field : "fechab",
						title : "Fecha Apertura",
						width: 70
					}, {
						field : "fechat",
						title : "Fecha y Hora de Atenci&oacute;n",
						width: 110
					}, {
						field : "estatus",
						title : "Estatus",
						width: 80
					}, {
						field : "fechat_val",
						title : "Cita",
						width: 30
					}, {
						field : "asunto",
						title : "Asunto",
						width: 170
					}]
	            });	
        
				if(idTicket != 'null'){
					executeFindBusquedaByIdTicketFilter(idTicket);
				}
        
    });
					
		    function valida_openclose()
			{
				if(open_menu==0)
				{
					open_menu=1;
					var cssObj = {      
					'left':'0px'    
					};    
					$("#my-scrollable-div").css(cssObj);
				}
				else
				{
					open_menu=0;
					var cssObj = {      
					'left':'-200px'    
					};    
					$("#my-scrollable-div").css(cssObj);
				}
					
			}

		function crearImpresionTicket() {	
			var grid = window.parent.$("#GridConsultaCatalogoTickets").data("kendoGrid");
		    var model = grid.dataItem(grid.select());
		    if (model == undefined ) {
		    	alert ("Requiere seleccionar un ticket");
		    	return;
		    } else {
		    	setEstatusBar("Generando reporte","mensaje");
		    	document.getElementById('visorReporte').src= contexPath + "/ticket/GeneraReporte.htm?idTicket=" + model.idTicket;
			}
		}
		
		
		function crearExcel() {	
		    	setEstatusBar("Generando reporte","mensaje");
				document.getElementById('visorReporte').src= contexPath + "/ticket/GeneraExcel.htm?idTicket=" + $("#idTicketInicial").val() + "&idFase=" + $("#faseTicket").val() + "&idEquipo=" + $("#equipoTicket").val() + "&estatus=" + $("#estatus").val() + "&fechaInicial=" + $("#fechaInicial").val() + "&fechaFinal=" + $("#fechaFinal").val();
		}
</script>
    <link href="<%=basePath %>/jixedbar/default/jx.stylesheet.css" type="text/css"  rel="stylesheet" />
	<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />
    
   <style>
    button:disabled {
       	border-radius: 4px;
   		border-width: 1px;
   		font: 10px "Century Gothic";
   		height:23px;
   		border:1px solid
   }
      
   </style>
</head>

<body topmargin="0">
<table width="1180" height="819" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
    <td width="1180" height="102" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="0"><input type="image" name="imageField5" id="imageField5" src="<%=basePath %>/images/images/pages/header_left.png" /></td>
        <td width="211" background="<%=basePath %>/images/images/pages/header_logo.png"><table border="0" cellspacing="0" cellpadding="0" width="115">
          <tr>
            <td colspan="2"></td>
            </tr>
          <tr>
            <td width="1" align="center">&nbsp;</td>
            <td width="116" align="center"><div class="fadein" id="slaimg"></div></td>
          </tr>
          <tr>
            <td height="5" colspan="2"></td>
          </tr>
        </table></td>
        <td width="100%" background="<%=basePath %>/images/images/pages/header_middle.png">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100%" height="31">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="141" height="25" id="TituloMain">Gestion de Vivienda: </td>
                <td width="424" id="TituloModulo">Consulta de Tickets</td>
                <td width="276" id="TituloModulo"><table width="200" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td width="100%" align="center"><%= user.getId_ut_sup_cm().length()>=21 ? user.getId_ut_sup_cm().substring(0,20):user.getId_ut_sup_cm() %></td>
                        <td width="0" align="center"><%= user.getId_ut_sup() %></td>
                      </tr>
                    </table></td>
                  </tr>
                </table></td>
                <td width="79" id="TituloMain">Bienvenido:  </td>
                <td width="17" id="TituloModulo"><%= user.getUsuario() %></td>
                <td width="64" align="center"><input type="image" name="imageField8" id="imageField8" src="<%=basePath %>/images/exitr32.png" alt="SALIR" onclick="salirSistema()"></td>
                </tr>
            </table>
        
        	</td>
            </tr>
          <tr>
            <td height="22">&nbsp;</td>
            </tr>
          <tr>
            <td height="32"><table width="95%" height="16" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="15%"><button class="k-button" style="width:115px" id="seleccionCarCli" onclick="dialogoFiltroReporteCatalogoTicket()">Seleccionar</button></td>
                <td width="15%"><a class="k-button" id="verDetalleTicket" style="width:115px; visibility: hidden;" onclick="verDetalleTicket()">Detalle</a></td>
                <td width="45%"><a class="k-button" id="crearExcel" style="width:115px; visibility: hidden;" onclick="crearExcel()">Excel</a></td>
                <td width="4%">&nbsp;</td>
                <td width="4%">&nbsp;</td>
                <td width="4%"></td>
                <td width="4%">&nbsp;</td>
                <td width="4%">&nbsp;</td>
                <td width="5%">&nbsp;</td>
                </tr>
            </table></td>
            </tr>
        </table></td>
        <td width="0"><input type="image" name="imageField6" id="imageField6" src="<%=basePath %>/images/images/pages/header_right.png" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="540" valign="top"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="0" height="16"><input type="image" name="imageField" id="imageField" src="<%=basePath %>/images/images/pages/main_corner4.png" /></td>
        <td width="1150" background="<%=basePath %>/images/images/pages/main_corner4_comp.png"></td>
        <td width="0"><input type="image" name="imageField4" id="imageField4" src="<%=basePath %>/images/images/pages/main_corner3.png" /></td>
      </tr>
      <tr>
        <td height="634" background="<%=basePath %>/images/images/pages/main_corner2_comp.png"></td>
        <td background="<%=basePath %>/images/images/pages/main_relleno.png" valign="top">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td height="612" valign="top">
                         <div id="area_trabajo">
                             <div id="GridConsultaCatalogoTickets" style="height: 600px; width:1135px;"></div>
                         </div>
                      </td>
                    </tr>
          </table></td>
        <td background="<%=basePath %>/images/images/pages/main_corner3_comp.png"></td>
      </tr>
      <tr>
        <td height="0"><input type="image" name="imageField2" id="imageField2" src="<%=basePath %>/images/images/pages/main_corner1.png" /></td>
        <td background="<%=basePath %>/images/images/pages/main_corner1_comp.png"></td>
        <td><input type="image" name="imageField3" id="imageField3" src="<%=basePath %>/images/images/pages/main_corner2.png" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="19">&nbsp;</td>
  </tr>
</table>
<div id="sample-bar" >
   <ul>
      <li title=""><div id="base_estatus" style="width:17px; height:20px; vertical-align:middle">>>></div></li>
   </ul>
   <span class="jx-separator-left"></span>
   <div id="mensajes_main">Mensajes de la aplicacion</div>
   <span class="jx-separator-right"></span>
</div>
<div id="my-scrollable-div" style="position:fixed; top:100px; left:-200px;">
<div id="my-scrollable-div_int" style="position:absolute; top:-5.7px; left:200px;">
<input name="" type="image" src="<%=basePath %>/images/images/pages/flecha_menu.png" onclick="valida_openclose()"/>
</div>
	<table border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>       
            <div id="div_menu" style="width:200px">
            	<ul id="menu">
                	  
                </ul>
            </div>     
        </td>
      </tr>
    </table>
</div>


<div id="windowConsultaCatalogoTicketFilter"></div>
<div id="windowConsultaCatalogoTicketDetalle"></div>
<div id="windowConsultaConstruccionTicketDetalle"></div>
<div id="windowAtencionConstruccionTicket"></div>
<div id="windowProcesoConstruccionTicket"></div>

<div id="windowCapturaTickets"></div>
<div id="windowConfirm"></div> 
<div id="windowUpdateCliente"></div> 
<div id="windowSelCarCli"></div> 

<iframe src="" frameborder="0" id="visorReporte" style="display:hidden" width="0" height="0" scrolling="auto" frameborder='0'>
</iframe>

<input type="hidden" id="idTicketInicial" name="idTicketInicial" value="<%=idTicket %>">
<input type="hidden" id="idFase" name="idFase">
<input type="hidden" id="idEquipo" name="idEquipo">
<input type="hidden" id="estatus" name="estatus">
<input type="hidden" id="fechaInicial" name="fechaInicial">
<input type="hidden" id="fechaFinal" name="fechaFinal">

</body>
</html>