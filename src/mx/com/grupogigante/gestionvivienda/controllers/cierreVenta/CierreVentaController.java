/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 29/08/2012               
 */
package mx.com.grupogigante.gestionvivienda.controllers.cierreVenta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.com.grupogigante.gestionvivienda.controllers.contratos.ContratosController;
import mx.com.grupogigante.gestionvivienda.dao.ConexionManagerDaoImp;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfPag;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseClientActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetConPagosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.CriteriosGetInfGetCot;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseActualizaStatusImpresionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseAddPagosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseCierreVentaActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseDigitalizacionActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseDigitalizacionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseGetInfCotizacionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseGetInfoPagRegDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseObtieneReferenciaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.CriteriosDatosDigitalizacionImageDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.ResponseDatosDigitActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.ResponseUploadFilesDigitDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.services.CierreVentaService;
import mx.com.grupogigante.gestionvivienda.services.ConexionService;
import mx.com.grupogigante.gestionvivienda.services.ContratosService;
import mx.com.grupogigante.gestionvivienda.services.DigitalizacionService;
import mx.com.grupogigante.gestionvivienda.utils.SessionValidatorSTS;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Clase controladora para el modulo de Cierre de venta  Delete
 * Fecha de creación: XX/06/2012             
 */
@Controller
public class CierreVentaController {
	private final static Logger log = Logger.getLogger(ContratosController.class);

	@Autowired
	CierreVentaService cierreVentaService;
	@Autowired
	ConexionService datosImageMap;
	@Autowired
	DigitalizacionService digitService;
	
	@Autowired
	ContratosService contratosService;
	
	ResponseCierreVentaActionDto responseAction = new ResponseCierreVentaActionDto();
	ResponseGetInfCotizacionDto respGetCot=new ResponseGetInfCotizacionDto();
	ResponseAddPagosDto respAddPag=new ResponseAddPagosDto();
	ResponseGetInfoPagRegDto respGetPag=new ResponseGetInfoPagRegDto();
	ResponseGetConPagosDto respGetCon=new ResponseGetConPagosDto();
	
	ResponseDigitalizacionActionDto responseDigitalAction = new ResponseDigitalizacionActionDto();
	ResponseDigitalizacionDto responseDigitalDto = new ResponseDigitalizacionDto();
	ResponseClientActionDto responsePedidoAction = new ResponseClientActionDto();
	ResponseActualizaStatusImpresionDto resGetActualizaImpresionDTO = new ResponseActualizaStatusImpresionDto();
	
	List<String> listaNombreArchivos = new ArrayList<String>();
	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina Seleccion de parametros Cotizacion/Pedidos 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina deredireccion 
	 */	
	@RequestMapping(value = "/CierreVenta.htm", method = RequestMethod.GET)
	public String returnaddCliente(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "CierreVenta";
		 }
		 return redir;
	
}
	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina Resultado de Cotizaciones /pedidos encontrados  
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina deredireccion 
	 */	
	@RequestMapping(value = "/CierreVenta.htm", method = RequestMethod.POST)
	public @ResponseBody
	ResponseCierreVentaActionDto getCot(
			@ModelAttribute(value = "criteriosGetCot") CriteriosGetInfGetCot criterios,
			BindingResult result,HttpSession session) {
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		try {
			criterios.setUsuario(sesionValida.getDatos(session,"usuario"));
			criterios.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
			if(criterios.getAccion()==2)
			{
			respGetCot = cierreVentaService.getBusquedaCot(criterios);
			responseAction.setResponseCotizacion(respGetCot);
			}
							
		} catch (ViviendaException e) {
			e.getMensajeError();
			
		}
		return responseAction;
	}

	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para aguegar criterios de busquedad 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/SeleccionCierreVenta.htm", method = RequestMethod.GET)
	public String returnConfirmacion(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "SeleccionCierreVenta";
		 }
		 return redir;
	
	}
	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para aguegar criterios de busquedad 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/ParamsCierreVenta.htm", method = RequestMethod.GET)
	public String returnParamsCierreVenta(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "ParamsCierreVenta";
		 }
		 return redir;
	
	}
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina Archivero Electrónico 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina deredireccion 
	 */	
	@RequestMapping(value = "/RegistroPagoMensual.htm", method = RequestMethod.GET)
	public String addPagoMensual(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "RegistroPagoMensual";
		 }
		 return redir;
	
}

	/**
	 * Método @RequestMapping por metodo POST para la alta de Pagos Mensuales 
	 * 1) registra Pagos Mensuales 
	 * 
	 * @param ModelAtribbute   
	 * @param BindingResult   
	 * @param HttpSession 
	 * @return ResponseClientActionDto objeto de respuesta
	 */	
	@RequestMapping(value = "/RegistroPagoMensual.htm", method = RequestMethod.POST)
	public @ResponseBody
	ResponseCierreVentaActionDto addPagos(
			@ModelAttribute(value = "CriteriosGetInfGetCot") CriteriosGetInfGetCot criterios,
			BindingResult result, HttpSession session) {
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		
		criterios.setXmlPagos(criterios.getXmlPagos().replace("&", "&amp;"));
		System.out.println( criterios.getXmlPagos());
		
		try {
			criterios.setUsuario(sesionValida.getDatos(session,"usuario"));
			criterios.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
			log.info(criterios);
			
			//TODO JLP
			String[] faseEquipo = criterios.getIdFaseEquipo().split("-");
			String idFase = faseEquipo[0].substring(7,10);
			
			log.info("idFase detectada:" + idFase);
			
			switch (criterios.getAccion()) {
			case 1:
				CriteriosDatosDigitalizacionImageDto criteriosDto = new CriteriosDatosDigitalizacionImageDto();
				criteriosDto.setFase(faseEquipo[0].substring(0, 10));
				criteriosDto.setEquipo(criterios.getIdFaseEquipo());
				criteriosDto.setTipo("02");
				criteriosDto.setTipoa("00");
				criteriosDto.setSubtipo("02");
				criteriosDto.setSubtipoa("01");
				criteriosDto.setEstatus("");
				criteriosDto.setAccion("guardar");
				criteriosDto.setId_usuario(sesionValida.getDatos(session,"usuario"));
				criteriosDto.setIdUTS(sesionValida.getDatos(session,"unidad"));
				
				//Map<String, Object> mapaArchivos= (HashMap<String, Object>)session.getAttribute("registrarArchivoPago");
				Map<String, Object> mapaArchivos= (LinkedHashMap<String, Object>)session.getAttribute("registrarArchivoPago");
				if (mapaArchivos != null) {
					//Set<String> keys = mapaArchivos.keySet();
					
					listaNombreArchivos = new ArrayList<String>();
					for (Map.Entry<String, Object> key : mapaArchivos.entrySet()) {
						MultipartFile file = (MultipartFile)key.getValue();  //mapaArchivos.get(key);
						criteriosDto.setFileData((CommonsMultipartFile)file);
						this.guardaArchivosPago(criteriosDto, session, file);
						log.info("key file:" + key + ", filename:" + file.getOriginalFilename());
					}
					/*for (String key : keys) {
						MultipartFile file = (MultipartFile) mapaArchivos.get(key);
						criteriosDto.setFileData((CommonsMultipartFile)file);
						this.guardaArchivosPago(criteriosDto, session, file);
						log.info("key file:" + key + ", filename:" + file.getOriginalFilename());
					}*/
				}
				session.setAttribute("registrarArchivoPago",null);
				
				respAddPag=cierreVentaService.addPagosMensuales(criterios, listaNombreArchivos);
				responseAction.setResponseAddPagos(respAddPag);
				
				//Si ocurre un error en SAP, se aplica reverso a las imágenes cargadas
				if (respAddPag == null || respAddPag.getMensaje() == null || respAddPag.getMensaje().equals("FAULT")) {
					CriteriosDatosDigitalizacionImageDto dto = new CriteriosDatosDigitalizacionImageDto();
					for (String nombreArchivo : listaNombreArchivos) {
						dto = new CriteriosDatosDigitalizacionImageDto();
						dto.setEquipo(criterios.getIdFaseEquipo());
						dto.setNombreImagen(nombreArchivo);
						new ConexionManagerDaoImp().setDeleteDigitFiles(dto);
					}
				}
				
				/*respAddPag=cierreVentaService.addPagosMensuales(criterios);
				responseAction.setResponseAddPagos(respAddPag);
				
				if (respAddPag != null && respAddPag.getMensaje() != null && respAddPag.getMensaje().equals("SUCCESS")) {
					CriteriosDatosDigitalizacionImageDto criteriosDto = new CriteriosDatosDigitalizacionImageDto();
					criteriosDto.setFase(faseEquipo[0].substring(0, 10));
					criteriosDto.setEquipo(criterios.getIdFaseEquipo());
					criteriosDto.setTipo("02");
					criteriosDto.setTipoa("00");
					criteriosDto.setSubtipo("02");
					criteriosDto.setSubtipoa("01");
					criteriosDto.setEstatus("");
					criteriosDto.setAccion("guardar");
					criteriosDto.setId_usuario(sesionValida.getDatos(session,"usuario"));
					criteriosDto.setIdUTS(sesionValida.getDatos(session,"unidad"));

					
					Map<String, Object> mapaArchivos= (HashMap<String, Object>)session.getAttribute("registrarArchivoPago");
					if (mapaArchivos != null) {
						Set<String> keys = mapaArchivos.keySet();
						for (String key : keys) {
							MultipartFile file = (MultipartFile) mapaArchivos.get(key);
							criteriosDto.setFileData((CommonsMultipartFile)file);
							this.guardaArchivosPago(criteriosDto, session, file);
							//datosImageMap.setDigitFiles(criteriosDto);
							log.info("key file:" + key + ", filename:" + file.getOriginalFilename());
						}
					}
					session.setAttribute("registrarArchivoPago",null);
				}*/
				break;
			default:
				break;
			}			
		} catch (ViviendaException e) {
			e.getMensajeError();
			respAddPag.setMensaje("FAULT");
			respAddPag.setDescripcion(e.getMensajeError());		
			responseAction.setResponseAddPagos(respAddPag);
		}
		return responseAction;
	}
	
	private ResponseDigitalizacionActionDto guardaArchivosPago(CriteriosDatosDigitalizacionImageDto criterios, HttpSession session , MultipartFile file){
		 ResponseUploadFilesDigitDto respValidaDigit = new ResponseUploadFilesDigitDto();
		 ResponseUploadFilesDigitDto respInsertDigit = new ResponseUploadFilesDigitDto();
		 ResponseUploadFilesDigitDto respUpdateDigit = new ResponseUploadFilesDigitDto();
		 ResponseUploadFilesDigitDto respContDigit = new ResponseUploadFilesDigitDto();
		 ResponseUploadFilesDigitDto respAddDigit = new ResponseUploadFilesDigitDto();
		 ResponseDatosDigitActionDto resp = new ResponseDatosDigitActionDto();
		    
			try {						
				//String [] ni=file.getOriginalFilename().split("\\.");
				String fileName=file.getOriginalFilename();
				String ext="";
				int dotPos = fileName.lastIndexOf(".");
				ext = fileName.substring(dotPos);
			
				criterios.setImagenMaping(file);													
				
				respValidaDigit=datosImageMap.validaExisteDigit(criterios);
			
				respContDigit=datosImageMap.getContador(criterios);
					
					if(respContDigit.getMensaje().equals("SUCCESS"))
					{		
						try
						{
							criterios.setConsecutivo(""+(respContDigit.getDatosContador().get(0).getContador_consecutivo()+1));
						}
						catch(Exception e)
						{
							criterios.setConsecutivo("1");
						}
						String consec_= criterios.getConsecutivo();
						if(consec_.length()==1)
						{
							consec_="0"+consec_;
						}
						log.info("consecutigo" + consec_);
						criterios.setNombreImagen(criterios.getEquipo()+"_"+criterios.getSubtipo()+"_"+criterios.getSubtipoa()+"_"+consec_+ext);
						listaNombreArchivos.add(criterios.getNombreImagen());
						
						respInsertDigit=datosImageMap.setDigitFiles(criterios);
						
						if(respInsertDigit.getMensaje().equals("SUCCESS"))
						{						
							respInsertDigit.setMensaje("SUCCESS");
							respInsertDigit.setDescripcion("Archivo(s) digitalizado (s)  se guardo satisfactoriamente");//+criterios.getNombreImagen());
							respAddDigit=digitService.setDataDigit(criterios);
							responseDigitalDto.setMensaje(respInsertDigit.getMensaje());
							responseDigitalDto.setDescripcion(respInsertDigit.getDescripcion());
						}
					}
					else
					{
						respInsertDigit.setMensaje("FAULT");
						respInsertDigit.setDescripcion("Hubo un error en la actualizacion del contador.");
						responseDigitalDto.setMensaje(respInsertDigit.getMensaje());
						responseDigitalDto.setDescripcion(respInsertDigit.getDescripcion());
					}
					resp.setRespDatosUploadDigitFile(respInsertDigit);								
				
			} catch (ViviendaException e) {
				respUpdateDigit.setMensaje("FAULT");
				respUpdateDigit.setDescripcion(e.getMessage());
				responseDigitalDto.setMensaje(respInsertDigit.getMensaje());
				responseDigitalDto.setDescripcion(respInsertDigit.getDescripcion());
			}
			
		//session.setAttribute("registrarArchivoPago",null);
		responseDigitalAction.setResponseDigitalizacionDto(responseDigitalDto);
	
		return responseDigitalAction;

	}
	
	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para aguegar criterios de busquedad 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/MonitorPagos.htm", method = RequestMethod.GET)
	public String returnSeleccionMonitor(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "MonitorPagos";
		 }
		 return redir;
	
	}
	
	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina Archivero Electrónico 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina deredireccion 
	 */	
	@RequestMapping(value = "/MonitorPagos.htm", method = RequestMethod.POST)
	public @ResponseBody
	ResponseCierreVentaActionDto getMonitorPagos(
			@ModelAttribute(value = "criteriosGetCot") CriteriosGetInfPag criteriosPagos,
			BindingResult result,HttpSession session) {
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		try {
			criteriosPagos.setUsuario(sesionValida.getDatos(session,"usuario"));
			criteriosPagos.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
			responseAction.setResponsePagosReg(cierreVentaService.getBusquedaPagos(criteriosPagos));
							
		} catch (ViviendaException e) {
			e.getMensajeError();
			
		}	
		return responseAction;
	}
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para el detalle de los pagos 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/DetallePago.htm", method = RequestMethod.GET)
	public String returnDetallePago(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "DetallePago";
		 }
		 return redir;
	
	}

	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina matchcode clientes 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * accion 
	 * 1) busca los clientes  
	 * @return cadena de nueva pagina deredireccion 
	 */	
	@RequestMapping(value = "/ParamsCierreVenta.htm", method = RequestMethod.POST)
	public @ResponseBody
	ResponseCierreVentaActionDto getClientesMatch(
			@ModelAttribute(value = "criteriosGetCon") CriteriosGetInfGetCot criteriosCli,
			BindingResult result,HttpSession session) {
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();   
		try {
			criteriosCli.setUsuario(sesionValida.getDatos(session,"usuario"));
			criteriosCli.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
			
			switch (criteriosCli.getAccion()) { 
			case 1:
				responseAction.setResponseCotizacion((cierreVentaService.getBusquedaCot(criteriosCli)));
				//responseAction.getResponseCotizacion().getObjCotPedHdrList()
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
	 * redireccionamiento de la pagina para visualizar los pagos no conciliados 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/ConciliacionPagos.htm", method = RequestMethod.GET)
	public String returnConciliacionPagos(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "ConciliacionPagos";
		 }
		 return redir;
	
	}	

	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina la Conciliacion de Pagos 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina deredireccion 
	 */	
	@RequestMapping(value = "/ConciliacionPagos.htm", method = RequestMethod.POST)
	public @ResponseBody
	ResponseCierreVentaActionDto getPagosNoConciliados(
			@ModelAttribute(value = "criteriosGetCon") CriteriosGetInfPag criteriosConPagos,
			BindingResult result,HttpSession session) {
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		try {
			criteriosConPagos.setUsuario(sesionValida.getDatos(session,"usuario"));
			criteriosConPagos.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
			log.info("entrando a conciliacion de pagos");
			
			switch (criteriosConPagos.getAccion()) {
			case 1:
				responseAction.setResponseConciPagos(cierreVentaService.getPagosConciliacion(criteriosConPagos));
				break;
			case 2://GUARDAR LIGA
				log.info("GUARDANDO LIGA");
				responseAction.setResponseConciPagos(cierreVentaService.getPagosConciliacion(criteriosConPagos));
				log.info("LIGA GUARDADA");
				break;

			default:
				break;
			}
			
							
		} catch (ViviendaException e) {
			log.error("ERROR:", e);
			e.getMensajeError();
			
		}
		return responseAction;
	}	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para visualizar los pagos no conciliados 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/SeleccionMoniPagos.htm", method = RequestMethod.GET)
	public String returnSeleccionMoniPagos(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "SeleccionMoniPagos";
		 }
		 return redir;
	
	}
	/**	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para visualizar los params moni pagos 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/ParamsMoniPagos.htm", method = RequestMethod.GET)
	public String returnParamsMoniPagos(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "ParamsMoniPagos";
		 }
		 return redir;
	
	}
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para el detalle de los pagos 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/DatosPago.htm", method = RequestMethod.GET)
	public String returnDatosPago(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "DatosPago";
		 }
		 return redir;
	
	}
	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para el detalle de los pagos 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/CierreVentasBlock.htm", method = RequestMethod.GET)
	public String returnBloqueaCierreVentas(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "CierreVentasBlock";
		 }
		 return redir;
	
	}
	
	@RequestMapping(value = "/CierreVentasBlock.htm", method = RequestMethod.POST)
	public @ResponseBody ResponseCierreVentaActionDto getProcesoCierreVenta(
			@ModelAttribute(value = "criteriosGetCon") CriteriosGetInfPag criteriosConPagos,
			BindingResult result,HttpSession session) {
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		try {
			criteriosConPagos.setUsuario(sesionValida.getDatos(session,"usuario"));
			criteriosConPagos.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
			
			switch (criteriosConPagos.getAccion()) {
			case 1:
				responseAction.setResponseConciPagos(cierreVentaService.getPagosConciliacion(criteriosConPagos));
				break;
			case 2:
				responseAction.setResponseConciPagos(cierreVentaService.getPagosConciliacion(criteriosConPagos));
				break;

			default:
				break;
			}
			
							
		} catch (ViviendaException e) {
			e.getMensajeError();
			
		}
		return responseAction;
	}	
	
	@RequestMapping(value = "/MonitorPagosPrincipal.htm", method = RequestMethod.GET)
	public String returnMonitorPagosPrincipal(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "MonitorPagosPrincipal";
		 }
		 return redir;
	
	}
	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para el detalle de los pagos 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/ModificaDetallePago.htm", method = RequestMethod.GET)
	public String returnModificaDetallePago(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "ModificaDetallePago";
		 }
		 return redir;
	
	}
	
	@RequestMapping(value="/DatosPago.htm", method = RequestMethod.POST)
	public String setGuardaSessionFile(@ModelAttribute(value="criterios")CriteriosDatosDigitalizacionImageDto criterios, BindingResult result, HttpServletRequest request, HttpServletResponse response, HttpSession session, Map model) {
		log.info("ingresando a guardar archivos temporalmente");
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
//		    ResponseUploadFilesDigitDto respValidaDigit = new ResponseUploadFilesDigitDto();
//		    ResponseUploadFilesDigitDto respInsertDigit = new ResponseUploadFilesDigitDto();
//		    ResponseUploadFilesDigitDto respUpdateDigit = new ResponseUploadFilesDigitDto();
//		    ResponseUploadFilesDigitDto respContDigit = new ResponseUploadFilesDigitDto();
//		    ResponseUploadFilesDigitDto respAddDigit = new ResponseUploadFilesDigitDto();
//		    ResponseDatosDigitActionDto resp = new ResponseDatosDigitActionDto();
		    log.info(criterios);
			criterios.setIdUTS(sesionValida.getDatos(session,"unidad"));
			criterios.setId_usuario(sesionValida.getDatos(session,"usuario"));
					
			MultipartFile file = criterios.getFileData();
						
			//session.setAttribute("registrarArchivoPago", file);
			
			//Map<String, Object> mapaArchivos= (HashMap<String, Object>)session.getAttribute("registrarArchivoPago");
			LinkedHashMap<String, Object> mapaArchivos= (LinkedHashMap<String, Object>)session.getAttribute("registrarArchivoPago");
						
			log.info("archivo ingresado:" + file.getOriginalFilename());
			
			
			//mapaArchivos = (mapaArchivos == null? new HashMap<String, Object>():mapaArchivos);
			mapaArchivos = (mapaArchivos == null? new LinkedHashMap<String, Object>():mapaArchivos);
			
			String idArchivoHash = criterios.getFolioOper() + "|" + criterios.getFchPago() + "|" + criterios.getHrPago() + ":00|" + criterios.getMonto() + "|" + criterios.getRefer();
			log.info("ingresando el archivo id:" + idArchivoHash);
			mapaArchivos.put(idArchivoHash, file);
			session.setAttribute("registrarArchivoPago", mapaArchivos);
			
			return null;
	}
	
	@RequestMapping(value = "/EliminaPagoMensualTmp.htm", method = RequestMethod.POST)
	public void eliminaPagosTmp(@ModelAttribute(value="criterios")CriteriosDatosDigitalizacionImageDto criterios, HttpSession session) {
		try {
			Map<String, Object> mapaArchivos= (HashMap<String, Object>)session.getAttribute("registrarArchivoPago");
			log.info(criterios);
			String idArchivoHash = criterios.getFolioOper() + "|" + criterios.getFchPago() + "|" + criterios.getHrPago() + "|" + criterios.getMonto() + "|" + criterios.getRefer();
			if (mapaArchivos != null) {
				log.info("eliminando el archivo id:" + idArchivoHash);
				log.info("antes " + mapaArchivos.get(idArchivoHash));
				mapaArchivos.remove(idArchivoHash);
				log.info("despues " + mapaArchivos.get(idArchivoHash));
			}
		} catch (Exception e) {
			log.error("ERROR:", e);
		}
	}

	
	
	@RequestMapping(value="/DetallePago.htm", method = RequestMethod.POST)
	public  @ResponseBody ResponseDigitalizacionActionDto setGuardaDigitalizacionFile(@ModelAttribute(value="criterios")CriteriosDatosDigitalizacionImageDto criterios, BindingResult result, HttpServletRequest request, HttpServletResponse response, HttpSession session, Map model) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    ResponseUploadFilesDigitDto respValidaDigit = new ResponseUploadFilesDigitDto();
		    ResponseUploadFilesDigitDto respInsertDigit = new ResponseUploadFilesDigitDto();
		    ResponseUploadFilesDigitDto respUpdateDigit = new ResponseUploadFilesDigitDto();
		    ResponseUploadFilesDigitDto respContDigit = new ResponseUploadFilesDigitDto();
		    ResponseUploadFilesDigitDto respAddDigit = new ResponseUploadFilesDigitDto();
		    ResponseDatosDigitActionDto resp = new ResponseDatosDigitActionDto();
		    
			criterios.setIdUTS(sesionValida.getDatos(session,"unidad"));
			criterios.setId_usuario(sesionValida.getDatos(session,"usuario"));
			
			
			MultipartFile fileRegistroPago=null;
			MultipartFile file=null;
			
			//fileRegistroPago= (MultipartFile)session.getAttribute("registrarArchivoPago");
			//Map<String, Object> mapaArchivos= (HashMap<String, Object>)session.getAttribute("registrarArchivoPago");
			Map<String, Object> mapaArchivos= (LinkedHashMap<String, Object>)session.getAttribute("registrarArchivoPago");
			//if (fileRegistroPago==null){
			if (mapaArchivos==null){
				responseDigitalDto.setMensaje("SUCCESS");
				responseDigitalDto.setDescripcion("Se actualizo el pago de forma exitosa.No se encontro ningun archivo pdf u otro a subir.");
				responseDigitalAction.setResponseDigitalizacionDto(responseDigitalDto);
				return responseDigitalAction;
			}
			ResponseDigitalizacionActionDto respuesta=null;
			Iterator it = mapaArchivos.entrySet().iterator();
			int z=0;
			while (it.hasNext()) {
			    Map.Entry e = (Map.Entry)it.next();
			    System.out.println(e.getKey() + " " + e.getValue());
			    CommonsMultipartFile archivoActual = (CommonsMultipartFile) e.getValue();
			    criterios.setFileData(archivoActual);
				file=criterios.getFileData();
				respuesta= this.guardaImagenes(criterios, request, session, file);
				z++;
			}
			if (z>0){
				if (respuesta.getResponseDigitalizacionDto().getMensaje().equals("SUCCESS")){
					responseDigitalDto.setMensaje("SUCCESS");
					responseDigitalDto.setDescripcion("Se subieron: "+ z + " archivo(s) digitalizados.");
					responseDigitalAction.setResponseDigitalizacionDto(responseDigitalDto);
					respuesta=responseDigitalAction;
				}
			}
			
					
			return respuesta;
	}

	
	private ResponseDigitalizacionActionDto guardaImagenes(CriteriosDatosDigitalizacionImageDto criterios, HttpServletRequest request, HttpSession session , MultipartFile file){
		 ResponseUploadFilesDigitDto respValidaDigit = new ResponseUploadFilesDigitDto();
		 ResponseUploadFilesDigitDto respInsertDigit = new ResponseUploadFilesDigitDto();
		 ResponseUploadFilesDigitDto respUpdateDigit = new ResponseUploadFilesDigitDto();
		 ResponseUploadFilesDigitDto respContDigit = new ResponseUploadFilesDigitDto();
		 ResponseUploadFilesDigitDto respAddDigit = new ResponseUploadFilesDigitDto();
		 ResponseDatosDigitActionDto resp = new ResponseDatosDigitActionDto();
		    
		String datos []= request.getParameter("datos").split("\\|");
		
		//$("#cmb_faces").val()+"|"+$("#cmb_equipo").val()+"|"+itemTipos.id_ticket_area+"|"+itemTipos.id_ticket_file+"|"+itemSubTipos.id_ticket_area+"|"+itemSubTipos.id_ticket_file+"|"+itemEstatus.id_ticket_stat+"|"+itemEstatus.id_ticket_statx
		
		criterios.setFase(datos[0]);
		criterios.setEquipo(datos[1]);
		criterios.setTipo(datos[2]);
		criterios.setTipoa(datos[3]);
		criterios.setSubtipo(datos[4]);
		criterios.setSubtipoa(datos[5]);
		criterios.setEstatus(datos[6].trim());			
		
				  
		
		if(request.getParameter("accion").equals("guardar"))
		{
			
			try {						
				//String [] ni=file.getOriginalFilename().split("\\.");
				String fileName=file.getOriginalFilename();
				String ext="";
				int dotPos = fileName.lastIndexOf(".");
				ext = fileName.substring(dotPos);
			
				criterios.setImagenMaping(file);													
				
				respValidaDigit=datosImageMap.validaExisteDigit(criterios);
			
				respContDigit=datosImageMap.getContador(criterios);
					
					if(respContDigit.getMensaje().equals("SUCCESS"))
					{		
						try
						{
							criterios.setConsecutivo(""+(respContDigit.getDatosContador().get(0).getContador_consecutivo()+1));
						}
						catch(Exception e)
						{
							criterios.setConsecutivo("1");
						}
						String consec_= criterios.getConsecutivo();
						if(consec_.length()==1)
						{
							consec_="0"+consec_;
						}
						criterios.setNombreImagen(criterios.getEquipo()+"_"+criterios.getSubtipo()+"_"+criterios.getSubtipoa()+"_"+consec_+ext);
						
						respInsertDigit=datosImageMap.setDigitFiles(criterios);
						
						if(respInsertDigit.getMensaje().equals("SUCCESS"))
						{						
							respInsertDigit.setMensaje("SUCCESS");
							respInsertDigit.setDescripcion("Archivo(s) digitalizado (s)  se guardo satisfactoriamente");//+criterios.getNombreImagen());
							respAddDigit=digitService.setDataDigit(criterios);
							responseDigitalDto.setMensaje(respInsertDigit.getMensaje());
							responseDigitalDto.setDescripcion(respInsertDigit.getDescripcion());
						}
					}
					else
					{
						respInsertDigit.setMensaje("FAULT");
						respInsertDigit.setDescripcion("Hubo un error en la actualizacion del contador.");
						responseDigitalDto.setMensaje(respInsertDigit.getMensaje());
						responseDigitalDto.setDescripcion(respInsertDigit.getDescripcion());
					}
					resp.setRespDatosUploadDigitFile(respInsertDigit);								
				
			} catch (ViviendaException e) {
				respUpdateDigit.setMensaje("FAULT");
				respUpdateDigit.setDescripcion(e.getMessage());
				responseDigitalDto.setMensaje(respInsertDigit.getMensaje());
				responseDigitalDto.setDescripcion(respInsertDigit.getDescripcion());
			}
			
		}
		session.setAttribute("registrarArchivoPago",null);
		responseDigitalAction.setResponseDigitalizacionDto(responseDigitalDto);
	
		return responseDigitalAction;

	}
	
	
	@RequestMapping(value = "/ModificaDetallePago.htm", method = RequestMethod.POST)
	public @ResponseBody ResponseClientActionDto 
			actualizaStatusImpresion(@ModelAttribute(value = "criterioActualiza") CriteriosGetInfCarCliente criterios, 
					ModelMap map,HttpSession session,HttpServletRequest request, HttpServletResponse response) {
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		try {
			criterios.setUsuario(sesionValida.getDatos(session,"usuario"));
			criterios.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
			
			switch (criterios.getAccion()) {
			case 1:
				resGetActualizaImpresionDTO = contratosService.actualizaStatusImpresion(criterios);
				responsePedidoAction.setResActualizaImpresion(resGetActualizaImpresionDTO);
				break;
			case 2:
				session.removeAttribute("registrarArchivoPago");
			default:
				break;
			}
			
		} catch (ViviendaException e) {
			resGetActualizaImpresionDTO.setMensaje("FAULT");
			resGetActualizaImpresionDTO.setMensaje(e.getMensajeError());		
			responsePedidoAction.setResActualizaImpresion(resGetActualizaImpresionDTO);
		}
		return responsePedidoAction;
	
	}
	
	/**
	 * Método @RequestMapping por metodo POST para la obtención de la 
	 * referencia de un pago. 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return ResponseObtieneReferenciaDto 
	 */	
	@RequestMapping(value = "/ObtieneReferencia.htm", method = RequestMethod.POST)
	public @ResponseBody ResponseObtieneReferenciaDto getReferencia(
			@RequestParam(value = "conceptoReferencia") String conceptoReferencia,
			ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) {
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		ResponseObtieneReferenciaDto referenciaDto = new ResponseObtieneReferenciaDto();
		try {			
			referenciaDto = cierreVentaService.getReferencia(conceptoReferencia);
		} catch (ViviendaException e) {
			e.getMensajeError();
		}	
		return referenciaDto;
	}
}
