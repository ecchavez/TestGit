/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 17/07/2012               
 */
package mx.com.grupogigante.gestionvivienda.controllers.ubicaciones;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.com.grupogigante.gestionvivienda.controllers.simulador.SimuladorController;
import mx.com.grupogigante.gestionvivienda.domain.dto.CarpetasDigitDto;
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
import mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.CriteriosDatosDigitalizacionImageDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.ResponseDatosDigitActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.ResponseUploadFilesDigitDto;
import mx.com.grupogigante.gestionvivienda.domain.vo.EquipoDatosMapaImagenVo;
import mx.com.grupogigante.gestionvivienda.domain.vo.Fizzle;
import mx.com.grupogigante.gestionvivienda.domain.vo.UbicacionTecnicaDatosMapaImagenVo;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.resources.UploadItem;
import mx.com.grupogigante.gestionvivienda.services.ConexionService;
import mx.com.grupogigante.gestionvivienda.services.DigitalizacionService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.sql.Blob;
import com.sap.conn.jco.JCoTable;

/**
 * Clase controladora para el modulo de Cartera de Clientes 
 * Fecha de creación: 17/07/2012               
 */
@Controller
public class UbicacionesController {
	private Logger log = Logger.getLogger(UbicacionesController.class);
	
	@Autowired
	UbicacionesService ubicaciones;
	@Autowired
	ConexionService datosImageMap;
	@Autowired
	UploadFilesService uploadFilesService;
	
	@RequestMapping(value="/Ubicaciones.htm", method= RequestMethod.GET)
	public String returnForm(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "Ubicaciones";
		 }
		 return redir;
	 }

	
	@RequestMapping(value="/Ubicaciones.htm",method=RequestMethod.POST)
	public @ResponseBody ResponseUbicacionesActionDto getUnidatTecnicaSuperior(@ModelAttribute(value="ubicacionesView")CriteriosUbicacionesDto criterios, BindingResult result, HttpSession session){
		ResponseUbicacionesActionDto resAction = new ResponseUbicacionesActionDto();
		ResponseGetUtInfSupDto resUTS = new ResponseGetUtInfSupDto();
		ResponseGetUtInfSupDto resUTSaux = new ResponseGetUtInfSupDto();
		
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		criterios.setI_usuario(sesionValida.getDatos(session,"usuario"));
		criterios.setI_id_ut_sup(sesionValida.getDatos(session,"unidad"));

		try
		{
			if(criterios.getAccion().equals("faces"))
			{
				criterios.setI_charact("");
				criterios.setI_deep_srch("");
				criterios.setI_equnr_price("");
				criterios.setI_id_ut_current(sesionValida.getDatos(session,"unidad"));
				resUTS = ubicaciones.getUtInfSup(criterios);
				
				if(resUTS.getMensaje().equals("SUCCESS"))
				{					
					resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
				}
			}
			else if (criterios.getAccion().equals("pisos"))
			{
				criterios.setI_charact("X");
				criterios.setI_deep_srch("X");
				criterios.setI_equnr_price("X");
				resUTS = ubicaciones.getUtInfSup(criterios);
				if(resUTS.getMensaje().equals("SUCCESS"))
				{
					resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
				}				
			}			
			else if (criterios.getAccion().equals("arbol"))
			{
				boolean flag = false;
				
				criterios.setI_charact("");
				criterios.setI_deep_srch("X");
				criterios.setI_equnr_price("");
				criterios.setI_digit_areas("X");
				criterios.setI_id_ut_current(sesionValida.getDatos(session,"unidad"));
				
				//Permisos para visualización carpeta LALDD
				UsuarioDto usuario = new UsuarioDto();
				usuario = (UsuarioDto)session.getAttribute("usrSession");
				
				for(int i=1;usuario.getPermisosUserList().size()>i; i++){
					//40 Id de permiso carpeta Digitalización
					if(usuario.getPermisosUserList().get(i).getId_permiso().equals("40")){
						flag = true;
						break;
					} 
				}
						
				resUTS = ubicaciones.getUtInfSup(criterios);
				if(resUTS.getMensaje().equals("SUCCESS"))
				{
					if(flag){
						Iterator<CarpetasDigitDto> nombreIterator = resUTS.getCarpetasDigitalizacion().iterator();
						while(nombreIterator.hasNext()){
							CarpetasDigitDto elemento = nombreIterator.next();
							//40 Id de carpeta Digitalización
							if(elemento.getId_ticket_area().equals("07") || (elemento.getId_ticket_area().equals("02") && elemento.getId_ticket_file().equals("00")) ||
									(elemento.getId_ticket_area().equals("02") && elemento.getId_ticket_file().equals("03"))){
									
							}else{
								nombreIterator.remove();
							}
						}
						resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
					}else{					
						resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
					}
				}				
			}	
		}
		catch (ViviendaException e) 
		{			
			e.getMensajeError();
			resUTS.setMensaje("FAULT");
			resUTS.setMensaje(e.getMensajeError());
			resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
		}
		
		
//*********************************** Filtro - Fase ***************************************
		//String sFase=criterios.getfFace(); //"f01";
		//System.out.println(criterios.getfFace());
		//------Fase
		if(criterios.getfFace() != null && criterios.getfFace().length()>0){
			Iterator<UbicacionTecnicaDto> faseIterator =resAction.getRespGetUnidadesTecnicasSuperiores().getUbicacionesList().iterator();
			while(faseIterator.hasNext()){
				UbicacionTecnicaDto elementoFase = faseIterator.next();
				
				if(elementoFase.getUbicacionDescripcion().toLowerCase().contains(criterios.getfFace().toLowerCase())){
					//System.out.println(elementoFase.getUbicacionDescripcion() + " ok");
				}else{
					faseIterator.remove();
					//System.out.println(elementoFase.getUbicacionDescripcion());
				}
			}
		}

//*********************************** Filtro - Piso/Modulo ********************************
/*		String sModulo=""; //"2";
		
		
		if(sModulo != null && sModulo.length()>0){
			//------Fase
			Iterator<UbicacionTecnicaDto> faseIterator =resAction.getRespGetUnidadesTecnicasSuperiores().getUbicacionesList().iterator();
			while(faseIterator.hasNext()){
				UbicacionTecnicaDto elementoFase = faseIterator.next();
				
						//------ Piso/Modulo
						Iterator<UbicacionTecnicaDto> moduloIterator =elementoFase.getPisosList().iterator();
						while(moduloIterator.hasNext()){
							UbicacionTecnicaDto elementoModulo = moduloIterator.next();
							
									if(elementoModulo.getUbicacionDescripcion().toLowerCase().contains(sModulo.toLowerCase())){
										System.out.println(elementoModulo.getUbicacionDescripcion() + " ok");
									}else{
										moduloIterator.remove();
										System.out.println(elementoModulo.getUbicacionDescripcion());
									}

						}

			}
		}
*/
//*********************************** Departamento ****************************************

		//String sDepart=""; //"105";
		boolean depR=true;
		
		if(criterios.getfDepart() != null && criterios.getfDepart().length()>0){
			//------Fase
			Iterator<UbicacionTecnicaDto> faseIterator =resAction.getRespGetUnidadesTecnicasSuperiores().getUbicacionesList().iterator();
			while(faseIterator.hasNext()){
				UbicacionTecnicaDto elementoFase = faseIterator.next();
				
						//------ Piso/Modulo
						Iterator<UbicacionTecnicaDto> moduloIterator =elementoFase.getPisosList().iterator();
						while(moduloIterator.hasNext()){
							UbicacionTecnicaDto elementoModulo = moduloIterator.next();
							
								//------ Departamento
								Iterator<EquipoDto> departIterator =elementoModulo.getEquiposList().iterator();
								while(departIterator.hasNext()){
									EquipoDto elementoDepart = departIterator.next();
									
									
										if(elementoDepart.getId_equnrx().toLowerCase().equals(criterios.getfDepart().toLowerCase())){
											//System.out.println(elementoDepart.getId_equnrx() + " ok");
											depR=false;
										}else{
											departIterator.remove();
											//System.out.println(elementoDepart.getId_equnrx());
										}
	
								}
								if(depR){moduloIterator.remove();}
								depR=true;

						}

			}
		}
		

/*		
		ArrayList<String>sCmp=new ArrayList<String>();
	     //sCmp.add("COPIA DEL ACTA CONSITITUTIVA");
		//sCmp.add("IDENTIFICACIÓN OFICIAL");
		//sCmp.add("HOJA AZUL");
		
		
		
//**********************************************
		boolean subR=true;
		boolean itr=false;
		
		if(sCmp.size()>0){
				Iterator<CarpetasDigitDto> carpIterator =resAction.getRespGetUnidadesTecnicasSuperiores().getCarpetasDigitalizacion().iterator();
				
				for(int i=resAction.getRespGetUnidadesTecnicasSuperiores().getCarpetasDigitalizacion().size()-1 ; i>=0 ;i--){
					CarpetasDigitDto elementoCarp=resAction.getRespGetUnidadesTecnicasSuperiores().getCarpetasDigitalizacion().get(i);
					System.out.println(elementoCarp.getId_ticket_area() + " | " +  elementoCarp.getId_ticket_file() + " | " + elementoCarp.getId_ticket_areax());
					if(!elementoCarp.getId_ticket_file().equals("00")){
						if(sCmp.contains(elementoCarp.getId_ticket_areax())){
							//System.out.println(elementoCarp.getId_ticket_areax() + " ok");
							subR=false;
						}else{
							resAction.getRespGetUnidadesTecnicasSuperiores().getCarpetasDigitalizacion().remove(i);
						}
					}else{
						if(subR){
							resAction.getRespGetUnidadesTecnicasSuperiores().getCarpetasDigitalizacion().remove(i);
						}
						subR=true;
						
					}
				}
		}
*/
		
		
	
		if(criterios.getFsubCarp() != null && criterios.getFsubCarp().length()>0){
			Iterator<CarpetasDigitDto> carpIterator =resAction.getRespGetUnidadesTecnicasSuperiores().getCarpetasDigitalizacion().iterator();
			while(carpIterator.hasNext()){
				CarpetasDigitDto elementoCarp = carpIterator.next();
				
				if(!elementoCarp.getId_ticket_area().equals(criterios.getFsubCarp())){
					carpIterator.remove();
				}

			}
		}
		
			
		return resAction;
	}
	
	@RequestMapping(value="/AdministraMapeo.htm", method= RequestMethod.GET)
	public String returnFormMapeo(ModelMap map, HttpSession session) {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "AdministraMapeo";
		 }
		 return redir;
	 }

	
	@RequestMapping(value="/AdministraMapeo.htm",method=RequestMethod.POST)
	public @ResponseBody ResponseUbicacionesActionDto getUbicacionesMapeo(@ModelAttribute(value="ubicacionesMap")CriteriosUbicacionesDto criterios, BindingResult result, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		ResponseUbicacionesActionDto resAction = new ResponseUbicacionesActionDto();
		ResponseGetUtInfSupDto resUTS = new ResponseGetUtInfSupDto();
		ResponseUbicacionDatosMapaDto datosImagen = new ResponseUbicacionDatosMapaDto();
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
				
		if(!sesionValida.validaSesion(session)) {
			resUTS.setMensaje("LOGOUT");
			resUTS.setDescripcion("");
			datosImagen.setMensaje("LOGOUT");
			datosImagen.setDescripcion("");
			resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
			resAction.setRespDatosImagen(datosImagen);
			return resAction;
		 }
		
		criterios.setI_usuario(sesionValida.getDatos(session,"usuario"));
		criterios.setI_id_ut_sup(sesionValida.getDatos(session,"unidad"));

		try
		{
			if(criterios.getAccion().equals("faces"))
			{
				criterios.setI_charact("");
				criterios.setI_deep_srch("");
				criterios.setI_equnr_price("");
				criterios.setI_coords("X");
				criterios.setI_coords_sup("X");
				criterios.setI_id_ut_current(sesionValida.getDatos(session,"unidad"));
				resUTS = ubicaciones.getUtInfSup(criterios);
				if(resUTS.getMensaje().equals("SUCCESS"))
				{					
					resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
				}
			}
			else if (criterios.getAccion().equals("pisos"))
			{
				criterios.setI_charact("");
				criterios.setI_deep_srch("");
				criterios.setI_equnr_price("");
				criterios.setI_coords("X");
				criterios.setI_coords_sup("X");
				resUTS = ubicaciones.getUtInfSup(criterios);
				if(resUTS.getMensaje().equals("SUCCESS"))
				{
					resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
				}				
			}
			else if (criterios.getAccion().equals("equipos"))
			{
				criterios.setI_charact("");
				criterios.setI_deep_srch("");
				criterios.setI_equnr_price("");
				criterios.setI_coords("X");
				criterios.setI_coords_sup("X");
				resUTS = ubicaciones.getUtInfSup(criterios);
				if(resUTS.getMensaje().equals("SUCCESS"))
				{
					resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
				}				
			}
			else if(criterios.getAccion().equals("imagen_datos"))
			{
				criterios.setI_charact("");
				criterios.setI_deep_srch("");
				criterios.setI_equnr_price("");
				criterios.setI_coords("X");
				criterios.setI_coords_sup("X");					
				CriteriosDatosMapaImagenDto critImanegMap = new CriteriosDatosMapaImagenDto();
				critImanegMap.setIdUbicacion(criterios.getI_id_ut_current());
				datosImagen = datosImageMap.getUTSImageMap(critImanegMap);
								
				if(datosImagen.getListaImagenDatos().size()>0)
				{		
					try
					{
						ServletContext context = RequestContextUtils.getWebApplicationContext(request).getServletContext();
						Blob bdblob = datosImagen.getListaImagenDatos().get(0).getBlobImage();
						//InputStream isImage = bdblob.getBinaryStream();
						FileOutputStream outputStream = new FileOutputStream(context.getRealPath("/")+"images/maping/"+datosImagen.getListaImagenDatos().get(0).getNombreImagen());
						int readBytes = 0;
						//int lengthBytes = (int)bdblob.length();
						byte[] buffer = datosImagen.getListaImagenDatos().get(0).getIsImage();
						//while ((readBytes = isImage.read(buffer, 0, lengthBytes)) != -1) {
							outputStream.write(buffer);
						//}
						outputStream.close();
						//isImage.close();
					}
					catch(Exception e){							
						resUTS.setMensaje("FAULT");
						resUTS.setMensaje(e.getMessage());
						resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
					}				
				}
				
				for(int i = 0; i<datosImagen.getListaImagenDatos().size(); i++)
				{
					datosImagen.getListaImagenDatos().get(i).setBlobImage(null);					
				}
				datosImagen.setListaCoordenadas(resUTS.getCoordenadasList());
				resAction.setRespDatosImagen(datosImagen);
			}		
		}
		catch (ViviendaException e) 
		{			
			resUTS.setMensaje("FAULT");
			resUTS.setMensaje(e.getMensajeError());
			resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
		}
		
		return resAction;
	}
	
	@RequestMapping(value="/MultipleImagenMapeo.htm", method= RequestMethod.GET)
	public String returnFormMapeoMultiple(ModelMap map, CriteriosUbicacionesDto criterios, HttpServletRequest request, HttpSession session) {
		ResponseUbicacionesActionDto resAction = new ResponseUbicacionesActionDto();
		ResponseGetUtInfSupDto resUTS = new ResponseGetUtInfSupDto();
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();
		criterios.setI_usuario(validaSesion.getDatos(session,"usuario"));
		criterios.setI_id_ut_sup(validaSesion.getDatos(session,"unidad"));
			
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "MultipleImagenMapeo";
		 }
			
			try
			{
				criterios.setI_charact("");
				criterios.setI_deep_srch("");
				criterios.setI_equnr_price("");
				criterios.setI_coords("X");
				criterios.setI_coords_sup("X");
				criterios.setI_id_ut_current(request.getParameter("idUbicacion"));
				resUTS = ubicaciones.getUtInfSup(criterios);
				if(resUTS.getMensaje().equals("SUCCESS"))
				{					
					resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
					map.put("respUbicacionAction", resAction.getRespGetUnidadesTecnicasSuperiores().getObjFiltradoCoordenadasList());
				}
				else
				{
					
				}			
			}
			catch (ViviendaException e) 
			{			
				e.getMensajeError();
			}
		 return redir;
	 }

	
	@RequestMapping(value="/MultipleImagenMapeo.htm",method=RequestMethod.POST)
	public @ResponseBody ResponseUbicacionesActionDto getUbicacionesMapeoMultiple(@ModelAttribute(value="ubicacionesMap")CriteriosUbicacionesDto criterios, BindingResult result, HttpSession session){
		ResponseUbicacionesActionDto resAction = new ResponseUbicacionesActionDto();
		ResponseGetUtInfSupDto resUTS = new ResponseGetUtInfSupDto();
		
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		
		if(!sesionValida.validaSesion(session)) {
			resUTS.setMensaje("LOGOUT");
			resUTS.setDescripcion("");
			resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
			return resAction;
		 }
		
		criterios.setI_usuario(sesionValida.getDatos(session,"usuario"));
		criterios.setI_id_ut_sup(sesionValida.getDatos(session,"unidad"));

		try
		{
			if(criterios.getAccion().equals("faces"))
			{
				criterios.setI_charact("");
				criterios.setI_deep_srch("");
				criterios.setI_equnr_price("");
				criterios.setI_id_ut_current(sesionValida.getDatos(session,"unidad"));
				resUTS = ubicaciones.getUtInfSup(criterios);
				if(resUTS.getMensaje().equals("SUCCESS"))
				{					
					resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
				}
			}
			else if (criterios.getAccion().equals("pisos"))
			{
				System.out.println("entro");
				criterios.setI_charact("X");
				criterios.setI_deep_srch("X");
				criterios.setI_equnr_price("X");
				resUTS = ubicaciones.getUtInfSup(criterios);
				if(resUTS.getMensaje().equals("SUCCESS"))
				{
					resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
				}	
			}
			else if(criterios.getAccion().equals("coordenadas"))
			{
				
			}
		}
		catch (ViviendaException e) 
		{			
			e.getMensajeError();
			resUTS.setMensaje("FAULT");
			resUTS.setMensaje(e.getMensajeError());
			resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
		}
		
		return resAction;
	}
	
	@RequestMapping(value="/Plantilla.htm", method= RequestMethod.GET)
	public String returnFormPlantilla(ModelMap map, HttpSession session) {
	return "Plantilla";
	 }

	
	@RequestMapping(value="/Plantilla.htm",method=RequestMethod.POST)
	public @ResponseBody Fizzle getRequest(@RequestBody Fizzle person , BindingResult result) {         
	    System.out.println(person.getBaz() + " " + person.getBaz()); 
	    return person; 
	}
	
	
	/*public @ResponseBody Fozzle plantilla(@RequestBody Fizzle input, BindingResult result, HttpSession session){
		return new Fozzle(input); 
	}*/
	
	

	
	/*@RequestMapping(value="/MapeoImagenes.htm", method= RequestMethod.GET)
	public String returnMapeoImagenes(ModelMap map, HttpSession session) {
	return "MapeoImagenes";
	 }

	
	@RequestMapping(value="/MapeoImagenes.htm",method=RequestMethod.POST)
	public @ResponseBody void MapeoImagenes(@ModelAttribute(value="plantillaRep")CriteriosUbicacionesDto criterios, BindingResult result, HttpSession session){

	}*/
	
	/*@RequestMapping(value="/Reportes.htm", method= RequestMethod.GET)
	public void returnReportes(Model model,HttpServletRequest request,HttpServletResponse response) {
		
		response.setHeader ("Pragma", "No-cache");
		response.setDateHeader ("Expires", 0);
		try
		{
			ServletOutputStream servletOutputStream = response.getOutputStream();
	        //ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream inputStream = this.getClass().getResourceAsStream("ejemplo2.jasper");
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);
			Map<String, Object> params = new HashMap<String, Object>();
    		//params.put("programa", "stes");
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,params);
			
			response.setContentType("application/pdf");
			response.setHeader ("Content-disposition", "inline; filename=ejemplo1.pdf");
    		JRPdfExporter exporter = new JRPdfExporter();
    		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
    		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
    		exporter.exportReport();
    		servletOutputStream.flush();
            servletOutputStream.close();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	//return "Reportes";
	 }*/
	
	@RequestMapping(value = "/Reportes.htm", method = RequestMethod.GET)
	public void downloadAdmin(Model model,HttpServletRequest request,HttpServletResponse response) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("titulo", "Reporte");
		
		try {
			InputStream reportStream = this.getClass().getResourceAsStream("report3.jrxml");
			JasperDesign jd = JRXmlLoader.load(reportStream);
			JasperReport jr = JasperCompileManager.compileReport(jd);

			/*List<Sales> items = new ArrayList<Sales>();

			Sales item1 = new Sales();
			item1.setId(1001L);
			item1.setName("Pencil");
			item1.setDescription("This is used for sketching drawings");
			item1.setPrice(10.50);*/
			
			// Create second item
			/*Sales item2 = new Sales();
			item2.setId(1002L);
			item2.setName("Pen");
			item2.setDescription("This is used for signing autographs");
			item2.setPrice(15.00);
			
			// Create third item
			Sales item3 = new Sales();
			item3.setId(1003L);
			item3.setName("Bag");
			item3.setDescription("This is used for storing other items");
			item3.setPrice(50.00);*/
			
			// Add to list
			//items.add(item1);
			//items.add(item2);
			//items.add(item3);

			/*JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(items);
			JasperPrint jp = JasperFillManager.fillReport(jr, params, jrbcds);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			exporter.exportReport();
			String fileName = "UserReport.pdf";
			response.setHeader("Content-Disposition", "inline; filename="+ fileName);
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			ServletOutputStream outputStream = response.getOutputStream();
			baos.writeTo(outputStream);
			outputStream.flush();*/

		} catch (Exception ex) {
 			ex.printStackTrace();
 		}
		

	}  
	
	@RequestMapping(value="/Reportes.htm" ,method=RequestMethod.POST)
	public @ResponseBody void returnReportes(@ModelAttribute(value="plantillaRep")CriteriosUbicacionesDto criterios, BindingResult result, HttpSession session){

	}
	
	@RequestMapping(value="/MapeoImagenes.htm", method = RequestMethod.GET)
	public String getUploadForm(ModelMap map, CriteriosUbicacionesDto criterios, HttpServletRequest request, HttpSession session) {
		ResponseUbicacionesActionDto resAction = new ResponseUbicacionesActionDto();
		ResponseGetUtInfSupDto resUTS = new ResponseGetUtInfSupDto();
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();
		criterios.setI_usuario(validaSesion.getDatos(session,"usuario"));
		criterios.setI_id_ut_sup(validaSesion.getDatos(session,"unidad"));
		ResponseUbicacionDatosMapaDto datosImagen = new ResponseUbicacionDatosMapaDto();
		List<UbicacionTecnicaDatosMapaImagenVo> listaImagenDatos = new ArrayList<UbicacionTecnicaDatosMapaImagenVo>();
			
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "MapeoImagenes";
		 }
			
			try
			{
				criterios.setI_charact("");
				criterios.setI_deep_srch("");
				criterios.setI_equnr_price("");
				criterios.setI_coords("X");
				if(request.getParameter("from").equals("uts1"))
				{
					criterios.setI_id_ut_current(validaSesion.getDatos(session,"unidad"));
					resUTS = ubicaciones.getUtInfSup(criterios);
					if(resUTS.getMensaje().equals("SUCCESS"))
					{
						List<UbicacionTecnicaDto> listaUT = resUTS.getUbicacionesList();
						for(int i=0; i < listaUT.size(); i++)
						{
							CriteriosDatosMapaImagenDto criteriosiM = new CriteriosDatosMapaImagenDto();
							criteriosiM.setIdUbicacion(listaUT.get(i).getUbicacionClave());
							datosImagen=datosImageMap.getUTSImageMap(criteriosiM);
							if(datosImagen.getListaImagenDatos().size()>0)
							{								
								try
								{
									ServletContext context = RequestContextUtils.getWebApplicationContext(request).getServletContext();
									String nombre_imagen=datosImagen.getListaImagenDatos().get(0).getNombreImagen();
									String url_imagen=context.getRealPath("/")+"images/maping/"+datosImagen.getListaImagenDatos().get(0).getNombreImagen();
									FileOutputStream outputStream = new FileOutputStream(url_imagen);
									int readBytes = 0;
									//int lengthBytes = (int)bdblob.length();
									byte[] buffer = datosImagen.getListaImagenDatos().get(0).getIsImage();
									//while ((readBytes = isImage.read(buffer, 0, lengthBytes)) != -1) {
										outputStream.write(buffer);
									//}
									outputStream.close();
									//isImage.close();
									
									/*ServletContext context = RequestContextUtils.getWebApplicationContext(request).getServletContext(); 
									InputStream isImage = datosImagen.getListaImagenDatos().get(0).getBlobImage().getBinaryStream();
									
									String nombre_imagen=datosImagen.getListaImagenDatos().get(0).getNombreImagen();
									FileOutputStream outputStream = new FileOutputStream(url_imagen);
									int readBytes = 0;
									int lengthBytes = (int)datosImagen.getListaImagenDatos().get(0).getBlobImage().length();
									byte[] buffer = new byte[lengthBytes];
									while ((readBytes = isImage.read(buffer, 0, lengthBytes)) != -1) {
										outputStream.write(buffer, 0, readBytes);
									}
									outputStream.close();
									isImage.close();*/
									UbicacionTecnicaDatosMapaImagenVo datosMapaVo= new UbicacionTecnicaDatosMapaImagenVo();
									datosMapaVo.setNombreImagen(nombre_imagen);
									datosMapaVo.setUrlImagen(url_imagen);
									datosMapaVo.setIdUbicacion(listaUT.get(i).getUbicacionClave());
									listaImagenDatos.add(datosMapaVo);
									datosImagen.setListaImagenDatos(listaImagenDatos);
									resAction.setRespDatosImagen(datosImagen);
									break;
								}
								catch(Exception ex)
								{
									resAction.getRespDatosImagen().setMensaje("FAULT");
									resAction.getRespDatosImagen().setDescripcion(ex.getMessage());
									map.put("respListadoImagenesAction",resAction);
								}				
							}
							
							for(int j = 0; j<datosImagen.getListaImagenDatos().size(); j++)
							{
								datosImagen.getListaImagenDatos().get(j).setBlobImage(null);					
							}
							
							resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
						}
						try
						{
							map.put("respListadoImagenesAction", resAction.getRespDatosImagen().getListaImagenDatos());
						}
						catch(Exception e){
							map.put("respListadoImagenesAction", new ArrayList <UbicacionTecnicaDatosMapaImagenVo>());
							}
					}
					else
					{
						
					}
				}
				else 
				{
					criterios.setI_id_ut_current(request.getParameter("idUbicacion"));
					resUTS = ubicaciones.getUtInfSup(criterios);
					if(resUTS.getMensaje().equals("SUCCESS"))
					{
						List<EquipoDto> listaUTE = resUTS.getEquiposList();
						for(int i=0; i < listaUTE.size(); i++)
						{
							CriteriosDatosMapaImagenDto criteriosiM = new CriteriosDatosMapaImagenDto();
							criteriosiM.setIdUbicacion(listaUTE.get(i).getId_equnr());
							datosImagen=datosImageMap.getUTSImageMap(criteriosiM);
							if(datosImagen.getListaImagenDatos().size()>0)
							{								
								try
								{
									ServletContext context = RequestContextUtils.getWebApplicationContext(request).getServletContext();
									String url_imagen=context.getRealPath("/")+"images/maping/"+datosImagen.getListaImagenDatos().get(0).getNombreImagen();
									String nombre_imagen=datosImagen.getListaImagenDatos().get(0).getNombreImagen();
									FileOutputStream outputStream = new FileOutputStream(url_imagen);
									int readBytes = 0;
									//int lengthBytes = (int)bdblob.length();
									byte[] buffer = datosImagen.getListaImagenDatos().get(0).getIsImage();
									//while ((readBytes = isImage.read(buffer, 0, lengthBytes)) != -1) {
										outputStream.write(buffer);
									//}
									outputStream.close();
									//isImage.close();
									
									/*ServletContext context = RequestContextUtils.getWebApplicationContext(request).getServletContext(); 
									InputStream isImage = datosImagen.getListaImagenDatos().get(0).getBlobImage().getBinaryStream();
									String url_imagen=context.getRealPath("/")+"images/maping/"+datosImagen.getListaImagenDatos().get(0).getNombreImagen();
									String nombre_imagen=datosImagen.getListaImagenDatos().get(0).getNombreImagen();
									FileOutputStream outputStream = new FileOutputStream(url_imagen);
									int readBytes = 0;
									int lengthBytes = (int)datosImagen.getListaImagenDatos().get(0).getBlobImage().length();
									byte[] buffer = new byte[lengthBytes];
									while ((readBytes = isImage.read(buffer, 0, lengthBytes)) != -1) {
										outputStream.write(buffer, 0, readBytes);
									}
									outputStream.close();
									isImage.close();*/
									UbicacionTecnicaDatosMapaImagenVo datosMapaVo= new UbicacionTecnicaDatosMapaImagenVo();
									datosMapaVo.setNombreImagen(nombre_imagen);
									datosMapaVo.setUrlImagen(url_imagen);
									datosMapaVo.setIdUbicacion(listaUTE.get(i).getId_equnr());
									listaImagenDatos.add(datosMapaVo);
									datosImagen.setListaImagenDatos(listaImagenDatos);
									resAction.setRespDatosImagen(datosImagen);
									break;
								}
								catch(Exception ex)
								{
									resAction.getRespDatosImagen().setMensaje("FAULT");
									resAction.getRespDatosImagen().setDescripcion(ex.getMessage());
									map.put("respListadoImagenesAction",resAction);
								}				
							}
							
							for(int j = 0; j<datosImagen.getListaImagenDatos().size(); j++)
							{
								datosImagen.getListaImagenDatos().get(j).setBlobImage(null);					
							}
							
							resAction.setRespGetUnidadesTecnicasSuperiores(resUTS);
						}
						try
						{
							map.put("respListadoImagenesAction", resAction.getRespDatosImagen().getListaImagenDatos());
						}
						catch(Exception e){
							map.put("respListadoImagenesAction", new ArrayList <UbicacionTecnicaDatosMapaImagenVo>());
							}
					}
					else
					{
						
					}									
				}			
			}
			catch (ViviendaException e) 
			{			
				e.getMensajeError();
			}
		return redir;
	}

	@RequestMapping(value="/MapeoImagenes.htm", method = RequestMethod.POST)
	public String setUploadFileMaing(@ModelAttribute(value="criterios")CriteriosFileUploadDto criterios, BindingResult result, HttpServletRequest request, HttpServletResponse response, HttpSession session, Map model) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    ResponseDatosMapingActionDto responseDatosMapingActionDto = new ResponseDatosMapingActionDto();
			ResponseUbicacionDatosMapaDto responseValidaImagenMapeo= new ResponseUbicacionDatosMapaDto();
			ResponseUbicacionDatosMapaDto responseInsertImagenMapeo= new ResponseUbicacionDatosMapaDto();
			ResponseUbicacionDatosMapaDto responseDeleteImagenMapeo= new ResponseUbicacionDatosMapaDto();
			String values_chk [] = new String [0];
			 /*System.err.println("-------------------------------------------");     
			 System.err.println("Test upload: " + criterios.getName());     
			 System.err.println("Test upload: " + criterios.getFileData().getOriginalFilename());     
			 System.err.println("-------------------------------------------");   */
			MultipartFile file = criterios.getFileData();
			CriteriosDatosMapaImagenDto criteriosMaping = new CriteriosDatosMapaImagenDto();
			UUID uidImage = UUID.randomUUID();
			String redir = "";
			
			if(!sesionValida.validaSesion(session))
			 {
				return "../../index";
			 }
			
			if(request.getParameter("idUTS").equals(""))
			{
				criteriosMaping.setIdUTS(sesionValida.getDatos(session,"unidad"));
				criteriosMaping.setIdUbicacion(request.getParameter("idUbicacion"));
			}
			else
			{
				criteriosMaping.setIdUTS(criterios.getIdUbicacion());
				criteriosMaping.setIdUbicacion(request.getParameter("idUTS"));				
			}
			
			
			if(request.getParameter("accion").equals("newimagen"))
			{				
				Boolean con_imagenes=false;
				try
				{
					values_chk=request.getParameterValues("imagenes");
					int limg=values_chk.length;
					con_imagenes=true;
				}
				catch(Exception e){
					con_imagenes=false;
				}
				
				if(!con_imagenes)
				{
					criteriosMaping.setTipo("insert");
					try {						
						String [] ni=file.getOriginalFilename().split("\\.");
						ni[0]=uidImage.toString();
						criteriosMaping.setImagenMaping(file);
						criteriosMaping.setNombreImagen(ni[0]+"."+ni[1]);										
						
						responseValidaImagenMapeo=datosImageMap.getValidaDatosImagen(criteriosMaping);
					
						if(responseValidaImagenMapeo.getDescripcion().equals("SD"))
						{							
							responseDeleteImagenMapeo=datosImageMap.deleteImageDatosMaping(criteriosMaping);
							if(responseDeleteImagenMapeo.getMensaje().equals("SUCCESS"))
							{
								responseInsertImagenMapeo=datosImageMap.createImageDatosMaping(criteriosMaping);
								
								if(responseInsertImagenMapeo.getMensaje().equals("FAULT"))
								{
									responseValidaImagenMapeo.setMensaje("FAULT");
									responseValidaImagenMapeo.setDescripcion("No se pudo insertar la imagen");
								}
								else
								{
									responseValidaImagenMapeo.setMensaje(responseInsertImagenMapeo.getMensaje());
									responseValidaImagenMapeo.setDescripcion(responseInsertImagenMapeo.getDescripcion());
								}
							}
							else
							{
								responseValidaImagenMapeo.setMensaje("FAULT");
								responseValidaImagenMapeo.setDescripcion("No se pudo eliminar la imagen para sustituir la nueva");
							}
						}
						else
						{							
							responseValidaImagenMapeo.setMensaje("FAULT");
							responseValidaImagenMapeo.setDescripcion("El archivo ya existe renombre la imagen e intente subirla nuevamente");
						}		
						
					} 
					catch (Exception e) {
						responseValidaImagenMapeo.setMensaje("FAULT");
						responseValidaImagenMapeo.setDescripcion(e.getMessage());
					}
				}
				else
				{
					String [] ni=file.getOriginalFilename().split("\\.");
					ni[0]=uidImage.toString();
					criteriosMaping.setImagenMaping(file);
					criteriosMaping.setNombreImagen(ni[0]+"."+ni[1]);
					criteriosMaping.setTipo("insert");
					
					try {
						responseValidaImagenMapeo=datosImageMap.getValidaDatosImagen(criteriosMaping);
						if(responseValidaImagenMapeo.getDescripcion().equals("SD"))
						{								
							responseInsertImagenMapeo=datosImageMap.createImageDatosMaping(criteriosMaping);
							
							if(responseInsertImagenMapeo.getMensaje().equals("FAULT"))
							{
								responseValidaImagenMapeo.setMensaje("FAULT");
								responseValidaImagenMapeo.setDescripcion("No se pudo insertar la imagen en la base de datos");
							}
							else
							{
								responseValidaImagenMapeo.setMensaje(responseInsertImagenMapeo.getMensaje());
								responseValidaImagenMapeo.setDescripcion(responseInsertImagenMapeo.getDescripcion());
								
								String trImagenes [];
								for(int i = 0; i<values_chk.length; i++)
								{
									trImagenes = values_chk[i].split("\\|");									
															
									criteriosMaping.setImagenMaping(file);
									criteriosMaping.setNombreImagen(trImagenes[0]);
									criteriosMaping.setIdUbicacion(trImagenes[1]);
															
									responseDeleteImagenMapeo=datosImageMap.deleteImageDatosMaping(criteriosMaping);
									if(responseDeleteImagenMapeo.getMensaje().equals("SUCCESS"))
									{
										criteriosMaping.setNombreImagen(file.getOriginalFilename());
										criteriosMaping.setTipo("update");
										responseInsertImagenMapeo=datosImageMap.createImageDatosMaping(criteriosMaping);
										
										if(responseDeleteImagenMapeo.getMensaje().equals("FAULT"))
										{
											responseValidaImagenMapeo.setMensaje("FAULT");
											responseValidaImagenMapeo.setDescripcion("No se pudo actualizar la imagen en la base de datos");
										}
										else
										{
											responseValidaImagenMapeo.setMensaje(responseInsertImagenMapeo.getMensaje());
											responseValidaImagenMapeo.setDescripcion(responseInsertImagenMapeo.getDescripcion());
										}
									}
									else
									{
										responseValidaImagenMapeo.setMensaje("FAULT");
										responseValidaImagenMapeo.setDescripcion("No se pudo eliminar la imagen para sustituir la nueva");
									}															
								}									
							}								
						}
						else
						{							
							responseValidaImagenMapeo.setMensaje("FAULT");
							responseValidaImagenMapeo.setDescripcion("El archivo ya existe renombre la imagen e intente subirla nuevamente");
						}
					} catch (ViviendaException e) {
						responseValidaImagenMapeo.setMensaje("FAULT");
						responseValidaImagenMapeo.setDescripcion(e.getMessage());
					}
				}
			}
			else if(request.getParameter("accion").equals("upimagen"))
			{
				criteriosMaping.setTipo("update");				
				
				try {						
					
					criteriosMaping.setImagenMaping(file);
					criteriosMaping.setNombreImagen(request.getParameter("img_nom"));				
					
					responseDeleteImagenMapeo=datosImageMap.deleteImageDatosMaping(criteriosMaping);
					if(responseDeleteImagenMapeo.getMensaje().equals("SUCCESS"))
					{
						responseInsertImagenMapeo=datosImageMap.createImageDatosMaping(criteriosMaping);
						
						if(responseInsertImagenMapeo.getMensaje().equals("FAULT"))
						{
							responseValidaImagenMapeo.setMensaje("FAULT");
							responseValidaImagenMapeo.setDescripcion("No se pudo insertar la imagen");
						}
						else
						{
							responseValidaImagenMapeo.setMensaje(responseInsertImagenMapeo.getMensaje());
							responseValidaImagenMapeo.setDescripcion(responseInsertImagenMapeo.getDescripcion());
						}
					}
					else
					{
						responseValidaImagenMapeo.setMensaje("FAULT");
						responseValidaImagenMapeo.setDescripcion("No se pudo eliminar la imagen para sustituir la nueva");
					}				
				} 
				catch (Exception e) {
					responseValidaImagenMapeo.setMensaje("FAULT");
					responseValidaImagenMapeo.setDescripcion(e.getMessage());
				}
			}
		    
			
			
			responseDatosMapingActionDto.setResponseUbicacionDatosMapaDto(responseValidaImagenMapeo);
			//responseDatosMapingActionDto.setResponseUploadFilesDto(responseUplodFilesDto);
			
			model.put("responseDatosMapingActionDto", responseDatosMapingActionDto);
			
		return "RespuestaAdminImagenes";
	}
	
	@RequestMapping(value="/RespuestaAdminImagenes.htm", method= RequestMethod.GET)
	public String getRespuestaAdminImagen(ModelMap map, HttpSession session) {
	return "RespuestaAdminImagenes";
	 }

	
	@RequestMapping(value="/RespuestaAdminImagenes.htm",method=RequestMethod.POST)
	public @ResponseBody Fizzle postRespuestaAdminImagen(@RequestBody Fizzle person , BindingResult result) {         
	    System.out.println(person.getBaz() + " " + person.getBaz()); 
	    return person; 
	}
	
	@RequestMapping(value="/MapeoImagenComponent.htm", method= RequestMethod.GET)
	public String getMapeoComponent(ModelMap map, HttpSession session) {
	return "MapeoImagenComponent";
	}

	
	@RequestMapping(value="/MapeoImagenComponent.htm",method=RequestMethod.POST)
	public @ResponseBody ResponseUbicacionesActionDto postCoordenadasImagen(@ModelAttribute(value="coordenadasMap")CriteriosUbicacionesDto criterios, BindingResult result, HttpSession session){        
		ResponseUbicacionesActionDto resAction = new ResponseUbicacionesActionDto();
		ResponseUbicacionDatosMapaDto resUDMD = new ResponseUbicacionDatosMapaDto();
		
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		
		if(!sesionValida.validaSesion(session)) {
			resUDMD.setMensaje("LOGOUT");
			resUDMD.setDescripcion("");
			resAction.setRespDatosImagen(resUDMD);
			return resAction;
		 }
		
		criterios.setI_usuario(sesionValida.getDatos(session,"usuario"));
		criterios.setI_id_ut_sup(sesionValida.getDatos(session,"unidad"));

		try
		{
			if(criterios.getAccion().equals("addCoordenadas"))
			{
				criterios.setI_charact("");
				criterios.setI_deep_srch("");
				criterios.setI_equnr_price("");
				criterios.setI_id_ut_current(criterios.getI_id_ut_current());
				resUDMD = ubicaciones.setCoordenadasImagen(criterios);
				if(resUDMD.getMensaje().equals("SUCCESS"))
				{					
					resAction.setRespDatosImagen(resUDMD);
				}
			}
			else
			{
				
			}
		}
		catch (ViviendaException e) 
		{			
			e.getMensajeError();
			resUDMD.setMensaje("FAULT");
			resUDMD.setMensaje(e.getMensajeError());
			resAction.setRespDatosImagen(resUDMD);
		}
		
		return resAction;
	}	
	
	@RequestMapping(value="/UbicacionesMain.htm", method= RequestMethod.GET)
	public String getUbicacionesMain(ModelMap map, CriteriosUbicacionesDto criterios, HttpServletRequest request, HttpSession session) {
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();
		String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "UbicacionesMain";
		 }
		return redir;
	}

	
	@RequestMapping(value="/UbicacionesMain.htm",method=RequestMethod.POST)
	public @ResponseBody ResponseUbicacionesActionDto postUbicacionesMain(@ModelAttribute(value="ubicacionesMainMap")CriteriosUbicacionesDto criterios, BindingResult result, HttpServletRequest request, HttpSession session){        
		ResponseUbicacionesActionDto resAction = new ResponseUbicacionesActionDto();
		ResponseGetUtInfSupDto pisosgrid = new ResponseGetUtInfSupDto();
		NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();

		
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		
		if(!sesionValida.validaSesion(session)) {
			pisosgrid.setMensaje("LOGOUT");
			pisosgrid.setDescripcion("");
			resAction.setRespGetUnidadesTecnicasSuperiores(pisosgrid);
			return resAction;
		}
		
		criterios.setI_usuario(sesionValida.getDatos(session,"usuario"));
		criterios.setI_id_ut_sup(sesionValida.getDatos(session,"unidad"));
		criterios.setI_charact("");		
		try
		{
			
			if (criterios.getAccion().equals("pisos"))
			{
				criterios.setI_charact("X");
				criterios.setI_deep_srch("X");
				criterios.setI_equnr_price("X");
				criterios.setI_coords("");
				criterios.setI_coords_sup("");
				pisosgrid = ubicaciones.getUtInfSup(criterios);
				
				if(pisosgrid.getMensaje().equals("SUCCESS"))
				{
					try
					{
					for(int f=0; f<pisosgrid.getEquiposList().size(); f++)
					{
						for(int g=0; g<pisosgrid.getEqPriceList().size(); g++)
						{
							if(pisosgrid.getEqPriceList().get(g).getId_equnr().equals(pisosgrid.getEquiposList().get(f).id_equnr))
							{
								pisosgrid.getEquiposList().get(g).setId_precio(pisosgrid.getEqPriceList().get(g).getPrice());								
								pisosgrid.getEquiposList().get(g).setId_precio_format(""+defaultFormat.format(pisosgrid.getEqPriceList().get(g).getPrice()));							
							}							
						}
					}
					}
					catch(Exception e){}
					
					Collections.reverse(pisosgrid.getUbicacionesList());
					resAction.setRespGetUnidadesTecnicasSuperiores(pisosgrid);
				}				
			}			
		}		
		catch (ViviendaException e) 
		{			
			pisosgrid.setMensaje("FAULT");
			pisosgrid.setMensaje(e.getMensajeError());
			resAction.setRespGetUnidadesTecnicasSuperiores(pisosgrid);
		}
		
		return resAction;
	}	
	
	@RequestMapping(value="/UbicacionTS.htm", method= RequestMethod.GET)
	public String getUbicacionesTS(ModelMap map, CriteriosUbicacionesDto criterios, HttpServletRequest request, HttpSession session) {
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();
		ServletContext context = RequestContextUtils.getWebApplicationContext(request).getServletContext();
		String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "UbicacionTS";
		 }
		 
		ResponseUbicacionesActionDto resAction = new ResponseUbicacionesActionDto();
		ResponseUbicacionDatosMapaDto imagenes = new ResponseUbicacionDatosMapaDto();
		ResponseGetUtInfSupDto coordenadas = new ResponseGetUtInfSupDto();
		ResponseGetUtInfSupDto coordenadas_valida = new ResponseGetUtInfSupDto();
		UbicacionTecnicaDatosMapaImagenVo datosImagenVo = new UbicacionTecnicaDatosMapaImagenVo();
		
		

		criterios.setI_usuario(validaSesion.getDatos(session,"usuario"));
		criterios.setI_id_ut_sup(validaSesion.getDatos(session,"unidad"));
		criterios.setI_charact("");
		criterios.setI_deep_srch("");
		criterios.setI_equnr_price("X");
		criterios.setI_coords("X");
		criterios.setI_coords_sup("");
		CriteriosDatosMapaImagenDto critImanegMap = new CriteriosDatosMapaImagenDto();		
		
		try
		{			
			critImanegMap.setIdUbicacion(validaSesion.getDatos(session,"unidad"));
			criterios.setI_id_ut_current(validaSesion.getDatos(session,"unidad"));
			imagenes=datosImageMap.getUTForImage(critImanegMap);
			int iuts = imagenes.getListaImagenDatos().size(); 
			
			if(imagenes.getMensaje().equals("SUCCESS"))
			{
				if(iuts>0)
				{
					String nombre_imagen_l="";
					for(int i=0; i<iuts; i++)
					{
						try
						{
							nombre_imagen_l=imagenes.getListaImagenDatos().get(i).getNombreImagen();
							
							if(!nombre_imagen_l.equals(""))
							{
								datosImagenVo = imagenes.getListaImagenDatos().get(i);
								break;
							}
						}
						catch(Exception e)
						{
							imagenes.setMensaje("FAULT");
							imagenes.setMensaje(e.getMessage());
						}														
					}	
					
					try
					{						
						String url_imagen=context.getRealPath("/")+"images/maping/"+datosImagenVo.getNombreImagen();
						String nombre_imagen=datosImagenVo.getNombreImagen();
						FileOutputStream outputStream = new FileOutputStream(url_imagen);
						int readBytes = 0;
						//int lengthBytes = (int)bdblob.length();
						byte[] buffer = datosImagenVo.getIsImage();
						//while ((readBytes = isImage.read(buffer, 0, lengthBytes)) != -1) {
							outputStream.write(buffer);
						//}
						outputStream.close();
						//isImage.close();
						
						/*ServletContext context = RequestContextUtils.getWebApplicationContext(request).getServletContext(); 
						InputStream isImage = datosImagenVo.getBlobImage().getBinaryStream();
						String url_imagen=context.getRealPath("/")+"images/maping/"+datosImagenVo.getNombreImagen();
						String nombre_imagen=datosImagenVo.getNombreImagen();
						FileOutputStream outputStream = new FileOutputStream(url_imagen);
						int readBytes = 0;
						int lengthBytes = (int)datosImagenVo.getBlobImage().length();
						byte[] buffer = new byte[lengthBytes];
						while ((readBytes = isImage.read(buffer, 0, lengthBytes)) != -1) {
							outputStream.write(buffer, 0, readBytes);
						}
						outputStream.close();
						isImage.close();*/
					}
					catch(Exception e)
					{
						imagenes.setMensaje("FAULT");
						imagenes.setMensaje(e.getMessage());
						
					}
					coordenadas = ubicaciones.getUtInfSup(criterios);
					Collections.reverse(coordenadas.getUbicacionesList());
				}
				else
				{
					imagenes.setMensaje("FAULT");
					imagenes.setMensaje("No existe imagen para ser mostrada");
				}
			}					
			
			
			
			//criterios.setI_charact("");
			//criterios.setI_deep_srch("");
			//criterios.setI_equnr_price("");
			//criterios.setI_coords("X");
			//criterios.setI_id_ut_current(sesionValida.getDatos(session,"unidad"));				
			
			//ubicacionDto=ubicaciones.getUtInfSup(criterios);
			
			//if(ubicacionDto.getMensaje().equals("SUCCESS"))
			//{					
				//int uts = ubicacionDto.getUbicacionesList().size();
				//if(uts>0)
				//{
					//for(int i=0; i<uts; i++)
					//{
						//critImanegMap.setIdUbicacion(validaSesion.getDatos(session,"unidad"));
						//imagenes=datosImageMap.getUTForImage(critImanegMap);
						//int iuts = imagenes.getListaImagenDatos().size(); 
						
						//if(imagenes.getMensaje().equals("SUCCESS"))
						//{
							//if(iuts>0)
							//{
								//for(int j=0; j<iuts; j++)
								//{
									
								//}
							//}
						//}						
					//}				
				//}
			//}

			resAction.setRespGetUnidadesTecnicasSuperiores(coordenadas);
			resAction.setRespDatosImagen(imagenes);
		}
		catch (ViviendaException e) 
		{			
			//e.getMensajeError();
			//resUDMD.setMensaje("FAULT");
			//resUDMD.setMensaje(e.getMensajeError());
			//resAction.setRespDatosImagen(resUDMD);
		}		
		map.put("imagen",datosImagenVo);
		map.put("allcoords",resAction.getRespGetUnidadesTecnicasSuperiores().getCoordenadasList());
		
		return redir;
	}

	
	@RequestMapping(value="/UbicacionTS.htm",method=RequestMethod.POST)
	public @ResponseBody ResponseUbicacionesActionDto postUbicacionTS(@ModelAttribute(value="ubicacionesTSMap")CriteriosUbicacionesDto criterios, BindingResult result, HttpServletRequest request, HttpSession session){        
		ResponseUbicacionesActionDto resAction = new ResponseUbicacionesActionDto();
		return resAction;
	}	
	
	@RequestMapping(value="/UbicacionTSGrid.htm", method= RequestMethod.GET)
	public String getUbicacionesTSGrid(ModelMap map, CriteriosUbicacionesDto criterios, HttpServletRequest request, HttpSession session) {
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();
		ServletContext context = RequestContextUtils.getWebApplicationContext(request).getServletContext();
		
		String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "UbicacionTSGrid";
		 }
		    ResponseUbicacionesActionDto resAction = new ResponseUbicacionesActionDto();
			ResponseUbicacionDatosMapaDto imagenes = new ResponseUbicacionDatosMapaDto();
			ResponseUbicacionDatosMapaDto equipos_imagenes = new ResponseUbicacionDatosMapaDto();
			ResponseGetUtInfSupDto coordenadas = new ResponseGetUtInfSupDto();
			UbicacionTecnicaDatosMapaImagenVo datosImagenVo = new UbicacionTecnicaDatosMapaImagenVo();
			
			

			criterios.setI_usuario(validaSesion.getDatos(session,"usuario"));
			criterios.setI_id_ut_sup(validaSesion.getDatos(session,"unidad"));
			criterios.setI_id_ut_current(request.getParameter("idUbicacion"));
			criterios.setI_charact("X");
			criterios.setI_deep_srch("");
			criterios.setI_equnr_price("X");
			criterios.setI_coords("X");
			criterios.setI_coords_sup("");
			CriteriosDatosMapaImagenDto critImanegMap = new CriteriosDatosMapaImagenDto();		
			
			try
			{			
				critImanegMap.setIdUbicacion(request.getParameter("idUbicacion"));
				imagenes=datosImageMap.getUTForImage(critImanegMap);
				int iuts = imagenes.getListaImagenDatos().size(); 
				
				if(imagenes.getMensaje().equals("SUCCESS"))
				{
					if(iuts>0)
					{
						String nombre_imagen_l="";
						for(int i=0; i<iuts; i++)
						{
							try
							{
								nombre_imagen_l=imagenes.getListaImagenDatos().get(i).getNombreImagen();
								
								if(!nombre_imagen_l.equals("")) 
								{
									datosImagenVo = imagenes.getListaImagenDatos().get(i);
									break;
								}
							}
							catch(Exception e)
							{
								imagenes.setMensaje("FAULT");
								imagenes.setMensaje(e.getMessage());
							}														
						}	
						
						try
						{
							String url_imagen=context.getRealPath("/")+"images/maping/"+datosImagenVo.getNombreImagen();
							String nombre_imagen=datosImagenVo.getNombreImagen();
							FileOutputStream outputStream = new FileOutputStream(url_imagen);
							int readBytes = 0;
							//int lengthBytes = (int)bdblob.length();
							byte[] buffer = datosImagenVo.getIsImage();
							//while ((readBytes = isImage.read(buffer, 0, lengthBytes)) != -1) {
								outputStream.write(buffer);
							//}
							outputStream.close();
							//isImage.close();
							
							/*ServletContext context = RequestContextUtils.getWebApplicationContext(request).getServletContext(); 
							InputStream isImage = datosImagenVo.getBlobImage().getBinaryStream();
							String url_imagen=context.getRealPath("/")+"images/maping/"+datosImagenVo.getNombreImagen();
							String nombre_imagen=datosImagenVo.getNombreImagen();
							FileOutputStream outputStream = new FileOutputStream(url_imagen);
							int readBytes = 0;
							int lengthBytes = (int)datosImagenVo.getBlobImage().length();
							byte[] buffer = new byte[lengthBytes];
							while ((readBytes = isImage.read(buffer, 0, lengthBytes)) != -1) {
								outputStream.write(buffer, 0, readBytes);
							}
							outputStream.close();
							isImage.close();*/
						}
						catch(Exception e)
						{
							imagenes.setMensaje("FAULT");
							imagenes.setMensaje(e.getMessage());
							
						}						
						CriteriosEquipoTiposDto critTipos = new CriteriosEquipoTiposDto();
						critTipos.setI_usuario(criterios.getI_usuario());
						critTipos.setI_id_fase(request.getParameter("utsF"));
						critTipos.setI_id_ut_sup(criterios.getI_id_ut_sup());
						imagenes.setListaTipos(ubicaciones.getTiposEquipos(critTipos).getListaTipos());
						
						try
						{
							critImanegMap.setIdUbicacion(imagenes.getListaImagenDatos().get(0).getIdUbicacion());
							critImanegMap.setIdUTS(imagenes.getListaImagenDatos().get(0).getIdUTS().substring(0,10));
							equipos_imagenes=datosImageMap.getImagenEquipo(critImanegMap);
							
							for(int i=0; i<equipos_imagenes.getListaEquiposImagenesDatos().size(); i++)
							{	
								String url_imagen=context.getRealPath("/")+"images/maping/"+equipos_imagenes.getListaEquiposImagenesDatos().get(i).getNombreImagen();
								String path_img="/images/maping/"+equipos_imagenes.getListaEquiposImagenesDatos().get(i).getNombreImagen();
								String nombre_imagen=equipos_imagenes.getListaEquiposImagenesDatos().get(i).getNombreImagen();
								FileOutputStream outputStream = new FileOutputStream(url_imagen);
								int readBytes = 0;
								//int lengthBytes = (int)bdblob.length();
								byte[] buffer = equipos_imagenes.getListaEquiposImagenesDatos().get(i).getIsImage();								
								outputStream.write(buffer);								
								outputStream.close();
								equipos_imagenes.getListaEquiposImagenesDatos().get(i).setUrlImagen(path_img);
									
							}
							imagenes.setListaEquiposImagenesDatos(equipos_imagenes.getListaEquiposImagenesDatos());
						}
						catch(Exception e)
						{
							imagenes.setMensaje("FAULT");
							imagenes.setMensaje(e.getMessage());								
						}	
																	
					}
					else
					{
						imagenes.setMensaje("FAULT");
						imagenes.setMensaje("No existe imagen para ser mostrada");
					}
				}					
				
				coordenadas = ubicaciones.getUtInfSup(criterios);
				Collections.reverse(coordenadas.getUbicacionesList());
				
				//criterios.setI_charact("");
				//criterios.setI_deep_srch("");
				//criterios.setI_equnr_price("");
				//criterios.setI_coords("X");
				//criterios.setI_id_ut_current(sesionValida.getDatos(session,"unidad"));				
				
				//ubicacionDto=ubicaciones.getUtInfSup(criterios);
				
				//if(ubicacionDto.getMensaje().equals("SUCCESS"))
				//{					
					//int uts = ubicacionDto.getUbicacionesList().size();
					//if(uts>0)
					//{
						//for(int i=0; i<uts; i++)
						//{
							//critImanegMap.setIdUbicacion(validaSesion.getDatos(session,"unidad"));
							//imagenes=datosImageMap.getUTForImage(critImanegMap);
							//int iuts = imagenes.getListaImagenDatos().size(); 
							
							//if(imagenes.getMensaje().equals("SUCCESS"))
							//{
								//if(iuts>0)
								//{
									//for(int j=0; j<iuts; j++)
									//{
										
									//}
								//}
							//}						
						//}				
					//}
				//}

				resAction.setRespGetUnidadesTecnicasSuperiores(coordenadas);
				resAction.setRespDatosImagen(imagenes);
			}
			catch (ViviendaException e) 
			{			
				//e.getMensajeError();
				//resUDMD.setMensaje("FAULT");
				//resUDMD.setMensaje(e.getMensajeError());
				//resAction.setRespDatosImagen(resUDMD);
			}		
			
			try
			{
				if(datosImagenVo.getNombreImagen()==null)
				{
					datosImagenVo.setNombreImagen("sinimagenUTA101010.png");
				}
			}
			catch(Exception e)
			{
				datosImagenVo.setNombreImagen("sinimagenUTA101010.png");
			}
			
			try
			{
				List <EqCaracteristicasDto> ecar = resAction.getRespGetUnidadesTecnicasSuperiores().getEqCaracteristicasList();
				List <EqCaracteristicasDto> ecarn= new ArrayList<EqCaracteristicasDto>();
				for(int f=0; f<ecar.size(); f++)
				{					
					if(ecar.get(f).getCharact().equals("LOTE") || ecar.get(f).getCharact().equals("M2JA") || ecar.get(f).getCharact().equals("M2PR") || ecar.get(f).getCharact().equals("M2TE") || ecar.get(f).getCharact().equals("NIVEL") || ecar.get(f).getCharact().equals("ORIE") || ecar.get(f).getCharact().equals("STUNX") || ecar.get(f).getCharact().equals("TIPO") || ecar.get(f).getCharact().equals("VISA") || ecar.get(f).getCharact().equals("Z1NUM_B") || ecar.get(f).getCharact().equals("Z1NUM_E") || ecar.get(f).getCharact().equals("Z3TOTAL_AREAS"))
					{
						ecarn.add(ecar.get(f));
					}
				}
				
				resAction.getRespGetUnidadesTecnicasSuperiores().setEqCaracteristicasList(new ArrayList<EqCaracteristicasDto>());
				resAction.getRespGetUnidadesTecnicasSuperiores().setEqCaracteristicasList(ecarn);
			}
			catch(Exception e)
			{
				
			}
			map.put("equipos",coordenadas.getEquiposList());
			map.put("renders",resAction.getRespDatosImagen().getListaEquiposImagenesDatos());
			map.put("imagen",datosImagenVo);
			map.put("allcoords",resAction.getRespGetUnidadesTecnicasSuperiores().getCoordenadasList());
			map.put("detallequipo",resAction.getRespGetUnidadesTecnicasSuperiores().getEqCaracteristicasList());
			return redir;
	}

	
	@RequestMapping(value="/UbicacionTSGrid.htm",method=RequestMethod.POST)
	public @ResponseBody ResponseUbicacionesActionDto postUbicacionesTSGrid(@ModelAttribute(value="ubicacionesMainMap")CriteriosUbicacionesDto criterios, BindingResult result, HttpServletRequest request, HttpSession session){        
		ResponseUbicacionesActionDto resAction = new ResponseUbicacionesActionDto();
		ResponseGetUtInfSupDto pisosgrid = new ResponseGetUtInfSupDto();	
		
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		
		if(!sesionValida.validaSesion(session)) {
			pisosgrid.setMensaje("LOGOUT");
			pisosgrid.setDescripcion("");
			resAction.setRespGetUnidadesTecnicasSuperiores(pisosgrid);
			return resAction;
		}
		
		criterios.setI_usuario(sesionValida.getDatos(session,"usuario"));
		criterios.setI_id_ut_sup(sesionValida.getDatos(session,"unidad"));
		criterios.setI_charact("");		
		try
		{			
			if (criterios.getAccion().equals("pisos"))
			{
				criterios.setI_charact("X");
				criterios.setI_deep_srch("X");
				criterios.setI_equnr_price("X");
				criterios.setI_coords("");
				criterios.setI_coords_sup("");
				pisosgrid = ubicaciones.getUtInfSup(criterios);				
				
				//imagenes = datosImageMap.getImagenEquipo(criterios)
				
				if(pisosgrid.getMensaje().equals("SUCCESS"))
				{
					Collections.reverse(pisosgrid.getUbicacionesList());
					resAction.setRespGetUnidadesTecnicasSuperiores(pisosgrid);
				}				
			}			
		}		
		catch (ViviendaException e) 
		{			
			pisosgrid.setMensaje("FAULT");
			pisosgrid.setMensaje(e.getMensajeError());
			resAction.setRespGetUnidadesTecnicasSuperiores(pisosgrid);
		}
		
		return resAction;
	}	
	
	@RequestMapping(value="/UploadTiposEquipos.htm", method= RequestMethod.GET)
	public String returnMapeoImagenes(ModelMap map, CriteriosUbicacionesDto criterios, HttpServletRequest request, HttpSession session) {
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();
		ResponseUbicacionTecnicaDto respUbicacion = new ResponseUbicacionTecnicaDto(); 
		
		String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "UploadTiposEquipos";
		 }
		 respUbicacion = new ResponseUbicacionTecnicaDto();
			
			/*try
			{
				respUbicacion=ubicaciones.getUbicaciones();
				if(respUbicacion.getMensaje().equals("SUCCESS"))
				{
					map.put("ubicacionObject", respUbicacion);
					map.put("objIdUnidad", respUbicacion.getUbicacionesList().get(0).getUbicacionClave());
				}
				else
				{
					System.out.println(respUbicacion.getDescripcion());
				}
			}
			catch (ViviendaException e) 
			{			
				e.getMensajeError();
			}*/
			
		 return redir;
		 
	 }

	
	@RequestMapping(value="/UploadTiposEquipos.htm",method=RequestMethod.POST)
	public @ResponseBody ResponseUbicacionesActionDto uploadImageEquipos(@ModelAttribute(value="fileupRender")CriteriosFileUploadDto criterios, BindingResult result, HttpServletRequest request, HttpServletResponse response, HttpSession session){
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();		
		ResponseUbicacionDatosMapaDto respValidaImagenEquipo= new ResponseUbicacionDatosMapaDto();
		ResponseUbicacionDatosMapaDto responseTipoEquipos= new ResponseUbicacionDatosMapaDto();
		ResponseUbicacionesActionDto resAction = new ResponseUbicacionesActionDto();
		CriteriosDatosMapaImagenDto criteriosImagenEq = new CriteriosDatosMapaImagenDto();	
		
		if(!sesionValida.validaSesion(session)) {
			responseTipoEquipos.setMensaje("LOGOUT");
			responseTipoEquipos.setDescripcion("");
			resAction.setRespDatosImagen(responseTipoEquipos);
			return resAction;
		}
		
		if(criterios.getAccion().equals("getTiposEquipos"))
		{
			try
			{
				CriteriosEquipoTiposDto critTipos = new CriteriosEquipoTiposDto();
				critTipos.setI_usuario(sesionValida.getDatos(session,"usuario"));
				critTipos.setI_id_fase(criterios.getIdUbicacion());
				critTipos.setI_id_ut_sup(sesionValida.getDatos(session,"unidad"));
				
				responseTipoEquipos=ubicaciones.getTiposEquipos(critTipos);
					
					if(responseTipoEquipos.getMensaje().equals("SUCCESS"))
					{
						
						criteriosImagenEq.setIdUTS(criterios.getIdUbicacion());
						//try
						//{
							if(criterios.getTipo()==null)
							{
								if(responseTipoEquipos.getListaTipos().size()>0)
								{	
									criteriosImagenEq.setTipo(responseTipoEquipos.getListaTipos().get(0).getI_tipo_eq());
								}
								else
								{
									criteriosImagenEq.setTipo(criterios.getTipo());
								}
							}
							else
							{
								criteriosImagenEq.setTipo(criterios.getTipo());
							}
						/*}
						catch(Exception e){	
							if(responseTipoEquipos.getListaTipos().size()>0)
							{
								criteriosImagenEq.setTipo(responseTipoEquipos.getListaTipos().get(0).getI_tipo_eq());
							}
						}*/
											
						respValidaImagenEquipo=datosImageMap.validaExisteImagenEquipo(criteriosImagenEq);
						
						if(respValidaImagenEquipo.getMensaje().equals("SUCCESS"))
						{
							try
							{
								if(respValidaImagenEquipo.getListaEquiposImagenesDatos().size()>0)
								{
									responseTipoEquipos.setDescripcion("Esta unidad tecnica ya cuenta con imagen");
								}
								else
								{
									responseTipoEquipos.setDescripcion("");
								}
							}
							catch(Exception e){	
								responseTipoEquipos.setDescripcion("");
							}							
						}
						else
						{
							responseTipoEquipos.setDescripcion("");
						}
					}
					
			}							
			catch (ViviendaException e) 
			{			
				//e.getMensajeError();
				resAction.getRespDatosImagen().setDescripcion(e.getMensajeError());
			}
		}
		else if(criterios.getAccion().equals("validaEquipos"))
		{
			try
			{
				criteriosImagenEq.setIdUTS(criterios.getIdUbicacion());
				criteriosImagenEq.setTipo(criterios.getTipo());
				respValidaImagenEquipo=datosImageMap.validaExisteImagenEquipo(criteriosImagenEq);				
				
				try
				{
					if(respValidaImagenEquipo.getListaEquiposImagenesDatos().size()>0)
					{
						responseTipoEquipos.setDescripcion("Esta unidad tecnica ya cuenta con imagen");
					}
					else
					{
						responseTipoEquipos.setDescripcion("");
						responseTipoEquipos.setMensaje("SUCCESS");
					}
				}
				catch(Exception e){	
					responseTipoEquipos.setDescripcion("");
					responseTipoEquipos.setMensaje("SUCCESS");
				}							
				
			}
			catch (ViviendaException e) 
			{			
				responseTipoEquipos.setDescripcion(e.mensajeError);
				responseTipoEquipos.setMensaje("FAULT");
			}
		}
		
		resAction.setRespDatosImagen(responseTipoEquipos);
		
		return resAction;		
	}
	
	@RequestMapping(value="/RespuestaAdminImagenesEq.htm", method= RequestMethod.GET)
	public String getRespuestaAdminImagenesEq(ModelMap map, HttpSession session) {
	return "RespuestaAdminImagenesEq";
	}

	
	@RequestMapping(value="/RespuestaAdminImagenesEq.htm",method=RequestMethod.POST)
	public String postImagenesEq(@ModelAttribute(value="vImagenesEq")CriteriosFileUploadDto criterios, BindingResult result, HttpServletRequest request, HttpSession session, Map model){        
		CriteriosDatosMapaImagenDto criteriosMaping = new CriteriosDatosMapaImagenDto();
		ResponseUbicacionDatosMapaDto responseInsertImageRender= new ResponseUbicacionDatosMapaDto();
		ResponseUbicacionDatosMapaDto respValidaImagenEquipo= new ResponseUbicacionDatosMapaDto();
		ResponseUbicacionesActionDto resAction = new ResponseUbicacionesActionDto();
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		CriteriosDatosMapaImagenDto criteriosImagenEq = new CriteriosDatosMapaImagenDto();
		MultipartFile file = criterios.getFileData();
		UUID uidImage = UUID.randomUUID();
		
		if(!sesionValida.validaSesion(session))
		 {
			 return "../../index";
		 }
		
		try
		{
			
			criteriosImagenEq.setIdUTS(criterios.getIdUbicacion());
			criteriosImagenEq.setTipo(criterios.getTipo());
			respValidaImagenEquipo=datosImageMap.validaExisteImagenEquipo(criteriosImagenEq);				
			
			try
			{
				if(respValidaImagenEquipo.getListaEquiposImagenesDatos().size()>0)
				{
					String [] ni=file.getOriginalFilename().split("\\.");
					ni[0]=uidImage.toString();
					criteriosMaping.setImagenMaping(file);
					criteriosMaping.setNombreImagen(ni[0]+"."+ni[1]);
					criteriosMaping.setIdUTS(criterios.getIdUbicacion());
					criteriosMaping.setTipo(criterios.getTipo());
					
					responseInsertImageRender=datosImageMap.setUpdateEquipoTipoImagen(criteriosMaping);
				}
				else
				{
					String [] ni=file.getOriginalFilename().split("\\.");
					ni[0]=uidImage.toString();
					criteriosMaping.setImagenMaping(file);
					criteriosMaping.setNombreImagen(ni[0]+"."+ni[1]);
					criteriosMaping.setIdUTS(criterios.getIdUbicacion());
					criteriosMaping.setTipo(criterios.getTipo());
					
					responseInsertImageRender=datosImageMap.setEquipoTipoImagen(criteriosMaping);
				}
			}
			catch(Exception e){	
				
			}
			
			
			
			resAction.setRespDatosImagen(responseInsertImageRender);
							
		}
		catch (ViviendaException e) 
		{			
			responseInsertImageRender.setDescripcion(e.mensajeError);
			resAction.setRespDatosImagen(responseInsertImageRender);
		}
		model.put("objectDatosImagen", resAction.getRespDatosImagen());
	    return "RespuestaAdminImagenesEq"; 
	}
	
	
	@RequestMapping(value="/MapeoUbicacionSimilares.htm", method= RequestMethod.GET)
	public String getRespuestaUbicacionesSimilares(ModelMap map, CriteriosUbicacionesDto criterios, HttpServletRequest request, HttpSession session) {
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();
		String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "MapeoUbicacionSimilares";
		 }
		 
		 return redir;
	}

	
	@RequestMapping(value="/MapeoUbicacionSimilares.htm",method=RequestMethod.POST)
	public @ResponseBody ResponseUbicacionesActionDto postMapeoUbicacionesSimilares(@ModelAttribute(value="ubicacionesMainMap")CriteriosUbicacionesDto criterios, BindingResult result, HttpServletRequest request, HttpSession session){        
		ResponseUbicacionesActionDto resAction = new ResponseUbicacionesActionDto();   
		ResponseUbicacionDatosMapaDto respUbicacion = new ResponseUbicacionDatosMapaDto(); 
		ResponseUbicacionDatosMapaDto addCoords = new ResponseUbicacionDatosMapaDto(); 
		CriteriosDatosMapaImagenDto critDBMAp = new CriteriosDatosMapaImagenDto();
		CriteriosDatosMapaImagenDto critDatosMapa = new CriteriosDatosMapaImagenDto();
		ResponseGetUtInfSupDto pisosReplica = new ResponseGetUtInfSupDto();
		SessionValidatorSTS validaSesion = new SessionValidatorSTS();
		CriteriosUbicacionesDto cudto_replica=new CriteriosUbicacionesDto();
		ResponseUbicacionDatosMapaDto responseValidaImagenMapeo= new ResponseUbicacionDatosMapaDto();
		ResponseUbicacionDatosMapaDto responseInsertImagenMapeo= new ResponseUbicacionDatosMapaDto();
		ResponseUbicacionDatosMapaDto responseDeleteImagenMapeo= new ResponseUbicacionDatosMapaDto();
		
		critDBMAp.setIdUbicacion(criterios.getI_id_ut_current());		
		
		if(!validaSesion.validaSesion(session)) {
			addCoords.setMensaje("LOGOUT");
			addCoords.setDescripcion("");
			responseInsertImagenMapeo.setMensaje("LOGOUT");
			responseInsertImagenMapeo.setDescripcion("");
			responseDeleteImagenMapeo.setMensaje("LOGOUT");
			responseDeleteImagenMapeo.setDescripcion("");
			resAction.setAddCoords(addCoords);
			resAction.setResponseDeleteImagenMapeo(responseInsertImagenMapeo);
			resAction.setResponseInsertImagenMapeo(responseDeleteImagenMapeo);
			return resAction;
		}
		
		try
		{
			respUbicacion=datosImageMap.getUTForImage(critDBMAp);
			
			if(respUbicacion.getMensaje().equals("SUCCESS"))
			{
				criterios.setI_usuario(validaSesion.getDatos(session,"usuario"));
				criterios.setI_id_ut_sup(validaSesion.getDatos(session,"unidad"));
				criterios.setI_charact("X");
				criterios.setI_deep_srch("");
				criterios.setI_equnr_price("X");
				criterios.setI_coords("X");
				criterios.setI_coords_sup("");
				// ,5005PSLF0205,5005PSLF0206
				String ubicacionesSeleccionadas=criterios.getI_list_coord();				
				String ubicaciones_sel[] = ubicacionesSeleccionadas.split("\\|");
							
				pisosReplica=ubicaciones.getUtInfSup(criterios);
				if(pisosReplica.getMensaje().equals("SUCCESS"))
				{
				
					for(int i=0; i<ubicaciones_sel.length; i++){
						
						//criterios.setI_id_ut_current(ubicaciones_sel[i]);
						String sp_uts[] = new String[2];
						sp_uts[0]= ubicaciones_sel[i].substring(0,7);
						sp_uts[1]= ubicaciones_sel[i].substring(8,ubicaciones_sel[i].length());
						//setCoordenadasImagen
						
							for(int j=0; j<pisosReplica.getCoordenadasList().size(); j++)
							{
								String repCad = pisosReplica.getCoordenadasList().get(j).getUteq(); //ubicaciones_sel[i];
								String splUTEQ[] = new String[2];
								splUTEQ[0]= repCad.substring(0,7);
								splUTEQ[1]= repCad.substring(8,repCad.length());
								String newUteq = splUTEQ[0]+"F"+splUTEQ[1].replaceFirst(splUTEQ[1].substring(0,4), sp_uts[1].substring(0,4));
								String[] utssp=newUteq.split("-");
								String piso_depto="";
								String uts_replace="";
								String resultado="";
								
								if(utssp[1].length()==4)
								{
									int piso_encontrado=Integer.parseInt(sp_uts[1].substring(2,4));
									uts_replace = piso_encontrado+"";
									piso_depto=utssp[1].substring(1, 2);
									resultado=utssp[1].replaceFirst(piso_depto,uts_replace);
								}
								else
								{
									int piso_encontrado=Integer.parseInt(sp_uts[1].substring(2,4));
									uts_replace = piso_encontrado+"";
									piso_depto=utssp[1].substring(1, 3);
									resultado=utssp[1].replaceFirst(piso_depto,uts_replace);
								}
								
								cudto_replica.setI_charact("");
								cudto_replica.setI_deep_srch("");
								cudto_replica.setI_equnr_price("");
								cudto_replica.setI_coords(pisosReplica.getCoordenadasList().get(j).getCoord());
								cudto_replica.setI_usuario(validaSesion.getDatos(session,"usuario"));
								cudto_replica.setI_id_ut_sup(validaSesion.getDatos(session,"unidad"));
								cudto_replica.setI_id_ut_current(utssp[0]+"-"+resultado);
								UUID uidImage = UUID.randomUUID();
								
								if(respUbicacion.getListaImagenDatos().size()>=1)
								{
									try
									{											
										String [] ni=respUbicacion.getListaImagenDatos().get(0).getNombreImagen().split("\\.");
										ni[0]=uidImage.toString();
										critDatosMapa.setNombreImagen(ni[0]+"."+ni[1]);
										critDatosMapa.setBlobImage(respUbicacion.getListaImagenDatos().get(0).getBlobImage());
										critDatosMapa.setIsImage(respUbicacion.getListaImagenDatos().get(0).getIsImage());
										critDatosMapa.setIdUbicacion(utssp[0]+"-"+resultado);	
										critDatosMapa.setIdUTS(utssp[0]);
										critDatosMapa.setTipo("copy");
										resAction.setResponseValidaImagenMapeo(responseValidaImagenMapeo=datosImageMap.getValidaDatosImagen(critDatosMapa));
										
										if(responseValidaImagenMapeo.getDescripcion().equals("SD"))
										{	
											resAction.setResponseDeleteImagenMapeo(responseDeleteImagenMapeo=datosImageMap.deleteImageDatosMaping(critDatosMapa));
											if(responseDeleteImagenMapeo.getMensaje().equals("SUCCESS"))
											{									
												resAction.setResponseInsertImagenMapeo((responseInsertImagenMapeo=datosImageMap.createImageDatosMaping(critDatosMapa)));												
												
												if(responseInsertImagenMapeo.getMensaje().equals("SUCCESS"))
												{
													resAction.setAddCoords((addCoords = ubicaciones.setCoordenadasImagen(cudto_replica)));
													if(addCoords.getMensaje().equals("SUCCESS"))
													{
														// System.out.println("OK");
														if(addCoords.getMensaje().equals("SUCCESS"))
														{															
															try
															{
																respUbicacion.getListaImagenDatos().get(0).getNombreImagen();
																//System.out.println(addCoords.getDescripcion());
															}
															catch(Exception e){
																log.error("ERROR: ",e);
															}
														}
													}													
												}
											}
										}
									}
									catch(Exception e){										
										
									}								
									
									
								}
								else
								{
									
								}						
							}
						}
					}		
				//respUbicacion=ubicaciones.setReplicaPisos(criterios);
				
			}
			
		}
		catch (ViviendaException e) 
		{			
			log.error("ERROR: ",e);
		}
	    return resAction; 
	}
	
}
