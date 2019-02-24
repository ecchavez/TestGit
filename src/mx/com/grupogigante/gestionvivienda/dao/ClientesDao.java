/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.dao;

import mx.com.grupogigante.gestionvivienda.domain.dto.ClienteSapDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ContactoDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosClientesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfClienteSap;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseAddClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseDelClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfClienteSap;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetReporteClientesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUpClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.ResponseGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;

/**
 * interface en donde se declaran los abstract methods de las operaciones de Clientes 
 * Fecha de creación: XX/06/2012               
 */
public interface ClientesDao {
	public abstract ResponseGetInfCarCliente getClientes(CriteriosGetInfCarCliente criteriosClienteDto)throws ViviendaException;
	public abstract ResponseGetInfCarCliente getClientesReporte(CriteriosGetInfCarCliente criteriosClienteDto) throws ViviendaException;
	public abstract ResponseAddClienteDto addCliente(CriteriosGetInfCarCliente criteriosClienteDto)throws ViviendaException;
	public abstract ResponseDelClienteDto delCliente(CriteriosGetInfCarCliente criteriosClienteDto)throws ViviendaException;
	public abstract ResponseUpClienteDto upCliente(CriteriosGetInfCarCliente criteriosClienteDto)throws ViviendaException;
	public abstract ResponseGetInfCarCliente getViaContacto(CriteriosGetInfCarCliente criteriosClienteDto)throws ViviendaException;
	public abstract ResponseGetReporteClientesDto getReporteClientes(CriteriosClientesDto criteriosReporte)throws ViviendaException;
	public abstract ResponseAddClienteDto addVisitaCliente(CriteriosGetInfCarCliente criteriosClienteDto)throws ViviendaException;
	public abstract ResponseAddClienteDto addClienteSap(ClienteSapDto clientes,ContactoDto contacto)throws ViviendaException;
	public abstract ResponseGetInfClienteSap getClientesSap(CriteriosGetInfClienteSap criteriosClienteSapDto,ResponseGetCatalogosDto respCatalogosDTO)throws ViviendaException;
	public abstract ResponseGetInfClienteSap getNamesClientesSap(CriteriosGetInfClienteSap criteriosClienteSapDto)throws ViviendaException;
    public abstract ResponseGetInfCarCliente getClientePorId(CriteriosGetInfCarCliente criteriosClienteDto)throws ViviendaException;
	
}
