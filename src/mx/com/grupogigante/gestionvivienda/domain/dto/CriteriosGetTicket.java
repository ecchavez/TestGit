/**
 * @author JL SACTI CONSULTORES / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;


/**
 * Clase que encapsula los fields para Busqueda e insercion de cartera de Clientes 
 * Fecha de creación: XX/06/2012               
 */
public class CriteriosGetTicket {
	
	private String usuario;
	private String id_ut_sup;
	private int accion;
	private String id_car_cli;
	private String asunto;
	private String body_mail;
	private String ticket_area;
	private String sls_man;
	private String idFase;
	private String idEquipo;
	
	private String idTicketInicial;
	private String idTicketFinal;
	private String fechaInicial;
	private String fechaFinal;
	
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
	public int getAccion() {
		return accion;
	}
	public void setAccion(int accion) {
		this.accion = accion;
	}
	public String getId_car_cli() {
		return id_car_cli;
	}
	public void setId_car_cli(String id_car_cli) {
		this.id_car_cli = id_car_cli;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getBody_mail() {
		return body_mail;
	}
	public void setBody_mail(String body_mail) {
		this.body_mail = body_mail;
	}
	public String getTicket_area() {
		return ticket_area;
	}
	public void setTicket_area(String ticket_area) {
		this.ticket_area = ticket_area;
	}
	public String getSls_man() {
		return sls_man;
	}
	public void setSls_man(String sls_man) {
		this.sls_man = sls_man;
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
	public String getIdTicketInicial() {
		return idTicketInicial;
	}
	public void setIdTicketInicial(String idTicketInicial) {
		this.idTicketInicial = idTicketInicial;
	}
	public String getIdTicketFinal() {
		return idTicketFinal;
	}
	public void setIdTicketFinal(String idTicketFinal) {
		this.idTicketFinal = idTicketFinal;
	}
	public String getFechaInicial() {
		return fechaInicial;
	}
	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
	public String getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	
}
