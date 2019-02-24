package mx.com.grupogigante.gestionvivienda.report.listaprecio.dto;

public class ListaPrecioReporteRequestDto {

	private String unidadTecnicaSuperior;
	private String idUsuario;
	private String idFase;
	private String idEquipo;
	private String idEquipoInicial;
	private String idEquipoFinal;
	private String idTipo;
	public ListaPrecioReporteRequestDto() {
		super();
	}
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
	public String getIdTipo() {
		return idTipo;
	}
	public void setIdTipo(String idTipo) {
		this.idTipo = idTipo;
	}
	@Override
	public String toString() {
		return "ListaPrecioReporteRequestDto [unidadTecnicaSuperior="
				+ unidadTecnicaSuperior + ", idUsuario=" + idUsuario
				+ ", idFase=" + idFase + ", idEquipo=" + idEquipo
				+ ", idEquipoInicial=" + idEquipoInicial + ", idEquipoFinal="
				+ idEquipoFinal + ", idTipo=" + idTipo + "]";
	}

}
