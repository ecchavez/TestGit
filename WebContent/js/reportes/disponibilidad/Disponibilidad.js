function dialogoFiltroConsultaEdoCtaReporte() {   
	var windowSelecCarCli =$("#windowConsultaCatalogoReporteFilter").kendoWindow({
			actions: ["Close"],
			modal: true,
			resizable: false,
			content: 
				{
					url:contexPath+"/report/disponibilidad/ReporteDisponibilidadFilterView.htm"
			},
	        height: "350px",
	     	width: "610px",
	        title: "Parametros de Selecci�n"
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
	  var datosEstatusList  = $("#estatus").data('kendoComboBox');
	  var itemEstatus       = datosEstatusList.dataItem();

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

function onChangeEstatusReporte(e)
{
	var datosEstatusList = $("#estatus").data('kendoComboBox');
	var itemEstatus = datosEstatusList.dataItem();
	//$("#descripcionEstatus").html(itemEstatus.stunx);
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

function executeFindBusquedaCatalogoReporteFilter (est,frep,eqrep,eqrepfin) {
	$("#estatusP").val(est);
	$("#faseReporteP").val(frep);
	$("#equipoReporteP").val(eqrep);
	$("#equipoReportefinP").val(eqrepfin);
//	alert($("#estatus").val());
//	alert($("#faseReporte").val());
//	alert($("#equipoReporte").val());
//	alert($("#equipoReportefin").val());
	
	$.ajax({  
	    type: "POST", 
	    cache : false,
	    async:  false,
	    url: contexPath + "/report/disponibilidad/ConsultaDisponibilidadPreview.htm",  
	    data: "idEstatusReporte=" + est + 
	          "&idFaseReporte="   + frep + 
	          "&idEquipoInicialReporte=" + eqrep +
	          "&idEquipoFinalReporte=" + eqrepfin, 
	    success: function(response){
	    	if (response.mensaje == "SUCCESS") {
				$("#mensajes_main").html("Registros encontrados");
				//alert(response.listaDisponibilidad);
				/*window.parent.$("#GridConsultaCatalogoReportes").data("kendoGrid").dataSource.data(response.listaDisponibilidad);	
				window.parent.$("#GridConsultaCatalogoReportes").data("kendoGrid").refresh();*/
				var dataSourceConsultaCatalogoReportes = new kendo.data.DataSource({
			        data: response.listaDisponibilidad,	    	    	        
			        pageSize: 50,
			        messages: {
			            empty: "No hay registros que mostrar"
			        }
			    });
			    $("#GridConsultaCatalogoReportes").empty();
				$("#GridConsultaCatalogoReportes").kendoGrid( {
						 dataSource : dataSourceConsultaCatalogoReportes,
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
							field : "descripcionFase",
							title : "Fase",
							width: 150
						}, {
							field : "casaDepto",
							title : "Casa / Depto.",
							width: 150
						}, {
							field : "descripcionTipo",
							title : "tipo",
							width: 100
						}, {
							field : "m2Privativos",
							title : "M2 PRIVATIVOS",
							width: 100
						}, {
							field : "descripcionEstatus",
							title : "Descripcion Estatus",
							width: 100
						},{
							field : "idCliente",
							title : "Cliente",
							width: 100
						},{
							field : "nombreCliente",
							title : "Nombre Cliente",
							width: 150
						},{
							field : "apPaterno",
							title : "Ap. Paterno",
							width: 150
						}],
						selectable: "row"
		            });	

				
				//window.parent.$("#GridConsultaCatalogoReportes").data("kendoGrid").Pageable(x => x.PageSizes(new int[] { 10, 20, 50, 100 }));
				if (response.listaDisponibilidad.length > 0) {
					document.getElementById("verDetalleReporte").style.visibility='';
				} else {
					document.getElementById("verDetalleReporte").style.visibility='hidden';						
				}
				
				$("#windowConsultaCatalogoReporteFilter").data("kendoWindow").close();
	    	} else if (response.mensaje == "LOGOUT") { 
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
