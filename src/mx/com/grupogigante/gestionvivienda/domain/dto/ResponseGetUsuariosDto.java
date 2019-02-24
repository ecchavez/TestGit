package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseGetUsuariosDto {
	private List<UsuarioDto> usuariosList = new ArrayList<UsuarioDto>();
	private List<PermisosDto> permisosList = new ArrayList<PermisosDto>();
	private List<PermisoUsuariosDto> permisosUserList = new ArrayList<PermisoUsuariosDto>();
	private Object objUsuariosList;
	private Object objPermisosList;
	private Object objPermisosUserList;
	private String mensaje;
	private String descripcion;
	
	public Object getObjUsuariosList() {
		return objUsuariosList;
	}
	public void setObjUsuariosList(Object objUsuariosList) {
		this.objUsuariosList = objUsuariosList;
	}
	public Object getObjPermisosList() {
		return objPermisosList;
	}
	public void setObjPermisosList(Object objPermisosList) {
		this.objPermisosList = objPermisosList;
	}
	public Object getObjPermisosUserList() {
		return objPermisosUserList;
	}
	public void setObjPermisosUserList(Object objPermisosUserList) {
		this.objPermisosUserList = objPermisosUserList;
	}	
	public List<PermisosDto> getPermisosList() {
		return permisosList;
	}
	public void setPermisosList(List<PermisosDto> permisosList) {
		this.permisosList = permisosList;
	}
	public List<PermisoUsuariosDto> getPermisosUserList() {
		return permisosUserList;
	}
	public void setPermisosUserList(List<PermisoUsuariosDto> permisosUserList) {
		this.permisosUserList = permisosUserList;
	}	
	public List<UsuarioDto> getUsuariosList() {
		return usuariosList;
	}
	public void setUsuariosList(List<UsuarioDto> usuariosList) {
		this.usuariosList = usuariosList;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
