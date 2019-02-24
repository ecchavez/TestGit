/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 27/09/2012              
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.cierre;

import java.util.ArrayList;
import java.util.List;


import mx.com.grupogigante.gestionvivienda.domain.dto.ContactoByClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.DireccionSocDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.PagoDetDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.PagoHdrDto;


/**
 * @author WSNADM
 *
 */
public class ResponseGetInfoPagRegDto {
	private List<PagoDetDto> PagoDetList = new ArrayList<PagoDetDto>();
	private List<PagoHdrDto> PagoHdrList = new ArrayList<PagoHdrDto>();
	private Object objPagoHdrList  = new Object();
	private List<DireccionSocDto> DirecSocList = new ArrayList<DireccionSocDto>();
	private Object objDirecSocList = new Object();
	
	private String mensaje;
	private String descripcion;
	/**
	 * @return the pagoDetList
	 */
	public List<PagoDetDto> getPagoDetList() {
		return PagoDetList;
	}
	/**
	 * @param pagoDetList the pagoDetList to set
	 */
	public void setPagoDetList(List<PagoDetDto> pagoDetList) {
		PagoDetList = pagoDetList;
	}
	
	
	/**
	 * @return the pagoHdrList
	 */
	public List<PagoHdrDto> getPagoHdrList() {
		return PagoHdrList;
	}
	/**
	 * @param pagoHdrList the pagoHdrList to set
	 */
	public void setPagoHdrList(List<PagoHdrDto> pagoHdrList) {
		PagoHdrList = pagoHdrList;
	}
	/**
	 * @return the objPagoHdrList
	 */
	public Object getObjPagoHdrList() {
		return objPagoHdrList;
	}
	/**
	 * @param objPagoHdrList the objPagoHdrList to set
	 */
	public void setObjPagoHdrList(Object objPagoHdrList) {
		this.objPagoHdrList = objPagoHdrList;
	}
	/**
	 * @return the direcSocList
	 */
	public List<DireccionSocDto> getDirecSocList() {
		return DirecSocList;
	}
	/**
	 * @param direcSocList the direcSocList to set
	 */
	public void setDirecSocList(List<DireccionSocDto> direcSocList) {
		DirecSocList = direcSocList;
	}
	/**
	 * @return the objDirecSocList
	 */
	public Object getObjDirecSocList() {
		return objDirecSocList;
	}
	/**
	 * @param objDirecSocList the objDirecSocList to set
	 */
	public void setObjDirecSocList(Object objDirecSocList) {
		this.objDirecSocList = objDirecSocList;
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
}
