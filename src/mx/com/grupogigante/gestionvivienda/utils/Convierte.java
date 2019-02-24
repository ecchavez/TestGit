/**
 * 
 */
package mx.com.grupogigante.gestionvivienda.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Omar Romero A. BAF Consulting S.C.
 * @version 1.0
 */
public class Convierte {
	
	
	
	/**
	 * Convierte un tipo Date a una String en Año Mes Dia
	 * @param date Fecha a Convertir
	 * @return String con formato Año Mes Dia
	 */
	public static String dateToString (Date date){

		if(date != null){
			String fecha = new String ();
			SimpleDateFormat formatoFechaSAP = new SimpleDateFormat("yyyyMMdd");
			StringBuilder conversion = new StringBuilder(formatoFechaSAP.format(date)); 
			fecha = conversion.toString();
			
			return fecha;
		}
		return null;
	}
	
	public static String timeToString (Date time){
		if(time != null){
			String tiempo = new String();
			SimpleDateFormat formatoFechaSAP = new SimpleDateFormat("HHmmss");
			StringBuilder conversion = new StringBuilder(formatoFechaSAP.format(time));
			tiempo = conversion.toString();
			
			return tiempo;
		}
		return null;
	}
}
