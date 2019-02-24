package mx.com.grupogigante.gestionvivienda.controllers.reporte;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.com.grupogigante.gestionvivienda.dao.report.ComisionesReporteDao;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.report.comisiones.dto.ComisionesReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.comisiones.dto.ComisionesReporteResponse;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.resources.ArchivoPropiedades;
import mx.com.grupogigante.gestionvivienda.utils.SessionValidatorSTS;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.type.OrientationEnum;

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
public class ComisionesReportController {
	private Logger log = Logger.getLogger(ComisionesReportController.class);

	@Autowired
	ComisionesReporteDao comisionesDao;
	
	@RequestMapping(value = "/report/comisiones/FiltroBusquedaView.htm", method = RequestMethod.GET)
	public String filtroBusquedaReporteView (Model model,HttpSession session) {
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		return !validaSesion.validaSesion(session)? "../../index":"/report/comisiones/FiltroBusquedaView"; 
	}
	
	@RequestMapping(value = "/report/comisiones/ConsultaComisionesPreview.htm", method = RequestMethod.POST)
	public @ResponseBody ComisionesReporteResponse consultaComisiones(													
													@RequestParam("idFaseReporte") String idFaseReporte,
													@RequestParam("idEquipoInicialReporte") String idEquipoInicialReporte,
													@RequestParam("idEquipoFinalReporte") String idEquipoFinalReporte,
													HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    ComisionesReporteRequest  comisionesRequest  = new ComisionesReporteRequest();
		    ComisionesReporteResponse comisionesResponse = new ComisionesReporteResponse(); 
		try {
			System.out.println("entrando");
			comisionesRequest.setIdUsuario(sesionValida.getDatos(session,"usuario"));
			comisionesRequest.setUnidadTecnicaSuperior(sesionValida.getDatos(session,"unidad"));
			comisionesRequest.setIdEquipoInicial(idEquipoInicialReporte);
			comisionesRequest.setIdEquipoFinal(idEquipoFinalReporte);
			
			comisionesResponse = comisionesDao.findComisiones(comisionesRequest);
			
			//System.out.println("saliendo:" + comisionesResponse.getListaComisiones());
		} catch (ViviendaException e) {
			e.printStackTrace();
		}
		return comisionesResponse;
	}

	@RequestMapping(value = "/report/comisiones/GeneraReporteComisiones.htm", method = RequestMethod.GET)
	public void generaReporteComisiones(
											@RequestParam("idEstatusReporte") String idEstatusReporte,
											@RequestParam("idFaseReporte") String idFaseReporte,
											@RequestParam("idEquipoInicialReporte") String idEquipoInicialReporte,
											@RequestParam("idEquipoFinalReporte") String idEquipoFinalReporte,
										    HttpServletRequest  request, 
										    HttpServletResponse response, 
											HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    ComisionesReporteRequest  comisionesRequest  = new ComisionesReporteRequest();
		    ComisionesReporteResponse comisionesResponse = new ComisionesReporteResponse(); 
		try {
			System.out.println("entrando reporte x");
			comisionesRequest.setIdUsuario(sesionValida.getDatos(session,"usuario"));
			comisionesRequest.setUnidadTecnicaSuperior(sesionValida.getDatos(session,"unidad"));
			comisionesRequest.setIdEquipoInicial(idEquipoInicialReporte);
			comisionesRequest.setIdEquipoFinal(idEquipoFinalReporte);
			
			comisionesResponse = comisionesDao.findComisiones(comisionesRequest);

			JRBeanCollectionDataSource dataSource    = new JRBeanCollectionDataSource(comisionesResponse.getListaComision());

			ArchivoPropiedades recursos      = new ArchivoPropiedades(request);
			String             nombreArchivo =  recursos.getValorPropiedad("ruta.reportesg.gestion.vivienda") + "Comisiones.jrxml";
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
			
			//InputStream                is            = this.getClass().getResourceAsStream("/mx/com/grupogigante/gestionvivienda/reportes/comisiones/Comisiones.jrxml");
			//JasperReport               jasperReport  = (JasperReport) JasperCompileManager.compileReport(is);        

			Map parametros = new HashMap();
			JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
			ByteArrayOutputStream  baos      = new ByteArrayOutputStream();
			JRPdfExporter          exporter  = new JRPdfExporter();
			jp.setOrientation(OrientationEnum.PORTRAIT);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			exporter.exportReport();
			response.setContentType("application/octet-stream");
			//response.setHeader ("Content-disposition", "attachment; filename=" + ( idEquipoCompleto != null && idEquipoCompleto.trim().length() > 0 ? idEquipoCompleto + ".pdf":"EstadoCuentaReporte.pdf"))
			response.setHeader("Content-Disposition", "attachment; filename=Comisiones.pdf");
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			ServletOutputStream outputStream = response.getOutputStream();
			baos.writeTo(outputStream);
			outputStream.flush();		
			
			System.out.println("saliendo");
		} catch (ViviendaException e) {
			log.error(e.getMessage());
	 	} catch (JRException e) {
	 		log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
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
	@RequestMapping(value = "/report/comisiones/ReporteComisionesFilterView.htm", method = RequestMethod.GET)
	public String reporteEstadoCuentaFilterView(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else {
			 redir = "/report/comisiones/ConsultaComisionesReporteFilter";
		 }
		 return redir;	
	}	
}
