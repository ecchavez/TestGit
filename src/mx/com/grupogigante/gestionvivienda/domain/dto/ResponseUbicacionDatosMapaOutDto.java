package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.List;

import mx.com.grupogigante.gestionvivienda.domain.vo.UbicacionTecnicaDatosMapaImagenVo;
import mx.com.grupogigante.gestionvivienda.domain.vo.UbicacionTecnicaDatosMaperImageOutVo;

public class ResponseUbicacionDatosMapaOutDto {
	private List<UbicacionTecnicaDatosMaperImageOutVo> listaImagenDatos;
	private String mensaje;
	private String descripcion;
	
	public List<UbicacionTecnicaDatosMaperImageOutVo> getListaImagenDatos() {
		return listaImagenDatos;
	}
	public void setListaImagenDatos(
			List<UbicacionTecnicaDatosMaperImageOutVo> listaImagenDatos) {
		this.listaImagenDatos = listaImagenDatos;
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
