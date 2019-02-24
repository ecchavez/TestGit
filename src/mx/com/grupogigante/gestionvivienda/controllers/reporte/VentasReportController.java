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

import mx.com.grupogigante.gestionvivienda.dao.report.VentasReporteDao;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.report.ventas.dto.VentasReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.ventas.dto.VentasReporteResponse;
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
public class VentasReportController {
	private Logger log = Logger.getLogger(VentasReportController.class);

	@Autowired
	VentasReporteDao ventasDao;
	
	@RequestMapping(value = "/report/ventas/FiltroBusquedaView.htm", method = RequestMethod.GET)
	public String filtroBusquedaReporteView (Model model,HttpSession session) {
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		return !validaSesion.validaSesion(session)? "../../index":"/report/ventas/FiltroBusquedaView"; 
	}
	
	@RequestMapping(value = "/report/ventas/ConsultaVentasPreview.htm", method = RequestMethod.POST)
	public @ResponseBody VentasReporteResponse consultaVentas(
													@RequestParam("idEstatusReporte") String idEstatusReporte,
													@RequestParam("idFaseReporte") String idFaseReporte,
													@RequestParam("idEquipoInicialReporte") String idEquipoInicialReporte,
													@RequestParam("idEquipoFinalReporte") String idEquipoFinalReporte,
													HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    VentasReporteRequest  ventasRequest  = new VentasReporteRequest();
		    VentasReporteResponse ventasResponse = new VentasReporteResponse(); 
		    if(!sesionValida.validaSesion(session)) {			
		    	ventasResponse.setMensaje("LOGOUT");
		    	ventasResponse.setDescripcion("");
				return ventasResponse;
			 }
		try {
			log.info("entrando");
			ventasRequest.setIdUsuario(sesionValida.getDatos(session,"usuario"));
			ventasRequest.setUnidadTecnicaSuperior(sesionValida.getDatos(session,"unidad"));
			ventasRequest.setIdFase(idFaseReporte);
			ventasRequest.setIdEquipoInicial(idEquipoInicialReporte);
			ventasRequest.setIdEquipoFinal(idEquipoFinalReporte);
			ventasRequest.setIdEstatus(idEstatusReporte);
			
			ventasResponse = ventasDao.findVentas(ventasRequest);
			
			//log.info("saliendo:" + ventasResponse.getListaVentas());
		} catch (ReporteException e) {
			log.error("ERROR:", e);
		}
		return ventasResponse;
	}

	@RequestMapping(value = "/report/ventas/GeneraReporteVentas.htm", method = RequestMethod.GET)
	public void generaReporteVentas(
											@RequestParam("idEstatusReporte") String idEstatusReporte,
											@RequestParam("idFaseReporte") String idFaseReporte,
											@RequestParam("idEquipoInicialReporte") String idEquipoInicialReporte,
											@RequestParam("idEquipoFinalReporte") String idEquipoFinalReporte,
										    HttpServletRequest  request, 
										    HttpServletResponse response, 
											HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    VentasReporteRequest  ventasRequest  = new VentasReporteRequest();
		    VentasReporteResponse ventasResponse = new VentasReporteResponse(); 
		try {
		    if(!sesionValida.validaSesion(session)) {			
			    log.info("Sesion invalida");
			    request.getRequestDispatcher("/index.jsp").forward(request, response);
			 } else {
					ventasRequest.setIdUsuario(sesionValida.getDatos(session,"usuario"));
					ventasRequest.setUnidadTecnicaSuperior(sesionValida.getDatos(session,"unidad"));
					ventasRequest.setIdFase(idFaseReporte);
					ventasRequest.setIdEquipoInicial(idEquipoInicialReporte);
					ventasRequest.setIdEquipoFinal(idEquipoFinalReporte);
					ventasRequest.setIdEstatus(idEstatusReporte);
					
					ventasResponse = ventasDao.findVentas(ventasRequest);

					JRBeanCollectionDataSource dataSource    = new JRBeanCollectionDataSource(ventasResponse.getListaVentas());
					
					ArchivoPropiedades recursos      = new ArchivoPropiedades(request);
					String             nombreArchivo =  recursos.getValorPropiedad("ruta.reportesg.gestion.vivienda") + "Ventas.jrxml";
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
					response.setHeader("Content-Disposition", "attachment; filename=Ventas.pdf");
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
	
	
	@RequestMapping(value = "/report/ventas/GeneraReporteVentasExcel.htm", method = RequestMethod.GET)
	public void generaReporteVentasExcel(
											@RequestParam("idEstatusReporte") String idEstatusReporte,
											@RequestParam("idFaseReporte") String idFaseReporte,
											@RequestParam("idEquipoInicialReporte") String idEquipoInicialReporte,
											@RequestParam("idEquipoFinalReporte") String idEquipoFinalReporte,
										    HttpServletRequest  request, 
										    HttpServletResponse response, 
											HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    VentasReporteRequest  ventasRequest  = new VentasReporteRequest();
		    VentasReporteResponse ventasResponse = new VentasReporteResponse(); 
		try {
		    if(!sesionValida.validaSesion(session)) {			
			    log.info("Sesion invalida");
			    request.getRequestDispatcher("/index.jsp").forward(request, response);
			 } else {
					ventasRequest.setIdUsuario(sesionValida.getDatos(session,"usuario"));
					ventasRequest.setUnidadTecnicaSuperior(sesionValida.getDatos(session,"unidad"));
					ventasRequest.setIdFase(idFaseReporte);
					ventasRequest.setIdEquipoInicial(idEquipoInicialReporte);
					ventasRequest.setIdEquipoFinal(idEquipoFinalReporte);
					ventasRequest.setIdEstatus(idEstatusReporte);
					
					ventasResponse = ventasDao.findVentas(ventasRequest);

					JRBeanCollectionDataSource dataSource    = new JRBeanCollectionDataSource(ventasResponse.getListaVentas());
					
					ArchivoPropiedades recursos      = new ArchivoPropiedades(request);
					String             nombreArchivo =  recursos.getValorPropiedad("ruta.reportesg.gestion.vivienda") + "VentasExcel.jrxml";
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
					response.setHeader("Content-Disposition", "attachment; filename=Ventas.xls");
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
	@RequestMapping(value = "/report/ventas/ReporteVentasFilterView.htm", method = RequestMethod.GET)
	public String reporteEstadoCuentaFilterView(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else {
			 redir = "/report/ventas/ConsultaVentasReporteFilter";
		 }
		 return redir;	
	}	
}
