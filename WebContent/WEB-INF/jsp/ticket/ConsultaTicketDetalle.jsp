<%@page import="mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
UsuarioDto user = (UsuarioDto) request.getSession().getAttribute("usrSession");
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 

response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Registro Tickets</title>
    
    <script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
    <script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
    <script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js"></script>   

    <script type="text/javascript">
            var contexPath="<%=basePath %>";
     </script> 
     <script src="<%=basePath %>/js/escritorio/EscritorioJQ.js"></script>      
     <script src="<%=basePath %>/js/tickets/RegistroTickets.js"></script>      
     <script src="<%=basePath %>/js/utils.js"></script>      
    <script>
	var open_menu=0;
    
    $(document).ready(function() {
	    $("#mensajes_main").html('<%=request.getParameter("paramMensaje")==null?"":request.getParameter("paramMensaje")%>');
		$("#sample-bar").jixedbar({    
			showOnTop: true,    
			transparent: true,    
			opacity: 0.5,    
			slideSpeed: "slow",    
			roundedCorners: false,    
			roundedButtons: false,    
			menuFadeSpeed: "slow",    
			tooltipFadeSpeed: "fast",    
			tooltipFadeOpacity: 0.5
		});

		cargaValoresDetalle();		
    });
					
		function valida_openclose()
		{
			if(open_menu==0)
			{
				open_menu=1;
				var cssObj = {      
				'left':'0px'    
				}    
				$("#my-scrollable-div").css(cssObj);
			}
			else
			{
				open_menu=0;
				var cssObj = {      
				'left':'-200px'    
				}    
				$("#my-scrollable-div").css(cssObj);
			}
				
		}
</script>
    <link href="<%=basePath %>/jixedbar/default/jx.stylesheet.css" type="text/css"  rel="stylesheet" />
	<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />
     

</head>

<body topmargin="0">
        	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            	<tr>
                  <td>&nbsp;</td>
                </tr>
                <tr>
                  <td valign="top">
                  	<div id="area_trabajo">
                  		<div id="registroTicket">
                  			<form id="registroTicketForm" name="registroTicketForm" action="" method="post">
                  				<table border="0">
									<tbody>
										<tr valign="top">
											<td align="right"># Ticket:</td>
											<td colspan="5" valign="bottom">
												<div id="idTicket"></div>&nbsp;
											</td>
										</tr>
										<tr valign="top">
											<td align="right">* &Aacute;rea:</td>
											<td>
												<div id="idAreaTicket"></div>&nbsp;
											</td>
											<td>
												<div id="nomAreaTicket"></div>&nbsp;									
											</td>
											<td align="right">* Ut. Sup:</td>
											<td>
												<div id="utSuperiorTicket"></div>&nbsp;
											</td>
											<td>
												<div id="txt_uts_desc">${sessionScope.usrSession.id_ut_sup_cm }</div>&nbsp;
											</td>
										</tr>
										<tr valign="top">
											<td align="right">* Cliente:</td>
											<td>
												<div id="claveClienteTicket"></div>&nbsp;									
											</td>
											<td>
												<div id="nomClienteTicket"></div>&nbsp;									
											</td>
											<td align="right">Fase:</td>
											<td>
												<div id="faseTicket"></div>&nbsp;
											</td>
											<td>
												<div id="txt_desc_fase"></div>&nbsp;
											</td>
										</tr>
										<tr valign="top">
											<td align="right">* Vendedor:</td>
											<td>
												<div id="claveVendedorTicket"></div>&nbsp;									
											</td>
											<td>
												<div id="nomVendedorTicket"></div>&nbsp;									
											</td>
											<td align="right">Equipo:</td>
											<td>
												<div id="equipoTicket"></div>&nbsp;
											</td>
											<td>
												<div id="txt_desc_equ"></div>&nbsp;
											</td>
										</tr>
										<tr valign="top">
											<td align="right" colspan="2">&nbsp;</td>
											<td align="right">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
											<td align="right" colspan="3">&nbsp;</td>
										</tr>
										<tr valign="top">
											<td align="right">* Asunto:</td>
											<td colspan="5">
												<div id="asuntoTicket"></div>&nbsp;
											</td>
										</tr>
										<tr valign="top">
											<td align="right">* Descripci&oacute;n<br> del ticket:</td>
											<td colspan="5">
												<textarea id="descripcionTicket" readonly="readonly" name="descripcionTicket" style="text-transform: uppercase" onblur="javascript:this.value=this.value.toUpperCase();" rows="10" cols="82" onkeypress="return letrasnumeros(event)"></textarea>
											</td>
										</tr>
										<tr valign="top">
											<td>
												&nbsp;
											</td>
											<td colspan="5">
												<input type="button" class="k-button" onclick="cancelVerDetalleTicket()" value="Cancelar"/>
											</td>
										</tr>
									</tbody>
								</table>
                  			</form>
                  		</div> 
                  	</div> 
                  </td>
				</tr>
          	</table>
<div id="windowGridBusquedaClienteRegistroTicket"></div>

</body>
</html>