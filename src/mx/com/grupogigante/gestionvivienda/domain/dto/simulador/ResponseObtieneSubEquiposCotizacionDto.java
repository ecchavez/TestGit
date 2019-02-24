/**
 *  Clase creada por Osvaldo Rodriguez Martinez (SACTI consultores) con fecha de 02/10/2012
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.simulador;

import java.util.ArrayList;

/**
 * @author osvaldo
 *
 */
public class ResponseObtieneSubEquiposCotizacionDto {
	String mensaje;	
	String descripcion;
	ArrayList<SubEquiposCotizacionDto> subEquiposCotizacion = new ArrayList<SubEquiposCotizacionDto>();
	
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
	public ArrayList<SubEquiposCotizacionDto> getSubEquiposCotizacion() {
		return subEquiposCotizacion;
	}
	public void setSubEquiposCotizacion(
			ArrayList<SubEquiposCotizacionDto> subEquiposCotizacion) {
		this.subEquiposCotizacion = subEquiposCotizacion;
	}
}
