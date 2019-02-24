<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
		String path = request.getContextPath();
		String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
		String fromR=request.getParameter("from"); 
		String conceptoReferencia=request.getParameter("conceptoReferencia");
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registro de Comprobantes de Pagos Mensuales</title>

<script src="<%=basePath%>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath%>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath%>/kendo/js/console.js"></script>
<script src="<%=basePath %>/kendo/js/cultures/kendo.culture.es-EC.min.js"></script>
<script src="<%=basePath %>/js/cierreVenta/monitorCotPedJQ.js"></script>
<script src="<%=basePath %>/js/cierreVenta/monitorPagosJQ.js"></script>
<script src="<%=basePath %>/js/utils.js"></script>
<script src="jquery-1.7.1.js"></script>
<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />
<script type="text/javascript">
	var contexPath="<%=basePath%>";
</script>
		<script src="<%=basePath%>/js/cierreVenta/monitorPagosJQ.js"></script>
		<script src="<%=basePath%>/js/utils.js"></script>
		<script>
		kendo.culture("es-EC");
		$(document).ready(function(){		
			$.ajaxSetup({async:false});
							
			 $("#fchPago").kendoDatePicker({
 			format: "dd/MM/yyyy",
 			min: new Date(1,0,1900)
			}); 

			 $('#idFaseEquipoXY').val(window.parent.$('#idFaseEquipoX').val());
			 //alert (window.parent.$('#idFaseEquipoX').val());
			 //alert ($('#idFaseEquipoXY').val());

			 //Declaración de dropdownlist para Concepto
			 $("#ddlConceptos").kendoDropDownList({
					optionLabel: "Seleccione un concepto ...",
					dataTextField: "valor",
			        dataValueField: "id_val",
			        index: 0
			 });
			 
			 //Instancia de dropdownlist para Concepto
		 	 ddlistConceptos = $("#ddlConceptos").data("kendoDropDownList");

		 	 //Declaración de dropdownlist para Medios
			 $("#ddlMedios").kendoDropDownList({
					optionLabel: "Seleccione un medio ...",
					dataTextField: "valor",
			        dataValueField: "id_val",
			        index: 0
			 });
			 //Instancia de dropdownlist para Medios
		 	 ddlistMedios = $("#ddlMedios").data("kendoDropDownList");	

		 	
		 	 setConceptos();
		 	 setMedios();

		 	 setReferencia();
		 	$.ajaxSetup({async:true});
		 	 
		});
		
	 	//Recupera Conceptos
			function setConceptos(){
				var idCatalogo = 'CONCEPTO';
				//Para llenar dropdownlist para Concepto
				ddlistConceptos.dataSource.data([]);
			    $.ajax({  
				    type: "POST",
				    async: false,
				    url:  "<%=basePath%>/ObtieneCatalogos.htm",  
				    data: "idCatalogo="+ idCatalogo,
				    success: function(response){
							if(response.mensaje == "SUCCESS"){
								var objCatalogosList= response.objCatalogosList;
								ddlistConceptos.dataSource.data(objCatalogosList);
							}else{
								//alert("No se encontraron datos");
								$("#mensajes_main").html("No se encontraron datos"); 
								$('#ddlConceptos').val("");
								ddlistConceptos.dataSource.data([]);
							}
				    },
				    error: function(e){  
					    	alert('Error: ' + e);  
				   	} 
				});
			}


		//Recupera Medios
		function setMedios(){
			var idCatalogo = 'MED_PAGO';
			//Para llenar dropdownlist para Medio
			ddlistMedios.dataSource.data([]);
		    $.ajax({  
			    type: "POST",
			    async:  false,
			    url:  "<%=basePath%>/ObtieneCatalogos.htm",  
			    data: "idCatalogo="+ idCatalogo,
			    success: function(response){
						if(response.mensaje == "SUCCESS"){
							var objCatalogosList= response.objCatalogosList;
							ddlistMedios.dataSource.data(objCatalogosList);
						}else{
							//alert("No se encontraron datos");
							$("#mensajes_main").html("No se encontraron datos"); 
							$('#ddlMedios').val("");
							ddlistMedios.dataSource.data([]);
						}
			    },
			    error: function(e){  
				    	alert('Error: ' + e);  
			   	} 
			});
		}	

		//Recupera referencia
		function setReferencia(){
			//alert('setReferencia('+ '<--%=conceptoReferencia%>' + ')');
			$('#refer').val("");
			$.ajax({  
				    type: "POST",
				    async:  false,
				    url:  "<%=basePath%>/ObtieneReferencia.htm",  
				    data: "conceptoReferencia="+ "<%=conceptoReferencia%>",
				    success: function(response){
							if(response.mensaje == "SUCCESS"){
								$('#refer').val(response.referencia);
								if($('#refer').val() == ""){
									$("#mensajes_main").html("No existe referencia para este equipo.");
								}
							}else{
								$('#refer').val("");
								$("#mensajes_main").html(response.descripcion);
							}
				    },
				    error: function(e){  
					    	alert('Error: ' + e);
					    	$("#mensajes_main").html(response.descripcion);  
				   	} 
			});
		}
		
</script>
	</head>
<body>
<form name="regPagos" id="regPagos" method="post" enctype="multipart/form-data" action="<%=basePath %>/DatosPago.htm">
<input type="hidden" id="fromDatos" name="fromDatos" value="<%= fromR%>" />
  
<table align="left">

<tr>
  <td width="54">Fecha Pago</td>
  <td width="149"><input id="fchPago" name="fchPago" class="k-textbox"/></td>
  <td width="149">&nbsp;</td>
  <td width="149">&nbsp;</td>
</tr>
<tr>
  <td width="54">Concepto</td>
  <td width="149"><input id="ddlConceptos" class="k-select" style="width: 150px"/></td>
</tr>
<tr>
  <td width="54">Medio de Pago</td>
  <td width="149"><input id="ddlMedios" class="k-select" style="width: 150px"/></td>
</tr>
<tr>
  <td>Monto </td>
  <td><input id="monto" name="monto" class="k-textbox" maxlength="15" onkeypress="return NumCheck(event, this)"/></td>
  <td></td>
  <td></td>

</tr>
<tr>
  <td>Referencia</td>
  <td><input id="refer" name="refer" onkeyup="this.value=this.value.toUpperCase();" class="k-textbox" maxlength="16" readonly="readonly" disabled/></td>
  <td></td>
  <td></td>

</tr>
<tr>
  <td>Folio Operaci&oacute;n</td>
  <td><input id="folioOper" name="folioOper" onkeyup="this.value=this.value.toUpperCase();" class="k-textbox" maxlength="15"/></td>
  <td></td>
  <td></td>
</tr>

<tr>
  <td>Archivo</td>
  <td colspan="3"><input type="hidden" id="archivo" name="archivo" value="NA" class="k-button" style="width: 250px" />
  <input type="file" id="fileData" name="fileData"  style="width: 300px"/></td>
</tr>

<tr>
  <td>Observaciones</td>
  <td><input id="observaciones" name="observaciones" onkeyup="this.value=this.value.toUpperCase();" class="k-textbox" size="60" style="width:300px" maxlength="132"/></td>
  <td></td>
  <td></td>
</tr>

<tr>
  <td colspan="6">&nbsp;<input type="hidden" id="idFaseEquipoXY" value="idFaseEquipoXY" name="" />    
    </td>
</tr>
<tr>
  <td>
     <input type="button" value="Aceptar " class="k-button" align="left" onclick="addPago()"/>

  </td>
  <td>
    <input type="button" value="Cancelar " class="k-button" align="left" onclick="cancelarAgregar()"/>
  </td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
</tr>
<tr>
   <td colspan="6">&nbsp;
    
   </td>
</tr>
<tr>
  <td colspan="6">	  <div id="mensajes_main">Mensajes de la aplicacion</div></td>
</tr>
</table>
 



</form>

</body>
</html>
