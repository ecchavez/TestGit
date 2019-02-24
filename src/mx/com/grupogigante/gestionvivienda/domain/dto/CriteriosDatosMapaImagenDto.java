package mx.com.grupogigante.gestionvivienda.domain.dto;

import mx.com.grupogigante.gestionvivienda.domain.vo.EquipoDatosMapaImagenVo;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.sql.Blob;
import java.util.ArrayList;



public class CriteriosDatosMapaImagenDto {
	String idUbicacion;
	String idUTS;
	String urlImagen;
	String nombreImagen;
	String fechaAlta;
	String fechaActualizacion;
	String accion;
	String tipo;
	MultipartFile imagenMaping;
	Blob blobImage;
	byte [] isImage;
	CommonsMultipartFile fileData;  
	String id_usuario;
	
	public String getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}
	public CommonsMultipartFile getFileData() {
		return fileData;
	}
	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
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
	public MultipartFile getImagenMaping() {
		return imagenMaping;
	}
	public void setImagenMaping(MultipartFile imagenMaping) {
		this.imagenMaping = imagenMaping;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}	
	public String getIdUbicacion() {
		return idUbicacion;
	}
	public void setIdUbicacion(String idUbicacion) {
		this.idUbicacion = idUbicacion;
	}
	public String getUrlImagen() {
		return urlImagen;
	}
	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
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
