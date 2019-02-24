/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 17/07/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

/**
 * Clase que encapsula los fields de Cotizacion Header
 * Fecha de creación: 17/07/2012               
 */
public class EqPriceDto {

	private String id_equnr;
	private double price;//decimales 
	private String currs;
	private int numl;
	private String knumh;
	private int kopos;
	
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
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * @return the currs
	 */
	public String getCurrs() {
		return currs;
	}
	/**
	 * @param currs the currs to set
	 */
	public void setCurrs(String currs) {
		this.currs = currs;
	}
	/**
	 * @return the numl
	 */
	public int getNuml() {
		return numl;
	}
	/**
	 * @param numl the numl to set
	 */
	public void setNuml(int numl) {
		this.numl = numl;
	}
	/**
	 * @return the knumh
	 */
	public String getKnumh() {
		return knumh;
	}
	/**
	 * @param knumh the knumh to set
	 */
	public void setKnumh(String knumh) {
		this.knumh = knumh;
	}
	/**
	 * @return the kopos
	 */
	public int getKopos() {
		return kopos;
	}
	/**
	 * @param kopos the kopos to set
	 */
	public void setKopos(int kopos) {
		this.kopos = kopos;
	}


}
