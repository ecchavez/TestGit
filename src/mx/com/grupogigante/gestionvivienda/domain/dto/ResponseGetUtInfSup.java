/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;
import java.util.List;
/**
 * Clase que encapsula los fields para la respuesta de las listas de consulta para Inf UT
 * Fecha de creación: XX/06/2012               
 */
public class ResponseGetUtInfSup {

	private List<UbicacionTecnicaDto> ubicacionesList = new ArrayList<UbicacionTecnicaDto>();
	private Object objUbicacionesList = new Object();
	private List<EquipoDto> equiposList = new ArrayList<EquipoDto>();
	private Object objEquiposList = new Object();
	private List<SubequiposDto> subequiposList = new ArrayList<SubequiposDto>();
	private Object objSubequiposList = new Object();
	private List<EqCaracteristicasDto> EqCaracteristicasList = new ArrayList<EqCaracteristicasDto>();
	private Object objEqCaracteristicasList = new Object();
	private List<UTCaracteristicasDto> UtCaracterisiticasList = new ArrayList<UTCaracteristicasDto>();
	private Object objUtCaracterisiticasList = new Object();
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
	 * @return the equiposList
	 */
	public List<EquipoDto> getEquiposList() {
		return equiposList;
	}
	/**
	 * @param equiposList the equiposList to set
	 */
	public void setEquiposList(List<EquipoDto> equiposList) {
		this.equiposList = equiposList;
	}
	/**
	 * @return the objEquiposList
	 */
	public Object getObjEquiposList() {
		return objEquiposList;
	}
	/**
	 * @param objEquiposList the objEquiposList to set
	 */
	public void setObjEquiposList(Object objEquiposList) {
		this.objEquiposList = objEquiposList;
	}
	/**
	 * @return the subequiposList
	 */
	public List<SubequiposDto> getSubequiposList() {
		return subequiposList;
	}
	/**
	 * @param subequiposList the subequiposList to set
	 */
	public void setSubequiposList(List<SubequiposDto> subequiposList) {
		this.subequiposList = subequiposList;
	}
	/**
	 * @return the objSubequiposList
	 */
	public Object getObjSubequiposList() {
		return objSubequiposList;
	}
	/**
	 * @param objSubequiposList the objSubequiposList to set
	 */
	public void setObjSubequiposList(Object objSubequiposList) {
		this.objSubequiposList = objSubequiposList;
	}
	/**
	 * @return the eqCaracteristicasList
	 */
	public List<EqCaracteristicasDto> getEqCaracteristicasList() {
		return EqCaracteristicasList;
	}
	/**
	 * @param eqCaracteristicasList the eqCaracteristicasList to set
	 */
	public void setEqCaracteristicasList(
			List<EqCaracteristicasDto> eqCaracteristicasList) {
		EqCaracteristicasList = eqCaracteristicasList;
	}
	/**
	 * @return the objEqCaracteristicasList
	 */
	public Object getObjEqCaracteristicasList() {
		return objEqCaracteristicasList;
	}
	/**
	 * @param objEqCaracteristicasList the objEqCaracteristicasList to set
	 */
	public void setObjEqCaracteristicasList(Object objEqCaracteristicasList) {
		this.objEqCaracteristicasList = objEqCaracteristicasList;
	}
	/**
	 * @return the utCaracterisiticasList
	 */
	public List<UTCaracteristicasDto> getUtCaracterisiticasList() {
		return UtCaracterisiticasList;
	}
	/**
	 * @param utCaracterisiticasList the utCaracterisiticasList to set
	 */
	public void setUtCaracterisiticasList(
			List<UTCaracteristicasDto> utCaracterisiticasList) {
		UtCaracterisiticasList = utCaracterisiticasList;
	}
	/**
	 * @return the objUtCaracterisiticasList
	 */
	public Object getObjUtCaracterisiticasList() {
		return objUtCaracterisiticasList;
	}
	/**
	 * @param objUtCaracterisiticasList the objUtCaracterisiticasList to set
	 */
	public void setObjUtCaracterisiticasList(Object objUtCaracterisiticasList) {
		this.objUtCaracterisiticasList = objUtCaracterisiticasList;
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
