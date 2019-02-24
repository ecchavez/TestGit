<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;}
String idTicket=request.getParameter("idTicket");
String unidad=request.getParameter("unidad");
String usuario=request.getParameter("usuario");
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sistema Gestion de Vivienda (Area Restringida)</title>

<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/utils.js"></script>
<script src="<%=basePath %>/js/usuarios/UsuariosJQ.js"></script>

<script>

	var idTicket='<%=idTicket%>';
	var unidad='<%=unidad%>';
	var usuario='<%=usuario%>';

	$(document).ready(function(){

	    $("#cmb_ubicaciones").kendoDropDownList({
	        dataTextField: "text",
	        dataValueField: "value",	        
	        open: onOpen
	    });	
		 
		function onOpen() {
	       //setWidth($("#cmb_ubicaciones"));
	    } 		
	    	    
	});
	
	function setMensaje(mensaje)
	{
	    $("#div_msj").html(mensaje);
	}

</script>

<style id="antiClickjack">
body{
display:none !important;
}
</style>

<script type="text/javascript">
   if (self === top) {
       var antiClickjack = document.getElementById("antiClickjack");
       antiClickjack.parentNode.removeChild(antiClickjack);
   } else {
       top.location = self.location;
   }
</script>

<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" rel="stylesheet" type="text/css"/>
</head>
	<body>
		
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="91">&nbsp;</td>
  </tr>
  <tr>
    <td height="81" background="<%=basePath %>/images/images/login/login_barra.png"><table width="450" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="151" height="38"><img src="<%=basePath %>/images/images/login/login_gestion.png" width="145" height="30" /></td>
        <td width="10">&nbsp;</td>
        <td width="84" align="center"><img src="<%=basePath %>/images/images/login/login_logoggi.png" /></td>
        <td width="8">&nbsp;</td>
        <td width="197"><img src="<%=basePath %>/images/images/login/login_vivienda_label.png" width="163" height="30" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="40">&nbsp;</td>
  </tr>
  <tr>
    <td><table width="369" height="210" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="369" height="210" style="background-image: url(<%=basePath %>/images/images/login/login_base.png); background-repeat: no-repeat; font-family: 'Century Gothic';"><table width="248" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="23" colspan="2" align="center">LOGIN</td>
            </tr>
          <tr>
            <td colspan="2">&nbsp;</td>
            </tr>
          <tr>
            <td width="80">Unidad</td>
            <td width="190"><select name="cmb_ubicaciones" id="cmb_ubicaciones" style="width: 190px">
	          <c:forEach var="ubicaciones" items="${ubicacionObject.ubicacionesList}" varStatus="status">
	            <option value='<c:out value="${ubicaciones.ubicacionClave}"/>'>
	              <c:out value="${ubicaciones.ubicacionDescripcion}"/>
	              </option>
	          </c:forEach>         
	        </select></td>
          </tr>
          <tr>
            <td><br></td>
            <td><br></td>
          </tr>
          <tr>
            <td>Usuario</td>
            <td><input type="text" class="k-textbox" id="txt_user" name="txt_user" style="width:190px"></td><td><br></td>
          </tr>
          <tr>
            <td><br></td>
            <td><br></td>
          </tr>
          <tr>
            <td>Contrase&ntilde;a</td>
            <td><input type="password" class="k-textbox" id="txt_passwd" name="txt_passwd" style="width:190px"></td><td><br></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2" align="center"><input type="button" value="Aceptar" class="k-button" onclick="validaUsuario()" id="btn_aceptar" name="btn_aceptar"/></td>
            </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td align="center"><div id="div_msj" style="color:#666; font-size:10px; font-weight:bold;"><c:out value="${ubicacionObject.descripcion}"/></div></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>
		
	</td>
  </tr>
</table>
</body>
<script>
if(unidad != 'null'){
	$("#cmb_ubicaciones").val(unidad).change();
}

if(usuario != 'null'){
	$("#txt_user").val(usuario).change();
}
</script>
</html>