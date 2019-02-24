package mx.com.grupogigante.gestionvivienda.domain.vo;

import java.sql.Blob;

public class DigitDatosImagenVo {
	String file_id;
	String file_clave;
	String file_nombre;
	String file_fecha_alta;
	String file_datablob;
	String file_unidad;
	String file_proceso;
	String file_tarchivo;
	String file_estatus;
	Blob blobImage;
    byte [] isImage;
    String urlImagen;
    String file_tipo;
	
	public String getFile_proceso() {
		return file_proceso;
	}
	public void setFile_proceso(String file_proceso) {
		this.file_proceso = file_proceso;
	}
	public String getFile_tarchivo() {
		return file_tarchivo;
	}
	public void setFile_tarchivo(String file_tarchivo) {
		this.file_tarchivo = file_tarchivo;
	}
	public String getFile_estatus() {
		return file_estatus;
	}
	public void setFile_estatus(String file_estatus) {
		this.file_estatus = file_estatus;
	}
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public String getFile_clave() {
		return file_clave;
	}
	public void setFile_clave(String file_clave) {
		this.file_clave = file_clave;
	}
	public String getFile_nombre() {
		return file_nombre;
	}
	public void setFile_nombre(String file_nombre) {
		this.file_nombre = file_nombre;
	}
	public String getFile_fecha_alta() {
		return file_fecha_alta;
	}
	public void setFile_fecha_alta(String file_fecha_alta) {
		this.file_fecha_alta = file_fecha_alta;
	}
	public String getFile_datablob() {
		return file_datablob;
	}
	public void setFile_datablob(String file_datablob) {
		this.file_datablob = file_datablob;
	}
	public String getFile_unidad() {
		return file_unidad;
	}
	public void setFile_unidad(String file_unidad) {
		this.file_unidad = file_unidad;
	}
	public Blob getBlobImage() {
		return blobImage;
	}
	public void setBlobImage(Blob blobImage) {
		this.blobImage = blobImage;
	}
	public byte[] getIsImage() {
		return isImage;
	}
	public void setIsImage(byte[] isImage) {
		this.isImage = isImage;
	}
	public String getUrlImagen() {
		return urlImagen;
	}
	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}
	public String getFile_tipo() {
		return file_tipo;
	}
	public void setFile_tipo(String file_tipo) {
		this.file_tipo = file_tipo;
	}
}
