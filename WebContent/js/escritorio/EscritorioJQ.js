/**
 * 
 */
var usuario;
var url_conf="";

function onSelectMenu(e)
{
	
	 var sp_main = $("#sp_escritorio").data("kendoSplitter");
	 
	 var seleccion=$.trim($(e.item).children(".k-link").text());
	 
	 if(seleccion=="Escritorio")
	 {
		 window.location=contexPath+'/Escritorio.htm';
	 }
	 else if(seleccion=="Registro, Consulta y modificaciones")
	 {
		 if(validaIngresoModulos("CAT_USU", "PERMISO_V"))
		 {
			 window.location=contexPath+'/CatalogoUsuarios.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }
		 
	 }
	 else if(seleccion=="Conciliacion Pagos")
	 {
		 if(validaIngresoModulos("CON_PAGO", "PERMISO_V"))
		 {
			 window.location=contexPath+'/ConciliacionPagos.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }
		 
	 }
	 else if(seleccion=="Registro Cartera")
	 {
		 if(validaIngresoModulos("CAT_CLI", "PERMISO_V"))
		 {
			 window.location=contexPath+'/RegistroCarCliente.htm?from=agregaCartera';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }		 
	 }
	 else if(seleccion=="Consulta y modificaciones Cartera")
	 {
		 if(validaIngresoModulos("CAT_CLI", "PERMISO_V"))
		 {
			 window.location=contexPath+'/CatalogoCarClientes.htm?from=catalogo';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }
		 
	 }
	 else if(seleccion=="Reporte de clientes asignados a vendedor")
	 {
		 //if(validaIngresoModulos("CAT_CLI", "PERMISO_V"))
		 //{
			 window.location=contexPath+'/ReporteClientes.htm';
		 //}
		 //else
		 //{
			 //$("#mensajes_main").html("Usuario sin permisos para este modulo");
		 //}
		 
	 }
	 else if(seleccion=="Registro Clientes")
	 {
		 //if(validaIngresoModulos("CAT_CLI", "PERMISO_V"))
		 //{
			 window.location=contexPath+'/RegistroCliente.htm';
		 //}
		 //else
		 //{
			 //$("#mensajes_main").html("Usuario sin permisos para este modulo");
		 //}		 
	 }
	 else if(seleccion=="Consultas Clientes")
	 {
		 if(validaIngresoModulos("CLIENTE", "PERMISO_V"))
		 {
			 window.location=contexPath+'/CatalogoClientes.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }
		 
	 }
	 else if(seleccion=="Administracion ubicaciones")
	 {
		 if(validaIngresoModulos("ADM_UBIC", "PERMISO_V"))
		 {
			 window.location=contexPath+'/AdministraMapeo.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }		 
	 }
	 else if(seleccion=="Control ubicaciones")
	 {
		 if(validaIngresoModulos("CTR_UBIC", "PERMISO_V"))
		 {
			 window.location=contexPath+'/UbicacionesMain.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }		 
	 }
	 else if(seleccion=="Consulta y modificaciones")
	 {
		 //if(validaIngresoModulos("CTR_UBIC", "PERMISO_V"))
		 //{
			 window.location=contexPath+'/CierreVenta.htm?from=cotizador';
		 //}
		 //else
		 //{
			 //$("#mensajes_main").html("Usuario sin permisos para este modulo");
		 //}
		 
	 }
	 else if(seleccion=="Registrar pago")
	 {
		 if(validaIngresoModulos("CTR_PAGO_R", "PERMISO_V"))
		 {
			 window.location=contexPath+'/CierreVenta.htm?from=pagoIni';
		 }
		 else
		{
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }
		 
	 }
	 else if(seleccion=="Registrar")
	 {
		 if(validaIngresoModulos("DIGIT", "PERMISO_C"))
		 {
			 window.location=contexPath+'/DigitalizacionFiles.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }		 
	 }
	 else if(seleccion=="Visualizar")
	 {
		 if(validaIngresoModulos("DIGIT", "PERMISO_V"))
		 {
			 window.location=contexPath+'/Digitalizacion.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }
		 
	 }
	 else if(seleccion=="Registro Ticket")
	 {
		 if(validaIngresoModulos("TICKET", "PERMISO_C"))
		 {
			 window.location=contexPath+'/tickets/RegistroTicket.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }
		 
	 }
	 else if(seleccion=="Consulta de tickets")
	 {
		 if(validaIngresoModulos("TICKET", "PERMISO_V"))
		 {
			 window.location=contexPath+'/ticket/ConsultaCatalogoTicketsView.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }		 
	 }
	 else if(seleccion=="Estado de cuenta")
	 {
		 if(validaIngresoModulos("REPORTE A", "PERMISO_V"))
		 {
			 window.location=contexPath+'/report/estadocuenta/FiltroBusquedaView.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }	
		 
	 }
	 else if(seleccion=="Cancelacion parcial y traspasos")
	 {
		 if(validaIngresoModulos("COTIZ", "PERMISO_V"))
		 {
			 window.location=contexPath+'/CierreVenta.htm?from=cancelacion';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }	
		 
	 }
	 else if(seleccion=="Traspasos")
	 {
		 //if(validaIngresoModulos("REPORTE", "PERMISO_A"))
		 //{
		 	  window.location=contexPath+'/CierreVenta.htm?from=traspasos';
		 //}
		 //else
		 //{
		 	 //$("#mensajes_main").html("Usuario sin permisos para este modulo");
		 //}	
		 
	 }
	 else if(seleccion=="Monitor de Pagos")
	 {
		 if(validaIngresoModulos("CTR_PAGO_M", "PERMISO_V"))
		 {
			 window.location=contexPath+'/MonitorPagosPrincipal.htm?from=monitorPagoPrin';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }	
		 
	 } 
	 else if(seleccion=="Disponibilidad")
	 {
		 if(validaIngresoModulos("REPORTE B", "PERMISO_V"))
		 {
			 window.location=contexPath+'/report/disponibilidad/FiltroBusquedaView.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }	
		 
	 } 
	 else if(seleccion=="Lista Precios")
	 {
		 if(validaIngresoModulos("REPORTE C", "PERMISO_V"))
		 {
			 window.location=contexPath+'/report/listaprecios/FiltroBusquedaView.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }	
		 
	 } 
	 else if(seleccion=="Ventas")
	 {
		 if(validaIngresoModulos("REPORTE D", "PERMISO_V"))
		 {
			 window.location=contexPath+'/report/ventas/FiltroBusquedaView.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }	
		 
	 } 
	 else if(seleccion=="Referencia bancaria")
	 {
		 if(validaIngresoModulos("REPORTE E", "PERMISO_V"))
		 {
			 window.location=contexPath+'/report/referencias/FiltroBusquedaView.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }	
		 
	 } 
	 else if(seleccion=="Contratos")
	 {
		 if(validaIngresoModulos("REPORTE F", "PERMISO_V"))
		 {
			 window.location=contexPath+'/report/contratos/FiltroBusquedaView.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }	
		 
	 }
	 else if(seleccion=="Comisiones")
	 {
		 if(validaIngresoModulos("REPORTE F", "PERMISO_V"))
		 {
			 window.location=contexPath+'/report/comisiones/FiltroBusquedaView.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }	
		 
	 }//nueva opción menu
	 else if(seleccion=="Consulta de Pagos")
	 {
		 if(validaIngresoModulos("CTR_PAGO_C", "PERMISO_V"))
		 {
			 window.location=contexPath+'/report/consultapagos/FiltroBusquedaView.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }	
		 
	 }
	 else if(seleccion=="Publicación")
	 {
		 if(validaIngresoModulos("BOLETIN", "PERMISO_V"))
		 {
			 window.location=contexPath+'/RegistroBoletin.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }	 
	 }
	 else if(seleccion=="Consulta de tickets")
	 {
		 if(validaIngresoModulos("TICKET", "PERMISO_V"))
		 {
			 window.location=contexPath+'/ticket/ConsultaCatalogoTicketsView.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }	 
	 }
	 else if(seleccion=="Tickets")
	 {
		 if(validaIngresoModulos("TICKET", "PERMISO_V"))
		 {
			 window.location=contexPath+'/ticket/ReporteTicketsView.htm';
		 }
		 else
		 {
			 $("#mensajes_main").html("Usuario sin permisos para este modulo");
		 }	
		 
	 }

	//kendoConsole.log("Selected: " + $(e.item).children(".k-link").text());
}

function salirSistema()
{
	$.ajax({  
	    type: "POST",  
	    url: contexPath + "/Escritorio.htm",  
	    data: "accion=salir",  
	    success: function(response){
	      // we have the response 
	      if(response.respSesionEscritorio.mensaje == "SUCCESS"){
	    	  window.location=contexPath + "/Login.htm";   
	      }
	      else if(response.respSesionEscritorio.mensaje == "LOGOUT")
	      {
	    	  window.location=contexPath + "/Login.htm";
	      }
	      else
	      {
	    	  $("#mensajes_main").html("Error: "+response.respSesionEscritorio.descripcion);
	    	  window.location=contexPath + "/Login.htm";
	      }	      
	    },  
	    error: function(xhr, textStatus, errorThrown) { 
	           // Don't raise this alert if user has navigated away from the page 
	    	$("#mensajes_main").html("Error: "+textStatus);
	    	window.location=contexPath + "/Login.htm";
	    }	    
	}); 
}

function validaIngresoModulos(module_, authr_)
{
	var permisosusr=[];
	var permisoscat=[];
	var validacion=false;
	var existe_p=false;
	var existe_l=false;
	
	if(usuario!=null && usuario!=undefined)
	{
		permisoscat=usuario.catalogoPermisos;
		permisosusr=usuario.permisosUserList;
		
		var id_permiso_="";
		for(var i=0; i<permisoscat.length; i++)
		{
			if(permisoscat[i].module==module_ && permisoscat[i].authr==authr_)
			{
				id_permiso_=permisoscat[i].id_permiso;
				existe_p=true;
				break;
			}
		}
		
		if(existe_p)
		{
			for(var i=0; i<permisosusr.length; i++)
			{
				if(permisosusr[i].id_permiso==id_permiso_)
				{
					existe_l=true;
					break;
				}
			}
		}
		
		if(existe_l)
		{
			validacion=true;
		}
		else
		{
			validacion=false;
		}
	}
	else
	{
		$("#mensajes_main").html("Error: No se cargaron los permisos para la aplicacion. Favor de reingresar al sistema");
	}
	return validacion;
}

function getPermisos()
{
	$.ajax({  
	    type: "POST",  
	    url: contexPath + "/Escritorio.htm",  
	    data: "accion=permisos",  
	    success: function(response){
	      // we have the response 
	      if(response.respPermisosUser.mensaje == "SUCCESS"){
	    	  usuario=response.respPermisosUser.usuario;
	    	  //url_conf=response.respPermisosUser.url_conf;
	    	  url_conf="http://vivienda.ggi.com.mx"; 
	    	  setLogosAndCss();
	      }
	      else if(response.respPermisosUser.mensaje == "LOGOUT")
	      {
	    	  salirSistema();
	      }
	      else
	      {
	    	  $("#mensajes_main").html("Error: "+response.respPermisosUser.descripcion);
	      }	      
	    },  
	    error: function(xhr, textStatus, errorThrown) { 
	           // Don't raise this alert if user has navigated away from the page 
	    	$("#mensajes_main").html("Error: "+textStatus);
	    }	    
	}); 
}

function setLogosAndCss()
{
	$(".fadein").css({'position':'relative', 'height':'64px', 'width':'64px'});
	
	var imagenes_fade='<img src="'+url_conf+'/imagenes/logos_header/login_logoggi.png" id="ifi1"/>';	
	imagenes_fade+='<img src="'+url_conf+'/imagenes/logos_header/login_logoggi.png" id="ifi2"/>';
	imagenes_fade+='<img src="'+url_conf+'/imagenes/logos_header/logo_'+usuario.id_ut_sup+'.png" id="ifi3"/>';
	imagenes_fade+='<img src="'+url_conf+'/imagenes/logos_header/logo_'+usuario.id_ut_sup+'.png" id="ifi4"/>';
	imagenes_fade+='<img src="'+url_conf+'/imagenes/logos_header/logo_des_'+usuario.id_ut_sup+'.png" id="ifi5"/>';
	$("#slaimg").html(imagenes_fade);		
	
	$('#ifi3').error(function() {
		$('#ifi3').attr("src",url_conf+"/imagenes/logos_header/login_logoggi.png");
	});
	$('#ifi4').error(function() {
		$('#ifi4').attr("src",url_conf+"/imagenes/logos_header/login_logoggi.png");
	});
	$('#ifi5').error(function() {
		$('#ifi5').attr("src",url_conf+"/imagenes/logos_header/login_logoggi.png");
	});

	$("#ifi1").css({'position':'absolute'});
	$("#ifi2").css({'position':'absolute'});
	$("#ifi3").css({'position':'absolute'});
	$("#ifi4").css({'position':'absolute'});
	$("#ifi5").css({'position':'absolute'});
	//$("#ifi3").css({'position':'absolute', 'left':'0px', 'top':'0px'});
	$(function(){
    	$('.fadein img:gt(0)').hide();
    	setInterval(function(){$('.fadein :first-child').fadeOut().next('img').fadeIn().end().appendTo('.fadein');}, 5000);
    });
}

$(document).ready(function() {	
	getPermisos();
	var menu = $('#menu').kendoMenu({
		dataSource: [{
            text: "Escritorio", spriteCssClass: "brazilFlag"
        },
        {
            text: "Administracion", spriteCssClass: "indiaFlag", items: [
                { text: "Usuarios", spriteCssClass: "historyIcon", items: [
                   {text: "Registro, Consulta y modificaciones", spriteCssClass: "historyIcon"}
                   ]
                },
                { text: "Conciliacion Pagos", spriteCssClass: "geographyIcon" },
                { text: "Digitalizacion", spriteCssClass: "geographyIcon", items: [
                  	{ text: "Registrar", spriteCssClass: "geographyIcon" },
                  	{ text: "Visualizar", spriteCssClass: "geographyIcon" }
                   ] 
                },
                { text: "Administracion ubicaciones", spriteCssClass: "geographyIcon" }
            ]
        },
        {           
            text: "Atiende a cliente", spriteCssClass: "geographyIcon", items: [
            	{ text: "Cartera de clientes", spriteCssClass: "geographyIcon" , items: [
                 	{ text: "Registro Cartera", spriteCssClass: "geographyIcon" },
                  	{ text: "Consulta y modificaciones Cartera", spriteCssClass: "geographyIcon" },
                  	{ text: "Reporte de clientes asignados a vendedor", spriteCssClass: "geographyIcon" }
                 	]
            	},            	 
            	{ text: "Control de ubicaciones", spriteCssClass: "geographyIcon", items: [                	
                  	{ text: "Control ubicaciones", spriteCssClass: "geographyIcon" }
                   ]
            	},
            	{ text: "Cotizador", spriteCssClass: "geographyIcon", items: [                 	
                  	{ text: "Consulta y modificaciones", spriteCssClass: "geographyIcon" },
                  	{ text: "Cancelacion parcial y traspasos", spriteCssClass: "geographyIcon" }
                   ]
            	},            	
            	{ text: "Tickets", spriteCssClass: "geographyIcon", items: [
                    { text: "Registro Ticket", spriteCssClass: "geographyIcon" },
                	{ text: "Consulta de tickets", spriteCssClass: "geographyIcon" }
                  ]
              	}
             ] 
        },
        {
        	text: "Cierre venta", spriteCssClass: "indiaFlag", items: [
                         { text: "Clientes", spriteCssClass: "historyIcon", items: [                
                         {text: "Consultas Clientes", spriteCssClass: "historyIcon"}
                    ]
                 },
                 {
                	 text: "Control pagos", spriteCssClass: "geographyIcon", items: [
                         { text: "Registrar pago", spriteCssClass: "geographyIcon"},
                         { text: "Monitor de Pagos", spriteCssClass: "geographyIcon" },
                         { text: "Consulta de Pagos", spriteCssClass: "geographyIcon" }
                    ]
                 },
                 {
                	 text: "Boletín", spriteCssClass: "geographyIcon", items: [
                         { text: "Publicación", spriteCssClass: "geographyIcon"},
                    ]
                 }                  
             ]
        },        
        {        	
            text: "Reportes", spriteCssClass: "geographyIcon", items: [
                 { text: "Estado de cuenta", spriteCssClass: "geographyIcon"},
                 { text: "Disponibilidad", spriteCssClass: "geographyIcon"},
                 { text: "Lista Precios", spriteCssClass: "geographyIcon"},
                 { text: "Ventas", spriteCssClass: "geographyIcon"},
                 { text: "Referencia bancaria", spriteCssClass: "geographyIcon"}, 
                 { text: "Contratos", spriteCssClass: "geographyIcon"},
                 { text: "Comisiones", spriteCssClass: "geographyIcon"},
                 { text: "Tickets", spriteCssClass: "geographyIcon"}
            ]
        
        }],
        hoverDelay: 700,
		orientation: 'vertical',
		select: onSelectMenu
	}).data("kendoMenu");

});

function setEstatusBar(mensaje,tipo)
{
	var estatus="";
	if(tipo=="error")
	{
		estatus="rojo.png";
	}
	else if(tipo=="warning")
	{
		estatus="amarillo.png";
	}
	else if(tipo=="mensaje")
	{
		estatus="verde.png";
	}
	
	//$("#estatus_img").attr("src",url_conf+"/GestionVivienda/images/page/"+estatus);
	$("#mensajes_main").html(mensaje);	
	//$("#base_estatus").effect("bounce", { times:5 }, 300);
}
    
