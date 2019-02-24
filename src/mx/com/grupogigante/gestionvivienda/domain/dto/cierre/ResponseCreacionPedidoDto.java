/**
 *  Clase creada por Osvaldo Rodriguez Martinez (SACTI consultores) con fecha de 24/10/2012
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.cierre;

/**
 * @author osvaldo
 *
 */
public class ResponseCreacionPedidoDto {
	private String mensaje;
	private String descripcion;
	private String idPedido;
	
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}	
}
