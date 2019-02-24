<%@page import="mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto"%>
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

<title>Untitled Document</title>

    <script type="text/javascript">
            var contexPath="<%=basePath %>";
     </script> 

<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js"></script>   

<script src="<%=basePath %>/js/tickets/RegistroTickets.js"></script> 

<script>
$(document).on('change','input[type="file"]',function(){
	// this.files[0].size recupera el tamaño del archivo
	// alert(this.files[0].size);
	
	var fileName = this.files[0].name;
	var fileSize = this.files[0].size;

	if(fileSize > 1000000){
		alert('La imagen adjunta no debe superar un megabyte de tamaño');
		this.value = '';
		this.files[0].name = '';
	}else{
		// recuperamos la extensión del archivo
		var ext = fileName.split('.').pop();

		// console.log(ext);
		switch (ext) {
			case 'jpg':
			case 'jpeg':break;
				default:
				alert('El archivo no tiene la extensión adecuada');
				this.value = ''; // reset del valor
				this.files[0].name = '';
		}
	}
});
</script>

</head>

<body>
<form id="cargaImagenesViciosOcultos" name="cargaImagenesViciosOcultos">
<p align="center">
  <input type="file" id="imagenViciosOcultos" name="imagenViciosOcultos">
</p>
<div align="center"><input type="button" class="k-button" onclick="registroImagenesViciosOcultos()" value="Aceptar"/></div>
</form>
</body>
</html>
