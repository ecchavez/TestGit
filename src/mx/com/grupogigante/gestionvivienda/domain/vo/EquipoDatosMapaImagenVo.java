package mx.com.grupogigante.gestionvivienda.domain.vo;

import java.sql.Blob;

public class EquipoDatosMapaImagenVo {
	String idUbicacion;
	String idUTS;
	String tipo;
	String nombreImagen;
	String fechaAlta;
	String fechaActualizacion;
	Blob blobImage;
    byte [] isImage;
    String urlImagen;
	
	public String getUrlImagen() {
		return urlImagen;
	}
	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}
	public byte[] getIsImage() {
		return isImage;
	}
	public void setIsImage(byte[] isImage) {
		this.isImage = isImage;
	}
	public String getIdUTS() {
		return idUTS;
	}
	public void setIdUTS(String idUTS) {
		this.idUTS = idUTS;
	}
	public Blob getBlobImage() {
		return blobImage;
	}
	public void setBlobImage(Blob blobImage) {
		this.blobImage = blobImage;
	}
	public String getIdUbicacion() {
		return idUbicacion;
	}
	public void setIdUbicacion(String idUbicacion) {
		this.idUbicacion = idUbicacion;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNombreImagen() {
		return nombreImagen;
	}
	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}
	public String getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}	
}
