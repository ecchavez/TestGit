/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 25/09/2012              
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;

/**
 * @author WSNADM
 *
 */
public class PagoHdrDto {

	private String vblen;
	private String kunnr;
	private String kunnrtx;
	private String id_equnr;
	private String fregi;
	private String netwr_x_pagar;
	private String netwr_pag;
	private String ernam;
	private String aedat;
	private String cptum;
	private String dpto;
	private String numPagos;
	private ArrayList<PagoDetDto> pagosDetList = new ArrayList<PagoDetDto>();
	private String stContra;
	/**
	 * @return the vblen
	 */
	public String getVblen() {
		return vblen;
	}
	/**
	 * @param vblen the vblen to set
	 */
	public void setVblen(String vblen) {
		this.vblen = vblen;
	}
	/**
	 * @return the kunnr
	 */
	public String getKunnr() {
		return kunnr;
	}
	/**
	 * @param kunnr the kunnr to set
	 */
	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
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
	 * @return the ernam
	 */
	public String getErnam() {
		return ernam;
	}
	/**
	 * @param ernam the ernam to set
	 */
	public void setErnam(String ernam) {
		this.ernam = ernam;
	}
	/**
	 * @return the aedat
	 */
	public String getAedat() {
		return aedat;
	}
	/**
	 * @param aedat the aedat to set
	 */
	public void setAedat(String aedat) {
		this.aedat = aedat;
	}
	/**
	 * @return the cptum
	 */
	public String getCptum() {
		return cptum;
	}
	/**
	 * @param cptum the cptum to set
	 */
	public void setCptum(String cptum) {
		this.cptum = cptum;
	}
	/**
	 * @return the pagosDetList
	 */
	public ArrayList<PagoDetDto> getPagosDetList() {
		return pagosDetList;
	}
	/**
	 * @param pagosDetList the pagosDetList to set
	 */
	public void setPagosDetList(ArrayList<PagoDetDto> pagosDetList) {
		this.pagosDetList = pagosDetList;
	}
	/**
	 * @return the kunnrtx
	 */
	public String getKunnrtx() {
		return kunnrtx;
	}
	/**
	 * @param kunnrtx the kunnrtx to set
	 */
	public void setKunnrtx(String kunnrtx) {
		this.kunnrtx = kunnrtx;
	}
	/**
	 * @return the dpto
	 */
	public String getDpto() {
		return dpto;
	}
	/**
	 * @param dpto the dpto to set
	 */
	public void setDpto(String dpto) {
		this.dpto = dpto;
	}
	/**
	 * @return the netwr_x_pagar
	 */
	public String getNetwr_x_pagar() {
		return netwr_x_pagar;
	}
	/**
	 * @param netwr_x_pagar the netwr_x_pagar to set
	 */
	public void setNetwr_x_pagar(String netwr_x_pagar) {
		this.netwr_x_pagar = netwr_x_pagar;
	}
	/**
	 * @return the netwr_pag
	 */
	public String getNetwr_pag() {
		return netwr_pag;
	}
	/**
	 * @param netwr_pag the netwr_pag to set
	 */
	public void setNetwr_pag(String netwr_pag) {
		this.netwr_pag = netwr_pag;
	}
	public String getNumPagos() {
		return numPagos;
	}
	public void setNumPagos(String numPagos) {
		this.numPagos = numPagos;
	}
	public String getStContra() {
		return stContra;
	}
	public void setStContra(String stContra) {
		this.stContra = stContra;
	}
	
	
	
}
