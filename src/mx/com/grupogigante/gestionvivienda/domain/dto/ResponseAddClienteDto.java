/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;
/**
 * Clase que encapsula los fields de Response Add Clientes +
 * Fecha de creación: XX/06/2012               
 */
public class ResponseAddClienteDto {
	private String mensaje;
	private String descripcion;
	private String id_cliente_z;
	private String id_cliente_sap;
	
	
	public String getId_cliente_z() {
		return id_cliente_z;
	}
	public void setId_cliente_z(String id_cliente_z) {
		this.id_cliente_z = id_cliente_z;
	}
	public String getId_cliente_sap() {
		return id_cliente_sap;
	}
	public void setId_cliente_sap(String id_cliente_sap) {
		this.id_cliente_sap = id_cliente_sap;
	}		
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
	
	
	
}
