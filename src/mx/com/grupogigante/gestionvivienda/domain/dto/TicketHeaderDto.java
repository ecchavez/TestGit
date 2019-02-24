package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.List;

public class TicketHeaderDto {
	private String idTicket;
	private String idUtSuperior;
	private String idFase;
	private String idEquipo;
	private String cliente;
	private String asunto;
	private String ticketArea;
	private String areatx;
	private String prioridad;
	private String estatus;
	private String fechab;
	private String fechpr;
	private String fechre;
	private String fechat;
	private String fechte;
	private String fechci;
	private String fechat_val;
	private String atiende;
	private String aprobado;
	private String asignado;
	private String recibido;
	private String observaciones;
	private String ccp;
	
	public String getCcp() {
		return ccp;
	}
	public void setCcp(String ccp) {
		this.ccp = ccp;
	}
	private List<TicketDetailDto> listTicketDetail;
	
	public String getIdTicket() {
		return idTicket;
	}
	public void setIdTicket(String idTicket) {
		this.idTicket = idTicket;
	}
	public String getIdUtSuperior() {
		return idUtSuperior;
	}
	public void setIdUtSuperior(String idUtSuperior) {
		this.idUtSuperior = idUtSuperior;
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
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getTicketArea() {
		return ticketArea;
	}
	public void setTicketArea(String ticketArea) {
		this.ticketArea = ticketArea;
	}
	public String getAreatx() {
		return areatx;
	}
	public void setAreatx(String areatx) {
		this.areatx = areatx;
	}
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getFechab() {
		return fechab;
	}
	public void setFechab(String fechab) {
		this.fechab = fechab;
	}
	public String getFechpr() {
		return fechpr;
	}
	public void setFechpr(String fechpr) {
		this.fechpr = fechpr;
	}
	public String getFechre() {
		return fechre;
	}
	public void setFechre(String fechre) {
		this.fechre = fechre;
	}
	public String getFechat() {
		return fechat;
	}
	public void setFechat(String fechat) {
		this.fechat = fechat;
	}
	public String getFechte() {
		return fechte;
	}
	public void setFechte(String fechte) {
		this.fechte = fechte;
	}
	public String getFechci() {
		return fechci;
	}
	public void setFechci(String fechci) {
		this.fechci = fechci;
	}
	public String getFechat_val() {
		return fechat_val;
	}
	public void setFechat_val(String fechat_val) {
		this.fechat_val = fechat_val;
	}
	public String getAtiende() {
		return atiende;
	}
	public void setAtiende(String atiende) {
		this.atiende = atiende;
	}
	public List<TicketDetailDto> getListTicketDetail() {
		return listTicketDetail;
	}
	public void setListTicketDetail(List<TicketDetailDto> listTicketDetail) {
		this.listTicketDetail = listTicketDetail;
	}
	public String getAprobado() {
		return aprobado;
	}
	public void setAprobado(String aprobado) {
		this.aprobado = aprobado;
	}
	public String getAsignado() {
		return asignado;
	}
	public void setAsignado(String asignado) {
		this.asignado = asignado;
	}
	public String getRecibido() {
		return recibido;
	}
	public void setRecibido(String recibido) {
		this.recibido = recibido;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
}