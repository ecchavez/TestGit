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
<title>Cancelaciones Parciales</title>

<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>
<script src="<%=basePath %>/js/utils.js"></script>

<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/usuarios/UsuariosJQ.js"></script>
<script src="<%=basePath %>/js/cierreVenta/monitorCotPedJQ.js"></script>
<script src="<%=basePath %>/js/digitalizacion/DigitalizacionJQ.js"></script>
<script>
	$(document).ready(function(){	
		var equipo_add=window.parent.dataItemCotizacion.cotizacionSubequipos;
		var solo_equipo_add=[];
		
			
		var  subEquipoDS = new kendo.data.DataSource({		     
		     pageSize: 50
		  });
		 
		  $("#dg_subequipos").kendoGrid({							  
			  filterable: false,
			  height: 150,
			  sortable: false,				  
			  pageable: false,
	          resizable: true,
	          scrollable: true,
	          selectable: "row",
	          close: onCloseWinSubEquiposCancel,
	          dataSource: subEquipoDS										  
		 });	
		  
		for(var i=0; i<equipo_add.length; i++)
		{
			var precio_eq_add=parseFloat(equipo_add[i].netwr);
			if((equipo_add[i].tipo=="E" || equipo_add[i].tipo=="B") && precio_eq_add>=1)
			{
				solo_equipo_add.push(equipo_add[i]);
			}			
		}
		
		additemsSubEquipo(solo_equipo_add);
		
		<% 
			if(request.getParameter("from").equals("traspaso"))
			{
		%>
			initCombos();
		<% 
			}				
		%>
	});
	
	function onChangeEquiposCancel()
	{
		var dataItemEquipo=[];
		var gridEquCanel = $("#dg_subequipos").data("kendoGrid");    
		dataItemEquipo = gridEquCanel.dataItem(gridEquCanel.tbody.find("tr.k-state-selected"));
	}
	
	function additemsSubEquipo(datos_sei)
	{
		if(datos_sei.length>=1)
		{
			$("#dg_subequipos tbody").html("");
			for(var i=0; i<datos_sei.length; i++)
			{			
				row_item="";
				row_item+="<tr>";
				    if(parseFloat(datos_sei[i].netwr)>=1 && (datos_sei[i].tipo=="E" || datos_sei[i].tipo=="B"))
				    {
				    	row_item+="<td width='13px' align='left'><input type='checkbox' id='chk_sequ"+i+"' name='chk_sequ"+i+"' value='"+datos_sei[i].id_equnr+"' onclick='window.parent.seleccionaSubequipo("+i+",this)'/></td>";				    
			    	    row_item+="<td width='24px'>"+datos_sei[i].tipo+"</td>";
			    	    row_item+="<td width='127px'>"+datos_sei[i].id_equnrx+"</td>";
			    		row_item+="<td width='81px'>"+parseFloat(datos_sei[i].netwr).toFixed(2)+"</td>";
			    		row_item+="<td width='110px'>"+datos_sei[i].id_equnr+"</td>";
				    }
		    	row_item+="</tr>";

		    	$("#dg_subequipos tbody").append(row_item);
			}
		}
	}
	
	function setCancelaEquipos(accion)
	{	
		<% 
		if(request.getParameter("from").equals("traspaso"))
			{
		%>
				var datosEquiposListCancel = $("#cmb_equipo_cancel").data('kendoComboBox');
		        var itemEquiposCancel = datosEquiposListCancel.dataItem();
		        window.parent.equ_ct=itemEquiposCancel.id_equnr;
		<%
			}
		%>		
		window.parent.openCotizadorFromPagoInicial(accion);
		window.parent.closeWinCancel();	
	}
	
	function initCombos()
	{
		$("#cmb_faces_cancel").kendoComboBox({
				dataTextField: "text",
				dataValueField: "id",
				dataSource: [],
				filter: "contains",
				change: onChangeUTSFacesTraspaso,
				suggest: true
		});
		$("#cmb_equipo_cancel").kendoComboBox({
			dataTextField: "id_equnrx",
			dataValueField: "id_equnr",
			dataSource: [],
			filter: "contains",
			change: onChangeUTSequipoTraspaso,
			suggest: true
		});	
		getFasesCancelaciones("faces");
	}
	
	function validaForma()
	{
		<% 
			if(request.getParameter("from").equals("traspaso"))
			{
		%>
				var datosFaseListCancel = $("#cmb_faces_cancel").data('kendoComboBox');
		        var itemFasesCancel = datosFaseListCancel.dataItem();
				var datosEquiposListCancel = $("#cmb_equipo_cancel").data('kendoComboBox');
		        var itemEquiposCancel = datosEquiposListCancel.dataItem();
		<%
			}
		%>
	}
	
</script>

<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />


</head>
<body>
  <table width="503" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="2">&nbsp;</td>
    </tr>
    <% 
	if(request.getParameter("from").equals("traspaso"))
	{
    %>
	    <tr>
	    <td colspan="2">Indique los Adicionales que desea conservar en su cotización</td>
	    </tr>
    <%
	}
	else
	{
    %>
    	<tr>
	    <td colspan="2">Indique los Adicionales que desea cancelar en su cotización</td>
	    </tr>
    <%
	}
    %>
    <tr>
    <td colspan="2">&nbsp;</td>
    </tr>
  <tr>
    <td colspan="2"><table width="494" id="dg_subequipos" style="width:500px">
                <thead>
                    <tr>                   
                        <th data-field="" width="10px">&nbsp;</th>
                        <th data-field="tipo" width='20px'>Tipo</th>
                        <th data-field="id_equnrx" width='110px'>Descripcion</th>
                        <th data-field="netwr" width='70px'>Precio</th>
                        <th data-field="id_equnr" width='110px'>Equipo</th>
                    </tr>
                </thead>
                <tbody>                   
                </tbody>
            </table></td>
    </tr>
 <% 
	if(request.getParameter("from").equals("traspaso"))
	{
 %>
  <tr>
    <td colspan="2">
    	<table width="113%" border="0" align="left" cellpadding="0" cellspacing="0">
    	    <tr>
    	    	<td colspan="4">&nbsp;</td>
            </tr>  
             <tr>
    	    	<td colspan="4">&nbsp;</td>
            </tr>  
             <tr>
    	    	<td colspan="4">Seleccione el inmueble al que se quiere traspasar  </td>
            </tr>   
            <tr>
    	    	<td colspan="4">&nbsp;</td>
            </tr>           	    
            <tr>              
              <td width="9%">Fase</td>
              <td width="36%"><div id="cmb_faces_cancel" style="width:150px;"> </div></td>
              <td width="16%">Casa/Depto</td>
              <td width="32%"><div id="cmb_equipo_cancel" style="width:150px;"> </div></td>
            </tr>
        </table>
    </td>
  </tr>
  <%
	}
  %>
  <tr>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="74"><% if(request.getParameter("from").equals("traspaso")) { %><input type="button" name="btnok" id="button" value="Traspasar" class="k-button" onclick="setCancelaEquipos('traspasar')"><% } else { %><input type="button" name="btnok" id="button" value="Cotizar" class="k-button" onclick="setCancelaEquipos('cancelar')"><% } %></td>
    <td width="400"><input type="button" name="btncancel" id="btncancel" value="Cancelar" class="k-button" onclick="window.parent.closeWinCancel()"></td>
  </tr>
</table>
  <div id="mensajes_main"></div>  
</body>
</html>