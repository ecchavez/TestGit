<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>
<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/jquery.maskedinput-1.3.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>


<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/clientes/ClientesJQ.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Documento sin titulo</title>
<script>
var fechaInicial;
var fechaFinal;

$(document).ready(function(){
    fechaInicial = $("#fechaInicial").kendoDatePicker({
    	change: startChange,
    	format: "dd/MM/yyyy"
    });
    
    fechaFinal = $("#fechaFinal").kendoDatePicker({
    	change: endChange,
    	format: "dd/MM/yyyy"
    });
    
	$("#pub_button").click(function(){
		userVendedor=[];
	    vendedores_seleccionado=[];
	    var window = $("#winListaUsuarios").data("kendoWindow");
	    window.center();
	    window.open();
	    getVendedoresForReportes();
    });
	
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
	  	height: 200,
	  	selectable : "row",
	  	filterable: true,
	  	sortable: true,				  
        pageable: true		  
	}); 
	
    var window = $("#winListaUsuarios"),
    undo = $("#pub_button")
            .bind("click", function() {
                window.data("kendoWindow").open();
                //undo.enable(false);                
            });

    var onClose = function() {
        //undo.enable();
    }

    if (!window.data("kendoWindow")) {
        window.kendoWindow({
             height: "300px",
		     title: "Vendedores",
		     visible: false,
		     modal: true,
		     width: "600px",
             close: onClose
        });
    }
	
    $("#dg_usuarios_v").kendoGrid({
    	dataSource: {
                      data: []
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
	
	
	$("#dg_vendedores").kendoGrid({
	    	dataSource: {
                            data: []
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
	  	height: 500,
	  	selectable : "row",
	  	sortable: true		  
	}); 
	
	//$("#fechaInicial").mask("99/99/9999");
	//$("#fechaFinal").mask("99/99/9999");
});

function closeWinVendedores()
{
	var window = $("#winListaUsuarios").data("kendoWindow");
	window.close();
}

</script>

<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />

</head>

<body>
<div class="demo-section" style="width: 90%;"></div>
  <table width="721" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td colspan="2"><input type="button" name="pub_button" id="pub_button" value="->"></td>
      </tr>
    <tr>
      <td colspan="2"><div id="dg_vendedores" style="width: 800px"></div>
      </td>
      </tr>
    <tr>
      <td width="193">&nbsp;</td>
      <td width="207">&nbsp;</td>
    </tr>
  </table>
  <div id="winListaUsuarios"> 
     <table width="406" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td colspan="2">Filtro de reporte de vendedores</td>
        </tr>
        <tr>
          <td width="118">Fecha Inicial</td>
          <td width="272"><input id="fechaInicial"/></td>
        </tr>
        <tr>
          <td>Fecha Final</td>
          <td><input id="fechaFinal" /></td>
        </tr>
        <tr>
          <td height="150">Vendedores</td>
          <td><div id="dg_usuarios_v"></div></td>
        </tr>
        <tr>
          <td><input type="button" name="button2" id="button2" value="Aceptar" class="k-button" onclick="validaFormaReporte()"></td>
          <td>&nbsp;</td>
        </tr>
      </table>
</div>

<div id="alert"/>

<div id="buttomBar">
  	<div class="console"/>
</div>
</body>
</html>