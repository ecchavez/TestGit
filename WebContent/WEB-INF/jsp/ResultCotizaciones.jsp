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
<script src="<%=basePath %>/js/cierreVenta/cierreVentaJQ.js"></script>
<script src="<%=basePath %>/js/buttons/buttons_state.js"></script>
<script src="<%=basePath %>/js/utils.js"></script>
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo/styles/examples.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>/kendo/styles/main.css" rel="stylesheet" type="text/css"/>


<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Criterios Adicionales</title>
<script>
              $(document).ready(function () {
               
                 cargaBusq();
         });
</script>
</head>

<body onload="preloadImages()">
<form name="criteriosCotPed" id="criteriosCotPed" method="post" action="">	
<table width="100%" style="height:100%; " cellpadding="10" cellspacing="0" border="0" >
  <tbody>
    <tr>
      <!-- ============ HEADER SECTION ============== -->
      <td colspan="1" style="height:5px; " bgcolor="#FFFFFF" >
        <table  border="0" cellpadding="0" cellspacing="0">
          
      </table></td>
    </tr>
    <tr>
      <!-- ============ RIGHT COLUMN (CONTENT) ============== -->
       <td valign="top" background="<%=basePath %>/images/bg.jpg" height="550">      
      	 
		<table width="600" border="1" align="center" cellpadding="0" cellspacing="0">
<tbody><tr>
<td align="center" width="596">
Parámetros de Selección
</td>
</tr>
<tr>
<td bgcolor="#FFFFFF">
<table align="center">

<tbody>
<tr><td colspan="3" align="center">
         	<input type="button" id="aceptar" value="Aceptar" onclick="aceptarCriterios('cot')"></input>
 </td>
</tr>
<tr>
<td> <div id="dg_FindCotsPeds"></div>
</td>
</tr>


</tbody></table>
</td>
</tr>
</tbody></table>
		
		

	  <div id="buttomBar">
	  	<div class="console"/>
	  </div>	
	  </div>
      
      </td>
    </tr>

  </tbody>
</table>
</form>
</body>
</html>
