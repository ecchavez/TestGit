function dialogoFiltroConsultaEdoCtaReporte() {   
	var windowSelecCarCli =$("#windowConsultaCatalogoReporteFilter").kendoWindow({
			actions: ["Close"],
			modal: true,
			resizable: false,
			content: 
				{
					url:contexPath+"/report/estadocuenta/ReporteEstadoCuentaFilterView.htm"
			},
	        height: "550px",
	     	width: "810px",
	        title: "Parametros de Selección"
		    });	
	windowSelecCarCli = $("#windowConsultaCatalogoReporteFilter").data("kendoWindow");
	windowSelecCarCli.center();
	windowSelecCarCli.open();
}

function executeFindBusquedaCatalogoReporteFilter (idCteRep,idFaseRep,idEquRep) {
	$.ajax({  
	    type: "POST", 
	    cache : false,
	    async:  false,
	    url: contexPath + "/report/estadocuenta/CatalogoClientes.htm",  
	    data: "idClienteReporte=" + idCteRep + "&idFaseReporte=" + idFaseRep + "&idEquipoReporte=" + idEquRep, 
	    success: function(response){
	    	if (response.mensaje == "SUCCESS") {
				$("#mensajes_main").html("Registros encontrados");

				var dataSourceConsultaCatalogoReportes = new kendo.data.DataSource({
			        data: response.layout,	    	    	        
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
							field : "idCliente",
							title : "#Cliente",
							width: 100
						}, {
							field : "nombre",
							title : "Nombre",
							width: 100
						}, {
							field : "segundo",
							title : "Segundo",
							width: 100
						}, {
							field : "appaterno",
							title : "Ap. Paterno",
							width: 100
						}, {
							field : "apmaterno",
							title : "Ap. Materno",
							width: 100
						},{
							field : "desarrollo",
							title : "Desarrollo",
							width: 100
						},{
							field : "fase",
							title : "Fase",
							width: 100
						},{
							field : "casadepto",
							title : "Casa/Depto.",
							width: 100
						},{
							field : "pedido",
							title : "Pedido",
							width: 100
						}]
		            });	
				if (response.layout.length > 0) {
					document.getElementById("verDetalleReporte").style.visibility='';
				} else {
					document.getElementById("verDetalleReporte").style.visibility='hidden';						
				}
				
				$("#windowConsultaCatalogoReporteFilter").data("kendoWindow").close();
	    	} else if (response.mensaje == "LOGOUT") {
	    		salirSistema();
	    	}
	    	else {
	    		$("#mensajes_main").html("Fallo el acceso a los datos " + response.descripcion);
	    	}
	    },  
	    error: function(e){  
	    	//$.unblockUI()
	    	$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
	    }  
	  });  
	    
}

/*
 * 
 */
var loadComboAreasRegistroReporte = false;
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

/*
 * Funcion que habilita el dialogo que contiene el grid de busqueda para el cliente Reportes
 */
function dialogoGridBusquedaReporteEstadoCuenta() {   
	var windowParamsClienteRegistroReporte =$("#windowGridBusquedaClienteRegistroReporte").kendoWindow({
			actions: ["Close"],
			modal: true,
			resizable: false,
			content: 
				{
					url:contexPath+"/report/estadocuenta/GridBusquedaClienteRegistroReport.htm"
			},
	        height: "450px",
	     	width: "710px"
		    });	
	windowParamsClienteRegistroReporte = $("#windowGridBusquedaClienteRegistroReporte").data("kendoWindow");
	windowParamsClienteRegistroReporte.center();
	windowParamsClienteRegistroReporte.open();
}
function cancelarGridFiltroBusquedaCriteriosReporteEstadoCuenta() {
	window.parent.$("#windowConsultaCatalogoReporteFilter").data("kendoWindow").close();
}

/*
 * Funcion que habilita los parametros de registro para el campo de cliente
 */
function dialogoParamsClienteRegistroReporte() {   
	var windowParamsClienteRegistroReporte =$("#windowParamsBusquedaClienteRegistroReporte").kendoWindow({
			actions: ["Close"],
			modal: true,
			resizable: false,
			content: 
				{
					url:contexPath+"/report/estadocuenta/ParamsClienteRegistroReporte.htm"
			},
	        height: "350px",
	     	width: "450px"
		    });	
	windowParamsClienteRegistroReporte = $("#windowParamsBusquedaClienteRegistroReporte").data("kendoWindow");
	windowParamsClienteRegistroReporte.center();
	windowParamsClienteRegistroReporte.open();
}

/*
 * Agrega parametros de busqueda campo cliente para registro de Reporte
 */
function addNomClienteParams() {
	var gridNom = $("#gridclientesReporte").data("kendoGrid");
	var model = gridNom.dataItem(gridNom.select());	
	window.parent.$("#claveClienteReporte").val(model.id_car_cli);
	window.parent.$("#nomClienteReporte").html(model.nombre1 + " " + model.nombre2 + " " + model.app_pat + " " + model.app_mat);
	//var arrVendedorSeleccion = arrVendedores[model.id_car_cli];

	var userSalesManSap   = "";
	var nombreSalesManSap = "";
	var idSalesManSap     = 0;
//	if (arrVendedorSeleccion != undefined) {
//		for (i = 0 ; i < arrVendedorSeleccion.length ; i++) {
//			if (parseInt (arrVendedorSeleccion[i].id_visita) > idSalesManSap) {
//				idSalesManSap     = parseInt (arrVendedorSeleccion[i].id_visita);
//				userSalesManSap   = arrVendedorSeleccion[i].slsman;
//				nombreSalesManSap = arrVendedorSeleccion[i].slsman_nombre1 + " " + (arrVendedorSeleccion[i].slsman_app_pat != undefined ? arrVendedorSeleccion[i].slsman_app_pat:"")+ " " + (arrVendedorSeleccion[i].slsman_app_mat != undefined ? arrVendedorSeleccion[i].slsman_app_mat:"");
//			}
////			alert(arrVendedorSeleccion[i].id_visita);
////			alert(arrVendedorSeleccion[i].slsman);
////			alert(arrVendedorSeleccion[i].slsman_nombre1 + " " + (arrVendedorSeleccion[i].slsman_app_pat != undefined ? arrVendedorSeleccion[i].slsman_app_pat:"")+ " " + (arrVendedorSeleccion[i].slsman_app_mat != undefined ? arrVendedorSeleccion[i].slsman_app_mat:""));
//		}
//	}
	//alert (idSalesManSap);
	window.parent.$("#claveVendedorReporte").val(userSalesManSap);
	window.parent.$("#nomVendedorReporte").html(nombreSalesManSap);
	
	window.parent.$("#windowGridBusquedaClienteRegistroReporte").data("kendoWindow").close();
}

/*
 * Cancela la edicion de parametros de busqueda campo cliente para registro de Reporte
 */
function cancelarNomClienteParams() {
	window.parent.$("#windowGridBusquedaClienteRegistroReporte").data("kendoWindow").close();
}

/*
 * Funcion reutilizada y copiada para la busqueda de clientes
 */
//funcion para concatenar los para,etros de envio de consulta de clientes y creacion de los data source de los mismos 
function armaEstructuraCriterioClientesReporte() {	
	_that=this;
	// get the form values
	if($("#nomTxt").val()=="" && $("#nom2Txt").val()=="" && $("#apPatTxt").val()=="" && $("#apMatTxt").val()=="") {
		kendoConsole.log("Capture criterios de busqueda","error")
	} else {
	if($("#nomTxt").val()!="" && this.dataNombre.length==0)	{
		xmlNom ="<criterios><criterio><nombre>"+$("#nomTxt").val()+"</nombre></criterio></criterios>";
	} else if(this.dataNombre.length>0) {
		var criteriosNom=[];
		for(var i=0; i<this.dataNombre.length; i++)	{	
			var params={};
			if(this.dataNombre[i].nombre!="" ) {
				params["nombre"]=this.dataNombre[i].nombre;
				criteriosNom.push( params);	
			}
		}
		xmlNom = createXMLCriterios("criterios",criteriosNom);
	}
	if($("#nom2Txt").val()!="" && this.dataNombre2.length==0) {
		xmlNom2 ="<criterios><criterio><nombre2>"+$("#nom2Txt").val()+"</nombre2></criterio></criterios>";
	} else if(this.dataNombre2.length>0) {
		var criteriosNom2=[];
		for(var i=0; i<this.dataNombre2.length; i++) {
			var params2={};
			if(this.dataNombre2[i].nombre2!="" ){
				params2["nombre2"]=this.dataNombre2[i].nombre2;
				criteriosNom2.push(params2);	
			}
		}
		xmlNom2 = createXMLCriterios("criterios",criteriosNom2);
	}
	if($("#apPatTxt").val()!="" && this.dataApPaterno.length==0) {
		xmlApPat ="<criterios><criterio><apPat>"+$("#apPatTxt").val()+"</apPat></criterio></criterios>";
	} else if(this.dataApPaterno.length>0){
		var criteriosApPat=[];
		for(var i=0; i<this.dataApPaterno.length; i++) {
			var params3={};
			if(this.dataApPaterno[i].apPat!="" ){
				params3["apPat"]=this.dataApPaterno[i].apPat;
				criteriosApPat.push(params3);	
			}
		}
		xmlApPat = createXMLCriterios("criterios",criteriosApPat);
	}	
	if($("#apMatTxt").val()!="" && this.dataApMaterno.length==0) {
		xmlApMat ="<criterios><criterio><apMat>"+$("#apMatTxt").val()+"</apMat></criterio></criterios>";
	} else if(this.dataApMaterno.length>0) {
		var criteriosApMat=[];
		for(var i=0; i<this.dataApMaterno.length; i++) {
			var params4={};
			if(this.dataApMaterno[i].apMat!="" ){
				params4["apMat"]=this.dataApMaterno[i].apMat;
				criteriosApMat.push(params4);	
			}
		}
		xmlApMat = createXMLCriterios("criterios",criteriosApMat);
	}
	window.parent.xmlNom=xmlNom;
	window.parent.xmlNom2=xmlNom2;
	window.parent.xmlApPat=xmlApPat;
	window.parent.xmlApMat=xmlApMat;
	window.parent.ejecutaFiltroCriterioClientesReporte();	
  }
}

//para enviar los xml de consulta en catalogo de cartera de cleintes, dependiendo los criterios captirados
//var arrVendedores = new Object();
function ejecutaFiltroCriterioClientesReporte() {
	$.ajax( {
		type : "POST",
		cache : false,
		url : contexPath + "/report/estadocuenta/CatalogoClientesReporte.htm",
		data : "accion=2&xmlNom=" + xmlNom+"&xmlNom2="+xmlNom2+"&xmlApPat="+xmlApPat+"&xmlApMat="+xmlApMat,
		success : function(response) {
			// we have the response
		if (response.respGetInfCarCliente.mensaje == "SUCCESS") {
			$("#windowParamsBusquedaClienteRegistroReporte").data("kendoWindow").close();  
			$("#mensajes_main").html("Registros encontrados");
			
			clientesDataExport = response.respGetInfCarCliente.clientesList;
			
			dataClient = [];
			
			for (i = 0; i < clientesDataExport.length; i++) {
				dataClient.push(clientesDataExport[i]);
				//alert (clientesDataExport[i].id_car_cli);
				//alert(clientesDataExport[i].visitasClienteList);
				//arrVendedores[clientesDataExport[i].id_car_cli] = clientesDataExport[i].visitasClienteList;
			}
			
			var dataSourceClienteCar = new kendo.data.DataSource({
		        data: dataClient,	    	        
		        pageSize: 50
		    });

			$("#gridclientesReporte").empty();
			

			$("#gridclientesReporte").kendoGrid( {
				 dataSource : dataSourceClienteCar,
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
                 scrollable:{
					virtual:true
				},
				 columns : [ {
					field : "id_car_cli",
					title : "Id Cliente",
					width: 100
						
				}, {
					field : "nombre1",
					title : "Nombre",
					sortable: true,
					width: 110
				}, {
					field : "nombre2",
					title : "Segundo Nombre",
					width: 110
				}, {
					field : "app_pat",
					title : "Apellido",
					width: 100
				}, {
					field : "app_mat",
					title : "Segundo Apellido",
					width: 100
				}, {
					field : "fch_ncm",
					title : "F.Nac",
					width: 100
				}]
            });	
			
					
		} else if (response.respGetInfCarCliente.mensaje == "LOGOUT") { 
			salirSistema();
		} else {
			//kendoConsole.log("Hubo un error en carga clientes: "+response.respGetInfCarCliente.descripcion ,"error");
			$("#mensajes_main").html("Hubo un error en carga clientes: "+response.respGetInfCarCliente.descripcion);
			$("#gridclientesReporte").empty();
			reiniciaGridBusquedaClienteReporte();
		}
	},
	error : function(e) {
		   
		   //kendoConsole.log(e,"error");
		  $("#mensajes_main").html("Hubo un error en carga clientes: "+ e);
	  }
	});

}

function cancelarGridBusquedaCriteriosClienteReporte() {
	window.parent.$("#windowParamsBusquedaClienteRegistroReporte").data("kendoWindow").close();
}

function reiniciaGridBusquedaClienteReporte() {
	//arrVendedores = new Object();
	var dataSourceClienteCar = new kendo.data.DataSource({
        data: [ ],	    	        
        pageSize: 50
    });
	
	$("#gridclientesReporte").kendoGrid( {
		 dataSource : dataSourceClienteCar,
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
        scrollable:{
			virtual:true
		},
		 columns : [ {
			field : "id_car_cli",
			title : "Id Cliente",
			width: 100
				
		}, {
			field : "nombre1",
			title : "Nombre",
			sortable: true,
			width: 110
		}, {
			field : "nombre2",
			title : "Segundo Nombre",
			width: 110
		}, {
			field : "app_pat",
			title : "Apellido",
			width: 100
		}, {
			field : "app_mat",
			title : "Segundo Apellido",
			width: 100
		}, {
			field : "fch_ncm",
			title : "F.Nac",
			width: 100
		}]
   });

}
