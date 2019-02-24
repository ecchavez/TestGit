<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <%@ page session="true" %> 
		
<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>

<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/ubicaciones/UbicacionesJQ.js"></script>
		
<script>
	$(document).ready(function() {
		
	});
	
</script>
		
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo/styles/examples.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>/kendo/styles/main.css" rel="stylesheet" type="text/css"/>
  </head>
  
  <body>
  <table width="387" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="19" colspan="2" align="center">&nbsp;</td>
    </tr>
    <tr>
      <td width="154">UbicacionTecnica</td>
      <td width="233"><select name="cmb_ubicaciones" id="cmb_ubicaciones">
	          <c:forEach var="ubicaciones" items="${ubicacionObject.ubicacionesList}" varStatus="status">
	            <option value='<c:out value="${ubicaciones.ubicacionClave}"/>'>
	              <c:out value="${ubicaciones.ubicacionDescripcion}"/>
	              </option>
	          </c:forEach>
	        </select></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td align="center">&nbsp;</td>
    </tr>
    <tr>
      <td>Tipo</td>
      <td><select name="cmb_ubicaciones" id="cmb_ubicaciones">
	          <c:forEach var="ubicaciones" items="${ubicacionObject.ubicacionesList}" varStatus="status">
	            <option value='<c:out value="${ubicaciones.ubicacionClave}"/>'>
	              <c:out value="${ubicaciones.ubicacionDescripcion}"/>
	              </option>
	          </c:forEach>
	        </select></td>
    </tr>
    <tr>
      <td align="center">&nbsp;</td>
      <td align="center">&nbsp;</td>
    </tr>
    <tr>
      <td>Seleccione la imagen</td>
      <td align="center"><input name="fileData" id="fileData" type="file" class="k-button"/></td>
    </tr>
    <tr>
      <td align="center">&nbsp;</td>
      <td align="center">&nbsp;</td>
    </tr>
  </table>
    
  </body>
</html>
