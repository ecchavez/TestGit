<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Nuevo Cliente</title>

<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/js/jquery.colorPicker.js"></script>
<script src="<%=basePath %>/js/mapeo/MapeoJQ.js"></script>
<link href="<%=basePath %>/kendo/styles/colorPicker.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=basePath %>/kendo/styles/imgmap.css" type="text/css">

<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script>
		$(document).ready(function(){	    
		    javascript:gui_loadImage('<%=basePath %>/images/maping/<%= request.getParameter("nom_imagen")%>');
		});	
</script>

</head>
  <form id="img_area_form">
		<fieldset>
			<legend>
				<a onclick="toggleFieldset(this.parentNode.parentNode)">Coordenadas que se guardaran para esta imagen</a>
			</legend >
			<div>
             Copiar coordenadas en esta casilla
             <textarea name="coordenadas" cols="40" rows="2" id="coordenadas"></textarea>
			  <input type="button" name="button" id="button" value="Guardar Coordenadas" onClick="setCoordenadasImagen()">
				<!--<div class="source_desc">An image on your computer:</div>
				<div class="source_url">
					<iframe id="iframe_uploader"
						name="uploader"
						src="index.jsp"
						scrolling="no"
						noresize="noresize"
						frameborder="no"
						width="250"
						height="120"></iframe>
				</div> 
				<a href="javascript:gui_loadImage(window.frames['uploader'].document.getElementById('src').getAttribute('rel'))" class="source_accept">accept</a>
				-->
				<!--<div class="source_desc">An image on the Internet:</div>
				<div class="source_url"><input type="text" id="source_url2" disabled value=""></div>
				<a href="javascript:gui_loadImage(document.getElementById('source_url2').value)" class="source_accept">accept</a> -->
				
				<!--<div class="source_desc">Use a sample image:</div>
				<div class="source_url">
					<select id="source_url3">
						<option value="example_files/sample1.jpg" >sample 1</option>
						<option value="example_files/sample2.jpg" >sample 2</option>
						<option value="example_files/sample3.jpg" >sample 3</option>
						<option value="example_files/sample4.jpg" >sample 4</option>
					</select>
				</div>
				<a href="javascript:gui_loadImage(document.getElementById('source_url3').value);" class="source_accept">accept</a> -->
			</div>
		</fieldset>
		<fieldset>
			<legend>
				<a onclick="toggleFieldset(this.parentNode.parentNode)">Image map areas</a>
			</legend>
			<div style="border-bottom: solid 1px #efefef">
			<div id="button_container">
				<!-- buttons come here -->
			  <img src="<%=basePath %>/images/mapcomponent/add.gif" onclick="myimgmap.addNewArea()" alt="Add new area" title="Add new area"/>
			  <img src="<%=basePath %>/images/mapcomponent/delete.gif" onclick="myimgmap.removeArea(myimgmap.currentid)" alt="Delete selected area" title="Delete selected area"/>
			  <img src="<%=basePath %>/images/mapcomponent/zoom.gif" id="i_preview" onclick="myimgmap.togglePreview();" alt="Preview image map" title="Preview image map"/>
			  <img src="<%=basePath %>/images/mapcomponent/html.gif" onclick="gui_htmlShow()" alt="Get image map HTML" title="Get image map HTML"/>
				<label for="dd_zoom">Zoom:</label>
				<select onchange="gui_zoom(this)" id="dd_zoom">
				<option value='0.25'>25%</option>
				<option value='0.5'>50%</option>
				<option value='1' selected="1">100%</option>
				<option value='2'>200%</option>
				<option value='3'>300%</option>
				</select>
				<label for="dd_output">Output:</label> 
			  <select id="dd_output" onchange="return gui_outputChanged(this)">
				<option value='imagemap'>Standard imagemap</option>
				<!-- option value='css'>CSS imagemap</option -->
				<!-- option value='wiki'>Wiki imagemap</option -->
				</select>                
				<div>
					<a class="toggler toggler_off" onclick="gui_toggleMore();return false;">More actions</a>
					<div id="more_actions" style="display: none; position: absolute;">
						<div><a href="" onclick="toggleBoundingBox(this); return false;">&nbsp; bounding box</a></div>
						<div><a href="" onclick="return false">&nbsp; background color </a><input onchange="gui_colorChanged(this)" id="color1" style="display: none;" value="#ffffff"></div>
				  </div>
			  </div>
			</div>
			<div style="float: right; margin: 0 5px">
				<select onchange="changelabeling(this)">
				<option value=''>No labeling</option>
				<option value='%n' selected='1'>Label with numbers</option>
				<option value='%a'>Label with alt text</option>
				<!-- option value='%h'>Label with href</option-->
				<!-- option value='%c'>Label with coords</option-->
				</select>
			</div>
			</div>
			<div id="form_container" style="clear: both;">
			<!-- form elements come here -->
         	</div>
		</fieldset>
		<fieldset>
			<legend>
				<a onclick="toggleFieldset(this.parentNode.parentNode)">Image</a>
			</legend>
			<div id="pic_container">
			</div>			
		</fieldset>
		<fieldset>
			<legend>
				<a onclick="toggleFieldset(this.parentNode.parentNode)">Status</a>
			</legend>
			<div id="status_container"></div>
		</fieldset>
		<fieldset id="fieldset_html" class="fieldset_off">
			<legend>
				<a onclick="toggleFieldset(this.parentNode.parentNode)">Code</a>
			</legend>
			<div>
			<div id="output_help">
			</div>
			<textarea id="html_container"></textarea></div>
		</fieldset>
	</form>
    <script type="text/javascript" src="<%=basePath %>/js/imgmap.js"></script>
	<script type="text/javascript" src="<%=basePath %>/js/default_interface.js"></script>
    <input id="idUbicacion" name="idUbicacion" value="<%=request.getParameter("idUbicacion") %>" type="hidden"/>
</html>
