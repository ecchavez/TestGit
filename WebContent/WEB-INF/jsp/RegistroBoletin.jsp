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
<title>Registro de Archivos</title>
    
    <script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
    <script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
    <script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js"></script>   

    <script type="text/javascript">
            var contexPath="<%=basePath %>";
     </script> 
   	 <script src="<%=basePath %>/js/boletin/BoletinJQ.js"></script>
   	 <script src="<%=basePath %>/js/ubicaciones/UbicacionesJQ.js"></script>
     <script src="<%=basePath %>/js/escritorio/EscritorioJQ.js"></script>     
    <script>
	var open_menu=0;
    
					
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

		$(document).ready(function(){
			$("#mensajes_main").html('${responseBoletin}');
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
			
			var sharedDataSource = new kendo.data.DataSource();
			$.ajax({  
			    type: "POST",
			    url: contexPath + "/ConsultaBoletines.htm",
			    data: "uts=uts",   
			    success: function(response){
			      if(response.length >= 0){
			    	  sharedDataSource = response;
			    	  $("#GridConsultaCatalogoReportes").empty();
						$("#GridConsultaCatalogoReportes").kendoGrid( {
							dataSource:{
					       		 data:sharedDataSource,
					       		 pageSize:10
					       	},
						 	selectable:"row",
						 	resizable: true,							
						 	sortable: true,
						 	reorderable: true,	
						 	width: 600,
						 	height: 400,
						 	pageable:true,
						 	/*pageable: {
			                	input: true,
			                	numeric: false,
			                	pageSizes: [50,100,150],
			                	messages: { 
									display: "{1} de {2} registros",
									page: "Página",
									of: "de",
									itemsPerPage: "registros por página"
							  	}
			           		},	*/
			            	scrollable:{
								virtual:true
							},
						 	columns : [{
											field : "nombreArchivo",
											title : "Nombre de Archivo",
											width: 120
									   }, 
									   { 
											command: [{ text: "Ver boletín", className:"verBoletinButton"}], 
			           			      		title: "Acción", width: "80px" 
			               			   }]
			       		});		

						$("#GridConsultaCatalogoReportes").delegate(".verBoletinButton", "click", function(e) {
				            e.preventDefault();
				            var vargrid = $("#GridConsultaCatalogoReportes").data("kendoGrid");
				            var renglon = vargrid.dataItem($(this).closest("tr"));
				            //alert(renglon.nombreArchivo);
				            window.open(contexPath + "/obtenerBoletin.htm?nombreArchivo="+renglon.nombreArchivo);
				        });	
			      }else{
	                  sharedDataSource = response.result;
		    		  alert("Ocurrió un problema de comunicación "+sharedDataSource);
		      	  }	      
		    	},  
		    	error: function(e){  
		      		alert('Error: ' + e);  
		    	}
			});		
		});


	</script>

    <link href="<%=basePath %>/jixedbar/default/jx.stylesheet.css" type="text/css"  rel="stylesheet" />
	<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />
     

</head>

<body topmargin="0">
<form name="formulario" method="post" enctype="multipart/form-data" action="<%=basePath %>/RespuestaBoletinFile.htm">
<table width="1180" height="819" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
    <td width="1180" height="102" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="0"><img src="<%=basePath %>/images/images/pages/header_left.png" /></td>
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
                <td width="424" id="TituloModulo"> <p>Publicacion de Boletines</p></td>
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
                <td width="90"><a class="k-button" id="btn_valida3" onclick="if(validaIngresoModulos('BOLETIN', 'PERMISO_C')){eliminaboletin()}else{$('#mensajes_main').html('Sin permisos para este modulo')}" style="width: 80px">Eliminar</a></td>
                <td width="99">&nbsp;</td>
                <td width="49">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="140">&nbsp;</td>
                </tr>
            </table></td>
            </tr>
        </table></td>
        <td width="0"><img src="<%=basePath %>/images/images/pages/header_right.png" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="540" valign="top"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="0" height="16"><img src="<%=basePath %>/images/images/pages/main_corner4.png" /></td>
        <td width="1150" background="<%=basePath %>/images/images/pages/main_corner4_comp.png"></td>
        <td width="0"><img src="<%=basePath %>/images/images/pages/main_corner3.png" /></td>
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
                      
                      	<table width="1117" height="200" border="0" align="left" cellpadding="0" cellspacing="0">
						    <tr>
						      <td width="1117" height="157"><table width="100%" border="0" cellpadding="0" cellspacing="0">
						        <tr>
						          <td width="289">&nbsp;</td>
						          <td width="155">&nbsp;</td>
						          <td width="369">&nbsp;</td>
						          <td width="262">&nbsp;</td>
						          <td width="37">&nbsp;</td>
						          <td width="23">&nbsp;</td>
					            </tr>
						        <tr>
						          <td></td>
						          <td colspan="2"><fieldset><legend>Subir boletines a Sistema</legend><input type="file" id="fileData" name="fileData" class="k-button" style="width: 250px"/>
						            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="k-button" id="btn_valida2" onClick="if(validaIngresoModulos('BOLETIN', 'PERMISO_C')){validaforma()}else{$('#mensajes_main').html('Sin permisos para este modulo')}" style="width: 80px"><span class="k-icon k-i-plus"></span>Guardar</a> 
						          </fieldset></td>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
						          <td></td>
					            </tr>
						        <tr>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
					            </tr>
						        <tr>
						          <td width="289">&nbsp;</td>
						          <td width="155" align="right">&nbsp;</td>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
					            </tr>
						        <tr>
						          <td height="19" colspan="6"><div id="area_trabajo">
                             <center><div id="GridConsultaCatalogoReportes" style="height: 400px; width:600px"></div></center>
                         </div></td>
					            </tr>
					          </table></td>
						    </tr>
						    <tr>
						      <td height="19">&nbsp;</td>
					      </tr>
						    <tr>
						      <td height="19"><table width="95" border="0" align="left" cellpadding="0" cellspacing="0">
						        <tr>
						          <td width="55">
						            <input type="button" name="guardar" id="guardar" value="Guardar" class="k-button" onClick="validaforma()" style="display: none;"></td>
						          <td width="69">
						            <input type="button" name="cancelar" id="cancelar" value="Cancelar" class="k-button" onClick="window.parent.closeWindowUser()" style="display: none;"></td>
						        </tr>
						      </table></td>
						    </tr>
						  </table>
                      	
						  <input type="hidden" id="accion" name="accion">

					  </td>
              </tr>
          </table></td>
        <td background="<%=basePath %>/images/images/pages/main_corner3_comp.png"></td>
      </tr>
      <tr>
        <td height="0"><img src="<%=basePath %>/images/images/pages/main_corner1.png" /></td>
        <td background="<%=basePath %>/images/images/pages/main_corner1_comp.png"></td>
        <td><img src="<%=basePath %>/images/images/pages/main_corner2.png" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="19">&nbsp;</td>
  </tr>
</table>
</form>
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
<img src="<%=basePath %>/images/images/pages/flecha_menu.png" onclick="valida_openclose()"/>
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

</body>
</html>