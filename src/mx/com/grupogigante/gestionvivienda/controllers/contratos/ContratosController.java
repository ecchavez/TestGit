/**
 /**
 /**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 16/08/2012               
 */
package mx.com.grupogigante.gestionvivienda.controllers.contratos;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.com.grupogigante.gestionvivienda.dao.CatalogosDaoImp;
import mx.com.grupogigante.gestionvivienda.domain.dto.CartaOfertaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CotizacionDetDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfClienteSap;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfContrato;
import mx.com.grupogigante.gestionvivienda.domain.dto.CtaBancariaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseContratoActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetCompPago;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfClienteSap;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfContrato;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.CriteriosGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.ResponseGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.resources.ArchivoPropiedades;
import mx.com.grupogigante.gestionvivienda.resources.LoadFileProperties;
import mx.com.grupogigante.gestionvivienda.services.CatalogosService;
import mx.com.grupogigante.gestionvivienda.services.ClientesService;
import mx.com.grupogigante.gestionvivienda.services.ContratosService;
import mx.com.grupogigante.gestionvivienda.utils.NumberToLetterConverter;
import mx.com.grupogigante.gestionvivienda.utils.PageHeader;
import mx.com.grupogigante.gestionvivienda.utils.SessionValidatorSTS;
import mx.com.grupogigante.gestionvivienda.utils.utilsCommon;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Clase controladora para el modulo de Contratos Fecha de creación: 16/08/2012
 */

@Controller
public class ContratosController {
	private final static Logger log = Logger
			.getLogger(ContratosController.class);
	@Autowired
	ContratosService contratoService;
	@Autowired
	CatalogosService catalogosService;
	@Autowired
	ClientesService clientesService;

	ResponseContratoActionDto responseAction = new ResponseContratoActionDto();
	ResponseGetInfContrato resInfContrato = new ResponseGetInfContrato();
	ResponseGetCompPago resInfCompPago = new ResponseGetCompPago();
	ResponseGetInfClienteSap resGetClientesSapDTO = new ResponseGetInfClienteSap();
	CriteriosGetInfClienteSap criteriosSap = new CriteriosGetInfClienteSap();
	utilsCommon utils = new utilsCommon();
	JasperReport jasperReport;
	JasperPrint jasperPrint;
	private JRDataSource dataSource;
	private String unidadTecSup;

	ServletContext context = null;
	HttpServletResponse response = null;

	ArrayList<String> sourceFiles = new ArrayList<String>();
	ArrayList<String> nombreFiles = new ArrayList<String>();
	// ResourceBundle recursos =
	// ResourceBundle.getBundle("mx.com.grupogigante.gestionvivienda.resources.recursos");

	ArchivoPropiedades recursos = null;// new
	// ArchivoPropiedades("mx/com/grupogigante/gestionvivienda/resources/recursos.properties");
	// // Carga los datos del archivo

	@RequestMapping(value = "/Impresion.htm", method = RequestMethod.GET)
	public String handleRequestImpresion(Model model, HttpSession session) {
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();
		String redir = "";
		if (!validaSesion.validaSesion(session)) {
			redir = "../../index";
		} else {
			redir = "Impresion";
		}
		return redir;

	}

	/**
	 * Método que para la impresion de contratos
	 * 
	 * @param CriteriosGetInfoContrato
	 *            informacion para la impresion 1) impresion de contrato 2)
	 *            impresion de contrato y o carta promesa 3) impresion de
	 *            talonario
	 * @exception Exception
	 */
	/**
	 * @param model
	 * @param criteriosC
	 * @param request
	 * @param response
	 * @param session
	 */

	Calendar currentDate = GregorianCalendar.getInstance();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MMM/dd");
	String dateNow = formatter.format(currentDate.getTime());
	int month = currentDate.get(Calendar.MONTH);
	String[] camposFecha = dateNow.split("/");
	String mesActual = "";
	NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
	String formatoNumerico = "$0.00";

	String[] meses = { "ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO",
			"JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE",
	"DICIEMBRE" };
	String[] mes = { "enero", "febrero", "marzo", "abril", "mayo", "junio",
			"julio", "agosto", "septiembre", "octubre", "noviembre",
	"diciembre" };

	@RequestMapping(value = "/ImpresionContrato.htm", method = RequestMethod.GET)
	public void handleRequest(Model model, CriteriosGetInfContrato criteriosC,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		Map<String, Object> params = new HashMap<String, Object>();
		CriteriosGetCatalogosDto criteriosGetCat = new CriteriosGetCatalogosDto();
		CatalogosDaoImp catDesc = new CatalogosDaoImp();
		ResponseGetCatalogosDto respCatalogosDTO = new ResponseGetCatalogosDto();
		ArchivoPropiedades propiedades = new ArchivoPropiedades();
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		recursos = new ArchivoPropiedades(request);

		String pathBase = recursos
				.getValorPropiedad("ruta.reportes.gestion.vivienda");

		dataSource = new JRBeanCollectionDataSource(null);

		this.response = response;

		try {

			criteriosC.setUsuario(sesionValida.getDatos(session, "usuario"));
			criteriosC.setId_ut_sup(sesionValida.getDatos(session, "unidad"));
			this.setUnidadTecSup(criteriosC.getId_ut_sup());
			criteriosC.setAccion(criteriosC.getAccion());
			criteriosGetCat.setI_id_ut_sup(criteriosC.getId_ut_sup());
			criteriosGetCat.setI_usuario(criteriosC.getUsuario());
			ServletContext context = RequestContextUtils
					.getWebApplicationContext(request).getServletContext();
			this.context = context;
			String url_imagen = sesionValida.getDatos(session, "url")
					+ "/imagenes/logos_jasper/logo" + criteriosC.getId_ut_sup()
					+ ".png";

			File fichero = new File(url_imagen);

			if (!fichero.exists()) {
				url_imagen = sesionValida.getDatos(session, "url") + "/images/logos_jasper/logoGrupoGigante.png";
			}

			if (criteriosC.getAccion() < 5) {
				
				//Obtiene los datos para el set de Documentación.
				resInfContrato = contratoService.getContrato(criteriosC);
				
				//Utiliza servicio de clientes para la impresión del documento para la Ley Antilavado.
				criteriosGetCat.setI_id_ut_sup(criteriosC.getId_ut_sup());
				criteriosGetCat.setI_usuario(criteriosC.getUsuario());
				criteriosGetCat.setI_paises('X');
				criteriosGetCat.setId_pais("MX");
				criteriosGetCat.setI_regiones('X');
	   		    criteriosGetCat.setI_sexo('X');
				criteriosGetCat.setI_tratamientos('X');
				criteriosGetCat.setI_edo_civil('X');
				respCatalogosDTO = catalogosService.getCatalogos(criteriosGetCat);
				
				criteriosSap.setUsuario(criteriosC.getUsuario());
				criteriosSap.setId_ut_sup(criteriosC.getId_ut_sup());
				criteriosSap.setAccion(1);
				criteriosSap.setXmlCliSap("<criterios><criterio><idClieteSap>"+resInfContrato.getCliente().getId_cliente_sap()+"</idClieteSap></criterio></criterios>");
				resGetClientesSapDTO = clientesService.getClientesSap(criteriosSap,respCatalogosDTO);
				
				//Catálogos
				criteriosGetCat.setI_paises('X');
				criteriosGetCat.setId_pais(resInfContrato.getCliente().getPais());
				criteriosGetCat.setI_regiones('X');
				criteriosGetCat.setI_sexo('X');
				criteriosGetCat.setI_tratamientos('X');
				criteriosGetCat.setI_edo_civil('X');
				respCatalogosDTO = catalogosService.getCatalogos(criteriosGetCat);
			
			} else if (criteriosC.getAccion() == 5) {
				resInfCompPago = contratoService.getComprobante(criteriosC);
			}

			// validar si existe el archivo de contrato o carta promesa

			switch (criteriosC.getAccion()) {
			case 1:

				String contrato_name = "contrato_" + resInfContrato.getCliente().getId_cliente_sap() + ".pdf";

				try {

					String sFichero = "";
					sFichero = pathBase + "DESA_" + this.getUnidadTecSup() + "//contrato.htm";
					
					// String sFichero = pathReportes
					// +"DESA_"+this.getUnidadTecSup()+"//"+fileName+".jrxml";
					File html_file = new File(sFichero);

					String temp_folder = context.getRealPath("/") + "files/"
							+ contrato_name;
					if (html_file.exists()) {

						String rep_html = "";
						// Document document = new
						// Document(PageSize.LETTER.rotate());
						Document document = new Document(PageSize.LETTER);

						PdfWriter pdfWriter = PdfWriter.getInstance(document,
								new FileOutputStream(temp_folder));
						document.open();
						document.addAuthor("Grupo Gigante Inmobiliario");
						document.addCreator("Grupo Gigante Inmobiliario");
						document.addSubject("Gracias por preferirnos");
						document.addCreationDate();
						document.addTitle("Contrato");
						document.top((float) 0.0);
						HTMLWorker htmlWorker = new HTMLWorker(document);

						FileInputStream fstream = new FileInputStream(sFichero);

						DataInputStream in = new DataInputStream(fstream);
						BufferedReader br = new BufferedReader(
								new InputStreamReader(in, "UTF-8"));
						String strLine = "";
						String str_html = "";

						int ii = 0;
						while ((strLine = br.readLine()) != null) {

							if (strLine.indexOf("|imgcontrato" + ii + "|") != -1) {
								String nom_images[] = strLine.split(" ");
								Image image = Image.getInstance(pathBase
										+ "DESA_" + this.getUnidadTecSup()
										+ "//imagenes//" + nom_images[1]);
								// image.setAlignment(Image.MIDDLE);
								image.setAbsolutePosition(
										Float.parseFloat(nom_images[2]),
										Float.parseFloat(nom_images[3]));
								image.scalePercent(100);
								document.add(image);
								ii++;
							} else {
								str_html += strLine;
							}
						}

						String tabla_det = "<table border='1' cellspacing='0' cellpadding='0' width='350' align='center'>"
								+ "<tr>"
								+ "<td valign='top' align='center'><strong>MENSUALIDAD</strong></td>"
								+ "<td valign='top' align='center'><strong>FECHA</strong></td>"
								+ "<td valign='top' align='center'><strong>CANTIDAD A </strong><strong>PAGAR</strong></td>"
								+ "</tr>";
						int num_pagos = 0;
						for (int i = 0; i < resInfContrato.getCotizacionDetList().size(); i++) {
							if (resInfContrato.getCotizacionDetList().get(i).getConce().equals("02")) {
								num_pagos++;
							}
						}

						int mens = 0;
						
						String pago_final_res = "";
						String fecha_pago_cuota = "";
						BigDecimal monto_cuota = new BigDecimal(0);
						
						for (int i = 0; i < resInfContrato.getCotizacionDetList().size(); i++) {
							if (resInfContrato.getCotizacionDetList().get(i).getConce().equals("02")) {
								mens++;
								tabla_det += "<tr>";
								tabla_det += "<td width='146' align='center'>"
										+ mens + "/" + num_pagos + "</td>";
								tabla_det += "<td width='206' align='center'>"
										+ resInfContrato.getCotizacionDetList()
										.get(i).getFlim() + "</td>";
								tabla_det += "<td width='157' align='center'>"
										+ nf.format(new BigDecimal(
												resInfContrato
												.getCotizacionDetList()
												.get(i).getTotal()))
												+ "</td>";
								tabla_det += "</tr>";
							}
							if (resInfContrato.getCotizacionDetList().get(i)
									.getConce().equals("03")) {
								pago_final_res = resInfContrato.getCotizacionDetList().get(i).getTotal() + "";
							}
							
							if (resInfContrato.getCotizacionDetList().get(i).getConcex().equals("GASTOS_ADMIN")) {
								fecha_pago_cuota = resInfContrato.getCotizacionDetList().get(i).getFlim() + "";
							}
							
							if (resInfContrato.getCotizacionDetList().get(i).getConcex().equals("GASTOS_ADMIN")) {
								monto_cuota = new BigDecimal(resInfContrato.getCotizacionDetList().get(i).getTotal());
							}
							
						}

						tabla_det += "</table>";

						for (int i = 0; i < respCatalogosDTO.getEdoCivilList()
								.size(); i++) {
							if (respCatalogosDTO
									.getEdoCivilList()
									.get(i)
									.getEdocvl()
									.equals(resInfContrato.getCliente()
											.getEdocvl())) {
								resInfContrato.getCliente().setEdo_civilCDesc(
										respCatalogosDTO.getEdoCivilList()
										.get(i).getEdocvlx());
								break;
							}
						}

						try {
							rep_html = str_html.replace("|apoderado1|",
									resInfContrato.getEquipo().getApoderado1());
							str_html = rep_html;
							rep_html = str_html.replace("|apoderado2|",
									resInfContrato.getEquipo().getApoderado2());
							str_html = rep_html;
							rep_html = str_html.replace("|nomcte|",
									resInfContrato.getCliente().getNombre1C()
									+ " "
									+ resInfContrato.getCliente()
									.getNombre2C()
									+ " "
									+ resInfContrato.getCliente()
									.getApp_patC()
									+ " "
									+ resInfContrato.getCliente()
									.getApp_matC());
							str_html = rep_html;
							rep_html = str_html
									.replace("|codigo|",
											resInfContrato.getEquipo()
											.getEqunr() == null ? ""
													: resInfContrato
													.getEquipo()
													.getEqunr());
							str_html = rep_html;
							rep_html = str_html
									.replace("|npiso|", resInfContrato
											.getEquipo().getPiso() == null ? ""
													: resInfContrato.getEquipo()
													.getPiso());
							str_html = rep_html;
							rep_html = str_html
									.replace(
											"|m2p|",
											resInfContrato.getEquipo()
											.getSup_total() <= 0 ? "0"
													: String.valueOf(resInfContrato
															.getEquipo()
															.getSup_total()));
							str_html = rep_html;
							rep_html = str_html
									.replace("|nest|", resInfContrato
											.getEquipo().getNest() <= 0 ? "0"
													: resInfContrato.getEquipo()
													.getNest() + "");
							str_html = rep_html;
							rep_html = str_html
									.replace("|asigne|",
											resInfContrato.getEquipo()
											.getAsign_E() == null ? ""
													: resInfContrato
													.getEquipo()
													.getAsign_E());
							str_html = rep_html;
							if (resInfContrato.getCliente().getId_ifeC().equals("")	&& resInfContrato.getCliente().getId_pasprtC().equals("")) {
								rep_html = str_html.replace("|pasporife|", "Credencial de Elector con número de folio");
								str_html = rep_html;
								rep_html = str_html.replace("|ifepaspor|", resInfContrato.getCliente().getId_ifeC() == null ? "" : resInfContrato.getCliente().getId_ifeC() +", expedida por el Instituto Federal Electoral");
								str_html = rep_html;

							} else if (resInfContrato.getCliente().getId_ifeC().equals("") && !resInfContrato.getCliente().getId_pasprtC().equals("")) {
								rep_html = str_html.replace("|pasporife|", "Pasaporte con número de folio");
								str_html = rep_html;
								rep_html = str_html.replace("|ifepaspor|", resInfContrato.getCliente().getId_pasprtC() == null ? "" : resInfContrato.getCliente().getId_pasprtC());
								str_html = rep_html;

							} else {
								rep_html = str_html.replace("|pasporife|", "Credencial de Elector con número de folio");
								str_html = rep_html;
								rep_html = str_html.replace("|ifepaspor|", resInfContrato.getCliente().getId_ifeC() == null ? "" : resInfContrato.getCliente().getId_ifeC() +", expedida por el Instituto Federal Electoral");
								str_html = rep_html;
							}
							//rep_html = str_html.replace("|ife|", resInfContrato.getCliente().getId_ifeC() == null ? "" : resInfContrato.getCliente().getId_ifeC());
							//str_html = rep_html;
							// rep_html=str_html.replace("|edocivil|",
							// respCatalogosDTO.getEdoCivilDesc()==null?"":
							// respCatalogosDTO.getEdoCivilDesc());
							// str_html=rep_html;
							rep_html = str_html.replace("|edocivil|",
									resInfContrato.getCliente()
									.getEdo_civilCDesc() == null ? ""
											: resInfContrato.getCliente()
											.getEdo_civilCDesc());
							str_html = rep_html;
							rep_html = str_html.replace("|rfc|", resInfContrato
									.getCliente().getRfcC() == null ? ""
											: resInfContrato.getCliente().getRfcC());
							str_html = rep_html;
							// Dirección
							StringBuilder direccionCte = new StringBuilder();
							// direccionCte.append("Calle: ");
							direccionCte.append(resInfContrato.getCliente()
									.getCalleC() == null ? "" : " "
											+ resInfContrato.getCliente().getCalleC()
											+ " ");
							// direccionCte.append(" No Ext: ");
							direccionCte.append(resInfContrato.getCliente()
									.getNoextC() == null ? "" : resInfContrato
											.getCliente().getNoextC() + ", ");
							// direccionCte.append(" No Int: ");
							String noInt = resInfContrato.getCliente()
									.getNointC() == null ? "" : resInfContrato
											.getCliente().getNointC();
							direccionCte.append(noInt.trim().equals("") ? ""
									: "No Int. "
									+ resInfContrato.getCliente()
									.getNointC() + ", ");
							// direccionCte.append(" Col: ");
							direccionCte.append(resInfContrato.getCliente()
									.getColnC() == null ? "" : resInfContrato
											.getCliente().getColnC() + ", ");
							// direccionCte.append(" C.P. ");
							direccionCte.append(resInfContrato.getCliente()
									.getCdpstC() == null ? "" : resInfContrato
											.getCliente().getCdpstC() == "" ? ""
													: "C.P. "
													+ resInfContrato.getCliente()
													.getCdpstC() + ", ");
							// direccionCte.append(" Mun. o Del.: ");
							direccionCte.append(resInfContrato.getCliente()
									.getDlmcpC() == null ? "" : resInfContrato
											.getCliente().getDlmcpC() + ", ");
							// direccionCte.append(" País: ");
							direccionCte.append(catDesc.getPaisDesc(
									respCatalogosDTO.getPaisesList(),
									resInfContrato.getCliente().getPais()));
							direccionCte.append(", ");
							direccionCte
							.append(catDesc.getRegionDesc(
									respCatalogosDTO.getRegionesList(),
									resInfContrato.getCliente()
									.getRegion() == null ? ""
											: resInfContrato
											.getCliente()
											.getRegion()));
							direccionCte.append(".");

							rep_html = str_html.replace("|direccioncte|",
									direccionCte.toString());
							str_html = rep_html;
							rep_html = str_html.replace(
									"|preciovta|",
									nf.format(resInfContrato.getEquipo()
											.getPrice()) + "");
							str_html = rep_html;
							rep_html = str_html.replace(
									"|apartado|",
									nf.format(resInfContrato.getEquipo()
											.getAnticipo() <= 0 ? 0
													: resInfContrato.getEquipo()
													.getAnticipo())
													+ "");
							str_html = rep_html;
							rep_html = str_html
									.replace(
											"|apartadocl|",
											NumberToLetterConverter
											/*********.convertNumberToLetter(resInfContrato
													.getEquipo()
													.getAnticipo() <= 0 ? 0
															: resInfContrato
															.getEquipo()
															.getAnticipo()));**********/
											.convertNumberToLetter(resInfContrato
													.getEquipo()
													.getAnticipo() <= 0 ? new BigDecimal(0)
															: new BigDecimal(resInfContrato
															.getEquipo()
															.getAnticipo())));
							str_html = rep_html;
							String cadenaNueva = resInfContrato.getEquipo()
									.getEngp() + "";

							String puntoPorcentaje = new String(resInfContrato
									.getEquipo().getEngp() + "");
							String[] porcentajePunto = puntoPorcentaje
									.split("\\.");

							if (porcentajePunto[1].length() == 1) {
								cadenaNueva = cadenaNueva + "0";
							} else if (porcentajePunto[1].length() == 3) {
								cadenaNueva = cadenaNueva.substring(0,
										cadenaNueva.length() - 1);

							}

							rep_html = str_html.replace("|penganche|",
									cadenaNueva);
							str_html = rep_html;
							/*
							 * String puntoPorcentaje = new
							 * String(resInfContrato.getEquipo().getEngp()+"");
							 * String [] porcentajePunto =
							 * puntoPorcentaje.split("\\.");
							 * if(porcentajePunto.length>0){
							 * 
							 * String cadena1=
							 * NumberToLetterConverter.convertNumberToLetter
							 * (porcentajePunto[0]); String cadena2=
							 * NumberToLetterConverter
							 * .convertNumberToLetter(porcentajePunto[1]);
							 * 
							 * cadena1 = cadena1.substring(0,
							 * cadena1.length()-18); cadena2 =
							 * cadena2.substring(0, cadena2.length()-18);
							 * 
							 * String cadena = cadena1 + " punto " + cadena2;
							 * rep_html=str_html.replace("|plenganche|",
							 * cadena);
							 * 
							 * }else{
							 * 
							 * String cadena=
							 * NumberToLetterConverter.convertNumberToLetter
							 * (porcentajePunto[0]);
							 * rep_html=str_html.replace("|plenganche|",
							 * cadena.substring(0, cadena.length()-18));
							 * 
							 * }
							 */
							str_html = rep_html;
							rep_html = str_html.replace(
									"|cenganche|",
									nf.format(resInfContrato.getEquipo()
											.getEngm() == 0 ? 0
													: resInfContrato.getEquipo()
													.getEngm())
													+ "");
							str_html = rep_html;
							rep_html = str_html
									.replace(
											"|clenganche|",
											NumberToLetterConverter
											/**********.convertNumberToLetter(resInfContrato
													.getEquipo()
													.getEngm()));**********/
											.convertNumberToLetter(new BigDecimal(resInfContrato
													.getEquipo()
													.getEngm())));
							str_html = rep_html;
							rep_html = str_html.replace(
									"|cdiferidos|",
									nf.format(resInfContrato.getEquipo()
											.getDifem() == 0 ? 0
													: resInfContrato.getEquipo()
													.getDifem())
													+ "");
							str_html = rep_html;
							rep_html = str_html
									.replace(
											"|cldiferidos|",
											NumberToLetterConverter
											/*********.convertNumberToLetter(resInfContrato
													.getEquipo()
													.getDifem()));**********/
											.convertNumberToLetter(new BigDecimal(resInfContrato
													.getEquipo()
													.getDifem())));
							str_html = rep_html;
							rep_html = str_html.replace(
									"|tabladiferidos-mesfechacant|", tabla_det);
							str_html = rep_html;
							
							String fechacuota = "";
							
							//Coloca la fecha de la cuota única
							if(!fecha_pago_cuota.equals("00/00/0000")){
								fechacuota = "en fecha: "+ fecha_pago_cuota;
							}else{
								fechacuota = "a la firma del presente contrato";
							}
							
							rep_html = str_html.replace(
									"|fechacuota|", fechacuota);
							str_html = rep_html;
							
							rep_html = str_html.replace(
									"|montocuota|", nf.format(monto_cuota));
							str_html = rep_html;
							
							rep_html = str_html.replace(
									"|montocuotaletra|", NumberToLetterConverter.convertNumberToLetter(monto_cuota));
							str_html = rep_html;
							
							
							rep_html = str_html.replace("|pagofinal|",
									nf.format(new BigDecimal(pago_final_res)));
							str_html = rep_html;
							rep_html = str_html
									.replace(
											"|ctabanco|",
											resInfContrato.getCuentasList().get(0).getCta() == null ? ""
													: resInfContrato.getCuentasList().get(0).getCta());
							str_html = rep_html;
							rep_html = str_html
									.replace("|nbanco|",
											resInfContrato.getCuentasList().get(0).getBanco()  == null ? ""
													: resInfContrato.getCuentasList().get(0).getBanco());
							str_html = rep_html;
							rep_html = str_html
									.replace("|ctaclabe|",
											resInfContrato.getCuentasList().get(0).getClabe() == null ? ""
													: resInfContrato.getCuentasList().get(0).getClabe());
							str_html = rep_html;
							rep_html = str_html
									.replace(
											"|referencia|",
											resInfContrato.getCuenta()
											.getReferencia() == null ? ""
													: resInfContrato
													.getCuenta()
													.getReferencia());
							str_html = rep_html;
							rep_html = str_html
									.replace("|telefono|",
											resInfContrato.getCliente()
											.getTelfnC() == null ? ""
													: resInfContrato
													.getCliente()
													.getTelfnC());
							str_html = rep_html;
							
							String espacios = "";
							
							int row_a_insertar = 0 ;
							
							//Espacios a insertar contrato firmas Miyana
							
							if(this.getUnidadTecSup().equals("5900MYA")){
							
								if(6 <= num_pagos && num_pagos >= 16 ){
									row_a_insertar = 19 - num_pagos;
								}
								
								if(41 <= num_pagos && num_pagos >= 51 ){
									row_a_insertar = 54 - num_pagos;
								}
							}else if(this.getUnidadTecSup().equals("5022PJA")){
							
								if(0 <= num_pagos && num_pagos <= 9){
									row_a_insertar = 9 - num_pagos;
								}
								
							}else if(this.getUnidadTecSup().equals("5022PSL")){ 
								
								if( 0 <= num_pagos && num_pagos <= 10){
									row_a_insertar = 15 - num_pagos;
								}
							}
							
							for (int i = 0; i < row_a_insertar; i++) {
								espacios += "<br>";	
							}
								
							rep_html = str_html
									.replace("|espacios|", espacios);
							
							str_html = rep_html;
							rep_html = str_html.replace("|ciudad|", "MEXICO");
							str_html = rep_html;
							rep_html = str_html.replace("|dias|",
									camposFecha[2]);
							str_html = rep_html;
							// rep_html=str_html.replace("|mes|",
							// camposFecha[1]);
							rep_html = str_html.replace("|mes|", mes[month]);
							str_html = rep_html;
							rep_html = str_html.replace("|anio|",
									camposFecha[0]);
							str_html = rep_html;
							String fecha = camposFecha[2]+ "/" + mes[month] + "/" + camposFecha[0];
							rep_html = str_html.replace("|fechaimp|", fecha);
							str_html = rep_html;
							rep_html = str_html
									.replace("|fase|", resInfContrato
											.getEquipo().getFase() == null ? ""
													: resInfContrato.getEquipo()
													.getFase());
							str_html = rep_html;
							in.close();
						} catch (Exception e) {
							System.out.println(e);
						}

						htmlWorker.parse(new StringReader(str_html));
						document.close();

						InputStream is = new FileInputStream(new File(
								temp_folder));

						// response.setHeader("Content-Disposition",
						// "attachment;filename=\"contrato.pdf\"");
						// IOUtils.copy(is, response.getOutputStream());
						// response.flushBuffer();

						System.out.println("Contrato creado");
					} else {
						log.info("Archivo de contrato no existe");
					}

					// ///////////////////////////////// reporte encuesta cierre
					// ////////////////////////////////

					String encuesta_name = "encuesta_"
							+ resInfContrato.getCliente().getId_cliente_sap()
							+ ".pdf";
					String sFileEncuesta = pathBase + "DESA_"
							+ this.getUnidadTecSup() + "//encuestacierre.htm";
					// String sFichero = pathReportes
					// +"DESA_"+this.getUnidadTecSup()+"//"+fileName+".jrxml";
					File html_encuesta = new File(sFileEncuesta);

					String encuesta_html = "";
					String encuesta_folder = context.getRealPath("/")
							+ "files/" + encuesta_name;

					if (html_encuesta.exists()) {
						String rep_html_enc = "";

						Document docencuesta = new Document(PageSize.LETTER);

						PdfWriter pdfWriterEncuesta = PdfWriter.getInstance(
								docencuesta, new FileOutputStream(
										encuesta_folder));
						docencuesta.open();
						docencuesta.addAuthor("Grupo Gigante Inmobiliario");
						docencuesta.addCreator("Grupo Gigante Inmobiliario");
						docencuesta.addSubject("Gracias por preferirnos");
						docencuesta.addCreationDate();
						docencuesta.addTitle("Encuesta Cierre");
						docencuesta.top((float) 0.0);
						HTMLWorker htmlWorkerEncuesta = new HTMLWorker(
								docencuesta);

						FileInputStream fstreamencuesta = new FileInputStream(
								sFileEncuesta);

						DataInputStream inenc = new DataInputStream(
								fstreamencuesta);
						BufferedReader brenc = new BufferedReader(
								new InputStreamReader(inenc, "UTF-8"));
						String strLineEnc = "";
						String str_html_enc = "";

						int ii = 0;
						while ((strLineEnc = brenc.readLine()) != null) {

							if (strLineEnc.indexOf("|imgencuesta" + ii + "|") != -1) {
								try {
									String nom_images[] = strLineEnc.split(" ");
									Image image = Image.getInstance(pathBase
											+ "DESA_" + this.getUnidadTecSup()
											+ "//imagenes//" + nom_images[1]);
									// image.setAlignment(Image.MIDDLE);
									image.setAbsolutePosition(
											Float.parseFloat(nom_images[2]),
											Float.parseFloat(nom_images[3]));
									image.scalePercent(60);
									docencuesta.add(image);
								} catch (Exception e) {
								}
								ii++;

							} else {
								str_html_enc += strLineEnc;
							}
						}
						
						String fecha = camposFecha[2]+ "/" + mes[month] + "/" + camposFecha[0];
						rep_html_enc = str_html_enc.replace("|fechaimp|", fecha);
						str_html_enc = rep_html_enc;
						rep_html_enc = str_html_enc.replace("|nomcte|",
								resInfContrato.getCliente().getNombre1C()
								+ " "
								+ resInfContrato.getCliente()
								.getNombre2C()
								+ " "
								+ resInfContrato.getCliente()
								.getApp_patC()
								+ " "
								+ resInfContrato.getCliente()
								.getApp_matC());
						str_html_enc = rep_html_enc;
						rep_html_enc = str_html_enc.replace("|casadepto|",
								resInfContrato.getEquipo().getId_equnrx());
						str_html_enc = rep_html_enc;

						inenc.close();

						htmlWorkerEncuesta
						.parse(new StringReader(str_html_enc));
						docencuesta.close();

						InputStream isenc = new FileInputStream(new File(
								encuesta_folder));

						// response.setHeader("Content-Disposition",
						// "attachment;filename=\"encuestacierre.pdf\"");
						// IOUtils.copy(isenc, response.getOutputStream());
						// response.flushBuffer();

						System.out.println("Encuesta creada");
					} else {
						log.info("Archivo de encuesta no existe");
					}

					// ///////////////////////////////// Fin reporte encuesta
					// cierre ////////////////////////////

					// ///////////////////////////////// Inicio Memoria
					// descriptiva ////////////////////////////////

					String memoria_name = "memoria_"
							+ resInfContrato.getCliente().getId_cliente_sap()
							+ ".pdf";

					String sFileMemoria = pathBase + "DESA_"
							+ this.getUnidadTecSup() + "//memoria.htm";
					// String sFichero = pathReportes
					// +"DESA_"+this.getUnidadTecSup()+"//"+fileName+".jrxml";
					File html_memoria = new File(sFileMemoria);

					String memoria_html = "";
					String memoria_folder = context.getRealPath("/") + "files/"
							+ memoria_name;

					if (html_memoria.exists()) {
						String rep_html_memoria = "";

						Document docmemoria = new Document(PageSize.LETTER);

						PdfWriter pdfWriterMemoria = PdfWriter.getInstance(
								docmemoria,
								new FileOutputStream(memoria_folder));

						/*
						 * PageHeader event = new PageHeader();
						 * event.setHeader("d:/exit.png");
						 * pdfWriterMemoria.setPageEvent(event);
						 */

						docmemoria.open();
						docmemoria.addAuthor("Grupo Gigante Inmobiliario");
						docmemoria.addCreator("Grupo Gigante Inmobiliario");
						docmemoria.addSubject("Gracias por preferirnos");
						docmemoria.addCreationDate();
						docmemoria.addTitle("Memoria descriptiva");
						docmemoria.top((float) 0.0);

						/*
						 * Image logo =
						 * Image.getInstance(getImage(seasonalFilter
						 * .getPictureFileId()).getAbsolutePath());
						 * logo.setAlignment(Image.MIDDLE);
						 * logo.setAbsolutePosition(0, 0);
						 * logo.scalePercent(100); Chunk chunk = new Chunk(logo,
						 * 0, 0); HeaderFooter header = new HeaderFooter(new
						 * Phrase(chunk), true);
						 * header.setBorder(Rectangle.NO_BORDER);
						 * document.setHeader(header);
						 */

						HTMLWorker htmlWorkerMemoria = new HTMLWorker(
								docmemoria);

						FileInputStream fstreammemoria = new FileInputStream(
								sFileMemoria);

						DataInputStream inmemoria = new DataInputStream(
								fstreammemoria);
						BufferedReader brmemoria = new BufferedReader(
								new InputStreamReader(inmemoria, "UTF-8"));
						String strLineMemoria = "";
						String str_html_memoria = "";
						int ii = 0;
						while ((strLineMemoria = brmemoria.readLine()) != null) {

							if (strLineMemoria
									.indexOf("|imgmemoria" + ii + "|") != -1) {
								try {
									String nom_images[] = strLineMemoria
											.split(" ");
									Image image = Image.getInstance(pathBase
											+ "DESA_" + this.getUnidadTecSup()
											+ "//imagenes//" + nom_images[1]);
									// image.setAlignment(Image.MIDDLE);
									image.setAbsolutePosition(
											Float.parseFloat(nom_images[2]),
											Float.parseFloat(nom_images[3]));
									image.scalePercent(60);
									docmemoria.add(image);
								} catch (Exception e) {
								}

								ii++;
							} else {
								str_html_memoria += strLineMemoria;
							}
						}
						rep_html_memoria = str_html_memoria.replace(
								"|casadepto|", resInfContrato.getEquipo()
								.getId_equnrx());
						str_html_memoria = rep_html_memoria;
						rep_html_memoria = str_html_memoria.replace("|nomcte|",
								resInfContrato.getCliente().getNombre1C()
								+ " "
								+ resInfContrato.getCliente()
								.getNombre2C()
								+ " "
								+ resInfContrato.getCliente()
								.getApp_patC()
								+ " "
								+ resInfContrato.getCliente()
								.getApp_matC());
						str_html_memoria = rep_html_memoria;

						inmemoria.close();

						htmlWorkerMemoria.parse(new StringReader(
								str_html_memoria));
						docmemoria.close();

						InputStream ismemoria = new FileInputStream(new File(
								memoria_folder));

						// response.setHeader("Content-Disposition",
						// "attachment;filename=\"encuestacierre.pdf\"");
						// IOUtils.copy(isenc, response.getOutputStream());
						// response.flushBuffer();

						System.out.println("Memoria creada");
					} else {
						log.info("Memoria no existe");
					}
					// ///////////////////////////////// Fin memoria descriptiva
					// ////////////////////////////////

					// ///////////////////////////////// Aviso de privacidad
					// ///////////////////////////////////
						avisoPrivacidad();
					// ///////////////////////////////// Fin Aviso de privacidad
					// ////////////////////////////////

					// ///////////////////////////////// Tabla de acabados
					// //////////////////////////////////////
					String acabado_name = "acabado_"
							+ resInfContrato.getCliente().getId_cliente_sap()
							+ ".pdf";

					String tipo_equipoA = resInfContrato.getEquipo().getEqunr()
							.substring(0, 1).toUpperCase();

					String tipo_acabado = "";

					if (tipo_equipoA.equals("C")) {
						tipo_acabado = "acabadosCasa";
					} else {
						tipo_acabado = "acabados";
					}

					String sFileAcabado = "";

					if (!(resInfContrato.getEquipo().getFase().equals("F02") && unidadTecSup
							.equals("5900MYA"))) {
						sFileAcabado = pathBase + "DESA_" + this.getUnidadTecSup() + "//" + tipo_acabado + ".htm";
					}else{
						sFileAcabado = pathBase + "DESA_" + this.getUnidadTecSup() + "//fase_02//" + tipo_acabado + ".htm";	
					}

					// String sFichero = pathReportes
					// +"DESA_"+this.getUnidadTecSup()+"//"+fileName+".jrxml";
					File html_acabado = new File(sFileAcabado);

					String acabado_html = "";
					String acabado_folder = context.getRealPath("/") + "files/"
							+ acabado_name;

					if (html_acabado.exists()) {
						String rep_html_acabado = "";

						// Document docacabado = new
						// Document(PageSize.LETTER.rotate());
						Document docacabado = new Document(PageSize.LETTER);

						PdfWriter pdfWriterAcabado = PdfWriter.getInstance(
								docacabado,
								new FileOutputStream(acabado_folder));

						/*
						 * PageHeader event = new PageHeader();
						 * event.setHeader("d:/exit.png");
						 * pdfWriterMemoria.setPageEvent(event);
						 */

						docacabado.open();
						docacabado.addAuthor("Grupo Gigante Inmobiliario");
						docacabado.addCreator("Grupo Gigante Inmobiliario");
						docacabado.addSubject("Gracias por preferirnos");
						docacabado.addCreationDate();
						docacabado.addTitle("Tabla de Acabados");
						docacabado.top((float) 0.0);
						// docacabado.setHtmlStyleClass("fornt")

						/*
						 * Image logo =
						 * Image.getInstance(getImage(seasonalFilter
						 * .getPictureFileId()).getAbsolutePath());
						 * logo.setAlignment(Image.MIDDLE);
						 * logo.setAbsolutePosition(0, 0);
						 * logo.scalePercent(100); Chunk chunk = new Chunk(logo,
						 * 0, 0); HeaderFooter header = new HeaderFooter(new
						 * Phrase(chunk), true);
						 * header.setBorder(Rectangle.NO_BORDER);
						 * document.setHeader(header);
						 */

						HTMLWorker htmlWorkerAcabado = new HTMLWorker(
								docacabado);

						FileInputStream fstreamacabado = new FileInputStream(
								sFileAcabado);

						DataInputStream inacabado = new DataInputStream(
								fstreamacabado);
						BufferedReader bracabado = new BufferedReader(
								new InputStreamReader(inacabado, "UTF-8"));
						String strLineAcabado = "";
						String str_html_acabado = "";
						int ii = 0;
						while ((strLineAcabado = bracabado.readLine()) != null) {

							if (strLineAcabado
									.indexOf("|imgacabado" + ii + "|") != -1) {
								try {
									String nom_images[] = strLineAcabado
											.split(" ");
									Image image = Image.getInstance(pathBase
											+ "DESA_" + this.getUnidadTecSup()
											+ "//imagenes//" + nom_images[1]);
									// image.setAlignment(Image.MIDDLE);
									image.setAbsolutePosition(
											Float.parseFloat(nom_images[2]),
											Float.parseFloat(nom_images[3]));
									image.scalePercent(60);
									docacabado.add(image);
								} catch (Exception e) {
								}

								ii++;
							} else {
								str_html_acabado += strLineAcabado;
							}
						}

						rep_html_acabado = str_html_acabado.replace(
								"|casadepto|", resInfContrato.getEquipo()
								.getId_equnrx());
						str_html_acabado = rep_html_acabado;
						rep_html_acabado = str_html_acabado.replace("|nomcte|",
								resInfContrato.getCliente().getNombre1C()
								+ " "
								+ resInfContrato.getCliente()
								.getNombre2C()
								+ " "
								+ resInfContrato.getCliente()
								.getApp_patC()
								+ " "
								+ resInfContrato.getCliente()
								.getApp_matC());
						str_html_acabado = rep_html_acabado;

						inacabado.close();

						htmlWorkerAcabado.parse(new StringReader(
								str_html_acabado));
						docacabado.close();

						InputStream isacabado = new FileInputStream(new File(
								acabado_folder));

						// response.setHeader("Content-Disposition",
						// "attachment;filename=\"encuestacierre.pdf\"");
						// IOUtils.copy(isenc, response.getOutputStream());
						// response.flushBuffer();
						System.out.println("Acabados creada");
					} else {
						log.info("Acabados no existe");
					}
					// ///////////////////////////////// Fin tabla de acabados
					// ////////////////////////////////

					// ///////////////////////////////// Tipos de casa o depto
					// ////////////////////////////////

					String tipos_name = "tipos_"
							+ resInfContrato.getCliente().getId_cliente_sap()
							+ ".pdf";

					String sFileTipos = "";
					
					sFileTipos = pathBase + "DESA_" + this.getUnidadTecSup() + "//tipos.htm";

					// String sFichero = pathReportes
					// +"DESA_"+this.getUnidadTecSup()+"//"+fileName+".jrxml";
					File html_tipos = new File(sFileTipos);

					String tipos_html = "";
					String tipos_folder = context.getRealPath("/") + "files/"
							+ tipos_name;

					if (html_tipos.exists()) {
						String rep_html_tipos = "";

						Document doctipos = new Document(PageSize.LETTER);

						PdfWriter pdfWriterTipos = PdfWriter.getInstance(
								doctipos, new FileOutputStream(tipos_folder));

						doctipos.open();
						doctipos.addAuthor("Grupo Gigante Inmobiliario");
						doctipos.addCreator("Grupo Gigante Inmobiliario");
						doctipos.addSubject("Gracias por preferirnos");
						doctipos.addCreationDate();
						doctipos.addTitle("Tipos de casa o depto");
						doctipos.top((float) 0.0);

						HTMLWorker htmlWorkerTipos = new HTMLWorker(doctipos);

						FileInputStream fstreamtipos = new FileInputStream(
								sFileTipos);

						DataInputStream intipos = new DataInputStream(
								fstreamtipos);
						BufferedReader brtipos = new BufferedReader(
								new InputStreamReader(intipos, "UTF-8"));
						String strLineTipos = "";
						String str_html_tipos = "";

						try {
							int ii = 0;
							while ((strLineTipos = brtipos.readLine()) != null) {
								// str_html_tipos+=strLineTipos;
								if (strLineTipos.indexOf("|imgtipo" + ii + "|") != -1) {
									String nom_images[] = strLineTipos
											.split(" ");
									Image imagefooter = Image
											.getInstance(pathBase + "DESA_"
													+ this.getUnidadTecSup()
													+ "//imagenes//"
													+ nom_images[1]);
									// image.setAlignment(Image.MIDDLE);
									imagefooter.setAbsolutePosition(
											Float.parseFloat(nom_images[2]),
											Float.parseFloat(nom_images[3]));
									imagefooter.scalePercent(100);
									doctipos.add(imagefooter);
									ii++;
								} else {
									str_html_tipos += strLineTipos;
								}
							}
						} catch (Exception e) {
							System.out.print(e);
						}
						String imgtipocd = resInfContrato.getEquipo().getTipo();

						if (imgtipocd != null) {
							imgtipocd.replace("\"", "\'");
						} else {
							imgtipocd = "";
						}

						String path_casa_img = pathBase + "DESA_"
								+ this.getUnidadTecSup()
								+ "//imagenes//tipos//casa tipo " + imgtipocd
								+ ".png";
						
						String path_depto_img = "";
						String fase = resInfContrato.getEquipo().getFase();
						String equipo = resInfContrato.getEquipo().getEqunr();
						String ud ="";
						
						/* Siendo diferente en la distribución y orientacion, utilizaron los tipos de departamento 
						 * en el conjunto de Miyana y Puerta Jardín lo que obliga a crear una carpeta por cada fase*/
						
						if (fase.equals("F02") && unidadTecSup.equals("5900MYA")) {
							path_depto_img = pathBase + "DESA_"
								+ this.getUnidadTecSup()
								+ "//fase_02//tipos//depto tipo " + imgtipocd + ".png";
						
						}else if(fase.equals("F03") && unidadTecSup.equals("5900MYA")){
							
							if(resInfContrato.getEquipo().getPiso().equals("00")){
								ud = " t";
							}
							
							if (imgtipocd.equals("J") && equipo.substring(equipo.length()-1).equals("8")){
								ud = ud + " ud 8";
							}
							
							path_depto_img = pathBase + "DESA_"
									+ this.getUnidadTecSup()
									+ "//fase_03//tipos//depto tipo " + imgtipocd + ud + ".png";
						
						}else if(fase.equals("F04") && unidadTecSup.equals("5022PJA")) {
							 
							ud = "";
							
							 if(resInfContrato.getEquipo().getPiso().equals("04")){
								ud = " t";
							}
							
							if (imgtipocd.equals("A") && equipo.substring(equipo.length()-1).equals("4")){
								ud = ud + " ud 4";
							}else if(imgtipocd.equals("D") && equipo.substring(equipo.length()-1).equals("8")){
								ud = ud + " ud 8";
							}else if(imgtipocd.equals("E") && equipo.substring(equipo.length()-1).equals("7")){
								ud = ud + " ud 7";
							}
							
							path_depto_img = pathBase + "DESA_"
								+ this.getUnidadTecSup()
								+ "//fase_04//tipos//depto tipo " + imgtipocd + ud + ".png";
							
						}else{
						
							path_depto_img = pathBase + "DESA_"
								+ this.getUnidadTecSup()
								+ "//imagenes//tipos//depto tipo " + imgtipocd + ".png";
						}
						
						String tipo_equipo = resInfContrato.getEquipo()
								.getEqunr().substring(0, 1).toUpperCase();

						String imagen_tipo = "";

						if (tipo_equipo.equals("C")) {
							imagen_tipo = path_casa_img;
						} else {
							imagen_tipo = path_depto_img;
						}

						try {
							Image image = Image.getInstance(imagen_tipo);
							image.setAlignment(Image.TOP);
							image.setAbsolutePosition(22, 85);
							// image.setAbsolutePosition(40, 90);
							// image.scalePercent(70);
							image.scalePercent(43);
							doctipos.add(image);
						} catch (Exception e) {
							log.error(e.getMessage());
						}

						rep_html_tipos = str_html_tipos.replace("|casadepto|",
								resInfContrato.getEquipo().getId_equnrx());
						str_html_tipos = rep_html_tipos;
						rep_html_tipos = str_html_tipos.replace("|nomcte|",
								resInfContrato.getCliente().getNombre1C()
								+ " "
								+ resInfContrato.getCliente()
								.getNombre2C()
								+ " "
								+ resInfContrato.getCliente()
								.getApp_patC()
								+ " "
								+ resInfContrato.getCliente()
								.getApp_matC());
						str_html_tipos = rep_html_tipos;

						intipos.close();

						htmlWorkerTipos.parse(new StringReader(str_html_tipos));
						doctipos.close();

						InputStream istipos = new FileInputStream(new File(
								tipos_folder));

						// response.setHeader("Content-Disposition",
						// "attachment;filename=\"encuestacierre.pdf\"");
						// IOUtils.copy(isenc, response.getOutputStream());
						// response.flushBuffer();

						System.out.println("Tipos creada");
					} else {
						log.info("Tipos no existe");
					}
					// ///////////////////////////////// Fin tipos de casa o
					// depto ////////////////////////////////

					// ///////////////////////////////// Planta de conjunto
					// ////////////////////////////////
						plantaConjunto();
					// ///////////////////////////////// Fin planta de conjunto
					// ////////////////////////////////
					// ///////////////////////////////// Estacionamiento
					// /////////////////////////////

					String estacionamiento_name = "estacionamiento_"
							+ resInfContrato.getCliente().getId_cliente_sap()
							+ ".pdf";

					String sFileEstacionamiento = "";

						sFileEstacionamiento = pathBase + "DESA_"
								+ this.getUnidadTecSup()
								+ "//estacionamiento.htm";
						
					// String sFichero = pathReportes
					// +"DESA_"+this.getUnidadTecSup()+"//"+fileName+".jrxml";
					File html_estacionamiento = new File(sFileEstacionamiento);

					String tipos_estacionamiento = "";
					String estacionamiento_folder = context.getRealPath("/")
							+ "files/" + estacionamiento_name;

					if (html_estacionamiento.exists()
							&& !tipo_equipoA.equals("C")) {
						String rep_html_estacionamiento = "";

						Document docestacionamiento = new Document(
								PageSize.LETTER);

						PdfWriter pdfWriterEstacionamiento = PdfWriter
								.getInstance(docestacionamiento,
										new FileOutputStream(
												estacionamiento_folder));

						docestacionamiento.open();
						docestacionamiento
						.addAuthor("Grupo Gigante Inmobiliario");
						docestacionamiento
						.addCreator("Grupo Gigante Inmobiliario");
						docestacionamiento
						.addSubject("Gracias por preferirnos");
						docestacionamiento.addCreationDate();
						docestacionamiento
						.addTitle("Planta de Estacionamiento");
						docestacionamiento.top((float) 0.0);

						HTMLWorker htmlWorkerEstacionamiento = new HTMLWorker(
								docestacionamiento);

						FileInputStream fstreamestacionamiento = new FileInputStream(
								sFileEstacionamiento);

						DataInputStream inestacionamiento = new DataInputStream(
								fstreamestacionamiento);
						BufferedReader brestacionamiento = new BufferedReader(
								new InputStreamReader(inestacionamiento,
										"UTF-8"));
						String strLineEstacionamiento = "";
						String str_html_estacionamiento = "";

						try {
							int ii = 0;
							while ((strLineEstacionamiento = brestacionamiento
									.readLine()) != null) {
								// str_html_tipos+=strLineTipos;
								if (strLineEstacionamiento
										.indexOf("|imgestacionamiento" + ii
												+ "|") != -1) {
									String nom_images[] = strLineEstacionamiento
											.split(" ");
									Image imagefooter = Image
											.getInstance(pathBase + "DESA_"
													+ this.getUnidadTecSup()
													+ "//imagenes//"
													+ nom_images[1]);
									// image.setAlignment(Image.MIDDLE);
									imagefooter.setAbsolutePosition(
											Float.parseFloat(nom_images[2]),
											Float.parseFloat(nom_images[3]));
									imagefooter.scalePercent(60);
									docestacionamiento.add(imagefooter);
									ii++;
								} else {
									str_html_estacionamiento += strLineEstacionamiento;
								}
							}
						} catch (Exception e) {
							System.out.print(e);
						}

						String path_estacionamiento_img = pathBase + "DESA_"
								+ this.getUnidadTecSup()
								+ "//imagenes//estacionamiento.png";

						try {
							Image image = Image
									.getInstance(path_estacionamiento_img);
							image.setAlignment(Image.TOP);
							image.setAbsolutePosition(22, 85);
							// image.setAbsolutePosition(40, 90);
							// image.scalePercent(70);
							image.scalePercent(43);
							docestacionamiento.add(image);
						} catch (Exception e) {
							log.error(e.getMessage());
						}

						rep_html_estacionamiento = str_html_estacionamiento
								.replace("|casadepto|", resInfContrato
										.getEquipo().getId_equnrx());
						str_html_estacionamiento = rep_html_estacionamiento;
						rep_html_estacionamiento = str_html_estacionamiento
								.replace("|nomcte|", resInfContrato
										.getCliente().getNombre1C()
										+ " "
										+ resInfContrato.getCliente()
										.getNombre2C()
										+ " "
										+ resInfContrato.getCliente()
										.getApp_patC()
										+ " "
										+ resInfContrato.getCliente()
										.getApp_matC());
						str_html_estacionamiento = rep_html_estacionamiento;

						inestacionamiento.close();

						htmlWorkerEstacionamiento.parse(new StringReader(
								str_html_estacionamiento));
						docestacionamiento.close();

						InputStream isestacionamiento = new FileInputStream(
								new File(estacionamiento_folder));

						// response.setHeader("Content-Disposition",
						// "attachment;filename=\"encuestacierre.pdf\"");
						// IOUtils.copy(isenc, response.getOutputStream());
						// response.flushBuffer();

						System.out.println("Estacionamiento creado");
					} else {
						log.info("Estacionamiento no existe");
					}
					// ///////////////////////////////// Fin estacionamiento
					// ////////////////////////////////

					// ///////////////////////////////// Carta Autorizacion
					// Bancaria //////////////////////////////////
					String cartaautorizacion_name = "cartaautorizacion_"
							+ resInfContrato.getCliente().getId_cliente_sap()
							+ ".pdf";

					String sFileCartaAutorizacion = "";

					sFileCartaAutorizacion = pathBase + "DESA_" + this.getUnidadTecSup() + "//cartaautorizacion.pdf";
					
					// File htmlcartaautorizacion = new
					// File(sFileCartaAutorizacion);

					String cartaautorizacion_folder = context.getRealPath("/")
							+ "files/" + cartaautorizacion_name;

					File source = new File(sFileCartaAutorizacion);
					File destination = new File(cartaautorizacion_folder);

					if (source.exists())
						copiar(source, destination);

					// ///////////////////////////////// Fin Autorizacion
					// Bancaria ////////////////////////////////

					// ///////////////////////////////Cuenta Bancaria de pago
					// ////////////////////////////////
						cuentaBancaria();

					// ////////////////////////////////Fin de Cuenta Bancaria
					// ///////////////////////////////////

					// ///////////////////////////////// Inicio Indice Anexos
					// ///////////////////////////////////

					String indice_name = "indice_"
							+ resInfContrato.getCliente().getId_cliente_sap()
							+ ".pdf";

					String sFileIndice = "";

						sFileIndice = pathBase + "DESA_"
								+ this.getUnidadTecSup() + "//indice.htm";
					
					// String sFichero = pathReportes
					// +"DESA_"+this.getUnidadTecSup()+"//"+fileName+".jrxml";
					File html_indice = new File(sFileIndice);

					String tipos_indice = "";
					String indice_folder = context.getRealPath("/") + "files/"
							+ indice_name;

					if (html_indice.exists()) {
						String rep_html_indice = "";

						Document docindice = new Document(PageSize.LETTER);

						PdfWriter pdfWriterIndice = PdfWriter.getInstance(
								docindice, new FileOutputStream(indice_folder));

						docindice.open();
						docindice.addAuthor("Grupo Gigante Inmobiliario");
						docindice.addCreator("Grupo Gigante Inmobiliario");
						docindice.addSubject("Gracias por preferirnos");
						docindice.addCreationDate();
						docindice.addTitle("Indice de Anexos");
						docindice.top((float) 0.0);

						HTMLWorker htmlWorkerIndice = new HTMLWorker(docindice);

						FileInputStream fstreamindice = new FileInputStream(
								sFileIndice);

						DataInputStream inindice = new DataInputStream(
								fstreamindice);
						BufferedReader brindice = new BufferedReader(
								new InputStreamReader(inindice, "UTF-8"));
						String strLineIndice = "";
						String str_html_indice = "";

						try {
							int ii = 0;
							while ((strLineIndice = brindice.readLine()) != null) {
								// str_html_tipos+=strLineTipos;
								if (strLineIndice.indexOf("|imgindice" + ii	+ "|") != -1) {
									String nom_images[] = strLineIndice.split(" ");
									Image imagefooter = Image.getInstance(pathBase + "DESA_"
													+ this.getUnidadTecSup()
													+ "//imagenes//"
													+ nom_images[1]);
									// image.setAlignment(Image.MIDDLE);
									imagefooter.setAbsolutePosition(
											Float.parseFloat(nom_images[2]),
											Float.parseFloat(nom_images[3]));
									imagefooter.scalePercent(60);
									docindice.add(imagefooter);
									ii++;
								} else {
									str_html_indice += strLineIndice;
								}
							}
						} catch (Exception e) {
							System.out.print(e);
						}

						rep_html_indice = str_html_indice.replace(
								"|casadepto|", resInfContrato.getEquipo()
								.getId_equnrx());
						str_html_indice = rep_html_indice;
						rep_html_indice = str_html_indice.replace("|nomcte|",
								resInfContrato.getCliente().getNombre1C()
								+ " "
								+ resInfContrato.getCliente()
								.getNombre2C()
								+ " "
								+ resInfContrato.getCliente()
								.getApp_patC()
								+ " "
								+ resInfContrato.getCliente()
								.getApp_matC());
						str_html_indice = rep_html_indice;

						inindice.close();

						htmlWorkerIndice
						.parse(new StringReader(str_html_indice));
						docindice.close();

						InputStream isindice = new FileInputStream(new File(
								indice_folder));

						// response.setHeader("Content-Disposition",
						// "attachment;filename=\"encuestacierre.pdf\"");
						// IOUtils.copy(isenc, response.getOutputStream());
						// response.flushBuffer();

						System.out.println("Indice creado");
					} else {
						log.info("Indice no existe");
					}
					/////////////////////////////////// Fin Indice Anexos	//////////////////////////////////
					
					
					// ///////////////////////////////// Manifestación //////////////////////////////////
					String manifestacion_name = "manifestacion_"
							+ resInfContrato.getCliente().getId_cliente_sap()
							+ ".pdf";

					String sFileManifestacion = "";

					sFileManifestacion = pathBase + "DESA_" + this.getUnidadTecSup() + "//manifestacion.pdf";

					// File htmlcartaautorizacion = new
					// File(sFileCartaAutorizacion);

					String manifestacion_folder = context.getRealPath("/")
							+ "files/" + manifestacion_name;

					File source_manifestacion = new File(sFileManifestacion);
					File destination_manifestacion = new File(manifestacion_folder);

					if (source_manifestacion.exists()){
						System.out.println("Manifestacion Creada");
						copiar(source_manifestacion, destination_manifestacion);
					}else{
						System.out.println("Manifestacion no Existe");
					}
					
					
					// ///////////////////////////////// Carta Couta Unica //////////////////////////////////
					String cartacuotaunica_name = "cartacuotaunica_"
							+ resInfContrato.getCliente().getId_cliente_sap()
							+ ".pdf";

					String sFileCartaCuotaUnica = "";

					sFileCartaCuotaUnica = pathBase + "DESA_" + this.getUnidadTecSup() + "//cartacuotaunica.pdf";

					// File htmlcartaautorizacion = new
					// File(sFileCartaAutorizacion);

					String cartacuotaunica_folder = context.getRealPath("/")
							+ "files/" + cartacuotaunica_name;

					File source_cartacuotaunica = new File(sFileCartaCuotaUnica);
					File destination_cartacuotaunica = new File(cartacuotaunica_folder);

					if (source_cartacuotaunica.exists()){
						System.out.println("Carta Couta Unica Creada");
						copiar(source_cartacuotaunica, destination_cartacuotaunica);
					}else{
						System.out.println("Carta Couta Unica Existe");
					}

					// ///////////////////////////////// Fin Autorizacion
					// Bancaria ////////////////////////////////
					
					// ///////////////////////////////// Ubicación Depto
					// ////////////////////////////////

					String ubicacion_name = "ubicacion_"
							+ resInfContrato.getCliente().getId_cliente_sap()
							+ ".pdf";

					String sFileUbicacion = "";
					
					//Ticket#201501212085212
					if (resInfContrato.getEquipo().getFase().equals("F03") && unidadTecSup
							.equals("5022PSL"))
					sFileUbicacion = pathBase + "DESA_" + this.getUnidadTecSup() + "//ubicacion.htm";

					// String sFichero = pathReportes
					// +"DESA_"+this.getUnidadTecSup()+"//"+fileName+".jrxml";
					File html_ubicacion = new File(sFileUbicacion);

					String ubicacion_html = "";
					String ubicacion_folder = context.getRealPath("/") + "files/"
							+ ubicacion_name;

					if (html_ubicacion.exists()) {
						String rep_html_ubicacion = "";

						Document docubicacion = new Document(PageSize.LETTER);

						PdfWriter pdfWriterUbicacion = PdfWriter.getInstance(
								docubicacion, new FileOutputStream(ubicacion_folder));

						docubicacion.open();
						docubicacion.addAuthor("Grupo Gigante Inmobiliario");
						docubicacion.addCreator("Grupo Gigante Inmobiliario");
						docubicacion.addSubject("Gracias por preferirnos");
						docubicacion.addCreationDate();
						docubicacion.addTitle("Tipos de casa o depto");
						docubicacion.top((float) 0.0);

						HTMLWorker htmlWorkerUbicacion = new HTMLWorker(docubicacion);

						FileInputStream fstreamubicacion = new FileInputStream(
								sFileUbicacion);

						DataInputStream inubicacion = new DataInputStream(
								fstreamubicacion);
						BufferedReader brubicacion = new BufferedReader(
								new InputStreamReader(inubicacion, "UTF-8"));
						String strLineUbicacion = "";
						String str_html_ubicacion = "";

						try {
							int ii = 0;
							while ((strLineUbicacion = brubicacion.readLine()) != null) {
								// str_html_tipos+=strLineTipos;
								if (strLineUbicacion.indexOf("|imgubicacion" + ii + "|") != -1) {
									String nom_images[] = strLineUbicacion
											.split(" ");
									Image imagefooter = Image
											.getInstance(pathBase + "DESA_"
													+ this.getUnidadTecSup()
													+ "//imagenes//"
													+ nom_images[1]);
									// image.setAlignment(Image.MIDDLE);
									imagefooter.setAbsolutePosition(
											Float.parseFloat(nom_images[2]),
											Float.parseFloat(nom_images[3]));
									imagefooter.scalePercent(100);
									docubicacion.add(imagefooter);
									ii++;
								} else {
									str_html_ubicacion += strLineUbicacion;
								}
							}
						} catch (Exception e) {
							System.out.print(e);
						}
						
						String udd = "";
						//Ticket#201501212085212
						if (resInfContrato.getEquipo().getFase().equals("F03") && unidadTecSup
								.equals("5022PSL") && resInfContrato.getEquipo().getEqunr().substring(
										resInfContrato.getEquipo().getEqunr().length()-2).equals("07")){
							udd = " udd 7";
						}else if(resInfContrato.getEquipo().getFase().equals("F03") && unidadTecSup
								.equals("5022PSL") && resInfContrato.getEquipo().getEqunr().substring(
										resInfContrato.getEquipo().getEqunr().length()-2).equals("02")){
							udd = " udd 2";
						}
						
						String path_ubicacion_img = pathBase + "DESA_"
								+ this.getUnidadTecSup()
								+ "//imagenes//ubicaciones//F03//depto tipo " + resInfContrato.getEquipo().getTipo() + udd + ".png";
						
						try {
							Image image = Image.getInstance(path_ubicacion_img);
							image.setAlignment(Image.TOP);
							image.setAbsolutePosition(22, 85);
							// image.setAbsolutePosition(40, 90);
							// image.scalePercent(70);
							image.scalePercent(43);
							docubicacion.add(image);
						} catch (Exception e) {
							log.error(e.getMessage());
						}

						rep_html_ubicacion = str_html_ubicacion.replace("|casadepto|",
								resInfContrato.getEquipo().getId_equnrx());
						str_html_ubicacion = rep_html_ubicacion;
						rep_html_ubicacion = str_html_ubicacion.replace("|nomcte|",
								resInfContrato.getCliente().getNombre1C()
								+ " "
								+ resInfContrato.getCliente()
								.getNombre2C()
								+ " "
								+ resInfContrato.getCliente()
								.getApp_patC()
								+ " "
								+ resInfContrato.getCliente()
								.getApp_matC());
						str_html_ubicacion = rep_html_ubicacion;

						inubicacion.close();

						htmlWorkerUbicacion.parse(new StringReader(str_html_ubicacion));
						docubicacion.close();

						InputStream isubicacion = new FileInputStream(new File(
								ubicacion_folder));

						// response.setHeader("Content-Disposition",
						// "attachment;filename=\"encuestacierre.pdf\"");
						// IOUtils.copy(isenc, response.getOutputStream());
						// response.flushBuffer();

						System.out.println("Ubicacion creada");
					} else {
						log.info("Ubicacion no existe");
					}
					
					// ///////////////////////////////// Ubicación Detpo ////////////////////////////////


					sourceFiles.add(indice_folder);
					sourceFiles.add(encuesta_folder);
					sourceFiles.add(temp_folder);
					sourceFiles.add(memoria_folder);
					sourceFiles.add(acabado_folder);
					sourceFiles.add(tipos_folder);
					sourceFiles.add(estacionamiento_folder);
					sourceFiles.add(cartaautorizacion_folder);
					sourceFiles.add(manifestacion_folder);
					sourceFiles.add(cartacuotaunica_folder);
					sourceFiles.add(ubicacion_folder);

					nombreFiles.add(indice_name);
					nombreFiles.add(encuesta_name);
					nombreFiles.add(contrato_name);
					nombreFiles.add(memoria_name);
					nombreFiles.add(acabado_name);
					nombreFiles.add(tipos_name);
					nombreFiles.add(estacionamiento_name);
					nombreFiles.add(cartaautorizacion_name);
					nombreFiles.add(manifestacion_name);
					nombreFiles.add(cartacuotaunica_name);
					nombreFiles.add(ubicacion_name);
					
					conoceCliente();
					zipeaDocumentos();
					
				} catch (Exception e) {
					log.error(e.getMessage());
				}

				break;
			case 2:
				List<CartaOfertaDto> lstCartaOferta = new ArrayList<CartaOfertaDto>();
				CartaOfertaDto carta = new CartaOfertaDto();

				String fechaCreacion = "México, Distrito Federal, a "
						+ camposFecha[2] + " de " + meses[month] + " de "
						+ camposFecha[0] + ".";
				String cliente = resInfContrato.getCliente().getNombre1C()
						+ " " + resInfContrato.getCliente().getNombre2C() + " "
						+ resInfContrato.getCliente().getApp_patC() + " "
						+ resInfContrato.getCliente().getApp_matC();
				String casaDepto = this.DeptoCasa(resInfContrato.getEquipo()
						.getEqunr());
				String numDepto = resInfContrato.getEquipo().getEqunr();
				String piso = resInfContrato.getEquipo().getPiso();
				String superficieTotal = String.valueOf(resInfContrato
						.getEquipo().getSup_total());
				String numEstacionamiento = String.valueOf(resInfContrato
						.getEquipo().getNest());
				String marcadoNumeroEstacionamiento = "";
				String cantidadApartado = String.valueOf(resInfContrato
						.getEquipo().getAnticipo());
				String letra1 = NumberToLetterConverter
						/**********.convertNumberToLetter(resInfContrato.getEquipo()
								.getAnticipo());**********/
						.convertNumberToLetter(new BigDecimal(resInfContrato.getEquipo()
								.getAnticipo()));
				String cantidad2 = "";
				String letra2 = "";
				String numeroPagos = "";
				String cantidad3 = "";
				String letra3 = "";
				String precioInmueble = "$"
						+ String.valueOf(resInfContrato.getEquipo().getPrice());
				String domicilio1 = "Calle: "
						+ resInfContrato.getCliente().getCalleC() + " No Ext: "
						+ resInfContrato.getCliente().getNoextC() + " No Int: "
						+ resInfContrato.getCliente().getNointC() + " Col: "
						+ resInfContrato.getCliente().getColnC() + " C.P. "
						+ resInfContrato.getCliente().getCdpstC();
				String domicilio2 = "Municipio:"
						+ resInfContrato.getCliente().getDlmcpC()
						+ " ESTADO: "
						+ catDesc.getPaisDesc(respCatalogosDTO.getPaisesList(),
								resInfContrato.getCliente().getPais())
								+ ", "
								+ catDesc.getRegionDesc(
										respCatalogosDTO.getRegionesList(),
										resInfContrato.getCliente().getRegion());
				String correoElectronico = "";

				/*
				 * carta.setFechaCreacion(fechaCreacion);
				 * carta.setPersonaCompra(cliente);
				 * carta.setCasaDepto(casaDepto); carta.setNumDepto(numDepto);
				 * carta.setPisoDepto(piso);
				 * carta.setSuperficieDepto(superficieTotal);
				 * carta.setLugarEstacionamiento(numEstacionamiento);
				 * carta.setMarcadoNumero(marcadoNumeroEstacionamiento); //
				 * carta.setCantidad1(cantidadApartado); //
				 * carta.setImporteLetra1(letra1); //
				 * carta.setCantidad2(cantidad2); //
				 * carta.setImporteLetra2(letra2); //
				 * carta.setNumeroPagos(numeroPagos); //
				 * carta.setCantidad3(cantidad3); //
				 * carta.setImporteLetra3(letra3);
				 * carta.setPrecioInmueble(precioInmueble);
				 * carta.setDomicilio1(domicilio1);
				 * carta.setDomicilio2(domicilio2);
				 * carta.setCorreoElectronico(correoElectronico);
				 * lstCartaOferta.add(carta); dataSource = new
				 * JRBeanCollectionDataSource(lstCartaOferta); jasperReport =
				 * getCompiled("CartaOferta");
				 */

				String temp_folderco = context.getRealPath("/")
						+ "files/cartaoferta_" + Math.random() + ".pdf";
				String cartaoferta_name = "cartaoferta_"
						+ resInfContrato.getCliente().getId_cliente_sap()
						+ ".pdf";

				try {
					Document document = new Document(PageSize.LETTER);

					PdfWriter pdfWriter = PdfWriter.getInstance(document,
							new FileOutputStream(temp_folderco));
					document.open();
					document.addAuthor("Grupo Gigante Inmobiliario");
					document.addCreator("Grupo Gigante Inmobiliario");
					document.addSubject("Gracias por preferirnos");
					document.addCreationDate();
					document.addTitle("Carta Oferta");
					document.top((float) 0.0);
					HTMLWorker htmlWorker = new HTMLWorker(document);

					String sFichero = "";
					if (resInfContrato.getEquipo().getFase().equals("F02")
							&& unidadTecSup.equals("5900MYA")) {
						sFichero = (new StringBuilder(
								String.valueOf(recursos
										.getValorPropiedad("ruta.reportes.gestion.vivienda"))))
										.append("DESA_").append(getUnidadTecSup())
										.append("//fase_02//cartaoferta.htm")
										.toString();
					} else {
						sFichero = (new StringBuilder(
								String.valueOf(recursos
										.getValorPropiedad("ruta.reportes.gestion.vivienda"))))
										.append("DESA_").append(getUnidadTecSup())
										.append("//cartaoferta.htm").toString();
					}

					// String sFichero =
					// recursos.getValorPropiedad("ruta.reportes.gestion.vivienda")
					// +"DESA_"+this.getUnidadTecSup()+"//cartaoferta.htm";
					// String sFichero = pathReportes
					// +"DESA_"+this.getUnidadTecSup()+"//"+fileName+".jrxml";
					File html_file = new File(sFichero);

					String rep_html = "";
					if (html_file.exists()) {
						FileInputStream fstream = new FileInputStream(sFichero);

						DataInputStream in = new DataInputStream(fstream);
						BufferedReader br = new BufferedReader(
								new InputStreamReader(in, "UTF-8"));
						String strLine = "";
						String str_html = "";
						while ((strLine = br.readLine()) != null) {
							str_html += strLine;
						}
						String tabla_det = "<table border='1' cellspacing='0' cellpadding='0' width='350'>"
								+ "<tr>"
								+ "<td valign='top' align='center'><strong>PAGO</strong></td>"
								+ "<td valign='top' align='center'><strong>FECHA</strong></td>"
								+ "<td valign='top' align='center'><strong>MENSUALIDAD</strong></td>"
								+ "</tr>";

						int mens = 0;
						String pago_final_res = "";
						DecimalFormat df = new DecimalFormat("#.##");
						
						String fecha = resInfContrato.getCotizacionDetList().get(0).getFlim();
						
						if (fecha.equals("") || fecha == null || fecha.equals("00000000") || fecha.equals("00-00-00") || fecha.equals("00/00/0000")){
							fecha = "";
						}

						tabla_det += "<tr>";
						tabla_det += "<td width='146' align='center'>"
								+ resInfContrato.getCotizacionDetList().get(0)
								.getConcex() + "</td>";
						tabla_det += "<td width='206' align='center'>"
								+ fecha + "</td>";
						tabla_det += "<td width='157' align='right'>"
								+ nf.format(resInfContrato.getEquipo()
										.getAnticipo()) + "</td>";
						tabla_det += "</tr>";
						
						String concex= "";
						for (int i = 1; i < resInfContrato
								.getCotizacionDetList().size()-1; i++) {
							concex = resInfContrato.getCotizacionDetList().get(i).getConcex();
							
							if(concex.equals("GASTOS_ADMIN")){
								concex = "CUOTA ÚNICA";
							}
							
							if(concex.equals("ANTICIPO")){
								concex = "APARTADO";
							}
							
							if(concex.equals("SUB-TOTAL")){
								concex = "PRECIO TOTAL";
							}
							
							//Inserta Espacio en Blanco
							if(concex.equals("CUOTA ÚNICA")){
							tabla_det += "<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
							}
							
							mens++;
							tabla_det += "<tr>";
							tabla_det += 
									"<td width='146' align='center'>" + concex + 
							
									"</td>";
												
							tabla_det += "<td width='206' align='center'>"
									+ resInfContrato.getCotizacionDetList()
									.get(i).getFlim() + "</td>";
							// tabla_det+="<td width='157' align='right'>"+
							// nf.format(resInfContrato.getCotizacionDetList().get(i).getTotalDesc())+"</td>";
							tabla_det += "<td width='157' align='right'>"
									+ resInfContrato.getCotizacionDetList()
									.get(i).getTotalDesc() + "</td>";
							tabla_det += "</tr>";

							if (resInfContrato.getCotizacionDetList().get(i)
									.getConce().equals("03")) {
								pago_final_res = resInfContrato
										.getCotizacionDetList().get(i)
										.getTotal()
										+ "";
							}
						}

						tabla_det += "</table>";

						rep_html = str_html.replace("|apoderado1|",
								resInfContrato.getEquipo().getApoderado1());
						str_html = rep_html;
						rep_html = str_html.replace("|apoderado2|",
								resInfContrato.getEquipo().getApoderado2());
						str_html = rep_html;
						rep_html = str_html.replace("|nomcte|", resInfContrato
								.getCliente().getNombre1C()
								+ " "
								+ resInfContrato.getCliente().getNombre2C()
								+ " "
								+ resInfContrato.getCliente().getApp_patC()
								+ " "
								+ resInfContrato.getCliente().getApp_matC());
						str_html = rep_html;
						rep_html = str_html.replace("|codigo|", resInfContrato
								.getEquipo().getEqunr() == null ? ""
										: resInfContrato.getEquipo().getEqunr());
						str_html = rep_html;
						rep_html = str_html.replace("|npiso|", resInfContrato
								.getEquipo().getPiso() == null ? ""
										: resInfContrato.getEquipo().getPiso());
						str_html = rep_html;
						rep_html = str_html.replace("|m2p|", resInfContrato
								.getEquipo().getM2pr() == null ? "0"
										: resInfContrato.getEquipo().getM2pr() + "");
						str_html = rep_html;
						rep_html = str_html.replace("|nest|", resInfContrato
								.getEquipo().getNest() <= 0 ? "0"
										: resInfContrato.getEquipo().getNest() + "");
						str_html = rep_html;
						rep_html = str_html.replace("|asigne|", resInfContrato
								.getEquipo().getAsign_E() == null ? ""
										: resInfContrato.getEquipo().getAsign_E());
						str_html = rep_html;
						rep_html = str_html.replace("|ife|", resInfContrato
								.getCliente().getId_ifeC() == null ? ""
										: resInfContrato.getCliente().getId_ifeC());
						str_html = rep_html;
						rep_html = str_html.replace("|edocivil|",
								respCatalogosDTO.getEdoCivilDesc() == null ? ""
										: respCatalogosDTO.getEdoCivilDesc());
						str_html = rep_html;
						rep_html = str_html.replace("|rfc|", resInfContrato
								.getCliente().getRfcC() == null ? ""
										: resInfContrato.getCliente().getRfcC());
						str_html = rep_html;

						StringBuilder direccionCte = new StringBuilder();
						// direccionCte.append("Calle: ");
						direccionCte
						.append(resInfContrato.getCliente().getCalleC() == null ? ""
								: " "
								+ resInfContrato.getCliente()
								.getCalleC() + " ");
						// direccionCte.append(" No Ext: ");
						direccionCte.append(resInfContrato.getCliente()
								.getNoextC() == null ? "" : resInfContrato
										.getCliente().getNoextC() + ", ");
						// direccionCte.append(" No Int: ");
						String noInt = resInfContrato.getCliente().getNointC() == null ? ""
								: resInfContrato.getCliente().getNointC();
						direccionCte.append(noInt.trim().equals("") ? ""
								: "No Int. "
								+ resInfContrato.getCliente()
								.getNointC() + ", ");
						// direccionCte.append(" Col: ");
						direccionCte.append(resInfContrato.getCliente()
								.getColnC() == null ? "" : resInfContrato
										.getCliente().getColnC() + ", ");
						// direccionCte.append(" C.P. ");
						direccionCte.append(resInfContrato.getCliente()
								.getCdpstC() == null ? "" : resInfContrato
										.getCliente().getCdpstC() == "" ? "" : "C.P. "
												+ resInfContrato.getCliente().getCdpstC()
												+ ", ");
						// direccionCte.append(" Mun. o Del.: ");
						direccionCte.append(resInfContrato.getCliente()
								.getDlmcpC() == null ? "" : resInfContrato
										.getCliente().getDlmcpC() + ", ");
						// direccionCte.append(" País: ");
						direccionCte.append(catDesc.getPaisDesc(
								respCatalogosDTO.getPaisesList(),
								resInfContrato.getCliente().getPais()));
						direccionCte.append(", ");
						direccionCte
						.append(catDesc.getRegionDesc(respCatalogosDTO
								.getRegionesList(), resInfContrato
								.getCliente().getRegion() == null ? ""
										: resInfContrato.getCliente()
										.getRegion()));
						direccionCte.append(".");
						rep_html = str_html.replace("|direccioncte|",
								direccionCte.toString());
						str_html = rep_html;
						// rep_html=str_html.replace("|direccioncte|",
						// "Calle: "+resInfContrato.getCliente().getCalleC()==null?"":resInfContrato.getCliente().getCalleC()+
						// " No Ext: "+resInfContrato.getCliente().getNoextC()==null?"":resInfContrato.getCliente().getNoextC()+" No Int: "
						// +resInfContrato.getCliente().getNointC()==null?"":resInfContrato.getCliente().getNointC()+" Col: "+resInfContrato.getCliente().getColnC()==null?"":resInfContrato.getCliente().getColnC()+" C.P. "+resInfContrato.getCliente().getCdpstC()==null?"":resInfContrato.getCliente().getCdpstC()+" Municipio: "+resInfContrato.getCliente().getDlmcpC()==null?"":resInfContrato.getCliente().getDlmcpC()+" ESTADO: "+catDesc.getPaisDesc(respCatalogosDTO.getPaisesList(),
						// resInfContrato.getCliente().getPais())+", "+catDesc.getRegionDesc(respCatalogosDTO.getRegionesList(),
						// resInfContrato.getCliente().getRegion()==null?"":resInfContrato.getCliente().getRegion()));
						// str_html=rep_html;
						rep_html = str_html.replace("|preciovta|", "$"
								+ resInfContrato.getEquipo().getPrice());
						str_html = rep_html;
						rep_html = str_html.replace("|apartado|", nf
								.format(new BigDecimal(resInfContrato
										.getEquipo().getAnticipo() <= 0 ? "0"
												: resInfContrato.getEquipo()
												.getAnticipo() + "")));
						str_html = rep_html;
						rep_html = str_html
								.replace(
										"|apartadocl|",
										NumberToLetterConverter
										/**********.convertNumberToLetter(resInfContrato
												.getEquipo()
												.getAnticipo() <= 0 ? 0
														: resInfContrato
														.getEquipo()
														.getAnticipo()));**********/
										.convertNumberToLetter(resInfContrato
												.getEquipo()
												.getAnticipo() <= 0 ? new BigDecimal(0)
														: new BigDecimal(resInfContrato
														.getEquipo()
														.getAnticipo())));
						str_html = rep_html;
						rep_html = str_html.replace("|penganche|",
								resInfContrato.getEquipo().getEngp() == 0 ? "0"
										: resInfContrato.getEquipo().getEngp()
										+ "");
						str_html = rep_html;
						rep_html = str_html.replace("|plenganche|",
								NumberToLetterConverter
								/*********.convertNumberToLetter(resInfContrato
										.getEquipo().getEngp()));**********/
								.convertNumberToLetter(new BigDecimal(resInfContrato
										.getEquipo().getEngp())));
						str_html = rep_html;
						rep_html = str_html.replace("|cenganche|",
								resInfContrato.getEquipo().getEngm() == 0 ? "0"
										: resInfContrato.getEquipo().getEngm()
										+ "");
						str_html = rep_html;
						rep_html = str_html.replace("|clenganche|",
								NumberToLetterConverter
								/**********.convertNumberToLetter(resInfContrato
										.getEquipo().getEngm()));**********/
								.convertNumberToLetter(new BigDecimal(resInfContrato
										.getEquipo().getEngm())));
						str_html = rep_html;
						rep_html = str_html
								.replace("|cdiferidos|", resInfContrato
										.getEquipo().getDifem() == 0 ? "0"
												: resInfContrato.getEquipo().getDifem()
												+ "");
						str_html = rep_html;
						rep_html = str_html.replace("|cldiferidos|",
								NumberToLetterConverter
								/**********.convertNumberToLetter(resInfContrato
										.getEquipo().getDifem()));***********/
								.convertNumberToLetter(new BigDecimal(resInfContrato
										.getEquipo().getDifem())));
						str_html = rep_html;
						rep_html = str_html.replace(
								"|tabladiferidos-mesfechacant|", tabla_det);
						str_html = rep_html;
						rep_html = str_html.replace(
								"|pagofinal|",
								nf.format(new BigDecimal(resInfContrato
										.getEquipo().getPrice() + "")));
						str_html = rep_html;
						rep_html = str_html
								.replace("|ctabanco|",
										resInfContrato.getCuenta()
										.getTipo_cuenta() == null ? ""
												: resInfContrato.getCuenta()
												.getTipo_cuenta());
						str_html = rep_html;
						rep_html = str_html.replace("|nbanco|", resInfContrato
								.getCuenta().getBanco() == null ? ""
										: resInfContrato.getCuenta().getBanco());
						str_html = rep_html;
						rep_html = str_html
								.replace("|ctaclabe|", resInfContrato
										.getCuenta().getClabe() == null ? ""
												: resInfContrato.getCuenta().getClabe());
						str_html = rep_html;
						rep_html = str_html
								.replace("|referencia|",
										resInfContrato.getCuenta()
										.getReferencia() == null ? ""
												: resInfContrato.getCuenta()
												.getReferencia());
						str_html = rep_html;
						rep_html = str_html
								.replace("|telefono|", resInfContrato
										.getCliente().getTelfnC() == null ? ""
												: resInfContrato.getCliente()
												.getTelfnC());
						str_html = rep_html;
						rep_html = str_html.replace("|ciudad|", "MEXICO");
						str_html = rep_html;
						rep_html = str_html.replace("|dias|", camposFecha[2]);
						str_html = rep_html;
						rep_html = str_html.replace("|mes|", meses[month]);
						str_html = rep_html;
						rep_html = str_html.replace("|anio|", camposFecha[0]);
						str_html = rep_html;
						rep_html = str_html.replace("|fechaimp|", dateNow);
						str_html = rep_html;
						rep_html = str_html
								.replace("|correocte|", resInfContrato
										.getCliente().getMail1C() == null ? ""
												: resInfContrato.getCliente()
												.getMail1C());
						str_html = rep_html;
						
						in.close();

						htmlWorker.parse(new StringReader(str_html));
						document.close();

						InputStream is = new FileInputStream(new File(
								temp_folderco));

						
							response.setHeader("Content-Disposition",
									"attachment;filename=\"cartaoferta.pdf\"");
							IOUtils.copy(is, response.getOutputStream());
							response.flushBuffer();


						System.out.println("Carta oferta creada");
					} else {
						log.info("File dont exist");
					}
				} catch (Exception e) {
					log.error(e.getMessage());
				}
				break;

			case 3:

				// params.put("imgPath",url_imagen);
				params.put("imgPath",
						"http://vivienda.ggi.com.mx/vivienda/imagenes/logos_header/login_logoggi.png");

				params.put("title", "Puerta Santa Lucia");
				params.put("vivienda", resInfContrato.getEquipo().getEqunr());
				params.put("noCliente", resInfContrato.getCliente()
						.getId_cliente_sap());
				params.put("nombreCliente", resInfContrato.getCliente()
						.getNombre1C()
						+ " "
						+ resInfContrato.getCliente().getNombre2C()
						+ " "
						+ resInfContrato.getCliente().getApp_patC()
						+ " "
						+ resInfContrato.getCliente().getApp_matC());
				List<CtaBancariaDto> lista = resInfContrato.getCuentasList();
				if (lista != null && lista.size() > 0) {
					log.info("Referencia bancaria detectada:"
							+ lista.get(0).getReferencia());
					params.put("referencia", lista.get(0).getReferencia()); // ??
				}

				if (resInfContrato.getCuentasList().size() > 0) {
					for (int i = 0; i < resInfContrato.getCuentasList().size(); i++) {
						int z = 1;
						if (i == 0) {
							params.put("ctaba1", resInfContrato
									.getCuentasList().get(i).getCta());
							params.put("clabeba1", resInfContrato
									.getCuentasList().get(i).getClabe());
						} else if (i == 1) {
							params.put("ctaba2", resInfContrato
									.getCuentasList().get(i).getCta());
							params.put("clabeba2", resInfContrato
									.getCuentasList().get(i).getClabe());
						}
						log.info("BANCOS:"
								+ resInfContrato.getCuentasList().get(i)
								.getBanco());
						String[] datosBanco = this.logoBanco(resInfContrato
								.getCuentasList().get(i).getBanco());
						String tipoBanco = datosBanco[0].toString();
						log.info("Datos para imagen bancaria: logoBanco" + z
								+ ", datosBanco[1]:" + datosBanco[1]);
						if (tipoBanco.trim().equals("01")) { // ES BANAMEX
							params.put("logoBanco" + z, datosBanco[1]); // URL
							// DEL
							// BANCO
							// BANAMEX
						}

						if (tipoBanco.trim().equals("02")) { // ES BANAMEX
							params.put("logoBanco" + z, datosBanco[1]); // URL
							// DEL
							// BANCO
							// BANAMEX
						}
						z++;
					}
				} else {
					params.put("ctaba1", null);
					params.put("clabeba1", null);
					params.put("ctaba2", null);
					params.put("clabeba2", null);
				}

				List<CotizacionDetDto> listaP = new ArrayList<CotizacionDetDto>();
				for (int i = 0; i < resInfContrato.getCotizacionDetList()
						.size(); i++) {

					String mesActualx = "";
					String consecutivo = "";
					consecutivo = resInfContrato.getCotizacionDetList().get(i)
							.getConce();
					if (!consecutivo.trim().equals("")) {

						String[] periodo = resInfContrato
								.getCotizacionDetList().get(i).getFlim()
								.split("/");

						String fechaLimite = resInfContrato
								.getCotizacionDetList().get(i).getFlim();
						double total = resInfContrato.getCotizacionDetList()
								.get(i).getTotal();
						String conseX = resInfContrato.getCotizacionDetList()
								.get(i).getConcex();

						String totalDescuento = resInfContrato
								.getCotizacionDetList().get(i).getTotalDesc();
						totalDescuento = totalDescuento.replace(",", "");

						/**********Double totalDescuento2 = this.TruncarMonto(
								Double.valueOf(totalDescuento.substring(1)), 2);**********/
						BigDecimal totalDescuento2 = new BigDecimal(totalDescuento.substring(1)).setScale(2, BigDecimal.ROUND_DOWN);

						mesActualx = meses[Integer.parseInt(periodo[1]) - 1];
						CotizacionDetDto p = new CotizacionDetDto("", 0, "",
								"", "", fechaLimite, total, 0, 0, conseX, 0,
								mesActualx, "", "", totalDescuento2.toString());
						listaP.add(p);
					} else {
						mesActualx = "";
					}

				}
				dataSource = new JRBeanCollectionDataSource(listaP);
				jasperReport = this.getCompiled("Talonario");

				break;
			case 4:

				/*
				 * Calendar currentDate = Calendar.getInstance();
				 * SimpleDateFormat formatter= new
				 * SimpleDateFormat("yyyy/MMM/dd"); String dateNow =
				 * formatter.format(currentDate.getTime()); int month =
				 * currentDate.get(Calendar.MONTH); String [] camposFecha =
				 * dateNow.split("/"); String mesActual="";
				 */

				params.put("fecha", "México, Distrito Federal, a "
						+ camposFecha[2] + " de " + meses[month] + " de "
						+ camposFecha[0] + ".");
				params.put("nombreCliente", resInfContrato.getCliente()
						.getNombre1C()
						+ " "
						+ resInfContrato.getCliente().getNombre2C()
						+ " "
						+ resInfContrato.getCliente().getApp_patC()
						+ " "
						+ resInfContrato.getCliente().getApp_matC());
				params.put("dom1", "CALLE: "
						+ resInfContrato.getCliente().getCalleC() + " NO EXT: "
						+ resInfContrato.getCliente().getNoextC() + " NO INT: "
						+ resInfContrato.getCliente().getNointC() + " COL: "
						+ resInfContrato.getCliente().getColnC() + " C.P. "
						+ resInfContrato.getCliente().getCdpstC());
				params.put(
						"dom2",
						"MUNICIPIO:"
								+ resInfContrato.getCliente().getDlmcpC()
								+ " ESTADO: "
								+ catDesc.getPaisDesc(
										respCatalogosDTO.getPaisesList(),
										resInfContrato.getCliente().getPais())
										+ ", "
										+ catDesc.getRegionDesc(
												respCatalogosDTO.getRegionesList(),
												resInfContrato.getCliente().getRegion()));
				// params.put("facsimil","123456");//??
				// params.put("atencion", "0987654356789765"); //???

				List<CotizacionDetDto> listaPagare = new ArrayList<CotizacionDetDto>();
				int total = 0;
				for (int i = 0; i < resInfContrato.getCotizacionDetList()
						.size(); i++) {
					String consecutivo = resInfContrato.getCotizacionDetList()
							.get(i).getConce();
					if (!consecutivo.trim().equals("")) {
						total++;
					}
				}
				for (int i = 0; i < resInfContrato.getCotizacionDetList()
						.size(); i++) {

					String[] camposPeriodo = resInfContrato
							.getCotizacionDetList().get(i).getFlim().split("/");

					String consecutivo = resInfContrato.getCotizacionDetList()
							.get(i).getConce();
					if (!consecutivo.trim().equals("")) {

						mesActual = meses[Integer.parseInt(camposPeriodo[1]) - 1];

						formatoNumerico = "$0.00";
						String cantidadReporte = resInfContrato
								.getCotizacionDetList().get(i).getTotalDesc();
						cantidadReporte = cantidadReporte.replace(",", "");
						log.info("CANTIDAD PAGARE:" + cantidadReporte);

						if (cantidadReporte != null
								&& !cantidadReporte.trim().equals("")) {
							formatoNumerico = nf.format(new BigDecimal(
									cantidadReporte).doubleValue());
						}

						// String cantidadReporte2 =
						// resInfContrato.getCotizacionDetList().get(i).getTotal()
						// + "";
						// cantidadReporte2 = cantidadReporte2.replace(",", "");
						String parrafo = "Por el presente pagaré, el C. "
								+ resInfContrato.getCliente().getNombre1C()
								+ " "
								+ resInfContrato.getCliente().getNombre2C()
								+ " "
								+ resInfContrato.getCliente().getApp_patC()
								+ " "
								+ resInfContrato.getCliente().getApp_matC()
								+ ""
								+ " promete y se obliga a pagar de manera incondicional la cantidad de "
								+ formatoNumerico
								+ " ("
								+ NumberToLetterConverter
								.convertNumberToLetter(formatoNumerico
										.replace(",", "").replace("$",
												""))
												+ "), a favor de CALERMI, S.A. DE C.V., a más tardar el día "
												+ camposPeriodo[0]
														+ " del mes de "
														+ mesActual
														+ " de "
														+ camposPeriodo[2]
																+ ", en su domicilio ubicado en Avenida Ejército Nacional 350, Chapultepec Morales, Delegación Miguel Hidalgo, México, Distrito Federal.";
						String parrafo2 = "El presente pagaré forma parte de una serie de "
								+ (i + 1)
								+ "/"
								+ total
								+ ",  el deudor reconoce que por el solo vencimiento del presente, se vencerán anticipadamente los restantes.";
						//
						CotizacionDetDto p = new CotizacionDetDto("", 0, "",
								"", "", "", 0, 0, 0, parrafo2, 0, "", "", "",
								parrafo);
						listaPagare.add(p);
					} else {
						mesActual = "";
					}
				}

				dataSource = new JRBeanCollectionDataSource(listaPagare);
				jasperReport = getCompiled("Pagares");

				break;
			case 5:
				String cantidad1 = resInfCompPago.getPagoHdr().getNetwr_pag();
				formatoNumerico = "$0.00";

				log.info("cantidad:" + cantidad1);
				cantidad1 = cantidad1.replace(",", "");
				log.info("cantidad DES:" + cantidad1);
				/**********Double cantidad = this.TruncarMonto(Double.valueOf(cantidad1), 2);**********/
				BigDecimal cantidad = new BigDecimal(cantidad1).setScale(2, BigDecimal.ROUND_DOWN);
				if (cantidad1 != null && !cantidad1.trim().equals("")) {
					formatoNumerico = nf.format(new BigDecimal(cantidad1)
					.doubleValue());
				}

				params.put("noRegi", criteriosC.getIdPedido());
				// params.put("ruta",url_imagen);
				params.put("ruta",
						"http://vivienda.ggi.com.mx/vivienda/imagenes/logos_header/login_logoggi.png");

				params.put("nomEmpresa", resInfCompPago.getSociedad()
						.getNombre_soc());
				params.put("dirEmpresa", "Calle "
						+ resInfCompPago.getSociedad().getCalle() + " No Ext "
						+ resInfCompPago.getSociedad().getNoext());
				params.put("dir2Empresa", resInfCompPago.getSociedad()
						.getColn()
						+ " Delegación "
						+ resInfCompPago.getSociedad().getDlmcp()
						+ " C.P. "
						+ resInfCompPago.getSociedad().getCdpst());
				params.put("nomCliente", resInfCompPago.getPagoHdr()
						.getKunnrtx());
				params.put("dpto", resInfCompPago.getPagoHdr().getId_equnr());
				params.put("importe", formatoNumerico);
				params.put("importeDesc", NumberToLetterConverter
						.convertNumberToLetter(cantidad.toString()));
				//params.put("fechaPago", resInfCompPago.getPagoDet().getFpago());
				
				String[] parseo = resInfCompPago.getPagoDet().getFpago().split("-");
				Integer mesFecha = new Integer(parseo[1]);
				params.put("fechaPago", parseo[2] + " de " + mes[mesFecha-1] + " del " + parseo[0]);
				
				dataSource = new JRBeanCollectionDataSource(null);
				jasperReport = getCompiled("Recibos");
				break;

			default:
				break;
			}

			if (criteriosC.getAccion() != 1 && criteriosC.getAccion() != 2) {

				JasperPrint jp = JasperFillManager.fillReport(jasperReport,
						params, dataSource);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				log.info("baos.size:" + baos.size());
				JRPdfExporter exporter = new JRPdfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
				exporter.exportReport();
				String fileName = "reporte.pdf";
				// response.setHeader("Content-Disposition",
				// "inline; filename="+ fileName);
				// response.setContentType("application/pdf");
				response.setContentType("application/octet-stream");
				response.setHeader("Content-disposition",
						"attachment; filename=" + fileName);
				response.setContentLength(baos.size());
				ServletOutputStream outputStream = response.getOutputStream();
				log.info("baos.size:" + baos.size());
				log.info("os.size:" + outputStream.toString());
				baos.writeTo(outputStream);
				outputStream.flush();
			}

		} catch (Exception ex) {
			log.error(ex.getMessage());
			System.out.print(ex.getMessage());
		}

	}

	// ///////////////////////////////// Reporte aviso privacidad
	// ////////////////////////////////
	private void avisoPrivacidad() {

		try {
			String pathBase = recursos
					.getValorPropiedad("ruta.reportes.gestion.vivienda");
			String avisop_name = "avisoprivacidad_"
					+ resInfContrato.getCliente().getId_cliente_sap() + ".pdf";
			String sFileAvisop = pathBase + "DESA_" + this.getUnidadTecSup()
					+ "//avisodeprivacidad.htm";
			// String sFichero = pathReportes
			// +"DESA_"+this.getUnidadTecSup()+"//"+fileName+".jrxml";
			File html_avisop = new File(sFileAvisop);

			String avisop_html = "";
			String avisop_folder = context.getRealPath("/") + "files/"
					+ avisop_name;

			if (html_avisop.exists()) {
				String rep_html_avisop = "";

				Document docavisop = new Document(PageSize.LETTER);

				PdfWriter pdfWriterEncuesta = PdfWriter.getInstance(docavisop,
						new FileOutputStream(avisop_folder));
				docavisop.open();
				docavisop.addAuthor("Grupo Gigante Inmobiliario");
				docavisop.addCreator("Grupo Gigante Inmobiliario");
				docavisop.addSubject("Gracias por preferirnos");
				docavisop.addCreationDate();
				docavisop.addTitle("Aviso de privacidad");
				docavisop.top((float) 0.0);
				HTMLWorker htmlWorkerAvisop = new HTMLWorker(docavisop);

				FileInputStream fstreamavisop = new FileInputStream(sFileAvisop);

				DataInputStream inavisop = new DataInputStream(fstreamavisop);
				BufferedReader bravisop = new BufferedReader(
						new InputStreamReader(inavisop, "UTF-8"));
				String strLineAvisop = "";
				String str_html_avisop = "";

				int ii = 0;
				while ((strLineAvisop = bravisop.readLine()) != null) {

					if (strLineAvisop.indexOf("|imgavisop" + ii + "|") != -1) {
						try {
							String nom_images[] = strLineAvisop.split(" ");
							Image image = Image.getInstance(pathBase + "DESA_"
									+ this.getUnidadTecSup() + "//imagenes//"
									+ nom_images[1]);
							// image.setAlignment(Image.MIDDLE);
							image.setAbsolutePosition(
									Float.parseFloat(nom_images[2]),
									Float.parseFloat(nom_images[3]));
							image.scalePercent(60);
							docavisop.add(image);
						} catch (Exception e) {
						}

						ii++;
					} else {
						str_html_avisop += strLineAvisop;
					}

				}

				rep_html_avisop = str_html_avisop.replace("|casadepto|",
						resInfContrato.getEquipo().getId_equnrx());
				str_html_avisop = rep_html_avisop;
				rep_html_avisop = str_html_avisop.replace("|nomcte|",
						resInfContrato.getCliente().getNombre1C() + " "
								+ resInfContrato.getCliente().getNombre2C()
								+ " "
								+ resInfContrato.getCliente().getApp_patC()
								+ " "
								+ resInfContrato.getCliente().getApp_matC());
				str_html_avisop = rep_html_avisop;

				inavisop.close();

				htmlWorkerAvisop.parse(new StringReader(str_html_avisop));
				docavisop.close();

				InputStream isavisop = new FileInputStream(new File(
						avisop_folder));

				System.out.println("Aviso creada");

				sourceFiles.add(avisop_folder);
				nombreFiles.add(avisop_name);

			} else {
				log.info("Archivo de aviso no existe");
			}
			// response.setHeader("Content-Disposition",
			// "attachment;filename=\"encuestacierre.pdf\"");
			// IOUtils.copy(isenc, response.getOutputStream());
			// response.flushBuffer();

		} catch (Exception ex) {
			log.error(ex.getMessage());
			System.out.print(ex.getMessage());
		}
	}

	// ///////////////////////////////// Fin aviso privacidad
	// ////////////////////////////////

	// ///////////////////////////////// Planta de conjunto
	// ////////////////////////////////
	private void plantaConjunto() {
		try {

			String pathBase = recursos
					.getValorPropiedad("ruta.reportes.gestion.vivienda");
			String planta_name = "planta_"
					+ resInfContrato.getCliente().getId_cliente_sap() + ".pdf";
			String sFilePlanta = pathBase + "DESA_" + this.getUnidadTecSup()
					+ "//planta.htm";

			// String sFichero = pathReportes
			// +"DESA_"+this.getUnidadTecSup()+"//"+fileName+".jrxml";
			File html_planta = new File(sFilePlanta);

			String tipos_planta = "";
			String planta_folder = context.getRealPath("/") + "files/"
					+ planta_name;

			if (html_planta.exists()) {
				String rep_html_planta = "";

				Document docplanta = new Document(PageSize.LETTER);

				PdfWriter pdfWriterPlanta = PdfWriter.getInstance(docplanta,
						new FileOutputStream(planta_folder));

				docplanta.open();
				docplanta.addAuthor("Grupo Gigante Inmobiliario");
				docplanta.addCreator("Grupo Gigante Inmobiliario");
				docplanta.addSubject("Gracias por preferirnos");
				docplanta.addCreationDate();
				docplanta.addTitle("Planta de Conjunto");
				docplanta.top((float) 0.0);

				HTMLWorker htmlWorkerPlanta = new HTMLWorker(docplanta);

				FileInputStream fstreamplanta = new FileInputStream(sFilePlanta);

				DataInputStream inplanta = new DataInputStream(fstreamplanta);
				BufferedReader brplanta = new BufferedReader(
						new InputStreamReader(inplanta, "UTF-8"));
				String strLinePlanta = "";
				String str_html_planta = "";

				try {
					int ii = 0;
					while ((strLinePlanta = brplanta.readLine()) != null) {
						// str_html_tipos+=strLineTipos;
						if (strLinePlanta.indexOf("|imgplanta" + ii + "|") != -1) {
							String nom_images[] = strLinePlanta.split(" ");
							Image imagefooter = Image.getInstance(pathBase
									+ "DESA_" + this.getUnidadTecSup()
									+ "//imagenes//" + nom_images[1]);
							// image.setAlignment(Image.MIDDLE);
							imagefooter.setAbsolutePosition(
									Float.parseFloat(nom_images[2]),
									Float.parseFloat(nom_images[3]));
							imagefooter.scalePercent(60);
							docplanta.add(imagefooter);
							ii++;
						} else {
							str_html_planta += strLinePlanta;
						}
					}
				} catch (Exception e) {
					System.out.print(e);
				}

				String path_planta_img = "";

				if ((resInfContrato.getEquipo().getFase().equals("F02") && unidadTecSup.equals("5900MYA"))) {
					path_planta_img = pathBase + "DESA_"
							+ this.getUnidadTecSup()
							+ "//imagenes//planta_f02.png";
				} else if(resInfContrato.getEquipo().getFase().equals("F01") && unidadTecSup.equals("5900MYA")) {
					path_planta_img = pathBase + "DESA_"
							+ this.getUnidadTecSup() + "//imagenes//planta.png";
					
				}else if(resInfContrato.getEquipo().getFase().equals("F03") && unidadTecSup.equals("5900MYA")) {
					path_planta_img = pathBase + "DESA_"
							+ this.getUnidadTecSup() + "//imagenes//planta_f03.png";
					
				}else if((resInfContrato.getEquipo().getFase().equals("F02") && unidadTecSup
						.equals("5022PJA"))) {
					path_planta_img = pathBase + "DESA_"
							+ this.getUnidadTecSup()
							+ "//imagenes//plantaj_f02.png";
				} else {
					path_planta_img = pathBase + "DESA_"
							+ this.getUnidadTecSup() + "//imagenes//plantaj.png";
				}

				try {
					Image image = Image.getInstance(path_planta_img);
					image.setAlignment(Image.TOP);
					image.setAbsolutePosition(22, 85);
					// image.setAbsolutePosition(40, 90);
					// image.scalePercent(70);
					image.scalePercent(43);
					docplanta.add(image);
				} catch (Exception e) {
					log.error(e.getMessage());
				}

				rep_html_planta = str_html_planta.replace("|casadepto|",
						resInfContrato.getEquipo().getId_equnrx());
				str_html_planta = rep_html_planta;
				rep_html_planta = str_html_planta.replace("|nomcte|",
						resInfContrato.getCliente().getNombre1C() + " "
								+ resInfContrato.getCliente().getNombre2C()
								+ " "
								+ resInfContrato.getCliente().getApp_patC()
								+ " "
								+ resInfContrato.getCliente().getApp_matC());
				str_html_planta = rep_html_planta;

				inplanta.close();

				htmlWorkerPlanta.parse(new StringReader(str_html_planta));
				docplanta.close();

				InputStream isplanta = new FileInputStream(new File(
						planta_folder));

				// response.setHeader("Content-Disposition",
				// "attachment;filename=\"encuestacierre.pdf\"");
				// IOUtils.copy(isenc, response.getOutputStream());
				// response.flushBuffer();

				sourceFiles.add(planta_folder);
				nombreFiles.add(planta_name);

				System.out.println("Planta creada");
			} else {
				log.info("Planta no existe");
			}

		} catch (Exception ex) {
			log.error(ex.getMessage());
			System.out.print(ex.getMessage());
		}
	}

	// ///////////////////////////////// Fin planta de conjunto	// ////////////////////////////////

	// ///////////////////////////////Cuenta Bancaria de pago // ////////////////////////////////

	private void cuentaBancaria() {
		try {

			String pathBase = recursos
					.getValorPropiedad("ruta.reportes.gestion.vivienda");
			String cuentabancaria_name = "cuentabancaria_"
					+ resInfContrato.getCliente().getId_cliente_sap() + ".pdf";
			String sFileCuenta = pathBase + "DESA_" + this.getUnidadTecSup()
					+ "//cuentabancaria.htm";
			// String sFichero = pathReportes
			// +"DESA_"+this.getUnidadTecSup()+"//"+fileName+".jrxml";
			File html_cuentabancaria = new File(sFileCuenta);

			String cuentabancaria_html = "";
			String cuentabancaria_folder = context.getRealPath("/") + "files/"
					+ cuentabancaria_name;

			if (html_cuentabancaria.exists()) {
				String rep_html_enc = "";

				Document docCuenta = new Document(PageSize.LETTER);

				PdfWriter pdfWriterCuenta = PdfWriter.getInstance(docCuenta,
						new FileOutputStream(cuentabancaria_folder));
				docCuenta.open();
				docCuenta.addAuthor("Grupo Gigante Inmobiliario");
				docCuenta.addCreator("Grupo Gigante Inmobiliario");
				docCuenta.addSubject("Gracias por preferirnos");
				docCuenta.addCreationDate();
				docCuenta.addTitle("Cuenta Bancaria");
				docCuenta.top((float) 0.0);
				HTMLWorker htmlWorkerCuenta = new HTMLWorker(docCuenta);

				FileInputStream fstreamTalonario = new FileInputStream(
						sFileCuenta);

				DataInputStream inenc = new DataInputStream(fstreamTalonario);
				BufferedReader brenc = new BufferedReader(
						new InputStreamReader(inenc, "UTF-8"));
				String strLineEnc = "";
				String str_html_enc = "";

				int ii = 0;
				while ((strLineEnc = brenc.readLine()) != null) {

					if (strLineEnc.indexOf("|imgcuenta" + ii + "|") != -1) {
						try {
							String nom_images[] = strLineEnc.split(" ");
							Image image = Image.getInstance(pathBase + "DESA_"
									+ this.getUnidadTecSup() + "//imagenes//"
									+ nom_images[1]);
							// image.setAlignment(Image.MIDDLE);
							image.setAbsolutePosition(
									Float.parseFloat(nom_images[2]),
									Float.parseFloat(nom_images[3]));
							image.scalePercent(60);
							docCuenta.add(image);
						} catch (Exception e) {
						}
						ii++;

					} else {
						str_html_enc += strLineEnc;
					}
				}

				rep_html_enc = str_html_enc.replace("|title|",
						"Puerta Santa Lucia");
				str_html_enc = rep_html_enc;
				rep_html_enc = str_html_enc.replace("|vivienda|",
						resInfContrato.getEquipo().getEqunr());
				str_html_enc = rep_html_enc;
				rep_html_enc = str_html_enc.replace("|noCliente|",
						resInfContrato.getCliente().getId_cliente_sap());
				str_html_enc = rep_html_enc;
				rep_html_enc = str_html_enc.replace("|nombreCliente|",
						resInfContrato.getCliente().getNombre1C() + " "
								+ resInfContrato.getCliente().getNombre2C()
								+ " "
								+ resInfContrato.getCliente().getApp_patC()
								+ " "
								+ resInfContrato.getCliente().getApp_matC());
				str_html_enc = rep_html_enc;
				List<CtaBancariaDto> lista = resInfContrato.getCuentasList();
				if (lista != null && lista.size() > 0) {
					log.info("Referencia bancaria detectada:"
							+ lista.get(0).getReferencia());
					rep_html_enc = str_html_enc.replace("|referencia|", lista
							.get(0).getReferencia()); // ??
					str_html_enc = rep_html_enc;
				}

				if (resInfContrato.getCuentasList().size() > 0) {
					for (int i = 0; i < resInfContrato.getCuentasList().size(); i++) {
						int z = 1;
						if (i == 0) {
							rep_html_enc = str_html_enc.replace("|ctaba1|",
									resInfContrato.getCuentasList().get(i)
									.getCta());
							str_html_enc = rep_html_enc;
							rep_html_enc = str_html_enc.replace("|clabeba1|",
									resInfContrato.getCuentasList().get(i)
									.getClabe());
							str_html_enc = rep_html_enc;
						} else if (i == 1) {
							rep_html_enc = str_html_enc.replace("|ctaba2|",
									resInfContrato.getCuentasList().get(i)
									.getCta());
							str_html_enc = rep_html_enc;
							rep_html_enc = str_html_enc.replace("|clabeba2|",
									resInfContrato.getCuentasList().get(i)
									.getClabe());
							str_html_enc = rep_html_enc;
						}
						log.info("BANCOS:"
								+ resInfContrato.getCuentasList().get(i)
								.getBanco());
						String[] datosBanco = this.logoBanco(resInfContrato
								.getCuentasList().get(i).getBanco());
						String tipoBanco = datosBanco[0].toString();
						log.info("Datos para imagen bancaria: logoBanco" + z
								+ ", datosBanco[1]:" + datosBanco[1]);
						if (tipoBanco.trim().equals("01")) { // ES BANAMEX
							rep_html_enc = str_html_enc.replace("|logoBanco|"
									+ z, datosBanco[1]); // URL DEL BANCO
							// BANAMEX
							str_html_enc = rep_html_enc;
						}

						if (tipoBanco.trim().equals("02")) { // ES BANAMEX
							rep_html_enc = str_html_enc.replace("|logoBanco|"
									+ z, datosBanco[1]); // URL DEL BANCO
							// BANAMEX
							str_html_enc = rep_html_enc;
						}
						z++;
					}
				}

				inenc.close();

				htmlWorkerCuenta.parse(new StringReader(str_html_enc));
				docCuenta.close();

				InputStream isenc = new FileInputStream(new File(
						cuentabancaria_folder));

				// response.setHeader("Content-Disposition",
				// "attachment;filename=\"encuestacierre.pdf\"");
				// IOUtils.copy(isenc, response.getOutputStream());
				// response.flushBuffer();

				sourceFiles.add(cuentabancaria_folder);
				nombreFiles.add(cuentabancaria_name);

				System.out.println("Cuenta Bancaria creada");
			} else {
				log.info("Archivo de Cuenta Bancaria no existe");
			}

		} catch (Exception ex) {
			log.error(ex.getMessage());
			System.out.print(ex.getMessage());
		}

	}

	// ////////////////////////////////Fin de Cuenta Bancaria // ///////////////////////////////////

	// ///////////////////////////////// Couta Única // //////////////////////////////////////////
	private void coutaUnica() {

		try {
			String pathBase = recursos
					.getValorPropiedad("ruta.reportes.gestion.vivienda");
			String cuotau_name = "cuotaunica_"
					+ resInfContrato.getCliente().getId_cliente_sap() + ".pdf";
			String sFileCuotaU = pathBase + "DESA_" + this.getUnidadTecSup()
					+ "//cuotaunica.htm";
			// String sFichero = pathReportes
			// +"DESA_"+this.getUnidadTecSup()+"//"+fileName+".jrxml";
			File html_cuotau = new File(sFileCuotaU);

			String cuotau = "";
			String cuotau_folder = context.getRealPath("/") + "files/"
					+ cuotau_name;

			if (html_cuotau.exists()) {
				String rep_html_cuotau = "";

				Document doccuotau = new Document(PageSize.LETTER);

				PdfWriter pdfWriterCuota = PdfWriter.getInstance(doccuotau,
						new FileOutputStream(cuotau_folder));
				doccuotau.open();
				doccuotau.addAuthor("Grupo Gigante Inmobiliario");
				doccuotau.addCreator("Grupo Gigante Inmobiliario");
				doccuotau.addSubject("Gracias por preferirnos");
				doccuotau.addCreationDate();
				doccuotau.addTitle("Couta Unica");
				doccuotau.top((float) 0.0);
				HTMLWorker htmlWorkerCuotau = new HTMLWorker(doccuotau);

				FileInputStream fstreamcoutau = new FileInputStream(sFileCuotaU);

				DataInputStream incuotau = new DataInputStream(fstreamcoutau);
				BufferedReader brcuotau = new BufferedReader(
						new InputStreamReader(incuotau, "UTF-8"));
				String strLineCuotau = "";
				String str_html_cuotau = "";

				int ii = 0;
				while ((strLineCuotau = brcuotau.readLine()) != null) {

					if (strLineCuotau.indexOf("|imgcuotau" + ii + "|") != -1) {
						try {
							String nom_images[] = strLineCuotau.split(" ");
							Image image = Image.getInstance(pathBase + "DESA_"
									+ this.getUnidadTecSup() + "//imagenes//"
									+ nom_images[1]);
							// image.setAlignment(Image.MIDDLE);
							image.setAbsolutePosition(
									Float.parseFloat(nom_images[2]),
									Float.parseFloat(nom_images[3]));
							image.scalePercent(60);
							doccuotau.add(image);
						} catch (Exception e) {
						}

						ii++;
					} else {
						str_html_cuotau += strLineCuotau;
					}

				}

				rep_html_cuotau = str_html_cuotau.replace("|casadepto|",
						resInfContrato.getEquipo().getId_equnrx());
				str_html_cuotau = rep_html_cuotau;
				rep_html_cuotau = str_html_cuotau.replace("|nomcte|",
						resInfContrato.getCliente().getNombre1C() + " "
								+ resInfContrato.getCliente().getNombre2C()
								+ " "
								+ resInfContrato.getCliente().getApp_patC()
								+ " "
								+ resInfContrato.getCliente().getApp_matC());
				str_html_cuotau = rep_html_cuotau;

				rep_html_cuotau = str_html_cuotau.replace("|dias|",
						camposFecha[2]);
				str_html_cuotau = rep_html_cuotau;
				rep_html_cuotau = str_html_cuotau
						.replace("|mes|", meses[month]);
				str_html_cuotau = rep_html_cuotau;
				rep_html_cuotau = str_html_cuotau.replace("|anio|",
						camposFecha[0]);
				str_html_cuotau = rep_html_cuotau;

				incuotau.close();

				htmlWorkerCuotau.parse(new StringReader(str_html_cuotau));
				doccuotau.close();

				InputStream iscoutau = new FileInputStream(new File(
						cuotau_folder));

				System.out.println("Couta única creada");

				sourceFiles.add(cuotau_folder);
				nombreFiles.add(cuotau_name);

			} else {
				log.info("Archivo de Couta única no existe");
			}
			// response.setHeader("Content-Disposition",
			// "attachment;filename=\"encuestacierre.pdf\"");
			// IOUtils.copy(isenc, response.getOutputStream());
			// response.flushBuffer();

		} catch (Exception ex) {
			log.error(ex.getMessage());
			System.out.print(ex.getMessage());
		}
	}

	// ///////////////////////////////// Fin Couta Única // ///////////////////////////////////////

	// ///////////////////////////////// Couta Equipamiento // //////////////////////////////////////////
	private void coutaEquipamiento() {

		try {
			String pathBase = recursos
					.getValorPropiedad("ruta.reportes.gestion.vivienda");
			String cuotae_name = "cuotaequipamiento_"
					+ resInfContrato.getCliente().getId_cliente_sap() + ".pdf";
			String sFileCuotaE = pathBase + "DESA_" + this.getUnidadTecSup()
					+ "//cuotaequipamiento.htm";
			// String sFichero = pathReportes
			// +"DESA_"+this.getUnidadTecSup()+"//"+fileName+".jrxml";
			File html_cuotae = new File(sFileCuotaE);

			String cuotae_html = "";
			String cuotae_folder = context.getRealPath("/") + "files/"
					+ cuotae_name;

			if (html_cuotae.exists()) {
				String rep_html_cuotae = "";

				Document doccuotae = new Document(PageSize.LETTER);

				PdfWriter pdfWriterEncuesta = PdfWriter.getInstance(doccuotae,
						new FileOutputStream(cuotae_folder));
				doccuotae.open();
				doccuotae.addAuthor("Grupo Gigante Inmobiliario");
				doccuotae.addCreator("Grupo Gigante Inmobiliario");
				doccuotae.addSubject("Gracias por preferirnos");
				doccuotae.addCreationDate();
				doccuotae.addTitle("Couta Equipamiento");
				doccuotae.top((float) 0.0);
				HTMLWorker htmlWorkerCuotae = new HTMLWorker(doccuotae);

				FileInputStream fstreamcuotae = new FileInputStream(sFileCuotaE);

				DataInputStream incuotae = new DataInputStream(fstreamcuotae);
				BufferedReader brcuotae = new BufferedReader(
						new InputStreamReader(incuotae, "UTF-8"));
				String strLineCuotae = "";
				String str_html_cuotae = "";

				int ii = 0;
				while ((strLineCuotae = brcuotae.readLine()) != null) {

					if (strLineCuotae.indexOf("|imgcuotae" + ii + "|") != -1) {
						try {
							String nom_images[] = strLineCuotae.split(" ");
							Image image = Image.getInstance(pathBase + "DESA_"
									+ this.getUnidadTecSup() + "//imagenes//"
									+ nom_images[1]);
							// image.setAlignment(Image.MIDDLE);
							image.setAbsolutePosition(
									Float.parseFloat(nom_images[2]),
									Float.parseFloat(nom_images[3]));
							image.scalePercent(60);
							doccuotae.add(image);
						} catch (Exception e) {
						}

						ii++;
					} else {
						str_html_cuotae += strLineCuotae;
					}

				}

				rep_html_cuotae = str_html_cuotae.replace("|casadepto|",
						resInfContrato.getEquipo().getId_equnrx());
				str_html_cuotae = rep_html_cuotae;
				rep_html_cuotae = str_html_cuotae.replace("|nomcte|",
						resInfContrato.getCliente().getNombre1C() + " "
								+ resInfContrato.getCliente().getNombre2C()
								+ " "
								+ resInfContrato.getCliente().getApp_patC()
								+ " "
								+ resInfContrato.getCliente().getApp_matC());
				str_html_cuotae = rep_html_cuotae;

				rep_html_cuotae = str_html_cuotae.replace("|dias|",
						camposFecha[2]);
				str_html_cuotae = rep_html_cuotae;
				rep_html_cuotae = str_html_cuotae
						.replace("|mes|", meses[month]);
				str_html_cuotae = rep_html_cuotae;
				rep_html_cuotae = str_html_cuotae.replace("|anio|",
						camposFecha[0]);
				str_html_cuotae = rep_html_cuotae;

				incuotae.close();

				htmlWorkerCuotae.parse(new StringReader(str_html_cuotae));
				doccuotae.close();

				InputStream iscuotae = new FileInputStream(new File(
						cuotae_folder));

				System.out.println("Couta Equipamiento Creado");

				sourceFiles.add(cuotae_folder);
				nombreFiles.add(cuotae_name);

			} else {
				log.info("Archivo de Couta equipamiento no existe");
			}
			// response.setHeader("Content-Disposition",
			// "attachment;filename=\"encuestacierre.pdf\"");
			// IOUtils.copy(isenc, response.getOutputStream());
			// response.flushBuffer();

		} catch (Exception ex) {
			log.error(ex.getMessage());
			System.out.print(ex.getMessage());
		}
	}

	/////////////////////////////////// Fin Cuota Equipamiento	/////////////////////////////////////////
	
	
	
	/////////////////////////////////// Inicio Conoce a tu Cliente // //////////////////////////////////////////
	private void conoceCliente() {

		try {
			String pathBase = recursos.getValorPropiedad("ruta.reportes.gestion.vivienda");
			String conoceC_name = "conocecliente_"+ resInfContrato.getCliente().getId_cliente_sap() + ".pdf";
			String sConoceC = "";
			
			if(resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getRegimenFiscal().equals("PERSONA FISICA")){
				sConoceC = pathBase + "DESA_" + this.getUnidadTecSup()+ "//conoceclientef.htm";
			}else if(resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getRegimenFiscal().equals("PERSONA MORAL")){
				sConoceC = pathBase + "DESA_" + this.getUnidadTecSup()+ "//conoceclientem.htm";
			}
			
			// String sFichero = pathReportes
			// +"DESA_"+this.getUnidadTecSup()+"//"+fileName+".jrxml";
			File htmlConoceC = new File(sConoceC);

			String conoceC = "";
			String conoceC_folder = context.getRealPath("/") + "files/"+ conoceC_name;

			if (htmlConoceC.exists()) {
				String rep_html_conoceC = "";

				Document docConoceC = new Document(PageSize.LETTER);

				PdfWriter pdfWriterConoce = PdfWriter.getInstance(docConoceC,new FileOutputStream(conoceC_folder));
				docConoceC.open();
				docConoceC.addAuthor("Grupo Gigante Inmobiliario");
				docConoceC.addCreator("Grupo Gigante Inmobiliario");
				docConoceC.addSubject("Gracias por preferirnos");
				docConoceC.addCreationDate();
				docConoceC.addTitle("Conoce a tu Cliente");
				docConoceC.top((float) 0.0);
				HTMLWorker htmlWorkerConoceC = new HTMLWorker(docConoceC);

				FileInputStream fstreamconoceC = new FileInputStream(sConoceC);

				DataInputStream inConoceC = new DataInputStream(fstreamconoceC);
				BufferedReader brConoceC = new BufferedReader(
						new InputStreamReader(inConoceC, "UTF-8"));
				String strLineConoceC = "";
				String str_html_conoceC = "";

				int ii = 0;
				while ((strLineConoceC = brConoceC.readLine()) != null) {

					if (strLineConoceC.indexOf("|imgconoce" + ii + "|") != -1) {
						try {
							String nom_images[] = strLineConoceC.split(" ");
							Image image = Image.getInstance(pathBase + "DESA_"
									+ this.getUnidadTecSup() + "//imagenes//"
									+ nom_images[1]);
							// image.setAlignment(Image.MIDDLE);
							image.setAbsolutePosition(
									Float.parseFloat(nom_images[2]),
									Float.parseFloat(nom_images[3]));
							image.scalePercent(60);
							docConoceC.add(image);
						} catch (Exception e) {
						}

						ii++;
					} else {
						str_html_conoceC += strLineConoceC;
					}

				}
				
				//Encabezado
						rep_html_conoceC = str_html_conoceC.replace("|dias|", camposFecha[2]);
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|mes|", meses[month]);
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|anio|", camposFecha[0]);
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|numcliente|", resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getId_cliente_sap() == null ? "" : resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getId_cliente_sap());
						str_html_conoceC = rep_html_conoceC;
						
						rep_html_conoceC = str_html_conoceC.replace("|desarrollo|", unidadTecSup);
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|nodepto|", resInfContrato.getEquipo().getEqunr() == null ? "" : resInfContrato.getEquipo().getEqunr());
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|fase|", resInfContrato.getEquipo().getFase() == null ? "" : resInfContrato.getEquipo().getFase());
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|m2|", resInfContrato.getEquipo().getSup_total() <= 0 ? "0"
										: String.valueOf(resInfContrato.getEquipo().getSup_total()));
						str_html_conoceC = rep_html_conoceC;		
				
				if(resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getRegimenFiscal().equals("PERSONA FISICA")){

						rep_html_conoceC = str_html_conoceC.replace("|nomcte|",	
								resInfContrato.getCliente().getNombre1C()
								+" "
								+ resInfContrato.getCliente().getNombre2C());
						
						  str_html_conoceC = rep_html_conoceC;
						  rep_html_conoceC = str_html_conoceC.replace("|apemat|", resInfContrato.getCliente().getApp_matC() == null ? "" : resInfContrato.getCliente().getApp_matC());
					      str_html_conoceC = rep_html_conoceC;
					      rep_html_conoceC = str_html_conoceC.replace("|apepat|", resInfContrato.getCliente().getApp_patC() == null ? "" : resInfContrato.getCliente().getApp_patC());
					      str_html_conoceC = rep_html_conoceC;
					      rep_html_conoceC = str_html_conoceC.replace("|fechnac|", resInfContrato.getCliente().getFch_ncm().equals("00/00/0000") ? "" : resInfContrato.getCliente().getFch_ncm());
					      str_html_conoceC = rep_html_conoceC;
					      rep_html_conoceC = str_html_conoceC.replace("|rfc|", resInfContrato.getCliente().getRfcC() == null ? "" : resInfContrato.getCliente().getRfcC());
					      str_html_conoceC = rep_html_conoceC;
					      rep_html_conoceC = str_html_conoceC.replace("|curp|", resInfContrato.getCliente().getCurpC() == null ? "" : resInfContrato.getCliente().getCurpC());
					      str_html_conoceC = rep_html_conoceC;
					      rep_html_conoceC = str_html_conoceC.replace("|paisnac|", resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getPaisCDesc() == null ? "" : resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getPaisCDesc() .equals("") ? "N/A" : resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getPaisCDesc());
					      str_html_conoceC = rep_html_conoceC;
					 
				}else{
					rep_html_conoceC = str_html_conoceC.replace("|nomcte|",	"");
					str_html_conoceC = rep_html_conoceC;		
					rep_html_conoceC = str_html_conoceC.replace("|apemat|",	"");
					str_html_conoceC = rep_html_conoceC;
					rep_html_conoceC = str_html_conoceC.replace("|apepat|",	"");
					str_html_conoceC = rep_html_conoceC;
					rep_html_conoceC = str_html_conoceC.replace("|fechnac|", "");
					str_html_conoceC = rep_html_conoceC;
					rep_html_conoceC = str_html_conoceC.replace("|rfc|", "");
					str_html_conoceC = rep_html_conoceC;
					rep_html_conoceC = str_html_conoceC.replace("|curp|", "");
					str_html_conoceC = rep_html_conoceC;
					rep_html_conoceC = str_html_conoceC.replace("|nacfisica|", "");
					str_html_conoceC = rep_html_conoceC;
					rep_html_conoceC = str_html_conoceC.replace("|paisnac|", "");
					str_html_conoceC = rep_html_conoceC;
					rep_html_conoceC = str_html_conoceC.replace("|tipoiden|", "");
					str_html_conoceC = rep_html_conoceC;
					rep_html_conoceC = str_html_conoceC.replace("|numiden|", "");
					str_html_conoceC = rep_html_conoceC;
					rep_html_conoceC = str_html_conoceC.replace("|otro|", "");
					str_html_conoceC = rep_html_conoceC;
					rep_html_conoceC = str_html_conoceC.replace("|actividad|", "");
					str_html_conoceC = rep_html_conoceC;
					rep_html_conoceC = str_html_conoceC.replace("|autoridad|", "");
					str_html_conoceC = rep_html_conoceC;
				}	
				//Datos de la Persona Mora
				if(resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getRegimenFiscal().equals("PERSONA MORAL")){		
						rep_html_conoceC = str_html_conoceC.replace("|razsocial|", resInfContrato.getCliente().getNombre1C() == null ? "" : resInfContrato.getCliente().getNombre1C());
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|segnombre|", resInfContrato.getCliente().getNombre2C() == null ? "" : resInfContrato.getCliente().getNombre2C());
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|apepat|", resInfContrato.getCliente().getApp_patC()== null ? "" : resInfContrato.getCliente().getApp_patC());
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|apemat|", resInfContrato.getCliente().getApp_matC() == null ? "" : resInfContrato.getCliente().getApp_matC());
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|rfcmoral|", resInfContrato.getCliente().getRfcC() == null ? "" : resInfContrato.getCliente().getRfcC());
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|fechconst|", resInfContrato.getCliente().getFch_ncm().equals("00/00/0000") ? "" : resInfContrato.getCliente().getFch_ncm());
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|nacmoral|", resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getPaisCDesc() == null ? "" : resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getPaisCDesc());
						str_html_conoceC = rep_html_conoceC;
				}
						
              //datos del Apoderado Legal

				rep_html_conoceC = str_html_conoceC.replace("|nomapoleg|", resGetClientesSapDTO.getClientesSapList().get(0).getContacto().getNombre1Co() == null ? "" : resGetClientesSapDTO.getClientesSapList().get(0).getContacto().getNombre1Co().equals("") ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : resGetClientesSapDTO.getClientesSapList().get(0).getContacto().getNombre1Co() + "&nbsp;");
				str_html_conoceC = rep_html_conoceC;
				rep_html_conoceC = str_html_conoceC.replace("|nom2apoleg|", resGetClientesSapDTO.getClientesSapList().get(0).getContacto().getNombre1Co() == null ? "" : resGetClientesSapDTO.getClientesSapList().get(0).getContacto().getNombre1Co().equals("") ? "" : resGetClientesSapDTO.getClientesSapList().get(0).getContacto().getNombre2Co() + "&nbsp;");
				str_html_conoceC = rep_html_conoceC;
				rep_html_conoceC = str_html_conoceC.replace("|apepatapoleg|", resGetClientesSapDTO.getClientesSapList().get(0).getContacto().getApp_patCo() == null ? "" : resGetClientesSapDTO.getClientesSapList().get(0).getContacto().getApp_patCo());
				str_html_conoceC = rep_html_conoceC;
				rep_html_conoceC = str_html_conoceC.replace("|apematapoleg|", resGetClientesSapDTO.getClientesSapList().get(0).getContacto().getApp_matCo() == null ? "" : resGetClientesSapDTO.getClientesSapList().get(0).getContacto().getApp_matCo());
				str_html_conoceC = rep_html_conoceC;		
				rep_html_conoceC = str_html_conoceC.replace("|fechnacapoleg|", resGetClientesSapDTO.getClientesSapList().get(0).getContacto().getFechaNacContacto().equals("00/00/0000") ? "" : resGetClientesSapDTO.getClientesSapList().get(0).getContacto().getFechaNacContacto());
				str_html_conoceC = rep_html_conoceC;		
				rep_html_conoceC = str_html_conoceC.replace("|rfcapoleg|",	resGetClientesSapDTO.getClientesSapList().get(0).getContacto().getRfcCo() == null ? "" : resGetClientesSapDTO.getClientesSapList().get(0).getContacto().getRfcCo());
				str_html_conoceC = rep_html_conoceC;
				rep_html_conoceC = str_html_conoceC.replace("|curpapoleg|",	resGetClientesSapDTO.getClientesSapList().get(0).getContacto().getCurpCo() == null ? "" : resGetClientesSapDTO.getClientesSapList().get(0).getContacto().getCurpCo());
				str_html_conoceC = rep_html_conoceC;
						
						/*
						rep_html_conoceC = str_html_conoceC.replace("|tipoidenapoleg|", "PASAPORTE");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|numidenapoleg|", "0000000001");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|otro|",	"PEPO880306QM3CURP");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|autoridad|","MEXICANA");
						str_html_conoceC = rep_html_conoceC;
						
			  /*Datos del Fideicomiso			
						rep_html_conoceC = str_html_conoceC.replace("|razsocial|",	"FIDEY S.A de C.V");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|rfcfide|",	"FID34335442");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|numreffide|",	"011010122033");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|numnotaria|",	"15609");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|fechinstrumento|",	"14/10/2009");
						str_html_conoceC = rep_html_conoceC;	
			*/
						
			//Domicilio Comprador
						
						rep_html_conoceC = str_html_conoceC.replace("|clvlada|", resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getTelfnC().substring(0,2));
						str_html_conoceC = rep_html_conoceC;
						String TelfnC = resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getTelfnC();
						rep_html_conoceC = str_html_conoceC.replace("|numtel|",	TelfnC.substring(TelfnC.length()-8));
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|numcel|", resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getTlfmoC() == null ? "N/A" : resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getTlfmoC());
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|correlect|", resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getMail1C() == null ? "N/A" : resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getMail1C());
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|calle|", resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getCalleC() == null ? "N/A" : resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getCalleC());
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|numext|",	resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getNoextC() == null ? "N/A" : resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getNoextC());
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|numint|",	resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getNointC()  == null ? "N/A" : resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getNointC());
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|colonia|", resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getColnC() == null ? "N/A" : resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getColnC());
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|delmpio|", resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getDlmcpC() == null ? "N/A" : resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getDlmcpC());
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|cp|", resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getCdpstC() == null ? "N/A" : resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getCdpstC());
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|entfederativa|", resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getEstdoCDesc() == null ? "N/A" : resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getEstdoCDesc());
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|pais|", resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getPaisCDesc() == null ? "N/A" : resGetClientesSapDTO.getClientesSapList().get(0).getCliente().getPaisCDesc());
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|afirmativo|", "X");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|negativo|",	"X");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|desconoce|",	"X");
						str_html_conoceC = rep_html_conoceC;
						
			//Documentacion Requerida
						//Del cliente o Contratante
						
						rep_html_conoceC = str_html_conoceC.replace("|idenoficialreq|",	"X");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|curpreq|",	"X");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|cedifisreq|",	"X");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|comdomreq|",	"X");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|actconstreq|","X");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|contrarrenreq|","X");
						str_html_conoceC = rep_html_conoceC;
						
						//Apoderado (Representante legal)
						
						rep_html_conoceC = str_html_conoceC.replace("|idenoficialreq|",	"X");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|curprfcreq|",	"X");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|idenoficialreq|","X");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|podnotareq|","X");
						str_html_conoceC = rep_html_conoceC;
						
						
						//Beneficiario Controlador
						
						rep_html_conoceC = str_html_conoceC.replace("|idenoficialreq|",	"X");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|curpreq|",	"X");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|cedifisreq|",	"X");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|comdomreq|",	"X");
						str_html_conoceC = rep_html_conoceC;
						rep_html_conoceC = str_html_conoceC.replace("|actconstreq|","X");
						str_html_conoceC = rep_html_conoceC;



				inConoceC.close();

				htmlWorkerConoceC.parse(new StringReader(str_html_conoceC));
				docConoceC.close();

				InputStream isConoceC = new FileInputStream(new File(conoceC_folder));

				System.out.println("Conoce a tu cliente creada");

				sourceFiles.add(conoceC_folder);
				nombreFiles.add(conoceC_name);

			} else {
				log.info("Archivo de Conoce a tu cliente no existe");
			}
			// response.setHeader("Content-Disposition",
			// "attachment;filename=\"encuestacierre.pdf\"");
			// IOUtils.copy(isenc, response.getOutputStream());
			// response.flushBuffer();

		} catch (Exception ex) {
			log.error(ex.getMessage());
			System.out.print(ex.getMessage());
		}
	}

		//////////////////////////////////// Fin Conoce a tu Cliente ////////////////////////////////////////
	/////////////////////////////////// Zipeo de Contrato ///////////////////////////////////////////////
	private void zipeaDocumentos() {

		try {
			String zipFile = context.getRealPath("/") + "files/ArchivosCliente_" + resInfContrato.getCliente().getId_cliente_sap() + ".zip";

			// create byte buffer
			byte[] buffer = new byte[1024];

			/*
			 * To create a zip file, use
			 * 
			 * ZipOutputStream(OutputStream out) constructor of ZipOutputStream
			 * class.
			 */

			// create object of FileOutputStream
			FileOutputStream fout = new FileOutputStream(zipFile);

			// create object of ZipOutputStream from FileOutputStream
			ZipOutputStream zout = new ZipOutputStream(fout);

			for (int i = 0; i < sourceFiles.size(); i++) {
				try {
					System.out.println("Adding " + sourceFiles.get(i));
					// create object of FileInputStream for source file
					FileInputStream fin = new FileInputStream((String)sourceFiles.get(i));

					/*
					 * To begin writing ZipEntry in the zip file, use
					 * 
					 * void putNextEntry(ZipEntry entry) method of
					 * ZipOutputStream class.
					 * 
					 * This method begins writing a new Zip entry to the zip
					 * file and positions the stream to the start of the entry
					 * data.
					 */

					zout.putNextEntry(new ZipEntry((String)nombreFiles.get(i)));

					/*
					 * After creating entry in the zip file, actually write the
					 * file.
					 */
					int length;

					while ((length = fin.read(buffer)) > 0) {
						zout.write(buffer, 0, length);
					}

					/*
					 * After writing the file to ZipOutputStream, use
					 * 
					 * void closeEntry() method of ZipOutputStream class to
					 * close the current entry and position the stream to write
					 * the next entry.
					 */

					zout.closeEntry();

					// close the InputStream
					fin.close();

					File pdf_tmp = new File((String)sourceFiles.get(i));
					pdf_tmp.deleteOnExit();
					pdf_tmp.setWritable(true);
					pdf_tmp.getCanonicalFile();

					if (pdf_tmp.exists()) {
						if (pdf_tmp.delete()) {
							System.out.println("Archivo borrado");
						} else {
							System.out.println("Error al borrar el archivo");
							log.error("Error al borrar el archivo");
						}
					} else {
						System.out.println("Archivo no existe");
						log.error("Archivo no existe");
					}
				} catch (Exception e) {
					log.error("No se pudo generar archivo por que no existe");
				}

			}

			// close the ZipOutputStream
			zout.close();
			zout.flush();

			File zip_tmp = new File(zipFile);
			InputStream iszip = new FileInputStream(zip_tmp);

			response.setHeader("Content-Disposition", "attachment;filename=\"CLIENTE_"
							+ resInfContrato.getCliente().getId_cliente_sap()
							+ ".zip\"");
			IOUtils.copy(iszip, response.getOutputStream());
			response.flushBuffer();
			System.out.println("Zip file has been created!");
			zip_tmp.setWritable(true);
			zip_tmp.getCanonicalFile();

			if (zip_tmp.delete())
				System.out.println("Zip eliminado");
			else
				System.out.println("Error al borrar el zip");

			nombreFiles.clear();
			sourceFiles.clear();
		} catch (IOException ioe) {
			log.error("IOException :" + ioe);
		}

	}

	private double TruncarMonto(Double nD, int nDec) {
		if (nD > 0)
			nD = Math.floor(nD * Math.pow(10, nDec)) / Math.pow(10, nDec);
		else
			nD = Math.ceil(nD * Math.pow(10, nDec)) / Math.pow(10, nDec);

		return nD;
	}

	public static boolean desplazar(File source, File destination) {
		if (!destination.exists()) {
			// intentamos con renameTo
			boolean result = source.renameTo(destination);
			if (!result) {
				// intentamos copiar
				result = true;
				result &= copiar(source, destination);
				// result &= source.delete();
			}
			return (result);
		} else {
			// Si el fichero destination existe, cancelamos...
			return (false);
		}
	}

	/**
	 * copia el fichero source en el fichero destination devuelve true si
	 * funciona correctamente
	 */
	public static boolean copiar(File source, File destination) {
		boolean resultado = false;
		// declaración del flujo
		java.io.FileInputStream sourceFile = null;
		java.io.FileOutputStream destinationFile = null;
		try {
			// creamos fichero
			destination.createNewFile();
			// abrimos flujo
			sourceFile = new java.io.FileInputStream(source);
			destinationFile = new java.io.FileOutputStream(destination);
			// lectura por segmentos de 0.5Mb
			byte buffer[] = new byte[512 * 1024];
			int nbLectura;
			while ((nbLectura = sourceFile.read(buffer)) != -1) {
				destinationFile.write(buffer, 0, nbLectura);
			}
			// copia correcta
			resultado = true;
		} catch (java.io.FileNotFoundException f) {
		} catch (java.io.IOException e) {
		} finally {
			// pase lo que pase, cerramos flujo
			try {
				sourceFile.close();
			} catch (Exception e) {
			}
			try {
				destinationFile.close();
			} catch (Exception e) {
			}
		}
		return (resultado);
	}

	private JasperReport getCompiled(String fileName) throws JRException,
	ViviendaException {
		// /String sFichero =
		// "C:\\GESTION_VIVIENDA_REPORTES\\DESA_5005PSL\\"+fileName + ".jrxml";
		// LoadFileProperties lf = new LoadFileProperties();
		// String pathReportes = lf.getPropertiesFile("vivienda.reportes");
		String sFichero = recursos
				.getValorPropiedad("ruta.reportes.gestion.vivienda")
				+ "DESA_"
				+ this.getUnidadTecSup() + "//" + fileName + ".jrxml";
		// String sFichero = pathReportes
		// +"DESA_"+this.getUnidadTecSup()+"//"+fileName+".jrxml";
		File fichero = new File(sFichero);
		log.info("Ruta del reporte es: " + sFichero);

		InputStream is = null;
		try {
			if (buscaArchivo(fichero)) {
				is = new BufferedInputStream(new FileInputStream(fichero));
			} else {
				log.info("El archivo no existe");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		JasperReport jasperReport = (JasperReport) JasperCompileManager
				.compileReport(is);
		return jasperReport;
	}

	private boolean buscaArchivo(File file) {
		if (file.exists()) {
			return true;
		} else {
			return false;
		}

	}

	private String[] logoBanco(String tipoBanco) throws ViviendaException {
		log.info("logoBanco:" + tipoBanco);
		String datos[];
		datos = new String[2];
		String sFichero = new String();
		File fichero = null;
		log.info(tipoBanco + ", bnmx:"
				+ tipoBanco.trim().toLowerCase().indexOf("banmx")
				+ ", banamex:"
				+ tipoBanco.trim().toLowerCase().indexOf("bananmex"));
		log.info(tipoBanco + ", serfi:"
				+ tipoBanco.trim().toLowerCase().indexOf("serfi")
				+ ", santander:"
				+ tipoBanco.trim().toLowerCase().indexOf("santander"));
		if (tipoBanco.trim().toLowerCase().indexOf("banmx") > -1
				|| tipoBanco.trim().toLowerCase().indexOf("bananmex") > -1) {
			// sFichero =
			// "C:\\GESTION_VIVIENDA_REPORTES\\LOGOS_BANCO\\banamexlog.png";
			sFichero = recursos
					.getValorPropiedad("ruta.imagenes.gestion.vivienda")
					+ "banamexlog.png";
			// LoadFileProperties lf = new LoadFileProperties();
			// String pathImgReportes = lf.getPropertiesFile("vivienda.logosb");

			// sFichero = pathImgReportes + "banamexlog.png";
			log.info("banamex es el banco seleccionado:" + sFichero);

			fichero = new File(sFichero);
			if (!buscaArchivo(fichero)) {
				sFichero = "SIN LOGO";
			}
			datos[0] = "01"; // BANAMEX
			datos[1] = sFichero; // BANAMEX
		} else if (tipoBanco.trim().toLowerCase().indexOf("serfi") > -1
				|| tipoBanco.trim().toLowerCase().indexOf("santander") > -1) {
			// sFichero =
			// "C:\\GESTION_VIVIENDA_REPORTES\\LOGOS_BANCO\\santanderlog.png";
			sFichero = recursos
					.getValorPropiedad("ruta.imagenes.gestion.vivienda")
					+ "santanderlog.png";
			// LoadFileProperties lf = new LoadFileProperties();
			// String pathImgReportes = lf.getPropertiesFile("vivienda.logosb");
			// sFichero = pathImgReportes + "santanderlog.png";
			// sFichero =
			// recursos.getValorPropiedad("ruta.imagenes.gestion.vivienda") +
			// "santanderlog.png";
			log.info("santander es el banco seleccionado:" + sFichero);
			fichero = new File(sFichero);
			if (!buscaArchivo(fichero)) {
				sFichero = "SIN LOGO";
			}
			datos[0] = "02"; // SANTANDER
			datos[1] = sFichero; // SANTANDER
		} else {
			log.info("no hay banco seleccionado");
			datos[0] = "00"; // BANCO NO VALIDO
			datos[1] = "N/V"; // URL NO VALIDA
		}

		return datos;
	}

	private String DeptoCasa(String param) {
		String hogar = param.substring(0, 1);
		if (hogar.trim().equals("D")) {
			return "DEPARTAMENTO";
		} else {
			if (hogar.trim().equals("D")) {
				return "CASA";
			} else {
				return "";
			}
		}
	}

	public String getUnidadTecSup() {
		return unidadTecSup;
	}

	public void setUnidadTecSup(String unidadTecSup) {
		this.unidadTecSup = unidadTecSup;
	}

}