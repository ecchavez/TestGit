/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Clase que encapsula los fields de Cotizacion Header
 * Fecha de creación: XX/06/2012               
 */
public class CotizacionHdrDto {
	
	private String id_ut_sup;
	private String id_car_cli;
	private String id_cotiz_z;
	private String vbeln_cot;
	private String vbeln_ped;
	private String id_equnr;
	private String id_equnrDesc;
	private String tive;
	private String tivex;
	private String auart;
	private String aedat;
	private String cputm;
	private String datab;
	private String datub;
	private String id_cli_sap;
	private int nbod;
	private int nest;
	private Double m2tot;
	private String fech_entreg;
	private Double descp;
	private Double descm;
	private long numl;
	private int dias_pago;
	private char si_no_pasa;
	private int spaso;
	private String spasox;
	private int npaso;
	private String npasox;
	private String msgbapi;
	private String st_contra;
	
	/**
	 * @return the id_ut_sup
	 */
	public String getId_ut_sup() {
		return id_ut_sup;
	}
	/**
	 * @param id_ut_sup the id_ut_sup to set
	 */
	public void setId_ut_sup(String id_ut_sup) {
		this.id_ut_sup = id_ut_sup;
	}
	/**
	 * @return the id_car_cli
	 */
	public String getId_car_cli() {
		return id_car_cli;
	}
	/**
	 * @param id_car_cli the id_car_cli to set
	 */
	public void setId_car_cli(String id_car_cli) {
		this.id_car_cli = id_car_cli;
	}
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
	 * @return the tive
	 */
	public String getTive() {
		return tive;
	}
	/**
	 * @param tive the tive to set
	 */
	public void setTive(String tive) {
		this.tive = tive;
	}
	/**
	 * @return the tivex
	 */
	public String getTivex() {
		return tivex;
	}
	/**
	 * @param tivex the tivex to set
	 */
	public void setTivex(String tivex) {
		this.tivex = tivex;
	}
	/**
	 * @return the auart
	 */
	public String getAuart() {
		return auart;
	}
	/**
	 * @param auart the auart to set
	 */
	public void setAuart(String auart) {
		this.auart = auart;
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
	 * @return the cputm
	 */
	public String getCputm() {
		return cputm;
	}
	/**
	 * @param cputm the cputm to set
	 */
	public void setCputm(String cputm) {
		this.cputm = cputm;
	}
	/**
	 * @return the datab
	 */
	public String getDatab() {
		return datab;
	}
	/**
	 * @param datab the datab to set
	 */
	public void setDatab(String datab) {
		this.datab = datab;
	}
	/**
	 * @return the datub
	 */
	public String getDatub() {
		return datub;
	}
	/**
	 * @param datub the datub to set
	 */
	public void setDatub(String datub) {
		this.datub = datub;
	}
	/**
	 * @return the id_cli_sap
	 */
	public String getId_cli_sap() {
		return id_cli_sap;
	}
	/**
	 * @param id_cli_sap the id_cli_sap to set
	 */
	public void setId_cli_sap(String id_cli_sap) {
		this.id_cli_sap = id_cli_sap;
	}
	/**
	 * @return the nbod
	 */
	public int getNbod() {
		return nbod;
	}
	/**
	 * @param nbod the nbod to set
	 */
	public void setNbod(int nbod) {
		this.nbod = nbod;
	}
	/**
	 * @return the nest
	 */
	public int getNest() {
		return nest;
	}
	/**
	 * @param nest the nest to set
	 */
	public void setNest(int nest) {
		this.nest = nest;
	}
	/**
	 * @return the m2tot
	 */
	public double getM2tot() {
		return m2tot;
	}
	/**
	 * @param m2tot the m2tot to set
	 */
	public void setM2tot(double m2tot) {
		this.m2tot = m2tot;
	}
	/**
	 * @return the fech_entreg
	 */
	public String getFech_entreg() {
		return fech_entreg;
	}
	/**
	 * @param fech_entreg the fech_entreg to set
	 */
	public void setFech_entreg(String fech_entreg) {
		this.fech_entreg = fech_entreg;
	}
	/**
	 * @return the descp
	 */
	public double getDescp() {
		return descp;
	}
	/**
	 * @param descp the descp to set
	 */
	public void setDescp(double descp) {
		this.descp = descp;
	}
	/**
	 * @return the descm
	 */
	public double getDescm() {
		return descm;
	}
	/**
	 * @param descm the descm to set
	 */
	public void setDescm(double descm) {
		this.descm = descm;
	}
	/**
	 * @return the numl
	 */
	public long getNuml() {
		return numl;
	}
	/**
	 * @param numl the numl to set
	 */
	public void setNuml(long numl) {
		this.numl = numl;
	}
	/**
	 * @return the dias_pago
	 */
	public int getDias_pago() {
		return dias_pago;
	}
	/**
	 * @param dias_pago the dias_pago to set
	 */
	public void setDias_pago(int dias_pago) {
		this.dias_pago = dias_pago;
	}
	/**
	 * @return the si_no_pasa
	 */
	public char getSi_no_pasa() {
		return si_no_pasa;
	}
	/**
	 * @param si_no_pasa the si_no_pasa to set
	 */
	public void setSi_no_pasa(char si_no_pasa) {
		this.si_no_pasa = si_no_pasa;
	}
	/**
	 * @return the spaso
	 */
	public int getSpaso() {
		return spaso;
	}
	/**
	 * @param spaso the spaso to set
	 */
	public void setSpaso(int spaso) {
		this.spaso = spaso;
	}
	/**
	 * @return the spasox
	 */
	public String getSpasox() {
		return spasox;
	}
	/**
	 * @param spasox the spasox to set
	 */
	public void setSpasox(String spasox) {
		this.spasox = spasox;
	}
	/**
	 * @return the npaso
	 */
	public int getNpaso() {
		return npaso;
	}
	/**
	 * @param npaso the npaso to set
	 */
	public void setNpaso(int npaso) {
		this.npaso = npaso;
	}
	/**
	 * @return the npasox
	 */
	public String getNpasox() {
		return npasox;
	}
	/**
	 * @param npasox the npasox to set
	 */
	public void setNpasox(String npasox) {
		this.npasox = npasox;
	}
	/**
	 * @return the msgbapi
	 */
	public String getMsgbapi() {
		return msgbapi;
	}
	/**
	 * @param msgbapi the msgbapi to set
	 */
	public void setMsgbapi(String msgbapi) {
		this.msgbapi = msgbapi;
	}
	/**
	 * @param m2tot the m2tot to set
	 */
	public void setM2tot(Double m2tot) {
		this.m2tot = m2tot;
	}
	/**
	 * @param descp the descp to set
	 */
	public void setDescp(Double descp) {
		this.descp = descp;
	}
	/**
	 * @param descm the descm to set
	 */
	public void setDescm(Double descm) {
		this.descm = descm;
	}
	/**
	 * @return the id_equnrDesc
	 */
	public String getId_equnrDesc() {
		return id_equnrDesc;
	}
	/**
	 * @param id_equnrDesc the id_equnrDesc to set
	 */
	public void setId_equnrDesc(String id_equnrDesc) {
		this.id_equnrDesc = id_equnrDesc;
	}
	public String getSt_contra() {
		return st_contra;
	}
	public void setSt_contra(String st_contra) {
		this.st_contra = st_contra;
	}

	
		
}