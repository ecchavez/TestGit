<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>
<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>
    <script src="source/kendo.all.js"></script>

<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" type="text/css"/>



<script>
    $(document).ready(function () {
		
    });
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
<table width="400" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="400" colspan="2">Estatus de procesamiento de datos</td>
  </tr>
  <tr>
    <td colspan="2"><table width="87%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
      <tr>
        <td width="38"><input type="image" name="img_cte" id="img_cte" src="<%= basePath%>/images/loader/ajax-proces.gif" title=""></td>
        <td width="99"><div id="folio_cte"> </div></td>
        <td width="211"><div id="estatus_cte"> </div></td>
      </tr>
      <tr>
        <td><input type="image" name="img_pedido" id="img_pedido" src="<%= basePath%>/images/loader/ajax-proces.gif" title=""></td>
        <td><div id="folio_pedido"> </div></td>
        <td><div id="estatus_pedido"> </div></td>
      </tr>
      <tr>
        <td><input type="image" name="img_pago" id="img_pago" src="<%= basePath%>/images/loader/ajax-proces.gif" title=""></td>
        <td><div id="folio_pafo"> </div></td>
        <td><div id="estatus_pago"> </div></td>
      </tr>
      <tr>
        <td colspan="3">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="3" align="center"><input type="button" name="button" id="button" value="Reprocesar" class="k-button"></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>