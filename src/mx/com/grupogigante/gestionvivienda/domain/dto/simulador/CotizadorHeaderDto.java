/**
 *  Clase creada por Osvaldo Rodriguez Martinez (SACTI consultores) con fecha de 24/09/2012
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.simulador;

import java.util.ArrayList;

/**
 * @author osvaldo
 *
 */
public class CotizadorHeaderDto {
	String id_ut_sup;
	String id_car_cli;
	String id_cotiz_z;
	String vbeln_cot;
	String vbeln_ped;
	String id_equnr;
	String tive;
	String auart;
	String aedat;
	String cptum;
	String datab;
	String datub;
	String id_cli_sap;
	int nbod;
	int nest;
	float m2tot;
	String fech_entreg;
	String descp;
	String descm;
	int numl;
	String si_no_pasa;
	String descp_r;
	String descm_r;
	
	ArrayList<CotizadorDetalleDto> cotizacionDetalleList = new ArrayList<CotizadorDetalleDto>();
	ArrayList<CotizadorBillPlaningDto> cotizacionBillPlanList = new ArrayList<CotizadorBillPlaningDto>();
	ArrayList<CotizadorSubequiposDto> cotizacionSubequipos = new ArrayList<CotizadorSubequiposDto>();
	
	public ArrayList<CotizadorSubequiposDto> getCotizacionSubequipos() {
		return cotizacionSubequipos;
	}
	public void setCotizacionSubequipos(
			ArrayList<CotizadorSubequiposDto> cotizacionSubequipos) {
		this.cotizacionSubequipos = cotizacionSubequipos;
	}
	public ArrayList<CotizadorDetalleDto> getCotizacionDetalleList() {
		return cotizacionDetalleList;
	}
	public void setCotizacionDetalleList(
			ArrayList<CotizadorDetalleDto> cotizacionDetalleList) {
		this.cotizacionDetalleList = cotizacionDetalleList;
	}
	public ArrayList<CotizadorBillPlaningDto> getCotizacionBillPlanList() {
		return cotizacionBillPlanList;
	}
	public void setCotizacionBillPlanList(
			ArrayList<CotizadorBillPlaningDto> cotizacionBillPlanList) {
		this.cotizacionBillPlanList = cotizacionBillPlanList;
	}
	public String getDescp_r() {
		return descp_r;
	}
	public void setDescp_r(String descp_r) {
		this.descp_r = descp_r;
	}
	public String getDescm_r() {
		return descm_r;
	}
	public void setDescm_r(String descm_r) {
		this.descm_r = descm_r;
	}
	public String getId_ut_sup() {
		return id_ut_sup;
	}
	public void setId_ut_sup(String id_ut_sup) {
		this.id_ut_sup = id_ut_sup;
	}
	public String getId_car_cli() {
		return id_car_cli;
	}
	public void setId_car_cli(String id_car_cli) {
		this.id_car_cli = id_car_cli;
	}
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
	public String getId_equnr() {
		return id_equnr;
	}
	public void setId_equnr(String id_equnr) {
		this.id_equnr = id_equnr;
	}
	public String getTive() {
		return tive;
	}
	public void setTive(String tive) {
		this.tive = tive;
	}
	public String getAuart() {
		return auart;
	}
	public void setAuart(String auart) {
		this.auart = auart;
	}
	public String getAedat() {
		return aedat;
	}
	public void setAedat(String aedat) {
		this.aedat = aedat;
	}
	public String getCptum() {
		return cptum;
	}
	public void setCptum(String cptum) {
		this.cptum = cptum;
	}
	public String getDatab() {
		return datab;
	}
	public void setDatab(String datab) {
		this.datab = datab;
	}
	public String getDatub() {
		return datub;
	}
	public void setDatub(String datub) {
		this.datub = datub;
	}
	public String getId_cli_sap() {
		return id_cli_sap;
	}
	public void setId_cli_sap(String id_cli_sap) {
		this.id_cli_sap = id_cli_sap;
	}
	public int getNbod() {
		return nbod;
	}
	public void setNbod(int nbod) {
		this.nbod = nbod;
	}
	public int getNest() {
		return nest;
	}
	public void setNest(int nest) {
		this.nest = nest;
	}
	public float getM2tot() {
		return m2tot;
	}
	public void setM2tot(float m2tot) {
		this.m2tot = m2tot;
	}
	public String getFech_entreg() {
		return fech_entreg;
	}
	public void setFech_entreg(String fech_entreg) {
		this.fech_entreg = fech_entreg;
	}
	public String getDescp() {
		return descp;
	}
	public void setDescp(String descp) {
		this.descp = descp;
	}
	public String getDescm() {
		return descm;
	}
	public void setDescm(String descm) {
		this.descm = descm;
	}
	public int getNuml() {
		return numl;
	}
	public void setNuml(int numl) {
		this.numl = numl;
	}
	public String getSi_no_pasa() {
		return si_no_pasa;
	}
	public void setSi_no_pasa(String si_no_pasa) {
		this.si_no_pasa = si_no_pasa;
	}
	
}
