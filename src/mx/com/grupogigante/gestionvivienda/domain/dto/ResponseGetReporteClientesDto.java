package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;

public class ResponseGetReporteClientesDto {
	private String mensaje;	
	private String descripcion;

	private ArrayList<ClientesReporteDto> listaReporteUsuarios = new ArrayList<ClientesReporteDto>();
	
	private Object objListaReporteUsuarios = new Object();
	
	public ArrayList<ClientesReporteDto> getListaReporteUsuarios() {
		return listaReporteUsuarios;
	}
	public void setListaReporteUsuarios(ArrayList<ClientesReporteDto> listaReporteUsuarios) {
		this.listaReporteUsuarios = listaReporteUsuarios;
	}
	public Object getObjListaReporteUsuarios() {
		return objListaReporteUsuarios;
	}
	public void setObjListaReporteUsuarios(Object objListaReporteUsuarios) {
		this.objListaReporteUsuarios = objListaReporteUsuarios;
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
