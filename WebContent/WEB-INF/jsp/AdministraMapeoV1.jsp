<%@ page session="true" %>
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
<title>Login (Area restringida)</title>

<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/js/jquery.blockUI.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>

<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/ubicaciones/UbicacionesJQ.js"></script>
<script src="<%=basePath %>/js/mapeo/MapeoJQ.js"></script>
<script>
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
		$.blockUI({ css: {             
		    border: 'none',             
		    padding: '15px',             
		    backgroundColor: '#000',             
		    '-webkit-border-radius': '10px',             
		    '-moz-border-radius': '10px',             
		    opacity: .5,             
		    color: '#fff'         
		    },message: '<h3><img src="'+contexPath+'/images/loader/ajax_loader.gif" /> Cargando datos espere ...</h3>', 
		});
		
		getUTSAdmin("faces");
		
		
		 
		var imguts1 = $("#btnimguts").bind("click", function() {        
									var datosUPisoList = $("#cmb_ubicaciones_sup").data('kendoDropDownList');
									var itemutsp = datosUPisoList.dataItem();                            
									var urlmapimgif=contexPath +'/MapeoImagenes.htm?idUbicacion='+itemutsp.ubicacionClave+'&idUTS=&from=uts1';
									
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
										var urlmapimgif=contexPath +'/MapeoImagenComponent.htm?idUbicacion='+itemuts.ubicacionClave+'&nom_imagen='+nombre_img+'&from=uts1';
										
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
		                            							
									var urlmapimgif=contexPath +'/MapeoUbicacionSimilares.htm';
									
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
									
						var urlmapimgif=contexPath +'/MapeoImagenes.htm?idUbicacion='+itemutsp.ubicacionClave+'&idUTS='+itemutequipo.id_equnr+'&from=uts2';
						
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
							var urlmapimgif=contexPath +'/MapeoImagenComponent.htm?idUbicacion='+itemuts.id_equnr+'&nom_imagen='+nombre_img+'&from=uts2';
							
							var windowmapimg;
		                    windowmapimg = $("#winchaneimg1");
							if (!windowmapimg.data("kendoWindow")) {									
										
							}
							else
							{
								windowmapimg.data("kendoWindow").content("Cargando datos espere");
							}
							
							windowmapimg.kendoWindow({
								width: "750px",
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
							var urlmapimgif=contexPath +'/UploadTiposEquipos.htm?idUbicacion='+itemutsp.ubicacionClave;
							
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
			document.formulario.rdo_nivel[2].disabled = true;
		    
		    ddlsup.enable();
			$("#btnimguts").removeAttr("disabled");
			$("#btnmaputs").removeAttr("disabled");
			
			ddlint.enable(false);
			$("#btnimgutm").attr("disabled", "disabled");
			$("#btnmaputm").attr("disabled", "disabled");
			
			ddlequ.enable(false);
			$("#btnmagutl").attr("disabled", "disabled");
			$("#btnmaputl").attr("disabled", "disabled");
			
			$.blockUI({ css: {             
		    border: 'none',             
		    padding: '15px',             
		    backgroundColor: '#000',             
		    '-webkit-border-radius': '10px',             
		    '-moz-border-radius': '10px',             
		    opacity: .5,             
		    color: '#fff'         
		    },message: '<h3><img src="'+contexPath+'/images/loader/ajax_loader.gif" /> Cargando datos espere ...</h3>', 
			});
			
			getUTSAdmin("faces"); 			
		}
		else if(nivel==2 && bandera)
		{
			radio_ut=2;
		    //ddlsup.dataSource.data([]);
			//ddlint.dataSource.data([]);
			ddlequ.dataSource.data([]);
			document.formulario.rdo_nivel[2].disabled = false;
			
			ddlsup.enable(false);
			$("#btnimguts").attr("disabled", "disabled");
			$("#btnmaputs").attr("disabled", "disabled");
			
			ddlint.enable();
			$("#btnimgutm").attr("disabled", "disabled");
			$("#btnmaputm").attr("disabled", "disabled");
			
			ddlequ.enable(false);
			$("#btnmagutl").attr("disabled", "disabled");
			$("#btnmaputl").attr("disabled", "disabled");
			
			$.blockUI({ css: {             
		    border: 'none',             
		    padding: '15px',             
		    backgroundColor: '#000',             
		    '-webkit-border-radius': '10px',             
		    '-moz-border-radius': '10px',             
		    opacity: .5,             
		    color: '#fff'         
		    },message: '<h3><img src="'+contexPath+'/images/loader/ajax_loader.gif" /> Cargando datos espere ...</h3>', 
			});
			
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
			
			$.blockUI({ css: {             
		    border: 'none',             
		    padding: '15px',             
		    backgroundColor: '#000',             
		    '-webkit-border-radius': '10px',             
		    '-moz-border-radius': '10px',             
		    opacity: .5,             
		    color: '#fff'         
		    },message: '<h3><img src="'+contexPath+'/images/loader/ajax_loader.gif" /> Cargando datos espere ...</h3>', 
			});
		
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

</script>

<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo/styles/examples.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>/kendo/styles/main.css" rel="stylesheet" type="text/css"/>


</head>
<body>
<form name="formulario">
	<table width="999" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td width="696" align="center"><iframe id="iframe_maper"
						name="iframe_maper"
						src=""
						scrolling="no"
						noresize="noresize"
						frameborder="no"
						width="650"
						height="550"></iframe></td>
          <td width="303"><table width="80%" height="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="303" height="100">
              <div class="k-block" style="height:90px"><div class="k-header" align="center">Primer nivel de unidad tecnica</div>

              <table width="304" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="33" height="65" align="center"><input type="radio" name="rdo_nivel" id="rdo_nivel" value="primero" checked onClick="enableDisableNivel(1, this.checked)"></td>
                  <td width="146"><select name="cmb_ubicaciones_sup" id="cmb_ubicaciones_sup" style="width: 140px;"/>
                  </td>
                  <td width="61"><input type="button" class="k-button" name="btnimguts" id="btnimguts" value="Imagen"></td>
                  <td width="64"><input type="button" class="k-button" name="btnmaputs" id="btnmaputs" value="Mapeo"></td>
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
              <div class="k-block" style="height:90px">
              <div class="k-header" align="center">Segundo nivel de unidad tecnica</div>
              <table width="302" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="33" height="69" align="center"><input type="radio" name="rdo_nivel" id="rdo_nivel" value="segundo" onClick="enableDisableNivel(2,this.checked)"></td>
                  <td width="147"><select name="cmb_ubicaciones_int" id="cmb_ubicaciones_int" style="width: 140px;" disabled/>
                  </td>
                  <td width="62"><!--input type="button" class="k-button" name="btnimgutm" id="btnimgutm" value="Imagen" disabled--><input type="button" class="k-button" name="btnduplicate" id="btnduplicate" value="Duplicar"></td>
                  <td width="60"><!--input type="button" class="k-button" name="btnmaputm" id="btnmaputm" value="Mapeo" disabled--></td>
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
              <div class="k-block" style="height:90px"><div class="k-header" align="center">Tercer nivel de unidad tecnica</div>
              <table width="300" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="33" height="70" align="center"><input type="radio" name="rdo_nivel" id="rdo_nivel" value="tercero" onClick="enableDisableNivel(3,this.checked)" disabled></td>
                  <td width="145"><select name="cmb_ubicaciones_equipos" id="cmb_ubicaciones_equipos" style="width: 140px;" disabled/>
                  </td>
                  <td width="66"><input class="k-button" type="button" name="btnmagutl" id="btnmagutl" value="Imagen" disabled></td>
                  <td width="56"><input class="k-button" type="button" name="btnmaputl" id="btnmaputl" value="Mapeo" disabled></td>
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
	              	<input class="k-button" type="button" name="btneq" id="btneq" value="ImagenesEquipos" align="center">
	              </div></td>
            </tr>
            <tr>
              <td height="168">
	              &nbsp;
              </td>
            </tr>
          </table></td>
        </tr>
      </table>
  <div id="buttomBar">
  	<div class="console"/>
  </div>
	<div id="winchaneimg"></div>
    <div id="winchaneimg1"></div>
    <div id="winimagenesequipos"></div>
   </form>
</body>
</html>