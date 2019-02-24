<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
String origen= request.getParameter("origen");
String origenRegPago = request.getParameter("origenRegPago");
origenRegPago = origenRegPago ==null ? "" : origenRegPago;
String idClienteActual = request.getParameter("idClienteActual");
idClienteActual = idClienteActual == null ? "" : idClienteActual;

%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Clientes SAP</title>

<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>



<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/clientes/ClientesJQ.js"></script>
<script src="<%=basePath %>/js/clientes/ClientesSAPJQ.js"></script>
<script src="<%=basePath %>/js/utils.js"></script>
<script>
  		$(document).ready(function(){
		 $("#fch_ncm").kendoDatePicker({
 			format: "dd/MM/yyyy",
 			width: 20,
 			min: new Date(1, 0, 1900)
		});
		
		$("#fechaNacContacto").kendoDatePicker({
 			format: "dd/MM/yyyy",
 			width: 20,
 			min: new Date(1, 0, 1900)
		});
		
		var origenRegPagox='<%=origenRegPago %>';
		var idClienteActualx = '<%=idClienteActual %>';
		if (origenRegPagox==='SEGUNDOPAGO' || origenRegPagox==='MONITOR'){
			
			buscaClienteSap(idClienteActualx);
			
		    if(window.parent.dataItem!=null)
		    {    //Datos para el cliente onlyRead y viene de modulo cliente SAP
		       setDatos();
	        }
		} else {
		
			if(window.parent.dataItemCotizacion!=null)
			{
			     buscaClienteSap(window.parent.dataItemCotizacion.idCliente);
			}
			
			if(window.parent.dataItem!=null)
			{    //Datos para el cliente onlyRead y viene de modulo cliente SAP
			     setDatos();
		    }
		
		}
	
			
	      $("#tabstrip").kendoTabStrip({
						animation:	{
							open: {
								effects: "fadeIn"
							}
						}
					
					});
		detailsTemplate = kendo.template($("#template").html());  
		
	});	
</script>

<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />
</head>
  
  <body>
  
  <form id="registroC" name="registroC" action="" method="post">
									 <input type="hidden" id="origen" name="origen" value="<%=origen%>">
												
									    
									          <table width="100%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
									              <tr>
									                <td>
									                 <div>
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
														    <td><input type="text"  class="k-textbox"  name="regimenFiscal" id="regimenFiscal" /></td>
														    <td></td>
														    <td>&nbsp;</td>
														    <td>*Tratamientos</td>
														    <td><input type="text"  class="k-textbox"  name="tratamientoC" id="tratamientoC" /></td>
														    </tr>
									                        <tr>
									                       	   <td>Estado Civil</td>
									                           <td colspan="2"><input type="text"  class="k-textbox"  name="edo_civilC" id="edo_civilC" /></td>
									                           <td>&nbsp;</td>
									                           <td>Sexo</td>
									                           <td><input type="text"  class="k-textbox"  name="sexoC" id="sexoC" /></td>
									                        </tr>
														  <tr>
														    <td>*Nombre</td>
														    <td colspan="2"><input id="nombre1C" style="text-transform:uppercase;" name="nombre1C" size="25" maxlength="30" class="k-textbox" onkeyup="this.value=this.value.toUpperCase();" onKeyPress="return captureNombres(event);"/></td>
														    <td>&nbsp;</td>
														    <td>Segundo Nombre</td>
														    <td><input id="nombre2C" onkeyup="this.value=this.value.toUpperCase();" style="text-transform:uppercase;" name="nombre2C" size="25" maxlength="30" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    </tr>
														  <tr>
														    <td><label id="idApellido">*Apellido</label></td>
														    <td colspan="2"><input id="app_patC" onkeyup="this.value=this.value.toUpperCase();" style="text-transform:uppercase;" name="app_patC" size="25" maxlength="30" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    <td>&nbsp;</td>
														    <td>Segundo Apellido</td>
														    <td><input id="app_matC" onkeyup="this.value=this.value.toUpperCase();" style="text-transform:uppercase;" name="app_matC" size="25" maxlength="30" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    </tr>
														  <tr>
														    <td><label id="idFechaNacimiento">*Fecha Nacimiento</label></td>
														    <td colspan="2"><input id="fch_ncm" name="fch_ncm" style="width: 150px;" /></td>
														    <td>&nbsp;</td>
														    <td>&nbsp;</td>
														    <td>&nbsp;</td>
														    </tr>
														  <tr>
														    <td><label id="idIfeCliente">*IFE</label></td>
														    <td colspan="2"><input id="id_ifeC" style="text-transform:uppercase;" name="id_ifeC" maxlength="13" class="k-textbox" onkeypress="return captureEnteros(event)"/></td>
														    <td>&nbsp;</td>
														    <td><label id="idPasaporteCliente">Pasaporte</label></td>
														    <td><input id="id_pasprtC" onkeyup="this.value=this.value.toUpperCase();" name="id_pasprtC" maxlength="9" class="k-textbox"/></td>
														    </tr>
														  <tr>
														    <td><Label id="idCurpCliente">*Curp</Label></td>
														    <td colspan="2"><input id="curpC" style="text-transform:uppercase;" name="curpC" maxlength="18" class="k-textbox" onfocus="calcularfc()"/></td>
														    <td>&nbsp;</td>
														    <td>*RFC</td>
														    <td><input id="rfcC" style="text-transform:uppercase;" name="rfcC" maxlength="13" class="k-textbox" /></td>
														    </tr>
														  <tr>
														    <td colspan="6" align="center" ><strong>Datos Direcci&oacute;n Cliente</strong></td>
														    </tr>
														  <tr>
														    <td>*Pais </td>
														    <td colspan="2"><input type="text"  class="k-textbox"  name="paisC" id="paisC" /></td>
														    <td>&nbsp;</td>
														    <td>*Regiones</td>
														    <td><input type="text"  class="k-textbox"  name="estdoC" id="estdoC" /></td>
														    </tr>
														  <tr>
														    <td>*Calle</td>
														    <td colspan="2"><input id="calleC" style="text-transform:uppercase;" name="calleC"  size="40" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    <td>&nbsp;</td>
														    <td colspan="2"><table width="219" border="0">
														      <tr>
														        <td>*No Ext</td>
														        <td><input id="noextC" style="text-transform:uppercase;" name="noextC" maxlength="5" class="k-textbox" size="5" onKeyPress="return captureEnteros(event);"/></td>
														        <td>No Int</td>
														        <td><input id="nointC" style="text-transform:uppercase;" name="nointC" maxlength="5" class="k-textbox" size="5" onKeyPress="return captureEnteros(event);"/></td>
														        </tr>
														      </table></td>
														    </tr>
														  <tr>
														    <td>*Colonia</td>
														    <td colspan="2"><input id="colnC" style="text-transform:uppercase;" name="colnC" size="40" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    <td>&nbsp;</td>
														    <td>*CP</td>
														    <td><input id="cdpstC" style="text-transform:uppercase;" name="cdpstC" maxlength="5" class="k-textbox" onKeyPress="return captureEnteros(event);"/></td>
														    </tr>
														  <tr>
														    <td>*Municipio</td>
														    <td colspan="2"><input id="dlmcpC" style="text-transform:uppercase;" name="dlmcpC" size="40" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    <td>&nbsp;</td>
														    <td>&nbsp;</td>
														    <td>&nbsp;</td>
														    </tr>
														  <tr>
														    <td colspan="6" align="center" ><strong>Datos Contacto Cliente</strong></td>
														    </tr>
														  <tr>
														    <td>*Telefono</td>
														    <td colspan="2"><input id="telfnC" style="text-transform:uppercase;" name="telfnC" class="k-textbox" maxlength="10" onKeyPress="return captureEnteros(event);"/></td>
														    <td>&nbsp;</td>
														    <td>Celular</td>
														    <td><input id="tlfmoC" style="text-transform:uppercase;" name="tlfmoC" class="k-textbox" maxlength="10" onKeyPress="return captureEnteros(event);"/></td>
														    </tr>
														  <tr>
														    <td>Oficina</td>
														    <td colspan="2"><input id="telofC" style="text-transform:uppercase;" name="telofC" class="k-textbox" maxlength="10" onKeyPress="return captureEnteros(event);"/></td>
														    <td>&nbsp;</td>
														    <td>Extension</td>
														    <td><input id="extncC" style="text-transform:uppercase;" name="extncC" class="k-textbox" maxlength="6" onKeyPress="return captureEnteros(event);"/></td>
														    </tr>
														  <tr>
														    <td>*Correo 1 </td>
														    <td colspan="2"><input id="mail1C" style="text-transform:uppercase;" name="mail1C" size="30" class="k-textbox" /></td>
														    <td>&nbsp;</td>
														    <td>Correo 2 </td>
														    <td><input id="mail2C" style="text-transform:uppercase;" name="mail2C" size="30" class="k-textbox"/></td>
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
														    <td width="320"><input type="text"  class="k-textbox"  name="tratamientoCo" id="tratamientoCo" /></td>
														    <td width="10">&nbsp;</td>
														    <td width="155">Sexo</td>
														    <td width="259"><input type="text"  class="k-textbox"  name="sexoCo" id="sexoCo" /></td>
														    </tr>
									                       
														  <tr>
														    <td>*Nombre</td>
														    <td><input id="nombre1Co" style="text-transform:uppercase;" name="nombre1Co" maxlength="30" size="25" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    <td>&nbsp;</td>
														    <td>Segundo Nombre</td>
														    <td><input id="nombre2Co" style="text-transform:uppercase;" name="nombre2Co" maxlength="30" size="25" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    </tr>
														  <tr>
														    <td>*Apellido</td>
														    <td><input id="app_patCo" style="text-transform:uppercase;" name="app_patCo" maxlength="30" size="25" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    <td>&nbsp;</td>
														    <td>Segundo Apellido</td>
														    <td><input id="app_matCo" style="text-transform:uppercase;" name="app_matCo" maxlength="30" size="25" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    </tr>
														
														  <tr>
														    <td>*IFE</td>
														    <td><input id="ifeCo" style="text-transform:uppercase;" name="ifeCo" maxlength="13" class="k-textbox"/></td>
														    <td>&nbsp;</td>
														    <td>*Pasaporte</td>
														    <td><input id="pasprtCo" style="text-transform:uppercase;" name="pasprtCo" maxlength="9" class="k-textbox"/></td>
														    </tr>
														  <tr>
														    <td>*Fecha nacimiento</td>
														    <td><input id="fechaNacContacto" name="fechaNacContacto" style="width: 150px;" /></td>
														    <td>&nbsp;</td>
														    <td>*RFC</td>
														    <td><input id="rfcCo" style="text-transform:uppercase;" name="rfcCo" maxlength="13" class="k-textbox"/></td>
									    </tr>
														  <tr>
														    <td>*Curp</td>
														    <td><input id="curpCo" style="text-transform:uppercase;" name="curpCo" maxlength="18" class="k-textbox" onfocus="calcularfccontacto();"/></td>
														    <td>&nbsp;</td>
														    <td>&nbsp;</td>
														    <td>&nbsp;</td>
														    </tr>
														  <tr>
														    <td colspan="5" align="center"><strong>Datos Direcci&oacute;n Representante Legal</strong></td>
														    </tr>
														  <tr>
														    <td>*Pais </td>
														    <td><input type="text"  class="k-textbox"  name="paisCo" id="paisCo" /></td>
														    <td>&nbsp;</td>
														    <td>*Regiones</td>
														    <td><input type="text"  class="k-textbox"  name="estdoCo" id="estdoCo" /></td>
														    </tr>
														  <tr>
														    <td>*Calle</td>
														    <td><input id="calleCo" style="text-transform:uppercase;" name="calleCo"  size="40" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    <td>&nbsp;</td>
														    <td colspan="2"><table width="219" border="0">
														      <tr>
														        <td>*No Ext</td>
														        <td><input id="noextCo" style="text-transform:uppercase;" name="noextCo" maxlength="5" class="k-textbox" size="5" onKeyPress="return captureEnteros(event);"/></td>
														        <td>No Int</td>
														        <td><input id="nointCo" style="text-transform:uppercase;" name="nointCo" maxlength="5" class="k-textbox" size="5" onKeyPress="return captureEnteros(event);"/></td>
														        </tr>
														      </table></td>
														    </tr>
														  <tr>
														    <td>*Colonia</td>
														    <td><input id="colnCo" style="text-transform:uppercase;" name="colnCo" size="40" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    <td>&nbsp;</td>
														    <td>*CP</td>
														    <td><input id="cdpstCo" style="text-transform:uppercase;" name="cdpstCo" maxlength="5" class="k-textbox" onKeyPress="return captureEnteros(event);"/></td>
														    </tr>
														  <tr>
														    <td>*Municipio</td>
														    <td><input id="dlmcpCo" style="text-transform:uppercase;" name="dlmcpCo" size="40" class="k-textbox" onKeyPress="return captureNombres(event);"/></td>
														    <td>&nbsp;</td>
														    <td>&nbsp;</td>
														    <td>&nbsp;</td>
														    </tr>
														  <tr>
														    <td colspan="5" align="center"><strong>Datos Contacto Representante Legal</strong></td>
														    </tr>
														
														  <tr>
														    <td>*Telefono</td>
														    <td><input id="telfnCo" style="text-transform:uppercase;" name="telfnCo" class="k-textbox" maxlength="10" onKeyPress="return captureEnteros(event);"/></td>
														    <td>&nbsp;</td>
														    <td>Celular</td>
														    <td><input id="tlfmoCo" style="text-transform:uppercase;" name="tlfmoCo" class="k-textbox" maxlength="10" onKeyPress="return captureEnteros(event);"/></td>
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
														    <td><input id="mail1Co" style="text-transform:uppercase;" name="mail1Co" size="30" class="k-textbox" /></td>
														    <td>&nbsp;</td>
														    <td>Correo 2 </td>
														    <td><input id="mail2Co" style="text-transform:uppercase;" name="mail2Co" size="30" class="k-textbox"/></td>
														    </tr>
														  </table>
									  
									  		</div>
										</div>
									</div>
									                
									                
									                
									                </td>
									                </tr>
									             </table>           
	
									
								</form>
								</body></html>