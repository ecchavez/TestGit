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
<title>Catalogo de Usuarios</title>
    
    <script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
    <script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
    <script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js"></script>   
    
    <script type="text/javascript">
            var contexPath="<%=basePath %>";
     </script> 
     <script src="<%=basePath %>/js/escritorio/EscritorioJQ.js"></script>  
     <script src="<%=basePath %>/js/usuarios/UsuariosJQ.js"></script>
    
    <script>
	var open_menu=0;
    
    $(document).ready(function() {
        
		getUsuarios(); 
		var window = $("#winOptionDelete");
		 
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
					
        var dataSourceUsuarios = new kendo.data.DataSource({
		    data: [],	    	        
		    pageSize: 50
		}); 
		
		$("#dg_usuarios").kendoGrid({
				dataSource: dataSourceUsuarios,
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
				  columns: [
							{
								field: "nombre1",
                                title: "Nombre",
                                width: 150
                            },
                            {
                                field: "nombre2",
                                title: "Segundo Nombre",
                                width: 150
                            },
                            {
                                field: "app_pat",
                                title: "Primer Apellido",
                                width: 150			                                
                            },
                            {
                                field: "app_mat",
                                title: "Segundo Apellido",
                                width: 150			 
                            },
                            {
                            	field: "pass",
                                title: "Contraseña",
                                hidden: true,
                                width: 100			                                 
                            },
                            {
                            	field: "usuario",
                                title: "Usuario",
                                width: 120			 
							},
                            {
                            	field: "correo",
                                title: "Correo",
                                width: 120			 
							},
                            {
                            	field: "telefono",
                                title: "Telefono",
                                width: 120			 
							},
                            {
                            	field: "extension",
                                title: "Ext",
                                width: 50			 
							}
						],
                  selectable: "row",
				  change: onChangeUsers								  
			  }); 
			
			if (!window.data("kendoWindow")) {
				        window.kendoWindow({
				        height: "120px",
				        title: "Pregunta",
				        modal: true,
				        visible: false,
				        width: "350px"
			        });
   				}
						   	
		});
	
		function setResponseAddUser()
		{
			$("#mensajes_main").html("Usuario guardado satisfactoriamente");
		}
		
		function closeUserWindow()
		{
			$("#windowAddUser").data("kendoWindow").close();
			getUsuarios();
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
                <td width="424" id="TituloModulo">Catalogo de Usuarios</td>
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
                <td width="80"><a class="k-button" id="btn_new_user" onclick="if(validaIngresoModulos('CAT_USU', 'PERMISO_C')){openWinAddUser('nuevo')}else{$('#mensajes_main').html('Sin permisos para este modulo')}" style="width: 80px"><span class="k-icon k-i-plus"></span>Nuevo</a></td>
                <td width="80"><a class="k-button" id="btn_new_upd" onclick="if(validaIngresoModulos('CAT_USU', 'PERMISO_M')){openWinAddUser('actualiza')}else{$('#mensajes_main').html('Sin permisos para este modulo')}" style="width: 80px;"><span class="k-icon k-i-pencil"></span>Modificar</a></td>
                <td width="80"><a class="k-button" id="btn_new_del" onclick="if(validaIngresoModulos('CAT_USU', 'PERMISO_B')){validaEliminaUser('delUser')}else{$('#mensajes_main').html('Sin permisos para este modulo')}" style="width: 80px"><span class="k-icon k-i-cancel"></span>Borrar</a></td>
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
                      <td height="612" valign="top"><div id="clientsDb">
                        <div id="dg_usuarios" style="height: 600px; width:1135px"></div>
                      </div></td>
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

<div id="winOptionDelete">
<table width="314" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="2">El usuario se eliminara, esta usted seguro?</td>
   </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
   </tr>
  <tr>
    <td width="153">&nbsp;</td>
    <td width="161">&nbsp;</td>
  </tr>
  <tr>
    <td align="center"><input class="k-button" type="button" name="button" id="button" value="Aceptar" onClick="setEliminaUsuario()"></td>
    <td align="center"><input class="k-button" type="button" name="button2" id="button2" value="Cancelar" onClick="closeConfirmaEliminaUser()"></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
</div>
<div id="windowAddUser"></div>
<input type="text" id=protocolo" value="window.location.protocol">
</body>
</html>