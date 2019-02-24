package mx.com.grupogigante.gestionvivienda.utils;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.com.grupogigante.gestionvivienda.domain.dto.PermisosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto;

public class SessionValidatorSTS {
	
	public Boolean validaSesion (HttpSession session)
	{	 
		 Boolean respuesta=false;		 		 
		 
		 try
		 {
			 UsuarioDto sesionUser = (UsuarioDto) session.getAttribute("usrSession");
			 if(sesionUser.getUsuario().equals(""))
			 {
				 respuesta = false;
			 }
			 else
			 {
				 respuesta = true;
			 }
		 }
		 catch(Exception e)
		 {
			 respuesta = false;
		 }		 
		 return respuesta;
	}
	
	public  String getDatos(HttpSession session, String element)
	{
		UsuarioDto sesionUser = (UsuarioDto) session.getAttribute("usrSession");
		String result="";
		ArrayList<PermisosDto> catalogoPermisos = new ArrayList<PermisosDto>();	
		
		if(element.equals("usuario"))
		{
			result=sesionUser.getUsuario();			
		}
		else if(element.equals("unidad"))
		{
			result=sesionUser.getId_ut_sup();
		}
		else if(element.equals("unidadx"))
		{
			result=sesionUser.getId_ut_sup_cm();
		}
		else if(element.equals("usrisinit"))
		{
			catalogoPermisos=sesionUser.getCatalogoPermisos();
			if(catalogoPermisos.size()>=1)
			{
				if(catalogoPermisos.get(0).getE_usr_init().equals(sesionUser.getUsuario()))
				{
					result=catalogoPermisos.get(0).getE_usr_init();
				}
				else
				{
					result="";
				}
			}
			else
			{
				result="";
			}
		}
		else if(element.equals("url"))
		{
			result=sesionUser.getPathrel();			
		}
		else
		{
			result = "";
		}
		
		
		
		return result;
	}
	
	public ArrayList<PermisosDto> getPermisos(HttpSession session)
	{
		UsuarioDto sesionUser = (UsuarioDto) session.getAttribute("usrSession");
		ArrayList<PermisosDto> catalogoPermisos = new ArrayList<PermisosDto>();		
		catalogoPermisos=sesionUser.getCatalogoPermisos();
		
		return catalogoPermisos;
	}
	
	public String setDestruyeSesion(HttpSession session, HttpServletRequest request)
	{
		session.removeAttribute("usrSession");
		session.removeAttribute("flagContratoSession");
		session.removeAttribute("registrarArchivoPago");
		session.invalidate();
		return "SUCCESS";
	}
}
