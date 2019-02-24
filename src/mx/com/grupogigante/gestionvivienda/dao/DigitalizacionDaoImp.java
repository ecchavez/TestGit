/**
 * @author Osvaldo Rodriguez/ Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 19/07/2012             
 */
package mx.com.grupogigante.gestionvivienda.dao;



import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import mx.com.grupogigante.gestionvivienda.controllers.simulador.SimuladorController;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.CriteriosDatosDigitalizacionImageDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.ResponseUploadFilesDigitDto;
import mx.com.grupogigante.gestionvivienda.exceptions.DaoException;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.CheckFileConnection;
import mx.com.grupogigante.gestionvivienda.utils.Connection;

import com.sap.conn.jco.JCoFunction;

/**
 * Clase en donde se realiza la implementacion de todas las operaciones referentes a Catalogos
 * Fecha de creación: 19/07/2012               
 */

@Repository
public class DigitalizacionDaoImp implements DigitalizacionDao {
	private Logger log = Logger.getLogger(DigitalizacionDaoImp.class);
	
	private CheckFileConnection traerArchivo;
	private ResponseUploadFilesDigitDto respAddDigit;

	
public ResponseUploadFilesDigitDto setDataDigit(CriteriosDatosDigitalizacionImageDto criterios) throws ViviendaException {
		
		respAddDigit=new ResponseUploadFilesDigitDto();
		traerArchivo = new CheckFileConnection();	
		
		String sStatus;
		Connection connect = null;
		sStatus = ArchLogg.leeLogg();
		String subrc = "";
		String bapierror = "";
		
		if (sStatus.equals("OK")) 
		{
			try 
			{
				//if (connect == null) {
			    if (Connection.getConnect() == null) 
			    {
					//connect = new Connection(ArchLogg.getSapSystem());
			    	connect = new Connection(ArchLogg.getSapSystem());
				}
			    else
			    {
					connect = Connection.getConnect();
				}
				// Establece RFC a ejecutar en SAP
				JCoFunction function = connect.getFunction("ZCSMF_0079_SET_STATUS_DIGIT");
				// Establece Parametros Import			 
				 		 
				function.getImportParameterList().setValue("I_USUARIO", criterios.getId_usuario());
				function.getImportParameterList().setValue("I_ID_UT_SUP", criterios.getIdUTS());
				function.getImportParameterList().setValue("I_ID_AREA", criterios.getSubtipo());
				function.getImportParameterList().setValue("I_ID_FILE", criterios.getSubtipoa());
				function.getImportParameterList().setValue("I_STATUS", criterios.getEstatus());
				function.getImportParameterList().setValue("I_ID_EQUNR", criterios.getEquipo());
				
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				if (subrc.equals("00")) 
				{	
					respAddDigit.setMensaje("SUCCESS");	
					respAddDigit.setDescripcion("Datos enviados satisfactoriamente");
				}
				else
				{
					respAddDigit.setMensaje("FAULT");	
					respAddDigit.setDescripcion(bapierror);
				}
			} 
			catch (Exception excp) 
			{
				log.error("ERROR: ",excp);
				respAddDigit.setMensaje("FAULT");
				respAddDigit.setDescripcion(excp.getMessage());
				throw new ViviendaException(excp.getMessage());
			}			
		}	
		return respAddDigit;
	}
}
