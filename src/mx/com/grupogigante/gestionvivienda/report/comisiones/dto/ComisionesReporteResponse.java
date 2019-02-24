package mx.com.grupogigante.gestionvivienda.report.comisiones.dto;

import java.util.List;

public class ComisionesReporteResponse {
	private List<ComisionesReporteRowDto> listaComision;
	private String mensaje;
	private String descripcion;

	public ComisionesReporteResponse() {
		super();
	}

	public List<ComisionesReporteRowDto> getListaComision() {
		return listaComision;
	}

	public void setListaComision(
			List<ComisionesReporteRowDto> listaComision) {
		this.listaComision = listaComision;
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
