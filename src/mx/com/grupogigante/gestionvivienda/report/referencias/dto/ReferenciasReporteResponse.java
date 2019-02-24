package mx.com.grupogigante.gestionvivienda.report.referencias.dto;

import java.util.List;

public class ReferenciasReporteResponse {
	private List<ReferenciasReporteRowDto> listaReferencias;
	private String mensaje;
	private String descripcion;

	public ReferenciasReporteResponse() {
		super();
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

	public List<ReferenciasReporteRowDto> getListaReferencias() {
		return listaReferencias;
	}

	public void setListaReferencias(List<ReferenciasReporteRowDto> listaReferencias) {
		this.listaReferencias = listaReferencias;
	}
	
	
}
