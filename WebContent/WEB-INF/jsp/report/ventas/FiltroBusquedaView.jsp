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
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Ventas</title>
    
    <script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
    <script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
    <script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js"></script>
    <script src="<%=basePath %>/js/jquery.blockUI.js"></script>
    <script src="<%=basePath %>/kendo/js/console.js"></script>   

    <script type="text/javascript">
            var contexPath="<%=basePath %>";
     </script> 
     <script src="<%=basePath %>/js/escritorio/EscritorioJQ.js"></script>  
   	 <script src="<%=basePath %>/js/utils.js"></script>
     <script src="<%=basePath %>/js/reportes/ventas/Ventas.js"></script>
         
    <script>
	var open_menu=0;
	var ReportesDataExport = [];
    
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
		
		var dataSourceConsultaCatalogoReportes = new kendo.data.DataSource({
		        data: [],	    	    	        
		        pageSize: 50,
		        messages: {
		            empty: "No hay registros que mostrar"
		        }
		    });
		    
		$("#GridConsultaCatalogoReportes").kendoGrid( {
					 dataSource : dataSourceConsultaCatalogoReportes,
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
					 columns : [{
							field : "equnr",
							title : "Casa / Depto.",
							width: 120
					}, {
						field : "stunx",
						title : "Estatus",
						width: 80
					}, {
						field : "fechaStun",
						title : "Fecha apartado",
						width: 100
					}, {
						field : "m2pr",
						title : "Area m2",
						width: 70
					}, {
						field : "nest",
						title : "#Est. Base",
						width: 70
					},{
						field : "nbod",
						title : "#Bod. Base",
						width: 70
					},{
						field : "precioLista",
						title : "Precio Lista",
						width: 100
					},{
						field : "nestAdic",
						title : "#Est. Ad.",
						width: 60
					},{
						field : "preciEstAdic",
						title : "Precio est.<br>adicionales",
						width: 100
					},{
						field : "nbodAdic",
						title : "#Bod. Ad.",
						width: 60
					},{
						field : "priceBodAdic",
						title : "Precio bod.<br>adicionales",
						width: 100
					},{
						field : "importe",
						title : "Importe total",
						width: 100
					},{
						field : "descp",
						title : "Desc. %",
						width: 100
					},{
						field : "descm",
						title : "Descuento $",
						width: 100
					},{
						field : "priceVenta",
						title : "Precio venta",
						width: 100
					},{
						field : "priceM2",
						title : "Valor M2",
						width: 100
					},{
						field : "kunnrx",
						title : "Cliente",
						width: 200
					},{
						field : "asesor",
						title : "Asesor",
						width: 200
					},{
						field : "viaCon",
						title : "Medio",
						width: 100
					},{
						field : "ffco",
						title : "Fecha firma<br>contrato",
						width: 100
					}]
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

		function verReporte () {
			document.getElementById('visorReporte').src="<%= request.getContextPath()%>/report/ventas/GeneraReporteVentas.htm?idEstatusReporte=" + $('#estatusP').val() + "&idFaseReporte=" + $('#faseReporteP').val() + "&idEquipoInicialReporte=" + $('#equipoReporteP').val() + "&idEquipoFinalReporte=" + $('#equipoReportefinP').val() ;			
			setEstatusBar("Generando reporte","mensaje");		}

		function verReporteExcel () {
			document.getElementById('visorReporte').src="<%= request.getContextPath()%>/report/ventas/GeneraReporteVentasExcel.htm?idEstatusReporte=" + $('#estatusP').val() + "&idFaseReporte=" + $('#faseReporteP').val() + "&idEquipoInicialReporte=" + $('#equipoReporteP').val() + "&idEquipoFinalReporte=" + $('#equipoReportefinP').val() ;			
			setEstatusBar("Generando reporte","mensaje");		}
		
</script>
    <link href="<%=basePath %>/jixedbar/default/jx.stylesheet.css" type="text/css"  rel="stylesheet" />
	<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />
     

</head>

<body topmargin="0">
<table width="1180" height="819" border="0" align="center" cellpadding="0" cellspacing="0">
   <input  type="hidden" id="fromCatalogoCar" value="<%=fromCatalogoCar%>"/>
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
        <td width="100%" background="<%=basePath %>/images/images/pages/header_middle.png"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100%" height="31">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="141" height="25" id="TituloMain">Gestion de Vivienda: </td>
                <td width="424" id="TituloModulo">Reporte ventas</td>
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
                <td width="65"><a class="k-button" id="seleccionCarCli" onclick="dialogoFiltroConsultaEdoCtaReporte()">Seleccionar</a></td>
                <td width="65"><a class="k-button" id="verDetalleReporte" style="visibility: hidden;" onclick="verReporte()">Reporte</a></td>
                <td width="65"><a class="k-button" id="verDetalleReporteExcel" style="visibility: hidden;" onclick="verReporteExcel()">Descargar</a></td>
                <td width="121">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="127">&nbsp;</td>
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
                             <div id="GridConsultaCatalogoReportes" style="height: 600px; width:1135px"></div>
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
                	<li>
                        Menu Principal
                    </li>
                    <li>
                        Administracion
                        <ul>
                            <li>
                                Usuarios
                                <ul> <!-- moving the UL to the next line will cause an IE7 problem -->
                                    <li>Registro, Consulta y modificaciones</li>                               
                                </ul>
                            </li> 
                            <li>
                                Conciliacion Pagos                               
                            </li>
                        </ul>
                    </li>
                    <li>
                        Atiende a cliente
                        <ul>
                           <li>
                                Cartera de clientes
                                <ul> <!-- moving the UL to the next line will cause an IE7 problem -->
                                    <li>Registro Cartera</li>
                                    <li>Consulta y modificaciones Cartera</li>
                                    <li>Reporte de clientes asignados a vendedor</li>                               
                                </ul>
                            </li>
                            <li>
                                Control de ubicaciones 
                                <ul> <!-- moving the UL to the next line will cause an IE7 problem -->
                                    <li>Administracion ubicaciones</li>
                                    <li>Control ubicaciones</li>                                                            
                                </ul>                            
                            </li>
                            <li>
                                Simulador
                                <ul> <!-- moving the UL to the next line will cause an IE7 problem -->
                                    <li>Registro</li>
                                    <li>Consulta y modificaciones</li>                                                           
                                </ul>
                            </li>                        
                            <li>
                                Tickets
                                <ul> <!-- moving the UL to the next line will cause an IE7 problem -->
                                    <li>Registro Ticket</li>
                                    <li>Consulta y modificaciones tickets</li>                                                           
                                </ul>
                            </li>                        
                        </ul>
                    </li>
                    <li>
                        Cierre venta
                        <ul>
                            <li>
                                Clientes
                                <ul> <!-- moving the UL to the next line will cause an IE7 problem -->
                                    <li>Registro Clientes</li>
                                    <li>Consultas Clientes</li>                               
                                </ul>
                            </li> 
                        </ul>
                    </li>
                    <li>
                        Control pagos
                        <ul>
                            <li>Registrar pago inicial</li> 
                            <li>Registrar pagos parciales</li> 
                        </ul>
                    </li>  
                    <li>
                        Reportes
                        <ul>
                            <li>Estado de cuenta</li> 
                            <li>Disponibilidad</li> 
                            <li>Ventas</li> 
                        </ul>
                    </li>  
                </ul>
            </div>     
        </td>
      </tr>
    </table>
</div>

<input type="hidden" id="faseReporteP" name="faseReporteP" value="" />
<input type="hidden" id="equipoReporteP" name="equipoReporteP" value="" />
<input type="hidden" id="equipoReportefinP" name="equipoReportefinP" value="" />
<input type="hidden" id="estatusP" name="estatusP" value="" />

<div id="windowConsultaCatalogoReporteFilter"></div>
<div id="windowConsultaCatalogoReporteDetalle"></div>

<div id="windowCapturaReportes"></div>
<div id="windowConfirm"></div> 
<div id="windowUpdateCliente"></div> 
<div id="windowSelCarCli"></div> 
<iframe src="" frameborder="0" id="visorReporte" style="display:hidden" width="0" height="0" scrolling="auto" frameborder='0'>
	<form action="<%= request.getContextPath() %>/report/estadocuenta/GeneraReporte.htm" id="visorReporteForm" name="visorReporteForm" method="post">
	
	</form>
</iframe>


</body>
</html>