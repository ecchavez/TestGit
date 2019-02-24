package mx.com.grupogigante.gestionvivienda.domain.dto;

public class ResponseDatosMapingActionDto {
	ResponseUbicacionDatosMapaDto responseUbicacionDatosMapaDto;
	ResponseUploadFilesDto responseUploadFilesDto;

	public ResponseUploadFilesDto getResponseUploadFilesDto() {
		return responseUploadFilesDto;
	}

	public void setResponseUploadFilesDto(
			ResponseUploadFilesDto responseUploadFilesDto) {
		this.responseUploadFilesDto = responseUploadFilesDto;
	}

	public ResponseUbicacionDatosMapaDto getResponseUbicacionDatosMapaDto() {
		return responseUbicacionDatosMapaDto;
	}

	public void setResponseUbicacionDatosMapaDto(
			ResponseUbicacionDatosMapaDto responseUbicacionDatosMapaDto) {
		this.responseUbicacionDatosMapaDto = responseUbicacionDatosMapaDto;
	}
}
