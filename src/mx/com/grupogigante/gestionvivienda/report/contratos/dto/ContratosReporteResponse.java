package mx.com.grupogigante.gestionvivienda.report.contratos.dto;

import java.util.List;

import mx.com.grupogigante.gestionvivienda.domain.dto.EstatusFiltroReporteDto;

public class ContratosReporteResponse {
	private List<ContratosReporteRowDto> listaContrato;
	private List<EstatusFiltroReporteDto> listaEstatus;
	
	private String mensaje;
	private String descripcion;

	public ContratosReporteResponse() {
		super();
	}

	public List<ContratosReporteRowDto> getListaContrato() {
		return listaContrato;
	}

	public List<EstatusFiltroReporteDto> getListaEstatus() {
		return listaEstatus;
	}

	public void setListaEstatus(List<EstatusFiltroReporteDto> listaEstatus) {
		this.listaEstatus = listaEstatus;
	}

	public void setListaContrato(
			List<ContratosReporteRowDto> listaContrato) {
		this.listaContrato = listaContrato;
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
