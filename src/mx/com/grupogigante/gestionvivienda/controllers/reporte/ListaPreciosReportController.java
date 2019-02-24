package mx.com.grupogigante.gestionvivienda.controllers.reporte;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.com.grupogigante.gestionvivienda.dao.UbicacionesDao;
import mx.com.grupogigante.gestionvivienda.dao.report.ListaPreciosReporteDao;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosEquipoTiposDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.EquipoTiposDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionDatosMapaDto;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.report.listaprecio.dto.ListaPrecioReporteRequestDto;
import mx.com.grupogigante.gestionvivienda.report.listaprecio.dto.ListaPrecioReporteResponseDto;
import mx.com.grupogigante.gestionvivienda.resources.ArchivoPropiedades;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ListaPreciosReportController {
	private Logger log = Logger.getLogger(ListaPreciosReportController.class);

	@Autowired
	ListaPreciosReporteDao listaPreciosDao;
	@Autowired
	UbicacionesDao         ubicacionesDao;
	
	@RequestMapping(value = "/report/listaprecios/TiposView.htm", method = RequestMethod.POST)
	public @ResponseBody ResponseUbicacionDatosMapaDto getListaTipos(@RequestParam("idFaseReporte") String idFaseReporte, HttpSession session) {
		CriteriosEquipoTiposDto criterios = new CriteriosEquipoTiposDto();
		ResponseUbicacionDatosMapaDto response = null;
		try {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
			criterios.setI_id_ut_sup(sesionValida.getDatos(session,"unidad"));
			criterios.setI_usuario(sesionValida.getDatos(session,"usuario"));
			criterios.setI_id_fase(idFaseReporte);
			response = ubicacionesDao.getTiposEquipos(criterios);
			EquipoTiposDto equipoInicial = new EquipoTiposDto();
			equipoInicial.setI_tipo_eq("");
			response.getListaTipos().add(0, equipoInicial);
			//log.info(response.getListaTipos());
		} catch (Exception e) {
			log.error("ERROR:", e);
		}
		return response;
	}
	
	@RequestMapping(value = "/report/listaprecios/FiltroBusquedaView.htm", method = RequestMethod.GET)
	public String filtroBusquedaReporteView (Model model,HttpSession session) {
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		return !validaSesion.validaSesion(session)? "../../index":"/report/listaprecios/FiltroBusquedaView"; 
	}
	
	@RequestMapping(value = "/report/listaprecios/ConsultaListaPreciosPreview.htm", method = RequestMethod.POST)
	public @ResponseBody ListaPrecioReporteResponseDto consultaListaPrecio(
													@RequestParam("idTipoReporte") String idTipoReporte,
													@RequestParam("idFaseReporte") String idFaseReporte,
													@RequestParam("idEquipoInicialReporte") String idEquipoInicialReporte,
													@RequestParam("idEquipoFinalReporte") String idEquipoFinalReporte,
													HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    ListaPrecioReporteRequestDto  listaPrecioRequest  = new ListaPrecioReporteRequestDto();
		    ListaPrecioReporteResponseDto listaPrecioResponse = new ListaPrecioReporteResponseDto(); 
		    if(!sesionValida.validaSesion(session)) {			
		    	listaPrecioResponse.setMensaje("LOGOUT");
		    	listaPrecioResponse.setDescripcion("");
				return listaPrecioResponse;
			 }

		try {
			log.info("entrando");
			listaPrecioRequest.setIdUsuario(sesionValida.getDatos(session,"usuario"));
			listaPrecioRequest.setUnidadTecnicaSuperior(sesionValida.getDatos(session,"unidad"));
			listaPrecioRequest.setIdFase(idFaseReporte);
			listaPrecioRequest.setIdEquipoInicial(idEquipoInicialReporte);
			listaPrecioRequest.setIdEquipoFinal(idEquipoFinalReporte);
			listaPrecioRequest.setIdTipo(idTipoReporte);
			
			listaPrecioResponse = listaPreciosDao.findListaPrecios(listaPrecioRequest);
			
		} catch (ReporteException e) {
			log.error("ERROR:", e);
		}
		return listaPrecioResponse;
	}

	@RequestMapping(value = "/report/listaprecios/GeneraReporteListaPrecios.htm", method = RequestMethod.GET)
	public void generaReporteDisponibilidad(
											@RequestParam("idTipoReporte") String idTipoReporte,
											@RequestParam("idFaseReporte") String idFaseReporte,
											@RequestParam("idEquipoInicialReporte") String idEquipoInicialReporte,
											@RequestParam("idEquipoFinalReporte") String idEquipoFinalReporte,
										    HttpServletRequest  request, 
										    HttpServletResponse response, 
											HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    ListaPrecioReporteRequestDto  listaPrecioRequest  = new ListaPrecioReporteRequestDto();
		    ListaPrecioReporteResponseDto listaPrecioResponse = new ListaPrecioReporteResponseDto(); 
		try {
		    if(!sesionValida.validaSesion(session)) {			
			    log.info("Sesion invalida");
			    request.getRequestDispatcher("/index.jsp").forward(request, response);
			 } else {
					listaPrecioRequest.setIdUsuario(sesionValida.getDatos(session,"usuario"));
					listaPrecioRequest.setUnidadTecnicaSuperior(sesionValida.getDatos(session,"unidad"));
					listaPrecioRequest.setIdFase(idFaseReporte);
					listaPrecioRequest.setIdEquipoInicial(idEquipoInicialReporte);
					listaPrecioRequest.setIdEquipoFinal(idEquipoFinalReporte);
					listaPrecioRequest.setIdTipo(idTipoReporte);
					
					listaPrecioResponse = listaPreciosDao.findListaPrecios(listaPrecioRequest);

					JRBeanCollectionDataSource dataSource    = new JRBeanCollectionDataSource(listaPrecioResponse.getListaPrecio());

					ArchivoPropiedades recursos      = new ArchivoPropiedades(request);
					String             nombreArchivo =  recursos.getValorPropiedad("ruta.reportesg.gestion.vivienda") + "ListaPrecios.jrxml";
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
					
					//InputStream                is            = this.getClass().getResourceAsStream("/mx/com/grupogigante/gestionvivienda/reportes/listaprecios/ListaPrecios.jrxml");
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
					response.setHeader("Content-Disposition", "attachment; filename=ListaPrecios.pdf");
					response.setContentType("application/pdf");
					response.setContentLength(baos.size());
					ServletOutputStream outputStream = response.getOutputStream();
					baos.writeTo(outputStream);
					outputStream.flush();
					log.info("saliendo");
			 }
		} catch (ReporteException e) {
			log.error("ERROR:", e);
		} catch (Exception e) {
			log.error("ERROR:", e);
		}
	}

	@RequestMapping(value = "/report/listaprecios/GeneraReporteListaPreciosExcel.htm", method = RequestMethod.GET)
	public void generaReporteDisponibilidadExcel(
											@RequestParam("idTipoReporte") String idTipoReporte,
											@RequestParam("idFaseReporte") String idFaseReporte,
											@RequestParam("idEquipoInicialReporte") String idEquipoInicialReporte,
											@RequestParam("idEquipoFinalReporte") String idEquipoFinalReporte,
										    HttpServletRequest  request, 
										    HttpServletResponse response, 
											HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    ListaPrecioReporteRequestDto  listaPrecioRequest  = new ListaPrecioReporteRequestDto();
		    ListaPrecioReporteResponseDto listaPrecioResponse = new ListaPrecioReporteResponseDto(); 
		try {
		    if(!sesionValida.validaSesion(session)) {			
			    log.info("Sesion invalida");
			    request.getRequestDispatcher("/index.jsp").forward(request, response);
			 } else {
					listaPrecioRequest.setIdUsuario(sesionValida.getDatos(session,"usuario"));
					listaPrecioRequest.setUnidadTecnicaSuperior(sesionValida.getDatos(session,"unidad"));
					listaPrecioRequest.setIdFase(idFaseReporte);
					listaPrecioRequest.setIdEquipoInicial(idEquipoInicialReporte);
					listaPrecioRequest.setIdEquipoFinal(idEquipoFinalReporte);
					listaPrecioRequest.setIdTipo(idTipoReporte);
					
					listaPrecioResponse = listaPreciosDao.findListaPrecios(listaPrecioRequest);

					JRBeanCollectionDataSource dataSource    = new JRBeanCollectionDataSource(listaPrecioResponse.getListaPrecio());
					InputStream                is            = this.getClass().getResourceAsStream("/mx/com/grupogigante/gestionvivienda/reportes/listaprecios/ListaPreciosExcel.jrxml");
					JasperReport               jasperReport  = (JasperReport) JasperCompileManager.compileReport(is);        

					Map parametros = new HashMap();
					JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
					ByteArrayOutputStream  baos      = new ByteArrayOutputStream();
					//JRPdfExporter          exporter  = new JRPdfExporter();
					//exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
					//exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
					//exporter.exportReport();
					JRXlsExporter exporterXLS = new JRXlsExporter();
					exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
					exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
					exporterXLS.exportReport(); 			
					response.setContentType("application/vnd.ms-excel");
					//response.setHeader ("Content-disposition", "attachment; filename=" + ( idEquipoCompleto != null && idEquipoCompleto.trim().length() > 0 ? idEquipoCompleto + ".pdf":"EstadoCuentaReporte.pdf"))
					response.setHeader("Content-Disposition", "attachment; filename=ListaPrecios.xls");
					response.setContentType("application/vnd.ms-excel");
					response.setContentLength(baos.size());
					ServletOutputStream outputStream = response.getOutputStream();
					baos.writeTo(outputStream);
					outputStream.flush();
					log.info("saliendo");				 
			 }
		} catch (ReporteException e) {
			log.error("ERROR:", e);
		} catch (Exception e) {
			log.error("ERROR:", e);
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
	@RequestMapping(value = "/report/listaprecios/ReporteListaPreciosFilterView.htm", method = RequestMethod.GET)
	public String reporteEstadoCuentaFilterView(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else {
			 redir = "/report/listaprecios/ConsultaListaPreciosReporteFilter";
		 }
		 return redir;	
	}	
}
