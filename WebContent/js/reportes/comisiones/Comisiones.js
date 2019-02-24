function dialogoFiltroConsultaEdoCtaReporte() {   
	var windowSelecCarCli =$("#windowConsultaCatalogoReporteFilter").kendoWindow({
			actions: ["Close"],
			modal: true,
			resizable: false,
			content: 
				{
					url:contexPath+"/report/comisiones/ReporteComisionesFilterView.htm"
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
	    		  $("#descripcionEstatus").html(itemAreas.text);
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
		    		  $("#txt_desc_fase").html(itemFases.text);
		    		  fillComboRegistroReporte("equipos");
	    		  }
	    		  else if(accion=="equipos")
	    		  {
	    			  $("#mensajes_main").html("Se encontraron "+ute_num.length+ " registros");
		    		  
	    			  datosEquiposList.dataSource.data([]);     
	    			  datosEquiposList.dataSource.data(ute_num);
	    			  datosEquiposList.select(0);
	    			  itemEquipos = datosEquiposList.dataItem();
	    			  $("#txt_desc_equ").html(itemEquipos.id_equnrx);		    			  

	    			  datosEquiposfinList.dataSource.data([]);     
	    			  datosEquiposfinList.dataSource.data(ute_num);
	    			  datosEquiposfinList.select(0);
	    			  itemEquiposfin = datosEquiposfinList.dataItem();
	    			  $("#txt_desc_equfin").html(itemEquipos.id_equnrx);		    			  
	    			  
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
	$("#descripcionEstatus").html(itemEstatus.stunx);
}

function onChangeFacesRegistroReporte(e)
{
	var datosFaseList = $("#faseReporte").data('kendoComboBox');
	var itemFases = datosFaseList.dataItem();
	$("#txt_desc_fase").html(itemFases.text);
	fillComboRegistroReporte("equipos");
}

function onChangeEquipoRegistroReporte(e)
{
	var datosEquiposList = $("#equipoReporte").data('kendoComboBox');
	var itemEquipos = datosEquiposList.dataItem();
	$("#txt_desc_equ").html(itemEquipos.id_equnrx);
}

function onChangeEquipoRegistroReporteFin(e)
{
	var datosEquiposfinList = $("#equipoReportefin").data('kendoComboBox');
	var itemEquiposfin = datosEquiposfinList.dataItem();
	$("#txt_desc_equfin").html(itemEquiposfin.id_equnrx);
}
function cancelarGridFiltroBusquedaCriteriosReporteEstadoCuenta() {
	window.parent.$("#windowConsultaCatalogoReporteFilter").data("kendoWindow").close();
}

function executeFindBusquedaCatalogoReporteFilter (fase, rini,rfin) {
	$("#faseReporteP").val(fase);
	$("#equipoReporteP").val(rini);
	$("#equipoReportefinP").val(rfin);
	
	$.ajax({  
	    type: "POST", 
	    cache : false,
	    async:  false,
	    url: contexPath + "/report/comisiones/ConsultaComisionesPreview.htm",  
	    data: "idFaseReporte="   + fase + 
	          "&idEquipoInicialReporte=" + rini +
	          "&idEquipoFinalReporte=" + rfin, 
	    success: function(response){
	    	if (response.mensaje == "SUCCESS") {
				$("#mensajes_main").html("Registros encontrados");
				//alert(response.listaComisiones);
				$("#GridConsultaCatalogoReportes").data("kendoGrid").dataSource.data(response.listaComision);
				
				if (response.listaComision.length > 0) {
					document.getElementById("verDetalleReporte").style.visibility='';
				} else {
					document.getElementById("verDetalleReporte").style.visibility='hidden';						
				}
				
				$("#windowConsultaCatalogoReporteFilter").data("kendoWindow").close();
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
