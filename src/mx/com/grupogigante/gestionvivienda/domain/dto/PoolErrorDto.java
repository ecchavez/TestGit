/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 04/10/2012              
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

/**
 * @author WSNADM
 *
 */
public class PoolErrorDto {

	private String fregi;
	private String extract;
	private int conse;
	private String bapi_msg;
	/**
	 * @return the fregi
	 */
	public String getFregi() {
		return fregi;
	}
	/**
	 * @param fregi the fregi to set
	 */
	public void setFregi(String fregi) {
		this.fregi = fregi;
	}
	/**
	 * @return the extract
	 */
	public String getExtract() {
		return extract;
	}
	/**
	 * @param extract the extract to set
	 */
	public void setExtract(String extract) {
		this.extract = extract;
	}
	/**
	 * @return the conse
	 */
	public int getConse() {
		return conse;
	}
	/**
	 * @param conse the conse to set
	 */
	public void setConse(int conse) {
		this.conse = conse;
	}
	/**
	 * @return the bapi_msg
	 */
	public String getBapi_msg() {
		return bapi_msg;
	}
	/**
	 * @param bapi_msg the bapi_msg to set
	 */
	public void setBapi_msg(String bapi_msg) {
		this.bapi_msg = bapi_msg;
	}
}
