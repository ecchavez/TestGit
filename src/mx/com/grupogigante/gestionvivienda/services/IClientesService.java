/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.services;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletResponse;

import mx.com.grupogigante.gestionvivienda.domain.dto.ClienteSapDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ContactoDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfClienteSap;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseAddClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseDelClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfClienteSap;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUpClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.ResponseGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

/**
 *  Interface que contiene los abstrac methods para la Cartera de Clientes.
 *         
 */

public interface IClientesService {
	
	public abstract ResponseGetInfCarCliente getViaContacto(CriteriosGetInfCarCliente criterioClienteDto)throws ViviendaException;
	public abstract ResponseGetInfCarCliente getClientes(CriteriosGetInfCarCliente criterioClienteDto)throws ViviendaException;
	public abstract ResponseAddClienteDto addCliente(CriteriosGetInfCarCliente criterioClienteDto)throws ViviendaException;
	public abstract ResponseDelClienteDto delCliente(CriteriosGetInfCarCliente criterioClienteDto)throws ViviendaException;
	public abstract ResponseUpClienteDto upCliente(CriteriosGetInfCarCliente criterioClienteDto)throws ViviendaException;
	public abstract ResponseAddClienteDto addVisitaCliente(CriteriosGetInfCarCliente criterioClienteDto)throws ViviendaException;
	public abstract ResponseAddClienteDto addClienteSap(ClienteSapDto clientes,ContactoDto contacto)throws ViviendaException;
	//public abstract ResponseGetInfClienteSap getClientesSap(CriteriosGetInfClienteSap criteriosSap)throws ViviendaException;
	public abstract ResponseGetInfClienteSap getClientesSap(CriteriosGetInfClienteSap criteriosClienteSapDto,ResponseGetCatalogosDto respCatalogosDTO) throws ViviendaException;
	public abstract ResponseGetInfClienteSap getNamesClientesSap(CriteriosGetInfClienteSap criteriosSap)throws ViviendaException;
}
