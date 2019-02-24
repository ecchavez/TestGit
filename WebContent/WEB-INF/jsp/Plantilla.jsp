<%@page import="mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
UsuarioDto user = (UsuarioDto) request.getSession().getAttribute("usrSession");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Documento sin título</title>
    
    <script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
    <script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
    <script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js"></script>   
    <script src="<%=basePath %>/kendo/examples/content/shared/js/people.js"></script>
    
    
    <script>
	var open_menu=0;
                $(document).ready(function() {
					
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
					
                    $("#grid").kendoGrid({
                        dataSource: {
                            data: createRandomData(50),
                            pageSize: 50
                        },
                        sortable: true,
                        pageable: {
                            input: true,
                            numeric: false,
							refresh: true,
                            pageSizes: [10,20,30]
                        },
                        columns: [ {
                                field: "FirstName",
                                width: 300,
                                title: "First Name"
                            } , {
                                field: "LastName",
                                width: 300,
                                title: "Last Name"
                            } , {
                                width: 300,
                                field: "City"
                            } , {
								width: 300,
                                field: "Title"
                            } , {
								width: 300,
                                field: "BirthDate",
                                title: "Birth Date",
                                template: '#= kendo.toString(BirthDate,"dd MMMM yyyy") #'
                            } , {
                                width: 500,
                                field: "Age"
                            }
                        ]
                    });
					
			var m = $('#menu').kendoMenu({
            orientation: 'vertical'
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
<table width="1180" height="800" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
    <td width="1180" height="102" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="30"><input type="image" name="imageField5" id="imageField5" src="<%=basePath %>/images/images/pages/header_left.png" /></td>
        <td width="116" background="<%=basePath %>/images/images/pages/header_logo.png"><table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr>
            <td colspan="2"></td>
            </tr>
          <tr>
            <td width="7" align="center">&nbsp;</td>
            <td width="100" align="center"><div class="fadein" id="slaimg"></div></td>
          </tr>
          <tr>
            <td height="5" colspan="2"></td>
          </tr>
        </table></td>
        <td width="1101" background="<%=basePath %>/images/images/pages/header_middle.png"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="1089" height="31"><table width="1059" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="147" height="25" id="TituloMain">Gestion de Vivienda: </td>
                <td width="750" id="TituloModulo">Catalogo de Usuarios</td>
                <td width="87" id="TituloMain">Bienvenido:  </td>
                <td width="73" id="TituloModulo"><div id="lbl_user_sesion"><%= user.getUsuario()%></div></td>
                <td width="74">&nbsp;</td>
                </tr>
            </table></td>
            </tr>
          <tr>
            <td height="22">&nbsp;</td>
            </tr>
          <tr>
            <td height="32"><table width="1027" height="16" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="65"><a class="k-button" id="btn_new_user" onclick="openWinAddUser('nuevo')"><span class="k-icon k-i-plus"></span>Nuevo</a></td>
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
    <td height="569" valign="top"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="32" height="16"><input type="image" name="imageField" id="imageField" src="<%=basePath %>/images/images/pages/main_corner4.png" /></td>
        <td width="1180" background="<%=basePath %>/images/images/pages/main_corner4_comp.png"></td>
        <td width="0" ><input type="image" name="imageField4" id="imageField4" src="<%=basePath %>/images/images/pages/main_corner3.png" /></td>
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
                        <div id="dg_usuarios" style="height: 615px; width:1150px"></div>
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
    <td height="10">&nbsp;</td>
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
                        MENU
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
</body>
</html>