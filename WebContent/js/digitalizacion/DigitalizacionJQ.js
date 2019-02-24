var carpetas_dig=[];
var carpetas_dig_=[];
var estatus_car=[];

function getFasesDigit(accion)
	{			
		  var datosFaseList = $("#cmb_faces").data('kendoComboBox');
		  var itemFases = datosFaseList.dataItem();
		  var datosEquiposList = $("#cmb_equipo").data('kendoComboBox');
		  var itemEquipos = datosEquiposList.dataItem();
		  var datosTiposList = $("#cmb_tipos").data('kendoComboBox');
		  var itemTipos = datosTiposList.dataItem();
		  var datosSubTiposList = $("#cmb_subtipos").data('kendoComboBox');
		  var itemSubTipos = datosSubTiposList.dataItem();
		  var datosEstatusList = $("#cmb_estatus").data('kendoComboBox');		
		  var itemEstatus = datosEstatusList.dataItem();
		  
		  var iduts="";
		  
		  if(accion=="faces")
		  {
			 /*if(itemFases != undefined)
			  iduts=itemFases.ubicacionClave;*/
		  }
		  else if(accion=="equipos")
		  {
			  if(itemFases != undefined)
				  iduts=itemFases.ubicacionClave;
		  }
		  //onVentanaWait("Espere ", true)
		  $.ajax({  
		    type: "POST",  
		    url: contexPath + "/DigitalizacionFiles.htm",  
		    data: "accion="+accion+"&i_id_ut_current="+iduts, 
		    success: function(response){
		      // we have the response 
		      if(response.respGetUnidadesTecnicasSuperiores.mensaje == "SUCCESS"){	
		    	  //$.unblockUI()
		    	  var uts_num=response.respGetUnidadesTecnicasSuperiores.objUbicacionesList;
		    	  var ute_num=response.respGetUnidadesTecnicasSuperiores.objEquiposList;
		    	  carpetas_dig = response.respGetUnidadesTecnicasSuperiores.carpetasDigitalizacion;
		    	  carpetas_dig_ = response.respGetUnidadesTecnicasSuperiores.carpetasDigitalizacion;
		    	  estatus_car = response.respGetUnidadesTecnicasSuperiores.estatusDigitalizacion;
		    	  
		    	  if(uts_num.length>=1 || ute_num.length>=1)
		    	  {	  	    		  
		    		  if(accion=="faces")
		    		  {  
			    		  //$("#mensajes_main").html("Se encontraron "+uts_num.length+ " registros");
			    		  
			    		  datosFaseList.dataSource.data([]);     
			    		  datosFaseList.dataSource.data(uts_num);
			    		  datosFaseList.select(0);
			    		  itemFases = datosFaseList.dataItem();	
			    		  
			    		  getFasesDigit("equipos");
		    		  }
		    		  else if(accion=="equipos")
		    		  {
		    			  //$("#mensajes_main").html("Se encontraron "+ute_num.length+ " registros");
			    		  
		    			  datosEquiposList.dataSource.data([]);     
		    			  datosEquiposList.dataSource.data(ute_num);
		    			  datosEquiposList.select(0);
		    			  itemEquipos = datosEquiposList.dataItem();
		    			  		    			  
		    			  
		    			  datosTiposList.dataSource.data([]);     
		    			  datosTiposList.dataSource.data(getProcesos("proceso","00"));
		    			  datosTiposList.select(0);	
		    			  itemTipos = datosTiposList.dataItem();
		    			  
		    			  datosSubTiposList.dataSource.data([]);     
		    			  
		    			  var subtipos = getProcesos("",itemTipos.id_ticket_area);
		    			 
		    			  if(subtipos.length>=1)
		    			  {
		    				    datosSubTiposList.dataSource.data([]);
		    					datosSubTiposList.dataSource.data(subtipos);
		    				    datosSubTiposList.select(0);
		    				    itemSubTipos=datosSubTiposList.dataItem();
		    				    
		    				    
		    				    datosSubTiposList.dataSource.data([]);
		    					datosSubTiposList.dataSource.data(subtipos);
		    				    		    
		    					var datos_est=getEstatusCar(itemSubTipos.id_ticket_file, itemSubTipos.id_ticket_area);	
		    					datosEstatusList.dataSource.data([]);
		    					
		    					if(datos_est.length>=1)
		    					{
		    						datosEstatusList.dataSource.data(datos_est);
		    					    datosEstatusList.select(0);			
		    					} 
		    					else
		    					{
		    						datosEstatusList.dataSource.data([]);
		    						datosEstatusList.value(null);
		    					}
		    			  }	    			  
		    		  }
		    	  }
		    	  else
		    	  {
		    		  //$.unblockUI()
		    		  $("#mensajes_main").html("No se encontraron ubicaciones para esta sesion");	    		  
		    	  }
		    	  
		      }
		      else if(response.respGetUnidadesTecnicasSuperiores.mensaje == "LOGOUT")
		      {
		    	  salirSistema();
		      }
		      else
		      {
		    	  //$.unblockUI()
		    	  $("#mensajes_main").html(response.respGetUnidadesTecnicasSuperiores.descripcion);
		      }	      
		    },  
		    error: function(e){  
		    	//$.unblockUI()
		    	$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
		    }  
		  });  
		}  
		
	function onChangeUTSFaces(e)
	{
		var datosFaseList = $("#cmb_faces").data('kendoComboBox');
		var itemFases = datosFaseList.dataItem();
		$("#txt_desc_fase").html(itemFases.text);
		getFasesDigit("equipos");		
		datosEstatusList.dataSource.data([]);	
		datosEstatusList.refresh();
	}
	

	function onChangeUTSequipo(e)
	{
		var datosTiposList = $("#cmb_tipos").data('kendoComboBox');
		var itemTipos = datosTiposList.dataItem();
		var datosSubTiposList = $("#cmb_subtipos").data('kendoComboBox');
		var itemSubTipos = datosSubTiposList.dataItem();
		var datosEquiposList = $("#cmb_equipo").data('kendoComboBox');		
		var itemEquipos = datosEquiposList.dataItem();
		var datosEstatusList = $("#cmb_estatus").data('kendoComboBox');		
		var itemEstatus = datosEstatusList.dataItem();
		datosEstatusList.dataSource.data([]);
		
		$("#txt_desc_equ").html(itemEquipos.id_equnrx);
		
		datosTiposList.dataSource.data([]);     
		datosTiposList.dataSource.data(getProcesos("proceso","00"));
		datosTiposList.select(0);	
		itemTipos = datosTiposList.dataItem();
		  
		datosSubTiposList.dataSource.data([]);     
		  
		var subtipos =getProcesos("",itemTipos.id_ticket_area);
		
		if(subtipos.length>=1)
		{
			datosSubTiposList.dataSource.data([]);
			datosSubTiposList.dataSource.data(subtipos);
		    datosSubTiposList.select(0);
		    itemSubTipos=datosSubTiposList.dataItem();
		    
		    
		    datosSubTiposList.dataSource.data([]);
			datosSubTiposList.dataSource.data(subtipos);
		    		    
			var datos_est=getEstatusCar(itemSubTipos.id_ticket_file, itemSubTipos.id_ticket_area);	
			datosEstatusList.dataSource.data([]);
			
			if(datos_est.length>=1)
			{
				datosEstatusList.dataSource.data(datos_est);
			    datosEstatusList.select(0);			
			}
			else
			{
				datosEstatusList.dataSource.data([]);
				datosEstatusList.value(null);
			}
			
		}
		
	}
	
	function onChangeUTSTipos(e)
	{
		var datosTiposList = $("#cmb_tipos").data('kendoComboBox');
		var itemTipos = datosTiposList.dataItem();
		var datosSubTiposList = $("#cmb_subtipos").data('kendoComboBox');
		datosSubTiposList.dataSource.data(getProcesos("",itemTipos.id_ticket_area,datosSubTiposList));
		datosSubTiposList.select(0);
		var datosEstatusList = $("#cmb_estatus").data('kendoComboBox');		
		datosEstatusList.dataSource.data([]);
		
		var subtipos =getProcesos("",itemTipos.id_ticket_area);
		
		if(subtipos.length>=1)
		{
			datosSubTiposList.dataSource.data([]);
			datosSubTiposList.dataSource.data(subtipos);
		    datosSubTiposList.select(0);
		    itemSubTipos=datosSubTiposList.dataItem();
		    		    
		    datosSubTiposList.dataSource.data([]);
			datosSubTiposList.dataSource.data(subtipos);
		    		    
			var datos_est=getEstatusCar(itemSubTipos.id_ticket_file, itemSubTipos.id_ticket_area);	
			datosEstatusList.dataSource.data([]);
			
			if(datos_est.length>=1)
			{
				datosEstatusList.dataSource.data(datos_est);
			    datosEstatusList.select(0);			
			}
			else
			{
				datosEstatusList.dataSource.data([]);
				datosEstatusList.value(null);
			}
			
		}
		
		if (itemTipos.id_ticket_areax == "ENTREGA PRODUCTO") {
			$(".fechaGarantia").show();
		}else{
			$(".fechaGarantia").hide();
		}

	}
	
	function onChangeUTSubSTipos(e)
	{
		var datosSubTiposList = $("#cmb_subtipos").data('kendoComboBox');
		itemSubTipos=datosSubTiposList.dataItem();
		var datosEstatusList = $("#cmb_estatus").data('kendoComboBox');		
		var datos_est=getEstatusCar(itemSubTipos.id_ticket_file, itemSubTipos.id_ticket_area);	
		datosEstatusList.dataSource.data([]);
		
		if(datos_est.length>=1)
		{
			datosEstatusList.dataSource.data(datos_est);
		    datosEstatusList.select(0);			
		}
		else
		{
			datosEstatusList.dataSource.data([]);
			datosEstatusList.value(null);
		}

	}
	
	
	function getProcesos(tipo,id)
	{		
		var docs_folder = [];
		
		if(tipo=="proceso")
		{
			for(var i=0; i<carpetas_dig.length; i++)
		  	  {
		  		  if(carpetas_dig[i].id_ticket_file==id)
		  		  {			    
		  			  if(getProcesos("",carpetas_dig[i].id_ticket_area).length>=1)
	  				  {
		  				   docs_folder.push(carpetas_dig[i]);
	  				  }			          			    	
		  	  	  }
		  	  }
		}
		else
		{
			for(var i=0; i<carpetas_dig.length; i++)
		  	  {
		  		  if(carpetas_dig[i].id_ticket_area == id && carpetas_dig[i].id_ticket_file!="00")
		  		  {		    		  
			          docs_folder.push(carpetas_dig[i]);			    				 
		  	  	  }
		  	  }		
		}
		return docs_folder;
	}
	
	function getEstatusCar(idf,id)
	{		
		var estatus_folder = [];
		
		for(var i=0; i<estatus_car.length; i++)
	  	  {
	  		  if(estatus_car[i].id_ticket_file==idf && estatus_car[i].id_ticket_area==id)
	  		  {			  			  
	  			  estatus_folder.push(estatus_car[i]); 				 		          			    	
	  	  	  }
	  	  }
		
		return estatus_folder;
	}
	
	
	
	function getFasesCancelaciones(accion)
	{			
		  var datosFaseListCancel = $("#cmb_faces_cancel").data('kendoComboBox');
		  var itemFasesCancel = datosFaseListCancel.dataItem();
		  var datosEquiposListCancel = $("#cmb_equipo_cancel").data('kendoComboBox');
		  var itemEquiposCancel = datosEquiposListCancel.dataItem();
		  
		  var iduts="";
		  
		  if(accion=="faces")
		  {
			 /*if(itemFases != undefined)
			  iduts=itemFases.ubicacionClave;*/
		  }
		  else if(accion=="equipos")
		  {
			  if(itemFasesCancel != undefined)
				  iduts=itemFasesCancel.ubicacionClave;
		  }
		  //onVentanaWait("Espere ", true)
		  $.ajax({  
		    type: "POST",  
		    url: contexPath + "/DigitalizacionFiles.htm",  
		    data: "accion="+accion+"&i_id_ut_current="+iduts, 
		    success: function(response){
		      // we have the response 
		      if(response.respGetUnidadesTecnicasSuperiores.mensaje == "SUCCESS"){	
		    	  //$.unblockUI()
		    	  var uts_num=response.respGetUnidadesTecnicasSuperiores.objUbicacionesList;
		    	  var ute_num=response.respGetUnidadesTecnicasSuperiores.objEquiposList;
		
		    	  
		    	  if(uts_num.length>=1 || ute_num.length>=1)
		    	  {	  	    		  
		    		  if(accion=="faces")
		    		  {  
			    		  //$("#mensajes_main").html("Se encontraron "+uts_num.length+ " registros");
			    		  
			    		  datosFaseListCancel.dataSource.data([]);     
			    		  datosFaseListCancel.dataSource.data(uts_num);
			    		  datosFaseListCancel.select(0);
			    		  itemFasesCancel = datosFaseListCancel.dataItem();	
			    		  
			    		  getFasesCancelaciones("equipos");
		    		  }
		    		  else if(accion=="equipos")
		    		  {
		    			  //$("#mensajes_main").html("Se encontraron "+ute_num.length+ " registros");
			    		  
		    			  datosEquiposListCancel.dataSource.data([]);     
		    			  datosEquiposListCancel.dataSource.data(ute_num);
		    			  datosEquiposListCancel.select(0);
		    			  itemEquiposCancel = datosEquiposListCancel.dataItem();		    			    			  
		    		  }
		    	  }
		    	  else
		    	  {
		    		  //$.unblockUI()
		    		  $("#mensajes_main").html("No se encontraron ubicaciones para esta sesion");	    		  
		    	  }
		    	  
		      }
		      else if(response.respGetUnidadesTecnicasSuperiores.mensaje == "LOGOUT")
		      {
		    	  salirSistema();
		      }
		      else
		      {
		    	  //$.unblockUI()
		    	  $("#mensajes_main").html(response.respGetUnidadesTecnicasSuperiores.descripcion);
		      }	      
		    },  
		    error: function(e){  
		    	//$.unblockUI()
		    	$("#mensajes_main").html("Fallo el acceso a los datos " + e.responseText + " " + e.status);
		    }  
		  });  
		}  	
	
	function onChangeUTSFacesTraspaso(e)
	{
		var datosFaseListCancel = $("#cmb_faces_cancel").data('kendoComboBox');
		var itemFasesCancel = datosFaseListCancel.dataItem();

		getFasesCancelaciones("equipos");			
	}
	

	function onChangeUTSequipoTraspaso(e)
	{		
		var datosEquiposListCancel = $("#cmb_equipo_cancel").data('kendoComboBox');			
	}
	
	function onChangeUTSequipoTraspaso(e)
	{		
		var datosEquiposListCancel = $("#cmb_equipo_cancel").data('kendoComboBox');			
	}
	
	function onChangeSumaAnioFechaFin(e)
	{	
	  var fechaFin = $("#fechaFin").data("kendoDatePicker");
	  
	  var year = kendo.toString($("#fechaInicio").data("kendoDatePicker").value(), 'yyyy');
	  var month = kendo.toString($("#fechaInicio").data("kendoDatePicker").value(), 'MM');
	  var day = kendo.toString($("#fechaInicio").data("kendoDatePicker").value(), 'dd');

	  year = parseInt(year) + 1;
	  month = parseInt(month) - 1;
	  fechaFin.value(new Date(year,month,day));
	  
	}
	
	function checkDates() {
		
		var fechaInicio = $("#fechaInicio").data("kendoDatePicker");
		var fechaFin = $("#fechaFin").data("kendoDatePicker");
		
        if (fechaInicio.value().trim() != '' && fechaFin.value().trim() != '') {
            if (Date.parse(fechaInicio.value()) > Date.parse(fechaFin.value())) {
            	alert("Fecha fin tiene que ser posterior a fecha de inicio");
	              
                  var year = kendo.toString($("#fechaInicio").data("kendoDatePicker").value(), 'yyyy');
	          	  var month = kendo.toString($("#fechaInicio").data("kendoDatePicker").value(), 'MM');
	          	  var day = kendo.toString($("#fechaInicio").data("kendoDatePicker").value(), 'dd');
	
	          	  year = parseInt(year) + 1;
	          	  month = parseInt(month) - 1;
	          	  fechaFin.value(new Date(year,month,day));
	          	$("#fechaFin").focus();
                
            }
        }
    }
	
 	function validaFechaRegExp(txtDate) {
 		var dateformat = '^(0[1-9]|[1-9]|[12][0-9]|3[01])[/](0[1-9]|[1-9]|1[012])[/](19|20)[0-9][0-9]';
	    //var Val_date=$('#datepicker').val();
	    if(txtDate.match(dateformat)){
	        var seperator1 = txtDate.split('/');
	
	        if (seperator1.length>1)
	        {
	            var splitdate = txtDate.split('/');
	        }else{
	        	return false;
	        }
	
	        var dd = parseInt(splitdate[0]);
	        var mm  = parseInt(splitdate[1]);
	        var yy = parseInt(splitdate[2]);
	        var ListofDays = [31,28,31,30,31,30,31,31,30,31,30,31];
	        if (mm==1 || mm>2)
	        {
	            if (dd>ListofDays[mm-1])
	            {
	                return false;
	            }
	        }
	        if (mm==2)
	        {
	            var lyear = false;
	            if ( (!(yy % 4) && yy % 100) || !(yy % 400))
	            {
	               lyear = true;
	            }
	            if ((lyear==false) && (dd>=29))
	            {
	                return false;
	            }
	            if ((lyear==true) && (dd>29))
	            {
	                return false;
	            }
	        }
	    }
	    else
	    {
	    return false;
	    }
	    return true;
     }
