/**
 *  Clase creada por Osvaldo Rodriguez Martinez (SACTI consultores) con fecha de 02/11/2012
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

/**
 * @author osvaldo
 *
 */
public class CarpetasDigitDto {

	private String id_ticket_area;
	private String id_ticket_file;
	private String id_ticket_areax;
	
	
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
	/**
	 * @return the id_ticket_areax
	 */
	public String getId_ticket_areax() {
		return id_ticket_areax;
	}
	/**
	 * @param id_ticket_areax the id_ticket_areax to set
	 */
	public void setId_ticket_areax(String id_ticket_areax) {
		this.id_ticket_areax = id_ticket_areax;
	}	

}
