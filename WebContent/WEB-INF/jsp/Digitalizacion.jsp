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
<title>Visualizacion</title>
    
    <script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
    <script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
    <script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js"></script>   

    <script type="text/javascript">
            var contexPath="<%=basePath %>";
     </script> 
     <script src="<%=basePath %>/js/ubicaciones/UbicacionesJQ.js"></script>
     <script src="<%=basePath %>/js/escritorio/EscritorioJQ.js"></script>      
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
		
		var folderAdd=[];
		var split_tree;
		$(document).ready(function(){
			 $("#tr_arbol").kendoTreeView({
				 select: onSelectArbol	  					 
			 }).data("kendoTreeView");
			 
			 $("#tr_arbol_files").kendoTreeView({
				 select: onSelectArbolFiles				 
			 }).data("kendoTreeView");
			
			 split_tree=$("#sp_arbol").kendoSplitter({
	                        panes: [
	                            { collapsible: true, size: "200px" },
	                            { collapsible: true, size: "200px" },
	                            { collapsible: true, size: "100%" }
	                        ],
	                        select: onSelectTreeMain
	                    }).data("kendoSplitter");
			getArbol();

			var rInit=1;
			$("#filtro").on("click",function(){
				$("#winFiltr").data("kendoWindow").center();
				$("#winFiltr").data("kendoWindow").open();
				
				if(rInit==1){
					fillComboRegistroReporte("faces");
					rInit=0;
				}
			});
			

			$("#Limpiar").on("click",function(){
				$("#faseReporte").data('kendoComboBox').value(null);
				$("#equipoReporte").data('kendoComboBox').value(null);
				$("#subCarpeta").data('kendoComboBox').value(null);
			});

			$("#buscar").on("click",function(){
				$("#winFiltr").data("kendoWindow").close();
				$("#faseReporte").data('kendoComboBox');
				  var itmEq="";
				  var itmEq="";
				  var datosFaseList     = $("#faseReporte").data('kendoComboBox');
				  var itemFases         = datosFaseList.dataItem();
				  var datosEquiposList  = $("#equipoReporte").data('kendoComboBox');
				  var itemEquipos       = datosEquiposList.dataItem();
				  var subCarpeta = $("#subCarpeta").data("kendoComboBox");
				  if(itemEquipos){itmEq=itemEquipos.id_equnrx;}
				  if(itemFases){itmUb=itemFases.ubicacionDescripcion;}
				  
				  getArbol(itmUb , itmEq, subCarpeta.value());
			});

			$("#winFiltr").kendoWindow({
                width: "298px",
                modal: true,
                title: "Parametros de Selección",
                resizable: false,
                visible: false,
                actions: [
                    "Close"
                ]//,close: onClose
            });

			$("#subCarpeta").kendoComboBox({
                dataTextField: "text",
                dataValueField: "value",
                dataSource: [
					{ text: "",                  value: "" },
                    { text: "ATIENDE CLIENTE",   value: "01" },
                    { text: "CIERRE DE VENTA",   value: "02" },
                    { text: "RECIBE PRODUCTO",   value: "03" },
                    { text: "ESCRITURACIÓN",     value: "04" },
                    { text: "ENTREGA PRODUCTO",  value: "05" },
                    { text: "ATIENDE GARANTÍAS", value: "06" },
                    { text: "DOCUMENTOS",        value: "99" },
                    { text: "LALDD",             value: "07" }
                ],
                filter: "contains",
                suggest: true
                //index: 3
            });

			$("#faseReporte").kendoComboBox({
				dataTextField: "text",
				dataValueField: "id",
				dataSource: [],
				filter: "contains",
			    change: function(){fillComboRegistroReporte("equipos");},
				suggest: true
		    });

			$("#equipoReporte").kendoComboBox({
				dataTextField: "id_equnrx",
				dataValueField: "id_equnr",
				dataSource: [],
				filter: "contains",
				//change: onChangeEquipoRegistroReporte,
				suggest: true
		    });
			
			function fillComboRegistroReporte (accion) {			
				  var datosFaseList     = $("#faseReporte").data('kendoComboBox');
				  var itemFases         = datosFaseList.dataItem();
				  var datosEquiposList  = $("#equipoReporte").data('kendoComboBox');
				  var itemEquipos       = datosEquiposList.dataItem();

				  var iduts = "";
				  if(accion=="faces")
				  {
					 /*if(itemFases != undefined)
					  iduts=itemFases.ubicacionClave;*/
				  }
				  else if(accion=="equipos")
				  {
					  if(itemFases != undefined)
						  iduts=itemFases.ubicacionClave;
				  }
				  //onVentanaWait("Espere ", true)
				  
				  $.ajax({  
				    type: "POST", 
				    cache : false,
				    async:  false,
				    url: contexPath + "/ticket/LlenaCombosInicialesRegistroTicket.htm",  
				    data: "accion="+accion+"&i_id_ut_current="+iduts, 
				    success: function(response){
				      // we have the response 
				      if(response.respGetUnidadesTecnicasSuperiores != undefined &&
				         response.respGetUnidadesTecnicasSuperiores.mensaje != undefined &&
				    	 response.respGetUnidadesTecnicasSuperiores.mensaje == "SUCCESS"){	
				    	  //$.unblockUI()
				    	  var uts_num=response.respGetUnidadesTecnicasSuperiores.objUbicacionesList;
				    	  var ute_num=response.respGetUnidadesTecnicasSuperiores.objEquiposList;
				    	  carpetas_dig = response.respGetUnidadesTecnicasSuperiores.carpetasDigitalizacion;
				    	  carpetas_dig_ = response.respGetUnidadesTecnicasSuperiores.carpetasDigitalizacion;
				    	  
				    	  if(uts_num.length>=1 || ute_num.length>=1)
				    	  {	  	    		  
				    		  if(accion=="faces")
				    		  {  
					    		  $("#mensajes_main").html("Se encontraron "+uts_num.length+ " registros");
					    		  
					    		  datosFaseList.dataSource.data([]);     
					    		  datosFaseList.dataSource.data(uts_num);
					    		  datosFaseList.select(0);
					    		  itemFases = datosFaseList.dataItem();
					    		  
					    		  //$("#txt_desc_fase").html(itemFases.text);
					    		  fillComboRegistroReporte("equipos");
				    		  }
				    		  else if(accion=="equipos")
				    		  {
				    			  $("#mensajes_main").html("Se encontraron "+ute_num.length+ " registros");
					    		  
				    			  datosEquiposList.dataSource.data([]);     
				    			  datosEquiposList.dataSource.data(ute_num);
				    			  datosEquiposList.select(0);
				    			  itemEquipos = datosEquiposList.dataItem();
				    			  //$("#txt_desc_equ").html(itemEquipos.id_equnrx);		    			  
				    			  
//				    			  datosTiposList.dataSource.data([]);     
//				    			  datosTiposList.dataSource.data(getProcesos("proceso","00"));
//				    			  datosTiposList.select(0);	
//				    			  itemTipos = datosTiposList.dataItem();
//				    			  
//				    			  datosSubTiposList.dataSource.data([]);     
//				    			  datosSubTiposList.dataSource.data(getProcesos("",itemTipos.id_ticket_file));
//				    			  datosSubTiposList.select(0);
				    		  }
				    	  }
				    	  else
				    	  {
				    		  //$.unblockUI()
				    		  $("#mensajes_main").html("No se encontraron ubicaciones para esta sesion");	    		  
				    	  }
				    	  
				      }else{
				    	  //$.unblockUI()
				    	  if (response.respGetUnidadesTecnicasSuperiores != undefined &&
				    		  response.respGetUnidadesTecnicasSuperiores.descripcion != undefined) {
					    	  $("#mensajes_main").html(response.respGetUnidadesTecnicasSuperiores.descripcion);
				    	  }
				      }	      
				    },  
				    error: function(e){  
				    	//$.unblockUI()
				    	$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
				    }  
				  });  
				}
			
		});	
</script>
    <link href="<%=basePath %>/jixedbar/default/jx.stylesheet.css" type="text/css"  rel="stylesheet" />
	<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />
     

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
                <td width="424" id="TituloModulo">Digitalizacion/Visualizacion</td>
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
                <td width="65"><input type="button" id="filtro" class="k-button" value="Seleccionar" style="margin-bottom:3px;" /></td>
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
                      <td height="620" valign="top"> 
                      
                      <table width="1110" border="0" align="center" cellpadding="0" cellspacing="0">
					    <tr>
					        <td height="590">
					                
					                <div id="sp_arbol" style="height: 100%">
						                <div style="height: 100%; width: 100px;"> 
						                	<div class="demo-section" id="arbol_content" >
												<ul id="tr_arbol"> 
												</ul>
								            </div>                	
						                </div>
						                 <div style="height: 100%; width: 100px;"> 
						                	<div class="demo-section" id="arbol_archivos" >
												<ul id="tr_arbol_files"> 
												</ul>
								            </div>                	
						                </div>
						                <div style="height: 100%; width: 100%;" id="pdfContent">
						                
						                </div>
					                
					            </div>
							</td>
					    </tr>
					</table>
					<style scoped>
			                
			                #tr_arbol .k-sprite {
			                    background-image: url("<%=basePath %>/kendo/examples/content/web/treeview/coloricons-sprite.png");
			                }
							
							#tr_arbol_files .k-sprite {
			                    background-image: url("<%=basePath %>/kendo/examples/content/web/treeview/coloricons-sprite.png");
			                }
			                
			                .rootfolder { background-position: 0 0; }
			                .folder { background-position: 0 -16px; }
			                .pdf { background-position: 0 -32px; }
			                .html { background-position: 0 -48px; }
			                .image { background-position: 0 -64px; }
			
			        </style>
			                                            
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


			<div id="winFiltr">
					<fieldset style="margin-bottom: 5px">
						
						<table width="100%" border="0" align="left">
							<tr>
								<td>Fase</td>
								<td>
									<select id="faseReporte" name="faseReporte" style="width: 5cm;">
									</select>
								</td>
								<td>
									<div id="txt_desc_fase"></div>&nbsp;
								</td>
							</tr>
							<tr>
								<td>Equipo</td>
								<td>
									<select id="equipoReporte" name="equipoReporte" style="width: 5cm;">
									</select>
								</td>
								<td>
									<div id="txt_desc_equ"></div>&nbsp;
								</td>
							</tr>
							<tr>
								<td>Subcarpeta</td>
								<td>
									<select id="subCarpeta" name="subCarpeta" style="width: 5cm;">
									</select>
								</td>
								<td>
								</td>
							</tr>
						</table>
					</fieldset>
                
                
                <input type="button" class="k-button" value="Buscar" id="buscar" />
                <input type="button" class="k-button" value="Limpiar" id="Limpiar" />
            </div>

</body>
</html>