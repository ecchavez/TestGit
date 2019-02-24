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
String origen= request.getParameter("origen");
String isPopup=request.getParameter("isPopup");
isPopup = isPopup==null ? "" : isPopup;
String paramCarteraCliente=request.getParameter("paramIdClienteCartera");
paramCarteraCliente = paramCarteraCliente==null ? "" : paramCarteraCliente;
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Registro de Clientes</title>
    
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
	<script src="<%=basePath %>/js/clientes/ClientesSAPJQ.js"></script>
	<script src="<%=basePath %>/js/contratos/ContratosJQ.js"></script>
	<script src="<%=basePath %>/js/cierreVenta/monitorPagosJQ.js"></script>
    <script src="<%=basePath %>/js/cierreVenta/monitorCotPedJQ.js"></script>
	<script src="<%=basePath %>/js/utils.js"></script>
     
         
    <script>
	var open_menu=0;
    
    $(document).ready(function() {
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
		
		var window = $("#winOptionConfirmar");
  		
  		if (!window.data("kendoWindow")) {
					        window.kendoWindow({
					        height: "120px",
					        title: "Confirmacion",
					        modal: true,
					        visible: false,
					        width: "350px"
				        });
    				}
    	
		    				
  		var windowBlock = $("#winOptionBloquear");
  		
  		if (!windowBlock.data("kendoWindow")) {
					        windowBlock.kendoWindow({
					        height: "185px",
					        title: "Procesando...",
					        modal: true,
					        visible: false,
					        width: "350px",
					        actions: [ ]
				        });
    				}
  		
  		var winAvisaClienteMenorEdadz = $("#winAvisaClienteMenorEdadCli");
  		
  		if (!winAvisaClienteMenorEdadz.data("kendoWindow")) {
  			winAvisaClienteMenorEdadz.kendoWindow({
					        height: "110px",
					        title: "Aviso de edad actual",
					        modal: true,
					        visible: false,
					        width: "350px"
				        });
    				}
  		
  		
		 $("#fch_ncm").kendoDatePicker({
 			format: "dd/MM/yyyy",
 			width: 20,
 			min: new Date(1, 0, 1900)
		});
				
		$("#paisC").kendoDropDownList({
	        dataTextField: "paisx",
	        dataValueField: "pais",
	        select : onSelectPaisC 
	    });	 
	    $("#estdoC").kendoDropDownList({
	        dataTextField: "regionx",
	        dataValueField: "region",
	        select:onSelectEdoC
	    });	 
	     $("#tratamientoC").kendoDropDownList({
	        dataTextField: "tratamientox",
	        dataValueField: "tratamiento",
	         optionLabel: " "
	    });	 
	     $("#sexoC").kendoDropDownList({
	        dataTextField: "sexox",
	        dataValueField: "sexo",
	         optionLabel: " "
	    });	 
	     $("#edo_civilC").kendoDropDownList({
	        dataTextField: "edocvlx",
	        dataValueField: "edocvl",
	         optionLabel: " "
	    });
	     $("#paisCo").kendoDropDownList({
	        dataTextField: "paisx",
	        dataValueField: "pais",
	        optionLabel: " "
	    });	 
	    $("#estdoCo").kendoDropDownList({
	        dataTextField: "regionx",
	        dataValueField: "region",
	        optionLabel: " "
	    });	 
	     $("#tratamientoCo").kendoDropDownList({
	        dataTextField: "tratamientox",
	        dataValueField: "tratamiento",
	         optionLabel: " "
	    });	 
	     $("#sexoCo").kendoDropDownList({
	        dataTextField: "sexox",
	        dataValueField: "sexo",
	         optionLabel: " "
	    });
	    
	    $("#fechaNacContacto").kendoDatePicker({
 			format: "dd/MM/yyyy",
 			width: 20,
 			min: new Date(1, 0, 1900)
		});
		
		if(window.parent.dataItemCotizacion!=null)
		{
		     buscaClienteSap(window.parent.dataItemCotizacion.idCliente);
		}
		
		if(window.parent.dataItem!=null)
		{    //Datos para el cliente onlyRead y viene de modulo cliente SAP
		     setDatos();
	    }
		else{
		 	cargaInit('<%=paramCarteraCliente%>');
		}
			
	      $("#tabstrip").kendoTabStrip({
	       dataSpriteCssClass: "transparent",
						animation:	{
							open: {
								effects: "fadeIn"
							}
						}
					
					});
		detailsTemplate = kendo.template($("#template").html());
		
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
</script>
    <link href="<%=basePath %>/jixedbar/default/jx.stylesheet.css" type="text/css"  rel="stylesheet" />
	<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />
     

</head>

<body topmargin="0">

                                 <form id="registroC" name="registroC" action="" method="post">
									 <input type="hidden" id="origen" name="origen" value="<%=origen%>">
									 <input type="hidden" id="paramIdClienteCartera" name="paramIdClienteCartera" value="<%=paramCarteraCliente%>">  
									 
									 <input type="hidden" id="nom1Cliente" name="nom1Cliente"/>
									 <input type="hidden" id="nom2Cliente" name="nom2Cliente"/>
									 <input type="hidden" id="primerApeCliente" name="primerApeCliente"/>
									 <input type="hidden" id="segundoApeCliente" name="segundoApeCliente"/>
									 <input type="hidden" id="fechaNacCliente" name="fechaNacCliente"/>
									 
									 <input type="hidden" id="nom1CO" name="nom1CO"/>
									 <input type="hidden" id="nom2CO" name="nom2CO"/>
									 <input type="hidden" id="primerApeCO" name="primerApeCO"/>
									 <input type="hidden" id="segundoApeCO" name="segundoApeCO"/>
									 <input type="hidden" id="fechaNacCO" name="fechaNacCO"/>
									 <input type="hidden" id="existeClienteSap" name="existeClienteSap"/>
									 <input type="hidden" id="idClienteSapExiste" name="idClienteSapExiste"/>
									 								
									      <table width="870" border="0" align="left" cellpadding="0" cellspacing="0">
									        <tr>
									          <td height="400">
									          <table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
									             <tr>
									                <td>
									                 <div >
													<div id="tabstrip">
														<ul>
															<li class="k-state-active">
																Datos Cliente
															</li>
															<li>
																Datos Representate Legal
															</li>
														</ul>
														<div id="cliente" style="background-color: transparent;">
														<table width="900" border="0">
														 <tr>
														    <td colspan="6" align="center" ><strong>Datos B&aacute;sicos Cliente</strong></td>
														    </tr>
														  <tr>
														    <td>*Persona</td>
														    <td><input type="radio" name="regimenFiscal" id="radioFisica" value="01" checked="checked" onclick="cambiaEtiquetaTipoPersona();">
													        Persona Fisica													       </td>
														    <td><input type="radio" name="regimenFiscal" id="radioMoral" value="02" onclick="cambiaEtiquetaTipoPersona();">
															Persona Moral</td>
														    <td>&nbsp;</td>
														    <td>*Tratamientos</td>
														    <td><select name="tratamientoC" id="tratamientoC">
													        </select></td>
														    </tr>
									                        <tr>
									                       	   <td>*Estado Civil</td>
									                           <td colspan="2"><select name="edo_civilC" id="edo_civilC">
									                           </select></td>
									                           <td>&nbsp;</td>
									                           <td>*Sexo</td>
									                           <td><select name="sexoC" id="sexoC">
								                               </select></td>
									                        </tr>
														  <tr>
														    <td>*Nombre</td>
														    <td colspan="2"><input id="nombre1C"  onblur="validaRFCCliente()" name="nombre1C" size="25" maxlength="30" class="k-textbox" onkeyup="this.value=this.value.toUpperCase();" onKeyPress="return captureNombres(event);"/></td>
														    <td>&nbsp;</td>
														    <td>Segundo Nombre</td>
														    <td><input id="nombre2C" onkeyup="this.value=this.value.toUpperCase();"  name="nombre2C" size="25" maxlength="30" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    </tr>
														  <tr>
														    <td><label id="idApellido">*Apellido</label></td>
														    <td colspan="2"><input id="app_patC" onblur="validaRFCCliente()" onkeyup="this.value=this.value.toUpperCase();" name="app_patC" size="25" maxlength="30" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    <td>&nbsp;</td>
														    <td><label id="idSegundoApellidoCliente" >*Segundo Apellido</label></td>
														    <td><input id="app_matC" onblur="validaRFCCliente()" onkeyup="this.value=this.value.toUpperCase();"  name="app_matC" size="25" maxlength="30" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    </tr>
														  <tr>
														    <td><label id="idFechaNacimiento">*Fecha Nacimiento</label></td>
														    <td colspan="2"><input id="fch_ncm" name="fch_ncm" style="width: 150px;" onblur="validaRFCCliente()" /></td>
														    <td>&nbsp;</td>
														    <td>&nbsp;</td>
														    <td>&nbsp;</td>
														    </tr>
														  <tr>
														    <td><label id="idIfeCliente">*IFE</label></td>
														    <td colspan="2"><input id="id_ifeC" onkeyup="this.value=this.value.toUpperCase();" name="id_ifeC" maxlength="13" class="k-textbox" onkeypress="return captureEnteros(event)"/></td>
														    <td>&nbsp;</td>
														    <td><label id="idPasaporteCliente">Pasaporte</label></td>
														    <td><input id="id_pasprtC" onkeyup="this.value=this.value.toUpperCase();" name="id_pasprtC" maxlength="9" class="k-textbox"/></td>
														    </tr>
														  <tr>
														    <td><Label id="idCurpCliente">*Curp</Label></td>
														    <td colspan="2"><input id="curpC" onkeyup="this.value=this.value.toUpperCase();" name="curpC" maxlength="18" class="k-textbox" onfocus="calcularfc()"/></td>
														    <td>&nbsp;</td>
														    <td>*RFC</td>
														    <td><input id="rfcC" onkeyup="this.value=this.value.toUpperCase();" name="rfcC" maxlength="13" class="k-textbox" onfocus="calcularRfcPersonaMoral();" /></td>
														    </tr>
														  <tr>
														    <td colspan="6" align="center" ><strong>Datos Direcci&oacute;n Cliente</strong></td>
														    </tr>
														  <tr>
														    <td>*Pais </td>
														    <td colspan="2"><select name="paisC" id="paisC">
														      </select></td>
														    <td>&nbsp;</td>
														    <td>*Regiones</td>
														    <td><select name="estdoC" id="estdoC">
														      </select></td>
														    </tr>
														  <tr>
														    <td>*Calle</td>
														    <td colspan="2"><input id="calleC" maxlength="30" onkeyup="this.value=this.value.toUpperCase();" name="calleC"  size="40" class="k-textbox" onKeyPress="return letrasnumeros(event);"/></td>
														    <td>&nbsp;</td>
														    <td colspan="2"><table width="219" border="0">
														      <tr>
														        <td>*No Ext</td>
														        <td><input id="noextC" onkeyup="this.value=this.value.toUpperCase();" name="noextC" maxlength="10" class="k-textbox" size="5" onKeyPress="return letrasnumeros(event);"/></td>
														        <td>No Int</td>
														        <td><input id="nointC" onkeyup="this.value=this.value.toUpperCase();" name="nointC" maxlength="10" class="k-textbox" size="5" onKeyPress="return letrasnumeros(event);"/></td>
														        </tr>
														      </table></td>
														    </tr>
														  <tr>
														    <td>*Colonia</td>
														    <td colspan="2"><input id="colnC" maxlength="30" onkeyup="this.value=this.value.toUpperCase();" name="colnC" size="40" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    <td>&nbsp;</td>
														    <td>*CP</td>
														    <td><input id="cdpstC" onkeyup="this.value=this.value.toUpperCase();" name="cdpstC" maxlength="5" class="k-textbox" onKeyPress="return captureEnteros(event);"/></td>
														    </tr>
														  <tr>
														    <td>*Delegaci&oacute;n o Municipio</td>
														    <td colspan="2"><input id="dlmcpC" maxlength="30" onkeyup="this.value=this.value.toUpperCase();" name="dlmcpC" size="40" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    <td>&nbsp;</td>
														    <td>&nbsp;</td>
														    <td>&nbsp;</td>
														    </tr>
														  <tr>
														    <td colspan="6" align="center" ><strong>Datos Contacto Cliente</strong></td>
														    </tr>
														  <tr>
														    <td>*Telefono</td>
														    <td colspan="2"><input id="telfnC" onkeyup="this.value=this.value.toUpperCase();" name="telfnC" class="k-textbox" maxlength="10" onKeyPress="return captureEnteros(event);"/></td>
														    <td>&nbsp;</td>
														    <td>Celular</td>
														    <td><input id="tlfmoC" onkeyup="this.value=this.value.toUpperCase();" name="tlfmoC" class="k-textbox" maxlength="10" onKeyPress="return captureEnteros(event);"/></td>
														    </tr>
														  <tr>
														    <td>Oficina</td>
														    <td colspan="2"><input id="telofC" onkeyup="this.value=this.value.toUpperCase();" name="telofC" class="k-textbox" maxlength="10" onKeyPress="return captureEnteros(event);"/></td>
														    <td>&nbsp;</td>
														    <td>Extension</td>
														    <td><input id="extncC" onkeyup="this.value=this.value.toUpperCase();" name="extncC" class="k-textbox" maxlength="6" onKeyPress="return captureEnteros(event);"/></td>
														    </tr>
														  <tr>
														    <td>*Correo 1 </td>
														    <td colspan="2"><input id="mail1C"  maxlength="45" name="mail1C" size="30" class="k-textbox" /></td>
														    <td>&nbsp;</td>
														    <td>Correo 2 </td>
														    <td><input id="mail2C" name="mail2C" maxlength="45" size="30" class="k-textbox"/></td>
														    </tr>
														  </table>
														
									  </div>
									<div id="prestanombre" style="background-color: transparent;">
									  <table width="900" border="0">
									  					 <tr>
														    <td colspan="5" align="center"><strong>Datos Representante Legal</strong></td>
														    </tr>
														  <tr>
														    <td width="134">*Tratamientos</td>
														    <td width="320"><select name="tratamientoCo" id="tratamientoCo">
														      </select></td>
														    <td width="10">&nbsp;</td>
														    <td width="155">Sexo</td>
														    <td width="259"><select name="sexoCo" id="sexoCo">
														      </select></td>
														    </tr>
									                       
														  <tr>
														    <td>*Nombre</td>
														    <td><input id="nombre1Co" onblur="validaRFCContacto()" onkeyup="this.value=this.value.toUpperCase();" name="nombre1Co" maxlength="30" size="25" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    <td>&nbsp;</td>
														    <td>Segundo Nombre</td>
														    <td><input id="nombre2Co" onkeyup="this.value=this.value.toUpperCase();" name="nombre2Co" maxlength="30" size="25" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    </tr>
														  <tr>
														    <td>*Apellido</td>
														    <td><input id="app_patCo" onblur="validaRFCContacto()" onkeyup="this.value=this.value.toUpperCase();" name="app_patCo" maxlength="30" size="25" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    <td>&nbsp;</td>
														    <td><label>*Segundo Apellido</label></td>
														    <td><input id="app_matCo" onblur="validaRFCContacto()" onkeyup="this.value=this.value.toUpperCase();" name="app_matCo" maxlength="30" size="25" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    </tr>
														
														  <tr>
														    <td>IFE</td>
														    <td><input id="ifeCo" onkeyup="this.value=this.value.toUpperCase();" name="ifeCo" maxlength="13" class="k-textbox"/></td>
														    <td>&nbsp;</td>
														    <td>Pasaporte</td>
														    <td><input id="pasprtCo" onkeyup="this.value=this.value.toUpperCase();" name="pasprtCo" maxlength="9" class="k-textbox"/></td>
														    </tr>
														  <tr>
														    <td>*Fecha nacimiento</td>
														    <td><input id="fechaNacContacto" onblur="validaRFCContacto()" name="fechaNacContacto" style="width: 150px;" /></td>
														    <td>&nbsp;</td>
														    <td>*RFC</td>
														    <td><input id="rfcCo" onkeyup="this.value=this.value.toUpperCase();" name="rfcCo" maxlength="13" class="k-textbox"/></td>
									    </tr>
														  <tr>
														    <td>*Curp</td>
														    <td><input id="curpCo" onkeyup="this.value=this.value.toUpperCase();" name="curpCo" maxlength="18" class="k-textbox" onfocus="calcularfccontacto();"/></td>
														    <td>&nbsp;</td>
														    <td>&nbsp;</td>
														    <td>&nbsp;</td>
														    </tr>
														  <tr>
														    <td colspan="5" align="center"><strong>Datos Direcci&oacute;n Representante Legal</strong></td>
														    </tr>
														  <tr>
														    <td>*Pais </td>
														    <td><select name="paisCo" id="paisCo">
														      </select></td>
														    <td>&nbsp;</td>
														    <td>*Regiones</td>
														    <td><select name="estdoCo" id="estdoCo">
														      </select></td>
														    </tr>
														  <tr>
														    <td>*Calle</td>
														    <td><input id="calleCo" maxlength="30" onkeyup="this.value=this.value.toUpperCase();" name="calleCo"  size="40" class="k-textbox" onKeyPress="return letrasnumeros(event);"/></td>
														    <td>&nbsp;</td>
														    <td colspan="2"><table width="219" border="0">
														      <tr>
														        <td>*No Ext</td>
														        <td><input id="noextCo" onkeyup="this.value=this.value.toUpperCase();" name="noextCo" maxlength="10" class="k-textbox" size="5" onKeyPress="return letrasnumeros(event);"/></td>
														        <td>No Int</td>
														        <td><input id="nointCo" onkeyup="this.value=this.value.toUpperCase();" name="nointCo" maxlength="10" class="k-textbox" size="5" onKeyPress="return letrasnumeros(event);"/></td>
														        </tr>
														      </table></td>
														    </tr>
														  <tr>
														    <td>*Colonia</td>
														    <td><input id="colnCo" maxlength="30" onkeyup="this.value=this.value.toUpperCase();" name="colnCo" size="40" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    <td>&nbsp;</td>
														    <td>*CP</td>
														    <td><input id="cdpstCo" onkeyup="this.value=this.value.toUpperCase();" name="cdpstCo" maxlength="5" class="k-textbox" onKeyPress="return captureEnteros(event);"/></td>
														    </tr>
														  <tr>
														    <td>*Municipio o Delegaci&oacute;n</td>
														    <td><input id="dlmcpCo" maxlength="30" onkeyup="this.value=this.value.toUpperCase();" name="dlmcpCo" size="40" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    <td>&nbsp;</td>
														    <td>&nbsp;</td>
														    <td>&nbsp;</td>
														    </tr>
														  <tr>
														    <td colspan="5" align="center"><strong>Datos Contacto Representante Legal</strong></td>
														    </tr>
														
														  <tr>
														    <td>*Telefono</td>
														    <td><input id="telfnCo" onkeyup="this.value=this.value.toUpperCase();" name="telfnCo" class="k-textbox" maxlength="10" onKeyPress="return captureEnteros(event);"/></td>
														    <td>&nbsp;</td>
														    <td>Celular</td>
														    <td><input id="tlfmoCo" onkeyup="this.value=this.value.toUpperCase();" name="tlfmoCo" class="k-textbox" maxlength="10" onKeyPress="return captureEnteros(event);"/></td>
														    </tr>
														  <tr>
														    <td>Oficina</td>
														    <td><input id="telofCo" name="telofCo" class="k-textbox" maxlength="10" onKeyPress="return captureEnteros(event);"/></td>
														    <td>&nbsp;</td>
														    <td>Extension</td>
														    <td><input id="extncCo" maxlength="6" name="extncCo" class="k-textbox" onKeyPress="return captureEnteros(event);"/></td>
														    </tr>
														  <tr>
														    <td>*Correo 1 </td>
														    <td><input id="mail1Co" maxlength="45" name="mail1Co" size="30" class="k-textbox" /></td>
														    <td>&nbsp;</td>
														    <td>Correo 2 </td>
														    <td><input id="mail2Co"  maxlength="45" name="mail2Co" size="30" class="k-textbox"/></td>
														    </tr>
														  </table>
									  
									  		</div>
										</div>
									</div>
									                
									                
									                
									                </td>
									                </tr>
									             </table>           
									            </td>
									          </tr>
									        <tr>
									          <td>&nbsp;</td>
									        </tr>
									   							        
								         <tr>
								           <td>
								              <a class="k-button" id="aceptarCli"  onclick="validaConfirmaAlta()"/>Aceptar</a>
								           </td>
								         </tr>
								          <tr>
								           <td>
								              <div id="mensajes_main">Mensajes de la aplicacion</div>
								           </td>
								         </tr>
								          
								         
								         
									      </table>
									  
									<div id="winOptionConfirmar">
										<table width="314" border="0" align="center" cellpadding="0" cellspacing="0">
									  <tr>
									    <td colspan="2">Los datos se enviaran para ser registrados. Esta usted seguro de enviar esta informacion?</td>
									   </tr>
									  <tr>
									    <td colspan="2">&nbsp;</td>
									   </tr>
									  <tr>
									    <td width="153">&nbsp;</td>
									    <td width="161">&nbsp;</td>
									  </tr>
									  <tr>
									    <td align="center"><input class="k-button" type="button" name="button" id="button" value="Aceptar" onClick="guardaC()"></td>
									    <td align="center"><input class="k-button" type="button" name="button2" id="button2" value="Cancelar" onClick="closeConfirmaWindow()"></td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									</table>
									</div>
									<div id="winOptionBloquear">
										<table width="317" border="0" align="center" cellpadding="0" cellspacing="0">
									  <tr>
									    <td width="317" colspan="2">Estatus de procesamiento de datos</td>
									  </tr>
									  <tr>
									    <td colspan="2"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
									      <tr>
									        <td colspan="3">&nbsp;</td>
									      </tr>
									      <tr>
									        <td width="38"><input type="image" name="img_cte" id="img_cte" src="<%= basePath%>/images/loader/ajax-proces.gif" title=""></td>
									        <td width="99"><div id="folio_cte"> </div></td>
									        <td width="211"><div id="estatus_cte"> </div></td>
									      </tr>
									      <tr>
									        <td><input type="image" name="img_pedido" id="img_pedido" src="<%= basePath%>/images/loader/ajax-proces.gif" title=""></td>
									        <td><div id="folio_pedido"> </div></td>
									        <td><div id="estatus_pedido"> </div></td>
									      </tr>
									      <tr>
									        <td><input type="image" name="img_pago" id="img_pago" src="<%= basePath%>/images/loader/ajax-proces.gif" title=""></td>
									        <td><div id="folio_pago"> </div></td>
									        <td><div id="estatus_pago"> </div></td>
									      </tr>
									      <tr>
									        <td colspan="3">&nbsp;</td>
									      </tr>
									      <tr>
									        <td colspan="3" align="center">
									         <table border="0" cellpadding="0" cellspacing="0">
									          <tr>
									            <td>
									              <input type="hidden" id="intentosContador" value="0"/>
									              <input type="hidden" id="valorPagoInicialAlImprimir"/>
									              <input type="button" name="btn_reprocesa" id="btn_reprocesa" value="Reprocesar" class="k-button" onclick="guardaClienteSAP()" disabled="disabled">
									            </td>
									            <td><input type="button" name="btn_ContinuarImpresion" id="btn_ContinuarImpresion" value="Continuar" class="k-button" onclick="imprimirReportePagInicial()" disabled="disabled"></td>
									            <td><input type="button" style="visibility: hidden;" name="btn_cerrar" id="btn_cerrar" value="Cerrar" class="k-button" onClick="cerrarBloquea()"></td>
									          </tr>
									          <tr>
									             <td>&nbsp;</td>
									          </tr>
									          <tr>
									            <td>
									               <div id="mensajes_mainProceso">Mensajes de la aplicacion</div>
									            </td>
									          </tr>
									          </table>
									        </td>
									      </tr>
									    </table></td>
									  </tr>
									</table>
									</div>
								</form>	
                                 <div id="avisaCambiosCarteraCliente"></div> 
                              
                              <div id="winAvisaClienteMenorEdadCli">
							      <table width="295" border="0">
							      <tr>
							        <td colspan="2" align="center"><b><label id="AnyoActualCliente"></label></b></td>
							      </tr>
							      <tr>
							        <td colspan="2">&nbsp;</td>
							      </tr>
							      <tr>
							        <td align="center">
							            <input class="k-button" type="button" name="button" id="button" value="Regresar" onClick="cerrarVentanaAvisoMenorEdadz();">
							        </td>
							        <td>
							           <input class="k-button" type="button" name="button" id="button" value="Continuar" onClick="continuarGuardadoCliente();">
							        </td>
							      </tr>
							      <tr>
							        <td colspan="2">&nbsp;</td>
							      </tr>
							      </table>
							  </div>
                             
</body>
</html>