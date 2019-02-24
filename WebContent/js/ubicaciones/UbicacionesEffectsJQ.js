/**
 * 
 */
$.fx.off = false;

var max_w_img_ren=240;
var max_h_img_ren=160;

var simulador_accionado=0;

function setImageToLeft(estado, idequpo)
{
	simulador_accionado=1;
	$("#btn_open_cotizador").attr("disabled", "disabled");
	//var dgdetalle = $("#dg_equipo_detalle").data("kendoGrid");
	//var datos_detalle_equ_=dgdetalle.dataSource.data();
	var left_tr="";
	var top_tr="";
	var opac_tr=0;
	var top_=0;
	
	if(estado=="left")
	{
		left_tr= '+=800px';
		top_tr= '+=280px';
		opac_tr=1.0;
		wi="25%";
		he="40%";
		top_ =-70;
	}
	else
	{
		left_tr= '-=800px';
		top_tr= '-=280px';
		opac_tr=0.0;
		wi="45%";
		he="60%";
		top_ =+70;
	}
		
	//if(datos_detalle_equ_.length>=1)
	//{	
		$("#render_imagen").animate({
		    left: left_tr,
		    height: he,
			width: wi,
			top: top_,
		  }, 1500, function() {
			  if(estado=="left")
			  {
				  callSimulador("iframe_simulador",idequpo);
				  $("#cotizador_container").show("slow", function() {
					  // Animation complete.
				  });
			  }
			  else
			  {
				  $("#iframe_simulador").attr("src","");
				  $("#cotizador_container").hide("slow", function() {
					  // Animation complete.
				  });
			  }
		  });
		
		$("#dg_equipo_detalle").animate({
			width: 300,
			top: top_tr,
		  }, 1500, function() {
		    // Animation complete.
		});
		
		/*$("#btn_open_cotizador").animate({
			left: '+=190px',
		  }, 1500, function() {
		    // Animation complete.
		});*/
		
		$("#cotizador_container").animate({
			opacity: opac_tr
		  }, 1000, function() {
		    // Animation complete.
		});	
	/*}
	else
	{
		setEstatusAccion("No hay datos para validar este equipo, recargue la pagina o salga de la sesion y vuelva a entrar","error");
	}*/	
}

function callSimulador(iframe_name, id_equnr)
{
	$("#"+iframe_name+"").attr("src",contexPath + "/Simulador.htm?idequ_get="+id_equnr+"&from=base");
}

function onSelectTU (e)
{
	if(e.item.outerText=="Seleccione un departamento ")
	{
		if(simulador_accionado==0)
		{
			$("#btn_open_cotizador").removeAttr("disabled");
		}
	}
	else
	{
		$("#btn_open_cotizador").attr("disabled", "disabled");
	}
}