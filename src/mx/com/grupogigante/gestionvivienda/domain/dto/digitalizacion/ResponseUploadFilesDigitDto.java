package mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion;

import java.util.ArrayList;
import java.util.List;

import mx.com.grupogigante.gestionvivienda.domain.vo.DigitContadorDto;
import mx.com.grupogigante.gestionvivienda.domain.vo.DigitDatosImagenVo;
import mx.com.grupogigante.gestionvivienda.domain.vo.UbicacionTecnicaDatosMapaImagenVo;
import mx.com.grupogigante.gestionvivienda.resources.maper.DigitContadorRowMaper;
import mx.com.grupogigante.gestionvivienda.resources.maper.DigitImagenRowMaper;

public class ResponseUploadFilesDigitDto {
	private String mensaje;
	private String descripcion;
	private int consecutivo;
	private String nombreImg;
	private String urlImagen;
	private List<DigitDatosImagenVo> imagesDigit = new ArrayList<DigitDatosImagenVo>();
	private List<DigitContadorDto> datosContador = new ArrayList<DigitContadorDto>();
	
	public List<DigitContadorDto> getDatosContador() {
		return datosContador;
	}
	public void setDatosContador(List<DigitContadorDto> datosContador) {
		this.datosContador = datosContador;
	}
	public List<DigitDatosImagenVo> getImagesDigit() {
		return imagesDigit;
	}
	public void setImagesDigit(List<DigitDatosImagenVo> imagesDigit) {
		this.imagesDigit = imagesDigit;
	}
	public int getConsecutivo() {
		return consecutivo;
	}
	public void setConsecutivo(int consecutivo) {
		this.consecutivo = consecutivo;
	}
	public String getNombreImg() {
		return nombreImg;
	}
	public void setNombreImg(String nombreImg) {
		this.nombreImg = nombreImg;
	}
	public String getUrlImagen() {
		return urlImagen;
	}
	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
