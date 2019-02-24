package mx.com.grupogigante.gestionvivienda.controllers.usuarios;

import java.io.FileOutputStream;
import java.io.StringReader;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.PermisosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseAddUsuarioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseDelUsuarioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionTecnicaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUserActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseValidaLoginDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.resources.ExtraProperties;
import mx.com.grupogigante.gestionvivienda.services.IUbicacionesService;
import mx.com.grupogigante.gestionvivienda.services.IUsuariosService;
import mx.com.grupogigante.gestionvivienda.services.UbicacionesService;
import mx.com.grupogigante.gestionvivienda.services.UsuariosService;
import mx.com.grupogigante.gestionvivienda.utils.Connection;
import mx.com.grupogigante.gestionvivienda.utils.SessionValidatorSTS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;



@Controller
public class UsuariosController { 
	@Autowired
	IUbicacionesService ubicaciones;
	@Autowired
	IUsuariosService usuarios;
	private ResponseUbicacionTecnicaDto respUbicacion; 
	
	@RequestMapping(value="/Login.htm", method= RequestMethod.GET)
	public String returnForm(ModelMap map) throws ViviendaException {
		respUbicacion = new ResponseUbicacionTecnicaDto();
		    
	    try
		{
	    	Connection.setCloseConnect();
	    	Connection.setConnect(null);	    	
		}
		catch(ViviendaException e)
		{
			respUbicacion.setMensaje("FAULT");
			respUbicacion.setDescripcion(e.getMessage());
			respUbicacion.setUbicacionesList(null);
		}
	    
	    try
	    {
	    	respUbicacion=ubicaciones.getUbicaciones();	
	    	ExtraProperties exp = new ExtraProperties();
	    	//exp.getExtraProperties("logs");
		}
	    catch(ViviendaException e)
		{
			respUbicacion.setMensaje("FAULT");
			respUbicacion.setDescripcion(e.getMessage());
			respUbicacion.setUbicacionesList(null);
		}	    	 
	    map.put("ubicacionObject", respUbicacion);
		return "Login";
	 }

	
	@RequestMapping(value="/Login.htm",method=RequestMethod.POST)
	public @ResponseBody ResponseValidaLoginDto login(@ModelAttribute(value="validarUsuario")UsuarioDto usuario, BindingResult result, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ViviendaException{
		ResponseValidaLoginDto res = new ResponseValidaLoginDto();
		ResponseGetUsuariosDto respgetusr = new ResponseGetUsuariosDto();
		CriteriosUsuariosDto usuarioc= new CriteriosUsuariosDto();
		String path = request.getContextPath();
		String basePath = "";
		
		if(request.getServerPort()==80)
		{
			basePath = "//"+request.getServerName()+"/vivienda";
		}
		else
		{
			basePath = "//"+request.getServerName()+":"+request.getServerPort()+"/vivienda";
		}
		
		try
		{
			res = usuarios.validaLogin(usuario);
			if(res.getMensaje().equals("SUCCESS"))
			{
				String utsx = usuario.getId_ut_sup_cm();
				usuarioc.setUsuario(usuario.getUsuario());
				usuarioc.setId_ut_sup(usuario.getId_ut_sup());
				usuarioc.setAccion("permisos");				
				respgetusr= usuarios.getUsuarios(usuarioc);				
				if(respgetusr.getUsuariosList() != null && respgetusr.getUsuariosList().size()>=1)
				{
					usuario=respgetusr.getUsuariosList().get(0);
					usuario.setId_ut_sup_cm(utsx);
				}
				//usuario.setCatalogoPermisos(usuarios.getCatalogoPermisos(usuario.getUsuario(),usuario.getId_ut_sup()));	
				usuario.setCatalogoPermisos((ArrayList<PermisosDto>)respgetusr.getPermisosList());
				usuario.setPermisosUserList(respgetusr.getPermisosUserList());
				usuario.setPathrel(basePath);
				session.setAttribute("usrSession", usuario);	
				session.setAttribute("flagContratoSession", res.getFlagContrato());
				res.setMensaje("SUCCESS");
				res.setUsuario(usuario);
			}
		}
		catch (ViviendaException e) 
		{			
			res.setMensaje("FAULT");
			res.setDescripcion(e.getMessage());
		}		
		
		return res;
	}
	
	@RequestMapping(value="/CatalogoUsuarios.htm", method= RequestMethod.GET)
	public String returnCatUser(ModelMap map, HttpSession session) throws ViviendaException {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "CatalogoUsuarios";
		 }
		 return redir;
	 }
	
	@RequestMapping(value="/CatalogoUsuarios.htm",method=RequestMethod.POST)
	public @ResponseBody ResponseUserActionDto controllerCatUsuarios(@ModelAttribute(value="traerUsuarios")CriteriosUsuariosDto usuario, BindingResult result, HttpSession session) throws ViviendaException{
		
		ResponseUserActionDto resUser = new ResponseUserActionDto();
		ResponseGetUsuariosDto res = new ResponseGetUsuariosDto();
		ResponseAddUsuarioDto resAddUser = new ResponseAddUsuarioDto();
		ResponseDelUsuarioDto resDelUser = new ResponseDelUsuarioDto();
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		
		if(!sesionValida.validaSesion(session)) {
			res.setMensaje("LOGOUT");
			res.setDescripcion("");
			resAddUser.setMensaje("LOGOUT");
			resAddUser.setDescripcion("");
			resUser.setRespGetUsuariosDto(res);
			resUser.setRespGetUsuariosDto(res);
			resUser.setRespAddUsuarioDto(resAddUser);
			return resUser;
		}
		
		if(sesionValida.validaSesion(session))
		{
			
			usuario.setUsuario(sesionValida.getDatos(session,"usuario"));
			usuario.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
			
			if(sesionValida.getDatos(session,"usrisinit").equals(""))
			{			
				try
				{
					if(usuario.getAccion().equals("getUsuarios"))
					{								
						res = usuarios.getUsuarios(usuario);
						resUser.setRespGetUsuariosDto(res);
					}
					
					else if(usuario.getAccion().equals("updUsuario"))
					{
						usuario.setId_ut_sup_cm(sesionValida.getDatos(session,"unidad"));						
						resAddUser=usuarios.addUsuario(usuario);
						resUser.setRespAddUsuarioDto(resAddUser);
					}
					else if(usuario.getAccion().equals("delUsuario"))
					{
						usuario.setId_ut_sup_cm(sesionValida.getDatos(session,"unidad"));
						resDelUser=usuarios.delUsuario(usuario);
						resUser.setRespDelUsuarioDto(resDelUser);
					}
					
				}
				catch (ViviendaException e) {
					resUser.getRespGetUsuariosDto().setMensaje("FAULT");
					resUser.getRespGetUsuariosDto().setDescripcion(e.getMessage());
				}				
			}
			else
			{
				res = new ResponseGetUsuariosDto();
				res.setObjPermisosList(sesionValida.getPermisos(session));
				res.setPermisosList(sesionValida.getPermisos(session));
				resUser.setRespGetUsuariosDto(res);				
				resUser.getRespGetUsuariosDto().setMensaje("INICIAL");
				resUser.getRespGetUsuariosDto().setDescripcion("Este es un usuario inicial");
			}
		}
		else
		{
			// sesion expirada
		}
		return resUser;
	}
	
	@RequestMapping(value="/AddNuevoUsuario.htm", method= RequestMethod.GET)
	public String returnAddUsuario(ModelMap map, HttpSession session) throws ViviendaException {
		 SessionValidatorSTS validaSesion = new SessionValidatorSTS();	
		 String redir = "";
		 if(!validaSesion.validaSesion(session))
		 {
			 redir = "../../index";
		 }
		 else
		 {
			 redir = "AddNuevoUsuario";
		 }
		 return redir;
	 }
	
	@RequestMapping(value="/AddNuevoUsuario.htm",method=RequestMethod.POST)
	public @ResponseBody ResponseUserActionDto controllerAddUsuarios(@ModelAttribute(value="addUsuarios")CriteriosUsuariosDto usuario, BindingResult result, HttpSession session)throws ViviendaException{
		ResponseUserActionDto resUser = new ResponseUserActionDto();
		ResponseGetUsuariosDto res = new ResponseGetUsuariosDto();
		ResponseAddUsuarioDto resAddUser = new ResponseAddUsuarioDto();
		ArrayList<PermisosDto> catPermisos;
		respUbicacion = new ResponseUbicacionTecnicaDto();
		
		SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		
		if(!sesionValida.validaSesion(session)) {
			res.setMensaje("LOGOUT");
			res.setDescripcion("");
			resAddUser.setMensaje("LOGOUT");
			resAddUser.setDescripcion("");
			resUser.setRespGetUsuariosDto(res);
			resUser.setRespUbicacionTecnicaDto(respUbicacion);
			return resUser;
		}
		
		usuario.setUsuario(sesionValida.getDatos(session,"usuario"));
		usuario.setId_ut_sup(sesionValida.getDatos(session,"unidad"));
		
		try
		{						
			if(usuario.getAccion().equals("addUsuario"))
			{	
				usuario.setId_ut_sup_cm(sesionValida.getDatos(session,"unidad"));
				
				resAddUser=usuarios.addUsuario(usuario);
				resUser.setRespAddUsuarioDto(resAddUser);
			}
			else if(usuario.getAccion().equals("getDataInit"))
			{			
				usuario.setId_ut_sup_cm(sesionValida.getDatos(session,"unidad"));	
				catPermisos=usuarios.getCatalogoPermisos(sesionValida.getDatos(session,"usuario"), sesionValida.getDatos(session,"unidad"));
				res.setPermisosList(catPermisos);
				res.setObjPermisosList(res.getPermisosList());
				respUbicacion=ubicaciones.getUbicaciones();
				
				if(respUbicacion.getMensaje().equals("SUCCESS"))
				{
					resUser.setRespGetUsuariosDto(res);
					resUser.setRespUbicacionTecnicaDto(respUbicacion);
				}
			}
		}
		catch (ViviendaException e) 
		{					
			resUser.getRespGetUsuariosDto().setMensaje("FAULT");
			resUser.getRespGetUsuariosDto().setDescripcion(e.getMessage());
		}		
		return resUser;
	}

}
