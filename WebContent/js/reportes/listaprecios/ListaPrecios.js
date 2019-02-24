function dialogoFiltroConsultaEdoCtaReporte() {   
	var windowSelecCarCli =$("#windowConsultaCatalogoReporteFilter").kendoWindow({
			actions: ["Close"],
			modal: true,
			resizable: false,
			content: 
				{
					url:contexPath+"/report/listaprecios/ReporteListaPreciosFilterView.htm"
			},
	        height: "350px",
	     	width: "610px",
	        title: "Parametros de Selección"
		    });	
	windowSelecCarCli = $("#windowConsultaCatalogoReporteFilter").data("kendoWindow");
	windowSelecCarCli.center();
	windowSelecCarCli.open();
}

function fillComboTiposReporte (idFaseReporte) {
	  var datosTiposList  = $("#tipo").data('kendoComboBox');
	  var itemTipos       = datosTiposList.dataItem();
	  $.ajax({  
		    type: "POST", 
		    cache : false,
		    async:  false,
		    url: contexPath + "/report/listaprecios/TiposView.htm",  
		    data: "idFaseReporte=" + idFaseReporte, 
		    success: function(response){
		      // we have the response 
    		  datosTiposList.dataSource.data([]);     
    		  datosTiposList.select(0);
    		  $("#descripcionTipo").html("");
		      if(response.listaTipos != undefined &&
		         response.mensaje != undefined &&
		    	 response.mensaje == "SUCCESS"){	
		    	  //$.unblockUI()
		    	  var listaTiposReporte = response.listaTipos;
		    	 
		    	  if(listaTiposReporte.length > 0)
		    	  {	  	    		  
			    		  $("#mensajes_main").html("Se encontraron " + listaTiposReporte.length + " registros");
			    		  datosTiposList.dataSource.data([]);     
			    		  datosTiposList.dataSource.data(listaTiposReporte);
			    		  datosTiposList.select(0);
			    		  itemTipos = datosTiposList.dataItem();	
			    		  $("#descripcionTipo").html(itemTipos.i_tipo_eq);
		    	  }
		    	  else
		    	  {
		    		  $("#mensajes_main").html("No se encontraron ubicaciones para esta sesion");	    		  
		    	  }
		    	  
		      }else{
		    	  //$.unblockUI()
		    	  if (response != undefined &&
		    		  response.descripcion != undefined) {
			    	  //$("#mensajes_main").html(response.descripcion);
		    	  }
		      }	      
		    },  
		    error: function(e){  
		    	//$.unblockUI()
		    	//$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
		    }  
		  });  
}

/*
 * 
 */
function fillComboRegistroReporte (accion) {			
	  var datosFaseList     = $("#faseReporte").data('kendoComboBox');
	  var itemFases         = datosFaseList.dataItem();
	  var datosEquiposList  = $("#equipoReporte").data('kendoComboBox');
	  var itemEquipos       = datosEquiposList.dataItem();
	  var datosEquiposfinList  = $("#equipoReportefin").data('kendoComboBox');
	  var itemEquiposfin       = datosEquiposfinList.dataItem();

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

	    	  if (accion=="estatus" && !loadComboEstatusReporte) {
    			  estatusArr = response.respGetUnidadesTecnicasSuperiores.estatus;;
    	    	  loadComboEstatusReporte = true;
	    		  $("#mensajes_main").html("Se encontraron "+uts_num.length+ " registros");
	    		  
	    		  datosEstatusList.dataSource.data([]);     
	    		  datosEstatusList.dataSource.data(estatusArr);
	    		  datosEstatusList.select(0);
	    		  itemAreas = datosEstatusList.dataItem();	
	    		  //$("#descripcionEstatus").html(itemAreas.text);
    		  }	    			  
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
		    		  fillComboTiposReporte("");
	    		  } 
	    		  else if(accion=="equipos")
	    		  {
	    			  $("#mensajes_main").html("Se encontraron "+ute_num.length+ " registros");
		    		  
	    			  datosEquiposList.dataSource.data([]);     
	    			  datosEquiposList.dataSource.data(ute_num);
	    			  datosEquiposList.select(0);
	    			  itemEquipos = datosEquiposList.dataItem();
	    			  //$("#txt_desc_equ").html(itemEquipos.id_equnrx);		    			  

	    			  datosEquiposfinList.dataSource.data([]);     
	    			  datosEquiposfinList.dataSource.data(ute_num);
	    			  datosEquiposfinList.select(0);
	    			  itemEquiposfin = datosEquiposfinList.dataItem();
	    			  //$("#txt_desc_equfin").html(itemEquipos.id_equnrx);		    			  
	    			  
//	    			  datosTiposList.dataSource.data([]);     
//	    			  datosTiposList.dataSource.data(getProcesos("proceso","00"));
//	    			  datosTiposList.select(0);	
//	    			  itemTipos = datosTiposList.dataItem();
//	    			  
//	    			  datosSubTiposList.dataSource.data([]);     
//	    			  datosSubTiposList.dataSource.data(getProcesos("",itemTipos.id_ticket_file));
//	    			  datosSubTiposList.select(0);
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
		    	  //$("#mensajes_main").html(response.respGetUnidadesTecnicasSuperiores.descripcion);
	    	  }
	      }	      
	    },  
	    error: function(e){  
	    	//$.unblockUI()
	    	//$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
	    }  
	  });  
	}  

function onChangeTipoReporte(e)
{
	var datosEstatusList = $("#tipo").data('kendoComboBox');
	var itemEstatus = datosEstatusList.dataItem();
	//$("#descripcionTipo").html(itemEstatus.i_tipo_eq);
}

function onChangeFacesRegistroReporte(e)
{
	var datosFaseList = $("#faseReporte").data('kendoComboBox');
	var itemFases = datosFaseList.dataItem();
	//$("#txt_desc_fase").html(itemFases.text);
	fillComboRegistroReporte("equipos");
	fillComboTiposReporte(itemFases.ubicacionClave);
}

function onChangeEquipoRegistroReporte(e)
{
	var datosEquiposList = $("#equipoReporte").data('kendoComboBox');
	var itemEquipos = datosEquiposList.dataItem();
	//$("#txt_desc_equ").html(itemEquipos.id_equnrx);
}

function onChangeEquipoRegistroReporteFin(e)
{
	var datosEquiposfinList = $("#equipoReportefin").data('kendoComboBox');
	var itemEquiposfin = datosEquiposfinList.dataItem();
	//$("#txt_desc_equfin").html(itemEquiposfin.id_equnrx);
}
function cancelarGridFiltroBusquedaCriteriosReporteEstadoCuenta() {
	$("#windowConsultaCatalogoReporteFilter").data("kendoWindow").close();
}

function executeFindBusquedaCatalogoReporteFilter (tipo_,frep,eqrep,eqrepfin) {
	$("#tipoP").val(tipo_);
	$("#faseReporteP").val(frep);
	$("#equipoReporteP").val(eqrep);
	$("#equipoReportefinP").val(eqrepfin);
	
	
	$.ajax({  
	    type: "POST", 
	    cache : false,
	    async:  false,
	    url: contexPath + "/report/listaprecios/ConsultaListaPreciosPreview.htm",  
	    data: "idTipoReporte=" + tipo_ + 
	          "&idFaseReporte="   + frep + 
	          "&idEquipoInicialReporte=" + eqrep +
	          "&idEquipoFinalReporte=" + eqrepfin, 
	    success: function(response){
	    	if (response.mensaje == "SUCCESS") {
				$("#mensajes_main").html("Registros encontrados");
				//alert(response.listaDisponibilidad);
								
				var dataSourceConsultaCatalogoReportes = new kendo.data.DataSource({
			        data: response.listaPrecio,	    	    	        
			        pageSize: 50,
			        messages: {
			            empty: "No hay registros que mostrar"
			        }
			    });
			$("#GridConsultaCatalogoReportes").empty();
			$("#GridConsultaCatalogoReportes").kendoGrid( {
						 dataSource : dataSourceConsultaCatalogoReportes,
						 selectable:"row",
						 resizable: true,							
						 sortable: true,
						 reorderable: true,	
						 pageable: {
	                         input: true,
	                         numeric: false,
	                         pageSizes: [50,100,150],
	                         messages: { 
								display: "{1} de {2} registros",
								page: "Pagina",
								of: "de",
								itemsPerPage: "registros por pagina"
							  }
	                    	},	
		               
						 columns : [ {
							field : "fase",
							title : "Fase",
							width: 120
						}, {
							field : "equnr",
							title : "Casa / Depto.",
							width: 150
						}, {
							field : "m2pr",
							title : "M2 Priv.",
							width: 100
						}, {
							field : "m2ja",
							title : "M2 Jardin",
							width: 80
						}, {
							field : "m2te",
							title : "M2 Terraza",
							width: 80
						},{
							field : "m2es",
							title : "M2 Est.",
							width: 50
						},{
							field : "m2bo",
							title : "M2 Bod.",
							width: 50
						},{
							field : "sumaM2",
							title : "Suma",
							width: 100
						},{
							field : "tipo",
							title : "Tipo",
							width: 50
						},{
							field : "visa",
							title : "Vista a",
							width: 100
						},{
							field : "orie",
							title : "Orientacion",
							width: 100
						},{
							field : "nest",
							title : "# Estac.",
							width: 50
						},{
							field : "nbod",
							title : "# Bod.",
							width: 50
						},{
							field : "precioLista",
							title : "Total",
							width: 100
						},{
							field : "pach",
							title : "Desc. CH",
							width: 100
						},{
							field : "paco",
							title : "Desc. CO",
							width: 100
						},{
							field : "pafi",
							title : "Desc. FO",
							width: 100
						},{
							field : "stun",
							title : "Estatus",
							width: 100
						}]
		            });	
				if (response.listaPrecio.length > 0) {
					document.getElementById("verDetalleReporte").style.visibility='';
					document.getElementById("verDetalleReporteExcel").style.visibility='';
				} else {
					document.getElementById("verDetalleReporte").style.visibility='hidden';						
					document.getElementById("verDetalleReporteExcel").style.visibility='hidden';						
				}
				
				$("#windowConsultaCatalogoReporteFilter").data("kendoWindow").close();
	    	}  else if (response.mensaje == "LOGOUT") { 
	    		salirSistema();
	    	} else {
	    		$("#mensajes_main").html("Fallo el acceso a los datos " + response.descripcion);
	    	}
	    },  
	    error: function(e){  
	    	//$.unblockUI()
	    	$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
	    }  
	  });  
	    
}
