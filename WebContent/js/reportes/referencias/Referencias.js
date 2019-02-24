function dialogoFiltroConsultaEdoCtaReporte() {   
	var windowSelecCarCli =$("#windowConsultaCatalogoReporteFilter").kendoWindow({
			actions: ["Close"],
			modal: true,
			resizable: false,
			content: 
				{
					url:contexPath+"/report/referencias/ReporteReferenciasFilterView.htm"
			},
	        height: "350px",
	     	width: "610px",
	        title: "Parametros de Selección"
		    });	
	windowSelecCarCli = $("#windowConsultaCatalogoReporteFilter").data("kendoWindow");
	windowSelecCarCli.center();
	windowSelecCarCli.open();
}

/*
 * 
 */
var loadComboEstatusReporte = false;
function fillComboRegistroReporte (accion) {			
	  var datosFaseList     = $("#faseReporte").data('kendoComboBox');
	  var itemFases         = datosFaseList.dataItem();
	  var datosEquiposList  = $("#equipoReporte").data('kendoComboBox');
	  var itemEquipos       = datosEquiposList.dataItem();
	  var datosEquiposfinList  = $("#equipoReportefin").data('kendoComboBox');
	  var itemEquiposfin       = datosEquiposfinList.dataItem();
	  var datosBancoList  = $("#bancoReporte").data('kendoComboBox');
	  var itemBanco       = datosBancoList.dataItem();

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

	    	  if (accion=="banco" && !loadComboEstatusReporte) {
    			  bancoArr = response.respGetUnidadesTecnicasSuperiores.bancos;
    	    	  loadComboEstatusReporte = true;
	    		  $("#mensajes_main").html("Se encontraron "+uts_num.length+ " registros");
	    		  
	    		  datosBancoList.dataSource.data([]);     
	    		  datosBancoList.dataSource.data(bancoArr);
	    		  datosBancoList.select(0);
	    		  itemBanco = datosBancoList.dataItem();	
	    		  //$("#descripcionBanco").html(itemBanco.text);
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

function onChangeBancoReporte(e)
{
	var datosBancoList = $("#bancoReporte").data('kendoComboBox');
	var itemBanco = datosBancoList.dataItem();
	//$("#descripcionBanco").html(itemBanco.stunx);
}

function onChangeFacesRegistroReporte(e)
{
	var datosFaseList = $("#faseReporte").data('kendoComboBox');
	var itemFases = datosFaseList.dataItem();
	//$("#txt_desc_fase").html(itemFases.text);
	fillComboRegistroReporte("equipos");
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

function executeFindBusquedaCatalogoReporteFilter (bcoRep,frep,equrep,equrepf) {
	$("#idBancoLowP").val(bcoRep);
	$("#idFaseLowP").val(frep);
	$("#equipoReporteP").val(equrep);
	$("#equipoReportefinP").val(equrepf);
	
	$.ajax({  
	    type: "POST", 
	    cache : false,
	    async:  false,
	    url: contexPath + "/report/referencias/ConsultaReferenciasPreview.htm",  
	    data: "idFaseLow=" + frep + 
	          "&idBancoLow="   + bcoRep + 
	          "&idEquipoInicialReporte=" + equrep +
	          "&idEquipoFinalReporte=" + equrepf, 
	    success: function(response){
	    	if (response.mensaje == "SUCCESS") {
				$("#mensajes_main").html("Registros encontrados");
				//alert(response.listaReferencias);
				$("#GridConsultaCatalogoReportes").empty();		
				var dataSourceConsultaCatalogoReportes = new kendo.data.DataSource({
			        data: response.listaReferencias,	    	    	        
			        pageSize: 50,
			        messages: {
			            empty: "No hay registros que mostrar"
			        }
			    });
			    
			$("#GridConsultaCatalogoReportes").kendoGrid( {
						 dataSource : dataSourceConsultaCatalogoReportes,
						 selectable:"row",
						 resizable: true,							
						 sortable: true,
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
							field : "idFase",
							title : "Fase",
							width: 80
						}, {
							field : "idEqunr",
							title : "Casa / Depto.",
							width: 80
						}, {
							field : "idBanco",
							title : "Banco",
							width: 80
						}, //{
							//field : "refPago",
							//title : "Referencia",
							//width: 100
						//}, 
						{
							field : "cta",
							title : "Cuenta",
							width: 80
						}, {
							field : "clabe",
							title : "Clabe",
							width: 80
						}
						]
		            });
				if (response.listaReferencias.length > 0) {
					document.getElementById("verDetalleReporte").style.visibility='';
				} else {
					document.getElementById("verDetalleReporte").style.visibility='hidden';						
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