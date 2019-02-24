package mx.com.grupogigante.gestionvivienda.report.ventas.dto;

public class VentasReporteRequest {

	private String unidadTecnicaSuperior;
	private String idUsuario;
	private String idFase;
	private String idEquipo;
	private String idEstatus;
	private String idEquipoInicial;
	private String idEquipoFinal;
	
	
	public String getUnidadTecnicaSuperior() {
		return unidadTecnicaSuperior;
	}
	public void setUnidadTecnicaSuperior(String unidadTecnicaSuperior) {
		this.unidadTecnicaSuperior = unidadTecnicaSuperior;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getIdFase() {
		return idFase;
	}
	public void setIdFase(String idFase) {
		this.idFase = idFase;
	}
	public String getIdEquipo() {
		return idEquipo;
	}
	public void setIdEquipo(String idEquipo) {
		this.idEquipo = idEquipo;
	}
	public String getIdEstatus() {
		return idEstatus;
	}
	public void setIdEstatus(String idEstatus) {
		this.idEstatus = idEstatus;
	}
	public VentasReporteRequest() {
		super();
	}
	public String getIdEquipoInicial() {
		return idEquipoInicial;
	}
	public void setIdEquipoInicial(String idEquipoInicial) {
		this.idEquipoInicial = idEquipoInicial;
	}
	public String getIdEquipoFinal() {
		return idEquipoFinal;
	}
	public void setIdEquipoFinal(String idEquipoFinal) {
		this.idEquipoFinal = idEquipoFinal;
	}
	@Override
	public String toString() {
		return "VentasReporteRequest [unidadTecnicaSuperior="
				+ unidadTecnicaSuperior + ", idUsuario=" + idUsuario
				+ ", idFase=" + idFase + ", idEquipo=" + idEquipo
				+ ", idEstatus=" + idEstatus + ", idEquipoInicial="
				+ idEquipoInicial + ", idEquipoFinal=" + idEquipoFinal + "]";
	}
	
	
}
