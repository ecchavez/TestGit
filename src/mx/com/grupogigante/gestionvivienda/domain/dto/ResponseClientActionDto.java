/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.ResponseGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseActualizaStatusImpresionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseCreacionPedidoDto;

/**
 * Clase que encapsula los tipos de respuesta de  Cliente 
 * Fecha de creación: XX/06/2012               
 */
public class ResponseClientActionDto {
	//Respuestas para Carteras de Clientes 
	ResponseGetInfCarCliente respGetInfCarCliente;
	ResponseGetUsuariosDto respGetUsuarios;
	ResponseValidaLoginDto respValidaLogin;
	
	//Respuesta para Clientes Sap
	ResponseGetInfClienteSap respGetInfSapCliente;
	ResponseGetCatalogosDto respGetCatalogos;
	
	//Respuestas en comun para clientes 
	ResponseAddClienteDto respAddClienteDto;
	ResponseDelClienteDto respDelClienteDto;
	ResponseUpClienteDto respUpClienteDto;
	ResponseCreacionPedidoDto resPedido;
	
	ResponseActualizaStatusImpresionDto resActualizaImpresion;
	
	
	public ResponseCreacionPedidoDto getResPedido() {
		return resPedido;
	}
	public void setResPedido(ResponseCreacionPedidoDto resPedido) {
		this.resPedido = resPedido;
	}
	/**
	 * @return the respAddClienteDto
	 */
	public ResponseAddClienteDto getRespAddClienteDto() {
		return respAddClienteDto;
	}
	/**
	 * @param respAddClienteDto the respAddClienteDto to set
	 */
	public void setRespAddClienteDto(ResponseAddClienteDto respAddClienteDto) {
		this.respAddClienteDto = respAddClienteDto;
	}
	/**
	 * @return the respGetInfCarCliente
	 */
	public ResponseGetInfCarCliente getRespGetInfCarCliente() {
		return respGetInfCarCliente;
	}
	/**
	 * @param respGetInfCarCliente the respGetInfCarCliente to set
	 */
	public void setRespGetInfCarCliente(
			ResponseGetInfCarCliente respGetInfCarCliente) {
		this.respGetInfCarCliente = respGetInfCarCliente;
	}
	/**
	 * @return the respDelClienteDto
	 */
	public ResponseDelClienteDto getRespDelClienteDto() {
		return respDelClienteDto;
	}
	/**
	 * @param respDelClienteDto the respDelClienteDto to set
	 */
	public void setRespDelClienteDto(ResponseDelClienteDto respDelClienteDto) {
		this.respDelClienteDto = respDelClienteDto;
	}
	/**
	 * @return the respUpClienteDto
	 */
	public ResponseUpClienteDto getRespUpClienteDto() {
		return respUpClienteDto;
	}
	/**
	 * @param respUpClienteDto the respUpClienteDto to set
	 */
	public void setRespUpClienteDto(ResponseUpClienteDto respUpClienteDto) {
		this.respUpClienteDto = respUpClienteDto;
	}
	/**
	 * @return the respGetUsuarios
	 */
	public ResponseGetUsuariosDto getRespGetUsuarios() {
		return respGetUsuarios;
	}
	/**
	 * @param respGetUsuarios the respGetUsuarios to set
	 */
	public void setRespGetUsuarios(ResponseGetUsuariosDto respGetUsuarios) {
		this.respGetUsuarios = respGetUsuarios;
	}
	/**
	 * @return the respGetCatalogos
	 */
	public ResponseGetCatalogosDto getRespGetCatalogos() {
		return respGetCatalogos;
	}
	/**
	 * @param respGetCatalogos the respGetCatalogos to set
	 */
	public void setRespGetCatalogos(ResponseGetCatalogosDto respGetCatalogos) {
		this.respGetCatalogos = respGetCatalogos;
	}
	/**
	 * @return the respGetInfSapCliente
	 */
	public ResponseGetInfClienteSap getRespGetInfSapCliente() {
		return respGetInfSapCliente;
	}
	/**
	 * @param respGetInfSapCliente the respGetInfSapCliente to set
	 */
	public void setRespGetInfSapCliente(
			ResponseGetInfClienteSap respGetInfSapCliente) {
		this.respGetInfSapCliente = respGetInfSapCliente;
	}
	/**
	 * @return the respValidaLogin
	 */
	public ResponseValidaLoginDto getRespValidaLogin() {
		return respValidaLogin;
	}
	/**
	 * @param respValidaLogin the respValidaLogin to set
	 */
	public void setRespValidaLogin(ResponseValidaLoginDto respValidaLogin) {
		this.respValidaLogin = respValidaLogin;
	}
	public ResponseActualizaStatusImpresionDto getResActualizaImpresion() {
		return resActualizaImpresion;
	}
	public void setResActualizaImpresion(
			ResponseActualizaStatusImpresionDto resActualizaImpresion) {
		this.resActualizaImpresion = resActualizaImpresion;
	}
	
	
	
	
}
