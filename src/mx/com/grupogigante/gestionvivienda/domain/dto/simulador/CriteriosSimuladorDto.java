/**
 *  Clase creada por Osvaldo Rodriguez Martinez (SACTI consultores) con fecha de 24/09/2012
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.simulador;

import java.util.ArrayList;

/**
 * @author osvaldo
 *
 */
public class CriteriosSimuladorDto {
	String accion;
	String i_id_ut_sup;
	String i_usuario;
	String i_equnr;
	String i_datab;
	String i_type;
	String i_cancl;
	String i_trasp;
	String i_cotiz_orig;
	String i_equnr_orig;
	
	
	//criterios cotizador
	String id_ut_sup;
	String id_car_cli;
	String id_cotiz_z;
	String vbeln_cot;
	String vbeln_ped;
	String id_equnr;
	String tive;
	String tivex;
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
	String descp_r;
	String descm_r;
	String numl;
	int dias_pago;
	String si_no_pasa;
	int spaso;
	String spasox;
	int npaso;
	String npasox;
	String detalle;
	String plan;
	String equipos;
	String msgbapi;
	String i_eqps_adic;
	String i_simula;
	String i_guarda;
	String i_log;
	
	String i_pedido;
	String i_cotizacion;
	String i_get_info_z;
	String i_get_pend;
	
	String nom_vend;
	String t_cotizacion;
	String t_equipo;
	String t_cliete;
	String t_pedido;
	String preciocd;
	String equiposXml;
	String equipo_select;
	String nombreC;
	String subequipos;
	String total;
	String from;
	String cotizacionZ;	
	ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposList;	
	
	public String getNom_vend() {
		return nom_vend;
	}
	public void setNom_vend(String nom_vend) {
		this.nom_vend = nom_vend;
	}
	public String getCotizacionZ() {
		return cotizacionZ;
	}
	public void setCotizacionZ(String cotizacionZ) {
		this.cotizacionZ = cotizacionZ;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getEquipo_select() {
		return equipo_select;
	}
	public void setEquipo_select(String equipo_select) {
		this.equipo_select = equipo_select;
	}
	public ArrayList<CotizadorSubequiposDto> getCotizacionSubEquiposList() {
		return cotizacionSubEquiposList;
	}
	public String getSubequipos() {
		return subequipos;
	}
	public void setSubequipos(String subequipos) {
		this.subequipos = subequipos;
	}
	public void setCotizacionSubEquiposList(
			ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposList) {
		this.cotizacionSubEquiposList = cotizacionSubEquiposList;
	}
	public String getI_cancl() {
		return i_cancl;
	}
	public void setI_cancl(String i_cancl) {
		this.i_cancl = i_cancl;
	}
	public String getI_trasp() {
		return i_trasp;
	}
	public void setI_trasp(String i_trasp) {
		this.i_trasp = i_trasp;
	}
	public String getI_cotiz_orig() {
		return i_cotiz_orig;
	}
	public void setI_cotiz_orig(String i_cotiz_orig) {
		this.i_cotiz_orig = i_cotiz_orig;
	}
	public String getI_equnr_orig() {
		return i_equnr_orig;
	}
	public void setI_equnr_orig(String i_equnr_orig) {
		this.i_equnr_orig = i_equnr_orig;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getNombreC() {
		return nombreC;
	}
	public void setNombreC(String nombreC) {
		this.nombreC = nombreC;
	}
	public String getEquiposXml() {
		return equiposXml;
	}
	public void setEquiposXml(String equiposXml) {
		this.equiposXml = equiposXml;
	}
	public String getPreciocd() {
		return preciocd;
	}
	public void setPreciocd(String preciocd) {
		this.preciocd = preciocd;
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
	public String getT_cotizacion() {
		return t_cotizacion;
	}
	public void setT_cotizacion(String t_cotizacion) {
		this.t_cotizacion = t_cotizacion;
	}
	public String getT_equipo() {
		return t_equipo;
	}
	public void setT_equipo(String t_equipo) {
		this.t_equipo = t_equipo;
	}
	public String getT_cliete() {
		return t_cliete;
	}
	public void setT_cliete(String t_cliete) {
		this.t_cliete = t_cliete;
	}
	public String getT_pedido() {
		return t_pedido;
	}
	public void setT_pedido(String t_pedido) {
		this.t_pedido = t_pedido;
	}
	public String getI_pedido() {
		return i_pedido;
	}
	public void setI_pedido(String i_pedido) {
		this.i_pedido = i_pedido;
	}
	public String getI_cotizacion() {
		return i_cotizacion;
	}
	public void setI_cotizacion(String i_cotizacion) {
		this.i_cotizacion = i_cotizacion;
	}
	public String getI_get_info_z() {
		return i_get_info_z;
	}
	public void setI_get_info_z(String i_get_info_z) {
		this.i_get_info_z = i_get_info_z;
	}
	public String getI_get_pend() {
		return i_get_pend;
	}
	public void setI_get_pend(String i_get_pend) {
		this.i_get_pend = i_get_pend;
	}	
	public String getI_eqps_adic() {
		return i_eqps_adic;
	}
	public void setI_eqps_adic(String i_eqps_adic) {
		this.i_eqps_adic = i_eqps_adic;
	}
	public String getI_simula() {
		return i_simula;
	}
	public void setI_simula(String i_simula) {
		this.i_simula = i_simula;
	}
	public String getI_guarda() {
		return i_guarda;
	}
	public void setI_guarda(String i_guarda) {
		this.i_guarda = i_guarda;
	}
	public String getI_log() {
		return i_log;
	}
	public void setI_log(String i_log) {
		this.i_log = i_log;
	}
	// fin criterios cotizador
	public String getMsgbapi() {
		return msgbapi;
	}
	public void setMsgbapi(String msgbapi) {
		this.msgbapi = msgbapi;
	}
	public int getSpaso() {
		return spaso;
	}
	public void setSpaso(int spaso) {
		this.spaso = spaso;
	}
	public String getSpasox() {
		return spasox;
	}
	public void setSpasox(String spasox) {
		this.spasox = spasox;
	}
	public int getNpaso() {
		return npaso;
	}
	public void setNpaso(int npaso) {
		this.npaso = npaso;
	}
	public String getNpasox() {
		return npasox;
	}
	public void setNpasox(String npasox) {
		this.npasox = npasox;
	}
	public int getDias_pago() {
		return dias_pago;
	}
	public void setDias_pago(int dias_pago) {
		this.dias_pago = dias_pago;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public String getEquipos() {
		return equipos;
	}
	public void setEquipos(String equipos) {
		this.equipos = equipos;
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
	public String getTivex() {
		return tivex;
	}
	public void setTivex(String tivex) {
		this.tivex = tivex;
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
	public String getNuml() {
		return numl;
	}
	public void setNuml(String numl) {
		this.numl = numl;
	}
	public String getSi_no_pasa() {
		return si_no_pasa;
	}
	public void setSi_no_pasa(String si_no_pasa) {
		this.si_no_pasa = si_no_pasa;
	}
	public String getI_type() {
		return i_type;
	}
	public void setI_type(String i_type) {
		this.i_type = i_type;
	}
	public String getI_datab() {
		return i_datab;
	}
	public void setI_datab(String i_datab) {
		this.i_datab = i_datab;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getI_id_ut_sup() {
		return i_id_ut_sup;
	}
	public void setI_id_ut_sup(String i_id_ut_sup) {
		this.i_id_ut_sup = i_id_ut_sup;
	}
	public String getI_usuario() {
		return i_usuario;
	}
	public void setI_usuario(String i_usuario) {
		this.i_usuario = i_usuario;
	}
	public String getI_equnr() {
		return i_equnr;
	}
	public void setI_equnr(String i_equnr) {
		this.i_equnr = i_equnr;
	}	
}
