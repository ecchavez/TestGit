package mx.com.grupogigante.gestionvivienda.controllers.reporte;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.com.grupogigante.gestionvivienda.dao.report.EstadoCuentaReporteDao;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseClientActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.EstadoCuentaReporteFieldDto;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.EstadoCuentaReporteLayout;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.EstadoCuentaReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.EstadoCuentaReporteResponse;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.TablaCobroDetalleDto;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.TablaDireccionSociedadDto;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.TablaPagoDetalleDto;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.resources.ArchivoPropiedades;
import mx.com.grupogigante.gestionvivienda.services.ClientesReporteService;
import mx.com.grupogigante.gestionvivienda.utils.SessionValidatorSTS;
import mx.com.grupogigante.gestionvivienda.utils.utilsCommon;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPrintPage;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
public class EstadoCuentaReportController {
	private Logger log = Logger.getLogger(EstadoCuentaReportController.class);
	
	@Autowired
	ClientesReporteService clientesService;
	
	@Autowired
	EstadoCuentaReporteDao estadoCuentaReporteDao;
	
	@RequestMapping(value = "/report/estadocuenta/FiltroBusquedaView.htm", method = RequestMethod.GET)
	public String filtroBusquedaReporteView (Model model,HttpSession session) {
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		return !validaSesion.validaSesion(session)? "../../index":"/report/estadocuenta/FiltroBusquedaView"; 
	}
	
	@RequestMapping(value = "/report/estadocuenta/CatalogoClientes.htm", method = RequestMethod.POST)
	public @ResponseBody EstadoCuentaReporteResponse getClientes(
													@RequestParam("idClienteReporte") String idClienteReporte,
													@RequestParam("idFaseReporte") String idFaseReporte,
													@RequestParam("idEquipoReporte") String idEquipoReporte,
													HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    EstadoCuentaReporteRequest estadoCuentaRequest = new EstadoCuentaReporteRequest();
		    EstadoCuentaReporteResponse estadoCuentaResponse = new EstadoCuentaReporteResponse(); 

		    if(!sesionValida.validaSesion(session)) {			
		    	estadoCuentaResponse.setMensaje("LOGOUT");
		    	estadoCuentaResponse.setDescripcion("");
				return estadoCuentaResponse;
			 }
		    
		try {
			log.info("entrando");
			estadoCuentaRequest.setIdUsuario(sesionValida.getDatos(session,"usuario"));
			estadoCuentaRequest.setIdUnidadTecnicaSuperior(sesionValida.getDatos(session,"unidad"));
			estadoCuentaRequest.setIdCliente(idClienteReporte);
			estadoCuentaRequest.setIdFase(idFaseReporte);
			estadoCuentaRequest.setIdEquipo(idEquipoReporte);
			
			log.info("CONSULTA REQUEST:" + estadoCuentaRequest);
			estadoCuentaResponse = estadoCuentaReporteDao.buscaReporte(estadoCuentaRequest);
			log.info("saliendo");
		} catch (ReporteException e) {
			log.error("ERROR:", e);
		}
		return estadoCuentaResponse;
	}
	
	@RequestMapping(value = "/report/estadocuenta/GeneraReporte.htm", method = RequestMethod.GET)
	public void generaReporteEdoCuenta(@RequestParam("idClienteReporte") String idClienteReporte,
									   @RequestParam("idPedidoReporte")  String idPedidoReporte,
									   HttpServletRequest  request, 
									   HttpServletResponse response, 
									   HttpSession         session) {
		try {
		    SessionValidatorSTS         sesionValida          = new SessionValidatorSTS();
		    EstadoCuentaReporteRequest  estadoCuentaRequest   = new EstadoCuentaReporteRequest();
		    EstadoCuentaReporteResponse estadoCuentaResponse  = new EstadoCuentaReporteResponse(); 
		    
			if(!sesionValida.validaSesion(session)) {
			    log.info("Sesion invalida");
			    request.getRequestDispatcher("/index.jsp").forward(request, response);
			 } else {
				 
				 	Date now = new Date();
				 	SimpleDateFormat formatoCorte = new SimpleDateFormat("yyyyMMdd");
				 	SimpleDateFormat formatoFechaCorte = new SimpleDateFormat("dd/MM/yyyy");
				 
					estadoCuentaRequest.setIdUsuario(sesionValida.getDatos(session,"usuario"));
					estadoCuentaRequest.setIdUnidadTecnicaSuperior(sesionValida.getDatos(session,"unidad"));
					estadoCuentaRequest.setIdCliente(idClienteReporte);
					estadoCuentaRequest.setFechaCorte(formatoCorte.format(now));
					
					log.info("REPORTE REQUEST:" + estadoCuentaRequest);
					
					estadoCuentaResponse = estadoCuentaReporteDao.buscaReporte(estadoCuentaRequest);
					String idEquipoCompleto = "";
					
					String[] mes = { "enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
					new utilsCommon();
					
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.DAY_OF_YEAR, -1);

					//String lastdayMon = utilsCommon.getLastDayLastMonth();
					String lastdayMon =  formatoFechaCorte.format(calendar.getTime());
					String[] camposFecha = lastdayMon.split("/");
					String sMes = camposFecha[1];
					int iMes = new Integer(sMes);
					String fechaDoc = camposFecha[0] + " de " + mes[iMes-1] + " "+ camposFecha[2];
					String saldoTexto = "";
					
						
					for (int i = 0 ; i < estadoCuentaResponse.getLayout().size() ; i++) {
						EstadoCuentaReporteLayout layout = estadoCuentaResponse.getLayout().get(i);
						if (layout.getPedido().equals(idPedidoReporte)) {
							log.info("layout.pedido:" + layout.getPedido() + ", idPedidoReporte:" + idPedidoReporte + ",");
							HashMap<String, Object>                         parametros        = new HashMap<String, Object>();
							
							ServletContext context = RequestContextUtils.getWebApplicationContext(request).getServletContext();
							if (layout.getDireccionEmpresa() == null) {
								layout.setDireccionEmpresa(new TablaDireccionSociedadDto());
							}
							parametros.put("nombreConstructora",     layout.getDireccionEmpresa().getNombresoc());
							parametros.put("direccion1Constructora", layout.getDireccionEmpresa().getCalle() + " " + layout.getDireccionEmpresa().getNoext() + " " + layout.getDireccionEmpresa().getColn());
							parametros.put("direccion2Constructora", layout.getDireccionEmpresa().getCdpst() + " " + layout.getDireccionEmpresa().getDlmcp() + ", " + layout.getDireccionEmpresa().getPaisx() + " " + layout.getDireccionEmpresa().getEstdox());
							parametros.put("numeroContrato",    layout.getIdCliente());
							parametros.put("nombreCliente",     layout.getNombre() + " " + layout.getSegundo() + " " + layout.getAppaterno() + " " + layout.getApmaterno());
							parametros.put("direccion1Cliente", layout.getCalle() + " " + layout.getNoext() +  ", " + (layout.getNoint().equals("") ? "" : "INT. " + layout.getNoint() + ", ") + layout.getColn());
							parametros.put("direccion2Cliente", layout.getCdpst() + " " + layout.getDlmcp() + ", " + layout.getPaixtx() + " " + layout.getEstdo());
							parametros.put("numDepto", layout.getCasadepto());
							parametros.put("casa", layout.getCasa());
							parametros.put("unidadTecnicaDesc", layout.getUnidaTecnicaDesc());
							parametros.put("nomUtSuperior", layout.getDesarrollo());
							//Couta Única
							parametros.put("msgCuota", layout.getMsgCouta()==null?"":layout.getMsgCouta());
							String sFichero = context.getRealPath("/") + "images/logos_plantillas/logo_" + sesionValida.getDatos(session,"unidad") + ".png"; 						
							File file = new File(sFichero);
							
							if(!file.exists()){					
							//sFichero = context.getRealPath("/") + "images/logos_plantillas/logoGrupoGigante.png";
							sFichero = context.getRealPath("/") + "images/logos_plantillas/logoGrupoGigante.png";
							}							
							parametros.put("ruta", sFichero);
							//log.info(sFichero);
							
							//Imagenes Estado de cuenta	aviso puerta jardín						
							String rutaImgAvisoPJ = context.getRealPath("/") + "images/img_edo_cta_pja/iconAvisoPJ.png";
							String rutaImgAvisoHipotecaria = context.getRealPath("/") + "images/img_edo_cta_pja/iconAvisoHipotecaria.png";
							String rutaImgAvisoGigante = context.getRealPath("/") + "images/img_edo_cta_pja/iconAvisoGigante.png";
							String rutaImgAvisoLomelin = context.getRealPath("/") + "images/img_edo_cta_pja/iconAvisoLomelin.png";
							
							parametros.put("rutaImgAvisoPJ", rutaImgAvisoPJ);
							parametros.put("rutaImgAvisoHipotecaria", rutaImgAvisoHipotecaria);
							parametros.put("rutaImgAvisoGigante", rutaImgAvisoGigante);
							parametros.put("rutaImgAvisoLomelin", rutaImgAvisoLomelin);
							//termina imagenes para Estado de cuenta aviso puerta jardín
							
							
							idEquipoCompleto = layout.getIdEquipoCompleto();
							
							List<EstadoCuentaReporteFieldDto> lista = new ArrayList<EstadoCuentaReporteFieldDto>();
							List<EstadoCuentaReporteFieldDto> listaProximoPago = new ArrayList<EstadoCuentaReporteFieldDto>();
							
							List<TablaCobroDetalleDto> listaCobro = layout.getCobro();
							log.info("Reporte listaCobro:" + listaCobro);
							
							SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
							String moneda = "";
							String fecha = "";
							Date nuevaFecha = null;
							BigDecimal importe = new BigDecimal("0");
							BigDecimal importeCompromiso = new BigDecimal("0");
							BigDecimal pagos = new BigDecimal("0");
							BigDecimal corteImp = new BigDecimal("0");
							boolean flag = false;
							String totalCorte = "$ 0.00";
							String strFechaCom = "Total compromiso de pago";
							
							
							if (listaCobro != null) {
								for (int d = 0 ; d < listaCobro.size() ; d++) {
								
									EstadoCuentaReporteFieldDto rowReporte = new EstadoCuentaReporteFieldDto();
									
									//Cambia la fecha de tipo de Dato String a Date
									fecha = listaCobro.get(d).getFechDoc();
									nuevaFecha = formatoFecha.parse(fecha);
									
									//Cambia el importe de tipo String a BigDecimal
									moneda = listaCobro.get(d).getNetwr();
									moneda = moneda.replace("$", "");
									moneda = moneda.replace(",", "");
									importe = new BigDecimal(moneda);
									
									if(listaCobro.get(d).getConcepto() != null || !listaCobro.get(d).getConcepto().trim().equals("")){
										if(listaCobro.get(d).getConcepto().indexOf("Total compromiso") == -1){
											importeCompromiso = importeCompromiso.add(importe);
										}else if(listaCobro.get(d).getConcepto().indexOf("Total compromiso") > -1){
											strFechaCom = listaCobro.get(d).getConcepto();
											strFechaCom = strFechaCom.replaceAll("\\.", "/");
											flag = true;
											totalCorte = listaCobro.get(d).getNetwr();
											moneda = listaCobro.get(d).getNetwr();
											moneda = moneda.replace("$", "");
											moneda = moneda.replace(",", "");
											corteImp = new BigDecimal(moneda);
										}
									}
									
									if(listaCobro.get(d).getConcepto().equals("PAGO FINAL") && 
										layout.getFase().equals("F02") && idEquipoCompleto.substring(0,7).equals("5900MYA")){
											rowReporte.setFechaCompromiso(null);
									}else{
											rowReporte.setFechaCompromiso(nuevaFecha);
									}
									

									if(importe.signum() > 0){
										rowReporte.setImporteCompromiso(importe);
									}else{
										rowReporte.setImporteCompromiso(null);
									}
									
									rowReporte.setConceptoCompromiso(listaCobro.get(d).getConcepto());
									rowReporte.setmCompromiso(listaCobro.get(d).getMoneda());
									
									if(flag){
										if(listaCobro.get(d).getConcepto() != null && !listaCobro.get(d).getConcepto().trim().equals("")){
											if(listaCobro.get(d).getConcepto().indexOf("Total compromiso") == -1
												&& listaCobro.get(d).getConcepto().indexOf("Sus pr") == -1){
												listaProximoPago.add(rowReporte);
											}
										}
									}else{
										lista.add(rowReporte);
									}
									
								}
							}
						
							
							
							List<TablaPagoDetalleDto> listaPago = layout.getPago();
							log.info("Reporte listaPago:" + listaPago);
							if (listaPago != null) {
								for (int j = 0 ; j < listaPago.size() ; j++) {
									moneda = listaPago.get(j).getNetwr();
									moneda = moneda.replace("$", "");
									moneda = moneda.replace(",", "");
									if(moneda.substring(moneda.length()-1).equals(")")){
										moneda = moneda.replace("(", "");
										moneda = moneda.replace(")", "");
										moneda = "-"+ moneda;
									}
									importe = new BigDecimal(moneda);
									
									pagos = pagos.add(importe);
									
									if (j < lista.size()) {									
										lista.get(j).setConceptoPago(listaPago.get(j).getConcepto());
										if(!listaPago.get(j).getFechDoc().equals("30/11/0002")){
											lista.get(j).setFechaPago(listaPago.get(j).getFechDoc());
										}else{
											lista.get(j).setFechaPago("");
										}
										lista.get(j).setImportePago(importe);
										lista.get(j).setmPago(listaPago.get(j).getMoneda());
									} else {
										EstadoCuentaReporteFieldDto rowReporte = new EstadoCuentaReporteFieldDto();
										rowReporte.setConceptoPago(listaPago.get(j).getConcepto());
										if(!listaPago.get(j).getFechDoc().equals("30/11/0002")){
										rowReporte.setFechaPago(listaPago.get(j).getFechDoc());
										}
										else{
										rowReporte.setFechaPago("");
										}
										rowReporte.setImportePago(importe);
										rowReporte.setmPago(listaPago.get(j).getMoneda());
										lista.add(rowReporte);
									}
								}
								
								if(listaPago.size() >= lista.size()){
									EstadoCuentaReporteFieldDto rowReporte = new EstadoCuentaReporteFieldDto();
									lista.add(rowReporte);
								}
							}
							
							
							if(corteImp.subtract(pagos).signum()<0){
								saldoTexto = "A favor del cliente";
							}else{
								saldoTexto = "Pendiente por pagar";
							}
							
							
							parametros.put("importePago", corteImp.subtract(pagos));
							parametros.put("importeCompromiso", importeCompromiso);
							parametros.put("fechaDoc", fechaDoc);
							parametros.put("saldoTexto", saldoTexto);
							parametros.put("saldoCorte", totalCorte);
							parametros.put("montosRecibidos", pagos);
							parametros.put("lastDay", lastdayMon);
							parametros.put("fechaCom", strFechaCom);
							parametros.put("fase", layout.getFase());
							parametros.put("referencia", layout.getReferenciaDv());
							parametros.put("cuenta", layout.getCuenta());
							parametros.put("equipo", layout.getIdEquipoCompleto());
							parametros.put("msgGastosAdmin", layout.getMsgGastosAdmin());
							parametros.put("clabe", layout.getClabe());
							
							i = estadoCuentaResponse.getLayout().size() + 5;

							//Codificación para validar bug de ireport cuando 
							//un subreporte está vacío.							
							if(listaProximoPago != null && listaProximoPago.size() == 0){
								EstadoCuentaReporteFieldDto dto = new EstadoCuentaReporteFieldDto();
								listaProximoPago.add(dto); 
							}
							
							if(lista != null && lista.size() == 0){
								EstadoCuentaReporteFieldDto dto = new EstadoCuentaReporteFieldDto();
								lista.add(dto); 
							}
							
							JRBeanCollectionDataSource dataSource    = new JRBeanCollectionDataSource(lista);				
							JRBeanCollectionDataSource dataSourceSub    = new JRBeanCollectionDataSource(listaProximoPago);						
							
							ArchivoPropiedades recurso      = new ArchivoPropiedades(request);
							parametros.put("SUBREPORT", recurso.getValorPropiedad("ruta.reportesg.gestion.vivienda") + "Compromiso.jasper");
							parametros.put("SUBREPORT_DS", dataSourceSub);
							
							ArchivoPropiedades recursos      = new ArchivoPropiedades(request);
							String             nombreArchivo =  recursos.getValorPropiedad("ruta.reportesg.gestion.vivienda") + "EstadoCuenta.jrxml";
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
							
							//InputStream                is            = this.getClass().getResourceAsStream("/mx/com/grupogigante/gestionvivienda/reportes/estadocuenta/EstadoCuenta.jrxml");
							JasperReport               jasperReport  = (JasperReport) JasperCompileManager.compileReport(isReadArchivoReporte);
							
							JasperPrint jp = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
							removeBlankPage(jp.getPages());
							ByteArrayOutputStream  baos      = new ByteArrayOutputStream();
							JRPdfExporter          exporter  = new JRPdfExporter();
							exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
							exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
							exporter.exportReport();
							response.setContentType("application/octet-stream");
							response.setHeader ("Content-disposition", "attachment; filename=" + ( idEquipoCompleto != null && idEquipoCompleto.trim().length() > 0 ? idEquipoCompleto + ".pdf":"EstadoCuentaReporte.pdf"));
							response.setContentLength(baos.size());
							ServletOutputStream outputStream = response.getOutputStream();
							baos.writeTo(outputStream);
							outputStream.flush();
							log.info("El archivo se acabo de imprimir verifique impresion");
						}
					}				 
			 }
		} 
		catch (Exception e) {
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
	@RequestMapping(value = "/report/estadocuenta/ReporteEstadoCuentaFilterView.htm", method = RequestMethod.GET)
	public String reporteEstadoCuentaFilterView(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else {
			 redir = "/report/estadocuenta/ConsultaEdoCuentaReporteFilter";
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
	@RequestMapping(value = "/report/estadocuenta/GridBusquedaClienteRegistroReport.htm", method = RequestMethod.GET)
	public String gridBusquedaClienteRegistroTicket(ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else	 {
			 redir = "/report/estadocuenta/GridBusquedaClienteRegistroReporte";
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
	@RequestMapping(value = "/report/estadocuenta/ParamsClienteRegistroReporte.htm", method = RequestMethod.GET)
	public String paramsClienteRegistroReporte (ModelMap map,HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session)) {
			 redir = "../../index";
		 } else	 {
			 redir = "/report/estadocuenta/GridParamsClienteRegistroReporte";
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
	@RequestMapping(value = "/report/estadocuenta/CatalogoClientesReporte.htm", method = RequestMethod.POST)
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
	
	private void removeBlankPage(List<JRPrintPage> pages) {
	      for (java.util.Iterator<JRPrintPage> i=pages.iterator(); i.hasNext();) {
	          JRPrintPage page = i.next();
	          if (page.getElements().size() == 0)
	              i.remove();
	      }
	  }

}
