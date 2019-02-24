//funcion que carga el motor de busqueda de Clientes para el matchcode
function cargaBusqueda()
{
	 var dataSourceCliSearch = new kendo.data.DataSource({
         batch: true,
         pageSize: 30,
         schema: {
             model: {
               
               fields: {
		 		  idCli: { validation: { required: false } },	
                  nombre1: { validation: { required: false } },
                  nombre2: { validation: { required: false } },
                  apellidoPaterno: { validation: { required: false } },
                  apellidoMaterno: { validation: { required: false } },
                }
             }
         }
      });

   $("#gridCliFind").kendoGrid({
       dataSource: dataSourceCliSearch,
       resizable: true,
       pageable: true,
       height:150,
       toolbar: ["create"],
       columns: [
           {  field:"idCli",title:"Id Cliente ",validation: { required: true } },      
           {  field:"nombre1",title:"Nombre ",validation: { required: true } },
           { field:"nombre2",title:"Segundo Nombre" },
           { field: "apellidoPaterno", title:"Apellido" },
           { field: "apellidoMaterno", title:"Segundo Apellido" },
           { command: "destroy", title: " ", width: "50px" }],
       editable: true
   });
}

function onSelectClienteFind(e)
{	
	e.preventDefault();
    var grid = $("#dg_clientesFind").data("kendoGrid");    
    dataItemCliente= grid.dataItem(grid.tbody.find("tr.k-state-selected"));
}

//funcion para concatenar los parametros para la busq en rfc43  
function buscarClientes() {
	// get the form values
		id_sel="";
		var gridCliFind = $("#gridCliFind").data("kendoGrid");
		var dataGridCliFind = gridCliFind.dataSource.data();
		if(dataGridCliFind.length>0)
		{
			var vacio=true;
			for(z=0;z<dataGridCliFind.length;z++)
			{
				if( (dataGridCliFind[z].idCli=="" && dataGridCliFind[z].nombre1=="" && dataGridCliFind[z].nombre2=="" && dataGridCliFind[z].apellidoPaterno=="" && dataGridCliFind[z].apellidoMaterno=="") && vacio!=false)
					vacio=true;
				else
					vacio=false;
			}
		 if( vacio==true){
			 kendoConsole.log("Capture Criterios de Busqueda","error");
	     }else{

	    	 var criteriosCliente= [];

	    		for(var i=0; i<dataGridCliFind.length; i++)
	    		{
	    			if(dataGridCliFind[i].idCli!="" || dataGridCliFind[i].nombre1!="" || dataGridCliFind[i].nombre2!="" || dataGridCliFind[i].apellidoPaterno!="" || dataGridCliFind[i].apellidoMaterno!=""){
	    				var params = {};
	    				params["idCli"] = dataGridCliFind[i].idCli;
	        			params["nombre1"] = dataGridCliFind[i].nombre1;
	        			params["nombre2"] = dataGridCliFind[i].nombre2;
	        			params["apellidoMaterno"] = dataGridCliFind[i].apellidoMaterno;
	        			params["apellidoPaterno"] = dataGridCliFind[i].apellidoPaterno;
	        			criteriosCliente.push(params);	
	    			}
	    			
	    		}
	    		   
	    		xmlCriteriosCliente = createXMLCriterios("criterios",criteriosCliente);
	    
	    $.ajax( {
			type : "POST",
			url : contexPath + "/MatchCliente.htm",
			data : "accion=1"
					+ "&xmlClientesFind=" + xmlCriteriosCliente,
			success : function(response) {
				// we have the response
			if (response.responseCotizacion.mensaje == "SUCCESS") {
				var clientesDataExport = response.responseCotizacion.objClientesList;
				var dataClientFind = [];
			
				for (i = 0; i < clientesDataExport.length; i++) {
					dataClientFind.push(clientesDataExport[i]);
				}
				
				var dataSourceClientFind = new kendo.data.DataSource( {
					data : dataClientFind,
					pageSize : 10
				});
				
				
				$("#dg_clientesFind").empty();
				$("#dg_clientesFind").kendoGrid( {
					 dataSource : dataSourceClientFind,
					 height:250,
					 selectable:"row",
					 sortable: true,
	                 resizable: true,
	                 pageable: true,
					 change: onSelectClienteFind,
	                 columns : [ 
	                 {
						field : "id_cliente_z",
						title : "Id Cliente Z",
						width: 100
					 },
					{
						field : "nombre1C",
						title : "Nombre",
						width: 100
					}, {
						field : "nombre2C",
						title : "Segundo Nombre",
						width: 100
					}, {
						field : "app_patC",
						title : "Apellido",
						width: 100
					}, {
						field : "app_matC",
						title : "Segundo Apellido",
						width: 100
					}]
					}).data("kendoGrid");
				
			} else {
				kendoConsole.log("Hubo un error en carga clientes Sap: "+response.responseCotizacion.descripcion ,"error");
			}
		},
		error : function(e) {
			kendoConsole.log(e,"error");
		  }
		});
		}//else de grid(0) != ""
	}//id de  $("#grid").empty
		else{
			kendoConsole.log("Agregue criterios de busqueda","error");
		}

}




