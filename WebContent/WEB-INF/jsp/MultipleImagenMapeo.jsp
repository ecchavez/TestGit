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
<script src="<%=basePath %>/js/jquery.imagemapster.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>

<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/ubicaciones/UbicacionesJQ.js"></script>

<script type='text/javascript'>
$(document).ready(function() {
	$('img').mapster({
		isDeselectable: false,
		singleSelect: true,
		showToolTip: true,
		noHrefIsMask: false,
		fillColor: 'FFFFFF',
		fillOpacity: 0.5,
		strokeWidth: 2,
		stroke:true,
		strokeColor: 'bd0101',
		areas: [
				{
				key: '1',
				selected: true,
				render_select: {
					fillColor: '00ff00',
					stroke: true            
				}
			},
			{
				key: '2',
				selected: true,
				render_select: {
					 fillColor: 'ff0000',
					 stroke: true 
				}
			}
	   ],
		mapKey: 'estatus'
		// onClick: function (e) {showFloorMap(e.key);}
	}); 
});

</script>
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />


</head>
<body style="background-color:transparent">

        <!-- img src="http://localhost/vivienda/imagenes/directorio.png" ismap usemap="#ubicacion" -->
        <img src='<%=basePath %>/images/maping/<%= request.getParameter("nombreImagen")%>?rand=<%= Math.random()%>' ismap usemap="#ubicacion">
          <map id="ubicacion" name="ubicacion">
              <c:forEach var="coordenadas" items="${respUbicacionAction}" varStatus="status">
                <area href="#" estatus ="1" full="<c:out value="${coordenadas.uteq}" />" shape="poly"  coords="<c:out value="${coordenadas.coord}"/>" />
              </c:forEach>
         </map>   
</body>
</html>