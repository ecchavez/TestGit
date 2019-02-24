var permisos_user=[];
var permisos_user_add=[];
var dataPermisos = [];
var dataPermisosAdd = [];
var id_permiso="";
var id_permiso_add="";
var desc_permiso_add="";
var windowAddUser;
var id_ubicacion_add="";
var id_ubicacion="";
var itemUsuario=[];
var accion_user="";

var usuariosDataExport = [];
var permisosUsuarioDataExport = [];
var permisosDataExport = [];

var accion_="";
var datos_row_="";

var tipo_usr_sesion="";

var winDecideEliminaUser;

var perm_left = 0;
var perm_rigth = 0;


function validaUsuario() {  
	  // get the form values 
	var ubic_cmb=$('#cmb_ubicaciones').data("kendoDropDownList");
	var ubicacion = $.trim($('#cmb_ubicaciones').val());
	var ubicacionx = ubic_cmb.text(); 
	var usuario = $.trim($('#txt_user').val());
	var passwd = $.trim($('#txt_passwd').val());
	
	usuariosDataExport = [];
	permisosUsuarioDataExport = [];
	permisosDataExport = [];
 
	if(validaFormLogin())
	{
		
	  var userInfo = "";
	  var kendoAlert;
	  
	  $("#btn_aceptar").attr("disabled","disabled");
	  
	  $.ajax({  
	    type: "POST",  
	    url: contexPath + "/Login.htm",  
	    data: "usuario=" + usuario + "&pass=" + passwd + "&id_ut_sup=" + ubicacion + "&id_ut_sup_cm=" + ubicacionx, 
	    success: function(response){	      
		  // we have the response 		  
	      if(response.mensaje == "SUCCESS"){	    	  
	    	  $("#div_msj").html("Bienvenido "+usuario+", espere mientras se cargan datos iniciales");
	    	  if(idTicket == 'null' || idTicket == ''){
	    		  setTimeout(function() {window.location=contexPath+'/Escritorio.htm';} , 1000);
	    	  }else{
	    		  setTimeout(function() {window.location=contexPath+'/ticket/ConsultaCatalogoTicketsView.htm?idTicket=' + idTicket;} , 1000);
	    	  }
	      }else{	
	    	  $("#btn_aceptar").removeAttr("disabled", "disabled");
	    	  $("#div_msj").html(response.descripcion);
	    	  //winAlert("Alert",response.descripcion,100,200);
	      }	      
	    },  
	    error: function(e){
	    	$("#btn_aceptar").removeAttr("disabled", "disabled");
	    	$("#div_msj").html(e);
	    }  
	  }); 
	}
	}

function getUsuarios() {  
	  // get the form values  
	  var usuariosList = "";
	  var permisosUsuariosList = "";
	  var permisosList = "";
	  dataPermisos = [];
	  usuariosDataExport = [];
	  permisosUsuarioDataExport = [];
	  permisosDataExport = [];
		
	  $.ajax({  
	    type: "POST",  
	    url: contexPath + "/CatalogoUsuarios.htm",
	    data: "accion=getUsuarios",  
	    success: function(response){
	      // we have the response 
	      if(response.respGetUsuariosDto.mensaje == "SUCCESS"){
	    	  usuariosDataExport = response.respGetUsuariosDto.objUsuariosList;  
	    	  permisosUsuarioDataExport = response.respGetUsuariosDto.objPermisosUserList; 
	    	  permisosDataExport = response.respGetUsuariosDto.objPermisosList;
	    	  
	    	  
	    	  var dataSampleUser = [];
	    	  for(var i=0 ; i < usuariosDataExport.length ; i++){
	    		  dataSampleUser.push(usuariosDataExport[i]);			  
	    	  }
	    	  
	    	  var dataSourceUser = new kendo.data.DataSource({
	    	        data: dataSampleUser,	    	        
	    	        pageSize: 50
	    	    });
	    	  $("#dg_usuarios").empty();
	    	  $("#dg_usuarios").kendoGrid({
					dataSource: dataSourceUser,
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
									field: "nombre1",
	                                title: "Nombre",
	                                width: 150
	                            },
	                            {
	                                field: "nombre2",
	                                title: "Segundo Nombre",
	                                width: 150
	                            },
	                            {
	                                field: "app_pat",
	                                title: "Primer Apellido",
	                                width: 150			                                
	                            },
	                            {
	                                field: "app_mat",
	                                title: "Segundo Apellido",
	                                width: 150			 
	                            },
	                            {
	                            	field: "pass",
	                                title: "Contraseña",
	                                hidden: true,
	                                width: 100			                                 
	                            },
	                            {
	                            	field: "usuario",
	                                title: "Usuario",
	                                width: 120			 
								},
	                            {
	                            	field: "correo",
	                                title: "Correo",
	                                width: 120			 
								},
	                            {
	                            	field: "telefono",
	                                title: "Telefono",
	                                width: 120			 
								},
	                            {
	                            	field: "extension",
	                                title: "Ext",
	                                width: 50			 
								}	                            
							],
	                  selectable: "row",
					  change: onChangeUsers								  
				  }); 

	    	  var dataUserPermisos = [];
	    	  for(var i =0 ; i < permisosUsuarioDataExport.length ; i++){	    		  
	    		  dataUserPermisos.push(permisosUsuarioDataExport[i]);			  
	    	  }
	    	  
	    	  var dataSourceUserPermisos = new kendo.data.DataSource({
	    	        //data: dataUserPermisos,	    	        
	    	        //pageSize: 5
	    	    });

	    	  for(var i =0 ; i < permisosDataExport.length ; i++){
	    		    
	    		    if(permisosDataExport[i].special!="X")
		    		{
	    		    	dataPermisos.push(permisosDataExport[i]);
		    		}
	    	  }
	    	  dataPermisosAdd=dataPermisos;
	    	  permisosDataExport=dataPermisos;
	    	  //setDataPermisos(dataPermisos);
			  
	      }
	      else if(response.respGetUsuariosDto.mensaje == "INICIAL")
	      {
	    	  usuariosDataExport = response.respGetUsuariosDto.objUsuariosList;  
	    	  permisosUsuarioDataExport = response.respGetUsuariosDto.objPermisosUserList; 
	    	  permisosDataExport = response.respGetUsuariosDto.objPermisosList;
	    	  
	    	  for(var i =0 ; i < permisosDataExport.length ; i++){
	    		  if(permisosDataExport[i].special!="X")
		    		{
	    			  dataPermisos.push(permisosDataExport[i]);	  
		    		}
	    	  }
	    	  dataPermisosAdd=dataPermisos;
	    	  permisosDataExport=dataPermisos;
	    	  openWinAddUser('nuevo');
	    	  tipo_usr_sesion='INICIAL';
	      }
	      else
	      {
	    	  $("#mensajes_main").html(response.respGetUsuariosDto.descripcion,"error");
	      }	      
	    },  
	    error: function(e){  
	    	$("#mensajes_main").html(e);  
	    }  
	  });  
	}
	
	function onChangeUsers(arg) {
		
    }
	
	function onChangeUsrPerm(arg) {   
	    perm_rigth=1;
      var selected = $.map(this.select(), function(item) {        	 
      	window.parent.id_permiso_add=item.cells[0].innerHTML;      
      	window.parent.desc_permiso_add=item.cells[1].innerHTML;
      });

      //kendoConsole.log("Selected: " + selected.length + " item(s), [" + selected.join(", ") + "]");
  }	
	function onChangePermisos(arg) {
		perm_left=1;
      var selected = $.map(this.select(), function(item) {
      	window.parent.id_permiso_add=item.cells[0].innerHTML;
      	window.parent.desc_permiso_add=item.cells[1].innerHTML;
      	//alert(id_permiso_add+" "+desc_permiso_add);
      });

      //kendoConsole.log("Selected: " + selected.length + " item(s), [" + selected.join(", ") + "]");
  }	
	
	function validaFormLogin()
	{
		var ubicacion = $.trim($('#cmb_ubicaciones').val());
		var usuario = $.trim($('#txt_user').val());
		var passwd = $.trim($('#txt_passwd').val());

		var resp=false;
		
		if(ubicacion=="")
		{
			$("#div_msj").html("Debe de seleccionar una ubicacion");
			//winAlert("Alert","Debe de seleccionar una ubicacion",100,200);
		}
		else if(usuario=="")
		{
			$("#div_msj").html("Debe introducir un usuario");
			//winAlert("Alert","Debe introducir un usuario",100,200);
		}
		else if(passwd=="")
		{
			$("#div_msj").html("Debe introducir un password");
			//winAlert("Alert","Debe introducir un password",100,200);
		}
		else
		{
			resp=true;
		}
		return resp;
	}
	
	function winAlert(titulo,contenido,altopx,anchopx)
	{
		$("#alert").kendoWindow({
		    actions: ["Close"],
		    draggable: true,
		    height: altopx+"px",
		    modal: true,
		    resizable: false,
		    title: titulo,
		    width: anchopx+"px"
  	  });
  	kendoAlert = $("#alert").data("kendoWindow");
  	kendoAlert.open();
	    kendoAlert.center();
	    kendoAlert.content(contenido);
	}
	
	function agregaPermiso()
	{
		var existe=false;
		for(var i=0; i<permisos_user.length; i++)
      {			
			if(permisos_user[i].id_permiso==id_permiso_add)
			{
				existe=true;
			}			
      }
		
		if(!existe)
		{			
			permisos_user.push({id_permiso: id_permiso_add, id_permisox: desc_permiso_add});		        
			setDataUserPermisos(permisos_user)
		}
		else
		{
			$("#mensajes_main").html("El permiso ya se ha asignado verifique datos");
		}
	}
	
	function quitarPermiso()
	{
		for(var i=0; i<permisos_user.length; i++)
      {
			if(permisos_user[i].id_permiso==id_permiso)
			{
				permisos_user.splice(i, 1);
			}
      }
		
		setDataUserPermisos(permisos_user);
		
	}
	

	function adminUsuarios(accion,datos_row)
	{
		var sel_row = datos_row;
		var listaUsuariosStr="";
		//alert(permisos_user_add.length);
		if(accion == "addUser")
		{		
			if(validaFormAdminUsuario("add"))
			{
				for(var i=0; i<window.parent.permisos_user_add.length; i++)
		        {
					listaUsuariosStr+=window.parent.permisos_user_add[i].id_permiso+"|";
		        }
				
				var nombre1 = $.trim($('#txt_nom').val());
				var nombre2 = $.trim($('#txt_snom').val());
				var app_pat = $.trim($('#txt_apa').val());
				var app_mat = $.trim($('#txt_ama').val());
				var usuario_cm = $.trim($('#txt_user').val());
				var pass = $.trim($('#txt_pass').val());
				var tel = $.trim($('#txt_tel').val());
				var ext = $.trim($('#txt_ext').val());
				var correo = $.trim($('#txt_correo').val());
				var permisosStr = listaUsuariosStr;
				
				var datos = "telefono="+tel+"&extension="+ext+"&correo="+correo+"&nombre1="+nombre1+"&nombre2="+nombre2+"&app_pat="+app_pat+"&app_mat="+app_mat+"&usuario_cm="+usuario_cm+"&pass="+pass+"&act_usr=&permisosStr="+permisosStr+"&accion=addUsuario";
				
				$.ajax({  
				    type: "POST",  
				    url: contexPath + "/AddNuevoUsuario.htm",  
				    data: datos,  
				    success: function(response){
				      // we have the response 
				      if(response.respAddUsuarioDto.mensaje == "SUCCESS"){
				    	  window.parent.$("#mensajes_main").html("Usuario guardado satisfactoriamente");				    	  				    	  
					      $('#txt_nom').val("");
					      $('#txt_snom').val("");
					      $('#txt_apa').val("");
					      $('#txt_ama').val("");
					      $('#txt_user').val("");
					      $('#txt_pass').val("");
					      $('#txt_tel').val("");
					      $('#txt_ext').val("");
					      $('#txt_correo').val("");
					      listaUsuariosStr="";
					      window.parent.permisos_user_add=[];
					      window.parent.setDataUserPermisosAdd([]);

					      window.parent.itemUsuario=[];
					      window.parent.listaUsuariosStr="";
					      window.parent.permisos_user=[];
					      // window.parent.setDataUserPermisos([]);
					      // window.parent.setDataPermisos([]);
					      window.parent.permisos_user=[];    
					      
					      if(window.parent.tipo_usr_sesion=="INICIAL")
					      {			
					    	  window.parent.closeSesion();
					    	  window.parent.tipo_usr_sesion="";
					      }
					      else
					      {
					    	  window.parent.closeUserWindow();
					      }
					      
				      }
				      else
				      {
				    	  $("#mensajes_main").html(response.respAddUsuarioDto.descripcion);
				      }	      
				    },  
				    error: function(xhr, textStatus, errorThrown) { 
				           // Don't raise this alert if user has navigated away from the page 
				    	$("#mensajes_main").html(textStatus);
				    }
				    
				  }); 
			}
		}		
		else if(accion == "upUser")
		{		
			if(validaFormAdminUsuario("update"))
			{
				for(var i=0; i<window.parent.permisos_user_add.length; i++)
		        {
					listaUsuariosStr+=window.parent.permisos_user_add[i].id_permiso+"|";
		        }
				
				var nombre1 = $.trim($('#txt_nom').val());
				var nombre2 = $.trim($('#txt_snom').val());
				var app_pat = $.trim($('#txt_apa').val());
				var app_mat = $.trim($('#txt_ama').val());
				var usuario_cm = $.trim($('#txt_user').val());
				var pass = $.trim($('#txt_pass').val());
				var tel = $.trim($('#txt_tel').val());
				var ext = $.trim($('#txt_ext').val());
				var correo = $.trim($('#txt_correo').val());
				var permisosStr = listaUsuariosStr;
				var datos = "telefono="+tel+"&extension="+ext+"&correo="+correo+"&nombre1="+nombre1+"&nombre2="+nombre2+"&app_pat="+app_pat+"&app_mat="+app_mat+"&usuario_cm="+usuario_cm+"&pass="+pass+"&act_usr=X&permisosStr="+listaUsuariosStr+"&accion=updUsuario&jsonPermisosStr="+permisos_user;
				
			    $.ajax({  
			    type: "POST",  
			    url: contexPath + "/CatalogoUsuarios.htm",   
			    data: datos,  
			    success: function(response){
			      // we have the response 
			      if(response.respAddUsuarioDto.mensaje == "SUCCESS"){
			    	  $("#mensajes_main").html("Usuario actualizado satisfactoriamente");
				      $('#txt_nom').val("");
				      $('#txt_snom').val("");
				      $('#txt_apa').val("");
				      $('#txt_ama').val("");
				      $('#txt_user').val("");
				      $('#txt_pass').val("");
				      $('#txt_tel').val("");
				      $('#txt_ext').val("");
				      $('#txt_correo').val("");
				      window.parent.permisos_user_add=[];
				      itemUsuario=[];
				      listaUsuariosStr="";
				      permisos_user=[];
				      // setDataUserPermisos([]);
				      //setDataPermisos([]);
				      permisos_user=[];
				      window.parent.getUsuarios();
				      window.parent.closeUserWindow();
			      }else{
			    	  $("#mensajes_main").html(response.respAddUsuarioDto.descripcion);
			      }	      
			    },  
			    error: function(e){  
			    	$("#mensajes_main").html(e);  
			    }  
			  }); 
			}
		}
		else if (accion == "delUser")
		{
			var usuario = sel_row;
			
			$.ajax({  
			    type: "POST",  
			    url: contexPath + "/CatalogoUsuarios.htm",
			    data: "usuario_cm=" + usuario +"&accion=delUsuario",  
			    success: function(response){
			      // we have the response 
			      if(response.respDelUsuarioDto.mensaje == "SUCCESS"){
			    	  $("#mensajes_main").html("Usuario eliminado satisfactoriamente");	
			    	  $('#txt_nom').val("");
				      $('#txt_snom').val("");
				      $('#txt_apa').val("");
				      $('#txt_ama').val("");
				      $('#txt_user').val("");
				      $('#txt_pass').val("");
				      $('#txt_tel').val("");
				      $('#txt_ext').val("");
				      $('#txt_correo').val("");
				      itemUsuario=[];
				      listaUsuariosStr="";
				      permisos_user=[];
				      //setDataUserPermisos([]);
				      //setDataPermisos([]);
				      permisos_user=[];
				      getUsuarios();
			      }else{
			    	  $("#mensajes_main").html(response.respDelUsuarioDto.descripcion);	
			      }	      
			    },  
			    error: function(e){  
			    	$("#mensajes_main").html(e);
			    }  
			  }); 
		}
		else if (accion == "getDataInit")
		{
			$.ajax({  
			    type: "POST",  
			    url: contexPath + "/AddNuevoUsuario.htm",  
			    data: "accion=" + accion,  
			    success: function(response){
			      // we have the response 
				  
			      if(response.respUbicacionTecnicaDto.mensaje == "SUCCESS"){
			    	  
			    	  if(response.respGetUsuariosDto.objPermisosList.length>=1)
			    	  {			    		  
			    		  setDataPermisosAdd(response.respGetUsuariosDto.objPermisosList);
			    		  $("#mensajes_main").html(response.respGetUsuariosDto.objPermisosList.length+" Permisos extraidos");
			    	  }
			    	  else
			    	  {
			    		  $("#mensajes_main").html("No existen datos de ubicaciones");			    		 
			    	  }
			      }else{
			    	  $("#mensajes_main").html(response.respUbicacionTecnicaDto.descripcion);
			      }	      
			    },  
			    error: function(e){  			    	
			    	$("#mensajes_main").html(e);
			    }  
			  }); 
		}		
	 }	
	
	
	function openWinAddUser(accion)
	{	
		
		setDataPermisosAdd(permisosDataExport);
		accion_user=accion;
		if(accion_user=="nuevo")
		{
			  id_permiso_add="";
      	  desc_permiso_add="";
      	  permisos_user_add = [];
      	  $('#txt_nom').val("");
		      $('#txt_snom').val("");
		      $('#txt_apa').val("");
		      $('#txt_ama').val("");
		      $('#txt_user').val("");
		      $('#txt_pass').val("");
		      $('#txt_tel').val("");
		      $('#txt_ext').val("");
		      $('#txt_correo').val("");
      	  itemUsuario = [];  
      	  
      	windowAddUser = $("#windowAddUser").kendoWindow({
			actions: ["Close"],
			modal: true,
			resizable: false,
			content: "",
          height: "450px",
          title: "Usuarios",
			width: "800px"
		  }).data("kendoWindow");	
		
		windowAddUser = $("#windowAddUser").kendoWindow({
			actions: ["Close"],
			modal: true,
			resizable: false,
			content: contexPath+"/AddNuevoUsuario.htm",
          height: "450px",
          title: "Usuarios",
			width: "800px"
		  }).data("kendoWindow");	

		windowAddUser.open();
		windowAddUser.refresh();
		windowAddUser.center();
      	  
		}
		else
		{
			var gridUsuarios = $("#dg_usuarios").data("kendoGrid");   
			var usuario;
			
			if(gridUsuarios.dataItem(gridUsuarios.tbody.find("tr.k-state-selected"))!= undefined)
			{
				dataItemUsers= gridUsuarios.dataItem(gridUsuarios.tbody.find("tr.k-state-selected"));
				usuario = dataItemUsers.usuario;
				for(var i=0; i<usuariosDataExport.length; i++)
		        {
		        	//alert(usuariosDataExport[i].usuario+" - "+item.cells[5].innerHTML);
		        	if(usuariosDataExport[i].usuario == usuario)
		        	{
		        		permisos_user_add=usuariosDataExport[i].objPermisosUserList;	        		
		        		itemUsuario = usuariosDataExport[i];
		        		break;
		        	}
		        }
				
				windowAddUser = $("#windowAddUser").kendoWindow({
					actions: ["Close"],
					modal: true,
					resizable: false,
					content: "",
		          height: "450px",
		          title: "Usuarios",
					width: "800px"
				  }).data("kendoWindow");	
				
				windowAddUser = $("#windowAddUser").kendoWindow({
					actions: ["Close"],
					modal: true,
					resizable: false,
					content: contexPath+"/AddNuevoUsuario.htm",
		          height: "450px",
		          title: "Usuarios",
					width: "800px"
				  }).data("kendoWindow");	

				windowAddUser.open();
				windowAddUser.refresh();
				windowAddUser.center();
				
			}
			else
			{
				window.parent.$("#mensajes_main").html("Seleccione un usuario de la lista");	
			}
						
		}
		
		
	}
	
	/*function setDataPermisos(listaPermisos)
	{
		$("#dg_permisos").empty();
		var dataSourcePermisos = new kendo.data.DataSource({
		    data: listaPermisos,	    	        
		    pageSize: 5
		});

	  	$("#dg_permisos").kendoGrid({
	  		dataSource: dataSourcePermisos, 
	  		columns: [                            
	                    {
	                        field: "id_permiso",
	                        width: 60,
	                        title: "Permiso"
	                    },
	                    {
	                        field: "id_permisox",
	                        title: "Descripcion"
	                    }
	                ],
	
				change: onChangePermisos,
				height: 200,
				selectable: "row",
				filterable: true,
				sortable: true,				  
	            pageable: true
		});
	}*/
	
	function setDataPermisosAdd(listaPermisos)
	{
		dataPermisosAdd=listaPermisos;
		$("#dg_permisos_add").empty();
		var dataSourcePermisos = new kendo.data.DataSource({
		    data: listaPermisos,	    	        
		    pageSize: 5
		});
		
		$("#dg_permisos_add").empty();
	  	$("#dg_permisos_add").kendoGrid({
	  		dataSource: dataSourcePermisos, 
	  		columns: [                            
	                    {
	                        field: "id_permiso",
	                        width: 70,
	                        title: "Permiso"
	                    },
	                    {
	                        field: "id_permisox",
	                        title: "Descripcion"
	                    }
	                ],
	
				change: onChangePermisos,
				height: 200,
				selectable: "row",
				filterable: true,
				sortable: true,				  
	            pageable: true
		});
	}
	
	/*function setDataUserPermisos(listaPermisos)
	{
		$("#dg_usr_perm").empty();	
		
		var dataSourceUserPermisos = new kendo.data.DataSource({
	        data: permisos_user,	    	        
	        pageSize: 5
	    });
      
      $("#dg_usr_perm").kendoGrid({
  		  dataSource: dataSourceUserPermisos,
  		  columns: [                            
                      {
                          field: "id_permiso",
                          width: 60,
                          title: "Permiso"
                      },
                      {
                          field: "id_permisox",
                          title: "Descripcion"
                      },
                  ],

			  change: onChangeUsrPerm,
			  height: 200,
			  selectable: "row",
			  filterable: true,
			  sortable: true,				  
            pageable: true
      });
	}*/
	
	function setDataUserPermisosAdd(listaPermisos)
	{
		$("#dg_usr_perm_add").empty();	
		
		var dataSourceUserPermisos = new kendo.data.DataSource({
	        data: listaPermisos,	    	        
	        pageSize: 5
	    });
      
      $("#dg_usr_perm_add").kendoGrid({
  		  dataSource: dataSourceUserPermisos,
  		  columns: [                            
                      {
                          field: "id_permiso",
                          width: 70,
                          title: "Permiso"
                      },
                      {
                          field: "id_permisox",
                          title: "Descripcion"
                      },
                  ],

			  change: onChangeUsrPerm,
			  height: 200,
			  selectable: "row",
			  filterable: true,
			  sortable: true,				  
            pageable: true
      });
	}
	
	function agregaPermisoAdd(allItems)
	{
		var dgup = $("#dg_usr_perm_add").data("kendoGrid");
		var dgper = $("#dg_permisos_add").data("kendoGrid");
		
		var perm=window.parent.dataPermisosAdd;
		var permu=window.parent.permisos_user_add;
		
		if(allItems=="X")
		{			
			for(var i=0; i<window.parent.dataPermisosAdd.length; i++)
	        {
				window.parent.permisos_user_add.push(window.parent.dataPermisosAdd[i]);
	        }
			
			dgup.dataSource.data([]);
			dgper.dataSource.data([]);

			window.parent.dataPermisosAdd=[];
			dgup.dataSource.data(window.parent.permisos_user_add);
		}
		else
		{
			if(perm_left==1)
			{
				var permu_=[];
				
				dgper.select().each(function() {
				    var dataItem = dgper.dataItem($(this));				    
				    permu.push({id_permiso: dataItem.id_permiso, id_permisox: dataItem.id_permisox});		       
				});
				
				dgper.select().each(function() {
				    var dataItem = dgper.dataItem($(this));
				    for(var i=0; i<perm.length; i++)
			        {			
						if(perm[i].id_permiso==dataItem.id_permiso)
						{
							perm.splice(i, 1);
						}			
			        }
				});
				
				//if(perm.length>=1)
				//{
					dgup.dataSource.data([]);		
					dgper.dataSource.data([]);
					
					dgup.dataSource.data(permu);
					dgper.dataSource.data(perm);
					window.parent.dataPermisosAdd=perm;
					window.parent.permisos_user_add=permu;
				//}
			}
		}
		perm_left=0;		
	}
	
	function quitarPermisoAdd(allItems)
	{
		var dgup = $("#dg_usr_perm_add").data("kendoGrid");
		var dgper = $("#dg_permisos_add").data("kendoGrid");
		
		var perm=window.parent.dataPermisosAdd;
		var permu=window.parent.permisos_user_add;
		
		if(allItems=="X")
		{		
			for(var i=0; i<window.parent.permisos_user_add.length; i++)
	        {
				window.parent.dataPermisosAdd.push(window.parent.permisos_user_add[i]);
	        }
			
			dgup.dataSource.data([]);
			dgper.dataSource.data([]);

			window.parent.permisos_user_add=[];
			dgper.dataSource.data(window.parent.dataPermisosAdd);			
		}
		else
		{
			if(perm_rigth==1)
			{			
				dgup.select().each(function() {
				    var dataItem = dgup.dataItem($(this));				    
				    perm.push({id_permiso: dataItem.id_permiso, id_permisox: dataItem.id_permisox});		       
				});
				
				dgup.select().each(function() {
				    var dataItem = dgup.dataItem($(this));
				    for(var i=0; i<permu.length; i++)
			        {			
						if(permu[i].id_permiso==dataItem.id_permiso)
						{
							permu.splice(i, 1);
						}			
			        }
				});	   
					
				dgup.dataSource.data([]);		
				dgper.dataSource.data([]);
				
				dgup.dataSource.data(permu);
				dgper.dataSource.data(perm);
				
				window.parent.dataPermisosAdd=perm;
				window.parent.permisos_user_add=permu;
				
				//setDataUserPermisosAdd(window.parent.permisos_user_add);
			}
		}
		perm_rigth=0;		
	}
	
	function validaFormAdminUsuario(accion_from)
	{
		var usuario = $.trim($('#txt_user').val());		
		var nombre1 = $.trim($('#txt_nom').val());
		var nombre2 = $.trim($('#txt_snom').val());
		var app_pat = $.trim($('#txt_apa').val());
		var app_mat = $.trim($('#txt_ama').val());	
		var tel = $.trim($('#txt_tel').val());
		var ext = $.trim($('#txt_ext').val());
		var correo = $.trim($('#txt_correo').val());

		var resp=false;
		
		if(accion_from=="update")
		{		
			var dg_usr_perm_= $("#dg_usr_perm_add").data("kendoGrid");
			var permisos_ctes_ = dg_usr_perm_.dataSource.data();
			var passwd = $.trim($('#txt_pass').val());
			
			if(nombre1=="")
			{
				$("#mensajes_main").html("Debe de introducir un nombre");
			}			
			else if(app_pat=="")
			{
				$("#mensajes_main").html("Debe introducir el apellido paterno");
			}
			else if(app_mat=="")
			{
				$("#mensajes_main").html("Debe introducir el apellido materno");
			}
			else if(usuario=="")
			{
				$("#mensajes_main").html("Debe de introducir un usuario");
			}
			else if(passwd=="")
			{
				$("#mensajes_main").html("Debe introducir un password");
			}
			else if(tel=="")
			{
				$("#mensajes_main").html("Debe introducir un telefono");
			}	
			else if(correo=="")
			{
				$("#mensajes_main").html("Debe introducir cuenta de correo valida");
			}
			else if(permisos_ctes_.length <=0)
			{
				$("#mensajes_main").html("Debe asignar permisos al usuario");
			}
			else				
			{
				resp=true;
			}
		}
		else if(accion_from=="add")
		{
			var dg_usr_perm_add_= $("#dg_usr_perm_add").data("kendoGrid");
			var permisos_ctes_add_ = dg_usr_perm_add_.dataSource.data();
			var passwd = $.trim($('#txt_pass').val());
			
			if(nombre1=="")
			{
				$("#mensajes_main").html("Debe de introducir un nombre");
			}			
			else if(app_pat=="")
			{
				$("#mensajes_main").html("Debe introducir el apellido paterno");
			}
			else if(app_mat=="")
			{
				$("#mensajes_main").html("Debe introducir el apellido materno");
			}
			else if(usuario=="")
			{
				$("#mensajes_main").html("Debe de introducir un usuario");
			}
			else if(passwd=="")
			{
				$("#mensajes_main").html("Debe introducir un password");
			}
			else if(tel=="")
			{
				$("#mensajes_main").html("Debe introducir un telefono");
			}	
			else if(correo=="")
			{
				$("#mensajes_main").html("Debe introducir cuenta de correo valida");
			}
			else if(permisos_ctes_add_.length <=0)
			{
				$("#mensajes_main").html("Debe asignar permisos al usuario");
			}			
			else
			{
				resp=true;
			}
		}
		return resp;
	}
	
	function validaEliminaUser(accion)
	{
		var gridUsuarios = $("#dg_usuarios").data("kendoGrid");  
		
		if(gridUsuarios.dataItem(gridUsuarios.tbody.find("tr.k-state-selected"))!=undefined)
		{
			 dataItemUsers= gridUsuarios.dataItem(gridUsuarios.tbody.find("tr.k-state-selected"));
				
			    var usuario = dataItemUsers.usuario;
				accion_=accion;
				datos_row_=usuario;
				
				/*winDecideEliminaUser = $("#winOptionDelete").kendoWindow({
					actions: ["Close"],
					modal: true,
					resizable: false,
		          height: "200px",
		          title: "Confirmacion",
					width: "300px"
				  }).data("kendoWindow");	
				
				winDecideEliminaUser.data("kendoWindow").open();
				winDecideEliminaUser.data("kendoWindow").center();
				*/
				
				$("#winOptionDelete").data("kendoWindow").open();
				$("#winOptionDelete").data("kendoWindow").center();
		}
		else
		{
		    window.parent.$("#mensajes_main").html("Seleccione un usuario de la lista");
		}
	   

		var onClose = function() {
			
		}
	}

	function setEliminaUsuario()
	{
		adminUsuarios(accion_,datos_row_);		
		$("#winOptionDelete").data("kendoWindow").close(); 
		accion_="";
		datos_row_="";
	}
	
	function closeConfirmaEliminaUser()
	{
		$("#winOptionDelete").data("kendoWindow").close();
	}
	
	function closeWindowUser()
	{
		$("#windowAddUser").data("kendoWindow").close();
	}
	
	function closeSesion()
	{
		window.parent.salirSistema();
	}
	
	function asignaPermisosUsers()
	{
		var perm_=window.parent.dataPermisosAdd;
		var perm=window.parent.dataPermisosAdd;
		var permu=window.parent.permisos_user_add;
		var permu_=[];
		var fields_perm="";
		var tabla_perm_d="";
		for(var i=0; i<perm.length; i++)
		{
			var encontrado=false;			
						
			for(var j=0; j<permu.length; j++)
			{
				if(perm[i].id_permiso==permu[j].id_permiso)
				{
					encontrado=true;									
				}
			}
			fields_perm+="<tr>";
			if(!encontrado)
			{
				fields_perm+='<td width="50px"><input type="checkbox" onclick="setPermisos(this)" /></td>';
				fields_perm+='<td width="90px">'+perm[i].id_permiso+'</td>';
				fields_perm+='<td >'+perm[i].id_permisox+'</td>';
			}
			else
			{
				fields_perm+='<td width="50px"><input type="checkbox" onclick="setPermisos(this)" checked="checked" /></td>';
				fields_perm+='<td width="90px">'+perm[i].id_permiso+'</td>';
				fields_perm+='<td >'+perm[i].id_permisox+'</td>';
			}
			fields_perm+="</tr>";			
		}
		
		$("#dg_usr_perm_add tbody").html("");
		$("#dg_usr_perm_add tbody").html(fields_perm);
		
		window.parent.dataPermisosAdd=permu_;		
	}
			