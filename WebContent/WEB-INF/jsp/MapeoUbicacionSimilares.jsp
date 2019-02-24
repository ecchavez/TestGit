<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Users using ajax</title>

<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>
<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/ubicaciones/UbicacionesJQ.js"></script>
<script>
	var ds_ut=window.parent.ddlint.dataSource.data();
	
	$(document).ready(function(){
        
	    $("#cmb_ubicaciones_sup").kendoDropDownList({	    		
		     	dataTextField: "ubicacionDescripcion",
		     	dataValueField: "ubicacionClave",
		     	dataSource: ds_ut
	 	});
		
		$("#dg_ubicaciones").kendoGrid({
								  height: 400,									  
								  sortable: true,								  
                  				  resizable: true,
                  				  scrollable: false,
								  dataSource: ds_ut,
								  columns: [
											{
												field: "ubicacionDescripcion",
				                                title: "Unidad Tecnica"
				                            },
				                            {
				                                field: "ubicacionClave",
				                                title: "ID ubicacion"
				                            }								
										],
				                  selectable: "multiple"							  
							  }); 
   

	});
	
	
</script>

<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />

	</head>
	<body>
	
<table width="585" height="272" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="161">Seleccione una unidad</td>
          <td width="35">&nbsp;</td>
          <td width="401">Seleccione los piso a replicar</td>
        </tr>
        <tr>
          <td><select name="cmb_ubicaciones_sup" id="cmb_ubicaciones_sup" style="width: 140px;"/></td>
          <td rowspan="17"></td>
          <td rowspan="17"><div id="dg_ubicaciones"></td>
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
        <tr>
          <td>&nbsp;</td>
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
        <tr>
          <td>&nbsp;</td>
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
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><input type="button" name="btn_replicar" id="btn_replicar" value="Replicar mapeo" onClick="setReplicaPisos()" class="k-button" /></td>
        </tr>
      </table>
        <div id="mensajes_main"></div>
</body></html>