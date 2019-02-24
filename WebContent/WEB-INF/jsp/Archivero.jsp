<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
UsuarioDto usr = (UsuarioDto) request.getSession().getAttribute("usrSession");
%>
<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>
<script src="<%=basePath %>/js/contratos/ContratosJQ.js"></script>
<script src="<%=basePath %>/js/buttons/buttons_state.js"></script>
<script src="<%=basePath %>/js/utils.js"></script>
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo/styles/examples.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>/kendo/styles/main.css" rel="stylesheet" type="text/css"/>


<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Catalogo de Clientes</title>
<script>
              $(document).ready(function () {
                 $("#menu").kendoMenu();
              
                           });
</script>
</head>

<body onload="preloadImages()">
	
<table width="100%" style="height:100%; " cellpadding="10" cellspacing="0" border="0" >
  <tbody>
    <tr>
      <!-- ============ HEADER SECTION ============== -->
      <td colspan="1" style="height:5px; " bgcolor="#FFFFFF" >
        <table  border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td><h1><img alt="" src="images/logo_principal.png" ></h1></td>
            <td><ul id="menu">
                <li>
                    Administracion
                    <ul>
                        <li>
                            Usuarios
                            <ul> <!-- moving the UL to the next line will cause an IE7 problem -->
                                <li>Registro</li>
                                <li>Consulta y modificaciones</li>                               
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
                                <li>Registro</li>
                                <li>Consulta y modificaciones</li>
                                <li>Reporte de clientes asignados a vendedor</li>                               
                            </ul>
                        </li>
                        <li>
                            Control de ubicaciones                            
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
                                <li>Registro</li>
                                <li>Consultas</li>                               
                            </ul>
                        </li> 
                    </ul>
                </li>
                <li>
                    Control pagos
                    <ul>
                        <li>
                            Registrar pago inicial                            
                        </li> 
                        <li>
                            Registrar pagos parciales                            
                        </li> 
                    </ul>
                </li>
                <li>
                    Escrituracion
                </li>
                <li>
                    Entrega de producto
                </li>
                <li>
                    Garantias
                </li>
            </ul>
			</td>
          </tr>
      </table></td>
    </tr>
    <tr>
      <!-- ============ RIGHT COLUMN (CONTENT) ============== -->
      <td valign="top" background="<%=basePath %>/images/bg.jpg" height="550">
      <table width="870" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><table width="899" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="15" height="19">
               <a href="#"
				onMouseOver="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/up_arrow_24x24.png'); return true;"
			    onMouseOut="changeImages('button_Layer_1', '<%=basePath %>/images/icons/up_arrow_24x24.png'); return true;"
			    onMouseDown="changeImages('button_Layer_1', '<%=basePath %>/images/icons_press/up_arrow_24x24.png'); return true;"
				onMouseUp="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/up_arrow_24x24.png'); return true;"
				onClick="openAdminDocs('<%=usr.getId_ut_sup()%>','up')" >
				<img name="button_Layer_1" src="<%=basePath %>/images/icons/up_arrow_24x24.png" border="0" alt="Administrador Documentos"></a>
            </td>
              <td width="15" height="19">
              <a href="#"
				onMouseOver="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/down_arrow_24x24.png'); return true;"
			    onMouseOut="changeImages('button_Layer_1', '<%=basePath %>/images/icons/down_arrow_24x24.png'); return true;"
			    onMouseDown="changeImages('button_Layer_1', '<%=basePath %>/images/icons_press/down_arrow_24x24.png'); return true;"
				onMouseUp="changeImages('button_Layer_1', '<%=basePath %>/images/icons_over/down_arrow_24x24.png'); return true;"
				onClick="openAdminDocs('<%=usr.getId_ut_sup()%>','down')" >
				<img name="button_Layer_1" src="<%=basePath %>/images/icons/down_arrow_24x24.png" border="0" alt="Administrador Documentos"></a>
			</td>
            </tr>
          </table></td>
          </tr>
         
        <tr>
          <td height="400">
          <table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td height="23">&nbsp;</td>
                </tr>
            
                <tr>
                <td height="19">&nbsp;</td>
                </tr>
                 <tr>
                   <td>&nbsp;</td>
                 </tr>
             </table>           
            </td>
          </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        </table>
      </td>
    </tr>
    
    <tr>
      <td colspan="1" align="center" height="20" style="background-image:url(images/gradient.png); color: #FFF;" >Derechos reservados Grupo Gigante ©</td>
    </tr>
  </tbody>
</table>


<div id="windowAdminDocs"></div> 



<div id="buttomBar">
  	<div class="console"/>
</div>
</body>

</html>