var ancho_aplicado=0;

/**
*
*  Permite capturar caracteres alfabéticos
*  @param  objeto de la pantalla
*  @out    boolean
*  @pres   IE5.0+ requerido
*  @pres   Sólo se interceptan eventos keyPress, teclas como SHIFT, BACKSPACE o combinaciones de estas teclas no son capturadas.
*  @posts  En caso de caracter inválido se cancela el evento
*/
function captureNombres(e)
{
    var key;

	 if(window.event) // IE
	 {
	  key = e.keyCode;
	 }
	  else if(e.which) // Netscape/Firefox/Opera
	 {
	  key = e.which;
	 }
	
	 tecla = String.fromCharCode(key).toLowerCase();
	 letras = " abcdefghijklmnñopqrstuvwxyz";
	 especiales = [8,9];

	 tecla_especial = false
	 for(var i in especiales){
	     if(key == especiales[i]){
	  tecla_especial = true;
	  break;
            } 
	 }
	 
	        if(letras.indexOf(tecla)==-1 && !tecla_especial)
	     return false;
}

function isNumberKey(evt)
{
	var charCode = (evt.which) ? evt.which : event.keyCode
		if (charCode > 31 && (charCode < 48 || charCode > 57))
			return false;
	 
	return true;
}



function letrasnumeros(e)
{
    var key;

	 if(window.event) // IE
	 {
	  key = e.keyCode;
	 }
	  else if(e.which) // Netscape/Firefox/Opera
	 {
	  key = e.which;
	 }
	
	 tecla = String.fromCharCode(key).toLowerCase();
	 letras = " abcdefghijklmnñopqrstuvwxyz1234567890@-_.";
	 especiales = [8,9];

	 tecla_especial = false
	 for(var i in especiales){
	     if(key == especiales[i]){
	  tecla_especial = true;
	  break;
            } 
	 }
	 
	        if(letras.indexOf(tecla)==-1 && !tecla_especial)
	     return false;
}

/**
*
*  Permite capturar caracteres numericos enteros.
*  @param  objeto de la pantalla
*  @out    boolean
*  @pres   IE5.0+ requerido
*  @pres   Sólo se interceptan eventos keyPress, teclas como SHIFT, BACKSPACE o combinaciones de estas teclas no son capturadas.
*  @posts  En caso de caracter inválido se cancela el evento
*/
//
function captureEnteros(e)
{
    var key;

	 if(window.event) // IE
	 {
	  key = e.keyCode;
	 }
	  else if(e.which) // Netscape/Firefox/Opera
	 {
	  key = e.which;
	 }

	 if(key == 8)
	 {
		 return true ;
	 }else if (key < 48 || key > 57)
	    {
	      return false;
	    }

}


/**
 * Metodos para la creacion del reporte  
 * 
 * Fecha de creación: XX/07/2012               
 */

//funcion para crear el xml 
function createXMLCriterios( root, crit )
{
  var nodes;
  var xml = "";
  if( root )
    xml += "<" + root + ">";
  for(var i=0; i<crit.length; i++)
  {
	  nodes=crit[i];
	  xml += "<criterio>";
	  for( theNode in nodes )
	  {
		 if(theNode=="flim")
		 {
			 var date_bp=nodes[theNode];
			 var resp_dat=date_bp.replace(/-/g,"");
			 xml += "<" + theNode + ">" + resp_dat + "</" + theNode + ">";
	     }
		 else
		 {
			 xml += "<" + theNode + ">" + nodes[theNode] + "</" + theNode + ">";
		 }
	  }	
	  xml += "</criterio>";
  }  
  xml += "</" + root + ">";
  
  return xml;
}
//function que  valida formato el formato de hora que valida es:
//HH:MM o HH:MM:SS o HH:MM:SS.mmm
function validate_time(str) {
return /^([1-9]|1[0-2]):[0-5]d(:[0-5]d(.d{1,3})?)?$/.test(str);
}



function validaEnter(e)
{
if (window.event) {keyval=e.keyCode}
else
if (e.which) {keyval=e.which}

if (keyval=="13" &&  $.trim($('#txt_passwd').val())=="") {validaUsuario();}
else
if (keyval=="13" &&  $.trim($('#txt_passwd').val())!="") {validaUsuario();}
} 

function separarMiles(Elemento){
	    //0123456789
	    //deve de quedar $123,456,789.00
	    //del elemento obtenemos su valor
	    var valor=Elemento.value;

	    var valorDecimal;
	    var agregar00=true;
	    if(valor.indexOf("$")!=-1){

	    }else{
	        //se compara para ver si tiene decimales
	        if(cantidad.indexOf(".")!=-1){
	            //si tiene decimales no se la gregara el .00
	            agregar00=false;
	            //se separa el valor decimal del valor entero
	            valorDecimal=valor.substring(valor.indexOf("."), largo);
	            valor=valor.substring(0, valor.indexOf("."));

	        }
	        //se toma el valor del entero
	        var largo=valor.length;

	        if(largo>9){
	            valor=valor.substring(largo-12,largo-9)+","+valor.substring(largo-9,largo-6)+","+valor.substring(largo-6,largo-3)+","+valor.substring(largo-3, largo);
	        }else
	        if(largo>6){
	            valor=valor.substring(largo-9,largo-6)+","+valor.substring(largo-6,largo-3)+","+valor.substring(largo-3, largo);
	        }else
	        if(largo>3){
	            valor=valor.substring(largo-6,largo-3)+","+valor.substring(largo-3, largo);
	        }
	        valor="$"+valor;
	        if(agregar00==true){
	            valor=valor+".00";
	        }else{
	            valor=valor+valorDecimal;
	        }
	        Elemento.value=valor;
	    }
	}

function setWidth(el)
{
	if(ancho_aplicado==0)
	{
	    var d = el;
	    var p = d.data("kendoDropDownList").popup.element;
	    var w = p.css("visibility","hidden").show().outerWidth();
	    p.hide().css("visibility","visible");
	    d.closest(".k-widget").width(w+15);
	    p.css('width', ''+(w+10)+'px');
	    ancho_aplicado=1;
	}
}


function CalcularRFC(nombre,  apellidoPaterno,  apellidoMaterno, fecha)
{
    nombre = nombre.toUpperCase();
    apellidoPaterno = apellidoPaterno.toUpperCase();
    apellidoMaterno = apellidoMaterno.toUpperCase();
    rfc  = "";
    nombre         = Trim(nombre);
    apellidoPaterno = Trim(apellidoPaterno);
    apellidoMaterno = Trim(apellidoMaterno);
 
    nombre         = SinNombresComunes(nombre);       
    apellidoPaterno = SinPrefijos(apellidoPaterno);       
    apellidoMaterno = SinPrefijos(apellidoMaterno);

    // Primera letra del apellido paterno
    rfc = apellidoPaterno.substring(0, 1);

    //Buscamos y agregamos al rfc la primera vocal del primer apellido que encontremos       
    for( i=1; i<apellidoPaterno.length; i++)
    {
         c = apellidoPaterno.substring(i, i+1);
        if (EsVocal(c))
        {
            rfc += SinAcento(c);               
            break;
           
        }
    }
   
    //Si no hay vocal después de la primera letra ponemos una X
    if(rfc.length < 2)
       var PaternoCompleto = false;
    else
       var PaternoCompleto = true;
   //Agregamos el primer caracter del apellido materno
    rfc += SinAcento(apellidoMaterno.substring(0, 1));
 
    //Agregamos el primer caracter del primer nombre o los dos primeros si es que el apellido paterno
    //no aportó los 2 caracteres que se requerían       
    if(PaternoCompleto)
       rfc += SinAcento(nombre.substring(0, 1));
    else
       rfc += SinAcento(nombre.substring(0, 2));       
   
    // Si la cadena forma cualquier palabra altisonante, destruimos la altisonancia con una X en lugar de la ultima letra.
    rfc = SinAltisonantes(rfc);

    //Agregamos la fecha yymmdd (por ejemplo: 680825, 25 de agosto de 1968 )
    // 25-08-2006  => 060825

    rfc += fecha.substring(8, 10)+fecha.substring(3, 5) + fecha.substring(0, 2)

    return rfc;
}


function Trim(STRING)
{
    STRING = LTrim(STRING);
    return RTrim(STRING);
}

function RTrim(STRING)
{
    while(STRING.charAt((STRING.length -1))==" ")
    {
        STRING = STRING.substring(0,STRING.length-1);
    }
    return STRING;
}
function LTrim(STRING)
{
    while(STRING.charAt(0)==" ")
    {
        STRING = STRING.replace(STRING.charAt(0),"");
    }
    return STRING;
}

function EsVocal( letra)
{
    //Aunque para el caso del RFC cambié todas las letras a mayúsculas
    //igual agregé las minúsculas.
    if (    letra == 'A' || letra == 'E' || letra == 'I' || letra == 'O' || letra == 'U' ||
        letra == 'a' || letra == 'e' || letra == 'i' || letra == 'o' || letra == 'u' ||
        letra == 'Á' || letra == 'É' || letra == 'Í' || letra == 'Ó' || letra == 'Ú' ||
        letra == 'á' || letra == 'é' || letra == 'í' || letra == 'ó' || letra == 'ú')
        return true;
    else
        return false;
}

function inArray(elemento, arreglo)
{
    for ( var keyVar in arreglo )
    {

        if (keyVar== elemento)
        {
          return arreglo[keyVar];
        }
    }
    return false;

}


function SinPrefijos(nombres)   
{
 
  if(nombres.substring(0,5) == 'DE LA')
     return(Trim(nombres.substring(5,nombres.length )));
 
  if(nombres.substring(0,5) == 'DE EL')
     return(Trim(nombres.substring(5,nombres.length)));

  if(nombres.substring(0,6) == 'DE LOS')
     return(Trim(nombres.substring(6,nombres.length)));
     
     if(nombres.substring(0,6) == 'DE LAS')
     return(Trim(nombres.substring(6,nombres.length)));

  if(nombres.substring(0,3) == 'DEL')
     return(Trim(nombres.substring(3,nombres.length)));
      
  if(nombres.substring(0,3) == 'LOS')
     return(Trim(nombres.substring(3,nombres.length)));

  if(nombres.substring(0,2) == 'EL')
     return(Trim(nombres.substring(2,nombres.length)));

  if(nombres.substring(0,2) == 'DE')
     return(Trim(nombres.substring(2,nombres.length)));

  if(nombres.substring(0,2) == 'LA')
     return(Trim(nombres.substring(2,nombres.length)));

  
   return(nombres);
}



function SinNombresComunes(nombres)
{

   // Cuantas palabras conforman el nombre?
   var num = 1;
  
   for( i=0; i<nombres.length; i++)
    {
         c = nombres.substring(i, i+1);
        if( c==' ' )
        {
            num++;
           
            //Si hay más de un espacio vacío consecutivo, recorremos hasta el siguiente caracter no vacío.
            while ( nombres.substring((i+1), (i+2))==' ' )
            { 
                  i++;
            }
        }           
    }
  
  
   if(num == 1)
      return(nombres);   

   //Si hay más de un nombre, verificamos que el primero no sea de los de la lista, y si lo és, lo reovemos.

  if(nombres.substring(0,5) == 'MARIA')
     return(Trim(nombres.substring(5,nombres.length)));

  if(nombres.substring(0,4) == 'JOSE')
     return(Trim(nombres.substring(4,nombres.length)));   
  
   return(nombres);   
}


function SinAcento( vocal )
{

  //Minúsculas
  if(vocal == 'á')
       return('a');

  if(vocal == 'é')
       return('e');

  if(vocal == 'í')
       return('i');

  if(vocal == 'ó')
       return('o');

  if(vocal == 'ú')
       return('u');

  // Mayusculas
  if(vocal == 'Á')
       return('A');
      
  if(vocal == 'É')
       return('E');

  if(vocal == 'Í')
       return('I');

  if(vocal == 'Ó')
       return('O');

  if(vocal == 'Ú')
       return('U');

  return(vocal);
}   

function SinAltisonantes(palabra)
{

    var mal = new Array(    "BUEI" ,
                "BUEY" ,
                "CACA" ,
                "CACO" ,
                "CAGA" ,
                "CAGO" ,
                "CAKA" ,
                "CAKO" ,
                "COGE" ,
                "COJA" ,
                "COJE" ,
                "COJI" ,
                "COJO" ,
                "CULO" ,
                "FETO" ,
                "GUEY" ,
                "JOTO" ,
                "KACA" ,
                "KACO" ,
                "KAGA" ,
                "KAGO" ,
                "KOGE" ,                       
                "KOJO" ,
                "KAKA" ,
                "KULO" ,
                "LOCA" ,
                "LOCO" ,
                "LOKA" ,
                "LOKO" ,
                "MAME" ,
                "MAMO" ,
                "MEAR" ,
                "MEAS" ,
                "MEON" ,
                "MION" ,
                "MOCO" ,
                "MULA" ,
                "PEDA" ,
                "PEDO" ,
                "PENE" ,
                "PUTA" ,
                "PUTO" ,
                "QULO" ,
                "RATA" ,
                "RUIN"  );
                       
    for ( i=0; i< mal.length; i++)
    {
        if( mal[i] == palabra )
        {
            nrfx = mal[i];
            nrfx = nrfx.substring(0,3) + 'X';
                return(nrfx);               
       
        }
    }                             
                             
/*    */                             
return(palabra);
}

function CalcularHomoclave(nombre, pat,  mat)
{   
    var strNombreComp =  pat.toUpperCase() + " " + mat.toUpperCase() + " " + nombre.toUpperCase();
var strCharsHc = '123456789ABCDEFGHIJKLMNPQRSTUVWXYZ';
var strCadena = '0';
var strChr;
var intNum1;
var intNum2;
var intSum=0;
var int3;
var intQuo;
var intRem;
   
for( i=0; i<=strNombreComp.length; i++)
 {
   strChr = strNombreComp.substr(i,1);
 
   if (strChr==' ' || strChr=='-' )
   {
     strCadena = strCadena + '00';
   }
  
   if (strChr=='Ñ' || strChr=='Ü' )
   {
     strCadena = strCadena + '10';
   }
  
    if (strChr=='A' || strChr=='B' || strChr=='C' || strChr=='D' || strChr=='E' ||
    strChr=='F' || strChr=='G' || strChr=='H' || strChr=='I')
   {
    strCadena = strCadena + ((strChr.charCodeAt())-54);
   }
 
       if (strChr=='J' || strChr=='K' || strChr=='L' || strChr=='M' || strChr=='N' ||
    strChr=='O' || strChr=='P' || strChr=='Q' || strChr=='R')
   {
     strCadena = strCadena + ((strChr.charCodeAt())-53);
   }     
 
  if (strChr=='S' || strChr=='T' || strChr=='U' || strChr=='V' || strChr=='W' ||
    strChr=='X' || strChr=='Y' || strChr=='Z')
   {
     strCadena = strCadena + ((strChr.charCodeAt())-51);
   }   
 
 }

 for( i=0; i<(strCadena.length)-1; i++)
 {
  intNum1 = parseInt(strCadena.substr(i,2));
  intNum2 = parseInt(strCadena.substr(i+1,1));
  intSum = intSum + intNum1 * intNum2;
 }
 
intSum=intSum+' ';
intSum= Trim(intSum)
int3 = intSum.substr(-3);
intQuo = parseInt(int3 / 34);
intRem = int3 % 34;
   
return ((strCharsHc.substr(intQuo, 1)) + (strCharsHc.substr(intRem, 1)));
}

function strpos( haystack, needle, offset){
// http://kevin.vanzonneveld.net
// +   original by: Kevin van Zonneveld (http://kevin.vanzonneveld.net)
// *     example 1: strpos('Kevin van Zonneveld', 'e', 5);
// *     returns 1: 14

var i = haystack.indexOf( needle, offset ); // returns -1
return i >= 0 ? i : false;
}

function DigitoVerificador(rfc_homo)
{
var strChars = '0123456789ABCDEFGHIJKLMN&OPQRSTUVWXYZ*';
var strDV;
var intDV;
intSumas=0
for(i=0;i<rfc_homo.length;i++)
{
strCh=rfc_homo.substr(i,1);
strCh= ((strCh == ' ') ? '*':strCh);
intIdx = strpos(strChars,strCh);
intSumas = intSumas + intIdx * (14 - (i+1));
}
if ((intSumas % 11)==0)
{
strDV=0;     
}
else
{
intDV = 11 - intSumas % 11;     
if(intDV > 9)
{
 strDV='A';
}
else
{
   strDV=intDV;
}
}   
return strDV;   
} 

	function onVentanaWait(mensaje, icon)
	{
		var msj="";
		
		if(icon)
		{
			msj='<h3><img src="'+contexPath+'/images/loader/ajax_loader.gif" /> '+mensaje+' ...</h3>"';
		}
		else
		{
			msj=msj='<h3>'+mensaje+' ...</h3>"';;
		}
		
		$.blockUI({ 
			css: {             
		    border: 'none',             
		    padding: '15px',             
		    backgroundColor: '#000',             
		    '-webkit-border-radius': '10px',             
		    '-moz-border-radius': '10px',             
		    opacity: .5,             
		    color: '#fff'         
		    },
		    message: ''+msj+''
		});	
	}

function offVentanaWait()
{
	$.unblockUI();
}

function convertDate(fecha)
{
	var resfecha="";
	if(fecha=="" || fecha == undefined)
	{
		resfecha="";
	}
	else
	{			
		var fend=fecha.split("-");
		var fecharet=fend[2]+"-"+fend[1]+"-"+fend[0];
		resfecha=fecharet;		
	}
	return resfecha;
}

function uc(txt_obj)
{
	var palabra=txt_obj.value;
	palabra=palabra.toUpperCase();
	txt_obj.value=palabra;
}
function NumCheck(e, field) {
	  key = e.keyCode ? e.keyCode : e.which
	  // backspace
	  if (key == 8) return true
	  // 0-9
	  if (key > 47 && key < 58) {
	    if (field.value == "") return true
	    regexp = /.[0-9]{12}$/
	    return !(regexp.test(field.value))
	  }
	  // .
	  if (key == 46) {
	    if (field.value == "") return false
	    regexp = /^[0-9]+$/
	    return regexp.test(field.value)
	  }
	  // other key
	  return false

	}
