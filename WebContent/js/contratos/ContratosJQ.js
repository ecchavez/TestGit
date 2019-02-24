//////////////MODULO PARA IMPRESION DE CONTRATOS//////////////////////
function validaSeleccion(op,paramOrigen){
 if(op<5){
	
		if (paramOrigen=='MONITOR'){
			 if(dataItemPagoMonitorHead==null){
				 $("#mensajes_main").html("Seleccione un pago");
				 return;
			 }
			 			 
			 openWindowImpresion(op,dataItemPagoMonitorHead.vblen,"");
			   
		}else{
			if (paramOrigen=='PAGOINICIAL' || paramOrigen=='SEGUNDOPAGO'){
				var idPedidoz=$("#idPedidoRegPago").val();
				if (idPedidoz==null || idPedidoz=='' || idPedidoz==undefined){
					$("#mensajes_main").html("No existe ningun pedido activo, favor de ir al monitor de pagos");
					return;
				}
				openWindowImpresion(op,idPedidoz,"");
			} else {
			     if(dataItemCotizacion.length<=0){
			        $("#mensajes_main").html("Seleccione un pedido");
			        return;
			     }
			     openWindowImpresion(op,dataItemCotizacion.idPedido,"");
			}
				
		}
	
 }else {
	 if(op==1000){
		 if(dataItemPagoMonitorHead==null){
			 //kendoConsole.log("Seleccione un Pago","warning");
			 $("#mensajes_main").html("Seleccione un pago");
		}else {
			 openWindowImpresion(op,dataItemPagoMonitorHead.fregi,"");
		}
	 } else {
		 if(dataItemPago==null){
			 //kendoConsole.log("Seleccione un Pago","warning");
			 $("#mensajes_main").html("Seleccione un pago");
		}else {
			 openWindowImpresion(op,dataItemPago.fregi,"");
		}
		 
	 }
	
	
	
  }
	 
}	


function openWindowImpresion(action,idPedido,from)
{   
   
	if(from=="pagoIni")
	{
		
		window.parent.parent.$("#mensajes_main").html("Generando reporte");
    	window.parent.parent.$("#visorReporteDiversos").attr('src', contexPath+"/ImpresionContrato.htm?idPedido="+idPedido+"&accion="+action);
	}else {
	
	    if (action==1000){
	    	$("#mensajes_main").html("Generando reporte");
	    	$("#visorReporteComprobanteMonitor").attr('src', contexPath+"/ImpresionContrato.htm?idPedido="+idPedido+"&accion=5");
			
	    }else{
	    	$("#mensajes_main").html("Generando reporte");
	    	$("#visorReporteDiversos").attr('src', contexPath+"/ImpresionContrato.htm?idPedido="+idPedido+"&accion="+action);
				
	    }		  		
		   	   	  
		   
	}
	
	
		
}	  

////////////MODULO PARA ARCHIVERO ELECTRONICO//////////////////////
	
	function openAdminDocs(uts,action)
	{   
		if(action=='up')
			urlAux="http://10.1.5.40/vivienda/script/upload.php?ut="+uts;
		else
			urlAux="http://10.1.5.40/vivienda/script/download.php?ut="+uts;
		
		var onClose = function(e) {
			window.location.reload();
		};
		
		   $("#windowAdminDocs").kendoWindow({
				close: onClose,
				modal: true,
				resizable: false,
				content: 
					{
						//url:"http://10.1.5.40/vivienda/script/upload/index.php?ut="+uts
						url: urlAux
				},
		        height: "400px",
		        title: "Administracion Documentos de la Unidad "+uts,
				width: "800px"
			    });		
		    var windowRegistro= $("#windowAdminDocs").data("kendoWindow");
		    windowRegistro.center();
			windowRegistro.open();
	}



