package mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion;

public class ResponseDatosDigitActionDto {
	ResponseUploadFilesDigitDto respDatosUploadDigitFile = new ResponseUploadFilesDigitDto();

	public ResponseUploadFilesDigitDto getRespDatosUploadDigitFile() {
		return respDatosUploadDigitFile;
	}

	public void setRespDatosUploadDigitFile(
			ResponseUploadFilesDigitDto respDatosUploadDigitFile) {
		this.respDatosUploadDigitFile = respDatosUploadDigitFile;
	}
}
