/**
 * Clase que encapsula los fields de Response Add Clientes +
 * Fecha de creación: 25/09/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.cierre;

public class ResponseAddPagosDto {
	private String mensaje;
	private String descripcion;
	private String fregi;
	private String tipoRegistroPago;
	private String idPedido;
	private String idCliente;
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
	/**
	 * @return the fregi
	 */
	public String getFregi() {
		return fregi;
	}
	/**
	 * @param fregi the fregi to set
	 */
	public void setFregi(String fregi) {
		this.fregi = fregi;
	}
	public String getTipoRegistroPago() {
		return tipoRegistroPago;
	}
	public void setTipoRegistroPago(String tipoRegistroPago) {
		this.tipoRegistroPago = tipoRegistroPago;
	}
	public String getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}
	public String getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
}
