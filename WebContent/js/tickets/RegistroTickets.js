/*
 * Guarda Registro Ticket
 */
function registrarTicket() {
	// alert("idAreaTicket=" +
	// $("#idAreaTicket").data('kendoComboBox').dataItem().id_ticket_area
	// +"&idVendedorTicket=" + $("#claveVendedorTicket").val()
	// +"&idCarClienteTicket=" + $("#claveClienteTicket").val() +
	// "&asuntoTicket=" + $("#asuntoTicket").val() + "&descripcionTicket=" +
	// $("#descripcionTicket").val() +"&idFaseTicket=" + $("#faseTicket").val()
	// + "&idEquipoTicket=" + $("#equipoTicket").val());
	if (ValidaRegistroTicket()) {
		$
				.ajax({
					type : "POST",
					cache : false,
					url : contexPath + "/ticket/AddRegistroTicket.htm",
					// data : "idAreaTicket=1",
					data : "idAreaTicket="
							+ $("#idAreaTicket").data('kendoComboBox')
									.dataItem().id_ticket_area
							+ "&idVendedorTicket="
							+ $("#claveVendedorTicket").val()
							+ "&idCarClienteTicket="
							+ $("#claveClienteTicket").val() + "&asuntoTicket="
							+ $("#asuntoTicket").val() + "&descripcionTicket="
							+ $("#descripcionTicket").val() + "&idFaseTicket="
							+ $("#faseTicket").val() + "&idEquipoTicket="
							+ $("#equipoTicket").val(),
					success : function(response) {
						if (response.mensaje == "SUCCESS") {
							$("#mensajes_main").html(
									"Registro satisfactorio numero de ticket "
											+ response.id_ticket_sap);
							limpiaFormaCaptura();
						} else if (response.mensaje == "LOGOUT") {
							salirSistema();
						} else {
							$("#mensajes_main").html(
									"Hubo un error en el registro de ticket "
											+ response.descripcion);
						}
					},
					error : function(e) {
						$("#mensajes_main").html(
								"Hubo un error en el registro: "
										+ e.responseText + " " + e.status);
					}
				});
	}
}

function limpiaFormaCaptura() {
	// $('#idAreaTicket :nth-child(0)').attr('selected', 'selected');
	// $('#idAreaTicket option[value=""]').attr('selected', 'selected');
	var datosAreasList = $("#idAreaTicket").data('kendoComboBox');
	datosAreasList.select(0);
	$("#nomAreaTicket").html("");
	$("#claveClienteTicket").val("");
	$("#nomClienteTicket").html("");
	var datosFaceList = $("#faseTicket").data('kendoComboBox');
	datosFaceList.select(0);
	$("#txt_desc_fase").html("");
	$("#claveVendedorTicket").val("");
	$("#nomVendedorTicket").html("");
	$("#txt_desc_equ").html("");
	var datosEquipoList = $("#equipoTicket").data('kendoComboBox');
	datosEquipoList.dataSource.data([]);
	datosEquipoList.text("");
	datosEquipoList.select(-1);
	$("#asuntoTicket").val("");
	$("#descripcionTicket").val("");

}

function actualizaFormaCaptura(idTicket) {
	// $('#idAreaTicket :nth-child(0)').attr('selected', 'selected');
	// $('#idAreaTicket option[value=""]').attr('selected', 'selected');
	$
			.ajax({
				type : "POST",
				cache : false,
				url : contexPath + "/ticket/FindTicketVicios.htm",
				data : "idTicket=" + idTicket,
				// data : formData,
				success : function(response) {
					if (response.mensaje == "SUCCESS") {

						window.parent.$("#GridConsultaCatalogoTickets").empty();

						window.parent.$("#idTicketInicial").val(idTicket);

						var dataSourceConsultaCatalogoTickets = new kendo.data.DataSource();
						dataSourceConsultaCatalogoTickets = response.listTicket;
						window.parent.ticketsDataExport = response.listTicket;

						window.parent
								.$("#GridConsultaCatalogoTickets")
								.kendoGrid(
										{
											dataSource : dataSourceConsultaCatalogoTickets,
											selectable : "row",
											resizable : true,
											sortable : true,
											reorderable : true,
											change : function() {
												var gview = window.parent
														.$(
																"#GridConsultaCatalogoTickets")
														.data("kendoGrid");
												var selectedItem = gview
														.dataItem(gview
																.select());
												if (selectedItem.estatus == 'ABIERTO'
														&& validaIngresoModulos(
																"TICKET",
																"PERMISO_A")) {
													window.parent.document
															.getElementById("darAtencionTicket").style.visibility = '';
													window.parent.document
															.getElementById("darProcesoTicket").style.visibility = '';
													window.parent
															.$(
																	"#darProcesoTicket")
															.prop("disabled",
																	true);
													window.parent
															.$(
																	"#darProcesoTicket")
															.removeClass(
																	"k-button");
													window.parent
															.$(
																	"#darAtencionTicket")
															.prop("disabled",
																	false);
													window.parent
															.$(
																	"#darAtencionTicket")
															.addClass(
																	"k-button");
													// window.parent.document.getElementById("crearImpresionTicket").style.visibility='';
												} else if ((selectedItem.estatus == 'EN PROCESO' || selectedItem.estatus == 'EJECUTADO')
														&& validaIngresoModulos(
																"TICKET",
																"PERMISO_M")) {
													window.parent.document
															.getElementById("darAtencionTicket").style.visibility = '';
													window.parent.document
															.getElementById("darProcesoTicket").style.visibility = '';
													// window.document.getElementById("crearImpresionTicket").style.visibility='';
													window.parent
															.$(
																	"#darProcesoTicket")
															.prop("disabled",
																	false);
													window.parent
															.$(
																	"#darProcesoTicket")
															.addClass(
																	"k-button");
													window.parent
															.$(
																	"#darAtencionTicket")
															.prop("disabled",
																	true);
													window.parent
															.$(
																	"#darAtencionTicket")
															.removeClass(
																	"k-button");
												} else if (selectedItem.estatus == 'CERRADO'
														|| selectedItem.estatus == 'RECHAZADO') {
													window.parent
															.$(
																	"#darAtencionTicket")
															.prop("disabled",
																	true);
													window.parent
															.$(
																	"#darAtencionTicket")
															.removeClass(
																	"k-button");
													window.parent
															.$(
																	"#darProcesoTicket")
															.prop("disabled",
																	true);
													window.parent
															.$(
																	"#darProcesoTicket")
															.removeClass(
																	"k-button");
													// window.document.getElementById("darAtencionTicket").style.visibility='hidden';
													// window.document.getElementById("darProcesoTicket").style.visibility='hidden';
													// window.document.getElementById("crearImpresionTicket").style.visibility='hidden';
												}
												;
											},
											pageable : {
												input : true,
												numeric : false,
												pageSizes : [ 50, 100, 150 ],
												messages : {
													display : "{1} de {2} registros",
													page : "Pagina",
													of : "de",
													itemsPerPage : "registros por pagina"
												}
											},
											scrollable : {
												virtual : true
											},
											columns : [
													{
														field : "idTicket",
														title : "#Ticket",
														width : 60
													},
													{
														field : "idFase",
														title : "Fase",
														width : 30
													},
													{
														field : "areatx",
														title : "Area",
														width : 70
													},
													{
														field : "idEquipo",
														title : "Equipo",
														width : 90
													},
													{
														field : "fechab",
														title : "Fecha Apertura",
														width : 70
													},
													{
														field : "fechat",
														title : "Fecha y Hora de Atenci&oacute;n",
														width : 110
													}, {
														field : "estatus",
														title : "Estatus",
														width : 80
													}, {
														field : "asunto",
														title : "Asunto",
														width : 170
													} ]
										});

						// window.parent.$("#GridConsultaCatalogoTickets").data("kendoGrid").dataSource.data([]);
						// window.parent.$("#GridConsultaCatalogoTickets").data("kendoGrid").dataSource.data(response.listTicket);
						// window.parent.$("#GridConsultaCatalogoTickets").data("kendoGrid").dataSource.data.read();
						// window.parent.$("#GridConsultaCatalogoTickets").data("kendoGrid").refresh();
					} else if (response.mensaje == "LOGOUT") {
						salirSistema();
					} else {
						window.parent.$("#mensajes_main").html(
								"Hubo un error: " + response.descripcion);
					}
				}
			});

}

/*
 * Funcion que valida el registro de tickets
 */
function ValidaRegistroTicket() {
	if ($("#idAreaTicket").val() == undefined || $("#idAreaTicket").val() == 0) {
		$("#mensajes_main").html("Debe introducir una area");
		$('#idAreaTicket').focus();
		return false;
	}
	if ($("#utSuperiorTicket").val() == undefined
			|| $.trim($("#utSuperiorTicket").val()) == "") {
		$("#mensajes_main").html("Debe introducir un unidad tecnica superior");
		$('#utSuperiorTicket').focus();
		return false;
	}
	if ($("#claveClienteTicket").val() == undefined
			|| $.trim($("#claveClienteTicket").val()) == "") {
		$("#mensajes_main").html("Debe introducir un cliente");
		$('#claveClienteTicket').focus();
		return false;
	}
	if ($("#claveVendedorTicket").val() == undefined
			|| $.trim($("#claveVendedorTicket").val()) == "") {
		$("#mensajes_main").html("Debe introducir un vendedor");
		$('#claveVendedorTicket').focus();
		return false;
	}
	if ($("#asuntoTicket").val() == undefined
			|| $.trim($("#asuntoTicket").val()) == "") {
		$("#mensajes_main").html("Debe introducir un asunto del ticket");
		$('#asuntoTicket').focus();
		return false;
	}
	if ($("#descripcionTicket").val() == undefined
			|| $.trim($("#descripcionTicket").val()) == "") {
		$("#mensajes_main").html("Debe introducir una descripcion del ticket");
		$('#descripcionTicket').focus();
		return false;
	} else if ($.trim($("#descripcionTicket").val()).length > 255) {
		$("#mensajes_main").html(
				"La descripcion no puede ser mayor a 255 caracteres");
		$('#descripcionTicket').focus();
		return false;
	}

	return true;
}

/*
 * Funcion que habilita los parametros de registro para el campo de cliente
 */
function dialogoParamsClienteRegistroTicket() {
	var windowParamsClienteRegistroTicket = $(
			"#windowParamsBusquedaClienteRegistroTicket").kendoWindow({
		actions : [ "Close" ],
		modal : true,
		resizable : false,
		content : {
			url : contexPath + "/ParamsClienteRegistroTicket.htm"
		},
		height : "350px",
		width : "450px"
	});
	windowParamsClienteRegistroTicket = $(
			"#windowParamsBusquedaClienteRegistroTicket").data("kendoWindow");
	windowParamsClienteRegistroTicket.center();
	windowParamsClienteRegistroTicket.open();
}

function dialogoParamsVerHistorial() {
	var windowParamsVerHistorial = $("#windowVerHistorial").kendoWindow({
		actions : [ "Close" ],
		modal : true,
		resizable : false,
		content : {
			url : contexPath + "/ParamsVerHistorial.htm"
		},
		height : "250px",
		width : "700px"
	});
	windowParamsVerHistorial = $("#windowVerHistorial").data("kendoWindow");
	windowParamsVerHistorial.center();
	windowParamsVerHistorial.open();
}

/*
 * Funcion que habilita el dialogo que contiene el grid de busqueda para el
 * cliente tickets
 */
function dialogoGridBusquedaClienteRegistroTicket(from) {
	var h = "450px";
	var w = "710px";

	if (from == "cotizador") {
		h = "450px";
		w = "550px";
	}

	var windowParamsClienteRegistroTicket = $(
			"#windowGridBusquedaClienteRegistroTicket").kendoWindow({
		actions : [ "Close" ],
		modal : true,
		resizable : false,
		content : {
			url : contexPath + "/ticket/GridBusquedaClienteRegistroTicket.htm"
		},
		height : h,
		width : w
	});
	windowParamsClienteRegistroTicket = $(
			"#windowGridBusquedaClienteRegistroTicket").data("kendoWindow");
	windowParamsClienteRegistroTicket.center();
	windowParamsClienteRegistroTicket.open();
}

/*
 * Funcion que permite presentar un grid donde se visualizaran los parametros de
 * busqueda para el campo cliente dentro del registro de tickets
 */
var arrClienteBusquedaParams = [];
function loadClienteGridRegistroTicket() {
	document.getElementById("paramCliente").setAttribute("style",
			"visibility:visible");
	var dataSourceCliente;
	// arrClienteBusquedaParams = [];
	if (window.parent.arrClienteBusquedaParams.length > 0) {
		dataSourceCliente = new kendo.data.DataSource({
			batch : true,
			data : window.parent.arrClienteBusquedaParams,
			schema : {
				model : {
					fields : {
						nombre : {
							validation : {
								required : false,
								min : 10
							}
						},
					}
				}
			}
		});
	} else {
		dataSourceCliente = new kendo.data.DataSource({
			batch : true,
			schema : {
				model : {
					fields : {
						nombre : {
							validation : {
								required : false,
								min : 10
							}
						},
					}
				}
			}
		});
	}
	$("#gridCliente").kendoGrid({
		dataSource : dataSourceCliente,
		resizable : true,
		height : 150,
		toolbar : [ {
			name : "create",
			text : "Agregar registro"
		} ],
		columns : [ {
			field : "nombre",
			title : "Nombres",
			width : "130px"
		}, {
			command : "destroy",
			title : " ",
			width : "80px"
		} ],
		editable : {
			update : true,
			destroy : true, // does not remove the row when it is deleted, but
							// marks it for deletion
			confirmation : "¿Esta seguro de eliminar esta fila?"
		}
	});
}

/*
 * 
 */
function dialogoParametrosBusquedaClienteTicket() {
	var windowSelecCarCli = $("#windowParamsBusquedaClienteRegistroTicket")
			.kendoWindow({
				actions : [ "Close" ],
				modal : true,
				resizable : false,
				content : {
					url : contexPath + "/SeleccionCarCli.htm"
				},
				height : "300px",
				title : "Parametros de Selección",
				width : "420px"
			});
	windowSelecCarCli = $("#windowParamsBusquedaClienteRegistroTicket").data(
			"kendoWindow");
	windowSelecCarCli.center();
	windowSelecCarCli.open();
}

/*
 * Agrega parametros de busqueda campo cliente para registro de ticket
 */
function addNomClienteParams() {
	var gridNom = $("#gridclientesTicket").data("kendoGrid");
	var model = gridNom.dataItem(gridNom.select());
	window.parent.$("#claveClienteTicket").val(model.id_car_cli);
	window.parent.$("#nomClienteTicket").html(
			model.nombre1 + " " + model.nombre2 + " " + model.app_pat + " "
					+ model.app_mat);
	var arrVendedorSeleccion = arrVendedores[model.id_car_cli];
	// alert (arrVendedorSeleccion);
	var userSalesManSap = "";
	var nombreSalesManSap = "";
	var idSalesManSap = 0;
	if (arrVendedorSeleccion != undefined) {
		for ( var i = 0; i < arrVendedorSeleccion.length; i++) {
			if (parseInt(arrVendedorSeleccion[i].id_visita) > idSalesManSap) {
				idSalesManSap = parseInt(arrVendedorSeleccion[i].id_visita);
				userSalesManSap = arrVendedorSeleccion[i].slsman;
				nombreSalesManSap = arrVendedorSeleccion[i].slsman_nombre1
						+ " "
						+ (arrVendedorSeleccion[i].slsman_app_pat != undefined ? arrVendedorSeleccion[i].slsman_app_pat
								: "")
						+ " "
						+ (arrVendedorSeleccion[i].slsman_app_mat != undefined ? arrVendedorSeleccion[i].slsman_app_mat
								: "");
			}
			// alert(arrVendedorSeleccion[i].id_visita);
			// alert(arrVendedorSeleccion[i].slsman);
			// alert(arrVendedorSeleccion[i].slsman_nombre1 + " " +
			// (arrVendedorSeleccion[i].slsman_app_pat != undefined ?
			// arrVendedorSeleccion[i].slsman_app_pat:"")+ " " +
			// (arrVendedorSeleccion[i].slsman_app_mat != undefined ?
			// arrVendedorSeleccion[i].slsman_app_mat:""));
		}
	}
	// alert (idSalesManSap);
	window.parent.$("#claveVendedorTicket").val(userSalesManSap);
	window.parent.$("#nomVendedorTicket").html(nombreSalesManSap);

	window.parent.$("#windowGridBusquedaClienteRegistroTicket").data(
			"kendoWindow").close();
}

/*
 * Cancela la edicion de parametros de busqueda campo cliente para registro de
 * ticket
 */
function cancelarNomClienteParams() {
	window.parent.$("#windowGridBusquedaClienteRegistroTicket").data(
			"kendoWindow").close();
}

function cancelarGridBusquedaCriteriosClienteTicket() {
	window.parent.$("#windowParamsBusquedaClienteRegistroTicket").data(
			"kendoWindow").close();
}
function cancelarGridFiltroBusquedaCriteriosClienteTicket() {
	window.parent.$("#windowConsultaCatalogoTicketFilter").data("kendoWindow")
			.close();
}

/*
 * Funcion reutilizada y copiada para la busqueda de clientes
 */
// funcion para concatenar los para,etros de envio de consulta de clientes y
// creacion de los data source de los mismos
function armaEstructuraCriterioClientesTicket() {
	_that = this;
	// get the form values
	if ($("#nomTxt").val() == "" && $("#nom2Txt").val() == ""
			&& $("#apPatTxt").val() == "" && $("#apMatTxt").val() == "") {
		kendoConsole.log("Capture criterios de busqueda", "error");
	} else {
		if ($("#nomTxt").val() != "" && this.dataNombre.length == 0) {
			xmlNom = "<criterios><criterio><nombre>" + $("#nomTxt").val()
					+ "</nombre></criterio></criterios>";
		} else if (this.dataNombre.length > 0) {
			var criteriosNom = [];
			for ( var i = 0; i < this.dataNombre.length; i++) {
				var params = {};
				if (this.dataNombre[i].nombre != "") {
					params["nombre"] = this.dataNombre[i].nombre;
					criteriosNom.push(params);
				}
			}
			xmlNom = createXMLCriterios("criterios", criteriosNom);
		}
		if ($("#nom2Txt").val() != "" && this.dataNombre2.length == 0) {
			xmlNom2 = "<criterios><criterio><nombre2>" + $("#nom2Txt").val()
					+ "</nombre2></criterio></criterios>";
		} else if (this.dataNombre2.length > 0) {
			var criteriosNom2 = [];
			for ( var i = 0; i < this.dataNombre2.length; i++) {
				var params2 = {};
				if (this.dataNombre2[i].nombre2 != "") {
					params2["nombre2"] = this.dataNombre2[i].nombre2;
					criteriosNom2.push(params2);
				}
			}
			xmlNom2 = createXMLCriterios("criterios", criteriosNom2);
		}
		if ($("#apPatTxt").val() != "" && this.dataApPaterno.length == 0) {
			xmlApPat = "<criterios><criterio><apPat>" + $("#apPatTxt").val()
					+ "</apPat></criterio></criterios>";
		} else if (this.dataApPaterno.length > 0) {
			var criteriosApPat = [];
			for ( var i = 0; i < this.dataApPaterno.length; i++) {
				var params3 = {};
				if (this.dataApPaterno[i].apPat != "") {
					params3["apPat"] = this.dataApPaterno[i].apPat;
					criteriosApPat.push(params3);
				}
			}
			xmlApPat = createXMLCriterios("criterios", criteriosApPat);
		}
		if ($("#apMatTxt").val() != "" && this.dataApMaterno.length == 0) {
			xmlApMat = "<criterios><criterio><apMat>" + $("#apMatTxt").val()
					+ "</apMat></criterio></criterios>";
		} else if (this.dataApMaterno.length > 0) {
			var criteriosApMat = [];
			for ( var i = 0; i < this.dataApMaterno.length; i++) {
				var params4 = {};
				if (this.dataApMaterno[i].apMat != "") {
					params4["apMat"] = this.dataApMaterno[i].apMat;
					criteriosApMat.push(params4);
				}
			}
			xmlApMat = createXMLCriterios("criterios", criteriosApMat);
		}
		window.parent.xmlNom = xmlNom;
		window.parent.xmlNom2 = xmlNom2;
		window.parent.xmlApPat = xmlApPat;
		window.parent.xmlApMat = xmlApMat;
		window.parent.ejecutaFiltroCriterioClientesTicket();
	}
}

// para enviar los xml de consulta en catalogo de cartera de clientes,
// dependiendo los criterios captirados
var arrVendedores = new Object();
function ejecutaFiltroCriterioClientesTicket() {
	$
			.ajax({
				type : "POST",
				cache : false,
				url : contexPath + "/CatalogoCarClientes.htm",
				data : "accion=2&xmlNom=" + xmlNom + "&xmlNom2=" + xmlNom2
						+ "&xmlApPat=" + xmlApPat + "&xmlApMat=" + xmlApMat,
				success : function(response) {
					// we have the response
					if (response.respGetInfCarCliente.mensaje == "SUCCESS") {
						$("#windowParamsBusquedaClienteRegistroTicket").data(
								"kendoWindow").close();
						$("#mensajes_main").html("Registros encontrados");

						clientesDataExport = response.respGetInfCarCliente.objClientesList;

						dataClient = [];

						for ( var i = 0; i < clientesDataExport.length; i++) {
							dataClient.push(clientesDataExport[i]);
							// alert (clientesDataExport[i].id_car_cli);
							// alert(clientesDataExport[i].visitasClienteList.length);
							arrVendedores[clientesDataExport[i].id_car_cli] = clientesDataExport[i].visitasClienteList;
						}

						var dataSourceClienteCar = new kendo.data.DataSource({
							data : dataClient,
							pageSize : 50
						});

						$("#gridclientesTicket").empty();

						$("#gridclientesTicket").kendoGrid({
							dataSource : dataSourceClienteCar,
							selectable : "row",
							resizable : true,
							sortable : true,
							reorderable : true,
							pageable : {
								input : true,
								numeric : false,
								pageSizes : [ 50, 100, 150 ],
								messages : {
									display : "{1} de {2} registros",
									page : "Pagina",
									of : "de",
									itemsPerPage : "registros por pagina"
								}
							},
							scrollable : {
								virtual : true
							},
							columns : [ {
								field : "id_car_cli",
								title : "Id Cliente",
								width : 100

							}, {
								field : "nombre1",
								title : "Nombre",
								sortable : true,
								width : 110
							}, {
								field : "nombre2",
								title : "Segundo Nombre",
								width : 110
							}, {
								field : "app_pat",
								title : "Apellido",
								width : 100
							}, {
								field : "app_mat",
								title : "Segundo Apellido",
								width : 100
							}, {
								field : "fch_ncm",
								title : "F.Nac",
								width : 100
							} ]
						});

					} else if (response.respGetInfCarCliente.mensaje == "LOGOUT") {
						window.parent.salirSistema();
					} else {
						// kendoConsole.log("Hubo un error en carga clientes:
						// "+response.respGetInfCarCliente.descripcion
						// ,"error");
						$("#mensajes_main")
								.html(
										"Hubo un error en carga clientes: "
												+ response.respGetInfCarCliente.descripcion);
						$("#gridclientesTicket").empty();
						reiniciaGridBusquedaClienteTicket();
					}
				},
				error : function(e) {

					// kendoConsole.log(e,"error");
					$("#mensajes_main").html(
							"Hubo un error en carga clientes: " + e);
				}
			});

}

function reiniciaGridBusquedaClienteTicket() {
	arrVendedores = new Object();
	var dataSourceClienteCar = new kendo.data.DataSource({
		data : [],
		pageSize : 50
	});

	$("#gridclientesTicket").kendoGrid({
		dataSource : dataSourceClienteCar,
		selectable : "row",
		resizable : true,
		sortable : true,
		reorderable : true,
		pageable : {
			input : true,
			numeric : false,
			pageSizes : [ 50, 100, 150 ],
			messages : {
				display : "{1} de {2} registros",
				page : "Pagina",
				of : "de",
				itemsPerPage : "registros por pagina"
			}
		},
		scrollable : {
			virtual : true
		},
		columns : [ {
			field : "id_car_cli",
			title : "Id Cliente",
			width : 100

		}, {
			field : "nombre1",
			title : "Nombre",
			sortable : true,
			width : 110
		}, {
			field : "nombre2",
			title : "Segundo Nombre",
			width : 110
		}, {
			field : "app_pat",
			title : "Apellido",
			width : 100
		}, {
			field : "app_mat",
			title : "Segundo Apellido",
			width : 100
		}, {
			field : "fch_ncm",
			title : "F.Nac",
			width : 100
		} ]
	});

}

function dialogoFiltroConsultaCatalogoTicket() {
	var windowSelecCarCli = $("#windowConsultaCatalogoTicketFilter")
			.kendoWindow(
					{
						actions : [ "Close" ],
						modal : true,
						resizable : false,
						content : {
							url : contexPath
									+ "/ticket/ConsultaCatalogoTicketsFilterView.htm"
						},
						height : "470px",
						width : "500px",
						title : "Parametros de Selección"
					});
	windowSelecCarCli = $("#windowConsultaCatalogoTicketFilter").data(
			"kendoWindow");
	windowSelecCarCli.center();
	windowSelecCarCli.open();
}

function dialogoFiltroReporteCatalogoTicket() {
	var windowSelecCarCli = $("#windowConsultaCatalogoTicketFilter")
			.kendoWindow(
					{
						actions : [ "Close" ],
						modal : true,
						resizable : false,
						content : {
							url : contexPath
									+ "/ticket/ConsultaReporteTicketsFilterView.htm"
						},
						height : "470px",
						width : "500px",
						title : "Parametros de Selección"
					});
	windowSelecCarCli = $("#windowConsultaCatalogoTicketFilter").data(
			"kendoWindow");
	windowSelecCarCli.center();
	windowSelecCarCli.open();
}

function dialogoFiltroConsultaEdoCtaReporte() {
	var windowSelecCarCli = $("#windowConsultaCatalogoTicketFilter")
			.kendoWindow(
					{
						actions : [ "Close" ],
						modal : true,
						resizable : false,
						content : {
							url : contexPath
									+ "/report/estadocuenta/ReporteEstadoCuentaFilterView.htm"
						},
						height : "550px",
						width : "810px",
						title : "Parametros de Selección"
					});
	windowSelecCarCli = $("#windowConsultaCatalogoTicketFilter").data(
			"kendoWindow");
	windowSelecCarCli.center();
	windowSelecCarCli.open();
}

// funcion para establecer la fecha de inicio
function fechaInicialFiltroTicketEvent() {
	var startDate = fechaInicialFiltroCatalogoTicket.val();

	if (startDate) {
		startDate = new Date(startDate);
		startDate.setDate(startDate.getDate() + 1);
		// fechaFinalFiltroCatalogoTicket.min(startDate);
	}
}

// funcion para establecer la fecha de inicio
function fechaInicialFechat() {
	var startDate = fechat.val();

	if (startDate) {
		startDate = new Date(startDate);
		startDate.setDate(startDate.getDate() + 1);
		// fechaFinalFiltroCatalogoTicket.min(startDate);
	}
}

// funcion para deshabilitar la fecha de fin dependiendo de la fecha de inicio
function fechaFinalFiltroTicketEvent() {
	var endDate = fechaFinalFiltroCatalogoTicket.val();

	if (endDate) {
		endDate = new Date(endDate);
		endDate.setDate(endDate.getDate() - 1);
		// fechaInicialFiltroCatalogoTicket.max(endDate);
	}
}

/*
 * 
 */
var loadComboAreasRegistroTicket = false;
function fillComboRegistroTicket(accion) {
	var datosAreasList = $("#idArea").data('kendoComboBox');
	var itemAreas = datosAreasList.dataItem();
	var datosFaseList = $("#idFase").data('kendoComboBox');
	var itemFases = datosFaseList.dataItem();
	var datosEquiposList = $("#idEquipo").data('kendoComboBox');
	var itemEquipos = datosEquiposList.dataItem();

	var iduts = "";
	if (accion == "faces") {
		/*
		 * if(itemFases != undefined) iduts=itemFases.ubicacionClave;
		 */
	} else if (accion == "equipos") {
		if (itemFases != undefined)
			iduts = itemFases.ubicacionClave;
	}
	// alert (iduts);
	// onVentanaWait("Espere ", true)
	$
			.ajax({
				type : "POST",
				cache : false,
				async : false,
				url : contexPath
						+ "/ticket/LlenaCombosInicialesRegistroTicket.htm",
				data : "accion=" + accion + "&i_id_ut_current=" + iduts,
				success : function(response) {
					// we have the response
					if (response.respGetUnidadesTecnicasSuperiores != undefined) {
						if (response.respGetUnidadesTecnicasSuperiores.mensaje != undefined
								&& response.respGetUnidadesTecnicasSuperiores.mensaje == "SUCCESS") {
							// $.unblockUI()
							var uts_num = response.respGetUnidadesTecnicasSuperiores.objUbicacionesList;
							var ute_num = response.respGetUnidadesTecnicasSuperiores.objEquiposList;
							carpetas_dig = response.respGetUnidadesTecnicasSuperiores.carpetasDigitalizacion;
							carpetas_dig_ = response.respGetUnidadesTecnicasSuperiores.carpetasDigitalizacion;

							if (accion == "areas"
									&& !loadComboAreasRegistroTicket) {
								carpetasTicket = response.respGetUnidadesTecnicasSuperiores.carpetasTicket;
								loadComboAreasRegistroTicket = true;
								$("#mensajes_main").html(
										"Se encontraron " + uts_num.length
												+ " registros");

								datosAreasList.dataSource.data([]);
								datosAreasList.dataSource.data(carpetasTicket);
								datosAreasList.select(0);
								itemAreas = datosAreasList.dataItem();
								// $("#idArea").html(itemAreas.text);
							}
							if (uts_num.length >= 1 || ute_num.length >= 1) {
								if (accion == "faces") {
									$("#mensajes_main").html(
											"Se encontraron " + uts_num.length
													+ " registros");

									datosFaseList.dataSource.data([]);
									datosFaseList.dataSource.data(uts_num);
									datosFaseList.select(0);
									itemFases = datosFaseList.dataItem();
									// $("#txt_desc_fase").html(itemFases.text);
									fillComboRegistroTicket("equipos");
								} else if (accion == "equipos") {
									$("#mensajes_main").html(
											"Se encontraron " + ute_num.length
													+ " registros");

									datosEquiposList.dataSource.data([]);
									datosEquiposList.dataSource.data(ute_num);
									datosEquiposList.select(0);
									itemEquipos = datosEquiposList.dataItem();
									$("#txt_desc_equ").html(
											itemEquipos.id_equnrx);

									// datosTiposList.dataSource.data([]);
									// datosTiposList.dataSource.data(getProcesos("proceso","00"));
									// datosTiposList.select(0);
									// itemTipos = datosTiposList.dataItem();
									//	    			  
									// datosSubTiposList.dataSource.data([]);
									// datosSubTiposList.dataSource.data(getProcesos("",itemTipos.id_ticket_file));
									// datosSubTiposList.select(0);
								}
							} else {
								// $.unblockUI()
								$("#mensajes_main")
										.html(
												"No se encontraron ubicaciones para esta sesion");
							}

						} else {
							// $.unblockUI()
							$("#mensajes_main")
									.html(
											response.respGetUnidadesTecnicasSuperiores.descripcion);
						}
					}

				},
				error : function(e) {
					// $.unblockUI()
					$("#mensajes_main").html(
							"Fallo el acceso a los datos " + e.responseText
									+ " " + e.status);
				}
			});
}

function onChangeEstausProcesoTicket(e) {
	var datosEstatusList = $("#estatus").data('kendoComboBox');
	var itemEstatus = datosEstatusList.dataItem();

	if (itemEstatus.ticket_val == "CERRAR") {
		$("#labelPDF").show();
		$("#fileData").show();
	} else {
		$("#labelPDF").hide();
		$("#fileData").hide();
	}
	;
}

function onChangeFacesRegistroTicket(e) {
	// var datosFaseList = $("#idFase").data('kendoComboBox');
	// var itemFases = datosFaseList.dataItem();
	// $("#txt_desc_fase").html(itemFases.text);
	fillComboRegistroTicket("equipos");
}

function onChangeEquipoRegistroTicket(e) {
	// var datosEquiposList = $("#equipoTicket").data('kendoComboBox');
	// var itemEquipos = datosEquiposList.dataItem();
	// $("#txt_desc_equ").html(itemEquipos.id_equnrx);
	fillDatosClienteTicket();
}

function onChangeAreaRegistroTicket(e) {
	var datosAreaList = $("#idArea").data('kendoComboBox');
	var itemArea = datosAreaList.dataItem();
	// $("#nomAreaTicket").html(itemArea.id_ticket_areax);
	if (itemArea.id_ticket_areax == "CONSTRUCCIÓN"
			|| itemArea.id_ticket_areax == "ADMINISTRACIÓN") {
		// $("#tblFormTicket").hide();
		$(".tblFormVicios").show();
		$(".tblFormTicket").hide();
		// alert(itemArea.id_ticket_areax);
	} else {
		$(".tblFormVicios").hide();
		$(".tblFormTicket").show();
	}
}

function validaParametrosBusqueda() {
	if ($.trim($("#idTicketInicial")) != ""
			&& $.trim($("#idTicketFinal")) == "") {
		$("#mensajes_main").html("Debe especificar un ticket final");
		$("#idTicketFinal").focus();
		return false;
	}
	if ($.trim($("#idTicketFinal")) != ""
			&& $.trim($("#idTicketInicial")) == "") {
		$("#mensajes_main").html("Debe especificar un ticket de inicial");
		$("#idTicketInicial").focus();
		return false;
	}
	if ($.trim($("#fechaInicialFiltroCatalogoTicket")) != ""
			&& $.trim($("#fechaFinalFiltroCatalogoTicket")) == "") {
		$("#mensajes_main").html("Debe especificar una fecha final");
		$("#fechaFinalFiltroCatalogoTicket").focus();
		return false;
	}
	if ($.trim($("#fechaFinalFiltroCatalogoTicket")) != ""
			&& $.trim($("#fechaInicialFiltroCatalogoTicket")) == "") {
		$("#mensajes_main").html("Debe especificar una fecha inicial");
		$("#fechaInicialFiltroCatalogoTicket").focus();
		return false;
	}

	return true;
}

function verDetalleTicket() {
	var grid = window.parent.$("#GridConsultaCatalogoTickets")
			.data("kendoGrid");
	var model = grid.dataItem(grid.select());
	if (model == undefined) {
		alert("Requiere seleccionar un ticket");
		return;
	} else if (model.areatx == 'CONSTRUCCIÓN'
			|| model.areatx == 'ADMINISTRACIÓN') {

		var windowSelecCarCli1 = $("#windowConsultaConstruccionTicketDetalle")
				.kendoWindow(
						{
							actions : [ "Close" ],
							modal : true,
							resizable : false,
							content : {
								url : contexPath
										+ "/ticket/ConsultaConstruccionTicketsDetalleView.htm"
							},
							height : "550px",
							width : "1000px",
							title : "Detalle de Ticket"
						});
		windowSelecCarCli1 = $("#windowConsultaConstruccionTicketDetalle")
				.data("kendoWindow");
		windowSelecCarCli1.center();
		windowSelecCarCli1.open();

	} else {
		var windowSelecCarCli1 = $("#windowConsultaTicketDetalle").kendoWindow(
				{
					actions : [ "Close" ],
					modal : true,
					resizable : false,
					content : {
						url : contexPath
								+ "/ticket/ConsultaTicketsDetalleView.htm"
					},
					height : "550px",
					width : "1000px",
					title : "Detalle de Ticket"
				});
		windowSelecCarCli1 = $("#windowConsultaTicketDetalle").data(
				"kendoWindow");
		windowSelecCarCli1.center();
		windowSelecCarCli1.open();
	}

}

function darAtencionTicket() {
	var grid = window.parent.$("#GridConsultaCatalogoTickets")
			.data("kendoGrid");
	var model = grid.dataItem(grid.select());
	if (model == undefined) {
		alert("Requiere seleccionar un ticket");
		return;
	} else if (model.areatx == 'CONSTRUCCIÓN' || model.areatx == 'ADMINISTRACIÓN') {
		
		var windowSelecCarCli = $("#windowAtencionConstruccionTicket")
		.kendoWindow(
				{
					actions : [ "Close" ],
					modal : true,
					resizable : false,
					content : {
						url : contexPath
								+ "/ticket/AtencionConstruccionTicketsView.htm"
					},
					height : "550px",
					width : "1000px",
					title : "Ticket en Atención"
				});
		windowSelecCarCli = $("#windowAtencionConstruccionTicket").data(
				"kendoWindow");
		windowSelecCarCli.center();
		windowSelecCarCli.open();

	} else {
		
		var windowSelecCarCli = $("#windowAtencionTicket").kendoWindow({
			actions : [ "Close" ],
			modal : true,
			resizable : false,
			content : {
				url : contexPath + "/ticket/AtencionTicketsView.htm"
			},
			height : "550px",
			width : "1000px",
			title : "Ticket en Atención"
		});
		windowSelecCarCli = $("#windowAtencionTicket").data("kendoWindow");
		windowSelecCarCli.center();
		windowSelecCarCli.open();
		

	}
}

function darProcesoTicket() {
	var grid = window.parent.$("#GridConsultaCatalogoTickets")
			.data("kendoGrid");
	var model = grid.dataItem(grid.select());
	if (model == undefined) {
		alert("Requiere seleccionar un ticket");
		return;
	} else {

		var windowSelecCarCli = $("#windowProcesoConstruccionTicket")
				.kendoWindow(
						{
							actions : [ "Close" ],
							modal : true,
							resizable : false,
							content : {
								url : contexPath
										+ "/ticket/ProcesoConstruccionTicketsView.htm"
							},
							height : "550px",
							width : "1000px",
							title : "Ticket en Proceso"
						});
		windowSelecCarCli = $("#windowProcesoConstruccionTicket").data(
				"kendoWindow");
		windowSelecCarCli.center();
		windowSelecCarCli.open();
	}
}

function cargaDetalle() {

}
function cancelVerDetalleTicket() {
	window.parent.$("#windowConsultaCatalogoTicketDetalle").data("kendoWindow")
			.close();
}

function cancelVerDetalle() {
	window.parent.$("#windowConsultaTicketDetalle").data("kendoWindow").close();
}

function cancelVerDetalleVicio() {
	window.parent.$("#windowConsultaConstruccionTicketDetalle").data(
			"kendoWindow").close();
}

function cancelAtencionVicio() {
	window.parent.$("#windowAtencionConstruccionTicket").data("kendoWindow")
			.close();
}

function cancelAtencion() {
	window.parent.$("#windowAtencionTicket").data("kendoWindow").close();
}

function cancelProcesoVicio() {
	window.parent.$("#windowProcesoConstruccionTicket").data("kendoWindow")
			.close();
}

function cargaValoresDetalle() {

	var grid = window.parent.$("#GridConsultaCatalogoTickets")
			.data("kendoGrid");
	var model = grid.dataItem(grid.select());
	if (model == undefined) {
		alert("Requiere seleccionar un ticket");
		return;
	}
	var index = -1;
	for ( var i = 0; i < window.parent.ticketsDataExport.length; i++) {
		if (window.parent.ticketsDataExport[i].idTicket == model.idTicket) {
			index = i;
			break;
		}
	}
	// alert(model.idTicket);

	$("#idTicket").html(window.parent.ticketsDataExport[index].idTicket);
	$("#idAreaTicket").html(window.parent.ticketsDataExport[index].idArea);
	$("#nomAreaTicket").html(
			window.parent.ticketsDataExport[index].nomAreaTicket);
	$("#utSuperiorTicket").html(
			window.parent.ticketsDataExport[index].idUtSuperior);

	$("#claveClienteTicket").html(
			window.parent.ticketsDataExport[index].idCliente);
	$("#nomClienteTicket").html(
			window.parent.ticketsDataExport[index].nombre1Cliente + " "
					+ window.parent.ticketsDataExport[index].nombre2Cliente
					+ " "
					+ window.parent.ticketsDataExport[index].appaternoCliente
					+ " "
					+ window.parent.ticketsDataExport[index].apmaternoCliente);
	$("#faseTicket").html(window.parent.ticketsDataExport[index].idFase);
	$("#txt_desc_fase").html(
			window.parent.ticketsDataExport[index].nomAreaTicket);
	$("#claveVendedorTicket").html(
			window.parent.ticketsDataExport[index].idVendedor);
	$("#nomVendedorTicket").html(
			window.parent.ticketsDataExport[index].nombre1Vendedor + " "
					+ window.parent.ticketsDataExport[index].nombre2Vendedor
					+ " "
					+ window.parent.ticketsDataExport[index].appaternoVendedor
					+ " "
					+ window.parent.ticketsDataExport[index].apmaternoVendedor);
	$("#equipoTicket").html(window.parent.ticketsDataExport[index].idEquipo);
	$("#txt_desc_equ").html(
			window.parent.ticketsDataExport[index].nomAreaTicket);
	$("#asuntoTicket").html(window.parent.ticketsDataExport[index].asunto);
	$("#descripcionTicket").val(
			window.parent.ticketsDataExport[index].descripcion);
}

function cargaValoresConstruccion() {

	var grid = window.parent.$("#GridConsultaCatalogoTickets")
			.data("kendoGrid");
	var model = grid.dataItem(grid.select());
	if (model == undefined) {
		alert("Requiere seleccionar un ticket");
		return;
	}
	var index = -1;
	for ( var i = 0; i < window.parent.ticketsDataExport.length; i++) {
		if (window.parent.ticketsDataExport[i].idTicket == model.idTicket) {
			index = i;
			break;
		}
	}
	// alert(model.idTicket);

	$("#idTicket").html(window.parent.ticketsDataExport[index].idTicket);
	$("#idTicketHidden").val(window.parent.ticketsDataExport[index].idTicket);
	$("#txtArea").html(window.parent.ticketsDataExport[index].areatx);
	$("#idFase").html(window.parent.ticketsDataExport[index].idFase);
	$("#idEquipo").html(window.parent.ticketsDataExport[index].idEquipo);
	$("#cliente").html(window.parent.ticketsDataExport[index].cliente);
	$("#prioridad").html(
			"Prioridad de la solicitud: "
					+ window.parent.ticketsDataExport[index].prioridad);
	$("#asunto").html(window.parent.ticketsDataExport[index].asunto);
	$("#observaciones").html(
			window.parent.ticketsDataExport[index].observaciones);
	// $("#asignado").html(window.parent.ticketsDataExport[index].asignado);
	$("#asignado").html(model.asignado);
	// $("#aprobado").html(window.parent.ticketsDataExport[index].aprobado);
	$("#aprobado").html(model.aprobado);
	// $("#recibido").html(window.parent.ticketsDataExport[index].recibido);
	$("#recibido").html(model.recibido);
	// $("#estatus").html(window.parent.ticketsDataExport[index].estatus);
	$("#estatus").html(model.estatus);
	// $("#fechat").html(window.parent.ticketsDataExport[index].fechat);
	$("#fechat").html(model.fechat);
	$("#ccp").html(model.ccp);

	var nuevaFila = "";

	if (window.parent.ticketsDataExport[index].areatx == 'CONSTRUCCIÓN' || window.parent.ticketsDataExport[index].areatx == 'ADMINISTRACIÓN') {

		for ( var y = 0; y < window.parent.ticketsDataExport[index].listTicketDetail.length; y++) {

			nuevaFila += "<tr>";

			// Aniadimos una columna con el numero total de columnas.
			// Aniadimos uno al total, ya que cuando cargamos los valores para
			// la
			// columna.

			nuevaFila += "<td width=\"23px\" style=\"text-align:center\"> <div id=\"conse["
					+ y + "]\"> </td>";
			nuevaFila += "<td width=\"110px\" style=\"text-align:left\"> <div id=\"zona["
					+ y + "]\"> </td>";
			nuevaFila += "<td width=\"165px\" align=\"left\" style=\"text-align:left\"> <div id=\"vicio["
					+ y + "]\">  </td>";
			nuevaFila += "<td width=\"20px\" style=\"text-align:center\"><div id=\"foto["
					+ y + "]\"></td>";
			nuevaFila += "<td width=\"70px\" style=\"text-align:center\"> <div id=\"estatusDet["
					+ y + "]\"> </td>";
			nuevaFila += "<td width=\"110px\" style=\"text-align:left\"> <div id=\"motivo["
					+ y + "]\"> </td>";
			nuevaFila += "</tr>";
		}

	} else {

		nuevaFila += "<tr>";

		// Aniadimos una columna con el numero total de columnas.
		// Aniadimos uno al total, ya que cuando cargamos los valores para la
		// columna.
		for ( var y = 0; y < window.parent.ticketsDataExport[index].listTicketDetail.length; y++) {
			nuevaFila += "<td width=\"25px\" style=\"text-align:center\"> <div id=\"conse["
					+ y + "]\"> </td>";
			nuevaFila += "<td width=\"473px\" align=\"left\" style=\"text-align:center\"> <div id=\"vicio["
					+ y + "]\">  </td>";
			nuevaFila += "</tr>";
		}

	}

	$("#dg_vicios").append(nuevaFila);

	if (window.parent.ticketsDataExport[index].areatx == 'CONSTRUCCIÓN' || window.parent.ticketsDataExport[index].areatx == 'ADMINISTRACIÓN') {

		for ( var n = 0; n < window.parent.ticketsDataExport[index].listTicketDetail.length; n++) {

			document.getElementById("conse[" + n + "]").innerHTML = window.parent.ticketsDataExport[index].listTicketDetail[n].consecutivo;
			document.getElementById("zona[" + n + "]").innerHTML = window.parent.ticketsDataExport[index].listTicketDetail[n].zona;
			document.getElementById("vicio[" + n + "]").innerHTML = window.parent.ticketsDataExport[index].listTicketDetail[n].descripcion;
			// document.getElementById("estatusDet[" + n + "]").innerHTML =
			// window.parent.ticketsDataExport[index].listTicketDetail[n].estatus;
			document.getElementById("estatusDet[" + n + "]").innerHTML = model.listTicketDetail[n].estatus;

			if (window.parent.ticketsDataExport[index].listTicketDetail[n].foto == "SI") {
				document.getElementById("foto[" + n + "]").innerHTML = "<a href=\""
						+ contexPath
						+ "/ticket/GetRegistroFotografico.htm?idTicket="
						+ window.parent.ticketsDataExport[index].listTicketDetail[n].idTicket
						+ "-"
						+ window.parent.ticketsDataExport[index].listTicketDetail[n].consecutivo
						+ "\"><img src=\""
						+ contexPath
						+ "\/images/images/descargar.jpg\" border=\"0\" /></a>";
			} else {
				// document.getElementById("foto[" + n + "]").innerHTML = "NO";
				document.getElementById("foto[" + n + "]").innerHTML = "<a id=\"myLink\" title=\"Carga Imagenes\" href=\"#\" onclick=\"dialogoCargaImagenes();return false;\"><img src=\""
						+ contexPath
						+ "\/images/images/upload.jpg\" border=\"0\" /></a>";
			}
			// document.getElementById("motivo[" + n + "]").innerHTML =
			// window.parent.ticketsDataExport[index].listTicketDetail[n].motivo;
			document.getElementById("motivo[" + n + "]").innerHTML = model.listTicketDetail[n].motivo;
		}
	} else {

		for ( var x = 0; x < window.parent.ticketsDataExport[index].listTicketDetail.length; x++) {

			document.getElementById("conse[" + x + "]").innerHTML = window.parent.ticketsDataExport[index].listTicketDetail[x].consecutivo;
			document.getElementById("vicio[" + x + "]").innerHTML = window.parent.ticketsDataExport[index].listTicketDetail[x].descripcion;
		}

	}

	if (model.estatus == "CERRADO") {
		document.getElementById("labelCierre").innerHTML = "PDF Cierre:";
		document.getElementById("linkPDF").innerHTML = "<a href=\""
				+ contexPath + "/ticket/GetRegistroPDF.htm?idTicket="
				+ model.idTicket + "\">DESCARGAR</a>";
	}

	fillDatosClienteTicketById(window.parent.ticketsDataExport[index].idEquipo);

}

function cargaValoresAtencion() {

	var grid = window.parent.$("#GridConsultaCatalogoTickets")
			.data("kendoGrid");
	var model = grid.dataItem(grid.select());
	if (model == undefined) {
		alert("Requiere seleccionar un ticket");
		return;
	}
	var index = -1;
	for ( var i = 0; i < window.parent.ticketsDataExport.length; i++) {
		if (window.parent.ticketsDataExport[i].idTicket == model.idTicket) {
			index = i;
			break;
		}
	}
	// alert(model.idTicket);

	$("#idTicket").html(window.parent.ticketsDataExport[index].idTicket);
	$("#idTicketHidden").val(window.parent.ticketsDataExport[index].idTicket);
	$("#txtArea").html(window.parent.ticketsDataExport[index].areatx);
	$("#idFase").html(window.parent.ticketsDataExport[index].idFase);
	$("#idFaseHidden").val(window.parent.ticketsDataExport[index].idFase);
	$("#idEquipo").html(window.parent.ticketsDataExport[index].idEquipo);
	$("#idEquipoHidden").val(window.parent.ticketsDataExport[index].idEquipo);
	$("#cliente").html(window.parent.ticketsDataExport[index].cliente);
	$("#clienteHidden").val(window.parent.ticketsDataExport[index].cliente);
	$("#prioridad").html(
			"Prioridad de la solicitud: "
					+ window.parent.ticketsDataExport[index].prioridad);
	$("#prioridadHidden").val(window.parent.ticketsDataExport[index].prioridad);
	$("#recibido").html(window.parent.ticketsDataExport[index].recibido);
	$("#recibidoHidden").html(window.parent.ticketsDataExport[index].recibido);
	$("#asunto").html(window.parent.ticketsDataExport[index].asunto);
	$("#asuntoHidden").html(window.parent.ticketsDataExport[index].asunto);
	$("#observaciones").html(
			window.parent.ticketsDataExport[index].observaciones);
	// $("#observacionesHidden").val(window.parent.ticketsDataExport[index].observaciones);
	$("#estatus").html(window.parent.ticketsDataExport[index].estatus);
	// $("#estatusHidden").val(window.parent.ticketsDataExport[index].estatus);
	$("#ccp").html(window.parent.ticketsDataExport[index].ccp);

	var nuevaFila = "";

	if (window.parent.ticketsDataExport[index].areatx == 'CONSTRUCCIÓN') {

		for ( var y = 0; y < window.parent.ticketsDataExport[index].listTicketDetail.length; y++) {

			nuevaFila += "<tr>";

			// Aniadimos una columna con el numero total de columnas.
			// Aniadimos uno al total, ya que cuando cargamos los valores para
			// la
			// columna.

			nuevaFila += "<td width=\"23px\" style=\"text-align:center\"> <div id=\"conse["
					+ y
					+ "]\"></div> <input type=\"hidden\" id=\"conse["
					+ y
					+ "]Hidden\" name=\"conse\"> </td>";
			nuevaFila += "<td width=\"110px\" style=\"text-align:left\"> <div id=\"zona["
					+ y
					+ "]\"></div> <input type=\"hidden\" id=\"zona["
					+ y
					+ "]Hidden\" name=\"zona\"> </td>";
			nuevaFila += "<td width=\"165px\" align=\"left\" style=\"text-align:left\"> <div id=\"vicio["
					+ y
					+ "]\"> </div> <input type=\"hidden\" id=\"vicio["
					+ y
					+ "]Hidden\" name=\"vicio\"> </td>";
			nuevaFila += "<td width=\"20px\" style=\"text-align:center\"> <div id=\"foto["
					+ y + "]\"> </td>";
			nuevaFila += "<td width=\"70px\" style=\"text-align:center\">"
					+ "<select style=\"font-size: 10px !important; font-family:Century Gothic\" name=\"estatusDet\" id=\"estatusDet["
					+ y + "]\"> "
					+ "<option value=\"ACEPTADO\">ACEPTADO</option> "
					+ "<option value=\"RECHAZADO\">RECHAZADO</option> "
					+ "<option value=\"EN REVISION\">EN REVISION</option> "
					+ "</select> </td>";
			nuevaFila += "<td width=\"110px\" style=\"text-align:left\"> <input type=\"text\" class=\"k-textbox\" name=\"motivo\" id=\"motivo["
					+ y
					+ "]\" style=\"width: 100%; text-transform: uppercase\" onblur=\"javascript:this.value=this.value.toUpperCase().trim();\" onkeypress=\"return letrasnumeros(event)\" maxlength=\"125\"> </td>";
			nuevaFila += "</tr>";
		}
	} else if(window.parent.ticketsDataExport[index].areatx == 'ADMINISTRACIÓN'){
		
		for ( var y = 0; y < window.parent.ticketsDataExport[index].listTicketDetail.length; y++) {

			nuevaFila += "<tr>";

			// Aniadimos una columna con el numero total de columnas.
			// Aniadimos uno al total, ya que cuando cargamos los valores para
			// la
			// columna.

			nuevaFila += "<td width=\"23px\" style=\"text-align:center\"> <div id=\"conse["
					+ y + "]\"> </td>";
			nuevaFila += "<td width=\"110px\" style=\"text-align:left\"> <div id=\"zona["
					+ y + "]\"> </td>";
			nuevaFila += "<td width=\"165px\" align=\"left\" style=\"text-align:left\"> <div id=\"vicio["
					+ y + "]\">  </td>";
			nuevaFila += "<td width=\"20px\" style=\"text-align:center\"><div id=\"foto["
					+ y + "]\"></td>";
			nuevaFila += "<td width=\"70px\" style=\"text-align:center\"> <div id=\"estatusDet["
					+ y + "]\"> </td>";
			nuevaFila += "<td width=\"110px\" style=\"text-align:left\"> <div id=\"motivo["
					+ y + "]\"> </td>";
			nuevaFila += "</tr>";
		}
		
	} else {

		nuevaFila += "<tr>";

		// Aniadimos una columna con el numero total de columnas.
		// Aniadimos uno al total, ya que cuando cargamos los valores para la
		// columna.
		for ( var y = 0; y < window.parent.ticketsDataExport[index].listTicketDetail.length; y++) {
			nuevaFila += "<td width=\"25px\" style=\"text-align:center\"> <div id=\"conse["
					+ y + "]\"> </td>";
			nuevaFila += "<td width=\"473px\" align=\"left\" style=\"text-align:center\"> <div id=\"vicio["
					+ y + "]\">  </td>";
			nuevaFila += "</tr>";
		}
	}

	$("#dg_vicios").append(nuevaFila);

	if (window.parent.ticketsDataExport[index].areatx == 'CONSTRUCCIÓN') {

		for ( var n = 0; n < window.parent.ticketsDataExport[index].listTicketDetail.length; n++) {

			document.getElementById("conse[" + n + "]").innerHTML = window.parent.ticketsDataExport[index].listTicketDetail[n].consecutivo;
			document.getElementById("conse[" + n + "]Hidden").value = window.parent.ticketsDataExport[index].listTicketDetail[n].consecutivo;
			document.getElementById("zona[" + n + "]").innerHTML = window.parent.ticketsDataExport[index].listTicketDetail[n].zona;
			document.getElementById("zona[" + n + "]Hidden").value = window.parent.ticketsDataExport[index].listTicketDetail[n].zona;
			document.getElementById("vicio[" + n + "]").innerHTML = window.parent.ticketsDataExport[index].listTicketDetail[n].descripcion;
			document.getElementById("vicio[" + n + "]Hidden").value = window.parent.ticketsDataExport[index].listTicketDetail[n].descripcion;

			if (window.parent.ticketsDataExport[index].listTicketDetail[n].foto == "SI") {
				document.getElementById("foto[" + n + "]").innerHTML = "<a href=\""
						+ contexPath
						+ "/ticket/GetRegistroFotografico.htm?idTicket="
						+ window.parent.ticketsDataExport[index].listTicketDetail[n].idTicket
						+ "-"
						+ window.parent.ticketsDataExport[index].listTicketDetail[n].consecutivo
						+ "\"><img src=\""
						+ contexPath
						+ "\/images/images/descargar.jpg\" border=\"0\" /></a>";
			} else {
				document.getElementById("foto[" + n + "]").innerHTML = "NO";
				// document.getElementById("foto[" + n + "]").innerHTML = "<a
				// id=\"myLink\" title=\"Carga Imagenes\" href=\"#\"
				// onclick=\"dialogoCargaImagenes();return false;\"><img
				// src=\""+ contexPath +"\/images/images/upload.jpg\"
				// border=\"0\" /></a>";
			}
		}

	} else if(window.parent.ticketsDataExport[index].areatx == 'ADMINISTRACIÓN'){
		
		for ( var n = 0; n < window.parent.ticketsDataExport[index].listTicketDetail.length; n++) {

			document.getElementById("conse[" + n + "]").innerHTML = window.parent.ticketsDataExport[index].listTicketDetail[n].consecutivo;
			document.getElementById("zona[" + n + "]").innerHTML = window.parent.ticketsDataExport[index].listTicketDetail[n].zona;
			document.getElementById("vicio[" + n + "]").innerHTML = window.parent.ticketsDataExport[index].listTicketDetail[n].descripcion;
			// document.getElementById("estatusDet[" + n + "]").innerHTML =
			// window.parent.ticketsDataExport[index].listTicketDetail[n].estatus;
			document.getElementById("estatusDet[" + n + "]").innerHTML = model.listTicketDetail[n].estatus;

			if (window.parent.ticketsDataExport[index].listTicketDetail[n].foto == "SI") {
				document.getElementById("foto[" + n + "]").innerHTML = "<a href=\""
						+ contexPath
						+ "/ticket/GetRegistroFotografico.htm?idTicket="
						+ window.parent.ticketsDataExport[index].listTicketDetail[n].idTicket
						+ "-"
						+ window.parent.ticketsDataExport[index].listTicketDetail[n].consecutivo
						+ "\"><img src=\""
						+ contexPath
						+ "\/images/images/descargar.jpg\" border=\"0\" /></a>";
			} else {
				// document.getElementById("foto[" + n + "]").innerHTML = "NO";
				document.getElementById("foto[" + n + "]").innerHTML = "<a id=\"myLink\" title=\"Carga Imagenes\" href=\"#\" onclick=\"dialogoCargaImagenes();return false;\"><img src=\""
						+ contexPath
						+ "\/images/images/upload.jpg\" border=\"0\" /></a>";
			}
			// document.getElementById("motivo[" + n + "]").innerHTML =
			// window.parent.ticketsDataExport[index].listTicketDetail[n].motivo;
			document.getElementById("motivo[" + n + "]").innerHTML = model.listTicketDetail[n].motivo;
		}
		
		
	} else {

		for ( var x = 0; x < window.parent.ticketsDataExport[index].listTicketDetail.length; x++) {

			document.getElementById("conse[" + x + "]").innerHTML = window.parent.ticketsDataExport[index].listTicketDetail[x].consecutivo;
			document.getElementById("vicio[" + x + "]").innerHTML = window.parent.ticketsDataExport[index].listTicketDetail[x].descripcion;
		}

	}

	fillDatosClienteTicketById(window.parent.ticketsDataExport[index].idEquipo);

}

function cargaValoresProceso() {

	var grid = window.parent.$("#GridConsultaCatalogoTickets")
			.data("kendoGrid");
	var model = grid.dataItem(grid.select());
	if (model == undefined) {
		alert("Requiere seleccionar un ticket");
		return;
	}
	var index = -1;
	for ( var i = 0; i < window.parent.ticketsDataExport.length; i++) {
		if (window.parent.ticketsDataExport[i].idTicket == model.idTicket) {
			index = i;
			break;
		}
	}
	// alert(model.idTicket);

	$("#labelPDF").hide();
	$("#fileData").hide();

	$("#idTicket").html(window.parent.ticketsDataExport[index].idTicket);
	$("#idTicketHidden").val(window.parent.ticketsDataExport[index].idTicket);
	// $("#idAreaTicket").html(window.parent.ticketsDataExport[index].idArea);
	$("#idFase").html(window.parent.ticketsDataExport[index].idFase);
	$("#idFaseHidden").val(window.parent.ticketsDataExport[index].idFase);
	$("#idEquipo").html(window.parent.ticketsDataExport[index].idEquipo);
	$("#idEquipoHidden").val(window.parent.ticketsDataExport[index].idEquipo);
	$("#cliente").html(window.parent.ticketsDataExport[index].cliente);
	$("#clienteHidden").val(window.parent.ticketsDataExport[index].cliente);
	$("#prioridad").html(
			"Prioridad de la solicitud: "
					+ window.parent.ticketsDataExport[index].prioridad);
	$("#prioridadHidden").val(window.parent.ticketsDataExport[index].prioridad);
	$("#asunto").html(window.parent.ticketsDataExport[index].asunto);
	$("#asuntoHidden").html(window.parent.ticketsDataExport[index].asunto);
	$("#observaciones").html(
			window.parent.ticketsDataExport[index].observaciones);
	$("#ccp").html(
			window.parent.ticketsDataExport[index].ccp);
	// $("#observacionesHidden").val(window.parent.ticketsDataExport[index].observaciones);
	// $("#estatus").html(window.parent.ticketsDataExport[index].estatus);
	// $("#estatusHidden").val(window.parent.ticketsDataExport[index].estatus);
	$("#aprobado").html(model.aprobado);
	$("#aprobadoHidden").val(model.aprobado);
	$("#asignado").html(model.asignado);
	$("#asignadoHidden").val(model.asignado);
	$("#recibido").html(model.recibido);
	$("#recibidoHidden").val(model.recibido);
	var str = model.fechat;
	var res = str.split("-");
	$("#fechat").val(
			str.substring(8, 10) + "/" + res[1] + "/" + res[0]
					+ str.substring(10, 19));

	var nuevaFila = "";

	for ( var y = 0; y < window.parent.ticketsDataExport[index].listTicketDetail.length; y++) {

		nuevaFila += "<tr>";

		// Aniadimos una columna con el numero total de columnas.
		// Aniadimos uno al total, ya que cuando cargamos los valores para la
		// columna.

		nuevaFila += "<td width=\"23px\" style=\"text-align:center\"> <div id=\"conse["
				+ y
				+ "]\"></div> <input type=\"hidden\" id=\"conse["
				+ y
				+ "]Hidden\" name=\"conse\"> </td>";
		nuevaFila += "<td width=\"110px\" style=\"text-align:left\"> <div id=\"zona["
				+ y
				+ "]\"> </div> <input type=\"hidden\" id=\"zona["
				+ y
				+ "]Hidden\" name=\"zona\"> </td>";
		nuevaFila += "<td width=\"165px\" align=\"left\" style=\"text-align:left\"> <div id=\"vicio["
				+ y
				+ "]\"> </div> <input type=\"hidden\" id=\"vicio["
				+ y
				+ "]Hidden\" name=\"vicio\"></td>";
		nuevaFila += "<td width=\"20px\" style=\"text-align:center\"><div id=\"foto["
				+ y + "]\"></td>";
		nuevaFila += "<td width=\"70px\" style=\"text-align:center\"> <div id=\"estatusDet["
				+ y
				+ "]\"> </div> <input type=\"hidden\" id=\"estatusDet["
				+ y
				+ "]Hidden\"> </td>";
		nuevaFila += "<td width=\"110px\" style=\"text-align:left\"> <div id=\"motivo["
				+ y + "]\"> </div></td>";

		nuevaFila += "</tr>";
	}

	$("#dg_vicios").append(nuevaFila);

	for ( var n = 0; n < window.parent.ticketsDataExport[index].listTicketDetail.length; n++) {

		document.getElementById("conse[" + n + "]").innerHTML = window.parent.ticketsDataExport[index].listTicketDetail[n].consecutivo;
		document.getElementById("conse[" + n + "]Hidden").value = window.parent.ticketsDataExport[index].listTicketDetail[n].consecutivo;
		document.getElementById("zona[" + n + "]").innerHTML = window.parent.ticketsDataExport[index].listTicketDetail[n].zona;
		document.getElementById("zona[" + n + "]Hidden").value = window.parent.ticketsDataExport[index].listTicketDetail[n].zona;
		document.getElementById("vicio[" + n + "]").innerHTML = window.parent.ticketsDataExport[index].listTicketDetail[n].descripcion;
		document.getElementById("vicio[" + n + "]Hidden").value = window.parent.ticketsDataExport[index].listTicketDetail[n].descripcion;

		if (model.listTicketDetail[n].estatus == 'EN REVISION') {
			document.getElementById("estatusDet[" + n + "]").innerHTML = "<select style=\"font-size: 10px !important; font-family:Century Gothic\" name=\"estatusDet\" id=\"estatusDet["
					+ n
					+ "]\" class=\"habilitar\"> "
					+ "<option value=\"EN REVISION\">EN REVISION</option> "
					+ "<option value=\"ACEPTADO\">ACEPTADO</option> "
					+ "<option value=\"RECHAZADO\">RECHAZADO</option> "
					+ "</select>";
		} else if (model.listTicketDetail[n].estatus == 'ACEPTADO') {
			document.getElementById("estatusDet[" + n + "]").innerHTML =
			// "<select style=\"font-size: 10px !important; font-family:Century
			// Gothic\" name=\"estatusDet\" id=\"estatusDet["+ n +"]\" disabled
			// =\"disabled\" class=\"habilitar\"> " +
			"<select style=\"font-size: 10px !important; font-family:Century Gothic\" id=\"estatusDet["
					+ n
					+ "]\" disabled =\"disabled\" class=\"habilitar\"> "
					+ "<option value=\"ACEPTADO\">ACEPTADO</option> "
					+ "<option value=\"EN REVISION\">EN REVISION</option> "
					+ "</select>"
					+ "<input type=\"hidden\" name=\"estatusDet\" value=\"ACEPTADO\"/>";
		} else if (model.listTicketDetail[n].estatus == 'RECHAZADO') {
			document.getElementById("estatusDet[" + n + "]").innerHTML =
			// "<select style=\"font-size: 10px !important; font-family:Century
			// Gothic\" name=\"estatusDet\" id=\"estatusDet["+ n +"]\" disabled
			// =\"disabled\"> class=\"habilitar\"> " +
			"<select style=\"font-size: 10px !important; font-family:Century Gothic\" id=\"estatusDet["
					+ n
					+ "]\" disabled =\"disabled\"> class=\"habilitar\">  "
					+ "<option value=\"RECHAZADO\">RECHAZADO</option> "
					+ "<option value=\"EN REVISION\">EN REVISION</option> "
					+ "</select>"
					+ "<input type=\"hidden\" name=\"estatusDet\" value=\"RECHAZADO\"/>";
			// document.getElementById("estatusDet[" + n + "]").innerHTML =
			// model.listTicketDetail[n].estatus;
			// document.getElementById("estatusDet[" + n + "]Hidden").value =
			// model.listTicketDetail[n].estatus;
		}

		if (window.parent.ticketsDataExport[index].listTicketDetail[n].foto == "SI") {
			document.getElementById("foto[" + n + "]").innerHTML = "<a href=\""
					+ contexPath
					+ "/ticket/GetRegistroFotografico.htm?idTicket="
					+ window.parent.ticketsDataExport[index].listTicketDetail[n].idTicket
					+ "-"
					+ window.parent.ticketsDataExport[index].listTicketDetail[n].consecutivo
					+ "\"><img src=\"" + contexPath
					+ "\/images/images/descargar.jpg\" border=\"0\" /></a>";
		} else {
			document.getElementById("foto[" + n + "]").innerHTML = "NO";
		}

		if (model.listTicketDetail[n].estatus == 'EN REVISION') {
			document.getElementById("motivo[" + n + "]").innerHTML = "<input type=\"text\" style=\"font-size: 10px !important; font-family:Century Gothic; text-transform: uppercase\"  onblur=\"javascript:this.value=this.value.toUpperCase().trim();\" id=\"motivo["
					+ n + "]\" name=\"motivo\">";
		} else {
			// document.getElementById("motivo[" + n + "]").innerHTML = "<input
			// type=\"text\" style=\"font-size: 10px !important;
			// font-family:Century Gothic; text-transform: uppercase\"
			// onblur=\"javascript:this.value=this.value.toUpperCase().trim();\"
			// id=\"motivo["+ n +"]\" name=\"motivo\" value=\""+
			// model.listTicketDetail[n].motivo +"\" disabled=\"disabled\">";
			document.getElementById("motivo[" + n + "]").innerHTML = "<input type=\"text\" style=\"font-size: 10px !important; font-family:Century Gothic; text-transform: uppercase\" onblur=\"javascript:this.value=this.value.toUpperCase().trim();\" id=\"motivo["
					+ n
					+ "]\" value=\""
					+ model.listTicketDetail[n].motivo
					+ "\" disabled=\"disabled\">"
					+ "<input type=\"hidden\" name=\"motivo\" value=\""
					+ model.listTicketDetail[n].motivo + "\"/>";
		}
		// document.getElementById("motivo[" + n + "]").innerHTML =
		// model.listTicketDetail[n].motivo;
		// document.getElementById("motivo[" + n + "]Hidden").value =
		// model.listTicketDetail[n].motivo;
	}
	fillDatosClienteTicketById(window.parent.ticketsDataExport[index].idEquipo);

}

function executeFindBusquedaCatalogoTicketFilter() {
	if (validaParametrosBusqueda()) {
		$
				.ajax({
					type : "POST",
					cache : false,
					url : contexPath + "/ticket/FindRegistroTicket.htm",
					data : "idTicketInicial="
							+ $("#idTicketInicial").val()
							+ "&idTicketFinal="
							+ $("#idTicketFinal").val()
							+ "&fechaInicialFiltroCatalogoTicket="
							+ $("#fechaInicialFiltroCatalogoTicket").val()
							+ "&fechaFinalFiltroCatalogoTicket="
							+ $("#fechaFinalFiltroCatalogoTicket").val()
							+ "&idAreaTicket="
							+ $("#idAreaTicket").data('kendoComboBox')
									.dataItem().id_ticket_area
							+ "&idClienteTicket="
							+ $("#claveClienteTicket").val()
							+ "&idVendedorTicket="
							+ $("#claveVendedorTicket").val()
							+ "&idFaseTicket=" + $("#faseTicket").val()
							+ "&idEquipoTicket=" + $("#equipoTicket").val(),
					success : function(response) {
						// we have the response
						if (response.mensaje == "SUCCESS") {
							window.parent.$(
									"#windowConsultaCatalogoTicketFilter")
									.data("kendoWindow").close();
							window.parent.$("#mensajes_main").html(
									"Registros encontrados");

							window.parent.ticketsDataExport = response.listTicket;

							if (window.parent.ticketsDataExport.length > 0) {
								window.parent.document
										.getElementById("verDetalleTicket").style.visibility = '';
								window.parent.document
										.getElementById("crearExcel").style.visibility = '';
							} else {
								window.parent.document
										.getElementById("verDetalleTicket").style.visibility = 'hidden';
								window.parent.document
										.getElementById("darAtencionTicket").style.visibility = 'hidden';
								window.parent.document
										.getElementById("darProcesoTicket").style.visibility = 'hidden';
								window.parent.document
										.getElementById("crearImpresionTicket").style.visibility = 'hidden';
								window.parent.document
										.getElementById("crearExcel").style.visibility = 'hidden';
							}

							// window.parent.$("#GridConsultaCatalogoTickets").empty();
							window.parent.$("#GridConsultaCatalogoTickets")
									.data("kendoGrid").dataSource
									.data(window.parent.ticketsDataExport);
							window.parent.$("#GridConsultaCatalogoTickets")
									.data("kendoGrid").refresh();

							// window.parent.$("#GridConsultaCatalogoTickets").kendoGrid(
							// {
							// dataSource : dataSourceTickets,
							// selectable:"row",
							// resizable: true,
							// sortable: true,
							// reorderable: true,
							// pageable: {
							// input: true,
							// numeric: false,
							// pageSizes: [50,100,150],
							// messages: {
							// display: "{1} de {2} registros",
							// page: "Pagina",
							// of: "de",
							// itemsPerPage: "registros por pagina"
							// }
							// },
							// scrollable:{
							// virtual:true
							// },
							// columns : [ {
							// field : "idTicket",
							// title : "#Ticket",
							// width: 60
							//									
							// }, {
							// field : "idArea",
							// title : "Area",
							// width: 60
							// }, {
							// field : "idCliente",
							// title : "Cliente",
							// width: 100
							// }, {
							// field : "nombre1Cliente",
							// title : "Nombre",
							// width: 100
							// }, {
							// field : "idVendedor",
							// title : "Vendedor",
							// width: 100
							// },{
							// field : "idEquipo",
							// title : "Equipo",
							// width: 60
							// },{
							// field : "asunto",
							// title : "Asunto",
							// width: 200
							// }]
							// });
						} else if (response.mensaje == "LOGOUT") {
							window.parent.salirSistema();
						} else {
							// kendoConsole.log("Hubo un error en carga
							// clientes:
							// "+response.respGetInfCarCliente.descripcion
							// ,"error");
							window.parent.$("#mensajes_main").html(
									"Hubo un error en carga clientes: "
											+ response.descripcion);
							window.parent.$("#GridConsultaCatalogoTickets")
									.data("kendoGrid").dataSource.data([]);
							window.parent.$("#GridConsultaCatalogoTickets")
									.data("kendoGrid").refresh();
							// window.parent.$("#GridConsultaCatalogoTickets").empty();
							// reiniciaGridBusquedaClienteTicket();
						}
					},
					error : function(e) {
						// kendoConsole.log(e,"error");
						window.parent.$("#mensajes_main").html(
								"Hubo un error en carga clientes: " + e);
					}
				});
	}
}

function executeBusquedaTicketViciosFilter() {
	if (validaParametrosBusqueda()) {
		var fechaInicial = $("#fechaInicialFiltroCatalogoTicket").val();
		var fechaFinal = $("#fechaFinalFiltroCatalogoTicket").val();
		$
				.ajax({
					type : "POST",
					cache : false,
					url : contexPath + "/ticket/FindTicketVicios.htm",
					data : {
						idTicket : $("#idTicketInicial").val(),
						idFase : $("#idFase").val(),
						idEquipo : $("#idEquipo").val(),
						txtArea : $("#idArea").val(),
						estatus : $("#estatus").val(),
						fechaInicial : $("#fechaInicialFiltroCatalogoTicket")
								.val(),
						fechaFinal : $("#fechaFinalFiltroCatalogoTicket").val()
					},
					success : function(response) {
						// we have the response
						if (response.mensaje == "SUCCESS") {
							window.parent.$(
									"#windowConsultaCatalogoTicketFilter")
									.data("kendoWindow").close();
							window.parent.$("#mensajes_main").html(
									"Registros encontrados");

							window.parent.ticketsDataExport = response.listTicket;

							if (window.parent.ticketsDataExport.length > 0) {
								window.parent.document
										.getElementById("verDetalleTicket").style.visibility = '';
								window.parent.document
										.getElementById("darAtencionTicket").style.visibility = '';
								window.parent.document
										.getElementById("darProcesoTicket").style.visibility = '';
								window.parent.document
										.getElementById("crearImpresionTicket").style.visibility = '';
								window.parent.document
										.getElementById("crearExcel").style.visibility = '';
							} else {
								window.parent.document
										.getElementById("verDetalleTicket").style.visibility = 'hidden';
								window.parent.document
										.getElementById("darAtencionTicket").style.visibility = 'hidden';
								window.parent.document
										.getElementById("darProcesoTicket").style.visibility = 'hidden';
								window.parent.document
										.getElementById("crearImpresionTicket").style.visibility = 'hidden';
								window.parent.document
										.getElementById("crearExcel").style.visibility = 'hidden';
							}

							// window.parent.$("#GridConsultaCatalogoTickets").empty();
							window.parent.$("#GridConsultaCatalogoTickets")
									.data("kendoGrid").dataSource.data([]);
							window.parent.$("#GridConsultaCatalogoTickets")
									.data("kendoGrid").dataSource
									.data(window.parent.ticketsDataExport);
							window.parent.$("#GridConsultaCatalogoTickets")
									.data("kendoGrid").refresh();

							window.parent.$("#idTicketInicial").val(
									$("#idTicketInicial").val());
							window.parent.$("#idFase").val($("#idFase").val());
							window.parent.$("#idEquipo").val(
									$("#idEquipo").val());
							window.parent.$("#estatus")
									.val($("#estatus").val());
							window.parent.$("#fechaInicial").val(fechaInicial);
							window.parent.$("#fechaFinal").val(fechaFinal);

						} else if (response.mensaje == "LOGOUT") {
							window.parent.salirSistema();
						} else {
							// kendoConsole.log("Hubo un error en carga
							// clientes:
							// "+response.respGetInfCarCliente.descripcion
							// ,"error");
							window.parent.$("#mensajes_main").html(
									"Hubo un error en carga clientes: "
											+ response.descripcion);
							window.parent.$("#GridConsultaCatalogoTickets")
									.data("kendoGrid").dataSource.data([]);
							window.parent.$("#GridConsultaCatalogoTickets")
									.data("kendoGrid").refresh();
							// window.parent.$("#GridConsultaCatalogoTickets").empty();
							// reiniciaGridBusquedaClienteTicket();
						}
					},
					error : function(e) {
						// kendoConsole.log(e,"error");
						window.parent.$("#mensajes_main").html(
								"Hubo un error en carga clientes: " + e);
					}
				});
	}
}

function executeReporteTicketViciosFilter() {
	if (validaParametrosBusqueda()) {
		var fechaInicial = $("#fechaInicialFiltroCatalogoTicket").val();
		var fechaFinal = $("#fechaFinalFiltroCatalogoTicket").val();
		$
				.ajax({
					type : "POST",
					cache : false,
					url : contexPath + "/ticket/FindTicketVicios.htm",
					data : {
						idTicket : $("#idTicketInicial").val(),
						idFase : $("#idFase").val(),
						idEquipo : $("#idEquipo").val(),
						estatus : $("#estatus").val(),
						fechaInicial : $("#fechaInicialFiltroCatalogoTicket")
								.val(),
						fechaFinal : $("#fechaFinalFiltroCatalogoTicket").val()
					},
					success : function(response) {
						// we have the response
						if (response.mensaje == "SUCCESS") {
							window.parent.$(
									"#windowConsultaCatalogoTicketFilter")
									.data("kendoWindow").close();
							window.parent.$("#mensajes_main").html(
									"Registros encontrados");

							window.parent.ticketsDataExport = response.listTicket;

							if (window.parent.ticketsDataExport.length > 0) {
								window.parent.document
										.getElementById("verDetalleTicket").style.visibility = '';
								// window.parent.document.getElementById("darAtencionTicket").style.visibility='';
								// window.parent.document.getElementById("darProcesoTicket").style.visibility='';
								// window.parent.document.getElementById("crearImpresionTicket").style.visibility='';
								window.parent.document
										.getElementById("crearExcel").style.visibility = '';
							} else {
								window.parent.document
										.getElementById("verDetalleTicket").style.visibility = 'hidden';
								// window.parent.document.getElementById("darAtencionTicket").style.visibility='hidden';
								// window.parent.document.getElementById("darProcesoTicket").style.visibility='hidden';
								// window.parent.document.getElementById("crearImpresionTicket").style.visibility='hidden';
								window.parent.document
										.getElementById("crearExcel").style.visibility = 'hidden';
							}

							// window.parent.$("#GridConsultaCatalogoTickets").empty();
							window.parent.$("#GridConsultaCatalogoTickets")
									.data("kendoGrid").dataSource.data([]);
							window.parent.$("#GridConsultaCatalogoTickets")
									.data("kendoGrid").dataSource
									.data(window.parent.ticketsDataExport);
							window.parent.$("#GridConsultaCatalogoTickets")
									.data("kendoGrid").refresh();

							window.parent.$("#idTicketInicial").val(
									$("#idTicketInicial").val());
							window.parent.$("#idFase").val($("#idFase").val());
							window.parent.$("#idEquipo").val(
									$("#idEquipo").val());
							window.parent.$("#estatus")
									.val($("#estatus").val());
							window.parent.$("#fechaInicial").val(fechaInicial);
							window.parent.$("#fechaFinal").val(fechaFinal);

						} else if (response.mensaje == "LOGOUT") {
							window.parent.salirSistema();
						} else {
							// kendoConsole.log("Hubo un error en carga
							// clientes:
							// "+response.respGetInfCarCliente.descripcion
							// ,"error");
							window.parent.$("#mensajes_main").html(
									"Hubo un error en carga clientes: "
											+ response.descripcion);
							window.parent.$("#GridConsultaCatalogoTickets")
									.data("kendoGrid").dataSource.data([]);
							window.parent.$("#GridConsultaCatalogoTickets")
									.data("kendoGrid").refresh();
							// window.parent.$("#GridConsultaCatalogoTickets").empty();
							// reiniciaGridBusquedaClienteTicket();
						}
					},
					error : function(e) {
						// kendoConsole.log(e,"error");
						window.parent.$("#mensajes_main").html(
								"Hubo un error en carga clientes: " + e);
					}
				});
	}
}

function executeFindBusquedaByIdTicketFilter(idTicket) {
	if (idTicket != 'null') {
		$
				.ajax({
					type : "POST",
					cache : false,
					url : contexPath + "/ticket/FindTicketVicios.htm",
					data : {
						idTicket : idTicket
					},
					success : function(response) {
						// we have the response
						if (response.mensaje == "SUCCESS") {
							$("#mensajes_main").html("Registros encontrados");

							ticketsDataExport = response.listTicket;
							document.getElementById("idTicketInicial").value = idTicket;

							if (window.parent.ticketsDataExport.length > 0) {
								document.getElementById("verDetalleTicket").style.visibility = '';
								document.getElementById("darAtencionTicket").style.visibility = '';
								document.getElementById("darProcesoTicket").style.visibility = '';
								document.getElementById("crearImpresionTicket").style.visibility = '';
								document.getElementById("crearExcel").style.visibility = '';
							} else {
								document.getElementById("verDetalleTicket").style.visibility = 'hidden';
								document.getElementById("darAtencionTicket").style.visibility = 'hidden';
								document.getElementById("darProcesoTicket").style.visibility = 'hidden';
								document.getElementById("crearImpresionTicket").style.visibility = 'hidden';
								document.getElementById("crearExcel").style.visibility = '';
							}

							// window.parent.$("#GridConsultaCatalogoTickets").empty();
							$("#GridConsultaCatalogoTickets").data("kendoGrid").dataSource
									.data(ticketsDataExport);
							$("#GridConsultaCatalogoTickets").data("kendoGrid")
									.refresh();

						} else if (response.mensaje == "LOGOUT") {
							salirSistema();
						} else {
							// kendoConsole.log("Hubo un error en carga
							// clientes:
							// "+response.respGetInfCarCliente.descripcion
							// ,"error");
							$("#mensajes_main").html(
									"Hubo un error en carga clientes: "
											+ response.descripcion);
							$("#GridConsultaCatalogoTickets").data("kendoGrid").dataSource
									.data([]);
							$("#GridConsultaCatalogoTickets").data("kendoGrid")
									.refresh();
							// window.parent.$("#GridConsultaCatalogoTickets").empty();
							// reiniciaGridBusquedaClienteTicket();
						}
					},
					error : function(e) {
						// kendoConsole.log(e,"error");
						$("#mensajes_main").html(
								"Hubo un error en carga clientes: " + e);
					}
				});
	}
}

function registrarTicketConstruccion() {
	if (validaRegistroTicketConstruccion()) {
		$("#Guardar").prop("disabled", true);
		$("#Guardar").removeClass("k-button");
		$("#Guardar").addClass("desactivado");
		$("#Cancelar").prop("disabled", true);
		$("#Cancelar").removeClass("k-button");
		$("#Cancelar").addClass("desactivado");
		$("#Accion").val("construccion");
		document.registroTicketForm.submit();
	}
}

function registrarTicketNoConstruccion() {
	if (validaRegistroTicket()) {
		$("#GuardarT").prop("disabled", true);
		$("#GuardarT").removeClass("k-button");
		$("#GuardarT").addClass("desactivado");
		$("#CancelarT").prop("disabled", true);
		$("#CancelarT").removeClass("k-button");
		$("#CancelarT").addClass("desactivado");
		$("#Accion").val("noconstruccion");
		document.registroTicketForm.submit();
	}
}

/*
 * Funcion que valida el registro de tickets
 */
function validaRegistroTicketConstruccion() {

	var trs = $("#dg_vicios tr").length;

	if ($("#idArea").val() == undefined || $("#idArea").val() == 0) {
		$("#mensajes_main").html("Debe introducir una área");
		$('#idArea').focus();
		return false;
	}
	if ($("#idFase").val() == undefined || $.trim($("#idFase").val()) == "") {
		$("#mensajes_main").html("Debe introducir una fase");
		$('#idFase').focus();
		return false;
	}
	if ($("#idEquipo").val() == undefined || $.trim($("#idEquipo").val()) == "") {
		$("#mensajes_main").html("Debe introducir un equipo");
		$('#idEquipo').focus();
		return false;
	}
	/*
	 * if ($("#nombreClienteTicket").val() == undefined ||
	 * $.trim($("#nombreClienteTicket").val()) == "") {
	 * $("#mensajes_main").html("Debe introducir un cliente");
	 * $('#nombreClienteTicket').focus(); return false; } if
	 * ($("#mailClienteTicket").val() == undefined ||
	 * $.trim($("#mailClienteTicket").val()) == "") {
	 * $("#mensajes_main").html("Debe introducir un correo de cliente");
	 * $('#mailClienteTicket').focus(); return false; } if
	 * (!validateEmail($("#mailClienteTicket").val())) {
	 * $("#mensajes_main").html("Debe introducir un correo válido");
	 * $('#mailClienteTicket').focus(); return false; }
	 */
	if (!$('input[name="prioridad"]').is(':checked')) {
		$("#mensajes_main").html("Debe seleccionar la prioridad");
		return false;
	}
	if (trs > 0) {
		for ( var i = 0; i < trs; i++) {
			// alert(document.getElementsByName("zona")[i].value);
			if ($.trim(document.getElementsByName("zona")[i].value) == "") {
				$("#mensajes_main").html(
						"Consecutivo " + (i + 1)
								+ ": Debe introducir la Zona/Área");
				document.getElementsByName("zona")[i].focus();
				return false;
			}
			/*if ($.trim(document.getElementsByName("vicio")[i].value) == "") {
				$("#mensajes_main").html(
						"Consecutivo " + (i + 1)
								+ ": Debe introducir la Descripción");
				document.getElementsByName("vicio")[i].focus();
				return false;
			}*/
			
			if ($.trim(document.getElementsByName("categoria")[i].value) == "") {
				$("#mensajes_main").html(
						"Consecutivo " + (i + 1)
								+ ": Debe seleccionar la categoria");
				document.getElementsByName("categoria")[i].focus();
				return false;
			}
			
			if ($.trim(document.getElementsByName("categoria")[i].value) == "OTRO" &&
					$.trim(document.getElementsByName("textoabierto")[i].value) == "" ) {
				$("#mensajes_main").html(
						"Consecutivo " + (i + 1)
								+ ": Debe introducir la Descripción");
				document.getElementsByName("textoabierto")[i].focus();
				return false;
			}

			if (document.getElementById("archivos[" + i + "]").files.length == 1) {
				var nameFile = document.getElementById("archivos[" + i + "]").value;
				var extension = (nameFile.substring(nameFile.lastIndexOf(".")))
						.toLowerCase();
				if (extension == ".jpg") {

				} else {
					$("#mensajes_main")
							.html(
									"Consecutivo "
											+ (i + 1)
											+ ": Favor de verificar que sea formato .jpg");
					document.getElementById("archivos[" + i + "]").focus();
					return false;
				}
			}
		} // Fin For

	} else {
		$("#mensajes_main").html("Debe introducir un Hallazgo");
		return false;
	}

	if ($("#asunto").val() == undefined || $.trim($("#asunto").val()) == "") {
		$("#mensajes_main").html("Debe introducir un asunto");
		$('#asunto').focus();
		return false;
	}

	if ($.trim($("#observaciones").val()).length > 512) {
		$("#mensajes_main").html(
				"La observación no puede ser mayor a 512 caracteres");
		$('#observaciones').focus();
		return false;
	}

	return true;
}

function validaRegistroTicket() {

	var trs = $("#dg_ticket tr").length;

	if ($("#idArea").val() == undefined || $("#idArea").val() == 0) {
		$("#mensajes_main").html("Debe introducir una área");
		$('#idArea').focus();
		return false;
	}
	if ($("#idFase").val() == undefined || $.trim($("#idFase").val()) == "") {
		$("#mensajes_main").html("Debe introducir una fase");
		$('#idFase').focus();
		return false;
	}
	if ($("#idEquipo").val() == undefined || $.trim($("#idEquipo").val()) == "") {
		$("#mensajes_main").html("Debe introducir un equipo");
		$('#idEquipo').focus();
		return false;
	}
	/*
	 * if ($("#nombreClienteTicket").val() == undefined ||
	 * $.trim($("#nombreClienteTicket").val()) == "") {
	 * $("#mensajes_main").html("Debe introducir un cliente");
	 * $('#nombreClienteTicket').focus(); return false; } if
	 * ($("#mailClienteTicket").val() == undefined ||
	 * $.trim($("#mailClienteTicket").val()) == "") {
	 * $("#mensajes_main").html("Debe introducir un correo de cliente");
	 * $('#mailClienteTicket').focus(); return false; } if
	 * (!validateEmail($("#mailClienteTicket").val())) {
	 * $("#mensajes_main").html("Debe introducir un correo válido");
	 * $('#mailClienteTicket').focus(); return false; }
	 */
	if (!$('input[name="prioridad"]').is(':checked')) {
		$("#mensajes_main").html("Debe seleccionar la prioridad");
		return false;
	}
	if (trs > 0) {
		for ( var i = 0; i < trs; i++) {
			// alert(document.getElementsByName("zona")[i].value);
			/*
			 * if($.trim(document.getElementsByName("zona")[i].value) == ""){
			 * $("#mensajes_main").html("Consecutivo " + (i+1) +": Debe
			 * introducir la Zona/Área");
			 * document.getElementsByName("zona")[i].focus(); return false; }
			 */
			if ($.trim(document.getElementsByName("descripcion")[i].value) == "") {
				$("#mensajes_main").html(
						"Consecutivo " + (i + 1)
								+ ": Debe introducir la Descripción");
				document.getElementsByName("descripcion")[i].focus();
				return false;
			}

			/*
			 * if(document.getElementById("archivos[" + i + "]").files.length ==
			 * 1) { var nameFile = document.getElementById("archivos[" + i +
			 * "]").value; var extension =
			 * (nameFile.substring(nameFile.lastIndexOf("."))).toLowerCase(); if
			 * (extension == ".jpg"){
			 * 
			 * }else{ $("#mensajes_main").html("Consecutivo " + (i+1) +": Favor
			 * de verificar que sea formato .jpg");
			 * document.getElementById("archivos[" + i + "]").focus(); return
			 * false; } }
			 */
		} // Fin For

	} else {
		$("#mensajes_main").html("Debe introducir un Hallazgo");
		return false;
	}

	if ($("#asunto").val() == undefined || $.trim($("#asunto").val()) == "") {
		$("#mensajes_main").html("Debe introducir un asunto");
		$('#asunto').focus();
		return false;
	}

	if ($.trim($("#observaciones").val()).length > 512) {
		$("#mensajes_main").html(
				"La observación no puede ser mayor a 512 caracteres");
		$('#observaciones').focus();
		return false;
	}

	return true;
}

function atencionTicketConstruccion() {
	
	var grid = window.parent.$("#GridConsultaCatalogoTickets")
	.data("kendoGrid");
	var model = grid.dataItem(grid.select());
	var bandera = false;
	
	if (model == undefined) {
	alert("Requiere seleccionar un ticket");
	return;
	}
	var index = -1;
	for ( var i = 0; i < window.parent.ticketsDataExport.length; i++) {
		if (window.parent.ticketsDataExport[i].idTicket == model.idTicket) {
			index = i;
			break;
		}
	}
	
	if (window.parent.ticketsDataExport[index].areatx == 'ADMINISTRACIÓN'){
		if (validaAtencionTicketAdministracion()){
			bandera = true;
		}
	}else{
		if (validaAtencionTicketConstruccion()){
			bandera = true;
		}
	}
	
	if (bandera) {
		// $("#accion").val("guardar");
		// document.atencionTicketForm.submit();
		var formData = new FormData(document
				.getElementById("atencionTicketForm"));

		$.ajax({
			type : "POST",
			cache : false,
			url : contexPath + "/ticket/UpdateTicketConstruccion.htm",
			// data : "idAreaTicket=1",
			data : formData,
			processData : false,
			contentType : false,
			success : function(response) {
				if (response.mensaje == "SUCCESS") {
					window.parent.$("#mensajes_main").html(
							"La actualización del ticket fue satisfactoria "
									+ response.id_ticket_sap);
					actualizaFormaCaptura(response.id_ticket_sap);
					window.parent.$("#windowAtencionConstruccionTicket").data(
							"kendoWindow").close();
					// limpiaFormaCaptura();
				} else if (response.mensaje == "LOGOUT") {
					salirSistema();
				} else {
					$("#mensajes_main").html(
							"Hubo un error en el registro de ticket "
									+ response.descripcion);
				}
			}
		});
	}
}

function validaAtencionTicketConstruccion() {

	var trs = $("#dg_vicios tr").length;
	var bandera = true;

	/*
	 * if ($("#estatus").val() == undefined || $.trim($("#estatus").val()) ==
	 * "") { window.parent.$("#mensajes_main").html("Debe introducir un
	 * estatus"); $('#estatus').focus(); return false; }
	 */

	if (trs > 0) {
		for ( var i = 0; i < trs; i++) {
			// alert(document.getElementsByName("estatusDet")[i].value);
			if ($.trim(document.getElementsByName("estatusDet")[i].value) == "RECHAZADO"
					&& (document.getElementsByName("motivo")[i].value == "" || (document
							.getElementsByName("motivo")[i].value == null))) {
				$("#mensajes_main").html(
						"Consecutivo " + (i + 1)
								+ ": Debe introducir el motivo de rechazo");
				document.getElementsByName("motivo")[i].focus();
				return false;
			}/*
				 * if($.trim(document.getElementsByName("vicio")[i].value) ==
				 * ""){ $("#mensajes_main").html("Consecutivo " + (i+1) +": Debe
				 * introducir la Descripción");
				 * document.getElementsByName("vicio")[i].focus(); return false; }
				 * 
				 * if(document.getElementById("archivos[" + i +
				 * "]").files.length == 0) {
				 * $("#mensajes_main").html("Consecutivo " + (i+1) +": Debe
				 * introducir un Registro Fotográfico");
				 * document.getElementById("archivos[" + i + "]").focus();
				 * return false; }
				 * if($.trim(document.getElementsByName("estatusDet")[i].value) ==
				 * "RECHAZADO"){ cuentaRechazados ++; if(cuentaRechazados ==
				 * trs){ bandera = false; } }
				 */
		} // Fin For

		if (bandera) {

			if ($("#asignado").val() == undefined
					|| $.trim($("#asignado").val()) == "") {
				$("#mensajes_main").html(
						"Debe introducir el personal que atendera");
				$("#asignado").focus();
				return false;
			}

			if ($("#fechat").val() == undefined
					|| $.trim($("#fechat").val()) == "") {
				$("#mensajes_main").html(
						"Debe introducir una fecha de atención");
				$("#fechat").focus();
				return false;
			}
		}

		if ($.trim($("#observaciones").val()).length > 512) {
			$("#mensajes_main").html(
					"La observación no puede ser mayor a 512 caracteres");
			$('#observaciones').focus();
			return false;
		}

	} else {
		$("#mensajes_main").html("Debe introducir un Hallazgo");
		return false;
	}

	/*
	 * if ($("#asuntoTicket").val() == undefined ||
	 * $.trim($("#asuntoTicket").val()) == "") { $("#mensajes_main").html("Debe
	 * introducir un asunto"); $('#asuntoTicket').focus(); return false; }
	 * 
	 * if ( $.trim($("#observacionesTicket").val()).length > 255) {
	 * $("#mensajes_main").html("La observación no puede ser mayor a 255
	 * caracteres"); $('#observacionesTicket').focus(); return false; }
	 */

	return true;
}

function validaAtencionTicketAdministracion() {
	
	    $("#estatusHidden").val("CERRAR");
	    
	    if ($.trim($("#observaciones").val())== '') {
			$("#mensajes_main").html(
					"Es necesario dar respuesta en el campo de observaciones");
			$('#observaciones').focus();
			return false;
			}

		if ($.trim($("#observaciones").val()).length > 512) {
			$("#mensajes_main").html("La observación no puede ser mayor a 512 caracteres");
			$('#observaciones').focus();
		return false;
		}
		return true;
	}



function atencionTicket() {

	if (validaAtencionTicket()) {
		// $("#accion").val("guardar");
		// document.atencionTicketForm.submit();
		var formData = new FormData(document
				.getElementById("atencionTicketForm"));

		$.ajax({
			type : "POST",
			cache : false,
			url : contexPath + "/ticket/UpdateTicketConstruccion.htm",
			// data : "idAreaTicket=1",
			data : formData,
			processData : false,
			contentType : false,
			success : function(response) {
				if (response.mensaje == "SUCCESS") {
					window.parent.$("#mensajes_main").html(
							"La actualización del ticket fue satisfactoria "
									+ response.id_ticket_sap);
					actualizaFormaCaptura(response.id_ticket_sap);
					window.parent.$("#windowAtencionTicket")
							.data("kendoWindow").close();
					// limpiaFormaCaptura();
				} else if (response.mensaje == "LOGOUT") {
					salirSistema();
				} else {
					$("#mensajes_main").html(
							"Hubo un error en el registro de ticket "
									+ response.descripcion);
				}
			}
		});
	}
}

function validaAtencionTicket() {

	if ($.trim($("#observaciones").val()).length > 512) {
		$("#mensajes_main").html(
				"La observación no puede ser mayor a 512 caracteres");
		$('#observaciones').focus();
		return false;
	}

	return true;
}

function procesoTicketConstruccion() {

	if (validaProcesoTicketConstruccion()) {

		var formData = new FormData(document
				.getElementById("procesoTicketForm"));
		// $('.habilitar').prop('disabled',false);

		$.ajax({
			type : "POST",
			cache : false,
			url : contexPath + "/ticket/UpdateTicketConstruccion.htm",
			// data : "idAreaTicket=1",
			data : formData,
			processData : false,
			contentType : false,
			success : function(response) {
				if (response.mensaje == "SUCCESS") {
					window.parent.$("#mensajes_main").html(
							"La actualización del ticket fue satisfactoria "
									+ response.id_ticket_sap);
					actualizaFormaCaptura(response.id_ticket_sap);
					window.parent.$("#windowProcesoConstruccionTicket").data(
							"kendoWindow").close();
				} else if (response.mensaje == "LOGOUT") {
					salirSistema();
				} else {
					// $('.habilitar').prop('disabled',true);
					$("#mensajes_main").html(
							"Hubo un error en el registro de ticket "
									+ response.descripcion);
				}
			}
		});
	}
}

function validaProcesoTicketConstruccion() {

	var trs = $("#dg_vicios tr").length;

	if (trs > 0) {
		for ( var i = 0; i < trs; i++) {

			if ($.trim(document.getElementsByName("estatusDet")[i].value) == "RECHAZADO"
					&& $.trim(document.getElementsByName("motivo")[i].value) == "") {
				$("#mensajes_main").html(
						"Consecutivo " + (i + 1)
								+ ": Debe introducir el motivo de rechazo");
				document.getElementsByName("motivo")[i].focus();
				return false;
			} else if ($
					.trim(document.getElementsByName("estatusDet")[i].value) != "RECHAZADO") {
				document.getElementsByName("motivo")[i].value = "";
			}
			

			// alert(document.getElementsByName("zona")[i].value);
			/*
			 * if($.trim(document.getElementsByName("zona")[i].value) == ""){
			 * $("#mensajes_main").html("Consecutivo " + (i+1) +": Debe
			 * introducir la Zona/Área");
			 * document.getElementsByName("zona")[i].focus(); return false; }
			 * if($.trim(document.getElementsByName("vicio")[i].value) == ""){
			 * $("#mensajes_main").html("Consecutivo " + (i+1) +": Debe
			 * introducir la Descripción");
			 * document.getElementsByName("vicio")[i].focus(); return false; }
			 * 
			 * if(document.getElementById("archivos[" + i + "]").files.length ==
			 * 0) { $("#mensajes_main").html("Consecutivo " + (i+1) +": Debe
			 * introducir un Registro Fotográfico");
			 * document.getElementById("archivos[" + i + "]").focus(); return
			 * false; }
			 */

		} // Fin For

	} else {
		$("#mensajes_main").html("Debe introducir un Hallazgo");
		return false;
	}

	if ($("#fechat").val() == undefined || $.trim($("#fechat").val()) == "") {
		$("#mensajes_main").html("Debe introducir una fecha de atención");
		$('#fechat').focus();
		return false;
	}

	if ($("#estatus").val() == undefined || $.trim($("#estatus").val()) == "") {
		$("#mensajes_main").html("Debe introducir un estatus");
		$('#estatus').focus();
		return false;
	}

	var nameFile = $("#fileData").val();
	var estatus = $("#estatus").val();

	if (estatus == "CERRAR") {
		if (nameFile != "" && nameFile != undefined) {

			var extension = (nameFile.substring(nameFile.lastIndexOf(".")))
					.toLowerCase();
			// alert(extension);

			if (extension.toUpperCase() == ".PDF") {

			} else {
				// alert("Archivo no permitido, seleccione archivo PDF");
				$("#mensajes_main").html(
						"Verificar que el archivo sea extensión PDF");
				return false;
			}
		} else {
			$("#mensajes_main").html(
					"Favor de introducir el PDF de Visto Bueno");
			return false;
		}
	}

	/*
	 * if ($("#asuntoTicket").val() == undefined ||
	 * $.trim($("#asuntoTicket").val()) == "") { $("#mensajes_main").html("Debe
	 * introducir un asunto"); $('#asuntoTicket').focus(); return false; }
	 */

	if ($.trim($("#observaciones").val()).length > 512) {
		$("#mensajes_main").html(
				"La observación no puede ser mayor a 512 caracteres");
		$('#observaciones').focus();
		return false;
	}
	
	if (estatus == "EJECUTADO") {
		if (trs > 0) {
			for ( var i = 0; i < trs; i++) {
				if ($.trim(document.getElementsByName("estatusDet")[i].value) == "EN REVISION"){
					$("#mensajes_main").html("Asigne el Estatus Faltante");
					document.getElementsByName("estatusDet")[i].focus();
					return false;
				}
			}
		}
	}
	
	return true;
}

function validateEmail(sEmail) {
	var filter = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$/;
	if (filter.test(sEmail)) {
		return true;
	} else {
		return false;
	}
}

function fillDatosClienteTicket() {

	// alert (iduts);
	// onVentanaWait("Espere ", true)
	$
			.ajax({
				type : "POST",
				cache : false,
				async : false,
				url : contexPath
						+ "/ticket/LlenaDatosClienteRegistroTicket.htm",
				data : "equipo=" + $("#idEquipo").val(),
				success : function(response) {
					// we have the response
					if (response != undefined) {
						if (response.mensaje != undefined
								&& response.mensaje == "SUCCESS") {
							// $.unblockUI()
							$("#cliente").val(response.nombre);
							$("#correo").val(response.correo);
							$("#telefono").val(response.telefono);
							$("#fechaInicioGarantia").val(
									response.fechaInicioGarantia);
							$("#fechaFinGarantia").val(
									response.fechaFinGarantia);
						} else {
							// $.unblockUI()
							$("#mensajes_main").html(response.descripcion);
						}
					}

				},
				error : function(e) {
					// $.unblockUI()
					$("#mensajes_main").html(
							"Fallo el acceso a los datos " + e.responseText
									+ " " + e.status);
				}
			});
}

function fillDatosClienteTicketById(idTicket) {

	// alert (idTicket);
	// onVentanaWait("Espere ", true)
	$.ajax({
		type : "POST",
		cache : false,
		async : false,
		url : contexPath + "/ticket/LlenaDatosClienteRegistroTicket.htm",
		data : "equipo=" + idTicket,
		success : function(response) {
			// we have the response
			if (response != undefined) {
				if (response.mensaje != undefined
						&& response.mensaje == "SUCCESS") {
					// $.unblockUI()
					$("#correo").html(response.correo);
					$("#telefono").html(response.telefono);

				} else {
					// $.unblockUI()
					$("#mensajes_main").html(response.descripcion);
				}
			}

		},
		error : function(e) {
			// $.unblockUI()
			$("#mensajes_main").html(
					"Fallo el acceso a los datos " + e.responseText + " "
							+ e.status);
		}
	});
}

function getLogViciosTicketById() {

	// alert (idTicket);
	// onVentanaWait("Espere ", true)
	var idTicket = window.parent.$("#idTicketHidden").val();
	$
			.ajax({
				type : "POST",
				cache : false,
				async : false,
				url : contexPath + "/ticket/getLogViciosTicketById.htm",
				data : "idTicket=" + idTicket,
				success : function(response) {
					// we have the response
					if (response != undefined) {
						if (response.mensaje != undefined
								&& response.mensaje == "SUCCESS") {

							var dataSourceConsultaCatalogoTickets = new kendo.data.DataSource();
							dataSourceConsultaCatalogoTickets = response.listTicket;

							$("#GridLogTickets").kendoGrid({
								dataSource : dataSourceConsultaCatalogoTickets,
								selectable : "row",
								resizable : true,
								sortable : true,
								reorderable : true,

								pageable : {
									input : true,
									numeric : false,
									pageSizes : [ 50, 100, 150 ],
									messages : {
										display : "{1} de {2} registros",
										page : "Pagina",
										of : "de",
										itemsPerPage : "registros por pagina"
									}
								},
								scrollable : {
									virtual : true
								},
								columns : [ {
									field : "idTicket",
									title : "#Ticket",
									width : 80
								}, {
									field : "usuario",
									title : "Usuario",
									width : 60
								}, {
									field : "fecha",
									title : "Fecha",
									width : 80
								}, {
									field : "hora",
									title : "Hora",
									width : 80
								}, {
									field : "actividad",
									title : "Actividad",
									width : 80
								}, {
									field : "observaciones",
									title : "Observaciones",
									width : 200
								} ]
							});

							// window.parent.$("#GridConsultaCatalogoTickets").data("kendoGrid").dataSource.data([]);
							// window.parent.$("#GridConsultaCatalogoTickets").data("kendoGrid").dataSource.data(response.listTicket);
							// window.parent.$("#GridConsultaCatalogoTickets").data("kendoGrid").dataSource.data.read();
							// window.parent.$("#GridConsultaCatalogoTickets").data("kendoGrid").refresh();
						} else if (response.mensaje == "LOGOUT") {
							salirSistema();
						} else {
							window.parent.$("#mensajes_main").html(
									"Hubo un error: " + response.descripcion);
						}
					}
				},
			});
}

function dialogoCargaImagenes() {
	var windowSelecCarCli = $("#windowCargaImagenes").kendoWindow({
		actions : [ "Close" ],
		modal : true,
		resizable : false,
		content : {
			url : contexPath + "/ticket/WindowRegistraImagenes.htm"
		},
		height : "120px",
		width : "450px",
		title : "Registro Fotográfico"
	});
	windowSelecCarCli = $("#windowCargaImagenes").data("kendoWindow");
	windowSelecCarCli.center();
	windowSelecCarCli.open();
}

function registroImagenesViciosOcultos() {

	var formData = new FormData(document
			.getElementById("cargaImagenesViciosOcultos"));
	formData.append('idTicket', window.parent.$("#idTicket").html());
	var row = window.parent.$('table tr.k-state-selected td:first-child')
			.text();
	formData.append('consecutivo', row);

	$
			.ajax({
				type : "POST",
				cache : false,
				url : contexPath + "/ticket/RegistraImagenViciosOcultos.htm",
				// data : "idAreaTicket=1",
				data : formData,
				processData : false,
				contentType : false,
				success : function(response) {
					if (response.mensaje == "SUCCESS") {
						window.parent
								.$("#mensajes_main")
								.html(
										"Carga fotografica exitosa, consecutivo "
												+ response.id_ticket_z
												+ " ticket "
												+ response.id_ticket_sap
												+ " fue cargado. <a href=\"#\" onclick=\"cierraCargaImagenesAtte('"
												+ response.id_ticket_sap
												+ "');return false;\">Actualizar registro fotográfico</a>");
						window.parent.$("#windowCargaImagenes").data(
								"kendoWindow").close();
						// limpiaForegistroImagenesViciosOcultosrmaCaptura();
					} else if (response.mensaje == "LOGOUT") {
						salirSistema();
					} else {
						$("#mensajes_main").html(
								"Hubo un error en el registro de ticket "
										+ response.descripcion);
					}
				}
			});
}

function cierraCargaImagenesAtte(idTicket) {
	window.parent.$("#windowAtencionConstruccionTicket").data("kendoWindow")
			.close();
	// alert("Ticket= " +idTicket);
	window.parent.actualizaFormaCaptura(idTicket);
}

function cierraCargaImagenesViciosOcultos() {
	window.parent.$("#windowConsultaConstruccionTicketDetalle").data(
			"kendoWindow").close();
	// alert("Ticket= " +idTicket);
	var idTicket = $("#idTicket").text();
	window.parent.actualizaFormaCaptura(idTicket);
}

function changeFunc(variable) {
		
			var selectBox = document.getElementsByName("categoria");
			var comboid= selectBox[variable].getAttribute('id');
			var selected = document.getElementById(comboid).value;
			//alert(selected);
			//alert(comboid);
	  
	        $('.subcategoria').eq(comboid).empty();
	    	
	        for (var indice in arraytextos) {
			texto = arraytextos[indice];
			var n = texto.lastIndexOf("|");
			menu =  texto.substr(0, n);
	        submenu =  texto.substr(n+1, texto.length);
			 if(selected == menu ){
				$('.subcategoria').eq(comboid).append("<option value=\""+ submenu+"\">" + submenu + "</option>");
			  }
	       }

	    
	    if(selected == "OTRO"){
	    	$('.subcategoria').eq(comboid).hide();
	    	$('.textoabierto').eq(comboid).show();
	    	$('.separador').eq(comboid).show();

		}else if(selected == ""){
		    $('.subcategoria').eq(comboid).hide();
		    $('.textoabierto').eq(comboid).hide();
	    	$('.separador').eq(comboid).hide();
		
		}else{
			$('.subcategoria').eq(comboid).show();
			$('.textoabierto').eq(comboid).hide();
			$('.separador').eq(comboid).show();
	    }
	    
	}


function eliminateDuplicates(arr) {
	 var i,
	     len=arr.length,
	     out=[],
	     obj={};

	 for (i=0;i<len;i++) {
	    obj[arr[i]]=0;
	 }
	 for (i in obj) {
	    out.push(i);
	 }
	 return out;
}

