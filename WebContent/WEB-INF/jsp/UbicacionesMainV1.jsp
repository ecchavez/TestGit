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
    
    var uts_global="";
	var tabStrip;
	var tabStrip1;
	
	$(document).ready(function(){
		$("#btn_open_cotizador").attr("disabled", "disabled");
		tabStrip1 = $("#tabubucaciones").kendoTabStrip({
		    height: 300,
			animation:	{
				open: {
					effects: "fadeIn"
				}
			},
			select: onSelectTU
		
		});
	  

		
		$("#dg_equipo_detalle").kendoGrid({ 			 
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
		 }); 
	});
	
	function cargaPisosGrid(unidadTS)
	{
		var iframe = $('#iframe_detalle');		
	    $(iframe).attr('src', '');
		uts_global=unidadTS;
		getPisos(unidadTS);
	}
			
	function cargaPisosDetalle(iduts, equipos_detalle, equipos_imagenes)
	{
		var detalle_filtrado=[];
		var is_img=false;
		for(var i = 0; i<equipos_detalle.length; i++)
		{
			if(equipos_detalle[i].id_equnr==iduts)
			{
				detalle_filtrado.push(equipos_detalle[i]);
				for(var j=0; j<equipos_imagenes.length; j++)
				{
					if(equipos_imagenes[j].tipo==equipos_detalle[i].value)
					{
						$("#render_imagen").attr('src',''+contexPath+equipos_imagenes[j].pathimg+'');
						is_img=true;
					}
				}
			}
		}
		
		if(!is_img)
		{
			$("#render_imagen").attr('src','');
		}
		
		$("#dg_equipo_detalle").empty();	
		
		var dataSourceUserPermisos = new kendo.data.DataSource({
	        data: detalle_filtrado
	    });

        $("#dg_equipo_detalle").kendoGrid({
		        dataSource: dataSourceUserPermisos,
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
			  height: 300,
			  selectable: "row"
		});
        
		if(simulador_accionado==1)
		{
			setImageToLeft('right');
			simulador_accionado=0;
			$("#btn_open_cotizador").removeAttr("disabled");
		}
		
		tabStrip=$("#tabubucaciones").data("kendoTabStrip");
	    tabStrip.select(2);
	}
	function setEstatusAccion(descripcion, estado)
	{
		window.parent.$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
	}
    
    
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
            <td width="100%" height="31"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="152" height="25" id="TituloMain">Gestion de Vivienda: </td>
                <td width="727" id="TituloModulo"> Ubicaciones Tecnicas</td>
                <td width="82" id="TituloMain">Bienvenido:  </td>
                <td width="75" id="TituloModulo"><div id="lbl_user_sesion"><%= user.getUsuario()%></div></td>
                <td width="73">&nbsp;</td>
                </tr>
            </table></td>
            </tr>
          <tr>
            <td height="22">&nbsp;</td>
            </tr>
          <tr>
            <td height="32"><table width="95%" height="16" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="65"><input type="button" id="btn_open_cotizador" value="Cotizar" onclick="setImageToLeft('left')" class="k-button"/></td>
                <td width="68">&nbsp;</td>
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
        <td height="634" background="<%=basePath %>/images/images/pages/main_corner2_comp.png"></td>
        <td background="<%=basePath %>/images/images/pages/main_relleno.png" valign="top">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td height="612" valign="top">
                      
                          <style scoped>
                #pisos-container {
                    margin: 0 5px;
                    overflow: auto;
                    padding: 10px;
                    width: 930px;
                    height: 140px;
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
                    width: 930px;
                    height: 460px;
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
                    height: 460px;
                    border: 1px solid #bbb;
                    -moz-box-shadow: 0 1px 2px rgba(0,0,0,0.45), inset 0 0 30px rgba(0,0,0,0.07);
                    -webkit-box-shadow: 0 1px 2px rgba(0,0,0,0.45), inset 0 0 30px rgba(0,0,0,0.07);
                    box-shadow: 0 1px 2px rgba(0,0,0,0.45), inset 0 0 30px rgba(0,0,0,0.07);
                    -webkit-border-radius: 8px;
                    -moz-border-radius: 8px;
                    border-radius: 8px;
                }
	</style>
	
      	 <table width="973" border="0" align="center" cellpadding="0" cellspacing="0">
              
              <tr>
    			<td height="341">
    
                    <div id="tabubucaciones">
                        <ul>
                            <li class="k-state-active">
                                Seleccione una fase
                            </li>
                            <li>
                                Seleccione un piso
                            </li>
                            <li>
                                Seleccione un departamento
                            </li>
                        </ul>
                        <div class="weather" id="tab_mapa" align="center" title="uno">
                             <table width="930" border="0" align="center" cellpadding="0" cellspacing="0">
	                              <tr>
	                                <td width="930" height="400">			                                
	                                	<div id="sppmainmapa" style="height:500px; ">
			                                   <div></div>                                    
			                             </div>			
										<script>
			                                   $(document).ready(function() {
			                                       $("#sppmainmapa").kendoSplitter({
			                                           panes: [
			                                               { contentUrl: '<%=basePath %>/UbicacionTS.htm' }
			                                           ]
			                                       });
			                                   });
			                            </script>
									</td>
	                              </tr>
			                  </table>                                                                    
                        </div>                                                
                        <div class="weather" id="tab_grid">    
				          <div id="pisos-container">                                        
	                      </div>				              

				          <div id="imagen-pisos-container"> 
			                  <table width="930" border="0" align="center" cellpadding="0" cellspacing="0">
		                              <tr>
		                                <td width="930" height="460">			                                
		                                	<iframe id="iframe_detalle" 
		                                	allowtransparency="true" 
											name="iframe_detalle"
											src=""
											scrolling="auto"
											noresize="noresize"
											frameborder="no"
											width="100%"
											height="100%">
											</iframe>
										</td>
		                              </tr>
		                        </table> 
				           </div>			   
                      </div>	
        			    <div class="weather" id="tab_detalle" style="height: 500px; position: relative;" title="tres">
							 <style scoped>
							    .derecha   { float: right; }
								.izquierda   { float: left; }
							 
								#dg_equipo_detalle { 
									position: absolute;
									top: 5px;
									right: 10px;			  													
								}
								#render_imagen {
									position:relative; 
									margin-top:80px;
								}
								
								/*#btn_open_cotizador {
									position:absolute; 
									margin-top:460px;
									left:10px;
								}*/
								
								#cotizador_container {
									position: absolute; 
									float: left;
									top: 4px;
									left: 2px;
									width: 630px;
									height: 480px;
								}
							</style>
							
        			    	<img src="" id="render_imagen" class="izquierda"/>
                            <div id="dg_equipo_detalle" style="width: 250px"></div>
                            <div class="izquierda" id="cotizador_container" style="display: none">
                                 <iframe id="iframe_simulador" 
		                                	allowtransparency="true" 
											name="iframe_simulador"
											src=""
											scrolling="auto"
											noresize="noresize"
											frameborder="no"
											width="100%"
											height="100%">
								 </iframe>									
                            </div>
        			    </div> 
        		   </div>	
      			</td>
              </tr>
              <tr>
                <td height="23"></td>
              </tr>
        </table>
                      
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
                </ul>
            </div>     
        </td>
      </tr>
    </table>
</div>

</body>
</html>
