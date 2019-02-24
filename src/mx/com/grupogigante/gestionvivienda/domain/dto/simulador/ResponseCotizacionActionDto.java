/**
 *  Clase creada por Osvaldo Rodriguez Martinez (SACTI consultores) con fecha de 24/09/2012
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.simulador;

/**
 * @author osvaldo
 *
 */
public class ResponseCotizacionActionDto {
	ResponseObtieneCotizacionBaseDto resCotizacion;
	Object resCotizacionBase;
	ResponseObtieneSubEquiposCotizacionDto resCotizacionSubEquipos;

	public Object getResCotizacionBase() {
		return resCotizacionBase;
	}

	public void setResCotizacionBase(
			Object resCotizacionBase) {
		this.resCotizacionBase = resCotizacionBase;
	}

	public ResponseObtieneSubEquiposCotizacionDto getResCotizacionSubEquipos() {
		return resCotizacionSubEquipos;
	}

	public void setResCotizacionSubEquipos(
			ResponseObtieneSubEquiposCotizacionDto resCotizacionSubEquipos) {
		this.resCotizacionSubEquipos = resCotizacionSubEquipos;
	}

	public ResponseObtieneCotizacionBaseDto getResCotizacion() {
		return resCotizacion;
	}

	public void setResCotizacion(ResponseObtieneCotizacionBaseDto resCotizacion) {
		this.resCotizacion = resCotizacion;
	}
}
