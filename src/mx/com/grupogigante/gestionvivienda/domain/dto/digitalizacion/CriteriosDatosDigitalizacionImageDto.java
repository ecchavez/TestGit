package mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion;

import mx.com.grupogigante.gestionvivienda.domain.vo.EquipoDatosMapaImagenVo;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.sql.Blob;
import java.util.ArrayList;



public class CriteriosDatosDigitalizacionImageDto {
	String fase;
	String equipo;
	String idUTS;
	String urlImagen;
	String nombreImagen;
	String fechaAlta;
	String fechaActualizacion;
	String accion;
	String tipo;
	String tipoa;
	String subtipo;
	String subtipoa;
	String estatus;
	String consecutivo;
	String fechaInicio;
	String fechaFin;
	MultipartFile imagenMaping;
	CommonsMultipartFile fileData;  
	String id_usuario;
	
	String folioOper;
	String fchPago;
	String hrPago;
	String monto;
	String refer;
	String idFaseEquipoXY;
	
	public String getTipoa() {
		return tipoa;
	}
	public void setTipoa(String tipoa) {
		this.tipoa = tipoa;
	}
	public String getSubtipoa() {
		return subtipoa;
	}
	public void setSubtipoa(String subtipoa) {
		this.subtipoa = subtipoa;
	}
	public String getSubtipo() {
		return subtipo;
	}
	public void setSubtipo(String subtipo) {
		this.subtipo = subtipo;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getFase() {
		return fase;
	}
	public void setFase(String fase) {
		this.fase = fase;
	}
	public String getEquipo() {
		return equipo;
	}
	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}
	public String getConsecutivo() {
		return consecutivo;
	}
	public void setConsecutivo(String consecutivo) {
		this.consecutivo = consecutivo;
	}
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
		
	public String getIdUTS() {
		return idUTS;
	}
	public void setIdUTS(String idUTS) {
		this.idUTS = idUTS;
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
	public String getFolioOper() {
		return folioOper;
	}
	public void setFolioOper(String folioOper) {
		this.folioOper = folioOper;
	}
	public String getFchPago() {
		return fchPago;
	}
	public void setFchPago(String fchPago) {
		this.fchPago = fchPago;
	}
	public String getHrPago() {
		return hrPago;
	}
	public void setHrPago(String hrPago) {
		this.hrPago = hrPago;
	}
	public String getMonto() {
		return monto;
	}
	public void setMonto(String monto) {
		this.monto = monto;
	}
	public String getRefer() {
		return refer;
	}
	public void setRefer(String refer) {
		this.refer = refer;
	}
	
	public String getIdFaseEquipoXY() {
		return idFaseEquipoXY;
	}
	public void setIdFaseEquipoXY(String idFaseEquipoXY) {
		this.idFaseEquipoXY = idFaseEquipoXY;
	}
	@Override
	public String toString() {
		return "CriteriosDatosDigitalizacionImageDto [fase=" + fase
				+ ", equipo=" + equipo + ", idUTS=" + idUTS + ", urlImagen="
				+ urlImagen + ", nombreImagen=" + nombreImagen + ", fechaAlta="
				+ fechaAlta + ", fechaActualizacion=" + fechaActualizacion
				+ ", accion=" + accion + ", tipo=" + tipo + ", tipoa=" + tipoa
				+ ", subtipo=" + subtipo + ", subtipoa=" + subtipoa
				+ ", estatus=" + estatus + ", consecutivo=" + consecutivo
				+ ", imagenMaping=" + imagenMaping + ", fileData=" + fileData
				+ ", id_usuario=" + id_usuario + ", folioOper=" + folioOper
				+ ", fchPago=" + fchPago + ", hrPago=" + hrPago + ", monto="
				+ monto + ", refer=" + refer + ", idFaseEquipoXY="
				+ idFaseEquipoXY + "]";
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	
}
