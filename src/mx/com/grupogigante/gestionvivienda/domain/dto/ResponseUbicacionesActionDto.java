package mx.com.grupogigante.gestionvivienda.domain.dto;

import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.ResponseGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.utils.SessionValidatorSTS;

public class ResponseUbicacionesActionDto {
	private ResponseGetUtInfSupDto respGetUnidadesTecnicasSuperiores;
    private ResponseUbicacionDatosMapaDto respDatosImagen;
    
    
    
    private ResponseUbicacionDatosMapaDto respUbicacion;
    private ResponseUbicacionDatosMapaDto addCoords;
    private ResponseGetUtInfSupDto pisosReplica;
    private ResponseUbicacionDatosMapaDto responseValidaImagenMapeo;
    private ResponseUbicacionDatosMapaDto responseInsertImagenMapeo;
    private ResponseUbicacionDatosMapaDto responseDeleteImagenMapeo;
    
    private ResponseGetCatalogosDto responseGetCatalogosDto = new ResponseGetCatalogosDto();
    
	
	public ResponseUbicacionDatosMapaDto getRespUbicacion() {
		return respUbicacion;
	}
	public void setRespUbicacion(ResponseUbicacionDatosMapaDto respUbicacion) {
		this.respUbicacion = respUbicacion;
	}
	public ResponseUbicacionDatosMapaDto getAddCoords() {
		return addCoords;
	}
	public void setAddCoords(ResponseUbicacionDatosMapaDto addCoords) {
		this.addCoords = addCoords;
	}
	public ResponseGetUtInfSupDto getPisosReplica() {
		return pisosReplica;
	}
	public void setPisosReplica(ResponseGetUtInfSupDto pisosReplica) {
		this.pisosReplica = pisosReplica;
	}
	public ResponseUbicacionDatosMapaDto getResponseValidaImagenMapeo() {
		return responseValidaImagenMapeo;
	}
	public void setResponseValidaImagenMapeo(
			ResponseUbicacionDatosMapaDto responseValidaImagenMapeo) {
		this.responseValidaImagenMapeo = responseValidaImagenMapeo;
	}
	public ResponseUbicacionDatosMapaDto getResponseInsertImagenMapeo() {
		return responseInsertImagenMapeo;
	}
	public void setResponseInsertImagenMapeo(
			ResponseUbicacionDatosMapaDto responseInsertImagenMapeo) {
		this.responseInsertImagenMapeo = responseInsertImagenMapeo;
	}
	public ResponseUbicacionDatosMapaDto getResponseDeleteImagenMapeo() {
		return responseDeleteImagenMapeo;
	}
	public void setResponseDeleteImagenMapeo(
			ResponseUbicacionDatosMapaDto responseDeleteImagenMapeo) {
		this.responseDeleteImagenMapeo = responseDeleteImagenMapeo;
	}
	public ResponseUbicacionDatosMapaDto getRespDatosImagen() {
		return respDatosImagen;
	}
	public void setRespDatosImagen(ResponseUbicacionDatosMapaDto respDatosImagen) {
		this.respDatosImagen = respDatosImagen;
	}

	public ResponseGetUtInfSupDto getRespGetUnidadesTecnicasSuperiores() {
		return respGetUnidadesTecnicasSuperiores;
	}

	public void setRespGetUnidadesTecnicasSuperiores(
			ResponseGetUtInfSupDto respGetUnidadesTecnicasSuperiores) {
		this.respGetUnidadesTecnicasSuperiores = respGetUnidadesTecnicasSuperiores;
	}
	public ResponseGetCatalogosDto getResponseGetCatalogosDto() {
		return responseGetCatalogosDto;
	}
	public void setResponseGetCatalogosDto(ResponseGetCatalogosDto responseGetCatalogosDto) {
		this.responseGetCatalogosDto = responseGetCatalogosDto;
	}
	
}
