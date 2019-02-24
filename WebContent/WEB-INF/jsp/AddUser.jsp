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
<script src="<%=basePath %>/js/user.js"></script>
<script>
	$(document).ready(function() {                   
	   $("#grid").kendoGrid({ 
			height: 300  
	});
</script>

<link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" type="text/css"/>

<style scoped>
	#clientsDb {
		width: 500px;
		height: 413px;
		margin: 30px auto;
		padding: 51px 4px 0 4px;
		background: url('<%=basePath %>/images/clientsDb.png') no-repeat 0 0;
	}
	
	.k-content {
		font-family: Arial, Helvetica, sans-serif;
		font-size: 12px;
	}
</style>

</head>
<body>
<h1>Add Users using Ajax ........</h1>
	<table>
		<tr><td colspan="2"><div id="error" class="error"></div></td></tr>
		<tr><td>Enter your name : </td><td> <input type="text" id="name"><br/></td></tr>
		<tr><td>Education : </td><td> <input type="text" id="education"><br/></td></tr>
		<tr
    ><td colspan="2"><input type="button" value="Add Users" onclick="doAjaxPost()"><br/></td></tr>
		<tr><td colspan="2"><div id="info" class="success"></div></td></tr>
	</table>
    
    <div id="example" class="k-content">
    	<div id="clientsDb">
           
        </div>           
    </div>

</body>
</html>