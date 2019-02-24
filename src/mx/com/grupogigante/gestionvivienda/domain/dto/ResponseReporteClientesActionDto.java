package mx.com.grupogigante.gestionvivienda.domain.dto;

public class ResponseReporteClientesActionDto {
	
	ResponseGetReporteClientesDto resultReporte;
	ResponseGetUsuariosDto resGetClientesDTO;

	public ResponseGetUsuariosDto getResGetClientesDTO() {
		return resGetClientesDTO;
	}

	public void setResGetClientesDTO(ResponseGetUsuariosDto resGetClientesDTO) {
		this.resGetClientesDTO = resGetClientesDTO;
	}

	public ResponseGetReporteClientesDto getResultReporte() {
		return resultReporte;
	}

	public void setResultReporte(ResponseGetReporteClientesDto resultReporte) {
		this.resultReporte = resultReporte;
	}
}
