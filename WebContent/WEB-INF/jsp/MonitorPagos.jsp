<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>
<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>
<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/cierreVenta/monitorPagosJQ.js"></script>
<script src="<%=basePath %>/js/cierreVenta/monitorCotPedJQ.js"></script>
<script src="<%=basePath %>/js/buttons/buttons_state.js"></script>
<script src="<%=basePath %>/js/contratos/ContratosJQ.js"></script>
<script src="<%=basePath %>/kendo/js/cultures/kendo.culture.es-EC.min.js"></script>
<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Parámetros de selección Monitor de Pagos</title>
<script>

		kendo.culture("es-EC");

       $(document).ready(function () {
        $("#dg_PagosRegistrados").kendoGrid({
					 height:500
				 });
               	BuscarPagos();
         });

</script>
<style> 
.disabled{text-decoration:line-through;color:red}
</style> 
</head>


<body onload="preloadImages()">
<form action="" id="monitorPagos" >
  <table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
      <td height="400"><table width="80%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
          <td colspan="2">
          <div id="resultsPagos" style="visibility: hidden">
            <table>
              <tr align="center">
                <td><input type="button" class="k-button" style="width: 115px" id="registro" value="Registro Pago" onclick="window.parent.registraPagoBtn('fromMonitor')"/></td> 
                <td><input type="button" class="k-button" style="width: 115px" id="modificar" value="Modificar" onclick="openDetallePago('modificaPago')"/></td>
                <td><input type="button" class="k-button" style="width: 115px" id="modificar" value="Detalle" onclick="openDetallePago('visualizaPago')"/></td>
                <td><input type="button" class="k-button" style="width: 115px" id="comprobante" value="Comprobante Pago" onclick="validaSeleccion(5)"/></td>
                <td><input type="button" class="k-button" style="width: 115px" id="cierre" value="Cierre Venta" onclick="regresaCierre()"/></td>        
               </tr>
             </table>
          </div>
          </td>
        </tr>       
        <tr>
          <td><div id="dg_PagosRegistrados" style="width:800px"></div></td>
        </tr>

      </table></td>
    </tr>
    <tr>
      <td>
      </td>
    </tr>
    <tr>
       <td>
          <div id="mensajes_main">Mensajes de la aplicacion</div>
       </td>
    </tr>
  </table>
  
  <div id="windowDetallePago"></div>  
<div id="windowImpresion"></div>
<div id="winRegPago"></div>



</form>
</body>

</html>