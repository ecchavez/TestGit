function validaforma()
{	
	var validado = false;
	var nameFile = $("#fileData").val();
	
	if( nameFile != "" && nameFile != undefined)
	{
	    var extension = (nameFile.substring(nameFile.lastIndexOf("."))).toLowerCase();
	    //alert(extension);
	    
	    if (extension == ".jpg"){
	    	validado=true;
	    }else{				 
			alert("Archivo no permitido, seleccione imagen .jpg");
		}   
	}else{
		alert("Archivo vacio, seleccione otro");
	}	
		
	if(validado)
	{		
		$("#accion").val("guardar");
		document.formulario.submit();
	}
}

function eliminaboletin()
{		
	    //alert("Elimina boletin");
		$("#accion").val("eliminar");
		document.formulario.submit();
}