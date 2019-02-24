//variables globales 
var xmlPed="";
var xmlCli="";
var xmlReg="";
var dataGridPedM=null;
var dataGridPedMSource=null;
var dataGridCliM=null;
var dataGridCliMSource=null;
var dataGridRegM=null;
var dataGridRegMSource=null;
var dataItemPagoAddNew=null;
var dataItemPago=null;
var dataItemPagoAdd=null;
var selectedRow = "";
var index = "";
var indexAdd="";
var dataFindDet=null;
var pagos_add=[];
var arregloIdsPagos=[];
pagosEliminar=[];
dataDetPagos=null;
var gridDetallePagos = null;

//function que actualiza el grid con los datos que son actualizados en detalle de pagos 
function actualizaItem(){
		if(validadFields())
		{
			/*$("#btnAgregar").attr('disabled', 'true');
			$("#btnBorrar").attr('disabled', 'true');
			$("#btnModificar").attr('disabled', 'true');*/
			dataDetPagos[index].fpago=$('#fchPago').val();
			//dataDetPagos[index].hpago=$('#hrPago').val()+":00";
			dataDetPagos[index].concepto=$('#ddlConceptos').val();
			dataDetPagos[index].conceptoTxt=$('#ddlConceptos').data("kendoDropDownList").text();
			dataDetPagos[index].medioPago=$('#ddlMedios').val();
			dataDetPagos[index].medioPagoTxt=$('#ddlMedios').data("kendoDropDownList").text();
			dataDetPagos[index].observaciones=$('#observaciones').val();
			//alert('#dataDetPagos[index].observaciones ... ' + dataDetPagos[index].observaciones);
			dataDetPagos[index].monto=$('#monto').val();
			dataDetPagos[index].refer=$('#refer').val();
			dataDetPagos[index].file_a=$('#archivo').val();
			dataDetPagos[index].folioOper=$('#folioOper').val();
			dataDetPagos[index].update="U";
			setDataAdd(dataDetPagos);
		}
}
//funcion que elimina pago del grid y le pone bandera de "D" 
function quitPago()
{
	
	var gridPagMoni = $("#dg_Detalles").data("kendoGrid");    
	dataItemPagoAdd = gridPagMoni.dataItem(gridPagMoni.tbody.find("tr.k-state-selected"));
	selectedRow = gridPagMoni.select();
	indexAddPagMo = selectedRow.index();
	
	
	//alert (dataItemPagoAdd.validDesc);
	if(dataItemPagoAdd==null) {
			//kendoConsole.log("Seleccione un pago a eliminar","warning");
		$("#mensajes_main").html("Seleccione un pago a eliminar");
		
	}
	
	if(dataItemPagoAdd.validDesc != "" && dataItemPagoAdd.validDesc != "No")	{
		//kendoConsole.log("No se puede eliminar pagos conciliados")
		$("#mensajes_main").html("No se puede eliminar pagos conciliados");
	}else{
		$("#winConfirmaEliminaPagoMoni").data("kendoWindow").close();
		if(dataDetPagos[indexAddPagMo].conse!="") {
			
		   dataDetPagos[indexAddPagMo].update="D";
		   pagosEliminar.push(dataDetPagos[indexAddPagMo]);
		   dataDetPagos.splice(indexAddPagMo,1);
		   
		}else {
		   dataDetPagos.splice(indexAddPagMo,1);
		}
	}
		
	setDataAdd(dataDetPagos);
	//dataItemPagoAdd.removeRow(selectedRow);
	//dataItemPagoAdd.saveChanges();
	//dataItemPagoAdd.refresh();
		
}

//funcion que crea los xml del grid en caso de sufrir actualizaciones y verifica el arreglo de idElimados y guarda  
function guardaActualizacion() {    
	var cambios;
	if(pagosEliminar.length>0){
		cambios=true;
	}else {
		for(var i=0; i<dataDetPagos.length; i++) {
			 if(dataDetPagos[i].update!="") {
				 cambios=true;
				 break;
			 } else {
				 cambios=false;
			 }
		}
	}
	
	if(cambios==true) {
	var criteriosP = [];
		
	for(var i=0; i<dataDetPagos.length; i++)
	{
	 		if(dataDetPagos[i].update!=""){
		 		var params = {};
		 		params["FechaPago"] = dataDetPagos[i].fpago;
				params["HoraPago"] = dataDetPagos[i].hpago;
				params["Monto"] = dataDetPagos[i].monto;
				params["Referencia"] = dataDetPagos[i].refer;
				params["UpReg"] = dataDetPagos[i].update;
				params["Fregi"] = dataDetPagos[i].fregi;
		 		params["Conse"] = dataDetPagos[i].conse;
		 		params["FolioOper"] = dataDetPagos[i].folioOper;
		 		params["Archivo"] = dataDetPagos[i].file_a;
		 		params["Concepto"] = dataDetPagos[i].concepto;
		 		params["MedPago"] = dataDetPagos[i].medioPago;
		 		params["Observaciones"] = encodeURIComponent(dataDetPagos[i].observaciones);
				criteriosP.push(params);
	 		}
		}
	if(pagosEliminar.length>0)
	{
		for(var i=0; i<pagosEliminar.length; i++)
		{
			var params = {};
	 		params["FechaPago"] = pagosEliminar[i].fpago;
			params["HoraPago"] = pagosEliminar[i].hpago;
			params["Monto"] = pagosEliminar[i].monto;
			params["Referencia"] = pagosEliminar[i].refer;
			params["UpReg"] = pagosEliminar[i].update;
			params["Fregi"] = pagosEliminar[i].fregi;
	 		params["Conse"] = pagosEliminar[i].conse;
	 		params["FolioOper"] = pagosEliminar[i].folioOper;
	 		params["Archivo"] = pagosEliminar[i].file_a;
	 		params["Concepto"] = pagosEliminar[i].concepto;
	 		params["MedPago"] = pagosEliminar[i].medioPago;
	 		params["Observaciones"] = encodeURIComponent(pagosEliminar[i].observaciones);
			criteriosP.push(params);
		}
	}	
	xmlPagos= createXMLCriterios("pagos",criteriosP);

	$.ajax( {
		type : "POST",
		url : contexPath + "/RegistroPagoMensual.htm",
		data : "accion=1&time=1&pedido="+window.parent.dataItemPagoMonitorHead.vblen+"&idFaseEquipo=" + $('#idFaseEquipoX').val() + "&fregi="+window.parent.dataItemPagoMonitorHead.fregi+"&xmlPagos="+xmlPagos,
		success : function(response) {
			// we have the response
		if (response.responseAddPagos.mensaje == "SUCCESS") {
			window.parent.$("#windowModificaDetallePagoMonitor").data("kendoWindow").close();
			var actualizaURIXML= "<criterio><criterio><clientes>"+ $("#fromURIActualiza").val() +"</clientes></criterio></criterio>";
		     var urldatos="accion=2&cotPed=1&xmlCli="+actualizaURIXML;
			 window.parent.buscaPedidoDeCierreVentaAMonitor('REGISTRO_PAGO', 'OTROS', response.responsePagosReg.pagoHdrList[0].fregi , response.responsePagosReg.pagoHdrList[0].vblen , response.responsePagosReg.pagoHdrList[0].kunnr);
		     //window.parent.buscaPedidosParaMonitor(urldatos,'NO',response.responsePagosReg.pagoHdrList[0].fregi); //SE CAMBIA A IMPRESION DE 
			 $("#mensajes_main").html("Pagos actualizados");
			
		} else {
			$("#mensajes_main").html("Error: "+response.responseAddPagos.descripcion);
	    }
	},
	error : function(e) {
		//kendoConsole.log(e,"error");
		$("#mensajes_main").html("Error: "+e);
		
	}
	});
  }	else
  {
	window.parent.$("#mensajes_main").html("No se realizó ninguna modificación");  
	window.parent.$("#windowModificaDetallePagoMonitor").data("kendoWindow").close();
	//parent.kendoConsole.log("No se realizó ninguna modificación","warning");
  }
}

//funcion que va agregando los pagos , aun cuando modifica y elimina
function addPago()
{
		if(validadFields())
		{
			var horap = '12:00:00';
			
				if(window.parent.$("#fromDetalle").val()=="modificaPago")
				{
					//var horap=$('#hrPago').val() + ":00";
					window.parent.dataDetPagos.push({aedat_val:"",conse:"",cputm_val:"",ernam_val:"",file_a:$("#archivo").val(),flag:"",folioOper:$("#folioOper").val(),fpago:$('#fchPago').val(),fregi:window.parent.$("#regi").val(),fvalid:"",hpago:horap,monto:$('#monto').val(),refer:$('#refer').val(),update:"I",valid:"",validDesc:"","concepto":$('#ddlConceptos').val(),"medioPago":$('#ddlMedios').val(), "conceptoTxt":$('#ddlConceptos').data("kendoDropDownList").text(),"medioPagoTxt":$('#ddlMedios').data("kendoDropDownList").text(),"observaciones":$('#observaciones').val()});
					window.parent.$("#winDetPago").data("kendoWindow").close();
					setDataFromModif(window.parent.dataDetPagos);
				}else{
					var gridPag =window.parent.$("#gridPagos").data("kendoGrid");
					var con=1;
					if(gridPag.dataSource.data().length==0)
					{
						cont=1;
					}
					else{
						var cuenta=gridPag.dataItem(gridPag.tbody.find(">tr:last"));
						cont=cuenta.conse+1;
					}
					//var horap=$('#hrPago').val() + ":00";
					//window.parent.pagos_add.push({"conse":cont,"fpago":$('#fchPago').val(),"hpago":horap,"monto":$('#monto').val(),"refer":$('#refer').val(),"file_a":$('#archivo').val(),"folioOper":$('#folioOper').val(),"validDesc":"","fregi":"","ernam_val":"","aedat_val":"","cputm_val":""});
					window.parent.pagos_add.push({"conse":cont,"fpago":$('#fchPago').val(),"hpago":horap,"concepto":$('#ddlConceptos').val(),"medioPago":$('#ddlMedios').val(),"observaciones":$('#observaciones').val(),"conceptoTxt":$('#ddlConceptos').data("kendoDropDownList").text(),"medioPagoTxt":$('#ddlMedios').data("kendoDropDownList").text(),"monto":$('#monto').val(),"refer":$('#refer').val(),"file_a":$('#archivo').val(),"folioOper":$('#folioOper').val(),"validDesc":"","fregi":"","ernam_val":"","aedat_val":"","cputm_val":""});
					window.parent.$("#winDetPago").data("kendoWindow").close();
					setDataPagosNew(window.parent.pagos_add);
				}
				
			
				$('#regPagos').submit();
			
				
		}

	
}

//funcion que valida el contenido de los campos y al menos que esten llenos
function validadFields(){
var resp=false;
//var expTime=/^(0[1-9]|1\d|2[0-3]):([0-5]\d):([0-5]\d)$/;
//var expTime=/^(0[1-9]|1\d|2[0-3]):([0-5]\d)$/;
var datepicker = $("#fchPago").data("kendoDatePicker");
var setValue = function () {
	     datepicker.value($("#value").val());
};
if($('#fchPago').val()=="" || $('#ddlConceptos').val()=="" || $('#ddlMedios').val()=="" || $('#monto').val()=="" || $('#fileData').val()=="" || $('#folioOper').val()=="")
{
	//kendoConsole.log("Información incompleta","warning");
	$("#mensajes_main").html("Información incompleta");
}else if(datepicker.value()==null){
	//kendoConsole.log("Fecha Inválida","warning");
	$("#mensajes_main").html("Fecha invalida");
}/*else if(!expTime.test($('#hrPago').val())){
	//kendoConsole.log("Hora Inválida. Capture HH:MM:SS","warning");
	$("#mensajes_main").html("Hora Inválida. Capture HH:MM");
}*/
//else if($('#file').val()==""){
//	kendoConsole.log("Archivo obligatorio","warning");
//}
else{
	resp=true;
}

return resp;
}

//funcion que llena el grid con los detalles de pagos 
function setDataFromModif(listaPagos)
{
//alert('entra a setDataFromModif() ...');
		window.parent.$("#dg_Detalles").empty();
		window.parent.$("#dg_Detalles").kendoGrid( {
			dataSource : listaPagos,
			selectable:"row",
			sortable: true,
	        resizable: true,
	        pageable: true,
	        change: onChangePago ,
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
			},/*
			{	
				field : "hpago",
				title : "Hora de Pago",
				editable: true,
				width:150
					
			},*/
			 {	
				field : "conceptoTxt",
				title : "Concepto",
				editable: true,
				width:150
					
			},
			{	
				field : "medioPagoTxt",
				title : "Medio de Pago",
				editable: true,
				width:150
					
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
				field : "observaciones",
				title : "Observaciones",
				width:300
			},
			{
				field : "file_a",
				title : "Archivo",
				editable: true,
				width:200
			},
			{
				field : "folioOper",
				title : "Folio Operacion",
				editable: true,
				width:100
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
				width:100,
			}, {
				field : "aedat_val",
				title : "Fecha Validación",
				width:100
			}, {
				field : "cputm_val",
				title : "Hora Validación",
				width:100
			}],
			}).data("kendoGrid");	
		gridDetallePagos = window.parent.$("#dg_Detalles").data("kendoGrid");  
}

//funcion que cuando se inserta un pago en modo insertar pago e va agregando 
function setDataAdd(listaPagos)
{
	//alert('entra a setDataAdd() ...');
	var dataSourceListaPag = new kendo.data.DataSource({
        data: listaPagos,	    	    	        
        pageSize: 50,
        messages: {
            empty: "No hay registros que mostrar"
        }
    });
	
	$("#dg_Detalles").empty();
	$("#dg_Detalles").kendoGrid( {
		dataSource : listaPagos,
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
        /*change: onChangePago,*/
        change: onChangePagoFromMonitor,
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
		/*
		{	
			field : "hpago",
			title : "Hora de Pago",
			editable: true,
			width:150
				
		},*/
		 {	
			field : "conceptoTxt",
			title : "Concepto",
			editable: true,
			width:150
				
		},
		{	
			field : "medioPagoTxt",
			title : "Medio de Pago",
			editable: true,
			width:150
				
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
			field : "file_a",
			title : "Archivo",
			editable: true,
			width:200
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
			width:100,
		}, {
			field : "aedat_val",
			title : "Fecha Validación",
			width:100
		}, {
			field : "cputm_val",
			title : "Hora Validación",
			width:100
		}],
		}).data("kendoGrid");

	//alert("LIMPIANDO FORMULARIO :::");
	//$('#fchPago').data("kendoDatePicker");
	//$('#hrPago').val(null);
	//var lConceptos = $("#ddlConceptos").data("kendoDropDownList");
	//lConceptos.text("");
	//lConceptos.element.val("");
	//$("#ddlConceptos").data("kendoDropDownList").selectedIndex = -1;
	//var lMedios = $("#ddlMedios").data("kendoDropDownList");
	//lMedios.text("");
	//lMedios.element.val("");
	//$("#ddlMedios").data("kendoDropDownList").selectedIndex = -1;
	//$('#observaciones').val(null);
	//$('#monto').val(null);
	//$('#refer').val(null);
	//$('#archivo').val(null);
	//$('#folioOper').val(null);
	 
}

//funcion que va creando el arreglo de los id de los clientes seleccionados 
function cambioCheckReg(obj,id)
{
	if(obj.checked)
	{
		arregloIdsPagos.push({"id":obj.value});	
	}else{
		for(j=0;j<arregloIdsPagos.length;j++)
		{
			if(arregloIdsPagos[j].id==obj.value)
			{
				arregloIdsPagos.splice(j,1);	
			}
		}
	  }	
}
//funcion queidentifica que pago esta seleccionado (para ser modificado y/o eliminado)
function onChangePagoAdd(e) {
	var gridAdd = window.parent.$("#gridPagos").data("kendoGrid");    
    dataItemPagoAddNew = gridAdd.dataItem(gridAdd.tbody.find("tr.k-state-selected"));
    selectedRow = gridAdd.select();
    indexAdd = selectedRow.index();
}
// funcion para cambiar el dataItem seleccionado del dataGrid de pagos agregados
function onChangePago(e) {
	//alert (gridDetallePagos);
	//if (gridDetallePagos == null || gridDetallePagos == undefined) {
	//	gridDetallePagos = $("#dg_Detalles").data("kendoGrid");
	//}
	var gridDetallePagos = e.sender;
    dataItemPagoAdd = gridDetallePagos.dataItem(gridDetallePagos.tbody.find("tr.k-state-selected"));
	//alert("entra onChangePago() con dataItemPagoAdd.validDesc = " + dataItemPagoAdd.validDesc);
    selectedRow = gridDetallePagos.select();
    index = selectedRow.index();
    
    //alert("'FUNCION'");
    if(dataItemPagoAdd.validDesc=="No" || dataItemPagoAdd.validDesc=="") {
    	var horaPago = dataItemPagoAdd.hpago + "";
    	//alert("cargaDatos()");
    		    
    	window.parent.cargaDatos(horaPago.substring(0,horaPago.lastIndexOf(":")), dataItemPagoAdd.fpago, dataItemPagoAdd.monto, dataItemPagoAdd.refer, dataItemPagoAdd.folioOper, dataItemPagoAdd.concepto, dataItemPagoAdd.medioPago, dataItemPagoAdd.observaciones);
	    
	    //var datePicker = $("#fchPago").data("kendoDatePicker");
	    //alert("'" + dataItemPagoAdd.fpago + "'");
	    //$("#fchPago").val(dataItemPagoAdd.fpago);
		
		//var horaPago = dataItemPagoAdd.hpago + "";
	    //alert("'" + dataItemPagoAdd.hpago + "'");
	    //$('#hrPago').val(horaPago.substring(0,horaPago.lastIndexOf(":")));
	    //$('#hrPago').val("11:00");
	    //document.getElementById('hrPago').value=horaPago.substring(0,horaPago.lastIndexOf(":"));
	    //alert("'" + dataItemPagoAdd.monto + "'");
	    //$('#monto').val(dataItemPagoAdd.monto);
	    //document.getElementById('monto').value=dataItemPagoAdd.monto;
	    //7alert("'" + dataItemPagoAdd.refer + "'");
	    //$('#refer').val(dataItemPagoAdd.refer);
	    //alert("'" + dataItemPagoAdd.file_a + "'");
	    //$('#archivo').val(dataItemPagoAdd.file_a);
	    //alert("'" + dataItemPagoAdd.folioOper + "'");
	    //$('#folioOper').val(dataItemPagoAdd.folioOper);
    }
    else if(dataItemPagoAdd.validDesc=="Si"){
    	//kendoConsole.log("Pago conciliado, no se puede modificar","warning");
    	$("#mensajes_main").html("Pago conciliado, no se puede modificar");
    }
}


//funcion que registra los pagos en sap 
function registrarPagos(){
	var paramClienteActual = window.parent.dataItemCotizacion.idCliente;
	if(pagos_add.length>0) {
		 var criteriosP = [];
		 for(var i=0; i<pagos_add.length; i++) {
			 		var params = {};
			 		params["FechaPago"] = pagos_add[i].fpago;
					params["HoraPago"] = pagos_add[i].hpago;
					params["Monto"] = pagos_add[i].monto;
					params["Referencia"] = pagos_add[i].refer;
					params["UpReg"] = "I";
					params["Fregi"] = "";
			 		params["Conse"] = pagos_add[i].conse;
			 		params["FolioOper"] = pagos_add[i].folioOper;
			 		params["Archivo"] = pagos_add[i].file_a;
					params["Concepto"] = pagos_add[i].concepto;
					params["MedioPago"] = pagos_add[i].medioPago;
					params["Observaciones"] = encodeURIComponent(pagos_add[i].observaciones);
			 		criteriosP.push(params);
		  }

		 console.log(criteriosP);
		 
		 xmlPagos= createXMLCriterios("pagos",criteriosP);
		 var idPedidoOrCotizacionActual='';
		 
		 var datosURLx='';
		 
		 if ($("#fromR").val()=="fromMonitor2"){
			 //alert("entrando:" + $('#idFaseEquipoX').val());
			 idPedidoOrCotizacionActual=window.parent.dataItemPagoMonitorHead.vblen;
			 datosURLx="accion=1&time=1&pedido="+idPedidoOrCotizacionActual + "&idFaseEquipo=" + $('#idFaseEquipoX').val() + "&xmlPagos="+xmlPagos;
		  } else {
			 idPedidoOrCotizacionActual = window.parent.dataItemCotizacion.idCotizacion;
			 datosURLx="accion=1&pedido="+idPedidoOrCotizacionActual + "&idFaseEquipo=" + $('#idFaseEquipoX').val() + "&xmlPagos="+xmlPagos;
		  }		 
		 $.ajax( {
				type : "POST",
				url : contexPath + "/RegistroPagoMensual.htm",
				data : datosURLx, // "accion=1&pedido="+idPedidoOrCotizacionActual+"&xmlPagos="+xmlPagos,
				success : function(response) {
					if (response.responseAddPagos.mensaje == "SUCCESS01") {
						window.parent.$("#divIdImpTalonario").css("display", "block"); 
						openWinViewClient("registrar","SI",paramClienteActual);
						
					} else {
						if (response.responseAddPagos.mensaje == "SUCCESS") { //PAGOS DIFERIDOS
							window.parent.$("#idPedidoRegPago").val(response.responseAddPagos.idPedido);
							window.parent.$("#idClienteRegPago").val(response.responseAddPagos.idCliente);
							
							window.parent.$("#windowRegPago").data("kendoWindow").close();
							if($("#fromR").val()=="fromMonitor" || $("#fromR").val()=="fromMonitor2")
							  {
								//si manda amonitor de pagos 
								 if ($("#fromR").val()=="fromMonitor2"){
									 var actualizaURIXML= "<criterio><criterio><clientes>"+ $("#fromURIActualiza").val() +"</clientes></criterio></criterio>";
									 var urldatos="accion=2&cotPed=1&xmlCli="+actualizaURIXML;
								     window.parent.buscaPedidoDeCierreVentaAMonitor('REGISTRO_PAGO', 'OTROS', response.responseAddPagos.fregi , response.responseAddPagos.idPedido , response.responseAddPagos.idCliente);
									
								 } else {
									 window.parent.openSeleccionMonitorPagos();
									 window.parent.openWindowImpresion(5,response.responseAddPagos.fregi);	 
								 }
								
								
							} else {
								//window.parent.openWindowImpresion(5,response.responseAddPagos.fregi);
								//RegistrarArchivoVao();
								window.parent.buscaPedidoDeCierreVentaAMonitor('REGISTRO_PAGO', 'OTROS', response.responseAddPagos.fregi , response.responseAddPagos.idPedido , response.responseAddPagos.idCliente);
							}
							
						} else {
							if (response.responseAddPagos.mensaje == "SUCCESS02") { //SEGUNDO PAGO
								
								if(response.responseAddPagos.descripcion != null && response.responseAddPagos.descripcion!=undefined && response.responseAddPagos.descripcion!= "")
								{
									window.parent.$("#windowRegPago").data("kendoWindow").close();
									window.parent.$("#mensajes_main").html("Error: " + response.responseAddPagos.descripcion);
								}
								else
								{
									window.parent.$("#windowRegPago").data("kendoWindow").close();
									window.parent.buscaPedidoDeCierreVentaAMonitor('REGISTRO_PAGO', 'SEGUNDO', response.responseAddPagos.fregi , response.responseAddPagos.idPedido , response.responseAddPagos.idCliente);							
								}				

								
							} else {
								$("#mensajes_main").html("Error: "+response.responseAddPagos.descripcion);
						    }
						}
					}					
				},
				error : function(e) {
					    //ocultaBotonesRegP();
					$("#mensajes_main").html("Error: "+e);
						
				}
		});
	}
}

//funcion que cierra la ventana de registrar pago
function cancelarRegistro()
{
	 window.parent.$("#windowRegPago").data("kendoWindow").close();
}




//funcion que busca los pedidos dependiendo de los criterios de seleccion que se hayan capt
function BuscarPagos()
{
	var datosMonitor = $('#monitorPagos').serialize()+"&accion=1&xmlPed=<criterios><criterio><id>"+window.parent.dataItemCotizacion.idPedido+"</id></criterio></criterios>";
	
	$.ajax( {
		type : "POST",
		url : contexPath + "/MonitorPagos.htm",
		data : datosMonitor,
		success : function(response) {
    	// we have the response
		if (response.responsePagosReg.mensaje == "SUCCESS") {
				document.getElementById("resultsPagos").setAttribute("style","visibility:visible")
				var findDataExportPagos = response.responsePagosReg.objPagoHdrList;
				var dataFindPagos = [];
				for (i = 0; i < findDataExportPagos.length; i++) {
					dataFindPagos.push(findDataExportPagos[i]);
				}
				
				var dataSourcePagosLoc = new kendo.data.DataSource({
			        data: dataFindPagos,	    	    	        
			        pageSize: 50,
			        messages: {
			            empty: "No hay registros que mostrar"
			        }
			    });
				
			$("#dg_PagosRegistrados").empty();
			$("#dg_PagosRegistrados").kendoGrid( {
				 dataSource :dataSourcePagosLoc,
				 height: 400,
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
                 change: onChangePagoHdr,
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
			
			
		} else {
			//kendoConsole.log("Hubo un error: "+response.responsePagosReg.descripcion,"error");
			$("#mensajes_main").html("Error: "+response.responsePagosReg.descripcion);
	    }
	},
	error : function(e) {
		//kendoConsole.log(e,"error");
		$("#mensajes_main").html("Error: "+e);
		
	}
	});

}


//
function openCriteriosMonitor(tipoM)
{
	switch (tipoM) {
	case 'ped':
		document.getElementById("divRegistrosMonitor").setAttribute("style","visibility:hidden")
		document.getElementById("divPedidosMonitor").setAttribute("style","visibility:visible")
		break;
	case 'reg':
		document.getElementById("divPedidosMonitor").setAttribute("style","visibility:hidden")
		document.getElementById("divRegistrosMonitor").setAttribute("style","visibility:visible")
		break;

	default:
		break;
	}
}

//funcion para crear el arreglo con los datos capturados en los grids de busquedad 
function aceptarCriteriosMonitor(opM)
{
switch (opM) {
case 'ped':
	document.getElementById("divPedidosMonitor").setAttribute("style","visibility:hidden")
	dataGridPedM = $("#gridPedidosMonitor").data("kendoGrid");
	dataGridPedMSource = dataGridPedM.dataSource.data();
	if(dataGridPedMSource[0].id!="")
	{
	$('#pedido').val(dataGridPedMSource[0].id).attr('disabled', 'true');
	}else
	{
		// kendoConsole.log("No deje criterios en blanco en Pedidos","warning")
		$("#mensajes_main").html("No deje criterios en blanco en Pedidos");
		document.getElementById("divPedidosMonitor").setAttribute("style","visibility:visible")
	}
	break;

case 'reg':
	document.getElementById("divRegistrosMonitor").setAttribute("style","visibility:hidden")
	dataGridRegM = $("#gridRegistrosMonitor").data("kendoGrid");
	dataGridRegMSource = dataGridRegM.dataSource.data();
	if(dataGridRegMSource[0].id!="")
	{
	$('#registro').val(dataGridRegMSource[0].id).attr('disabled', 'true');
	}else
	{
		 //kendoConsole.log("No deje criterios en blanco Registro","warning")
		$("#mensajes_main").html("No deje criterios en blanco en Pedidos");
		document.getElementById("divRegistrosMonitor").setAttribute("style","visibility:visible")
	}
	break;
default:
	break;
}	
	
}

// funcion para crear el xml
function createXMLMonitor( root, params )
{
	  var nodes;
	  var xml = "";
	  if( root )
	    xml += "<" + root + ">";
	  for(var i=0; i<params.length; i++)
	  {
		  nodes=params[i];
		  xml += "<element>";
		  for( theNode in nodes )
		  {
		    xml += "<" + theNode + ">" + nodes[theNode] + "</" + theNode + ">";
		  }	
		  xml += "</element>";
	  }  
	  xml += "</" + root + ">";
	  
	  return xml;
	}

//funcion que cierra las divisiones de los grids 
function cancelarCriteriosMonitor(opcM){
	switch (opcM) {
	case 'ped':
		document.getElementById("divPedidosMonitor").setAttribute("style","visibility:hidden")
		break;
	case 'reg':
		document.getElementById("divRegistrosMonitor").setAttribute("style","visibility:hidden")
		break;
	default:
		break;
	}	
	
}

//funcion que extrae los array para su posterior transformacion a xml
function extraccionArrayMonitor(dataGridAux,dataGridSourceAux,tipo)
{
	if(dataGridAux!=null)
	{     
		 var criterios = [];
		 for(var i=0; i<dataGridSourceAux.length; i++)
			{
				if(dataGridSourceAux[i].id!="" ){
					var params = {};
					params["id"] = dataGridSourceAux[i].id;
					criterios.push(params);
				}
			}
	}
	return createXMLMonitor("criterios",criterios);
	
}

//funcion que valida el formulario 
function ValidaFormSeleccionMonitor()
{
	var datepickerIni = $("#fch_ini").data("kendoDatePicker");
	var setValueIni = function () {
		     datepickerIni.value($("#value").val());
	};
	var datepickerFin = $("#fch_fin").data("kendoDatePicker");
	var setValueFin = function () {
		     datepickerFin.value($("#value").val());
	};
	
	
var resp=false;

	if($.trim($('#fch_ini').val())=="" && $.trim($('#fch_fin').val())=="" && $.trim($('#clientes').val())==""  &&  $.trim($('#pedido').val())=="" &&  $.trim($('#registro').val())=="")
	{  
		//kendoConsole.log("Capture algun criterio de búsquedad","warning");
		$("#mensajes_main").html("Capture algun criterio de búsquedad","warning");
		$('#fch_ini').focus();
	}else if($('#fch_ini').val()==null || $('#fch_fin').val()==null){
		//kendoConsole.log("Fechas erroneas","warning");
		$("#mensajes_main").html("Fechas erroneas");
	}else{
		resp=true;
	}
	
	return resp;
}

// funcion para cambiar el dataItem seleccionado
function onChangePagoHdr(e) {
	var gridPagos = $("#dg_PagosRegistrados").data("kendoGrid");    
    dataItemPago = gridPagos.dataItem(gridPagos.tbody.find("tr.k-state-selected"));
   
}

///funcion que abre la ventana de detalle delos pagos 
function openDetallePago(from)
{      
	if(dataItemPago==null)
	{
		 //kendoConsole.log("Seleccione un Registro","warning");
		$("#mensajes_main").html("Seleccione un Registro");
	}
	else
	{
		 var windowDetPago=$("#windowDetallePago").kendoWindow({
				actions: ["Close"],
				modal: true,
				resizable: false,
				content: 
					{
						url:contexPath+"/DetallePago.htm?regi="+dataItemPago.fregi+"&from="+from
				},
		        height: "450px",
		        title: "Cliente "+dataItemPago.kunnr +"-"+dataItemPago.kunnrtx,
				width: "800px"
			    }).data("kendoWindow");		
		 windowDetPago.center();
		 windowDetPago.open();
	}
	

}



//funcion que llena los detalles de los pagos , una vez q selecciona un header 
function cargaDatosDetallePagos()
{
	//alert('entra a cargaDatosDetallePagos() ...');
   var xmlRegistro="<criterios><criterio><id>"+window.parent.dataItemPago.fregi+"</id></criterio></criterios>"; 
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
				
				setDataAdd(dataDetPagos);
				
		} else {
			//kendoConsole.log("Hubo un error: "+response.responsePagosReg.descripcion,"error");
			$("#mensajes_main").html("Error: "+response.responsePagosReg.descripcion);
	    }
	},
	error : function(e) {
		//kendoConsole.log(e,"error");
		$("#mensajes_main").html("Error: "+e);
		
	}
	});
}


//funcion que abre la pantalla de monitor de pagos 
function openSeleccionMonitorPagos()
{
	if(dataItemCotizacion!=null)
	{
	var windowMonitorPagos=$("#windowMonitorPagos").kendoWindow({
		actions: ["Close"],
		modal: true,
		resizable: false,
		content: 
			{
				url:contexPath+"/MonitorPagos.htm"
		},
        height: "480px",
        title: "Monitor de Pagos ",
		width: "855px"
	    }).data("kendoWindow");		
   windowMonitorPagos.center();
   windowMonitorPagos.open();
	}else 
	{
		//kendoConsole.log("Seleccione pedido","warning");
		$("#mensajes_main").html("Seleccione pedido");
	}
}


//funcion que cierra la ventana y regresa a cierre venta jsp 
function regresaCierre()
{
	window.parent.$("#windowMonitorPagos").data("kendoWindow").close();
}
//funcion que cierra la ventana de detalle de pago 
function cierraDetalle()
{
	window.parent.$("#windowDetallePagoMonitor").data("kendoWindow").close();
}

//funcion que cierra la ventana de detalle de pago 
function cierraDetalleModificaPago()
{
	window.parent.$("#windowModificaDetallePagoMonitor").data("kendoWindow").close();
}


function cierraBorradoModificaDetalleMonitor()
{
	$("#winConfirmaEliminaPagoMoni").data("kendoWindow").close();
}

function cierraAvisoDeBorradoPagosPS()
{
	$("#winAvisoNoEdicionElimina").data("kendoWindow").close();
}

function validaEliminarPagoMonitor()
{
	var gridPagMoni = $("#dg_Detalles").data("kendoGrid");    
    var dataItemPagoAddNewEliMon = gridPagMoni.dataItem(gridPagMoni.tbody.find("tr.k-state-selected"));
    selectedRow = gridPagMoni.select();
    indexAdd = selectedRow.index();
	if(dataItemPagoAddNewEliMon==null)
	{
		$("#mensajes_main").html("Seleccione un pago a eliminar");
	}else{
		/*$("#btnAgregar").attr('disabled', 'true');
		$("#btnModificar").attr('disabled', 'true');
		$("#btnBorrar").attr('disabled', 'true');*/
	    $("#winConfirmaEliminaPagoMoni").data("kendoWindow").open();
	    $("#winConfirmaEliminaPagoMoni").data("kendoWindow").center();
	}
}

//funcion uqe 
function regPagoDsdMoniPagos()
{
	function openRegPago()
	{   
		   var windowPago=$("#windowRegPago").kendoWindow({
				actions: ["Close"],
				modal: true,
				resizable: false,
				content: 
					{
						url:contexPath+"/RegistroPagoMensual.htm"
				},
		        height: "600px",
		        title: "Registro Pago Mensual",
				width: "900px"
			    }).data("kendoWindow");		
		   windowPago.center();
		   windowPago.open();
	}
}



//funcion que settea el grid con los detalles de los pagos 
function setDataPagosNew(lista)
{
	//alert('entra a setDataPagosNew() ...');
	if(lista.length>0){
	window.parent.$("#gridPagos").empty();
	window.parent.$("#gridPagos").kendoGrid( {
		dataSource : lista,
		height: 390,
		selectable:"row",
		sortable: true,
        resizable: true,
        change: onChangePagoAdd ,
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
		},/*
		 {	
			field : "hpago",
			title : "Hora de Pago",
			editable: true,
			width:150
				
		},*/
		{	
			field : "conceptoTxt",
			title : "Concepto",
			editable: true,
			width:150
				
		},
		{	
			field : "medioPagoTxt",
			title : "Medio de Pago",
			editable: true,
			width:150
				
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
			editable: true,
			width:300
		}],
		}).data("kendoGrid");
	}else{
		window.parent.$("#gridPagos").empty();	
	}
}

