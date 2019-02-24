package mx.com.grupogigante.gestionvivienda.report.ventas.dto;

import java.util.List;

public class VentasReporteResponse {
	private List<VentasReporteRowDto> listaVentas;
	private String mensaje;
	private String descripcion;

	public VentasReporteResponse() {
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

	public List<VentasReporteRowDto> getListaVentas() {
		return listaVentas;
	}

	public void setListaVentas(List<VentasReporteRowDto> listaVentas) {
		this.listaVentas = listaVentas;
	}
	
	
}
