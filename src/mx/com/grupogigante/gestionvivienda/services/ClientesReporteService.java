/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.services;

import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;

/**
 *  Interface que contiene los abstrac methods para la Cartera de Clientes.
 *         
 */

public interface ClientesReporteService {
	public ResponseGetInfCarCliente getClientesReporte(CriteriosGetInfCarCliente criteriosClienteDto)  throws ViviendaException;
//	
//	public abstract ResponseGetInfCarCliente getViaContacto(CriteriosGetInfCarCliente criterioClienteDto)throws ViviendaException;
//	public abstract ResponseAddClienteDto addClientes(CriteriosGetInfCarCliente criterioClienteDto)throws ViviendaException;
//	public abstract ResponseDelClienteDto delClientes(CriteriosGetInfCarCliente criterioClienteDto)throws ViviendaException;
//	public abstract ResponseAddClienteDto upClientes(CriteriosGetInfCarCliente criterioClienteDto)throws ViviendaException;
//	public abstract ResponseAddClienteDto addVisitaClientes(CriteriosGetInfCarCliente criterioClienteDto)throws ViviendaException;
//	public abstract ResponseAddClienteDto addClientesSap(ClienteSapDto clientes,ContactoDto contacto)throws ViviendaException;
//	public abstract ResponseGetInfClienteSap getClientesSap(CriteriosGetInfClienteSap criteriosSap)throws ViviendaException;
//	public abstract ResponseGetInfClienteSap getNamesClientesSap(CriteriosGetInfClienteSap criteriosSap)throws ViviendaException;
}
