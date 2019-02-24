/**
 *  Clase creada por Edwin Chavez (SACTI consultores) con fecha de 29/05/2014
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.cierre;

/**
 * @author Edwin
 *
 */
public class ResponseObtieneReferenciaDto {
	private String mensaje;
	private String descripcion;
	private String referencia;
	
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
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

}
