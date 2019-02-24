//Inicializacion de variables globales 
var visitas_client=[];
var dataSourceClient=null;
var dataSourceVisit=null;
var dataClient=null;
var dataVisit=null;
var auxIdV="";
var windowM;
var windowConfirmx;
var fechaInicial;
var fechaFinal;

//varaibles para los parametros de seleccion 
var mod="";
var dataNombre=[];
var dataNombre2=[];
var dataApPaterno=[];
var dataApMaterno=[];
var xmlNom="";
var xmlNom2="";
var xmlApPat="";
var xmlApMat="";
//variable para el reporte 
var userVendedor = [];
var vendedores_seleccionado=[];
var vendedorSelecReasigna="";
var vendedor = "";
var isVendedorReasignado = false;

//function para agregar, modificar o ver detalle en cartera de clientes
function continuaGuardado(){
	$("#winAvisaClienteMenorEdadCar").data("kendoWindow").close();
	guardaCarteraCliente();
}
function adminCartCliente()
{
	_that=this;
	if($("#fromCartera").val()=="agregaCartera")
	{
		if(validaIngresoModulos('CAT_CLI', 'PERMISO_C')) {
			 if(ValidaRegCli()){
				 guardaCarteraCliente();
				 
			 } 
		} else {
			$('#mensajes_main').html('Sin permisos para este modulo');
		}
	}else if($("#fromCartera").val()=="modificaCartera")
	{
		if(!validaIngresoModulos('CAT_CLI', 'PERMISO_M')) {
			$('#mensajes_main').html('Sin permisos para este modulo');
			return;
		}
		 if(ValidaRegCli()){
				reasignaV=false;
				
				//if($("#ultVendedor").val()!=vendedor) // || parametros != ""
				if (isVendedorReasignado)
					reasignaV=true;
				
				if(reasignaV==true)
				{
					
					openConfirm('confirmaReasignacion');
					
				}
				else{//no reasigno vendedor 
		    		datos = $('#carteraC').serialize()+"&accion=3&reasignaV=false&via_con="+$("#cmb_viaContacto").val()+"&vendedor="+vendedor+"&id_selected="+window.parent.dataItemCar.id_car_cli;
					url_aux=contexPath + "/RegistroCarCliente.htm"; 
				 
					$.ajax( {
						type : "POST",
						url : url_aux,
						data : datos,
						success : function(response) {
							// we have the response
						if (response.respUpClienteDto.mensaje == "SUCCESS") {
							//window.parent.kendoConsole.log("Cliente modificado satisfactoriamente");
							window.parent.$("#windowUpdateCliente").data("kendoWindow").close();    
							window.parent.enviaDatosCarCliente("Cliente modificado satisfactoriamente");
							
						} else {
							//kendoConsole.log("Hubo un error: "+response.respUpClienteDto.descripcion,"error");
						}
					},
					error : function(e) {
						
						//kendoConsole.log(e,"error");
						$("#mensajes_main").html("Error:"+e);
					}
					});  
			}
			
		 }
		
	}
	
}


function guardaCarteraCliente(){

	 if (parametros == "") {
		 parametros = "&idVendedorActual=-1&idVendedorSiguiente=-1&idMotivo=-1";
	 }
	 var datos = $('#carteraC').serialize()+"&accion=2&act_cli= &via_con="+$("#cmb_viaContacto").val()+"&vendedor=" + $("#cmb_vendedor").val() + parametros;
	 url_aux=contexPath + "/RegistroCarCliente.htm";
	 
		$.ajax( {
			type : "POST",
			url : url_aux,
			data : datos ,
			success : function(response) {
				// we have the response
			if (response.respAddClienteDto.mensaje == "SUCCESS") {
				 //kendoConsole.log("Cliente registrado exitosamente "+response.respAddClienteDto.descripcion);
				  $("#mensajes_main").html("Cliente registrado exitosamente "+response.respAddClienteDto.descripcion);
				 //limpiar formulario
				 getCargaInicial("","");
				 $('#nombre1C').val(null);
				 $('#nombre2C').val(null);
				 $('#app_patC').val(null);
				 $('#app_matC').val(null);
				 $('#fch_ncm').val(null);
				 $('#telfnC').val(null);
				 $('#tlfmoC').val(null);
				 $('#telofC').val(null);
				 $('#extncC').val(null);
				 $('#mail1C').val(null);
				 $('#mail2C').val(null);
				 loadMotivos();
				
			} else {
				$("#mensajes_main").html("Hubo un error: "+response.respAddClienteDto.descripcion);
		    }
		},
		error : function(e) {
			$("#mensajes_main").html("Error:"+e);
		}
		});

}

//Funcion para la administracion de cartera de clientes (actualiza, detalle, borra, registra visita , registra tickets)
 function showDetailsCart(e,accion,paramPopup){
	    var gridCar = $("#dg_clientes").data("kendoGrid");
	 
	    
	    dataItemCar = gridCar.dataItem(gridCar.tbody.find("tr.k-state-selected"));
	    
	    if(dataItemCar==null || dataItemCar==undefined || dataItemCar=='')
		{
			 //kendoConsole.log("Seleccione un Registro","warning");
			$("#mensajes_main").html("Seleccione un Registro");
			return;
		}
	    
	    var datos="";
		var url_aux="";
		var k=0;
		_that=this;
		
		
		//dataItemCar = gridCar.dataItem($(e.currentTarget).closest("tr"));
	    
		 switch(accion){
		 case 'openUpCliente':
			 //cuando sea modificacion el tamaño de la ventana se debe reducir
			  openWinUpClient(dataItemCar,dataItemCar.visitasClienteList[0].slsman,'modificaCartera',paramPopup,"200px");
			 break;
		 case 'detCliente':

			   var vistaClienteCar=''; 
			   if (dataItemCar.visitasClienteList.length>0){
				   vistaClienteCar=dataItemCar.visitasClienteList[0].slsman;
			   }
				 openWinUpClient(dataItemCar,vistaClienteCar,'det',paramPopup,"520px");	 
			 
			 break;
		 case 'delCliente':
			 //si el grid de busqueda de clientes no contiene datos 
			 openConfirm('confirmaBorrar'); 
			 break;
		
		 case 'regVisita':
			 var vistaClienteCar='';
			 var cmb_motivos=$("#cmb_motivos").data("kendoDropDownList");
			 cmb_motivos.select(0);
			 if (dataItemCar.visitasClienteList.length>0){
				 vistaClienteCar=dataItemCar.visitasClienteList[0].slsman;
				 //CUANDO ES 01=TIENE VISITAS Y 02='NO TIENE VISITAS VIENE DE OTRO DESARROLLO'
				 asignaVendedorNuevo(vistaClienteCar,'01');
			 } else {
				 //cuando venga de otro desarrollo
				 $("#winAsignaVendedorDesarrollo").data("kendoWindow").open();
				 $("#winAsignaVendedorDesarrollo").data("kendoWindow").center();
			 }
			
			 
			 break;
		 case 'regTicket':
			 openWinTickets(dataItemCar,dataItemCar.visitasClienteList[0].slsman,'addTicket');
		 }
	
}
 

 
 function asignaVendedorNuevo(paramId,paramOrigen){
	 var vendedorActualSelecx='';
	 if (paramOrigen=='01'){
		 vendedorActualSelecx=paramId;
	 }
	 if (paramOrigen=='02'){
		 vendedorActualSelecx=vendedorSelecReasigna;
		 if (vendedorActualSelecx=='' || vendedorActualSelecx==null || vendedorActualSelecx==undefined){
			 $("#mensajes_mainReasigna").html('Seleccione un vendedor');
			 return;
		 }
		 $("#winAsignaVendedorDesarrollo").data("kendoWindow").close();
	 }

	 if (window.parent.parametros == "") {
		 window.parent.parametros = "&idVendedorActual=-1&idVendedorSiguiente=-1&idMotivo=-1";
	 }

	 datos="accion=3"+"&id_selected="+dataItemCar.id_car_cli+"&vendedor="+vendedorActualSelecx + window.parent.parametros;
	 url_aux=contexPath + "/CatalogoCarClientes.htm"; 
	
		$.ajax( {
			type : "POST",
			url : url_aux,
			data : datos,
			success : function(response) {
				// we have the response
			if (response.respAddClienteDto.mensaje == "SUCCESS") {
				//$("#mensajes_main").html("Visita registrada");  
				enviaDatosCarCliente("Visita registrada");
				openAvisaRegistroVisita();
			} else {
				$("#mensajes_main").html("Hubo un error "+response.respAddClienteDto.descripcion,"error");
		    }
		},
		error : function(e) {
			 //kendoConsole.log("Hubo un error "+e,"error");
			 $("#mensajes_main").html("Hubo un error: "+ e);
			}
		});
 }
 
 function cierraWinReasignaVendedor(){
		$("#winAsignaVendedorDesarrollo").data("kendoWindow").close();
} 

 function loadVendedorConsulta() {
	 //alert("HOLA");
	  $.ajax({  
		    type: "POST", 
		    cache : false,
		    async:  false,
		    url: contexPath + "/cliente/reasignacion/ReasignaVendedorFind.htm",  
		    //data: "idFaseReporte=" + idFaseReporte, 
		    success: function(response){
		      if(response.listaMotivos != undefined &&
		         response.mensaje != undefined &&
		    	 response.mensaje == "SUCCESS") {	
				  var cmb_vendedor=$("#cmb_vendedor").data("kendoDropDownList");
				  cmb_vendedor.dataSource.data([]);
				  cmb_vendedor.dataSource.data(response.listaVendedor);	
				  vendedorActualIndex = parseInt(response.index) + 1;
				  totalVendedoresSize = response.listaVendedor.length;
				  //alert ("posicion:" + vendedorActualIndex + ", tamaño:" + response.listaVendedor.length);
				  if ( response.listaVendedor != undefined &&  response.listaVendedor.length > 0) {
					  //++vendedorActualIndex;
					  vendedorActualIndex = (vendedorActualIndex > response.listaVendedor.length? 1:vendedorActualIndex);
					  //$("#cmb_vendedor").attr("disabled","false");
					  $("#cmb_vendedor").data("kendoDropDownList").enable(true);
					  //cmb_vendedor.select(vendedor);
					  i = 0;
					  for (i ; i < totalVendedoresSize ; i++) {
						  if (response.listaVendedor[i].usuario == vendedor) {
							  //alert("usuario:" + response.listaVendedor[i].usuario + ", vendedor:" + vendedor + ", i:" + i);
							  cmb_vendedor.select(i + 1);
						  }
					  }
					  if (i == 0) {
						  cmb_vendedor.select(vendedorActualIndex);
					  }
					  //alert("a:" + vendedor);
					  //vendedor = cmb_vendedor.dataItem().usuario;
 					  $("#cmb_vendedor").data("kendoDropDownList").enable(false);
					  //$("#cmb_vendedor").attr("disabled","true");
					  //alert ("posicion:" + vendedorActualIndex + ", tamaño:" + response.listaVendedor.length);
				  }
		      }else{
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

 function loadMotivos() {
	 //alert("HOLA");
	  $.ajax({  
		    type: "POST", 
		    cache : false,
		    async:  false,
		    url: contexPath + "/cliente/reasignacion/ReasignaVendedorFind.htm",  
		    //data: "idFaseReporte=" + idFaseReporte, 
		    success: function(response){
		      if(response.listaMotivos != undefined &&
		         response.mensaje != undefined &&
		    	 response.mensaje == "SUCCESS") {	
		    	  var cmb_motivos=$("#cmb_motivos").data("kendoDropDownList");
		    	  cmb_motivos.dataSource.data([]);
		    	  cmb_motivos.dataSource.data(response.listaMotivos);	
				  var cmb_vendedor=$("#cmb_vendedor").data("kendoDropDownList");
				  cmb_vendedor.dataSource.data([]);
				  cmb_vendedor.dataSource.data(response.listaVendedor);	
				  vendedorActualIndex = parseInt(response.index) + 1;
				  totalVendedoresSize = response.listaVendedor.length;
				  //alert ("posicion:" + vendedorActualIndex + ", tamaño:" + response.listaVendedor.length);
				  if ( response.listaVendedor != undefined &&  response.listaVendedor.length > 0) {
					  ++vendedorActualIndex;
					  vendedorActualIndex = (vendedorActualIndex > response.listaVendedor.length? 1:vendedorActualIndex);
					  //$("#cmb_vendedor").attr("disabled","false");
					  $("#cmb_vendedor").data("kendoDropDownList").enable(true);
					  cmb_vendedor.select(vendedorActualIndex);					  
 					  $("#cmb_vendedor").data("kendoDropDownList").enable(false);
					  //$("#cmb_vendedor").attr("disabled","true");
					  //alert ("posicion:" + vendedorActualIndex + ", tamaño:" + response.listaVendedor.length);
				  }
		      }else{
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

 function loadMotivosNewVendedor() {
	 //alert("HOLA");
	  $.ajax({  
		    type: "POST", 
		    cache : false,
		    async:  false,
		    url: contexPath + "/cliente/reasignacion/ReasignaVendedorFind.htm",  
		    //data: "idFaseReporte=" + idFaseReporte, 
		    success: function(response){
		      if(response.listaMotivos != undefined &&
		         response.mensaje != undefined &&
		    	 response.mensaje == "SUCCESS") {	
		    	  var cmb_motivos=$("#cmb_motivos").data("kendoDropDownList");
		    	  cmb_motivos.dataSource.data([]);
		    	  cmb_motivos.dataSource.data(response.listaMotivos);	
				  var cmb_vendedor=$("#cmb_vendedorAsignaNew").data("kendoDropDownList");
				  cmb_vendedor.dataSource.data([]);
				  cmb_vendedor.dataSource.data(response.listaVendedor);	
				  vendedorActualIndex = parseInt(response.index) + 1;
				  totalVendedoresSize = response.listaVendedor.length;
				  //alert ("posicion:" + vendedorActualIndex + ", tamaño:" + response.listaVendedor.length);
				  if ( response.listaVendedor != undefined &&  response.listaVendedor.length > 0) {
					  ++vendedorActualIndex;
					  vendedorActualIndex = (vendedorActualIndex > response.listaVendedor.length? 1:vendedorActualIndex);
					  //$("#cmb_vendedor").attr("disabled","false");
					  $("#cmb_vendedorAsignaNew").data("kendoDropDownList").enable(true);
					  cmb_vendedor.select(vendedorActualIndex);	
					  vendedorSelecReasigna = cmb_vendedor.dataItem().usuario;
 					  $("#cmb_vendedorAsignaNew").data("kendoDropDownList").enable(false);
					  //$("#cmb_vendedor").attr("disabled","true");
					  //alert ("posicion:" + vendedorActualIndex + ", tamaño:" + response.listaVendedor.length);
				  }
				  cmb_motivos.select(0);
		      }else{
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

 function loadMotivosModifica() {
	 //alert("HOLA");
	  $.ajax({  
		    type: "POST", 
		    cache : false,
		    async:  false,
		    url: contexPath + "/cliente/reasignacion/ReasignaVendedorFind.htm",  
		    //data: "idFaseReporte=" + idFaseReporte, 
		    success: function(response){
		      if(response.listaMotivos != undefined &&
		         response.mensaje != undefined &&
		    	 response.mensaje == "SUCCESS") {	
		    	  var cmb_motivos=$("#cmb_motivos").data("kendoDropDownList");
		    	  cmb_motivos.dataSource.data([]);
		    	  cmb_motivos.dataSource.data(response.listaMotivos);	
				  var cmb_vendedor=$("#cmb_vendedor").data("kendoDropDownList");
				  cmb_vendedor.dataSource.data([]);
				  cmb_vendedor.dataSource.data(response.listaVendedor);	
				  vendedorActualIndex = parseInt(response.index) + 1;
				  totalVendedoresSize = response.listaVendedor.length;
				  //alert ("posicion:" + vendedorActualIndex + ", tamaño:" + response.listaVendedor.length);
				  if ( response.listaVendedor != undefined &&  response.listaVendedor.length > 0) {
					  //++vendedorActualIndex;
					  vendedorActualIndex = (vendedorActualIndex > response.listaVendedor.length? 1:vendedorActualIndex);
					  //$("#cmb_vendedor").attr("disabled","false");
					  $("#cmb_vendedor").data("kendoDropDownList").enable(true);
					  i = 0;
					  for (i ; i < totalVendedoresSize ; i++) {
						  if (response.listaVendedor[i].usuario == vendedor) {
							  //alert("usuario:" + response.listaVendedor[i].usuario + ", vendedor:" + vendedor + ", i:" + i);
							  cmb_vendedor.select(i + 1);
						  }
					  }
					  if (i == 0) {
						  cmb_vendedor.select(vendedorActualIndex);
					  }
					  
					  //cmb_vendedor.select(vendedor);					  
					  //vendedor = cmb_vendedor.dataItem().usuario;
					  $("#ultVendedor").val(vendedor);
 					  $("#cmb_vendedor").data("kendoDropDownList").enable(false);
					  //$("#cmb_vendedor").attr("disabled","true");
					  //alert ("posicion:" + vendedorActualIndex + ", tamaño:" + response.listaVendedor.length);
				  }
		      }else{
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

 function reasignarVendedor() {
	 var cmb_vendedor = $("#cmb_vendedor").data("kendoDropDownList");
	 var cmb_motivos = $("#cmb_motivos").data("kendoDropDownList");
	 if (cmb_motivos.select() > 0) {
		 //alert ("vendedorActura:" + vendedorActualIndex + ", tamaño:" + totalVendedoresSize);
		 var VendedorReasingado = function (vendedorActual, vendedorSiguiente, motivo) {
			    this.vendedorActual = vendedorActual;
			    this.vendedorSiguiente = vendedorSiguiente;
			    this.motivo = motivo;
		 }

		 
		 var vendedorR = new VendedorReasingado($("#cmb_vendedor").val(), $("#cmb_vendedor").val(), $("#cmb_motivos").val());
		 //alert ("vendedorActualIndex+1:" + (vendedorActualIndex + 1));
		 //alert ("totalVendedoresSize:" + totalVendedoresSize);
		 vendedorActualIndex = ((++vendedorActualIndex) > (totalVendedoresSize)? 1:vendedorActualIndex);
		 //alert ("VENDEDOR:" + vendedorActualIndex);
		 $("#cmb_vendedor").data("kendoDropDownList").enable(true);
		 cmb_vendedor.select(vendedorActualIndex);
		 $("#cmb_vendedor").data("kendoDropDownList").enable(false);
		 vendedorR.vendedorSiguiente = $("#cmb_vendedor").val();
		 cmb_motivos.select(0);
		 vendedor = cmb_vendedor.dataItem().usuario;
		 //alert ($("#ultVendedor").val());
		 //alert(vendedor);
		 listaReasignaVendedor.push(vendedorR);
		 parametros = "";
		 for (i = 0 ; i < listaReasignaVendedor.length ; i++) {
			 parametros += "&idVendedorActual=" + listaReasignaVendedor[i].vendedorActual + "&idVendedorSiguiente=" + listaReasignaVendedor[i].vendedorSiguiente + "&idMotivo=" + listaReasignaVendedor[i].motivo;
		 }
	 } else {
		 alert ("Para reasignar vendedor debe seleccionar un motivo");
		 return false;
	 }	 
 }
 
 function reasignarNewVendedor() {
	 var cmb_vendedor = $("#cmb_vendedorAsignaNew").data("kendoDropDownList");
	 var cmb_motivos = $("#cmb_motivos").data("kendoDropDownList");
	 if (cmb_motivos.select() > 0) {
		 //alert ("vendedorActura:" + vendedorActualIndex + ", tamaño:" + totalVendedoresSize);
		 var VendedorReasingado = function (vendedorActual, vendedorSiguiente, motivo) {
			    this.vendedorActual = vendedorActual;
			    this.vendedorSiguiente = vendedorSiguiente;
			    this.motivo = motivo;
		 }

		 
		 var vendedorR = new VendedorReasingado($("#cmb_vendedorAsignaNew").val(), $("#cmb_vendedorAsignaNew").val(), $("#cmb_motivos").val());
		 //alert ("vendedorActualIndex+1:" + (vendedorActualIndex + 1));
		 //alert ("totalVendedoresSize:" + totalVendedoresSize);
		 vendedorActualIndex = ((++vendedorActualIndex) > (totalVendedoresSize)? 1:vendedorActualIndex);
		 //alert ("VENDEDOR:" + vendedorActualIndex);
		 $("#cmb_vendedorAsignaNew").data("kendoDropDownList").enable(true);
		 cmb_vendedor.select(vendedorActualIndex);
		 vendedorSelecReasigna = cmb_vendedor.dataItem().usuario;
		 $("#cmb_vendedorAsignaNew").data("kendoDropDownList").enable(false);
		 vendedorR.vendedorSiguiente = $("#cmb_vendedorAsignaNew").val();
		 cmb_motivos.select(0);
		 vendedor = cmb_vendedor.dataItem().usuario;
		 //alert ($("#ultVendedor").val());
		 //alert(vendedor);
		 window.parent.listaReasignaVendedor.push(vendedorR);
		 window.parent.parametros = "";
		 for (i = 0 ; i < listaReasignaVendedor.length ; i++) {
			 window.parent.parametros += "&idVendedorActual=" + listaReasignaVendedor[i].vendedorActual + "&idVendedorSiguiente=" + listaReasignaVendedor[i].vendedorSiguiente + "&idMotivo=" + listaReasignaVendedor[i].motivo;
		 }
	 } else {
		 alert ("Para reasignar vendedor debe seleccionar un motivo");
		 return false;
	 }
	 
 }

 
 //funcion para la carga inicial de los combos de via de contacto y vendedores 
 function getCargaInicial(indxVi,indxVe) {
	
	 $.ajax( {
					type : "POST",
					url : contexPath + "/RegistroCarCliente.htm",
					data : "accion=1",
					async:  false,
					success : function(response) {
					//we have the response
					if (response.respGetInfCarCliente.mensaje == "SUCCESS") {
						//si viene de alta o de modificar 
						if(indxVi!='' || indxVe!='')
						{
							for(i=0;i<response.respGetInfCarCliente.objViaContactoList.length;i++)
							{
								if(response.respGetInfCarCliente.objViaContactoList[i].via_con==indxVi)
								{
//									via_con=response.respGetInfCarCliente.objViaContactoList[i].via_con;
									indxVi=i+1;
									break;
								}
							}	
							for(i=0;i<response.respGetUsuarios.objUsuariosList.length;i++)
							{
								if(response.respGetUsuarios.objUsuariosList[i].usuario==indxVe)
								{
									vendedor=response.respGetUsuarios.objUsuariosList[i].usuario;
									indxVe=i+1;
									break;
								}
									
							}		
						}
						else{
							indxVe=0;
							indxVi=0;
						}
						
						var cmb_via=$("#cmb_viaContacto").data("kendoDropDownList");
						cmb_via.dataSource.data([]);
						cmb_via.dataSource.data(response.respGetInfCarCliente.objViaContactoList);
						cmb_via.select(indxVi);
						vendedor = indxVe;
						$("#cmb_vendedor").data("kendoDropDownList").enable(true);
						//var cmb_vendedor = $("#cmb_vendedor").data("kendoDropDownList");
						//cmb_vendedor.dataSource.data([]);
						//cmb_vendedor.dataSource.data(response.respGetUsuarios.objUsuariosList);
						//cmb_vendedor.select(indxVe);
						//$("#cmb_vendedor").data("kendoDropDownList").enable(false);
						//alert ("inicio:" + vendedor);
					} else {
						//kendoConsole.log("Usuario Autenticado","error");
						$("#mensajes_main").html("Usuario autenticado");
					}
				},
				error : function(e) {
					//kendoConsole.log(e,"error");
					$("#mensajes_main").html("Error:"+e);
				}
				});
	}
 
 //funcion para la carga inicial de los combos de via de contacto,vendedores, fases y equipos 
 function getCargaInicialUbi(indxVe) {
	
	 $.ajax( {
					type : "POST",
					url : contexPath + "/CapturaTickets.htm",
					data : "accion=4",
					success : function(response) {
					//we have the response
					if (response.respGetInfCarCliente.mensaje == "SUCCESS") {
						//si viene de alta o de modificar 
						if(indxVe!='')
						{   
							for(i=0;i<response.respGetUsuarios.objUsuariosList.length;i++)
							{
								if(response.respGetUsuarios.objUsuariosList[i].usuario==indxVe)
								{
									vendedor=response.respGetUsuarios.objUsuariosList[i].usuario;
									indxVe=i;
									break;
								}
									
							}		
						}
						
						var cmb_vendedor=$("#cmb_vendedor").data("kendoDropDownList");
						cmb_vendedor.dataSource.data([]);
						cmb_vendedor.dataSource.data(response.respGetUsuarios.objUsuariosList);
						cmb_vendedor.select(indxVe);
						
						var fases = $("#fases").kendoDropDownList({
	                        autoBind: false,
	                        optionLabel: "",
	                        dataTextField: "ubicacionDescripcion",
	                        dataValueField: "ubicacionClave",
	                        dataSource:response.respGetUbicaciones.objUbicacionesList 
	                    }).data("kendoDropDownList");
						
						var equipos = $("#equipos").kendoDropDownList({
	                        autoBind: false,
	                        cascadeFrom: "fases",
	                        optionLabel: "",
	                        dataTextField: "id_equnrx",
	                        dataValueField: "id_ubi_tec_aux",
	                        dataSource:response.respGetUbicaciones.objEquiposList 
	                    }).data("kendoDropDownList");
						
						
						//var fases=$("#fases").data("kendoDropDownList");
						//fases.dataSource.data([]);
						//fases.dataSource.data(response.respGetUbicaciones.objUbicacionesList);
						
						//var equipos=$("#equipos").data("kendoDropDownList");
						//equipos.dataSource.data([]);
						//equipos.dataSource.data(response.respGetUbicaciones.objEquiposList);
						
					} else {
						//kendoConsole.log("Usuario Autenticado","error");
						$("#mensajes_main").html("Usuario autenticado");
					}
				},
				error : function(e) {
					//kendoConsole.log(e,"error");
					$("#mensajes_main").html("Error:"+e);
				}
				});
	}

//funcion para cambiar el index de vendedor una vez seleccionado 
function onSelectVendedor(e) {
	//var dataItemVend =  this.dataItem(e.item.index());
	//alert(dataItemVend.usuario);
	var cmb_vendedor = $("#cmb_vendedor").data("kendoDropDownList");
	vendedor = cmb_vendedor.dataItem().usuario;
	isVendedorReasignado = true;
	//alert("vendedor:" + ", combo" + cmb_vendedor.dataItem().usuario);
	//var dataItemVend =  this.dataItem(e.item.index());
	//vendedor = dataItemVend.usuario;
}


function onSelectNewVendedor(e) {
	//var dataItemVend =  this.dataItem(e.item.index());
	//alert(dataItemVend.usuario);
	var cmb_vendedor = $("#cmb_vendedorAsignaNew").data("kendoDropDownList");
	vendedor = cmb_vendedor.dataItem().usuario;
	vendedorSelecReasigna = cmb_vendedor.dataItem().usuario;
	isVendedorReasignado = true;
	//alert("vendedor:" + ", combo" + cmb_vendedor.dataItem().usuario);
	//var dataItemVend =  this.dataItem(e.item.index());
	//vendedor = dataItemVend.usuario;
}
//funcion para concatenar los para,etros de envio de consulta de clientes y creacion de los data source de los mismos 
function buscarClientes() {
	
	_that=this;
	xmlNom='';
	xmlNom2='';
	xmlApPat='';
	xmlApMat='';
	// get the form values
	if($("#nomTxt").val()=="" && $("#nom2Txt").val()=="" && $("#apPatTxt").val()=="" && $("#apMatTxt").val()=="")
	{
		//kendoConsole.log("Capture criterios de busqueda","error")
		$("#mensajes_main").html("Capture criterios de busqueda");
	}else{
	if($("#nomTxt").val()!="" && this.dataNombre.length==0)
	{
		xmlNom ="<criterios><criterio><nombre>"+$("#nomTxt").val()+"</nombre></criterio></criterios>";
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
	if($("#nom2Txt").val()!="" && this.dataNombre2.length==0)
	{
		xmlNom2 ="<criterios><criterio><nombre2>"+$("#nom2Txt").val()+"</nombre2></criterio></criterios>";
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
	if($("#apPatTxt").val()!="" && this.dataApPaterno.length==0)
	{
		xmlApPat ="<criterios><criterio><apPat>"+$("#apPatTxt").val()+"</apPat></criterio></criterios>";
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
	
	if($("#apMatTxt").val()!="" && this.dataApMaterno.length==0)
	{
		xmlApMat ="<criterios><criterio><apMat>"+$("#apMatTxt").val()+"</apMat></criterio></criterios>";
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
	window.parent.xmlNom=xmlNom;
	window.parent.xmlNom2=xmlNom2;
	window.parent.xmlApPat=xmlApPat;
	window.parent.xmlApMat=xmlApMat;
	window.parent.enviaDatosCarCliente();	
  }
}

// para enviar los xml de consulta en catalogo de cartera de cleintes, dependiendo los criterios captirados 
function enviaDatosCarCliente(paramMensaje)
{
	var mensaje=paramMensaje;
	$.ajax( {
		type : "POST",
		url : contexPath + "/CatalogoCarClientes.htm",
		data : "accion=2&xmlNom=" + xmlNom+"&xmlNom2="+xmlNom2+"&xmlApPat="+xmlApPat+"&xmlApMat="+xmlApMat,
		success : function(response) {
			// we have the response
		if (response.respGetInfCarCliente.mensaje == "SUCCESS") {
			$("#windowSelCarCli").data("kendoWindow").close();  
			if (mensaje!=null && mensaje!='' && mensaje!=undefined){
				$("#mensajes_main").html(mensaje);
			}else{
				$("#mensajes_main").html("Registros encontrados");
			}
			
			$("#visualizaCliente").attr("style", "visibility: visible;width:100px;");
			$("#actualizaCartera").attr("style", "visibility: visible;width:100px;");
			$("#eliminaCartera").attr("style", "visibility: visible;width:100px;");
			$("#registraVisita").attr("style", "visibility: visible;width:100px;");
			
			clientesDataExport = response.respGetInfCarCliente.objClientesList;
			
			dataClient = [];
			for (i = 0; i < clientesDataExport.length; i++) {
				dataClient.push(clientesDataExport[i]);
			}
			
			var dataSourceClienteCar = new kendo.data.DataSource({
		        data: dataClient,	    	        
		        pageSize: 50
		    });

			$("#dg_clientes").empty();
			
			if($("#fromCatalogoCar").val()=="catalogo")
			{
				$("#dg_clientes").kendoGrid( {
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
					},{
						field : "statusx",
						title : "Estatus",
						width: 100
					}, {
						field : "via_conx",
						title : "Via Contacto",
						width: 100
					}]
	            });	
			}else if($("#fromCatalogoCar").val()=="tickets"){
				$("#dg_clientes").kendoGrid( {
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
						width: 100
					}, {
						field : "nombre2",
						title : "Segundo Nombre",
						width: 100
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
					},{
						field : "statusx",
						title : "Estatus",
						width: 100
					}, {
						field : "via_conx",
						title : "Via Contacto",
						width: 100
					},
					{
						  title: "",
						  width:100 ,
						  template: "<input type=\"button\" class=\"k-button\" value=\"Reg Ticket\" onclick=\"showDetailsCart(event,'regTicket')\" />"
					}]
	            });	
				
				
			}
					
		} else {
			//kendoConsole.log("Hubo un error en carga clientes: "+response.respGetInfCarCliente.descripcion ,"error");
			$("#mensajes_main").html("Hubo un error en carga clientes: "+response.respGetInfCarCliente.descripcion);
			$("#dg_clientes").empty();
			gridClientesdg();
		}
	},
	error : function(e) {
		   
		   //kendoConsole.log(e,"error");
		  $("#mensajes_main").html("Hubo un error en carga clientes: "+ e);
	  }
	});

}


function gridClientesdg(){
	var dataSourceClienteCar = new kendo.data.DataSource({
        data: [ ],	    	        
        pageSize: 50
    });
	
	$("#dg_clientes").kendoGrid( {
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
		},{
			field : "statusx",
			title : "Estatus",
			width: 100
		}, {
			field : "via_conx",
			title : "Via Contacto",
			width: 100
		},
		{
			  title: "", 
			  width: 100 ,
			  template: "<input type=\"button\" class=\"k-button\" value=\"Visualizar\" onclick=\"showDetailsCart(event,'detCliente','SI')\" />"
       },
		{
			  title: "", 
			  width: 100 ,
			  template: "<input type=\"button\" class=\"k-button\" value=\"Modificar\" onclick=\"showDetailsCart(event,'openUpCliente','SI')\" />"
       },
		{
			  title: "",
			  width:80 ,
             template: "<input type=\"button\" class=\"k-button\" value=\"Borrar\" onclick=\"showDetailsCart(event,'delCliente')\" />"
		},
		{
			  title: "",
			  width:100 ,
			  template: "<input type=\"button\" class=\"k-button\" value=\"Reg Visita\" onclick=\"showDetailsCart(event,'regVisita')\" />"
		}]
   });
}



//funcion para abrir la ventana de Actualizacion Clientes en modo popup
function openWinUpClient(dataItemCar,idUltVen,opc,paramPopup,paramAlto)
{   
		
	var paramAltoVar=paramAlto;
	if(dataItemCotizacion==null)
	{
			kendoConsole.log("Seleccione un registro","warning");
	}else{
	
	if(opc=='modificaCartera')
	{
		var titulox="Modificacion de Cliente";
		mod="modificaCartera";
		 var windowM =$("#windowUpdateCliente").kendoWindow({
				actions: ["Close"],
				modal: true,
			    resizable: false,
			    height:"295px",
		        title:titulox,
				width: "950px",
			    content: 
					{
						url:contexPath+"/RegistroCarCliente.htm?idC=&idV="+idUltVen+"&from="+mod+"&isPopup="+paramPopup
				}
		        
		    });	

		   windowM = $("#windowUpdateCliente").data("kendoWindow");
		   windowM.center();
		   windowM.open();
	}else if(opc='det')
	{
		var titulo="Detalle del Cliente";
		mod="det";
		 var windowM2 =$("#windowDetalleCliente").kendoWindow({
				actions: ["Close"],
				modal: true,
			    resizable: false,
			    height:"520px",
		        title:titulo,
				width: "950px",
			    content: 
					{
						url:contexPath+"/RegistroCarCliente.htm?idC=&idV="+idUltVen+"&from="+mod+"&isPopup="+paramPopup
				}
		        
		    });	

		   windowM2 = $("#windowDetalleCliente").data("kendoWindow");
		   windowM2.center();
		   windowM2.open();
	}


   
	
}
}

//funcion que llena el grid de las visitas para cuando un cleinte es consultado
function llenaVisitas()
{
	var dataSourceVisitasCliente = new kendo.data.DataSource({
        data: window.parent.dataItemCar.visitasClienteList,	    	        
        pageSize: 50
    });
	
	$("#gridVisitas").empty();
	$("#gridVisitas").kendoGrid({
    	dataSource: dataSourceVisitasCliente,//window.parent.dataItemCar.visitasClienteList,
        scrollable:{
			virtual:true
		},
		height: 240,
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
			field : "id_visita",
			title : "Id Visita"
		}, {
			field : "statusx",
			title : "Estatus"
		}, {
			field : "slsman",
			title : "Id Vendedor"
		}, {
			field : "slsman_nombre1",
			title : "Nombre"
		}, {
			field : "slsman_app_pat",
			title : "Apellido"
		}, {
			field : "slsman_app_mat",
			title : "Segundo Apellido"
		}, {
			field : "aedat_vis",
			title : "Fecha Visita"
		}, {
			field : "cputm_vis",
			title : "Hora Visita"
		} ]
    });
	
}

//funcion para abrir la ventana de Captura de Tickets en modo pickets popup
function openWinTickets(dataItemCar,idUltVen,opc)
{   
	
		var titulo="Captura de Tickets";
		
	windowM =$("#windowCapturaTickets").kendoWindow({
			actions: ["Close"],
			modal: true,
			resizable: false,
			content: 
				{
					url:contexPath+"/CapturaTickets.htm?idC="+dataItemCar.id_car_cli+"&idV="+idUltVen+"&fromT=addTicket"
			},
	        height: "500px",
	        title:titulo,
			width: "650px"
		    });	
		   windowM = $("#windowCapturaTickets").data("kendoWindow");
		   windowM.center();
		   windowM.open();
	
}

//funcion para cancelar la operacion de Actualiza
function cancelarCart(win)
{
	_that=this;
	if($("#fromCartera").val()=='agregaCartera')
	{	
		var sp_main = window.parent.$("#sp_escritorio").data("kendoSplitter");
		 sp_main.ajaxRequest("#main_paine", contexPath+'/BlankScreen.htm', { id: 42 });
	} 
	
	if($("#fromCartera").val()=='modificaCartera')
	{		
	window.parent.$("#windowUpdateCliente").data("kendoWindow").close();	
	window.parent.enviaDatosCarCliente();
	}
	
	if(win=='windowConfirm')
	{
	window.parent.$("#windowConfirmaReasignacion").data("kendoWindow").close(); 
//	window.parent.enviaDatosCarCliente(_that);
	}
	
	if(win=='autorizacion')
	{	
		window.parent.$("#windowConfirmaReasignacion").data("kendoWindow").close();
	}
	
	if(win=='windowConfirmBorrado')
	{	
		window.parent.$("#windowConfirmBorrado").data("kendoWindow").close();
	}
}






//funcion que valida los campor capturados para la alta de un nuevo cliente 
function ValidaRegCli(accionAdmin)
{
var datepicker = $("#fch_ncm").data("kendoDatePicker");
var setValue = function () {
     datepicker.value($("#value").val());
};
var resp=false;
var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	
	if($.trim($('#nombre1C').val())=="")
	{    		
		//kendoConsole.log("Debe introducir Nombre Cliente","warning");
		$("#mensajes_main").html("Debe introducir Nombre Cliente");
		$('#nombre1C').focus();
	}
	else if($.trim($('#app_patC').val())=="")
	{
		//kendoConsole.log("Debe introducir Apellido","warning");
		$("#mensajes_main").html("Debe introducir Apellido");
		$('#app_patC').focus();
	}
	else if($.trim($('#app_matC').val())=="")
	{
		//kendoConsole.log("Debe introducir Segundo","warning");
		$("#mensajes_main").html("Debe introducir Segundo");
		$('#app_matC').focus();
	}
	else if($.trim($("#fch_ncm").val())=="")
	{
		//kendoConsole.log("Debe introducir Fecha Nacimiento","warning");
		$("#mensajes_main").html("Debe introducir Fecha Nacimiento");
		$('#fch_ncm').focus();
	}else if(datepicker.value()==null)
	{
		//kendoConsole.log("Capture fecha válida o seleccionela","warning");
		$("#mensajes_main").html("Capture fecha válida o seleccionela");
		$('#fch_ncm').focus();
	}
	else if($.trim($("#telfnC").val())!="" && $.trim($("#telfnC").val().length)<10)
	{
		//kendoConsole.log("Capture telefono de 10 digitos","warning");
		$("#mensajes_main").html("Capture telefono de 10 digitos");
		$('#telfnC').focus();
	}else if($.trim($("#tlfmoC").val())!="" && $.trim($("#tlfmoC").val().length)<10)
	{
		//kendoConsole.log("Capture telefono de 10 digitos","warning");
		$("#mensajes_main").html("Capture telefono de 10 digitos");
		$('#tlfmoC').focus();
	}else if($.trim($("#telofC").val())!="" && $.trim($("#telofC").val().length)<10)
	{
		//kendoConsole.log("Capture telefono de 10 digitos","warning");
		$("#mensajes_main").html("Capture telefono de 10 digitos");
		$('#telofC').focus();
	}
	else if($.trim($("#mail1C").val())!="" && !emailReg.test($.trim($("#mail1C").val())))
	{
		//kendoConsole.log("Capture correo valido","warning");
		$("#mensajes_main").html("Capture correo valido");
		$('#mail1C').focus();
	}else if($.trim($("#mail2C").val())!="" && !emailReg.test($.trim($("#mail2C").val())))
	{
		//kendoConsole.log("Capture correo valido","warning");
		$("#mensajes_main").html("Capture correo valido");
		$('#mail2C').focus();
	}else if($("#cmb_viaContacto").val()==0)
	{
		//kendoConsole.log("Seleccione via de contacto","warning");
		$("#mensajes_main").html("Seleccione via de contacto");
	}
	else if($("#cmb_vendedor").val()=="")
	{
		//kendoConsole.log("Seleccione vendedor","warning");
		$("#mensajes_main").html("Seleccione vendedor");
		return false;
	} 
	var fechaNacc=$("#fch_ncm").val();
	if (!validaClienteMayorEdadCar(fechaNacc)){
		$("#AnyoActualCliente").html("El cliente es menor de edad(edad minima de 18 años).");
		$("#winAvisaClienteMenorEdadCar").data("kendoWindow").center();
		$("#winAvisaClienteMenorEdadCar").data("kendoWindow").open();
		return false;
	} else {
		resp=true;
	}
	
	
	return resp;
}


function cerrarVentanaAvisoMenorEdadx(){
	$("#winAvisaClienteMenorEdadCar").data("kendoWindow").close();
}
function validaClienteMayorEdadCar(paramFechaNacimiento){
	var fechaNacimiento=paramFechaNacimiento;
	
	
	var birthday = new Date(fechaNacimiento);
	var today = new Date();
	var years = today.getFullYear() - birthday.getFullYear();

	// Reset birthday to the current year.
	birthday.setFullYear(today.getFullYear());

	// If the user's birthday has not occurred yet this year, subtract 1.
	if (today < birthday)
	{
	    years--;
	}
	
    
    if (parseInt(years)<18){
    	return false;
    } else {
    	return true;
    }
    	
	
}


//funcion para la consulta de vendedores para los reportes 
function getVendedoresForReportes()
{
	$.ajax({
		type : "POST",
		url : contexPath + "/ReporteClientes.htm",
		data : "accion=getVendedores",
		success : function(response) {
			// we have the response
		
		if (response.resGetClientesDTO.mensaje == "SUCCESS") {	
			$("#dg_usuarios_v").empty();
			$("#dg_usuarios_v").kendoGrid({
		    	dataSource: {
		                      data: response.resGetClientesDTO.objUsuariosList
		                    },
		    	columns: [   
		    				  {
								  field: "",
								  width: 30,
								  title: "",
								  template: '<input type="checkbox" onClick="asignaUsuarioVenta(this,\'${ usuario }\',\'${ id_ut_sup }\')" />'
							  },                         
		                      {
		                          field: "usuario",
		                          width: 80,
		                          title: "Vendedor"
		                      },
		                      {
		                          field: "id_ut_sup",
		                          width: 180,
		                          title: "Unidad Tecnica"
		                      }
		                   ],					
			  	height: 140,
			  	selectable : "row",
			  	sortable: true		
		 	});	
		} else {
			kendoConsole.log(response.resGetClientesDTO.descripcion,"error");
		}
	},
	error: function(jqXHR, textStatus, errorThrown) { 
		  console.log(textStatus, errorThrown); 
		  kendoConsole.log(errorThrown,"error");
	}
	});
}


//funcion que agrega clientes para los reportes de los mismos 
function addClientes()
{
	var selectItemVendedores = $("#cmb_usuarios").data("kendoDropDownList");
	var itemVendedor = selectItemVendedores.dataItem();
	
	if(userVendedor.length==0)
	{
		userVendedor.push({id_ut_sup_cm: itemVendedor.id_ut_sup, usuario_cm: itemVendedor.usuario});
	}
	else
	{
		var existeUsuario = false;
		
		for(var i=0; i<userVendedor.length; i++)
		{
			if(userVendedor[i].usuario_cm==itemVendedor.usuario)
			{
				existeUsuario=true;
				break;
			}
		}
		
		if(!existeUsuario)
		{
			userVendedor.push({id_ut_sup_cm: itemVendedor.id_ut_sup, usuario_cm: itemVendedor.usuario});
		}
		else
		{
			// winAlert("Alert", "El usuario ya existe en la lista, seleccione otro", 100, 200);
			//kendoConsole.log("El usuario ya existe en la lista, seleccione otro","error");
			$("#mensajes_main").html("El usuario ya existe en la lista, seleccione otro");
		}
	}
	
	$("#dg_usuarios").empty();
	
	$("#dg_usuarios").kendoGrid({
    	columns: [                            
                      {
                          field: "id_ut_sup_cm",
                          width: 60,
                          title: "Unidad tecnica"
                      },
                      {
                          field: "usuario_cm",
                          title: "Usuario"
                      }
                  ],
         dataSource : userVendedor,
  	height: 200,
  	selectable : "row",
  	filterable: true,
  	sortable: true,				  
    pageable: true		  
}); 
}
/**
 * Metodos para la creacion del reporte  
 * 
 * Fecha de creación: XX/07/2012               
 */
//Funcion que ejecuta la busqueda y generacion de los reportes a consultar 
function getClientesForReportes()
{
	var fecha_fin = "";
	var fecha_ini = "";
	var listaUsuarios = "";
	
	var fi = $('#fechaInicial').val().split("/");
	var ff = $('#fechaFinal').val().split("/")
	var rtipo = $('#rdo_ca').val();
	
	fecha_ini = fi[2]+fi[1]+fi[0];
	fecha_fin = ff[2]+ff[1]+ff[0];
	
	var vendedores = [];

	for(var i=0; i<userVendedor.length; i++)
	{
		var params = {};
		params["id_ut_sup_cm"] = userVendedor[i].id_ut_sup;
		params["usuario_cm"] = userVendedor[i].usuario;
		vendedores.push(params);
	}
	   
	xmlVendedores = createXML("vendedores",vendedores);
	
	$.ajax({
		type : "POST",
		url : contexPath + "/ReporteClientes.htm",
		data : "fecha_vis_b=" + fecha_ini + "&fecha_vis_e=" + fecha_fin	+"&strListaVendedores="+ xmlVendedores +"&accion=getReporteClientes",
		
		success : function(response) {
		if (response.resultReporte.mensaje == "SUCCESS") { 
				window.parent.$("#mensajes_main").html("Se encontraron "+response.resultReporte.objListaReporteUsuarios.length+" registros");
			    
				for(var i=0; i<response.resultReporte.objListaReporteUsuarios.length; i++)
				{
					var f1=response.resultReporte.objListaReporteUsuarios[i].vis_aedat_vis.split("-");
					response.resultReporte.objListaReporteUsuarios[i].vis_aedat_vis = f1[2]+"/"+f1[1]+"/"+f1[0];
					
					var f2=response.resultReporte.objListaReporteUsuarios[i].aedat_reas.split("-");
					response.resultReporte.objListaReporteUsuarios[i].aedat_reas = f1[2]+"/"+f1[1]+"/"+f1[0];
				}
				
				$("#dg_vendedores").empty();
			    var dataSourceVendedores = new kendo.data.DataSource({
			        data: response.resultReporte.objListaReporteUsuarios,	    	        
			        pageSize: 50
			    });
			    $("#dg_vendedores").kendoGrid({
			    	dataSource: dataSourceVendedores,
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
			    	columns: [                            
		                          {
		                              field: "slsman",
		                              width: 80,
		                              title: "Vendedor"
		                          },
		                          {
		                              field: "slsman_nombre1",
		                              width: 180,
		                              title: "Nombre"
		                          },
		                          {
		                              field: "slsman_app_pat",
		                              width: 150,
		                              title: "A.Paterno"
		                          },
		                          {
		                              field: "slsman_app_mat",
		                              width: 150,
		                              title: "A.Materno"
		                          },
		                          {
		                              field: "id_car_cli",
		                              width: 100,
		                              title: "Cliente"
		                          },
		                          {
		                              field: "car_cli_nombre1",
		                              width: 180,
		                              title: "Nombre"
		                          },
		                          {
		                              field: "car_cli_app_pat",
		                              width: 150,
		                              title: "A.Paterno"
		                          },
		                          {
		                              field: "car_cli_app_mat",
		                              width: 150,
		                              title: "A.Materno"
		                          },
		                          {
		                              field: "vis_aedat_vis",
		                              width: 80,
		                              title: "Fecha Vis"
		                              
		                          },
		                          {
		                              field: "num_visitas",
		                              width: 60,
		                              title: "Num Vis"
		                          },
		                          {
		                              field: "slsman_reas",
		                              width: 120,
		                              title: "Vendedor Reasignado"
		                          },
		                          {
		                              field: "aedat_reas",
		                              width: 120,
		                              title: "Fecha Reasignacion"
		                          }
		                      ],					
			  	selectable : "row"  
			}); 
			    window.parent.closeWinVendedores();
		} else {
			window.parent.$("#mensajes_main").html(response.resultReporte.descripcion);
		}
	},
	error: function(jqXHR, textStatus, errorThrown) {  
		window.parent.$("#mensajes_main").html(errorThrown);
	}
	}); 
}


//funcion para crear el xml 
function createXML( root, vend )
{
  var nodes;
  var xml = "";
  if( root )
    xml += "<" + root + ">";
  for(var i=0; i<vend.length; i++)
  {
	  nodes=vend[i];
	  xml += "<vendedor>";
	  for( theNode in nodes )
	  {
	    xml += "<" + theNode + ">" + nodes[theNode] + "</" + theNode + ">";
	  }	
	  xml += "</vendedor>";
  }  
  xml += "</" + root + ">";
  
  return xml;
}

//funcion para establecer la fecha de inicio 
function startChange() {
    var startDate = fechaInicial.value();

    if (startDate) {
        startDate = new Date(startDate);
        startDate.setDate(startDate.getDate() + 1);
        fechaFinal.min(startDate);
    }
}

//funcion para deshabilitar la fecha de fin dependiendo de la fecha de inicio 
function endChange() {
    var endDate = fechaFinal.value();

    if (endDate) {
        endDate = new Date(endDate);
        endDate.setDate(endDate.getDate() - 1);
        fechaInicial.max(endDate);
    }
}

function validaFormaReporte()
{
	var fi = $('#fechaInicial').val();
	var ff = $('#fechaFinal').val();
	
	
    if(fi == "")
    {
    	//kendoConsole.log("Seleccione una fecha inicial","warning");
    	$("#mensajes_main").html("Seleccione una fecha inicial");
    }
    else if(ff == "")    
    {
    	//kendoConsole.log("Seleccione una fecha final","warning");
    	$("#mensajes_main").html("Seleccione una fecha final");
    }
    else if(userVendedor.length==0)
    {
    	//kendoConsole.log("Debe de agregar a la lista al menos un vendedor","warning");
    	$("#mensajes_main").html("Debe de agregar a la lista al menos un vendedor");
    	// var window = $("#winListaUsuarios").data("kendoWindow");
 	    //window.center();
 	    //window.open();
 	    //getVendedoresForReportes();
    }
    else
    {
    	getClientesForReportes();
    }
}

//funcion para abrir la ventana de Actualizacion Clientes en modo popup
function openConfirm(acc)
{   
		if(acc=="confirmaBorrar")
		{
			urlAux=contexPath+"/Confirmacion.htm?id="+dataItemCar.id_car_cli+"&act="+acc;
			
			var windowConfirm =$("#windowConfirmBorrado").kendoWindow({
				actions: ["Close"],
				modal: true,
				resizable: false,
				content: 
				{
					url:urlAux
			    },
	            height: "150px",
		        title: "Confirmacion",
				width: "400px"
			    });
			windowConfirm = $("#windowConfirmBorrado").data("kendoWindow");
			windowConfirm.center();
			windowConfirm.open();
			
		}else if (acc=="confirmaReasignacion")
		{
			urlAux=contexPath+"/ConfirmacionReasignacion.htm?id="+window.parent.dataItemCar.id_car_cli+"&act="+acc;
			var windowConfirmaReasignacionVen =$("#windowConfirmaReasignacion").kendoWindow({
				actions: ["Close"],
				modal: true,
				resizable: false,
				content: 
				{
					url:urlAux
			    },
	            height: "158px",
		        title: "Confirmacion",
				width: "400px"
			    }).data("kendoWindow");
			
			windowConfirmaReasignacionVen.center();
			windowConfirmaReasignacionVen.refresh();
			windowConfirmaReasignacionVen.open();
		}
		
		
		
	
		
}
//funcion del aceptar cuando coonfirma la operacion de eliminar 
 function confirma()
 {
	 _that=this;
//	 if($("#accion").val()==)
	 datos="id_selected="+window.parent.dataItemCar.id_car_cli;
	 url_aux=contexPath + "/Confirmacion.htm"; 
	
		$.ajax( {
			type : "POST",
			url : url_aux,
			data : datos,
			success : function(response) {
				// we have the response
			if (response.respDelClienteDto.mensaje == "SUCCESS") {
				//window.parent.kendoConsole.log("Cliente eliminado satisfactoriamente");
				window.parent.$("#windowConfirm").data("kendoWindow").close();    
				window.parent.enviaDatosCarCliente("Cliente eliminado satisfactoriamente");
				
			} else {
				//kendoConsole.log("Hubo un error "+response.respDelClienteDto.descripcion,"error");
				window.parent.$("#windowConfirm").data("kendoWindow").close();	
				window.parent.enviaDatosCarCliente("Hubo un error "+response.respDelClienteDto.descripcion);
			
		    }
		},
		error : function(e) {
			 //kendoConsole.log("Hubo un error "+e,"error");
			$("#mensajes_main").html("Hubo un error "+e)
			}
		});	 
	 
 }
//funcion que realiza la operacion de reasignacion de cliente 
function validaAutorizacion()
{
	
	_that=this;
	
	if($("#usr").val()!="" && $("#pwd").val()!="")
	{
		 if (window.parent.parametros == "") {
			 window.parent.parametros = "&idVendedorActual=-1&idVendedorSiguiente=-1&idMotivo=-1";
		 }
		$.ajax({  
		    type: "POST",  
		    url: contexPath + "/RegistroCarCliente.htm",  
		    data: "accion=5&usrAdm=" + $("#usr").val()+ "&pwdAdm=" + $("#pwd").val(), 
		    success: function(response){	      
			  // we have the response 		  
		      if(response.respValidaLogin.mensaje == "SUCCESS"){	    	  
		    	 
		    	    window.parent.$("#windowConfirmaReasignacion").data("kendoWindow").close();
		    		datos = window.parent.$('#carteraC').serialize()+"&accion=3&reasignaV=true&id_selected="+window.parent.parent.dataItemCar.id_car_cli+"&usrAdm="+$("#usr").val()+"&via_con="+window.parent.$("#cmb_viaContacto").val()+"&vendedor="+window.parent.vendedor + window.parent.parametros;
					url_aux=contexPath + "/RegistroCarCliente.htm"; 
				 
					$.ajax( {
						type : "POST",
						url : url_aux,
						data : datos,
						success : function(response) {
							// we have the response
						if (response.respUpClienteDto.mensaje == "SUCCESS") {
//							auxIdV=window.parent.$("#cmb_vendedor").data("kendoDropDownList").dataItem().usuario;
							window.parent.parent.$("#windowUpdateCliente").data("kendoWindow").close();
							//kendoConsole.log("Reasignación de vendedor satisfactoria");
							window.parent.parent.enviaDatosCarCliente("Reasignación de vendedor satisfactoria");
							
						} else {
							window.parent.$("#mensajes_main2").html("Hubo un error: "+response.respUpClienteDto.descripcion);
						}
					},
					error : function(e) {
						window.parent.$("#mensajes_main2").html("Error:"+e);
					}
					});  
		    	  
		      }else{	
		    	  window.parent.$("#mensajes_main2").html(response.respValidaLogin.descripcion);
		    	  cancelarCart('windowConfirm');
		    	  
		    	  
		      }	      
		    },  
		    error: function(e){
		    	//kendoConsole.log(e,"error");
		    	window.parent.$("#mensajes_main2").html("Error:"+e);
		    }  
		  }); 	

	}
	else{
		
		alert("Capture informaciónn completa");
	}
}

/////////////////////Funcionalidad para los parametros de seleccion

//funcion para abrir parametros de seleccion 
function openSelecCarCli()
{   
	var windowSelecCarCli =$("#windowSelCarCli").kendoWindow({
			actions: ["Close"],
			modal: true,
			resizable: false,
			content: 
				{
					url:contexPath+"/SeleccionCarCli.htm"
			},
	        height: "300px",
	        title: "Parametros de Selección",
			width: "390px"
		    });	
	windowSelecCarCli = $("#windowSelCarCli").data("kendoWindow");
	windowSelecCarCli.center();
	windowSelecCarCli.open();
}
//funcion para abrir parametros de seleccion 
function openParamCarCli(grid)
{   
	$("#windowParamsCarCli").empty();
	var windowparamCarCli =$("#windowParamsCarCli").kendoWindow({
			actions: ["Close"],
			modal: true,
			resizable: false,
			content: 
				{
					url:contexPath+"/ParamsCarCli.htm?queGrid="+grid
			},
	        height: "210px",
	     	width: "350px"
		    }).data("kendoWindow");	
			
			windowparamCarCli.center(); //prueba
			windowparamCarCli.refresh();
			windowparamCarCli.open();  //prueba
}

//funcion que carga el grid correspondiente 
function cargaGrid(opc)
{
switch (opc) {
case 'Nombre':
	$("#nom").attr("style", "visibility: visible;");
	if(window.parent.dataNombre.length>0)
	{
		var arrDatos=[];
		for (i=0; i<window.parent.dataNombre.length; i++){
			var datos={
				nombre:	window.parent.dataNombre[i].nombre
			};
			
			arrDatos.push(datos);
		}

		var dataSourceNom= new kendo.data.DataSource({
	        batch: true,
	        data:arrDatos, //window.parent.dataNombre,
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
	$("#gridNom").empty();
	$("#gridNom").kendoGrid({
        dataSource: dataSourceNom,
        resizable: true,
        height:150,
        toolbar: [{name:"create",text:"Agregar registro"}],
        columns: [
			{
				field: "nombre", title:"Nombres" , width: "130px",template:"#= nombre.toUpperCase() #"},
			{ command: "destroy", title: " ", width: "80px" 
				
		}],
         editable:{
		   update: true,
		   destroy:true, // does not remove the row when it is deleted, but marks it for deletion
           confirmation: "¿Esta seguro de eliminar esta fila?"
           
	    }
    });
	var grid = $("#gridNom").data("kendoGrid");
	grid.refresh();
	
	break;
case 'SegundoNombre':
	//document.getElementById("nom2").setAttribute("style","visibility:visible");
	$("#nom2").attr("style", "visibility: visible;");
	if(window.parent.dataNombre2.length>0)
	{
		var arrDatos=[];
		for (i=0; i<window.parent.dataNombre2.length; i++){
			var datos={
				nombre2:window.parent.dataNombre2[i].nombre2
			};
			
			arrDatos.push(datos);
		}

		var dataSourceNom2= new kendo.data.DataSource({
	        batch: true,
	        data: arrDatos, //window.parent.dataNombre2,
	        schema: {
	            model: {
	              fields: {
	            	  "class": "myClass",
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
	$("#gridNom2").empty();
	$("#gridNom2").kendoGrid({
        dataSource: dataSourceNom2,
        resizable: true,
        //pageable: true,
        height:150,
        toolbar: [{name:"create",text:"Agregar registro"}],
        columns: [
			{ field: "nombre2", title:"Segundos Nombres" , width: "130px",template:"#= nombre2.toUpperCase() #"},
			{ command: "destroy", title: " ", width: "80px" }],
		editable:{
				   update: true,
				   destroy:true, // does not remove the row when it is deleted, but marks it for deletion
		           confirmation: "¿Esta seguro de eliminar esta fila?"
			    }
    });
	break;
case 'ApellidoPaterno':
	//document.getElementById("apPat").setAttribute("style","visibility:visible");
	$("#apPat").attr("style", "visibility: visible;");
	if(window.parent.dataApPaterno.length>0)
	{
		var arrDatos=[];
		for (i=0; i<window.parent.dataApPaterno.length; i++){
			var datos={
				apPat:window.parent.dataApPaterno[i].apPat
			};
			
			arrDatos.push(datos);
		}
		var dataSourceApPat= new kendo.data.DataSource({
	        batch: true,
	        data: arrDatos,//window.parent.dataApPaterno,
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
	$("#gridApPat").empty();
	$("#gridApPat").kendoGrid({
        dataSource: dataSourceApPat,
        resizable: true,
        //pageable: true,
        height:150,
        toolbar: [{name:"create",text:"Agregar registro"}],
        columns: [
			{ field: "apPat", title:"Apellidos Paternos" , width: "130px",template:"#= apPat.toUpperCase() #"},
			{ command: "destroy", title: " ", width: "80px" }],
			editable:{
				   update: true,
				   destroy:true, // does not remove the row when it is deleted, but marks it for deletion
		           confirmation: "¿Esta seguro de eliminar esta fila?"
			    }
    });
	
	break;
case 'ApellidoMaterno':
	//document.getElementById("apMat").setAttribute("style","visibility:visible");
	$("#apMat").attr("style", "visibility: visible;");
	if(window.parent.dataApMaterno.length>0)
	{
		var arrDatos=[];
		for (i=0; i<window.parent.dataApMaterno.length; i++){
			var datos={
					apMat:window.parent.dataApMaterno[i].apMat
			};
			
			arrDatos.push(datos);
		}
		var dataSourceApMat= new kendo.data.DataSource({
	        batch: true,
	        data: arrDatos,//window.parent.dataApMaterno,
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
	$("#gridApMat").empty();
	$("#gridApMat").kendoGrid({
        dataSource: dataSourceApMat,
        resizable: true,
        //pageable: true,
        height:150,
        toolbar: [{name:"create",text:"Agregar registro"}],
        columns: [
			{ field: "apMat", title:"Apellidos Maternos" , width: "130px",template:"#= apMat.toUpperCase() #"},
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
//funcion para aceptar Params de Seleccion, crear arreglo  
function aceptarParams(opc)
{
switch (opc) {
case 'Nombre':
	
	var gridNom =$("#gridNom").data("kendoGrid");
	window.parent.dataNombre = gridNom.dataSource.data();
	if(window.parent.dataNombre.length>0)
	{
	   window.parent.$("#nomTxt").val(window.parent.dataNombre[0].nombre);
	}	
	window.parent.$("#windowParamsCarCli").data("kendoWindow").close();
//	alert("HOLA");
	break;
case 'SegundoNombre':
	var gridNom2 = $("#gridNom2").data("kendoGrid");
	window.parent.dataNombre2 = gridNom2.dataSource.data();
	if(window.parent.dataNombre2.length>0)
	{
	   window.parent.$("#nom2Txt").val(window.parent.dataNombre2[0].nombre2);
	}
	window.parent.$("#windowParamsCarCli").data("kendoWindow").close();
	break;
case 'ApellidoPaterno':
	var gridApPat = $("#gridApPat").data("kendoGrid");
	window.parent.dataApPaterno = gridApPat.dataSource.data();
	if(window.parent.dataApPaterno.length>0)
	{
	   window.parent.$("#apPatTxt").val(window.parent.dataApPaterno[0].apPat);
	}
	window.parent.$("#windowParamsCarCli").data("kendoWindow").close();
	break;
case 'ApellidoMaterno':
	var gridApMat = $("#gridApMat").data("kendoGrid");
	window.parent.dataApMaterno = gridApMat.dataSource.data();
	if(window.parent.dataApMaterno.length>0)
	{
	   window.parent.$("#apMatTxt").val(window.parent.dataApMaterno[0].apMat);
	}
	window.parent.$("#windowParamsCarCli").data("kendoWindow").close();
	break;
default:
	break;
}
//window.parent.$("#windowParamsCarCli").data("kendoWindow").close();
}
//funcion que cierra ventana de  seleccion  Car Cliente 
function cancelarSelec()
{
	window.parent.$("#windowSelCarCli").data("kendoWindow").close();
}



//funcion para cancelar Params de Seleccion 
function cancelarParams()
{
	window.parent.$("#windowParamsCarCli").data("kendoWindow").close();
}

function validaEnter(e)
{
if (window.event) {keyval=e.keyCode}
else
if (e.which) {keyval=e.which}

if (keyval=="13" &&  $.trim($('#pwd').val())=="") {validaAutorizacion();}
else
if (keyval=="13" &&  $.trim($('#pwd').val())!="") {validaAutorizacion();}
} 

function asignaUsuarioVenta(obj_chk_selec, user_, uts_)
{
	if(obj_chk_selec.checked)
	{
		vendedores_seleccionado.push({usuario:user_, id_ut_sup:uts_});
	}
	else
	{
		for(var i=0; i<vendedores_seleccionado.length; i++)
		{
			if(vendedores_seleccionado[i].usuario_cm==user_)
			{
				vendedores_seleccionado.splice(i, 1);
			}
		}
	}
	userVendedor=vendedores_seleccionado;
}


function openAvisaRegistroVisita()
{   
		
		var urlAux=contexPath+"/AvisaRegistroVisita.htm";
		
		
	
		windowConfirmx =$("#avisaRegistroVisita").kendoWindow({
			actions: ["Close"],
			modal: true,
			resizable: false,
			content: 
			{
				url:urlAux
		    },
            height: "120px",
	        title: "Aviso de registro de visita",
			width: "400px"
		    });
		windowConfirmx = $("#avisaRegistroVisita").data("kendoWindow");
		windowConfirmx.center();
		windowConfirmx.open();
}

function cerrarAvisoVisita(){
	window.parent.$("#avisaRegistroVisita").data("kendoWindow").close();
}

function getCargaInicialReasignaVendedor() {
	
	 $.ajax( {
					type : "POST",
					url : contexPath + "/RegistroCarCliente.htm",
					data : "accion=1",
					success : function(response) {
					//we have the response
					if (response.respGetInfCarCliente.mensaje == "SUCCESS") {
						
						
						var cmb_vendedor=$("#cmb_vendedorAsignaNew").data("kendoDropDownList");
						cmb_vendedor.dataSource.data([]);
						cmb_vendedor.dataSource.data(response.respGetUsuarios.objUsuariosList);
						
						
					} else {
						//kendoConsole.log("Usuario Autenticado","error");
						$("#mensajes_main").html("Error al cargar los datos de vendedor");
					}
				},
				error : function(e) {
					//kendoConsole.log(e,"error");
					$("#mensajes_main").html("Error:"+e);
				}
				});
	}

function onSelectVendedorReasigna(e) {
	var dataItemVend = this.dataItem(e.item.index());
	vendedorSelecReasigna = dataItemVend.usuario;
}