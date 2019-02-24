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

import mx.com.grupogigante.gestionvivienda.dao.report.ContratosReporteDao;
import mx.com.grupogigante.gestionvivienda.report.contratos.dto.ContratosReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.contratos.dto.ContratosReporteResponse;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
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
public class ContratosReportController {
	private Logger log = Logger.getLogger(ContratosReportController.class);

	@Autowired
	ContratosReporteDao contratosDao;
	
	@RequestMapping(value = "/report/contratos/FiltroBusquedaView.htm", method = RequestMethod.GET)
	public String filtroBusquedaReporteView (Model model,HttpSession session) {
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		return !validaSesion.validaSesion(session)? "../../index":"/report/contratos/FiltroBusquedaView"; 
	}

	@RequestMapping(value = "/report/contratos/ConsultaEstatus.htm", method = RequestMethod.POST)
	public @ResponseBody ContratosReporteResponse consultaEstatus(HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    ContratosReporteRequest  contratosRequest  = new ContratosReporteRequest();
		    ContratosReporteResponse contratosResponse = new ContratosReporteResponse(); 
		try {
			log.info("entrando");
			contratosRequest.setIdUsuario(sesionValida.getDatos(session,"usuario"));
			contratosRequest.setUnidadTecnicaSuperior(sesionValida.getDatos(session,"unidad"));
			
			contratosResponse = contratosDao.findEstatusAll(contratosRequest);
			
			log.info(contratosResponse.getListaContrato());
			//log.info("saliendo:" + contratosResponse.getListaContratos());
		} catch (ReporteException e) {
			log.error("ERROR:", e);
		}
		return contratosResponse;
	}
	
	@RequestMapping(value = "/report/contratos/ConsultaContratosPreview.htm", method = RequestMethod.POST)
	public @ResponseBody ContratosReporteResponse consultaContratos(
													@RequestParam("idEstatusReporte") String idEstatusReporte,
													@RequestParam("idFaseReporte") String idFaseReporte,
													@RequestParam("idEquipoInicialReporte") String idEquipoInicialReporte,
													@RequestParam("idEquipoFinalReporte") String idEquipoFinalReporte,
													HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    ContratosReporteRequest  contratosRequest  = new ContratosReporteRequest();
		    ContratosReporteResponse contratosResponse = new ContratosReporteResponse(); 
		try {
			log.info("entrando");
			contratosRequest.setIdUsuario(sesionValida.getDatos(session,"usuario"));
			contratosRequest.setUnidadTecnicaSuperior(sesionValida.getDatos(session,"unidad"));
			contratosRequest.setIdFase(idFaseReporte);
			contratosRequest.setIdEquipoInicial(idEquipoInicialReporte);
			contratosRequest.setIdEquipoFinal(idEquipoFinalReporte);
			contratosRequest.setIdEstatus(idEstatusReporte);
			
			contratosResponse = contratosDao.findContratos(contratosRequest);
			
			log.info(contratosResponse.getListaContrato());
			//log.info("saliendo:" + contratosResponse.getListaContratos());
		} catch (ReporteException e) {
			log.error("ERROR:", e);
		}
		return contratosResponse;
	}

	@RequestMapping(value = "/report/contratos/GeneraReporteContratos.htm", method = RequestMethod.GET)
	public void generaReporteContratos(
											@RequestParam("idEstatusReporte") String idEstatusReporte,
											@RequestParam("idFaseReporte") String idFaseReporte,
											@RequestParam("idEquipoInicialReporte") String idEquipoInicialReporte,
											@RequestParam("idEquipoFinalReporte") String idEquipoFinalReporte,
										    HttpServletRequest  request, 
										    HttpServletResponse response, 
											HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    ContratosReporteRequest  contratosRequest  = new ContratosReporteRequest();
		    ContratosReporteResponse contratosResponse = new ContratosReporteResponse(); 
		try {
			log.info("entrando reporte x");
			contratosRequest.setIdUsuario(sesionValida.getDatos(session,"usuario"));
			contratosRequest.setUnidadTecnicaSuperior(sesionValida.getDatos(session,"unidad"));
			contratosRequest.setIdFase(idFaseReporte);
			contratosRequest.setIdEquipoInicial(idEquipoInicialReporte);
			contratosRequest.setIdEquipoFinal(idEquipoFinalReporte);
			contratosRequest.setIdEstatus(idEstatusReporte);
			
			contratosResponse = contratosDao.findContratos(contratosRequest);

			JRBeanCollectionDataSource dataSource    = new JRBeanCollectionDataSource(contratosResponse.getListaContrato());

			
			ArchivoPropiedades recursos      = new ArchivoPropiedades(request);
			String             nombreArchivo =  recursos.getValorPropiedad("ruta.reportesg.gestion.vivienda") + "Contratos.jrxml";
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

			//InputStream                is            = this.getClass().getResourceAsStream("/mx/com/grupogigante/gestionvivienda/reportes/contratos/Contratos.jrxml");
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
			response.setHeader("Content-Disposition", "attachment; filename=Contratos.pdf");
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			ServletOutputStream outputStream = response.getOutputStream();
			baos.writeTo(outputStream);
			outputStream.flush();
			
			
			log.info("saliendo");
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
	@RequestMapping(value = "/report/contratos/ReporteContratosFilterView.htm", method = RequestMethod.GET)
	public String reporteEstadoCuentaFilterView(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else {
			 redir = "/report/contratos/ConsultaContratosReporteFilter";
		 }
		 return redir;	
	}	
}
