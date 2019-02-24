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
<script src="<%=basePath %>/js/usuarios/UsuariosJQ.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Catalogo de usuarios</title>
<script>
    var windowAddUser; 
	$(document).ready(function() { 
	    getUsuarios(); 
    $("#dg_usr_perm").kendoGrid({
			    columns: [                            
                            {
                                field: "id_permiso",
                                width: 60,
                                title: "Permiso"
                            },
                            {
                                field: "id_permisox",
                                title: "Descripcion"
                            }
                        ],

			  change: onChangeUsrPerm,
			  height: 200,
			  selectable: "row",
			  filterable: true,
			  sortable: true,				  
	          pageable: true
		}); 
		
	$("#dg_permisos").kendoGrid({
			    columns: [                            
                            {
                                field: "id_permiso",
                                width: 60,
                                title: "Permiso"
                            },
                            {
                                field: "id_permisox",
                                title: "Descripcion"
                            }
                        ],

			  change: onChangeUsrPerm,
			  height: 200,
			  selectable: "row",
			  filterable: true,
			  sortable: true,				  
	          pageable: true
		}); 
		
		$("#dg_usuarios").kendoGrid({
	    		  columns: [
                            {
                                field: "nombre1",
                                title: "Nombre"
                            },
                            {
                                field: "nombre2",
                                title: "S.Nombre"
                            },
                            {
                                field: "app_pat",
                                title: "A.Paterno"
                            },
                            {
                                field: "app_mat",
                                title: "A.Materno"
                            },
                            {
                            	field: "pass",
                                title: "Password"                                
                            },
                            {
                            	field: "usuario",
                                title: "Usuario"
                            },
                            {
                            	field: "usuario",
                                title: "Usuario"
                            },
                            {
                            	field: "id_ut_sup",
                                title: "Unidad"
                            }
                        ],

				  change: onChangeUsers,
				  height: 200,
				  selectable: "row",
				  filterable: true,
				  sortable: true,				  
                  pageable: true
			  });  	
	});
	
	function setResponseAddUser()
	{
		kendoConsole.log("Usuario guardado satisfactoriamente","");
	}
	  
</script>

<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo/styles/examples.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>/kendo/styles/main.css" rel="stylesheet" type="text/css"/>

</head>
<body>
<form action="" method="post">
<div id="base" class="k-content">
<table width="1024" border="1" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td>Catalogo de usuarios</td>
  </tr>
  <tr>
    <td><table width="596" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="108">Nombre</td>
        <td width="187"><input id="txt_nom" class="k-textbox"/></td>
        <td width="81">A. Paterno</td>
        <td colspan="2"><input id="txt_apa" class="k-textbox"/></td>
      </tr>
      <tr>
        <td>Segundo Nombre</td>
        <td><input id="txt_snom" class="k-textbox"/></td>
        <td>A. Materno</td>
        <td colspan="2"><input id="txt_ama" class="k-textbox"/></td>
      </tr>
      <tr>
        <td>Password</td>
        <td><input id="txt_pass" class="k-textbox"/></td>
        <td>Usuario</td>
        <td colspan="2"><input id="txt_user" class="k-textbox" disabled/></td>
        </tr>
      <tr>
        <td></td>
        <td></td>
        <td><input type="button" class="k-button" name="nuevoUser" id="nuevoUser" value="Nuevo" onClick="openWinAddUser()"></td>
        <td width="90"><input type="button" class="k-button" name="guardar" id="guardar" value="Guardar" onClick="adminUsuarios('upUser')"></td>
        <td width="130"><input type="button" class="k-button" name="elimina" id="elimina" value="Eliminar" onClick="adminUsuarios('delUser')"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="595" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><div id="cnv_usuarios">
      <div id="dg_usuarios">
        </div>   
  </div></td>
  </tr>
  </table>
</td>
  </tr>
  <tr>
    <td><table width="673" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td colspan="2"><div id="cnv_user_permisos">
        <div id="dg_usr_perm">
      </div> 
  </div></td>
        <td colspan="2"><div id="cnv_permisos">
          <div id="dg_permisos">
            </div>
        </div></td>
      </tr>
      <tr>
        <td width="291">&nbsp;</td>
        <td width="51"><input type="button" class="k-button" name="quitar" id="quitar" value="Quitar" onClick="quitarPermiso()"/></td>
        <td width="59"><input type="button" class="k-button" name="asignar" id="asignar" value="Asignar" onClick="agregaPermiso()"/></td>
        <td width="291">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
   
</div>
<div id="alert"></div> 
  
<div id="windowAddUser"></div>

<div id="buttomBar">
  	<div class="console"/>
</div>
</form>
</body>
</html>