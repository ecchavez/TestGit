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
<script src="<%=basePath %>/kendo/js/console.js"></script> 
    <script src="source/kendo.all.js"></script>

<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />

<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/contratos/ContratosJQ.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Impresion Contratos </title>
</head>
<body>
		<form name="carteraC" id="carteraC" method="post" action="">
<table width="100%" style="height:100%; " cellpadding="10" cellspacing="0" border="0" >
  <tbody>
    <tr>
      <!-- ============ HEADER SECTION ============== -->
      <td colspan="1" style="height:5px; " bgcolor="#FFFFFF" ><h1><img alt="" src="images/logo_principal.png" ></h1>
      </td>
    </tr>
    <tr background="<%=basePath %>/images/bg.jpg">
      <!-- ============ RIGHT COLUMN (CONTENT) ============== -->
      <td valign="top" background="<%=basePath %>/images/bg.jpg" height="550">      
      	 
		<table width="600" border="1" align="center" cellpadding="0" cellspacing="0">
<tbody><tr>
<td align="center" width="596" bgcolor="#bbccdd">
Impresión Documentos
</td>
</tr>
<tr>
<td bgcolor="#FFFFFF">
<table align="center">

<tbody><tr>
<td>
Id Pedido
</td>
<td><input type="text" id="idPedido" name="idPedido" class="k-textbox" onKeyPress="captureNombres(this,event);" />
</td>
<td> 
<input type="button" class="k-button" name="btn" id="btn" value="Contrato" onClick="impresion(1,'')"/>
<input type="button" class="k-button" name="btn" id="btn" value="Carta Promesa" onClick="impresion(2,'')"/>
<input type="button" class="k-button" name="btn" id="btn" value="Talonario" onClick="impresion(3,'')"/>
<input type="button" class="k-button" name="btn" id="btn" value="Pagare" onClick="impresion(4,'')"/>
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
    
    <div id="windowImpresion">
    </div>
    
    <tr>
      <td colspan="1" align="center" height="20" style="background-image:url(images/gradient.png); " >Derechos reservados Grupo Gigante ©</td>
    </tr>
  </tbody>
</table>
</form>
</body>
</html>