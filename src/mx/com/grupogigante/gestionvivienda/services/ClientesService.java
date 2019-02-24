/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.services;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletResponse;

import mx.com.grupogigante.gestionvivienda.dao.ClientesDao;
import mx.com.grupogigante.gestionvivienda.dao.UsuariosDao;
import mx.com.grupogigante.gestionvivienda.domain.dto.ClienteSapDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ContactoDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosClientesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfClienteSap;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseAddClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseDelClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseDelUsuarioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfClienteSap;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetReporteClientesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUpClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.ResponseGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase con los Response de Cartera de Clientes               
 */

@Service
public class ClientesService implements IClientesService {

	@Autowired
	ClientesDao clientesDao;
	@Autowired
	UsuariosDao usuariosDao;
	
	
	public ResponseGetInfCarCliente getClientes(CriteriosGetInfCarCliente criteriosClienteDto) throws ViviendaException{
		return clientesDao.getClientes(criteriosClienteDto);
	}	
	public ResponseGetInfCarCliente getClientePorId(CriteriosGetInfCarCliente criteriosClienteDto) throws ViviendaException{
		return clientesDao.getClientePorId(criteriosClienteDto);
	}	
	public ResponseGetInfCarCliente getViaContacto(CriteriosGetInfCarCliente criteriosClienteDto) throws ViviendaException{
		return clientesDao.getViaContacto(criteriosClienteDto);
	}
	public ResponseAddClienteDto addCliente(CriteriosGetInfCarCliente criteriosClienteDto) throws ViviendaException{
		return clientesDao.addCliente(criteriosClienteDto);
	}
	public ResponseDelClienteDto delCliente(CriteriosGetInfCarCliente criteriosClienteDto) throws ViviendaException{
		return clientesDao.delCliente(criteriosClienteDto);
	}
	public ResponseUpClienteDto upCliente(CriteriosGetInfCarCliente criteriosClienteDto) throws ViviendaException{
		return clientesDao.upCliente(criteriosClienteDto);
	}	
	public ResponseGetReporteClientesDto getReporteClientes(CriteriosClientesDto criterios) throws ViviendaException {
		return clientesDao.getReporteClientes(criterios);
	}
	public ResponseAddClienteDto addVisitaCliente(CriteriosGetInfCarCliente criteriosClienteDto) throws ViviendaException{
		return clientesDao.addVisitaCliente(criteriosClienteDto);
	}
	public ResponseAddClienteDto addClienteSap(ClienteSapDto clientes,ContactoDto contactos) throws ViviendaException{
		return clientesDao.addClienteSap(clientes,contactos);
	}
	public ResponseGetInfClienteSap getClientesSap(CriteriosGetInfClienteSap criteriosClienteSapDto,ResponseGetCatalogosDto respCatalogosDTO) throws ViviendaException{
		return clientesDao.getClientesSap(criteriosClienteSapDto,respCatalogosDTO);
	}
	public ResponseGetInfClienteSap getNamesClientesSap(CriteriosGetInfClienteSap criteriosClienteSapDto) throws ViviendaException{
		return clientesDao.getNamesClientesSap(criteriosClienteSapDto);
	}

}