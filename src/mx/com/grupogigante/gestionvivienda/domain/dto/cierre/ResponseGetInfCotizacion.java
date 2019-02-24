/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 12/09/2012              
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.cierre;

import java.util.ArrayList;
import java.util.List;

import mx.com.grupogigante.gestionvivienda.domain.dto.CotizacionDetDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CotizacionHdrDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CotizacionesDet1Dto;
import mx.com.grupogigante.gestionvivienda.domain.dto.EquiposAdicionalesDto;


/**
 * @author WSNADM
 *
 */
public class ResponseGetInfCotizacion {
	private List<CotizacionHdrDto> cotHdrList = new ArrayList<CotizacionHdrDto>();
	private Object objcotHdrList = new Object();
	private List<CotizacionesDet1Dto> cotDet1List = new ArrayList<CotizacionesDet1Dto>();
	private Object objcotDet1List  = new Object();
	private List<CotizacionHdrDto> pedHdrList = new ArrayList<CotizacionHdrDto>();
	private Object objpedHdrList = new Object();
	private List<CotizacionesDet1Dto> pedDet1List = new ArrayList<CotizacionesDet1Dto>();
	private Object objpedDet1List  = new Object();
	private List<CotizacionDetDto> BillPlanList = new ArrayList<CotizacionDetDto>();
	private Object objBillPlanList = new Object();
	private List<EquiposAdicionalesDto> EqAdicList = new ArrayList<EquiposAdicionalesDto>();
	private Object objEqAdicList = new Object();
	private String mensaje;
	private String descripcion;
	
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
	 * @return the cotHdrList
	 */
	public List<CotizacionHdrDto> getCotHdrList() {
		return cotHdrList;
	}
	/**
	 * @param cotHdrList the cotHdrList to set
	 */
	public void setCotHdrList(List<CotizacionHdrDto> cotHdrList) {
		this.cotHdrList = cotHdrList;
	}
	/**
	 * @return the objcotHdrList
	 */
	public Object getObjcotHdrList() {
		return objcotHdrList;
	}
	/**
	 * @param objcotHdrList the objcotHdrList to set
	 */
	public void setObjcotHdrList(Object objcotHdrList) {
		this.objcotHdrList = objcotHdrList;
	}
	/**
	 * @return the cotDet1List
	 */
	public List<CotizacionesDet1Dto> getCotDet1List() {
		return cotDet1List;
	}
	/**
	 * @param cotDet1List the cotDet1List to set
	 */
	public void setCotDet1List(List<CotizacionesDet1Dto> cotDet1List) {
		this.cotDet1List = cotDet1List;
	}
	/**
	 * @return the objcotDet1List
	 */
	public Object getObjcotDet1List() {
		return objcotDet1List;
	}
	/**
	 * @param objcotDet1List the objcotDet1List to set
	 */
	public void setObjcotDet1List(Object objcotDet1List) {
		this.objcotDet1List = objcotDet1List;
	}
	/**
	 * @return the pedHdrList
	 */
	public List<CotizacionHdrDto> getPedHdrList() {
		return pedHdrList;
	}
	/**
	 * @param pedHdrList the pedHdrList to set
	 */
	public void setPedHdrList(List<CotizacionHdrDto> pedHdrList) {
		this.pedHdrList = pedHdrList;
	}
	/**
	 * @return the objpedHdrList
	 */
	public Object getObjpedHdrList() {
		return objpedHdrList;
	}
	/**
	 * @param objpedHdrList the objpedHdrList to set
	 */
	public void setObjpedHdrList(Object objpedHdrList) {
		this.objpedHdrList = objpedHdrList;
	}
	/**
	 * @return the pedDet1List
	 */
	public List<CotizacionesDet1Dto> getPedDet1List() {
		return pedDet1List;
	}
	/**
	 * @param pedDet1List the pedDet1List to set
	 */
	public void setPedDet1List(List<CotizacionesDet1Dto> pedDet1List) {
		this.pedDet1List = pedDet1List;
	}
	/**
	 * @return the objpedDet1List
	 */
	public Object getObjpedDet1List() {
		return objpedDet1List;
	}
	/**
	 * @param objpedDet1List the objpedDet1List to set
	 */
	public void setObjpedDet1List(Object objpedDet1List) {
		this.objpedDet1List = objpedDet1List;
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
	
}
