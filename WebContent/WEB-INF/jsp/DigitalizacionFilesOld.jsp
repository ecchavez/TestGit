<%@page import="mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
UsuarioDto user = (UsuarioDto) request.getSession().getAttribute("usrSession");
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 

%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cargar archivos</title>

<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>
<script src="<%=basePath %>/js/utils.js"></script>

<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/digitalizacion/DigitalizacionJQ.js"></script>

<script>

	$(document).ready(function(){
		$("#cmb_faces").kendoComboBox({
			dataTextField: "id",
			dataValueField: "id",
			dataSource: [],
			filter: "contains",
			change: onChangeUTSFaces,
			suggest: true
		});
		$("#cmb_equipo").kendoComboBox({
			dataTextField: "id_equnr",
			dataValueField: "id_equnr",
			dataSource: [],
			filter: "contains",
			change: onChangeUTSequipo,
			suggest: true
		});
		$("#cmb_tipos").kendoComboBox({
			dataTextField: "id_ticket_areax",
			dataValueField: "id_ticket_area",
			dataSource: [],
			filter: "contains",
			change: onChangeUTSTipos,
			suggest: true
		});
		$("#cmb_subtipos").kendoComboBox({
			dataTextField: "id_ticket_areax",
			dataValueField: "id_ticket_area",
			dataSource: [],
			filter: "contains",
			change: onChangeUTSubSTipos,
			suggest: true 
		});
		$("#cmb_estatus").kendoComboBox({
			dataTextField: "id_ticket_statx",
			dataValueField: "id_ticket_stat",
			dataSource: [],
			filter: "contains",
			suggest: true,
			index: 3
		});
		
		getFasesDigit("faces");
						
	});
	
	function validaforma()
	{
		var validado=false;
		var datosTiposList = $("#cmb_tipos").data('kendoComboBox');
		var itemTipos = datosTiposList.dataItem();
		var datosSubTiposList = $("#cmb_subtipos").data('kendoComboBox');
		var itemSubTipos = datosSubTiposList.dataItem();
		var datosEstatusList = $("#cmb_estatus").data('kendoComboBox');		
		var itemEstatus = datosEstatusList.dataItem();
		
		if($("#cmb_faces").val() == "" || $("#cmb_faces").val() == undefined)
		{
			alert("Seleccione una fase");
		}
		else if($("#cmb_equipo").val() == "" || $("#cmb_equipo").val() == undefined)
		{
			alert("Seleccione una casa o depto");
		}			
		else if($("#cmb_tipos").val() == "" || $("#cmb_tipos").val() == undefined)
		{
			alert("Seleccione un tipo");
		}
		else if($("#cmb_subtipos").val() == "" || $("#cmb_subtipos").val() == undefined)
		{
			alert("Seleccione una carpeta");	
		}		
		else if($("#fileData").val() == "" || $("#fileData").val() == undefined)
		{
			alert("Seleccione un archivo pdf");
		}		
		else
		{				 
			validado=true;
		}
		
		if(validado)
		{
			if($("#cmb_estatus").val() == "" || $("#cmb_estatus").val() == undefined)
			{
				$("#datos").val($("#cmb_faces").val()+"|"+$("#cmb_equipo").val()+"|"+itemTipos.id_ticket_area+"|"+itemTipos.id_ticket_file+"|"+itemSubTipos.id_ticket_area+"|"+itemSubTipos.id_ticket_file+"| | ");
			}	
			else
			{
				$("#datos").val($("#cmb_faces").val()+"|"+$("#cmb_equipo").val()+"|"+itemTipos.id_ticket_area+"|"+itemTipos.id_ticket_file+"|"+itemSubTipos.id_ticket_area+"|"+itemSubTipos.id_ticket_file+"|"+itemEstatus.id_ticket_stat+"|"+itemEstatus.id_ticket_statx);
			}
			
			$("#accion").val("guardar");
			document.formulario.submit();
		}
	}

</script>

<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />


</head>
<body>
<form name="formulario" method="post" enctype="multipart/form-data" action="<%=basePath %>/RespuestaDigitalizacionFile.htm">
  <table width="761" height="218" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="761" height="21">&nbsp;</td>
    </tr>
    <tr>
      <td height="118"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      
        <tr>
          <td height="104" colspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="105">Desarrollo</td>
              <td width="180"><div id="txt_uts"><%= user.getId_ut_sup() %></div></td>
              <td width="232"><div id="txt_uts_desc"><%= user.getId_ut_sup_cm() %></div></td>
            </tr>
            <tr>
              <td>Fase</td>
              <td><div id="cmb_faces" style="width:150px;"> </div></td>
              <td><div id="txt_desc_fase"></div></td>
            </tr>
            <tr>
              <td>Casa/Depto</td>
              <td><div id="cmb_equipo" style="width:150px;"> </div></td>
              <td><div id="txt_desc_equ"></div></td>
            </tr>
            <tr>
              <td colspan="3">&nbsp;</td>
            </tr>
            <tr>
              <td height="27" colspan="3"><input type="file" id="fileData" name="fileData" class="k-button"/></td>
            </tr>
          </table></td>
          <td width="300" valign="top">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="244"><div id="cmb_tipos" style="width:180px;"> </div></td>
                </tr>
              <tr>
                <td><div id="cmb_subtipos" style="width:180px;"> </div></td>
                </tr>
              <tr>
                <td><div id="cmb_estatus" style="width:180px;"> </div></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td>&nbsp;</td>
              </tr>
            </table>            </td>
        </tr>
        <tr>
          <td height="19" colspan="3">&nbsp;</td>
        </tr>
      </table></td>
    </tr>
    <tr>
      <td height="2"></td>
    </tr>
    <tr>
      <td height="19">&nbsp;</td>
    </tr>
    <tr>
      <td height="19">&nbsp;</td>
    </tr>
    <tr>
      <td height="19"><table width="95" border="0" align="left" cellpadding="0" cellspacing="0">
        <tr>
          <td width="55">
            <input type="button" name="guardar" id="guardar" value="Guardar" class="k-button" onClick="validaforma()"></td>
          <td width="69">
            <input type="button" name="cancelar" id="cancelar" value="Cancelar" class="k-button" onClick="window.parent.closeWindowUser()"></td>
        </tr>
      </table></td>
    </tr>
  </table>
  <input type="hidden" id="datos" name="datos">
  <input type="hidden" id="accion" name="accion">
  <div id="buttomBar">
  	<div class="console"/>
</div>
</form>
</body>
</html>