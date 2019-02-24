/**
 * 
 */
package mx.com.grupogigante.gestionvivienda.utils;

import com.sap.conn.jco.*;
import java.util.*;

/**
 * @author Omar R. BAF Consulting S.C. / Para Grupo Gigante "Super Precio"
 *         Programa que muestra un ejemplo de como hacer una llamada a un MF RFC
 *         de SAP
 */
public class EjmpCallRFC {

	/*public static void main(String args[]){
		try{
	    // Instancia objeto de la clase
	    EjmpCallRFC vref = new EjmpCallRFC();
	    // manda ejecutar metodo para llamada a RFC
		vref.CallRFC();
		}
		// En caso de ocurrir un error, se imprime este en pantalla
		catch (Exception ex){
			System.out.println(ex);
		}
	}*/
	
	/**
	 * Metodo en el cual se realiza la ejecución de un RFC en un ambiente SAP
	 * @throws EmptyStackException Excepciones cachadas durante el intento de ejec. de un RFC
	 */
	/*public void CallRFC() throws EmptyStackException {
		// Obtiene info. para conexion a SAP de Archivo encriptado
		String sStatus = ArchLogg.leeLogg();

		if (sStatus == "OK") {

			// Para Manejo de exepciones
			try {
				// Se crea Obj para establecer conexion a SAP y se envia info
				// neces. para conex.
				Connection connect = new Connection(ArchLogg.getSapSystem());
				// Establece RFC a ejecutar en SAP
				JCoFunction function = connect.getFunction("BAPI_USER_GETLIST");
				// Establece Parametros Import para ejecución del RFC
				function.getImportParameterList().setValue("MAX_ROWS", 10);
				// Manda ejecutar el RFC en el sistema SAP y se obtiene resultad
				connect.execute(function);
				// Se rescata los valores obtenidos de la llamada al RFC en el
				// parametro tabla USERLIST
				JCoTable table = function.getTableParameterList().getTable(
						"USERLIST");

				// Verifica que tabla no este vacia
				if (!table.isEmpty()) {
					// Imprime registros contenidos en table
					TableAdapterReader tableAdapter = new TableAdapterReader(
							table);
					System.out.println("Number of Users: "
							+ tableAdapter.size());
					for (int i = 0; i < tableAdapter.size(); i++) {
						// USERNAME es un campo de la estructura user
						System.out.println(tableAdapter.get("USERNAME"));
						tableAdapter.next();
					}
				}
		      // En caso de una excepción se regresa a programa que hizo la llamada.
			} catch (Exception excp) {
				throw new RuntimeException(excp);
			}
		}
	}*/
}
