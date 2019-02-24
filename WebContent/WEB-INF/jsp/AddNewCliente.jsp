<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Nuevo Cliente</title>

<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>
<script src="jquery-1.7.1.js"></script>
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/clientes/ClientesJQ.js"></script>
<script>
		$(document).ready(function(){
		
	    $("#cmb_viaContacto").kendoComboBox({
	        dataTextField: "text",
	        dataValueField: "value"
	    });	 
	    $("#cmb_vendedor").kendoComboBox({
	        dataTextField: "text",
	        dataValueField: "value"
	    });	 
	    $("#datePicker").kendoDatePicker({
 			format: "yyyy/MM/dd",
 			min: new Date(1900, 0, 1)
		});
	         
		getCargaInicial();
		
	var validator = $("#clientesForm").kendoValidator().data("kendoValidator");
                    
                    
                  
                    $("addCliente").click(function() {
                    if (validator.validate()) {
                        alta();
                        }
                    
                    });
		  
	});	
</script>

</head>
  <body>
  <form name="clientesForm" method="post" action="" >
  <table width="1024" border="1" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>Nuevo Cliente</td>
  </tr>
  <tr>
  <td>
  <table align="center">
  
  <tr>
	<td>Nombre</td>
	<td>
	<input type="text" id="txt_nom" name="txt_nom" class="k-textbox" />
	</td>
	</tr>
	<tr>
	<td>Segundo Nombre</td>
	<td>
	<input type="text" id="txt_segnom" name="txt_segnom" class="k-textbox" />
	</td>
	</tr>
	<tr>
  <td>Apellido Paterno</td>
  <td>
  <input type="text" id="txt_apa" name="txt_apa" class="k-textbox" />
  </td>
  </tr>
  <tr>
	<td>Apellido Materno</td>
  <td>
  <input type="text" id="txt_ama" name="txt_ama" class="k-textbox" />
  </td>
  </tr>
  	<tr>
  <td>Fecha Nacimiento</td>
  <td>
  <input id="datePicker" value="" style="width:150px;" />
  </td>
  </tr>
      <tr>
        <td>Vía de contacto</td>
        <td><select name="cmb_viaContacto" id="cmb_viaContacto">
       </select></td>
       </tr>
         <tr>
        <td>Telefono casa</td>
        <td><input id="txt_telCasa" maxlength="10" type="tel" class="k-textbox"/></td>
        </tr>
        <tr>
        <td>Telefono celular</td>
        <td><input id="txt_telCel" maxlength="10" type="tel" class="k-textbox"/></td>
        </tr>
         <tr>
        <td>Telefono oficina</td>
        <td><input id="txt_telOfic" maxlength="10" type="tel" class="k-textbox"/></td>
        </tr>
        <tr>
        <td>Extensión</td>
        <td><input id="txt_ext" maxlength="15" class="k-textbox"/></td>
        </tr>
         <tr>
        <td>Mail 1</td>
        <td><input id="txt_correo1" name="txt_correo1" class="k-textbox" type="email" /></td>
       </tr>
        <tr>
        <td>Mail 2</td>
        <td><input id="txt_correo2" name="txt_correo2" class="k-textbox" type="email" /></td>
       </tr>
       <tr>
        <td>Vendedor</td>
        <td><select name="cmb_vendedor" id="cmb_vendedor">
        </select></td>
        </tr>
        <tr>
        <td ><input type="button" class="k-button" name="addCliente" id="addCliente" value="Guardar" onclick="adminClientes('addCliente',1)"/></td>
        <td ><input type="button" class="k-button" name="cancelar" id="guardar" value="Cancelar" onClick=""></td>
        </tr></table>
  </td>
  </tr>  
  <tr> <div id="buttomBar">
  	<div class="console"/>
  </div>
  </tr>
  </table>
  <div id="alert" />
  </form> 
  </body>
</html>
