/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 12/09/2012              
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.cierre;

import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetConPagosDto;

/**
 * @author WSNADM
 *
 */
public class ResponseCierreVentaActionDto {
	//Respuestas para Busquedad Cotizaciones  
	ResponseGetInfCotizacionDto responseCotizacion;
	ResponseAddPagosDto responseAddPagos;
	ResponseGetInfoPagRegDto responsePagosReg;
	ResponseGetConPagosDto responseConciPagos;
	
	/**
	 * @return the responseConciPagos
	 */
	public ResponseGetConPagosDto getResponseConciPagos() {
		return responseConciPagos;
	}

	/**
	 * @param responseConciPagos the responseConciPagos to set
	 */
	public void setResponseConciPagos(ResponseGetConPagosDto responseConciPagos) {
		this.responseConciPagos = responseConciPagos;
	}

	/**
	 * @return the responseCotizacion
	 */
	public ResponseGetInfCotizacionDto getResponseCotizacion() {
		return responseCotizacion;
	}

	/**
	 * @param responseCotizacion the responseCotizacion to set
	 */
	public void setResponseCotizacion(ResponseGetInfCotizacionDto responseCotizacion) {
		this.responseCotizacion = responseCotizacion;
	}

	/**
	 * @return the responseAddPagos
	 */
	public ResponseAddPagosDto getResponseAddPagos() {
		return responseAddPagos;
	}

	/**
	 * @param responseAddPagos the responseAddPagos to set
	 */
	public void setResponseAddPagos(ResponseAddPagosDto responseAddPagos) {
		this.responseAddPagos = responseAddPagos;
	}

	/**
	 * @return the responsePagosReg
	 */
	public ResponseGetInfoPagRegDto getResponsePagosReg() {
		return responsePagosReg;
	}

	/**
	 * @param responsePagosReg the responsePagosReg to set
	 */
	public void setResponsePagosReg(ResponseGetInfoPagRegDto responsePagosReg) {
		this.responsePagosReg = responsePagosReg;
	}
}
