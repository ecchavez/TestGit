/**
 * @author ECHAVEZ - SACTI  / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 12/02/2014               
 */
package mx.com.grupogigante.gestionvivienda.controllers.catalogos;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.com.grupogigante.gestionvivienda.domain.dto.ClienteSapDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ContactoDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosClientesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfClienteSap;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.PermisosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseAddClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseClientActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseDelClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfClienteSap;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetReporteClientesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseReporteClientesActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUpClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseValidaLoginDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.CriteriosGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.ResponseGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.CriteriosGetInfGetCot;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseCreacionPedidoDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.services.CatalogosService;
import mx.com.grupogigante.gestionvivienda.services.CierreVentaService;
import mx.com.grupogigante.gestionvivienda.services.ClientesService;
import mx.com.grupogigante.gestionvivienda.services.UsuariosService;
import mx.com.grupogigante.gestionvivienda.utils.SessionValidatorSTS;
import mx.com.grupogigante.gestionvivienda.utils.utilsCommon;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Clase controladora para el modulo de Catalogos SAP
 * Fecha de creación: 12/02/2014               
 */
@Controller
public class CatalogosController {
	private Logger log = Logger.getLogger(CatalogosController.class);

	@Autowired
	CatalogosService catalogosService;

	ResponseGetCatalogosDto respCatalogosDTO=new ResponseGetCatalogosDto();
	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para busqueda de clientes SAP
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/ObtieneCatalogos.htm", method = RequestMethod.GET)
	public String returngetClientesSap(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "ObtieneCatalogos";
		 }
		 return redir;
		
	}
	/**
	 * Método @RequestMapping por metodo POST para la consulta de clientes SAP  
	 * 1) obtener clientes encontrados segun los criterios de busqueda establecidos
	 * 
	 * 
	 * @param ModelAtribbute   
	 * @param BindingResult   
	 * @param HttpSession 
	 * @return ResponseClientActionDto objeto de respuesta
	 */
	@RequestMapping(value = "/ObtieneCatalogos.htm", method = RequestMethod.POST)
	public @ResponseBody
	ResponseGetCatalogosDto ObtieneCatalogos(
			@RequestParam(value="idCatalogo") String idCatalogo,
			CriteriosGetInfClienteSap criteriosSap,
			BindingResult result,HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    CriteriosGetCatalogosDto criteriosGetCat = new CriteriosGetCatalogosDto();
		    
		try {
			criteriosGetCat.setI_usuario(sesionValida.getDatos(session,"usuario"));
			criteriosGetCat.setI_id_ut_sup(sesionValida.getDatos(session,"unidad"));
			criteriosGetCat.setI_catalogo(idCatalogo);
			
			respCatalogosDTO = catalogosService.getCatalogos2(criteriosGetCat);
			
		} catch (ViviendaException e) {
			e.getMensajeError();
		}
		return respCatalogosDTO;
	}		
}
