/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 29/08/2012               
 */
package mx.com.grupogigante.gestionvivienda.controllers.archivero;

import javax.servlet.http.HttpSession;

import mx.com.grupogigante.gestionvivienda.utils.SessionValidatorSTS;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Clase controladora para el modulo de Archivero Electronico
 * Fecha de creación: XX/06/2012               
 */
@Controller
public class ArchiveroController {
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina Archivero Electrónico 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina deredireccion 
	 */	
	@RequestMapping(value = "/Archivero.htm", method = RequestMethod.GET)
	public String returnaddCliente(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "Archivero";
		 }
		 return redir;
	
}


}
