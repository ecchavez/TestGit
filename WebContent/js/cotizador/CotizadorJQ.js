
	var hdr_datos=[];
	var hdr_datos_reload=[];
	var hdr_base=[];
	var simulador=false;
	var tipo_pago_g="CH";
	var datos_fpago=[];
	var datos_fpago_g=[];
	var max_porc_desc=0;
	var max_porc_eng=5;
	var max_porc_dif=5;
	var diferidos_editables=[];
	var datos_pagos_edit=[];
	var obj_from="";
	var sub_equipos_data=[];
	var sub_equipos_data_ini=[];
	var sub_equipos_select=[];
	var sub_equipos_select_ini=[];
	var tabi_pos=15;
	
	var descp_hd="";	
	var descm_hd="";
	var fecha_ent_hd="";
	var accion_format="";
	var datos_cotizador="";
	var equipo_sel="";
	
	var porc_diferido_val=0;
	
	var pos_tp;
	var imprime_after_getcot=0;
	
	
	function getCotizacionBase(accion)
	{	
		hdr_datos=[];
		hdr_datos_reload=[];
		hdr_base=[];
		var datos_post="";
		if(accion=="base")
		{
			datos_post="from="+$("#from_is").val()+"&accion="+accion+"&id_equnr="+$("#id_eln").val();
		}
		else if(accion=="traspasar")
		{
			datos_post="from="+$("#from_is").val()+"&accion="+accion+"&equiposXml="+$("#datosEquipos").val()+"&id_cotiz_z="+$("#idCotizacionZ").val()+"&id_equnr="+$("#id_eln").val()+"&equipo_select="+$("#equipoSelect").val()+"";
		}
		else if(accion=="cancelar")
		{
			datos_post="from="+$("#from_is").val()+"&accion="+accion+"&equiposXml="+$("#datosEquipos").val()+"&id_cotiz_z="+$("#idCotizacionZ").val()+"&id_equnr="+$("#id_eln").val()+"";
		}
		$.ajax({  
		    type: "POST",  
		    url: contexPath + "/Simulador.htm",  
		    data: datos_post,  
		    success: function(response){
		      // we have the response 
		      if(response.resCotizacion.mensaje == "SUCCESS"){
		    	  hdr_datos=response.resCotizacion.cotizacionHeaderList;
		    	  hdr_datos_reload = hdr_datos.slice(0);
		    	  hdr_base = response.resCotizacionBase.cotizacionHeaderList;		    	  
		    	  /*hdr_datos_reload=response.resCotizacion.cotizacionHeaderList;
		    	  hdr_base=response.resCotizacionBase.cotizacionHeaderList;*/
		    	  
		    	  
		    	  if(hdr_datos[0].si_no_pasa=="0")
	    		  {
	    			  setEstatusCotizacion("paso");	
	    			  window.parent.$("#btn_print").removeAttr("disabled", "disabled");
	    			  window.parent.$("#mensajes_main").html("Datos de cotizacion validados correctamente");	    			  
	    		  }
	    		  else
	    		  {
	    			  setEstatusCotizacion("npaso");
	    			  window.parent.$("#mensajes_main").html("La cotizacion no cumple con los requerimientos minimos, verifique datos");
	    		  }
		    	  
		    	  selectDatosByPago("CH","init");		    	  
		    	  		      
		      }
		      else if(response.resCotizacion.mensaje == "LOGOUT")
		      {
		    	  salirSistema();
		      }
		      else
		      {
		    	  window.parent.$("#mensajes_main").html(response.resCotizacion.descripcion);
		      }	      
		    },  
		    error: function(xhr, textStatus, errorThrown) { 
		           // Don't raise this alert if user has navigated away from the page 
		    	window.parent.$("#mensajes_main").html(textStatus);
		    },
		    /* error: function(e){  
		    	winAlert("Alert",e,100,200);  
		    } */
		  }); 

	}			
	
	function selectDatosByPago(tipo,from_accion)
	{
		datos_fpago=[];
		datos_fpago_g=[];
		diferidos_editables=[];
		sub_equipos_data_ini=[];
		
		for(var i=0; i<hdr_datos_reload.length; i++)
		{
			if(hdr_datos_reload[i].tive==tipo)
			{
				tipo_pago_g=tipo;
				pos_tp=i;
				if(parseFloat(hdr_datos[i].cotizacionDetalleList[4].netwr)>parseFloat(hdr_base[i].cotizacionDetalleList[4].netwr))
				{					
					datos_fpago=hdr_datos[i];

				}
				else
				{
					datos_fpago=hdr_base[i];
				}
				
				if(datos_fpago.si_no_pasa=="0")
	    		{
	    			setEstatusCotizacion("paso");
	    			window.parent.$("#btn_print").removeAttr("disabled", "disabled");
	    			window.parent.$("#mensajes_main").html("Datos de cotizacion validados correctamente");	    			  
	    		}
	    		else
	    		{
	    			setEstatusCotizacion("npaso");
	    			window.parent.$("#mensajes_main").html("La cotizacion no cumple con los requerimientos minimos, verifique datos");
	    		}
			}			
		}	
		
		if(from_accion=="init")
		{
			enableDisableComps(false);
			$("#activasimulador").attr('value', 'Activar');
			simulador=false;
		}
		
		imprimeDatosCotizador(datos_fpago, from_accion);
	}
	
	function imprimeDatosCotizador(datos, from_accion)
	{
		var datos_equipo=$('#dg_equipos').data('kendoGrid');
		var datos_detalle=$('#dg_detallepagos').data('kendoGrid');
		var depto_val=datos.id_equnr.split("-");
		var detalle=datos.cotizacionDetalleList;
		var pagos=datos.cotizacionBillPlanList;
		var subequipos_l=datos.cotizacionSubequipos;
		var cotizacion_any=datos.id_cotiz_z;
		equipo_sel=datos.id_equnr;
		
		if(cotizacion_any.indexOf("$")==-1)
		{
			var new_cotizacion_any = cotizacion_any.substring(1); 
			$('#id_cotiz_z').val("$"+new_cotizacion_any);
		}
		else
		{
			$('#id_cotiz_z').val(cotizacion_any);
		}
		
		$('#numl').val(datos.numl);
		$('#depto').val(depto_val[1]);
		$('#fechaiv').val(convertDate(datos.aedat));
		$('#datab').val(convertDate(datos.datab));
		$('#datub').val(convertDate(datos.datub));
		$('#textfield9').val(convertDate(datos.aedat));
		
		var fechaHora = new Date();
		var horas = fechaHora.getHours();
		var minutos = fechaHora.getMinutes();
		var segundos = fechaHora.getSeconds();
		
		if(horas < 10) { horas = '0' + horas; }
		if(minutos < 10) { minutos = '0' + minutos; }
		if(segundos < 10) { segundos = '0' + segundos; }
		
		$('#textfield10').val(horas+ ":" + minutos + ":" + segundos);
		
		//$('#fech_entreg').val(datos.fech_entreg);
		$('#nest').val(datos.nest);
		$('#nbod').val(datos.nbod);
		$('#m2tot').val(datos.m2tot);		
		$('#subequipos').autoNumericSet(0);
		
		if($('#descp').val()=="0" || $('#descp').val()=="" || from_accion=="init")
		{
			$('#descp').val("0");
		}
	
		if($('#descm').autoNumericGet()==0 || $('#descm').autoNumericGet()=="0" || $('#descm').autoNumericGet()=="" || from_accion=="init")
		{
			$('#descm').autoNumericSet(0);
		}
		
		$('#descp').val(parseInt(datos.descp_r));
		$('#descm').autoNumericSet(parseFloat(datos.descm_r).toFixed(2));
		
		descp_hd=datos.descp;
		descm_hd=datos.descm;
		fecha_ent_hd=datos.fech_entreg;
		//max_porc_desc=parseInt(datos.descp);
		max_porc_desc=parseFloat(datos.descp);
				
			for(var i=0; i<detalle.length; i++)
			{
				if(detalle[i].cve_web=="CUOTA ÚNICA")
				{
					$('#gastos_admin').autoNumericSet(parseFloat(detalle[i].netwr).toFixed(2));
				}
				else if(detalle[i].cve_web=="APARTADO")
				{
					$('#anticipo').autoNumericSet(parseFloat(detalle[i].netwr).toFixed(2));
				}
				else if(detalle[i].cve_web=="ENGANCHE")
				{
					$('#enganche').autoNumericSet(parseFloat(detalle[i].netwr).toFixed(2));
					
					$('#penganche').val(parseInt(detalle[i].netwrp));
					
					max_porc_eng = float2int(detalle[i].netwrp);
				}
				else if(detalle[i].cve_web=="DIFERIDO")
				{
					$('#diferido').autoNumericSet(parseFloat(detalle[i].netwr).toFixed(2));
					max_porc_dif = float2int(detalle[i].netwrp);
					$('#pdiferido').val(parseInt(detalle[i].netwrp));
					
					porc_diferido_val=parseInt(detalle[i].netwrp);
					if(porc_diferido_val<max_porc_dif)
					{
						$('#pdiferido').attr("disabled","disabled");
					}					
				}
				else if(detalle[i].cve_web=="EQUIPO")
				{
					$('#equipo').autoNumericSet(parseFloat(detalle[i].netwr).toFixed(2));
				}
				else if(detalle[i].cve_web=="PAGO_FINAL")
				{
					$('#pago_final').autoNumericSet(parseFloat(detalle[i].netwr).toFixed(2));
					
					$('#ppago_final').val(parseInt(detalle[i].netwrp));
				}
				else if(detalle[i].cve_web=="TOTAL")
				{
					$('#total').autoNumericSet(parseFloat(detalle[i].netwr).toFixed(2));
				}
			}
			//alert($('#enganche').attr('title'));
		
		sub_equipos_data_ini=subequipos_l;
		
		var total_equ=0;
		
		for(var i=0; i<sub_equipos_data_ini.length; i++)
		{
			
			if(parseFloat(sub_equipos_data_ini[i].netwr)>=1 && (sub_equipos_data_ini[i].tipo=="E" || sub_equipos_data_ini[i].tipo=="B"))
			{
				total_equ+=parseFloat(sub_equipos_data_ini[i].netwr);
			}
		}
		//$('#total').autoNumericSet(parseFloat($('#total').autoNumericGet())+total_equ);
		
		$('#subequipos').autoNumericSet(parseFloat(total_equ).toFixed(2));	
		
		setInitEquiposData(subequipos_l);
		
		datos_fpago_g=datos_fpago;
		//datos_detalle.dataSource.data([]);
		setDetallePlaning(pagos);
		
		$('#descmdes').autoNumericSet(eqmenosdesc());
	}
	
	function float2int(value){
	return value | 0;	
	}

	function simuladorActDes()
	{			
		if(!simulador)
		{	
			$("#activasimulador").attr('value', 'Simular');
			setEstatusCotizacion("simular");
			simulador=true;
			datos_fpago_g=datos_fpago;
			setDetallePlaning(datos_fpago_g.cotizacionBillPlanList);
			/*simulador=false;
			setDetallePlaning(datos_fpago.cotizacionBillPlanList);*/	
			enableDisableComps(simulador);
		}			
		else if($("#activasimulador").attr("value")=="Simular")
		{
			setAdminCotizacion('simular');
		}
		else
		{
			//alert("nada");
		}		
	}
	
	
	function setDetallePlaning(datos_)
	{
		datos_pagos_edit=datos_;
		$("#dg_detallepagos tbody").html("");
		
	    for(var i=0; i<datos_.length-1; i++)
		//for(var i=0; i<datos_.length; i++)
	    {
	    	var row_item="";
	    	if(datos_[i].conce=="02" || datos_[i].conce=="01")
	    	{
	    		var con=datos_[i].conse;
	    		if(simulador)
	    		{
	    			if(datos_[i].conce=="01")
	    			{
	    				row_item+="<tr>";
				    		row_item+="<td width='20px'></td>";
				    	    row_item+="<td width='30px'>"+datos_[i].conse+"</td>";
				    	    row_item+="<td width='90px'>"+datos_[i].concex+"</td>";
				    		row_item+="<td width='70px'>"+convertDate(datos_[i].flim)+"</td>";
				    		if(parseFloat(datos_[i].total).toFixed(2)<=0)
			    			{
				    			row_item+="<td width='80px'><input type='text' value='0.00' title='0.00' class='k-textbox' id='txt"+con+"' name='txt"+con+"' style='width:80px; height:10px; font-size: 8px;' onKeyUp='detalleRowEnganche(this, event)' onBlur='detalleRowEnganche(this, event)'/></td>";
			    			}
				    		else
			    			{
				    			row_item+="<td width='80px'><input type='text' value='"+parseFloat(datos_[i].total).toFixed(2)+"' title='"+parseFloat(datos_[i].total).toFixed(2)+"' class='k-textbox' id='txt"+con+"' name='txt"+con+"' style='width:80px; height:10px; font-size: 8px;' onKeyUp='detalleRowEnganche(this, event)' onBlur='detalleRowEnganche(this, event)'/></td>";
			    			}				    		
				    		row_item+="<td width='60px'>"+datos_[i].sim1+"</td>";
				    		//row_item+="<td width='60px'>"+datos_[i].sim2+"</td>";
			    		row_item+="</tr>";
	    			}
	    			else
	    			{
				    	row_item+="<tr>";
				    		row_item+="<td width='20px'><input type='checkbox' id='chk"+con+"' name='chk"+con+"' value='"+con+"' onclick='elimina_diferido(this)'/></td>";
				    	    row_item+="<td width='30px'>"+datos_[i].conse+"</td>";
				    	    row_item+="<td width='90px'>"+datos_[i].concex+"</td>";
				    		row_item+="<td width='70px'>"+convertDate(datos_[i].flim)+"</td>";
				    		row_item+="<td width='80px'><input type='text' value='"+parseFloat(datos_[i].total).toFixed(2)+"' title='"+parseFloat(datos_[i].total).toFixed(2)+"' class='k-textbox' id='txt"+con+"' name='txt"+con+"' style='width:80px; height:10px; font-size: 8px;' onKeyUp='detalleRowEquipo(1,chk"+con+", this, event)' onBlur='detalleRowEquipo(1,chk"+con+", this, event)' /></td>";
				    		row_item+="<td width='60px'>"+datos_[i].sim1+"</td>";
				    		//row_item+="<td width='60px'>"+datos_[i].sim2+"</td>";
				    	row_item+="</tr>";
	    			}
	    		}
	    		else
	    		{
	    			row_item+="<tr>";
			    		row_item+="<td width='20px'></td>";
			    	    row_item+="<td width='30px'>"+datos_[i].conse+"</td>";
			    	    row_item+="<td width='90px'>"+datos_[i].concex+"</td>";
			    		row_item+="<td width='70px'>"+convertDate(datos_[i].flim)+"</td>";
			    		row_item+="<td width='80px' title='"+parseFloat(datos_[i].total).toFixed(2)+"' id='txt"+i+"'>"+formatNumber(parseFloat(datos_[i].total).toFixed(2),2)+"</td>";
			    		row_item+="<td width='60px'>"+datos_[i].sim1+"</td>";
			    		//row_item+="<td width='60px'>"+datos_[i].sim2+"</td>";
			    	row_item+="</tr>";
	    		}
	    	}
	    	else
	    	{	    		
	    		row_item+="<tr>";
		    		row_item+="<td width='20px'></td>";
		    	    row_item+="<td width='30px'>"+datos_[i].conse+"</td>";
		    	    row_item+="<td width='90px'>"+datos_[i].concex+"</td>";
		    		row_item+="<td width='70px'>"+convertDate(datos_[i].flim)+"</td>";
		    		row_item+="<td width='80px' title='"+parseFloat(datos_[i].total).toFixed(2)+"' id='txt"+i+"'>"+formatNumber(parseFloat(datos_[i].total).toFixed(2),2)+"</td>";
		    		row_item+="<td width='60px'>"+datos_[i].sim1+"</td>";
		    		//row_item+="<td width='60px'>"+datos_[i].sim2+"</td>";
		    	row_item+="</tr>";
	    	}
	        $("#dg_detallepagos tbody").append(row_item);
    	}
	                               
    }
	
	function enableDisableComps(enable)
	{
		if(enable)
		{
			$("#descp").removeAttr("disabled");
			//$("#nest").removeAttr("disabled");
			//$("#nbod").removeAttr("disabled");
			$("#btn_est").removeAttr("disabled");
			$("#btn_bod").removeAttr("disabled");
			//if(porc_diferido_val>=max_porc_dif)
			//{
				$("#pdiferido").removeAttr("disabled");
			//}
			$("#penganche").removeAttr("disabled");
		}
		else
		{
			$("#descp").attr("disabled", "disabled");
			//$("#nbod").attr("disabled", "disabled");
			//$("#nest").attr("disabled", "disabled");
			$("#btn_est").attr("disabled", "disabled");
			$("#btn_bod").attr("disabled", "disabled");
			if(porc_diferido_val>=max_porc_dif)
			{
				$("#pdiferido").attr("disabled", "disabled");
			}
			$("#penganche").attr("disabled", "disabled");
		}
	}
	
	function validaPorcDesc(event)
	{
		window.parent.$("#mensajes_main").html("");
		
		var des_= $("#descp").val();
		var eng_ = $("#penganche").val();
		var dif_ = $("#pdiferido").val();
		
		
		if(event.keyCode == 8 || event.keyCode==32)
		{
			
		}
		else
		{
			if(des_=="" || des_==undefined || des_==null)
			{
				$("#descp").val(max_porc_desc);
			}
			else if(eng_=="" || eng_==undefined || eng_==null)
			{
				$("#penganche").val(max_porc_eng);
			}
			else if(dif_=="" || dif_==undefined || dif_==null)
			{
				$("#pdiferido").val(max_porc_dif);
			}
		}
		
		setEstatusCotizacion("simular");
		var id_obj=event.currentTarget.id;
		
		if(event.keyCode == 13 || event.type=="blur")
		{ 			
			ejecutadesc(id_obj);
		}
		
		else
		{
			
		}	
	}
	
	function ejecutadesc(id_obj)
	{
		if(id_obj=="penganche")
		{
			if(max_porc_eng > parseInt($("#penganche").val()))
			{
				window.parent.$("#mensajes_main").html("Porcentaje es menor al permitido en enganche");
				$("#penganche").val(max_porc_eng);
				ejecutaPorcDesc("");
			}
			else
			{
				var pe=parseInt($("#penganche").val());
				var pa=parseInt($("#pdiferido").val());
				var sumaporc=pe+pa;
				if(sumaporc>100)
				{
					var restaporc=sumaporc-100;
					var resporced=restaporc-pa;
					
					if(0<resporced)
					{
						obj_from="pdiferido";
						$("#penganche").val(100);
						$("#diferido").autoNumericSet(0);
						$("#diferido").autoNumericSet(0);
						$("#pdiferido").val(0);
						ejecutaPorcDesc("");
					}
					else
					{
						obj_from="pdiferido";
						$("#enganche").autoNumericSet(0);
						$("#diferido").autoNumericSet(0);
						$("#pdiferido").val(resporced*-1);
						$("#penganche").val(100 - resporced*-1);
						ejecutaPorcDesc("");
					}
					
				}
				else
				{						
					ejecutaPorcDesc(id_obj);
				}					
			}
		}
		else
		{
			if(porc_diferido_val<max_porc_dif)
			{
				
			}
			else
			{
				if(max_porc_dif > parseInt($("#pdiferido").val()))
				{
					$("#pdiferido").val(max_porc_dif);								
				}
			}		
			
			obj_from=id_obj;
			ejecutaPorcDesc(id_obj);
		}	
	}
	
	function ejecutaPorcDesc(id_obj)
	{
		var desc_txt=0;
		
		var patron = /^\d{0,2}(\.[0-9]{0,2})?$/;
		if (!patron.test($('#descp').val())){
		//if($('#descp').val()=="")
			$('#descp').val('0');
			$('#descm').autoNumericSet(0);
			$('#descmdes').autoNumericSet($('#equipo').autoNumericGet());
			$('#total').autoNumericSet(parseFloat($('#subequipos').autoNumericGet())+parseFloat($('#descmdes').autoNumericGet()));
			procesaEnganche($('#total').autoNumericGet(),$("#penganche").val(),$("#anticipo").autoNumericGet(),id_obj);
		}
		else
		{
			//desc_txt=parseInt($('#descp').val());
			desc_txt=parseFloat($('#descp').val());
			//alert('Descuento porcentaje descuento :::::::' + desc_txt + ' Max_por_desc:::::' + max_porc_desc );
			if(desc_txt>max_porc_desc)
			{
				window.parent.$("#mensajes_main").html("Porcentaje es mayor al permitido");
				$('#descp').val('0');
				$('#descm').autoNumericSet(0);
				$('#descmdes').autoNumericSet($('#equipo').autoNumericGet());
				$('#total').autoNumericSet(parseFloat($('#subequipos').autoNumericGet())+parseFloat($('#descmdes').autoNumericGet()));
				procesaEnganche($('#total').autoNumericGet(),$("#penganche").val(),$("#anticipo").autoNumericGet(),id_obj);
			}
			else
			{
				//var desp=""+parseFloat($('#descp').val());
				var desp=$('#descp').val();
				//alert("desp ... " + desp + " longitud ... " + desp.length + " desp[0] ... " + desp[0]);
				/*if(desp.length <= 1)
				{
					desp=".0"+desp;
				}
				else if(desp.length == 2)
				{
					desp="."+desp;
				}
				else if(desp.length == 3)
				{
					desp=desp[0];
				}
				*/	
				
				var res_desc=procesaDescuento(desp,$('#equipo').autoNumericGet());
				$('#descm').autoNumericSet(res_desc);
				$('#descmdes').autoNumericSet(eqmenosdesc());
				$('#total').autoNumericSet(parseFloat($('#subequipos').autoNumericGet())+parseFloat($('#descmdes').autoNumericGet()));	
				procesaEnganche($('#total').autoNumericGet(),$("#penganche").val(),$("#anticipo").autoNumericGet(),id_obj);					
			}
		}
	}
	
	function eqmenosdesc()
	{
		var totaleq=$('#equipo').autoNumericGet();
		var desc=$('#descm').autoNumericGet();
		var res=parseFloat(totaleq)-parseFloat(desc);	
		return res.toFixed(2);
	}
	
	function procesaDescuento(pdesc,monto)
	{
		var res=0;
		var pd=parseFloat(pdesc);
		var mt=parseFloat(monto);
		res=mt*pd/100;	
		return res.toFixed(2);
	}
	
	function procesaEnganche(total,penganche,apartado,from)
	{
		//alert('entrando procesaEnganche() ... total ... ' + total + ' penganche ... ' + penganche + ' apartado ... ' + apartado + ' from ... ' + from);
		var peng=""+parseFloat(penganche);
		if(peng.lastIndexOf(",") != -1){
		peng.replace(/,/g, '');
		$("#penganche").val(peng);
		}
		/*
		if(peng.length <= 1)
		{
			peng=".0"+peng;
		}
		else if(peng.length == 2)
		{
			peng="."+peng;
		}
		else if(peng.length == 3)
		{
			peng=peng[0];
		}*/
		
		var to = parseFloat(total).toFixed(2);
		var pe = parseFloat(peng);
		var ap = parseFloat(apartado).toFixed(2);

		var patron = /\./;
		if (patron.test(pe)){
			pe = float2int(pe);
			$("#penganche").val(pe);
			window.parent.$("#mensajes_main").html("Entrada no válida, ajustando porcentaje enganche a enteros");
		}
		
		var res = (to * pe / 100) - ap;
			
			if(res<=to){
				$("#enganche").autoNumericSet(res.toFixed(2));
				$("#txt2").val(res.toFixed(2));
				$("#penganche").val(pe);
			}else{
				$("#enganche").autoNumericSet(to.toFixed(2));
				$("#txt2").val(to.toFixed(2));
				$("#penganche").val(100);
				//alert('entrando procesaEnganche() ... res<=to ... #enganche ... ' + $("#enganche").val() + ' ... #txt2 ... ' + $("#txt2").val());
			}
			
		procesaDiferido($('#total').autoNumericGet(),$("#pdiferido").val());	
		
		var porciento_total=validaPorcentageNegativo();
		
		//alert('entrando procesaEnganche() ... porciento_total >= 0 ... ' + porciento_total);
		if(porciento_total>=0)
		{
			$("#ppago_final").val(porciento_total);
			procesaPagoFinal($('#total').autoNumericGet(),$("#ppago_final").val());			
		}
		else
		{
			if(from=="penganche")
			{
				
			}
			else
			{
				//alert('entrando procesaEnganche() ... from != penganche ... ')
				var restapeng = parseFloat(penganche) + (porciento_total*-1);
				$("#pago_final").autoNumericSet(0);
				$("#ppago_final").val(0);
				//alert('entrando procesaEnganche() ... restapeng (porciento_total*-1) ... ' + porciento_total);
				//alert('entrando procesaEnganche() ... $(#pago_final) ... ' + $("#pago_final").val())
				
				if(restapeng>=101)
				{					
					window.parent.$("#mensajes_main").html("Se rebasó el porcentaje del 100, ajustando cantidades");
					$("#diferido").autoNumericSet(0);
					$("#pdiferido").val(0);
					//$("#penganche").autoNumericSet(100);
					$("#penganche").val(100);
					//alert('entrando procesaEnganche() ... [restapeng>=101] ... $(#pago_final) ... ' + $("#pago_final").val() + ' ... $(#diferido) ... ' + $("#pdiferido").val(0) + ' ... $(#penganche) ... ' + $("#penganche").val())
				}
				else
				{
					//$("#diferido").val(0);
					var res_p1p2=100 - restapeng;
					$("#pdiferido").val(res_p1p2);
					var pdif=""+parseFloat(res_p1p2);
					/*if(pdif.length <= 1)
					{
						pdif=".0"+pdif;
					}
					else if(peng.length == 2)
					{
						pdif="."+pdif;
					}
					else if(peng.length == 3)
					{
						pdif=pdif[0];
					}*/
										
					var resultadocant=parseFloat($("#total").autoNumericGet())*pdif;	
					
					$("#diferido").autoNumericSet(resultadocant);
					//alert('entrando procesaEnganche() ... [restapeng<101] ... $(#pdiferido) ... ' + $("#pdiferido").val() + ' ... ' + resultadocant + ' ... $("#total") ... ' + $("#total").val() + ' * pdfif ... ' + pdif);
					
					//procesaEnganche($('#total').val(),$("#penganche").val(),$("#anticipo").val(),"")
					//window.parent.$("#mensajes_main").html("Se revaso el porcentaje del 100, ajustando cantidades");
					
					//Recalcular el porcentaje de pago total al primer enter
					if(porciento_total<=0)
					{
						porciento_total = porciento_total * -1;
						$("#ppago_final").val(porciento_total);
						procesaPagoFinal($('#total').autoNumericGet(),$("#ppago_final").val());			
					}
				
				}		
			}		
		}
		recalculaDiferido(null);
		var dif_list=datos_fpago_g.cotizacionBillPlanList;
		setRePlanificaTotales(dif_list);
	}
	
	function procesaDiferido(total,pdiferido)
	{
		var pdif=""+parseFloat(pdiferido);
		$("#pdiferido").val(pdif);
		
		/*if(pdif.length <= 1)
		{
			pdif=".0"+pdif;
		}
		else if(pdif.length == 2)
		{
			pdif="."+pdif;
		}
		else if(pdif.length == 3)
		{
			pdif=pdif[0];
		}*/
		
		var to=parseFloat(total).toFixed(2);
		var pd=parseFloat(pdif);
		
		var patron = /\./;
		if (patron.test(pd)){
			pd = float2int(pd);
			$("#pdiferido").val(pd);
			window.parent.$("#mensajes_main").html("Entrada no válida, ajustando porcentaje diferido a enteros");
		}
		
		var res = to * pd / 100;
		
		$("#diferido").autoNumericSet(res.toFixed(2));
	}
	
	function procesaPagoFinal(total,pfinal)
	{
		var pfin=""+parseFloat(pfinal);
		/*if(pfin.length <= 1)
		{
			pfin=".0"+pfin;
		}
		else if(pfin.length == 2)
		{
			pfin="."+pfin;
		}
		else if(pfin)
		{
			pdif=pdif[0];
		}*/
		
		var to=parseFloat(total).toFixed(2);
		var pf=parseFloat(pfin);
		var res=to*pf/100;
		$("#pago_final").autoNumericSet(res.toFixed(2));
		//alert('entra a procesaPagoFinal() ... total ... ' + total + ' ... pfinal ... ' + pfinal + ' ... res ... to*pf' + res + ' ... #pago_final ... ' + $("#pago_final").val());
	}
		
	function validaPorcentageNegativo()
	{
		var res=0;
		var pdif=$("#pdiferido").val();
		var peng=$("#penganche").val();
		res=100-(parseInt(pdif)+parseInt(peng));
		return res;
	}
	
	function detalleRowEquipo(num_item, check_obj, obj_txt, event)
	{	
		setEstatusCotizacion("simular");
		
		if (obj_txt.value != '')
		 {
			 obj_txt.value=obj_txt.value.replace(/[^0-9\.]/g, ""); 
		     if(obj_txt.value.split('.').length>2)  
		     {
                obj_txt.value = obj_txt.value.replace(/\.+$/, ""); 
            }		 
		 }
		else
		{
			//obj_txt.value=0;
		}
		
		if(event.keyCode != undefined || event.type=="blur")
		{
	 	    if(check_obj!=null)	
	 	    {	 
	 	    	if(event.keyCode != undefined)
	 	    	{
			    	check_obj.checked=true;	
			    	
			    	if(diferidos_editables.length==0)
			    	{
			    		diferidos_editables.push({checado:check_obj.value});
			    	}
			    	else
			    	{
			    		var existe_checado=false;
			    		
			    		for(var i = 0; i<diferidos_editables.length; i++)
				    	{
				    		if(diferidos_editables[i].checado==check_obj.value)
				    		{
				    			existe_checado=true;
				    			break;
				    		}
				    	}
			    		
			    		if(!existe_checado)
		    			{
			    			diferidos_editables.push({checado:check_obj.value});
		    			}			    		
			    	}
	 	    	}
		    	
		    	if(event.keyCode == 13 || event.type=="blur")
		    	{
		    		recalculaDiferido(obj_txt);
		    	}		    	
		    }
	 	    else
	 	    {
	 	    	reprocesaEnganche(obj_txt);	 	    	
	 	    	//validaPorcDesc(event);	
	 	    }	   		 
		 }		
     }
	
	function elimina_diferido(check_obj)
	{
		if(!check_obj.checked)
		{
			for(var i = 0; i<diferidos_editables.length; i++)
	    	{
	    		if(diferidos_editables[i].checado==check_obj.value)
	    		{
	    			diferidos_editables.splice(i, 1);
	    		}
	    	}
		}
		else
		{
			diferidos_editables.push({checado:check_obj.value});
		}
	}
	
	function validaPorcentajeEngDif()
	{
		
	}
	
	function reprocesaEnganche(obj_txt)
	{
		var obj_txt_val=obj_txt.value;
		
		if(obj_txt_val==null || obj_txt_val== undefined || obj_txt_val=="")
		{
			obj_txt_val=0;
		}
		
		$("#enganche").autoNumericSet(parseFloat(obj_txt_val).toFixed(2));	
		var eng=parseFloat(obj_txt_val);
		var tot=parseFloat($('#total').autoNumericGet());
		var ant=parseFloat($("#anticipo").autoNumericGet());
		var percent_eng =Math.round(((eng+ant) * 100 ) / tot);
		if(percent_eng<max_porc_eng)
		{
			$("#penganche").val(max_porc_eng);
		}
		else
		{
			$("#penganche").val(percent_eng);
		}
		//alert('reprocesaEnganche() ... $(#penganche) = ' + $("#penganche").val())
	}
	
	function recalculaDiferido(obj_txt)
	{
		if(obj_from=="pdiferido")
		{
			diferidos_editables=[];			
		}
		
		obj_from="";
					
		var tot_diferido=parseFloat($("#diferido").autoNumericGet());
		var dif_list=datos_fpago_g.cotizacionBillPlanList;
		var disponibles=[];	
		var total_disp_difer=0;
		
		for(var i=0; i<dif_list.length; i++)
		{
			var encontrado=false;
			
			for(var j=0; j<diferidos_editables.length; j++)
			{
				if(parseInt(diferidos_editables[j].checado)==dif_list[i].conse && dif_list[i].posnr_z=="000040")
				{
					encontrado=true;
					break;
				}				
			}
			
			if(!encontrado && dif_list[i].posnr_z=="000040")
			{
				if(tot_diferido<=0)
				{
					$("#txt"+dif_list[i].conse+"").val(0);
				}
				else
				{
					disponibles.push(dif_list[i]);
				}
			}
			else
			{
				if(dif_list[i].posnr_z=="000040")
				{
					var diferidos_suma=parseFloat($("#txt"+dif_list[i].conse+"").val());
					total_disp_difer+=diferidos_suma;
					
					var resto_diferido=tot_diferido-total_disp_difer;
					
					if(resto_diferido<=0)
					{
						if(obj_txt!=null)
						{
							obj_txt.value=parseFloat(tot_diferido-(total_disp_difer-diferidos_suma)).toFixed(2);
						}
					}					
					//regresar datos a arreglo clon
				}
			}
		}
		
		var resto_diferido=tot_diferido-total_disp_difer;
		
		var dife_item=(tot_diferido-total_disp_difer)/disponibles.length;
		
		for(var k=0; k<disponibles.length; k++)
		{
			if(resto_diferido>=0)
			{
				$("#txt"+disponibles[k].conse+"").val(parseFloat(dife_item).toFixed(2));
			}
			else
			{				
				$("#txt"+disponibles[k].conse+"").val(0);
			}
			//regresar datos a arreglo clon
		}
	}
	
	function detalleRowEnganche(obj_txt,event)	
	{
		setEstatusCotizacion("simular");
		if (obj_txt.value != '')
		{
			 obj_txt.value=obj_txt.value.replace(/[^0-9\.]/g, ""); 
		     if(obj_txt.value.split('.').length>2)  
		     {
                obj_txt.value = obj_txt.value.replace(/\.+$/, ""); 
             }		 
		}
		else
		{
			obj_txt.value=0;
		}
		
		if(event.keyCode == 13 || event.type=="blur" || event.keyCode == 9)
		{			
			//alert('Entra a detalleRowEngache');
		
			$("#activasimulador").focus();
			reprocesaEnganche(obj_txt);
			var res=parseFloat(obj_txt.value);		
			
			procesaEnganche($('#total').autoNumericGet(),$("#penganche").val(),$("#anticipo").autoNumericGet(),"");
			
			if(parseInt($("#penganche").val())!=max_porc_eng)
			{
				if(parseInt($("#penganche").val())<=max_porc_eng){
					$("#txt2").val(res);
					$("#enganche").autoNumericSet(res);
				}
			}
			
			var res_pago_fin = parseFloat($("#total").autoNumericGet()-(parseFloat($("#anticipo").autoNumericGet())+ parseFloat($("#enganche").autoNumericGet()) + parseFloat($("#diferido").autoNumericGet())));
			$("#pago_final").autoNumericSet(parseFloat(res_pago_fin).toFixed(2));
			var dif_list=datos_fpago_g.cotizacionBillPlanList;
			setRePlanificaTotales(dif_list);
			
			//actualizaDatosForPago(res.toFixed(2));
			//reprocesaEnganche(obj_txt);
			//procesaDiferido($('#total').val(),$("#pdiferido").val());
		}
	}
	
	function setRePlanificaTotales(datos_)
	{			
		datos_pagos_edit=datos_;
		
		var row_item="";
		
	    for(var i=0; i<datos_pagos_edit.length; i++)
	    {	    	
	    	if(datos_pagos_edit[i].conce=="02" || datos_pagos_edit[i].conce=="01")
	    	{
	    		var con=datos_pagos_edit[i].conse;
	    		if(simulador)
	    		{
	    			if(datos_pagos_edit[i].conce=="01")
	    			{
	    				row_item+="<tr>";
				    		row_item+="<td width='20px'></td>";
				    	    row_item+="<td width='30px'>"+datos_pagos_edit[i].conse+"</td>";
				    	    row_item+="<td width='90px'>"+datos_pagos_edit[i].concex+"</td>";
				    		row_item+="<td width='70px'>"+convertDate(datos_pagos_edit[i].flim)+"</td>";
				    		if(parseFloat(datos_pagos_edit[i].total).toFixed(2)<=0)
			    			{
				    			row_item+="<td width='80px'><input type='text' value='0.00' class='k-textbox' id='txt"+con+"' name='txt"+con+"' style='width:80px; height:10px; font-size: 8px;' onKeyUp='detalleRowEnganche(this, event)' onBlur='detalleRowEnganche(this, event)' tabindex="+tabi_pos+"/></td>";
			    			}
				    		else
			    			{
				    			row_item+="<td width='80px'><input type='text' value='"+parseFloat($("#txt"+con+"").val()).toFixed(2)+"' class='k-textbox' id='txt"+con+"' name='txt"+con+"' style='width:80px; height:10px; font-size: 8px;' onKeyUp='detalleRowEnganche(this, event)' onBlur='detalleRowEnganche(this, event)'/></td>";
			    			}	
				    		
				    		row_item+="<td width='60px'>"+datos_pagos_edit[i].sim1+"</td>";
				    		//row_item+="<td width='60px'>"+datos_pagos_edit[i].sim2+"</td>";
			    		row_item+="</tr>";
	    			}
	    			else
	    			{
				    	row_item+="<tr>";
				    	    var pchecado=false; 
				    	    for(var p=0; p<diferidos_editables.length; p++)
				    	    {
				    	    	if(parseInt(diferidos_editables[p].checado)==datos_pagos_edit[i].conse)
		    	    			{
				    	    		pchecado=true;
				    	    		break;
		    	    			}
				    	    }
				    	    if(pchecado)
				    	    {
				    	    	row_item+="<td width='20px'><input type='checkbox' id='chk"+con+"' name='chk"+con+"' value='"+con+"' onclick='elimina_diferido(this)' checked/></td>";
				    	    }
				    	    else
				    	    {
				    	    	row_item+="<td width='20px'><input type='checkbox' id='chk"+con+"' name='chk"+con+"' value='"+con+"' onclick='elimina_diferido(this)'/></td>";
				    	    }
				    		
				    	    row_item+="<td width='30px'>"+datos_pagos_edit[i].conse+"</td>";
				    	    row_item+="<td width='90px'>"+datos_pagos_edit[i].concex+"</td>";
				    		row_item+="<td width='70px'>"+convertDate(datos_pagos_edit[i].flim)+"</td>";
				    		row_item+="<td width='80px'><input type='text' value='"+parseFloat($("#txt"+con+"").val()).toFixed(2)+"' class='k-textbox' id='txt"+con+"' name='txt"+con+"' style='width:80px; height:10px; font-size: 8px;' onKeyUp='detalleRowEquipo(1,chk"+con+", this, event)' onBlur='detalleRowEquipo(1,chk"+con+", this, event)'/></td>";
				    		row_item+="<td width='60px'>"+datos_pagos_edit[i].sim1+"</td>";
				    		//row_item+="<td width='60px'>"+datos_pagos_edit[i].sim2+"</td>";
				    	row_item+="</tr>";
	    			}
	    		}
	    		else
	    		{
	    			row_item+="<tr>";
			    		row_item+="<td width='20px'></td>";
			    	    row_item+="<td width='30px'>"+datos_pagos_edit[i].conse+"</td>";
			    	    row_item+="<td width='90px'>"+datos_pagos_edit[i].concex+"</td>";
			    		row_item+="<td width='70px'>"+convertDate(datos_pagos_edit[i].flim)+"</td>";
			    		row_item+="<td width='80px'>"+parseFloat(datos_pagos_edit[i].total).toFixed(2)+"</td>";
			    		row_item+="<td width='60px'>"+datos_pagos_edit[i].sim1+"</td>";
			    		//row_item+="<td width='60px'>"+datos_pagos_edit[i].sim2+"</td>";
			    	row_item+="</tr>";
	    		}
	    	}
	    	else
	    	{	
	    		if(datos_pagos_edit[i].concex=="PRECIO TOTAL" || datos_pagos_edit[i].concex=="TOTAL")
	    		{
	    			row_item+="<tr>";
			    		row_item+="<td width='20px'></td>";
			    	    row_item+="<td width='30px'";             
				    	     if(datos_pagos_edit[i].concex == "TOTAL"){
				    	    	row_item+= " style='visibility:hidden'";	
				    	     }
				    	    row_item+=">"+datos_pagos_edit[i].conse+"</td>";
				    	    row_item+="<td width='90px'";
				    	      if(datos_pagos_edit[i].concex == "TOTAL"){
				    	    	row_item+= " style='visibility:hidden'";	
				    	     }
				    	    row_item+=">"+datos_pagos_edit[i].concex+"</td>";
				    		row_item+="<td width='70px'";
				    		 if(datos_pagos_edit[i].concex == "TOTAL"){
					    	    	row_item+= " style='visibility:hidden'";	
					    	 }
				    		 row_item+=">"+datos_pagos_edit[i].flim+"</td>";
			    		
				    	var suma_total=parseFloat($("#total").autoNumericGet())+parseFloat($("#gastos_admin").autoNumericGet());
			    		if(datos_pagos_edit[i].concex=="PRECIO TOTAL")
			    		{
			    			row_item+="<td width='80px'>"+formatNumber(parseFloat($("#total").autoNumericGet()).toFixed(2),2)+"</td>";
			    		}
			    		else
			    		{
			    			row_item+="<td width='80px' style='visibility:hidden'>"+formatNumber(parseFloat(suma_total).toFixed(2),2)+"</td>";
			    		}
			    		row_item+="<td width='60px'";
				    		if(datos_pagos_edit[i].concex == "TOTAL"){
				    	    	row_item+= " style='visibility:hidden'";	
				    	     }
				    	row_item+=">"+datos_pagos_edit[i].sim1+"</td>";
			    		//row_item+="<td width='60px'>"+datos_pagos_edit[i].sim2+"</td>";
		    		row_item+="</tr>";
	    		}
	    		else
	    		{
	    			if(datos_pagos_edit[i].conce=="03")
	    			{
	    				row_item+="<tr>";
				    		row_item+="<td width='20px'></td>";
				    	    row_item+="<td width='30px'>"+datos_pagos_edit[i].conse+"</td>";
				    	    row_item+="<td width='90px'>"+datos_pagos_edit[i].concex+"</td>";
				    		row_item+="<td width='70px'>"+convertDate(datos_pagos_edit[i].flim)+"</td>";
				    		row_item+="<td width='80px'>"+formatNumber(parseFloat($("#pago_final").autoNumericGet()).toFixed(2),2)+"</td>";
				    		row_item+="<td width='60px'>"+datos_pagos_edit[i].sim1+"</td>";
				    		//row_item+="<td width='60px'>"+datos_pagos_edit[i].sim2+"</td>";
			    		row_item+="</tr>";
	    			}
	    			else
	    			{
	    				row_item+="<tr>";
				    		row_item+="<td width='20px'></td>";
				    	    row_item+="<td width='30px'>"+datos_pagos_edit[i].conse+"</td>";
				    	    row_item+="<td width='90px'>"+datos_pagos_edit[i].concex+"</td>";
				    		row_item+="<td width='70px'>"+convertDate(datos_pagos_edit[i].flim)+"</td>";
				    		row_item+="<td width='80px'>"+formatNumber(parseFloat(datos_pagos_edit[i].total).toFixed(2),2)+"</td>";
				    		row_item+="<td width='60px'>"+datos_pagos_edit[i].sim1+"</td>";
				    		//row_item+="<td width='60px'>"+datos_pagos_edit[i].sim2+"</td>";
			    		row_item+="</tr>";
	    			}	    			
	    		}	    		
	    	}
    	}
	    $("#dg_detallepagos tbody").html("");
	    $("#dg_detallepagos tbody").html(row_item);
	    
	    
	    /*for(var i=0; i<datos_pagos_edit.length; i++)
	    {	    	
	    	if(datos_pagos_edit[i].conce=="02" || datos_pagos_edit[i].conce=="01")
	    	{
	    		var con=datos_pagos_edit[i].conse;
	    		if(simulador)
	    		{
	    			if(datos_pagos_edit[i].conce=="01")
	    			{	    				
			    		if(parseFloat(datos_pagos_edit[i].total).toFixed(2)<=0)
		    			{
			    			row_item+="<td width='80px'><input type='text' value='0.00' class='k-textbox' id='txt"+con+"' name='txt"+con+"' style='width:80px; height:10px; font-size: 8px;' onKeyUp='detalleRowEnganche(this, event)' onBlur='detalleRowEnganche(this, event)' tabindex="+tabi_pos+"/></td>";
		    			}
			    		else
		    			{
			    			row_item+="<td width='80px'><input type='text' value='"+parseFloat($("#txt"+con+"").val()).toFixed(2)+"' class='k-textbox' id='txt"+con+"' name='txt"+con+"' style='width:80px; height:10px; font-size: 8px;' onKeyUp='detalleRowEnganche(this, event)' onBlur='detalleRowEnganche(this, event)'/></td>";
		    			}					    		
	    			}
	    			else
	    			{
				    	
			    	    var pchecado=false; 
			    	    for(var p=0; p<diferidos_editables.length; p++)
			    	    {
			    	    	if(parseInt(diferidos_editables[p].checado)==datos_pagos_edit[i].conse)
	    	    			{
			    	    		pchecado=true;
			    	    		break;
	    	    			}
			    	    }
			    	    if(pchecado)
			    	    {
			    	    	row_item+="<td width='20px'><input type='checkbox' id='chk"+con+"' name='chk"+con+"' value='"+con+"' onclick='elimina_diferido(this)' checked/></td>";
			    	    }
			    	    else
			    	    {
			    	    	row_item+="<td width='20px'><input type='checkbox' id='chk"+con+"' name='chk"+con+"' value='"+con+"' onclick='elimina_diferido(this)'/></td>";
			    	    }				    		
			    	    
			    		row_item+="<td width='80px'><input type='text' value='"+parseFloat($("#txt"+con+"").val()).toFixed(2)+"' class='k-textbox' id='txt"+con+"' name='txt"+con+"' style='width:80px; height:10px; font-size: 8px;' onKeyUp='detalleRowEquipo(1,chk"+con+", this, event)' onBlur='detalleRowEquipo(1,chk"+con+", this, event)'/></td>";
				    		
	    			}
	    		}
	    		else
	    		{
			    	row_item+="<td width='80px'>"+parseFloat(datos_pagos_edit[i].total).toFixed(2)+"</td>";			    		
	    		}
	    	}
	    	else
	    	{	
	    		if(datos_pagos_edit[i].concex=="SUB-TOTAL" || datos_pagos_edit[i].concex=="TOTAL")
	    		{	    			
		    		var suma_total=parseFloat($("#total").autoNumericGet())+parseFloat($("#gastos_admin").autoNumericGet());
		    		if(datos_pagos_edit[i].concex=="SUB-TOTAL")
		    		{
		    			row_item+="<td width='80px'>"+formatNumber(parseFloat($("#total").autoNumericGet()).toFixed(2),2)+"</td>";
		    		}
		    		else
		    		{
		    			row_item+="<td width='80px'>"+formatNumber(parseFloat(suma_total).toFixed(2),2)+"</td>";
		    		}
			    		
	    		}
	    		else
	    		{
	    			if(datos_pagos_edit[i].conce=="03")
	    			{
	    				
				    		row_item+="<td width='80px'>"+formatNumber(parseFloat($("#pago_final").autoNumericGet()).toFixed(2),2)+"</td>";
				    		
	    			}
	    			else
	    			{
	    				
				    		row_item+="<td width='80px'>"+formatNumber(parseFloat(datos_pagos_edit[i].total).toFixed(2),2)+"</td>";
				    		
	    			}	    			
	    		}	    		
	    	}
    	}*/
		
		
		/*var cont_cols_bp=0;
	    $('#dg_detallepagos').find('tbody td').each(function() {
	    	var valor_txt=parseInt($(this).find('input:text').val());
	    	var lbl_item=$(this).find('input:text').val()
	    	
	    	
	    	
	    	if(cont_cols_bp==4)
	    	{
	    		if(isNaN(valor_txt))
		    	{
		    		//alert($(this).text()); 
					// alert($(this).html());
		    		
		    		/////////////////////////////////////////////////////////////////////////////
		    		for(var i=0; i<datos_pagos_edit.length; i++)
		    	    {	    		    	    	
		    	    	//var con=datos_pagos_edit[i].conse;
	    	    		if(simulador)
	    	    		{
	    	    			if($(this).text()==datos_pagos_edit[i].concex)
	    	    			{
	    	    				$(this).text(formatNumber(parseFloat(datos_pagos_edit[i].total).toFixed(2),2));
	    	    				break;
	    	    			}	    	    			
	    	    		}     	    	
		        	}
		    		
		    		/////////////////////////////////////////////////////////////////////////////
		    		
		    	}
		    	else
		    	{
		    		alert($(this).find('input:text').val());
		    	}
	    	}
	    	
	    	cont_cols_bp++;
	    });*/	    
    }
	
	function obtieneSubEquipos(tipo)
	{
		sub_equipos_data=[];
		//sub_equipos_data_ini=[];
		var dgsubequipos = $("#dg_subequipos").data('kendoGrid');
  	    dgsubequipos.dataSource.data([]);
		var fivig=convertDate($("#fechaiv").val());
		var fivsp=fivig.split("-");
		var datab = fivsp[0]+fivsp[1]+fivsp[2];
		$.ajax({  
		    type: "POST",  
		    url: contexPath + "/Simulador.htm",  
		    data: "accion=subequipos&i_type="+tipo+"&i_datab="+datab,  
		    success: function(response){
		      // we have the response 
		      if(response.resCotizacionSubEquipos.mensaje == "SUCCESS"){		    	  
		    	  sub_equipos_data=response.resCotizacionSubEquipos.subEquiposCotizacion;
		    	  //sub_equipos_data_ini=response.resCotizacionSubEquipos.subEquiposCotizacion;
		    	  setEquiposData();
		    	  //dgsubequipos.dataSource.data(sub_equipos_data);		    	        
		      }
		      else if(response.resCotizacionSubEquipos.mensaje == "LOGOUT")
		      {
		    	  window.parent.salirSistema();
		      }
		      else
		      {
		    	  window.parent.$("#mensajes_main").html(response.resCotizacionSubEquipos.descripcion);
		      }	      
		    },  
		    error: function(xhr, textStatus, errorThrown) { 
		           // Don't raise this alert if user has navigated away from the page 
		    	window.parent.$("#mensajes_main").html(textStatus);
		    },
		    /* error: function(e){  
		    	winAlert("Alert",e,100,200);  
		    } */
		  }); 
	}
	
	function setEquiposData()
	{
		if(sub_equipos_data.length>=1)
		{
			$("#dg_subequipos tbody").html("");
			for(var i=0; i<sub_equipos_data.length; i++)
			{			
				row_item="";
				row_item+="<tr>";
					row_item+="<td width='10px' align='left'><input type='checkbox' id='chk_equ"+i+"' name='chk_equ"+i+"' value='"+sub_equipos_data[i].id_equnr+"' onclick='seleccionaSubequipo("+i+",this)'/></td>";
		    	    row_item+="<td width='20px'>"+sub_equipos_data[i].tipo+"</td>";
		    	    row_item+="<td width='100px'>"+sub_equipos_data[i].id_equnrx+"</td>";
		    		row_item+="<td width='70px'>"+parseFloat(sub_equipos_data[i].netwr).toFixed(2)+"</td>";
		    		row_item+="<td width='100px'>"+sub_equipos_data[i].id_equnr+"</td>";
		    	row_item+="</tr>";

		    	$("#dg_subequipos tbody").append(row_item);
			}
		}
	}
	
	function setInitEquiposData()
	{
		if(sub_equipos_data_ini.length>=1)
		{
			$("#dg_equipos tbody").html("");
			for(var i=0; i<sub_equipos_data_ini.length; i++)
			{			
				row_item="";
				row_item+="<tr>";
				    if(parseFloat(sub_equipos_data_ini[i].netwr)>=1 && (sub_equipos_data_ini[i].tipo=="E" || sub_equipos_data_ini[i].tipo=="B"))
				    {
				    	row_item+="<td width='10px' align='left'><input type='checkbox' id='chk_sequ"+i+"' name='chk_sequ"+i+"' value='"+sub_equipos_data_ini[i].id_equnr+"' onclick='seleccionaSubequipoIni("+i+",this)'/></td>";
				    }
				    else
				    {
				    	row_item+="<td width='10px' align='left'></td>";
				    }
		    	    row_item+="<td width='20px'>"+sub_equipos_data_ini[i].tipo+"</td>";
		    	    row_item+="<td width='110px'>"+sub_equipos_data_ini[i].id_equnrx+"</td>";
		    		row_item+="<td width='70px'>"+parseFloat(sub_equipos_data_ini[i].netwr).toFixed(2)+"</td>";
		    		row_item+="<td width='110px'>"+sub_equipos_data_ini[i].id_equnr+"</td>";
		    	row_item+="</tr>";

		    	$("#dg_equipos tbody").append(row_item);
			}
		}
	}
	
	function seleccionaSubequipo(id, obj_chk)
	{
		if(obj_chk.checked)
		{
			var existe = false;
			
			if(sub_equipos_select.length==0)
			{
				sub_equipos_select.push({id_equnr: obj_chk.value});
			}
			else
			{
				for(var i=0; i<sub_equipos_select.length; i++)
				{
					if(sub_equipos_select[i].id_equnr==obj_chk.value)
					{
						existe=true;
					}
				}
				
				if(!existe)
				{
					sub_equipos_select.push({id_equnr: obj_chk.value});
				}
			}
			
			
		}
		else
		{
			if(sub_equipos_select.length>=1)
			{
				for(var i=0; i<sub_equipos_select.length; i++)
		        {			
					if(sub_equipos_select[i].id_equnr==obj_chk.value)
					{
						sub_equipos_select.splice(i, 1);
					}
		        }				
			}
		}
	}
	
	function seleccionaSubequipoIni(id, obj_chk)
	{
		if(obj_chk.checked)
		{
			var existe = false;
			
			if(sub_equipos_select_ini.length==0)
			{
				sub_equipos_select_ini.push({id_equnr: obj_chk.value});
			}
			else
			{
				for(var i=0; i<sub_equipos_select_ini.length; i++)
				{
					if(sub_equipos_select_ini[i].id_equnr==obj_chk.value)
					{
						existe=true;
					}
				}
				
				if(!existe)
				{
					sub_equipos_select_ini.push({id_equnr: obj_chk.value});
				}
			}			
		}
		else
		{
			if(sub_equipos_select_ini.length>=1)
			{
				for(var i=0; i<sub_equipos_select_ini.length; i++)
		        {			
					if(sub_equipos_select_ini[i].id_equnr==obj_chk.value)
					{
						sub_equipos_select_ini.splice(i, 1);
					}
		        }				
			}
		}
	}
	
	function enviaSubEquiposValida()
	{
		var existe=false;
		var id_eq="";
		for(var i=0; i<sub_equipos_data_ini.length; i++)
        {		
			for(var j=0; j<sub_equipos_select.length; j++)
			{
				if(sub_equipos_data_ini[i].id_equnr==sub_equipos_select[j].id_equnr)
				{
					existe=true;
					id_eq=sub_equipos_data_ini[i].id_equnr;
				}
			}
        }
		
		if(!existe)
		{		
			setEstatusCotizacion("simular");
			for(var i=0; i<sub_equipos_data.length; i++)
	        {		
				for(var j=0; j<sub_equipos_select.length; j++)
				{
					if(sub_equipos_data[i].id_equnr==sub_equipos_select[j].id_equnr)
					{
						var seditem={id_cotiz_z:$('#id_cotiz_z').val(), id_equnrx:sub_equipos_data[i].id_equnrx, netwr:sub_equipos_data[i].netwr, tipo:sub_equipos_data[i].tipo, id_equnr:sub_equipos_data[i].id_equnr, moneda:sub_equipos_data[i].moneda, posnr_z:"000000"};
						sub_equipos_data_ini.push(seditem);
						break;
					}
				}
	        }				
			setAdminCotizacion("addsubequipos");
		}
		else
		{
			window.parent.$("#mensajes_main").html("No se puede agregar el mismo equipo dos veces, verifique el equipo "+id_eq);
		}
	}
	
	function quitarSubEquiposValida()
	{
		if(sub_equipos_select_ini.length>=1)
		{
			var existe=false;
			for(var i=0; i<sub_equipos_data_ini.length; i++)
	        {	
				for(var j=0; j<sub_equipos_select_ini.length; j++)
				{
					if(sub_equipos_select_ini[j].id_equnr==sub_equipos_data_ini[i].id_equnr)
					{
						existe=true;
						setEstatusCotizacion("simular");
						sub_equipos_data_ini.splice(i, 1);
					}
				}
	        }
			if(existe)
			{
				setAdminCotizacion("addsubequipos");
			}
			else
			{
				window.parent.$("#mensajes_main").html("Equipos bloqueados no se pueden eliminar");
			}			
		}	
		else
		{
			window.parent.$("#mensajes_main").html("Equipos bloqueados no se pueden eliminar");
		}
	}
	
	function ActDes()
	{
		reasignaDatosActualizados();
		//var trs = $("#dg_detallepagos").find("tbody>tr");
		/*$('#dg_detallepagos').find('tbody td').each(function() { 
			  alert( $(this).text()); 
			  alert( $(this).html()); 
		});*/
		
	}
	
	function reasignaDatosActualizados()
	{
		var i=0;
		var cols=1;
		$('#dg_detallepagos').find('tbody td').each(function() { 
			
			if(cols==5)
			{
				if($(this).text()=="")
				{
					var obj_txt_val=$(this).html();
					datos_pagos_edit[i].total=parseFloat($("#txt"+datos_pagos_edit[i].conse).val());
					//.conse
				}
				else
				{
					datos_pagos_edit[i].total=parseFloat(unformater_number($(this).text()));
				}
				cols++;
			}
			else
			{
				if(cols==6)
				{
					cols=1;
					i++;
				}
				else
				{
					cols++;
				}
			}
			//alert( $(this).text()); 			
		});
		datos_pagos_edit;
		
		for(var i=0; i<datos_fpago_g.cotizacionDetalleList.length; i++)
		{
			if(datos_fpago_g.cotizacionDetalleList[i].cve_web=="CUOTA ÚNICA")
			{
				datos_fpago_g.cotizacionDetalleList[i].netwr=parseFloat($('#gastos_admin').autoNumericGet());
			}
			else if(datos_fpago_g.cotizacionDetalleList[i].cve_web=="APARTADO")
			{
				datos_fpago_g.cotizacionDetalleList[i].netwr=parseFloat($('#anticipo').autoNumericGet());
			}
			else if(datos_fpago_g.cotizacionDetalleList[i].cve_web=="ENGANCHE")
			{
				datos_fpago_g.cotizacionDetalleList[i].netwr=parseFloat($('#enganche').autoNumericGet());
			}
			else if(datos_fpago_g.cotizacionDetalleList[i].cve_web=="DIFERIDO")
			{
				datos_fpago_g.cotizacionDetalleList[i].netwr=parseFloat($('#diferido').autoNumericGet());
			}
			else if(datos_fpago_g.cotizacionDetalleList[i].cve_web=="EQUIPO")
			{
				datos_fpago_g.cotizacionDetalleList[i].netwr=parseFloat($('#descmdes').autoNumericGet());
			}
			else if(datos_fpago_g.cotizacionDetalleList[i].cve_web=="PAGO_FINAL")
			{
				datos_fpago_g.cotizacionDetalleList[i].netwr=parseFloat($('#pago_final').autoNumericGet());
			}
			else if(datos_fpago_g.cotizacionDetalleList[i].cve_web=="TOTAL")
			{
				datos_fpago_g.cotizacionDetalleList[i].netwr=parseFloat($('#total').autoNumericGet());
			}			
		}
	}
	
	function setAdminCotizacion(accion)
	{	
		
			if($("#activasimulador").val()=="Simular" && accion=="guardar" && $("#estatus_simulador").attr("title")!="PASO")
			{
				window.parent.$("#mensajes_main").html("No se puede guardar la cotizacion sin haber validado sus datos. Presione en el boton \"Simualr\" para poder continuar");
			}
			else
			{	
				if($("#claveClienteTicket").val()!="")
				{
					$("#activasimulador").attr("disabled", "disabled");
					$("#btn_save").attr("disabled", "disabled");
					$("#btn_del").attr("disabled", "disabled");
					
					if(accion!="addsubequipos")
			    	{
						reasignaDatosActualizados();
						datos_fpago_g.cotizacionBillPlanList=[];
						datos_fpago_g.cotizacionBillPlanList=datos_pagos_edit;
						datos_fpago_g.cotizacionSubequipos=[];
						datos_fpago_g.cotizacionSubequipos=sub_equipos_data_ini;
			    	}					
					
					var equipo_1=$('#equipo').val();// java
					var id_ut_sup_1="";// java
					var id_car_cli_1="";
					var id_cotiz_z_1=$('#id_cotiz_z').val();//
					var vbeln_cot_1="";
					var vbeln_ped_1="";
					var id_equnr_1=$("#id_eln").val();//
					var tive_1=tipo_pago_g;//
					var tivex_1="";
					var auart_1="";
					var aedat_1=convertDate($('#fechaiv').val());//
					var cputm_1="00:00:00";//
					var datab_1=convertDate($('#datab').val());//
					var datub_1=convertDate($('#datub').val());//				
					var antic_1=$("#anticipo").autoNumericGet();
					var eng_1=$("#enganche").autoNumericGet();
					var dif_1=$("#diferido").autoNumericGet();
					var pfin_1=$("#pago_final").autoNumericGet();
					var gadmin_1=$("#gastos_admin").autoNumericGet();
					var depto_1=$("#depto").val();
					var id_cli_sap_1="";
					var nbod_1=$('#nbod').val();//
					var nest_1=$('#nest').val();//
					var m2tot_1=$('#m2tot').val();//
					var fech_entreg_1=fecha_ent_hd;//
					var descp_1=descp_hd;//
					var descm_1=descm_hd;//
					var numl_1=$('#numl').val();//
					var dias_pago_1="";//
					var si_no_pasa_1="";//
					var spaso_1="";//
					var spasox_1="";
					var npaso_1="";//
					var npasox_1="";
					var msgbapi_1="";
					var id_car_cli_1 = $('#claveClienteTicket').val();
					var descp_r_1=$('#descp').val();
					var descm_r_1=$('#descm').autoNumericGet();
					var preciocd_1=$('#descmdes').autoNumericGet();
					var nombreC_1=$('#nomClienteTicket').html();
					var subequipos_1= $('#subequipos').val();
					var total_1= $('#total').val();
					
					
					
					var crit_list_detalle;
					var crit_list_plan;
					var crit_list_equipos;
					
					if(accion=="addsubequipos")
			    	{	
						for(var i=0; i<hdr_base[pos_tp].cotizacionDetalleList.length; i++)
						{					
							var cotizacion_any=hdr_base[pos_tp].cotizacionDetalleList[i].id_cotiz_z;					
							hdr_base[pos_tp].cotizacionDetalleList[i].id_cotiz_z="$"+cotizacion_any.substring(1); 
						}
						
						for(var i=0; i<hdr_base[pos_tp].cotizacionBillPlanList.length; i++)
						{					
							var cotizacion_any=hdr_base[pos_tp].cotizacionBillPlanList[i].id_cotiz_z;					
							hdr_base[pos_tp].cotizacionBillPlanList[i].id_cotiz_z="$"+cotizacion_any.substring(1); 
						}
						
						hdr_base[pos_tp].cotizacionSubequipos=sub_equipos_data_ini;
						
						for(var i=0; i<hdr_base[pos_tp].cotizacionSubequipos.length; i++)
						{					
							var cotizacion_any=hdr_base[pos_tp].cotizacionSubequipos[i].id_cotiz_z;					
							hdr_base[pos_tp].cotizacionSubequipos[i].id_cotiz_z="$"+cotizacion_any.substring(1); 							
						}					
						
						crit_list_detalle=createXMLCriterios("criterios",hdr_base[pos_tp].cotizacionDetalleList);
						crit_list_plan=createXMLCriterios("criterios",hdr_base[pos_tp].cotizacionBillPlanList);
						crit_list_equipos=createXMLCriterios("criterios",hdr_base[pos_tp].cotizacionSubequipos);
			    	}
					else
					{
						for(var i=0; i<datos_fpago_g.cotizacionDetalleList.length; i++)
						{					
							var cotizacion_any=datos_fpago_g.cotizacionDetalleList[i].id_cotiz_z;					
							datos_fpago_g.cotizacionDetalleList[i].id_cotiz_z="$"+cotizacion_any.substring(1); 
						}
						
						for(var i=0; i<datos_fpago_g.cotizacionBillPlanList.length; i++)
						{					
							var cotizacion_any=datos_fpago_g.cotizacionBillPlanList[i].id_cotiz_z;					
							datos_fpago_g.cotizacionBillPlanList[i].id_cotiz_z="$"+cotizacion_any.substring(1); 
						}
						
						for(var i=0; i<datos_fpago_g.cotizacionSubequipos.length; i++)
						{					
							var cotizacion_any=datos_fpago_g.cotizacionSubequipos[i].id_cotiz_z;					
							datos_fpago_g.cotizacionSubequipos[i].id_cotiz_z="$"+cotizacion_any.substring(1); 
						}
						
						crit_list_detalle=createXMLCriterios("criterios",datos_fpago_g.cotizacionDetalleList);
						crit_list_plan=createXMLCriterios("criterios",datos_fpago_g.cotizacionBillPlanList);
						crit_list_equipos=createXMLCriterios("criterios",datos_fpago_g.cotizacionSubequipos);
					}
					
					
					datos_cotizador="&id_car_cli="+id_car_cli_1;		
					datos_cotizador+= "&id_cotiz_z="+id_cotiz_z_1;
					datos_cotizador+= "&vbeln_cot="+vbeln_cot_1;
					datos_cotizador+= "&vbeln_ped="+vbeln_ped_1;
					datos_cotizador+= "&id_equnr="+id_equnr_1;
					datos_cotizador+= "&tive="+tive_1;
					datos_cotizador+= "&tivex="+tivex_1;
					datos_cotizador+= "&auart="+auart_1;
					datos_cotizador+= "&aedat="+aedat_1;
					datos_cotizador+= "&cputm="+cputm_1;
					datos_cotizador+= "&datab="+datab_1;
					datos_cotizador+= "&datub="+datub_1;
					datos_cotizador+= "&id_cli_sap="+id_cli_sap_1;
					datos_cotizador+= "&nbod="+nbod_1;
					datos_cotizador+= "&nest="+nest_1;
					datos_cotizador+= "&m2tot="+m2tot_1;
					datos_cotizador+= "&fech_entreg="+fech_entreg_1;
					datos_cotizador+= "&descp="+descp_1;
					datos_cotizador+= "&descm="+descm_1;
					datos_cotizador+= "&numl="+numl_1;
					datos_cotizador+= "&dias_pago="+dias_pago_1;
					datos_cotizador+= "&si_no_pasa="+si_no_pasa_1;
					datos_cotizador+= "&spaso="+spaso_1;
					datos_cotizador+= "&spasox="+spasox_1;
					datos_cotizador+= "&npaso="+npaso_1;
					datos_cotizador+= "&npasox="+npasox_1;
					datos_cotizador+= "&msgbapi="+msgbapi_1;
					datos_cotizador+= "&detalle="+crit_list_detalle;
					datos_cotizador+= "&plan="+crit_list_plan;
					datos_cotizador+= "&equipos="+crit_list_equipos;				
					datos_cotizador+= "&descp_r="+descp_r_1;
					datos_cotizador+= "&descm_r="+descm_r_1;
					datos_cotizador+= "&preciocd="+preciocd_1;
					datos_cotizador+= "&nombreC="+nombreC_1;
					datos_cotizador+= "&subequipos="+subequipos_1;
					datos_cotizador+= "&total="+total_1;	
					datos_cotizador+= "&cotizacionZ="+$("#idCotizacionZ").val();
					datos_cotizador+= "&from="+$("#from_is").val();
					datos_cotizador+= "&equipo_select="+$("#equipoSelect").val();
					datos_cotizador+= "&equiposXml="+$("#datosEquipos").val();

					
					if(accion!="print")
					{
						if($("#btn_save").attr('value')=="Nuevo")
						{
							getCotizacionGuardado();
						}
						else
						{									
						
						$.ajax({  
						    type: "POST",  
						    url: contexPath + "/Simulador.htm",  
						    data: "accion="+accion+datos_cotizador,  
						    success: function(response){
						      // we have the response 
						    	$("#activasimulador").removeAttr("disabled", "disabled");
								$("#btn_save").removeAttr("disabled", "disabled");
								$("#btn_del").removeAttr("disabled", "disabled");
						      if(response.resCotizacion.mensaje == "SUCCESS"){
						    	  sub_equipos_select_ini=[];
						    	  sub_equipos_select=[];
						    	  var hdr_tmp=response.resCotizacion.cotizacionHeaderList;
						    	  var hdr_tmp_obj=null;

						    	  if(accion=="simular")
						    	  {
						    		  if(hdr_tmp[0].si_no_pasa=="0")
						    		  {
						    			  setEstatusCotizacion("paso");	
						    			  window.parent.$("#btn_print").removeAttr("disabled", "disabled");
						    			  window.parent.$("#mensajes_main").html("Datos de cotizacion validados correctamente");
						    		  }
						    		  else
						    		  {
						    			  //reasignaDatosHeader(hdr_tmp,hdr_tmp_obj,accion);
						    			  setDetallePlaning(hdr_tmp[0].cotizacionBillPlanList);
						    			  setEstatusCotizacion("npaso");
						    			  window.parent.$("#mensajes_main").html("La cotizacion no cumple con los requerimientos minimos, verifique datos");
						    			  $("#estatus_simulador").attr("title","PASO");
						    		  }
						    	  }
						    	  else if(accion=="addsubequipos")
						    	  {
						    		  hdr_tmp_obj=response.resCotizacionBase.cotizacionHeaderList;
						    		  reasignaDatosHeader(hdr_tmp,hdr_tmp_obj,accion);
						    		  closeWinSubEqu();
						    		  window.parent.$("#mensajes_main").html("Los subequipos se han actualizado satisfactoriamente");
						    	  }
						    	  else if(accion=="guardar")
						    	  {		
						    		  $("#activasimulador").attr('value', 'Activar');
					    			  $("#btn_save").attr('value', 'Nuevo');
					    			  if($("#estatus_simulador").attr("src")!=contexPath+"/images/icons/semaforonp.png")
									  {
					    				  $("#btn_print").removeAttr("disabled", "disabled");
									  }			    			  
					    			  			    			  
						    		  if(response.resCotizacion.e_id_cotiz_sap!="")
						    		  {			    			 
						    			  $("#id_cotiz_z").val(response.resCotizacion.e_id_cotiz_sap);
							    		  window.parent.$("#mensajes_main").html("Se creo la cotizacion SAP "+response.resCotizacion.e_id_cotiz_sap);			    			 
							    		  $("#idCotizacion").val(response.resCotizacion.e_id_cotiz_sap);							    		  
						    		  }
						    		  else
						    		  {
						    			  $("#idCotizacion").val(response.resCotizacion.e_id_cotiz_z);
						    			  $("#id_cotiz_z").val(response.resCotizacion.e_id_cotiz_z);
							    		  window.parent.$("#mensajes_main").html("Se creo la cotizacion Z "+response.resCotizacion.e_id_cotiz_z);							    		  							    		  
						    		  }							    		  
						    		  
						    		  enableDisableComps(false);
						    		  $("#activasimulador").attr("disabled", "disabled");
						    		  $("#btn_del").attr("disabled", "disabled");
						    		  $("#rdo_tpago1").attr("disabled", "disabled");
						    		  $("#rdo_tpago2").attr("disabled", "disabled");
						    		  $("#rdo_tpago3").attr("disabled", "disabled");	
						    		  
						    	  }
						      }
						      else if(response.resCotizacion.mensaje == "LOGOUT")
						      {
						    	  window.parent.salirSistema();
						      }
						      else
						      {
						    	  setEstatusCotizacion("npaso");
						    	  $("#activasimulador").removeAttr("disabled", "disabled");
									$("#btn_save").removeAttr("disabled", "disabled");
									$("#btn_del").removeAttr("disabled", "disabled");
						    	  window.parent.$("#mensajes_main").html(response.resCotizacion.descripcion);
						      }	      
						    },  
						    error: function(xhr, textStatus, errorThrown) { 
						    	setEstatusCotizacion("npaso");
						    	$("#activasimulador").removeAttr("disabled", "disabled");
								$("#btn_save").removeAttr("disabled", "disabled");
								$("#btn_del").removeAttr("disabled", "disabled");
						    	window.parent.$("#mensajes_main").html(textStatus);
						    },
						   
						  });
						}
					}
					else
					{
						if($("#btn_save").attr('value')!="Nuevo")
						{							
							if(imprime_after_getcot=1)
							{
								$("#btn_save").removeAttr("disabled", "disabled");
								var $currentIFrame = $('#uploadIFrame'); 
								$currentIFrame.contents().find("body #equipo").val(equipo_1);
								$currentIFrame.contents().find("body #depto").val(depto_1);
								$currentIFrame.contents().find("body #id_cotiz_z").val(id_cotiz_z_1);
								$currentIFrame.contents().find("body #id_equnr").val(id_equnr_1);
								$currentIFrame.contents().find("body #tive").val(tive_1);
								$currentIFrame.contents().find("body #fechaiv").val(aedat_1);
								$currentIFrame.contents().find("body #datab").val(datab_1);
								$currentIFrame.contents().find("body #datub").val(datub_1);
								$currentIFrame.contents().find("body #nbod").val(nbod_1);
								$currentIFrame.contents().find("body #nest").val(nest_1);
								$currentIFrame.contents().find("body #m2tot").val(m2tot_1);
								$currentIFrame.contents().find("body #fech_entreg").val(fech_entreg_1);
								$currentIFrame.contents().find("body #anticipo").val(antic_1);
								$currentIFrame.contents().find("body #enganche").val(eng_1);
								$currentIFrame.contents().find("body #diferido").val(dif_1);
								$currentIFrame.contents().find("body #pago_final").val(pfin_1);
								$currentIFrame.contents().find("body #gastos_admin").val(gadmin_1);
								$currentIFrame.contents().find("body #descm").val(gadmin_1);
								$currentIFrame.contents().find("body #numl").val(0);
								$currentIFrame.contents().find("body #id_car_cli").val(id_car_cli_1);
								$currentIFrame.contents().find("body #claveClienteTicket").val(id_car_cli_1);
								$currentIFrame.contents().find("body #descp").val(descp_r_1);
								$currentIFrame.contents().find("body #descm").val(descm_r_1);
								$currentIFrame.contents().find("body #descmdes").val(preciocd_1);
								$currentIFrame.contents().find("body #nombreC").val(nombreC_1);
								$currentIFrame.contents().find("body #subequipos").val(subequipos_1);
								$currentIFrame.contents().find("body #total").val(total_1);
								$currentIFrame.contents().find("body #detalle").val(crit_list_detalle);
								$currentIFrame.contents().find("body #plan").val(crit_list_plan);
								$currentIFrame.contents().find("body #equipos").val(crit_list_equipos);
								
								$currentIFrame.get(0).contentWindow.setImprimeCotizacion();
							}
							else
							{
								window.parent.$("#mensajes_main").html("No se puede imprimir el documento hasta que no o guarde la cotizacion");								
							}
						}
						else if(accion!="print")
						{								
							getCotizacionGuardado();		
						}
						else							
						{							
							$("#btn_save").removeAttr("disabled", "disabled");
							var $currentIFrame = $('#uploadIFrame');
							$currentIFrame.contents().find("body #equipo").val(equipo_1);
							$currentIFrame.contents().find("body #depto").val(depto_1);
							$currentIFrame.contents().find("body #id_cotiz_z").val(id_cotiz_z_1);
							$currentIFrame.contents().find("body #id_equnr").val(id_equnr_1);
							$currentIFrame.contents().find("body #tive").val(tive_1);
							$currentIFrame.contents().find("body #fechaiv").val(aedat_1);
							$currentIFrame.contents().find("body #datab").val(datab_1);
							$currentIFrame.contents().find("body #datub").val(datub_1);
							$currentIFrame.contents().find("body #nbod").val(nbod_1);
							$currentIFrame.contents().find("body #nest").val(nest_1);
							$currentIFrame.contents().find("body #m2tot").val(m2tot_1);
							$currentIFrame.contents().find("body #fech_entreg").val(fech_entreg_1);
							$currentIFrame.contents().find("body #anticipo").val(antic_1);
							$currentIFrame.contents().find("body #enganche").val(eng_1);
							$currentIFrame.contents().find("body #diferido").val(dif_1);
							$currentIFrame.contents().find("body #pago_final").val(pfin_1);
							$currentIFrame.contents().find("body #gastos_admin").val(gadmin_1);
							$currentIFrame.contents().find("body #descm").val(gadmin_1);
							$currentIFrame.contents().find("body #numl").val(0);
							$currentIFrame.contents().find("body #id_car_cli").val(id_car_cli_1);
							$currentIFrame.contents().find("body #claveClienteTicket").val(id_car_cli_1);
							$currentIFrame.contents().find("body #descp").val(descp_r_1);
							$currentIFrame.contents().find("body #descm").val(descm_r_1);
							$currentIFrame.contents().find("body #descmdes").val(preciocd_1);
							$currentIFrame.contents().find("body #nombreC").val(nombreC_1);
							$currentIFrame.contents().find("body #subequipos").val(subequipos_1);
							$currentIFrame.contents().find("body #total").val(total_1);
							$currentIFrame.contents().find("body #detalle").val(crit_list_detalle);
							$currentIFrame.contents().find("body #plan").val(crit_list_plan);
							$currentIFrame.contents().find("body #equipos").val(crit_list_equipos);
							
							$currentIFrame.get(0).contentWindow.setImprimeCotizacion();
						}
					}
				}
				else
				{
					window.parent.$("#mensajes_main").html("Debe introducir la cartera de clientes valida");
				}
			}			
	}
	
	
	function reasignaDatosHeader(header_reset,header_reset_obj, acc_)
	{
		var datos_act;
		var datos_act_base;
		var encontrado=false;
		
		for(var i=0; i<header_reset.length; i++)
		{
			if(header_reset[i].tive==tipo_pago_g)
			{
				encontrado=true;
				datos_act=header_reset[i];
				datos_act_base=header_reset_obj[i];
				break;
			}
		}
		
		if(encontrado)
		{		
			for(var i=0; i<hdr_datos_reload.length; i++)
			{
				if(hdr_datos_reload[i].tive==tipo_pago_g)
				{					
					
					hdr_datos_reload[i]=datos_act;
					hdr_base[i]=datos_act_base;
				}
			}
			selectDatosByPago(tipo_pago_g, "actualizacion");
			ejecutadesc("descp");
			
		}	
	}
	
	function closeWinSubEqu()
	{
		var winse = $("#winsubequipos").data("kendoWindow");
		winse.close();
	}
	
	function onCloseWinSubEqu()
	{
		sub_equipos_select_ini=[];
  	  	sub_equipos_select=[];
	}
	
	function setEstatusCotizacion(estatus_accion)
	{
		if(estatus_accion=="paso")
		{
			$("#estatus_simulador").attr("title","PASO");
			$("#estatus_simulador").attr("src",contexPath+"/images/icons/semaforop.png");
		}
		else if(estatus_accion=="simular")
		{
			imprime_after_getcot=0;
			$("#estatus_simulador").attr("title","SIMULAR");
			$("#estatus_simulador").attr("src",contexPath+"/images/icons/semaforos.png"); 
			$("#btn_print").attr("disabled", "disabled");
		}
		else if(estatus_accion=="npaso")
		{
			$("#estatus_simulador").attr("title","NO PASO");
			$("#estatus_simulador").attr("src",contexPath+"/images/icons/semaforonp.png");
		}
	}
	
	function enviarFormato(af,numero, decimal)
	{
		accion_format = af;
		validaFormater(numero, decimal);
	}
	
	function getCotizacionGuardado()
	{		
		var idCotizacion=$("#idCotizacion").val();
		var idCliente = $("#idCliente").val();
		var idCarCliente = $("#idCarCliente").val();
		var idPedido = $("#idPedido").val();		
		$.ajax({  
		    type: "POST",  
		    url: contexPath + "/Simulador.htm",  
		    data: "accion=getCotizacion&vbeln_cot="+idCotizacion,  
		    success: function(response){
		      // we have the response 
		      if(response.resCotizacion.mensaje == "SUCCESS"){
		    	  var tpago_return="";
		    	  hdr_datos=response.resCotizacion.cotizacionHeaderList;
		    	  hdr_datos_reload=response.resCotizacion.cotizacionHeaderList;
		    	  hdr_base = response.resCotizacionBase.cotizacionHeaderList;
		    	  
		    	  if(hdr_datos[0].vbeln_cot!=null && hdr_datos[0].vbeln_cot!=undefined && hdr_datos[0].vbeln_cot!="")
			      {
		    		  tpago_return=hdr_datos[0].tive;
			      }
		    	  else if(hdr_datos[1].vbeln_cot!=null && hdr_datos[1].vbeln_cot!=undefined && hdr_datos[1].vbeln_cot!="")
			      {
		    		  tpago_return=hdr_datos[1].tive;
			      }
		    	  else if(hdr_datos[2].vbeln_cot!=null && hdr_datos[2].vbeln_cot!=undefined && hdr_datos[2].vbeln_cot!="")
			      {
		    		  tpago_return=hdr_datos[2].tive;
			      }
		    	  
		    	  if(tpago_return=="CH")
	    		  {
		    		  $("#rdo_tpago").attr("checked","checked");
		    		  $("#rdo_tpago3").removeAttr("checked","checked");
		    		  $("#rdo_tpago2").removeAttr("checked","checked");
	    		  }
		    	  else if(tpago_return=="FO")
	    		  {
		    		  $("#rdo_tpago3").attr("checked","checked");
		    		  $("#rdo_tpago").removeAttr("checked","checked");
		    		  $("#rdo_tpago2").removeAttr("checked","checked");
	    		  }
		    	  else if(tpago_return=="CO")
	    		  {
		    		  $("#rdo_tpago2").attr("checked","checked");
		    		  $("#rdo_tpago3").removeAttr("checked","checked");
		    		  $("#rdo_tpago").removeAttr("checked","checked");
	    		  }
		    	  
		    	  
		    	  if(hdr_datos[0].si_no_pasa=="0")
	    		  {
	    			  setEstatusCotizacion("paso");		    			  
	    			  window.parent.$("#mensajes_main").html("Datos de cotizacion validados correctamente");	
	    			  $("#btn_print").removeAttr("disabled", "disabled");	    			  
	    		  }
	    		  else
	    		  {
	    			  setEstatusCotizacion("npaso");
	    			  window.parent.$("#mensajes_main").html("La cotizacion no cumple con los requerimientos minimos, verifique datos");
	    		  }	    	  
		    	  selectDatosByPago(tpago_return,"init");
		    	  simulador=false;
		    	  $("#activasimulador").removeAttr("disabled", "disabled");
		    	  $("#activasimulador").attr('value', 'Activar');
		    	  
		    	  
		    	  $("#btn_del").removeAttr("disabled", "disabled");
	    		  $("#rdo_tpago1").removeAttr("disabled", "disabled");
	    		  $("#rdo_tpago2").removeAttr("disabled", "disabled");
	    		  $("#rdo_tpago3").removeAttr("disabled", "disabled");
		    	  $("#btn_save").attr('value', 'Guardar');
		  		  enableDisableComps(false);				  			
		  		  imprime_after_getcot=1;
		      }
		      else if(response.resCotizacion.mensaje == "LOGOUT")
		      {
		    	  window.parent.salirSistema();
		      }
		      else
		      {
		    	  window.parent.$("#mensajes_main").html(response.resCotizacion.descripcion);
		      }	      
		    },  
		    error: function(xhr, textStatus, errorThrown) { 
		           // Don't raise this alert if user has navigated away from the page 
		    	window.parent.$("#mensajes_main").html(textStatus);
		    },
		    /* error: function(e){  
		    	winAlert("Alert",e,100,200);  
		    } */
		  }); 

	}
	
	function openImprimeCotizacion()
	{
		
		
		
		/* datos_cotizador="&id_car_cli="+id_car_cli_1;		
		datos_cotizador+= "&id_cotiz_z="+id_cotiz_z_1;
		datos_cotizador+= "&vbeln_cot="+vbeln_cot_1;
		datos_cotizador+= "&vbeln_ped="+vbeln_ped_1;
		datos_cotizador+= "&id_equnr="+id_equnr_1;
		datos_cotizador+= "&tive="+tive_1;
		datos_cotizador+= "&tivex="+tivex_1;
		datos_cotizador+= "&auart="+auart_1;
		datos_cotizador+= "&aedat="+aedat_1;
		datos_cotizador+= "&cputm="+cputm_1;
		datos_cotizador+= "&datab="+datab_1;
		datos_cotizador+= "&datub="+datub_1;
		datos_cotizador+= "&id_cli_sap="+id_cli_sap_1;
		datos_cotizador+= "&nbod="+nbod_1;
		datos_cotizador+= "&nest="+nest_1;
		datos_cotizador+= "&m2tot="+m2tot_1;
		datos_cotizador+= "&fech_entreg="+fech_entreg_1;
		datos_cotizador+= "&descp="+descp_1;
		datos_cotizador+= "&descm="+descm_1;
		datos_cotizador+= "&numl="+numl_1;
		datos_cotizador+= "&dias_pago="+dias_pago_1;
		datos_cotizador+= "&si_no_pasa="+si_no_pasa_1;
		datos_cotizador+= "&spaso="+spaso_1;
		datos_cotizador+= "&spasox="+spasox_1;
		datos_cotizador+= "&npaso="+npaso_1;
		datos_cotizador+= "&npasox="+npasox_1;
		datos_cotizador+= "&msgbapi="+msgbapi_1;
		datos_cotizador+= "&detalle="+crit_list_detalle;
		datos_cotizador+= "&plan="+crit_list_plan;
		datos_cotizador+= "&equipos="+crit_list_equipos;				
		datos_cotizador+= "&descp_r="+descp_r_1;
		datos_cotizador+= "&descm_r="+descm_r_1;
		datos_cotizador+= "&preciocd="+preciocd_1;	*/	
		/*
		macCI.put("cotizacion", cotizacionDetalleList.get(4).getNetwr());
		macCI.put("cliente", criterios.getId_car_cli());
		macCI.put("unidad", criterios.getI_equnr());
		macCI.put("nombre", criterios.getNombreC());
		macCI.put("fecha", criterios.getAedat());
		macCI.put("iniv", criterios.getDatab());
		macCI.put("finv",criterios.getDatub());
		macCI.put("reservado", "");
		macCI.put("hora", "");
		macCI.put("precio", cotizacionDetalleList.get(4).getNetwr());
		macCI.put("desc", criterios.getDescm());
		macCI.put("preciocd", criterios.getPreciocd());
		macCI.put("subequipos", cotizacionDetalleList.get(4).getNetwr());
		macCI.put("total", cotizacionDetalleList.get(6).getNetwr());
		macCI.put("apartado", cotizacionDetalleList.get(1).getNetwr());
		macCI.put("enganche", cotizacionDetalleList.get(2).getNetwr());
		macCI.put("diferido", cotizacionDetalleList.get(3).getNetwr());
		macCI.put("pagof", cotizacionDetalleList.get(5).getNetwr());
		macCI.put("gastosadmin", cotizacionDetalleList.get(0).getNetwr());*/
		
		
		

		/*$.ajax({  
		    type: "POST",  
		    url: contexPath + "/ImprimeSimulador.htm",  
		    data: "accion=print"+datos_cotizador,  
		    success: function(response){
		      // we have the response 
		      if(response.resCotizacion.mensaje == "SUCCESS"){	
	    		   window.parent.$("#mensajes_main").html("Creacion del archivo PDF creado con exito");
		      }	
		      else{
		    	  window.parent.$("#mensajes_main").html(response.resCotizacion.descripcion);
		      }	      
		    },  
		    error: function(xhr, textStatus, errorThrown) { 
		           // Don't raise this alert if user has navigated away from the page 
		    	window.parent.$("#mensajes_main").html(textStatus);
		    }
		    
		  });*/
	}