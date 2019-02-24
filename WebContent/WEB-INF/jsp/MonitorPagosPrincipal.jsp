<%@page import="mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
UsuarioDto user = (UsuarioDto) request.getSession().getAttribute("usrSession");
String flagContratoUts= (String) request.getSession().getAttribute("flagContratoSession");
flagContratoUts = flagContratoUts ==null ? "" : flagContratoUts;

String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
String from= request.getParameter("from");
String uriActualizaGrid1 = request.getParameter("uriActualizaGrid1");

//Variables usuadas cuando se trae datos desde el cierre de ventas pagos parciales
String cargaDatosDesdeCierre = request.getParameter("cargaDatosDesdeCierre");
String idPedidoDesdeCierre = request.getParameter("idPedidoDesdeCierre");
String idClienteParaMonitor = request.getParameter("idClienteParaMonitor");


String paramOrigenRegistro = request.getParameter("paramOrigenRegistro");
String paramTipoRegistroPago = request.getParameter("paramTipoRegistroPago");
String paramFregi = request.getParameter("paramFregi");


%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Monitor de Pagos</title>
    
    <script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
    <script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
    <script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js"></script>
    <script src="<%=basePath %>/js/jquery.blockUI.js"></script>
    <script src="<%=basePath %>/kendo/js/console.js"></script>   
    <script src="<%=basePath %>/kendo/js/cultures/kendo.culture.es-EC.min.js"></script>
    <script src="<%=basePath %>/js/utils.js"></script>
    
    <script type="text/javascript">
            var contexPath="<%=basePath %>";
     </script> 
     <script src="<%=basePath %>/js/escritorio/EscritorioJQ.js"></script>  
     
     
	 <script src="<%=basePath %>/js/cierreVenta/monitorPagosJQ.js"></script>
	 <script src="<%=basePath %>/js/cierreVenta/monitorCotPedJQ.js"></script>
	 <script src="<%=basePath %>/js/buttons/buttons_state.js"></script>
	 <script src="<%=basePath %>/js/contratos/ContratosJQ.js"></script>
     <script src="<%=basePath %>/js/clientes/ClientesSAPJQ.js"></script>
         
    <script>
    kendo.culture("es-EC");
    
	var open_menu=0;
	var consultaMonitorPago = '';
	
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
		
		
		var dataSourceFindMonitor = new kendo.data.DataSource( {
			data : [ ],
			pageSize : 50
			});

		
		$("#dg_PagosRegistradosMonitor").kendoGrid( {
			 dataSource :dataSourceFindMonitor,
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
            change: onChangePagoHdr,
			 columns : [ 
			{
				field : "vblen",
				title : "Pedido",
				 width:100
			 },
			 {
					field : "dpto",
					title : "Dpto",
					 width:100
			 },
			 {
				field : "fregi",
				title : "Folio de Registro",
				 width:100
			 }, 
			 {
					field : "kunnr",
					title : "Id Cliente",
					 width:100
			 }, {
					field : "kunnrtx",
					title : "Cliente",
					 width:100
			 },
			 {
				format: "{0:c}",
				field : "netwr_pag",
				title : "Monto",
				width:100
			},
			{
				field : "aedat",
				title : "Fecha Registro",
				width:100
			}, {
				field : "cptum",
				title : "Hora Registro",
				width:100
			}, {
				field : "ernam",
				title : "Usuario",
				width:180
			}]
			}).data("kendoGrid");
		
		var valorCargaDatoCierre= '<%=cargaDatosDesdeCierre %>';	
		var idPedidoDesdeCierre1= '<%=idPedidoDesdeCierre %>';	
		var idClienteParamM='<%= idClienteParaMonitor %>';
		
		var paramOrigenRegistrox = '<%=paramOrigenRegistro%>';
		var paramTipoRegistroPagox = '<%=paramTipoRegistroPago%>';
		var paramFregix = '<%=paramFregi%>';

		if (paramOrigenRegistrox==='REGISTRO_PAGO'){
			cargaDatosVienenDelCierreAlMonitor(idPedidoDesdeCierre1,idClienteParamM,paramOrigenRegistrox,paramTipoRegistroPagox,paramFregix);
			
		} else {
		    if (valorCargaDatoCierre==='datosCierre'){
			    cargaDatosVienenDelCierreAlMonitor(idPedidoDesdeCierre1,idClienteParamM,'','','');
		    }	
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
		var grid = window.parent.$("#dg_PagosRegistradosMonitor").data("kendoGrid");
	    var model = grid.dataItem(grid.select());
	    if (model == undefined ) {
	    	alert ("Requiere seleccionar un pedido");
	    	return;
	    } else {
	    	$("#mensajes_main").html("Generando reporte");
	    	$("#visorReporte").attr('src', "<%= request.getContextPath()%>/report/estadocuenta/GeneraReporte.htm?idClienteReporte=" + model.kunnr + "&idPedidoReporte=" + model.vblen);
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
                <td width="424" id="TituloModulo">Monitor de Pagos</td>
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
            <td height="32">
	            <table width="95%" height="16" border="0" align="center" cellpadding="0" cellspacing="0">
	              <tr>
	                  <td><a class="k-button" id="seleccionMonitorPagos" style="width:100px" onclick="openSelecCierreVenta('<%=from%>')">Selecci&oacute;n</a></td>
	                  <td><a class="k-button" id="idComprobantePagoFromMonitor" style="width:115px;visibility: hidden;" onclick="validaSeleccion(1000);">Imp Com Pago</a></td>
	                  <td><a  class="k-button" style="width:115px;visibility: hidden;" id="idImpTalonario" onclick="validaSeleccion(3,'MONITOR')">Imp. Talonario</a></td>
	                  <%if(flagContratoUts.equals("X")){ %> 
						  <td id="idCeldaContrato"><a style="width:115px;visibility: hidden;" class="k-button" id="impContrato" onclick="imprimirDocLegal();">Imp Contrato</a></td>
		                  <% System.out.println("PINTANDO EL BOTON"); %>
					  <%} else { %>
					      <td id="idCeldaContrato"></td>
					  <%} %>	
	                  <td><a  class="k-button" style="width:115px;visibility: hidden;" id="impCartaPromesa" onclick="validaSeleccion(2,'MONITOR');">Cart Oferta</a></td>
	                  <td><a  class="k-button" id="impPagare" style="width:115px;visibility: hidden;" onclick="validaSeleccion(4,'MONITOR');">Pagare</a></td>
	                  <td><a style="width:115px;visibility: hidden;" class="k-button" id="verCliente" onclick="verDatosCliente('MONITOR')">Ver Cliente</a></td>
	                  <td><a style="width:115px;visibility: hidden;" class="k-button" id="edoCuenta" onclick="verReporte();">Edo. Cuenta</a></td> 
	                  <td></td>              
	            </table>
            </td>
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
                             <div id="idControlMonitorPagos" style="height: 600px; width:1135px">
                             <form action="" id="cierreVenta" >
								<input type="hidden" value="<%=from%>" id="fromC"/>
								<input type="hidden" value="<%=uriActualizaGrid1%>" id="fromMonitorXmlClienteUri"/> 
								<input type="hidden"  id="indicaImpresionContratoPagare"/> 
								  <table width="870" border="0" align="center" cellpadding="0" cellspacing="0">
								    <tr>
								      <td><table width="899" border="0" align="center" cellpadding="0" cellspacing="0">
								      </table>
								       </td>
								    </tr>
								    <tr>
								      <td height="400">
									      <table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
									      <tr>
									         
									          <td width="65"><a class="k-button" id="idRegistroPagoFromMonitor" style="width:115px;visibility: hidden;" onclick="registraPagoBtn('fromMonitor2')">Registro Pago</a>
	                  						  <a class="k-button" id="idModificarPagoFromMonitor" style="width:115px;visibility: hidden;" onclick="openModificaDetallePagoFromMonitor('modificaPago');">Modificar</a>
	                  						  <a class="k-button" id="idDetallePagoFromMonitor" style="width:115px;visibility: hidden;" onclick="openDetallePagoFromMonitor('visualizaPago')">Detalle</a></td>
	                  					  </tr>
	                  
									        <tr>
									          <td><div id="dg_PagosRegistradosMonitor" style="height:550px; width:1135px"></div></td>
									        </tr>
									      </table>
								      </td>
								    </tr>
								    <tr>
								      <td>&nbsp;</td>
								    </tr>
								  </table>
								 <div id="windowSelecCierre"></div> 
							     <div id="windowDetallePagoMonitor"></div>
							     <div id="windowModificaDetallePagoMonitor"></div>  
								 <div id="windowImpresionMonitor"></div>
								 <div id="windowRegPago"></div>
								 <div id="windowRegCliente"></div>
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
<iframe src="" frameborder="0" id="visorReporteComprobanteMonitor" style="display:hidden" width="0" height="0" scrolling="auto" frameborder='0'>
	<form action="<%= request.getContextPath() %>/ImpresionContrato.htm" id="visorReporteComprobanteMonitorForm" name="visorReporteComprobanteMonitorForm" method="post">
	
	</form>
</iframe>
<iframe src="" frameborder="0" id="visorReporteDiversos" style="display:hidden" width="0" height="0" scrolling="auto" frameborder='0'>
	<form action="<%= request.getContextPath() %>/ImpresionContrato.htm" id="visorReporteDiversosForm" name="visorReporteDiversosForm" method="post">
	
	</form>
</iframe>

<iframe src="" frameborder="0" id="visorReporte" style="display:hidden" width="0" height="0" scrolling="auto" frameborder='0'>
	<form action="<%= request.getContextPath() %>/report/estadocuenta/GeneraReporte.htm" id="visorReporteForm" name="visorReporteForm" method="post">
	
	</form>
</iframe>
</body>
</html>