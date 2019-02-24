package mx.com.grupogigante.gestionvivienda.report.disponibilidad.dto;

import java.util.List;

public class DisponibilidadReporteResponse {
	private List<DisponibilidadReporteRowDto> listaDisponibilidad;
	private String mensaje;
	private String descripcion;

	public DisponibilidadReporteResponse() {
		super();
	}

	public List<DisponibilidadReporteRowDto> getListaDisponibilidad() {
		return listaDisponibilidad;
	}

	public void setListaDisponibilidad(
			List<DisponibilidadReporteRowDto> listaDisponibilidad) {
		this.listaDisponibilidad = listaDisponibilidad;
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
