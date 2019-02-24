/**
 * @author JL - SACTI CONSULTORES / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetTicket;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetTicketConstruccion;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseAddTicketDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseLogConstruccionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseTicketConstruccionDto;
import mx.com.grupogigante.gestionvivienda.domain.vo.ClienteDatosTicketVo;
import mx.com.grupogigante.gestionvivienda.domain.vo.ListaFicheros;
import mx.com.grupogigante.gestionvivienda.domain.vo.UpdateViciosVo;
import mx.com.grupogigante.gestionvivienda.domain.vo.ViciosResponse;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

/**
 * interface en donde se declaran los abstract methods de las operaciones de Clientes 
 * Fecha de creación: XX/06/2012               
 */
public interface TicketsDao {

	public abstract ResponseAddTicketDto addTicket(CriteriosGetTicket criteriosGetTicket) throws ViviendaException;
	public abstract ResponseAddTicketDto addTicketConstruccion(ListaFicheros listaFicheros) throws ViviendaException;
	public ResponseAddTicketDto updateTicketConstruccion(UpdateViciosVo updateViciosVo) throws ViviendaException;
	public ResponseAddTicketDto findTicket(CriteriosGetTicket criteriosGetTicket) throws ViviendaException;
	public ResponseTicketConstruccionDto findTicketConstruccion(CriteriosGetTicketConstruccion criteriosGetTicketConstruccion) throws ViviendaException;
	public ResponseLogConstruccionDto getLogTicketConstruccion(CriteriosGetTicketConstruccion criteriosGetTicketConstruccion) throws ViviendaException;
	public void addImagenesTicket(List<MultipartFile> files, String ticket, String path) throws ViviendaException;
	public void addImagenTicket(List<MultipartFile> files, String ticket, String consecutivo, String path) throws ViviendaException;
	public void addPDFTicket(MultipartFile files, String ticket, String path) throws ViviendaException;
	public ClienteDatosTicketVo obtieneDatosCliente(CriteriosGetTicketConstruccion criteriosGetTicketConstruccion) throws ViviendaException;
	public ViciosResponse obtieneCatalogoVicios() throws ViviendaException;
	
}
