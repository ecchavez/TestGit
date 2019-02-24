<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1);
%>
<%@ page session="true" %> 
		
<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/js/jquery.imagemapster.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>

<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/ubicaciones/UbicacionesJQ.js"></script>
		
<script>
	var equipos_detalle=[];
	var equipos_imagenes=[];
	var equipos_list=[];
	
	$(document).ready(function() {		
		<c:forEach var="equiposdet" items="${detallequipo}" varStatus="status">
		    equipos_detalle.push({charactx: '<c:out value="${equiposdet.charactx}" />', value: '<c:out value="${equiposdet.value}" />', id_equnr: '<c:out value="${equiposdet.id_equnr}" />'});
		</c:forEach>
		<c:forEach var="rendimg" items="${renders}" varStatus="status">
		    equipos_imagenes.push({tipo: '<c:out value="${rendimg.tipo}" />', pathimg: '<c:out value="${rendimg.urlImagen}" />'});
		</c:forEach>
		<c:forEach var="equls" items="${equipos}" varStatus="status">
	    	equipos_list.push({id_equnr: '<c:out value="${equls.id_equnr}" />', id_equnrx: '<c:out value="${equls.id_equnrx}" />'});
		</c:forEach>
		

		$('img').mapster({	
		    isDeselectable: false,
			singleSelect: true,
			showToolTip: true,
			noHrefIsMask: false,
			fillColor: 'FFFFFF',
			fillOpacity: 0.0,
			strokeWidth: 2,
			stroke:true,
			strokeColor: 'e0e0e0',
			showToolTip: true,
			areas: [
		  <c:forEach var="coordenadas" items="${allcoords}" varStatus="status">
		  {			  
            key: '<c:out value="${coordenadas.uteq}" />',
            selected: true,
            /*render_highlight: 
            {
                 fillOpacity: 0.5,
                 fillColor: '00FF00',

                 stroke: true,
                 strokeOpacity: 1.0,
                 strokeColor: 'FF0000',
                 strokeWidth: 2,
            },*/
            render_highlight: {
				<c:if test="${coordenadas.estatus eq '00'}">	
				    fillColor: '48d104',				    
			    </c:if>
				<c:if test="${coordenadas.estatus eq '01' }">	
				    fillColor: '666699',
			    </c:if>	
				<c:if test="${coordenadas.estatus eq '05' }">	
				    fillColor: '666699',
			    </c:if>	
				<c:if test="${coordenadas.estatus eq '07' }">	
				    fillColor: '666699',
			    </c:if>	
				<c:if test="${coordenadas.estatus eq '08' }">	
				    fillColor: '666699',
			    </c:if>	
                <c:if test="${coordenadas.estatus eq '03' }">	
				    fillColor: 'ff0000',
			    </c:if>	
				<c:if test="${coordenadas.estatus eq '04' }">	
				    fillColor: 'ff0000',
			    </c:if>
				<c:if test="${coordenadas.estatus eq '06' }">	
				    fillColor: 'ff0000',
			    </c:if>
				<c:if test="${coordenadas.estatus eq '02' }">	
				    fillColor: 'ffff00',
			    </c:if>	
				<c:if test="${coordenadas.estatus eq '09' }">	
				    fillColor: 'ffff00',
			    </c:if>	
				fillOpacity: 0.4,
				strokeWidth: 2,
				strokeColor: 'e0e0e0',
				tooltip: '<c:out value="${coordenadas.uteq}" />',
                stroke: true            
            }
			<c:if test="${fn:length(allcoords) > status.count}">			
        },
		</c:if>	
		<c:if test="${fn:length(allcoords) == status.count}">			
        }
		</c:if>
		</c:forEach>
		],
			mapKey: 'estatusequ'
		}); 

	});
	
	function cargaPisosDetalleExt(iduts, estatus_sel)
	{
		if(equipos_list!=undefined && equipos_list.length>=1)
		{
			for(var i=0; i<equipos_list.length; i++)
			{
				if(iduts==equipos_list[i].id_equnr)
				{
					$("#seccion").append(" / EQUIPO: "+equipos_list[i].id_equnrx);
					break;
				}
			}
		}
		
		window.parent.cargaPisosDetalle(iduts,equipos_detalle, equipos_imagenes, estatus_sel);
	}
</script>
		
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo/styles/examples.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>/kendo/styles/main.css" rel="stylesheet" type="text/css"/> 
  <body style="background-color:transparent">    
          <table width="706" height="370" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td width="706" height="370" align="center"><img src='<%=basePath %>/images/maping/<c:out value="${imagen.nombreImagen}"/>?rand=<%= Math.random()%>' ismap usemap="#ubicacion"> 
                <!-- table width="320" height="367" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="320" height="23">Detalle</td>
                  </tr>
                  <tr>
                    <td height="344"><div id="dg_equipo_detalle"></div></td>
                  </tr>
                </table -->
                <map id="ubicacion" name="ubicacion">
            <c:forEach var="coordenadas" items="${allcoords}" varStatus="status">
            	<area href="#" estatusequ ="<c:out value="${coordenadas.uteq}" />" full="<c:out value="${coordenadas.uteq}" />" shape="poly"  coords="<c:out value="${coordenadas.coord}"/>" alt="${coordenadas.uteq}" title="${coordenadas.uteq}" onClick="cargaPisosDetalleExt('${coordenadas.uteq}','${coordenadas.estatus}')"/>
            </c:forEach>
          </map></td>
            </tr>
          </table>
          
          
 
</body>
  <input type="hidden" value="<%=request.getParameter("idUbicacion") %>" id="idUbicacion"> 
</html>
