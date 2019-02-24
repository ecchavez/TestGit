/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;
import java.util.List;
/**
 * Clase que encapsula los fields para la respuesta (listas de consulta )
 * Fecha de creación: XX/06/2012               
 */
public class ResponseGetInfCarCliente {

	
	private List<ClientesDto> clientesList = new ArrayList<ClientesDto>();
	private Object objClientesList = new Object();
	private List<VisitsDto> visitsList = new ArrayList<VisitsDto>();
	private Object objVisitsList = new Object();
	private List<CotizacionHdrDto> cotizacionesHdrList = new ArrayList<CotizacionHdrDto>();
	private Object objCotizacionesHdrList = new Object();
	private List<CotizacionDetDto> cotizacionesDetList = new ArrayList<CotizacionDetDto>();
	private Object objCotizacionesDetList = new Object();
	private List<ViaContactoDto> viaContactoList = new ArrayList<ViaContactoDto>();
	private Object objViaContactoList = new Object();
	private List<VisitasClienteDto> visitasClienteList = new ArrayList<VisitasClienteDto>();
	private Object objVisitasClienteList = new Object();
	private String mensaje;
	private String descripcion;
	
	/**
	 * @return the clientesList
	 */
	public List<ClientesDto> getClientesList() {
		return clientesList;
	}
	/**
	 * @param clientesList the clientesList to set
	 */
	public void setClientesList(List<ClientesDto> clientesList) {
		this.clientesList = clientesList;
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
	 * @return the visitsList
	 */
	public List<VisitsDto> getVisitsList() {
		return visitsList;
	}
	/**
	 * @param visitsList the visitsList to set
	 */
	public void setVisitsList(List<VisitsDto> visitsList) {
		this.visitsList = visitsList;
	}
	/**
	 * @return the objVisitsList
	 */
	public Object getObjVisitsList() {
		return objVisitsList;
	}
	/**
	 * @param objVisitsList the objVisitsList to set
	 */
	public void setObjVisitsList(Object objVisitsList) {
		this.objVisitsList = objVisitsList;
	}
	/**
	 * @return the cotizacionesHdrList
	 */
	public List<CotizacionHdrDto> getCotizacionesHdrList() {
		return cotizacionesHdrList;
	}
	/**
	 * @param cotizacionesHdrList the cotizacionesHdrList to set
	 */
	public void setCotizacionesHdrList(List<CotizacionHdrDto> cotizacionesHdrList) {
		this.cotizacionesHdrList = cotizacionesHdrList;
	}
	/**
	 * @return the objCotizacionesHdrList
	 */
	public Object getObjCotizacionesHdrList() {
		return objCotizacionesHdrList;
	}
	/**
	 * @param objCotizacionesHdrList the objCotizacionesHdrList to set
	 */
	public void setObjCotizacionesHdrList(Object objCotizacionesHdrList) {
		this.objCotizacionesHdrList = objCotizacionesHdrList;
	}
	/**
	 * @return the cotizacionesDetList
	 */
	public List<CotizacionDetDto> getCotizacionesDetList() {
		return cotizacionesDetList;
	}
	/**
	 * @param cotizacionesDetList the cotizacionesDetList to set
	 */
	public void setCotizacionesDetList(List<CotizacionDetDto> cotizacionesDetList) {
		this.cotizacionesDetList = cotizacionesDetList;
	}
	/**
	 * @return the objCotizacionesDetList
	 */
	public Object getObjCotizacionesDetList() {
		return objCotizacionesDetList;
	}
	/**
	 * @param objCotizacionesDetList the objCotizacionesDetList to set
	 */
	public void setObjCotizacionesDetList(Object objCotizacionesDetList) {
		this.objCotizacionesDetList = objCotizacionesDetList;
	}
	/**
	 * @return the viaContactoList
	 */
	public List<ViaContactoDto> getViaContactoList() {
		return viaContactoList;
	}
	/**
	 * @param viaContactoList the viaContactoList to set
	 */
	public void setViaContactoList(List<ViaContactoDto> viaContactoList) {
		this.viaContactoList = viaContactoList;
	}
	/**
	 * @return the objViaContactoList
	 */
	public Object getObjViaContactoList() {
		return objViaContactoList;
	}
	/**
	 * @param objViaContactoList the objViaContactoList to set
	 */
	public void setObjViaContactoList(Object objViaContactoList) {
		this.objViaContactoList = objViaContactoList;
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
	 * @return the visitasClienteList
	 */
	public List<VisitasClienteDto> getVisitasClienteList() {
		return visitasClienteList;
	}
	/**
	 * @param visitasClienteList the visitasClienteList to set
	 */
	public void setVisitasClienteList(List<VisitasClienteDto> visitasClienteList) {
		this.visitasClienteList = visitasClienteList;
	}
	/**
	 * @return the objVisitasClienteList
	 */
	public Object getObjVisitasClienteList() {
		return objVisitasClienteList;
	}
	/**
	 * @param objVisitasClienteList the objVisitasClienteList to set
	 */
	public void setObjVisitasClienteList(Object objVisitasClienteList) {
		this.objVisitasClienteList = objVisitasClienteList;
	}
}
