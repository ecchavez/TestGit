/**
 * @author JL - SACTI CONSULTORES / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.List;

/**
 * Clase que encapsula los fields de Response Add Clientes +
 * Fecha de creación: XX/06/2012               
 */
public class ResponseAddTicketDto {
	private String mensaje;
	private String descripcion;
	private String id_ticket_z;
	private String id_ticket_sap;

	private List<TicketDto> listTicket;
	
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getId_ticket_z() {
		return id_ticket_z;
	}
	public void setId_ticket_z(String id_ticket_z) {
		this.id_ticket_z = id_ticket_z;
	}
	public String getId_ticket_sap() {
		return id_ticket_sap;
	}
	public void setId_ticket_sap(String id_ticket_sap) {
		this.id_ticket_sap = id_ticket_sap;
	}
	public List<TicketDto> getListTicket() {
		return listTicket;
	}
	public void setListTicket(List<TicketDto> listTicket) {
		this.listTicket = listTicket;
	}
	
	
	
}
