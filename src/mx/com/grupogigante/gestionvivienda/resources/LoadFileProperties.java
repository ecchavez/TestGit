package mx.com.grupogigante.gestionvivienda.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

public class LoadFileProperties  {

	public String getPropertiesFile(String propiedad) throws ViviendaException
	{
		String respuesta="";
		String path_base="/home/jbossgig/GestionVivienda/";
		Properties prop = new Properties(); 
	    
		try {
	        //load a properties file from class path, inside static method
	  	    prop.load(new FileInputStream(path_base+"CONFIGURACION/vivienda.properties"));
	        respuesta=prop.getProperty(propiedad);
	        } 
	    catch (Exception ex) {
	    	respuesta="";
            throw new ViviendaException("Error: "+ex.getMessage()); 	        
	    }
		return path_base+respuesta;
	}
}
