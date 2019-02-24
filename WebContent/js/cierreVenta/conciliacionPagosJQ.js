//result de la consulta inicial
var PagDetExport = [];
var dataPagDet = [];
var ExtractBankExport = [] ;
var dataExtractBank = [];
var dataPoolError = [];

var cont=0;
var rangoPago=0;
var numMax=0;
var dataItemPagDet=null;
var dataItemExtBank=null;


function cargaDatos(tipo)
{
	//alert("entra a cargaDatos() ... ");
	var datosConsulta="accion=1";
	$.ajax( {
		type : "POST",
		url : contexPath + "/ConciliacionPagos.htm",
		data : datosConsulta,
		success : function(response) {
    	// we have the response
		if (response.responseConciPagos.mensaje == "SUCCESS") {
				//para datos de los pagos detalle y extracto bancario 
				rangoPago=response.responseConciPagos.rango_pago;
				numMax=response.responseConciPagos.num_reg;
				PagDetExport = response.responseConciPagos.objExtractoPagosInfo;
				ExtractBankExport = response.responseConciPagos.objExtractoBankInfo;
				dataPagDet = [];
				dataExtractBank = [];
				
				if(tipo=="noLiga")
				{
					if(PagDetExport!=null || PagDetExport != undefined)
					{
						for (i = 0; i < PagDetExport.length; i++) {
							if(PagDetExport[i].fvalid=="")
							{
							    dataPagDet.push(PagDetExport[i]);
							}	
						}
					}
					else
					{
						PagDetExport = [];
					}
				    
					if(ExtractBankExport!=null || ExtractBankExport != undefined)
					{
						for (i = 0; i < ExtractBankExport.length; i++) {
							 if(ExtractBankExport[i].fregi=="" && ExtractBankExport[i].conse=="" )
							 {
								dataExtractBank.push(ExtractBankExport[i]);
							 }
						}
					}
					else
					{
						ExtractBankExport = [];
					}
				}else
				{
					for (i = 0; i < PagDetExport.length; i++) {
						dataPagDet.push(PagDetExport[i]);
					}
					for (i = 0; i < ExtractBankExport.length; i++) {
						dataExtractBank.push(ExtractBankExport[i]);
					}
				}	
				
				var dataSourcePagDet = new kendo.data.DataSource( {
					data : dataPagDet,
					pageSize : 50
				});
				var dataSourceExtBankNo = new kendo.data.DataSource( {
					data : dataExtractBank,
					pageSize : 50
				});
					
					document.getElementById("logErrores").disabled=true;
					setGrids(dataPagDet,dataExtractBank);
			
	
		} else {
			//kendoConsole.log("Hubo un error: "+response.responseConciPagos.descripcion,"error");
			$("#mensajes_main").html("Error: "+response.responseConciPagos.descripcion);
	    }
	},
	error : function(e) {
		//kendoConsole.log(e,"error");
		$("#mensajes_main").html("Error: "+e);
		
	}
	});
}


function ligar()
{
	//alert("monto:" + dataItemPagDet.monto + ", abono:" + dataItemExtBank.abono + ", rangoPago:" + rangoPago);
	//alert("dataItemPagDet.monto>(dataItemExtBank.abono+rangoPago):" + (dataItemPagDet.monto>(dataItemExtBank.abono+rangoPago)));
	//alert("dataItemPagDet.monto<(dataItemExtBank.abono-rangoPago):" + (dataItemPagDet.monto<(dataItemExtBank.abono-rangoPago)));
	if(dataItemPagDet.fvalid!="")
	{
		//kendoConsole.log("El elemento ya esta ligado","warning");
		$("#mensajes_main").html("El elemento ya esta ligado");
	}else if(dataItemPagDet==null || dataItemExtBank==null )
	{
		//kendoConsole.log("Seleccione pareja para ligar","warning");
		$("#mensajes_main").html("Seleccione pareja para ligar");
	} else if (dataItemPagDet.monto>(dataItemExtBank.abono+rangoPago) || dataItemPagDet.monto<(dataItemExtBank.abono-rangoPago)) {
		$("#mensajes_main").html("Datos no coinciden para ligar");
	}
	//else if(dataItemPagDet.fvalid=="" && cont<=10 && dataItemPagDet.fpago==dataItemExtBank.fpago && dataItemPagDet.hpago==dataItemExtBank.hpago && (dataItemPagDet.monto<=(dataItemExtBank.abono+rangoPago) || dataItemPagDet.monto>=(dataItemExtBank.abono-rangoPago)))
	else if(dataItemPagDet.fvalid=="" && cont<=10 && dataItemPagDet.fpago==dataItemExtBank.fpago && (dataItemPagDet.monto<=(dataItemExtBank.abono+rangoPago) || dataItemPagDet.monto>=(dataItemExtBank.abono-rangoPago)))
	{
		cont++;
		//actualizamos de la vista de los grids principales 
		for(k=0;k<dataPagDet.length;k++)
		{
			//si son iguales el del grid con el q selecciono, le actualiza el fextracto
			if(dataPagDet[k].fregi==dataItemPagDet.fregi && dataPagDet[k].conse==dataItemPagDet.conse)
			{
				dataPagDet[k].flag="temp";
				dataPagDet[k].fvalid=dataItemExtBank.fextracto;
				break;
			}
		}
		for(z=0;z<dataExtractBank.length;z++)
		{
			//si son iguales el del grid con el q selecciono, le actualiza el fextracto
			if(dataExtractBank[z].fextracto==dataItemExtBank.fextracto)
			{
				dataExtractBank[z].flag="temp";
				dataExtractBank[z].fregi=dataItemPagDet.fregi;
				dataExtractBank[z].conse=dataItemPagDet.conse;
				break;
			}
		}
		
		dataItemPagDet=null;
		dataItemExtBank=null;
		
		//if(cont==numMax)
			//guardaLigados();
		
		setGrids(dataPagDet, dataExtractBank);
		
		
	}else{
		//kendoConsole.log("Datos no coinciden para ligar","warning");
		$("#mensajes_main").html("Datos no coinciden para ligar");
	}
	
			
	
}
function changePagDet(e)
{
	
    var gridPagDet = $("#dg_conciliacionPagos").data("kendoGrid");    
    dataItemPagDet= gridPagDet.dataItem(gridPagDet.tbody.find("tr.k-state-selected"));
}
function changeExtBank(e)
{
	
    var gridExtBank = $("#dg_extractoBancario").data("kendoGrid");    
    dataItemExtBank= gridExtBank.dataItem(gridExtBank.tbody.find("tr.k-state-selected"));
}

function guardaLigados()
{
	cont=0;
	var arreglo_ligadosDet=[];
	var arreglo_ligadosExt=[];
	for(k=0;k<dataPagDet.length;k++)
	{
		if(dataPagDet[k].flag=="temp")
		{
			arreglo_ligadosDet.push({"fregi":dataPagDet[k].fregi,"conse":dataPagDet[k].conse,"fvalid":dataPagDet[k].fvalid,"monto":dataPagDet[k].monto});
		}
	}
	for(z=0;z<dataExtractBank.length;z++)
	{
		if(dataExtractBank[z].flag=="temp")
		{
			arreglo_ligadosExt.push({"fregi":dataExtractBank[z].fregi,"conse":dataExtractBank[z].conse,"fextracto":dataExtractBank[z].fextracto,"abono":dataExtractBank[z].abono});
		}
	}
	
	//alert(arreglo_ligadosDet.length + ", " + arreglo_ligadosExt.length);
	if(arreglo_ligadosDet.length>0 && arreglo_ligadosExt.length>0)
	{
		var xmlLigadosDet=createXMLConciliacion("conciliadoDet",arreglo_ligadosDet);
		var xmlLigadosExt= createXMLConciliacion("conciliadoExt",arreglo_ligadosExt);
		
		//alert("entrando a guardar");
		
		var datosGuarda= "accion=2&xmlLigadosDet="+xmlLigadosDet+"&xmlLigadosExt="+xmlLigadosExt;
		$.ajax( {
			type : "POST",
			url : contexPath + "/ConciliacionPagos.htm",
			data : datosGuarda,
			success : function(response) {
	    	// we have the response
			if (response.responseConciPagos.mensaje == "SUCCESS") {
					//vuelve a cargar los datos 
				xmlLigadosDet="";
				xmlLigadosExt="";
				cargaDatos('noLiga');	
				$("#mensajes_main").html("Información almacenada correctamente.");
			} else {
				PoolErrorExport = response.responseConciPagos.objPoolErrorInfo;
				window.parent.dataPoolError = [];
				for (i = 0; i < PoolErrorExport.length; i++) {
					window.parent.dataPoolError.push(PoolErrorExport[i]);
				}
				//kendoConsole.log("Hubo un error: "+response.responseConciPagos.descripcion+" revise el Log","error");
				$("#mensajes_main").html("Error: "+response.responseConciPagos.descripcion);
				document.getElementById("logErrores").disabled=false;
		    }
		},
		error : function(e) {
			//kendoConsole.log(e,"error");
			$("#mensajes_main").html("Error: "+e);
			
		}
		});	
	}
	else{
		//kendoConsole.log("No hay nada que guardar","warning")
		$("#mensajes_main").html("No hay nada que guardar");
	}
	
}

//funcion para crear el xml
function createXMLConciliacion( root, params )
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



//muestra los ligados y quita ligas 
function desligar()
{
	if(dataItemPagDet==null)
	{
		//kendoConsole.log("Seleccione el elemento a desligar","warning");
		$("#mensajes_main").html("Seleccione el elemento a desligar");
	}else if(dataItemPagDet.fvalid!="")
	{
		cont++;
		for(k=0;k<dataPagDet.length;k++)
		{
			//si son iguales el del grid con el q selecciono, le actualiza el fextracto
			if(dataPagDet[k].fvalid==dataItemPagDet.fvalid)
			 {
				dataPagDet[k].flag="temp";
				dataPagDet[k].fvalid="";
				break;
			 }
		}
		for(k=0;k<dataExtractBank.length;k++)
		{
			//si son iguales el del grid con el q selecciono, le actualiza el fextracto
			if(dataExtractBank[k].fextracto==dataItemPagDet.fvalid)
			 {
				dataExtractBank[k].flag="temp";
				dataExtractBank[k].fregi="";
				dataExtractBank[k].conse="";
				break;
			 }
		}
		//if(cont==numMax)
		//	guardaLigados();
		
		setGrids(dataPagDet, dataExtractBank);
	}else{
		//kendoConsole.log("Elemento no tiene liga","warning");
		$("#mensajes_main").html("Elemento no tiene liga");
	}
	
	
}

//guarda y muestra los que estan ligados para desligarlos 
function mostrarNoLigados()
{
	//si hay ligados guarda y carga nuevamente 
	if(arreglo_ligados.length>0)
	{
		guardaLigados();
		cargaDatos('noLiga');
		
	}else
	{
		setGrids(dataPagDetNo,dataExtractBankNo);
	}	
	
}


function setGrids(listaPag,listaExt){
	
	var dataSourcePagos = new kendo.data.DataSource({
        data: listaPag,	    	        
        pageSize: 50,
    });
	
	
	
	var dataSourceExt = new kendo.data.DataSource({
        data: listaExt,	    	        
        pageSize: 50
    });
	
	$("#dg_conciliacionPagos").empty();
	
	var dgcpagos=$("#dg_conciliacionPagos").kendoGrid( {
		 dataSource : dataSourcePagos, //listaPag,
		 selectable:"row",
		 resizable: true,							
		 sortable: true,
		 reorderable: true,	
		 height: 550,	
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
         change:changePagDet,
         columns : [ 
         {
			field : "fregi",
			title : "Folio de Registro"
			//width:100
		},
		{
			field : "conse",
			title : "Consecutivo"
			 //width:100
		},
		{
			field : "refer",
			title : "Referencia"
			 //width:100
		},
		{
			field : "fpago",
			title : "Fecha de Pago"
			//width:100
		}, {
			field : "hpago",
			title : "Hora de Pago"
			//width:100
		}, {
			field : "monto",
			format: "{0:c}",
			title : "Monto"
			//width:100
				
		},{
			field : "fvalid",
			title : "Folio Extracto"
			 //width:100
		}]
		}).data("kendoGrid");
	
//	dgcpagos.dataSource.query({
//	    page: 1,
//	    pageSize: 50,
//	    sort: {
//	        field: "fvalid",
//	        dir: "asc"
//	    }
//	});
	
	dgcpagos.dataSource.sort ({field: "fvalid", dir: "desc"});
	//

	$("#dg_extractoBancario").empty();
	$("#dg_extractoBancario").kendoGrid( {
		 dataSource : dataSourceExt,
		 selectable:"row",
		 resizable: true,							
		 sortable: true,
		 reorderable: true,	
		 height: 550,
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
	     change: changeExtBank,
		 columns : [ 
	     {
			field : "fextracto",
			title : "Folio Extracto"
			// width:100
		 }, {
			field : "fpago",
			title : "Fecha"
			//width:100
		},
		{
			field : "hpago",
			title : "Hora"
			//width:100
		}, {
			field : "descr",
			title : "Descripcion"
			///width:100
		}, {
			field : "abono",
			format: "{0:c}",
			title : "Abono"
			//width:150
				
		}, {
			field : "refer",
			title : "Referencia"
			//width:100
		}, {
			field : "fregi",
			title : "Folio registro",
			//width:100
		}]
		}).data("kendoGrid");	

}

//muestra el Log de errores
function showLog(){
	
		  		$("#windowLogErrores").kendoWindow({
				actions: ["Close"],
				modal: true,
				resizable: false,
				content: 
					{
				  		type: "GET",
						url:contexPath+"/LogError.htm"
				},
		        height: "300px",
		        title: "Errores",
				width: "600px"
			    });
		  	   var windowError =$("#windowLogErrores").data("kendoWindow");
		  	   windowError.center();
		  	   windowError.open();	
	
}


function cargaErrores()
{
	$("#gridErrores").empty();
	$("#gridErrores").kendoGrid( {
		 dataSource : window.parent.dataPoolError,
		 height: 200,
		 sortable: true,
	     resizable: true,
	     pageable: true,
	     columns : [ 
	     {
			field : "fregi",
			title : "Registro",
			 width:80
		 }, {
			field : "extract",
			title : "Extracto",
			width:80
		},
		{
			field : "bapi_msg",
			title : "Mensaje",
			width:150
		}]
		});	
}