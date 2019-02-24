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
String from=request.getParameter("from");

//VARIABLES PARA PAGO INICIAL
String idCotizacionCierreVenta=request.getParameter("idCotizacionCierreVenta");
idCotizacionCierreVenta = idCotizacionCierreVenta==null ? "" : idCotizacionCierreVenta;

String idFregiReportePagoInicial=request.getParameter("idFregiReportePagoInicial");
idFregiReportePagoInicial = idFregiReportePagoInicial==null ? "" :idFregiReportePagoInicial;

String isRegistroTipoPago=request.getParameter("isRegistroTipoPago");
isRegistroTipoPago = isRegistroTipoPago==null ? "" : isRegistroTipoPago;


//Variables usuadas cuando se trae datos desde el monitor
String cargaDatosDesdeMonitor = request.getParameter("cargaDatosDesdeMonitor");
cargaDatosDesdeMonitor = cargaDatosDesdeMonitor == null ? "" : cargaDatosDesdeMonitor;
String idCarClienteMonitor = request.getParameter("idCarClienteMonitor");
idCarClienteMonitor = idCarClienteMonitor == null ? "" : idCarClienteMonitor;

%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
    <script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
    <script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js"></script>
    <script src="<%=basePath %>/js/jquery.blockUI.js"></script>  
    <script src="<%=basePath %>/js/utils.js"></script>
    
    <script type="text/javascript">
            var contexPath="<%=basePath %>";
     </script> 
     <script src="<%=basePath %>/js/escritorio/EscritorioJQ.js"></script>  
     
     
     <script src="<%=basePath %>/js/cierreVenta/monitorCotPedJQ.js"></script>
	<script src="<%=basePath %>/js/cierreVenta/monitorPagosJQ.js"></script>
	<script src="<%=basePath %>/js/clientes/ClientesJQ.js"></script>
	<script src="<%=basePath %>/js/clientes/ClientesSAPJQ.js"></script>
	<script src="<%=basePath %>/js/contratos/ContratosJQ.js"></script>
     
         
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
		
		var dataSourceFind = new kendo.data.DataSource( {
			data : [ ],
			pageSize : 50
			});

			$("#dg_FindCotsPeds").empty();

			$("#dg_FindCotsPeds").kendoGrid( {
			dataSource : dataSourceFind,
			selectable:	"row",
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
			change: onChangeCotizacion,
			columns : [	{
			field :	"idCotizacion",
			title :	"Cotizacion",
			width:100
			}, {
			field :	"inicioVig",
			title :	"Inicio Vigencia",
			width:100
			},
			{
			field :	"finVig",
			title :	"Fin Vigencia",
			width:100
			}, {
			field :	"idPedido",
			title :	"Id Pedido",
			width:100
			}, {
			field :	"dptoCasa",
			title :	"Dpto/Casa",
			width:150
			}, {
			field :	"idCliente",
			title :	"Id Cliente",
			width:100
			},{
			field :	"nombre",
			title :	"Nombre",
			width:100,
			}, {
			field :	"snombre",
			title :	"Segundo Nombre",
			width:100
			}, {
			field :	"aapat",
			title :	"Apellido",
			width:100
			}, {
			field :	"amat",
			title :	"Segundo Apellido",
			width:100
			}, {
			field :	"fchNac",
			title :	"F.Nacimiento",
			width:100
			},{
			field :	"spasox",
			title :	"Paso",
			width:150
			},
			{
			field :	"npasox",
			title :	"Estatus Paso",
			width:150
			}, {
			field :	"mje",
			title :	"Mensaje",
			width:600
			}]}).data("kendoGrid"); 
		
		
			var valorCargaDatoMoni= '<%=cargaDatosDesdeMonitor %>';	
			var idCarClienteMonitor1= '<%=idCarClienteMonitor %>';	
			if (valorCargaDatoMoni=='datosMonitor'){
				cargaDatosVienenDeMonitorAlCierre(idCarClienteMonitor1);
			}	
			
			
			
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
			var grid = window.parent.$("#dg_FindCotsPeds").data("kendoGrid");
		    var model = grid.dataItem(grid.select());
		    if (model == undefined ) {
		    	alert ("Requiere seleccionar un pedido");
		    	return;
		    } else {
		    	$("#mensajes_main").html("Generando reporte");
		    	$("#visorReporte").attr('src', "<%= request.getContextPath()%>/report/estadocuenta/GeneraReporte.htm?idClienteReporte=" + model.idCliente + "&idPedidoReporte=" + model.pedido);
			}
		}
		    
</script>
    <link href="<%=basePath %>/jixedbar/default/jx.stylesheet.css" type="text/css"  rel="stylesheet" />
	<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />
     

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
        <td width="100%" background="<%=basePath %>/images/images/pages/header_middle.png"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100%" height="31">
            
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="141" height="25" id="TituloMain">Gestion de Vivienda: </td>
                 <%if(from.equals("pagoIni")){ %>                              
				 <td width="424" id="TituloModulo">Control de pagos</td>
				 <%} %>
				 <%if(from.equals("cotizador")){ %>  
				 <td width="424" id="TituloModulo">Consulta y modificaciones (Cotizador)</td>
				 <%} %>
				 <%if(from.equals("pagoParc")){ %> 
				 <td width="424" id="TituloModulo">Control de pagos</td>
				 <%} %>   
				 <%if(from.equals("cancelacion")){ %>
				 <td width="424" id="TituloModulo">Cancelaciones y Traspasos (Cotizador)</td>
				 <%} %>
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
                                
                <td width="65"><a class="k-button" id="seleccionCierre" style="width:100px" onclick="openSelecCierreVenta('<%=from%>')">Selecci&oacute;n</a></td>
                <%if(from.equals("pagoIni")){ %>
                  <td width="65"><a style="visibility: hidden;width:100px" class="k-button" id="abrirCot" onclick="if(validaIngresoModulos('COTIZ', 'PERMISO_V')){openCotizadorFromPagoInicial('ext')}else{$('#mensajes_main').html('Sin permisos para este modulo')}">Abrir Cot.</a></td>
                  <td width="65"><a style="visibility: hidden;width:100px" class="k-button" id="regPago" onclick="registraPagoBtn('<%=from%>')">Reg. Pago</a></td>
                  <td width="65">
                    
                  </td>
                  
                  <td width="65"> 
                    
                   </td>
                   
                  <td width="65">
                     
                  </td>
                  
                  <td width="65">
                    
                  </td>
                  
                  <td width="65">
                     
                  </td>
                  <td width="65"></td>
                
                <%} %>
                <%if(from.equals("cotizador")){ %>
                
                  <td width="65"><a style="visibility: hidden;width:100px" class="k-button" id="abrirCot" onclick="if(validaIngresoModulos('COTIZ', 'PERMISO_V')){openCotizadorFromPagoInicial('ext')}else{$('#mensajes_main').html('Sin permisos para este modulo')}">Abrir Cot.</a></td>
                  <td width="65"></td>
                  <td width="65"></td>
                  <td width="65"></td>
                  <td width="65"></td>
                  <td width="65"></td>
                  <td width="65"></td>
                  <td width="65"></td>
                
                <%} %>
                <%if(from.equals("pagoParc")){ %>
                
                <td width="65"><a style="visibility: hidden;width:100px" class="k-button" id="monitPagos" onclick="buscaPedidoDeCierreVentaAMonitor()">Monitor Pago</a></td>
                <td width="65"><a style="visibility: hidden;width:100px" class="k-button" id="regPago" onclick="registraPagoBtn('<%=from%>')">Reg. Pago</a></td>
                <td width="65"><a style="visibility: hidden;width:100px" class="k-button" id="impContrato" onclick="validaSeleccion(1)">Imprim. Contrato</a></td>
                <td width="65"><a style="visibility: hidden;width:100px" class="k-button" id="impPagare" onclick="validaSeleccion(4)">Imprim. Pagare</a></td>
                <td width="65"><a style="visibility: hidden;width:100px" class="k-button" id="impTalonario" onclick="validaSeleccion(3)">Imprim. Talonario</a></td>
                <td width="65"><a style="visibility: hidden;width:100px" class="k-button" id="verCliente" onclick="verDatosCliente()">Ver Cliente</a></td>
                <td width="65"><a style="visibility: hidden;width:100px" class="k-button" id="edoCuenta" onclick="verReporte();">Edo. Cuenta</a></td>
                <td width="65"></td>
                <%} %>   
                <%if(from.equals("cancelacion")){ %>
                
                  <td width="65"><a style="width:100px" class="k-button" id="iDcancelacion" onclick="if(validaIngresoModulos('COTIZ', 'PERMISO_C')){openWinEquiposCancelacion('cancelar')}else{$('#mensajes_main').html('Sin permisos para este modulo')}">Cancelaci&oacute;n</a></td>
                  <td width="65"><a style="width:100px" class="k-button" id="iDcancelacion" onclick="if(validaIngresoModulos('COTIZ', 'PERMISO_T')){openWinEquiposCancelacion('traspaso')}else{$('#mensajes_main').html('Sin permisos para este modulo')}">Traspasos</a></td>
                  <td width="65"></td>
                  <td width="65"></td>
                  <td width="65"></td>
                  <td width="65"></td>
                  <td width="65"></td>
                  <td width="65"></td>
                
                <%} %>
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
                             <div id="idControlPagos" style="height: 600px; width:1135px">
                             <form action="" id="cierreVenta" >
								<input type="hidden" value="<%=from%>" id="fromC"/>
								<input type="hidden"  id="isRegistroTipoPagox"/>
								<input type="hidden"  id="idPedidoRegPago"/>
								<input type="hidden"  id="idClienteRegPago"/>
								
								<input type="hidden"  id="fromMonitorXmlClienteUri"/> 
								  <table width="870" border="0" align="center" cellpadding="0" cellspacing="0">
								    <tr>
								      <td><table width="899" border="0" align="center" cellpadding="0" cellspacing="0">
								      </table>
								       </td>
								    </tr>
								    <tr>
								      <td height="400"><table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
								        
								       							       
								        <tr>
								          <td><div id="dg_FindCotsPeds" style="height: 600px; width:1135px"></div></td>
								        </tr>
								       
								      </table></td>
								    </tr>
								    <tr>
								      <td>&nbsp;</td>
								    </tr>
								  </table>
								  <div id="windowSelecCierre"></div> 
								  <div id="windowRegPago"></div>
								  <div id="windowImpresion"></div>
								  <div id="windowMonitorPagos"></div>
								  <div id="windowUpdateCliente"></div>  
								  <div id="windowRegCliente"></div>
								  <div id="winCotizador"></div>
								  <div id="winSubEquiposCancelacion"></div>							
								
								</form>
                             
                             </div>
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
<iframe src="" frameborder="0" id="visorReporte" style="display:hidden" width="0" height="0" scrolling="auto" frameborder='0'>
	<form action="<%= request.getContextPath() %>/report/estadocuenta/GeneraReporte.htm" id="visorReporteForm" name="visorReporteForm" method="post">
	
	</form>
</iframe>

<iframe src="" frameborder="0" id="visorReporteDiversos" style="display:hidden" width="0" height="0" scrolling="auto" frameborder='0'>
	<form action="<%= request.getContextPath() %>/ImpresionContrato.htm" id="visorReporteDiversosForm" name="visorReporteDiversosForm" method="post">
	
	</form>
</iframe>

</body>
</html>