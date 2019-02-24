/**
 * 
 */
package mx.com.grupogigante.gestionvivienda.controllers.simulador;



import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.com.grupogigante.gestionvivienda.dao.report.ReferenciasReporteDao;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CotizadorBillPlaningDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CotizadorDetalleDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CotizadorSubequiposDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CriteriosCotizadorImprimeDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CriteriosObtieneEquipoCotizacionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CriteriosSimuladorDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.ResponseCotizacionActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.ResponseObtieneCotizacionBaseDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.ResponseObtieneSubEquiposCotizacionDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.report.referencias.dto.ReferenciasReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.referencias.dto.ReferenciasReporteResponse;
import mx.com.grupogigante.gestionvivienda.resources.ArchivoPropiedades;
import mx.com.grupogigante.gestionvivienda.services.SimuladorService;
import mx.com.grupogigante.gestionvivienda.utils.NumberToLetterConverter;
import mx.com.grupogigante.gestionvivienda.utils.SessionValidatorSTS;
import mx.com.grupogigante.gestionvivienda.utils.utilsCommon;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author osvaldo
 *
 */

@Controller
public class SimuladorController {
	private Logger log = Logger.getLogger(SimuladorController.class);

	@Autowired
	SimuladorService simulador;
	@Autowired
	ReferenciasReporteDao referenciaDao;
	
	
	@RequestMapping(value="/Simulador.htm", method= RequestMethod.GET)
	public String getSimulador(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "Simulador";
		 }
		 return redir;
	 }

	
	@RequestMapping(value="/Simulador.htm",method=RequestMethod.POST)
	public @ResponseBody ResponseCotizacionActionDto postSimulador(@ModelAttribute(value="cotizacionView")CriteriosSimuladorDto criterios, BindingResult result, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ResponseCotizacionActionDto resAction = new ResponseCotizacionActionDto();
		ResponseObtieneCotizacionBaseDto resCotizacion = new ResponseObtieneCotizacionBaseDto();
		ResponseObtieneSubEquiposCotizacionDto resCotizacionSubEquipos = new ResponseObtieneSubEquiposCotizacionDto();
		utilsCommon utilC;
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		
		if(!sesionValida.validaSesion(session)) {			
			resCotizacion.setMensaje("LOGOUT");
			resCotizacion.setDescripcion("");
			resCotizacionSubEquipos.setMensaje("LOGOUT");
			resCotizacionSubEquipos.setDescripcion("");
			resAction.setResCotizacion(resCotizacion);
			resAction.setResCotizacionBase(resCotizacion);
			resAction.setResCotizacionSubEquipos(resCotizacionSubEquipos);
			return resAction;
		 }
		
		criterios.setI_usuario(sesionValida.getDatos(session,"usuario"));
		criterios.setI_id_ut_sup(sesionValida.getDatos(session,"unidad"));

		if(criterios.getFrom()!=null)
		{
			if(criterios.getFrom().equals("cancelar"))
			{
				criterios.setI_cancl("X");
			}
			else if(criterios.getFrom().equals("traspasar"))
			{
				criterios.setI_trasp("X");
			}
		}
		else
		{
			criterios.setFrom("");
		}
				
		try
		{
			if(criterios.getAccion().equals("base"))
			{
				criterios.setI_log("X");
				resAction = simulador.obtieneCotizacionBase(criterios);
				resAction.setResCotizacionBase(resAction.getResCotizacion());
			}
			else if (criterios.getAccion().equals("subequipos"))
			{
				CriteriosObtieneEquipoCotizacionDto critSE = new CriteriosObtieneEquipoCotizacionDto();
				
				critSE.setI_datab(criterios.getI_datab());
				critSE.setI_id_ut_sup(sesionValida.getDatos(session,"unidad"));
				critSE.setI_type(criterios.getI_type());
				critSE.setI_usuario(sesionValida.getDatos(session,"usuario"));
				resCotizacionSubEquipos = simulador.obtieneSubEquiposCotizacion(critSE);		
			}
			else if(criterios.getAccion().equals("simular"))
			{
	            criterios.setI_usuario(sesionValida.getDatos(session,"usuario"));
	            criterios.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
	            criterios.setI_simula("X");
	            criterios.setI_guarda("");
	            criterios.setI_eqps_adic("");
	            criterios.setI_log("X");
				utilC = new utilsCommon();
				ArrayList<CotizadorDetalleDto> cotizacionDetalleList = new ArrayList<CotizadorDetalleDto>();
				utilC.convierteXMLToCotizacionDetalle(criterios.getDetalle(), cotizacionDetalleList);
				ArrayList<CotizadorBillPlaningDto> cotizacionBillPlanList = new ArrayList<CotizadorBillPlaningDto>();
				utilC.convierteXMLToCotizacionPlan(criterios.getPlan(), cotizacionBillPlanList);
				ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposList = new ArrayList<CotizadorSubequiposDto>();
				utilC.convierteXMLToCotizacionSubEquipos(criterios.getEquipos(), cotizacionSubEquiposList);
				ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposListSel = new ArrayList<CotizadorSubequiposDto>();
				utilC.convierteXMLToCotizacionSubEquipos(criterios.getEquiposXml(),cotizacionSubEquiposListSel);
				criterios.setCotizacionSubEquiposList(cotizacionSubEquiposListSel);
				
				resAction = simulador.setAdminCotizacion(criterios,cotizacionDetalleList,cotizacionBillPlanList,cotizacionSubEquiposList);

				//System.out.println(cotizacionDetalleList.size());
			}
			else if(criterios.getAccion().equals("addsubequipos"))
			{
				criterios.setI_usuario(sesionValida.getDatos(session,"usuario"));
	            criterios.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
	            criterios.setI_simula("");
	            criterios.setI_guarda("");
	            criterios.setI_eqps_adic("X");
	            criterios.setI_log("X");
				utilC = new utilsCommon();
				ArrayList<CotizadorDetalleDto> cotizacionDetalleList = new ArrayList<CotizadorDetalleDto>();
				utilC.convierteXMLToCotizacionDetalle(criterios.getDetalle(), cotizacionDetalleList);
				ArrayList<CotizadorBillPlaningDto> cotizacionBillPlanList = new ArrayList<CotizadorBillPlaningDto>();
				utilC.convierteXMLToCotizacionPlan(criterios.getPlan(), cotizacionBillPlanList);
				ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposList = new ArrayList<CotizadorSubequiposDto>();
				utilC.convierteXMLToCotizacionSubEquipos(criterios.getEquipos(), cotizacionSubEquiposList);
				ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposListSel = new ArrayList<CotizadorSubequiposDto>();
				utilC.convierteXMLToCotizacionSubEquipos(criterios.getEquiposXml(),cotizacionSubEquiposListSel);
				criterios.setCotizacionSubEquiposList(cotizacionSubEquiposListSel);
				
				resAction = simulador.setAdminCotizacion(criterios,cotizacionDetalleList,cotizacionBillPlanList,cotizacionSubEquiposList);

				//System.out.println(cotizacionDetalleList.size());
			}
			else if(criterios.getAccion().equals("guardar"))
			{				
				criterios.setI_usuario(sesionValida.getDatos(session,"usuario"));
	            criterios.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
	            criterios.setI_simula("");
	            criterios.setI_guarda("X");
	            criterios.setI_eqps_adic("");
	            criterios.setI_log("X");
				utilC = new utilsCommon();
				ArrayList<CotizadorDetalleDto> cotizacionDetalleList = new ArrayList<CotizadorDetalleDto>();
				utilC.convierteXMLToCotizacionDetalle(criterios.getDetalle(), cotizacionDetalleList);
				ArrayList<CotizadorBillPlaningDto> cotizacionBillPlanList = new ArrayList<CotizadorBillPlaningDto>();
				utilC.convierteXMLToCotizacionPlan(criterios.getPlan(), cotizacionBillPlanList);
				ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposList = new ArrayList<CotizadorSubequiposDto>();
				utilC.convierteXMLToCotizacionSubEquipos(criterios.getEquipos(), cotizacionSubEquiposList);
				ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposListSel = new ArrayList<CotizadorSubequiposDto>();
				utilC.convierteXMLToCotizacionSubEquipos(criterios.getEquiposXml(),cotizacionSubEquiposListSel);
				criterios.setCotizacionSubEquiposList(cotizacionSubEquiposListSel);
				
				resAction = simulador.setAdminCotizacion(criterios,cotizacionDetalleList,cotizacionBillPlanList,cotizacionSubEquiposList);
				//resAction.setResCotizacion(resCotizacion);
				//System.out.println(cotizacionDetalleList.size());
			}
			else if(criterios.getAccion().equals("getCotizacion"))
			{
				criterios.setI_cotizacion("X");
				resCotizacion=simulador.getCotizacionGuardada(criterios);				
				resAction.setResCotizacion(resCotizacion);
				resAction.setResCotizacionBase(resAction.getResCotizacion());
			}			
			else if(criterios.getAccion().equals("cancelar"))
			{
				
				utilC = new utilsCommon();
				ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposList = new ArrayList<CotizadorSubequiposDto>();
				utilC.convierteXMLToCotizacionSubEquipos(criterios.getEquiposXml(),cotizacionSubEquiposList);
				criterios.setI_log("X");
				criterios.setCotizacionSubEquiposList(cotizacionSubEquiposList);
				resAction = simulador.obtieneCotizacionBase(criterios);
				resAction.setResCotizacionBase(resAction.getResCotizacion());
			}
			else if(criterios.getAccion().equals("traspasar"))
			{
				
				utilC = new utilsCommon();
				ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposList = new ArrayList<CotizadorSubequiposDto>();
				utilC.convierteXMLToCotizacionSubEquipos(criterios.getEquiposXml(),cotizacionSubEquiposList);
				criterios.setI_log("X");
				criterios.setCotizacionSubEquiposList(cotizacionSubEquiposList);
				resAction = simulador.obtieneCotizacionBase(criterios);
				resAction.setResCotizacionBase(resAction.getResCotizacion());
			}
			
		}
		catch (ViviendaException e) 
		{			
			resCotizacion.setMensaje("FAULT");
			resCotizacion.setMensaje(e.getMensajeError());
			resCotizacionSubEquipos.setMensaje("FAULT");
			resCotizacionSubEquipos.setMensaje(e.getMensajeError());
		}

		resAction.setResCotizacionSubEquipos(resCotizacionSubEquipos);
		
		return resAction;
	}
	
	@RequestMapping(value="/CotizadorAdmin.htm", method= RequestMethod.GET)
	public String getCotizadorAdmin(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "CotizadorAdmin";
		 }
		 return redir;
	 }

	
	@RequestMapping(value="/CotizadorAdmin.htm",method=RequestMethod.POST)
	public @ResponseBody ResponseCotizacionActionDto postCotizadorAdmin(@ModelAttribute(value="cotizadorAdminView")CriteriosSimuladorDto criterios, BindingResult result, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ResponseCotizacionActionDto resAction = new ResponseCotizacionActionDto();
		ResponseObtieneCotizacionBaseDto resCotizacion = new ResponseObtieneCotizacionBaseDto();
		ResponseObtieneSubEquiposCotizacionDto resCotizacionSubEquipos = new ResponseObtieneSubEquiposCotizacionDto();
		utilsCommon utilC;
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		criterios.setI_usuario(sesionValida.getDatos(session,"usuario"));
		criterios.setI_id_ut_sup(sesionValida.getDatos(session,"unidad"));
		//5005PSLF0901-D104
		//criterios.setI_equnr("5005PSLF0901-D105");
		
		try
		{
			if(criterios.getAccion().equals("getCotizacion"))
			{
				criterios.setI_cotizacion("X");
				resCotizacion=simulador.getCotizacionGuardada(criterios);
			}
		}
		catch (ViviendaException e) 
		{			
			resCotizacion.setMensaje("FAULT");
			resCotizacion.setMensaje(e.getMensajeError());
			resCotizacionSubEquipos.setMensaje("FAULT");
			resCotizacionSubEquipos.setMensaje(e.getMensajeError());
		}
		resAction.setResCotizacion(resCotizacion);
		resAction.setResCotizacionSubEquipos(resCotizacionSubEquipos);
		
		return resAction;
	}
	
	@RequestMapping(value="/ImprimeSimulador.htm", method= RequestMethod.GET)
	public String getCotizadorPrint(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "ImprimeSimulador";
		 }
		 return redir;
	 }

	
	@RequestMapping(value="/ImprimeSimulador.htm",method=RequestMethod.POST)
	public void postImprimeSimulador(@ModelAttribute(value="cotizadorAdminView")CriteriosSimuladorDto criterios, BindingResult result, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ResponseCotizacionActionDto resAction = new ResponseCotizacionActionDto();
		ResponseObtieneCotizacionBaseDto resCotizacion = new ResponseObtieneCotizacionBaseDto();
		utilsCommon utilC;		
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
		String formatoNumerico = "$0.00";
		ServletContext context = RequestContextUtils.getWebApplicationContext(request).getServletContext();
		try
		{
			if(criterios.getAccion().equals("print"))
			{
				utilC = new utilsCommon();
				ArrayList<CotizadorDetalleDto> cotizacionDetalleList = new ArrayList<CotizadorDetalleDto>();
				utilC.convierteXMLToCotizacionDetalle(criterios.getDetalle(), cotizacionDetalleList);
				ArrayList<CotizadorBillPlaningDto> cotizacionBillPlanList = new ArrayList<CotizadorBillPlaningDto>();
				utilC.convierteXMLToCotizacionPlan(criterios.getPlan(), cotizacionBillPlanList);
				ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposList = new ArrayList<CotizadorSubequiposDto>();
				utilC.convierteXMLToCotizacionSubEquipos(criterios.getEquipos(), cotizacionSubEquiposList);
				SessionValidatorSTS sesionValida = new SessionValidatorSTS();
				
				Map macCI = new HashMap();

				ReferenciasReporteRequest requestReferencias = new ReferenciasReporteRequest();
				requestReferencias.setUnidadTecnicaSuperior(sesionValida.getDatos(session,"unidad"));
				requestReferencias.setIdUsuario(sesionValida.getDatos(session,"usuario"));
				requestReferencias.setIdEquipoInicial(criterios.getId_equnr());
				
				ReferenciasReporteResponse responseReferencia = referenciaDao.findReferencias(requestReferencias);
				ArchivoPropiedades recursos = new ArchivoPropiedades(request);
								
				String temp_folder=context.getRealPath("/") + "files/cotizacion_"+Math.random()+".pdf";
		         
				try {
			        Document document = new Document(PageSize.LETTER);
			        
			        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(temp_folder));
			        document.open();
			        document.addAuthor("Grupo Gigante Inmobiliario");
			        document.addCreator("Grupo Gigante Inmobiliario");
			        document.addSubject("Gracias por preferirnos");
			        document.addCreationDate();
			        document.addTitle("Cotizacion");
			        document.top((float) 0.0);
			        HTMLWorker htmlWorker = new HTMLWorker(document);
			        
			        String sFichero =  recursos.getValorPropiedad("ruta.reportes.gestion.vivienda") +"DESA_"+sesionValida.getDatos(session,"unidad")+"//impcotizador.htm";
					//String sFichero =  pathReportes +"DESA_"+this.getUnidadTecSup()+"//"+fileName+".jrxml";
					File html_file = new File(sFichero);
					
					String rep_html="";
				        if(html_file.exists())
					    {				
			        	    FileInputStream fstream = new FileInputStream(sFichero);
			        	    
			        	    DataInputStream in = new DataInputStream(fstream);
			        	    BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			        	    String strLine="";
			        	    String str_html="";
			        	    while ((strLine = br.readLine()) != null){		        	    	
			        	    	str_html+=strLine;
			        	    }				
			        	    
							SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
					
							rep_html=str_html.replace("|id_cotiz_z|", criterios.getId_cotiz_z());
			        	    str_html=rep_html;
		
							rep_html=str_html.replace("|nombreC|", request.getParameter("nombreC"));
			        	    str_html=rep_html;
			        	    
			        	    rep_html=str_html.replace("|claveClienteTicket|", request.getParameter("claveClienteTicket"));
			        	    str_html=rep_html;
						
							rep_html=str_html.replace("|nom_vend|", request.getParameter("nom_vend"));
			        	    str_html=rep_html;
			        	    
			        	    rep_html=str_html.replace("|m2tot|", criterios.getM2tot()+"");
			        	    str_html=rep_html;

							sdt.applyPattern("yyyy-MM-dd");
							String fecha = criterios.getAedat();
							if(fecha != null && !fecha.trim().equals(""))
							{
								Date fechaReal = sdt.parse(fecha);
								sdt.applyPattern("dd/MM/yyyy");
								rep_html=str_html.replace("|fechaiv|", sdt.format(fechaReal));
				        	    str_html=rep_html;
							}
							else
							{
								rep_html=str_html.replace("|fechaiv|", "");
				        	    str_html=rep_html;
							}
														
							fecha = criterios.getDatab();
							sdt.applyPattern("yyyy-MM-dd");
							if(fecha != null && !fecha.trim().equals(""))
							{
								Date fechaReal = sdt.parse(fecha);
								sdt.applyPattern("dd/MM/yyyy");
								rep_html=str_html.replace("|datab|", sdt.format(fechaReal));
				        	    str_html=rep_html;
							}
							else
							{
								rep_html=str_html.replace("|datab|", "");
				        	    str_html=rep_html;
							}
														
							fecha = criterios.getDatub();
							sdt.applyPattern("yyyy-MM-dd");
							if(fecha != null && !fecha.trim().equals(""))
							{
								Date fechaReal = sdt.parse(fecha);
								sdt.applyPattern("dd/MM/yyyy");
								rep_html=str_html.replace("|datub|", sdt.format(fechaReal));
				        	    str_html=rep_html;
							}
							else
							{
								rep_html=str_html.replace("|datub|", "");
				        	    str_html=rep_html;
							}	
			        	    
							java.util.Date fechaHoy = new java.util.Date();
							rep_html=str_html.replace("|reserv|", sdt.format(fechaHoy));
			        	    str_html=rep_html;
			        	    
							sdt.applyPattern("hh:mm");							
							rep_html=str_html.replace("|hora|", sdt.format(fechaHoy));
			        	    str_html=rep_html;
							
							/*formatoNumerico = "$0.00";
							if (request.getParameter("equipo")!= null && !request.getParameter("equipo").trim().equals("")) {
								formatoNumerico = nf.format(new BigDecimal(request.getParameter("equipo")).doubleValue());
							}*/
			
							rep_html=str_html.replace("|equipo|", request.getParameter("equipo"));
			        	    str_html=rep_html;
			        	    
			        	    rep_html=str_html.replace("|nest|", criterios.getNest()+"");
			        	    str_html=rep_html;
			        	    
			        	    rep_html=str_html.replace("|nbod|", criterios.getNbod()+"");
			        	    str_html=rep_html;
			        	    
							formatoNumerico = "$0.00";
							String descuento ="";
							System.out.print(criterios.getDescm());
							
							if (criterios.getDescm()!= null && !criterios.getDescm().trim().equals("") && !criterios.getDescm().equals("0")) {
								
							
								descuento += "<tr>";
							    descuento += "<td><h5>Descuento</h5></td>";
							    descuento += "<td><h6>" + nf.format(new BigDecimal(criterios.getDescm()).doubleValue()) + "</h6></td>";
							    descuento += "</tr>";
							    descuento += "<tr>";
							    descuento += "<td><h5>Precio c/desc</h5></td>";
							    descuento += "<td><h6>" + nf.format(new BigDecimal(request.getParameter("descmdes")).doubleValue()) + "</h6></td>";
							    descuento += "</tr>";
							    rep_html=str_html.replace("|descuento|", descuento);
							    str_html=rep_html;
							}else{
								rep_html=str_html.replace("|descuento|", "");
								str_html=rep_html;								
							}
			        	    
							formatoNumerico = "$0.00";
							/*if (request.getParameter("subequipos")!= null && !request.getParameter("subequipos").equals("")) {
								formatoNumerico = nf.format(new BigDecimal(request.getParameter("subequipos")).doubleValue());
							}*/
							rep_html=str_html.replace("|subequipos|", request.getParameter("subequipos"));
			        	    str_html=rep_html;
			        	    
							formatoNumerico = "$0.00";
							if (request.getParameter("total")!= null && !request.getParameter("total").trim().equals("")) {
								formatoNumerico = request.getParameter("total");
							}
							rep_html=str_html.replace("|total|", formatoNumerico);
			        	    str_html=rep_html;
			        	    
							formatoNumerico = "$0.00";
							if (request.getParameter("anticipo")!= null && !request.getParameter("anticipo").trim().equals("")) {
								formatoNumerico = nf.format(new BigDecimal(request.getParameter("anticipo")).doubleValue());
							}
							rep_html=str_html.replace("|anticipo|", formatoNumerico);
			        	    str_html=rep_html;
			        	    
							formatoNumerico = "$0.00";
							if (request.getParameter("enganche")!= null && !request.getParameter("enganche").trim().equals("")) {
								formatoNumerico = nf.format(new BigDecimal(request.getParameter("enganche")).doubleValue());
							}
							rep_html=str_html.replace("|enganche|", formatoNumerico);
			        	    str_html=rep_html;
							
							formatoNumerico = "$0.00";
							if (request.getParameter("diferido")!= null && !request.getParameter("diferido").trim().equals("")) {
								formatoNumerico = nf.format(new BigDecimal(request.getParameter("diferido")).doubleValue());
							}
							rep_html=str_html.replace("|diferido|", formatoNumerico);
			        	    str_html=rep_html;
			        	    
							formatoNumerico = "$0.00";
							if (request.getParameter("pago_final")!= null && !request.getParameter("pago_final").trim().equals("")) {
								formatoNumerico = nf.format(new BigDecimal(request.getParameter("pago_final")).doubleValue());
							}
							rep_html=str_html.replace("|pago_final|", formatoNumerico);
			        	    str_html=rep_html;
			        	    
							formatoNumerico = "$0.00";
							if (request.getParameter("gastos_admin")!= null && !request.getParameter("gastos_admin").trim().equals("")) {
								formatoNumerico = nf.format(new BigDecimal(request.getParameter("gastos_admin")).doubleValue());
							}
							rep_html=str_html.replace("|gastos_admin|", formatoNumerico);
			        	    str_html=rep_html;
							
			        	    rep_html=str_html.replace("|depto|", request.getParameter("depto"));
			        	    str_html=rep_html;
							
							List<CriteriosCotizadorImprimeDto> listaImprimeCotizadorEq = new ArrayList<CriteriosCotizadorImprimeDto>();
							List<CriteriosCotizadorImprimeDto> listaImprimeCotizadorBp = new ArrayList<CriteriosCotizadorImprimeDto>();
											
							CriteriosCotizadorImprimeDto itemPrinteq;
							CriteriosCotizadorImprimeDto itemPrintbp;
							
							String tabla_equipos_add="";
							tabla_equipos_add+="<table id='dg_equipos' border='1' width='280'>";
							tabla_equipos_add+="<tr>";
							tabla_equipos_add+="<td width='30'><h6>TIPO</h6></td>";
							tabla_equipos_add+="<td><h6>DESCRIPCIÓN</h6></td>";
							tabla_equipos_add+="<td width='60'><h6>PRECIO</h6></td>";
							tabla_equipos_add+="<td><h6>EQUIPO</h6></td>";
							tabla_equipos_add+="</tr>";
							
							for(int i = 0; i<cotizacionSubEquiposList.size(); i++)
							{
								itemPrinteq = new CriteriosCotizadorImprimeDto();	
								if(itemPrinteq.getCosto()==null)
								{
									itemPrinteq.setCosto("");
								}
								if(itemPrinteq.getDesc1()==null)
								{
									itemPrinteq.setDesc1("");
								}
								if(itemPrinteq.getDesc2()==null)
								{
									itemPrinteq.setDesc2("");
								}
								if(itemPrinteq.getEquipo()==null)
								{
									itemPrinteq.setEquipo("");
								}
								if(itemPrinteq.getFecha()==null)
								{
									itemPrinteq.setFecha("");
								}
								if(itemPrinteq.getNumero()==null)
								{
									itemPrinteq.setNumero("");
								}
								if(itemPrinteq.getOpc1()==null)
								{
									itemPrinteq.setOpc1("");
								}
								if(itemPrinteq.getPrecio()==null)
								{
									itemPrinteq.setPrecio("");
								}
								if(itemPrinteq.getTipo()==null)
								{
									itemPrinteq.setTipo("");
								}
								
								itemPrinteq.setTipo(cotizacionSubEquiposList.get(i).getTipo());
								itemPrinteq.setDesc1(cotizacionSubEquiposList.get(i).getId_equnrx());
								formatoNumerico = "$0.00";
								if (cotizacionSubEquiposList.get(i).getNetwr()!= null && !cotizacionSubEquiposList.get(i).getNetwr().trim().equals("")) {
									formatoNumerico = nf.format(new BigDecimal(cotizacionSubEquiposList.get(i).getNetwr()).doubleValue());
								}

								itemPrinteq.setPrecio(formatoNumerico);
								//itemPrinteq.setPrecio(cotizacionSubEquiposList.get(i).getNetwr());
								itemPrinteq.setEquipo(cotizacionSubEquiposList.get(i).getId_equnr());
								
				                      
								tabla_equipos_add+="<tr>";
								tabla_equipos_add+="<td><h6>"+itemPrinteq.getTipo()+"</h6></td>";
								tabla_equipos_add+="<td><h6>"+itemPrinteq.getDesc1()+"</h6></td>";
								tabla_equipos_add+="<td><h6>"+itemPrinteq.getPrecio()+"</h6></td>";
								tabla_equipos_add+="<td><h6>"+itemPrinteq.getEquipo()+"</h6></td>";
								tabla_equipos_add+="</tr>";				                      
																		
								listaImprimeCotizadorEq.add(itemPrinteq);
							}
							tabla_equipos_add+="</table>";
							
							rep_html=str_html.replace("|sub_equipos|", tabla_equipos_add);
			        	    str_html=rep_html;
		
							log.info("listaImprimeCotizadorEq.size:" + listaImprimeCotizadorEq.size());
							sdt.applyPattern("yyyyMMdd");
							
							String tabla_billp="<table id='dg_detallepagos' width='90%' border='1' align='center'>"; 
							tabla_billp+="<tr>";
							tabla_billp+="<td width='20'><h5>CONCEPTO</h5></td>";
							tabla_billp+="<td><h5>DESCRIPCIÓN</h5></td>";
							tabla_billp+="<td><h5>FECHA</h5></td>";
							tabla_billp+="<td><h5>COSTO</h5></td>";
							tabla_billp+="<td><h5>OPC1</h5></td>";
							tabla_billp+="</tr>";
							
							//for(int i = 0; i<cotizacionBillPlanList.size(); i++)
							for(int i = 0; i<cotizacionBillPlanList.size()-1; i++)
							{
								//log.info(cotizacionBillPlanList.get(i).toString());
								sdt.applyPattern("yyyyMMdd");
								itemPrintbp = new CriteriosCotizadorImprimeDto();
								itemPrintbp.setNumero(cotizacionBillPlanList.get(i).getConce());
								itemPrintbp.setDesc2(cotizacionBillPlanList.get(i).getConcex());
								fecha = cotizacionBillPlanList.get(i).getFlim();
								
								if(itemPrintbp.getCosto()==null)
								{
									itemPrintbp.setCosto("");
								}
								if(itemPrintbp.getDesc1()==null)
								{
									itemPrintbp.setDesc1("");
								}
								if(itemPrintbp.getDesc2()==null)
								{
									itemPrintbp.setDesc2("");
								}
								if(itemPrintbp.getEquipo()==null)
								{
									itemPrintbp.setEquipo("");
								}
								if(itemPrintbp.getFecha()==null)
								{
									itemPrintbp.setFecha("");
								}
								if(itemPrintbp.getNumero()==null)
								{
									itemPrintbp.setNumero("");
								}
								if(itemPrintbp.getOpc1()==null)
								{
									itemPrintbp.setOpc1("");
								}
								if(itemPrintbp.getPrecio()==null)
								{
									itemPrintbp.setPrecio("");
								}
								if(itemPrintbp.getTipo()==null)
								{
									itemPrintbp.setTipo("");
								}
								
								if(fecha != null && !fecha.trim().equals("") && !fecha.trim().equals("00000000") && !fecha.trim().equals("0000-00-00"))
								{
									Date fechaReal = sdt.parse(fecha);
									sdt.applyPattern("dd/MM/yyyy");
									itemPrintbp.setFecha(sdt.format(fechaReal));
								}
								else
								{
									itemPrintbp.setFecha("");
								}
								//itemPrintbp.setFecha(cotizacionBillPlanList.get(i).getFlim());
								formatoNumerico = "$0.00";
								if (cotizacionBillPlanList.get(i).getTotal() != null && !cotizacionBillPlanList.get(i).getTotal().trim().equals("")) {
									formatoNumerico = nf.format(new BigDecimal(cotizacionBillPlanList.get(i).getTotal()).doubleValue());
								}
								
								//itemPrintbp.setFecha(sdt.format(fechaReal));
								//itemPrintbp.setCosto(cotizacionBillPlanList.get(i).getTotal());
								itemPrintbp.setCosto(formatoNumerico);
								formatoNumerico = nf.format(cotizacionBillPlanList.get(i).getSim1());
								itemPrintbp.setOpc1(formatoNumerico);					
								//itemPrintbp.setOpc1(""+cotizacionBillPlanList.get(i).getSim1());	
								
								if(itemPrintbp.getFecha().equals("")){
									itemPrintbp.setFecha("");
								}
								
								tabla_billp+="<tr>";
								tabla_billp+="<td><h6>"+itemPrintbp.getNumero()+"</h6></td>";
								tabla_billp+="<td><h6>"+itemPrintbp.getDesc2()+"</h6></td>";
								tabla_billp+="<td><h6>"+itemPrintbp.getFecha()+"</h6></td>";
								tabla_billp+="<td><h6>"+itemPrintbp.getCosto()+"</h6></td>";
								tabla_billp+="<td><h6>"+itemPrintbp.getOpc1()+"</h6></td>";
								tabla_billp+="</tr>";
													            
								listaImprimeCotizadorBp.add(itemPrintbp);
								log.info(itemPrintbp.toString());
							}
							tabla_billp+="</table>";
							
							rep_html=str_html.replace("|bplain|", tabla_billp);
			        	    str_html=rep_html;
			        	    
			        	    String tabla_bcos="<table id='bancos' width='300' border='0' align='center'>"; 
			        	    for(int i = 0; i<responseReferencia.getListaReferencias().size(); i++)
							{
			        	    	tabla_bcos+="<tr>";
				        	    tabla_bcos+="<td><h5>"+responseReferencia.getListaReferencias().get(i).getIdBanco()+"</h5></td>";
				        	    tabla_bcos+="<td><h5>"+responseReferencia.getListaReferencias().get(i).getRefPago()+"</h5></td>";
				        	    tabla_bcos+="</tr>";
							}
			        	    tabla_bcos+="</table>";
			        	    
			        	    rep_html=str_html.replace("|bancos|", tabla_bcos);
			        	    str_html=rep_html;

			        	    in.close();	
			        	    
					        htmlWorker.parse(new StringReader(str_html));
					        document.close();
					        
					        InputStream is = new FileInputStream(new File(temp_folder));

							response.setHeader("Content-Disposition", "attachment;filename=\"cotizacion.pdf\"");
							IOUtils.copy(is, response.getOutputStream());     
							response.flushBuffer();
							
					        System.out.println("Done");
				        }
				        else
				        {
				        	log.info("File dont exist");
				        }
			      }
			      catch (Exception e) {
			        log.error(e.getMessage());
			      }  
				
				/*JRBeanCollectionDataSource dc_equipos    = new JRBeanCollectionDataSource(listaImprimeCotizadorEq);
				JRBeanCollectionDataSource dc_billing    = new JRBeanCollectionDataSource(listaImprimeCotizadorBp);
				JRBeanCollectionDataSource dcReferencias = new JRBeanCollectionDataSource(responseReferencia.getListaReferencias());
				
				macCI.put("SUBREPORT", "/mx/com/grupogigante/gestionvivienda/reportes/subreporteequipos.jasper");
				macCI.put("REPORT_DS", dc_equipos);
				macCI.put("SUBREPORT_REFERENCIA", "/mx/com/grupogigante/gestionvivienda/reportes/ReferenciasBancariasSubreport.jasper");
				macCI.put("REPORT_REFERENCIAS_DS", dcReferencias);
				
				//log.info(listaImprimeCotizador);
				
				InputStream                is            = this.getClass().getResourceAsStream("/mx/com/grupogigante/gestionvivienda/reportes/ReporteCotizacion.jrxml");
				JasperReport               jasperReport  = (JasperReport) JasperCompileManager.compileReport(is);        
				
				JasperPrint jp = JasperFillManager.fillReport(jasperReport, macCI, dc_billing);
				ByteArrayOutputStream  baos      = new ByteArrayOutputStream();
				JRPdfExporter          exporter  = new JRPdfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
				exporter.exportReport();
				String fileName = "Cotizacion_"+criterios.getId_cotiz_z()+".pdf";
				response.setContentType("application/octet-stream");
				response.setHeader ("Content-disposition", "attachment; filename=Cotizacion_"+criterios.getId_cotiz_z()+".pdf");
//				response.setHeader("Content-Disposition", "inline; filename="+ fileName);
//				response.setContentType("application/pdf");
				response.setContentLength(baos.size());
				ServletOutputStream outputStream = response.getOutputStream();
				baos.writeTo(outputStream);
				outputStream.flush();*/
				log.info("pdf creado");
			}
		}
		catch (Exception e) 
		{			
			log.error("ERROR:", e);
			resCotizacion.setMensaje("FAULT");
			resCotizacion.setMensaje(e.getMessage());	
			resAction.setResCotizacion(resCotizacion);
		}
		//return "Simulador";
	}
	
	@RequestMapping(value="/CancelacionesParciales.htm", method= RequestMethod.GET)
	public String getCancelParciales(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "CancelacionesParciales";
		 }
		 return redir;
	 }

	
	@RequestMapping(value="/CancelacionesParciales.htm",method=RequestMethod.POST)
	public @ResponseBody ResponseCotizacionActionDto postCancelParciales(@ModelAttribute(value="cotizadorAdminView")CriteriosSimuladorDto criterios, BindingResult result, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ResponseCotizacionActionDto resAction = new ResponseCotizacionActionDto();
				
		return resAction;
	}
}
