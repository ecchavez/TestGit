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
<title>Consulta y Modificacion de Cartera</title>
    
    <script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
    <script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
    <script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js"></script>
    <script src="<%=basePath %>/js/jquery.blockUI.js"></script>
    <script src="<%=basePath %>/kendo/js/console.js"></script>   

    <script type="text/javascript">
            var contexPath="<%=basePath %>";
     </script> 
     <script src="<%=basePath %>/js/escritorio/EscritorioJQ.js"></script>  
     
     
     <script src="<%=basePath %>/js/clientes/ClientesJQ.js"></script>
	 <script src="<%=basePath %>/js/clientes/ClientesSAPJQ.js"></script>
	 <script src="<%=basePath %>/js/buttons/buttons_state.js"></script>
	 <script src="<%=basePath %>/js/utils.js"></script>
     
         
    <script>
	var open_menu=0;
    var listaReasignaVendedor = new Array();
    var parametros = "";
    
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
		
		var dataSourceClienteCar = new kendo.data.DataSource({
		        data: [],	    	    	        
		        pageSize: 50,
		        messages: {
		            empty: "No hay registros que mostrar"
		        }
		    });
		    
		$("#dg_clientes").kendoGrid( {
					 dataSource : dataSourceClienteCar,
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
					},{
						field : "statusx",
						title : "Estatus",
						width: 100
					}, {
						field : "via_conx",
						title : "Via Contacto",
						width: 100
					}					
					]
	            });
		  
		var windowAsigna = $("#winAsignaVendedorDesarrollo");
	  		
	  		if (!windowAsigna.data("kendoWindow")) {
	  			windowAsigna.kendoWindow({
						        height: "210px",
						        title: "Asignar vendedor",
						        modal: true,
						        visible: false,
						        width: "400px"
					        });
	    	}
	  		
	  		
		$("#cmb_vendedorAsignaNew").kendoDropDownList({
	        dataTextField: "usuario",
	        dataValueField: "id_ut_sup",
	        //select : onSelectVendedorReasigna,
	        optionLabel: " "
	    });	 

	    //$("#cmb_vendedorAsignaNew").data("kendoDropDownList").enable(false);
	    $("#cmb_motivos").kendoDropDownList({
	        dataTextField: "idMotivoReax",
	        dataValueField: "idMotivoRea",
	        select : onSelectNewVendedor,
	        optionLabel: " "
	    });	 
	    
	    //getCargaInicialReasignaVendedor();
	    loadMotivosNewVendedor();
	  
  		
		
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
                <td width="424" id="TituloModulo">Consulta y Modificacion de Cartera</td>
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
                <td width="65"><a class="k-button" id="seleccionCarCli" style="width:100px"   onclick="openSelecCarCli()">Seleccionar</a></td>
                <td width="65"><a class="k-button" style="visibility: hidden;width:100px" id="visualizaCliente" onclick="showDetailsCart('','detCliente','SI')">Visualizar</a></td>                
                <td width="65"><a class="k-button" style="visibility: hidden;width:100px" id="actualizaCartera" onclick="if(validaIngresoModulos('CAT_CLI', 'PERMISO_M')){showDetailsCart('','openUpCliente','SI')}else{$('#mensajes_main').html('Sin permisos para este modulo')}">Modificar</a></td>				
                <td width="65"><a class="k-button" style="visibility: hidden;width:100px"id="eliminaCartera" onclick="if(validaIngresoModulos('CAT_CLI', 'PERMISO_B')){showDetailsCart('','delCliente')}else{$('#mensajes_main').html('Sin permisos para este modulo')}">Borrar</a></td>                
                <td width="65"><a class="k-button" style="visibility: hidden;width:100px" id="registraVisita" onclick="if(validaIngresoModulos('CAT_CLI', 'PERMISO_VIS')){showDetailsCart('','regVisita')}else{$('#mensajes_main').html('Sin permisos para este modulo')}">Reg.Visita</a></td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="127">&nbsp;</td>
                </tr>
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
                             <div id="dg_clientes" style="height: 600px; width:1135px"></div>
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
<div id="windowCapturaTickets"></div>
<div id="windowConfirm"></div> 
<div id="windowConfirmBorrado"></div> 
<div id="windowUpdateCliente"></div> 
<div id="windowDetalleCliente"></div> 
<div id="windowSelCarCli"></div> 
<div id="avisaRegistroVisita"></div> 

<div id="winAsignaVendedorDesarrollo">
  <table width="343" border="0">
    <tr>
      <td colspan="4" bgcolor="#CCCCCC"><strong>Para este desarrollo no se cuenta con vendedor</strong></td>
    </tr>
    <tr>
      <td colspan="4" bgcolor="#CCCCCC"><strong>asignado, favor de asignarlo.</strong></td>
    </tr>
    <tr>
      <td colspan="2" >&nbsp;</td>
      <td colspan="2" >&nbsp;</td>
    </tr>
    <tr>
      <td width="150">Vendedor:</td>
      <td width="177"><select name="cmb_vendedorAsignaNew" id="cmb_vendedorAsignaNew" onkeyup="this.value=this.value.toUpperCase();"></select>
      </td>
	  <td>
	  	Motivo:
	  </td>													  
	  <td>
		<select name="cmb_motivos" id="cmb_motivos" onkeyup="this.value=this.value.toUpperCase();"></select>
	  </td>													  
    </tr>
    <tr>
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="4">
      <input  type="button" class="k-button" id="aceptarNuevoVendedor" value="Aceptar" onclick="asignaVendedorNuevo('','02');"/>&nbsp;&nbsp;&nbsp;
      <a class="k-button" id="idReasignarAceptar" onclick="reasignarNewVendedor()">Reasignar</a>&nbsp;&nbsp;&nbsp;
      <input  type="button" class="k-button" id="cancelaVentanaAsignacion" value="Cancelar" onclick="cierraWinReasignaVendedor();"/>
      </td>
    </tr>
    <tr>
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="4"><div id="mensajes_mainReasigna">Mensajes de la aplicacion</div></td>
    </tr>
  </table>

</div>

</body>
</html>