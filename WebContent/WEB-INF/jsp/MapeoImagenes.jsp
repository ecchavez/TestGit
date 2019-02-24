<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%> 
<%@ page session="true" %> 
		
		<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
        <script src="<%=basePath %>/js/jMyCarousel.min.js"></script>		
		<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>		
        <script src="<%=basePath %>/kendo/js/console.js"></script>
        
        <script type="text/javascript">
            var contexPath="<%=basePath %>";
        </script>
        <script src="<%=basePath %>/js/mapeo/MapeoJQ.js"></script>
        <script>
		  var imagenesSelec = [];
		  $(document).ready(function() {
		  		$(".jMyCarousel").jMyCarousel({ // Script de los Thumbnails
					    visible: '40%',
					    eltByElt: true,
						circular: false					
			    });
			    
			    $(".jMyCarousel1").jMyCarousel({ // Script de los Thumbnails
					    visible: '40%',
					    eltByElt: true,
						circular: false
			    });
			    									  
			  $("#tabstrip").kendoTabStrip({
				    animation:	{
						open: {
						effects: "fadeIn"
						}
					},
					select: onSelectTabImages
					
				});						    
					
				$(".jMyCarousel img").fadeTo(100, 0.6);
				$(".jMyCarousel a img").hover(
				function(){ //mouse over
				   $(this).fadeTo(400, 1);
				},
				function(){ //mouse out
				  $(this).fadeTo(400, 0.6);
				});
				
				$(".jMyCarousel1 img").fadeTo(100, 0.6);
				$(".jMyCarousel1 a img").hover(
				function(){ //mouse over
				   $(this).fadeTo(400, 1);
				},
				function(){ //mouse out
				  $(this).fadeTo(400, 0.6);
				});
				
				
				/* $("#dg_imagenes").kendoGrid({
                        dataSource: {
							data: imagenesSelec,
                            pageSize: 10
                        },
                        sortable: true,
                        pageable: {
                            refresh: true,
                            pageSizes: true
                        },
                        columns: [ {
                                field: "id_ubicacion",
                                title: "Unidad"
                            } , {
                                field: "nombre_img",
                                title: "Imagen"
                            } 
                        ]
                    }); */

		  });
		  
		  function validaForma()
		  {
		      var resp=true;
		       
			  if($("#accion").val()=="newimagen")
			  {
			  	  if($("#fileData").val()=="")
			  	  {
			  	  	  resp=false;
			  	  	$("#mensajes_main").html("Seleccione una imagen para subir");
			  	  }
			  }
			  else if($("#accion").val()=="upimagen")
			  {
			  	  if($("#id_ut").val()=="")
			  	  {
			  	  	resp=false;
			  	  	$("#mensajes_main").html("Seleccione una imagen para actualizar");
			  	  }
	  			  else if($("#img_nom").val()=="")
	  			  {
	  			  	resp=false;
	  			  $("#mensajes_main").html("Seleccione una imagen para actualizar");
	  			  }
			  }
			  
			 return resp;
		  }
		  		  
        </script>
        <script type="text/javascript">
			  $(document).ready(function(){ // Script de la Galeria
			    //$('#contenido_galeria div').css('position', 'absolute').not(':first').hide();
			    //$('#contenido_galeria div:first').addClass('aqui');
			    $('.jMyCarousel a').click(function(){
			        // $('#contenido_galeria div.aqui').fadeOut(400);
			        // $('#contenido_galeria div').removeClass('aqui').filter(this.hash).fadeIn(400).addClass('aqui');
			        return false;
			    });
			 });
		</script> 
        <style type="text/css">
	
			div.jMyCarousel .prev   { background: url('<%= basePath %>/images/maping/left.png') center center no-repeat; width:47px; height:61px; border:0px; }
			div.jMyCarousel .next  { background: url('<%= basePath %>/images/maping/right.png') center center no-repeat; width:40px; height:61px; border:0px; }
			
			
			div.jMyCarousel{
				border:1px solid #1c6f8e;
			}
			
			div.jMyCarousel ul{
				
			}
			
			div.jMyCarousel ul li{
				margin:0px;
				border:1px solid #ccc;
				line-height:0px;
				padding:0px;
			}
			
			div.jMyCarousel ul li a{ /* in case of link */
				display:block;
			}
			
			div.jMyCarousel ul li img{
				display:block;
				border:0px;
			}
			
			
			div.jMyCarousel1 .prev   { background: url('<%=basePath %>/images/maping/left.png') center center no-repeat; width:47px; height:61px; border:0px; }
			div.jMyCarousel1 .next  { background: url('<%=basePath %>/images/maping/right.png') center center no-repeat; width:40px; height:61px; border:0px; }
			
			
			div.jMyCarousel1{
				border:1px solid #1c6f8e;
			}
			
			div.jMyCarousel1 ul{
				
			}
			
			div.jMyCarousel1 ul li{
				margin:0px;
				border:1px solid #ccc;
				line-height:0px;
				padding:0px;
			}
			
			div.jMyCarousel1 ul li a{ /* in case of link */
				display:block;
			}
			
			div.jMyCarousel1 ul li img{
				display:block;
				border:0px;
			}

		</style>

	<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />
		
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Upload de archivos</title>
</head>
<body>

<form method="post" enctype="multipart/form-data" onsubmit="return validaForma()"> 

             <div id="tabstrip" style="width:auto">
                <ul>
                    <li class="k-state-active" id="newimagen">
                        Subir nueva imagen
                    </li>
                    <li id="upimagen">
                        Seleccionar imagen existente
                    </li>
                    
                </ul>
                <div align="center">
				<br />
                <center>
                Paso 1 seleccione una imagen para subir
                </center>
                <table width="387" border="0" align="center" cellpadding="0" cellspacing="0">
				          <tr>
				            <td height="19" colspan="2" align="center">&nbsp;</td>
			              </tr>
				          <tr>
				            <td width="154">&nbsp;</td>
				            <td width="233">&nbsp;</td>
			              </tr>
				          <tr>
				            <td align="center">Seleccione la imagen</td>
				            <td align="center"><input name="fileData" id="fileData" type="file" class="k-button"/></td>
			              </tr>
				          <tr>
				            <td colspan="2" align="center">&nbsp;</td>
			              </tr>
			            </table>
            
                <center>
                Esta imagen se reemplazara por la imagen nueva que usted seleccione
                </center>
                <c:if test="${fn:length(respListadoImagenesAction) > 0}">              
				<% int numPage11 = 0;  %>
					<div class="jMyCarousel1">							
					    <ul>
						    <c:forEach var="imagenes1" items="${respListadoImagenesAction}" varStatus="status">
						    <% numPage11 ++;  %>
						        <li><table width="228" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="2"><a href="#imagen<%= numPage11 %>" title="<c:out value="${imagenes1.idUbicacion}"/>"><img src="<%=basePath %>/images/maping/<c:out value="${imagenes1.nombreImagen}"/>" alt="" width="230" height="180"/></a></td>
    </tr>
  <tr>
    <td width="32" height="24" align="center"></td>
    <td width="198" align="center"><c:out value="${imagenes1.idUbicacion}"/></td>
    </tr>
                                </table>
</li>
						        
						    </c:forEach>
					    </ul>							 
				    </div><!-- Fin div jMyCarousel -->
                    </c:if>
				    <table width="511" border="0" cellpadding="0" cellspacing="0">
				      <tr>
				        <td width="511"><table width="438" border="0" align="center" cellpadding="0" cellspacing="0">
				          <tr>
				            <td width="438" align="center"></td>
			              </tr>
			            </table></td>
			          </tr>
				      <tr>
				        <td align="center">&nbsp;</td>
			          </tr>
				      <tr>
				        <td align="center"><input name="Enviar" type="submit" class="k-button" value="Guardar"/></td>
			          </tr>
		          </table>
			       <br/>
                </div>
                <div align="center">
                <br />
                <br />
                <c:if test="${fn:length(respListadoImagenesAction) > 0}"> 
                <center>Seleccione la imagen que representara la nueva unidad tecnica</center>
                <br />                
				<% int numPage1 = 0;  %>
					<div class="jMyCarousel">							
					    <ul>
						    <c:forEach var="imagenes" items="${respListadoImagenesAction}" varStatus="status">
						    <% numPage1 ++;  %>
						        <li><table width="218" border="0" cellpadding="0" cellspacing="0">
									  <tr>
									    <td width="218"><a href="#imagen<%= numPage1 %>" title="<c:out value="${imagenes.idUbicacion}"/>"><img src="<%=basePath %>/images/maping/<c:out value="${imagenes.nombreImagen}"/>" onclick="selecciona_imagen('<c:out value="${imagenes.nombreImagen}"/>','<c:out value="${imagenes.idUbicacion}"/>')" alt="" width="230" height="180"/></a></td>
									    </tr>
									  <tr>
									    <td height="24" align="center"><c:out value="${imagenes.idUbicacion}"/></td>
									    </tr>
	                                </table>
								</li>
						        
						    </c:forEach>
					    </ul>							 
				    </div><!-- Fin div jMyCarousel -->
				    
				    <table width="356" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td align="center">&nbsp;</td>
                        <td align="center">&nbsp;</td>
                        </tr>
                      <tr>
                        <td align="center">Unidad Tecnica</td>
                        <td align="center">Imagen</td>
                        </tr>
                      <tr>
                        <td width="130" align="center">&nbsp;</td>
                        <td width="128" align="center">&nbsp;</td>
                      </tr>
                      <tr>
                        <td colspan="2" align="center"></td>
                      </tr>
                      <tr>
                        <td align="center"><input name="id_ut" type="text" class="k-textbox" id="id_ut" readonly="readonly"/></td>
                        <td align="center"><input name="img_nom" type="text" readonly="readonly" class="k-textbox" id="img_nom"/></td>
                      </tr>
                      <tr>
                        <td colspan="2" align="center">&nbsp;</td>
                      </tr>
                      <tr>
                        <td align="center"><input name="boton_envia" type="submit" id="boton_envia" value="Actualizar" class="k-button"/></td>
                        <td align="center"><input name="boton_limpiar" type="button" id="boton_limpiar" value="Limpiar" class="k-button" onclick="limpiar_actualizacion()"/></td>
                      </tr>
                      <tr>
                        <td colspan="2" align="center">&nbsp;</td>
                      </tr>
                  </table>
				<br />	
				</c:if>	
				<c:if test="${fn:length(respListadoImagenesAction) == 0}">
				      <center>No hay imagenes en esta unidad tecnica para seleccionar</center>
     			</c:if>		
				</div>                
           </div>
           <input type="hidden" id="accion" name="accion" value="newimagen"/>	
           <input type="hidden" id="idUbicacion" name="idUbicacion" value="<%= request.getParameter("idUbicacion") %>"/>
           <input type="hidden" id="idUTS" name="idUTS" value="<%= request.getParameter("idUTS") %>"/>
           
</form> 
  <div id="buttomBar">
  	<div class="mensajes_main"/>
  </div>
</body> 
</html>	 