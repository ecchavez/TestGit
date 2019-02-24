package mx.com.grupogigante.gestionvivienda.domain.dto;

public class ResponseUserActionDto {
	ResponseGetUsuariosDto respGetUsuariosDto;
	ResponseAddUsuarioDto respAddUsuarioDto;
	ResponseUpdUsuariDto respUpdUsuarioDto;
	ResponseDelUsuarioDto respDelUsuarioDto;
	ResponseUbicacionTecnicaDto respUbicacionTecnicaDto;

	public ResponseUbicacionTecnicaDto getRespUbicacionTecnicaDto() {
		return respUbicacionTecnicaDto;
	}

	public void setRespUbicacionTecnicaDto(
			ResponseUbicacionTecnicaDto respUbicacionTecnicaDto) {
		this.respUbicacionTecnicaDto = respUbicacionTecnicaDto;
	}

	public ResponseUpdUsuariDto getRespUpdUsuarioDto() {
		return respUpdUsuarioDto;
	}

	public void setRespUpdUsuarioDto(ResponseUpdUsuariDto respUpdUsuarioDto) {
		this.respUpdUsuarioDto = respUpdUsuarioDto;
	}

	public ResponseDelUsuarioDto getRespDelUsuarioDto() {
		return respDelUsuarioDto;
	}

	public void setRespDelUsuarioDto(ResponseDelUsuarioDto respDelUsuarioDto) {
		this.respDelUsuarioDto = respDelUsuarioDto;
	}

	public ResponseAddUsuarioDto getRespAddUsuarioDto() {
		return respAddUsuarioDto;
	}

	public void setRespAddUsuarioDto(ResponseAddUsuarioDto respAddUsuarioDto) {
		this.respAddUsuarioDto = respAddUsuarioDto;
	}

	public ResponseGetUsuariosDto getRespGetUsuariosDto() {
		return respGetUsuariosDto;
	}

	public void setRespGetUsuariosDto(ResponseGetUsuariosDto respGetUsuariosDto) {
		this.respGetUsuariosDto = respGetUsuariosDto;
	}
}
