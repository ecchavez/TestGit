/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 20/07/2012       
 * 
 *  JS para la creacion y validacion de registro de cliente 
 */
//funcion para abrir la ventana de Actualizacion Clientes en modo popup

var dataItem=null;
var dataGridCliSource;

var intentos=0;
var dataCliZ=[];
var dataCliSap=[];
var dataNombre=[];
var dataNombre2=[];
var dataApPaterno=[];
var dataApMaterno=[];
var xmlCliZ="";
var xmlCliSap="";
var xmlNom="";
var xmlNom2="";
var xmlApPat="";
var xmlApMat="";
var objetoBusqueda = new Object();
var continuarConCambiosCartera=0;


var img_ok=contexPath+"/images/images/accept.gif";
var img_no=contexPath+"/images/images/delete.gif";
var img_stop=contexPath+"/images/images/bullet_orange.gif";
var img_proc=contexPath+"/images/loader/ajax-proces.gif";

var dataItemCotizacion=[];
var dataItemClienteSapRegistro=null;
var idCotizacionCierreVenta;
var idFregiReportePagoInicial;
var isRegistroTipoPago;

var idPedidoGenerado='';
var idClienteGenerado='';
var arrNombre1='';
var arrNombre2='';
var arrApellido1='';
var arrApellido2='';

var clienteVisual=[];

var onClose = function() {
    
}

//funcion para la carga inicial de los catalogos 
function cargaInit(paramCliente){
	
	$.ajax( {
		type : "POST",
		url : contexPath + "/RegistroCliente.htm",
		data : "accion=1",
		success : function(response) {
		// we have the response	
		if (response.respGetCatalogos.mensaje == "SUCCESS") {
			
			var cmb_paisC=$("#paisC").data("kendoDropDownList");
			cmb_paisC.dataSource.data([]);
			cmb_paisC.dataSource.data(response.respGetCatalogos.objPaisesList);
			cmb_paisC.select(function(dataItem) {
				 return dataItem.pais == "MX";
			});
			
			var cmb_estdoC=$("#estdoC").data("kendoDropDownList");
			cmb_estdoC.dataSource.data([]);
			cmb_estdoC.dataSource.data(response.respGetCatalogos.objRegionesList);
			cmb_estdoC.select(8);
			
			var cmb_sexoC=$("#sexoC").data("kendoDropDownList");
			cmb_sexoC.dataSource.data([]);
			 cmb_sexoC.dataSource.data(response.respGetCatalogos.objSexoList);
			 cmb_sexoC.select(0);
		
			 var cmb_tratamientoC=$("#tratamientoC").data("kendoDropDownList");
				cmb_tratamientoC.dataSource.data([]);
				cmb_tratamientoC.dataSource.data(response.respGetCatalogos.objTratamientoList);
				cmb_tratamientoC.select(0);
			
				var cmb_edo_civilC=$("#edo_civilC").data("kendoDropDownList");
				cmb_edo_civilC.dataSource.data([]);
				cmb_edo_civilC.dataSource.data(response.respGetCatalogos.objEdoCivilList);
				cmb_edo_civilC.select(0);
				
				var cmb_paisCo=$("#paisCo").data("kendoDropDownList");
				cmb_paisCo.dataSource.data([]);
				cmb_paisCo.dataSource.data(response.respGetCatalogos.objPaisesList);
				//cmb_paisCo.select(function(dataItem) {
				//	 return dataItem.pais == "MX";
				//});
				
				var cmb_estdoCo=$("#estdoCo").data("kendoDropDownList");
				cmb_estdoCo.dataSource.data([]);
				cmb_estdoCo.dataSource.data(response.respGetCatalogos.objRegionesList);
				cmb_estdoCo.select(0);
				
				var cmb_sexoCo=$("#sexoCo").data("kendoDropDownList");
				cmb_sexoCo.dataSource.data([]);
				cmb_sexoCo.dataSource.data(response.respGetCatalogos.objSexoList);
				cmb_sexoCo.select(0);
				
				 var cmb_tratamientoCo=$("#tratamientoCo").data("kendoDropDownList");
				 cmb_tratamientoCo.dataSource.data([]);
				 cmb_tratamientoCo.dataSource.data(response.respGetCatalogos.objTratamientoList);
				 cmb_tratamientoCo.select(0);
						 
						 //BUSCA EL CLIENTE EN CASO DE QUE EXISTA SE SETEA LOS VALORES
						 if (paramCliente!==''){
							 buscaClienteSapPorCarCli(paramCliente);
						 }
						
				} else {
					//kendoConsole.log("Usuario Autenticado","error");
					$("#mensajes_main").html("Usuario Autenticado");
				}
			},
			error : function(e) {
				//kendoConsole.log(e,"error");
				$("#mensajes_main").html("Error:"+e);
			}
			});
	
}


//funcion para cambiar el index de Pais del cliente una vez seleccionado 
function onSelectPaisC(e) {
	
	var dataItem = this.dataItem(e.item.index());
	paisC = dataItem.pais;

	$.ajax( {
				type : "POST",
				url : contexPath + "/RegistroCliente.htm",
				data : "accion=2&id_pais="+paisC,
				success : function(response) {
				// we have the response
				var datosRegC="";
				var visible=true;
				if (response.respGetCatalogos.mensaje == "SUCCESS") {
			
					datosRegC=response.respGetCatalogos.objRegionesList;
					visible=true;
						
				} 
				else {
					datosRegC= [{ regionx: "Sin regiones", region: "0" }];
					visible=false;
				}
				var cmb_estdoC=$("#estdoC").data("kendoDropDownList");
				cmb_estdoC.dataSource.data([]);
				cmb_estdoC.dataSource.data(datosRegC);
				//-cmb_estdoC.select(0);
				cmb_estdoC.select(8);
				cmb_estdoC.enable(visible);
			},
			error : function(e) {
				//kendoConsole.log(e,"error");
				$("#mensajes_main").html("Error:"+e);
			}
			});
}


//funcion para cambiar el index de Pais  una vez seleccionado en el contacto del cliente 
function onSelectPaisCo(e) {
	
	var dataItem = this.dataItem(e.item.index());
	paisCo = dataItem.pais;

	$.ajax( {
				type : "POST",
				url : contexPath + "/RegistroCliente.htm",
				data : "accion=2&id_pais="+paisCo,
				success : function(response) {
					// we have the response
				var datosRegCo="";
				var visibleCo=true;
				if (response.respGetCatalogos.mensaje == "SUCCESS") {
			
					datosRegCo=response.respGetCatalogos.objRegionesList;
					visibleCo=true;
						
				} 
				else {
					datosRegCo= [{ regionx: "Sin regiones", region: "0" }];
					visibleCo=false;
				}
				
				var cmb_estdoCo=$("#estdoCo").data("kendoDropDownList");
				cmb_estdoCo.dataSource.data([]);
				cmb_estdoCo.dataSource.data(datosRegCo);
				cmb_estdoCo.select(0);
				cmb_estdoCo.enable(visibleCo);
				
				
			},
			error : function(e) {
				//kendoConsole.log(e,"error");
				$("#mensajes_main").html("Error:"+e);
			}
			});
}

//funcion para cambiar el index de estado cliente una vez seleccionado 
function onSelectEdoC(e) {
	var dataItem = this.dataItem(e.item.index());
	estdoC = dataItem.region;
}
//funcion para cambiar el index de estado del contacto una vez seleccionado 
function onSelectEdoCo(e) {
	var dataItem = this.dataItem(e.item.index());
	estdoCo = dataItem.region;
}


// funcion para registrar los datos del cliente en SAP
function guardaC()
{
	
	$("#winOptionConfirmar").data("kendoWindow").close();
	
	  var criteriosIdClienteCartera=[];
	  var idClienteCartera = $("#paramIdClienteCartera").val();
	  
	  var numCambio=0;
	  var StatusNombre1= "";
	  var StatusNombre2= "";
	  
	  var StatusPrimerApellido= "";
	  var StatusSegundoApellido= "";
	  var StatusFechaNacimiento= "";
	  
	  var StatusNombre1N= "";
	  var StatusNombre2N= "";
	  
	  var StatusPrimerApellidoN= "";
	  var StatusSegundoApellidoN= "";
	  var StatusFechaNacimientoN= "";
	  
	  var continuarSave="SI";	  
	  
	  var xmlIdClienteCartera ="<criterios><criterio><idClienteCartera>"+idClienteCartera+"</idClienteCartera></criterio></criterios>";
	 
	  var clienteExiste=$('#existeClienteSap').val();
	  var idClienteSapExiste=$('#idClienteSapExiste').val();
	  
	  if (clienteExiste!=''){
		  guardaClienteSAP();
	  } else {
		  	$.ajax( {
				type : "POST",
				url : contexPath + "/CatalogoCarClientes.htm",
				data : "accion=4&xmlIds=" + xmlIdClienteCartera,
				success : function(response) {
					// we have the response
				if (response.respGetInfCarCliente.mensaje == "SUCCESS") {
					clientesDataExport = response.respGetInfCarCliente.objClientesList;
					
					dataClient = [];
					for (var i = 0; i < clientesDataExport.length; i++) {
						dataClient.push(clientesDataExport[i]);
					}
					
					if($.trim($('#nombre1C').val())!=""){
						if (dataClient[0].nombre1 != $.trim($('#nombre1C').val())){
							StatusNombre1=dataClient[0].nombre1;
							StatusNombre1N=$.trim($('#nombre1C').val());
							numCambio++;
						} else {
							StatusNombre1="-";
							StatusNombre1N="-";
						}
					}
					
					if($.trim($('#nombre2C').val())!=""){
						if (dataClient[0].nombre2 != $.trim($('#nombre2C').val())){
							StatusNombre2=dataClient[0].nombre2;
							StatusNombre2N=$.trim($('#nombre2C').val());
							numCambio++;
						} else {
							StatusNombre2="-";
							StatusNombre2N="-";
						}
					}
					
					if($.trim($('#app_patC').val())!=""){
						if (dataClient[0].app_pat != $.trim($('#app_patC').val())){
							StatusPrimerApellido=dataClient[0].app_pat;
							StatusPrimerApellidoN=$.trim($('#app_patC').val());
							numCambio++;
						} else {
							StatusPrimerApellido  = "-";
							StatusPrimerApellidoN = "-";
						}
					}
					
					if($.trim($('#app_matC').val())!=""){
						if (dataClient[0].app_mat != $.trim($('#app_matC').val())){
							StatusSegundoApellido=dataClient[0].app_mat;;
							StatusSegundoApellidoN=$.trim($('#app_matC').val());
							numCambio++;
						} else {
							StatusSegundoApellido="-";
							StatusSegundoApellidoN="-";
						}
					}
					
					if($.trim($('#fch_ncm').val())!=""){
						if (dataClient[0].fch_ncm != $.trim($('#fch_ncm').val())){
							StatusFechaNacimiento=dataClient[0].fch_ncm;
							StatusFechaNacimientoN= $.trim($('#fch_ncm').val());
							numCambio++;
						} else {
							StatusFechaNacimiento="-";
							StatusFechaNacimientoN="-";
						}
					}
					if (numCambio>0){
						continuarConCambiosCartera=1;
						openAvisaCambiosCarteracliente(StatusNombre1,StatusNombre2,StatusPrimerApellido,StatusSegundoApellido,StatusFechaNacimiento,
								StatusNombre1N,StatusNombre2N,StatusPrimerApellidoN,StatusSegundoApellidoN,StatusFechaNacimientoN);
					} else {
						guardaClienteSAP();
					}
					
					
					
					
					
				} else { 
					//kendoConsole.log("Hubo un error en carga clientes: "+response.respGetInfCarCliente.descripcion ,"error");
					$("#mensajes_main").html("Hubo un error al intentar localizar el cliente: "+response.respGetInfCarCliente.descripcion);
					$("#dg_clientes").empty();
					
				}
			},
			error : function(e) {
				   
				   //kendoConsole.log(e,"error");
				    $("#mensajes_main").html("Hubo un error en carga clientes: "+ e);
			  }
			});
	    }
	
}


function guardaClienteSAP2 (paramVenatana){
	  var valorOri="";
	  if (paramVenatana=='CAMBIOSCARTERA'){
		  valorOri=window.parent.$("#origen").val();
	  }
	  
	  if(valorOri=='registrar'){   
		  window.parent.$("#avisaCambiosCarteraCliente").data("kendoWindow").close();
		  //AQUI SE VALIDA 
		 	  
		  
		  
		  
		  //INICIO DE INTENTOS DE REGISTRO
		   if(intentos==3) {
		    	//window.parent.$("#winOptionBloquear").data("kendoWindow").close();
				//window.parent.parent.$("#windowRegCliente").data("kendoWindow").close();
				//window.parent.parent.parent.$("#windowRegPago").data("kendoWindow").close();
				
				window.parent.parent.parent.$("#mensajes_main").html("Existen errores en el procesamiento de informaci&oacute;n favor de comunicarse con el &aacute;rea de soporte");
				window.parent.$("#btn_reprocesa").attr("disabled");
				window.parent.$("#btn_cerrar").attr("style", "visibility: visible;");
				
		    }else if(intentos<4){
		    	bloqueaProcesoCierreVentas2();
				window.parent.$("#btn_reprocesa").attr("disabled");
				window.parent.$("#btn_ContinuarImpresion").attr("disabled");
				
			    var datos = window.parent.$('#registroC').serialize()+"&accion=3&carcli="+window.parent.parent.$("#idCliente").val();
			    intentos++;
		   }
		   //FIN DE INTENTOS DE REGISTRO
			if(intentos!=3){
				$.ajax( {
					type : "POST",
					url : contexPath + "/RegistroCliente.htm",
					data : datos,
					success : function(response) {
						// we have the response
					if (response.respAddClienteDto.mensaje == "SUCCESS") {
					
						if(window.parent.$("#origen").val()!='registrar')
						{			
						 
						 var mensaje="Cliente registrado exitosamente: "+response.respAddClienteDto.id_cliente_sap;
						 //var sp_main = window.parent.$("#sp_escritorio").data("kendoSplitter");
						 window.location= contexPath+'/Escritorio.htm?paramMensaje=' + mensaje;
						 //sp_main.ajaxRequest("#main_paine", contexPath+'/BlankScreen.htm', { id: 42 }); 
						} else {
							//kendoConsole.log("Se ha creado el cliente con el numero SAP: "+response.respAddClienteDto.id_cliente_sap,"");
							window.parent.$("#mensajes_main").html("Se ha creado el cliente con el numero SAP: "+response.respAddClienteDto.id_cliente_sap);
							window.parent.$("#img_cte").attr('src',''+img_ok+'');
							window.parent.$("#img_cte").attr('title','ok');
							window.parent.$("#estatus_cte").html('Terminado');
							window.parent.$("#folio_cte").html("Cliente: "+response.respAddClienteDto.id_cliente_sap);
							window.parent.idClienteGenerado=response.respAddClienteDto.id_cliente_sap;
							setCrearPedido2();
							//window.parent.parent.parent.$("#idClienteRegPago").val(response.respAddClienteDto.id_cliente_sap);
							
					    }
					}
					else
					{
						if(window.parent.$("#origen").val()=='registrar')
						{
							//kendoConsole.log("Hubo error: "+response.respAddClienteDto.descripcion,"error")
							//window.parent.$("#mensajes_main").html("Hubo error: "+response.respAddClienteDto.descripcion);
							window.parent.$("#mensajes_mainProceso").html("Hubo error: "+response.respAddClienteDto.descripcion);
							window.parent.$("#img_cte").attr('src',''+img_ok+'');
							window.parent.$("#img_cte").attr('title','ok');
							window.parent.$("#estatus_cte").html('Terminado');
							window.parent.$("#btn_reprocesa").removeAttr("disabled");
							window.parent.$("#folio_cte").html("?");
							/*setCrearPedido();*/
						}else{
							//kendoConsole.log("Hubo error: "+response.respAddClienteDto.descripcion,"error")
							//window.parent.$("#mensajes_main").html("Hubo error: "+response.respAddClienteDto.descripcion);
							window.parent.$("#mensajes_mainProceso").html("Hubo error: "+response.respAddClienteDto.descripcion);
						}
						
					}
				},
				error : function(e) {
					//kendoConsole.log(e,"error");
					//window.parent.$("#mensajes_main").html("Error:"+e);
					window.parent.$("#mensajes_main").html("Error: "+e);
					if(window.parent.$("#origen").val()=='registrar')
					{
						window.parent.$("#img_cte").attr('src',''+img_no+'');
						window.parent.$("#img_cte").attr('title','no');
						window.parent.$("#estatus_cte").html('Error');
						window.parent.$("#folio_cte").html('?');
						window.parent.$("#btn_reprocesa").removeAttr("disabled");
					}
					
				}
				});
			}
		}
	


}

function guardaClienteSAP (){
	  var datos;
	  if($("#origen").val()=='registrar'){   
		  
		  //AQUI SE VALIDA 
		 	  
		  
		  
		  
		  //INICIO DE INTENTOS DE REGISTRO
		   if(intentos==3) {
		    	//$("#winOptionBloquear").data("kendoWindow").close();
				//window.parent.$("#windowRegCliente").data("kendoWindow").close();
				//window.parent.parent.$("#windowRegPago").data("kendoWindow").close();
				window.parent.parent.$("#mensajes_main").html("Existen errores en el procesamiento de informaci&oacute;n favor de comunicarse con el &aacute;rea de soporte");
				$("#btn_reprocesa").attr("disabled");
				$("#btn_cerrar").attr("style", "visibility: visible;");
		    }else if(intentos<4){
		    	datos = $('#registroC').serialize()+"&accion=3&carcli="+window.parent.$("#idCliente").val();
		    	bloqueaProcesoCierreVentas();
				$("#btn_reprocesa").attr("disabled");
			    intentos++;
		   }
		   //FIN DE INTENTOS DE REGISTRO
		}else {
			datos = $('#registroC').serialize()+"&accion=3";
		}
	//var paisCl=$("#paisC").data("kendoDropDownList").dataItem().pais;
	//var estdoCl=$("#estdoC").data("kendoDropDownList").dataItem().region;if(estdoCl==0)estdoCl="";
	//var paisCo=$("#paisCo").data("kendoDropDownList").dataItem().pais;
	//var estdoCo=$("#estdoCo").data("kendoDropDownList").dataItem().region;if(estdoCo==0)estdoCo="";

	if(intentos!=3){
		    var clienteExiste=$('#existeClienteSap').val();
		    var idClienteSapExiste=$('#idClienteSapExiste').val();
		    /*if (clienteExiste!=''){
		    	$("#mensajes_main").html("Cliente SAP Existente: "+idClienteSapExiste);
				$("#img_cte").attr('src',''+img_ok+'');
				$("#img_cte").attr('title','ok');
				$("#estatus_cte").html('Encontrado');
				$("#folio_cte").html("Cliente: "+idClienteSapExiste);
				
				window.parent.parent.$("#idClienteRegPago").val(idClienteSapExiste);
				setCrearPedido();
		    }else {*/
					$.ajax( {
						type : "POST",
						url : contexPath + "/RegistroCliente.htm",
						data : datos,
						success : function(response) {
							// we have the response
						if (response.respAddClienteDto.mensaje == "SUCCESS") {
						
							if($("#origen").val()!='registrar')
							{			
							 
							 var mensaje="Cliente registrado exitosamente: "+response.respAddClienteDto.id_cliente_sap;
							 //var sp_main = window.parent.$("#sp_escritorio").data("kendoSplitter");
							 window.location= contexPath+'/Escritorio.htm?paramMensaje=' + mensaje;
							 //sp_main.ajaxRequest("#main_paine", contexPath+'/BlankScreen.htm', { id: 42 }); 
							} else {
								//kendoConsole.log("Se ha creado el cliente con el numero SAP: "+response.respAddClienteDto.id_cliente_sap,"");
								$("#mensajes_main").html("Se ha creado el cliente con el numero SAP: "+response.respAddClienteDto.id_cliente_sap);
								$("#img_cte").attr('src',''+img_ok+'');
								$("#img_cte").attr('title','ok');
								$("#estatus_cte").html('Terminado');
								$("#folio_cte").html("Cliente: "+response.respAddClienteDto.id_cliente_sap);
								//window.parent.parent.$("#idClienteRegPago").val(response.respAddClienteDto.id_cliente_sap);
								idClienteGenerado=response.respAddClienteDto.id_cliente_sap;
								setCrearPedido();						

						    }
						}
						else
						{
							if($("#origen").val()=='registrar')
							{
								//kendoConsole.log("Hubo error: "+response.respAddClienteDto.descripcion,"error")
								$("#mensajes_mainProceso").html("Hubo error: "+response.respAddClienteDto.descripcion);
								$("#img_cte").attr('src',''+img_ok+'');
								$("#img_cte").attr('title','ok');
								$("#estatus_cte").html('Terminado');
								$("#btn_reprocesa").removeAttr("disabled");
								$("#folio_cte").html("?");
								/*setCrearPedido();*/
							}else{
								//kendoConsole.log("Hubo error: "+response.respAddClienteDto.descripcion,"error")
								//$("#mensajes_main").html("Hubo error: "+response.respAddClienteDto.descripcion);
								$("#mensajes_main").html("Hubo error: "+response.respAddClienteDto.descripcion);
							}
							
						}
					},
					error : function(e) {
						//kendoConsole.log(e,"error");
						//$("#mensajes_main").html("Error:"+e);
						$("#mensajes_mainProceso").html("Error: "+e);
						if($("#origen").val()=='registrar')
						{
							$("#img_cte").attr('src',''+img_no+'');
							$("#img_cte").attr('title','no');
							$("#estatus_cte").html('Error');
							$("#folio_cte").html('?');
							$("#btn_reprocesa").removeAttr("disabled");
						}
						
					}
					});
		    //}
	}

}




//funcion para cancelar la operacion de registro de cliente 
function cancelar(opc)
{
	if(opc=="regCliente")
	{
		var sp_main = window.parent.$("#sp_escritorio").data("kendoSplitter");
		 sp_main.ajaxRequest("#main_paine", contexPath+'/BlankScreen.htm', { id: 42 });
	}	
	if($("#origen").value=='registrar')
	{
		window.parent.$("#windowRegCliente").data("kendoWindow").close();
	}
}
//funcion que valida los campor capturados para la alta de un nuevo cliente SAP ValidaRegCli(accionAdmin)
function ValidaRegCli(accionAdmin)
{
var datepicker = $("#fch_ncm").data("kendoDatePicker");
var datepicFecContacto = $("#fechaNacContacto").data("kendoDatePicker");
var setValue = function () {
	     datepicker.value($("#value").val());
};

var setValue = function () {
    datepicker.value($("#value").val());
};


var tabToActivate = $("#prestanombre");





var resp=false;
var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
var rfc=/^[a-zA-Z]{3,4}(\d{6})((\D|\d){3})?$/;
	
	if($.trim($('#nombre1C').val())=="")
	{    		
		//kendoConsole.log("Debe introducir Nombre Cliente","warning");
		$("#mensajes_main").html("Debe introducir Nombre Cliente");
		$('#nombre1C').focus();
		return;
	}
	if($.trim($('#app_patC').val())=="")
	{
		if($("#radioFisica").is(':checked')) {
			$("#mensajes_main").html("Debe introducir Apellido Paterno del Cliente");
			$('#app_patC').focus();
			return;
		}
		
		if($("#radioMoral").is(':checked')) {
			$("#mensajes_main").html("Debe introducir el Tipo de Sociedad del Cliente");
			$('#app_patC').focus();
			return;
		}
		//kendoConsole.log("Debe introducir Apellido Paterno del Cliente","warning");
		
	}
	if ($("#radioFisica").is(':checked')) {
		
		if($.trim($("#app_matC").val())==''){
			$("#mensajes_main").html("Debe introducir Apellido Materno del Cliente");
		    $('#app_matC').focus();
		    return;
		}	    
	}
    
	if ($.trim($("#edo_civilC").val())=="") {
		
		//if($.trim($("#app_matC").val())==''){
			$("#mensajes_main").html("Debe seleccionar un estado civil");
		    $('#edo_civilC').focus();
		    return;
		//}	    
	}
	
	if ($.trim($("#sexoC").val())=="") {
		
		//if($.trim($("#app_matC").val())==''){
			$("#mensajes_main").html("Debe seleccionar el genero");
		    $('#sexoC').focus();
		    return;
		//}	    
	}
		
	if($.trim($("#fch_ncm").val())=="")
	{
		//kendoConsole.log("Debe introducir Fecha Nacimiento","warning");
		
			$("#mensajes_main").html("Debe introducir Fecha");
			$('#fch_ncm').focus();
	
			return;		
	}
	
	if(datepicker.value()==null)
	{
		//kendoConsole.log("Capture fecha válida o seleccionela","warning");
		$("#mensajes_main").html("Capture fecha válida o seleccionela");
		$('#fch_ncm').focus();
		return;
	}
	
	if($.trim($('#id_ifeC').val())=='' && $.trim($('#id_pasprtC').val())=='')
	{
		//kendoConsole.log("Debe introducir IFE y/o Pasaporte del Cliente","warning");
		if($("#radioFisica").is(':checked')) {  
		   $("#mensajes_main").html("Debe introducir IFE y/o Pasaporte del Cliente");
		   $('#id_ifeC').focus();
		   return;
		}
	}
	var numIfe= $.trim($('#id_ifeC').val()).length;
	if (numIfe=='' || numIfe==undefined || numIfe==null){
		numIfe=0;
	}
	if($.trim($('#id_ifeC').val())!=='' && numIfe<13)
	{
		
				//kendoConsole.log("IFE por lo menos 13 caracteres","warning");
		if($("#radioFisica").is(':checked')) {
		        $("#mensajes_main").html("IFE por lo menos 13 caracteres");
				$('#id_ifeC').focus();
				return;
		}		
	}
	
	 var numPasa= $.trim($('#id_pasprtC').val()).length;
	 if (numPasa=='' || numPasa==undefined || numPasa==null){
		numPasa=0;
	}
	
	
		if($.trim($('#id_pasprtC').val())!=="" && numPasa<9)
		{
			//kendoConsole.log("Pasaporte mínimo 9 caracteres","warning");
			if($("#radioFisica").is(':checked')) {   
			    $("#mensajes_main").html("Pasaporte mínimo 9 caracteres");
			    $('#id_pasprtC').focus();
			    return;
			}
		}
	   	   
	
	
	
	if($.trim($("#curpC").val())=='' && $("#radioFisica").is(':checked'))
	{
				//kendoConsole.log("Debe introducir CURP del Cliente","warning");
				$("#mensajes_main").html("Debe introducir CURP del Cliente");
				$('#curpC').focus();
				return;
	}    
	
	if($.trim($("#curpC").val()).length<18 && $("#radioFisica").is(':checked'))
	     {
		    //kendoConsole.log("Curp minimo 18 caracteres","warning");
		    $("#mensajes_main").html("Curp minimo 18 caracteres");
		    $('#curpC').focus();
		    return;
	}	
	
	if($.trim($('#rfcC').val())=="")
	{
		//kendoConsole.log("Debe introducir RFC del Cliente","warning");
		$("#mensajes_main").html("Debe introducir RFC del Cliente");
		$('#rfcC').focus();
		return;
	}
	
	if($("#radioFisica").is(':checked')) {
		if($.trim($("#rfcC").val())!="" && !rfc.test($.trim($("#rfcC").val())))
		{
			//kendoConsole.log("Capture RFC válido","warning");
			$("#mensajes_main").html("Capture RFC válido");
			$('#rfcC').focus();
			return;
		}
	}
	
	if($("#radioMoral").is(':checked')) {
		if($.trim($('#rfcC').val()).length<12)
		{
			//kendoConsole.log("RFC minimo 10 caracteres","warning");
			$("#mensajes_main").html("RFC debe ser de 12 caracteres para personas morales");
			$('#rfcC').focus();
			return;
		}
		
		if($.trim($('#rfcC').val()).length>12)
		{
			//kendoConsole.log("RFC minimo 10 caracteres","warning");
			$("#mensajes_main").html("RFC debe ser de 12 caracteres para personas morales");
			$('#rfcC').focus();
			return;
		}
	}
	
	if($("#radioFisica").is(':checked')) {
		if($.trim($('#rfcC').val()).length<13)
		{
			//kendoConsole.log("RFC minimo 10 caracteres","warning");
			$("#mensajes_main").html("RFC debe ser de 13 caracteres");
			$('#rfcC').focus();
			return;
		}
	}
	
	
	if($.trim($('#calleC').val())=="")
	{
		//kendoConsole.log("Debe introducir calle del Cliente","warning");
		$("#mensajes_main").html("Debe introducir calle del Cliente");
		$('#calleC').focus();
		return;
	}
	
	if($.trim($('#colnC').val())=="")
	{
		//kendoConsole.log("Debe introducir colonia del Cliente","warning");}
		$("#mensajes_main").html("Debe introducir colonia del Cliente");
		$('#colnC').focus();
		return;
	}
	
	if($.trim($('#noextC').val())=="")
	{
		//kendoConsole.log("Debe introducir No Ext del Cliente","warning");
		$("#mensajes_main").html("Debe introducir No Ext del Cliente");
		$('#noextC').focus();
		return;
	}
   
	if($.trim($('#cdpstC').val())=="")
	{
		//kendoConsole.log("Debe introducir CP del Cliente","warning");
		$("#mensajes_main").html("Debe introducir CP del Cliente");
		$('#cdpstC').focus();
		return;
	}
	
	if($.trim($('#cdpstC').val()).length<5)
	{
		//kendoConsole.log("El código postal debe de ser de 5 números","warning");
		$("#mensajes_main").html("El código postal debe de ser de 5 números");
		$('#cdpstC').focus();
		return;
	}
	
	if($.trim($('#dlmcpC').val())=="")
	{
		//kendoConsole.log("Debe introducir municipio del Cliente","warning");
		$("#mensajes_main").html("Debe introducir municipio del Cliente");
		$('#dlmcpC').focus();
		return;
	}
	
	if($.trim($('#telfnC').val())=="")
	{
		//kendoConsole.log("Debe introducir municipio del Cliente","warning");
		$("#mensajes_main").html("Debe introducir un numero de telefono");
		$('#telfnC').focus();
		return;
	}
	
	if($.trim($("#telfnC").val())!="" && $.trim($("#telfnC").val().length)<10)
	{
		//kendoConsole.log("Capture telefono de 10 digitos","warning");
		$("#mensajes_main").html("Capture telefono de 10 digitos");
		$('#telfnC').focus();
		return;
	}
	
	if($.trim($("#tlfmoC").val())!="" && $.trim($("#tlfmoC").val().length)<10)
	{
		//kendoConsole.log("Capture telefono de 10 digitos","warning");
		$("#mensajes_main").html("Capture telefono de 10 digitos");
		$('#tlfmoC').focus();
		return;
	}
	
	if($.trim($("#telofC").val())!="" && $.trim($("#telofC").val().length)<10)
	{
		//kendoConsole.log("Capture telefono de 10 digitos","warning");
		$("#mensajes_main").html("Capture telefono de 10 digitos");
		$('#telofC').focus();
		return;
	}
	
	if($.trim($("#telfnC").val())=="" && $.trim($("#tlfmoC").val()) && $.trim($("#telofC").val())=="")
	{
		//kendoConsole.log("Es obligatorio un teléfono","warning");
		$("#mensajes_main").html("Es obligatorio un teléfono");
		$('#telfnC').focus();
		return;
	}
	
	if($.trim($("#mail1C").val())=="" && $.trim($("#mail2C").val())=="")
	{
		//kendoConsole.log("Es obligatorio un correo","warning");
		$("#mensajes_main").html("Es obligatorio un correo");
		$('#mail1C').focus();
		return;
	}
	
	if($.trim($("#mail1C").val())!="" && !emailReg.test($.trim($("#mail1C").val())))
	{
		//kendoConsole.log("Capture correo válido","warning");
		$("#mensajes_main").html("Capture correo válido");
		$('#mail1C').focus();
		return;
	}
	
	if($("#tratamientoC").val()=="")
	{
		//kendoConsole.log("Seleccione tratamiento","warning");
		$("#mensajes_main").html("Seleccione tratamiento");
		return;
	}
	
	if($.trim($("#mail2C").val())!="" && !emailReg.test($.trim($("#mail2C").val())))
	{
		//kendoConsole.log("Capture correo válido","warning");
		$("#mensajes_main").html("Capture correo válido");
		$('#mail2C').focus();
		return;
	}
	
	 
		if($("#radioFisica").is(':checked')) {
			var fechaNac=$('#fch_ncm').val();
			if (!validaClienteMayorEdad(fechaNac)){
				$("#AnyoActualCliente").html("El cliente es menor de edad(edad minima de 18 años).");
				$("#winAvisaClienteMenorEdadCli").data("kendoWindow").center();
				$("#winAvisaClienteMenorEdadCli").data("kendoWindow").open();
				return;
			}
			resp=true;
		} else 	{
			if($("#radioMoral").is(':checked')) {
				if($.trim($('#nombre1Co').val())=="")
				{    		
					//kendoConsole.log("Debe introducir Nombre Cliente","warning");
					$("#mensajes_main").html("Debe introducir Nombre de Representante legal");
					$("#tabStrip").data("kendoTabStrip").activateTab(tabToActivate);
					$('#nombre1Co').focus();
					return;
				}
				if($.trim($('#app_patCo').val())=="")
				{
					//kendoConsole.log("Debe introducir Apellido Paterno del Cliente","warning");
					$("#mensajes_main").html("Debe introducir Apellido Paterno del Representante legal");
					$("#tabStrip").data("kendoTabStrip").activateTab(tabToActivate);
					$('#app_patCo').focus();
					return;
				}
				
				
				if($.trim($("#app_matCo").val())==''){
					$("#mensajes_main").html("Debe introducir Apellido Materno del Contacto");
					$('#app_matCo').focus();
					return;
				}	    
				
				
				if($.trim($("#fechaNacContacto").val())=="")
				{
					//kendoConsole.log("Debe introducir Fecha Nacimiento","warning");
					$("#mensajes_main").html("Debe introducir Fecha Nacimiento del Representante legal");
					$("#tabStrip").data("kendoTabStrip").activateTab(tabToActivate);
					$('#fechaNacContacto').focus();
					return;
				}
				 if(datepicFecContacto.value()==null)
				{
					//kendoConsole.log("Capture fecha válida o seleccionela","warning");
					$("#mensajes_main").html("Capture fecha válida o seleccionela del Representante legal");
					$('#fechaNacContacto').focus();
					return;
				}
				 if($.trim($('#ifeCo').val())=='' && $.trim($('#pasprtCo').val())=='')
				{
					//kendoConsole.log("Debe introducir IFE y/o Pasaporte del Cliente","warning");
					$("#mensajes_main").html("Debe introducir IFE y/o Pasaporte del Representante legal");
					$('#ifeCo').focus();
					return;
				}
				 
				var numIfe2= $.trim($('#ifeCo').val()).length;
				if (numIfe2=='' || numIfe2==undefined || numIfe2==null){
					numIfe2=0;
				}

				 if($.trim($('#ifeCo').val())!=='' && numIfe2<13)
				{
					
							//kendoConsole.log("IFE por lo menos 13 caracteres","warning");
					$("#mensajes_main").html("IFE por lo menos 13 caracteres en Representante legal");
							$('#ifeCo').focus();
							return;
				}
				 var numPasa2= $.trim($('#pasprtCo').val()).length;
				 if (numPasa2=='' || numPasa2==undefined || numPasa2==null){
					numPasa2=0;
				}
					
				 if($.trim($('#pasprtCo').val())!=='' && numPasa2<9)
				{
					//kendoConsole.log("Pasaporte mínimo 9 caracteres","warning");
					$("#mensajes_main").html("Pasaporte mínimo 9 caracteres en Representante legal");
					$('#pasprtCo').focus();
					return;
				}
				
				 if($.trim($("#curpCo").val())=="")
				{
					//kendoConsole.log("Debe introducir CURP del Cliente","warning");
					$("#mensajes_main").html("Debe introducir CURP del Representante legal");
					$('#curpCo').focus();
					return;
				}
				 if($.trim($("#curpCo").val()).length<18)
				{
					//kendoConsole.log("Curp minimo 18 caracteres","warning");
					$("#mensajes_main").html("Curp minimo 18 caracteres en Representante legal");
					$('#curpCo').focus();
					return;
				}
				 if($.trim($('#rfcCo').val())=="")
				{
					//kendoConsole.log("Debe introducir RFC del Cliente","warning");
					$("#mensajes_main").html("Debe introducir RFC del Representante legal");
					$('#rfcCo').focus();
					return;
				}
				 if($.trim($("#rfcCo").val())!="" && !rfc.test($.trim($("#rfcCo").val())))
				{
					//kendoConsole.log("Capture RFC válido","warning");
					$("#mensajes_main").html("Capture RFC válido");
					$('#rfcCo').focus();
					return;
				}
				 if($.trim($('#rfcCo').val()).length<13)
				{
					//kendoConsole.log("RFC minimo 10 caracteres","warning");
					$("#mensajes_main").html("RFC debe ser de 13 caracteres en Representante legal");
					$('#rfcCo').focus();
					return;
				}
				
				 if($.trim($('#paisCo').val())=="")
					{
						//kendoConsole.log("Debe introducir RFC del Cliente","warning");
						$("#mensajes_main").html("Seleccione un pais");
						$('#paisCo').focus();
						return;
				}
				 
				 if($.trim($('#estdoCo').val())=="")
					{
						//kendoConsole.log("Debe introducir RFC del Cliente","warning");
						$("#mensajes_main").html("Seleccione un estado");
						$('#estdoCo').focus();
						return;
				}
				 
				 
				 if($.trim($('#calleCo').val())=="")
				{
					//kendoConsole.log("Debe introducir calle del Cliente","warning");
					$("#mensajes_main").html("Debe introducir calle del Representante legal");
					$('#calleCo').focus();
					return;
				}
				 if($.trim($('#colnCo').val())=="")
				{
					//kendoConsole.log("Debe introducir colonia del Cliente","warning");}
					$("#mensajes_main").html("Debe introducir colonia del Representante legal");
					$('#colnCo').focus();
					return;
				}
				 if($.trim($('#noextCo').val())=="")
				{
					//kendoConsole.log("Debe introducir No Ext del Cliente","warning");
					$("#mensajes_main").html("Debe introducir No Ext del Representante legal");
					$('#noextCo').focus();
					return;
				}
				 if($.trim($('#cdpstCo').val())=="")
				{
					//kendoConsole.log("Debe introducir CP del Cliente","warning");
					$("#mensajes_main").html("Debe introducir CP del Representante legal");
					$('#cdpstCo').focus();
					return;
				}
				 if($.trim($('#cdpstCo').val()).length<5)
				{
					//kendoConsole.log("El código postal debe de ser de 5 números","warning");
					$("#mensajes_main").html("El código postal debe de ser de 5 números en Representante legal");
					$('#cdpstCo').focus();
					return;
				}
				
				 if($.trim($('#dlmcpCo').val())=="")
				{
					//kendoConsole.log("Debe introducir municipio del Cliente","warning");
					$("#mensajes_main").html("Debe introducir municipio del Representante legal");
					$('#dlmcpCo').focus();
					return;
				}
				 if($.trim($('#telfnCo').val())=="")
				{
					//kendoConsole.log("Debe introducir municipio del Cliente","warning");
					$("#mensajes_main").html("Debe introducir un numero de telefono valido");
					$('#telfnCo').focus();
					return;
				}
				
				 if($.trim($("#telfnCo").val())!="" && $.trim($("#telfnCo").val().length)<10)
				{
					//kendoConsole.log("Capture telefono de 10 digitos","warning");
					$("#mensajes_main").html("Capture telefono de 10 digitos en representante legal");
					$('#telfnCo').focus();
					return;
				}
				
				 if($.trim($("#mail1Co").val())=="" && $.trim($("#mail2Co").val())=="")
				{
					//kendoConsole.log("Es obligatorio un correo","warning");
					$("#mensajes_main").html("Es obligatorio un correo en Representante legal");
					$('#mail1C').focus();
					return;
				}
				 if($.trim($("#mail1Co").val())!="" && !emailReg.test($.trim($("#mail1Co").val())))
				{
					//kendoConsole.log("Capture correo válido","warning");
					$("#mensajes_main").html("Capture correo válido en Representante Legal");
					$('#mail1Co').focus();
					return;
				}
				 
				 if($("#tratamientoCo").val()=="")
					{
						//kendoConsole.log("Seleccione tratamiento","warning");
						$("#mensajes_main").html("Seleccione tratamiento en Representante legal");
						return;
					}
				 
				 
				 if($.trim($("#mail2Co").val())!="" && !emailReg.test($.trim($("#mail2Co").val())))
				{
					//kendoConsole.log("Capture correo válido","warning");
					$("#mensajes_main").html("Capture correo válido en Representante legal");
					$('#mail2Co').focus();
					return;
				} 
				
				var fechaNacContacto=$('#fechaNacContacto').val();
				if (!validaClienteMayorEdad(fechaNacContacto)){
					$("#AnyoActualCliente").html("El representante es menor de edad(edad minima de 18 años).");
					$("#winAvisaClienteMenorEdadCli").data("kendoWindow").center();
					$("#winAvisaClienteMenorEdadCli").data("kendoWindow").open();
					return;
				}
				resp=true;
			}
		}
	
	if(!resp)
	{
		closeConfirmaWindow();
	}
	
	
	
	return resp;
}

function cerrarVentanaAvisoMenorEdadz(){
	$("#winAvisaClienteMenorEdadCli").data("kendoWindow").close();
}
function validaClienteMayorEdad(paramFechaNacimiento){
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









//funcion para concatenar los para,etros de envio de consulta de clientes y creacion de los data source de los mismos 
function buscarClientesSAP() {
	var arrNombre1='';
	var arrNombre2='';
	var arrApellido1='';
	var arrApellido2='';
	xmlNom='';
	xmlNom2='';
	xmlApPat='';
	xmlApMat='';
	
	_that=this;
	if($("#nomTxt").val()=="" && $("#nom2Txt").val()=="" && $("#apPatTxt").val()=="" && $("#apMatTxt").val()=="")
	{
		//kendoConsole.log("Capture criterios de busqueda","error")
		$("#mensajes_main").html("Capture criterios de busqueda");
	}else{

	if($("#nomTxt").val()!="" && this.dataNombre.length==0)
	{
		xmlNom ="<criterios><criterio><nombre>"+$("#nomTxt").val()+"</nombre></criterio></criterios>";
		arrNombre1='0';
	}else if(this.dataNombre.length>0){
		arrNombre1='1';
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
		arrNombre2='0';
	}else if(this.dataNombre2.length>0){
		arrNombre2='1';
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
		arrApellido1='0';
		xmlApPat ="<criterios><criterio><apPat>"+$("#apPatTxt").val()+"</apPat></criterio></criterios>";
	}else if(this.dataApPaterno.length>0){
		arrApellido1='1';
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
		arrApellido2='0';
		xmlApMat ="<criterios><criterio><apMat>"+$("#apMatTxt").val()+"</apMat></criterio></criterios>";
	}else if(this.dataApMaterno.length>0){
		arrApellido2='1';
		var criteriosApMat=[];
		for(var i=0; i<this.dataApMaterno.length; i++)
		{
			var params4={};
			if(this.dataApMaterno[i].apMat!="" ){
				params4["apMat"]=this.dataApMaterno[i].apMat;
				criteriosApMat.push(params4);	
			}
		}
		xmlApMat = createXMLCriterios("criterios",criteriosApMat) 
	}
	
	window.parent.xmlNom=xmlNom;
	window.parent.xmlNom2=xmlNom2;
	window.parent.xmlApPat=xmlApPat;
	window.parent.xmlApMat=xmlApMat;
	window.parent.arrNombre1=arrNombre1;
	window.parent.arrNombre2=arrNombre2;
	window.parent.arrApellido1=arrApellido1;
	window.parent.arrApellido2=arrApellido2;
	window.parent.enviaDatosClienteSap(_that,"Registros encontrados");
  }
}

function enviaDatosClienteSap(param,paramMensaje){
	  var _thatx=param;
	  var mensaje=paramMensaje;
    $.ajax( {
		type : "POST",
		url : contexPath + "/CatalogoClientes.htm",
		data : "accion=1&xmlNom=" + xmlNom+"&xmlNom2="+xmlNom2+"&xmlApPat="+xmlApPat+"&xmlApMat="+xmlApMat
		      +"&arrNombre1="+arrNombre1+ "&arrNombre2="+arrNombre2 
		      + "&arrApellido1="+arrApellido1 + "&arrApellido2="+arrApellido2,
		success : function(response) {
			// we have the response
		if (response.respGetInfSapCliente.mensaje == "SUCCESS") {
			$("#windowSelCliSap").data("kendoWindow").close();
			if (mensaje!==null && mensaje!=='' && mensaje!==undefined){
				$("#mensajes_main").html(mensaje);
			}else{
				$("#mensajes_main").html("Registros encontrados");
			}
			$("#idVisualizaCliente").attr("style", "visibility: visible;width:100px;");
			///_thatx.offVentanaWait();
			var clientesSAPDataExport = response.respGetInfSapCliente.objClientesSapList;
			var dataClientSap = [];
		
			for (i = 0; i < clientesSAPDataExport.length; i++) {
				dataClientSap.push(clientesSAPDataExport[i]);
			}
			
			var dataSourceClientSap = new kendo.data.DataSource( {
				data : dataClientSap,
				pageSize : 50
			});		
			
			$("#dg_clientesSap").empty();
			$("#dg_clientesSap").kendoGrid( {
				 dataSource : dataSourceClientSap,
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
                 change: onSelectClienteSap,
                 columns : [ 
                 {
					field : "cliente.id_cliente_z",
					title : "Id Cliente Z",
					width:100
				 }, {
					field : "cliente.id_cliente_sap",
					title : "Id Cliente Sap",
					width:100
				},
				{
					field : "cliente.nombre1C",
					title : "Nombre",
					width:100
				}, {
					field : "cliente.nombre2C",
					title : "Segundo Nombre",
					width:100
				}, {
					field : "cliente.app_patC",
					title : "Apellido",
					width:100
				}, {
					field : "cliente.app_matC",
					title : "Segundo Apellido",
					width:100
				}, {
					field : "cliente.rfcC",
					title : "RFC",
					width:130
				}, {
					field : "cliente.id_ifeC",
					title : "IFE",
					width:130
				}]
				}).data("kendoGrid");
				} else {
					reseteClienteSap();
					//_thatx.offVentanaWait();
					///kendoConsole.log("Hubo un error en carga clientes Sap: "+response.respGetInfSapCliente.descripcion ,"error");
					$("#mensajes_main").html("Hubo un error en carga clientes Sap: "+response.respGetInfSapCliente.descripcion);
		}
	},
	error : function(e) {
		//_thatx.offVentanaWait();
		//kendoConsole.log(e,"error");
		$("#mensajes_main").html("Error:"+e);
	  }
	});	
	
}


function cancelarSelecionClienteSap()
{
	$("#windowSelCliSap").data("kendoWindow").close();
}

function reseteClienteSap(){
	var dataSourceClientSap = new kendo.data.DataSource( {
		data : [ ],
		pageSize : 50
	});		
	
	$("#dg_clientesSap").empty();
	$("#dg_clientesSap").kendoGrid( {
		 dataSource : dataSourceClientSap,
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
         change: onSelectClienteSap,
         columns : [ 
         {
			field : "cliente.id_cliente_z",
			title : "Id Cliente Z",
			width:100
		 }, {
			field : "cliente.id_cliente_sap",
			title : "Id Cliente Sap",
			width:100
		},
		{
			field : "cliente.nombre1C",
			title : "Nombre",
			width:100
		}, {
			field : "cliente.nombre2C",
			title : "Segundo Nombre",
			width:100
		}, {
			field : "cliente.app_patC",
			title : "Apellido",
			width:100
		}, {
			field : "cliente.app_matC",
			title : "Segundo Apellido",
			width:100
		}, {
			field : "cliente.rfcC",
			title : "RFC",
			width:130
		}, {
			field : "cliente.id_ifeC",
			title : "IFE",
			width:130
		},
		{
			  title: "",
              template: "<button onclick=\"showDetails(event,'detail')\">Visualizar</button>",
              width:100
		}]
		}).data("kendoGrid");

}


function onSelectClienteSap(e)
{	
	
    var grid = $("#dg_clientesSap").data("kendoGrid");    
    dataItemCliente = grid.dataItem(grid.tbody.find("tr.k-state-selected"));
}


//funcion para recargar los datos de consulta del clientes Sap 

function showDetailsMonitorPagos(e,action) {
	dataItem = [];
	clienteVisual=[];
    var grid = $("#dg_PagosRegistradosMonitor").data("kendoGrid");
    clienteVisual=grid.dataItem(grid.tbody.find("tr.k-state-selected"));
    dataItem = grid.dataItem(grid.tbody.find("tr.k-state-selected"));
    
    if(dataItem==null || dataItem==undefined || dataItem=='')
	{
		 //kendoConsole.log("Seleccione un Registro","warning");
		$("#mensajes_main").html("Seleccione un Registro");
		return;
	}
    
   // dataItem = grid.dataItem($(e.currentTarget).closest("tr"));
    switch (action) {
	case 'detail':
		 openWinViewClientConsulta('detail');
		break;
	default:
		break;
	}
   
}

function showDetails(e,action) {
	dataItem = [];
	clienteVisual=[];
    var grid = $("#dg_clientesSap").data("kendoGrid");
    clienteVisual=grid.dataItem(grid.tbody.find("tr.k-state-selected"));
    dataItem = grid.dataItem(grid.tbody.find("tr.k-state-selected"));
    
    if(dataItem==null || dataItem==undefined || dataItem=='')
	{
		 //kendoConsole.log("Seleccione un Registro","warning");
		$("#mensajes_main").html("Seleccione un Registro");
		return;
	}
    
   // dataItem = grid.dataItem($(e.currentTarget).closest("tr"));
    switch (action) {
	case 'detail':
		 openWinViewClientConsulta('detail');
		break;
	default:
		break;
	}
   
}


//funcion para abrir la ventana de Detalle de  Clientes en modo popup
function openWinViewClient(accion,paramPopup,paramIdCliente)
{   
	   var windowRegistro=$("#windowRegCliente").kendoWindow({
			actions: ["Close"],
			modal: true,
			resizable: false,
			content: 
				{
					url:contexPath+"/RegistroCliente.htm?origen="+accion+"&isPopup="+paramPopup+"&paramIdClienteCartera="+paramIdCliente 
			},
	        height: "550px",
	        title: "Registro Cliente",
			width: "990px"
				
		    }).data("kendoWindow");		
		windowRegistro.center();
		windowRegistro.open();
}

//funcion para abrir la ventana de Detalle de  Clientes en modo popup
function openWinViewClientConsulta(accion,origen,idcliente)
{   
	    var idClientex='';
	    if (origen=='SEGUNDOPAGO' || origen=='MONITOR'){
	    	idClientex=idcliente;
	    }else{
			if(dataItemCotizacion.length<=0 && $("#fromC").val()=="pagoParc"){
				//kendoConsole.log("Selecciona un pedido","warning");
				$("#mensajes_main").html("Selecciona un pedido");
			}
	    }
			
			var windowRegistro=$("#windowRegCliente").kendoWindow({
				actions: ["Close"],
				modal: true,
				open: function(e) {
				 $("html","body").css("overflow", "hidden"); 
				},
				resizable: false,
				content: 
					{
						url:contexPath+"/ClienteConsulta.htm?origen="+accion + "&origenRegPago="+origen + "&idClienteActual="+idClientex
				},
		        height: "500px",
		        title: "Consulta de cliente",
				width: "1000px"
			    }).data("kendoWindow");		
			windowRegistro.center();
			windowRegistro.open();			   
}


/*Se creo esta nueva función  para abrir la ventana de Detalle de  Clientes en modo popup*/
function openWinViewClientConsultaMonitorPago(accion,origen,idcliente)
{   
	    var idClientex='';
	    if (origen=='SEGUNDOPAGO' || origen=='MONITOR'){
	    	idClientex=idcliente;
	    }else{
			if(dataItemCotizacion.length<=0 && $("#fromC").val()=="pagoParc"){
				//kendoConsole.log("Selecciona un pedido","warning");
				$("#mensajes_main").html("Selecciona un pedido");
			}
	    }
			
			var windowRegistro=$("#windowRegCliente").kendoWindow({
				actions: ["Close"],
				modal: true,
				open: function(e) {
				 $("html","body").css("overflow", "hidden"); 
				},
				resizable: false,
				content: 
					{
						url:contexPath+"/ClienteConsulta.htm?origen="+accion + "&origenRegPago="+origen + "&idClienteActual="+idClientex
				},
		        height: "500px",
		        title: "Consulta de cliente",
				width: "1000px"
			    }).data("kendoWindow");		
			windowRegistro.center();
			windowRegistro.open(); 
}

/**
 * Metodos para la creacion del reporte  
 * 
 * Fecha de creación: XX/07/2012               
 */

//funcion para crear el xml 
function createXMLCriterios( root, crit )
{
  var nodes
  var xml = "";
  if( root )
    xml += "<" + root + ">";
  for(var i=0; i<crit.length; i++)
  {
	  nodes=crit[i];
	  xml += "<criterio>";
	  for( theNode in nodes )
	  {
	    xml += "<" + theNode + ">" + nodes[theNode] + "</" + theNode + ">";
	  }	
	  xml += "</criterio>";
  }  
  xml += "</" + root + ">";
  
  return xml;
}

function buscaClienteSap(idSap)
{
	//alert(idSap);
	if(idSap!="")//viene de Cierre Venta 
	{
		var xmlClienteSap="<criterios><criterio><idClieteSap>"+idSap+"</idClieteSap></criterio></criterios>";	
	}	
	$.ajax( {
		type : "POST",
		url : contexPath + "/CatalogoClientes.htm",
		data : "accion=1&xmlCliSap="+xmlClienteSap,
		success : function(response) {
		// we have the response	
		if (response.respGetInfSapCliente.mensaje == "SUCCESS") {
			var clientesSAPDataExport = response.respGetInfSapCliente.objClientesSapList;
			window.parent.dataItem = clientesSAPDataExport[0];
			setDatos();
				
		} else {
			//kendoConsole.log("Usuario Autenticado","error");
			$("#mensajes_main").html("Usuario Autenticado");
		}
	},
	error : function(e) {
		//kendoConsole.log(e,"error");
		$("#mensajes_main").html("Error:"+e);
	}
	});

}

/////////////////////Funcionalidad para los parametros de seleccion

//funcion para abrir parametros de seleccion 
function openSelecCliSap()
{   
	var windowSelecCliSap =$("#windowSelCliSap").kendoWindow({
			actions: ["Close"],
			modal: true,
			resizable: false,
			content: 
				{
					url:contexPath+"/SeleccionCliSap.htm"
			},
	        height: "300px",
	        title: "Parametros de Selección",
			width: "390px"
		    });	
	windowSelecCliSap = $("#windowSelCliSap").data("kendoWindow");
	windowSelecCliSap.center();
	windowSelecCliSap.open();
}
//funcion para abrir parametros de seleccion 
function openParamCliSap(grid)
{   
	var windowparamCliSap =$("#windowParamsCliSap").kendoWindow({
			actions: ["Close"],
			modal: true,
			resizable: false,
			content: 
				{
					url:contexPath+"/ParamsCliSap.htm?queGrid="+grid
			},
	        height: "280px",
	     	width: "350px"
		    });	
			windowparamCliSap = $("#windowParamsCliSap").data("kendoWindow");
			windowparamCliSap.center();
			windowparamCliSap.open();
}

//funcion que carga el grid correspondiente 
function cargaGridSap(opc)
{
switch (opc) {
case 'ClienteZ':
	document.getElementById("cliZ").setAttribute("style","visibility:visible");
	if(window.parent.dataCliZ.length>0)
	{
		var arrDatos=[];
		for (i=0; i<window.parent.dataCliZ.length; i++){
			var datos={
					cliZ:	window.parent.dataCliZ[i].cliZ
			};
			
			arrDatos.push(datos);
		}  
		var dataSourceCliZ= new kendo.data.DataSource({
	        batch: true,
	        data: arrDatos,//window.parent.dataCliZ,
	        schema: {
	            model: {
	              fields: {
			  		   cliZ: {validation: { required: false ,min: 10} },
	               }
	            }
	        }
	     });	
	}else{
	var dataSourceCliZ= new kendo.data.DataSource({
      batch: true,
      schema: {
          model: {
            fields: {
		  		   cliZ: {validation: { required: false ,min: 10} },
             }
          }
      }
   });
	}
	$("#gridCliZ").kendoGrid({
      dataSource: dataSourceCliZ,
      resizable: true,
      pageable: true,
      height:150,
      toolbar: ["create"],
      columns: [
			{ field: "cliZ", title:"Clientes Z" , width: "130px",template:"#= cliZ.toUpperCase() #"},
			{ command: "destroy", title: " ", width: "80px" }],
      editable: true
  });
	
	break;
case 'ClienteSap':
	document.getElementById("cliSap").setAttribute("style","visibility:visible");
	if(window.parent.dataCliSap.length>0)
	{
		var arrDatos=[];
		for (i=0; i<window.parent.dataCliSap.length; i++){
			var datos={
					cliSap:	window.parent.dataCliSap[i].cliSap
			};
			
			arrDatos.push(datos);
		}  
		
		var dataSourceCliSap= new kendo.data.DataSource({
	        batch: true,
	        data: arrDatos,//window.parent.dataCliSap,
	        schema: {
	            model: {
	              fields: {
			  		   cliSap: {validation: { required: false ,min: 10} },
	               }
	            }
	        }
	     });	
	}else{
	var dataSourceCliSap= new kendo.data.DataSource({
      batch: true,
      schema: {
          model: {
            fields: {
		  		   cliSap: {validation: { required: false ,min: 10} },
             }
          }
      }
   });
	}
	$("#gridCliSap").kendoGrid({
      dataSource: dataSourceCliSap,
      resizable: true,
      pageable: true,
      height:150,
      toolbar: ["create"],
      columns: [
			{ field: "cliSap", title:"Clientes Sap" , width: "130px",template:"#= cliSap.toUpperCase() #"},
			{ command: "destroy", title: " ", width: "80px" }],
      editable: true
  });
	
	break;
case 'Nombre':
	document.getElementById("nom").setAttribute("style","visibility:visible");
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
	        data: arrDatos,//window.parent.dataNombre,
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
	$("#gridNom").kendoGrid({
      dataSource: dataSourceNom,
      resizable: true,
      pageable: true,
      height:150,
      toolbar: ["create"],
      columns: [
			{ field: "nombre", title:"Nombres" , width: "130px",template:"#= nombre.toUpperCase() #"},
			{ command: "destroy", title: " ", width: "80px" }],
      editable: true
  });
	
	break;
case 'SegundoNombre':
	document.getElementById("nom2").setAttribute("style","visibility:visible");
	if(window.parent.dataNombre2.length>0)
	{
		var arrDatos=[];
		for (i=0; i<window.parent.dataNombre2.length; i++){
			var datos={
					nombre2:	window.parent.dataNombre2[i].nombre2
			};
			
			arrDatos.push(datos);
		} 
		var dataSourceNom2= new kendo.data.DataSource({
	        batch: true,
	        data:arrDatos, //window.parent.dataNombre2,
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
	$("#gridNom2").kendoGrid({
      dataSource: dataSourceNom2,
      resizable: true,
      pageable: true,
      height:150,
      toolbar: ["create"],
      columns: [
			{ field: "nombre2", title:"Segundos Nombres" , width: "130px",template:"#= nombre2.toUpperCase() #"},
			{ command: "destroy", title: " ", width: "80px" }],
      editable: true
  });
	break;
case 'ApellidoPaterno':
	document.getElementById("apPat").setAttribute("style","visibility:visible");
	if(window.parent.dataApPaterno.length>0)
	{
		var arrDatos=[];
		for (i=0; i<window.parent.dataApPaterno.length; i++){
			var datos={
					apPat:	window.parent.dataApPaterno[i].apPat
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
	$("#gridApPat").kendoGrid({
      dataSource: dataSourceApPat,
      resizable: true,
      pageable: true,
      heightel:150,
      toolbar: ["create"],
      columns: [
			{ field: "apPat", title:"Apellidos Paternos" , width: "130px",template:"#= apPat.toUpperCase() #"},
			{ command: "destroy", title: " ", width: "80px" }],
      editable: true
  });
	break;
case 'ApellidoMaterno':
	document.getElementById("apMat").setAttribute("style","visibility:visible");
	if(window.parent.dataApMaterno.length>0)
	{
		var arrDatos=[];
		for (i=0; i<window.parent.dataApMaterno.length; i++){
			var datos={
					apMat:	window.parent.dataApMaterno[i].apMat
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
	$("#gridApMat").kendoGrid({
      dataSource: dataSourceApMat,
      resizable: true,
      pageable: true,
      height:150,
      toolbar: ["create"],
      columns: [
			{ field: "apMat", title:"Apellidos Maternos" , width: "130px",template:"#= apMat.toUpperCase() #"},
			{ command: "destroy", title: " ", width: "80px" }],
      editable: true
  });
	break;
default:
	break;
}	
}
//funcion para aceptar Params de Seleccion, crear arreglo  
function aceptarParamsSap(opc)
{
switch (opc) {
case 'ClienteZ':
	
	var gridCliZ=$("#gridCliZ").data("kendoGrid");
	window.parent.dataCliZ = gridCliZ.dataSource.data();
	if(window.parent.dataCliZ.length>0)
	{
	   window.parent.$("#cliZTxt").val(window.parent.dataCliZ[0].cliZ);
	}	
	window.parent.$("#windowParamsCliSap").data("kendoWindow").close();
	break;
case 'ClienteSap':
	
	var gridCliSap =$("#gridCliSap").data("kendoGrid");
	window.parent.dataCliSap = gridCliSap.dataSource.data();
	if(window.parent.dataCliSap.length>0)
	{
	   window.parent.$("#cliSapTxt").val(window.parent.dataCliSap[0].cliSap);
	}	
	window.parent.$("#windowParamsCliSap").data("kendoWindow").close();
	break;
case 'Nombre':
	
	var gridNom =$("#gridNom").data("kendoGrid");
	window.parent.dataNombre = gridNom.dataSource.data();
	if(window.parent.dataNombre.length>0)
	{
	   window.parent.$("#nomTxt").val(window.parent.dataNombre[0].nombre);
	}	
	window.parent.$("#windowParamsCliSap").data("kendoWindow").close();
	break;
case 'SegundoNombre':
	var gridNom2 = $("#gridNom2").data("kendoGrid");
	window.parent.dataNombre2 = gridNom2.dataSource.data();
	if(window.parent.dataNombre2.length>0)
	{
	   window.parent.$("#nom2Txt").val(window.parent.dataNombre2[0].nombre2);
	}
	window.parent.$("#windowParamsCliSap").data("kendoWindow").close();
	break;
case 'ApellidoPaterno':
	var gridApPat = $("#gridApPat").data("kendoGrid");
	window.parent.dataApPaterno = gridApPat.dataSource.data();
	if(window.parent.dataApPaterno.length>0)
	{
	   window.parent.$("#apPatTxt").val(window.parent.dataApPaterno[0].apPat);
	}
	window.parent.$("#windowParamsCliSap").data("kendoWindow").close();
	break;
case 'ApellidoMaterno':
	var gridApMat = $("#gridApMat").data("kendoGrid");
	window.parent.dataApMaterno = gridApMat.dataSource.data();
	if(window.parent.dataApMaterno.length>0)
	{
	   window.parent.$("#apMatTxt").val(window.parent.dataApMaterno[0].apMat);
	}
	window.parent.$("#windowParamsCliSap").data("kendoWindow").close();
	break;
default:
	break;
}
}
//funcion que cierra ventana de  seleccion  Car Cliente 
function cancelarSelecSap()
{
	window.parent.$("#windowSelCliSap").data("kendoWindow").close();
}

//funcion para cancelar Params de Seleccion 
function cancelarParamsSap()
{
	window.parent.$("#windowParamsCliSap").data("kendoWindow").close();
}

/*
 * Modificado por Oscar Pérez SACTI Consultores
 * Se Modifico la función setDatos, ya que cuando obtenia los valores para llenar el grid
 * mandaba mensaje de undefined*/

function setDatos()
{
	//alert ("consultaMonitorPago:" + window.parent.consultaMonitorPago);
	if(window.parent.consultaMonitorPago != undefined && window.parent.consultaMonitorPago == 'MONITOR') {
		   var itemCliente=[];
		   itemCliente.push(window.parent.dataItem.cliente);
		   
		   $("#tratamientoC").val(window.parent.dataItem.cliente.tratamientoCDesc).attr('disabled', 'true');
		   $("#sexoC").val(window.parent.dataItem.cliente.sexoCDesc).attr('disabled', 'true');
		   $("#edo_civilC").val(window.parent.dataItem.cliente.edo_civilCDesc).attr('disabled', 'true');
		   $("#paisC").val(window.parent.dataItem.cliente.paisCDesc).attr('disabled', 'true');
		   $("#estdoC").val(window.parent.dataItem.cliente.estdoCDesc).attr('disabled', 'true');
		    $('#nombre1C').val(window.parent.dataItem.cliente.nombre1C).attr('disabled', 'true');
		    $('#nombre2C').val(window.parent.dataItem.cliente.nombre2C).attr('disabled', 'true');
			$('#app_patC').val(window.parent.dataItem.cliente.app_patC).attr('disabled', 'true');
			$('#app_matC').val(window.parent.dataItem.cliente.app_matC).attr('disabled', 'true');
			$('#app_matC').val(window.parent.dataItem.cliente.app_matC).attr('disabled', 'true');
			$('#regimenFiscal').val(window.parent.dataItem.cliente.regimenFiscal).attr('disabled', 'true');
			
			var datePicker = $("#fch_ncm").data("kendoDatePicker");
			datePicker.value(window.parent.dataItem.cliente.fch_ncm);
			datePicker.enable(false);
			$('#rfcC').val(window.parent.dataItem.cliente.rfcC).attr('disabled', 'true');
			$('#id_ifeC').val(window.parent.dataItem.cliente.id_ifeC).attr('disabled', 'true');
			$('#id_pasprtC').val(window.parent.dataItem.cliente.id_pasprtC).attr('disabled', 'true');
			$('#curpC').val(window.parent.dataItem.cliente.curpC).attr('disabled', 'true');
			$('#calleC').val(window.parent.dataItem.cliente.calleC).attr('disabled', 'true');
		    $('#noextC').val(window.parent.dataItem.cliente.noextC).attr('disabled', 'true');
			$('#nointC').val(window.parent.dataItem.cliente.nointC).attr('disabled', 'true');
			$('#colnC').val(window.parent.dataItem.cliente.colnC).attr('disabled', 'true');
			$('#calleC').val(window.parent.dataItem.cliente.calleC).attr('disabled', 'true');
		    $('#noextC').val(window.parent.dataItem.cliente.noextC).attr('disabled', 'true');
			$('#nointC').val(window.parent.dataItem.cliente.nointC).attr('disabled', 'true');
			$('#colnC').val(window.parent.dataItem.cliente.colnC).attr('disabled', 'true');
			$('#cdpstC').val(window.parent.dataItem.cliente.cdpstC).attr('disabled', 'true');
			$('#dlmcpC').val(window.parent.dataItem.cliente.dlmcpC).attr('disabled', 'true');
			$('#telfnC').val(window.parent.dataItem.cliente.telfnC).attr('disabled', 'true');
			$('#tlfmoC').val(window.parent.dataItem.cliente.tlfmoC).attr('disabled', 'true');
			$('#telofC').val(window.parent.dataItem.cliente.telofC).attr('disabled', 'true');
			$('#extncC').val(window.parent.dataItem.cliente.extncC).attr('disabled', 'true');
			$('#mail1C').val(window.parent.dataItem.cliente.mail1C).attr('disabled', 'true');
			$('#mail2C').val(window.parent.dataItem.cliente.mail2C).attr('disabled', 'true');
			
			//Datos para el contacto 
			  var itemContacto=[];
			   itemContacto.push(window.parent.dataItem.contacto);
		       
			   $("#tratamientoCo").val(window.parent.dataItem.contacto.tratamientoCoDesc).attr('disabled', 'true');
			   $("#sexoCo").val(window.parent.dataItem.contacto.sexoCoDesc).attr('disabled', 'true');
			   $("#paisCo").val(window.parent.dataItem.contacto.paisCoDesc).attr('disabled', 'true');
			   $("#estdoCo").val(window.parent.dataItem.contacto.estdoCoDesc).attr('disabled', 'true');
		    $('#nombre1Co').val(window.parent.dataItem.contacto.nombre1Co).attr('disabled', 'true');
		    $('#nombre2Co').val(window.parent.dataItem.contacto.nombre2Co).attr('disabled', 'true');
			$('#app_patCo').val(window.parent.dataItem.contacto.app_patCo).attr('disabled', 'true');
			$('#app_matCo').val(window.parent.dataItem.contacto.app_matCo).attr('disabled', 'true');
			$('#rfcCo').val(window.parent.dataItem.contacto.rfcCo).attr('disabled', 'true');
			$('#id_ifeCo').val(window.parent.dataItem.contacto.id_ifeCo).attr('disabled', 'true');
			$('#id_pasprtCo').val(window.parent.dataItem.contacto.id_pasprtCo).attr('disabled', 'true');
			$('#curpCo').val(window.parent.dataItem.contacto.curpCo).attr('disabled', 'true');
			$('#calleCo').val(window.parent.dataItem.contacto.calleCo).attr('disabled', 'true');
		    $('#noextCo').val(window.parent.dataItem.contacto.noextCo).attr('disabled', 'true');
			$('#nointCo').val(window.parent.dataItem.contacto.nointCo).attr('disabled', 'true');
			$('#colnCo').val(window.parent.dataItem.contacto.colnCo).attr('disabled', 'true');
			$('#calleCo').val(window.parent.dataItem.contacto.calleCo).attr('disabled', 'true');
		    $('#noextCo').val(window.parent.dataItem.contacto.noextCo).attr('disabled', 'true');
			$('#nointCo').val(window.parent.dataItem.contacto.nointCo).attr('disabled', 'true');
			$('#colnCo').val(window.parent.dataItem.contacto.colnCo).attr('disabled', 'true');
			$('#cdpstCo').val(window.parent.dataItem.contacto.cdpstCo).attr('disabled', 'true');
			$('#dlmcpCo').val(window.parent.dataItem.contacto.dlmcpCo).attr('disabled', 'true');
			$('#telfnCo').val(window.parent.dataItem.contacto.telfnCo).attr('disabled', 'true');
			$('#tlfmoCo').val(window.parent.dataItem.contacto.tlfmoCo).attr('disabled', 'true');
			$('#telofCo').val(window.parent.dataItem.contacto.telofCo).attr('disabled', 'true');
			$('#extnCo').val(window.parent.dataItem.contacto.extncCo).attr('disabled', 'true');
			$('#mail1Co').val(window.parent.dataItem.contacto.mail1Co).attr('disabled', 'true');
			$('#mail2Co').val(window.parent.dataItem.contacto.mail2Co).attr('disabled', 'true');
			var datePicker2 = $("#fechaNacContacto").data("kendoDatePicker");
			datePicker2.value(window.parent.dataItem.cliente.fechaNacContacto);
			datePicker2.enable(false);		
	} else {
		clienteVisual;
		
		if(window.parent.clienteVisual!=null)
		{
			   var itemCliente=[];
			   itemCliente.push(window.parent.clienteVisual.cliente);
			   
			   $("#tratamientoC").val(window.parent.clienteVisual.cliente.tratamientoCDesc).attr('disabled', 'true');
			   $("#sexoC").val(window.parent.clienteVisual.cliente.sexoCDesc).attr('disabled', 'true');
			   $("#edo_civilC").val(window.parent.clienteVisual.cliente.edo_civilCDesc).attr('disabled', 'true');
			   $("#paisC").val(window.parent.clienteVisual.cliente.paisCDesc).attr('disabled', 'true');
			   $("#estdoC").val(window.parent.clienteVisual.cliente.estdoCDesc).attr('disabled', 'true');
			    $('#nombre1C').val(window.parent.clienteVisual.cliente.nombre1C).attr('disabled', 'true');
			    $('#nombre2C').val(window.parent.clienteVisual.cliente.nombre2C).attr('disabled', 'true');
				$('#app_patC').val(window.parent.clienteVisual.cliente.app_patC).attr('disabled', 'true');
				$('#app_matC').val(window.parent.clienteVisual.cliente.app_matC).attr('disabled', 'true');
				$('#app_matC').val(window.parent.clienteVisual.cliente.app_matC).attr('disabled', 'true');
				$('#regimenFiscal').val(window.parent.clienteVisual.cliente.regimenFiscal).attr('disabled', 'true');
				
				var datePicker = $("#fch_ncm").data("kendoDatePicker");
				datePicker.value(window.parent.clienteVisual.cliente.fch_ncm);
				datePicker.enable(false);
				$('#rfcC').val(window.parent.clienteVisual.cliente.rfcC).attr('disabled', 'true');
				$('#id_ifeC').val(window.parent.clienteVisual.cliente.id_ifeC).attr('disabled', 'true');
				$('#id_pasprtC').val(window.parent.clienteVisual.cliente.id_pasprtC).attr('disabled', 'true');
				$('#curpC').val(window.parent.clienteVisual.cliente.curpC).attr('disabled', 'true');
				$('#calleC').val(window.parent.clienteVisual.cliente.calleC).attr('disabled', 'true');
			    $('#noextC').val(window.parent.clienteVisual.cliente.noextC).attr('disabled', 'true');
				$('#nointC').val(window.parent.clienteVisual.cliente.nointC).attr('disabled', 'true');
				$('#colnC').val(window.parent.clienteVisual.cliente.colnC).attr('disabled', 'true');
				$('#calleC').val(window.parent.clienteVisual.cliente.calleC).attr('disabled', 'true');
			    $('#noextC').val(window.parent.clienteVisual.cliente.noextC).attr('disabled', 'true');
				$('#nointC').val(window.parent.clienteVisual.cliente.nointC).attr('disabled', 'true');
				$('#colnC').val(window.parent.clienteVisual.cliente.colnC).attr('disabled', 'true');
				$('#cdpstC').val(window.parent.clienteVisual.cliente.cdpstC).attr('disabled', 'true');
				$('#dlmcpC').val(window.parent.clienteVisual.cliente.dlmcpC).attr('disabled', 'true');
				$('#telfnC').val(window.parent.clienteVisual.cliente.telfnC).attr('disabled', 'true');
				$('#tlfmoC').val(window.parent.clienteVisual.cliente.tlfmoC).attr('disabled', 'true');
				$('#telofC').val(window.parent.clienteVisual.cliente.telofC).attr('disabled', 'true');
				$('#extncC').val(window.parent.clienteVisual.cliente.extncC).attr('disabled', 'true');
				$('#mail1C').val(window.parent.clienteVisual.cliente.mail1C).attr('disabled', 'true');
				$('#mail2C').val(window.parent.clienteVisual.cliente.mail2C).attr('disabled', 'true');
				
				//Datos para el contacto 
				  var itemContacto=[];
				   itemContacto.push(window.parent.clienteVisual.contacto);
			       
				   $("#tratamientoCo").val(window.parent.clienteVisual.contacto.tratamientoCoDesc).attr('disabled', 'true');
				   $("#sexoCo").val(window.parent.clienteVisual.contacto.sexoCoDesc).attr('disabled', 'true');
				   $("#paisCo").val(window.parent.clienteVisual.contacto.paisCoDesc).attr('disabled', 'true');
				   $("#estdoCo").val(window.parent.clienteVisual.contacto.estdoCoDesc).attr('disabled', 'true');
			    $('#nombre1Co').val(window.parent.clienteVisual.contacto.nombre1Co).attr('disabled', 'true');
			    $('#nombre2Co').val(window.parent.clienteVisual.contacto.nombre2Co).attr('disabled', 'true');
				$('#app_patCo').val(window.parent.clienteVisual.contacto.app_patCo).attr('disabled', 'true');
				$('#app_matCo').val(window.parent.clienteVisual.contacto.app_matCo).attr('disabled', 'true');
				$('#rfcCo').val(window.parent.clienteVisual.contacto.rfcCo).attr('disabled', 'true');
				$('#id_ifeCo').val(window.parent.clienteVisual.contacto.id_ifeCo).attr('disabled', 'true');
				$('#id_pasprtCo').val(window.parent.clienteVisual.contacto.id_pasprtCo).attr('disabled', 'true');
				$('#curpCo').val(window.parent.clienteVisual.contacto.curpCo).attr('disabled', 'true');
				$('#calleCo').val(window.parent.clienteVisual.contacto.calleCo).attr('disabled', 'true');
			    $('#noextCo').val(window.parent.clienteVisual.contacto.noextCo).attr('disabled', 'true');
				$('#nointCo').val(window.parent.clienteVisual.contacto.nointCo).attr('disabled', 'true');
				$('#colnCo').val(window.parent.clienteVisual.contacto.colnCo).attr('disabled', 'true');
				$('#calleCo').val(window.parent.clienteVisual.contacto.calleCo).attr('disabled', 'true');
			    $('#noextCo').val(window.parent.clienteVisual.contacto.noextCo).attr('disabled', 'true');
				$('#nointCo').val(window.parent.clienteVisual.contacto.nointCo).attr('disabled', 'true');
				$('#colnCo').val(window.parent.clienteVisual.contacto.colnCo).attr('disabled', 'true');
				$('#cdpstCo').val(window.parent.clienteVisual.contacto.cdpstCo).attr('disabled', 'true');
				$('#dlmcpCo').val(window.parent.clienteVisual.contacto.dlmcpCo).attr('disabled', 'true');
				$('#telfnCo').val(window.parent.clienteVisual.contacto.telfnCo).attr('disabled', 'true');
				$('#tlfmoCo').val(window.parent.clienteVisual.contacto.tlfmoCo).attr('disabled', 'true');
				$('#telofCo').val(window.parent.clienteVisual.contacto.telofCo).attr('disabled', 'true');
				$('#extnCo').val(window.parent.clienteVisual.contacto.extncCo).attr('disabled', 'true');
				$('#mail1Co').val(window.parent.clienteVisual.contacto.mail1Co).attr('disabled', 'true');
				$('#mail2Co').val(window.parent.clienteVisual.contacto.mail2Co).attr('disabled', 'true');
				var datePicker2 = $("#fechaNacContacto").data("kendoDatePicker");
				datePicker2.value(window.parent.clienteVisual.cliente.fechaNacContacto);
				datePicker2.enable(false);
				
		}		
	}


	
}


//funcion que calcula el RFC 
function validaRFCCliente(){
	if ($.trim($("#nom1Cliente").val())!=="" 
		      && $.trim($("#nom1Cliente").val())!==undefined 
		      && $.trim($("#primerApeCliente").val())!==''
		      && $.trim($("#primerApeCliente").val())!==undefined
		      && $.trim($("#segundoApeCliente").val())!==''
		      && $.trim($("#segundoApeCliente").val())!==undefined
		      && $.trim($("#fechaNacCliente").val())!==''
		      && $.trim($("#fechaNacCliente").val())!==undefined){
		
		   //valida
	   if ( ($.trim($("#nom1Cliente").val())!== $.trim($('#nombre1C').val()))  
			   || ($.trim($("#primerApeCliente").val())!== $.trim($('#app_patC').val()))
			   || ($.trim($("#segundoApeCliente").val())!== $.trim($('#app_matC').val()))
			   || ($.trim($("#fechaNacCliente").val())!== $.trim($('#fch_ncm').val())) ){
		   calcularfc(); 
	   }
		      
	 }
}

function calcularfc()
{
	
      if($('#nombre1C').val()=='' &&  $('#app_patC').val()=="" &&  $('#app_matC').val()==""  &&  $('#fch_ncm').val()=="" )
      {
      		//kendoConsole.log("Capture datos para calcular RFC y/o CURP","warning")
    		$("#mensajes_main").html("Capture datos para calcular RFC y/o CURP");
      }else{
    	   
    	  
    	   var nombre = $('#nombre1C').val();
           var pat = $('#app_patC').val();
           var mat = $('#app_matC').val();
           var fecha = $('#fch_ncm').val();
           
           $("#nom1Cliente").val(nombre);
           $("#primerApeCliente").val(pat);
           $("#segundoApeCliente").val(mat);
           $("#fechaNacCliente").val(fecha);
           
           
           
          
           if($("#radioFisica").is(':checked')) {
        	   var rfc = CalcularRFC(nombre, pat,  mat, fecha);
        	   $('#rfcC').val(rfc) ;
               $('#curpC').val(rfc) ;
           }
          
           
      }
	 
}

function calcularRfcPersonaMoral(){
	 if($("#radioMoral").is(':checked')) {
  	  
		 if($('#nombre1C').val()==''){
			 $("#mensajes_main").html("Capture el nombre de la empresa");
			 return;
		 }
		 var empresa = $('#nombre1C').val();
		 var fechaEmpresa = $('#fch_ncm').val();
		 var palabras = empresa.split(' ');
	
		 var primerPalabra=palabras[0];
		 var segundaPalabra = palabras[1];
	 	 var terceraPalabra = palabras[2]; 
	
		 var porcion1='';
	     var porcion2='';
		 var porcion3='';
	
	     if (primerPalabra==undefined){
	 	    porcion1="X";	
	     } else {
	 	    porcion1=primerPalabra.substring(0,1);
		 }
	
	     if (segundaPalabra ==undefined){
	 	    porcion2="X";	
	     } else {
	 	    porcion2=segundaPalabra.substring(0,1);
		 }
	
	     if (terceraPalabra==undefined){
	 	    porcion3="X";	
	     } else {
	 	    porcion3=terceraPalabra.substring(0,1);
		 }
		  
		 var fechaUnion = fechaEmpresa.substring(8, 10)+fechaEmpresa.substring(3, 5) + fechaEmpresa.substring(0, 2);
		 var rfcCreado= porcion1 + porcion2 + porcion3 + fechaUnion;
		 $('#rfcC').val(rfcCreado) ;
	 }
}


function validaConfirmaAlta()
{
  if (ValidaRegCli()) {
	 $("#winOptionConfirmar").data("kendoWindow").open();
	 $("#winOptionConfirmar").data("kendoWindow").center();
  }
}

function continuarGuardadoCliente(){
	$("#winAvisaClienteMenorEdadCli").data("kendoWindow").close();
	$("#winOptionConfirmar").data("kendoWindow").open();
	$("#winOptionConfirmar").data("kendoWindow").center();
	
}

function closeConfirmaWindow()
{
	$("#winOptionConfirmar").data("kendoWindow").close();
}

function bloqueaProcesoCierreVentas()
{
	$("#winOptionBloquear").parent().find(".k-window-action").css("visibility", "hidden");
	$("#winOptionBloquear").data("kendoWindow").open();
	$("#winOptionBloquear").data("kendoWindow").center();
	$("#img_cte").attr('src',''+img_proc+'');
	$("#estatus_cte").html('Procesando, espere.....');
	$("#img_pedido").attr('src',''+img_stop+'');
	$("#estatus_pedido").html('Pendiente.....');
	$("#img_pago").attr('src',''+img_stop+'');
	$("#estatus_pago").html('Pendiente.....');
}

function bloqueaProcesoCierreVentas2()
{
	window.parent.$("#winOptionBloquear").parent().find(".k-window-action").css("visibility", "hidden");
	window.parent.$("#winOptionBloquear").data("kendoWindow").open();
	window.parent.$("#winOptionBloquear").data("kendoWindow").center();
	window.parent.$("#img_cte").attr('src',''+img_proc+'');
	window.parent.$("#estatus_cte").html('Procesando, espere.....');
	window.parent.$("#img_pedido").attr('src',''+img_stop+'');
	window.parent.$("#estatus_pedido").html('Pendiente.....');
	window.parent.$("#img_pago").attr('src',''+img_stop+'');
	window.parent.$("#estatus_pago").html('Pendiente.....');
}

function closeBloqueaWindow()
{
	$("#winOptionBloquear").data("kendoWindow").close();
}

function getCotizacion()
{
	dataItemCotizacion=window.parent.dataItemCotizacion;
	//window.parent.dataItemCotizacion.idCotizacion;
}

function setCrearPedido()
{
	if($("#origen").val()=='registrar')
	{
		$("#img_pedido").attr('src',''+img_proc+'');
		$("#estatus_pedido").html('Procesando, espere.....');
	}

	$.ajax( {
		type : "POST",
		url : contexPath + "/RegistroCliente.htm",
		data : "accion=4&idCotizacion="+window.parent.$('#pedido').val(),
		success : function(response) {
		// we have the response	
		if (response.resPedido.mensaje == "SUCCESS") {
			if($("#origen").val()=='registrar')
			{
				$("#img_pedido").attr('src',''+img_ok+'');
				$("#img_pedido").attr('title','ok');
				$("#estatus_pedido").html('Terminado');
				$("#folio_pedido").html("Pedido: "+response.resPedido.idPedido);
				idCotizacionCierreVenta=window.parent.$('#pedido').val();
				//window.parent.parent.$("#idPedidoRegPago").val(response.resPedido.idPedido);
				idPedidoGenerado=response.resPedido.idPedido;
				
				procesoRegistraPago(window.parent.$('#pedido').val());//response.resPedido.idPedido); 131212 CAMBIO DE AJUSTE
			}				
		} else {
			//kendoConsole.log(response.resPedido.descripcion,"error");
			//$("#mensajes_main").html("Error:"+response.resPedido.descripcion);
			$("#mensajes_mainProceso").html("Error:"+response.resPedido.descripcion);
			
			if($("#origen").val()=='registrar')
			{
				$("#btn_reprocesa").removeAttr("disabled");
				$("#img_pedido").attr('src',''+img_no+'');
				$("#img_pedido").attr('title','no');
				$("#estatus_pedido").html('Terminado');
				$("#folio_pedido").html("?");
				/*procesoRegistraPago(response.resPedido.idPedido);*/
			}
		}
	},
	error : function(e) {
		//kendoConsole.log(e,"error");
		//$("#mensajes_main").html("Error:"+e);
		$("#mensajes_mainProceso").html("Error:"+e);
		if($("#origen").val()=='registrar')
		{
			$("#img_pedido").attr('src',''+img_no+'');
			$("#img_pedido").attr('title','no');
			$("#estatus_pedido").html('Terminado');
			$("#folio_pedido").html('?');
			$("#btn_reprocesa").removeAttr("disabled");
		}
	}
	});

}

function setCrearPedido2()
{
	if( window.parent.$("#origen").val()=='registrar')
	{
		window.parent.$("#img_pedido").attr('src',''+img_proc+'');
		window.parent.$("#estatus_pedido").html('Procesando, espere.....');
	}

	$.ajax( {
		type : "POST",
		url : contexPath + "/RegistroCliente.htm",
		data : "accion=4&idCotizacion="+window.parent.parent.$('#pedido').val(),
		success : function(response) {
		// we have the response	
		if (response.resPedido.mensaje == "SUCCESS") {
			if(window.parent.$("#origen").val()=='registrar')
			{
				window.parent.$("#img_pedido").attr('src',''+img_ok+'');
				window.parent.$("#img_pedido").attr('title','ok');
				window.parent.$("#estatus_pedido").html('Terminado');
				window.parent.$("#folio_pedido").html("Pedido: "+response.resPedido.idPedido);
				idCotizacionCierreVenta=window.parent.$('#pedido').val();
				//window.parent.parent.parent.$("#idPedidoRegPago").val(response.resPedido.idPedido);
				window.parent.idPedidoGenerado=response.resPedido.idPedido;
				procesoRegistraPago2(window.parent.parent.$('#pedido').val());//response.resPedido.idPedido); 131212 CAMBIOS
			}				
		} else {
			//kendoConsole.log(response.resPedido.descripcion,"error");
			//window.parent.$("#mensajes_main").html("Error:"+response.resPedido.descripcion);
			window.parent.$("#mensajes_mainProceso").html("Error: "+response.resPedido.descripcion);
			if(window.parent.$("#origen").val()=='registrar')
			{
				window.parent.$("#img_pedido").attr('src',''+img_no+'');
				window.parent.$("#img_pedido").attr('title','no');
				window.parent.$("#estatus_pedido").html('Terminado');
				window.parent.$("#folio_pedido").html("?");
				window.parent.$("#btn_reprocesa").removeAttr("disabled");
				/*procesoRegistraPago(response.resPedido.idPedido);*/
			}
		}
	},
	error : function(e) {
		//kendoConsole.log(e,"error");
		//window.parent.$("#mensajes_main").html("Error:"+e);
		window.parent.$("#mensajes_mainProceso").html("Error: "+e);
		if(window.parent.$("#origen").val()=='registrar')
		{
			window.parent.$("#img_pedido").attr('src',''+img_no+'');
			window.parent.$("#img_pedido").attr('title','no');
			window.parent.$("#estatus_pedido").html('Terminado');
			window.parent.$("#folio_pedido").html('?');
			window.parent.$("#btn_reprocesa").removeAttr("disabled");
		}
	}
	});

}

function procesoRegistraPago(idPedido_)
{
	$.ajax( {		
		type : "POST",
		url : contexPath + "/RegistroPagoMensual.htm",
		data : "accion=1&pedido="+idPedido_+"&xmlPagos="+window.parent.xmlPagos +"&idFaseEquipo="+window.parent.$("#idFaseEquipoX").val(),
		success : function(response) {
			// we have the response
		if (response.responseAddPagos.mensaje == "SUCCESS") {							
				$("#img_pago").attr('src',''+img_ok+'');
				$("#img_pago").attr('title','ok');
				$("#estatus_pago").html('Terminado');	
				$("#folio_pago").html("Pago: "+response.responseAddPagos.fregi);
				$("#btn_ContinuarImpresion").removeAttr("disabled");
				//$("#winOptionBloquear").data("kendoWindow").close();
				//window.parent.$("#windowRegCliente").data("kendoWindow").close();
				//window.parent.parent.$("#windowRegPago").data("kendoWindow").close();
				//openWindowImpresion(5,response.responseAddPagos.fregi,"pagoIni");
				$("#btn_reprocesa").attr("style", "visibility: hidden;");
				$("#btn_cerrar").attr("style", "visibility: hidden;");
				$("#valorPagoInicialAlImprimir").val(response.responseAddPagos.fregi);
				
				
				
			
		} else {
			    $("#mensajes_mainProceso").html("Error:"+response.responseAddPagos.descripcion);
				$("#img_pago").attr('src',''+img_no+'');
				$("#img_pago").attr('title','no');
				$("#estatus_pago").html('Terminado');
				$("#btn_reprocesa").removeAttr("disabled");
				$("#folio_pago").html("?");
	    }
	},
	error : function(e) {
		//kendoConsole.log(e,"error");
		//$("#mensajes_main").html("Error:"+e);
		$("#mensajes_mainProceso").html("Error:"+e);
		$("#img_pago").attr('src',''+img_no+'');
		$("#img_pago").attr('title','no');
		$("#estatus_pago").html('Error');
		$("#folio_pago").html('?');
		$("#btn_reprocesa").removeAttr("disabled");
	}
	});	
}


function imprimirReportePagInicial(){
	var valorFregi= $("#valorPagoInicialAlImprimir").val();
	$("#winOptionBloquear").data("kendoWindow").close();
	window.parent.$("#windowRegCliente").data("kendoWindow").close();
	window.parent.parent.$("#windowRegPago").data("kendoWindow").close();
	//openWindowImpresion(5,valorFregi,"pagoIni");
	//buscaPedidoDeCierreVentaAMonitor(paramOrigenRegistro, paramTipoRegistroPago, paramFregi, idPedidox,idClientex)
	window.parent.parent.parent.buscaPedidoDeCierreVentaAMonitor('REGISTRO_PAGO', 'INICIAL', valorFregi , idPedidoGenerado , idClienteGenerado);
	//buscaPedidoDeCierreVentaAMonitor('REGISTRO_PAGO', 'REGISTRO_UNO', response.responseAddPagos.fregi, response.responseAddPagos.idPedido,response.responseAddPagos.idCliente);
	//window.location= contexPath+'/CierreVenta.htm?from=pagoIni&idCotizacionCierreVenta='+idCotizacionCierreVenta+"&idFregiReportePagoInicial="+valorFregi + +"&isRegistroTipoPago=PAGOINICIAL";
}




function procesoRegistraPago2(idPedido_)
{
	$.ajax( {		
		type : "POST",
		url : contexPath + "/RegistroPagoMensual.htm",
		data : "accion=1&pedido="+idPedido_+"&xmlPagos="+window.parent.parent.xmlPagos+"&idFaseEquipo="+window.parent.parent.$("#idFaseEquipoX").val(),
		success : function(response) {
			// we have the response
		if (response.responseAddPagos.mensaje == "SUCCESS") {							
			    window.parent.$("#img_pago").attr('src',''+img_ok+'');
			    window.parent.$("#img_pago").attr('title','ok');
			    window.parent.$("#estatus_pago").html('Terminado');	
			    window.parent.$("#folio_pago").html("Pago: "+response.responseAddPagos.fregi);
				window.parent.$("#btn_ContinuarImpresion").removeAttr("disabled");
			    
			    ///window.parent.$("#winOptionBloquear").data("kendoWindow").close();
				//window.parent.parent.$("#windowRegCliente").data("kendoWindow").close();
				//window.parent.parent.parent.$("#windowRegPago").data("kendoWindow").close();
				window.parent.$("#btn_reprocesa").attr("style", "visibility: hidden;");
				window.parent.$("#btn_cerrar").attr("style", "visibility: hidden;");
				window.parent.$("#valorPagoInicialAlImprimir").val(response.responseAddPagos.fregi);
				////SE VA A QUITAR Y SE REEPLAZARA window.parent.openWindowImpresion(5,response.responseAddPagos.fregi,"pagoIni");
			
		} else {
			    window.parent.$("#mensajes_mainProceso").html("Error:"+response.responseAddPagos.descripcion);
			    window.parent.$("#img_pago").attr('src',''+img_no+'');
			    window.parent.$("#img_pago").attr('title','no');
			    window.parent.$("#estatus_pago").html('Terminado');
			    window.parent.$("#btn_reprocesa").removeAttr("disabled");
			    window.parent.$("#folio_pago").html("?");
	    }
	},
	error : function(e) {
		//kendoConsole.log(e,"error");
		//window.parent.$("#mensajes_main").html("Error:"+e);
		window.parent.$("#mensajes_mainProceso").html("Error: "+e);
		window.parent.$("#img_pago").attr('src',''+img_no+'');
		window.parent.$("#img_pago").attr('title','no');
		window.parent.$("#estatus_pago").html('Error');
		window.parent.$("#folio_pago").html('?');
		window.parent.$("#btn_reprocesa").removeAttr("disabled");
	}
	});	
}


function cerrarBloquea()
{
	$("#winOptionBloquear").data("kendoWindow").close();
}

function cambiaEtiquetaTipoPersona(){
	if($("#radioFisica").is(':checked')) {
		$("#idNombre").text("*Nombre");
		$("#idApellido").text("*Apellido");
		$("#idFechaNacimiento").text("*Fecha Nacimiento");
		$("#idPaisNacimiento").text("*País de Nacimineto");
		$("#idActEcoC").text("*Actividad u Ocupación");
		$("#idCurpCliente").text("*Curp");
		$("#idIfeCliente").text("*IFE");
		$("#idPasaporteCliente").text("*Pasaporte");
		$("#idSegundoApellidoCliente").text("*Segundo Apellido");
		$("#idNacionalidad").text("*Nacionalidad");
		
	}
	
	if($("#radioMoral").is(':checked')) {  
		$("#idNombre").text("*Razón Social");
		$("#idApellido").text("*Tipo Sociedad");
		$("#idFechaNacimiento").text("*Fecha Acta Const.");
		$("#idPaisNacimiento").text("País de Nacimineto");
		$("#idActEcoC").text("*Actividad Econónica");
		$("#idCurpCliente").text("Curp");
		$("#idIfeCliente").text("IFE");
		$("#idPasaporteCliente").text("Pasaporte");
		$("#idNacionalidad").text("Nacionalidad");
		$("#idSegundoApellidoCliente").text("Segundo Apellido");
		$('#curpC').val('');
	}
	
	
}

function validaRFCContacto(){
	if ($.trim($("#nom1CO").val())!=="" 
		      && $.trim($("#nom1CO").val())!==undefined 
		      && $.trim($("#primerApeCO").val())!==''
		      && $.trim($("#primerApeCO").val())!==undefined
		      && $.trim($("#segundoApeCO").val())!==''
		      && $.trim($("#segundoApeCO").val())!==undefined
		      && $.trim($("#fechaNacCO").val())!==''
		      && $.trim($("#fechaNacCO").val())!==undefined){
		
		   //valida
	   if ( ($.trim($("#nom1CO").val())!== $.trim($('#nombre1Co').val()))  
			   || ($.trim($("#primerApeCO").val())!== $.trim($('#app_patCo').val()))
			   || ($.trim($("#segundoApeCO").val())!== $.trim($('#app_matCo').val()))
			   || ($.trim($("#fechaNacCO").val())!== $.trim($('#fechaNacContacto').val())) ){
		   calcularfccontacto(); 
	   }
		      
	 }
}



//funcion que calcula el RFC 
function calcularfccontacto()
{
      if($('#nombre1Co').val()=='' &&  $('#app_patCo').val()=="" &&  $('#app_matCo').val()==""  &&  $('#fechaNacContacto').val()=="" )
      {
      		//kendoConsole.log("Capture datos para calcular RFC y/o CURP","warning")
    		$("#mensajes_main").html("Capture datos para calcular RFC y/o CURP de Contacto");
      }else{
      	   var nombre = $('#nombre1Co').val();
           var pat = $('#app_patCo').val();
           var mat = $('#app_matCo').val();
           var fecha = $('#fechaNacContacto').val();
           $("#nom1CO").val(nombre);
           $("#primerApeCO").val(pat);
           $("#segundoApeCO").val(mat);
           $("#fechaNacCO").val(fecha);
           

           var rfc = CalcularRFC(nombre, pat,  mat, fecha);
           $('#rfcCo').val(rfc) ;
           $('#curpCo').val(rfc) ;
          
      }
}

function openAvisaCambiosCarteracliente(paramNom1,paramNom2,paramPApe,paramSApe,paramFecNa,
		paramNom1N,paramNom2N,paramPApeN,paramSApeN,paramFecNaN)
{   
		
		var urlAux=contexPath+"/AvisaCambiosClienteCartera.htm?paramValorNom1="+paramNom1
							+"&paramValorNom2="+paramNom2+"&paramValorPApe="+ paramPApe + "&paramValorSApe="+paramSApe + "&paramValorFecNa="+paramFecNa +
							"&paramValorNom1N="+paramNom1N
							+"&paramValorNom2N="+paramNom2N+"&paramValorPApeN="+ paramPApeN + "&paramValorSApeN="+paramSApeN + "&paramValorFecNaN="+paramFecNaN;
		
		//url:contexPath+"/RegistroCliente.htm?origen="+accion+"&isPopup="+paramPopup+"&paramIdClienteCartera="+paramIdCliente
	
		windowConfirm =$("#avisaCambiosCarteraCliente").kendoWindow({
			actions: ["Close"],
			modal: true,
			resizable: false,
			content: 
			{
				url:urlAux
		    },
            height: "250px",
	        title: "Se detectaron las siguientes diferencias entre cartera de cliente y cliente",
			width: "820px"
		    });
		windowConfirm = $("#avisaCambiosCarteraCliente").data("kendoWindow");
		windowConfirm.center()
		windowConfirm.open();
}


function cerrarModificarDatosCarteraCliente(){
	window.parent.$("#avisaCambiosCarteraCliente").data("kendoWindow").close();
}

function openAvisoErrorProceso()
{   
		
		var urlAux=contexPath+"/AvisaCambiosClienteCartera.htm?paramValorNom1="+paramNom1
							+"&paramValorNom2="+paramNom2+"&paramValorPApe="+ paramPApe + "&paramValorSApe="+paramSApe + "&paramValorFecNa="+paramFecNa +
							"&paramValorNom1N="+paramNom1N
							+"&paramValorNom2N="+paramNom2N+"&paramValorPApeN="+ paramPApeN + "&paramValorSApeN="+paramSApeN + "&paramValorFecNaN="+paramFecNaN;
		
		//url:contexPath+"/RegistroCliente.htm?origen="+accion+"&isPopup="+paramPopup+"&paramIdClienteCartera="+paramIdCliente
	
		windowConfirm =$("#avisaCambiosCarteraCliente").kendoWindow({
			actions: ["Close"],
			modal: true,
			resizable: false,
			content: 
			{
				url:urlAux
		    },
            height: "250px",
	        title: "Se detectaron las siguientes diferencias entre cartera de cliente y cliente",
			width: "820px"
		    });
		windowConfirm = $("#avisaCambiosCarteraCliente").data("kendoWindow");
		windowConfirm.center();
		windowConfirm.open();
}

function buscaClienteSapPorCarCli(idCarCli)
{
	var xmlCarteraCli='';
	if(idCarCli!="")//viene de Cierre Venta 
	{
		xmlCarteraClix="<criterios><criterio><idCarteraCliente>"+idCarCli+"</idCarteraCliente></criterio></criterios>";	
	}	
	$.ajax( {
		type : "POST",
		url : contexPath + "/CatalogoClientes.htm",
		data : "accion=1&xmlCarteraCliente="+xmlCarteraClix,
		success : function(response) {
		// we have the response	
		if (response.respGetInfSapCliente.mensaje == "SUCCESS") {
			var clientesSAPDataExport = response.respGetInfSapCliente.objClientesSapList;
			dataItemClienteSapRegistro = clientesSAPDataExport[0];
			setDatosClienteSapExistente();
				
		} else {
			//kendoConsole.log("Usuario Autenticado","error");
			$("#mensajes_main").html("Nuevo cliente");
		}
	},
	error : function(e) {
		//kendoConsole.log(e,"error");
		$("#mensajes_main").html("Error:"+e);
	}
	});

}

function setDatosClienteSapExistente()
{

	if(dataItemClienteSapRegistro!=null)
	{
		
		   var itemCliente=[];
		   itemCliente.push(dataItemClienteSapRegistro.cliente);
		   if (itemCliente.length>0){
			  $('#existeClienteSap').val('EXISTE_CLIENTE');
			  $('#idClienteSapExiste').val(dataItemClienteSapRegistro.cliente.id_cliente_sap);
			  			  
		   }else{
			   $('#existeClienteSap').val('');
			   $('#idClienteSapExiste').val('');
		   }
		   var cmb_TratamientoC=$("#tratamientoC").data("kendoDropDownList");
		   cmb_TratamientoC.select(function(dataItem) {
			 return dataItem.tratamiento == dataItemClienteSapRegistro.cliente.tratamientoC;
		   });
		   //cmb_TratamientoC.enable(false);

		   var cmb_SexoC=$("#sexoC").data("kendoDropDownList");
		   cmb_SexoC.select(function(dataItem) {
			 return dataItem.sexo == dataItemClienteSapRegistro.cliente.sexoC;
		   });
		   
		   //cmb_SexoC.enable(false);
		   
		   var cmb_EstadoCivilC=$("#edo_civilC").data("kendoDropDownList");
		   cmb_EstadoCivilC.select(function(dataItem) {
				 return dataItem.edocvl == dataItemClienteSapRegistro.cliente.edo_civilC;
			});
			
		   //cmb_EstadoCivilC.enable(false);
		   
		   var cmb_PaisC=$("#paisC").data("kendoDropDownList");
		   cmb_PaisC.select(function(dataItem) {
				 return dataItem.pais == dataItemClienteSapRegistro.cliente.paisC;
			});
		   
		   //cmb_PaisC.enable(false);
		   
		   var cmb_EstadoC=$("#estdoC").data("kendoDropDownList");
		   cmb_EstadoC.select(function(dataItem) {
				 return dataItem.region == dataItemClienteSapRegistro.cliente.estdoC;
			});
		   //cmb_EstadoC.enable(false);
		   
		   /*$("#tratamientoC").attr('disabled', 'disabled');
		   $("#sexoC").attr('disabled', 'disabled');
		   $("#edo_civilC").attr('disabled', 'disabled');
		   $("#paisC").attr('disabled', 'disabled');
		   $("#estdoC").attr('disabled', 'disabled');*/
		   
		   
		    $('#nombre1C').val(dataItemClienteSapRegistro.cliente.nombre1C);
		    $('#nombre2C').val(dataItemClienteSapRegistro.cliente.nombre2C);
			$('#app_patC').val(dataItemClienteSapRegistro.cliente.app_patC);
			$('#app_matC').val(dataItemClienteSapRegistro.cliente.app_matC);
			$('#app_matC').val(dataItemClienteSapRegistro.cliente.app_matC);
		

			var regimenFiscal=dataItemClienteSapRegistro.cliente.regimenFiscal;
			if (regimenFiscal=='PERSONA FISICA'){
				$("#radioFisica").attr('checked', true);
				//$("#radioFisica").attr('disabled',true);
			} 
			if (regimenFiscal=='PERSONA MORAL'){
				$("#radioMoral").attr('checked', true);
				//$("#radioMoral").attr('disabled',true);
			}
			var datePicker = $("#fch_ncm").data("kendoDatePicker");
			datePicker.value(dataItemClienteSapRegistro.cliente.fch_ncm);
			//datePicker.enable(false);
			$('#rfcC').val(dataItemClienteSapRegistro.cliente.rfcC);
			$('#id_ifeC').val(dataItemClienteSapRegistro.cliente.id_ifeC);
			$('#id_pasprtC').val(dataItemClienteSapRegistro.cliente.id_pasprtC);
			$('#curpC').val(dataItemClienteSapRegistro.cliente.curpC);
			$('#calleC').val(dataItemClienteSapRegistro.cliente.calleC);
		    $('#noextC').val(dataItemClienteSapRegistro.cliente.noextC);
			$('#nointC').val(dataItemClienteSapRegistro.cliente.nointC);
			$('#colnC').val(dataItemClienteSapRegistro.cliente.colnC);
			$('#calleC').val(dataItemClienteSapRegistro.cliente.calleC);
		    $('#noextC').val(dataItemClienteSapRegistro.cliente.noextC);
			$('#nointC').val(dataItemClienteSapRegistro.cliente.nointC);
			$('#colnC').val(dataItemClienteSapRegistro.cliente.colnC);
			$('#cdpstC').val(dataItemClienteSapRegistro.cliente.cdpstC);
			$('#dlmcpC').val(dataItemClienteSapRegistro.cliente.dlmcpC);
			$('#telfnC').val(dataItemClienteSapRegistro.cliente.telfnC);
			$('#tlfmoC').val(dataItemClienteSapRegistro.cliente.tlfmoC);
			$('#telofC').val(dataItemClienteSapRegistro.cliente.telofC);
			$('#extncC').val(dataItemClienteSapRegistro.cliente.extncC);
			$('#mail1C').val(dataItemClienteSapRegistro.cliente.mail1C);
			$('#mail2C').val(dataItemClienteSapRegistro.cliente.mail2C);
			//$('#regimenFiscal').val(regimenFiscal);
			//Datos para el contacto 
			  var itemContacto=[];
			   itemContacto.push(dataItemClienteSapRegistro.contacto);
		     
			   
			   var cmb_TratamientoCO=$("#tratamientoCo").data("kendoDropDownList");
			   cmb_TratamientoCO.select(function(dataItem) {
				 return dataItem.tratamiento == dataItemClienteSapRegistro.cliente.tratamientoCo;
			   });
				
			   //cmb_TratamientoCO.enable(false);
			   
			   var cmb_SexoCO=$("#sexoCo").data("kendoDropDownList");
			   cmb_SexoCO.select(function(dataItem) {
				 return dataItem.sexo == dataItemClienteSapRegistro.cliente.sexoCo;
			   });
			   
			   //cmb_SexoCO.enable(false);
			   
			   var cmb_PaisCO=$("#paisCo").data("kendoDropDownList");
			   cmb_PaisCO.select(function(dataItem) {
					 return dataItem.pais == dataItemClienteSapRegistro.cliente.paisCo;
				});
			   
			   //cmb_PaisCO.enable(false);
			   
			   var cmb_EstadoCO=$("#estdoCo").data("kendoDropDownList");
			   cmb_EstadoCO.select(function(dataItem) {
					 return dataItem.region == dataItemClienteSapRegistro.cliente.estdoCo;
				});
			   
			   //cmb_EstadoCO.enable(false);
			   
			   //$("#tratamientoCo").attr('disabled', 'disabled');
			   //$("#sexoCo").attr('disabled', 'disabled');
			   //$("#paisCo").attr('disabled', 'disabled');
			   //$("#estdoCo").attr('disabled', 'disabled');
			   
			   
		    $('#nombre1Co').val(dataItemClienteSapRegistro.contacto.nombre1Co);
		    $('#nombre2Co').val(dataItemClienteSapRegistro.contacto.nombre2Co);
			$('#app_patCo').val(dataItemClienteSapRegistro.contacto.app_patCo);
			$('#app_matCo').val(dataItemClienteSapRegistro.contacto.app_matCo);
			$('#rfcCo').val(dataItemClienteSapRegistro.contacto.rfcCo);
			$('#id_ifeCo').val(dataItemClienteSapRegistro.contacto.id_ifeCo);
			$('#id_pasprtCo').val(dataItemClienteSapRegistro.contacto.id_pasprtCo);
			$('#curpCo').val(dataItemClienteSapRegistro.contacto.curpCo);
			$('#calleCo').val(dataItemClienteSapRegistro.contacto.calleCo);
		    $('#noextCo').val(dataItemClienteSapRegistro.contacto.noextCo);
			$('#nointCo').val(dataItemClienteSapRegistro.contacto.nointCo);
			$('#colnCo').val(dataItemClienteSapRegistro.contacto.colnCo);
			$('#calleCo').val(dataItemClienteSapRegistro.contacto.calleCo);
		    $('#noextCo').val(dataItemClienteSapRegistro.contacto.noextCo);
			$('#nointCo').val(dataItemClienteSapRegistro.contacto.nointCo);
			$('#colnCo').val(dataItemClienteSapRegistro.contacto.colnCo);
			$('#cdpstCo').val(dataItemClienteSapRegistro.contacto.cdpstCo);
			$('#dlmcpCo').val(dataItemClienteSapRegistro.contacto.dlmcpCo);
			$('#telfnCo').val(dataItemClienteSapRegistro.contacto.telfnCo);
			$('#tlfmoCo').val(dataItemClienteSapRegistro.contacto.tlfmoCo);
			$('#telofCo').val(dataItemClienteSapRegistro.contacto.telofCo);
			$('#extnCo').val(dataItemClienteSapRegistro.contacto.extncCo);
			$('#mail1Co').val(dataItemClienteSapRegistro.contacto.mail1Co);
			$('#mail2Co').val(dataItemClienteSapRegistro.contacto.mail2Co);
			var datePicker2 = $("#fechaNacContacto").data("kendoDatePicker");
			datePicker2.value(dataItemClienteSapRegistro.cliente.fechaNacContacto);
			//datePicker2.enable(false);
			
	} else {
		$('#existeClienteSap').val('');
		$('#idClienteSapExiste').val('');
	}

	

}
