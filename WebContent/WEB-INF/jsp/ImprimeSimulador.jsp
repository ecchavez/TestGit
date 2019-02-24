<%@page import="mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
UsuarioDto user = (UsuarioDto) request.getSession().getAttribute("usrSession");
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Simulador</title>

<%@ page session="true" %> 
		
<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/js/utils.js"></script>

<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>

<script>
	
	$(document).ready(function() { 
	   
	});
	
	function setImprimeCotizacion()
	{
		$("#nom_vend").val('<%= user.getNombre1()+" "+user.getApp_pat()+" "+user.getApp_mat() %>');
		document.form.submit();		
	}
	
</script>

<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo_ant/styles/examples.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>/kendo_ant/styles/mainb.css" rel="stylesheet" type="text/css"/>

</head>
<body style="background-color:transparent">
<form name="form" method="post" action="<%=basePath %>/ImprimeSimulador.htm">
<table border="0" align="right" cellpadding="0" cellspacing="0" width="100%">
  <tr>
    <td>
      <input name="equipo" type="text" id="equipo" /> 
      <input name="nom_vend" type="text" id="nom_vend" />
      <input name="id_equnr" type="text" id="id_equnr" />
      <input name="id_cotiz_z" type="text" id="id_cotiz_z" />
      <input type="text" id="claveClienteTicket" name="claveClienteTicket" />
      <input name="nombreC" id="nombreC" type="text"/>
	  <input name="aedat" type="text" id="fechaiv" />
      <input type="text" name="datab" id="datab" />
      <input type="text" name="datub" id="datub" />
      <input type="text" name="plan" id="plan" />
      <input type="text" name="equipos" id="equipos" />
      <input type="text" name="detalle" id="detalle" />
    
      <input type="text" name="nest" id="nest" />
    
      <input type="text" name="nbod" id="nbod" />
      <input type="text" name="depto" id="depto" />
      </td>
  </tr>
</table>

  <table border="0" cellpadding="0" cellspacing="0" width="200">
    <tr>
      <td colspan="4"><input type="text" name="equipo" id="equipo" />
        <input type="text" name="m2tot" id="m2tot" />
        <input type="text" name="descm" id="descm" />
        <input type="text" name="descp" id="descp" />
        <input type="text" name="descmdes" id="descmdes" />
        <input type="text" name="subequipos" id="subequipos" />
        <input type="text" name="total" id="total" />
        <input type="text" name="anticipo" id="anticipo" />
        <input type="text" name="enganche" id="enganche" />
        <input type="text" name="penganche" id="penganche" />
        <input type="text" name="diferido" id="diferido" />
        <input type="text" name="pdiferido" id="pdiferido" />
        <input type="text" name="pago_final" id="pago_final" />
        <input type="text" name="ppago_final" id="ppago_final" />
        <input type="text" name="gastos_admin" id="gastos_admin" />
        <input type="text" name="accion" id="accion" value="print"/>
        </td>
    </tr>
  </table>
  </form>
</body>
</html>