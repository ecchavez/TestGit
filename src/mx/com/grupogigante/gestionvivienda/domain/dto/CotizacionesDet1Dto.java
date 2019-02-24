/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 13/09/2012
 *              
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.sql.Date;

/**
 * Clase que encapsula los fields de Cotizaciones Det1
 * Fecha de creación: 13/09/2012               
 */
public class CotizacionesDet1Dto {
	private String id_cotiz_z;
	private String vbeln_cot;
	private String vbeln_ped;
	private int posnr_z;
	private int posnr_cot;
	private int posnr_ped;
	private String cve_web;
	private String matnr ;
	private String descr;
	private double netwr;
	private double netwrp;
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
	 * @return the vbeln_cot
	 */
	public String getVbeln_cot() {
		return vbeln_cot;
	}
	/**
	 * @param vbeln_cot the vbeln_cot to set
	 */
	public void setVbeln_cot(String vbeln_cot) {
		this.vbeln_cot = vbeln_cot;
	}
	/**
	 * @return the vbeln_ped
	 */
	public String getVbeln_ped() {
		return vbeln_ped;
	}
	/**
	 * @param vbeln_ped the vbeln_ped to set
	 */
	public void setVbeln_ped(String vbeln_ped) {
		this.vbeln_ped = vbeln_ped;
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
	 * @return the posnr_cot
	 */
	public int getPosnr_cot() {
		return posnr_cot;
	}
	/**
	 * @param posnr_cot the posnr_cot to set
	 */
	public void setPosnr_cot(int posnr_cot) {
		this.posnr_cot = posnr_cot;
	}
	/**
	 * @return the posnr_ped
	 */
	public int getPosnr_ped() {
		return posnr_ped;
	}
	/**
	 * @param posnr_ped the posnr_ped to set
	 */
	public void setPosnr_ped(int posnr_ped) {
		this.posnr_ped = posnr_ped;
	}
	/**
	 * @return the cve_web
	 */
	public String getCve_web() {
		return cve_web;
	}
	/**
	 * @param cve_web the cve_web to set
	 */
	public void setCve_web(String cve_web) {
		this.cve_web = cve_web;
	}
	/**
	 * @return the matnr
	 */
	public String getMatnr() {
		return matnr;
	}
	/**
	 * @param matnr the matnr to set
	 */
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	/**
	 * @return the descr
	 */
	public String getDescr() {
		return descr;
	}
	/**
	 * @param descr the descr to set
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}
	/**
	 * @return the netwr
	 */
	public double getNetwr() {
		return netwr;
	}
	/**
	 * @param netwr the netwr to set
	 */
	public void setNetwr(double netwr) {
		this.netwr = netwr;
	}
	/**
	 * @return the netwrp
	 */
	public double getNetwrp() {
		return netwrp;
	}
	/**
	 * @param netwrp the netwrp to set
	 */
	public void setNetwrp(double netwrp) {
		this.netwrp = netwrp;
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