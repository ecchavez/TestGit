/**
 * 
 */
package mx.com.grupogigante.gestionvivienda.utils;

import java.util.Properties;

import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;
import com.sap.conn.rfc.exceptions.RfcException;

/**
 * @author Osvaldo Rodriguez Martinez SACTI Consultores. / Para Grupo Gigante "Gestion Vivienda"
 */
public class Connection {
	private static Logger log = Logger.getLogger(Connection.class);
	private static String SAP_SERVER = "SAP_SERVER";
	private JCoRepository repos;
	private JCoDestination dest;
	private final Properties properties;
	private static Connection connect = null;
	
	//static String DESTINATION_NAME = "DESTINATION_WITH_POOL";
	private static MyDestinationDataProvider myProvider;

	public Connection(SapSystem system) throws ViviendaException {		
		
		properties = new Properties();
		properties.setProperty(DestinationDataProvider.JCO_ASHOST, system
				.getHost());
		properties.setProperty(DestinationDataProvider.JCO_SYSNR, system
				.getSystemNumber());
		properties.setProperty(DestinationDataProvider.JCO_CLIENT, system
				.getClient());
		properties.setProperty(DestinationDataProvider.JCO_USER, system
				.getUser());
		properties.setProperty(DestinationDataProvider.JCO_PASSWD, system
				.getPassword());
		properties.setProperty(DestinationDataProvider.JCO_LANG, system
				.getLanguage());
		// Esto se agrega (Inicia)
		properties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "200");
		properties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, "200");
		
		properties.setProperty(DestinationDataProvider.JCO_EXPIRATION_TIME, "30000");
		// Esto se agrega (Fin)
		
		myProvider = new MyDestinationDataProvider();
		myProvider.changePropertiesForABAP_AS(properties);
		
		try {
			if(!Environment.isDestinationDataProviderRegistered()){ 
				Environment.registerDestinationDataProvider(myProvider);	
				
			}
			dest = JCoDestinationManager.getDestination(SAP_SERVER);
			repos = dest.getRepository();
			
		} catch (ExceptionInInitializerError e) {
			log.error("ERROR: ",e);
			throw new ViviendaException("Error al registrar pool. "+e.getMessage());
		}catch (NoClassDefFoundError e) {
			log.error("ERROR: ",e);
			throw new ViviendaException("Error, ya existe una conexion JCO en JRE. "+e.getMessage());		
		}catch (JCoException e) {
			log.error("ERROR: ",e);
			throw new ViviendaException("Error al intentar conectar con JCO . "+e.getKey());		
		}  catch (Exception e) {	
			log.error("ERROR: ",e);
			throw new ViviendaException(e.getMessage());	
		}
		
		// 26/05/2010 ADD->INI
		Connection.setConnect(this);
		// 26/05/2010 ADD->FIN
	}
	
	
	/**
	 * Method getFunction read a SAP Function and return it to the caller. The
	 * caller can then set parameters (import, export, tables) on this function
	 * and call later the method execute.
	 * 
	 * getFunction translates the JCo checked exceptions into a non-checked
	 * exceptions
	 */
	public JCoFunction getFunction(String functionStr) throws ViviendaException {
		JCoFunction function = null;
		try {
			//function = repos.getFunction(functionStr);
			function = repos.getFunctionTemplate(functionStr).getFunction();
		} catch (Exception e) {
			log.error("ERROR: ",e);
			throw new ViviendaException("Problem retrieving JCO. Function object.");
		}
		if (function == null) {
			log.error("ERROR: null funtion jco");
			throw new ViviendaException("Not possible to receive function. ");
		}

		return function;
	}

	/**
	 * Method execute will call a function. The Caller of this function has
	 * already set all required parameters of the function
	 * 
	 */
	public void execute(JCoFunction function) throws ViviendaException {
		try {			
			//JCoContext.begin(dest);  Comentado			
			function.execute(dest);
			//JCoContext.end(dest);    Comentado
		} catch (Exception e) {
			//e.printStackTrace();
			throw new ViviendaException(e.getLocalizedMessage());
		}
	}

	/**
	 * @return the connect
	 */
	public static Connection getConnect() {
		return connect;
	}

	/**
	 * @param connect the connect to set
	 */
	public static void setConnect(Connection connect) {
		Connection.connect = connect;
	}
	
	 public static void setCloseConnect() throws ViviendaException
	 {
		 try {
				if(Environment.isDestinationDataProviderRegistered()){ 
					Environment.unregisterDestinationDataProvider(myProvider);	
				}
			} catch (ExceptionInInitializerError e) {
				log.error("ERROR: ",e);
				throw new ViviendaException("Error al registrar pool. "+e.getMessage());
			}catch (NoClassDefFoundError e) {
				log.error("ERROR: ",e);
				throw new ViviendaException("Error, ya existe una conexion JCO en JRE. "+e.getMessage());				
			}  catch (Exception e) {	
				log.error("ERROR: ",e);
				throw new ViviendaException(e.getMessage());	
			}
	 }

}