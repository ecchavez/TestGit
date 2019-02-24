<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Users using ajax</title>

<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/usuarios/UsuariosJQ.js"></script>

<script>
	
	$(document).ready(function(){

	    $("#cmb_ubicaciones").kendoDropDownList({
	        dataTextField: "text",
	        dataValueField: "value",	        
	        open: onOpen
	    });	
		
		function onOpen() {
	       setWidth($("#cmb_ubicaciones"));
	    }
	    
	});
	
	

</script>

<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo/styles/examples.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>/kendo/styles/main.css" rel="stylesheet" type="text/css"/>
</head>
	<body>
		
<table width="1279" border="0" align="center" cellpadding="0" cellspacing="0">
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
            <td width="76">&nbsp;Unidad</td>
            <td width="172"><select name="cmb_ubicaciones" id="cmb_ubicaciones">
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
            <td>&nbsp;Usuario</td>
            <td><input type="text" class="k-textbox"></td><td><br></td>
          </tr>
          <tr>
            <td><br></td>
            <td><br></td>
          </tr>
          <tr>
            <td>Contrase&ntilde;a</td>
            <td><input type="password" class="k-textbox"></td><td><br></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2" align="center"><input type="button" value="Aceptar" class="k-button" onclick="validaUsuarios()"/></td>
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
    <td align="center"><div id="div_msj" style="color:#666; font-size:16px; font-weight:bold"></div></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
</html>