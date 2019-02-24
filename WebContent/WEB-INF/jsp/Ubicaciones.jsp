<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
String path = request.getContextPath();
String basePath="";if(request.getServerPort()==80){basePath = "//"+request.getServerName()+path;}else{basePath = "//"+request.getServerName()+":"+request.getServerPort()+path;} 
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Users using ajax</title>

<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
<script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
<script src="<%=basePath %>/kendo/js/console.js"></script>

<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/ubicaciones/UbicacionesJQ.js"></script>
<script>
	$(document).ready(function() { 
		$("#cmb_ubicaciones_sup_f").kendoComboBox({
	     	dataTextField: "ubicacionClave",
	     	dataValueField: "ubicacionDescripcion"
 		});	              
	    getFaces();
		
	});
</script>

<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo/styles/examples.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>/kendo/styles/main.css" rel="stylesheet" type="text/css"/>
<style scoped>                     
                
                .metrotable > thead > tr > th 
                {
                	font-size: 1.3em;
                	padding-top: 0;
                	padding-bottom: 5px;
                }
            </style>

</head>
<body>
<div id="example" class="k-content">
<table border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="150"><input id="cmb_ubicaciones_sup_f" style="width: 150px;"/></td>
    <td width="183">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2">
    			<table id="pisos" class="metrotable">
                    <thead>
                        
                    </thead>
                    <tbody>
                        
                    </tbody>
                </table>
                </td>
  </tr>
</table>
</div>
<div id="buttomBar">
  	<div class="console"/>
  </div>
</body>
</html>