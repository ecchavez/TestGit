package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class UbicacionTecnicaDto {
	private String ubicacionDescripcion = "";
	private String ubicacionClave = "";
	private String tipoEstructura = "";
	private String id_ut_sup;
	private String id_ut_supx;
	private String ind_ut;
	
	private String i_usuario;
	private String i_id_ut_sup;
	private String i_charact;
	private String i_equnr_price;
	
	private List<EquipoDto> equiposList = new ArrayList<EquipoDto>();
	private List<UTCaracteristicasDto> UtCaracterisiticasList = new ArrayList<UTCaracteristicasDto>();
	
	private List<UbicacionTecnicaDto> pisosList = new ArrayList<UbicacionTecnicaDto>();
	
	
	/*datos de arbol*/
	private String id;
	private String text;
	private Boolean expanded;
	private String spriteCssClass;
	private Object items;

	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the expanded
	 */
	public Boolean getExpanded() {
		return expanded;
	}
	/**
	 * @param expanded the expanded to set
	 */
	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}
	/**
	 * @return the spriteCssClass
	 */
	public String getSpriteCssClass() {
		return spriteCssClass;
	}
	/**
	 * @param spriteCssClass the spriteCssClass to set
	 */
	public void setSpriteCssClass(String spriteCssClass) {
		this.spriteCssClass = spriteCssClass;
	}
	/**
	 * @return the items
	 */
	public Object getItems() {
		return items;
	}
	/**
	 * @param items the items to set
	 */
	public void setItems(Object items) {
		this.items = items;
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
	
	public List<UbicacionTecnicaDto> getPisosList() {
		return pisosList;
	}
	public void setPisosList(List<UbicacionTecnicaDto> pisosList) {
		this.pisosList = pisosList;
	}
	public List<EquipoDto> getEquiposList() {
		return equiposList;
	}
	public void setEquiposList(List<EquipoDto> equiposList) {
		this.equiposList = equiposList;
	}
	public String getI_usuario() {
		return i_usuario;
	}
	public void setI_usuario(String i_usuario) {
		this.i_usuario = i_usuario;
	}
	public String getI_id_ut_sup() {
		return i_id_ut_sup;
	}
	public void setI_id_ut_sup(String i_id_ut_sup) {
		this.i_id_ut_sup = i_id_ut_sup;
	}
	public String getI_charact() {
		return i_charact;
	}
	public void setI_charact(String i_charact) {
		this.i_charact = i_charact;
	}
	public String getI_equnr_price() {
		return i_equnr_price;
	}
	public void setI_equnr_price(String i_equnr_price) {
		this.i_equnr_price = i_equnr_price;
	}
	/**
	 * @return the ubicacionDescripcion
	 */
	public String getUbicacionDescripcion() {
		return ubicacionDescripcion;
	}
	/**
	 * @param ubicacionDescripcion the ubicacionDescripcion to set
	 */
	public void setUbicacionDescripcion(String ubicacionDescripcion) {
		this.ubicacionDescripcion = ubicacionDescripcion;
	}
	/**
	 * @return the ubicacionClave
	 */
	public String getUbicacionClave() {
		return ubicacionClave;
	}
	/**
	 * @param ubicacionClave the ubicacionClave to set
	 */
	public void setUbicacionClave(String ubicacionClave) {
		this.ubicacionClave = ubicacionClave;
	}
	/**
	 * @return the tipoEstructura
	 */
	public String getTipoEstructura() {
		return tipoEstructura;
	}
	/**
	 * @param tipoEstructura the tipoEstructura to set
	 */
	public void setTipoEstructura(String tipoEstructura) {
		this.tipoEstructura = tipoEstructura;
	}
	/**
	 * @return the id_ut_sup
	 */
	public String getId_ut_sup() {
		return id_ut_sup;
	}
	/**
	 * @param id_ut_sup the id_ut_sup to set
	 */
	public void setId_ut_sup(String id_ut_sup) {
		this.id_ut_sup = id_ut_sup;
	}
	/**
	 * @return the id_ut_supx
	 */
	public String getId_ut_supx() {
		return id_ut_supx;
	}
	/**
	 * @param id_ut_supx the id_ut_supx to set
	 */
	public void setId_ut_supx(String id_ut_supx) {
		this.id_ut_supx = id_ut_supx;
	}
	/**
	 * @return the ind_ut
	 */
	public String getInd_ut() {
		return ind_ut;
	}
	/**
	 * @param ind_ut the ind_ut to set
	 */
	public void setInd_ut(String ind_ut) {
		this.ind_ut = ind_ut;
	}

}
