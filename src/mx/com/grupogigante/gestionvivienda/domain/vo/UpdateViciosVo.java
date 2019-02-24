package mx.com.grupogigante.gestionvivienda.domain.vo;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

public class UpdateViciosVo {
	
	
	private String idTicket;
	private String idFase;
	private String idEquipo;
	private String cliente;
	private String prioridad;
	private String asunto;
	private String fechat;
	private String observaciones;
	private String estatus;
	private String aprobado;
	private String asignado;
	private String recibido;
	private MultipartFile fileData;
	
	
	private ArrayList<String> conse;
	private ArrayList<String> zona;
	private ArrayList<String> vicio;
	private ArrayList<String> estatusDet;
	private ArrayList<String> motivo;

	private String usuario;
	private String id_ut_sup;
	
	
	public ArrayList<String> getEstatusDet() {
		return estatusDet;
	}
	public void setEstatusDet(ArrayList<String> estatusDet) {
		this.estatusDet = estatusDet;
	}
	public ArrayList<String> getMotivo() {
		return motivo;
	}
	public void setMotivo(ArrayList<String> motivo) {
		this.motivo = motivo;
	}
	public String getFechat() {
		return fechat;
	}
	public void setFechat(String fechat) {
		this.fechat = fechat;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getId_ut_sup() {
		return id_ut_sup;
	}
	public void setId_ut_sup(String id_ut_sup) {
		this.id_ut_sup = id_ut_sup;
	}
	public String getIdTicket() {
		return idTicket;
	}
	public void setIdTicket(String idTicket) {
		this.idTicket = idTicket;
	}
	public ArrayList<String> getConse() {
		return conse;
	}
	public void setConse(ArrayList<String> conse) {
		this.conse = conse;
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
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
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
	public String getAprobado() {
		return aprobado;
	}
	public void setAprobado(String aprobado) {
		this.aprobado = aprobado;
	}
	public ArrayList<String> getZona() {
		return zona;
	}
	public void setZona(ArrayList<String> zona) {
		this.zona = zona;
	}
	public ArrayList<String> getVicio() {
		return vicio;
	}
	public void setVicio(ArrayList<String> vicio) {
		this.vicio = vicio;
	}
	public MultipartFile getFileData() {
		return fileData;
	}
	public void setFileData(MultipartFile fileData) {
		this.fileData = fileData;
	}
	
}
