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
<title>Documento sin título</title>
 <script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
 <script type="text/javascript">
         var contexPath="<%=basePath %>";
 </script> 
<script>
var cons=1;

function addOtherFileSelect()
{
	$("#files_upload").append("Archivo <input type='file' name='uploaded_files["+cons+"]' id='uploaded_files["+cons+"]' onChange='addOtherFileSelect()'/><br/>");
	conse++;
}
</script>
</head>

<body>

<form name="Upload" method="post" action="<%=basePath %>/RespuestaMultipleFileUpload.htm" enctype="multipart/form-data">

<%
	String unidad = request.getParameter("ut");
	String nombre_fichero = unidad;
%>
<table width="393" border="1" align="center">
  <tr>
    <td align="center">Archivos de <%= unidad %></td>
  </tr>
  
  <tr>
    <td><div id="files_upload">Archivo
      <input type='file' name='uploaded_files[0]' id='uploaded_files[0]' onChange='addOtherFileSelect()'/><br/>
      </div>
      </td>
    </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td align="center"><input type="submit" name="submit" value="Subir archivos" />
      <input type="hidden" id="unidad" name="unidad" value="<%=unidad %>"/>
      <input type="hidden" id="accion" name="accion" value="guardar"/></td>
    </tr>
  </table>
</form>
</body>
</html>
