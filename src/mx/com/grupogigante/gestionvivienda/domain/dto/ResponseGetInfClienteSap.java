/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 01/08/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;
import java.util.List;
/**
 * Clase que encapsula los fields para la respuesta (listas de consulta )
 * Fecha de creación: 01/08/2012                
 */
public class ResponseGetInfClienteSap {

	private ArrayList<ClientesDto> clienteInfo = new ArrayList<ClientesDto>();
	private Object objClienteInfo = new Object();
	private ArrayList<ContactoByClienteDto> clientesSapList = new ArrayList<ContactoByClienteDto>();
	private Object objClientesSapList = new Object();
	private String mensaje;
	private String descripcion;
	/**
	 * @return the clienteInfo
	 */
	public ArrayList<ClientesDto> getClienteInfo() {
		return clienteInfo;
	}
	/**
	 * @param clienteInfo the clienteInfo to set
	 */
	public void setClienteInfo(ArrayList<ClientesDto> clienteInfo) {
		this.clienteInfo = clienteInfo;
	}
	/**
	 * @return the objClienteInfo
	 */
	public Object getObjClienteInfo() {
		return objClienteInfo;
	}
	/**
	 * @param objClienteInfo the objClienteInfo to set
	 */
	public void setObjClienteInfo(Object objClienteInfo) {
		this.objClienteInfo = objClienteInfo;
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
	 * @return the clientesSapList
	 */
	public ArrayList<ContactoByClienteDto> getClientesSapList() {
		return clientesSapList;
	}
	/**
	 * @param clientesSapList the clientesSapList to set
	 */
	public void setClientesSapList(ArrayList<ContactoByClienteDto> clientesSapList) {
		this.clientesSapList = clientesSapList;
	}
	/**
	 * @return the objClientesSapList
	 */
	public Object getObjClientesSapList() {
		return objClientesSapList;
	}
	/**
	 * @param objClientesSapList the objClientesSapList to set
	 */
	public void setObjClientesSapList(Object objClientesSapList) {
		this.objClientesSapList = objClientesSapList;
	}
}
