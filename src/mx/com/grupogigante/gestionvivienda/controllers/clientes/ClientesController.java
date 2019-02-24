/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.controllers.clientes;

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
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Clase controladora para el modulo de Cartera de Clientes y Clientes SAP
 * Fecha de creación: XX/06/2012               
 */
@Controller
public class ClientesController {
	private Logger log = Logger.getLogger(ClientesController.class);

	@Autowired
	ClientesService clientesService;
	@Autowired
	UsuariosService vendedorService;
	@Autowired
	CatalogosService catalogosService;
	@Autowired
	CierreVentaService cierreVentaService;

	ResponseClientActionDto responseAction = new ResponseClientActionDto();
	ResponseGetInfCarCliente resGetClientesDTO = new ResponseGetInfCarCliente();
	ResponseAddClienteDto resAddCliDTO = new ResponseAddClienteDto();
	ResponseDelClienteDto resDelCliDTO = new ResponseDelClienteDto();
	ResponseUpClienteDto resUpCliDTO=new ResponseUpClienteDto();
	ResponseGetUsuariosDto resUsr = new ResponseGetUsuariosDto();
	ResponseGetCatalogosDto respCatalogosDTO=new ResponseGetCatalogosDto();
	ResponseGetInfClienteSap resGetClientesSapDTO = new ResponseGetInfClienteSap();
	ResponseValidaLoginDto resValidaLogin=new ResponseValidaLoginDto();
	ResponseCreacionPedidoDto resPedido=new ResponseCreacionPedidoDto();
	
	
	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para aguegar clientes RegistroCarCliente 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina deredireccion 
	 */	
	@RequestMapping(value = "/RegistroCarCliente.htm", method = RequestMethod.GET)
	public String returnaddCliente(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "RegistroCarCliente";
		 }
		 return redir;
	
	}

	/**
	 * Método @RequestMapping por metodo POST para la alta de un cliente 
	 * 1) consulta via de contactos para el combo y vendedores
	 * 2) registra en cartera  cliente 
	 * 3) actualiza en cartera cliente 
	 * @param ModelAtribbute   
	 * @param BindingResult   
	 * @param HttpSession 
	 * @return ResponseClientActionDto objeto de respuesta
	 */	
	@RequestMapping(value = "/RegistroCarCliente.htm", method = RequestMethod.POST)
	public @ResponseBody
	ResponseClientActionDto addCliente(
			@ModelAttribute(value = "criteriosUsr") CriteriosGetInfCarCliente criterios,
			BindingResult result,HttpSession session) {
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		CriteriosUsuariosDto usr = new CriteriosUsuariosDto();
		ResponseGetUsuariosDto resUserDto;
		List<UsuarioDto> usuariosPermisoVenta = new ArrayList<UsuarioDto>();
		utilsCommon utils=new utilsCommon();
		try {
			criterios.setUsuario(sesionValida.getDatos(session,"usuario"));
			criterios.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
			switch (criterios.getAccion()) {
			case 1:
				resGetClientesDTO = clientesService.getViaContacto(criterios);
				usr.setId_ut_sup(criterios.getId_ut_sup());
				usr.setUsuario(criterios.getUsuario());
			
				resUserDto = new ResponseGetUsuariosDto();
				resUserDto = vendedorService.getUsuarios(usr);
				
				resUsr.setUsuariosList(utils.vendedoresPermitidos(resUserDto));
				resUsr.setObjUsuariosList(resUsr.getUsuariosList());
				resAddCliDTO.setMensaje("SUCCESS");
				responseAction.setRespGetInfCarCliente(resGetClientesDTO);
				responseAction.setRespGetUsuarios(resUsr);
				break;
			case 2://REGISTRO CARTERA CLIENTES
				resAddCliDTO = clientesService.addCliente(criterios);
				responseAction.setRespAddClienteDto(resAddCliDTO);
				break;
			case 3://MODIFICA CARTERA DE CLIENTES
				resUpCliDTO=clientesService.upCliente(criterios);
				responseAction.setRespUpClienteDto(resUpCliDTO);
				break;
			
			case 5:
				UsuarioDto usrDto = new UsuarioDto();
				usrDto.setUsuario(criterios.getUsrAdm());
				usrDto.setPass(criterios.getPwdAdm());
				usrDto.setId_ut_sup(criterios.getId_ut_sup());
				resValidaLogin = vendedorService.validaLogin(usrDto);
				responseAction.setRespGetInfCarCliente(resGetClientesDTO);
				responseAction.setRespValidaLogin(resValidaLogin);
				if (responseAction.getRespValidaLogin().getMensaje() == "SUCCESS") {
					List<PermisosDto> listaPermisos = vendedorService.getCatalogoPermisos(criterios.getUsrAdm(), criterios.getId_ut_sup());
					log.info("PERMISOS:" + listaPermisos);
					responseAction.getRespValidaLogin().setMensaje("FAIL");
					responseAction.getRespValidaLogin().setDescripcion("ESTE USUARIO NO TIENE PERMISOS PARA ACCION");
					for (int i = 0 ; i < listaPermisos.size() ; i++) {
						if (listaPermisos.get(i).getModule().equals("CAT_CLI") && listaPermisos.get(i).getAuthr().equals("PERMISO_R")) {
							responseAction.getRespValidaLogin().setMensaje("SUCCESS");
							responseAction.getRespValidaLogin().setDescripcion("");
						}
					}
				}

				break;
			default:
				break;
			}
			
		} catch (ViviendaException e) {
			e.getMensajeError();
			resGetClientesDTO.setMensaje("FAULT");
			resGetClientesDTO.setMensaje(e.getMensajeError());		
			responseAction.setRespGetInfCarCliente(resGetClientesDTO);
		}
		return responseAction;
	}

	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para el catalogo de cartera clientes 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/CatalogoCarClientes.htm", method = RequestMethod.GET)
	public String returngetClientes(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "CatalogoCarClientes";
		 }
		 return redir;
	}

	/**
	 * Método @RequestMapping por metodo POST para la administracion de los clientes segun la accion: 
	 * 2) obtener clientes encontrados segun los criterios de busqueda establecidos/Visitas
	 * 3) Agregar visitas del cliente 
	 * 
	 * @param ModelAtribbute   
	 * @param BindingResult   
	 * @param HttpSession 
	 * @return ResponseClientActionDto objeto de respuesta
	 */
	@RequestMapping(value = "/CatalogoCarClientes.htm", method = RequestMethod.POST)
	public @ResponseBody
	ResponseClientActionDto getClientes(
			@ModelAttribute(value = "traerClientes") CriteriosGetInfCarCliente criterios,
			BindingResult result,HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    log.info("Entrando a metodo clientes:" + criterios.getAccion());
			if(!sesionValida.validaSesion(session)) {
				responseAction = new ResponseClientActionDto();
				ResponseGetInfCarCliente responseCliente = new ResponseGetInfCarCliente();
				responseCliente.setMensaje("LOGOUT");
				responseCliente.setDescripcion("");
				responseAction.setRespGetInfCarCliente(responseCliente);
			    log.info("Sesion invalida");
				return responseAction;
			 }
		    log.info("Sesion valida");
		try {
			criterios.setUsuario(sesionValida.getDatos(session,"usuario"));
			criterios.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
			switch (criterios.getAccion()) {
			case 2:
				resGetClientesDTO = clientesService.getClientes(criterios);
				responseAction.setRespGetInfCarCliente(resGetClientesDTO);
				break;
			case 3:
				resAddCliDTO = clientesService.addVisitaCliente(criterios);
				responseAction.setRespAddClienteDto(resAddCliDTO);
				break;
			case 4:
				resGetClientesDTO = clientesService.getClientePorId(criterios);
				responseAction.setRespGetInfCarCliente(resGetClientesDTO);
				break;
				
			default:
				break;
			}
		

		} catch (ViviendaException e) {
			log.error("ERROR:", e);
			//e.getMensajeError();
		}
		return responseAction;
	}
	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para el reporte de clientes
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value="/ReporteClientes.htm", method= RequestMethod.GET)
	public String returnReporteClientes(ModelMap map,HttpSession session) {		
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "ReporteClientes";
		 }
		 return redir;
	
	 }
	
	/**
	 * Método @RequestMapping por metodo POST para los reportes de los clientes en donde accion:
	 * 1) getVendedores 
	 * 2) getReporteClientes
	 * 
	 * @param ModelAtribbute   
	 * @param BindingResult   
	 * @param HttpSession 
	 * @return ResponseReporteClientesActionDto objeto de respuesta
	 */
	@RequestMapping(value="/ReporteClientes.htm",method=RequestMethod.POST)
	public @ResponseBody ResponseReporteClientesActionDto getReporteClientes(@ModelAttribute(value="criteriosGetReporteClientes")CriteriosClientesDto criterios, BindingResult result, HttpSession session){
		ResponseReporteClientesActionDto resp = new ResponseReporteClientesActionDto();		
		ResponseGetReporteClientesDto resultReporte = new ResponseGetReporteClientesDto();
		ResponseGetUsuariosDto resUserDto;
		utilsCommon utils=new utilsCommon();
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
			try {
				criterios.setUsuario(sesionValida.getDatos(session,"usuario"));
				criterios.setId_ut_sup(sesionValida.getDatos(session,"unidad"));	
			if(criterios.getAccion().equals("getVendedores"))
			{
				CriteriosUsuariosDto user = new CriteriosUsuariosDto();
				user.setId_ut_sup(criterios.getId_ut_sup());
				user.setUsuario(criterios.getUsuario());
				resUserDto = new ResponseGetUsuariosDto();
				resUserDto = vendedorService.getUsuarios(user);
				utils.vendedoresPermitidos(resUserDto);
				resp.setResGetClientesDTO(resUserDto);
			}
			else if(criterios.getAccion().equals("getReporteClientes"))
			{
				SAXReader reader = new SAXReader();
				org.dom4j.Document doc = reader.read(new StringReader(criterios.getStrListaVendedores()));
				org.dom4j.Element root = doc.getRootElement();
			    
				ArrayList<UsuarioDto> usrListDto = new ArrayList<UsuarioDto>();
				
				for (java.util.Iterator i = root.elementIterator( "vendedor" ); i.hasNext(); ) {
					org.dom4j.Element element = (org.dom4j.Element) i.next();
					UsuarioDto usrDto = new UsuarioDto();
					usrDto.setId_ut_sup_cm(element.node(0).getText());
					usrDto.setUsuario_cm(element.node(1).getText());
					usrListDto.add(usrDto);
		        }
				criterios.setListaVendedores(usrListDto);
				resultReporte = clientesService.getReporteClientes(criterios);			
				resp.setResultReporte(resultReporte);
			}
		}
		catch (ViviendaException e) 
		{			
			e.getMensajeError();			
			resultReporte.setMensaje("FAULT");
			resultReporte.setDescripcion(e.getMensajeError());
			resp.setResultReporte(resultReporte);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return resp;
	}
	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para aguegar clientes RegistroCliente SAP
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina deredireccion 
	 */	
	@RequestMapping(value = "/RegistroCliente.htm", method = RequestMethod.GET)
	public String returnregistroCliente(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "RegistroCliente";
		 }
		 return redir;
	
	}

	/**
	 * Método @RequestMapping por metodo POST para la alta de un cliente 
	 * 1) llena catalogos 
	 * 2) consulta de clientes 
	 * 3) registra un usuario
	 * 
	 * @param ModelAtribbute   
	 * @param BindingResult   
	 * @param HttpSession 
	 * @return ResponseClientActionDto objeto de respuesta
	 */	
	@RequestMapping(value = "/RegistroCliente.htm", method = RequestMethod.POST)
	public @ResponseBody
	ResponseClientActionDto registroCliente(
			@ModelAttribute(value = "cliente") ClienteSapDto datosCliente,@ModelAttribute(value = "contacto") ContactoDto datosContacto,
			BindingResult result,  HttpServletRequest request, HttpServletResponse response, HttpSession session) {
	
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		CriteriosGetCatalogosDto criteriosGetCat = new CriteriosGetCatalogosDto();
		ResponseGetUsuariosDto resUserDto;
		List<UsuarioDto> usuariosPermisoVenta = new ArrayList<UsuarioDto>();
		utilsCommon utils=new utilsCommon();
		try {
			criteriosGetCat.setI_usuario(sesionValida.getDatos(session,"usuario"));
 			criteriosGetCat.setI_id_ut_sup(sesionValida.getDatos(session,"unidad"));
 			datosCliente.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
			datosCliente.setUsuario(sesionValida.getDatos(session,"usuario"));
			
			switch (datosCliente.getAccion()) {
			case 1:
				criteriosGetCat.setI_paises('X');
				criteriosGetCat.setId_pais("MX");
				criteriosGetCat.setI_regiones('X');
				criteriosGetCat.setI_sexo('X');
				criteriosGetCat.setI_tratamientos('X');
				criteriosGetCat.setI_edo_civil('X');
				respCatalogosDTO = catalogosService.getCatalogos(criteriosGetCat);
				responseAction.setRespGetCatalogos(respCatalogosDTO);
			break;
			case 2:
				criteriosGetCat.setId_pais(datosCliente.getId_pais());
				criteriosGetCat.setI_regiones('X');
				respCatalogosDTO = catalogosService.getCatalogos(criteriosGetCat);
				responseAction.setRespGetCatalogos(respCatalogosDTO);
			break;
			case 3:
				try
				{
					datosCliente.setCarcli(request.getParameter("carcli"));
				}
				catch(Exception e)
				{
					datosCliente.setCarcli("");
				}
				resAddCliDTO = clientesService.addClienteSap(datosCliente,datosContacto);
				responseAction.setRespAddClienteDto(resAddCliDTO);
			
			
			break;
			
			case 4:
				int cotizacion=Integer.parseInt(request.getParameter("idCotizacion"));
				CriteriosGetInfGetCot critPedido= new CriteriosGetInfGetCot();
				critPedido.setId_ut_sup(criteriosGetCat.getI_id_ut_sup());
				critPedido.setUsuario(criteriosGetCat.getI_usuario());
				critPedido.setCotPed(cotizacion);
				resPedido=cierreVentaService.addPedido(critPedido);			
				responseAction.setResPedido(resPedido);
			break;

			default:
				break;
			}
			
			
			

		} catch (ViviendaException e) {
			e.getMensajeError();
			responseAction.setRespGetCatalogos(respCatalogosDTO);
		}
		return responseAction;
	}
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para la pantalla de Confirmacion
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/Confirmacion.htm", method = RequestMethod.GET)
	public String returnConfirmacion(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "Confirmacion";
		 }
		 return redir;
	
	}
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para aguegar Confirmacion de la Reasignacion
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/ConfirmacionReasignacion.htm", method = RequestMethod.GET)
	public String returnConfirmacionReasignacion(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "ConfirmacionReasignacion";
		 }
		 return redir;
	
	}
	/**
	 * Método @RequestMapping por metodo POST para la administracion de los clientes segun la accion: 
	 * 2) obtener clientes encontrados segun los criterios de busqueda establecidos/Visitas
	 * 3) Agregar visitas del cliente 
	 * 
	 * @param ModelAtribbute   
	 * @param BindingResult   
	 * @param HttpSession 
	 * @return ResponseClientActionDto objeto de respuesta
	 */
	@RequestMapping(value = "/Confirmacion.htm", method = RequestMethod.POST)
	public @ResponseBody
	ResponseClientActionDto getEliminaClientes(
			@ModelAttribute(value = "traerClientes") CriteriosGetInfCarCliente criterios,
			BindingResult result,HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		try {
			criterios.setUsuario(sesionValida.getDatos(session,"usuario"));
			criterios.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
			resDelCliDTO = clientesService.delCliente(criterios);
			responseAction.setRespDelClienteDto(resDelCliDTO);
		
		} catch (ViviendaException e) {
			e.getMensajeError();
		}
		return responseAction;
	}

	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para busqueda de clientes SAP
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/CatalogoClientes.htm", method = RequestMethod.GET)
	public String returngetClientesSap(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "CatalogoClientes";
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
	@RequestMapping(value = "/CatalogoClientes.htm", method = RequestMethod.POST)
	public @ResponseBody
	ResponseClientActionDto getEliminaClientes(
			@ModelAttribute(value = "traerClientes") CriteriosGetInfClienteSap criteriosSap,
			BindingResult result,HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    CriteriosGetCatalogosDto criteriosGetCat = new CriteriosGetCatalogosDto();
		    
		try {
			criteriosSap.setUsuario(sesionValida.getDatos(session,"usuario"));
			criteriosSap.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
			
			switch (criteriosSap.getAccion()) {
			case 1:
				criteriosGetCat.setI_id_ut_sup(criteriosSap.getId_ut_sup());
				criteriosGetCat.setI_usuario(criteriosSap.getUsuario());
				criteriosGetCat.setI_paises('X');
				criteriosGetCat.setId_pais("MX");
				criteriosGetCat.setI_regiones('X');
	   		    criteriosGetCat.setI_sexo('X');
				criteriosGetCat.setI_tratamientos('X');
				criteriosGetCat.setI_edo_civil('X');
				respCatalogosDTO = catalogosService.getCatalogos(criteriosGetCat);
				resGetClientesSapDTO = clientesService.getClientesSap(criteriosSap,respCatalogosDTO);
				responseAction.setRespGetInfSapCliente(resGetClientesSapDTO);	
				break;
			
			default:
				break;
			}
			
		} catch (ViviendaException e) {
			e.getMensajeError();
		}
		return responseAction;
	}	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para busqueda de clientes SAP
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/LogError.htm", method = RequestMethod.GET)
	public String returngetLogError(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "LogError";
		 }
		 return redir;
		
	}
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para parametros de seleccion Cartera de Clientes 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/SeleccionCarCli.htm", method = RequestMethod.GET)
	public String returngetSeleccionCarCli(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "SeleccionCarCli";
		 }
		 return redir;
//		return "SeleccionCarCli";
	}
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para parametros 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/ParamsCarCli.htm", method = RequestMethod.GET)
	public String returngetParamsCarCli(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "ParamsCarCli";
		 }
		 return redir;
//		 return "ParamsCarCli";
	}
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para el catalogo de clientes 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/BlankScreen.htm", method = RequestMethod.GET)
	public String returngetBlankScreen(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "BlankScreen";
		 }
		 return redir;
	}
	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para parametros de seleccion Cartera de Clientes 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/SeleccionCliSap.htm", method = RequestMethod.GET)
	public String returngetSeleccionCliSap(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "SeleccionCliSap";
		 }
		 return redir;
	}
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para parametros 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/ParamsCliSap.htm", method = RequestMethod.GET)
	public String returngetParamsCliSap(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "ParamsCliSap";
		 }
		 return redir;
	}
	
	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para aguegar Clientes Consulta 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina deredireccion 
	 */	
	@RequestMapping(value = "/ClienteConsulta.htm", method = RequestMethod.GET)
	public String returnConsultaCliente(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "ClienteConsulta";
		 }
		 return redir;
	
	}
	
	
	@RequestMapping(value = "/AvisaRegistroVisita.htm", method = RequestMethod.GET)
	public String returnAvisaRegistroVisita(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "AvisaRegistroVisita";
		 }
		 return redir;
	
	}
	
	
	@RequestMapping(value = "/AvisaCambiosClienteCartera.htm", method = RequestMethod.GET)
	public String returnAvisaCambiosCarteraCliente(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "AvisaCambiosClienteCartera";
		 }
		 return redir;
	
	}
	
}
