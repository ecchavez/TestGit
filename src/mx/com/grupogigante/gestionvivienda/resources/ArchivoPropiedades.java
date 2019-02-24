package mx.com.grupogigante.gestionvivienda.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContextUtils;

public class ArchivoPropiedades {
	// Atributos de la clase
		private Properties propiedades;
		private String archivo;

		/**
		 * Constructor que inicializa el objeto properties y la ruta del archivo a
		 * trabajar
		 */
		public ArchivoPropiedades(HttpServletRequest req) {
			ServletContext context = RequestContextUtils.getWebApplicationContext(req).getServletContext();
			String archivo=context.getRealPath("/") + "WEB-INF/recursos.properties";
			propiedades = new Properties();
   			this.archivo = archivo;
            this.cargarPropiedades();
		}

		public ArchivoPropiedades() {
			InputStream is = getClass().getResourceAsStream("/recursos.properties");
			//String archivo=context.getRealPath("/") + "WEB-INF/recursos.properties";
			propiedades = new Properties();
   			this.archivo = archivo;
            this.cargarPropiedadesConnection(is);
		}

		/**
		 * Carga en el objeto de propiedades los datos leidos del archivo
		 *
		 * @return boolean
		 */
		public boolean cargarPropiedades() {
			try {
				propiedades.load(new FileInputStream(archivo));
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

		public boolean cargarPropiedadesConnection(InputStream is) {
			try {
				propiedades.load(is);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

		/**
		 * Permite leer todas las propiedades almacenadas en el archivo mostrando su
		 * respectivo valor
		 */
		public void readPropiedades() {
			// Obtengo una enumeracion de llaves, cada llave permitira
			// obtener un valor dentro del properties
			Enumeration llaves = propiedades.keys();

			// Recorro llave por llave y obtengo su valor
			while (llaves.hasMoreElements()) {
				String llave = (String) llaves.nextElement();
				System.out.println(llave + "=" + propiedades.getProperty(llave));
			}
		}

		/**
		 * Obtiene el valor de una propiedad determinada y que exista en el archivo
		 * de propiedades, de lo contrario retornara null
		 *
		 * @param propiedad
		 * @return
		 */
		public String getValorPropiedad(String propiedad) {
			return propiedades.getProperty(propiedad);
		}
}
