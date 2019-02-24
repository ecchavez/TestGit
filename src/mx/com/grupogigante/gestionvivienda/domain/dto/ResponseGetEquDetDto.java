/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 17/07/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que encapsula los fields de Cotizaciones  Detalle 
 * 
 * Fecha de creación: 17/07/2012               
 */
public class ResponseGetEquDetDto {

	private List<Equipo2Dto> equipos2List = new ArrayList<Equipo2Dto>();
	private Object objEquipos2List = new Object();
	private List<SubequipoDto> subequiposList = new ArrayList<SubequipoDto>();
	private Object objSubequiposList = new Object();
	private List<EqCaracteristicasDto> EqCaracteristicasList = new ArrayList<EqCaracteristicasDto>();
	private Object objEqCaracteristicasList = new Object();
	private List<EqPriceDto> EqPriceList = new ArrayList<EqPriceDto>();
	private Object objEqPriceList = new Object();
	private String mensaje;
	private String descripcion;
	
	/**
	 * @return the equiposList
	 */
	public List<Equipo2Dto> getEquipos2List() {
		return equipos2List;
	}
	/**
	 * @param equiposList the equiposList to set
	 */
	public void setEquipos2List(List<Equipo2Dto> equipos2List) {
		this.equipos2List = equipos2List;
	}
	/**
	 * @return the objEquiposList
	 */
	public Object getObjEquipos2List() {
		return objEquipos2List;
	}
	/**
	 * @param objEquiposList the objEquiposList to set
	 */
	public void setObjEquipos2List(Object objEquipos2List) {
		this.objEquipos2List = objEquipos2List;
	}
	/**
	 * @return the subequiposList
	 */
	public List<SubequipoDto> getSubequiposList() {
		return subequiposList;
	}
	/**
	 * @param subequiposList the subequiposList to set
	 */
	public void setSubequiposList(List<SubequipoDto> subequiposList) {
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
	 * @return the eqPrecioList
	 */
	public List<EqPriceDto> getEqPriceList() {
		return EqPriceList;
	}
	/**
	 * @param eqPrecioList the eqPrecioList to set
	 */
	public void setEqPriceList(List<EqPriceDto> eqPriceList) {
		EqPriceList = eqPriceList;
	}
	/**
	 * @return the objEqPrecioList
	 */
	public Object getObjEqPriceList() {
		return objEqPriceList;
	}
	/**
	 * @param objEqPrecioList the objEqPrecioList to set
	 */
	public void setObjEqPriceList(Object objEqPriceList) {
		this.objEqPriceList = objEqPriceList;
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
