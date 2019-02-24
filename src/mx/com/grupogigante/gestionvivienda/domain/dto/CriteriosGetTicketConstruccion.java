/**
 * @author JL SACTI CONSULTORES / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;


/**
 * Clase que encapsula los fields para Busqueda de Tickets de construcción
 * @author Carlos Edwin
 * Fecha de creación: XX/10/2016               
 */
public class CriteriosGetTicketConstruccion {
	
	private String idTicket;
	private String id_ut_sup;
	private String usuario;
	private String idFase;
	private String idEquipo;
	private String txtArea;
	private String estatus;
	private String fechaInicial;
	private String fechaFinal;
	
	public String getIdTicket() {
		return idTicket;
	}
	public void setIdTicket(String idTicket) {
		this.idTicket = idTicket;
	}
	public String getId_ut_sup() {
		return id_ut_sup;
	}
	public void setId_ut_sup(String id_ut_sup) {
		this.id_ut_sup = id_ut_sup;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
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
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
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
	public String getTxtArea() {
		return txtArea;
	}
	public void setTxtArea(String txtArea) {
		this.txtArea = txtArea;
	}
	
}
