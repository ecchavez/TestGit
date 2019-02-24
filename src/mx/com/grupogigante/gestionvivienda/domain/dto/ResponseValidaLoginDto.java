package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;

public class ResponseValidaLoginDto {
	private String mensaje;
	private String descripcion;
	private UsuarioDto usuario;
	private String url_conf;
	private String flagContrato;
	
	private ArrayList<PermisosDto> returnPermisos = new ArrayList<PermisosDto>();
	
	public String getUrl_conf() {
		return url_conf;
	}
	public void setUrl_conf(String url_conf) {
		this.url_conf = url_conf;
	}
	public ArrayList<PermisosDto> getReturnPermisos() {
		return returnPermisos;
	}
	public void setReturnPermisos(ArrayList<PermisosDto> returnPermisos) {
		this.returnPermisos = returnPermisos;
	}
	public UsuarioDto getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
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
	public String getFlagContrato() {
		return flagContrato;
	}
	public void setFlagContrato(String flagContrato) {
		this.flagContrato = flagContrato;
	}	
}
