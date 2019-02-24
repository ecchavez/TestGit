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
<title>Cotizador </title>

<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/js/jquery.blockUI.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>

<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/cotizador/CotizadorJQ.js"></script>
<script>	
    
	$(document).ready(function(){
		//getCotizacionGuardado()
		
		datos_cancelacion=window.parent.sub_equipos_select_xml;
		//alert(datos_cancelacion);
		
		var url_simulador=contexPath+'/Simulador.htm?from=<%=request.getParameter("from") %>&idCarCliente=<%=request.getParameter("idCarCliente") %>&idCliente=<%=request.getParameter("idCliente") %>&idCotizacion=<%=request.getParameter("idCotizacion") %>&idPedido=<%=request.getParameter("idPedido")%>&idequ_get=<%=request.getParameter("idequ_get")%>&idCotizacionZ=<%=request.getParameter("idCotizacionZ")%>&equipo_select=<%=request.getParameter("equipo_select")%>&nombreCte=<%=request.getParameter("nombreCte")%>&datos_equipos='+datos_cancelacion;
		$("#iframe_simulador").attr('src', ''+url_simulador+'');
	});

</script>

<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo/styles/examples.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>/kendo/styles/main.css" rel="stylesheet" type="text/css"/>


</head>
<body>
	<style scoped>   
                
                #cotizador_container {
                    margin: 0 5px;
                    overflow: auto;
                    padding: 5px;
                    width: 660px;
                    height: 670px;
                    border: 1px solid #bbb;
                    -moz-box-shadow: 0 1px 2px rgba(0,0,0,0.45), inset 0 0 30px rgba(0,0,0,0.07);
                    -webkit-box-shadow: 0 1px 2px rgba(0,0,0,0.45), inset 0 0 30px rgba(0,0,0,0.07);
                    box-shadow: 0 1px 2px rgba(0,0,0,0.45), inset 0 0 30px rgba(0,0,0,0.07);
                    -webkit-border-radius: 8px;
                    -moz-border-radius: 8px;
                    border-radius: 8px;
                }
	</style>
	
	<div class="izquierda" id="cotizador_container">
          <iframe id="iframe_simulador" 
            allowtransparency="true" 
			name="iframe_simulador"
			src=""
			scrolling="auto"
			noresize="noresize"
			frameborder="no"
			width="100%"
			height="100%">
			</iframe>
									
   </div>
   <div id="mensajes_main"></div>
   
   <input type="hidden" id="from_cotizador"/> 
</body>
</html>