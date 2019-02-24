package mx.com.grupogigante.gestionvivienda.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import mx.com.grupogigante.gestionvivienda.utils.SessionValidatorSTS;

public class ExtraProperties {
	
	Properties prop;
	URL u;
    InputStream is = null;
    
	public String getExtraProperties(String propiedad, HttpSession session)
	{
		prop= new Properties();
		String res="";
		
		try {
			SessionValidatorSTS validaSesion = new SessionValidatorSTS();
			ArchivoPropiedades propiedades = new ArchivoPropiedades();
			u = new URL(validaSesion.getDatos(session, "url") + "/config/config.properties");	         
	        is = u.openStream();
	           //load a properties file
			prop.load(is);

			res=prop.getProperty(propiedad);
	
		} catch (IOException ex) {
			res="";
	    }
		
		if(res==null)
		{
			res="";
		}
		
		return res;
	}
}
