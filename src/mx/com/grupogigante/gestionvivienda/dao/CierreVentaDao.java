/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 12/09/2012            
 */
package mx.com.grupogigante.gestionvivienda.dao;

import java.util.List;

import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfPag;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetConPagosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.CriteriosGetInfGetCot;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseAddPagosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseCreacionPedidoDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseGetInfCotizacionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseGetInfoPagRegDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseObtieneReferenciaDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

/**
 * interface en donde se declaran los abstract methods de las operaciones de Catalogos 
 * Fecha de creación:  12/09/2012              
 */
public interface CierreVentaDao {

	public abstract ResponseGetInfCotizacionDto getBusquedaCot(CriteriosGetInfGetCot criteriosGetCotDto)throws ViviendaException;
	public abstract ResponseAddPagosDto addPagosMensuales(CriteriosGetInfGetCot criteriosPagosDto, List<String> listaNombreArchivos)throws ViviendaException;
	public abstract ResponseGetInfoPagRegDto getBusquedaPagos(CriteriosGetInfPag criteriosGetPagosDto)throws ViviendaException;
	public abstract ResponseGetConPagosDto getPagosConciliacion(CriteriosGetInfPag criteriosGetPagosDto)throws ViviendaException;
	public abstract ResponseGetInfCotizacionDto getClientesList(CriteriosGetInfGetCot criteriosGetClientesDto)throws ViviendaException;
	public abstract ResponseCreacionPedidoDto addPedido(CriteriosGetInfGetCot criteriosGet) throws ViviendaException;
	public abstract ResponseObtieneReferenciaDto getReferencia(String conceptoReferencia)throws ViviendaException;
}
