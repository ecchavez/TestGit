/**
 * @author Osvaldo Rodriguez Martinez / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 17/07/2012               
 */
package mx.com.grupogigante.gestionvivienda.controllers.digitalizacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.com.grupogigante.gestionvivienda.domain.dto.BoletinDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUploadFilesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.CriteriosDatosDigitalizacionImageDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.ResponseDatosDigitActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.ResponseUploadFilesDigitDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosFileUploadDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.resources.ArchivoPropiedades;
import mx.com.grupogigante.gestionvivienda.services.IUploadFilesService;
import mx.com.grupogigante.gestionvivienda.utils.SessionValidatorSTS;

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
import org.springframework.web.multipart.MultipartFile;

/**
 * Clase controladora para el modulo de Cartera de Clientes 
 * Fecha de creación: 17/07/2012               
 */
@Controller
public class BoletinController{
	private Logger log = Logger.getLogger(BoletinController.class);
    ArchivoPropiedades recursos = null;
	
	@Autowired
	IUploadFilesService uploadFiles;
	
	@RequestMapping(value = "/RegistroBoletin.htm", method = RequestMethod.GET)
	public String filtroBusquedaReporteView (Model model,HttpSession session) {
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		return !validaSesion.validaSesion(session)? "../../index":"/RegistroBoletin"; 
	}
	
	@RequestMapping(value="/ConsultaBoletines.htm",method=RequestMethod.POST)
	public @ResponseBody List<BoletinDto> consultaBoletines
				(@RequestParam(value="uts") String uts, 
				 ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, HttpSession session){
				 SessionValidatorSTS sesionValida = new SessionValidatorSTS();
				 CriteriosFileUploadDto criteriosUpload = new CriteriosFileUploadDto();
			     ResponseDatosDigitActionDto resp = new ResponseDatosDigitActionDto();
			     List<BoletinDto> listaBoletines =  new ArrayList<BoletinDto>();
				
		try {
			
			String udt = sesionValida.getDatos(session,"unidad");
			recursos = new ArchivoPropiedades(request);
			String name = recursos.getValorPropiedad("ruta.reportes.gestion.vivienda")
					+ "DESA_" + udt + "//boletines";
			criteriosUpload.setName(name);
			listaBoletines = uploadFiles.viewContentFolder(criteriosUpload);
			
		} catch (ViviendaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//BoletinDto dto = new BoletinDto();
		//dto.setNombreArchivo("boletinMiyana_Marzo2014");
		//dto.setRutaArchivo("C:\\home\\jboss\\GestionVivienda\\GESTION_VIVIENDA_REPORTES\\DESA_5900MYA\\boletines");
		//listaBoletines.add(dto);
		
		return listaBoletines;		
	}
	
	@RequestMapping(value="/RespuestaBoletinFile.htm", method = RequestMethod.POST)
	public String setGuardaDigitalizacionFile(@ModelAttribute(value="criterios")CriteriosDatosDigitalizacionImageDto criterios, BindingResult result, HttpServletRequest request, HttpServletResponse response, HttpSession session, Map model) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    CriteriosFileUploadDto criteriosUpload = new CriteriosFileUploadDto();
		    ResponseDatosDigitActionDto resp = new ResponseDatosDigitActionDto();
			ResponseUploadFilesDto responseUpload = new ResponseUploadFilesDto();
		    
			criterios.setIdUTS(sesionValida.getDatos(session,"unidad"));
			criterios.setId_usuario(sesionValida.getDatos(session,"usuario"));

			if(request.getParameter("accion").equals("guardar"))
			{
				
					criteriosUpload.setFileData(criterios.getFileData());
					
					MultipartFile file = criterios.getFileData();
					String fileName=file.getOriginalFilename();
					String ext="";
					int dotPos = fileName.lastIndexOf(".");
					ext = fileName.substring(dotPos);
					recursos = new ArchivoPropiedades(request);
					
					String name = recursos.getValorPropiedad("ruta.reportes.gestion.vivienda")
							+ "DESA_" + criterios.getIdUTS() + "//boletines//boletin" + ext.toLowerCase();
					criteriosUpload.setName(name);
					try {						
						responseUpload = uploadFiles.setUploadImageMaping(criteriosUpload);
					} catch (ViviendaException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
										
				//resp.setRespDatosUploadDigitFile(respUpdateDigit);
			}
			else if(request.getParameter("accion").equals("eliminar"))
			{
				String ext = ".jpg";
				String name = recursos.getValorPropiedad("ruta.reportes.gestion.vivienda")
						+ "DESA_" + criterios.getIdUTS() + "//boletines//boletin" + ext;
				criteriosUpload.setName(name);
				try {						
					responseUpload = uploadFiles.deleteContentFolder(criteriosUpload);
				} catch (ViviendaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}			
			//resp.setRespDatosUploadDigitFile(respUpdateDigit);
					model.put("responseBoletin", responseUpload.getDescripcion());
			
		return "RegistroBoletin";
	}
	
	/*
	@RequestMapping(value="/RespuestaBoletinFile.htm", method= RequestMethod.GET)
	public String returnMapeoImagenes(ModelMap map, HttpSession session) {
		return "RespuestaDigitalizacionFile";
	}*/
	
    /**
     * Método para obtener un documento del file system warehouse de la 
     * aplicación.
     * @param cveDocumento
     * @param modelMap
     * @param request
     * @param response
     */
    @RequestMapping(value = "/obtenerBoletin.htm", method = RequestMethod.GET)
    public String obtenerDocumento(
    		@RequestParam("nombreArchivo") String nombreArchivo,
    		ModelMap modelMap,HttpServletRequest request, HttpServletResponse response, HttpSession session, Map model) {
		log.info("Ejecucion del metodo obtenerDocumento");
		
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
	    CriteriosFileUploadDto criteriosUpload = new CriteriosFileUploadDto();
	    ResponseDatosDigitActionDto resp = new ResponseDatosDigitActionDto();
	    CriteriosDatosDigitalizacionImageDto criterios = new CriteriosDatosDigitalizacionImageDto();
	    ResponseUploadFilesDto responseUpload = new ResponseUploadFilesDto();
	    
		criterios.setIdUTS(sesionValida.getDatos(session,"unidad"));
		criterios.setId_usuario(sesionValida.getDatos(session,"usuario"));
		
		String pathFileSystem = 
				recursos.getValorPropiedad("ruta.reportes.gestion.vivienda")
				+ "DESA_" + criterios.getIdUTS() + "//boletines//";
		
		//MultipartFile mpFile = 

		try {					
			String sFile = pathFileSystem + nombreArchivo;
			File file = new File(sFile);
		   
			if(file.exists()){				   
			    log.info("File location on server::"+file.getAbsolutePath());
		        InputStream fis = new FileInputStream(file);
		        response.setContentType("image/jpg");
		        response.setContentLength((int) file.length());
		        response.setHeader("Content-Disposition", "inline; filename=\"" + nombreArchivo + "\"");
		        
		        ServletOutputStream os = response.getOutputStream();
		        byte[] bufferData = new byte[1024];
		        int read=0;
		        while((read = fis.read(bufferData))!= -1){
		            os.write(bufferData, 0, read);
		        }
				os.flush();
				os.close();
				fis.close();
				responseUpload.setDescripcion("Documento mostrado");
			}else{
				responseUpload.setDescripcion("No existe el documento");
			   //request.setAttribute("result_error", "No existe el documento con nombre " + nombreArchivo);
			}
		} catch (Exception e) {
				responseUpload.setDescripcion("Problema para obtener el documento");
			   //request.setAttribute("result_error", "Problema para obtener el documento con nombre " + nombreArchivo);
		}
		model.put("responseBoletin", responseUpload.getDescripcion());
		return "RegistroBoletin";
    } 

}
