/**
 *  Clase creada por Osvaldo Rodriguez Martinez (SACTI consultores) con fecha de 12/10/2012
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.escritorio;

import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseValidaLoginDto;

/**
 * @author osvaldo rodriguez martinez
 *
 */
public class ResponseEscritorioActionDto {
	RespuestaSesionEscritorioDto respSesionEscritorio;
	ResponseValidaLoginDto respPermisosUser;

	public ResponseValidaLoginDto getRespPermisosUser() {
		return respPermisosUser;
	}

	public void setRespPermisosUser(ResponseValidaLoginDto respPermisosUser) {
		this.respPermisosUser = respPermisosUser;
	}

	public RespuestaSesionEscritorioDto getRespSesionEscritorio() {
		return respSesionEscritorio;
	}

	public void setRespSesionEscritorio(
			RespuestaSesionEscritorioDto respSesionEscritorio) {
		this.respSesionEscritorio = respSesionEscritorio;
	}
}
