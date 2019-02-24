package mx.com.grupogigante.gestionvivienda.domain.vo;

import java.util.ArrayList;

public class ViciosResponse {
	private String descripcion = null;
	private String mensaje = null;
	private ArrayList<String> texto;
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
	public ArrayList<String> getTexto() {
		return texto;
	}
	public void setTexto(ArrayList<String> texto) {
		this.texto = texto;
	}

	
}
