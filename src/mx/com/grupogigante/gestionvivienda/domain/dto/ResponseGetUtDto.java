/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 16/07/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que encapsula los fields para la respuesta
 * Fecha de creación: 16/07/2012               
 */

public class ResponseGetUtDto {
	
	private String mensaje;
	private String descripcion;
	private String name1;
	private String city;
	private String post_code1;
	private String street;
	private String house_num1;
	private String country;
	private String tel_number;
	private String tel_extens;
	private String mail;
	private List<UbicacionTecnicaDto> ubicacionesList = new ArrayList<UbicacionTecnicaDto>();
	private Object objUbicacionesList = new Object();
	private List<EquipoDto> equiposList = new ArrayList<EquipoDto>();
	private Object objEquiposList = new Object();
	private List<SubequipoDto> subequiposList = new ArrayList<SubequipoDto>();
	private Object objSubequiposList = new Object();
	private List<EqCaracteristicasDto> EqCaracteristicasList = new ArrayList<EqCaracteristicasDto>();
	private Object objEqCaracteristicasList = new Object();
	private List<UTCaracteristicasDto> UtCaracteristicasList = new ArrayList<UTCaracteristicasDto>();
	private Object objUtCaracteristicasList = new Object();
	
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
	/**
	 * @return the name1
	 */
	public String getName1() {
		return name1;
	}
	/**
	 * @param name1 the name1 to set
	 */
	public void setName1(String name1) {
		this.name1 = name1;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the post_code1
	 */
	public String getPost_code1() {
		return post_code1;
	}
	/**
	 * @param post_code1 the post_code1 to set
	 */
	public void setPost_code1(String post_code1) {
		this.post_code1 = post_code1;
	}
	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	/**
	 * @return the house_num1
	 */
	public String getHouse_num1() {
		return house_num1;
	}
	/**
	 * @param house_num1 the house_num1 to set
	 */
	public void setHouse_num1(String house_num1) {
		this.house_num1 = house_num1;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the tel_number
	 */
	public String getTel_number() {
		return tel_number;
	}
	/**
	 * @param tel_number the tel_number to set
	 */
	public void setTel_number(String tel_number) {
		this.tel_number = tel_number;
	}
	/**
	 * @return the tel_extens
	 */
	public String getTel_extens() {
		return tel_extens;
	}
	/**
	 * @param tel_extens the tel_extens to set
	 */
	public void setTel_extens(String tel_extens) {
		this.tel_extens = tel_extens;
	}
	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}
	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
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
	 * @return the utCaracteristicasList
	 */
	public List<UTCaracteristicasDto> getUtCaracteristicasList() {
		return UtCaracteristicasList;
	}
	/**
	 * @param utCaracteristicasList the utCaracteristicasList to set
	 */
	public void setUtCaracteristicasList(
			List<UTCaracteristicasDto> utCaracteristicasList) {
		UtCaracteristicasList = utCaracteristicasList;
	}
	/**
	 * @return the objUtCaracteristicasList
	 */
	public Object getObjUtCaracteristicasList() {
		return objUtCaracteristicasList;
	}
	/**
	 * @param objUtCaracteristicasList the objUtCaracteristicasList to set
	 */
	public void setObjUtCaracteristicasList(Object objUtCaracteristicasList) {
		this.objUtCaracteristicasList = objUtCaracteristicasList;
	}


	

}
