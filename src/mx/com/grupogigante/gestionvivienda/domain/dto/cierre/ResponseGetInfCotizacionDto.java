/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 12/09/2012              
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.cierre;

import java.util.ArrayList;
import java.util.List;

import mx.com.grupogigante.gestionvivienda.domain.dto.ClienteSapDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CotizacionDetDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CotizacionHdrDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CotizacionesDet1Dto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CotizacionesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.EquiposAdicionalesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CotizadorSubequiposDto;


/**
 * @author WSNADM
 *
 */
public class ResponseGetInfCotizacionDto {
	private List<CotizacionHdrDto> CotPedHdrList = new ArrayList<CotizacionHdrDto>();
	private Object objCotPedHdrList = new Object();
	private List<CotizacionesDet1Dto> CotPedDet1List = new ArrayList<CotizacionesDet1Dto>();
	private Object objCotPedDet1List  = new Object();
	private List<CotizacionDetDto> BillPlanList = new ArrayList<CotizacionDetDto>();
	private Object objBillPlanList = new Object();
	private List<EquiposAdicionalesDto> EqAdicList = new ArrayList<EquiposAdicionalesDto>();
	private Object objEqAdicList = new Object();
	private List<CotizacionesDto> cotInfoList = new ArrayList<CotizacionesDto>();
	private Object objCotInfoList = new Object();
	private List<ClienteSapDto> clientesList = new ArrayList<ClienteSapDto>();
	private Object objClientesList = new Object();
	private String mensaje;
	private String descripcion;
	private ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposList = new ArrayList<CotizadorSubequiposDto>();
	private Object objCotizacionSubEquiposList = new Object();
	/**
	 * @return the cotPedHdrList
	 */
	public List<CotizacionHdrDto> getCotPedHdrList() {
		return CotPedHdrList;
	}
	/**
	 * @param cotPedHdrList the cotPedHdrList to set
	 */
	public void setCotPedHdrList(List<CotizacionHdrDto> cotPedHdrList) {
		CotPedHdrList = cotPedHdrList;
	}
	/**
	 * @return the objCotPedHdrList
	 */
	public Object getObjCotPedHdrList() {
		return objCotPedHdrList;
	}
	/**
	 * @param objCotPedHdrList the objCotPedHdrList to set
	 */
	public void setObjCotPedHdrList(Object objCotPedHdrList) {
		this.objCotPedHdrList = objCotPedHdrList;
	}
	/**
	 * @return the cotPedDet1List
	 */
	public List<CotizacionesDet1Dto> getCotPedDet1List() {
		return CotPedDet1List;
	}
	/**
	 * @param cotPedDet1List the cotPedDet1List to set
	 */
	public void setCotPedDet1List(List<CotizacionesDet1Dto> cotPedDet1List) {
		CotPedDet1List = cotPedDet1List;
	}
	/**
	 * @return the objCotPedDet1List
	 */
	public Object getObjCotPedDet1List() {
		return objCotPedDet1List;
	}
	/**
	 * @param objCotPedDet1List the objCotPedDet1List to set
	 */
	public void setObjCotPedDet1List(Object objCotPedDet1List) {
		this.objCotPedDet1List = objCotPedDet1List;
	}
	/**
	 * @return the billPlanList
	 */
	public List<CotizacionDetDto> getBillPlanList() {
		return BillPlanList;
	}
	/**
	 * @param billPlanList the billPlanList to set
	 */
	public void setBillPlanList(List<CotizacionDetDto> billPlanList) {
		BillPlanList = billPlanList;
	}
	/**
	 * @return the objBillPlanList
	 */
	public Object getObjBillPlanList() {
		return objBillPlanList;
	}
	/**
	 * @param objBillPlanList the objBillPlanList to set
	 */
	public void setObjBillPlanList(Object objBillPlanList) {
		this.objBillPlanList = objBillPlanList;
	}
	/**
	 * @return the eqAdicList
	 */
	public List<EquiposAdicionalesDto> getEqAdicList() {
		return EqAdicList;
	}
	/**
	 * @param eqAdicList the eqAdicList to set
	 */
	public void setEqAdicList(List<EquiposAdicionalesDto> eqAdicList) {
		EqAdicList = eqAdicList;
	}
	/**
	 * @return the objEqAdicList
	 */
	public Object getObjEqAdicList() {
		return objEqAdicList;
	}
	/**
	 * @param objEqAdicList the objEqAdicList to set
	 */
	public void setObjEqAdicList(Object objEqAdicList) {
		this.objEqAdicList = objEqAdicList;
	}
	/**
	 * @return the cotInfoList
	 */
	public List<CotizacionesDto> getCotInfoList() {
		return cotInfoList;
	}
	/**
	 * @param cotInfoList the cotInfoList to set
	 */
	public void setCotInfoList(List<CotizacionesDto> cotInfoList) {
		this.cotInfoList = cotInfoList;
	}
	/**
	 * @return the objCotInfoList
	 */
	public Object getObjCotInfoList() {
		return objCotInfoList;
	}
	/**
	 * @param objCotInfoList the objCotInfoList to set
	 */
	public void setObjCotInfoList(Object objCotInfoList) {
		this.objCotInfoList = objCotInfoList;
	}
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the objClientesList
	 */
	public Object getObjClientesList() {
		return objClientesList;
	}
	/**
	 * @param objClientesList the objClientesList to set
	 */
	public void setObjClientesList(Object objClientesList) {
		this.objClientesList = objClientesList;
	}
	/**
	 * @return the clientesList
	 */
	public List<ClienteSapDto> getClientesList() {
		return clientesList;
	}
	/**
	 * @param clientesList the clientesList to set
	 */
	public void setClientesList(List<ClienteSapDto> clientesList) {
		this.clientesList = clientesList;
	}
	public ArrayList<CotizadorSubequiposDto> getCotizacionSubEquiposList() {
		return cotizacionSubEquiposList;
	}
	public void setCotizacionSubEquiposList(
			ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposList) {
		this.cotizacionSubEquiposList = cotizacionSubEquiposList;
	}
	public Object getObjCotizacionSubEquiposList() {
		return objCotizacionSubEquiposList;
	}
	public void setObjCotizacionSubEquiposList(Object objCotizacionSubEquiposList) {
		this.objCotizacionSubEquiposList = objCotizacionSubEquiposList;
	}
	
	}
