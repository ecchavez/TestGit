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
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Registro de Archivos</title>
    
    <script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
    <script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
    <script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js"></script>
    <script src="<%=basePath %>/kendo/js/cultures/kendo.culture.es-EC.min.js"></script>  

    <script type="text/javascript">
            var contexPath="<%=basePath %>";
     </script> 
     <script src="<%=basePath %>/js/digitalizacion/DigitalizacionJQ.js"></script>
     <script src="<%=basePath %>/js/escritorio/EscritorioJQ.js"></script>      
    <script>
	var open_menu=0;
	var guarda=true;
	var window;
	var comboEquipo;
	var comboSubtipos;
	kendo.culture("es-EC");
		$(document).ready(function(){
		    $("#mensajes_main").html('<%=request.getParameter("paramMensaje")==null?"":request.getParameter("paramMensaje")%>');
		    //$("#mensajes_main").html('${errors}');
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
						        title: "Confirmación",
						        modal: true,
						        visible: false,
						        width: "350px"
					        });
	    				}
			
			$("#cmb_faces").kendoComboBox({
				enable: true,
				dataTextField: "text",
				dataValueField: "id",
				placeholder: "",
				dataSource: [],
				filter: "contains",
				change: onChangeUTSFaces,
				suggest: true
			});
			$("#cmb_equipo").kendoComboBox({
				enable: true,
				dataTextField: "id_equnrx",
				dataValueField: "id_equnr",
				placeholder: "",
				dataSource: [],
				filter: "contains",
				change: onChangeUTSequipo,
				suggest: true
			});
			$("#cmb_tipos").kendoComboBox({
				enable: true,
				dataTextField: "id_ticket_areax",
				dataValueField: "id_ticket_area",
				placeholder: "",
				dataSource: [],
				filter: "contains",
				change: onChangeUTSTipos,
				suggest: true
			});
			$("#cmb_subtipos").kendoComboBox({
				enable: true,
				dataTextField: "id_ticket_areax",
				dataValueField: "id_ticket_area",
				placeholder: "",
				dataSource: [],
				filter: "contains",
				change: onChangeUTSubSTipos,
				suggest: true 
			});
			$("#cmb_estatus").kendoComboBox({
				enable: true,
				dataTextField: "id_ticket_statx",
				dataValueField: "id_ticket_stat",
				placeholder: "",
				dataSource: [],
				filter: "contains",
				suggest: true,
				index: 3
			});
			
			getFasesDigit("faces");

			$(".fechaGarantia").hide();
			
			$("#fechaInicio").kendoDatePicker({
		    	value:new Date(),
		    	change: onChangeSumaAnioFechaFin,
		    	format: "dd/MM/yyyy"
		    });

			  var d = new Date();
			  d.setFullYear(d.getFullYear()+1);
			
			$("#fechaFin").kendoDatePicker({
		    	value: d,
		    	change: checkDates,
		    	format: "dd/MM/yyyy"
		    });
							
		});
		
		function validaforma()
		{	
			$("#mensajes_main").html('');
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
			else if( !validaFechaRegExp($("#fechaInicio").val()) && $("#cmb_tipos").data('kendoComboBox').value() == "05" )
			{
				alert("Fecha inicio formato no valido");
				$("#fechaInicio").focus();
			}
			else if( !validaFechaRegExp($("#fechaFin").val())&& $("#cmb_tipos").data('kendoComboBox').value() == "05" )
			{
				alert("Fecha fin formato no valido");
				$("#fechaFin").focus();
			}
			else
			{				 
				validado=true;
			}
			
			if(validado && guarda)
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

				comboEquipo = $("#cmb_equipo").data("kendoComboBox");
				comboSubtipos = $("#cmb_subtipos").data("kendoComboBox");

				var divEquipo = document.getElementById("equipo");
				//divEquipo.childNodes[0].nodeValue = comboEquipo.text();
				divEquipo.innerHTML = comboEquipo.text();

				var divSubEquipo = document.getElementById("subtipo");
				//divSubEquipo.childNodes[0].nodeValue = comboSubtipos.text();
				divSubEquipo.innerHTML = comboSubtipos.text();
				 
				$("#winOptionConfirmar").data("kendoWindow").open();
				$("#winOptionConfirmar").data("kendoWindow").center();
				
				//document.formulario.submit();
			}else if(!guarda){
				alert("Espere, transfiriendo datos");
				$("#mensajes_main").html("Enviando información");
			}
		}

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

		function closeConfirmaWindow()
		{
			$("#winOptionConfirmar").data("kendoWindow").close();
		}


		function submiteForma()
		{			
			document.formulario.submit();
					
			var combobox = $("#cmb_faces").data("kendoComboBox");
			combobox.enable(false);

			var combobox1 = $("#cmb_equipo").data("kendoComboBox");
			combobox1.enable(false);

			var combobox2 = $("#cmb_tipos").data("kendoComboBox");
			combobox2.enable(false);

			var combobox3 = $("#cmb_subtipos").data("kendoComboBox");
			combobox3.enable(false);

			var combobox4 = $("#cmb_estatus").data("kendoComboBox");
			combobox4.enable(false);

			var combobox5 = $("#fechaInicio").data("kendoDatePicker");
			combobox5.enable(false);

			var combobox6 = $("#fechaFin").data("kendoDatePicker");
			combobox6.enable(false);

			document.getElementById("fileData").disabled = true;
			
			$("#winOptionConfirmar").data("kendoWindow").close();
			guarda=false;
		}

	</script> 


    <link href="<%=basePath %>/jixedbar/default/jx.stylesheet.css" type="text/css"  rel="stylesheet" />
	<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />
     

</head>

<body topmargin="0">
<form name="formulario" method="post" enctype="multipart/form-data" action="<%=basePath %>/RespuestaDigitalizacionFile.htm">
<table width="1180" height="819" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
    <td width="1180" height="102" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="0"><img src="<%=basePath %>/images/images/pages/header_left.png" /></td>
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
                <td width="424" id="TituloModulo">Registro de archivos</td>
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
              <tr>
                <td width="80"><a class="k-button" id="btn_valida" onclick="if(validaIngresoModulos('DIGIT', 'PERMISO_C')){validaforma()}else{$('#mensajes_main').html('Sin permisos para este modulo')}" style="width: 80px"><span class="k-icon k-i-plus"></span>Guardar</a></td>
                <td width="68">&nbsp;</td>
                <td width="65">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="127">&nbsp;</td>
                </tr>
            </table></td>
            </tr>
        </table></td>
        <td width="0"><img src="<%=basePath %>/images/images/pages/header_right.png" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="540" valign="top"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="0" height="16"><img src="<%=basePath %>/images/images/pages/main_corner4.png" /></td>
        <td width="1150" background="<%=basePath %>/images/images/pages/main_corner4_comp.png"></td>
        <td width="0"><img src="<%=basePath %>/images/images/pages/main_corner3.png" /></td>
      </tr>
      <tr>
        <td height="634" background="<%=basePath %>/images/images/pages/main_corner2_comp.png"></td>
        <td background="<%=basePath %>/images/images/pages/main_relleno.png" valign="top">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td height="612" valign="top">
                      
                      	<table width="961" height="200" border="0" align="left" cellpadding="0" cellspacing="0">
						    <tr>
						      <td width="961" height="57"><table width="100%" border="0" cellpadding="0" cellspacing="0">
						        <tr>
						          <td width="104">Desarrollo</td>
						          <td width="162"><div id="txt_uts"><%= user.getId_ut_sup() %></div></td>
						          <td><div id="txt_uts_desc"><%= user.getId_ut_sup_cm() %></div></td>
						          <td>Proceso</td>
						          <td><div id="cmb_tipos" style="width:180px;"> </div></td>
						          <td>&nbsp;</td>
					            </tr>
						        <tr>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
						          <td width="143"></td>
						          <td width="142"></td>
						          <td width="192"></td>
						          <td width="218"></td>
					            </tr>
						        <tr>
						          <td>Fase</td>
						          <td><div id="cmb_faces" style="width:150px;"> </div></td>
						          <td>&nbsp;</td>
						          <td>Tipo archivo</td>
						          <td><div id="cmb_subtipos" style="width:180px;"> </div></td>
						          <td>&nbsp;</td>
					            </tr>
						        <tr>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
					            </tr>
						        <tr>
						          <td>Casa/Depto</td>
						          <td><div id="cmb_equipo" style="width:150px;"> </div></td>
						          <td>&nbsp;</td>
						          <td>Status</td>
						          <td><div id="cmb_estatus" style="width:180px;"> </div></td>
						          <td>&nbsp;</td>
					            </tr>
						        <tr>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
						          <td>&nbsp;</td>
					            </tr>
						        <tr>
						          <td colspan="2"><input type="file" id="fileData" name="fileData" class="k-button" style="width: 250px"/></td>
						          <td>&nbsp;</td>
						          <td class="fechaGarantia">Periodo de Garantia</td>
						          <td class="fechaGarantia"><input type="text" id="fechaInicio" name="fechaInicio" value="" size="30" maxlength="30"></td>
						          <td class="fechaGarantia"><input type="text" id="fechaFin" name="fechaFin" value="" size="30" maxlength="30"></td>
					            </tr>
						        <tr>
						          <td height="19" colspan="6">&nbsp;</td>
					            </tr>
					          </table></td>
						    </tr>
						    <tr>
						      <td height="19">&nbsp;</td>
					      </tr>
						    <tr>
						      <td height="19"><table width="95" border="0" align="left" cellpadding="0" cellspacing="0">
						        <tr>
						          <td width="55">
						            <input type="button" name="guardar" id="guardar" value="Guardar" class="k-button" onClick="validaforma()" style="display: none;"></td>
						          <td width="69">
						            <input type="button" name="cancelar" id="cancelar" value="Cancelar" class="k-button" onClick="window.parent.closeWindowUser()" style="display: none;"></td>
						        </tr>
						      </table></td>
						    </tr>
						  </table>
                      	
<input type="hidden" id="datos" name="datos">
						  <input type="hidden" id="accion" name="accion">

					  </td>
              </tr>
          </table></td>
        <td background="<%=basePath %>/images/images/pages/main_corner3_comp.png"></td>
      </tr>
      <tr>
        <td height="0"><img src="<%=basePath %>/images/images/pages/main_corner1.png" /></td>
        <td background="<%=basePath %>/images/images/pages/main_corner1_comp.png"></td>
        <td><img src="<%=basePath %>/images/images/pages/main_corner2.png" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="19">&nbsp;</td>
  </tr>
</table>
</form>
<div id="sample-bar" >
   <ul>
      <li title=""><div id="base_estatus" style="width:17px; height:20px; vertical-align:middle">>>></div></li>
   </ul>
   <span class="jx-separator-left"></span>
   <div id="mensajes_main"></div>
   <span class="jx-separator-right"></span>
</div>
<div id="my-scrollable-div" style="position:fixed; top:100px; left:-200px;">
<div id="my-scrollable-div_int" style="position:absolute; top:-5.7px; left:200px;">
<img src="<%=basePath %>/images/images/pages/flecha_menu.png" onclick="valida_openclose()"/>
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

<div id="winOptionConfirmar">
										<table width="314" border="0" align="center" cellpadding="0" cellspacing="0">
									  <tr>
									    <td colspan="2">¿Esta usted seguro de guardar esta digitalización?</td>
									   </tr>
									  <tr>
									  <tr>
									    <td colspan="2">&nbsp;</td>
									  </tr>
									  <tr>
									    <td align="center">Equipo:</td><td><div id="equipo" style="font-weight: bold"></div></td>
									   </tr>
									  <tr>
									  <tr>
									    <td align="center">Tipo:</td><td><div id="subtipo" style="font-weight: bold"></div></td>
									  </tr>
									  <tr>
									    <td width="153">&nbsp;</td>
									    <td width="161">&nbsp;</td>
									  </tr>
									  <tr>
									    <td align="center"><input class="k-button" type="button" name="button" id="button" value="Aceptar" onClick="submiteForma()"></td>
									    <td align="center"><input class="k-button" type="button" name="button2" id="button2" value="Cancelar" onClick="closeConfirmaWindow()"></td>
									  </tr>
									  <tr>
									    <td>&nbsp;</td>
									    <td>&nbsp;</td>
									  </tr>
									</table>
	</div>

</body>
</html>