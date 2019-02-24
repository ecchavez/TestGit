<%@page import="mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//WC//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
UsuarioDto user = (UsuarioDto) request.getSession().getAttribute("usrSession");
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 

response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1);
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Ubicaciones tecnicas</title>
    
    <script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
    <script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
    <script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js"></script>   
    <script src="<%=basePath %>/js/jquery.imagemapster.js"></script>

    <script type="text/javascript">
            var contexPath="<%=basePath %>";
     </script> 
     <script src="<%=basePath %>/js/escritorio/EscritorioJQ.js"></script>  
     <script src="<%=basePath %>/js/ubicaciones/UbicacionesJQ.js"></script>
	<script src="<%=basePath %>/js/ubicaciones/UbicacionesEffectsJQ.js"></script>    
    <script>
	var open_menu=0;
    var estatus_int_sel="";
    var uts_global="";
	var tabStrip;
	var tabStrip1;
	var datosEqDet = [];
	
	$(document).ready(function(){
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

		cargaProcesoInicial();
        		
		$("#btn_open_cotizador").attr("disabled", "disabled");
	});
	
	function cargaPisosGrid(unidadTS)
	{
		/*var iframe = $('#iframe_detalle');		
	    $(iframe).attr('src', '');*/
		uts_global=unidadTS;
		getPisos(unidadTS);
	}
			
	function cargaPisosDetalle(iduts, equipos_detalle, equipos_imagenes, estatus_sel)
	{
		//$("#seccion").append(" / EQUIPO: "+CASA 01);
		
		estatus_int_sel=estatus_sel;
		cargaProcesoDetalleGrid();
		var detalle_filtrado=[];
		var is_img=false;
		var odet_eq="";
		
		for(var i = 0; i<equipos_detalle.length; i++)
		{
			//alert(equipos_detalle[i].charactx);
			if(equipos_detalle[i].id_equnr==iduts)
			{
				detalle_filtrado.push(equipos_detalle[i]);
				odet_eq+="<dt>"+equipos_detalle[i].charactx+"</dt><dd>"+equipos_detalle[i].value+"</dd>";
				for(var j=0; j<equipos_imagenes.length; j++)
				{
					if(equipos_imagenes[j].tipo==equipos_detalle[i].value)
					{
						$("#render_imagen").attr('src',''+contexPath+equipos_imagenes[j].pathimg+'?rand='+Math.random()+'');
						is_img=true;
					}
				}
			}
		}
		
		datosEqDet=detalle_filtrado;
		
		if(!is_img)
		{
			$("#render_imagen").attr('src','');
		}
		$("#btn_open_cotizador").removeAttr("disabled");
		///$("#dg_equipo_detalle").empty();	
		
		var dataSourceDetalleEq = new kendo.data.DataSource({
	        data: detalle_filtrado
	    });
	    
	    
	    $("#detalle_eq").html(odet_eq);	    
	    /*		
		var dg_detalle=$("#dg_equipo_detalle").data("kendoGrid");
		dg_detalle.dataSource.data([]);
		dg_detalle.dataSource.data(detalle_filtrado);*/
        /*$("#dg_equipo_detalle").kendoGrid({
		        dataSource: dataSourceUserPermisos,
			    height: 300,                   	  
		  	 columns: [
					{
						field: "charactx",
						title: "Desc"
					},
					{
						field: "value",
						title: "Valor"
					}								
				],
			selectable: "row",
			filterable: true
		});*/
        
		if(simulador_accionado==1)
		{
			setImageToLeft('right',datosEqDet[0].id_equnr);
			simulador_accionado=0;
			$("#btn_open_cotizador").removeAttr("disabled");
		}
		
	}
	
	function setEstatusAccion(descripcion, estado)
	{
		window.parent.$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
	}
					
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

		function navReturn()
		{
			cargaProcesoInicial();    		
			$("#btn_open_cotizador").attr("disabled", "disabled");
			simulador_accionado=0;
			estatus_int_sel="";
			$("#seccion").html("");
		}

		function validaEstatus()
		{
			var paso=false;

			if(estatus_int_sel=="00")
			{
				paso=true;
			}
			
			return paso;
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
                <td width="424" id="TituloModulo">Ubicaciones Tecnicas</td>
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
                <td width="65"><input type="button" id="btn_open_cotizador" value="Cotizar" onclick="if(validaEstatus()){ if(validaIngresoModulos('CTR_UBIC', 'PERMISO_C')){setImageToLeft('left',datosEqDet[0].id_equnr)}else{$('#mensajes_main').html('Sin permisos para este modulo')} } else {$('#mensajes_main').html('No se puede cotizar, el estatus no lo permite')}" class="k-button"/></td>
                <td width="68"><input type="button" id="btn_regresa" value="Regresar" onclick="navReturn()" class="k-button"/></td>
                <td width="65">&nbsp;</td>
                <td width="118">&nbsp;</td>
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
        <td height="700" background="<%=basePath %>/images/images/pages/main_corner2_comp.png"></td>
        <td background="<%=basePath %>/images/images/pages/main_relleno.png" valign="top">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td><div id="seccion"></div></td>
                    </tr>
                    <tr>
                      <td height="690" valign="top">
                      
              <style scoped>
              #load_content {
                    margin: 0 5px;
                    overflow: auto;
                    padding: 5px;
                    background-image: url("<%=basePath %>/images/images/pasto_render.png");
                    background-repeat: repeat;
                    border: 1px solid #bbb;
                    -moz-box-shadow: 0 1px 2px rgba(0,0,0,0.45), inset 0 0 30px rgba(0,0,0,0.07);
                    -webkit-box-shadow: 0 1px 2px rgba(0,0,0,0.45), inset 0 0 30px rgba(0,0,0,0.07);
                    box-shadow: 0 1px 2px rgba(0,0,0,0.45), inset 0 0 30px rgba(0,0,0,0.07);
                    -webkit-border-radius: 8px;
                    -moz-border-radius: 8px;
                    border-radius: 8px;
                }
                
                #pisos-container {  
                    position: relative;                  	
                    margin: 0 5px;
                    height: 600px;
                    overflow-y: scroll;
					overflow-x: hidden;
                    padding: 10px;
                    background-image: url("<%=basePath %>/images/deptos/nubes.png");
                    background-repeat: repeat; 
                    border: 1px solid #bbb;
                    -moz-box-shadow: 0 1px 2px rgba(0,0,0,0.45), inset 0 0 30px rgba(0,0,0,0.07);
                    -webkit-box-shadow: 0 1px 2px rgba(0,0,0,0.45), inset 0 0 30px rgba(0,0,0,0.07);
                    box-shadow: 0 1px 2px rgba(0,0,0,0.45), inset 0 0 30px rgba(0,0,0,0.07);
                    -webkit-border-radius: 8px;
                    -moz-border-radius: 8px;
                    border-radius: 8px;
                }
                
                #cotizador_container {                   
                    margin: 0 5px;
                    overflow: auto;
                    padding: 5px;
                    width: 930px;
                    height: 650px;
                    border: 1px solid #bbb;
                    -moz-box-shadow: 0 1px 2px rgba(0,0,0,0.45), inset 0 0 30px rgba(0,0,0,0.07);
                    -webkit-box-shadow: 0 1px 2px rgba(0,0,0,0.45), inset 0 0 30px rgba(0,0,0,0.07);
                    box-shadow: 0 1px 2px rgba(0,0,0,0.45), inset 0 0 30px rgba(0,0,0,0.07);
                    -webkit-border-radius: 8px;
                    -moz-border-radius: 8px;
                    border-radius: 8px;
                }
                
                #imagen-pisos-container {
                    margin: 0 5px;
                    overflow: auto;
                    padding: 10px;
                     background-image: url("<%=basePath %>/images/images/pasto_render.png");
                    background-repeat: repeat;
                    border: 1px solid #bbb;
                    -moz-box-shadow: 0 1px 2px rgba(0,0,0,0.45), inset 0 0 30px rgba(0,0,0,0.07);
                    -webkit-box-shadow: 0 1px 2px rgba(0,0,0,0.45), inset 0 0 30px rgba(0,0,0,0.07);
                    box-shadow: 0 1px 2px rgba(0,0,0,0.45), inset 0 0 30px rgba(0,0,0,0.07);
                    -webkit-border-radius: 8px;
                    -moz-border-radius: 8px;
                    border-radius: 8px;
                }
                
                
                
                #grid_pisos {                   
                    position: relative;
                    margin: 0 5px;
                    overflow: auto; 
		            width:891px; 
                    
		        } 
		        
		        #grid_pisos_in{
		        	position:absolute;				 
				    bottom: 0px;
				}  
				
                
                .specification {
			        max-width: 670px;
			        margin: 10px 0;
			        padding: 0;
			    }
			    .specification dt, dd {
			        width: 140px;
			        float: left;
			        margin: 0;
			        padding: 5px 0 7px 0;
			        border-top: 1px solid rgba(0,0,0,0.3);
			    }
			    .specification dt {
			        clear: left;
			        width: 120px;
			        margin-right: 7px;
			        padding-right: 0;
			        text-align: right;
			        opacity: 0.7;
			    }
			    .specification:after, .wrapper:after {
			        content: ".";
			        display: block;
			        clear: both;
			        height: 0;
			        visibility: hidden;
			    } 
			    
			    #cesped_div {
					position: absolute;
					left: 0px;
					width: 1130px;
					height: 45px;
					background-image: url("<%=basePath %>/images/images/cesped_grid.png");
					bottom: -25px;
				}
				
				#arbol_1 {
				position: absolute;
					left: 1050px;
					top: -180px;					
					width: 48px;
					height: 29px;					
				}
				
				#arbol_2 {
					position: absolute;
					left: -35px;
					top: -90px;
					width: 32px;
					height: 29px;			
				}              
	          </style>  
	          <div id="main_content" style="height: 100%; width: 98%;"></div>         
                  
                      
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
   <div id="mensajes_main"></div>
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
<input type="hidden" id="utsx" value ="<%= user.getId_ut_sup_cm() %>"/>
<div id="printCotizador"></div>
</body>
</html>
