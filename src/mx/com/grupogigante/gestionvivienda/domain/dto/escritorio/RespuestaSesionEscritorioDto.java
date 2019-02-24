/**
 *  Clase creada por Osvaldo Rodriguez Martinez (SACTI consultores) con fecha de 12/10/2012
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.escritorio;

/**
 * @author osvaldo
 *
 */
public class RespuestaSesionEscritorioDto {
	private String mensaje = "";
	private String descripcion = "";
	
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
	
}
