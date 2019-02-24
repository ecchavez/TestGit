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

import mx.com.grupogigante.gestionvivienda.dao.report.ReferenciasReporteDao;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.report.referencias.dto.ReferenciasReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.referencias.dto.ReferenciasReporteResponse;
import mx.com.grupogigante.gestionvivienda.report.referencias.dto.ReferenciasReporteRowDto;
import mx.com.grupogigante.gestionvivienda.resources.ArchivoPropiedades;
import mx.com.grupogigante.gestionvivienda.utils.SessionValidatorSTS;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

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
public class ReferenciasReportController {
	private Logger log = Logger.getLogger(ReferenciasReportController.class);

	@Autowired
	ReferenciasReporteDao referenciasDao;
	
	@RequestMapping(value = "/report/referencias/FiltroBusquedaView.htm", method = RequestMethod.GET)
	public String filtroBusquedaReporteView (Model model,HttpSession session) {
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		return !validaSesion.validaSesion(session)? "../../index":"/report/referencias/FiltroBusquedaView"; 
	}
	
	@RequestMapping(value = "/report/referencias/ConsultaReferenciasPreview.htm", method = RequestMethod.POST)
	public @ResponseBody ReferenciasReporteResponse consultaReferencias(
													@RequestParam("idFaseLow") String idFaseLow,
													@RequestParam("idBancoLow") String idBancoLow,
													@RequestParam("idEquipoInicialReporte") String idEquipoInicialReporte,
													@RequestParam("idEquipoFinalReporte") String idEquipoFinalReporte,
													HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    ReferenciasReporteRequest  referenciasRequest  = new ReferenciasReporteRequest();
		    ReferenciasReporteResponse referenciasResponse = new ReferenciasReporteResponse(); 
		    if(!sesionValida.validaSesion(session)) {			
		    	referenciasResponse.setMensaje("LOGOUT");
		    	referenciasResponse.setDescripcion("");
				return referenciasResponse;
			 }
		try {
			log.info("entrando");
			referenciasRequest.setIdUsuario(sesionValida.getDatos(session,"usuario"));
			referenciasRequest.setUnidadTecnicaSuperior(sesionValida.getDatos(session,"unidad"));
			referenciasRequest.setFaseLow(idFaseLow);
			//referenciasRequest.setFaseHigh(idFaseHigh);
			referenciasRequest.setIdEquipoInicial(idEquipoInicialReporte);
			referenciasRequest.setIdEquipoFinal(idEquipoFinalReporte);
			referenciasRequest.setBancoLow(idBancoLow);
			//referenciasRequest.setBancoHigh(idBancoHigh);
			
			referenciasResponse = referenciasDao.findReferencias(referenciasRequest);
		} catch (ReporteException e) {
			log.error("ERROR:", e);
		}
		return referenciasResponse;
	}

	@RequestMapping(value = "/report/referencias/GeneraReporteReferencias.htm", method = RequestMethod.GET)
	public void generaReporteReferencias(
											@RequestParam("idFaseLow") String idFaseLow,
											@RequestParam("idBancoLow") String idBancoLow,
											@RequestParam("idEquipoInicialReporte") String idEquipoInicialReporte,
											@RequestParam("idEquipoFinalReporte") String idEquipoFinalReporte,
										    HttpServletRequest  request, 
										    HttpServletResponse response, 
											HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    ReferenciasReporteRequest  referenciasRequest  = new ReferenciasReporteRequest();
		    ReferenciasReporteResponse referenciasResponse = new ReferenciasReporteResponse(); 
		try {
		    if(!sesionValida.validaSesion(session)) {			
			    log.info("Sesion invalida");
			    request.getRequestDispatcher("/index.jsp").forward(request, response);
			 } else {
					referenciasRequest.setIdUsuario(sesionValida.getDatos(session,"usuario"));
					referenciasRequest.setUnidadTecnicaSuperior(sesionValida.getDatos(session,"unidad"));
					referenciasRequest.setFaseLow(idFaseLow);
					//referenciasRequest.setFaseHigh(idFaseHigh);
					referenciasRequest.setIdEquipoInicial(idEquipoInicialReporte);
					referenciasRequest.setIdEquipoFinal(idEquipoFinalReporte);
					referenciasRequest.setBancoLow(idBancoLow);
					//referenciasRequest.setBancoHigh(idBancoHigh);
					
					referenciasResponse = referenciasDao.findReferencias(referenciasRequest);

					JRBeanCollectionDataSource dataSource    = new JRBeanCollectionDataSource(referenciasResponse.getListaReferencias());

					ArchivoPropiedades recursos      = new ArchivoPropiedades(request);
					String             nombreArchivo =  recursos.getValorPropiedad("ruta.reportesg.gestion.vivienda") + "Referencias.jrxml";
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
					
					//InputStream                is            = this.getClass().getResourceAsStream("/mx/com/grupogigante/gestionvivienda/reportes/referencias/Referencias.jrxml");
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
					response.setHeader("Content-Disposition", "attachment; filename=Referencias.pdf");
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
	
	/**
	 * Método @RequestMapping por metodo GET para la obtencion de la session y 
	 * redireccionamiento de la pagina para aguegar clientes RegistroCarCliente 
	 * 
	 * @param ModelMap   
	 * @param HttpSession 
	 * @return cadena de nueva pagina deredireccion 
	 */	
	@RequestMapping(value = "/report/referencias/ReporteReferenciasFilterView.htm", method = RequestMethod.GET)
	public String reporteEstadoCuentaFilterView(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else {
			 redir = "/report/referencias/ConsultaReferenciasReporteFilter";
		 }
		 return redir;	
	}	
}
