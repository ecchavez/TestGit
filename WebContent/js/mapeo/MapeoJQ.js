/**
 * 
 */
var imagenesSelec = [];
var uts_num = [];

function getDatosMapaDB(idUbicacion, idUbicacionFloor) {  
	  var iframe = $('#iframe_maper');  
	  var userInfo = "";
	  var utsid_any = "";
	  
	  $(iframe).attr('src', '');
	  
	  if(idUbicacionFloor!="")
	  {
		  utsid_any=idUbicacionFloor;
	  }
	  else
	  {
		  utsid_any=idUbicacion
	  }
	  
	  
	  
	  $.ajax({  
	    type: "POST",  
	    url: contexPath + "/AdministraMapeo.htm",  
	    data: "accion=imagen_datos&i_id_ut_current="+utsid_any, 
	    success: function(response){
	      // we have the response 
	      if(response.respDatosImagen.mensaje == "SUCCESS"){	
	    	  $.unblockUI();
	    	  uts_num=response.respDatosImagen.listaImagenDatos;
	    	  if(uts_num.length>=1)
	    	  {	    		  
	    		  var url_maping=contexPath +'/MultipleImagenMapeo.htm?idUbicacion='+idUbicacion+'&i_id_eq_curr='+idUbicacionFloor+'&urlImagen='+uts_num[0].urlImagen+'&nombreImagen='+uts_num[0].nombreImagen;
	    	      $(iframe).attr('src', url_maping);
	    		   
	    		  $("#mensajes_main").html("Se encontraron "+uts_num.length+ " registros");
	    	  }
	    	  else
	    	  {
	    		  var url_maping=contexPath +'/MultipleImagenMapeo.htm?idUbicacion='+idUbicacion+'&i_id_eq_curr'+idUbicacionFloor+'=&urlImagen=&nombreImagen=sinimagenUTA101010.png';
	    	      $(iframe).attr('src', url_maping);
	    		  $("#mensajes_main").html("No se encontraron ubicaciones para esta sesion");
	    	  }
	    	  
	      }else{
	    	  $.unblockUI();	    	  
    		  var url_maping=contexPath +'/MultipleImagenMapeo.htm?idUbicacion='+idUbicacion+'&i_id_eq_curr='+idUbicacionFloor+'&urlImagen=&nombreImagen=sinimagenUTA101010.png';
    	      $(iframe).attr('src', url_maping);
	    	  $("#mensajes_main").html(response.respDatosImagen.descripcion);
	      }	      
	    },  
	    error: function(e){  
	    	$.unblockUI();
	    	$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
	    }  
	  });  
	}  

function selecciona_imagen(nombre_img, id_ubicacion)
{
	  $("#id_ut").val(id_ubicacion);
	  $("#img_nom").val(nombre_img);
}
function limpiar_actualizacion()
{
	  $("#id_ut").val("");
	  $("#img_nom").val("");
}


function onSelectTabImages(e) {
   $("#accion").val(e.item.id);
}

function poppushImagenes(nombre_img, id_ubicacion, checado)
{
	  if(checado)
	  {
		  imagenesSelec.push({id_ubicacion: id_ubicacion, nombre_img: nombre_img});
		  var dg_imgs= $("#dg_imagenes").data("kendoGrid");
		  dg_imgs.dataSource.data([]);
		  dg_imgs.dataSource.data(imagenesSelec);
	  }
	  else
	  {

		for(var i=0; i<imagenesSelec.length; i++)
		{
			if(imagenesSelec[i].id_ubicacion==id_ubicacion)
			{
				imagenesSelec.splice(i, 1);
			}
		}
	  var dg_imgs= $("#dg_imagenes").data("kendoGrid");
	  dg_imgs.dataSource.data([]);
	  dg_imgs.dataSource.data(imagenesSelec);
	 }
}

function setCoordenadasImagen()
{	
	 var idUbicacion = $("#idUbicacion").val();
	 var coord_img = $("#coordenadas").val();
	 
	 $.ajax({  
		    type: "POST",  
		    url: contexPath + "/MapeoImagenComponent.htm",  
		    data: "accion=addCoordenadas&i_id_ut_current="+idUbicacion+"&i_coords="+coord_img, 
		    success: function(response){
		      // we have the response 
		      if(response.respDatosImagen.mensaje == "SUCCESS"){	 
		    	  window.parent.$("#mensajes_main").html(response.respDatosImagen.descripcion);
		    	  window.parent.$("#winchaneimg1").data("kendoWindow").close();
		      }else{
		    	  window.parent.$("#mensajes_main").html(response.respDatosImagen.descripcion);
		    	  window.parent.$("#winchaneimg1").data("kendoWindow").close();
		      }	      
		    },  
		    error: function(e){  
		    	window.parent.$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
		    	window.parent.$("#winchaneimg1").data("kendoWindow").close();
		    } 	    
		  }); 
	 window.parent.enableDisableNivel(window.parent.radio_ut, true);
}