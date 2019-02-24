/**
 * 
 */
var carp_arbol="";
var carpetas_dig=[];
var carpetas_dig_=[];
var obj_arbol;
var obj_arbol_b;
var nodo_arbol="";
var last_node="";

function formatNumber(num) { 
    var p = num.toFixed(2).split("."); 
    return "$" + p[0].split("").reverse().reduce(function(acc, num, i, orig) { 
        return  num + (i && !(i % 3) ? "," : "") + acc; 
    }, "") + "." + p[1]; 
} 

function cargaProcesoInicial()
{
	$("#main_content").empty();
	$("#main_content").html('<div id="load_content" style="height: 96%; width: 100%;"></div>');
	$('#load_content').load(contexPath+"/UbicacionTS.htm?rand="+Math.random(), function(response, status, xhr) {
		  if (status == "success") {
			  window.parent.$("#mensajes_main").html("Contenido principal cargado");
		  }
		  else
		  {
			  var msg = "Error: ";
			  window.parent.$("#mensajes_main").html(msg + xhr.status + " " + xhr.statusText);
			  window.parent.salirSistema();		     
		  }
	});
}

function cargaProcesoGrid()
{	  
	$("#main_content").empty();
	$("#main_content").html('<div id="pisos-container" style="height: 96%; width: 99%;"></div>');
    //$("#load_content").fadeOut(1600, "linear", completeFOContent);		  
	//$("div").show();
}
function completeFOContent()
{
	
}

function cargaProcesoPisoGrid(id_ut_current,id_uts, piso_ut)
{	  
	$("#seccion").append(" / PISO: "+piso_ut);
	$("#main_content").empty();
	$("#main_content").html('<div id="imagen-pisos-container" style="height: 96%; width: 99%;"></div>');
	$('#imagen-pisos-container').load(contexPath+'/UbicacionTSGrid.htm?idUbicacion='+id_uts+'&id_ut_current='+id_ut_current+'&utsF='+uts_global+"&rand="+Math.random(), function(response, status, xhr) {
		  if (status == "success") {
			  window.parent.$("#mensajes_main").html("Contenido del piso cargado");
		  }
		  else
		  {
			  var msg = "Error: ";
			  window.parent.$("#mensajes_main").html(msg + xhr.status + " " + xhr.statusText);	
			  window.parent.salirSistema();
		  }
	});
}

function cargaProcesoDetalleGrid()
{	  
var contenido_detalle="";
	
	$("#main_content").empty();
	
			contenido_detalle+='<div id="tab_detalle" style="position: relative; height: 96%; width: 99%;">';
			contenido_detalle+='<style scoped>';	
			contenido_detalle+='.derecha   { float: right; }';	
			contenido_detalle+='.izquierda   { float: left; }';	
	 
			contenido_detalle+='#dg_equipo_detalle {';	 
			contenido_detalle+='position: absolute;';	
			contenido_detalle+='top: 5px;';	
			contenido_detalle+='right: 10px;';				  													
			contenido_detalle+='}';	
			contenido_detalle+='#render_imagen {';	
			contenido_detalle+='position:relative;';	 
			contenido_detalle+='margin-top:80px;';	
			contenido_detalle+='}';	
			
			contenido_detalle+='#cotizador_container {';	
			contenido_detalle+='position: absolute;';	 
			contenido_detalle+='float: left;';	
			contenido_detalle+='top: 4px;';	
			contenido_detalle+='left: 2px;';	
			contenido_detalle+='width: 630px;';	
			contenido_detalle+='height: 660px;';	
			contenido_detalle+='}';	
			contenido_detalle+='</style>';	
			contenido_detalle+='<img src="" id="render_imagen" class="izquierda"/>';	
			contenido_detalle+='<div id="dg_equipo_detalle" style="width: 350px; height: 290px">';	
			contenido_detalle+='<dl class="specification" id="detalle_eq">';	
				
			contenido_detalle+='</dl>';	
			contenido_detalle+='</div>';	
			contenido_detalle+='<div class="izquierda" id="cotizador_container" style="display: none">';	
			contenido_detalle+='<iframe id="iframe_simulador"';	
			contenido_detalle+='allowtransparency="true"';	
			contenido_detalle+='name="iframe_simulador"';	
			contenido_detalle+='src=""';	
			contenido_detalle+='scrolling="auto"';	
			contenido_detalle+='noresize="noresize"';	
			contenido_detalle+='frameborder="no"';	
			contenido_detalle+='width="100%"';	
			contenido_detalle+='height="100%">';	
			contenido_detalle+='</iframe>';									
			contenido_detalle+='</div>';
			contenido_detalle+='</div>';
				
			$("#main_content").html(contenido_detalle);
    //$("#load_content").fadeOut(1600, "linear", completeFOContent);		  
	//$("div").show();
}


function getFaces() {  
	  // get the form values 
	
	  var userInfo = "";
	  
	  $.ajax({  
	    type: "POST",  
	    url: contexPath + "/Ubicaciones.htm",  
	    data: "accion=faces", 
	    success: function(response){
	      // we have the response 
	      if(response.respGetUnidadesTecnicasSuperiores.mensaje == "SUCCESS"){	 
	    	  var uts_num=response.respGetUnidadesTecnicasSuperiores.objUbicacionesList.length;
	    	  if(uts_num>=1)
	    	  {	    		  
	    		  $("#mensajes_main").html("Se encontraron "+uts_num+ " registros");
		    	  $("#cmb_ubicaciones_sup_f").kendoComboBox({
		    		    index: 0,
		    		    dataTextField: "ubicacionDescripcion",
		    	        dataValueField: "ubicacionClave",
		    	        dataSource: response.respGetUnidadesTecnicasSuperiores.objUbicacionesList, 
		    	        change: onChangeUTSFaces
		    	  });
		    	  getPisos();
	    	  }
	    	  else
	    	  {
	    		  $("#mensajes_main").html("No se encontraron ubicaciones para esta sesion");
	    	  }
	    	  
	      }
	      else if(response.respGetUnidadesTecnicasSuperiores.mensaje == "LOGOUT")
	      {
	    	  salirSistema();
	      }
	      else
	      {
	    	  $("#mensajes_main").html(response.respGetUnidadesTecnicasSuperiores.descripcion);
	      }	      
	    },  
	    error: function(e){  
	    	$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
	    }  
	  });  
	}  

function getPisos(utsPiso) {  
	  // get the form values  
      $("#seccion").append("UNIDAD: "+$("#utsx").val());
	  var selectUTS = utsPiso;

	  $("#grid_pisos1 thead").html("");
	  $("#grid_pisos1 tbody").html("");
	  
	  $.ajax({  
	    type: "POST",  
	    url: contexPath + "/UbicacionesMain.htm",  
	    data: "accion=pisos&i_id_ut_current="+selectUTS,
	    success: function(response){
	      // we have the response 
	      if(response.respGetUnidadesTecnicasSuperiores.mensaje == "SUCCESS"){	    	  
	    	  //$("#mensajes_main").html("Se encontraron "+response.respGetUnidadesTecnicasSuperiores.objUbicacionesList.length+ " registros");
	    	  cargaProcesoGrid();
	    	  var pisos1 = response.respGetUnidadesTecnicasSuperiores.objUbicacionesList.length;
	    	  var lngDeptos=response.respGetUnidadesTecnicasSuperiores.maxep_piso;
	    	  var deptos="";
	    	  for(var i=0; i<pisos1; i++)
	    	  {
	    		  deptos+="<tr>";
	    		  var hdeptos="<tr>";
	    		  var img_nom;
	    		  for(var j=0; j<lngDeptos; j++)
		    	  {
	    			  img_nom="";
	    			  
	    			  var deptosxpiso=response.respGetUnidadesTecnicasSuperiores.objUbicacionesList[i].equiposList;
	    			  
	    			  if(j < deptosxpiso.length)
	    			  {
	    				  var estatus_equipo=parseInt(deptosxpiso[j].id_estatus);
	    				  var piso_depto=Right(deptosxpiso[j].id_equnr,6);
	    				  var sp_pd=piso_depto.split("-");
	    				  var piso_sp=sp_pd[0];
	    				  var depto_sp=sp_pd[1];
	    				  																			
	    				  
	    				  
		    			  if(estatus_equipo==0)
		    			  {
		    				  img_nom="depto_libre2.png";
		    			  }
		    			  else if(estatus_equipo==1 || estatus_equipo==5 || estatus_equipo==7 || estatus_equipo==8)
		    			  {
		    				 img_nom="blotrascancel2.png";
		    			  }
		    			  else if(estatus_equipo==2)
		    			  {
		    				  img_nom="depto_nd2.png";
		    			  }
		    			  else if(estatus_equipo==3 || estatus_equipo==4 || estatus_equipo==6)
		    			  {
		    				  img_nom="depto_vendido2.png";
		    			  }
		    			  else
		    			  {
		    				  img_nom="depto_empty2.png";
		    			  }
		    			  
		    			  var table_detalle='<table width="92" height="86" border="0" cellpadding="0" cellspacing="0" style="background: url('+contexPath+'/images/deptos/deptop.png); background-repeat: no-repeat; color: #FFF; font-weight: bold;">';
		    			  table_detalle+='<tr>';
		    			  table_detalle+='<td height="26"><a href="#" ></a></td>';
		    			  table_detalle+='<td height="26" valign="bottom" align="right">STATUS</td>';
		    			  table_detalle+='<td height="26" valign="bottom" align="center"><img src="'+contexPath+'/images/deptos/'+img_nom+'?rand='+Math.random()+'" /></td>';
		    			  table_detalle+='</tr>';
		    			  table_detalle+='<tr>';
		    			  table_detalle+='<td height="20" colspan="3"></td>';
		    			  table_detalle+='</tr>';
		    			  table_detalle+='<tr>';
		    			  table_detalle+='<td width="10" height="12"></td>';
		    			  table_detalle+='<td width="38">'+deptosxpiso[j].id_tipo+'</td>';
		    			  table_detalle+='<td width="40">'+depto_sp+'</td>';
		    			  table_detalle+='</tr>';
		    			  table_detalle+='<tr>';
		    			  table_detalle+='<td height="20" colspan="3" align="center">'+deptosxpiso[j].id_precio_format+'</td>';
    	                  table_detalle+='</tr>';
    	                  table_detalle+='</table>';
	    	                   
		    			  /*var table_detalle='<table width="75" border="0" cellpadding="0" cellspacing="0">';
		    			    table_detalle+='<tr>';
		    			    table_detalle+='<td><br><br><div style="position: relative; margin: 0 auto;width: 75px;"><a href="#" onclick="getDetallesDepto(\'detalle\',\''+deptosxpiso[j].id_equnr+'\',\''+deptosxpiso[j].id_ubi_tec+'\')">ver</a>  <h4 style="-moz-border-radius-bottomright: 10px; -moz-border-radius-topright: 10px; background-color: transparent; bottom: 6px; color: #000; margin: 0; opacity: 0.9; filter:alpha(opacity=90); padding: 10px 11px; position: absolute; width: 50px;">'+deptosxpiso[j].id_tipo+'</h4><h4 style="-moz-border-radius-bottomright: 10px; -moz-border-radius-topright: 10px; background-color: transparent; bottom: 6px; color: #000; margin: 0; opacity: 0.9; filter:alpha(opacity=90); padding: 0px 11px; position: absolute; width: 50px;">'+depto_sp+'</h4></div></td>';
		    			    table_detalle+='<td></td>';
		    			    table_detalle+='</tr>';
		    			    table_detalle+='<tr>';
		    			    table_detalle+='<td>'+deptosxpiso[j].id_precio_format+'</td>';
		    			    table_detalle+='<td></td>';
		    			    table_detalle+='</tr>';
		    			    table_detalle+='</table>';*/
		    			  
		    			  var piso_num=pisos1-i;
		    			  var piso_num_org=deptosxpiso[j].id_ubi_tec;
		    			  piso_num_org = piso_num_org.substr(-2);
		    			  
		    			  if(j==0)
		    			  {
		    				  deptos+="<td width=\"35\" align=\"center\" style=\"background: url("+contexPath+"/images/deptos/pestana.png); background-repeat: no-repeat;\">&nbsp;&nbsp;&nbsp;&nbsp;<strong><a href=\"#\" onclick=\"getDetallesDepto('detalle','"+deptosxpiso[j].id_equnr+"','"+deptosxpiso[j].id_ubi_tec+"','"+piso_num_org+"')\">"+(piso_num_org)+"</a></strong>&nbsp;&nbsp;</td><td width=\"0\">"+table_detalle+"</td>";
		    			  }
		    			  else
		    			  {
		    				  deptos+="<td width=\"0\">"+table_detalle+"</td>";
		    			  }
		    			  		    			  
		    			  //hdeptos+="<th>"+j+"</th>";
	    			  }
	    			  else
	    			  {
	    				  var table_detalle='<table width="75" border="0" cellpadding="0" cellspacing="0">';
		    			    table_detalle+='<tr>';
		    			    table_detalle+='<td><div style="position: relative; margin: 0 auto;width: 75px;"><a href="#" ><h4 style="-moz-border-radius-bottomright: 10px; -moz-border-radius-topright: 10px; background-color: transparent; bottom: 6px; color: #FFF; margin: 0; opacity: 0.9; filter:alpha(opacity=90); padding: 10px 11px; position: absolute; width: 50px;">N/D</h4><h4 style="-moz-border-radius-bottomright: 10px; -moz-border-radius-topright: 10px; background-color: transparent; bottom: 6px; color: #FFF; margin: 0; opacity: 0.9; filter:alpha(opacity=90); padding: 0px 11px; position: absolute; width: 50px;">N/D</h4></div></td>';
		    			    table_detalle+='<td></td>';
		    			    table_detalle+='</tr>';
		    			    table_detalle+='<tr>';
		    			    table_detalle+='<td>N/D</td>';
		    			    table_detalle+='<td></td>';
		    			    table_detalle+='</tr>';
		    			    table_detalle+='</table>';
	    				  deptos+="<td>"+table_detalle+"</td>";
	    				  //hdeptos+="<th>"+j+"</th>";
	    			  }
		    	  }
	    		  deptos+="</tr>";
	    		  //hdeptos+="</tr>";
	    		  
	    		  //$("#grid_pisos1 thead").html(hdeptos);
	    		  var tabla_pisos="<table id=\"grid_pisos1\" width=\"0\" align=\"center\" border=\"0\" cellpadding=\"1\" cellspacing=\"0\" valign=\"bottom\">";
	    		  tabla_pisos+=deptos;
	    		  tabla_pisos+="</table>";  
	    		  var espacio_tabla1="";    		  
	    		  espacio_tabla1+='<table width="100%" height="90" border="0" align="center" cellpadding="0" cellspacing="0">';
	    		  espacio_tabla1+='<tr>';
	    			  espacio_tabla1+='<td>&nbsp;</td>';
	    		  espacio_tabla1+='</tr>';
	              espacio_tabla1+='</table>';
	    		  
				var tabla_principal='<table width="100%" height="600" border="0" align="center" cellpadding="0" cellspacing="0" id="grid_principal">';
				tabla_principal+='<tr>';
				tabla_principal+='<td height="23">&nbsp;</td>';
				tabla_principal+='</tr>';
				tabla_principal+='<tr>';
				tabla_principal+='<td height="598" valign="bottom"><table width="1050" border="0" align="center" cellpadding="0" cellspacing="0">';
				tabla_principal+='<tr>';
				tabla_principal+='<td valign="bottom" align="center"><div id="grid_pisos">'+espacio_tabla1+tabla_pisos+'</div></td>';
				tabla_principal+='</tr>';
				tabla_principal+='<tr>';
				tabla_principal+='<td height="30" valign="bottom">';
				tabla_principal+='<div style="position:relative">'; 
				tabla_principal+='<div style="position: absolute; width: 1150px; left: -32px; top: -35px; background-image: url('+contexPath+'/images/images/cesped_grid.png); height: 65px;"></div>';
				tabla_principal+='<div style="position: absolute; width: 100px; left: 960px; top: -270px;">';
				tabla_principal+='<input name="img_arbol" type="image" src="'+contexPath+'/images/deptos/arbolm.png"/>';
				tabla_principal+='</div>';
				tabla_principal+='<div style="position: absolute; width: 100px; left: -40px; top: -230px;">';
				tabla_principal+='<input name="img_arbol" type="image" src="'+contexPath+'/images/deptos/arbol.png"/>';
			    tabla_principal+='</div>';
		        tabla_principal+=' </div>';
				tabla_principal+='</td>';
				tabla_principal+='</tr>';
				tabla_principal+='</table></td>';
				tabla_principal+='</tr>';
				tabla_principal+='</table>';
	    	  }
	    	  
	    	  $("#pisos-container").html("");
	    	  $("#pisos-container").html(tabla_principal);
	    	  
	    	  var objDiv = document.getElementById("pisos-container");
	    	  objDiv.scrollTop = objDiv.scrollHeight; 
	    	  
	    	  /*var tabToDeactivate = $("#tab_mapa");*/
	    	  //tabStrip=$("#tabubucaciones").data("kendoTabStrip");
	    	  //tabStrip.select(1);
	      }
	      else if(response.respGetUnidadesTecnicasSuperiores.mensaje == "LOGOUT")
	      {
	    	  salirSistema();
	      }
	      else
	      {
	    	  $("#mensajes_main").html(response.respGetUnidadesTecnicasSuperiores.descripcion);
	      }	      
	    },  
	    error: function(e){  
	    	$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
	    }  
	  });  
	} 
	
	
function getUTSAdmin(accion) {  
	  // get the form values  
	  var userInfo = "";
	  var datosFaceList = $("#cmb_ubicaciones_sup").data('kendoDropDownList');
	  var itemFaces = datosFaceList.dataItem();
	  var datosPisoList = $("#cmb_ubicaciones_int").data('kendoDropDownList');
	  var itemPiso = datosPisoList.dataItem();
	  var datosEquipoList = $("#cmb_ubicaciones_equipos").data('kendoDropDownList');
	  var itemEquipo = datosEquipoList.dataItem();
	  
	  var iduts="";
	  
	  if(accion=="faces")
	  {
		  //if(itemFaces != undefined)
		      
	  }
	  else if(accion=="pisos")
	  {
		  if(itemFaces != undefined)
			  iduts=itemFaces.ubicacionClave;
			  //iduts=itemPiso.ubicacionClave;
	  }
	  else if(accion=="equipos")
	  {
		  if(itemPiso != undefined)
			  iduts=itemPiso.ubicacionClave;
			  //iduts=itemPiso.ubicacionClave;
	  }
	  onVentanaWait("Espere ", true)
	  $.ajax({  
	    type: "POST",  
	    url: contexPath + "/AdministraMapeo.htm",  
	    data: "accion="+accion+"&i_id_ut_current="+iduts, 
	    success: function(response){
	      // we have the response 
	      if(response.respGetUnidadesTecnicasSuperiores.mensaje == "SUCCESS"){	
	    	  $.unblockUI();
	    	  var uts_num=response.respGetUnidadesTecnicasSuperiores.objUbicacionesList;
	    	  var ute_num=response.respGetUnidadesTecnicasSuperiores.objEquiposList;
	    	  
	    	  if(uts_num.length>=1 || ute_num.length>=1)
	    	  {	  
	    		 
	    		  
	    		  if(accion=="faces")
	    		  {  
		    		  $("#mensajes_main").html("Se encontraron "+uts_num.length+ " registros");
		    		  
		    		  datosFaceList.dataSource.data([]);     
		    		  datosFaceList.dataSource.data(uts_num);
		    		  datosFaceList.select(0);
			    	  itemFaces = datosFaceList.dataItem();
			    	  getDatosMapaDB(itemFaces.ubicacionClave,"");
			    	  //getUTSAdmin("pisos");
	    		  }
	    		  else if(accion=="pisos")
	    		  {
	    			  $("#mensajes_main").html("Se encontraron "+uts_num.length+ " registros");
		    		 
	    			  datosPisoList.dataSource.data([]);     
	    			  datosPisoList.dataSource.data(uts_num);
			    	  datosPisoList.select(0);
			    	  itemPiso = datosPisoList.dataItem();
			    	  //getDatosMapaDB(itemPiso.ubicacionClave);
			    	  //getUTSAdmin("equipos");
	    		  }
	    		  else if(accion=="equipos")
	    		  {
	    			  $("#mensajes_main").html("Se encontraron "+ute_num.length+ " registros");
		    		  
	    			  datosEquipoList.dataSource.data([]);     
	    			  datosEquipoList.dataSource.data(ute_num);
	    			  datosEquipoList.select(0);
			    	  itemEquipo = datosEquipoList.dataItem();
			    	  getDatosMapaDB(itemPiso.ubicacionClave,itemEquipo.id_equnr);
	    		  }
	    	  }
	    	  else
	    	  {
	    		  $.unblockUI()
	    		  $("#mensajes_main").html("No se encontraron ubicaciones para esta sesion");	    		  
	    	  }
	    	  
	      }
	      else if(response.respGetUnidadesTecnicasSuperiores.mensaje == "LOGOUT")
	      {
	    	  salirSistema();
	      }
	      else
	      {
	    	  $.unblockUI()
	    	  $("#mensajes_main").html(response.respGetUnidadesTecnicasSuperiores.descripcion);
	      }	      
	    },  
	    error: function(e){  
	    	$.unblockUI()
	    	$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
	    }  
	  });  
	}  
	
	function onChangeUTSFaces(e)
	{
		getPisos();
	}
	
	function onChangeUTS(e)
	{
		var datosUSTList = $("#cmb_ubicaciones_sup").data('kendoDropDownList');
		var itemVendedor = datosUSTList.dataItem();
		onVentanaWait("Espere un momento", true);
  	    getDatosMapaDB(itemVendedor.ubicacionClave,"");
  	    //getUTSAdmin("pisos");
	}
	
	function onChangeUTSpiso(e)
	{
		var datosUSTList = $("#cmb_ubicaciones_int").data('kendoDropDownList');
		var itemVendedor = datosUSTList.dataItem();
  	    //getDatosMapaDB(itemVendedor.ubicacionClave,"");
  	    //getUTSAdmin("equipos");
	}
	
	function onChangeUTSequipo(e)
	{
		var datosUSTList = $("#cmb_ubicaciones_int").data('kendoDropDownList');
		var itemUts = datosUSTList.dataItem();
		var datosEQUList = $("#cmb_ubicaciones_equipos").data('kendoDropDownList');
		var itemEqui = datosEQUList.dataItem();
		onVentanaWait("Espere un momento", true);
  	    getDatosMapaDB(itemUts.ubicacionClave,itemEqui.id_equnr);
	}
	
	function Right(str, n){
		if (n <= 0)
			return "";
		else if (n > str.length)
			return str;
		else {
			var iLen = str.length;
			return str.substring(iLen, iLen - n);
		}
	}

	
	function getDetallesDepto(accion,id_ut_current, id_uts, piso_ut)
	{
		var iframe = $('#iframe_detalle');
	    $(iframe).attr('src', "");
		if(accion == "vacio")
		{
			$("#mensajes_main").html("El departamento no existe favor de seleccionar otro ");
		}
		else
		{
			//$("#mensajes_main").html("Ok "+id_ut_current+" "+id_ubi_tec+"");
			getUbicacionesPisoVenta(id_ut_current,id_uts, piso_ut);
		}
	}
	
	function getUbicacionesPisoVenta(id_ut_current,id_uts, piso_ut)
	{ 
		cargaProcesoPisoGrid(id_ut_current,id_uts, piso_ut);
		//var splitter = $("#sppiso").data("kendoSplitter");
		//var detailspane = $("#contenidopiso").data();
		// load content into the pane with ID, pane1
		
		  //var iframe = $('#iframe_detalle');
		  //var url_maping=contexPath +'/UbicacionTSGrid.htm?idUbicacion='+id_uts+'&id_ut_current='+id_ut_current+'&utsF='+uts_global;
		  //splitter.ajaxRequest("#contenidopiso", url_maping, { id: 42 });
		  //splitter.expand("#pisoscontainer");
		  //$(iframe).attr('src', url_maping);
	}
	
	function getPisosVenta()
	{ 
		  var userInfo = "";
		  
		  $.ajax({  
		    type: "POST",  
		    url: contexPath + "/UbicacionesMain.htm",  
		    data: "accion=pisosdetalle", 
		    success: function(response){
		      // we have the response 
		      if(response.respGetUnidadesTecnicasSuperiores.mensaje == "SUCCESS"){	 
		    	  var uts_num=response.respGetUnidadesTecnicasSuperiores.objUbicacionesList.length;
		    	  if(uts_num>=1)
		    	  {	    		  
		    		  $("#mensajes_main").html("Se encontraron "+uts_num+ " registros","");			    	  
		    	  }
		    	  else
		    	  {
		    		  $("#mensajes_main").html("No se encontraron ubicaciones para esta sesion");
		    	  }
		    	  
		      }
		      else if(response.respGetUnidadesTecnicasSuperiores.mensaje == "LOGOUT")
		      {
		    	  salirSistema();
		      }
		      else
		      {
		    	  $("#mensajes_main").html(response.respGetUnidadesTecnicasSuperiores.descripcion);
		      }	      
		    },  
		    error: function(e){  
		    	$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
		    }  
		  });  	
	}
	
	function setReplicaPisos()
	{		
		var datosUSTList = $("#cmb_ubicaciones_sup").data('kendoDropDownList');
		var itemUTSMain = datosUSTList.dataItem();
		
		var grid_selec = $('#dg_ubicaciones').data('kendoGrid');		 
        var rows_selec = grid_selec.select();
        var seleccionados="";
        rows_selec.each( 
            function() { 
            	var dataItem = grid_selec.dataItem($(this));
            	seleccionados += dataItem.ubicacionClave+"|"; 
            } 
        );
        
        if(seleccionados=="")
        {
        	$("#mensajes_main").html("Seleccione las ubicaciones de la lista de la derecha que se duplicacar");
        }
        else
        {      
        $("#btn_replicar").attr("disabled","disabled");
		$.ajax({  
		    type: "POST",  
		    url: contexPath + "/MapeoUbicacionSimilares.htm",  
		    data: "accion=replicaPisos&i_id_ut_current="+itemUTSMain.ubicacionClave+"&i_list_coord="+seleccionados, 
		    success: function(response){
		      // we have the response 
		      if(response.addCoords.mensaje == "SUCCESS"){	 
		    	  $("#mensajes_main").html(response.addCoords.descripcion,"");
		    	  window.parent.$("#winchaneimg").data("kendoWindow").close();
		    	  window.parent.$("#mensajes_main").html(response.addCoords.descripcion);
		      }
		      else if(response.addCoords.mensaje == "LOGOUT")
		      {
		    	  window.parent.salirSistema();
		      }
		      else
		      {
		    	  $("#mensajes_main").html(response.addCoords.descripcion);
		      }	
		      $("#btn_replicar").removeAttr("disabled", "disabled");
		    },  
		    error: function(e){  
		    	$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
		    	$("#btn_replicar").removeAttr("disabled", "disabled");
		    }  
		  });	
        }
	}
	
	function onChangeFaseut(e)
	{
		var datosUSTList = $("#idUbicacion").data('kendoDropDownList');
		var itemUTSMain = datosUSTList.dataItem();
		getTiposEquipos(itemUTSMain.value);
	}
	
	function onChangeEquipo(e)
	{
		var datosUSTList = $("#idUbicacion").val();
		getValidaEquipos(datosUSTList);
	}
	
	function getTiposEquipos(idUbicacion)
	{ 		  
		  $.ajax({  
		    type: "POST",  
		    url: contexPath + "/UploadTiposEquipos.htm",  
		    data: "accion=getTiposEquipos&idUbicacion="+idUbicacion, 
		    success: function(response){
			 if(response.respDatosImagen.mensaje=="SUCCESS")
			 {
				 tipos_eq = $("#tipo").data('kendoDropDownList');
				 tipos_eq.dataSource.data([]);
				 tipos_eq.dataSource.data(response.respDatosImagen.listaTipos);
				 tipos_eq.select(0);
				 $("#mensajes_main").html("");
				 $("#mensajes_main").html(response.respDatosImagen.descripcion);
			 }
			 else if(response.respDatosImagen.mensaje == "LOGOUT")
		     {
		    	 salirSistema();
		     }
			 else
			 {
				 $("#mensajes_main").html(response.respDatosImagen.descripcion);
			 }
		    },  
		    error: function(e){  
		    	$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
		    }  
		  });  	
	}
	
	function getValidaEquipos(idUbicacion)
	{ 		  
		var datostipo = $("#tipo").data('kendoDropDownList');
		var itemTipo = datostipo.dataItem();
		  $.ajax({  
		    type: "POST",  
		    url: contexPath + "/UploadTiposEquipos.htm",  
		    data: "accion=validaEquipos&idUbicacion="+idUbicacion+"&tipo="+itemTipo.i_tipo_eq, 
		    success: function(response){
			 
			 $("#mensajes_main").html("");
			 if(response.respDatosImagen.descripcion!="")
			 {				 
				 $("#mensajes_main").html(response.respDatosImagen.descripcion);
			 }
			 
		    },  
		    error: function(e){  
		    	$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
		    }  
		  });  	
	}
	
	function getArbol(fFace,fDepart,fSubCarp) {  
		  // get the form values
		if(!fFace){fFace="";}
		if(!fDepart){fDepart="";}
		if(!fSubCarp){fSubCarp="";}

		  var userInfo = "";
		  
		  $.ajax({  
		    type: "POST",  
		    url: contexPath + "/Ubicaciones.htm",  
		    data: "accion=arbol&fFace=" + fFace + "&fDepart=" + fDepart + "&fsubCarp=" + fSubCarp, 
		    success: function(response){
		      // we have the response 
		      if(response.respGetUnidadesTecnicasSuperiores.mensaje == "SUCCESS"){	 
		    	  var uldatos= response.respGetUnidadesTecnicasSuperiores.ubicacionesList;
		    	  var uts_num=uldatos.length;
		    	  var uts_root="";
		    	  var arbol_uts="";
		    	  
		    	  
		    	  carpetas_dig = response.respGetUnidadesTecnicasSuperiores.carpetasDigitalizacion;
		    	  carpetas_dig_ = response.respGetUnidadesTecnicasSuperiores.carpetasDigitalizacion;
		    	  
		    	 
		    	  
		    	  $("#tr_arbol").html("");
		    	  $("#tr_arbol_files").html("");
		  	   	  obj_arbol_b=$("#tr_arbol_files").kendoTreeView({
					 select: onSelectArbolFiles				 
				  }).data("kendoTreeView");

		    	  arbol_uts+='<li data-expanded="true">';
		    	  arbol_uts+='<span class="k-sprite rootfolder"></span>';
		    	  arbol_uts+='Fases';
		    	  if(uts_num>=1)
		    	  {	   
		    		  //alert(uts_num);
		    		  arbol_uts+='<ul>';
		    		  for(var i = 0; i<uts_num; i++)
		    		  {
		    			  arbol_uts+='<li data-id="'+uldatos[i].ubicacionClave+'" data-expanded="false">';
		    			  arbol_uts+='<span class="k-sprite folder"></span>'+uldatos[i].ubicacionDescripcion;
		    			  arbol_uts+='<ul>';
		    			  for(var j = 0; j<uldatos[i].pisosList.length; j++)
			    		  {
		    				  if(validaIngresoModulos("DIGIT", "PERMISO_P"))
		    				  {
			    				  arbol_uts+='<li data-id="'+uldatos[i].pisosList[j].ubicacionClave+'" data-expanded="false">';
			    				  arbol_uts+='<span class="k-sprite folder"></span>'+uldatos[i].pisosList[j].ubicacionDescripcion;
				    			  arbol_uts+='<ul>';
				    			  for(var k = 0; k<uldatos[i].pisosList[j].equiposList.length; k++)
					    		  {
				    				  arbol_uts+='<li data-id="'+uldatos[i].pisosList[j].equiposList[k].id_equnr+'" data-expanded="false">';
				    				  arbol_uts+='<span class="k-sprite rootfolder"></span>'+uldatos[i].pisosList[j].equiposList[k].id_equnrx;
					    			  //arbol_uts+='<ul>';
					    			  //arbol_uts+=carp_arbol;
					    			  //arbol_uts+='</ul>';
					    			  arbol_uts+='</li>';
					    		  }
				    			  arbol_uts+='</ul>';
				    			  arbol_uts+='</li>';
		    				  }
		    				  else
	    					  {
		    					  var equipo_num=uldatos[i].pisosList[j].ubicacionClave.substr(-2); 
		    					  if(equipo_num=="99")
		    					  {
			    					  arbol_uts+='<li data-id="'+uldatos[i].pisosList[j].ubicacionClave+'" data-expanded="false">';
				    				  arbol_uts+='<span class="k-sprite folder"></span>'+uldatos[i].pisosList[j].ubicacionDescripcion;
					    			  arbol_uts+='<ul>';
					    			  for(var k = 0; k<uldatos[i].pisosList[j].equiposList.length; k++)
						    		  {
					    				  arbol_uts+='<li data-id="'+uldatos[i].pisosList[j].equiposList[k].id_equnr+'" data-expanded="false">';
					    				  arbol_uts+='<span class="k-sprite rootfolder"></span>'+uldatos[i].pisosList[j].equiposList[k].id_equnrx;
						    			  //arbol_uts+='<ul>';
						    			  //arbol_uts+=carp_arbol;
						    			  //arbol_uts+='</ul>';
						    			  arbol_uts+='</li>';
						    		  }
					    			  arbol_uts+='</ul>';
					    			  arbol_uts+='</li>';
		    					  }
	    					  }
			    		  }
		    			  arbol_uts+='</ul>';
		    			  arbol_uts+='</li>';
		    		  }
		    		  arbol_uts+='</ul>';  
		    		  	    	  
		    	  
		    	  arbol_uts+='</li>';		    	  
		    	  
		    	  $("#mensajes_main").html("Se encontraron "+uts_num+ " registros");	
		    	  
		    	  $("#tr_arbol").html(arbol_uts);
		    	  
		    	  obj_arbol=$("#tr_arbol").kendoTreeView({
		    		  select: onSelectArbol
		    	  }).data("kendoTreeView");
				  //$("#tr_arbol").html(arbol_k);
				  //document.write(arbol_k);
		      }	    	  
		      else
		      {
		    	  $("#mensajes_main").html(response.respGetUnidadesTecnicasSuperiores.descripcion);
		      }
		      }
		      else if(response.respGetUnidadesTecnicasSuperiores.mensaje == "LOGOUT")
			  {
			      salirSistema();
			  }
		      else		    	  
		      {
		    	  $("#mensajes_main").html("Error al acceder los datos");
		      }
		    },  
		    error: function(e){  
		    	$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
		    }  
		  });  
		}  
	
	function appendItemForlder(node_id)
	{
		var selectedNode = obj_arbol.select();
		var docs_folder = [];
		var docs_sub_folder = [];
        // passing a falsy value as the second append() parameter
        // will append the new node to the root group
        if (selectedNode.length == 0) {
        	appendItemForlder();
        }
        else
        {
	      for(var i=0; i<carpetas_dig.length; i++)
	  	  {
	  		  if(carpetas_dig[i].id_ticket_file=="00")
	  		  {
	  			      docs_sub_folder = [];
		    		  for(var j=0; j<carpetas_dig_.length; j++)
			    	  {
		    			  if(carpetas_dig_[j].id_ticket_area == carpetas_dig[i].id_ticket_area && carpetas_dig[j].id_ticket_file!="00")
		    			  {
		    				  docs_sub_folder.push({
					  	            text: carpetas_dig_[j].id_ticket_areax, expanded: false, spriteCssClass: "folder"
					          });		    				 
		    			  }
			    	  }
		    		  
		    		  docs_folder.push({
			  	            text: carpetas_dig[i].id_ticket_areax, expanded: false, spriteCssClass: "folder", items: docs_sub_folder
			          });
	  	  	  }
	  	  }
        
        obj_arbol.append(docs_folder, selectedNode);
  
        }
	}
	
	function onSelectArbol(e)
	{				
		var na=$(e.node).data("id");
		var sp_na=na.split("-");
		
		nodo_arbol=na;
		last_node=na;
		
		if(sp_na.length>=2)
		{
		 carp_arbol="";
		 carp_arbol+='<li data-expanded="true">';
		 carp_arbol+='<span class="k-sprite rootfolder"></span>';
		 carp_arbol+=this.text(e.node);
		 carp_arbol+='<ul>';
	   	  for(var i=0; i<carpetas_dig.length; i++)
	   	  {
	   		  //alert(carpetas_dig[i].id_ticket_file);
	   		  if(carpetas_dig[i].id_ticket_file=="00")
	   		  {
		    		  carp_arbol+='<li data-expanded="false">';
		    		  carp_arbol+='<span class="k-sprite folder"></span>'+carpetas_dig[i].id_ticket_areax;
		    		  carp_arbol+='<ul>';
		    		  for(var j=0; j<carpetas_dig_.length; j++)
			    	  {
		    			  if(carpetas_dig_[j].id_ticket_area == carpetas_dig[i].id_ticket_area && carpetas_dig[j].id_ticket_file!="00")
		    			  {
		    				  carp_arbol+='<li data-id="'+carpetas_dig_[j].id_ticket_area+"-"+carpetas_dig[j].id_ticket_file+'" data-expanded="false">';
		    				  carp_arbol+='<span class="k-sprite folder"></span>'+carpetas_dig_[j].id_ticket_areax;
		    				  carp_arbol+='<ul>';
			    			  			    				  
		    				  carp_arbol+='</ul>';
		    				  carp_arbol+='</li>';
		    			  }                                                                              
			    	  }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		    		  carp_arbol+='</ul>';
		    		  carp_arbol+='</li>';
	   	  	  }
	   	  }
   	  
	   	carp_arbol+='</ul>';   
	   	carp_arbol+='</li>';
	  	
	   	$("#tr_arbol_files").html("");	
	   	$("#tr_arbol_files").html(carp_arbol);
	   	  
	   	obj_arbol_b=$("#tr_arbol_files").kendoTreeView({
				 select: onSelectArbolFiles				 
			 }).data("kendoTreeView");
		}
		/*var nodo_arbol=this.text(e.node);
		var sp_na=nodo_arbol.split("-");
		
		if(sp_na.length>=2)
		{
			var existe_item=false;
			if(folderAdd.length==0)
			{
				folderAdd.push({id:nodo_arbol});
				appendItemForlder();
			}
			else
			{
				for (var i=0; i<folderAdd.length; i++)
				{
					if(folderAdd[i].id==nodo_arbol)
					{
						existe_item=true;
					}				
				}		
				
				if(!existe_item)
				{				
					folderAdd.push({id:nodo_arbol});
					appendItemForlder();				
				}
			}					
		}*/		
	}
	
	function onSelectArbolFiles(e)
	{
		$("#pdfContent").html("");
		var nodo_arbol_folder=this.text(e.node);
		
		if(nodo_arbol_folder.indexOf(".doc") != -1 || nodo_arbol_folder.indexOf(".docx") != -1 || nodo_arbol_folder.indexOf(".xls") != -1 || nodo_arbol_folder.indexOf(".xlsx") != -1 || nodo_arbol_folder.indexOf(".ppt") != -1 || nodo_arbol_folder.indexOf(".pptx") != -1)
		{
			window.location.href = contexPath+"/files/"+nodo_arbol_folder;
		}
		else if(nodo_arbol_folder.indexOf(".pdf") != -1)
		{
			split_tree.ajaxRequest("#pdfContent", contexPath+"/files/"+nodo_arbol_folder, { id: 42 });
		}
		else if(nodo_arbol_folder.indexOf(".jpg") != -1 || nodo_arbol_folder.indexOf(".png") != -1 )
		{
			$("#pdfContent").html('<input type="image" src="'+contexPath+'/files/'+nodo_arbol_folder +'"/>');
		}
		else
		{		
		
		var na=$(e.node).data("id");
		var nsp=na.split("-");
		$.ajax({  
		    type: "POST",  
		    url: contexPath + "/Digitalizacion.htm",  
		    data: "accion=getpdf&equipo="+last_node+"&subtipo="+nsp[0]+"&subtipoa="+nsp[1], 
		    success: function(response){
		      // we have the response 
		      if(response.respDatosUploadDigitFile.mensaje == "SUCCESS"){	 
		    	  
		    	  var archivospdf=response.respDatosUploadDigitFile.imagesDigit;
		    	  var selectedNode = obj_arbol_b.select();
		    	  
		    	  if (selectedNode.length == 0) {
                      selectedNode = null;
                  }
		    	  for(var i=0; i<archivospdf.length; i++)
			  	  {		
		    		  var foundNode = obj_arbol_b.findByText(archivospdf[i].file_nombre);
		    		  if(foundNode.length==0)
		    		  {
		    		      obj_arbol_b.append({id:archivospdf[i].file_proceso+"-"+archivospdf[i].file_archivo, text: archivospdf[i].file_nombre, expanded: false, spriteCssClass: "pdf"}, selectedNode);
		    		  }
			  	  }		    	 
		      }
		      else if(response.respDatosUploadDigitFile.mensaje == "LOGOUT")
			  {
			      salirSistema();
			  }
		      else
		      {
		    	  $("#mensajes_main").html(response.respDatosUploadDigitFile.descripcion);
		      }		 
		    },  
		    error: function(e){  
		    	$("#mensajes_main").html("Error: " + e.responseText + " " + e.status);
		    }  
		  });
		}
	}
	
	function onSelectTreeMain(evt)
	{
		
	}