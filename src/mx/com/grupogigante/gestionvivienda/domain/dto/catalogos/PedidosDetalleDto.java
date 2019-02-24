/**
 *  Clase creada por Osvaldo Rodriguez Martinez (SACTI consultores) con fecha de 22/10/2012
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.catalogos;

/**
 * @author osvaldo
 *
 */
public class PedidosDetalleDto {
	String id_cotiz_z;
	String vbeln_cot;
	String vbeln_ped;
	String posnr_z;
	String posnr_cot;
	String posnr_ped;
	String cve_web;
	String matnr;
	String descr;
	String netwr;
	String netwrp;
	String moneda;

	
	public String getId_cotiz_z() {
		return id_cotiz_z;
	}
	public void setId_cotiz_z(String id_cotiz_z) {
		this.id_cotiz_z = id_cotiz_z;
	}
	public String getVbeln_cot() {
		return vbeln_cot;
	}
	public void setVbeln_cot(String vbeln_cot) {
		this.vbeln_cot = vbeln_cot;
	}
	public String getVbeln_ped() {
		return vbeln_ped;
	}
	public void setVbeln_ped(String vbeln_ped) {
		this.vbeln_ped = vbeln_ped;
	}
	public String getPosnr_z() {
		return posnr_z;
	}
	public void setPosnr_z(String posnr_z) {
		this.posnr_z = posnr_z;
	}
	public String getPosnr_cot() {
		return posnr_cot;
	}
	public void setPosnr_cot(String posnr_cot) {
		this.posnr_cot = posnr_cot;
	}
	public String getPosnr_ped() {
		return posnr_ped;
	}
	public void setPosnr_ped(String posnr_ped) {
		this.posnr_ped = posnr_ped;
	}
	public String getCve_web() {
		return cve_web;
	}
	public void setCve_web(String cve_web) {
		this.cve_web = cve_web;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getNetwr() {
		return netwr;
	}
	public void setNetwr(String netwr) {
		this.netwr = netwr;
	}
	public String getNetwrp() {
		return netwrp;
	}
	public void setNetwrp(String netwrp) {
		this.netwrp = netwrp;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
}
