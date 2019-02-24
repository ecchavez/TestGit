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

response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1);
%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Registro Tickets</title>
    
 <script src="<%=basePath %>/js/jquery-1.7.2.js?1.1"></script>
    <script src="<%=basePath %>/kendo/js/kendo.web.min.js?1.1"></script>
    <script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js?1.1"></script>   

    <script type="text/javascript">
            var contexPath="<%=basePath %>";
     </script> 
     <script src="<%=basePath %>/js/escritorio/EscritorioJQ.js?1.2"></script>      
     <script src="<%=basePath %>/js/tickets/RegistroTickets.js?1.2"></script>      
     <script src="<%=basePath %>/js/utils.js"></script>   
        
    <script>
	var open_menu=0;
	var arraytextos=[];

	
	function insertaRegistro(){

            // Obtenemos el numero de filas (td) que tiene la primera columna
            // (tr) del id "tabla"
            
            $
			.ajax({
				type : "POST",
				cache : false,
				async : false,
				url : contexPath
						+ "/ticket/LlenaCatalogoViciosTicket.htm",
				success : function(response) {
					// we have the response
					if (response != undefined) {
						if (response.mensaje != undefined
								&& response.mensaje == "SUCCESS") {
								arraytextos = response.texto;
								arraytextos.push("OTRO|");
						} else {
							// $.unblockUI()
							$("#mensajes_main").html(response.descripcion);
						}
					}

				},
				error : function(e) {
					// $.unblockUI()
					$("#mensajes_main").html(
							"Fallo el acceso a los datos " + e.responseText
									+ " " + e.status);
				}
			});

            var menu;
            var arr_menu=[];

            for (var indice in arraytextos) {
            menu = arraytextos[indice];
            var n = menu.lastIndexOf("|");
            menu =  menu.substr(0, n);
            arr_menu.push(menu);
            }

            arr_menu = eliminateDuplicates(arr_menu); 

            //var tds=$("#dg_vicios tr:first td").length;
            // Obtenemos el total de columnas (tr) del id "tabla"
            var trs=$("#dg_vicios tr").length;
			
			//if(trs>=5){
			//	return;
			//	}
			
            var nuevaFila="<tr>";

            // Aï¿½adimos una columna con el numero total de columnas.
            // Aï¿½adimos uno al total, ya que cuando cargamos los valores para la
            // columna, todavia no esta aï¿½adida
            					
            nuevaFila+="<td width=\"4%\" style=\"text-align:center\">"+(trs+1)+"</td>";
            nuevaFila+="<td width=\"26%\"><input type=\"text\" id=\"zona[" + trs + "]\" style=\"width: 100%; text-transform: uppercase\" onblur=\"javascript:this.value=this.value.toUpperCase().trim();\" name=\"zona\" class=\"k-textbox\" maxlength=\"28\"/> </td>";
            //nuevaFila+="<td width=\"40%\"><input type=\"text\" id=\"vicio[" + trs + "]\" style=\"width: 100%; text-transform: uppercase\" onblur=\"javascript:this.value=this.value.toUpperCase().trim();\" name=\"vicio\" class=\"k-textbox\" maxlength=\"80\"/> </td>";
            nuevaFila+="<td width=\"40%\"><select style=\"font-size: 10px !important; text-transform: uppercase; font-family:Century Gothic\" onblur=\"javascript:this.value=this.value.toUpperCase().trim();\" name=\"categoria\" onchange=\"changeFunc("+ trs +")\" id=\"" + trs + "\">";
            for (var ind in arr_menu) {
            nuevaFila+= "<option value=\"" + arr_menu[ind] + "\">" + arr_menu[ind] + "</option> ";    
            }
            nuevaFila+= "</select>";
            nuevaFila+= "<span class=\"separador\" style=\"display: none\">/</span>";
            nuevaFila+= "<select style=\"font-size: 10px !important; font-family:Century Gothic; display: none\" name=\"subcategoria\" class=\"subcategoria\" >"
            				+ "<option value=\"\"></option>"
            				+ "</select>";
			nuevaFila+= "<input type=\"text\" onKeyPress=\"return letrasnumeros(event)\" style=\"font-size: 10px !important; font-family:Century Gothic; display: none; text-transform: uppercase\" name=\"textoabierto\" class=\"textoabierto\" maxlength=\"70\"></td>";
            nuevaFila+="<td width=\"30%\"><input type=\"file\" id=\"archivos[" + trs + "]\" name=\"archivos[" + trs + "]\" /> </td>";
            nuevaFila+="</tr>";


            $("#dg_vicios").append(nuevaFila);

        }


	function insertaRegistroTicket(){

        // Obtenemos el numero de filas (td) que tiene la primera columna
        // (tr) del id "tabla"

        //var tds=$("#dg_vicios tr:first td").length;
        // Obtenemos el total de columnas (tr) del id "tabla"
        var trs=$("#dg_ticket tr").length;
		
		//if(trs>=5){
		//	return;
		//	}
		
        var nuevaFila="<tr>";

        // Aï¿½adimos una columna con el numero total de columnas.
        // Aï¿½adimos uno al total, ya que cuando cargamos los valores para la
        // columna, todavia no esta aï¿½adida
        					
        nuevaFila+="<td width=\"4%\" style=\"text-align:center\">"+(trs+1)+"</td>";
        nuevaFila+="<td width=\"96%\"><input type=\"text\" id=\"descripcion[" + trs + "]\" style=\"width: 100%; text-transform: uppercase\" onblur=\"javascript:this.value=this.value.toUpperCase().trim();\" name=\"descripcion\" class=\"k-textbox\" maxlength=\"80\"/> </td>";
        nuevaFila+="</tr>";


        $("#dg_ticket").append(nuevaFila);

    }
    
    $(document).ready(function() {
    	//$("#tblFormTicket").hide();
    	$(".tblFormVicios").show();
		$(".tblFormTicket").hide();
        
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

		$("#idArea").kendoComboBox({
			dataTextField: "id_ticket_areax",
			dataValueField: "id_ticket_area",
			dataSource: [],
			filter: "contains",
			change: onChangeAreaRegistroTicket,
			suggest: true
	    });	 
		$("#idFase").kendoComboBox({
			dataTextField: "text",
			dataValueField: "id",
			dataSource: [],
			filter: "contains",
			change: onChangeFacesRegistroTicket,
			suggest: true 
	    });
		$("#idEquipo").kendoComboBox({
			dataTextField: "id_equnrx",
			dataValueField: "id_equnr",
			dataSource: [],
			filter: "contains",
		    change: onChangeEquipoRegistroTicket,
			suggest: true
	    });	 

		 $("#dg_vicios").kendoGrid({							  
			  filterable: false,
			  height: 120,
			  sortable: false,			  
			  pageable: false,
	          resizable: false,
	          scrollable: true,
	          selectable: "row",
	          //dataSource: subEquipoDS										  
		 });

		 $("#dg_ticket").kendoGrid({							  
			  filterable: false,
			  height: 120,
			  sortable: false,			  
			  pageable: false,
	          resizable: false,
	          scrollable: true,
	          selectable: "row",
	          //dataSource: subEquipoDS										  
		 });
		 
		fillComboRegistroTicket("areas");
		fillComboRegistroTicket("faces");

    	$("#mensajes_main").html('<c:out value="${response}"/>');
		
		insertaRegistro();
		insertaRegistroTicket();
				
		    //$("#btn_add").click(function(){
        
        
       $("#btn_del").click(function(){

            // Obtenemos el total de columnas (tr) del id "tabla"
            var trs=$("#dg_vicios tr").length;

            if(trs>1)
            {
                // Eliminamos la ultima columna
                $("#dg_vicios tr:last").remove();
            }

        });

       $("#btn_del_ticket").click(function(){

           // Obtenemos el total de columnas (tr) del id "tabla"
           var trs=$("#dg_ticket tr").length;

           if(trs>1)
           {
               // Eliminamos la ultima columna
               $("#dg_ticket tr:last").remove();
           }

       });
	
    });
					
		function valida_openclose()
		{
			if(open_menu==0)
			{
				open_menu=1;
				var cssObj = {      
				'left':'0px'    
				};
				$("#my-scrollable-div").css(cssObj);
			}else{
				open_menu=0;
				var cssObj = {      
				'left':'-200px'    
				};
				$("#my-scrollable-div").css(cssObj);
			}
				
		}

		$(document).on('change','input[type="file"]',function(){
			// this.files[0].size recupera el tamaño del archivo
			// alert(this.files[0].size);
			
			var fileName = this.files[0].name;
			var fileSize = this.files[0].size;

			if(fileSize > 1000000){
				alert('Las imagenes adjuntas no debe superar un megabyte de tamaño');
				this.value = '';
				this.files[0].name = '';
			}
		});
</script>
    <link href="<%=basePath %>/jixedbar/default/jx.stylesheet.css" type="text/css"  rel="stylesheet" />
	<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />
     
    <style type="text/css">
      
	.k-grid {
    	width: 80%;
	}
	
	.k-grid-content{
	    height:95px !important; 
	}
	
    .desactivado {
       	border-radius: 4px;
   		border-width: 1px;
   		font: 10px "Century Gothic";
   		height:23px;
   		border:1px solid
   }
	
	.sololectura{
			border-radius: 4px;
			font: 10px 'Century Gothic','Century Gothic',arial,helvetica,sans-serif;
			border-width: 1px;
			border-style: solid;
			padding: 2px .3em;
			height: 2.12em;
			line-height: 1.6em;
			box-sizing: border-box;
			outline: 0;
			border-color: #8a8a8a;  
		}
	
    </style>

</head>

<body topmargin="0">
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
                <td width="424" id="TituloModulo">Registro Tickets</td>
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
                <td width="65">&nbsp;</td>
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
        	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            	<tr>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td height="612" valign="top">
                  	<div id="area_trabajo">
                  		<div id="registroTicket" style="height: 600px; width:1135px">
                  			<form id="registroTicketForm" name="registroTicketForm" enctype="multipart/form-data" action="<%=basePath%>/ticket/AddTicketConstruccion.htm" method="post">
                  				<table border="0">
									<tbody>
										<tr>
											<td width="153" align="right">* &Aacute;rea:</td>
											<td width="215">
											  <select name="idArea" id="idArea" style="width: 5cm;">
											  </select>
										  </td>
											<td align="right">
												Cliente:</td>
											<td width="210" align="left"><input type="text" class="k-textbox" id="cliente" onBlur="javascript:this.value=this.value.toUpperCase().trim();"  name="cliente" style="width: 5.5cm; text-transform: uppercase" size="50" maxlength="80" onKeyPress="return letrasnumeros(event)" /></td>
											<td width="135" align="right" class="tblFormVicios"> Inicio de Garant&iacute;a:</td>
											<td width="264" class="tblFormVicios"><input type="text" id="fechaInicioGarantia" style="width: 1.8cm" class="sololectura" disabled />												
											</td>
									  </tr>
										<tr>
											<td align="right"> * Fase:</td>
											<td>
												<select name="idFase" id="idFase" style="width: 5cm;">
												</select>							
											</td>
											<td align="right">
											Email Cliente:</td>
											<td align="left"><input type="text" id="correo" style="width: 5.5cm" class="sololectura" disabled /></td>
											<td align="right" class="tblFormVicios">Fin de Garant&iacute;a:</td>
											<td class="tblFormVicios"><input type="text" id="fechaFinGarantia" style="width: 1.8cm" class="sololectura" disabled />
											</td>
										</tr>
										<tr>
											<td align="right">* Equipo:</td>
											<td>
											<select name="idEquipo" id="idEquipo" style="width: 5cm;">
											</select>
											</td>
										  <td align="right">Tel&eacute;fono:					
											</td>
										  <td align="left"><input type="text" id="telefono" style="width: 5.5cm" class="sololectura" disabled /></td>
											<td>&nbsp;</td>
											<td>&nbsp;
											</td>
										</tr>
										<tr>
											<td align="right"></td>
											<td>
											</td>
										  <td align="right">CCP:					
											</td>
										  <td align="left"><input type="text" id="ccp" name="ccp" style="width: 5.5cm; text-transform: uppercase" size="50" maxlength="80" class="k-textbox" onBlur="javascript:this.value=this.value.toUpperCase().trim();" /></td>
											<td>&nbsp;</td>
											<td>&nbsp;
											</td>
										</tr>
										<tr>
											<td align="right" colspan="2">&nbsp;</td>
											<td align="right">&nbsp;
											
											</td>
											<td align="right" colspan="3">&nbsp;</td>
										</tr>
                                        <tr valign="top" class="tblFormVicios">
											<td align="right">&nbsp;</td>
                                            
                                         
											<td colspan="2"  align="center">Prioridad de la solicitud: <INPUT type="radio" name="prioridad" value="CRITICA">
											Cr&iacute;tica
											  <INPUT type="radio" name="prioridad" value="MAYOR" checked="checked">
											  Mayor
											  <INPUT type="radio" name="prioridad" value="MENOR">
											  Menor											  <br></td>
                                              <td>&nbsp;
                                             </td>                                              
                                             <td align="right"><input type="button" name="btn_add" id="btn_add" value="Agrega" class="k-button" onclick="insertaRegistro()">
                                             <input type="button" name="btn_del" id="btn_del" value="Elimina" class="k-button"></td>
                                             <td width="264">&nbsp;
                                             
                                             </td>
										</tr>
										
										
										<tr valign="top" class="tblFormTicket">
											<td align="right"></td>
                                            
                                         
											<td colspan="2"  align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Prioridad de la solicitud: <INPUT type="radio" name="prioridad" value="CRITICA">
											Cr&iacute;tica
											  <INPUT type="radio" name="prioridad" value="MAYOR">
											  Mayor
											  <INPUT type="radio" name="prioridad" value="MENOR" checked="checked">
											  Menor											  <br></td>
                                             <td align="center"><input type="button" name="btn_add_ticket" id="btn_add" value="Agrega" class="k-button" onclick="insertaRegistroTicket()">
                                             <input type="button" name="btn_del_ticket" id="btn_del_ticket" value="Elimina" class="k-button"> 
                                             </td>
                                             <td></td>
                                             <td width="80">&nbsp;
                                             
                                             </td>
										</tr>
										
										
                                        <tr valign="top" class="tblFormVicios">
										  <td align="right">&nbsp;</td>
										  <td colspan="5"><table id="dg_vicios" cellpadding="0" cellspacing="0">
										    <thead>
										      <tr>
									
										        <th style="text-align:center" width="4%">Cvo</th>
										        <th style="text-align:center" width="26%">* Zona /&Aacute;rea</th>
										        <th style="text-align:center" width="40%">* Observaciones/ Descripci&oacute;n</th>
										        <th style="text-align:center" width="30%">Reporte Fotogr&aacute;fico</th>
									          </tr>
									        </thead>
										    <tbody>
									        </tbody>
									      </table>
									     </td>
									  </tr>
									  
									  
									  
									  
									   <tr valign="top" class="tblFormTicket">
										  <td align="right">&nbsp;</td>
										  <td colspan="5"><table id="dg_ticket" cellpadding="0" cellspacing="0">
										    <thead>
										      <tr>
									
										        <th style="text-align:center" width="4%">Cvo</th>
										        <th style="text-align:center" width="96%">* Observaciones/ Descripci&oacute;n</th>
									          </tr>
									        </thead>
										    <tbody>
									        </tbody>
									      </table>
									     </td>
									  </tr>
                                         <tr>
                                           <td align="right">&nbsp;</td>
                                           <td colspan="5">&nbsp;</td>
                                         </tr>
                                         <tr>
											<td align="right">* Asunto:</td>
											<td colspan="5">
												<input type="text" id="asunto" name="asunto" style="text-transform: uppercase; width: 20.60cm" class="k-textbox" onblur="javascript:this.value=this.value.toUpperCase();" size="111" maxlength="255" onkeypress="return letrasnumeros(event)"/>
											</td>
										</tr>
														
										<tr valign="top">
										  <td align="right"> Observaciones Generales:</td>
										  <td colspan="5"><textarea id="observaciones" name="observaciones" style="text-transform: uppercase; width:20.47cm; font-size: 10px !important; font-family:Century Gothic" onblur="javascript:this.value=this.value.toUpperCase().trim();" rows="5" cols="82" onkeypress="return letrasnumeros(event)"></textarea></td>
                                         
    </tr>
										<tr valign="top" class="tblFormVicios">
											<td colspan="6">
												<input type="button" id="Guardar" class="k-button" onclick="if(validaIngresoModulos('TICKET', 'PERMISO_C')){registrarTicketConstruccion()}else{$('#mensajes_main').html('Sin permisos para este modulo')}" value="Guardar"/>
												<input type="button" id="Cancelar" class="k-button" onclick="JavaScript:window.location='<%= request.getContextPath()%>/Escritorio.htm';" value="Cancelar"/>

											</td>
										</tr>
										
										<tr valign="top" class="tblFormTicket">
											<td colspan="6">
												<input type="button" id="Guardar" class="k-button" onclick="if(validaIngresoModulos('TICKET', 'PERMISO_C')){registrarTicketNoConstruccion()}else{$('#mensajes_main').html('Sin permisos para este modulo')}" value="Guardar"/>
												<input type="button" id="Cancelar" class="k-button" onclick="JavaScript:window.location='<%= request.getContextPath()%>/Escritorio.htm';" value="Cancelar"/>
												<input type="hidden" id="Accion" name="accion" value="construccion"/>
											</td>
										</tr>
									</tbody>
								</table>
                  			</form>
                  		</div> 
                  	</div> 
                  </td>
				</tr>
                
          	</table>
          </td>
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
   <div id="mensajes_main"></div>
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


<div id="windowGridBusquedaClienteRegistroTicket"></div>

</body>
</html>