/**
 *  Clase creada por Osvaldo Rodriguez Martinez (SACTI consultores) con fecha de 24/09/2012
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.simulador;

/**
 * @author osvaldo
 *
 */
public class CotizadorBillPlaningDto {
	String id_cotiz_z;
	String posnr_z;
	int conse;
	String conce;
	String concex;
	String flim;
	String total;
	float sim1;
	float sim2;
	String vbeln_cot;
	String vbeln_ped;

	
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
	public String getId_cotiz_z() {
		return id_cotiz_z;
	}
	public void setId_cotiz_z(String id_cotiz_z) {
		this.id_cotiz_z = id_cotiz_z;
	}
	public String getPosnr_z() {
		return posnr_z;
	}
	public void setPosnr_z(String posnr_z) {
		this.posnr_z = posnr_z;
	}
	public int getConse() {
		return conse;
	}
	public void setConse(int conse) {
		this.conse = conse;
	}
	public String getConce() {
		return conce;
	}
	public void setConce(String conce) {
		this.conce = conce;
	}
	public String getConcex() {
		return concex;
	}
	public void setConcex(String concex) {
		this.concex = concex;
	}
	public String getFlim() {
		return flim;
	}
	public void setFlim(String flim) {
		this.flim = flim;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public float getSim1() {
		return sim1;
	}
	public void setSim1(float sim1) {
		this.sim1 = sim1;
	}
	public float getSim2() {
		return sim2;
	}
	public void setSim2(float sim2) {
		this.sim2 = sim2;
	}
	@Override
	public String toString() {
		return "CotizadorBillPlaningDto [id_cotiz_z=" + id_cotiz_z
				+ ", posnr_z=" + posnr_z + ", conse=" + conse + ", conce="
				+ conce + ", concex=" + concex + ", flim=" + flim + ", total="
				+ total + ", sim1=" + sim1 + ", sim2=" + sim2 + ", vbeln_cot="
				+ vbeln_cot + ", vbeln_ped=" + vbeln_ped + "]";
	}
	
}
