<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <title>Clean USA Map Demo - jsFiddle demo by jamietre</title>
  
<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>

<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/ubicaciones/UbicacionesJQ.js"></script>

<script type='text/javascript'>
$(document).ready(function() {
	
	     getTiposEquipos('<%= request.getParameter("idUbicacion") %>');
	    
	    $("#tipo").kendoDropDownList({
	        dataTextField: "i_tipo_eq",
	        dataValueField: "i_tipo_eq",
	        change: onChangeEquipo
	    });
});

function validaForma()
{
     var resp=true; 	  
  if($("#fileData").val()=="")
  {
  	resp=false;
  	$("#mensajes_main").html("Seleccione una imagen para actualizar","warning");
  }	  
	 return resp;
}
</script>
<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />

</head>
<body>
<form method="post" enctype="multipart/form-data" onsubmit="return validaForma()" action="<%=basePath %>/RespuestaAdminImagenesEq.htm"> 
  	<table width="387" border="0" align="center" cellpadding="0" cellspacing="0">
	    <tr>
	      <td height="19" colspan="2" align="center">&nbsp;</td>
	       </tr>
	    <tr>
	      <td width="154">Unidad tecnica</td>
	      <td width="233"><%= request.getParameter("idUbicacion") %></td>
	       </tr>
	    <tr>
	      <td>Tipo de equipo</td>
	      <td><select name="tipo" id="tipo">
	        
          </select></td>
	       </tr>
	    <tr>
	      <td align="left">&nbsp;</td>
	      <td align="center"><div id="mensaje_imagen"></div></td>
	    </tr>
	    <tr>
	      <td align="left">Seleccione la imagen</td>
	      <td><input name="fileData" id="fileData" type="file" class="k-button"/></td>
      </tr>
	    <tr>
	      <td align="left">&nbsp;</td>
	      <td align="center">&nbsp;</td>
      </tr>
	    <tr>
	      <td colspan="2" align="center"><input type="submit" value="Guardar" class="k-button"/></td>
      </tr>
	</table>
	<input type="hidden" id="idUbicacion" name="idUbicacion" value="<%= request.getParameter("idUbicacion") %>"/>
	
  <div id="mensajes_main"></div>
</form>
</body>
</html>

