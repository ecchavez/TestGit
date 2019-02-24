package mx.com.grupogigante.gestionvivienda.domain.dto;

/**
 * Clase que encapsula los atributos de Boletines.
 * Fecha de creación: 24/04/2014               
 */

public class BoletinDto {
	private String nombreArchivo;
	private String rutaArchivo;	
	
	public BoletinDto() {
		super();
	}
	
	public BoletinDto(String nombreArchivo, String rutaArchivo) {
		super();
		this.nombreArchivo = nombreArchivo;
		this.rutaArchivo = rutaArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getRutaArchivo() {
		return rutaArchivo;
	}
	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}
}