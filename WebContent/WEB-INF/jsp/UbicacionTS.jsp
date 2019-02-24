<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 

response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1);
%>
<html>
  <head>
    <%@ page session="true" %> 
		
<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/js/jquery.imagemapster.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>

<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/ubicaciones/UbicacionesJQ.js"></script>
		
<script>
	$(document).ready(function() {
		var areas_colors = [];
		var params = [];
	
		<c:forEach var="coordenadas" items="${allcoords}" varStatus="status">
			params = [];
			params['key'] = '<c:out value="${coordenadas.uteq}" />';
			params['selected'] = true;
			params['render_select'] = { fillColor: '00ff00', stroke: true };
			areas_colors.push(params);
		</c:forEach>

		$('img').mapster({
			isDeselectable: false,
			singleSelect: true,
			showToolTip: true,
			noHrefIsMask: false,
			fillColor: '00ff00',
			fillOpacity: 0.5,
			strokeWidth: 1,
			stroke:true,
			strokeColor: '7d7d7d',
			areas: areas_colors,
			mapKey: 'estatus'
			// onClick: function (e) {showFloorMap(e.key);}
		}); 

	});
	
	function cargaPisosGridInt(iduts)
	{
		window.parent.cargaPisosGrid(iduts);
	}
</script>
		
  </head>
  
  <body style="background-color:transparent">
  <table width="900" height="336" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="828" height="19">&nbsp;</td>
    </tr>
    <tr>
      <td height="294" align="center"><img src='<%=basePath %>/images/maping/<c:out value="${imagen.nombreImagen}"/>?rand=<%= Math.random()%>' ismap usemap="#ubicacion"> 
             <map id="ubicacion" name="ubicacion">
               <c:forEach var="coordenadas" items="${allcoords}" varStatus="status">
               <area href="#" estatus ="<c:out value="${coordenadas.uteq}" />" full="<c:out value="${coordenadas.uteq}" />" shape="poly"  coords="<c:out value="${coordenadas.coord}"/>" onClick="cargaPisosGridInt('${coordenadas.uteq}')" alt="${coordenadas.uteq}" title="${coordenadas.uteq}" />
               </c:forEach>
             </map> 
     </td>
    </tr>
    <tr>
      <td height="23">&nbsp;</td>
    </tr>
  </table>
    
  </body>
</html>
