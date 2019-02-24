/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 25/09/2012              
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

/**
 * @author WSNADM
 *
 */
public class PagoDetDto {

	private String fregi;
	private int conse; 
	private String fpago;
	private String hpago;
	private String refer;
	private float monto;  // netwr
	private String file_a;
	private String valid;
	private String validDesc;
	private String ernam_val;
	private String aedat_val;
	private String cputm_val;
	private String fvalid;
	private String update;
	private String flag;
	private String folioOper;
	private String concepto;
	private String medioPago;
	private String conceptoTxt;
	private String medioPagoTxt;
	private String observaciones;
	private String fileName1;
	

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
	 * @return the fpago
	 */
	public String getFpago() {
		return fpago;
	}
	/**
	 * @param fpago the fpago to set
	 */
	public void setFpago(String fpago) {
		this.fpago = fpago;
	}
	/**
	 * @return the hpago
	 */
	public String getHpago() {
		return hpago;
	}
	/**
	 * @param hpago the hpago to set
	 */
	public void setHpago(String hpago) {
		this.hpago = hpago;
	}
	/**
	 * @return the refer
	 */
	public String getRefer() {
		return refer;
	}
	/**
	 * @param refer the refer to set
	 */
	public void setRefer(String refer) {
		this.refer = refer;
	}

	/**
	 * @return the file_a
	 */
	public String getFile_a() {
		return file_a;
	}
	/**
	 * @param file_a the file_a to set
	 */
	public void setFile_a(String file_a) {
		this.file_a = file_a;
	}
	/**
	 * @return the valid
	 */
	public String getValid() {
		return valid;
	}
	/**
	 * @param valid the valid to set
	 */
	public void setValid(String valid) {
		this.valid = valid;
	}
	/**
	 * @return the ernam_val
	 */
	public String getErnam_val() {
		return ernam_val;
	}
	/**
	 * @param ernam_val the ernam_val to set
	 */
	public void setErnam_val(String ernam_val) {
		this.ernam_val = ernam_val;
	}
	/**
	 * @return the aedat_val
	 */
	public String getAedat_val() {
		return aedat_val;
	}
	/**
	 * @param aedat_val the aedat_val to set
	 */
	public void setAedat_val(String aedat_val) {
		this.aedat_val = aedat_val;
	}
	/**
	 * @return the cputm_val
	 */
	public String getCputm_val() {
		return cputm_val;
	}
	/**
	 * @param cputm_val the cputm_val to set
	 */
	public void setCputm_val(String cputm_val) {
		this.cputm_val = cputm_val;
	}
	/**
	 * @return the fvalid
	 */
	public String getFvalid() {
		return fvalid;
	}
	/**
	 * @param fvalid the fvalid to set
	 */
	public void setFvalid(String fvalid) {
		this.fvalid = fvalid;
	}
	/**
	 * @return the update_reg
	 */
	public String getUpdate() {
		return update;
	}
	/**
	 * @param update_reg the update_reg to set
	 */
	public void setUpdate(String update) {
		this.update = update;
	}
	/**
	 * @return the validDesc
	 */
	public String getValidDesc() {
		return validDesc;
	}
	/**
	 * @param validDesc the validDesc to set
	 */
	public void setValidDesc(String validDesc) {
		this.validDesc = validDesc;
	}
	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	/**
	 * @return the monto
	 */
	public float getMonto() {
		return monto;
	}
	/**
	 * @param monto the monto to set
	 */
	public void setMonto(float monto) {
		this.monto = monto;
	}
	/**
	 * @return the folioOper
	 */
	public String getFolioOper() {
		return folioOper;
	}
	/**
	 * @param folioOper the folioOper to set
	 */
	public void setFolioOper(String folioOper) {
		this.folioOper = folioOper;
	}	
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getMedioPago() {
		return medioPago;
	}
	public void setMedioPago(String medioPago) {
		this.medioPago = medioPago;
	}
	public String getConceptoTxt() {
		return conceptoTxt;
	}
	public void setConceptoTxt(String conceptoTxt) {
		this.conceptoTxt = conceptoTxt;
	}
	public String getMedioPagoTxt() {
		return medioPagoTxt;
	}
	public void setMedioPagoTxt(String medioPagoTxt) {
		this.medioPagoTxt = medioPagoTxt;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getFileName1() {
		return fileName1;
	}
	public void setFileName1(String fileName1) {
		this.fileName1 = fileName1;
	}
	@Override
	public String toString() {
		return "PagoDetDto [fregi=" + fregi + ", conse=" + conse + ", fpago="
				+ fpago + ", hpago=" + hpago + ", refer=" + refer + ", monto="
				+ monto + ", file_a=" + file_a + ", valid=" + valid
				+ ", validDesc=" + validDesc + ", ernam_val=" + ernam_val
				+ ", aedat_val=" + aedat_val + ", cputm_val=" + cputm_val
				+ ", fvalid=" + fvalid + ", update=" + update + ", flag="
				+ flag + ", folioOper=" + folioOper + ", concepto=" + concepto
				+ ", medioPago=" + medioPago + ", conceptoTxt=" + conceptoTxt
				+ ", medioPagoTxt=" + medioPagoTxt + ", observaciones="
				+ observaciones + ", fileName1=" + fileName1 + "]";
	}
}
