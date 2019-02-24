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
<title>Administracion de Ubicaciones y Mapeo de Imagenes</title>
    
    <script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
    <script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
    <script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js"></script>  
    <script src="<%=basePath %>/js/jquery.blockUI.js"></script>     

    <script type="text/javascript">
            var contexPath="<%=basePath %>";
     </script> 
     <script src="<%=basePath %>/js/escritorio/EscritorioJQ.js"></script>
     <script src="<%=basePath %>/js/ubicaciones/UbicacionesJQ.js"></script>
	 <script src="<%=basePath %>/js/mapeo/MapeoJQ.js"></script>  
	 <script src="<%=basePath %>/js/utils.js"></script> 
	     
    <script>
	var open_menu=0;
    var ddlsup;
	var ddlint;
	var ddlequ;
	
	var radio_ut=1;
	
	$(document).ready(function(){
		
			
		$("#cmb_ubicaciones_sup").kendoDropDownList({	    		
		     	dataTextField: "ubicacionDescripcion",
		     	dataValueField: "ubicacionClave",
		     	dataSource: [],
		     	change: onChangeUTS	   
	 	});	
	 	
	 	$("#cmb_ubicaciones_int").kendoDropDownList({	    		
		     	dataTextField: "ubicacionDescripcion",
		     	dataValueField: "ubicacionClave",
		     	dataSource: [],
		     	change: onChangeUTSpiso   
	 	});
	 	
		$("#cmb_ubicaciones_equipos").kendoDropDownList({	    		
		     	dataTextField: "id_equnrx",
		     	dataValueField: "id_equnr",
		     	dataSource: [],
		     	change: onChangeUTSequipo	   
	 	});
		
		ddlsup = $("#cmb_ubicaciones_sup").data("kendoDropDownList");
		ddlint = $("#cmb_ubicaciones_int").data("kendoDropDownList");
		ddlequ = $("#cmb_ubicaciones_equipos").data("kendoDropDownList");
		/*var locations = ["/MultipleImagenMapeo.htm", "http://adobe.com"]; 
        var len = locations.length; 
        var iframe = $('#iframe_maper'); 
        var i = 0; 
        setInterval(function () { 
            $(iframe).attr('src', locations[++i % len]); 
        }, 30000); */
		onVentanaWait("Espere ", true)
		
		getUTSAdmin("faces");
		
		
		 
		var imguts1 = $("#btnimguts").bind("click", function() {        
									var datosUPisoList = $("#cmb_ubicaciones_sup").data('kendoDropDownList');
									var itemutsp = datosUPisoList.dataItem();                            
									var urlmapimgif=contexPath +'/MapeoImagenes.htm?idUbicacion='+itemutsp.ubicacionClave+'&idUTS=&from=uts1&rand='+Math.random();
									
									var windowimg;	    
		                            windowimg = $("#winchaneimg");

									if (!windowimg.data("kendoWindow")) {									
										
									}
									else
									{
										windowimg.data("kendoWindow").content("Cargando datos espere");
									}
									
									windowimg.kendoWindow({
											width: "750px",
											height: "500px",
											title: "Administracion de imagenes UTS (Fase)",
											actions: ["Close"],
											content: ""+urlmapimgif+"",
											modal: true,
	    									resizable: false
										});
									windowimg.data("kendoWindow").open();
									windowimg.data("kendoWindow").center();
									// windowimg.content(urlmapimgif);
                                });

                    var onClose = function() {
                        //undo.show();
                    }
			var mapeo1 = $("#btnmaputs").bind("click", function() {
									
									var windowmapimg;	    
		                            windowmapimg = $("#winchaneimg1");
		                            
                                    if(uts_num.length>0)
									{															
	    								var windowmapimg;
										windowmapimg = $("#winchaneimg1");
										
										var datosUSTList = $("#cmb_ubicaciones_sup").data('kendoDropDownList');
										var itemuts = datosUSTList.dataItem();
										var nombre_img = uts_num[0].nombreImagen; 
										var urlmapimgif=contexPath +'/MapeoImagenComponent.htm?idUbicacion='+itemuts.ubicacionClave+'&nom_imagen='+nombre_img+'&from=uts1&rand='+Math.random();
										
										if (!windowmapimg.data("kendoWindow")) {									
										
										}
										else
										{
											windowmapimg.data("kendoWindow").content("Cargando datos espere");
										}
										
										windowmapimg.kendoWindow({
											width: "750px",
											height: "500px",
											title: "Administracion de imagenes UTS (Fase)",
											actions: ["Close"],
											content: ""+urlmapimgif+"",
											modal: true,
											resizable: false
										});
										windowmapimg.data("kendoWindow").open();
										windowmapimg.data("kendoWindow").center();
									}
									else
									{
										 kendoConsole.log("No existe ninguna imagen para mapear","warning");	
									}
								    //windowimg.content(urlmapimgif);
                                });
			var duplica = $("#btnduplicate").bind("click", function() {	
									var windowimg;	    
		                            windowimg = $("#winchaneimg");	
		                            							
									var urlmapimgif=contexPath +'/MapeoUbicacionSimilares.htm?&rand='+Math.random();
									
									if (!windowimg.data("kendoWindow")) {									
										
									}
									else
									{
										windowimg.data("kendoWindow").content("Cargando datos espere");
									}
									
									windowimg.kendoWindow({
										width: "750px",
										height: "500px",
										title: "Niveles similares a los ya mapeados (Piso)",
										actions: ["Close"],
										content: ""+urlmapimgif+"",
										modal: true,
    									resizable: false
									});
									windowimg.data("kendoWindow").open();
									windowimg.data("kendoWindow").center();
									// windowimg.content(urlmapimgif);
                                });
                                
                                
            /* var mapeo2 = $("#btnmaputm").bind("click", function() {
                                    if(uts_num.length>0)
									{
										var datosUSTList = $("#cmb_ubicaciones_int").data('kendoDropDownList');
										var itemuts = datosUSTList.dataItem();
										var nombre_img = uts_num[0].nombreImagen; 
										var urlmapimgif=contexPath +'/MapeoImagenComponent.htm?idUbicacion='+itemuts.ubicacionClave+'&nom_imagen='+nombre_img+'&from=uts2';
										windowmapimg.kendoWindow({
											width: "750px",
											height: "500px",
											title: "Administracion de imagenes UTS (Piso)",
											actions: ["Close"],
											content: ""+urlmapimgif+"",
											modal: true,
											resizable: false
										});
										windowmapimg.data("kendoWindow").open();
										windowmapimg.data("kendoWindow").center();
									}
									else
									{
										 kendoConsole.log("No existe ninguna imagen para mapear","warning");	
									}
									// windowimg.content(urlmapimgif);
                                }); */
                                
            var imguts3 = $("#btnmagutl").bind("click", function() {                                    
                        var datosUPisoList = $("#cmb_ubicaciones_int").data('kendoDropDownList');
						var itemutsp = datosUPisoList.dataItem();
						var datosEquipoList = $("#cmb_ubicaciones_equipos").data('kendoDropDownList');
						var itemutequipo = datosEquipoList.dataItem();
									
						var urlmapimgif=contexPath +'/MapeoImagenes.htm?idUbicacion='+itemutsp.ubicacionClave+'&idUTS='+itemutequipo.id_equnr+'&from=uts2&rand='+Math.random();
						
						var windowimg;
		                windowimg = $("#winchaneimg1");
						if (!windowimg.data("kendoWindow")) {									
										
						}
						else
						{
							windowimg.data("kendoWindow").content("Cargando datos espere");
						}
						
						windowimg.kendoWindow({
							width: "750px",
							height: "500px",
							title: "Administracion de imagenes UTS (Equipos)",
							actions: ["Close"],
							content: ""+urlmapimgif+"",
							modal: true,
 									resizable: false
						});
						windowimg.data("kendoWindow").open();
						windowimg.data("kendoWindow").center();
						// windowimg.content(urlmapimgif);
                     });   
                     
           var mapeo3 = $("#btnmaputl").bind("click", function() {
                        if(uts_num.length>0)
						{
							var datosUSTList = $("#cmb_ubicaciones_equipos").data('kendoDropDownList');
							var itemuts = datosUSTList.dataItem();
							var nombre_img = uts_num[0].nombreImagen; 
							var urlmapimgif=contexPath +'/MapeoImagenComponent.htm?idUbicacion='+itemuts.id_equnr+'&nom_imagen='+nombre_img+'&from=uts2&rand='+Math.random();
							
							var windowmapimg;
		                    windowmapimg = $("#winchaneimg1");
							if (!windowmapimg.data("kendoWindow")) {									
										
							}
							else
							{
								windowmapimg.data("kendoWindow").content("Cargando datos espere");
							}
							
							windowmapimg.kendoWindow({
								width: "900px",
								height: "500px",
								title: "Administracion de imagenes UTS (Equipos)",
								actions: ["Close"],
								content: ""+urlmapimgif+"",
								modal: true,
								resizable: false
							});
							windowmapimg.data("kendoWindow").open();
							windowmapimg.data("kendoWindow").center();
						}
						else
						{
							 kendoConsole.log("No existe ninguna imagen para mapear","warning");	
						}
						// windowimg.content(urlmapimgif);
                    }); 
                    
       var equiposImagenes = $("#btneq").bind("click", function() {                        
							var datosUPisoList = $("#cmb_ubicaciones_sup").data('kendoDropDownList');
							var itemutsp = datosUPisoList.dataItem();                            
							var urlmapimgif=contexPath +'/UploadTiposEquipos.htm?idUbicacion='+itemutsp.ubicacionClave+'&rand='+Math.random();
							
							var windoimgequ;
		                    windoimgequ = $("#winimagenesequipos");
							if (!windoimgequ.data("kendoWindow")) {									
										
							}
							else
							{
								windoimgequ.data("kendoWindow").content("Cargando datos espere");
							}
							
							windoimgequ.kendoWindow({
								width: "500px",
								height: "250px",
								title: "Administracion imagenes (Equipos)",
								actions: ["Close"],
								content: ""+urlmapimgif+"",
								modal: true,
								resizable: false
							});
							windoimgequ.data("kendoWindow").open();
							windoimgequ.data("kendoWindow").center();
												
						// windowimg.content(urlmapimgif);
                    });             
	});
	
	function enableDisableNivel(nivel, bandera)
	{
		if(nivel==1 && bandera)
		{		
			radio_ut=1;
			//ddlsup.dataSource.data([]);
			ddlint.dataSource.data([]);
			ddlequ.dataSource.data([]);
			$("input:radio[name=rdo_nivel]")[2].disabled = true;
		    
		    ddlsup.enable();
			$("#btnimguts").removeAttr("disabled");
			$("#btnmaputs").removeAttr("disabled");
			
			ddlint.enable(false);
			$("#btnimgutm").attr("disabled", "disabled");
			$("#btnmaputm").attr("disabled", "disabled");
			
			ddlequ.enable(false);
			$("#btnmagutl").attr("disabled", "disabled");
			$("#btnmaputl").attr("disabled", "disabled");
			
			getUTSAdmin("faces"); 			
		}
		else if(nivel==2 && bandera)
		{
			radio_ut=2;
		    //ddlsup.dataSource.data([]);
			//ddlint.dataSource.data([]);
			ddlequ.dataSource.data([]);
			$("input:radio[name=rdo_nivel]")[2].disabled = false;
			
			ddlsup.enable(false);
			$("#btnimguts").attr("disabled", "disabled");
			$("#btnmaputs").attr("disabled", "disabled");
			
			ddlint.enable();
			$("#btnimgutm").attr("disabled", "disabled");
			$("#btnmaputm").attr("disabled", "disabled");
			
			ddlequ.enable(false);
			$("#btnmagutl").attr("disabled", "disabled");
			$("#btnmaputl").attr("disabled", "disabled");
			
		    getUTSAdmin("pisos");
			
		}
		else if(nivel==3 && bandera)
		{
			radio_ut=3;
			ddlsup.enable(false);
			$("#btnimguts").attr("disabled", "disabled");
			$("#btnmaputs").attr("disabled", "disabled");
			
			ddlint.enable(false);
			$("#btnimgutm").attr("disabled", "disabled");
			$("#btnmaputm").attr("disabled", "disabled");
			
			ddlequ.enable();
			$("#btnmagutl").removeAttr("disabled");
			$("#btnmaputl").removeAttr("disabled");
		
			getUTSAdmin("equipos");			
		}
	}
	function closeMapingWindow()
	{	
		if($("#winchaneimg").data("kendoWindow")!=undefined)
		{
			$("#winchaneimg").data("kendoWindow").close();
		}
		
		if($("#winchaneimg1").data("kendoWindow")!=undefined)
		{
			$("#winchaneimg1").data("kendoWindow").close();
		}
	}
    
    
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
                <td width="424" id="TituloModulo">Administracion de Ubicaciones y Mapeo de Imagenes</td>
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
                      
                      <table width="1110" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="925" align="center"><iframe id="iframe_maper"
						name="iframe_maper"
						src=""
						scrolling="no"
						noresize="noresize"
						frameborder="no"
						width="890"
						height="610"
						allowtransparency="true"></iframe></td>
          <td width="185"><table width="80%" height="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="303" height="90">
              <div class="k-block" style="height:90px; width:100%"><div class="k-header" align="center">Primer nivel de unidad tecnica</div>

              <table width="176" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="33" height="36" align="center"><input type="radio" name="rdo_nivel" id="rdo_nivel" value="primero" checked onClick="enableDisableNivel(1, this.checked)"></td>
                  <td><select name="cmb_ubicaciones_sup" id="cmb_ubicaciones_sup" style="width: 140px;"/>
                  </td>
                  </tr>
                <tr>
                  <td height="0" colspan="2" align="center"><table width="148" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><input type="button" class="k-button" name="btnimguts" id="btnimguts" value="Imagen"></td>
                      <td><input type="button" class="k-button" name="btnmaputs" id="btnmaputs" value="Mapeo"></td>
                    </tr>
                </table></td>
                  </tr>
              </table>
                </div>
                </td>
              </tr>
            <tr>
              <td height="23">&nbsp;</td>
              </tr>
            <tr>
              <td height="90">
              <div class="k-block" style="height:90px; width:100%">
              <div class="k-header" align="center">Segundo nivel de unidad tecnica</div>
              <table width="176" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="33" height="37" align="center"><input type="radio" name="rdo_nivel" id="rdo_nivel" value="segundo" onClick="enableDisableNivel(2,this.checked)"></td>
                  <td><select name="cmb_ubicaciones_int" id="cmb_ubicaciones_int" style="width: 140px;" disabled/>                    <!--input type="button" class="k-button" name="btnimgutm" id="btnimgutm" value="Imagen" disabled--><!--input type="button" class="k-button" name="btnmaputm" id="btnmaputm" value="Mapeo" disabled--></td>
                  </tr>
                <tr>
                  <td height="24" colspan="2" align="center"><table width="148" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td align="center"><input type="button" class="k-button" name="btnduplicate" id="btnduplicate" value="Duplicar"></td>
                      </tr>
                  </table></td>
                  </tr>
              </table>
              </div>
              </td>
            </tr>
            <tr>
              <td height="23">&nbsp;</td>
            </tr>
            <tr>
              <td height="100">
              <div class="k-block" style="height:90px; width:100%"><div class="k-header" align="center">Tercer nivel de unidad tecnica</div>
              <table width="176" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="33" height="36" align="center"><input type="radio" name="rdo_nivel" id="rdo_nivel" value="tercero" onClick="enableDisableNivel(3,this.checked)" disabled></td>
                  <td><select name="cmb_ubicaciones_equipos" id="cmb_ubicaciones_equipos" style="width: 140px;" disabled/>
                  </td>
                  </tr>
                <tr>
                  <td height="24" colspan="2" align="center"><table width="148" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><input class="k-button" type="button" name="btnmagutl" id="btnmagutl" value="Imagen" disabled></td>
                      <td><input class="k-button" type="button" name="btnmaputl" id="btnmaputl" value="Mapeo" disabled></td>
                    </tr>
                  </table></td>
                  </tr>
              </table>
              </div>
              </td>
            </tr>
            <tr>
              <td height="23"></td>
            </tr>
            <tr>
              <td height="23"><div class="k-block" style="height:90px" align="center"><div class="k-header" align="center">Imagenes para equipos</div>
              <br>
	              	<input class="k-button" type="button" name="btneq" id="btneq" value="ImagenesEquipos" align="center">
	              </div></td>
            </tr>
            <tr>
              <td height="168">&nbsp;
	              
              </td>
            </tr>
          </table></td>
        </tr>
      </table>
                      
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
<div id="winchaneimg"></div>
<div id="winchaneimg1"></div>
<div id="winimagenesequipos"></div>
</body>
</html>