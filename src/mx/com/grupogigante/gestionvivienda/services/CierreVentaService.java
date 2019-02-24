/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 12/09/2012              
 */
package mx.com.grupogigante.gestionvivienda.services;

import java.util.List;

import mx.com.grupogigante.gestionvivienda.dao.CierreVentaDao;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfContrato;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfPag;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseAddClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetConPagosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.CriteriosGetInfGetCot;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseAddPagosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseCreacionPedidoDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseGetInfCotizacionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseGetInfoPagRegDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseObtieneReferenciaDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WSNADM
 *
 */
@Service
public class CierreVentaService implements ICierreVentaService {

	@Autowired
	CierreVentaDao cierreDao;
	
	public ResponseGetInfCotizacionDto getBusquedaCot(CriteriosGetInfGetCot criteriosGetCotDto) throws ViviendaException{
		return cierreDao.getBusquedaCot(criteriosGetCotDto);
	}
	public ResponseAddPagosDto addPagosMensuales(CriteriosGetInfGetCot criteriosPagosDto, List<String> listaNombreArchivos) throws ViviendaException{
		return cierreDao.addPagosMensuales(criteriosPagosDto, listaNombreArchivos);
	}
	public ResponseGetInfoPagRegDto getBusquedaPagos(CriteriosGetInfPag criteriosGetPagosDto) throws ViviendaException{
		return cierreDao.getBusquedaPagos(criteriosGetPagosDto);
	}
	public ResponseGetConPagosDto getPagosConciliacion(CriteriosGetInfPag criteriosGetPagosDto) throws ViviendaException{
		return cierreDao.getPagosConciliacion(criteriosGetPagosDto);
	}
	public ResponseGetInfCotizacionDto getClientesList(CriteriosGetInfGetCot criteriosGetClientesDto) throws ViviendaException{
		return cierreDao.getClientesList(criteriosGetClientesDto);
	}
	public ResponseCreacionPedidoDto addPedido(CriteriosGetInfGetCot criteriosGet) throws ViviendaException {
		return cierreDao.addPedido(criteriosGet);
	}	
	public ResponseObtieneReferenciaDto getReferencia(String conceptoReferencia) throws ViviendaException{
		return cierreDao.getReferencia(conceptoReferencia);
	}

}
