package mx.com.grupogigante.gestionvivienda.report.disponibilidad.dto;

public class DisponibilidadReporteRowDto {
	private String idFase;
	private String descripcionFase;
	private String casaDepto;
	private String idTipo;
	private String descripcionTipo;
	private String m2Privativos;
	private String idEstatus;
	private String descripcionEstatus;
	private String fechaUltimoEstatus;
	private String idCliente;
	private String nombreCliente;
	private String apPaterno;
	
	public DisponibilidadReporteRowDto() {
		super();
	}
	public String getIdFase() {
		return idFase;
	}
	public void setIdFase(String idFase) {
		this.idFase = idFase;
	}
	public String getDescripcionFase() {
		return descripcionFase;
	}
	public void setDescripcionFase(String descripcionFase) {
		this.descripcionFase = descripcionFase;
	}
	public String getCasaDepto() {
		return casaDepto;
	}
	public void setCasaDepto(String casaDepto) {
		this.casaDepto = casaDepto;
	}
	public String getIdTipo() {
		return idTipo;
	}
	public void setIdTipo(String idTipo) {
		this.idTipo = idTipo;
	}
	public String getDescripcionTipo() {
		return descripcionTipo;
	}
	public void setDescripcionTipo(String descripcionTipo) {
		this.descripcionTipo = descripcionTipo;
	}
	public String getM2Privativos() {
		return m2Privativos;
	}
	public void setM2Privativos(String m2Privativos) {
		this.m2Privativos = m2Privativos;
	}
	public String getIdEstatus() {
		return idEstatus;
	}
	public void setIdEstatus(String idEstatus) {
		this.idEstatus = idEstatus;
	}
	public String getDescripcionEstatus() {
		return descripcionEstatus;
	}
	public void setDescripcionEstatus(String descripcionEstatus) {
		this.descripcionEstatus = descripcionEstatus;
	}
	public String getFechaUltimoEstatus() {
		return fechaUltimoEstatus;
	}
	public void setFechaUltimoEstatus(String fechaUltimoEstatus) {
		this.fechaUltimoEstatus = fechaUltimoEstatus;
	}
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getApPaterno() {
		return apPaterno;
	}
	public void setApPaterno(String apPaterno) {
		this.apPaterno = apPaterno;
	}	
}
