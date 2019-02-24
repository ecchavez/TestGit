//varaibles globales 
var xmlPagos="";
var dataItemCotizacion=null;
var ids_add=[];
var ClientesInfo=null;
var dataCotizacion=[];
var dataPedido=[];
var dataNombre=[];
var dataNombre2=[];
var dataApPaterno=[];
var dataApMaterno=[];
var dataEquipo=[];
var xmlNom="";
var xmlNom2="";
var xmlApPat="";
var xmlApMat="";
var xmlCot="";
var xmlCli="";
var xmlEq="";
var arregloIds=[];
var dataFind = [];
var dataFindAux = [];
var cotizador_open=false;
var dataItemPagoMonitorHead="";
var dataItemPagoAddMonitor=null;
var idClienteCarteraActual='';
var regimenFiscal="";
var arrDataActualizado='';
var uts_ct="";
var equ_ct="";
var st_contrato={};
var criteriosImpresionContrato=[];

function extraccionArray(dataGridAux,tipo)
//funcion que extrae la infoirmacion de los arrays de params seleccion
{
	if(dataGridAux!=null)
	{     
		 var criterios = [];
		 for(var i=0; i<dataGridAux.length; i++)
			{
			if(tipo=='cotizacion')
			 {
				 if(dataGridAux[i].cotizacion!="" ){
						var params = {};
						params[tipo] = dataGridAux[i].cotizacion;
						criterios.push(params);
					} 
			 }
			 
			if(tipo=='pedido')
			 {
				 if(dataGridAux[i].pedido!="" ){
						var params = {};
						params[tipo] = dataGridAux[i].pedido;
						criterios.push(params);
					} 
			 }
			if(tipo=='clientes')
			 {
				 if(dataGridAux[i].id!="" ){
						var params = {};
						params[tipo] = dataGridAux[i].id;
						criterios.push(params);
					} 
			 }
			if(tipo=='equipo')
			 {
				 if(dataGridAux[i].equipo!="" ){
						var params = {};
						params[tipo] = dataGridAux[i].equipo;
						criterios.push(params);
					} 
			 }
			}
	}
	return createXMLCriterios("criterio",criterios);
	
}



//funcion que  dependiendo de los idClientes q selecciono, busca cotizacions o pedidos 
function BuscarCotPed(from){
		if(arregloIds.length>0)
		{
			xmlCli=extraccionArray(arregloIds,'clientes')
			
		}else{
			xmlCli="";
			//kendoConsole.log("No selecciono nada para buscar","warning");
			$("#mensajes_main").html("No selecciono nada para buscar");
		}
	window.parent.xmlCli=xmlCli;
	window.parent.xmlCot="";
	window.parent.xmlEq="";
	window.parent.setGridPrincipal(from);	
   
}
//funcion que llena el grid principal con cotizaciones o pedidos encontrados
function setGridPrincipal(from)
{
	var cotPed="";
	if(from=="pagoIni" || from=="cotizador")
	{
		cotPed=0; //busqueda por cotizacion en la 043
	}else if(from=="pagoParc" || from=="cancelacion" || from=="monitorPagoPrin"){
		cotPed=1; //busqueda por pedido en la 043
	}
	
	var datos = $('#selecCierre').serialize()+"accion=2&cotPed="+cotPed+"&xmlCli="+xmlCli;
    if (from=="monitorPagoPrin"){
    	buscaPedidosParaMonitor(datos);
    } else {
    	buscaCotizacionPedidoCierre(datos);
    }
}

function buscaCotizacionPedidoCierre(paramDatosBusqueda,paramDesde){
	$.ajax( {
		type : "POST",
		url : contexPath + "/CierreVenta.htm",
		data : paramDatosBusqueda,
		success : function(response) {
    	// we have the response
		if (response.responseCotizacion.mensaje == "SUCCESS") {
			if (paramDesde!=='DESDEMONITOR'){
				$("#windowSelecCierre").data("kendoWindow").close();	
			}
				
			$("#mensajes_main").html("Registro(s) seleccionado exitosamente");
            $("#abrirCot").attr("style", "visibility: visible;width:100px");
			$("#regPago").attr("style", "visibility: visible;width:100px");
			$("#iDcancelacion").attr("style", "visibility: visible;width:100px");
			
			var findDataExport = response.responseCotizacion.objCotInfoList;

			var dataFind = [];

			var cotizaciones=false;

			for (i = 0; i < findDataExport.length; i++) {

			if(findDataExport[i].idCotizacion!="")

			{

			cotizaciones=true;

			}

			
			
			dataFind.push(findDataExport[i]);

			}

			$("#fromMonitorXmlClienteUri").val(findDataExport[0].idCarCliente); //asigna uri temporal
			
			
			var dataSourceFind = new kendo.data.DataSource( {
			data : dataFind,
			pageSize : 50
			});

			$("#dg_FindCotsPeds").empty();

			$("#dg_FindCotsPeds").kendoGrid( {
			dataSource : dataSourceFind,			
			selectable:	"row",
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
			change: onChangeCotizacion,
			columns : [	{
			field :	"idCotizacion",
			title :	"Cotizacion",
			width:100
			}, {
			field :	"inicioVig",
			title :	"Inicio Vigencia",
			width:100
			},
			{
			field :	"finVig",
			title :	"Fin Vigencia",
			width:100
			}, {
			field :	"idPedido",
			title :	"Id Pedido",
			width:100
			}, {
			field :	"dptoCasa",
			title :	"Dpto/Casa",
			width:150
			}, {
			field :	"idCliente",
			title :	"Id Cliente",
			width:100
			},{
			field :	"nombre",
			title :	"Nombre",
			width:100,
			}, {
			field :	"snombre",
			title :	"Segundo Nombre",
			width:100
			}, {
			field :	"aapat",
			title :	"Apellido",
			width:100
			}, {
			field :	"amat",
			title :	"Segundo Apellido",
			width:100
			}, {
			field :	"fchNac",
			title :	"F.Nacimiento",
			width:100
			},{
			field :	"spasox",
			title :	"Paso",
			width:150
			},
			{
			field :	"npasox",
			title :	"Estatus Paso",
			width:150
			}, {
			field :	"mje",
			title :	"Mensaje",
			width:600
			}]}).data("kendoGrid"); 
			
		
		} else {
			//kendoConsole.log("Hubo un error: "+response.responseCotizacion.descripcion,"error");
			$("#mensajes_main").html("Hubo un error:"+response.responseCotizacion.descripcion);
	    }
	},
	error : function(e) {
		//kendoConsole.log(e,"error");
		$("#mensajes_main").html("Error:"+e);
		
	}
	});
}
function buscaPedidosParaMonitor(paramDatosBusqueda,paramRegPago,paramFregi){
	$.ajax( {
		type : "POST",
		url : contexPath + "/CierreVenta.htm",
		data : paramDatosBusqueda,
		success : function(response) {
    	// we have the response
		if (response.responseCotizacion.mensaje == "SUCCESS") {
			
			if (paramRegPago=='' || paramRegPago==undefined || paramRegPago==null){
				$("#windowSelecCierre").data("kendoWindow").close();
			}
				
			$("#mensajes_main").html("Registro(s) seleccionado exitosamente");
			
            $("#idRegistroPagoFromMonitor").attr("style", "visibility: visible;width:100px");
			$("#idModificarPagoFromMonitor").attr("style", "visibility: visible;width:100px");
			$("#idDetallePagoFromMonitor").attr("style", "visibility: visible;width:100px");
			$("#idComprobantePagoFromMonitor").attr("style", "visibility: visible;width:100px");
			$("#idImpTalonario").attr("style", "visibility: visible;width:100px");
			$("#impPagare").attr("style", "visibility: visible;width:100px");
			$("#impCartaPromesa").attr("style", "visibility: visible;width:100px");
			$("#impContrato").attr("style", "visibility: visible;width:100px");
			$("#verCliente").attr("style", "visibility: visible;width:100px");
			$("#edoCuenta").attr("style", "visibility: visible;width:100px");
		
			
			var findDataExport = response.responseCotizacion.objCotInfoList;
			var criteriosIdPedido=[];
			var xmlIdPedidos='';
			for (i = 0; i < findDataExport.length; i++) {
				var params={};
				var st_contrato={};
				if(findDataExport[i].idPedido!="" ){
					params["idPedido"]= findDataExport[i].idPedido;
					criteriosIdPedido.push( params);	
					st_contrato={"idPedido" : findDataExport[i].idPedido,
								"stContra" : findDataExport[i].stContra};
					criteriosImpresionContrato.push( st_contrato);
				}
			}
			
			$("#fromMonitorXmlClienteUri").val(findDataExport[0].idCarCliente); //asigna uri temporal
			
			xmlIdPedidos = createXMLCriterios("criterios",criteriosIdPedido);
			var datosMonitor ='';
			if (paramRegPago=='' || paramRegPago==undefined || paramRegPago==null){
			   datosMonitor = $('#selecCierre').serialize()+"&accion=1&xmlPed="+xmlIdPedidos;
			}else{
				datosMonitor ="accion=1&xmlPed="+xmlIdPedidos;
			}   
			
			buscaPedidosEnMonitor(datosMonitor,paramRegPago,'','','','',paramFregi);
			
			
			
		} else {
			//kendoConsole.log("Hubo un error: "+response.responseCotizacion.descripcion,"error");
			$("#mensajes_main").html("Hubo un error:"+response.responseCotizacion.descripcion);
	    }
	},
	error : function(e) {
		//kendoConsole.log(e,"error");
		$("#mensajes_main").html("Error:"+e);
		
	}
	});
}

function buscaPedidosEnMonitor(paramURL,paramRegPago,paramTraeDatos,idClienteParam,paramOrigenRegistro, paramTipoRegistroPago,paramFregi){
	$.ajax( {
		type : "POST",
		url : contexPath + "/MonitorPagos.htm",
		data : paramURL,
		success : function(response) {
    	// we have the response
		if (response.responsePagosReg.mensaje == "SUCCESS") {
				//document.getElementById("resultsPagos").setAttribute("style","visibility:visible")
			   if (paramTraeDatos=='TRAEDATOS'){
				   $("#mensajes_main").html("Registro(s) cargados con exito");
					
		            $("#idRegistroPagoFromMonitor").attr("style", "visibility: visible;width:115px");
					$("#idModificarPagoFromMonitor").attr("style", "visibility: visible;width:115px");
					$("#idDetallePagoFromMonitor").attr("style", "visibility: visible;width:115px");
					$("#idComprobantePagoFromMonitor").attr("style", "visibility: visible;width:115px");
					$("#idImpTalonario").attr("style", "visibility: visible;width:100px");
					$("#impPagare").attr("style", "visibility: visible;width:100px");
					$("#impCartaPromesa").attr("style", "visibility: visible;width:100px");
					$("#impContrato").attr("style", "visibility: visible;width:100px");
					$("#verCliente").attr("style", "visibility: visible;width:100px");
					$("#edoCuenta").attr("style", "visibility: visible;width:100px");
					
					$("#fromMonitorXmlClienteUri").val(idClienteParam); //asigna uri temporal
			        					
			   }
			
				var findDataExportPagos = response.responsePagosReg.objPagoHdrList;
					
				var dataFindPagos = [];
				for (i = 0; i < findDataExportPagos.length; i++) {
					dataFindPagos.push(findDataExportPagos[i]);
				}
				var paramEquipo= "";
				
				if(findDataExportPagos.length>=1)
				{
					paramEquipo=findDataExportPagos[0].id_equnr;
					idClienteCarteraActual=findDataExportPagos[0].kunnr;	
				}
				else
				{
					$("#idRegistroPagoFromMonitor").attr("style", "visibility: hidden;width:115px");
					$("#idModificarPagoFromMonitor").attr("style", "visibility: hidden;width:115px");
					$("#idDetallePagoFromMonitor").attr("style", "visibility: hidden;width:115px");
					$("#idComprobantePagoFromMonitor").attr("style", "visibility: hidden;width:115px");
					$("#idImpTalonario").attr("style", "visibility: hidden;width:100px");
					$("#impPagare").attr("style", "visibility: hidden;width:100px");
					$("#impCartaPromesa").attr("style", "visibility: hidden;width:100px");
					$("#impContrato").attr("style", "visibility: hidden;width:100px");
					$("#verCliente").attr("style", "visibility: hidden;width:100px");
					$("#edoCuenta").attr("style", "visibility: hidden;width:100px");
					setEstatusBar("No existen pagos para este cliente","warning");
				}
					
				var dataSourcePagosLoc = new kendo.data.DataSource({
			        data: dataFindPagos,	    	    	        
			        pageSize: 50,
			        messages: {
			            empty: "No hay registros que mostrar"
			        }
			    });
		
					$("#dg_PagosRegistradosMonitor").empty();
										
					$("#dg_PagosRegistradosMonitor").kendoGrid( {
						 dataSource :dataSourcePagosLoc,
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
		                 change: onChangePagoHdrMonitor,
						 columns : [ 
						{
							field : "vblen",
							title : "Pedido",
							 width:100
						 },
						 {
								field : "dpto",
								title : "Dpto",
								 width:100
						 },
						 {
							field : "fregi",
							title : "Folio de Registro",
							 width:100
						 }, 
						 {
								field : "kunnr",
								title : "Id Cliente",
								 width:100
						 }, {
								field : "kunnrtx",
								title : "Cliente",
								 width:100
						 },
						 {
							format: "{0:c}",
							field : "netwr_pag",
							title : "Monto",
							width:100
						},
						{
							field : "aedat",
							title : "Fecha Registro",
							width:100
						}, {
							field : "cptum",
							title : "Hora Registro",
							width:100
						}, {
							field : "ernam",
							title : "Usuario",
							width:180
						}],autoBind: true
						
					}).data("kendoGrid");	
					
					$("#dg_PagosRegistradosMonitor").data("kendoGrid").refresh();
					
					if (paramRegPago=='SI'){
						window.parent.openWindowImpresion(1000,paramFregi);
						registrarArchivoVoucherVao(paramEquipo);
					}
					
					if (paramOrigenRegistro=='REGISTRO_PAGO'){
						
						openWindowImpresion(5,paramFregi);
						
						registrarArchivoVoucherVao(paramEquipo); //METODO QUE REGISTRA EL ARCHIVO
					}
				
		} else {
			//kendoConsole.log("Hubo un error: "+response.responsePagosReg.descripcion,"error");
			$("#mensajes_main").html("Hubo un error:"+response.responsePagosReg.descripcion);
	    }
	},
	error : function(e) {
		//kendoConsole.log(e,"error");
		$("#mensajes_main").html("Error:"+e);
		
	}
	});

}

function refrescaGridMonitorPagos(dataSourseRegistroPago){
	var dataSourcePagosLoc2 = new kendo.data.DataSource({
        data: dataSourseRegistroPago,	    	    	        
        pageSize: 50,
        messages: {
            empty: "No hay registros que mostrar"
        }
    });
	
	window.parent.arrDataActualizado=dataSourcePagosLoc2;
	
	window.parent.$("#dg_PagosRegistradosMonitor").kendoGrid( {
		 dataSource :window.parent.arrDataActualizado,
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
        change: onChangePagoHdrMonitor,
		 columns : [ 
		{
			field : "vblen",
			title : "Pedido",
			 width:100
		 },
		 {
				field : "dpto",
				title : "Dpto",
				 width:100
		 },
		 {
			field : "fregi",
			title : "Folio de Registro",
			 width:100
		 }, 
		 {
				field : "kunnr",
				title : "Id Cliente",
				 width:100
		 }, {
				field : "kunnrtx",
				title : "Cliente",
				 width:100
		 },
		 {
			format: "{0:c}",
			field : "netwr_pag",
			title : "Monto",
			width:100
		},
		{
			field : "aedat",
			title : "Fecha Registro",
			width:100
		}, {
			field : "cptum",
			title : "Hora Registro",
			width:100
		}, {
			field : "ernam",
			title : "Usuario",
			width:180
		}]
		
	}).data("kendoGrid");	
	
	window.parent.$("#dg_PagosRegistradosMonitor").data("kendoGrid").refresh();
}

//funcion para cambiar el dataItem seleccionado
function onChangePagoHdrMonitor(e) {
	
	var gridPagosMonitorHead = $("#dg_PagosRegistradosMonitor").data("kendoGrid");    
    dataItemPagoMonitorHead = gridPagosMonitorHead.dataItem(gridPagosMonitorHead.tbody.find("tr.k-state-selected"));
    if (dataItemPagoMonitorHead==null || dataItemPagoMonitorHead==undefined || dataItemPagoMonitorHead==''){
    	$("#mensajes_main").html("Seleccione un pedido valido");
    	return;
    }
 }

//funcion que abre pop up para registro de pago 
function registraPagoBtn(fromC)
{
	var paramURIx=$("#fromMonitorXmlClienteUri").val();
	if (fromC=='fromMonitor2'){
		if(dataItemPagoMonitorHead.length==0)
		{
			$("#mensajes_main").html("Seleccione un pedido");
			return;
		}
	} else {
		if(dataItemCotizacion.length==0)
		{
			$("#mensajes_main").html("Seleccione una cotización o busca cotizaciones");
			return;
		}
	}
	openRegPago(fromC,paramURIx);	
	
			

}


// funcion para abrir la ventana de Detalle de Clientes en modo popup
function openRegPago(fromC,paramURI)
{  
	/*var conceptoReferencia = "";
	if(dataItemCotizacion != null){
		if(dataItemCotizacion.idCotizacion != ""){
			conceptoReferencia = dataItemCotizacion.idCotizacion;
		}else if(dataItemCotizacion.idPedido != ""){
			conceptoReferencia = dataItemCotizacion.idPedido;
		}
	}*/
	
    var windowPago=$("#windowRegPago").kendoWindow({
		actions: ["Close"],
		modal: true,
		resizable: false,
		content: 
			{
				url:contexPath+"/RegistroPagoMensual.htm?from="+fromC + "&uriActualizaGrid="+paramURI + "&depto_get="+dataItemCotizacion.dpto
							  //+"&conceptoReferencia=" + conceptoReferencia
			},
        height: "623",//"598",
        title: "Registro de pagos",
		width: "1150"//"960px"
	 }).data("kendoWindow");		
	 windowPago.center();
	 windowPago.open();
}

// funcion para cambiar el dataItem seleccionado
function onChangeCotizacion(e) {
    var grid = $("#dg_FindCotsPeds").data("kendoGrid");    
    dataItemCotizacion = grid.dataItem(grid.tbody.find("tr.k-state-selected"));
}

//Funcion que abre datos de consulta de un cliente 
dataItem = [];
clienteVisual=[];

//Funcion que abre datos de consulta de un cliente 
function verDatosCliente(paramPago)
{
	
	if (window.parent.consultaMonitorPago != undefined) {
		window.parent.consultaMonitorPago = paramPago;
	}
	
	if (paramPago=='SEGUNDOPAGO'){
		var idCliente=$("#idClienteRegPago").val();
		openWinViewClientConsulta("detailCierre","SEGUNDOPAGO",idCliente);
		
	} else {
	   
		if (paramPago=='MONITOR'){
			    var dg_PagosHead = $("#dg_PagosRegistradosMonitor").data("kendoGrid");
				
				var selectedRowPagoHead = dg_PagosHead.dataItem(dg_PagosHead.tbody.find("tr.k-state-selected"));
				
				if(selectedRowPagoHead==null || selectedRowPagoHead==undefined || selectedRowPagoHead=='')
				{
					 //kendoConsole.log("Seleccione un Registro","warning");
					$("#mensajes_main").html("Seleccione un Registro");
					return;
				}
				
				openWinViewClientConsultaMonitorPago("detailCierre",paramPago,selectedRowPagoHead.kunnr);
				
		} else {
			if(dataItemCotizacion==null)
			{
				//kendoConsole.log("Seleccione un pedido","warning");
				$("#mensajes_main").html("Seleccione un pedido");
			}else{
				openWinViewClientConsulta("detailCierre");
		    } 
		}
	}
}

//funcion que obtiene el id de los clientes 
function onChangeClienteId(e)
{
	
    var grid = $("#dg_clientesIds").data("kendoGrid");    
    dataItemId= grid.dataItem(grid.tbody.find("tr.k-state-selected"));	
}

//funncion para quitar el ide los clienes seleccionados 
function quitarId()
{
	for(var i=0; i<window.parent.ids_add.length; i++)
    {
		if(window.parent.ids_add[i].id==dataItemId.id)
		{
			window.parent.ids_add.splice(i, 1);
		}
    }
	
	setDataIdsAdd(window.parent.ids_add);
	
}

//funcion para agregar ids de los clientes seleccionados para buscar 
function agregaId()
{
	var existe=false;
	for(var i=0; i<window.parent.ids_add.length; i++)
    {			
		if(window.parent.ids_add[i].id==dataItemCliente.id_cliente_z)
		{
			existe=true;
		}			
    }
	
	if(!existe)
	{			
		window.parent.ids_add.push({"id":dataItemCliente.id_cliente_z,"nom":dataItemCliente.nombre1C,"apPat":dataItemCliente.app_patC,"apMat":dataItemCliente.app_matC});		        
		setDataIdsAdd(window.parent.ids_add)
	}
	else
	{
		//kendoConsole.log("Id repetido","warning");
		$("#mensajes_main").html("Id repetido");
	}
}

//////////////Funcionalida parametros de seleccion
//fucnion que abre ventana principal de parametros de seleccion
function openSelecCierreVenta(opc)
{
	   ocultaBotoneraDiv();
	   
	   var windowSelecCierre=$("#windowSelecCierre").kendoWindow({
			actions: ["Close"],
			modal: true,
			resizable: false,
			content: 
				{
					url:contexPath+"/SeleccionCierreVenta.htm?from="+opc
			},
	        height: "510px",
	        title: "Parametros de Seleccion Cierre de Venta",
			width: "960px"
	    }).data("kendoWindow");	
	   
	   windowSelecCierre.center();
	   windowSelecCierre.open();	
}

function ocultaBotoneraDiv(){
	$("#divIdImpTalonario").css("display", "none");
	$("#divIdImpContrato").css("display", "none");
	$("#divIdImpPagare").css("display", "none");
	$("#divIdVerCliente2").css("display", "none");
	$("#divIdEdoCuenta2").css("display", "none");
	$("#divIdMonitorPagos2").css("display", "none");
	$("#divIdImpContrato").css("display", "none");
	$("#divIdVerCliente1").css("display", "none");
	$("#divIdEdoCuenta1").css("display", "none");
	$("#divIdMonitorPagos1").css("display", "none");
	
}

//funcion para cerrar params de seleccion principal 
function cancelarSelecCierre()
{
	window.parent.$("#windowSelecCierre").data("kendoWindow").close();
}
//funcion para abrir ventana de capotura de params de seleccion 
function openParamsCierre(grid)
{
	var windowparamCierre =$("#windowParamsCierre").kendoWindow({
		actions: ["Close"],
		modal: true,
		resizable: false,
		content: 
			{
				url:contexPath+"/ParamsCierreVenta.htm?queGrid="+grid
		},
        height: "206px",
     	width: "350px"
	    });	
	windowparamCierre = $("#windowParamsCierre").data("kendoWindow");
	windowparamCierre.center();
	windowparamCierre.open();
}
//funcion para recargar los datos capturados en params seleccion
function cargaGridCierre(grid)
{
	switch (grid) {
	case 'Cotizacion':
		if(window.parent.dataCotizacion.length>0)
		{
			var dataSourceCot= new kendo.data.DataSource({
		        batch: true,
		        data: window.parent.dataCotizacion,
		        schema: {
		            model: {
		              fields: {
				  		   cotizacion: {validation: { required: false ,min: 10} },
		               }
		            }
		        }
		     });	
		}else{
		var dataSourceCot= new kendo.data.DataSource({
	        batch: true,
	        schema: {
	            model: {
	              fields: {
			  		   cotizacion: {validation: { required: false ,min: 10} },
	               }
	            }
	        }
	     });
		}
		$("#gridCotizaciones").kendoGrid({
	        dataSource: dataSourceCot,
	        resizable: true,
	        
	        height:150,
	        toolbar: [{name:"create",text:"Agregar registro"}],
	        columns: [
				{ field: "cotizacion", title:"Cotizaciones" , width: "130px"},
				{ command: "destroy", title: " ", width: "80px" }],
				editable:{
					   update: true,
					   destroy:true, // does not remove the row when it is deleted, but marks it for deletion
			           confirmation: "¿Esta seguro de eliminar esta fila?"
				    }
	    });
		
		break;
	case 'Pedido':
		if(window.parent.dataPedido.length>0)
		{
			var dataSourcePed= new kendo.data.DataSource({
		        batch: true,
		        data: window.parent.dataPedido,
		        schema: {
		            model: {
		              fields: {
				  		   pedido: {validation: { required: false ,min: 10} },
		               }
		            }
		        }
		     });	
		}else{
			var dataSourcePed= new kendo.data.DataSource({
		        batch: true,
		        schema: {
		            model: {
		              fields: {
				  		   pedido: {validation: { required: false ,min: 10} },
		               }
		            }
		        }
		     });
			}
		$("#gridPedidos").kendoGrid({
	        dataSource: dataSourcePed,
	        resizable: true,
	        
	        height:150,
	        toolbar: [{name:"create",text:"Agregar registro"}],
	        columns: [
				{ field: "pedido", title:"Pedidos" , width: "130px"},
				{ command: "destroy", title: " ", width: "80px" }],
				editable:{
					   update: true,
					   destroy:true, // does not remove the row when it is deleted, but marks it for deletion
			           confirmation: "¿Esta seguro de eliminar esta fila?"
				    }
	    });
		
		break;
	case 'Nombre':
		if(window.parent.dataNombre.length>0)
		{
			var dataSourceNom= new kendo.data.DataSource({
		        batch: true,
		        data: window.parent.dataNombre,
		        schema: {
		            model: {
		              fields: {
				  		   nombre: {validation: { required: false ,min: 10} },
		               }
		            }
		        }
		     });	
		}else{
		var dataSourceNom= new kendo.data.DataSource({
	        batch: true,
	        schema: {
	            model: {
	              fields: {
			  		   nombre: {validation: { required: false ,min: 10} },
	               }
	            }
	        }
	     });
		}
		$("#gridNombres").kendoGrid({
	        dataSource: dataSourceNom,
	        resizable: true,
	        height:150,
	        toolbar: [{name:"create",text:"Agregar registro"}],
	        columns: [
				{ field: "nombre", title:"Nombres" , width: "130px"},
				{ command: "destroy", title: " ", width: "80px" }],
				editable:{
					   update: true,
					   destroy:true, // does not remove the row when it is deleted, but marks it for deletion
			           confirmation: "¿Esta seguro de eliminar esta fila?"
				    }
	    });
		
		break;
	case 'SegNombre':
	if(window.parent.dataNombre2.length>0)
		{
			var dataSourceNom2= new kendo.data.DataSource({
		        batch: true,
		        data: window.parent.dataNombre2,
		        schema: {
		            model: {
		              fields: {
				  		   nombre2: {validation: { required: false ,min: 10} },
		               }
		            }
		        }
		     });	
		}else{
		var dataSourceNom2= new kendo.data.DataSource({
	        batch: true,
	        schema: {
	            model: {
	              fields: {
			  		   nombre2: {validation: { required: false ,min: 10} },
	               }
	            }
	        }
	     });
		}
		$("#gridSegNombres").kendoGrid({
	        dataSource: dataSourceNom2,
	        resizable: true,
	        
	        height:150,
	        toolbar: [{name:"create",text:"Agregar registro"}],
	        columns: [
				{ field: "nombre2", title:"Segundos Nombres" , width: "130px"},
				{ command: "destroy", title: " ", width: "80px" }],
				editable:{
					   update: true,
					   destroy:true, // does not remove the row when it is deleted, but marks it for deletion
			           confirmation: "¿Esta seguro de eliminar esta fila?"
				    }
	    });
		break;
	case 'ApPaterno':
		if(window.parent.dataApPaterno.length>0)
		{
			var dataSourceApPat= new kendo.data.DataSource({
		        batch: true,
		        data: window.parent.dataApPaterno,
		        schema: {
		            model: {
		              fields: {
				  		   apPat: {validation: { required: false ,min: 10} },
		               }
		            }
		        }
		     });	
		}else{
		var dataSourceApPat= new kendo.data.DataSource({
	        batch: true,
	        schema: {
	            model: {
	              fields: {
			  		   apPat: {validation: { required: false ,min: 10} },
	               }
	            }
	        }
	     });
		}
		$("#gridApPaternos").kendoGrid({
	        dataSource: dataSourceApPat,
	        resizable: true,
	        
	        heightel:150,
	        toolbar: [{name:"create",text:"Agregar registro"}],
	        columns: [
				{ field: "apPat", title:"Apellidos Paternos" , width: "130px"},
				{ command: "destroy", title: " ", width: "80px" }],
				editable:{
					   update: true,
					   destroy:true, // does not remove the row when it is deleted, but marks it for deletion
			           confirmation: "¿Esta seguro de eliminar esta fila?"
				    }
	    });
		break;
	case 'ApMaterno':
		if(window.parent.dataApMaterno.length>0)
		{
			var dataSourceApMat= new kendo.data.DataSource({
		        batch: true,
		        data: window.parent.dataApMaterno,
		        schema: {
		            model: {
		              fields: {
				  		   apMat: {validation: { required: false ,min: 10} },
		               }
		            }
		        }
		     });	
		}else{
		var dataSourceApMat= new kendo.data.DataSource({
	        batch: true,
	        schema: {
	            model: {
	              fields: {
			  		   apMat: {validation: { required: false ,min: 10} },
	               }
	            }
	        }
	     });
		}
		$("#gridApMaternos").kendoGrid({
	        dataSource: dataSourceApMat,
	        resizable: true,
	        
	        height:150,
	        toolbar: [{name:"create",text:"Agregar registro"}],
	        columns: [
				{ field: "apMat", title:"Apellidos Maternos" , width: "130px"},
				{ command: "destroy", title: " ", width: "80px" }],
				editable:{
					   update: true,
					   destroy:true, // does not remove the row when it is deleted, but marks it for deletion
			           confirmation: "¿Esta seguro de eliminar esta fila?"
				    }
	    });
		break;	
	case 'Equipo':
			if(window.parent.dataEquipo.length>0)
			{
				var dataSourceEqu= new kendo.data.DataSource({
			        batch: true,
			        data: window.parent.dataEquipo,
			        schema: {
			            model: {
			              fields: {
					  		   equipo: {validation: { required: false ,min: 10} },
			               }
			            }
			        }
			     });	
			}else{
			var dataSourceEqu= new kendo.data.DataSource({
		        batch: true,
		        schema: {
		            model: {
		              fields: {
				  		   equipo: {validation: { required: false ,min: 10} },
		               }
		            }
		        }
		     });
			}
			$("#gridEquipos").kendoGrid({
		        dataSource: dataSourceEqu,
		        resizable: true,
		        
		        height:150,
		        toolbar: [{name:"create",text:"Agregar registro"}],
		        columns: [
					{ field: "equipo", title:"Equipos" , width: "130px"},
					{ command: "destroy", title: " ", width: "80px" }],
					editable:{
						   update: true,
						   destroy:true, // does not remove the row when it is deleted, but marks it for deletion
				           confirmation: "¿Esta seguro de eliminar esta fila?"
					    }
		    });
			break;
	default:
		break;
	}		
}

//funcion que crea arreglo con todo lo capturado en params selccion
function aceptarParamsCierre(grid)
{
switch(grid){
case 'Cotizacion':
	
	var gridCot =$("#gridCotizaciones").data("kendoGrid");
	window.parent.dataCotizacion = gridCot.dataSource.data();
	if(window.parent.dataCotizacion.length>0)
	{
	   window.parent.$("#cotizacion").val(window.parent.dataCotizacion[0].cotizacion);
	}	
	window.parent.$("#windowParamsCierre").data("kendoWindow").close();
	break;
case 'Pedido':
	
	var gridPed=$("#gridPedidos").data("kendoGrid");
	window.parent.dataPedido = gridPed.dataSource.data();
	if(window.parent.dataPedido.length>0)
	{
	   window.parent.$("#pedido").val(window.parent.dataPedido[0].pedido);
	}	
	window.parent.$("#windowParamsCierre").data("kendoWindow").close();
	break;
case 'Nombre':
	
	var gridNom =$("#gridNombres").data("kendoGrid");
	window.parent.dataNombre = gridNom.dataSource.data();
	if(window.parent.dataNombre.length>0)
	{
	   window.parent.$("#nombre").val(window.parent.dataNombre[0].nombre);
	}	
	window.parent.$("#windowParamsCierre").data("kendoWindow").close();
	break;
case 'SegNombre':
	var gridNom2 = $("#gridSegNombres").data("kendoGrid");
	window.parent.dataNombre2 = gridNom2.dataSource.data();
	if(window.parent.dataNombre2.length>0)
	{
	   window.parent.$("#segNombre").val(window.parent.dataNombre2[0].nombre2);
	}
	window.parent.$("#windowParamsCierre").data("kendoWindow").close();
	break;
case 'ApPaterno':
	var gridApPat = $("#gridApPaternos").data("kendoGrid");
	window.parent.dataApPaterno = gridApPat.dataSource.data();
	if(window.parent.dataApPaterno.length>0)
	{
	   window.parent.$("#apPaterno").val(window.parent.dataApPaterno[0].apPat);
	}
	window.parent.$("#windowParamsCierre").data("kendoWindow").close();
	break;
case 'ApMaterno':
	var gridApMat = $("#gridApMaternos").data("kendoGrid");
	window.parent.dataApMaterno = gridApMat.dataSource.data();
	if(window.parent.dataApMaterno.length>0)
	{
	   window.parent.$("#apMaterno").val(window.parent.dataApMaterno[0].apMat);
	}
	window.parent.$("#windowParamsCierre").data("kendoWindow").close();
	break;
case 'Equipo':
	
	var gridEqu =$("#gridEquipos").data("kendoGrid");
	window.parent.dataEquipo = gridEqu.dataSource.data();
	if(window.parent.dataEquipo.length>0)
	{
	   window.parent.$("#equipo").val(window.parent.dataEquipo[0].equipo);
	}	
	window.parent.$("#windowParamsCierre").data("kendoWindow").close();
	break;
default:
	break;
}
	
}
//funcion que cierra ventana de params seleccion 
function cancelarParamsCierre()
{
	window.parent.$("#windowParamsCierre").data("kendoWindow").close();
}
//fucnion para buscar cotizaciones o pedidos 
function BuscarClientes()
{
	var cotPed="";
	
  //onVentanaWait();
	var gridClientes =$("#dg_clientes").data("kendoGrid");
	var ClientesInfo = gridClientes.dataSource.data();

	if(arregloIds.length!=0)
	{
		BuscarCotPed($("#screenFrom").val());
	}else{
		if($("#screenFrom").val()=="pagoIni" || $("#screenFrom").val()=="cotizador")
		{
			cotPed=0;
		}else if($("#screenFrom").val()=="pagoParc" || $("#screenFrom").val()=="cancelacion" ||  $("#screenFrom").val()=="monitorPagoPrin" )
		{
			cotPed=1;
		}	
	if(($('#cotizacion').val()=="" || $('#pedido').val()=="") && $('#equipo').val()=="" && $('#fase').val()==""  && $('#nombre').val()=="" && $('#nombre2').val()=="" && $('#apPaterno').val()=="" && $('#apMaterno').val()=="")
	{
		//kendoConsole.log("Capture Criterios","warning");
		$("#mensajes_main").html("Capture Criterios");
	}else if($.trim($('#equipo').val())!="" &&  $.trim($('#fase').val())=="")
	{
		//offVentanaWait();
		//kendoConsole.log("Fase obligatoria","warning")
		$("#mensajes_main").html("Fase obligatoria");
	}else{
		
		if($.trim($('#cotizacion').val())!="")
		{
			if(dataCotizacion.length>0)
			{
				xmlCot=extraccionArray(dataCotizacion,'cotizacion')
				
			}else{
				xmlCot="<criterios><criterio><cotizacion>"+$.trim($('#cotizacion').val())+"</cotizacion></criterio></criterios>"
			}
		}else{xmlCot="";}
		
		if($.trim($('#pedido').val())!="")
		{
			if(dataPedido.length>0)
			{
				xmlPed=extraccionArray(dataPedido,'pedido')
				
			}else{
				xmlPed="<criterios><criterio><pedido>"+$.trim($('#pedido').val())+"</pedido></criterio></criterios>"
			}
		}else{xmlPed="";}
		
		if($.trim($('#equipo').val())!="")
		{
			if(dataEquipo.length>0)
			{
				xmlEq=extraccionArray(dataEquipo,'equipo')
				
			}else{
				xmlEq="<criterios><criterio><equipo>"+$.trim($('#equipo').val())+"</equipo></criterio></criterios>"
			}
		}else{xmlEq="";}
			
		if($("#nombre").val()!="" && this.dataNombre.length==0)
		{
			xmlNom ="<criterios><criterio><nombre>"+$("#nombre").val()+"</nombre></criterio></criterios>";
		}else if(this.dataNombre.length>0){
		var criteriosNom=[];
		for(var i=0; i<this.dataNombre.length; i++)
		{	
			var params={};
			if(this.dataNombre[i].nombre!="" ){
				params["nombre"]=this.dataNombre[i].nombre;
				criteriosNom.push( params);	
			}
		}
		xmlNom = createXMLCriterios("criterios",criteriosNom);
	    }
		
		if($("#nombre2").val()!="" && this.dataNombre2.length==0)
		{
			xmlNom2 ="<criterios><criterio><nombre2>"+$("#nombre2").val()+"</nombre2></criterio></criterios>";
		}else if(this.dataNombre2.length>0){
		var criteriosNom2=[];
		for(var i=0; i<this.dataNombre2.length; i++)
		{
			var params2={};
			if(this.dataNombre2[i].nombre2!="" ){
				params2["nombre2"]=this.dataNombre2[i].nombre2;
				criteriosNom2.push(params2);	
			}
		}
		xmlNom2 = createXMLCriterios("criterios",criteriosNom2);
	}
		
		if($("#apPaterno").val()!="" && this.dataApPaterno.length==0)
		{
			xmlApPat ="<criterios><criterio><apPat>"+$("#apPaterno").val()+"</apPat></criterio></criterios>";
		}else if(this.dataApPaterno.length>0){
		var criteriosApPat=[];
		for(var i=0; i<this.dataApPaterno.length; i++)
		{
			var params3={};
			if(this.dataApPaterno[i].apPat!="" ){
				params3["apPat"]=this.dataApPaterno[i].apPat;
				criteriosApPat.push(params3);	
			}
		}
		xmlApPat = createXMLCriterios("criterios",criteriosApPat);
	}
		
		if($("#apMaterno").val()!="" && this.dataApMaterno.length==0)
		{
			xmlApMat ="<criterios><criterio><apMat>"+$("#apMaterno").val()+"</apMat></criterio></criterios>";
		}else if(this.dataApMaterno.length>0){
			var criteriosApMat=[];
			for(var i=0; i<this.dataApMaterno.length; i++)
			{
				var params4={};
				if(this.dataApMaterno[i].apMat!="" ){
					params4["apMat"]=this.dataApMaterno[i].apMat;
					criteriosApMat.push(params4);	
				}
			}
			xmlApMat = createXMLCriterios("criterios",criteriosApMat);
		}
		$("#mensajes_main").html("Buscando...");
		var datos = "accion=1&cotPed="+cotPed+"&fase="+$("#fase").val()+"&xmlCot="+xmlCot+"&xmlPed="+xmlPed+"&xmlEq="+xmlEq+"&xmlNoms=" + xmlNom+"&xmlNoms2="+xmlNom2+"&xmlApPats="+xmlApPat+"&xmlApMats="+xmlApMat;
		$.ajax( {
			type : "POST",
			url : contexPath + "/ParamsCierreVenta.htm",
			data : datos,

			success : function(response) {
				// we have the response
			if (response.responseCotizacion.mensaje == "SUCCESS") {
				document.getElementById("resultsClientes").setAttribute("style","visibility:visible")
				var findDataExport = response.responseCotizacion.objCotInfoList;
				//offVentanaWait();
				$("#mensajes_main").html("Registros encontrados");
			dataFind = [];
			
			var cotizaciones=false;

			for (i = 0; i < findDataExport.length; i++) {

			if(findDataExport[i].idCotizacion!="")

			{

			cotizaciones=true;

			}
			dataFind.push(findDataExport[i]);

			}
			if($("#screenFrom").val()=="pagoIni")
			{
			   var temp_aux="<input type=\"checkbox\" id=\"checkCliente\" name=\"checkCliente\" value=\"${ idCliente }\" onclick=\"cambioCheck(this, '${ idCliente }')\" />";
			}else if($("#screenFrom").val()=="pagoParc")
			{
			   var temp_aux="<input type=\"checkbox\" id=\"checkCliente\" name=\"checkCliente\" value=\"${ idCarCliente }\" onclick=\"cambioCheck(this, '${ idCarCliente }')\" />"
			}else if($("#screenFrom").val()=="cotizador")
			{
			   var temp_aux="<input type=\"checkbox\" id=\"checkCliente\" name=\"checkCliente\" value=\"${ idCarCliente }\" onclick=\"cambioCheck(this, '${ idCarCliente }')\" />"
			}else if($("#screenFrom").val()=="cancelacion")
			{
			   var temp_aux="<input type=\"checkbox\" id=\"checkCliente\" name=\"checkCliente\" value=\"${ idCarCliente }\" onclick=\"cambioCheck(this, '${ idCarCliente }')\" />"
			}else if($("#screenFrom").val()=="monitorPagoPrin")
			{
			   var temp_aux="<input type=\"checkbox\" id=\"checkCliente\" name=\"checkCliente\" value=\"${ idCarCliente }\" onclick=\"cambioCheck(this, '${ idCarCliente }')\" />"
			}
			
			var dataSourceFindClie = new kendo.data.DataSource( {
				data : dataFind,
				pageSize : 50
			});
			
			
				$("#dg_clientes").empty();
				$("#dg_clientes").kendoGrid( {
					 change: onChangeCtes,
					 dataSource : dataSourceFindClie,
					 selectable:"row",
					 resizable: true,							
					 sortable: true,
					 reorderable: true,
					 height:250,
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
					 columns : [ 
					{
						  title: "", 
						   template: temp_aux,  
						   width:30
					},
	                 {
						field : "idCliente",
						title : "Id Cliente"
						
					 },
					{
						field : "nombre",
						title : "Nombre"
					
					}, {
						field : "snombre",
						title : "Segundo Nombre"
						
					}, {
						field : "aapat",
						title : "Apellido"
						
					}, {
						field : "amat",
						title : "Segundo Apellido"
						
					}, 
					{
						field : "dptoCasa",
						title : "Dpto"
						
					},
					{
						field : "fchNac",
						title : "F Nacimiento"
						
					}
					,{
						field : "mail",
						title : "Correo"
						
					}]
					}).data("kendoGrid");
				
					
			} else if (response.responseCotizacion.mensaje == "LOGOUT") { 
				window.parent.salirSistema();
			} else {
				$("#dg_clientes").empty();
				reseteaClientesPediCot();
				xmlCot="";
				xmlCli="";
				xmlEq="";
				xmlNom="";xmlNom2="";xmlApPat="";xmlApMat="";
				//kendoConsole.log("Hubo un error en carga clientes: "+response.responseCotizacion.descripcion ,"error");
				//$("#mensajes_main").html("Hubo un error en carga clientes: "+response.responseCotizacion.descripcion);
				$("#mensajes_main").html(response.responseCotizacion.descripcion);
			}
		},
		error : function(e) {
			//offVentanaWait();
			$("#mensajes_main").html("Error:"+e);
		  }
		});
		
	}//fin else 
  }//fin else buscar clientes
}

function reseteaClientesPediCot(){
	  var temp_aux="<input type=\"checkbox\" id=\"checkCliente\" />";
	
	  var dataSourceFindClie = new kendo.data.DataSource( {
		   data : [ ],
		   pageSize : 50
	   });
	
	
		$("#dg_clientes").empty();
		$("#dg_clientes").kendoGrid( {
			 dataSource : dataSourceFindClie,
			 change: onChangeCtes,
			 selectable:"row",
			 resizable: true,							
			 sortable: true,
			 reorderable: true,
			 height:250,
			 width:250,
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
			 columns : [ 
			{
				  title: "", 
				   template: temp_aux,  
				   width:30
			},
            {
				field : "idCliente",
				title : "Id Cliente"
				
			 },
			{
				field : "nombre",
				title : "Nombre"
			
			}, {
				field : "snombre",
				title : "Segundo Nombre"
				
			}, {
				field : "aapat",
				title : "Apellido"
				
			}, {
				field : "amat",
				title : "Segundo Apellido"
				
			}, 
			{
				field : "dptoCasa",
				title : "Dpto"
				
			},
			{
				field : "fchNac",
				title : "F Nacimiento"
				
			}
			,{
				field : "mail",
				title : "Correo"
				
			}]
			}).data("kendoGrid");

}

//funcion que crea arreglo con los check de los clientes seleccionados 
function cambioCheck(obj,id) {
	var checkArr = document.getElementsByName("checkCliente");
	
	if(obj.checked) {
		for (i = 0 ; i < checkArr.length ; i++) {
			//alert ("id:" + id + ", obj.value:" + checkArr[i].value);
			checkArr[i].checked = id==checkArr[i].value;
		}
		arregloIds.push({"id":obj.value});
	} else {
		for(j=0;j<arregloIds.length;j++) {
			if(arregloIds[j].id==obj.value) {
				arregloIds.splice(j,1);	
			}
		}
	}
}

//fuincion para abrir el cotizador 
function openCotizadorFromPagoInicial(from)
{
	var nom_cte= dataItemCotizacion.nombre+" "+dataItemCotizacion.snombre+" "+dataItemCotizacion.aapat+" "+dataItemCotizacion.amat;

	var windowCotizador=window.parent.$("#winCotizador").kendoWindow({
		actions: ["Close"],
		modal: true,
		resizable: true,
		content: 
			{
				url: ""+contexPath+"/CotizadorAdmin.htm?idCarCliente="+dataItemCotizacion.idCarCliente+"&idCliente="+dataItemCotizacion.idCliente+"&idCotizacion="+dataItemCotizacion.idCotizacion+"&idPedido="+dataItemCotizacion.idPedido+"&idequ_get="+dataItemCotizacion.dpto+"&from="+from+"&nombreCte="+nom_cte+"&datos_equipos="+sub_equipos_select_xml+"&idCotizacionZ="+dataItemCotizacion.idCotizacionZ+"&equipo_select="+equ_ct+""
		},
        height: "710px",
        title: "Cotizador",
		width: "730px"
	    }).data("kendoWindow");		
	windowCotizador.center();
	windowCotizador.open();
}


//Funciones para agregar pagos 
function agregaPago()
{
	//alert('entra a agregaPago() ...');
	/*$("#btnModificar").attr('disabled', 'true');
	$("#btnAgregar").attr('disabled', 'true');
	$("#btnBorrar").attr('disabled', 'true');*/
	var windowDetPago=$("#winDetPago").kendoWindow({
		actions: ["Close"],
		modal: true,
		resizable: false,
		content: 
			{
				url:contexPath+"/DatosPago.htm?from="+$("#fromR").val()
		},
        height: "340px",
        title: "Agregar pago",
		width: "490px"
	    }).data("kendoWindow");		
	windowDetPago.center();
	windowDetPago.open();
}

//Funciones para agregar pagos 
function agregaPago(conceptoReferencia)
{
	//alert('agregaPago('+conceptoReferencia+')');
	var windowDetPago=$("#winDetPago").kendoWindow({
		actions: ["Close"],
		modal: true,
		resizable: false,
		content: 
			{
				url:contexPath+"/DatosPago.htm?from="+$("#fromR").val() + "&conceptoReferencia=" + conceptoReferencia
		},
        height: "340px",
        title: "Agregar pago",
		width: "490px"
	    }).data("kendoWindow");		
	windowDetPago.center();
	windowDetPago.open();
}

//funcion que elimina un pago ala hora de modificarlo , pone bandera "D"
function eliminaPago()
{
	var gridAdd = $("#gridPagos").data("kendoGrid");    
    dataItemPagoAddNew = gridAdd.dataItem(gridAdd.tbody.find("tr.k-state-selected"));
    selectedRow = gridAdd.select();
    indexAdd = selectedRow.index();
	if(dataItemPagoAddNew==null)
	{
		$("#mensajes_main").html("Seleccione un pago a eliminar");
	}
	else {
		$("#winConfirmaEliminaPago").data("kendoWindow").close();
		
		//alert(dataItemPagoAddNew.hpago);
		$.ajax( {
			type : "POST",
			url : contexPath + "/EliminaPagoMensualTmp.htm",
			data : "folioOper=" + dataItemPagoAddNew.folioOper + "&fchPago=" + dataItemPagoAddNew.fpago + "&hrPago=" + dataItemPagoAddNew.hpago + "&monto=" + dataItemPagoAddNew.monto + "&refer=" + dataItemPagoAddNew.refer,
			success : function(response) {
	    	
					  },
			error : function(e) {
					//kendoConsole.log(e,"error");
					$("#mensajes_main").html("Error:"+e);
					}
		});	

		pagos_add.splice(indexAdd,1);
		setDataPagosNew(pagos_add);
		gridAdd.removeRow(selectedRow);
		gridAdd.saveChanges();
		gridAdd.refresh();
		
	}
	
}

function validaEliminarPago()
{
	var gridAdd = $("#gridPagos").data("kendoGrid");    
    dataItemPagoAddNew = gridAdd.dataItem(gridAdd.tbody.find("tr.k-state-selected"));
    selectedRow = gridAdd.select();
    indexAdd = selectedRow.index();
	if(dataItemPagoAddNew==null)
	{
		$("#mensajes_main").html("Seleccione un pago a eliminar");
	}else{
	    $("#winConfirmaEliminaPago").data("kendoWindow").open();
	    $("#winConfirmaEliminaPago").data("kendoWindow").center();
	}
}

function closeBorraPagoWindow()
{
	$("#winConfirmaEliminaPago").data("kendoWindow").close();
}

//funcion que cierra ventana de agregar 
function cancelarAgregar()
{
	window.parent.$("#winDetPago").data("kendoWindow").close();
}

function onChangeCtes(evt)
{
	cotizador_open=true;
	var dg_cte = $("#dg_clientes").data("kendoGrid");
	var selectedRowCtes = dg_cte.dataItem(dg_cte.tbody.find("tr.k-state-selected"));	
	dataItemCotizacion=selectedRowCtes;
	
}


///funcion que abre la ventana de detalle delos pagos desde monitor
function openDetallePagoFromMonitor(from)
{      
	var dg_PagosHead = $("#dg_PagosRegistradosMonitor").data("kendoGrid");
	var selectedRowPagoHead = dg_PagosHead.dataItem(dg_PagosHead.tbody.find("tr.k-state-selected"));
	
	if(selectedRowPagoHead==null || selectedRowPagoHead==undefined || selectedRowPagoHead=='')
	{
		 //kendoConsole.log("Seleccione un Registro","warning");
		$("#mensajes_main").html("Seleccione un Registro");
	}
	else
	{
		 var windowDetPago=$("#windowDetallePagoMonitor").kendoWindow({
				actions: ["Close"],
				modal: true,
				resizable: false,
				content: 
					{
						url:contexPath+"/DetallePago.htm?regi="+selectedRowPagoHead.fregi+"&from="+from+"&fromMonitorPrinci=monitor"   
				},
		        height: "380px",
		        title: "Cliente "+selectedRowPagoHead.kunnr +"-"+selectedRowPagoHead.kunnrtx,
				width: "955px"
			    }).data("kendoWindow");		
		 windowDetPago.center();
		 windowDetPago.open();
	}
	

}

///funcion que abre la ventana de detalle delos pagos desde monitor
function openModificaDetallePagoFromMonitor(from)
{      
	var paramURIx=$("#fromMonitorXmlClienteUri").val();
	var dg_PagosHead = $("#dg_PagosRegistradosMonitor").data("kendoGrid");
	
	var selectedRowPagoHead = dg_PagosHead.dataItem(dg_PagosHead.tbody.find("tr.k-state-selected"));
	
	if(selectedRowPagoHead==null || selectedRowPagoHead==undefined || selectedRowPagoHead=='')
	{
		 //kendoConsole.log("Seleccione un Registro","warning");
		$("#mensajes_main").html("Seleccione un Registro");
	}
	else
	{
		 var numPag = parseInt(dataItemPagoMonitorHead.numPagos);
    	
		 var windowDetPago=$("#windowModificaDetallePagoMonitor").kendoWindow({
				actions: ["Close"],
				modal: true,
				resizable: false,
				content: 
					{
						url:contexPath+"/ModificaDetallePago.htm?regi="+selectedRowPagoHead.fregi+"&from="+from+"&fromMonitorPrinci=monitor&uriActualizaGrid="+paramURIx + "&numPagos="+numPag  
				},
		        height: "650px",
		        title: "Cliente "+selectedRowPagoHead.kunnr +"-"+selectedRowPagoHead.kunnrtx,
				width: "970px"
			    }).data("kendoWindow");		
		 windowDetPago.center();
		 windowDetPago.open();
	}
	

}

//Carga el detalle pago
function cargaDatosDetallePagosFromMonitor(paramFregi)
{
//alert('entra a cargaDatosDetallePagosFromMonitor() ...');
   var xmlRegistro="<criterios><criterio><id>"+paramFregi+"</id></criterio></criterios>"; 
// window.parent.$("#dg_Detalles").empty();
		
	var datosReg = "accion=2&xmlReg="+xmlRegistro;
	$.ajax( {
		type : "POST",
		url : contexPath + "/MonitorPagos.htm",
		data : datosReg,
		success : function(response) {
    	// we have the response
		if (response.responsePagosReg.mensaje == "SUCCESS") {
				
				var findDataExportPagos = response.responsePagosReg.objPagoHdrList;
				dataDetPagos = [];
 			
				for (i = 0; i < findDataExportPagos[0].pagosDetList.length; i++) {
					dataDetPagos.push(findDataExportPagos[0].pagosDetList[i]);
				}
				$("#mensajes_main").html("No. Registros desde el monitor:"+findDataExportPagos[0].pagosDetList.length)
				setDataAddPagosMonitorPrin(dataDetPagos);
				
		} else {
			//kendoConsole.log("Hubo un error: "+response.responsePagosReg.descripcion,"error");
			$("#mensajes_main").html("Hubo un error: "+response.responsePagosReg.descripcion);
	    }
	},
	error : function(e) {
		//kendoConsole.log(e,"error");
		$("#mensajes_main").html("Error:"+e);
		
	}
	});
}

function setDataAddPagosMonitorPrin(listaPagos)
{
	//alert('entra a setDataAddPagosMonitorPrin() ...');
	var dataSourceListaPag = new kendo.data.DataSource({
        data: listaPagos,	    	    	        
        pageSize: 50,
        messages: {
            empty: "No hay registros que mostrar"
        }
    });
	
	$("#dg_Detalles").empty();
	$("#dg_Detalles").kendoGrid( {
		//dataSource : listaPagos,
		dataSource:{
		 		data:listaPagos,
   			pageSize:50
	 	},
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
        change: onChangePagoFromMonitor ,
        columns : [ 
        {
			field : "conse",
			title : "Consecutivo",
			 width:100
		 }, 
		 {
				field : "fpago",
				title : "Fecha de Pago",
				editable: true,
				width:100
		},
		 /*{	
			field : "hpago",
			title : "Hora de Pago",
			editable: true,
			width:150
				
		},*/
		{
			field : "conceptoTxt",
			title : "Concepto",
			editable: true,
			width:100
		},
		{
			field : "medioPagoTxt",
			title : "Medio de Pago",
			editable: true,
			width:100
		},
		{
			field : "monto",
			format: "{0:c}",
			title : "Monto",
			editable: true,
			width:100
		},
		{
			field : "refer",
			title : "Referencia",
			editable: true,
			width:100
		},
		{
			field : "folioOper",
			title : "Folio Operacion",
			editable: true,
			width:100
		},
		{
			field : "observaciones",
			title : "Observaciones",
			width:300
		},
		{
			field : "validDesc",
			title : "Validado",
			width:100
		},
		{
			field : "fregi",
			title : "Folio Validacion",
			width:100
		}, 
		{
			field : "ernam_val",
			title : "Validador",
			width:100
		}, {
			field : "aedat_val",
			title : "Fecha Validación",
			width:100
		}, {
			field : "cputm_val",
			title : "Hora Validación",
			width:100
		}]
		}).data("kendoGrid");

	$('#fchPago').val(null);
	$('#hrPago').val(null);
	$('#monto').val(null);
	$('#refer').val(null);
	$('#archivo').val(null);
	$('#folioOper').val(null);
	 
}


//funcion para cambiar el dataItem seleccionado del dataGrid de pagos agregados
function onChangePagoFromMonitor(e) {
	var grid = $("#dg_Detalles").data("kendoGrid");    
    dataItemPagoAddMonitor = grid.dataItem(grid.tbody.find("tr.k-state-selected"));
    selectedRow = grid.select();
    index = selectedRow.index();
    
	//alert('entra a onChangePagoFromMonitor() con dataItemPagoAddMonitor.validDesc = ' + dataItemPagoAddMonitor.validDesc + '...');
    
    if(dataItemPagoAddMonitor.validDesc=="No"){
    	var datePicker = $("#fchPago").data("kendoDatePicker");
    	//alert("datePicker ... " + datePicker + "   dataItemPagoAddMonitor.fpago ... " + dataItemPagoAddMonitor.fpago);
    	if (datePicker != undefined) {
    		//alert('fecha != undefined');
    		datePicker.value(dataItemPagoAddMonitor.fpago);
    	}
    	/*if (dataItemPagoAddMonitor.hpago!==''){
    		var tiempo = dataItemPagoAddMonitor.hpago;
    		var hora=tiempo.substring(0, 3);
    		var minuto=tiempo.substring(3, 5);
    		var horaMinuto=hora + minuto;
    		$('#hrPago').val(horaMinuto);	
    	}*/
    	
    	$('#monto').val(dataItemPagoAddMonitor.monto);
    	$('#refer').val(dataItemPagoAddMonitor.refer);
    	$('#archivo').val(dataItemPagoAddMonitor.file_a);
    	$('#folioOper').val(dataItemPagoAddMonitor.folioOper);
    	$('#NumPagos').val(dataItemPagoAddMonitor.conse);
    	//alert('F_PAGO ::: ' + dataItemPagoAddMonitor.fpago + ' ___CONCEPTO1 ::: ' + dataItemPagoAddMonitor.concepto + ' ___MED_PAGO1 ::: ' + dataItemPagoAddMonitor.medioPago + ' ___OBSERV ::: ' + dataItemPagoAddMonitor.observaciones);
    	/*var ddlConceptos = $("#ddlConceptos").data("kendoDropDownList");
    	ddlConceptos.value(dataItemPagoAddMonitor.concepto);*/
    	$("#ddlConceptos").data("kendoDropDownList").value(dataItemPagoAddMonitor.concepto);
    	/*var ddlMedios = $("#ddlMedios").data("kendoDropDownList");
    	ddlMedios.value(dataItemPagoAddMonitor.medioPago);*/
    	$("#ddlMedios").data("kendoDropDownList").value(dataItemPagoAddMonitor.medioPago);
    	$('#observaciones').val(dataItemPagoAddMonitor.observaciones);
    }
    else if(dataItemPagoAddMonitor.validDesc=="Si"){
    	//kendoConsole.log("Pago conciliado, no se puede modificar","warning");
    	$("#mensajes_main").html("Pago conciliado, no se puede modificar");
    }
}



//funcion que elimina pago del grid y le pone bandera de "D" 
function eliminaPagoMonitor()
{
	if(dataItemPagoAddMonitor==null)
		{
			//kendoConsole.log("Seleccione un pago a eliminar","warning");
			$("#mensajes_main").html("Seleccione un pago a eliminar");
		
	}else if(dataItemPagoAddMonitor.validDesc!="No")
	{
		//kendoConsole.log("No se puede eliminar pagos conciliados")
		$("#mensajes_main").html("No se puede eliminar pagos conciliados");
	}else{
		if(dataDetPagos[index].conse!="")
		{
		dataDetPagos[index].update="D";
		pagosEliminar.push(dataDetPagos[index]);
		dataDetPagos.splice(index,1);
		}else
		{
		dataDetPagos.splice(index,1);
		}
	}
		
	setDataAdd(dataDetPagos);
		
	
	var gridPagMoni = $("#dg_Detalles").data("kendoGrid");    
	dataItemPagoAddMonitor = gridPagMoni.dataItem(gridPagMoni.tbody.find("tr.k-state-selected"));
	selectedRow = gridPagMoni.select();
	indexAddPagMo = selectedRow.index();
	if(dataItemPagoAddMonitor==null)
	{
		$("#mensajes_main").html("Seleccione un pago a eliminar");
	}
	else {
		//$("#winConfirmaEliminaPago").data("kendoWindow").close();
		pagos_add.splice(indexAddPagMo,1);
		setDataPagosNew(pagos_add);
		dataItemPagoAddMonitor.removeRow(selectedRow);
		dataItemPagoAddMonitor.saveChanges();
		dataItemPagoAddMonitor.refresh();
		
	}
		
}


function buscaPedidoDeMonitoACierreVenta(){
    var dg_PagosHead = $("#dg_PagosRegistradosMonitor").data("kendoGrid");
	
	var selectedRowPagoHead = dg_PagosHead.dataItem(dg_PagosHead.tbody.find("tr.k-state-selected"));
	
	if(selectedRowPagoHead==null || selectedRowPagoHead==undefined || selectedRowPagoHead=='')
	{
		 //kendoConsole.log("Seleccione un Registro","warning");
		$("#mensajes_main").html("Seleccione un Registro");
		return;
	}
	
	window.location= contexPath+'/CierreVenta.htm?cargaDatosDesdeMonitor=datosMonitor&idCarClienteMonitor=' + $("#fromMonitorXmlClienteUri").val() + "&from=pagoParc";
		
}


function cargaDatosVienenDeMonitorAlCierre(paramIdcliente){
	 var actualizaURIXML= "<criterio><criterio><clientes>"+ paramIdcliente +"</clientes></criterio></criterio>";
	 
	 var urldatos="accion=2&cotPed=1&xmlCli="+actualizaURIXML;

	buscaCotizacionPedidoCierre(urldatos,'DESDEMONITOR');
}



function buscaPedidoDeCierreVentaAMonitor(paramOrigenRegistro, paramTipoRegistroPago, paramFregi, idPedidox,idClientex){
    
    var idClienteParaMonitor='';
    var idPedidoCliente='';
    
	
	
	if (paramOrigenRegistro=='REGISTRO_PAGO'){
		idPedidoCliente = idPedidox;
		idClienteParaMonitor = idClientex;
	} else {
		var dg_PagosHead = $("#dg_FindCotsPeds").data("kendoGrid");
		var selectedRowPagoHead = dg_PagosHead.dataItem(dg_PagosHead.tbody.find("tr.k-state-selected"));
		if(selectedRowPagoHead==null || selectedRowPagoHead==undefined || selectedRowPagoHead=='')
		{
			 //kendoConsole.log("Seleccione un Registro","warning");
			$("#mensajes_main").html("Seleccione un Registro");
			return;
		}
		idClienteParaMonitor=$("#fromMonitorXmlClienteUri").val();
	}
	
	
	//buscaPedidosEnMonitor
	window.location= contexPath+'/MonitorPagosPrincipal.htm?cargaDatosDesdeCierre=datosCierre&idPedidoDesdeCierre=' +
		idPedidoCliente + "&from=monitorPagoPrin&idClienteParaMonitor="+idClienteParaMonitor 
		+ "&paramOrigenRegistro="+paramOrigenRegistro + "&paramTipoRegistroPago="+paramTipoRegistroPago + "&paramFregi="+paramFregi;
		
}


function cargaDatosVienenDelCierreAlMonitor(paramIdPedido,paramIdClienteParaMoni,paramOrigenRegistro, paramTipoRegistroPago,paramFregi){
	// var actualizaURIXML= "<criterio><criterio><clientes>"+ paramIdcliente +"</clientes></criterio></criterio>";
	 
	 //var urldatos="accion=2&cotPed=1&xmlCli="+actualizaURIXML;
	var datosMonitor ="accion=1&xmlPed=<criterios><criterio><id>"+paramIdPedido+"</id></criterio></criterios>";
   
	buscaPedidosEnMonitor(datosMonitor,'NO',"TRAEDATOS",paramIdClienteParaMoni,paramOrigenRegistro, paramTipoRegistroPago,paramFregi);
}


function ocultaBotonesRegP(){
	//$("#indicaImpresionContratoPagare").val('');
	//$("#divIdImpContratoPagare").css("display", "none");
	//$("#divIdVerCliente2").css("display", "none");
	//$("#divIdEdoCuenta2").css("display", "none");
	//$("#divIdVerCliente1").css("display", "none");
	//$("#divIdEdoCuenta1").css("display", "none");
	//$("#divIdImpTalonario").css("display", "none");
}

function imprimirDocLegal(){
	var gridPagosMonitorHead = $("#dg_PagosRegistradosMonitor").data("kendoGrid");    
    dataItemPagoMonitorHead = gridPagosMonitorHead.dataItem(gridPagosMonitorHead.tbody.find("tr.k-state-selected"));
    if (dataItemPagoMonitorHead==null || dataItemPagoMonitorHead==undefined || dataItemPagoMonitorHead==''){
    	$("#mensajes_main").html("Seleccione un pedido valido");
    	return;
    }
	if (dataItemPagoMonitorHead.stContra=='' || dataItemPagoMonitorHead.stContra==undefined || dataItemPagoMonitorHead.stContra==null){
		$("#mensajes_main").html("El status de impresi&oacute;n para el pedido seleccionado es vacio o nulo, favor de validar");
		return;
	}
	
	for (i = 0; i < criteriosImpresionContrato.length; i++) {
		if(criteriosImpresionContrato[i].idPedido!="" ){
			if(criteriosImpresionContrato[i].idPedido == dataItemPagoMonitorHead.vblen){
				dataItemPagoMonitorHead.stContra = criteriosImpresionContrato[i].stContra;
			}
		}
	}
				if (dataItemPagoMonitorHead.stContra=='00' 
						|| dataItemPagoMonitorHead.stContra=='01' 
							|| dataItemPagoMonitorHead.stContra=='05'){
				
				validaSeleccion(1,'MONITOR'); //IMPRIME EL CONTRATO
				//ACTUALIZA EL STATUS DE IMPRESION
				setTimeout ("actualizaSituacionContrato();", 8000); 	
				}else{
					//validaSeleccion(1,'MONITOR'); //IMPRIME EL CONTRATO
					//ACTUALIZA EL STATUS DE IMPRESION
					//setTimeout ("actualizaSituacionContrato();", 8000); 	
					$("#mensajes_main").html("El status del pedido impide la impresi&oacute;n del contrato, favor de validar");
					return;
				}	
}

function actualizaSituacionContrato(){
	var gridPagosMonitorHead1 = $("#dg_PagosRegistradosMonitor").data("kendoGrid");    
    var dataItemPagoMonitorHead1 = gridPagosMonitorHead1.dataItem(gridPagosMonitorHead1.tbody.find("tr.k-state-selected"));
    
	//actualizaStatusImpresion("01",dataItemPagoMonitorHead1.id_equnr);
	
	var datosMonitor ="accion=1&xmlPed=<criterios><criterio><id>"+dataItemPagoMonitorHead1.vblen+"</id></criterio></criterios>";
	   
	buscaPedidosEnMonitor(datosMonitor,'NO',"TRAEDATOS","","", "","");
}

function actualizaStatusImpresion(paramStatus,paramIdequipo){

	 var datos = "accion=1&statusImpresion="+paramStatus+"&idEqunr="+paramIdequipo;
	 url_aux=contexPath + "/actualizaStatusImpresion";
	 
		$.ajax( {
			type : "POST",
			url : url_aux,
			data : datos ,
			success : function(response) {
				
			if (response.resActualizaImpresion.mensaje == "SUCCESS") {
				 //kendoConsole.log("Cliente registrado exitosamente "+response.respAddClienteDto.descripcion);
				  $("#mensajes_main").html(response.resActualizaImpresion.descripcion);
				
				
			} else {
				$("#mensajes_main").html("Hubo un error: "+response.resActualizaImpresion.descripcion);
		    }
		},
		error : function(e) {
			$("#mensajes_main").html("Error:"+e);
		}
		});

}



var sub_equipos_select=[];
var sub_equipos_select_xml="";

function seleccionaSubequipo(id, obj_chk)
{
	if(obj_chk.checked)
	{
		var existe = false;
		
		if(sub_equipos_select.length==0)
		{
			sub_equipos_select.push({id_equnr: obj_chk.value});
		}
		else
		{
			for(var i=0; i<sub_equipos_select.length; i++)
			{
				if(sub_equipos_select[i].id_equnr==obj_chk.value)
				{
					existe=true;
				}
			}
			
			if(!existe)
			{
				sub_equipos_select.push({id_equnr: obj_chk.value});
			}
		}		
	}
	else
	{
		if(sub_equipos_select.length>=1)
		{
			for(var i=0; i<sub_equipos_select.length; i++)
	        {			
				if(sub_equipos_select[i].id_equnr==obj_chk.value)
				{
					sub_equipos_select.splice(i, 1);
				}
	        }				
		}
	}
	
	if(sub_equipos_select.length>=1)
	{
		sub_equipos_select_xml=createXMLCriterios( "criterios", sub_equipos_select );
	}
	else
	{
		sub_equipos_select_xml="";
	}
}


function openWinEquiposCancelacion(from)
{
	sub_equipos_select=[];
	sub_equipos_select_xml="";

	
	var equipo_add=window.parent.dataItemCotizacion.cotizacionSubequipos;
	if(equipo_add.length>=1)
	{
		if(from=="cancelar")
		{
			var winSubEq=$("#winSubEquiposCancelacion").kendoWindow({
				actions: ["Close"],
				modal: true,
				resizable: false,
				content: 
					{
						url:contexPath+"/CancelacionesParciales.htm?from="+from
				},
		        height: "300px",
		        title: " Cancelaciones parciales / equipos",
				width: "600px"
			    }).data("kendoWindow");		
			winSubEq.center();
			winSubEq.open();
		}
		else	
		{
			var winSubEq=$("#winSubEquiposCancelacion").kendoWindow({
				actions: ["Close"],
				modal: true,
				resizable: false,
				content: 
					{
						url:contexPath+"/CancelacionesParciales.htm?from="+from   
				},
		        height: "350px",
		        title: " Traspasos / equipos",
				width: "600px"
			    }).data("kendoWindow");		
			winSubEq.center();
			winSubEq.open();
		}		
	}
	else
	{
		$("#mensajes_main").html("No existen equipos para cancelar");		
	}
	
}

function closeWinCancel()
{
	$("#winSubEquiposCancelacion").data("kendoWindow").close();
}

function onCloseWinSubEquiposCancel()
{
	
}

function registrarArchivoVoucherVao(paramEquipox)
{
	
	//$("#cmb_faces").val()+"|"+$("#cmb_equipo").val()+"|"+itemTipos.id_ticket_area+"|"+itemTipos.id_ticket_file+"|"+itemSubTipos.id_ticket_area+"|"+itemSubTipos.id_ticket_file+"|"+itemEstatus.id_ticket_stat+"|"+itemEstatus.id_ticket_statx
	
	
	var faseEquipo= paramEquipox; //window.parent.dataItemCotizacion.dpto;
	var palabras = faseEquipo.split('-');
	var fase = palabras[0];
	var paramCadenaReemplaza = SubStringRight(palabras[0],2);
	var paramFase = fase.replace(paramCadenaReemplaza,"");
	var paramEquipo = paramEquipox; //window.parent.dataItemCotizacion.dpto;
	var paramTipoId= "02";
	var paramTipoDes="00";
	var paramSubTipoId="02";
	var paramSubTipoDes="01";
	var paramEstatusId=" ";
	var paramEstatusDes = " ";
	
	var paramDatos="accion=guardar&origenPago=RegPago&datos="+paramFase + "|" + paramEquipo + "|" + paramTipoId + "|" + paramTipoDes +  "|" + paramSubTipoId + "|" + paramSubTipoDes + "|" + paramEstatusId + "|" + paramEstatusDes;
	$.ajax( {
		type : "POST",
		url : contexPath + "/DetallePago.htm",
		data : paramDatos,
		success : function(response) {
		if (response.responseDigitalizacionDto.mensaje == "SUCCESS") {
			$("#mensajes_main").html(response.responseDigitalizacionDto.descripcion);
		} else {
			
			$("#mensajes_main").html("Hubo un error:"+response.responseDigitalizacionDto.descripcion);
	    }
	},
	error : function(e) {
		$("#mensajes_main").html("Error:"+e);
		
	}
	});

}


function SubStringRight(Cad, NroCar){
	
	var Valor = Cad;
	var LongCad = parseInt(Cad.length);
	if(NroCar<=LongCad){  
		var Inicio = (LongCad-NroCar);   
		Valor = Valor.substring(Inicio, LongCad); 
	} else {   
		Valor = Cad;   
	}  
	return Valor; 
}


function ValidaComprobantes(paramEquipo)
{
	    
		//alert("Primer Alert " +regimenFiscal);
	
		buscaTipoPersonaSap(idClienteCarteraActual);
		
		//regimenFiscal = "PERSONA FISICA";
	
		//alert("Segundo Alert " +regimenFiscal);
		
		$.ajax({  
	    type: "POST",
	    url: contexPath + "/ValidaDigitalizacionFile.htm",
	    data: "equipo="+paramEquipo+"&i_id_ut_current=0&tipo="+regimenFiscal, 
	    success: function(response){
	      // we have the response 
	      if(response.mensaje == "SUCCESS"){
	    	  resValida = false;
	      } 
	      else
	      {
	    	  //document.getElementById("idCeldaCarta").setAttribute("style","display: none; visibility: hidden");
			  //document.getElementById("idCeldaContrato").setAttribute("style","display: none; visibility: hidden");
	    	  $("#mensajes_main").html("Favor de verificar: Digitalizaciones Requeridas");
	    	  alert(response.descripcion);
	    	  resValida = true;
	      }    
	    },  
	    error: function(e){  
	    	//$.unblockUI()
	    	$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
	    }  
	  });
		return;
}

function buscaTipoPersonaSap(idClienteCarteraActual)
{
		var xmlClienteSap="<criterios><criterio><idClieteSap>"+idClienteCarteraActual+"</idClieteSap></criterio></criterios>";		
	
		$.ajax( {
		type : "POST",
		url : contexPath + "/CatalogoClientes.htm",
		data : "accion=1&xmlCliSap="+xmlClienteSap,
		success : function(response) {
		// we have the response	
		//if (response.mensaje == "SUCCESS") {
			var clientesSAPDataExport = response.objClientesSapList;
		//	 regimenFiscal = clientesSAPDataExport[0].cliente.regimenFiscal;
				
	//	} else {
			//kendoConsole.log("Usuario Autenticado","error");
		//	$("#mensajes_main").html("Usuario Autenticado");
		//}
	},
	error : function(e) {
		//kendoConsole.log(e,"error");
		$("#mensajes_main").html("Error:"+e);
	}
	});

}
