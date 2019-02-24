<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>
		<title>VIVIENDA</title>
        <script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
		<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
        <script src="<%=basePath %>/kendo/js/console.js"></script>
        <script type="text/javascript">
            var contexPath="<%=basePath %>";
        </script>
        <script src="<%=basePath %>/js/buttons/buttons_state.js"></script>
        <script src="<%=basePath %>/js/usuarios/UsuariosJQ.js"></script>        
        <script>
                $(document).ready(function() {				
					getUsuarios(); 
					var window = $("#winOptionDelete");
					   
                    $("#menu").kendoMenu();
					  
					$("#dg_usuarios").kendoGrid({
							  height: 400,				
							  sortable: true,				  
                 			  resizable: true,
                 			  scrollable: true,
							  columns: [
										{
											field: "nombre1",
			                                title: "Nombre",
			                                width: "150px"
			                            },
			                            {
			                                field: "nombre2",
			                                title: "Segundo Nombre",
			                                width: "150px"
			                            },
			                            {
			                                field: "app_pat",
			                                title: "Primer Apellido",
			                                width: "150px"			                                
			                            },
			                            {
			                                field: "app_mat",
			                                title: "Segundo Apellido",
			                                width: "150px"			 
			                            },
			                            {
			                            	field: "pass",
			                                title: "Contraseña",
			                                width: "100px"			                                 
			                            },
			                            {
			                            	field: "usuario",
			                                title: "Usuario",
			                                width: "120px"			 
										},
										{
											field: "",
											title: "",
											width: "80px",
											template: '<div><input type="button" class="k-button" value="Modificar" onClick="openWinAddUser(\'actualiza\',\'${ usuario }\')"><div>'
										},
										{
											field: "",
											title: "",
											width: "80px",
											template: '<div><input type="button" class="k-button" value="Borrar" onClick="validaEliminaUser(\'delUser\',\'${ usuario }\')"></div>'
										}
									],
			                  selectable: "row",
							  change: onChangeUsers								  
						  }); 
							  
						  $("#tabstrip").kendoTabStrip({
							animation:	{
								open: {
									effects: "fadeIn"
								}
							}
					
					});
					
					if (!window.data("kendoWindow")) {
					        window.kendoWindow({
					        height: "120px",
					        title: "Pregunta",
					        modal: true,
					        visible: false,
					        width: "350px"
				        });
    				}
							   	
				});
		
				function setResponseAddUser()
				{
					kendoConsole.log("Usuario guardado satisfactoriamente","");
				}
				
				function closeUserWindow()
				{
					$("#windowAddUser").data("kendoWindow").close();
					getUsuarios();
				}
				
				/* var grid = $("#<Name of Grid>").data("kendoGrid"); 
				var selectedRow = grid.select();
 				var index = selectedRow.index(); */
				
            </script>
	<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
	<link href="<%=basePath %>/kendo/styles/examples.css" rel="stylesheet" type="text/css"/>
	<link href="<%=basePath %>/kendo/styles/main.css" rel="stylesheet" type="text/css"/>

	</head>
	<body onload="preloadImages()">
	
	<div class="demo-section" style="width: 90%;">
		<input type="button" class="k-button" value="NUEVO" id="btn_new_user" onclick="openWinAddUser('nuevo')"/>
	</div>

      <table width="900" border="0" align="center" cellpadding="0" cellspacing="0">         
        <tr>
          <td height="400">
          <table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td height="23"><div id="dg_usuarios" style="width: 897px">
                    </div></td>
                </tr>
             </table>           
            </td>
          </tr>
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          </tr>
 </table>
<div id="winOptionDelete">
	<table width="314" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="2">El usuario se eliminara, esta usted seguro?</td>
   </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
   </tr>
  <tr>
    <td width="153">&nbsp;</td>
    <td width="161">&nbsp;</td>
  </tr>
  <tr>
    <td align="center"><input class="k-button" type="button" name="button" id="button" value="Aceptar" onClick="setEliminaUsuario()"></td>
    <td align="center"><input class="k-button" type="button" name="button2" id="button2" value="Cancelar" onClick="closeConfirmaEliminaUser()"></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
</div>
<div id="buttomBar">
  <div class="console"/>
</div>
<div id="windowAddUser"></div>
</body></html>
