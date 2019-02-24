/**
 *  Clase creada por Osvaldo Rodriguez Martinez (SACTI consultores) con fecha de 24/09/2012
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.simulador;

import java.util.ArrayList;

import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.PedidosDetalleDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.PedidosDto;

/**
 * @author osvaldo
 *
 */
public class ResponseObtieneCotizacionBaseDto {
	String mensaje;	
	String descripcion;
	String e_id_cotiz_z;
	String e_id_cotiz_sap;
	String e_vpnm;
	String e_vpnu;
	ArrayList<CotizadorHeaderDto> cotizacionHeaderList = new ArrayList<CotizadorHeaderDto>();
	ArrayList<CotizadorDetalleDto> cotizacionDetalleList = new ArrayList<CotizadorDetalleDto>();
	ArrayList<CotizadorBillPlaningDto> cotizacionBillPlanList = new ArrayList<CotizadorBillPlaningDto>();
	ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposList = new ArrayList<CotizadorSubequiposDto>();
	ArrayList<PedidosDto> cotizacionPedidosList = new ArrayList<PedidosDto>();
	ArrayList<PedidosDetalleDto> cotizacionPedidosDetalleList = new ArrayList<PedidosDetalleDto>();

	public ArrayList<PedidosDetalleDto> getCotizacionPedidosDetalleList() {
		return cotizacionPedidosDetalleList;
	}
	public void setCotizacionPedidosDetalleList(
			ArrayList<PedidosDetalleDto> cotizacionPedidosDetalleList) {
		this.cotizacionPedidosDetalleList = cotizacionPedidosDetalleList;
	}
	public ArrayList<PedidosDto> getCotizacionPedidosList() {
		return cotizacionPedidosList;
	}
	public void setCotizacionPedidosList(ArrayList<PedidosDto> cotizacionPedidosList) {
		this.cotizacionPedidosList = cotizacionPedidosList;
	}
	public String getE_id_cotiz_z() {
		return e_id_cotiz_z;
	}
	public void setE_id_cotiz_z(String e_id_cotiz_z) {
		this.e_id_cotiz_z = e_id_cotiz_z;
	}
	public String getE_id_cotiz_sap() {
		return e_id_cotiz_sap;
	}
	public void setE_id_cotiz_sap(String e_id_cotiz_sap) {
		this.e_id_cotiz_sap = e_id_cotiz_sap;
	}
	public String getE_vpnm() {
		return e_vpnm;
	}
	public void setE_vpnm(String e_vpnm) {
		this.e_vpnm = e_vpnm;
	}
	public String getE_vpnu() {
		return e_vpnu;
	}
	public void setE_vpnu(String e_vpnu) {
		this.e_vpnu = e_vpnu;
	}
	public ArrayList<CotizadorSubequiposDto> getCotizacionSubEquiposList() {
		return cotizacionSubEquiposList;
	}
	public void setCotizacionSubEquiposList(
			ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposList) {
		this.cotizacionSubEquiposList = cotizacionSubEquiposList;
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
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
