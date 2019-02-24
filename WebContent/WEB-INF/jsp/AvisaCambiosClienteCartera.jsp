<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<%
String path = request.getContextPath(),Val=request.getParameter("id"),Act=request.getParameter("act");
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
String paramValorNom1= request.getParameter("paramValorNom1");
String paramValorNom2= request.getParameter("paramValorNom2");
String paramValorPApe= request.getParameter("paramValorPApe");
String paramValorSApe= request.getParameter("paramValorSApe");
String paramValorFecNa= request.getParameter("paramValorFecNa");

String paramValorNom1N = request.getParameter("paramValorNom1N");
String paramValorNom2N = request.getParameter("paramValorNom2N");
String paramValorPApeN = request.getParameter("paramValorPApeN");
String paramValorSApeN = request.getParameter("paramValorSApeN");
String paramValorFecNaN = request.getParameter("paramValorFecNaN");

%>
<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/js/jquery.blockUI.js"></script>
<script src="<%=basePath %>/js/utils.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />
<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/clientes/ClientesJQ.js"></script>
<script src="<%=basePath %>/js/clientes/ClientesSAPJQ.js"></script>
<script>
$(document).ready(function(){

});	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Aviso de Cambios en Cartera Cliente</title>

</head>

<body>
<table width="770" border="0" align="center">
  <tr>
    <td colspan="4" align="center" bgcolor="#CCCCCC"><b>Diferencias Detectadas</b></td>
  </tr>
  <tr>
    <td bgcolor="#CCCCCC"><b>CAMPO</b></td>
    <td align="center" bgcolor="#CCCCCC">&nbsp;</td>
    <td align="center" bgcolor="#CCCCCC"><B>Cartera de Cliente</B></td>
    <td align="center" bgcolor="#CCCCCC"><B>Cliente</B></td>
  </tr>
  <tr>
    <td width="230">Nombre1:</td>
    <td width="6" align="center">&nbsp;</td>
    <td width="249" align="center"><%=paramValorNom1%></td>
    <td width="267" align="center"><%=paramValorNom1N%></td>
  </tr>
  <tr>
    <td>Nombre2:</td>
    <td align="center">&nbsp;</td>
    <td align="center"><%=paramValorNom2%></td>
    <td align="center"><%=paramValorNom2N%></td>
  </tr>
  <tr>
    <td>Primer Apellido:</td>
    <td align="center">&nbsp;</td>
    <td align="center"><%=paramValorPApe%></td>
    <td align="center"><%=paramValorPApeN%></td>
  </tr>
  <tr>
    <td>Segundo Apellido:</td>
    <td align="center">&nbsp;</td>
    <td align="center"><%=paramValorSApe%></td>
    <td align="center"><%=paramValorSApeN%></td>
  </tr>
  <tr>
    <td>Fecha de Nacimiento o Constituci&oacute;n</td>
    <td align="center">&nbsp;</td>
    <td align="center"><%=paramValorFecNa%></td>
    <td align="center"><%=paramValorFecNaN%></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" align="center"> <input type="button" name="btn_continuar" id="btn_continuar" value="Continuar" class="k-button" onclick="guardaClienteSAP2('CAMBIOSCARTERA');">   
 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;   
       <input type="button" name="btn_regresarmodificar" id="btn_regresarmodificar" value="Regresar a modificar" class="k-button" onclick="cerrarModificarDatosCarteraCliente();"></td>
  </tr>
</table>
</body>
</html>