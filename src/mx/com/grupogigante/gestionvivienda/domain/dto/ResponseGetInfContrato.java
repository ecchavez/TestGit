/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 16/08/2012              
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WSNADM
 * Clase con la repuesta de Contrato 
 */
public class ResponseGetInfContrato {
	private ClienteSapDto cliente = new ClienteSapDto();
	private EquipoContratoDto equipo=new EquipoContratoDto();
	private CtaBancariaDto cuenta=new CtaBancariaDto();
	private ArrayList<CtaBancariaDto> cuentasList=new ArrayList<CtaBancariaDto>();
	
	/**
	 * @return the cuentasList
	 */
	public ArrayList<CtaBancariaDto> getCuentasList() {
		return cuentasList;
	}
	/**
	 * @param cuentasList the cuentasList to set
	 */
	public void setCuentasList(ArrayList<CtaBancariaDto> cuentasList) {
		this.cuentasList = cuentasList;
	}
	private CotizacionDetDto cotizacion=new CotizacionDetDto();
	private ArrayList<CotizacionDetDto> cotizacionDetList=new ArrayList<CotizacionDetDto>();

	private String mensaje;
	private String descripcion;
	
	/**
	 * @return the cliente
	 */
	public ClienteSapDto getCliente() {
		return cliente;
	}
	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(ClienteSapDto cliente) {
		this.cliente = cliente;
	}
	/**
	 * @return the equipo
	 */
	public EquipoContratoDto getEquipo() {
		return equipo;
	}
	/**
	 * @param equipo the equipo to set
	 */
	public void setEquipo(EquipoContratoDto equipo) {
		this.equipo = equipo;
	}
	/**
	 * @return the cuenta
	 */
	public CtaBancariaDto getCuenta() {
		return cuenta;
	}
	/**
	 * @param cuenta the cuenta to set
	 */
	public void setCuenta(CtaBancariaDto cuenta) {
		this.cuenta = cuenta;
	}
	/**
	 * @return the cotizacion
	 */
	public CotizacionDetDto getCotizacion() {
		return cotizacion;
	}
	/**
	 * @param cotizacion the cotizacion to set
	 */
	public void setCotizacion(CotizacionDetDto cotizacion) {
		this.cotizacion = cotizacion;
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
	 * @return the cotizacionDetList
	 */
	public ArrayList<CotizacionDetDto> getCotizacionDetList() {
		return cotizacionDetList;
	}
	/**
	 * @param cotizacionDetList the cotizacionDetList to set
	 */
	public void setCotizacionDetList(ArrayList<CotizacionDetDto> cotizacionDetList) {
		this.cotizacionDetList = cotizacionDetList;
	}
}
