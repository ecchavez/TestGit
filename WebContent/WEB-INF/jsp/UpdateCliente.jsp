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
<title>Modificar Cliente</title>

<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="jquery-1.7.1.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/clientes/ClientesJQ.js"></script>
<%
String miValor,Nom,Nom2,Apa,Ama,Fecha,Telca,Telcel,Telmob,Ext,Mail1,Mail2,Viasel;

miValor = request.getParameter("id_sel");
Nom = request.getParameter("nom");
Nom2 = request.getParameter("nom2");
Apa = request.getParameter("apa");
Ama = request.getParameter("ama");
Fecha = request.getParameter("fch_nac");
Telca = request.getParameter("telca");
Telcel = request.getParameter("telcel");
Telmob = request.getParameter("telmob");
Ext = request.getParameter("ext");
Mail1 = request.getParameter("mail1");
Mail2 = request.getParameter("mail2");
Viasel = request.getParameter("viasel");
%> 
<script>
		$(document).ready(function(){
	    $("#windowUpdateCliente").kendoWindow();
		getCargaByCliente(); 
	    $("#cmb_viaContacto").kendoComboBox({
	        dataTextField: "text",
	        dataValueField: "value"
	    });	 
	    $("#datePicker").kendoDatePicker({
 			format: "yyyy/MM/dd",
 			min: new Date(1990, 0, 1)
		});
	    	    
		
		
	var validator = $("#clientesForm").kendoValidator().data("kendoValidator");
                    
                    $("upCliente").click(function() {
                        update();
                    });
		  
	});	
</script>
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" type="text/css"/>

</head>
  <body>
		<form name="clientesForm" method="post" action="" >
		<div id="example" class="k-content"> 
		<input type="hidden" id="viasel" name="viasel" value=<%=Viasel%> ></hidden>
		<input type="hidden" id="id_sel" name="id_sel" value=<%=miValor%> ></hidden>
			<table width="1024" border="1" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td>
						Modificar Cliente

					</td>
				</tr>
				<tr>
				<td>
					<table align="center">
						<tr>
							<td>
								Nombre
							</td>
							<td>
								<input type="text" id="txt_nom" name="txt_nom" value="<%=Nom%>" class="k-textbox" />
								<span class="k-invalid-msg" data-for="txt_nom"></span>
							</td>
						</tr>
						<tr>
							<td>
								Segundo Nombre
							</td>
							<td>
								<input type="text" id="txt_segnom" name="txt_segnom" value="<%=Nom2%>"
									class="k-textbox" />
							</td>
						</tr>
						<tr>
							<td>
								Apellido Paterno
							</td>
							<td>
								<input type="text" id="txt_apa" name="txt_apa" value="<%=Apa%>" class="k-textbox" />
							</td>
						</tr>
						<tr>
							<td>
								Apellido Materno
							</td>
							<td>
								<input type="text" id="txt_ama" name="txt_ama" value="<%=Ama%>" class="k-textbox" />
							</td>
						</tr>
						<tr>
							<td>
								Fecha Nacimiento
							</td>
							<td>
								<input id="datePicker" value="<%=Fecha%>" style="width: 150px;" />
							</td>
						</tr>
						<tr>
							<td>
								Vía de contacto
							</td>
							<td>
								<select name="cmb_viaContacto" id="cmb_viaContacto" >
								</select>
							</td>
						</tr>
						<tr>
							<td>
								Telefono casa
							</td>
							<td>
								<input id="txt_telCasa" type="tel" value="<%=Telca%>" maxlength="10" class="k-textbox" />
							</td>
						</tr>
						<tr>
							<td>
								Telefono celular
							</td>
							<td>
								<input id="txt_telCel" type="tel"  value="<%=Telcel%>" maxlength="10" class="k-textbox" />
							</td>
						</tr>
						<tr>
							<td>
								Telefono oficina
							</td>
							<td>
								<input id="txt_telOfic" type="tel"  value="<%=Telmob%>" maxlength="10" class="k-textbox" />
							</td>
						</tr>
						<tr>
							<td>
								Extensión
							</td>
							<td>
								<input id="txt_ext" value="<%=Ext%>" maxlength="15" class="k-textbox" />
							</td>
						</tr>
						<tr>
							<td>
								Mail 1
							</td>
							<td>
								<input id="txt_correo1" name="txt_correo1" value="<%=Mail1%>" class="k-textbox"
									type="email" placeholder="e.g. myname@example.net"/>
							</td>
						</tr>
						<tr>
							<td>
								Mail 2
							</td>
							<td>
								<input id="txt_correo2" name="txt_correo2" value="<%=Mail2%>" class="k-textbox"
									type="email" placeholder="e.g. myname@example.net"/>
							</td>
						</tr>
						
						<tr>
							<td colspan="2">
								<input type="button" class="k-button" name="updateCliente"
									id="updateCliente" value="Guardar" onclick="adminClientes('upCliente',1)">
							</td>
							<td colspan="2">
								<input type="button" class="k-button" name="cancelar"
									id="cancelar" value="Cancelar" onClick="cerrar()">
							</td>
						</tr>
					</table>
				</td>
			 </tr>
			   <tr> <div id="buttomBar">
  	<div class="console"/>
  </div>
  </tr>
			 
			</table>
			<div id="alert" />
			</div>
		</form>
	</body>
</html>
