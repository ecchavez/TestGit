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
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Monitor de Conciliación de Pagos </title>
    
    <script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
    <script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
    <script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js"></script>
    <script src="<%=basePath %>/js/jquery.blockUI.js"></script>
    <script src="<%=basePath %>/kendo/js/console.js"></script>   
    <script src="<%=basePath %>/js/utils.js"></script>

    <script type="text/javascript">
            var contexPath="<%=basePath %>";
     </script> 
     <script src="<%=basePath %>/js/escritorio/EscritorioJQ.js"></script>  
          
     <script src="<%=basePath %>/js/cierreVenta/conciliacionPagosJQ.js"></script>
     
         
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
         
		 
	$("#dg_conciliacionPagos").kendoGrid( {
		 selectable:"row",
		 resizable: true,							
		 sortable: true,
		 reorderable: true,	
		 height: 550,	
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
         change:changePagDet,
         columns : [ 
         {
			field : "fregi",
			title : "Folio de Registro"
			//width:100
		},
		{
			field : "conse",
			title : "Consecutivo"
			 //width:100
		},
		{
			field : "refer",
			title : "Referencia"
			 //width:100
		},
		{
			field : "fpago",
			title : "Fecha de Pago"
			//width:100
		}, {
			field : "hpago",
			title : "Hora de Pago"
			//width:100
		}, {
			field : "monto",
			format: "{0:c}",
			title : "Monto"
			//width:100
				
		},{
			field : "fvalid",
			title : "Folio Extracto"
			 //width:100
		}]
		}).data("kendoGrid");

	$("#dg_extractoBancario").kendoGrid( {
		 selectable:"row",
		 resizable: true,							
		 sortable: true,
		 reorderable: true,	
		 height: 550,
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
	     change: changeExtBank,
		 columns : [ 
	     {
			field : "fextracto",
			title : "Folio Extracto"
			// width:100
		 }, {
			field : "fpago",
			title : "Fecha"
			//width:100
		},
		{
			field : "hpago",
			title : "Hora"
			//width:100
		}, {
			field : "descr",
			title : "Descripcion"
			///width:100
		}, {
			field : "abono",
			format: "{0:c}",
			title : "Abono"
			//width:150
				
		}, {
			field : "refer",
			title : "Referencia"
			//width:100
		}, {
			field : "fregi",
			title : "Folio registro"
			//width:100
		}]
		}).data("kendoGrid");
		 
		 
		cargaDatos('noLiga');
		
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
                <td width="424" id="TituloModulo">Monitor de Conciliación de Pagos</td>
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
                <td width="65"><a class="k-button" style="width:115px;" id="ligar" onclick="if(validaIngresoModulos('CON_PAGO', 'PERMISO_L')){ligar()}else{$('#mensajes_main').html('Sin permisos para este modulo')}">Ligar</a></td>
                <td width="68"><a class="k-button" style="width:115px;" id="borrarLiga" onclick="if(validaIngresoModulos('CON_PAGO', 'PERMISO_B')){desligar()}else{$('#mensajes_main').html('Sin permisos para este modulo')}">Borrar Liga</a></td>
                <td width="65"><a class="k-button" style="width:115px;" id="mostrarTodos" onclick="cargaDatos()">Ver todos</a></td>
                <td width="118"><a class="k-button" style="width:115px;" id="guardar" onclick="guardaLigados()">Guardar</a></td>
                <td width="118"><a class="k-button" style="width:115px;" id="logErrores" onclick="showLog()">Log</a></td>
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
                            <!-- div id="dg_clientes" style="height: 600px; width:1135px"></div-->
                              <table border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
                                   <td>
                                   Detalle de Pagos 
                                   </td>
                                   	<td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                   <td>
                                   Extracto Bancario
                                   </td>
                                </tr>
                                <tr>
                                   <td>
                                   <div id="cnv_conciliacionPagos">
        								<div id="dg_conciliacionPagos" style="width: 550px">
      									</div> 
  								   </div>
                                   </td>
                                   <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                   <td>
                                     <div id="cnv_extractoBancario">
							            <div id="dg_extractoBancario" style="width: 550px">
							            </div>
							         </div>
                                   </td>
                                </tr>
                              </table>
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
<div id="windowRegCliente"></div> 
<div id="windowLogErrores"></div> 

</body>
</html>