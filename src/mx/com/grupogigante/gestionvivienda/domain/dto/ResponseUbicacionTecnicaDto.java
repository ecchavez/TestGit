/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Clase que encapsula los fields de Ubicaciones 
 * Fecha de creación: XX/06/2012               
 */
public class ResponseUbicacionTecnicaDto {
	
	private List<UbicacionTecnicaDto> ubicacionesList = new ArrayList<UbicacionTecnicaDto>();
	private Object objUbicacionesList;	
	private String mensaje;
	private String descripcion;
	
	/**
	 * @return the ubicacionesList
	 */
	public List<UbicacionTecnicaDto> getUbicacionesList() {
		return ubicacionesList;
	}
	/**
	 * @param ubicacionesList the ubicacionesList to set
	 */
	public void setUbicacionesList(List<UbicacionTecnicaDto> ubicacionesList) {
		this.ubicacionesList = ubicacionesList;
	}
	/**
	 * @return the objUbicacionesList
	 */
	public Object getObjUbicacionesList() {
		return objUbicacionesList;
	}
	/**
	 * @param objUbicacionesList the objUbicacionesList to set
	 */
	public void setObjUbicacionesList(Object objUbicacionesList) {
		this.objUbicacionesList = objUbicacionesList;
	}
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	}
