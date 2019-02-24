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
<title>Consulta de Pagos</title>
    
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
     <script src="<%=basePath %>/js/reportes/consultapagos/ConsultaPagos.js"></script>
         
    <script>

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

	function verReporte() {
		document.getElementById('visorReporte').src="<%= request.getContextPath()%>/report/consultapagos/GeneraReportePagosExcel.htm?idClienteReporte=" + $('#idClienteReporteP').val() + "&idFaseReporte=" + $('#idFaseReporteP').val() + "&idEquipoReporte=" + $('#idEquipoReporteP').val() + "&fechaIni=" + $('#fechaIniP').val()+ "&fechaFin=" + $('#fechaFinP').val()+ "&idMedioPago=" + $('#idMedioPagoP').val();			
		setEstatusBar("Generando reporte","mensaje");}

	function verReportePDF() {
		document.getElementById('visorReporte').src="<%= request.getContextPath()%>/report/consultapagos/GeneraReportePagosPDF.htm?idClienteReporte=" + $('#idClienteReporteP').val() + "&idFaseReporte=" + $('#idFaseReporteP').val() + "&idEquipoReporte=" + $('#idEquipoReporteP').val() + "&fechaIni=" + $('#fechaIniP').val()+ "&fechaFin=" + $('#fechaFinP').val()+ "&idMedioPago=" + $('#idMedioPagoP').val();			
		setEstatusBar("Generando reporte","mensaje");}
	
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

		var dataSourceHeaderGrid = new kendo.data.DataSource({
	        data: [],	    	    	        
	        pageSize: 0,
	        messages: {
	            empty: "No hay registros que mostrar"
	        }
	    });
	    
		$("#GridDataHeader").kendoGrid( {
			 dataSource : dataSourceHeaderGrid,
			 selectable:"row",
			 resizable: false,							
			 sortable: false,
			 reorderable: false,	
			columns : [ {
				title : "Oficina de Ventas",
				width: 444,
				},{
				title : "",
				width: 2,
				headerAttributes:{
				style: "background: #000000"
				}
			  	},{
				title : "Cuentas por Cobrar",
				width: 205
			}]
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
					 resizable: false,							
					 sortable: true,
					 reorderable: false,	
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
						field : "equipo",
						title : "Equipo",
						width: 30,
					}, /*{
						field : "",
						title : "Nombre Archivo",
						width: 120,
						template : "#=armaLink(fileName)#",
					}, */{
						field : "folio",
						title : "Folio",
						template : "#=armaLink(fileName,folio)#",
						width: 50,
					}, {
						field : "consecutivo",
						title : "Sec",
						attributes:{style:"text-align:right;"},
						width: 20
					}, {
						field : "referencia",
						title : "Referencia",
						width: 65
					}, {
						field : "fechaPago",
						title : "Fecha de Pago",
						attributes:{style:"text-align:center;"},
						width: 60
					},{
						field : "txtConcepto",
						title : "Concepto",
						width: 50
					},{
						field : "txtMedioPago",
						title : "Medio de Pago",
						width: 70
					},{
						field : "tipoRegistro",
						title : "Tipo Reg",
						template : "#=armaTexto(tipoRegistro)#",
						width: 50
					},{
						field : "montoPago",
						title : "Monto",
						format: "{0:C}",
						attributes:{style:"text-align:right;"},
						width: 50
					},{
						field : "montoPago",
						title : "",
						headerAttributes:{
						style: "background: #000000"
						},
						attributes:{style:" background-color: black;"},
						width: 2
					},{
						field : "fechaVal",
						title : "Fecha Validación",
						attributes:{style:"text-align:center;"},
						width: 60
					},{
						field : "pagado",
						title : "Estatus",
						template: "#=getImg(pagado)#",
						attributes:{style:"text-align:center;"},
						width: 30
					},{
						field : "observaciones",
						title : "Observaciones",
						width: 115	
					}]
	            });
		
    });
							
</script>
    <link href="<%=basePath %>/jixedbar/default/jx.stylesheet.css" type="text/css"  rel="stylesheet" />
	<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />
	
	<style type="text/css">
	#GridDataHeader .k-grid-header .k-header {
    text-align:center;
    font-weight: bold;
    </style>
    
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
                <td width="424" id="TituloModulo">Consulta de Pagos</td>
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
                <td width="85"><a class="k-button" id="seleccionCarCli" onclick="dialogoFiltroConsultaPagosReporte()" style="width:5">Seleccionar</a></td>
                <td width="62"><a class="k-button" id="verDetalleReporte" style="visibility: hidden; width: 50px" onclick="verReporte()">Excel</a></td>
                <td width="120"><a class="k-button" id="verDetalleReportePDF" style="visibility: hidden; width: 50px" onclick="verReportePDF()">PDF</a></td>
                <td width="60">&nbsp;</td>
                <td width="98">&nbsp;</td>
                <td width="214"><div align="right">Simbologia:&nbsp;&nbsp;</div></td>
                <td width="176"><img src="<%=basePath %>/images/check.png" width="15" height="15" align="baseline">Reconocido cuentas por cobrar</td>
                <td width="118"><img src="<%=basePath %>/images/delete.png" width="15" height="15" align="baseline">No Reconocido</td>
                <td width="36">&nbsp;</td>
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
                      <td width="100%">
                       <div id="area_trabajo">
                             <div id="GridDataHeader" style="height: 23px; width: 1135px"></div>
                         </div>
                      </td>
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
                        </ul>
                    </li>  
                </ul>
            </div>     
        </td>
      </tr>
    </table>
</div>


<input type="hidden" id="idClienteReporteP" name="idClienteReporteP" value="" />
<input type="hidden" id="idFaseReporteP" name="idFaseReporteP" value="" />
<input type="hidden" id="idEquipoReporteP" name="idEquipoReporteP" value="" />
<input type="hidden" id="fechaIniP" name="fechaIniP" value="" />
<input type="hidden" id="fechaFinP" name="fechaFinP" value="" />
<input type="hidden" id="idMedioPagoP" name="idMedioPagoP" value="" />

<div id="windowConsultaCatalogoReporteFilter"></div>
<div id="windowConsultaCatalogoReporteDetalle"></div>

<div id="windowCapturaReportes"></div>
<div id="windowConfirm"></div> 
<div id="windowUpdateCliente"></div> 
<div id="windowSelCarCli"></div> 
<iframe src="" frameborder="0" id="visorReporte" style="display:hidden" width="0" height="0" scrolling="auto" frameborder='0'>
	<form action="<%= request.getContextPath() %>/report/consultapagos/GeneraReportePagosExcel.htm" id="visorReporteForm" name="visorReporteForm" method="post">
	
	</form>
</iframe>


</body>
</html>