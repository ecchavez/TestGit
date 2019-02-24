<%@ page session="true" %>
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
<title>Login (Area restringida)</title>

<script src="<%=basePath %>/js/jquery-1.7.2.js"></script>
    <script src="<%=basePath %>/kendo/js/kendo.web.min.js"></script>
    <script src="<%=basePath %>/jixedbar/default/jquery.jixedbar.js"></script>  
    <script src="<%=basePath %>/js/jquery.blockUI.js"></script>  

<script type="text/javascript">
	var contexPath="<%=basePath %>";
</script>
<script src="<%=basePath %>/js/ubicaciones/UbicacionesJQ.js"></script>
<script>
	var folderAdd=[];
	var split_tree;
	$(document).ready(function(){
		 $("#tr_arbol").kendoTreeView({
			 select: onSelectArbol	  					 
		 }).data("kendoTreeView");
		 
		 $("#tr_arbol_files").kendoTreeView({
			 select: onSelectArbolFiles				 
		 }).data("kendoTreeView");
		
		 split_tree=$("#sp_arbol").kendoSplitter({
                        panes: [
                            { collapsible: true, size: "200px" },
                            { collapsible: true, size: "200px" },
                            { collapsible: true, size: "100%" }
                        ]
                    }).data("kendoSplitter");
		getArbol();
	});		
	
	

</script>

<link href="<%=basePath %>/jixedbar/default/jx.stylesheet.css" type="text/css"  rel="stylesheet" />
	<link href="<%=basePath %>/kendo/examples/content/shared/styles/examples-offline.css" type="text/css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="<%=basePath %>/kendo/styles/kendo.default.min.css" rel="stylesheet" />


</head>
<body>
<table width="1005" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td height="650">
             
                <div id="sp_arbol" style="height: 100%">
	                <div style="height: 100%; width: 100px;"> 
	                	<div class="demo-section" id="arbol_content" >
							<ul id="tr_arbol"> 
							</ul>
			            </div>                	
	                </div>
	                 <div style="height: 100%; width: 100px;"> 
	                	<div class="demo-section" id="arbol_archivos" >
							<ul id="tr_arbol_files"> 
							</ul>
			            </div>                	
	                </div>
	                <div style="height: 100%; width: 100%;" id="pdfContent">
	                
	                </div>
                
            </div>
		</td>
    </tr>
</table>
<style scoped>
                
                #tr_arbol .k-sprite {
                    background-image: url("<%=basePath %>/kendo/examples/content/web/treeview/coloricons-sprite.png");
                }
				
				#tr_arbol_files .k-sprite {
                    background-image: url("<%=basePath %>/kendo/examples/content/web/treeview/coloricons-sprite.png");
                }
                
                .rootfolder { background-position: 0 0; }
                .folder { background-position: 0 -16px; }
                .pdf { background-position: 0 -32px; }
                .html { background-position: 0 -48px; }
                .image { background-position: 0 -64px; }

            </style>

  <div id="buttomBar">
  	<div class="console"/>
  </div>
</body>
</html>