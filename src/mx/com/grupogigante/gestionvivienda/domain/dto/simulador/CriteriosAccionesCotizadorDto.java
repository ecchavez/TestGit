/**
 *  Clase creada por Osvaldo Rodriguez Martinez (SACTI consultores) con fecha de 03/10/2012
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.simulador;

import java.util.ArrayList;

/**
 * @author osvaldo
 *
 */
public class CriteriosAccionesCotizadorDto {
	String i_usuario;
	String i_id_ut_sup;
	String i_eqps_adic;
	String i_simula;
	String i_guarda;
	String i_log;
	
	ArrayList<CotizadorHeaderDto> cotizacionHeaderList = new ArrayList<CotizadorHeaderDto>();
	ArrayList<CotizadorDetalleDto> cotizacionDetalleList = new ArrayList<CotizadorDetalleDto>();
	ArrayList<CotizadorBillPlaningDto> cotizacionBillPlanList = new ArrayList<CotizadorBillPlaningDto>();
	ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposList = new ArrayList<CotizadorSubequiposDto>();
	
	public String getI_usuario() {
		return i_usuario;
	}
	public void setI_usuario(String i_usuario) {
		this.i_usuario = i_usuario;
	}
	public String getI_id_ut_sup() {
		return i_id_ut_sup;
	}
	public void setI_id_ut_sup(String i_id_ut_sup) {
		this.i_id_ut_sup = i_id_ut_sup;
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
	public ArrayList<CotizadorHeaderDto> getCotizacionHeaderList() {
		return cotizacionHeaderList;
	}
	public void setCotizacionHeaderList(
			ArrayList<CotizadorHeaderDto> cotizacionHeaderList) {
		this.cotizacionHeaderList = cotizacionHeaderList;
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
	public ArrayList<CotizadorSubequiposDto> getCotizacionSubEquiposList() {
		return cotizacionSubEquiposList;
	}
	public void setCotizacionSubEquiposList(
			ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposList) {
		this.cotizacionSubEquiposList = cotizacionSubEquiposList;
	}
}
