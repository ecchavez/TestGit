package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;
import java.util.List;

import mx.com.grupogigante.gestionvivienda.domain.vo.EquipoDatosMapaImagenVo;
import mx.com.grupogigante.gestionvivienda.domain.vo.UbicacionTecnicaDatosMapaImagenVo;

public class ResponseUbicacionDatosMapaDto {
	private List<UbicacionTecnicaDatosMapaImagenVo> listaImagenDatos = new ArrayList<UbicacionTecnicaDatosMapaImagenVo>();
	private List<EquipoDatosMapaImagenVo> listaEquiposImagenesDatos = new ArrayList<EquipoDatosMapaImagenVo>();
	private ArrayList<CoordenadasDto> listaCoordenadas = new ArrayList<CoordenadasDto>();
	private ArrayList<EquipoTiposDto> listaTipos = new ArrayList<EquipoTiposDto>();
	private UbicacionTecnicaDatosMapaImagenVo imagenUbicacion = new UbicacionTecnicaDatosMapaImagenVo();
	private String mensaje;
	private String descripcion;
	
	public List<EquipoDatosMapaImagenVo> getListaEquiposImagenesDatos() {
		return listaEquiposImagenesDatos;
	}
	public void setListaEquiposImagenesDatos(
			List<EquipoDatosMapaImagenVo> listaEquiposImagenesDatos) {
		this.listaEquiposImagenesDatos = listaEquiposImagenesDatos;
	}
	public UbicacionTecnicaDatosMapaImagenVo getImagenUbicacion() {
		return imagenUbicacion;
	}
	public void setImagenUbicacion(UbicacionTecnicaDatosMapaImagenVo imagenUbicacion) {
		this.imagenUbicacion = imagenUbicacion;
	}
	public ArrayList<EquipoTiposDto> getListaTipos() {
		return listaTipos;
	}
	public void setListaTipos(ArrayList<EquipoTiposDto> listaTipos) {
		this.listaTipos = listaTipos;
	}
	public ArrayList<CoordenadasDto> getListaCoordenadas() {
		return listaCoordenadas;
	}
	public void setListaCoordenadas(ArrayList<CoordenadasDto> listaCoordenadas) {
		this.listaCoordenadas = listaCoordenadas;
	}
	public List<UbicacionTecnicaDatosMapaImagenVo> getListaImagenDatos() {
		return listaImagenDatos;
	}
	public void setListaImagenDatos(
			List<UbicacionTecnicaDatosMapaImagenVo> listaImagenDatos) {
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
