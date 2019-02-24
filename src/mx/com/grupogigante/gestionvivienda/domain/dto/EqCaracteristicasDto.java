/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

/**
 * Clase que encapsula los fields de Caracteristicas de Equipo
 * Fecha de creación: XX/06/2012               
 */
public class EqCaracteristicasDto {

	private String id_equnr;
	private String charact;
	private String charactx;
	private String value;
	
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
	 * @return the charact
	 */
	public String getCharact() {
		return charact;
	}
	/**
	 * @param charact the charact to set
	 */
	public void setCharact(String charact) {
		this.charact = charact;
	}
	/**
	 * @return the charactx
	 */
	public String getCharactx() {
		return charactx;
	}
	/**
	 * @param charactx the charactx to set
	 */
	public void setCharactx(String charactx) {
		this.charactx = charactx;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	

}
