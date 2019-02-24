package mx.com.grupogigante.gestionvivienda.controllers.reporte;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.com.grupogigante.gestionvivienda.dao.ConexionManagerDao;
import mx.com.grupogigante.gestionvivienda.dao.report.ConsultaPagosReporteDao;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseClientActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.ConsultaPagosReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.ConsultaPagosReporteResponse;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.report.ventas.dto.VentasReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.ventas.dto.VentasReporteResponse;
import mx.com.grupogigante.gestionvivienda.resources.ArchivoPropiedades;
import mx.com.grupogigante.gestionvivienda.services.ClientesReporteService;
import mx.com.grupogigante.gestionvivienda.utils.SessionValidatorSTS;
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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ConsultaPagosController {
	private Logger log = Logger.getLogger(ConsultaPagosController.class);
	
	@Autowired
	ClientesReporteService clientesService;
	
	@Autowired
	ConsultaPagosReporteDao consultaPagosReporteDao;
	
	@Autowired
	ConexionManagerDao conexionManagerDao;
	
	@RequestMapping(value = "/report/consultapagos/FiltroBusquedaView.htm", method = RequestMethod.GET)
	public String filtroBusquedaReporteView (Model model,HttpSession session) {
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		return !validaSesion.validaSesion(session)? "../../index":"/report/consultapagos/FiltroBusquedaView"; 
	}
	
	@RequestMapping(value = "/report/consultapagos/CatalogoClientes.htm", method = RequestMethod.POST)
	public @ResponseBody ConsultaPagosReporteResponse getClientes(
													@RequestParam("idClienteReporte") String idClienteReporte,
													@RequestParam("idFaseReporte") String idFaseReporte,
													@RequestParam("idEquipoReporte") String idEquipoReporte,
													@RequestParam("fechaIni") String fechaIni,
													@RequestParam("fechaFin") String fechaFin,
													@RequestParam("idMedioPago") String idMedioPago,
													HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    ConsultaPagosReporteRequest consutaPagosRequest = new ConsultaPagosReporteRequest();
		    ConsultaPagosReporteResponse consultaPagosResponse = new ConsultaPagosReporteResponse(); 

		    if(!sesionValida.validaSesion(session)) {			
		    	consultaPagosResponse.setMensaje("LOGOUT");
		    	consultaPagosResponse.setDescripcion("");
				return consultaPagosResponse;
			 }
		    
		try {
			log.info("entrando");
			consutaPagosRequest.setIdUsuario(sesionValida.getDatos(session,"usuario"));
			consutaPagosRequest.setIdUnidadTecnicaSuperior(sesionValida.getDatos(session,"unidad"));
			consutaPagosRequest.setIdCliente(idClienteReporte);
			consutaPagosRequest.setIdFase(idFaseReporte);
			consutaPagosRequest.setIdEquipo(idEquipoReporte);
			consutaPagosRequest.setFechaIni(fechaIni);
			consutaPagosRequest.setFechaFin(fechaFin);
			consutaPagosRequest.setIdMedioPago(idMedioPago);
			
			log.info("CONSULTA REQUEST:" + consutaPagosRequest);
			consultaPagosResponse = consultaPagosReporteDao.buscaReporte(consutaPagosRequest);
			log.info("saliendo");
		} catch (ReporteException e) {
			log.error("ERROR:", e);
		}
		return consultaPagosResponse;
	}
	
	@RequestMapping(value = "/report/consultapagos/GeneraReportePagosPDF.htm", method = RequestMethod.GET)
	public void generaReporteVentas(
											@RequestParam("idClienteReporte") String idClienteReporte,
											@RequestParam("idFaseReporte") String idFaseReporte,
											@RequestParam("idEquipoReporte") String idEquipoReporte,
											@RequestParam("fechaIni") String fechaIni,
											@RequestParam("fechaFin") String fechaFin,
											@RequestParam("idMedioPago") String idMedioPago,
										    HttpServletRequest  request, 
										    HttpServletResponse response, 
											HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    ConsultaPagosReporteRequest  pagosRequest  = new ConsultaPagosReporteRequest();
		    ConsultaPagosReporteResponse pagosResponse = new ConsultaPagosReporteResponse();  
		try {
		    if(!sesionValida.validaSesion(session)) {			
			    log.info("Sesion invalida");
			    request.getRequestDispatcher("/index.jsp").forward(request, response);
			 } else {
				    pagosRequest.setIdUsuario(sesionValida.getDatos(session,"usuario"));
					pagosRequest.setIdUnidadTecnicaSuperior(sesionValida.getDatos(session,"unidad"));
				 	pagosRequest.setIdFase(idFaseReporte);
				 	pagosRequest.setIdCliente(idClienteReporte);
				 	pagosRequest.setIdEquipo(idEquipoReporte);
				 	pagosRequest.setFechaIni(fechaIni);
				 	pagosRequest.setFechaFin(fechaFin);
				 	pagosRequest.setIdMedioPago(idMedioPago);
					
				 	pagosResponse = consultaPagosReporteDao.buscaReporte(pagosRequest);

				 	JRBeanCollectionDataSource dataSource    = new JRBeanCollectionDataSource(pagosResponse.getLayout());
					
					ArchivoPropiedades recursos      = new ArchivoPropiedades(request);
					String             nombreArchivo =  recursos.getValorPropiedad("ruta.reportesg.gestion.vivienda") + "ConsultaPagosPDF.jrxml";
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
					JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
					ByteArrayOutputStream  baos      = new ByteArrayOutputStream();
					JRPdfExporter          exporter  = new JRPdfExporter();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
					exporter.exportReport();
					response.setContentType("application/octet-stream");
					//response.setHeader ("Content-disposition", "attachment; filename=" + ( idEquipoCompleto != null && idEquipoCompleto.trim().length() > 0 ? idEquipoCompleto + ".pdf":"EstadoCuentaReporte.pdf"))
					response.setHeader("Content-Disposition", "attachment; filename=RegistroPagos.pdf");
					response.setContentType("application/pdf");
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
	
	@RequestMapping(value = "/report/consultapagos/GeneraReportePagosExcel.htm", method = RequestMethod.GET)
	public void generaReportePagosExcel(
											@RequestParam("idClienteReporte") String idClienteReporte,
											@RequestParam("idFaseReporte") String idFaseReporte,
											@RequestParam("idEquipoReporte") String idEquipoReporte,
											@RequestParam("fechaIni") String fechaIni,
											@RequestParam("fechaFin") String fechaFin,
											@RequestParam("idMedioPago") String idMedioPago,
										    HttpServletRequest  request,
										    HttpServletResponse response, 
											HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    ConsultaPagosReporteRequest  pagosRequest  = new ConsultaPagosReporteRequest();
		    ConsultaPagosReporteResponse pagosResponse = new ConsultaPagosReporteResponse(); 
		try {
		    if(!sesionValida.validaSesion(session)) {			
			    log.info("Sesion invalida");
			    request.getRequestDispatcher("/index.jsp").forward(request, response);
			 } else {
				 	pagosRequest.setIdUsuario(sesionValida.getDatos(session,"usuario"));
				 	pagosRequest.setIdUnidadTecnicaSuperior(sesionValida.getDatos(session,"unidad"));
				 	pagosRequest.setIdFase(idFaseReporte);
				 	pagosRequest.setIdCliente(idClienteReporte);
				 	pagosRequest.setIdEquipo(idEquipoReporte);
				 	pagosRequest.setFechaIni(fechaIni);
				 	pagosRequest.setFechaFin(fechaFin);
				 	pagosRequest.setIdMedioPago(idMedioPago);

					
				 	pagosResponse = consultaPagosReporteDao.buscaReporte(pagosRequest);

					JRBeanCollectionDataSource dataSource    = new JRBeanCollectionDataSource(pagosResponse.getLayout());
					
					ArchivoPropiedades recursos      = new ArchivoPropiedades(request);
					String             nombreArchivo =  recursos.getValorPropiedad("ruta.reportesg.gestion.vivienda") + "ConsultaPagos.jrxml";
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
					        
					Map<String, Object> parametros = new HashMap<String, Object>();
					JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
					ByteArrayOutputStream  baos      = new ByteArrayOutputStream();
					
					JRXlsExporter exporterXLS = new JRXlsExporter();
					exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
					exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
					exporterXLS.exportReport();			
					
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition", "attachment; filename=RegistroPagos.xls");
					response.setContentType("application/vnd.ms-excel");
					response.setContentLength(baos.size());
					
					ServletOutputStream outputStream = response.getOutputStream();
					baos.writeTo(outputStream);
					outputStream.flush();
					log.info("saliendo");
			 }
		} catch (Exception e) {
			System.out.print("ERROR:" + e.toString());
		}
	}
	
	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para aguegar clientes RegistroCarCliente 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina deredireccion 
	 */	
	@RequestMapping(value = "/report/consultapagos/ReporteConsultaPagosFilterView.htm", method = RequestMethod.GET)
	public String reporteEstadoCuentaFilterView(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else {
			 redir = "/report/consultapagos/ConsultaPagosReporteFilter";
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
	@RequestMapping(value = "/report/consultapagos/GridBusquedaClienteRegistroReport.htm", method = RequestMethod.GET)
	public String gridBusquedaClienteRegistroTicket(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else	 {
			 redir = "/report/consultapagos/GridBusquedaClienteRegistroReporte";
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
	@RequestMapping(value = "/report/consultapagos/ParamsClienteRegistroReporte.htm", method = RequestMethod.GET)
	public String paramsClienteRegistroReporte (ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else	 {
			 redir = "/report/consultapagos/GridParamsClienteRegistroReporte";
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
	@RequestMapping(value = "/report/consultapagos/CatalogoClientesReporte.htm", method = RequestMethod.POST)
	public @ResponseBody
	ResponseClientActionDto getClientes(
			@ModelAttribute(value = "traerClientes") CriteriosGetInfCarCliente criterios,
			BindingResult result,HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    ResponseClientActionDto responseAction = new ResponseClientActionDto();
		    if(!sesionValida.validaSesion(session)) {
		    	ResponseGetInfCarCliente resGetClientesDTO = new ResponseGetInfCarCliente();
		    	resGetClientesDTO.setMensaje("LOGOUT");
		    	resGetClientesDTO.setDescripcion("");
		    	responseAction.setRespGetInfCarCliente(resGetClientesDTO);
				return responseAction;
			 }
		    
		try {
			criterios.setUsuario(sesionValida.getDatos(session,"usuario"));
			criterios.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
			ResponseGetInfCarCliente resGetClientesDTO = clientesService.getClientesReporte(criterios);
			responseAction.setRespGetInfCarCliente(resGetClientesDTO);
		} catch (ViviendaException e) {
			log.error("ERROR:", e);
		}
		return responseAction;
	}
	
	/**
	 * Método que extrae la imagen del registro de pago en caseta
	 * @param folio
	 */
	@RequestMapping(value = "/reportes/consultapago/extraerDigitalizacionPago.htm", method = RequestMethod.GET)
    public void extraerDigitalizacionPago(
    		@RequestParam("fileName") String fileName,
    		ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) {
		log.info("Ejecucion del metodo extraerDigitalizacionPago");
		
		try {						
			 	
			   ByteArrayOutputStream pdf = conexionManagerDao.getImagen(fileName);
			   
			   if(pdf != null){
				   String nombreArchivo = "pago_"+ fileName +".pdf";
				   response.setHeader("Content-Disposition", "inline;filename=\"" + nombreArchivo + "\"");
				   
				   response.setContentType("application/pdf");
				   response.setContentLength(pdf.size());
				   
				   OutputStream out = response.getOutputStream();
				   out.write(pdf.toByteArray());
				   out.flush();
				   out.close();
			   }else{
				   request.setAttribute("result_error", "No se ha generado reporte de contratos y pedidos para el proyecto " + fileName);
			   }
		} catch (Exception e) {
			request.setAttribute("result_error", "Problema para generar reporte de pedidos y contratos para el proyecto " + fileName);
		}
    }
	
	/**
	 * Método que valida si existe o no la imagen de acuerdo al folio seleccionado.
	 * @param fileName
	 */
	@RequestMapping(value = "/reportes/consultapago/validarDigitalizacionPago.htm", method = RequestMethod.POST)
	@ResponseBody
    public Boolean validarDigitalizacionPago(
    		@RequestParam("fileName") String fileName,
    		ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) {
		log.info("Ejecucion del metodo validarDigitalizacionPago");
		Boolean existe = false;		
		try {									 	
			   ByteArrayOutputStream pdf = conexionManagerDao.getImagen(fileName);			   
			   if(pdf != null){
				   existe = true;
			   }
		} catch (Exception e) {
			request.setAttribute("result_error", "Problema para generar reporte de pedidos y contratos para el proyecto " + fileName);
		}
		return existe;
    }

}
