/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 01/08/2012             
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

/**
 * @author WSNADM
 *
 */
public class ContactoByClienteDto {
	
	private ContactoDto contacto;
	private ClienteSapDto cliente;
	
	/**
	 * @return the contacto
	 */
	public ContactoDto getContacto() {
		return contacto;
	}
	/**
	 * @param contacto the contacto to set
	 */
	public void setContacto(ContactoDto contacto) {
		this.contacto = contacto;
	}
	/**
	 * @return the cliente
	 */
	public ClienteSapDto getCliente() {
		return cliente;
	}
	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(ClienteSapDto cliente) {
		this.cliente = cliente;
	}

}
