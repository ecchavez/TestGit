/**
 *  Clase creada por Osvaldo Rodriguez Martinez (SACTI consultores) con fecha de 02/11/2012
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

/**
 * @author osvaldo
 *
 */
public class CarpetasDigitEstatusDto {

	private String id_ticket_area;
	private String id_ticket_file;
	private String id_ticket_statx;
	private String id_ticket_stat;
	
	
	/*datos de arbol*/
	private String id;
	private String text;
	private Boolean expanded;
	private String spriteCssClass;
	private Object items;
	
	public String getId_ticket_statx() {
		return id_ticket_statx;
	}
	public void setId_ticket_statx(String id_ticket_statx) {
		this.id_ticket_statx = id_ticket_statx;
	}
	public String getId_ticket_stat() {
		return id_ticket_stat;
	}
	public void setId_ticket_stat(String id_ticket_stat) {
		this.id_ticket_stat = id_ticket_stat;
	}
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
	 * @return the id_ticket_area
	 */
	public String getId_ticket_area() {
		return id_ticket_area;
	}
	/**
	 * @param id_ticket_area the id_ticket_area to set
	 */
	public void setId_ticket_area(String id_ticket_area) {
		this.id_ticket_area = id_ticket_area;
	}
	/**
	 * @return the id_ticket_file
	 */
	public String getId_ticket_file() {
		return id_ticket_file;
	}
	/**
	 * @param id_ticket_file the id_ticket_file to set
	 */
	public void setId_ticket_file(String id_ticket_file) {
		this.id_ticket_file = id_ticket_file;
	}	
}
