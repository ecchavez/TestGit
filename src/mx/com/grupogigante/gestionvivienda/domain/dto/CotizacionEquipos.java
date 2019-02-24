/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 20/09/2012              
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

/**
 * @author WSNADM
 *
 */
public class CotizacionEquipos {

	
	private String id_cotiz_z;
	private int posnr_z;
	private String id_equnr;
	private String id_equnrx;
	private char tipo;
	private float netwr;
	private String moneda;
	/**
	 * @return the id_cotiz_z
	 */
	public String getId_cotiz_z() {
		return id_cotiz_z;
	}
	/**
	 * @param id_cotiz_z the id_cotiz_z to set
	 */
	public void setId_cotiz_z(String id_cotiz_z) {
		this.id_cotiz_z = id_cotiz_z;
	}
	/**
	 * @return the posnr_z
	 */
	public int getPosnr_z() {
		return posnr_z;
	}
	/**
	 * @param posnr_z the posnr_z to set
	 */
	public void setPosnr_z(int posnr_z) {
		this.posnr_z = posnr_z;
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
	/**
	 * @return the tipo
	 */
	public char getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the netwr
	 */
	public float getNetwr() {
		return netwr;
	}
	/**
	 * @param netwr the netwr to set
	 */
	public void setNetwr(float netwr) {
		this.netwr = netwr;
	}
	/**
	 * @return the moneda
	 */
	public String getMoneda() {
		return moneda;
	}
	/**
	 * @param moneda the moneda to set
	 */
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
}
