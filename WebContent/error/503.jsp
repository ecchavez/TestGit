<html>
<head>
<title>Excepci&#243;n</title>

<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>

    <script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
    <script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
    <script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js"></script>   


    <link href="<%=basePath %>/jixedbar/default/jx.stylesheet.css" type="text/css"  rel="stylesheet" />
	<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />
    <style type="text/css">
    .textorojo {
	color: #F00;
}

html {
    font: 10px "Century Gothic";
}
    a:link {
	color: #F00;
}
a:visited {
	color: #F00;
}
a:hover {
	color: #F00;
}
a:active {
	color: #F00;
}

#TituloMain{
color: #fff;
font-family: "Century Gothic";
font-size: 13px;
font-weight: bold;
}
    </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body topmargin="0">
<% 
	System.out.println("Pagina 503.jsp ejecutada"); 
	//System.out.println("SESION INVALIDADA"); 
	//session.invalidate(); 
%>
<table width="1180" height="350" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr>
    <td width="1180" height="102" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="0"><input type="image" name="imageField5" id="imageField5" src="<%=basePath %>/images/header_left.png" /></td>
        <td width="211" background="<%=basePath %>/images/header_logo.png"><table border="0" cellspacing="0" cellpadding="0" width="115">
          <tr>
            <td colspan="2"></td>
            </tr>
          <tr>
            <td width="1" align="center">&nbsp;</td>
            <td width="116" align="center"><div id="slimg"> <img src="<%=basePath %>/images/login_logoggi.png"/></div></td>
          </tr>
          <tr>
            <td height="5" colspan="2"></td>
          </tr>
        </table></td>
        <td width="100%" background="<%=basePath %>/images/header_middle.png"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100%" height="31"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="141" height="25" id="TituloMain">Contratistas </td>
                <td width="424" id="TituloModulo">&nbsp;</td>
                <td width="276" id="TituloModulo"><table width="200" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr>
                        <td width="100%" align="center"></td>
                        <td width="0" align="center"></td>
                      </tr>
                    </table></td>
                  </tr>
                </table></td>
                <td width="79" id="TituloMain"></td>
                <td width="17" id="TituloModulo"></td>
                <td width="64" align="center">&nbsp;</td>
                </tr>
            </table></td>
            </tr>
          <tr>
            <td height="22">&nbsp;</td>
            </tr>
          <tr>
            <td height="32"><table width="95%" height="16" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="65">&nbsp;</td>
                <td width="68">&nbsp;</td>
                <td width="65">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="118">&nbsp;</td>
                <td width="127">&nbsp;</td>
                </tr>
            </table></td>
            </tr>
        </table></td>
        <td width="0"><input type="image" name="imageField6" id="imageField6" src="<%=basePath %>/images/header_right.png" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="400" valign="top"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="0" height="16"><input type="image" name="imageField" id="imageField" src="<%=basePath %>/images/main_corner4.png" /></td>
        <td width="1150" background="<%=basePath %>/images/main_corner4_comp.png"></td>
        <td width="0"><input type="image" name="imageField4" id="imageField4" src="<%=basePath %>/images/main_corner3.png" /></td>
      </tr>
      <tr>
        <td height="500" background="<%=basePath %>/images/main_corner2_comp.png"></td>
        <td background="<%=basePath %>/images/main_relleno.png" valign="top">
        <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td><p>&nbsp;</p>
                        <p class="textorojo"><strong>Servicio no disponible</strong></p><br>
                        <p>El servidor web actualmente no est&aacute; disponible para manejar la solicitud HTTP debido a una sobrecarga temporal o a un mantenimiento del servidor. <br>
                      Disculpe las molestias.</p>
                        <p>Favor de contactar al administrador.<br><br>
                      Usted puede:</p>
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#8250; <a href="javascript:window.history.back();">Volver</a> a la p&aacute;gina anterior e intentarlo de nuevo.<br>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#8250; Ir a la <a href="<%=basePath %>/Login.htm">p&aacute;gina de inicio</a>.<br>
                          <br>
                      </td>
                    </tr>
                    <tr>
                      <td height="200" valign="top"> </td>
              </tr>
          </table></td>
        <td background="<%=basePath %>/images/main_corner3_comp.png"></td>
      </tr>
      <tr>
        <td height="0"><input type="image" name="imageField2" id="imageField2" src="<%=basePath %>/images/main_corner1.png" /></td>
        <td background="<%=basePath %>/images/main_corner1_comp.png"></td>
        <td><input type="image" name="imageField3" id="imageField3" src="<%=basePath %>/images/main_corner2.png" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="19">&nbsp;</td>
  </tr>
</table>
<div id="my-scrollable-div" style="position:fixed; top:100px; left:-200px;">
  <div id="my-scrollable-div_int" style="position:absolute; top:-5.7px; left:200px;">

</div>
	<table border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>       
        </td>
      </tr>
    </table>
</div>

</body>
</html>