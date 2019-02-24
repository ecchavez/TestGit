package mx.com.grupogigante.gestionvivienda.report.edocta.dto;

public class ConsultaPagosReporteRequest {
	private String idCliente;
	private String idFase;
	private String idEquipo;
	private String idUsuario;
	private String idUnidadTecnicaSuperior;
	private String fechaIni;
	private String fechaFin;
	private String idMedioPago;
	
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
	public ConsultaPagosReporteRequest() {
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
	public String getFechaIni() {
		return fechaIni;
	}
	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getIdMedioPago() {
		return idMedioPago;
	}
	public void setIdMedioPago(String idMedioPago) {
		this.idMedioPago = idMedioPago;
	}
	@Override
	public String toString() {
		return "EstadoCuentaReporteRequest [idCliente=" + idCliente
				+ ", idFase=" + idFase + ", idEquipo=" + idEquipo
				+ ", idUsuario=" + idUsuario + ", idUnidadTecnicaSuperior="
				+ idUnidadTecnicaSuperior + ", fechaIni" + fechaIni
				+ ", fechaFin=" + fechaFin + ", idMedioPago=" + idMedioPago 
				+ "]";
	}
	
}
