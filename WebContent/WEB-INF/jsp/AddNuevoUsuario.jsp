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
<title>Login (Area restringida)</title>

<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/js/utils.js"></script>

<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/usuarios/UsuariosJQ.js"></script>

<script>

	$(document).ready(function(){
					
		asignaPermisosUsers();
		
		$("#dg_usr_perm_add").kendoGrid({
				  dataSource: window.parent.permisos_user_add,
	    		  columns: [                            
                            {
                                field: "id_permiso",
                                width: 70,
                                title: "Permiso"
                            },
                            {
                                field: "id_permisox",
                                title: "Descripcion"
                            },
                           ],

				  change: onChangeUsrPerm,
				  height: 200,
				  selectable: "multiple",
				  sortable: true
		});
		
		$("#dg_permisos_add").kendoGrid({
		        dataSource: window.parent.dataPermisosAdd,
			    columns: [                            
                            {
                                field: "id_permiso",
                                width: 70,
                                title: "Permiso"
                            },
                            {
                                field: "id_permisox",
                                title: "Descripcion"
                            },
                        ],

			  change: onChangePermisos,
			  height: 200,
			  selectable: "multiple",
			  sortable: true
		});
		
	  $('#txt_nom').val(window.parent.itemUsuario.nombre1);
      $('#txt_snom').val(window.parent.itemUsuario.nombre2);
      $('#txt_apa').val(window.parent.itemUsuario.app_pat);
      $('#txt_ama').val(window.parent.itemUsuario.app_mat);
      $('#txt_user').val(window.parent.itemUsuario.usuario);
      $('#txt_pass').val(window.parent.itemUsuario.pass);
      $('#txt_tel').val(window.parent.itemUsuario.telefono);
      $('#txt_ext').val(window.parent.itemUsuario.extension);
      $('#txt_correo').val(window.parent.itemUsuario.correo);
		//adminUsuarios("getDataInit");	
		if(window.parent.accion_user=="nuevo")
		{
			$('#txt_user').removeAttr("disabled");
		}
		else
		{
			$('#txt_user').attr('disabled','true')
		}
		
	});
	
	function enviarusuario()
	{
		if(window.parent.accion_user=="nuevo")
		{
			adminUsuarios('addUser');
		}
		else
		{
			adminUsuarios('upUser');
		}
	}
	
	function asignaPermisosUsers()
	{
		var perm_=window.parent.dataPermisosAdd;
		var perm=window.parent.dataPermisosAdd;
		var permu=window.parent.permisos_user_add;
		var permu_=[];
		
		for(var i=0; i<perm.length; i++)
		{
			var encontrado=false;
			for(var j=0; j<permu.length; j++)
			{
				if(perm[i].id_permiso==permu[j].id_permiso)
				{
					encontrado=true;									
				}
			}
			if(!encontrado)
			{
				permu_.push(perm[i]);
			}
		}
		
		window.parent.dataPermisosAdd=permu_;		
	}
	
</script>

<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />


</head>
<body>
<form name="form1" method="post" action="">
  <table width="694" height="212" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="21" colspan="2">&nbsp;</td>
      </tr>
    <tr>
      <td height="85" colspan="2"><table width="684" border="0" align="center" cellpadding="0" cellspacing="0">
      
        <tr>
          <td width="90">Nombre</td>
          <td width="249"><input id="txt_nom" class="k-textbox" onKeyPress="return captureNombres(event)" onKeyUp="uc(this)" maxlength="50"/></td>
          <td width="172">Segundo Nombre</td>
          <td width="173"><input id="txt_snom" class="k-textbox" onKeyPress="return captureNombres(event)" onKeyUp="uc(this)" maxlength="50"/></td>
        </tr>
        <tr>
          <td>Apellido</td>
          <td><input id="txt_apa" class="k-textbox" onKeyPress="return captureNombres(event)" onKeyUp="uc(this)" maxlength="50"/></td>
          <td>Segundo Apellido</td>
          <td><input id="txt_ama" class="k-textbox" onKeyPress="return captureNombres(event)" onKeyUp="uc(this)" maxlength="50"/></td>
        </tr>
        <tr>
          <td>Usuario</td>
          <td><input id="txt_user" class="k-textbox" onKeyPress="return letrasnumeros(event)" onKeyUp="uc(this)"/></td>
          <td>Contrase&ntilde;a</td>
          <td><input id="txt_pass" class="k-textbox" type="password" onKeyPress="return letrasnumeros(event);"/></td>
        </tr>
        <tr>
          <td>Telefono</td>
          <td><input type="text" id="txt_tel" class="k-textbox" name="txt_tel" maxlength="10" onKeyPress="return isNumberKey(event)"></td>
          <td>Extension</td>
          <td><input type="text" id="txt_ext" class="k-textbox" name="txt_ext" maxlength="5" onKeyPress="return isNumberKey(event)"></td>
        </tr>
        <tr>
          <td>Correo</td>
          <td><input type="text" id="txt_correo" class="k-textbox" name="txt_correo" maxlength="100"></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </table></td>
      </tr>
    <tr>
      <td width="353" height="15"></td>
      <td width="341"></td>
      </tr>
    <tr>
      <td colspan="2"><table width="688" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="325"><div id="cnv_user_permisos">
        <div id="dg_usr_perm_add">
      </div> 
  </div></td>
          <td width="36"><table border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              <td><input type="button" onclick="agregaPermisoAdd('X')" value="<<" class="k-button" /></td>
            </tr>
            <tr>
              <td><input type="button" onclick="agregaPermisoAdd('')" value="< " class="k-button" /></td>
              </tr>
            <tr>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td><input type="button" onclick="quitarPermisoAdd('')" value="> " class="k-button" /></td>
            </tr>
            <tr>
              <td><input type="button" onclick="quitarPermisoAdd('X')" value=">>" class="k-button" /></td>
            </tr>
          </table></td>
          <td width="327"><div id="cnv_permisos">
          <div id="dg_permisos_add">
            </div>
        </div></td>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td height="19" colspan="2">&nbsp;</td>
      </tr>
    <tr>
      <td height="19" colspan="2"><table width="95" border="0" align="left" cellpadding="0" cellspacing="0">
        <tr>
          <td width="55">
            <input type="button" name="guardar" id="guardar" value="Guardar" onClick="enviarusuario()" class="k-button"></td>
          <td width="69">
            <input type="button" name="cancelar" id="cancelar" value="Cancelar" class="k-button" onClick="window.parent.closeWindowUser()"></td>
        </tr>
      </table></td>
    </tr>
  </table>
  <br>
  <div id="mensajes_main"></div>
</form>
</body>
</html>