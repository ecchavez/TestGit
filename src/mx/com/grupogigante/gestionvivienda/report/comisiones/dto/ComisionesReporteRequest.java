package mx.com.grupogigante.gestionvivienda.report.comisiones.dto;

public class ComisionesReporteRequest {

	private String unidadTecnicaSuperior;
	private String idUsuario;

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
	public ComisionesReporteRequest() {
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
	
}
