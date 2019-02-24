package mx.com.grupogigante.gestionvivienda.domain.dto;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class CriteriosFileUploadDto {
	private String accion;
	private String name;   
	private CommonsMultipartFile fileData;  
    private String idUbicacion;   
    private String tipo;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getIdUbicacion() {
		return idUbicacion;
	}
	public void setIdUbicacion(String idUbicacion) {
		this.idUbicacion = idUbicacion;
	}
	public String getName()   {     
		return name;   
	}     
	public void setName(String name)   {     
		this.name = name;   
	}     
	public CommonsMultipartFile getFileData()   {     
		return fileData;   
	}     
	public void setFileData(CommonsMultipartFile fileData)   {     
		this.fileData = fileData;   
	}
}
