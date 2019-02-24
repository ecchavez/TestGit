package mx.com.grupogigante.gestionvivienda.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseFileConnect;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.resources.ArchivoPropiedades;

public class CheckFileConnection {
	private Logger log = Logger.getLogger(CheckFileConnection.class);
	private ResponseFileConnect respConexion;
	
	public ResponseFileConnect getFileConnection() throws ViviendaException
	{
		
		ArchivoPropiedades propiedades = new ArchivoPropiedades();
		  respConexion= new ResponseFileConnect();
		  URL u;
	      InputStream is = null;
	      //DataInputStream dis;
	      
	      try {
	    	  //u = new URL("http://10.1.5.40/sapconnect/login.conf?rand=" + Math.random());	         
	    	  u = new URL(propiedades.getValorPropiedad("ruta.files.gestion.vivienda") + "/sapconnect/login.conf?rand="+Math.random());	         
	          is = u.openStream();
	          //dis = new DataInputStream(new BufferedInputStream(is));
	          respConexion.setFileConnect(is);
	          respConexion.setMensaje("SUCCESS");
	      } catch (MalformedURLException mue) {
	    	 log.error("ERROR: ",mue);
	    	 respConexion.setMensaje("FAULT");
	         respConexion.setDescripcion("Error - La direccion del archivo esta mal formada.");
	         respConexion.setFileConnect(null);
	         throw new ViviendaException("Error - La direccion del archivo esta mal formada. ");

	      } catch (IOException ioe) {	
	    	 log.error("ERROR: ",ioe);
	         respConexion.setMensaje("FAULT");
	         respConexion.setDescripcion("Error - No se puede leer el archivo de configuracion.");
	         respConexion.setFileConnect(null);
	         throw new ViviendaException("Error - No se puede leer el archivo de configuracion. ");
	      } 
	      
	      return respConexion;
	}
}
