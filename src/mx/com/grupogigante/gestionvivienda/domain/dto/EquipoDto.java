/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que encapsula los fields de Equipos 
 * Fecha de creación: XX/06/2012               
 */
public class EquipoDto {

	public String id_ubi_tec;
	public String id_ubi_tecx;
	public String id_equnr;
	public String id_equnrx;
	public String id_tipo;
	public String id_estatus;
	public String id_estatus_det;
	public String id_total_metros;
	public String id_vista;
	public double id_precio;
	public String id_precio_format = "$0.00";
	
	private List<SubequipoDto> subequiposList = new ArrayList<SubequipoDto>();
	private List<EqCaracteristicasDto> EqCaracteristicasList = new ArrayList<EqCaracteristicasDto>();
	private List<EqPriceDto> EqPriceList = new ArrayList<EqPriceDto>();
	
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
	public String getId_estatus_det() {
		return id_estatus_det;
	}
	public void setId_estatus_det(String id_estatus_det) {
		this.id_estatus_det = id_estatus_det;
	}
	public String getId_precio_format() {
		return id_precio_format;
	}
	public void setId_precio_format(String id_precio_format) {
		this.id_precio_format = id_precio_format;
	}
	public String getId_total_metros() {
		return id_total_metros;
	}
	public void setId_total_metros(String id_total_metros) {
		this.id_total_metros = id_total_metros;
	}
	public String getId_vista() {
		return id_vista;
	}
	public void setId_vista(String id_vista) {
		this.id_vista = id_vista;
	}
	public double getId_precio() {
		return id_precio;
	}
	public void setId_precio(double id_precio) {
		this.id_precio = id_precio;
	}
	public String getId_tipo() {
		return id_tipo;
	}
	public void setId_tipo(String id_tipo) {
		this.id_tipo = id_tipo;
	}
	public String getId_estatus() {
		return id_estatus;
	}
	public void setId_estatus(String id_estatus) {
		this.id_estatus = id_estatus;
	}
	public List<EqCaracteristicasDto> getEqCaracteristicasList() {
		return EqCaracteristicasList;
	}
	public void setEqCaracteristicasList(
			List<EqCaracteristicasDto> eqCaracteristicasList) {
		EqCaracteristicasList = eqCaracteristicasList;
	}
	public List<EqPriceDto> getEqPriceList() {
		return EqPriceList;
	}
	public void setEqPriceList(List<EqPriceDto> eqPriceList) {
		EqPriceList = eqPriceList;
	}
	public List<SubequipoDto> getSubequiposList() {
		return subequiposList;
	}
	public void setSubequiposList(List<SubequipoDto> subequiposList) {
		this.subequiposList = subequiposList;
	}
	/**
	 * @return the id_ubi_tec
	 */
	public String getId_ubi_tec() {
		return id_ubi_tec;
	}
	/**
	 * @param id_ubi_tec the id_ubi_tec to set
	 */
	public void setId_ubi_tec(String id_ubi_tec) {
		this.id_ubi_tec = id_ubi_tec;
	}
	/**
	 * @return the id_ubi_tecx
	 */
	public String getId_ubi_tecx() {
		return id_ubi_tecx;
	}
	/**
	 * @param id_ubi_tecx the id_ubi_tecx to set
	 */
	public void setId_ubi_tecx(String id_ubi_tecx) {
		this.id_ubi_tecx = id_ubi_tecx;
	}
	/**
	 * @return the id_equnr
	 */
	public String getId_equnr() {
		return id_equnr;
	}
	/**
	 * @param id_equnr the id_equnr to set
	 */
	public void setId_equnr(String id_equnr) {
		this.id_equnr = id_equnr;
	}
	/**
	 * @return the id_equnrx
	 */
	public String getId_equnrx() {
		return id_equnrx;
	}
	/**
	 * @param id_equnrx the id_equnrx to set
	 */
	public void setId_equnrx(String id_equnrx) {
		this.id_equnrx = id_equnrx;
	}
	
	}
