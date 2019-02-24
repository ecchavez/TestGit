/**
 * @author Edwin Carlos Chavez Rivera / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 26/12/2016               
 */
package mx.com.grupogigante.gestionvivienda.controllers.ticket;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import mx.com.grupogigante.gestionvivienda.dao.CatalogosDao;
import mx.com.grupogigante.gestionvivienda.dao.TicketsDao;
import mx.com.grupogigante.gestionvivienda.dao.report.BancoDao;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetTicket;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetTicketConstruccion;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosUbicacionesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.EquipoDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseAddTicketDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetUtInfSupDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseLogConstruccionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseTicketConstruccionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionesActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.TicketHeaderDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.UbicacionTecnicaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.CatalogosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.CriteriosGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.ResponseGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.domain.vo.ClienteDatosTicketVo;
import mx.com.grupogigante.gestionvivienda.domain.vo.ListaFicheros;
import mx.com.grupogigante.gestionvivienda.domain.vo.UpdateViciosVo;
import mx.com.grupogigante.gestionvivienda.domain.vo.ViciosResponse;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.EstadoCuentaReporteFieldDto;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.report.referencias.dto.ReferenciasBancoDto;
import mx.com.grupogigante.gestionvivienda.report.ventas.dto.VentasReporteRowDto;
import mx.com.grupogigante.gestionvivienda.resources.ArchivoPropiedades;
import mx.com.grupogigante.gestionvivienda.services.UbicacionesService;
import mx.com.grupogigante.gestionvivienda.utils.SessionValidatorSTS;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

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

/**
 * Clase controladora para el modulo de TICKETS
 * Fecha de creación: XX/06/2012               
 */
@Controller
public class TicketController {
	private Logger log = Logger.getLogger(TicketController.class);

	@Autowired
	UbicacionesService ubicaciones;
	
	@Autowired
	BancoDao bancos;
	
	@Autowired
	TicketsDao ticketsDAO;
	
	@Autowired
	CatalogosDao catalogosDao;


	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para aguegar clientes RegistroCarCliente 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina deredireccion 
	 */	
	@RequestMapping(value = "/tickets/RegistroTicket.htm", method = RequestMethod.GET)
	public String returnaddTicket(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else {
			 redir = "/ticket/RegistroTicket";
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
	@RequestMapping(value = "/ParamsClienteRegistroTicket.htm", method = RequestMethod.GET)
	public String paramsClienteRegistroTicket(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else	 {
			 redir = "/ticket/GridParamsClienteRegistroTicket";
		 }
		 return redir;
	}
	
	
	@RequestMapping(value = "/ParamsVerHistorial.htm", method = RequestMethod.GET)
	public String ParamsVerHistorial(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else	 {
			 redir = "/ticket/ConsultaHistorialObservaciones";
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
	@RequestMapping(value = "/ticket/GridBusquedaClienteRegistroTicket.htm", method = RequestMethod.GET)
	public String gridBusquedaClienteRegistroTicket(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else	 {
			 redir = "/ticket/GridBusquedaClienteRegistroTicket";
		 }
		 return redir;
	}
	
	
	
	/**
	 * Método @RequestMapping por metodo POST. 
	 * Valida la session e imprime las variables recibidas vía log.info
	 *  
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 */	
	@RequestMapping(value = "/ticket/AddRegistroTicket.htm", method = RequestMethod.POST)
	public @ResponseBody ResponseAddTicketDto addRegistroTicket(@RequestParam("idAreaTicket") String idAreaTicket, 
									@RequestParam("idVendedorTicket") String idVendedorTicket,
									@RequestParam("idCarClienteTicket") String idCarClienteTicket,
									@RequestParam("asuntoTicket") String asuntoTicket,
									@RequestParam("descripcionTicket") String descripcionTicket,
									@RequestParam("idFaseTicket") String idFaseTicket,
									@RequestParam("idEquipoTicket") String idEquipoTicket,
						            HttpSession session) {

		ResponseAddTicketDto response = new ResponseAddTicketDto();
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		
		if(!sesionValida.validaSesion(session)) {
			response.setMensaje("LOGOUT");
			response.setDescripcion("");
			return response;
		 }

		log.info("idAreaTicket:" + idAreaTicket + ", idVendedorTicket:" + idVendedorTicket + ", idCarClienteTicket:" + idCarClienteTicket + 
				", asuntoTicket:" + asuntoTicket + ", descripcionTicket:" + descripcionTicket + ", idFaseTicket:" + idFaseTicket + 
				", idEquipoTicket:" + idEquipoTicket);

		CriteriosGetTicket tickets = new CriteriosGetTicket();
		tickets.setAsunto(asuntoTicket);
		tickets.setBody_mail(descripcionTicket);
		tickets.setId_car_cli(idCarClienteTicket);
		tickets.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
		tickets.setSls_man(idVendedorTicket);
		tickets.setTicket_area(idAreaTicket);
		tickets.setUsuario(sesionValida.getDatos(session,"usuario"));
		tickets.setIdFase(idFaseTicket);
		tickets.setIdEquipo(idEquipoTicket == null || idEquipoTicket.trim().toLowerCase().equals("null")? "":idEquipoTicket);
		
		
		try {
			 response = ticketsDAO.addTicket(tickets);
			 log.info("idTicket="    + response.getId_ticket_sap());
			 log.info("descripcion=" + response.getDescripcion());
			 log.info("mensaje="     + response.getMensaje());
		} catch (Exception e) {
			response.setMensaje("FAULT");
			response.setDescripcion(e.getMessage());
			log.error("ERROR:" + e);
		}

		return response;	
	}	
	
/**  PROCESO DE BUSQUEDA   **/	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para aguegar clientes RegistroCarCliente 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina deredireccion 
	 */	
	@RequestMapping(value = "/ticket/ConsultaCatalogoTicketsView.htm", method = RequestMethod.GET)
	public String consultaModificacionTicketsView(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else {
			 redir = "/ticket/ConsultaCatalogoTickets";
		 }
		 return redir;	
	}
	
	
	/**  PROCESO DE BUSQUEDA   **/	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para aguegar clientes RegistroCarCliente 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina deredireccion 
	 */	
	@RequestMapping(value = "/ticket/ReporteTicketsView.htm", method = RequestMethod.GET)
	public String consultaReporteTicketsView(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else {
			 redir = "/ticket/ReporteTickets";
		 }
		 return redir;	
	}	
	
	

	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para aguegar clientes RegistroCarCliente 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina deredireccion 
	 */	
	@RequestMapping(value = "/ticket/ConsultaCatalogoTicketsFilterView.htm", method = RequestMethod.GET)
	public String consultaModificacionTicketsFilterView(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else {
			 redir = "/ticket/ConsultaCatalogoTicketsFilter";
		 }
		 return redir;	
	}
	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para aguegar clientes RegistroCarCliente 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina deredireccion 
	 */	
	@RequestMapping(value = "/ticket/ConsultaReporteTicketsFilterView.htm", method = RequestMethod.GET)
	public String consultaReporteTicketsFilterView(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else {
			 redir = "/ticket/ConsultaReporteTicketsFilter";
		 }
		 return redir;	
	}	
	
	@RequestMapping(value = "/ticket/ConsultaCatalogoTicketsDetalleView.htm", method = RequestMethod.GET)
	public String consultaCatalogoTicketsDetalleView(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else {
			 redir = "/ticket/ConsultaTicketDetalle";
		 }
		 return redir;	
	}
	
	
	@RequestMapping(value = "/ticket/ConsultaConstruccionTicketsDetalleView.htm", method = RequestMethod.GET)
	public String consultaConstruccionTicketsDetalleView(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else {
			 redir = "/ticket/ConsultaTicketConstruccion";
		 }
		 return redir;	
	}	
	
	
	@RequestMapping(value = "/ticket/ConsultaTicketsDetalleView.htm", method = RequestMethod.GET)
	public String consultaTicketsDetalleView(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else {
			 redir = "/ticket/ConsultaTicket";
		 }
		 return redir;	
	}	
	
	
	@RequestMapping(value = "/ticket/AtencionConstruccionTicketsView.htm", method = RequestMethod.GET)
	public String atencionConstruccionTicketsView(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else {
			 redir = "/ticket/AtencionTicketConstruccion";
		 }
		 return redir;	
	}
	
	
	@RequestMapping(value = "/ticket/AtencionTicketsView.htm", method = RequestMethod.GET)
	public String atencionTicketsView(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else {
			 redir = "/ticket/AtencionTicket";
		 }
		 return redir;	
	}
	
	
	@RequestMapping(value = "/ticket/ProcesoConstruccionTicketsView.htm", method = RequestMethod.GET)
	public String procesoConstruccionTicketsView(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else {
			 redir = "/ticket/ProcesoTicketConstruccion";
		 }
		 return redir;	
	}

	
/*
 * llenado de combos	
 */
	
	@RequestMapping(value="/ticket/LlenaCombosInicialesRegistroTicket.htm",method=RequestMethod.POST)
	public @ResponseBody ResponseUbicacionesActionDto llenaCombosInicialesRegistroTicket(@ModelAttribute(value="arbolMap")CriteriosUbicacionesDto criterios, BindingResult result, HttpSession session){

		ResponseUbicacionesActionDto resAction = new ResponseUbicacionesActionDto();
		ResponseGetUtInfSupDto resUTS = new ResponseGetUtInfSupDto();
		
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		
		if(!sesionValida.validaSesion(session)) {			
			resUTS.setMensaje("LOGOUT");
			resUTS.setDescripcion("");
			resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
			return resAction;
		 }
		
		criterios.setI_usuario(sesionValida.getDatos(session,"usuario"));
		criterios.setI_id_ut_sup(sesionValida.getDatos(session,"unidad"));
		try
		{
			if(criterios.getAccion().equals("faces"))
			{
				criterios.setI_charact("");
				criterios.setI_deep_srch("");
				criterios.setI_equnr_price("");
				criterios.setI_id_ut_current(sesionValida.getDatos(session,"unidad"));
				resUTS = ubicaciones.getUtInfSup(criterios);
				
				if(resUTS.getMensaje().equals("SUCCESS"))
				{					
					List<UbicacionTecnicaDto> ubicaciones = (List<UbicacionTecnicaDto>) resUTS.getObjUbicacionesList();
					UbicacionTecnicaDto ubicacion = new UbicacionTecnicaDto();
					ubicacion.setI_id_ut_sup("");
					ubicacion.setUbicacionClave("");
					ubicacion.setUbicacionDescripcion("");
					ubicacion.setId("");
					ubicacion.setText("");
					ubicaciones.add(0, ubicacion);
					resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
				}
			}
			else if (criterios.getAccion().equals("equipos"))
			{
				criterios.setI_charact("");
				criterios.setI_deep_srch("X");
				criterios.setI_equnr_price("");
				criterios.setI_digit_areas("");
				resUTS = ubicaciones.getUtInfSup(criterios);
				if(resUTS.getMensaje().equals("SUCCESS"))
				{
					List<EquipoDto> listaEquipos = (List<EquipoDto>) resUTS.getObjEquiposList();
					EquipoDto vacioEquipo = new EquipoDto();
					vacioEquipo.setId("");
					vacioEquipo.setId_equnr("");
					vacioEquipo.setId_equnrx("");
					listaEquipos.add(0, vacioEquipo);
					resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
				}	
			}
			else if (criterios.getAccion().equals("areas"))
			{
				criterios.setI_charact("");
				criterios.setI_deep_srch("");
				criterios.setI_equnr_price("");
				criterios.setI_ticket_areas("X");
				criterios.setI_id_ut_current(sesionValida.getDatos(session,"unidad"));
				resUTS = ubicaciones.getUtInfSup(criterios);
				log.info(resUTS.getMensaje());
				log.info(resUTS.getDescripcion());
				if(resUTS.getMensaje().equals("SUCCESS"))
				{
					resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
				}	
			}
			else if (criterios.getAccion().equals("estatus"))
			{
				criterios.setI_charact("");
				criterios.setI_deep_srch("");
				criterios.setI_equnr_price("");
				criterios.setI_ticket_areas("");
				criterios.setI_stun("X");
				criterios.setI_id_ut_current(sesionValida.getDatos(session,"unidad"));
				resUTS = ubicaciones.getUtInfSup(criterios);
				log.info(resUTS.getMensaje());
				log.info(resUTS.getDescripcion());
				if(resUTS.getMensaje().equals("SUCCESS"))
				{
					resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
				}	
			}
			else if (criterios.getAccion().equals("banco"))
			{
				criterios.setI_charact("");
				criterios.setI_deep_srch("");
				criterios.setI_equnr_price("");
				criterios.setI_ticket_areas("");
				criterios.setI_stun("X");
				criterios.setI_id_ut_current(sesionValida.getDatos(session,"unidad"));
				try {
					resUTS = bancos.findBancos(criterios);
					resUTS.getBancos().add(0, new ReferenciasBancoDto("",""));
				} catch (ReporteException e) {
					log.error("ERROR:", e);
				}
				log.info(resUTS.getMensaje());
				log.info(resUTS.getDescripcion());
				if(resUTS.getMensaje().equals("SUCCESS"))
				{
					resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
				}	
			}
		}
		catch (ViviendaException e) 
		{			
			//log.error("ERROR:", e);
			resUTS.setMensaje("FAULT");
			resUTS.setMensaje(e.getMessage());
			resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
		}
		
		return resAction;
	}
	
	@RequestMapping(value = "/ticket/FindRegistroTicket.htm", method = RequestMethod.POST)
	public @ResponseBody ResponseAddTicketDto findRegistroTicket(@RequestParam("idTicketInicial") String idTicketInicial,
									@RequestParam("idTicketFinal") String idTicketFinal,
									@RequestParam("fechaInicialFiltroCatalogoTicket") String fechaTicketInicio,
									@RequestParam("fechaFinalFiltroCatalogoTicket") String fechaTicketFin,
									@RequestParam("idAreaTicket") String idAreaTicket,
									@RequestParam("idClienteTicket") String idClienteTicket,
									@RequestParam("idVendedorTicket") String idVendedorTicket,
									@RequestParam("idFaseTicket") String idFaseTicket,
									@RequestParam("idEquipoTicket") String idEquipoTicket,
						            HttpSession session) {
		ResponseAddTicketDto response = new ResponseAddTicketDto();
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		
		if(!sesionValida.validaSesion(session)) {			
			response.setMensaje("LOGOUT");
			response.setDescripcion("");
			return response;
		 }
		
		log.info("idTicketInicial:" + idTicketInicial);
		log.info("fechaTicketInicio:" + fechaTicketInicio);
		log.info("fechaTicketFin:" + fechaTicketFin);
		log.info("idAreaTicket:" + idAreaTicket);
		log.info("idClienteTicket:" + idClienteTicket);
		log.info("idVendedorTicket:" + idVendedorTicket);
		log.info("idFaseTicket:" + idFaseTicket);
		log.info("idEquipoTicket:" + idEquipoTicket);


		CriteriosGetTicket tickets = new CriteriosGetTicket();

		tickets.setIdTicketInicial(idTicketInicial);
		tickets.setIdTicketFinal(idTicketFinal);
		tickets.setFechaInicial(fechaTicketInicio);
		tickets.setFechaFinal(fechaTicketFin);
		tickets.setTicket_area(idAreaTicket);
		tickets.setId_car_cli(idClienteTicket);
		tickets.setSls_man(idVendedorTicket);
		tickets.setIdFase(idFaseTicket);
		tickets.setIdEquipo(idEquipoTicket);
		
		tickets.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
		tickets.setUsuario(sesionValida.getDatos(session,"usuario"));
		
		
		try {
			 response = ticketsDAO.findTicket(tickets);
			 log.info("idTicket="    + response.getListTicket());
			 log.info("descripcion=" + response.getDescripcion());
			 log.info("mensaje="     + response.getMensaje());
		} catch (ViviendaException e) {
			response.setMensaje("FAIL");
			response.setDescripcion(e.getMessage());
			//log.error("ERROR:" + e);
		}

		return response;	

	}
	
	@RequestMapping(value = "/ticket/FindTicketVicios.htm", method = RequestMethod.POST)
	public @ResponseBody ResponseTicketConstruccionDto findTicketVicios(@ModelAttribute(value="criteriosGetTicketConstruccion") CriteriosGetTicketConstruccion criteriosGetTicketConstruccion, BindingResult result,
		HttpServletRequest  request, 
		HttpServletResponse response, 
		HttpSession         session) {
		ResponseTicketConstruccionDto consultaSAP = new ResponseTicketConstruccionDto();
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		
		if(!sesionValida.validaSesion(session)) {			
			consultaSAP.setMensaje("LOGOUT");
			consultaSAP.setDescripcion("");
			return consultaSAP;
		 }

		criteriosGetTicketConstruccion.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
		criteriosGetTicketConstruccion.setUsuario(sesionValida.getDatos(session,"usuario"));
		
		try {
						
			consultaSAP = ticketsDAO.findTicketConstruccion(criteriosGetTicketConstruccion);
			
			//Busca si existe imagenes en el FileSystem
			ArchivoPropiedades recursos = null;
		    String udt = sesionValida.getDatos(session,"unidad");
		    recursos = new ArchivoPropiedades(request);
			String name = recursos.getValorPropiedad("ruta.warehouse.gestion.vivienda")
					+ "//DESA_" + udt + "//IMAGENES//";
			
			for (int i = 0; i < consultaSAP.getListTicket().size(); i++) {
				for (int y = 0; y < consultaSAP.getListTicket().get(i).getListTicketDetail().size(); y++) {
					File fichero = new File(name + 
							consultaSAP.getListTicket().get(i).getListTicketDetail().get(y).getIdTicket() + 
							"-" + consultaSAP.getListTicket().get(i).getListTicketDetail().get(y).getConsecutivo() 
							+ ".jpg");
					if (fichero.exists()){
					consultaSAP.getListTicket().get(i).getListTicketDetail().get(y).setFoto("SI");
					}else{
						consultaSAP.getListTicket().get(i).getListTicketDetail().get(y).setFoto("NO");
					}		
				}
			}//Fin Escribe si exixte la imagen en la respuesta 

		} catch (ViviendaException e) {
			consultaSAP.setMensaje("FAIL");
			consultaSAP.setDescripcion(e.getMessage());
			//log.error("ERROR:" + e);
		}

		return consultaSAP;	

	}
	
	
	@RequestMapping(value="/ticket/LlenaCombosInicialesRegistroConsultaPagos.htm",method=RequestMethod.POST)
	public @ResponseBody ResponseUbicacionesActionDto llenaCombosInicialesRegistroConsultaPagos(@ModelAttribute(value="arbolMap")CriteriosUbicacionesDto criterios, BindingResult result, HttpSession session){

		ResponseUbicacionesActionDto resAction = new ResponseUbicacionesActionDto();
		ResponseGetUtInfSupDto resUTS = new ResponseGetUtInfSupDto();
		
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		
		if(!sesionValida.validaSesion(session)) {			
			resUTS.setMensaje("LOGOUT");
			resUTS.setDescripcion("");
			resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
			return resAction;
		 }
		
		criterios.setI_usuario(sesionValida.getDatos(session,"usuario"));
		criterios.setI_id_ut_sup(sesionValida.getDatos(session,"unidad"));
		try
		{
			if(criterios.getAccion().equals("faces"))
			{
				criterios.setI_charact("");
				criterios.setI_deep_srch("");
				criterios.setI_equnr_price("");
				criterios.setI_id_ut_current(sesionValida.getDatos(session,"unidad"));
				resUTS = ubicaciones.getUtInfSup(criterios);
				
				if(resUTS.getMensaje().equals("SUCCESS"))
				{					
					List<UbicacionTecnicaDto> ubicaciones = (List<UbicacionTecnicaDto>) resUTS.getObjUbicacionesList();
					resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
				}
				
			 // Combo Medios
				CriteriosGetCatalogosDto criteriosDto = new CriteriosGetCatalogosDto();
				criteriosDto.setI_catalogo("MED_PAGO");
				criteriosDto.setI_id_ut_sup(sesionValida.getDatos(session, "unidad"));
				criteriosDto.setI_usuario(sesionValida.getDatos(session, "usuario"));
				
				ResponseGetCatalogosDto resCat = new ResponseGetCatalogosDto();
				resCat = catalogosDao.getCatalogos2(criteriosDto);

				log.info(resCat.getMensaje());
				log.info(resCat.getDescripcion());
				if(resCat.getMensaje().equals("SUCCESS"))
				{
					List<CatalogosDto> catalogos = (List<CatalogosDto>) resCat.getObjCatalogosList();
					CatalogosDto catalogo = new CatalogosDto();
					catalogo.setId_cat("");
					catalogo.setId_val("");
					catalogo.setVal_02("");
					catalogo.setValor("");
					catalogos.add(0, catalogo);
					resAction.setResponseGetCatalogosDto(resCat);
				}		
			}
			else if (criterios.getAccion().equals("equipos"))
			{
				criterios.setI_charact("");
				criterios.setI_deep_srch("X");
				criterios.setI_equnr_price("");
				criterios.setI_digit_areas("");
				resUTS = ubicaciones.getUtInfSup(criterios);
				if(resUTS.getMensaje().equals("SUCCESS"))
				{
					List<EquipoDto> listaEquipos = (List<EquipoDto>) resUTS.getObjEquiposList();
					EquipoDto vacioEquipo = new EquipoDto();
					vacioEquipo.setId("");
					vacioEquipo.setId_equnr("");
					vacioEquipo.setId_equnrx("");
					listaEquipos.add(0, vacioEquipo);
					resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
				}else{
				     resAction.setRespGetUnidadesTecnicasSuperiores(new ResponseGetUtInfSupDto());
				}
			
				 // Combo Medios
				CriteriosGetCatalogosDto criteriosDto = new CriteriosGetCatalogosDto();
				criteriosDto.setI_catalogo("MED_PAGO");
				criteriosDto.setI_id_ut_sup(sesionValida.getDatos(session, "unidad"));
				criteriosDto.setI_usuario(sesionValida.getDatos(session, "usuario"));
				
				ResponseGetCatalogosDto resCat = new ResponseGetCatalogosDto();
				resCat = catalogosDao.getCatalogos2(criteriosDto);

				log.info(resCat.getMensaje());
				log.info(resCat.getDescripcion());
				if(resCat.getMensaje().equals("SUCCESS"))
				{	
					List<CatalogosDto> catalogos = (List<CatalogosDto>) resCat.getObjCatalogosList();
					CatalogosDto catalogo = new CatalogosDto();
					catalogo.setId_cat("");
					catalogo.setId_val("");
					catalogo.setVal_02("");
					catalogo.setValor("");
					catalogos.add(0, catalogo);
					resAction.setResponseGetCatalogosDto(resCat);
				}		
			}
		}
		catch (ViviendaException e) 
		{			
			//log.error("ERROR:", e);
			resUTS.setMensaje("FAULT");
			resUTS.setMensaje(e.getMessage());
			resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
		}
		
		return resAction;
	}


	@RequestMapping(value = "/ticket/GeneraReporte.htm", method = RequestMethod.GET)
	public void generaReporte(@RequestParam("idTicket") String idTicket,
									   HttpServletRequest  request, 
									   HttpServletResponse response, 
									   HttpSession         session){
		try {
		    SessionValidatorSTS         sesionValida          = new SessionValidatorSTS();
		    ClienteDatosTicketVo respuesta = new ClienteDatosTicketVo();
		    CriteriosGetTicketConstruccion criteriosGetTicketConstruccion = new CriteriosGetTicketConstruccion();
		    ResponseTicketConstruccionDto consultaSAP = new ResponseTicketConstruccionDto();
		    UsuarioDto user = (UsuarioDto) request.getSession().getAttribute("usrSession");
		    
		    String[] mes = { "enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
		    SimpleDateFormat formatoFechaCorte = new SimpleDateFormat("dd/MM/yyyy");
			Calendar calendar = Calendar.getInstance();
		    String lastdayMon =  formatoFechaCorte.format(calendar.getTime());
			String[] camposFecha = lastdayMon.split("/");
			String sMes = camposFecha[1];
			int iMes = new Integer(sMes);
			String fechaDoc = camposFecha[0] + " de " + mes[iMes-1] + " "+ camposFecha[2];
		    
			if(!sesionValida.validaSesion(session)) {
			    log.info("Sesion invalida");
			    request.getRequestDispatcher("/index.jsp").forward(request, response);
			 	} else {
			 		
				criteriosGetTicketConstruccion.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
				criteriosGetTicketConstruccion.setUsuario(sesionValida.getDatos(session,"usuario"));
				criteriosGetTicketConstruccion.setIdTicket(idTicket);
				
				consultaSAP = ticketsDAO.findTicketConstruccion(criteriosGetTicketConstruccion);
				criteriosGetTicketConstruccion.setIdEquipo(consultaSAP.getListTicket().get(0).getIdEquipo());
				respuesta = ticketsDAO.obtieneDatosCliente(criteriosGetTicketConstruccion);
				
				ArchivoPropiedades recursos      = new ArchivoPropiedades(request);
				String             nombreArchivo =  recursos.getValorPropiedad("ruta.reportesg.gestion.vivienda") + "ViciosOcultos.jrxml";
				File               archivoReporte= new File(nombreArchivo);
	
				log.info("Ruta del reporte es: " + archivoReporte);
				
			  	InputStream isReadArchivoReporte = null;
				try {
					if (archivoReporte.exists()) {
						isReadArchivoReporte = new BufferedInputStream(new FileInputStream(archivoReporte));
					}else{
						 log.info("El archivo no existe");
					}
				} catch (FileNotFoundException e) {
					log.error("ERROR:" + e);
				}
				//List<EstadoCuentaReporteFieldDto> lista = new ArrayList<EstadoCuentaReporteFieldDto>();
				//InputStream                is            = this.getClass().getResourceAsStream("/mx/com/grupogigante/gestionvivienda/reportes/estadocuenta/EstadoCuenta.jrxml");
				JasperReport               jasperReport  = (JasperReport) JasperCompileManager.compileReport(isReadArchivoReporte);			
				HashMap<String, Object>                         parametros        = new HashMap<String, Object>();
				
				parametros.put("idTicket", consultaSAP.getListTicket().get(0).getIdTicket());
				parametros.put("idEquipo", consultaSAP.getListTicket().get(0).getIdEquipo());
				parametros.put("cliente", consultaSAP.getListTicket().get(0).getCliente());
				parametros.put("recibido", consultaSAP.getListTicket().get(0).getRecibido());
				parametros.put("asignado", consultaSAP.getListTicket().get(0).getAsignado());
				parametros.put("aprobado", consultaSAP.getListTicket().get(0).getAprobado());
				parametros.put("fechab", consultaSAP.getListTicket().get(0).getFechab());
				parametros.put("fechpr", consultaSAP.getListTicket().get(0).getFechpr());
				parametros.put("fechat", consultaSAP.getListTicket().get(0).getFechat());
				parametros.put("fechat_val", consultaSAP.getListTicket().get(0).getFechat_val());
				parametros.put("observaciones", consultaSAP.getListTicket().get(0).getObservaciones());
				parametros.put("prioridad", consultaSAP.getListTicket().get(0).getPrioridad());
				parametros.put("ut_sup_cm", user.getId_ut_sup_cm().length()>=21 ? user.getId_ut_sup_cm().substring(0,20):user.getId_ut_sup_cm());
				parametros.put("fechaDoc", fechaDoc);
				
				parametros.put("fechen", respuesta.getFechaInicioGarantia());
				
				
				JRBeanCollectionDataSource dataSource    = new JRBeanCollectionDataSource(consultaSAP.getListTicket().get(0).getListTicketDetail());
				
				//JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametros,  new JREmptyDataSource());
				JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
				//removeBlankPage(jp.getPages());
				ByteArrayOutputStream  baos      = new ByteArrayOutputStream();
				JRPdfExporter          exporter  = new JRPdfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
				exporter.exportReport();
				response.setContentType("application/octet-stream");
				String nombre = "ViciosOcultos-" +idTicket +".pdf"; 
				response.setHeader("Content-Disposition", "attachment; filename=\"" + nombre + "\"");
				//response.setHeader ("Content-disposition", "attachment; filename=ViciosOcultos.pdf");
				response.setContentLength(baos.size());
				ServletOutputStream outputStream = response.getOutputStream();
				baos.writeTo(outputStream);
				outputStream.flush();
				log.info("El archivo se acabo de imprimir verifique impresion");

			 }
		} catch (Exception e) {
			log.error("ERROR:", e);
		}
	}
	
	
	@RequestMapping(value = "/ticket/GeneraExcel.htm", method = RequestMethod.GET)
	public void generaExcel(@ModelAttribute(value="criteriosGetTicketConstruccion") CriteriosGetTicketConstruccion criteriosGetTicketConstruccion,
									   HttpServletRequest  request, 
									   HttpServletResponse response, 
									   HttpSession         session){
		try {
		    SessionValidatorSTS         sesionValida          = new SessionValidatorSTS();
		    ResponseTicketConstruccionDto consultaSAP = new ResponseTicketConstruccionDto();
		    UsuarioDto user = (UsuarioDto) request.getSession().getAttribute("usrSession");
		    
		    String[] mes = { "enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
		    SimpleDateFormat formatoFechaCorte = new SimpleDateFormat("dd/MM/yyyy");
			Calendar calendar = Calendar.getInstance();
		    String lastdayMon =  formatoFechaCorte.format(calendar.getTime());
			String[] camposFecha = lastdayMon.split("/");
			String sMes = camposFecha[1];
			int iMes = new Integer(sMes);
			String fechaDoc = camposFecha[0] + " de " + mes[iMes-1] + " "+ camposFecha[2];	
		    
			if(!sesionValida.validaSesion(session)) {
			    log.info("Sesion invalida");
			    request.getRequestDispatcher("/index.jsp").forward(request, response);
			 	} else {
			 		
				criteriosGetTicketConstruccion.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
				criteriosGetTicketConstruccion.setUsuario(sesionValida.getDatos(session,"usuario"));
				
				consultaSAP = ticketsDAO.findTicketConstruccion(criteriosGetTicketConstruccion);
				
				List<TicketHeaderDto>  lista         = null;
				lista = consultaSAP.getListTicket();
				
				JRBeanCollectionDataSource dataSource    = new JRBeanCollectionDataSource(lista);
				
				ArchivoPropiedades recursos      = new ArchivoPropiedades(request);
				String             nombreArchivo =  recursos.getValorPropiedad("ruta.reportesg.gestion.vivienda") + "ViciosExcel.jrxml";
				String             rutaSubreporte =  recursos.getValorPropiedad("ruta.reportesg.gestion.vivienda") + "ViciosExcelDetalle.jasper";
				File               archivoReporte= new File(nombreArchivo);

				log.info("Ruta del reporte es: " + archivoReporte);
				
			  	InputStream isReadArchivoReporte = null;
				try {
					if (archivoReporte.exists()) {
						isReadArchivoReporte = new BufferedInputStream(new FileInputStream(archivoReporte));
					}else{
						 log.info("El archivo no existe");
					}
				} catch (FileNotFoundException e) {
					log.error("ERROR:" + e);
				}
				
				JasperReport               jasperReport  = (JasperReport) JasperCompileManager.compileReport(isReadArchivoReporte);        
				
				//InputStream                is            = this.getClass().getResourceAsStream("/mx/com/grupogigante/gestionvivienda/reportes/ventas/Ventas.jrxml");
				//JasperReport               jasperReport  = (JasperReport) JasperCompileManager.compileReport(is);        

				Map parametros = new HashMap();
				parametros.put("SUBREPORT_DIR", rutaSubreporte);

				JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
				ByteArrayOutputStream  baos      = new ByteArrayOutputStream();
				//JRPdfExporter          exporter  = new JRPdfExporter();
				//exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
				//exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
				//exporter.exportReport();
				
				JRXlsExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				exporterXLS.exportReport();			
				response.setContentType("application/vnd.ms-excel");
				//response.setContentType("application/octet-stream");
				//response.setHeader ("Content-disposition", "attachment; filename=" + ( idEquipoCompleto != null && idEquipoCompleto.trim().length() > 0 ? idEquipoCompleto + ".pdf":"EstadoCuentaReporte.pdf"))
			 	String nombre = "ReporteVicios " + camposFecha[0] + "-" + iMes + "-"+ camposFecha[2] +"\".xls"; 
				response.setHeader("Content-Disposition", "attachment; filename=\"" + nombre + "\"");
				response.setContentLength(baos.size());
				ServletOutputStream outputStream = response.getOutputStream();
				baos.writeTo(outputStream);
				outputStream.flush();
				log.info("saliendo");
			 }
		} catch (Exception e) {
			log.error("ERROR:", e);
		}
	}
	
	/*
	 * Agrega un ticket para el área de construcción.
	 * @autor: Edwin Carlos Chavez
	 */
	
	
	@RequestMapping(value="/ticket/AddTicketConstruccion.htm", method = RequestMethod.POST)
	public String setGuardaTicketConstruccion(@ModelAttribute("ListaFicheros") ListaFicheros  listaFicheros, BindingResult result, HttpServletRequest request, HttpSession session, Map model) {
			
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		ResponseAddTicketDto response = new ResponseAddTicketDto();
		
		if(!sesionValida.validaSesion(session)) {
			response.setMensaje("LOGOUT");
			response.setDescripcion("");
			return "../../index";
		 }
		
			ArchivoPropiedades recursos = null;
			
			log.info("idAreaTicket:" + listaFicheros.getIdArea() + ", prioridad:" + listaFicheros.getPrioridad() + ", nombreClienteTicket:" + listaFicheros.getCliente() + 
					", observaciones:" + listaFicheros.getObservaciones() + ", idFase:" + listaFicheros.getIdFase().substring(7) + 
					", idEquipo:" + listaFicheros.getIdEquipo());

			try {
			
				ArrayList<String> zonas = listaFicheros.getZona();
				//ArrayList<String> vicios = listaFicheros.getVicio();
				ArrayList<String> vicios = new ArrayList<String>();
			
			if(listaFicheros.getAccion().equals("noconstruccion")){
				listaFicheros.setVicio(listaFicheros.getDescripcion());
				zonas = new ArrayList<String>();
			}else{
				
					 for(int i=0; zonas.size() > i; i++){
						 if(listaFicheros.getCategoria().get(i).equals("OTRO")){
							 	vicios.add(listaFicheros.getCategoria().get(i) + " / " +listaFicheros.getTextoabierto().get(i));
						 	}else{
						 		vicios.add(listaFicheros.getCategoria().get(i) + " / " +listaFicheros.getSubcategoria().get(i));	
						 	}
						 	log.info(zonas.get(i));
						 	log.info(vicios.get(i));
					 }
				listaFicheros.setVicio(vicios);
			}
			
			listaFicheros.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
			listaFicheros.setUsuario(sesionValida.getDatos(session,"usuario"));
			listaFicheros.setIdFase(listaFicheros.getIdFase().substring(7));
			
			 if(null != zonas && zonas.size() > 0) {
				 for(int i=0; zonas.size() > i; i++){
					 log.info("Detalle " + (i+1) );
					 log.info(zonas.get(i));
					 log.info(vicios.get(i));
				 }
			 }
			
			List<MultipartFile> ficheros = listaFicheros.getArchivos();
			
			 if(null != ficheros && ficheros.size() > 0) {
	        	 
	        	 for (MultipartFile fichero : ficheros) {       
	        		 
	        		 if (fichero.isEmpty()){
	        			 listaFicheros.setAdjunto("");
	        		 	}else{
	        		 	listaFicheros.setAdjunto("X");
	        		 	}
	        	 }
			}
			 
			response = ticketsDAO.addTicketConstruccion(listaFicheros);
			
 			String udt = sesionValida.getDatos(session,"unidad");
			recursos = new ArchivoPropiedades(request);
			String path = recursos.getValorPropiedad("ruta.warehouse.gestion.vivienda")
					+ "//DESA_" + udt + "//IMAGENES//";
			
			 log.info("idTicket="    + response.getId_ticket_sap());
			 log.info("descripcion=" + response.getDescripcion());
			 log.info("mensaje="     + response.getMensaje());
			
			ticketsDAO.addImagenesTicket(ficheros, response.getId_ticket_sap(), path);
			 
			} 	catch (Exception e) {
				response.setMensaje("FAULT");
				response.setDescripcion(e.getMessage());
				log.error("ERROR:" + e);
			}
			
			model.put("response", response.getDescripcion());
			
			return "/ticket/RegistroTicket";
	}
	
	
	
	@RequestMapping(value="/ticket/UpdateTicketConstruccion.htm", method = RequestMethod.POST)
	public @ResponseBody ResponseAddTicketDto setUpdateTicketConstruccion(@ModelAttribute("UpdateViciosVo") UpdateViciosVo  updateViciosVo, BindingResult result, HttpServletRequest request, HttpSession session, Map model) {
			
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		ResponseAddTicketDto response = new ResponseAddTicketDto();
		
		if(!sesionValida.validaSesion(session)) {
			response.setMensaje("LOGOUT");
			response.setDescripcion("");
			//return "../../index";
		 }
				
			/*log.info("idAreaTicket:" + listaFicheros.getIdArea() + ", prioridad:" + listaFicheros.getPrioridad() + ", nombreClienteTicket:" + listaFicheros.getCliente() + 
					", observaciones:" + listaFicheros.getObservaciones() + ", idFase:" + listaFicheros.getIdFase().substring(7) + 
					", idEquipo:" + listaFicheros.getIdEquipo());*/

			try {
			
			ArrayList<String> estatusDet = updateViciosVo.getEstatusDet();
			ArchivoPropiedades recursos = null;
			
			updateViciosVo.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
			updateViciosVo.setUsuario(sesionValida.getDatos(session,"usuario"));
			//listaFicheros.setIdFase(listaFicheros.getIdFase().substring(7));
			
			int CuentaRechazados = 0;
			
			 if(null != estatusDet && estatusDet.size() > 0) {
				 for(int i=0; estatusDet.size() > i; i++){
					 log.info("Detalle " + (i+1) );
					 log.info(estatusDet.get(i));
					 
					  if(estatusDet.get(i).equals("RECHAZADO")){
						  CuentaRechazados++;
					  }
					 
					  if(CuentaRechazados == estatusDet.size()){
						  updateViciosVo.setEstatus("RECHAZAR");
					  }
					  
				 }
			 }
			
			response = ticketsDAO.updateTicketConstruccion(updateViciosVo);
			
				if(updateViciosVo.getEstatus().equals("CERRAR")){
		 			String udt = sesionValida.getDatos(session,"unidad");
					recursos = new ArchivoPropiedades(request);
					String path = recursos.getValorPropiedad("ruta.warehouse.gestion.vivienda")
							+ "//DESA_" + udt + "//PDF//";
					
					 log.info("idTicket="    + response.getId_ticket_sap());
					 log.info("descripcion=" + response.getDescripcion());
					 log.info("mensaje="     + response.getMensaje());
					
					ticketsDAO.addPDFTicket(updateViciosVo.getFileData(), updateViciosVo.getIdTicket(), path);
				}
			} 	catch (Exception e) {
				response.setMensaje("FAULT");
				response.setDescripcion(e.getMessage());
				log.error("ERROR:" + e);
			}
			
			/*model.put("response", response.getDescripcion());
			
			return "/ticket/RegistroTicket";*/
			return response;
	}
	
	
	@RequestMapping(value = "/ticket/GetRegistroFotografico.htm", method = RequestMethod.GET)
	public void registroFotografico(@RequestParam("idTicket") String idTicket,
									HttpServletRequest  request, 
									HttpServletResponse response, 
									HttpSession         session){

		    SessionValidatorSTS         sesionValida          = new SessionValidatorSTS();
			ArchivoPropiedades recursos = null;
		    try {
			    String udt = sesionValida.getDatos(session,"unidad");
			    recursos = new ArchivoPropiedades(request);
				String name = recursos.getValorPropiedad("ruta.warehouse.gestion.vivienda")
					+ "//DESA_" + udt + "//IMAGENES//";
		    
					if(!sesionValida.validaSesion(session)) {
						log.info("Sesion invalida");
						request.getRequestDispatcher("/index.jsp").forward(request, response);
				 	} else {
				 		response.setContentType("application/x-download"); 
				 		String nombre = request.getParameter("idTicket")+".jpg";
				 		response.setHeader("Content-Disposition", "attachment; filename=\"" + nombre + "\"");
	
				 		File file = new File(name + request.getParameter("idTicket") + ".jpg");
				 		FileInputStream fileIn = new FileInputStream(file);
				 		ServletOutputStream outstream = response.getOutputStream();
	
				 		byte[] outputByte = new byte[40096];
	
				 		while(fileIn.read(outputByte, 0, 40096) != -1)
				 		{
				 		    outstream.write(outputByte, 0, 40096);
				 		}
				 		fileIn.close();
				 		outstream.flush();
				 		outstream.close();
				 	}
		    } catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("ERROR:" + e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("ERROR:" + e);
			}
	}
	
	
	@RequestMapping(value = "/ticket/GetRegistroPDF.htm", method = RequestMethod.GET)
	public void registroPDF(@RequestParam("idTicket") String idTicket,
									HttpServletRequest  request, 
									HttpServletResponse response, 
									HttpSession         session){

		    SessionValidatorSTS         sesionValida          = new SessionValidatorSTS();
			ArchivoPropiedades recursos = null;
		    try {
			    String udt = sesionValida.getDatos(session,"unidad");
			    recursos = new ArchivoPropiedades(request);
				String name = recursos.getValorPropiedad("ruta.warehouse.gestion.vivienda")
					+ "//DESA_" + udt + "//PDF//";
		    
					if(!sesionValida.validaSesion(session)) {
						log.info("Sesion invalida");
						request.getRequestDispatcher("/index.jsp").forward(request, response);
				 	} else {
				 		response.setContentType("application/pdf");
				 		String nombre = request.getParameter("idTicket")+".pdf";
				 		response.setHeader("Content-Disposition", "attachment; filename=" + nombre);
				 		//response.setHeader("Content-Disposition", "inline; filename=\"" + nombre + "\"");
	
				 		File file = new File(name + request.getParameter("idTicket") + ".pdf");
				 		FileInputStream fileIn = new FileInputStream(file);
				 		ServletOutputStream outstream = response.getOutputStream();
				 		response.setContentLength((int) file.length());
	
				 		byte[] outputByte = new byte[40096];
	
				 		while(fileIn.read(outputByte, 0, 40096) != -1)
				 		{
				 		    outstream.write(outputByte, 0, 40096);
				 		}
				 		fileIn.close();
				 		outstream.flush();
				 		outstream.close();
				 	}
		    } catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("ERROR:" + e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("ERROR:" + e);
			}
	}
	
	
	@RequestMapping(value = "/ticket/LlenaDatosClienteRegistroTicket.htm", method = RequestMethod.POST)
	public @ResponseBody ClienteDatosTicketVo llenaDatosClienteRegistroTicket(@RequestParam("equipo") String equipo,
									HttpServletRequest  request, 
									HttpServletResponse response, 
									HttpSession         session){

		    SessionValidatorSTS         sesionValida          = new SessionValidatorSTS();
		    CriteriosGetTicketConstruccion criteriosGetTicketConstruccion =  new CriteriosGetTicketConstruccion();
			criteriosGetTicketConstruccion.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
			criteriosGetTicketConstruccion.setUsuario(sesionValida.getDatos(session,"usuario"));
			criteriosGetTicketConstruccion.setIdEquipo(equipo);
			
			ClienteDatosTicketVo respuesta = new ClienteDatosTicketVo();
		    try {
		    
			if(!sesionValida.validaSesion(session)) {
			    log.info("Sesion invalida");
					request.getRequestDispatcher("/index.jsp").forward(request, response);
			 	} else {
			 		respuesta = ticketsDAO.obtieneDatosCliente(criteriosGetTicketConstruccion);	
			 	}
		    } catch (ServletException e) {
				e.printStackTrace();
				respuesta.setMensaje("FAULT");
				respuesta.setDescripcion(e.getMessage());
				log.error("ERROR:" + e);
			} catch (IOException e) {
				e.printStackTrace();
				respuesta.setMensaje("FAULT");
				respuesta.setDescripcion(e.getMessage());
				log.error("ERROR:" + e);
			}catch (ViviendaException e){			
				//log.error("ERROR:", e);
				respuesta.setMensaje("FAULT");
				respuesta.setDescripcion(e.getMessage());
			}
		    return respuesta;
	}
	
	
	@RequestMapping(value = "/ticket/LlenaCatalogoViciosTicket.htm", method = RequestMethod.POST)
	public @ResponseBody ViciosResponse LlenaCatalogoViciosTicket(HttpServletRequest  request, 
									HttpServletResponse response, 
									HttpSession         session){

		    SessionValidatorSTS         sesionValida          = new SessionValidatorSTS();

			
		    ViciosResponse respuesta = new ViciosResponse();
		    try {
		    
			if(!sesionValida.validaSesion(session)) {
			    log.info("Sesion invalida");
					request.getRequestDispatcher("/index.jsp").forward(request, response);
			 	} else {
			 		respuesta = ticketsDAO.obtieneCatalogoVicios();	
			 	}
		    } catch (ServletException e) {
				e.printStackTrace();
				respuesta.setMensaje("FAULT");
				respuesta.setDescripcion(e.getMessage());
				log.error("ERROR:" + e);
			} catch (IOException e) {
				e.printStackTrace();
				respuesta.setMensaje("FAULT");
				respuesta.setDescripcion(e.getMessage());
				log.error("ERROR:" + e);
			}catch (ViviendaException e){			
				//log.error("ERROR:", e);
				respuesta.setMensaje("FAULT");
				respuesta.setDescripcion(e.getMessage());
			}
		    return respuesta;
	}
	
	
	@RequestMapping(value = "/ticket/getLogViciosTicketById.htm", method = RequestMethod.POST)
	public @ResponseBody ResponseLogConstruccionDto getLogViciosTicketById(@RequestParam("idTicket") String idTicket,
		HttpServletRequest  request, 
		HttpServletResponse response, 
		HttpSession         session) {
		ResponseLogConstruccionDto consultaSAP = new ResponseLogConstruccionDto();
		CriteriosGetTicketConstruccion criteriosGetTicketConstruccion = new CriteriosGetTicketConstruccion();
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		if(!sesionValida.validaSesion(session)) {			
			consultaSAP.setMensaje("LOGOUT");
			consultaSAP.setDescripcion("");
			return consultaSAP;
		 }
		
		
		criteriosGetTicketConstruccion.setIdTicket(idTicket);
		criteriosGetTicketConstruccion.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
		criteriosGetTicketConstruccion.setUsuario(sesionValida.getDatos(session,"usuario"));
		
		try {
						
			consultaSAP = ticketsDAO.getLogTicketConstruccion(criteriosGetTicketConstruccion);

		} catch (ViviendaException e) {
			consultaSAP.setMensaje("FAIL");
			consultaSAP.setDescripcion(e.getMessage());
			//log.error("ERROR:" + e);
		}

		return consultaSAP;	
	}
	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para parametros 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina de redireccion 
	 */	
	@RequestMapping(value = "/ticket/WindowRegistraImagenes.htm", method = RequestMethod.GET)
	public String gridCargaImagenes(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else	 {
			 redir = "/ticket/RegistroImagenesViciosOcultos";
		 }
		 return redir;
	}
	
	
	@RequestMapping(value="/ticket/RegistraImagenViciosOcultos.htm", method = RequestMethod.POST)
	//public String setRegistraTicketConstruccion(@ModelAttribute("ListaFicheros") ListaFicheros  listaFicheros, BindingResult result, HttpServletRequest request, HttpSession session, Map model) {
	//public String setRegistraImagenViciosOcultos(@RequestParam("idTicket") String idTicket, @RequestParam("consecutivo") String consecutivo, @RequestParam("imagenViciosOcultos") List<MultipartFile> imagenViciosOcultos, HttpServletRequest request, HttpSession session, Map model) {
	public @ResponseBody ResponseLogConstruccionDto setRegistraImagenViciosOcultos(@RequestParam("idTicket") String idTicket, @RequestParam("consecutivo") String consecutivo, @RequestParam("imagenViciosOcultos") List<MultipartFile> imagenViciosOcultos, HttpServletRequest request, HttpSession session) {
			
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		ResponseAddTicketDto response = new ResponseAddTicketDto();
		ResponseLogConstruccionDto consultaSAP = new ResponseLogConstruccionDto();
		
		if(!sesionValida.validaSesion(session)) {			
			consultaSAP.setMensaje("LOGOUT");
			consultaSAP.setDescripcion("");
			return consultaSAP;
		 }
		
			ArchivoPropiedades recursos = null;
			
			log.info("usuario: " + sesionValida.getDatos(session,"usuario"));
			log.info("idTicket: " + idTicket + ", consecutivo: " + consecutivo);

			try {
			
			List<MultipartFile> ficheros = imagenViciosOcultos;
			
 			String udt = sesionValida.getDatos(session,"unidad");
			recursos = new ArchivoPropiedades(request);
			String path = recursos.getValorPropiedad("ruta.warehouse.gestion.vivienda")
					+ "//DESA_" + udt + "//IMAGENES//";
			
			
			ticketsDAO.addImagenTicket(ficheros, idTicket, consecutivo, path);
			
			consultaSAP.setMensaje("SUCCESS");
			consultaSAP.setId_ticket_sap(idTicket);
			consultaSAP.setId_ticket_z(consecutivo);
			 
			} 	catch (Exception e) {
				response.setMensaje("FAULT");
				response.setDescripcion(e.getMessage());
				log.error("ERROR:" + e);
			}
			
			return consultaSAP;
	}
	
}