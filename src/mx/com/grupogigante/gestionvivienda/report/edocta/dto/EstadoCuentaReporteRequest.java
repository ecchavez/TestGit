package mx.com.grupogigante.gestionvivienda.report.edocta.dto;

public class EstadoCuentaReporteRequest {
	private String idCliente;
	private String idFase;
	private String idEquipo;
	private String idUsuario;
	private String idUnidadTecnicaSuperior;
	private String fechaCorte;
	
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
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
	public EstadoCuentaReporteRequest() {
		super();
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getIdUnidadTecnicaSuperior() {
		return idUnidadTecnicaSuperior;
	}
	public void setIdUnidadTecnicaSuperior(String idUnidadTecnicaSuperior) {
		this.idUnidadTecnicaSuperior = idUnidadTecnicaSuperior;
	}
	@Override
	public String toString() {
		return "EstadoCuentaReporteRequest [idCliente=" + idCliente
				+ ", idFase=" + idFase + ", idEquipo=" + idEquipo
				+ ", idUsuario=" + idUsuario + ", idUnidadTecnicaSuperior="
				+ idUnidadTecnicaSuperior + "]";
	}
	public String getFechaCorte() {
		return fechaCorte;
	}
	public void setFechaCorte(String fechaCorte) {
		this.fechaCorte = fechaCorte;
	}
	
}
