/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 02/10/2012              
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;

/**
 * @author WSNADM
 *
 */
public class ResponseGetConPagosDto {

	private ArrayList<DetalleExtractoDto> extractoBankInfo= new ArrayList<DetalleExtractoDto>();
	private Object objExtractoBankInfo = new Object();
	private ArrayList<PagoDetDto> extractoPagosInfo = new ArrayList<PagoDetDto>();
	private Object objExtractoPagosInfo = new Object();
	private ArrayList<PoolErrorDto> poolErrorInfo = new ArrayList<PoolErrorDto>();
	private Object objPoolErrorInfo = new Object();
	private String num_reg;
	private String rango_pago;
	private String mensaje;
	private String descripcion;
	/**
	 * @return the extractoBankInfo
	 */
	public ArrayList<DetalleExtractoDto> getExtractoBankInfo() {
		return extractoBankInfo;
	}
	/**
	 * @param extractoBankInfo the extractoBankInfo to set
	 */
	public void setExtractoBankInfo(ArrayList<DetalleExtractoDto> extractoBankInfo) {
		this.extractoBankInfo = extractoBankInfo;
	}
	/**
	 * @return the objExtractoBankInfo
	 */
	public Object getObjExtractoBankInfo() {
		return objExtractoBankInfo;
	}
	/**
	 * @param objExtractoBankInfo the objExtractoBankInfo to set
	 */
	public void setObjExtractoBankInfo(Object objExtractoBankInfo) {
		this.objExtractoBankInfo = objExtractoBankInfo;
	}
	/**
	 * @return the extractoPagosInfo
	 */
	public ArrayList<PagoDetDto> getExtractoPagosInfo() {
		return extractoPagosInfo;
	}
	/**
	 * @param extractoPagosInfo the extractoPagosInfo to set
	 */
	public void setExtractoPagosInfo(ArrayList<PagoDetDto> extractoPagosInfo) {
		this.extractoPagosInfo = extractoPagosInfo;
	}
	/**
	 * @return the objExtractoPagosInfo
	 */
	public Object getObjExtractoPagosInfo() {
		return objExtractoPagosInfo;
	}
	/**
	 * @param objExtractoPagosInfo the objExtractoPagosInfo to set
	 */
	public void setObjExtractoPagosInfo(Object objExtractoPagosInfo) {
		this.objExtractoPagosInfo = objExtractoPagosInfo;
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
	 * @return the num_reg
	 */
	public String getNum_reg() {
		return num_reg;
	}
	/**
	 * @param num_reg the num_reg to set
	 */
	public void setNum_reg(String num_reg) {
		this.num_reg = num_reg;
	}
	/**
	 * @return the rango_pago
	 */
	public String getRango_pago() {
		return rango_pago;
	}
	/**
	 * @param rango_pago the rango_pago to set
	 */
	public void setRango_pago(String rango_pago) {
		this.rango_pago = rango_pago;
	}
	/**
	 * @return the poolErrorInfo
	 */
	public ArrayList<PoolErrorDto> getPoolErrorInfo() {
		return poolErrorInfo;
	}
	/**
	 * @param poolErrorInfo the poolErrorInfo to set
	 */
	public void setPoolErrorInfo(ArrayList<PoolErrorDto> poolErrorInfo) {
		this.poolErrorInfo = poolErrorInfo;
	}
	/**
	 * @return the objPoolErrorInfo
	 */
	public Object getObjPoolErrorInfo() {
		return objPoolErrorInfo;
	}
	/**
	 * @param objPoolErrorInfo the objPoolErrorInfo to set
	 */
	public void setObjPoolErrorInfo(Object objPoolErrorInfo) {
		this.objPoolErrorInfo = objPoolErrorInfo;
	}
}
