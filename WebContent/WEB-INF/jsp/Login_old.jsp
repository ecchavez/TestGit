<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login (Area restringida)</title>

<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>
<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/usuarios/UsuariosJQ.js"></script>
<script>
	$(document).ready(function(){

	    $("#cmb_ubicaciones").kendoComboBox({
	        dataTextField: "text",
	        dataValueField: "value"
	    });	    
		//getUbicaciones();
	});

</script>

<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/style/main.css" rel="stylesheet" type="text/css"/>


<style type="text/css">
#apDiv1 {
	position:absolute;
	left:34px;
	top:21px;
	width:1117px;
	height:32px;
	z-index:1;
}
#apDiv2 {
	position:absolute;
	left:22px;
	top:321px;
	width:1136px;
	height:38px;
	z-index:2;
}
</style>
</head>
<body>
<div id="apDiv1">
 
</div>
<div id="apDiv2"></div>
<form name="form1" method="post" action="">

    <div id="panel" class="k-content"><!-- c:out value="${ubicacionObject.mensaje}"/-->
      <p>&nbsp;</p>
      <p>&nbsp;</p>
      <table width="343" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td align="center">Autenticacion de usuarios</td>
      </tr>
      <tr>
        <td align="center">&nbsp;</td>
      </tr>
      <tr>
        <td align="center">&nbsp;</td>
      </tr>
      <tr>
        <td align="center"><select name="cmb_ubicaciones" id="cmb_ubicaciones">
          <c:forEach var="ubicaciones" items="${ubicacionObject.ubicacionesList}" varStatus="status">
            <!--c:out value="${status.count}"/-->
            <option value='<c:out value="${ubicaciones.ubicacionClave}"/>'>
              <c:out value="${ubicaciones.ubicacionDescripcion}"/>
              </option>
          </c:forEach>
        </select></td>
      </tr>
      <tr>
        <td align="center">&nbsp;</td>
      </tr>
      <tr>
        <td align="center"><input id="txt_user" class="k-textbox"/></td>
      </tr>
      <tr>
        <td align="center">&nbsp;</td>
      </tr>
      <tr>
        <td align="center"><input class="k-textbox" type="password" id="txt_passwd" /></td>
      </tr>
      <tr>
        <td align="center">&nbsp;</td>
      </tr>
      <tr>
        <td align="center"><input type="button" class="k-button" name="enviar" id="enviar" value="Enviar" onClick="validaUsuario()"/></td>
      </tr>
      </table>
      <p>&nbsp;</p>
      <p>&nbsp;</p>
  </div>
  <div id="buttomBar">
  	<div class="console"/>
  </div>
</form>
</body>
</html>