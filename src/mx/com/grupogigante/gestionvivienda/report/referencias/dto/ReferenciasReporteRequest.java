package mx.com.grupogigante.gestionvivienda.report.referencias.dto;

public class ReferenciasReporteRequest {

	private String unidadTecnicaSuperior;
	private String idUsuario;
	private String idEquipoInicial;
	private String idEquipoFinal;
	private String faseLow;
	private String faseHigh;
	private String bancoLow;
	private String bancoHigh;
	@Override
	public String toString() {
		return "ReferenciasReporteRequest [unidadTecnicaSuperior="
				+ unidadTecnicaSuperior + ", idUsuario=" + idUsuario
				+ ", idEquipoInicial=" + idEquipoInicial + ", idEquipoFinal="
				+ idEquipoFinal + ", faseLow=" + faseLow + ", haseHigh="
				+ faseHigh + ", bancoLow=" + bancoLow + ", bancoHigh="
				+ bancoHigh + "]";
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
	public String getFaseLow() {
		return faseLow;
	}
	public void setFaseLow(String faseLow) {
		this.faseLow = faseLow;
	}
	public String getFaseHigh() {
		return faseHigh;
	}
	public void setFaseHigh(String haseHigh) {
		this.faseHigh = haseHigh;
	}
	public String getBancoLow() {
		return bancoLow;
	}
	public void setBancoLow(String bancoLow) {
		this.bancoLow = bancoLow;
	}
	public String getBancoHigh() {
		return bancoHigh;
	}
	public void setBancoHigh(String bancoHigh) {
		this.bancoHigh = bancoHigh;
	}
	public ReferenciasReporteRequest() {
		super();
	}

	
	
}
