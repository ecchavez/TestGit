package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.io.InputStream;

public class ResponseFileConnect {
	private String mensaje;
	private String descripcion;
	private InputStream fileConnect;
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public InputStream getFileConnect() {
		return fileConnect;
	}
	public void setFileConnect(InputStream fileConnect) {
		this.fileConnect = fileConnect;
	}
}
