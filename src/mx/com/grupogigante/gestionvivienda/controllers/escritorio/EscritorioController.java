/**
 *  Clase creada por Osvaldo Rodriguez Martinez (SACTI consultores) con fecha de 12/10/2012
 */
package mx.com.grupogigante.gestionvivienda.controllers.escritorio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.com.grupogigante.gestionvivienda.dao.UsuariosDaoImpl;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseValidaLoginDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.escritorio.CriteriosEscritorioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.escritorio.ResponseEscritorioActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.escritorio.RespuestaSesionEscritorioDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.resources.ArchivoPropiedades;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.Connection;
import mx.com.grupogigante.gestionvivienda.utils.SessionValidatorSTS;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author osvaldo
 *
 */

@Controller
public class EscritorioController {
	private Logger log = Logger.getLogger(EscritorioController.class);
	@RequestMapping(value="/Escritorio.htm", method= RequestMethod.GET)
	public String getEscritorio(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "Escritorio";
		 }
		 return redir;
	 }

	
	@RequestMapping(value="/Escritorio.htm",method=RequestMethod.POST)
	public @ResponseBody ResponseEscritorioActionDto accionesEscritorio(@ModelAttribute(value="escritorioView")CriteriosEscritorioDto criterios, BindingResult result, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ViviendaException{
		ResponseEscritorioActionDto resAction = new ResponseEscritorioActionDto();
		RespuestaSesionEscritorioDto respSesion = new RespuestaSesionEscritorioDto();
		ResponseValidaLoginDto respPermisosUser = new ResponseValidaLoginDto();
		ArchivoPropiedades propiedades = new ArchivoPropiedades();
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();
		
		if(!validaSesion.validaSesion(session)) {
			respPermisosUser.setMensaje("LOGOUT");
			respPermisosUser.setDescripcion("");
			resAction.setRespPermisosUser(respPermisosUser);
			respSesion.setMensaje("LOGOUT");
			respSesion.setDescripcion("");
			resAction.setRespSesionEscritorio(respSesion);
			return resAction;
		 }
		
		if(criterios.getAccion().equals("salir"))
		{			
			validaSesion.setDestruyeSesion(session, request);
			if (Connection.getConnect() != null) 
			{
				//Connection.setConnect(null);
				Connection.setCloseConnect();
				respSesion.setDescripcion("Saliendo del sistema espere un momento");
				respSesion.setMensaje("SUCCESS");
			}		    
			
			resAction.setRespSesionEscritorio(respSesion);
		}			
		else if(criterios.getAccion().equals("permisos"))
		{
			respPermisosUser.setUsuario((UsuarioDto)session.getAttribute("usrSession"));
			respPermisosUser.setMensaje("SUCCESS");
			respPermisosUser.setUrl_conf(validaSesion.getDatos(session, "url"));
			resAction.setRespPermisosUser(respPermisosUser);
		}
		return resAction;
	}
}
