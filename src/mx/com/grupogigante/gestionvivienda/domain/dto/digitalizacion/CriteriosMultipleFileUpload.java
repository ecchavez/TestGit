package mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion;

import java.util.List;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
public class CriteriosMultipleFileUpload {
	private String filename;
	private List<CommonsMultipartFile> uploaded_files;
	
    public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public List<CommonsMultipartFile> getUploaded_files() {
		return uploaded_files;
	}
	public void setUploaded_files(List<CommonsMultipartFile> uploaded_files) {
		this.uploaded_files = uploaded_files;
	}
	
}