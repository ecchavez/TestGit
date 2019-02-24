/**
 * @author Osvaldo Rodriguez Martinez / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 17/07/2012               
 */
package mx.com.grupogigante.gestionvivienda.controllers.digitalizacion;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.com.grupogigante.gestionvivienda.dao.DigitalizacionDaoImp;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosDatosMapaImagenDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosEquipoTiposDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosFileUploadDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosUbicacionesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.EqCaracteristicasDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.EquipoDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.EquiposAdicionalesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.PermisoUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseDatosMapingActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseFileConnect;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetUtInfSupDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionDatosMapaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionDatosMapaOutDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionTecnicaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionesActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUploadFilesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.UbicacionTecnicaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.CriteriosDatosDigitalizacionImageDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.CriteriosMultipleFileUpload;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.ResponseDatosDigitActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.ResponseUploadFilesDigitDto;
import mx.com.grupogigante.gestionvivienda.domain.vo.EquipoDatosMapaImagenVo;
import mx.com.grupogigante.gestionvivienda.domain.vo.Fizzle;
import mx.com.grupogigante.gestionvivienda.domain.vo.UbicacionTecnicaDatosMapaImagenVo;
import mx.com.grupogigante.gestionvivienda.exceptions.DaoException;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.resources.UploadItem;
import mx.com.grupogigante.gestionvivienda.services.ConexionService;
import mx.com.grupogigante.gestionvivienda.services.DigitalizacionService;
import mx.com.grupogigante.gestionvivienda.services.IConexionManagerService;
import mx.com.grupogigante.gestionvivienda.services.IDigitalizacionService;
import mx.com.grupogigante.gestionvivienda.services.IUbicacionesService;
import mx.com.grupogigante.gestionvivienda.services.IUploadFilesService;
import mx.com.grupogigante.gestionvivienda.services.UbicacionesService;
import mx.com.grupogigante.gestionvivienda.services.UploadFilesService;
import mx.com.grupogigante.gestionvivienda.utils.SessionValidatorSTS;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.sql.Blob;
import com.sap.conn.jco.JCoTable;

/**
 * Clase controladora para el modulo de Cartera de Clientes Fecha de creación:
 * 17/07/2012
 */
@Controller
public class DigitalizacionController implements HandlerExceptionResolver {
	private Logger log = Logger.getLogger(DigitalizacionController.class);
	@Autowired
	IUbicacionesService ubicaciones;
	@Autowired
	IConexionManagerService datosImageMap;
	@Autowired
	IUploadFilesService uploadFilesService;
	@Autowired
	IDigitalizacionService digitService;

	@RequestMapping(value = "/Digitalizacion.htm", method = RequestMethod.GET)
	public String returnArbolMap(ModelMap map, HttpSession session) {
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();
		String redir = "";
		if (!validaSesion.validaSesion(session)) {
			redir = "../../index";
		} else {
			redir = "Digitalizacion";
		}
		return redir;
	}

	@RequestMapping(value = "/Digitalizacion.htm", method = RequestMethod.POST)
	public @ResponseBody
	ResponseDatosDigitActionDto getArbolMap(
			@ModelAttribute(value = "arbolMap") CriteriosDatosDigitalizacionImageDto criterios,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ResponseDatosDigitActionDto resAction = new ResponseDatosDigitActionDto();
		ResponseUploadFilesDigitDto respValidaDigit = new ResponseUploadFilesDigitDto();
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();

		if (!sesionValida.validaSesion(session)) {
			respValidaDigit.setMensaje("LOGOUT");
			respValidaDigit.setDescripcion("");
			resAction.setRespDatosUploadDigitFile(respValidaDigit);
			return resAction;
		}

		if (criterios.getAccion().equals("getpdf")) {
			try {
				respValidaDigit = datosImageMap.validaExisteDigit(criterios);
				if (respValidaDigit.getImagesDigit().size() > 0) {
					try {
						for (int i = 0; i < respValidaDigit.getImagesDigit()
								.size(); i++) {
							respValidaDigit.getImagesDigit().get(i)
									.setBlobImage(null);
							ServletContext context = RequestContextUtils
									.getWebApplicationContext(request)
									.getServletContext();
							String nombre_archivo = respValidaDigit
									.getImagesDigit().get(i).getFile_nombre();
							String url_archivo = context.getRealPath("/")
									+ "files/" + nombre_archivo;
							FileOutputStream outputStream = new FileOutputStream(
									url_archivo);
							int readBytes = 0;
							// int lengthBytes = (int)bdblob.length();
							byte[] buffer = respValidaDigit.getImagesDigit()
									.get(i).getIsImage();
							// while ((readBytes = isImage.read(buffer, 0,
							// lengthBytes)) != -1) {
							outputStream.write(buffer);
							// }
							outputStream.close();
							respValidaDigit.getImagesDigit().get(i)
									.setIsImage(null);
						}
					} catch (Exception e) {
						respValidaDigit.setMensaje("FAULT");
						respValidaDigit.setDescripcion(e.getMessage());
					}
				}

			} catch (ViviendaException e) {
				respValidaDigit.setMensaje("FAULT");
				respValidaDigit.setDescripcion(e.getMessage());
			}

			resAction.setRespDatosUploadDigitFile(respValidaDigit);
		}
		return resAction;
	}

	@RequestMapping(value = "/DigitalizacionFiles.htm", method = RequestMethod.GET)
	public String getDigitGuardaFile(ModelMap map, HttpSession session) {
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();
		String redir = "";
		if (!validaSesion.validaSesion(session)) {
			redir = "../../index";
		} else {
			redir = "DigitalizacionFiles";
		}
		return redir;
	}

	@RequestMapping(value = "/DigitalizacionFiles.htm", method = RequestMethod.POST)
	public @ResponseBody
	ResponseUbicacionesActionDto postDigitGuardaFile(
			@ModelAttribute(value = "arbolMap") CriteriosUbicacionesDto criterios,
			BindingResult result, HttpSession session) {
		ResponseUbicacionesActionDto resAction = new ResponseUbicacionesActionDto();
		ResponseGetUtInfSupDto resUTS = new ResponseGetUtInfSupDto();

		SessionValidatorSTS sesionValida = new SessionValidatorSTS();

		if (!sesionValida.validaSesion(session)) {
			resUTS.setMensaje("LOGOUT");
			resUTS.setDescripcion("");
			resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
			return resAction;
		}

		criterios.setI_usuario(sesionValida.getDatos(session, "usuario"));
		criterios.setI_id_ut_sup(sesionValida.getDatos(session, "unidad"));

		try {
			if (criterios.getAccion().equals("faces")) {
				criterios.setI_charact("");
				criterios.setI_deep_srch("");
				criterios.setI_equnr_price("");
				criterios.setI_id_ut_current(sesionValida.getDatos(session,
						"unidad"));
				resUTS = ubicaciones.getUtInfSup(criterios);
				if (resUTS.getMensaje().equals("SUCCESS")) {
					resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
				}
			} else if (criterios.getAccion().equals("equipos")) {
				criterios.setI_charact("");
				criterios.setI_deep_srch("X");
				criterios.setI_equnr_price("");
				criterios.setI_digit_areas("X");
				resUTS = ubicaciones.getUtInfSup(criterios);
				if (resUTS.getMensaje().equals("SUCCESS")) {
					resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
				}
			} else if (criterios.getAccion().equals("guardar")) {

			}
		} catch (ViviendaException e) {
			e.getMensajeError();
			resUTS.setMensaje("FAULT");
			resUTS.setMensaje(e.getMensajeError());
			resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
		}

		return resAction;
	}

	@RequestMapping(value = "/RespuestaDigitalizacionFile.htm", method = RequestMethod.POST)
	public String setGuardaDigitalizacionFile(
			@ModelAttribute(value = "criterios") CriteriosDatosDigitalizacionImageDto criterios,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Map model) {
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		ResponseUploadFilesDigitDto respValidaDigit = new ResponseUploadFilesDigitDto();
		ResponseUploadFilesDigitDto respInsertDigit = new ResponseUploadFilesDigitDto();
		ResponseUploadFilesDigitDto respUpdateDigit = new ResponseUploadFilesDigitDto();
		ResponseUploadFilesDigitDto respContDigit = new ResponseUploadFilesDigitDto();
		ResponseUploadFilesDigitDto respAddDigit = new ResponseUploadFilesDigitDto();
		ResponseDatosDigitActionDto resp = new ResponseDatosDigitActionDto();

		criterios.setIdUTS(sesionValida.getDatos(session, "unidad"));
		criterios.setId_usuario(sesionValida.getDatos(session, "usuario"));

		String datos[] = request.getParameter("datos").split("\\|");

		// $("#cmb_faces").val()+"|"+$("#cmb_equipo").val()+"|"+itemTipos.id_ticket_area+"|"+itemTipos.id_ticket_file+"|"+itemSubTipos.id_ticket_area+"|"+itemSubTipos.id_ticket_file+"|"+itemEstatus.id_ticket_stat+"|"+itemEstatus.id_ticket_statx

		criterios.setFase(datos[0]);
		criterios.setEquipo(datos[1]);
		criterios.setTipo(datos[2]);
		criterios.setTipoa(datos[3]);
		criterios.setSubtipo(datos[4]);
		criterios.setSubtipoa(datos[5]);
		criterios.setEstatus(datos[6].trim());

		MultipartFile file = criterios.getFileData();

		if (request.getParameter("accion").equals("guardar")) {

			try {
				String fileName = file.getOriginalFilename();
				String ext = "";
				int dotPos = fileName.lastIndexOf(".");
				ext = fileName.substring(dotPos);

				criterios.setImagenMaping(file);

				respValidaDigit = datosImageMap.validaExisteDigit(criterios);

				// if(respValidaDigit.getImagesDigit().size()==0)
				// {
				respContDigit = datosImageMap.getContador(criterios);

				if (respContDigit.getMensaje().equals("SUCCESS")) {
					try {
						criterios.setConsecutivo(""
								+ (respContDigit.getDatosContador().get(0)
										.getContador_consecutivo() + 1));
					} catch (Exception e) {
						criterios.setConsecutivo("1");
					}
					String consec_ = criterios.getConsecutivo();
					if (consec_.length() == 1) {
						consec_ = "0" + consec_;
					}
					criterios.setNombreImagen(criterios.getEquipo() + "_"
							+ criterios.getSubtipo() + "_"
							+ criterios.getSubtipoa() + "_" + consec_
							+ ext.toLowerCase());

					respInsertDigit = datosImageMap.setDigitFiles(criterios);

					if (respInsertDigit.getMensaje().equals("SUCCESS")) {
						respInsertDigit.setMensaje("SUCCESS");
						respInsertDigit
								.setDescripcion("El archivo fue guardado satisfactoriamente");
						respAddDigit = digitService.setDataDigit(criterios);

						// Si es igual a tipo "ENTREGA PRODUCTO"
						if (criterios.getTipo().equals("05")) {
							respInsertDigit = datosImageMap
									.setFechaGarantia(criterios);
							if (!respInsertDigit.getMensaje().equals("SUCCESS")) {
								respAddDigit.setMensaje("FAULT");
								respAddDigit
										.setDescripcion("Error al registrar la echa de garantia");
							}
						}
					}
				} else {
					respInsertDigit.setMensaje("FAULT");
					respInsertDigit
							.setDescripcion("Hubo un error en la actualizacion del contador.");
				}
				resp.setRespDatosUploadDigitFile(respInsertDigit);
				// }
				// else
				// {

				// criterios.setNombreImagen(respValidaDigit.getImagesDigit().get(0).getFile_nombre());
				// respContDigit=datosImageMap.getContador(criterios);

				// respUpdateDigit=datosImageMap.setUpdateDigit(criterios);

				// if(respUpdateDigit.getMensaje().equals("SUCCESS"))
				// {
				// respUpdateDigit.setMensaje("SUCCESS");
				// respUpdateDigit.setDescripcion("Es alrchivo fue actualizado satisfactoriamente");
				// }
				// resp.setRespDatosUploadDigitFile(respUpdateDigit);
				// }

			} catch (ViviendaException e) {
				respUpdateDigit.setMensaje("FAULT");
				respUpdateDigit.setDescripcion(e.getMessage());
				resp.setRespDatosUploadDigitFile(respUpdateDigit);
			}

		} else if (request.getParameter("accion").equals("getpdf")) {

		}
		// resp.setRespDatosUploadDigitFile(respUpdateDigit);
		model.put("responseDatosDigit", resp);

		return "RespuestaDigitalizacionFile";
	}

	@RequestMapping(value = "/RespuestaDigitalizacionFile.htm", method = RequestMethod.GET)
	public String returnMapeoImagenes(ModelMap map, HttpSession session) {
		return "RespuestaDigitalizacionFile";
	}

	@RequestMapping(value = "/RespuestaMultipleFileUpload.htm", method = RequestMethod.POST)
	public String setGuardaMultipleFiles(
			@ModelAttribute(value = "archivosUpload") CriteriosMultipleFileUpload criterios,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Map model) {
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		ResponseUploadFilesDigitDto respMultipleFiles = new ResponseUploadFilesDigitDto();
		ResponseDatosDigitActionDto resp = new ResponseDatosDigitActionDto();

		// criterios.setIdUTS(sesionValida.getDatos(session,"unidad"));
		// criterios.setId_usuario(sesionValida.getDatos(session,"usuario"));

		// String datos []= request.getParameter("datos").split("\\|");

		/*
		 * criterios.setFase(datos[0]); criterios.setEquipo(datos[1]);
		 * criterios.setTipo(datos[2]); criterios.setTipoa(datos[3]);
		 * criterios.setSubtipo(datos[4]); criterios.setSubtipoa(datos[5]);
		 * criterios.setEstatus(datos[6].trim());
		 */

		if (request.getParameter("accion").equals("guardar")) {
			List<CommonsMultipartFile> files = criterios.getUploaded_files();
			// System.out.println("Archivos: "+files.size());
			/*
			 * try { MultipartFile file = criterios.getFileData(); String
			 * fileName=file.getOriginalFilename(); String ext=""; int dotPos =
			 * fileName.lastIndexOf("."); ext = fileName.substring(dotPos);
			 * 
			 * criterios.setImagenMaping(file);
			 * 
			 * respMultipleFiles=datosImageMap.validaExisteDigit(criterios);
			 * 
			 * 
			 * if(respMultipleFiles.getMensaje().equals("SUCCESS")) {
			 * 
			 * } else { respMultipleFiles.setMensaje("FAULT");
			 * respMultipleFiles.
			 * setDescripcion("Hubo un error en la actualizacion del contador."
			 * ); } resp.setRespDatosUploadDigitFile(respMultipleFiles);
			 * 
			 * } catch (ViviendaException e) {
			 * respMultipleFiles.setMensaje("FAULT");
			 * respMultipleFiles.setDescripcion(e.getMessage());
			 * resp.setRespDatosUploadDigitFile(respMultipleFiles); }
			 */
		}
		model.put("responseDatosDigit", resp);

		return "RespuestaMultipleFileUpload";
	}

	@RequestMapping(value = "/RespuestaMultipleFileUpload.htm", method = RequestMethod.GET)
	public String getGuardaMultipleFiles(ModelMap map, HttpSession session) {
		return "RespuestaMultipleFileUpload";
	}

	@RequestMapping(value = "/UploadMultipleFileDocuments.htm", method = RequestMethod.POST)
	public String postUploadMFD(
			@ModelAttribute(value = "archivosUpload") CriteriosMultipleFileUpload criterios,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Map model) {

		return "UploadMultipleFileDocuments";
	}

	@RequestMapping(value = "/UploadMultipleFileDocuments.htm", method = RequestMethod.GET)
	public String getUploadMFD(ModelMap map, HttpSession session) {
		return "UploadMultipleFileDocuments";
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception exception) {
		Map<Object, Object> model = new HashMap<Object, Object>();
		if (exception instanceof MaxUploadSizeExceededException) {
			// model.put("errors",
			// "El Archivo no puede ser guardado, revasa el limite permitido de byte.");
			model.put(
					"errors",
					"El Archivo no puede ser guardado, sobrepasa el limite permitido "
							+ ((MaxUploadSizeExceededException) exception)
									.getMaxUploadSize() + " byte.");
			System.out.println("if");
			// request.setAttribute("errors",
			// "El Archivo no puede ser guardado, revasa el limite permitido de byte.");
		}

		// model.put("criterios", new ResponseUploadFilesDigitDto());
		return new ModelAndView("/DigitalizacionFiles", (Map) model);

	}
}
