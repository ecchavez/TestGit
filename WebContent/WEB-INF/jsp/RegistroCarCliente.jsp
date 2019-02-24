<%@page import="mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<%
UsuarioDto user = (UsuarioDto) request.getSession().getAttribute("usrSession");
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
String idV=request.getParameter("idV");
String fromModulo=request.getParameter("from");
String isPopup=request.getParameter("isPopup");
isPopup = isPopup==null ? "" : isPopup;
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Registro de Cartera de Cliente</title>
    
    <script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
    <script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
    <script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js"></script>
    <script src="<%=basePath %>/js/jquery.blockUI.js"></script>
    <script src="<%=basePath %>/kendo/js/console.js"></script>   

    <script type="text/javascript">
            var contexPath="<%=basePath %>";
     </script> 
     <script src="<%=basePath %>/js/escritorio/EscritorioJQ.js"></script>  
     
     <script src="<%=basePath %>/js/clientes/ClientesJQ.js"></script>
	 <script src="<%=basePath %>/js/utils.js"></script>
     
         
    <script>
	var open_menu=0;
    var vendedorActualIndex = 0;
    var totalVendedoresSize = 0;
    var listaReasignaVendedor = new Array();
    var parametros = "";
    var ddl_contacto;
    
    $(document).ready(function() {
    	 var winAvisaClienteMenorEdadx = $("#winAvisaClienteMenorEdadCar");
   		
   		if (!winAvisaClienteMenorEdadx.data("kendoWindow")) {
   			winAvisaClienteMenorEdadx.kendoWindow({
 					        height: "110px",
 					        title: "Aviso de edad actual",
 					        modal: true,
 					        visible: false,
 					        width: "350px"
 				        });
     				}
   		
	    $("#mensajes_main").html('<%=request.getParameter("paramMensaje")==null?"":request.getParameter("paramMensaje")%>');
		$("#sample-bar").jixedbar({    
			showOnTop: true,    
			transparent: true,    
			opacity: 0.5,    
			slideSpeed: "slow",    
			roundedCorners: false,    
			roundedButtons: false,    
			menuFadeSpeed: "slow",    
			tooltipFadeSpeed: "fast",    
			tooltipFadeOpacity: 0.5
		});
		
       
  		
		
		    //Seccion de clientes
    $("#fch_ncm").kendoDatePicker({
 			format: "dd/MM/yyyy",
 			width: 20,
 			min: new Date(1, 0, 1900)
		});
    ddl_contacto=$("#cmb_viaContacto").kendoDropDownList({
	        dataTextField: "via_conx",
	        dataValueField: "via_con",
	        optionLabel: " "
	    });	 
	    $("#cmb_vendedor").kendoDropDownList({
	        dataTextField: "usuario",
	        dataValueField: "id_ut_sup",
	        //select : onSelectVendedor,
	        optionLabel: " "
	    });	 

	    $("#cmb_vendedor").data("kendoDropDownList").enable(false);
	    $("#cmb_motivos").kendoDropDownList({
	        dataTextField: "idMotivoReax",
	        dataValueField: "idMotivoRea",
	        select : onSelectVendedor,
	        optionLabel: " "
	    });	 
		
		//si viene de detalle de cat carrtera de clientes 
		if(window.parent.dataItemCar!=null && window.parent.mod=="det")
		{
			$('#nombre1C').val(window.parent.dataItemCar.nombre1).attr('disabled', 'true');
		    $('#nombre2C').val(window.parent.dataItemCar.nombre2).attr('disabled', 'true');
			$('#app_patC').val(window.parent.dataItemCar.app_pat).attr('disabled', 'true');
			$('#app_matC').val(window.parent.dataItemCar.app_mat).attr('disabled', 'true');
			var datePicker = $("#fch_ncm").data("kendoDatePicker");
		   datePicker.value(window.parent.dataItemCar.fch_ncm);
		   datePicker.enable(false);
			$('#telfnC').val(window.parent.dataItemCar.telfn).attr('disabled', 'true');
			$('#tlfmoC').val(window.parent.dataItemCar.tlfmo).attr('disabled', 'true');
			$('#telofC').val(window.parent.dataItemCar.telof).attr('disabled', 'true');
			$('#extncC').val(window.parent.dataItemCar.extnc).attr('disabled', 'true');
			$('#mail1C').val(window.parent.dataItemCar.mail1).attr('disabled', 'true');
			$('#mail2C').val(window.parent.dataItemCar.mail2).attr('disabled', 'true');
			getCargaInicial(window.parent.dataItemCar.via_con,'<%=idV%>');
			loadVendedorConsulta();
			$("#cmb_viaContacto").data("kendoDropDownList").enable(false);
			$("#cmb_vendedor").data("kendoDropDownList").enable(false);
			$("#areaCmbMotivos").attr("style", "visibility: hidden");
			llenaVisitas();
		}else if(window.parent.dataItemCar!=null && window.parent.mod=="modificaCartera") // si va a actualizar 
		{
			//alert("2");
			//loadMotivos();
			$('#nombre1C').val(window.parent.dataItemCar.nombre1);
		    $('#nombre2C').val(window.parent.dataItemCar.nombre2);
			$('#app_patC').val(window.parent.dataItemCar.app_pat);
			$('#app_matC').val(window.parent.dataItemCar.app_mat);
			var datePicker = $("#fch_ncm").data("kendoDatePicker");
		   datePicker.value(window.parent.dataItemCar.fch_ncm);
			$('#telfnC').val(window.parent.dataItemCar.telfn);
			$('#tlfmoC').val(window.parent.dataItemCar.tlfmo);
			$('#telofC').val(window.parent.dataItemCar.telof);
			$('#extncC').val(window.parent.dataItemCar.extnc);
			$('#mail1C').val(window.parent.dataItemCar.mail1);
			$('#mail2C').val(window.parent.dataItemCar.mail2);
			getCargaInicial(window.parent.dataItemCar.via_con,'<%=idV%>');
			loadMotivosModifica();
			$("#cmb_vendedor").data("kendoDropDownList").enable(false);
			
		}else if(window.parent.dataItemCotizacion!=null )//si viene de detalle de cotizaciones 
		{
			$('#nombre1C').val(window.parent.dataItemCotizacion.nombre).attr('disabled', 'true');
		    $('#nombre2C').val(window.parent.dataItemCotizacion.snombre).attr('disabled', 'true');
			$('#app_patC').val(window.parent.dataItemCotizacion.aapat).attr('disabled', 'true');
			$('#app_matC').val(window.parent.dataItemCotizacion.amat).attr('disabled', 'true');
			var datePicker = $("#fch_ncm").data("kendoDatePicker");
		    datePicker.value(window.parent.dataItemCotizacion.fchNac);
		    datePicker.enable(false);
			$('#telfnC').val(window.parent.dataItemCotizacion.telfn).attr('disabled', 'true');
			$('#tlfmoC').val(window.parent.dataItemCotizacion.tlfmo).attr('disabled', 'true');
			$('#telofC').val(window.parent.dataItemCotizacion.telof).attr('disabled', 'true');
			$('#extncC').val(window.parent.dataItemCotizacion.extnc).attr('disabled', 'true');
			$('#mail1C').val(window.parent.dataItemCotizacion.mail).attr('disabled', 'true');
			$('#mail2C').val(window.parent.dataItemCotizacion.mail2).attr('disabled', 'true');
			getCargaInicial(window.parent.dataItemCotizacion.via_con,window.parent.dataItemCotizacion.vendedor);
			loadVendedorConsulta();
			$("#cmb_viaContacto").data("kendoDropDownList").enable(false);
			$("#cmb_vendedor").data("kendoDropDownList").enable(false);
			$("#areaButtonAdd").attr("style", "visibility: hidden");
			$("#areaButtonUp").attr("style", "visibility: hidden");
		}
		else
		{
			getCargaInicial("","");
			loadMotivos();
		}
	 //fin de la seccion
		
    });
    

					
		function valida_openclose()
		{
			if(open_menu==0)
			{
				open_menu=1;
				var cssObj = {      
				'left':'0px'    
				}    
				$("#my-scrollable-div").css(cssObj);
			}
			else
			{
				open_menu=0;
				var cssObj = {      
				'left':'-200px'    
				}    
				$("#my-scrollable-div").css(cssObj);
			}
				
		}

		function closenOnBlur()
		{
			ddl_contacto.close();
		}
</script>
    <link href="<%=basePath %>/jixedbar/default/jx.stylesheet.css" type="text/css"  rel="stylesheet" />
	<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />
     

</head>

<body>


<%if(!isPopup.equals("SI")){ %>

<table width="1180" height="819" border="0" align="center" cellpadding="0" cellspacing="0">
   
   <tr>
    <td width="1180" height="102" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="0"><input type="image" name="imageField5" id="imageField5" src="<%=basePath %>/images/images/pages/header_left.png" /></td>
        <td width="211" background="<%=basePath %>/images/images/pages/header_logo.png"><table border="0" cellspacing="0" cellpadding="0" width="115">
          <tr>
            <td colspan="2"></td>
            </tr>
          <tr>
            <td width="1" align="center">&nbsp;</td>
            <td width="116" align="center"><div class="fadein" id="slaimg"></div></td>
          </tr>
          <tr>
            <td height="5" colspan="2"></td>
          </tr>
        </table></td>
        <td width="100%" background="<%=basePath %>/images/images/pages/header_middle.png"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100%" height="31">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="141" height="25" id="TituloMain">Gestion de Vivienda: </td>
                <td width="424" id="TituloModulo">Registro de Cartera de Cliente</td>
                <td width="276" id="TituloModulo"><table width="200" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td width="100%" align="center"><%= user.getId_ut_sup_cm().length()>=21 ? user.getId_ut_sup_cm().substring(0,20):user.getId_ut_sup_cm() %></td>
                        <td width="0" align="center"><%= user.getId_ut_sup() %></td>
                      </tr>
                    </table></td>
                  </tr>
                </table></td>
                <td width="79" id="TituloMain">Bienvenido:  </td>
                <td width="17" id="TituloModulo"><%= user.getUsuario() %></td>
                <td width="64" align="center"><input type="image" name="imageField8" id="imageField8" src="<%=basePath %>/images/exitr32.png" alt="SALIR" onclick="salirSistema()"></td>
                </tr>
            </table>
           </td>
            </tr>
          <tr>
            <td height="22">&nbsp;</td>
            </tr>
          <tr>
            <td height="32"><table width="95%" height="16" border="0" align="center" cellpadding="0" cellspacing="0">
            <%if(fromModulo.equals("agregaCartera") || fromModulo.equals("modificaCartera") ){ %>		
              <tr>
                <td width="65"><a class="k-button" id="idAceptar" onclick="adminCartCliente()">Aceptar</a></td>
                <td width="68"><input class="k-button"  type="button" name="reasignarVendedor" id="reasignarVendedor" value="Reasignar" onclick="reasignarVendedor()"/></td>
                <td width="65">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="127">&nbsp;</td>
                </tr>
              <%} %>  
            </table></td>
            </tr>
        </table></td>
        <td width="0"><input type="image" name="imageField6" id="imageField6" src="<%=basePath %>/images/images/pages/header_right.png" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="540" valign="top"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="0" height="16"><input type="image" name="imageField" id="imageField" src="<%=basePath %>/images/images/pages/main_corner4.png" /></td>
        <td width="1150" background="<%=basePath %>/images/images/pages/main_corner4_comp.png"></td>
        <td width="0"><input type="image" name="imageField4" id="imageField4" src="<%=basePath %>/images/images/pages/main_corner3.png" /></td>
      </tr>
      <tr>
        <td height="634" background="<%=basePath %>/images/images/pages/main_corner2_comp.png"></td>
        <td background="<%=basePath %>/images/images/pages/main_relleno.png" valign="top">
        <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td height="612" valign="top">                      
                         <div id="area_trabajo">
                         
                         <%} %>
                             	<form name="carteraC" id="carteraC" method="post" action="">
								
									<input type="hidden" id="fromCartera" value="<%=fromModulo%>" />
									<input type="hidden" id="ultVendedor" value="<%=idV%>" />
						   	              <table width="905" border="0" align="left">
									                <tr>
								                      <td width="120"> 
														*Nombre 
													  </td>
													  <td width="135"><input type="text" id="nombre1C" name="nombre1C" onkeyup="this.value=this.value.toUpperCase();" class="k-textbox" maxlength="30" onKeyPress="return captureNombres(event);" />
													  </td>
													  <td width="120">Segundo Nombre </td>
													  <td width="136"><input type="text" onkeyup="this.value=this.value.toUpperCase();" id="nombre2C" name="nombre2C"
																		class="k-textbox" maxlength="30" onKeyPress="return captureNombres(event);" />
													  </td>
                                  	      			  <td width="111">*Apellido</td>
													  <td width="130"><input type="text" id="app_patC" name="app_patC" onkeyup="this.value=this.value.toUpperCase();"
																		class="k-textbox" maxlength="30" onKeyPress="return captureNombres(event);" />
													  </td>
												   </tr>
					                               <tr>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                  <td>&nbsp;</td>
					                                  <td>&nbsp;</td>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                               </tr>
													<tr>
													<td>*Segundo Apellido </td>
													<td><input type="text" id="app_matC" name="app_matC" onkeyup="this.value=this.value.toUpperCase();"
																							class="k-textbox" maxlength="30" onKeyPress="return captureNombres(event);" /></td>
													<td>*Fecha Nacimiento</td>
													<td><input name="fch_ncm" id="fch_ncm"
																							style="width: 150px;" /></td>
													<td>*V&iacute;a de contacto</td>
													
    												<td><input name="cmb_viaContacto" id="cmb_viaContacto" onblur="closenOnBlur()"/>
													  </td>
													</tr>
					                                <tr>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                  <td>&nbsp;</td>
					                                  <td>&nbsp;</td>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                </tr>
													<tr>
													<td>Telefono casa </td>
													<td><input id="telfnC" name="telfnC" maxlength="10" type="text" onkeyup="this.value=this.value.toUpperCase();"
																							class="k-textbox" onKeyPress="return captureEnteros(event);" /></td>
													<td>Telefono celular</td>
													<td><input id="tlfmoC" name="tlfmoC" maxlength="10" type="text" onkeyup="this.value=this.value.toUpperCase();"
																							class="k-textbox" onKeyPress="return captureEnteros(event);" /></td>
													<td>Telefono oficina</td>
													<td><input id="telofC" maxlength="10" name="telofC" type="text" onkeyup="this.value=this.value.toUpperCase();"
																							class="k-textbox" onKeyPress="return captureEnteros(event);" /></td>
													</tr>
					                                <tr>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                  <td>&nbsp;</td>
					                                  <td>&nbsp;</td>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                </tr>
													<tr>
													<td>Extensi&oacute;n </td>
													<td><input id="extncC" name="extncC" maxlength="6" onkeyup="this.value=this.value.toUpperCase();"
																							class="k-textbox" onKeyPress="return captureEnteros(event);"/></td>
													<td>Correo 1</td>
													<td><input id="mail1C" name="mail1C" class="k-textbox" type="text" /></td>
													<td>Correo 2 </td>
													<td><input id="mail2C" name="mail2C" class="k-textbox" type="text" /></td>
													</tr>
					                                <tr>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                  <td>&nbsp;</td>
					                                  <td>&nbsp;</td>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                </tr>
													<tr>
													<td>*Vendedor</td>
													<td colspan="2"><select name="cmb_vendedor" id="cmb_vendedor" onkeyup="this.value=this.value.toUpperCase();">
													  </select></td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													</tr>
					                                <tr>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                  <td>&nbsp;</td>
					                                  <td>&nbsp;</td>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                </tr>
													<tr id="areaCmbMotivos">
													<td>Motivos</td>
													<td>
														<select name="cmb_motivos" id="cmb_motivos" onkeyup="this.value=this.value.toUpperCase();">
													    </select>
													</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													<td>&nbsp;</td>
													</tr>
					                                <tr>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                  <td>&nbsp;</td>
					                                  <td>&nbsp;</td>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                  <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					                                </tr>
													<% System.out.println(fromModulo); %>
													<%if(fromModulo.equals("det")){ %>
													<tr>
													<td colspan="6"><div id="gridVisitas" style="width:880px"></div></td>
													</tr>
													<%} %>
								
								
								<%if(isPopup.equals("SI") && fromModulo.equals("modificaCartera")){ %>
									<tr>
									<td colspan="6" align="left">
									                 <a class="k-button" id="idAceptar" onclick="adminCartCliente()">Aceptar</a>&nbsp;&nbsp;&nbsp;
									                 <a class="k-button" id="idReasignarAceptar" onclick="reasignarVendedor()">Reasignar</a>
									</td>
									</tr>
									<tr>
									<td colspan="6" align="left"> <div id="mensajes_main2">Mensajes de la aplicacion</div></td>
									</tr>
									<tr>
									<td colspan="6" align="left"></td>
									</tr>
									<tr>
									<td colspan="6" align="left"></td>
									</tr>
								
								<%} %>
								
								<tr>
								  <td colspan="6" align="right">
								    <table align="left">
									</table>
								  </td>
								</tr>
								
								</table>
								<div id="windowConfirmaReasignacion"></div>
								 
								</form>
                         </div>
                         
                         <%if (!isPopup.equals("SI")){ %>
                      </td>
                    </tr>
          </table></td>
        <td background="<%=basePath %>/images/images/pages/main_corner3_comp.png"></td>
      </tr>
      <tr>
        <td height="0"><input type="image" name="imageField2" id="imageField2" src="<%=basePath %>/images/images/pages/main_corner1.png" /></td>
        <td background="<%=basePath %>/images/images/pages/main_corner1_comp.png"></td>
        <td><input type="image" name="imageField3" id="imageField3" src="<%=basePath %>/images/images/pages/main_corner2.png" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="19">&nbsp;</td>
  </tr>
</table>
<div id="sample-bar" >
   <ul>
      <li title=""><div id="base_estatus" style="width:17px; height:20px; vertical-align:middle">>>></div></li>
   </ul>
   <span class="jx-separator-left"></span>
   <div id="mensajes_main">Mensajes de la aplicacion</div>
   <span class="jx-separator-right"></span>
</div>
<div id="my-scrollable-div" style="position:fixed; top:100px; left:-200px;">
<div id="my-scrollable-div_int" style="position:absolute; top:-5.7px; left:200px;">
<input name="" type="image" src="<%=basePath %>/images/images/pages/flecha_menu.png" onclick="valida_openclose()"/>
</div>
	<table border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>       
            <div id="div_menu" style="width:200px">
            	<ul id="menu">
                	
                </ul>
            </div>     
        </td>
      </tr>
    </table>   
</div>
<%}%>
<div id="winAvisaClienteMenorEdadCar">
   <table width="295" border="0">
    <tr>
      <td colspan="2" align="center"><b><label id="AnyoActualCliente"></label></b></td>
    </tr>
    <tr>
      <td colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td align="center">
          <input class="k-button" type="button" name="button" id="button" value="Regresar" onClick="cerrarVentanaAvisoMenorEdadx();">
      </td>
      <td>
         <input class="k-button" type="button" name="button" id="button" value="Continuar" onClick="continuaGuardado();">
      </td>
    </tr>
    <tr>
      <td colspan="2">&nbsp;</td>
    </tr>
    </table>
</div>
</body>
</html>