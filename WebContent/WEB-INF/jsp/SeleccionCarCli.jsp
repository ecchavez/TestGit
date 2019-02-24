<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>
		<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
		<script src="<%=basePath %>/js/jquery.blockUI.js"></script>
				
		<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
		<script src="<%=basePath %>/kendo/js/console.js"></script>
		<script src="<%=basePath %>/js/clientes/ClientesJQ.js"></script>
		<script src="<%=basePath %>/js/utils.js"></script>
		<script type="text/javascript">var contexPath="<%=basePath %>";</script>
		<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
		<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
		<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />

		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title>Parámetros de selección</title>
		<script>
           
         $(document).ready(function () {
                   cargaGrid();
                   
         });

</script>
		<style>
.disabled {
	text-decoration: line-through;
	color: red
}
</style>
	</head>

<form action="" id="paramSeleccionCarCli">

  <table width="335" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="114">&nbsp;</td>
      <td width="150">&nbsp;</td>
      <td width="71">&nbsp;</td>
    </tr>
    <tr>
      <td height="36">Nombre</td>
      <td><input type="text"  style="text-transform: uppercase;" id="nomTxt"  class="k-textbox" onkeyup="this.value=this.value.toUpperCase();" onkeypress="return captureNombres(event)"/>
      </td>
      <td>
        <input type="button" class="k-button" id="nom" value="..."  onclick="openParamCarCli('Nombre')"/>
      </td>
    </tr>
    <tr>
      <td height="32">Segundo Nombre</td>
      <td><input type="text" style="text-transform: uppercase;" id="nom2Txt" onkeyup="this.value=this.value.toUpperCase();" class="k-textbox" onkeypress="return captureNombres(event)" /></td>
      <td><input type="button" class="k-button" id="nom2" value="..." onclick="openParamCarCli('SegundoNombre')" /></td>
    </tr>
    <tr>
      <td height="34">Apellido Paterno</td>
      <td><input type="text" style="text-transform: uppercase;" id="apPatTxt" onkeyup="this.value=this.value.toUpperCase();" class="k-textbox" onkeypress="return captureNombres(event)" /></td>
      <td><input type="button" class="k-button" id="apPat" value="..." onclick="openParamCarCli('ApellidoPaterno')" />	</td>
    </tr>
    <tr>
      <td height="42">Apellido Materno</td>
      <td><input type="text" style="text-transform: uppercase;" id="apMatTxt" onkeyup="this.value=this.value.toUpperCase();" class="k-textbox" onkeypress="return captureNombres(event)" /></td>
      <td><input type="button" class="k-button" id="apMat" value="..." onclick="openParamCarCli('ApellidoMaterno')"/></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td><input  type="button" class="k-button" id="aceptarCriterios" value="Aceptar" onclick="buscarClientes()"/></td>
      <td><input  type="button" class="k-button" id="cancelarCriterios" value="Cancelar" onclick="cancelarSelec()"/></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td height="26">&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td colspan="3"><div id="mensajes_main">Mensajes de la aplicacion</div></td>
    </tr>
  </table>
  <div id="windowParamsCarCli"></div>
</form>


</html>