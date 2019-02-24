package mx.com.grupogigante.gestionvivienda.report.listaprecio.dto;

import java.util.List;

public class ListaPrecioReporteResponseDto {
	private List<ListaPrecioRowFieldDto> listaPrecio;
	private String mensaje;
	private String descripcion;

	public ListaPrecioReporteResponseDto() {
		super();
	}

	public List<ListaPrecioRowFieldDto> getListaPrecio() {
		return listaPrecio;
	}

	public void setListaPrecio(List<ListaPrecioRowFieldDto> listaPrecio) {
		this.listaPrecio = listaPrecio;
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
