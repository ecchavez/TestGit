/**
 *  Clase creada por Osvaldo Rodriguez Martinez (SACTI consultores) con fecha de 24/09/2012
 */
package mx.com.grupogigante.gestionvivienda.services;

import java.util.ArrayList;

import mx.com.grupogigante.gestionvivienda.dao.SimuladorDao;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CotizadorBillPlaningDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CotizadorDetalleDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CotizadorSubequiposDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CriteriosObtieneEquipoCotizacionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CriteriosSimuladorDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.ResponseCotizacionActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.ResponseObtieneCotizacionBaseDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.ResponseObtieneSubEquiposCotizacionDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author osvaldo
 *
 */

@Service
public class SimuladorService implements ISimuladorService{
	
	@Autowired
	SimuladorDao simuladorDao;
	
	public ResponseCotizacionActionDto obtieneCotizacionBase (CriteriosSimuladorDto criterios)throws ViviendaException{			
		return simuladorDao.obtieneCotizacionBase(criterios);
	}
	public ResponseObtieneSubEquiposCotizacionDto obtieneSubEquiposCotizacion (CriteriosObtieneEquipoCotizacionDto criterios) throws ViviendaException {
		return simuladorDao.obtieneSubEquiposCotizacion(criterios);
	}
	public ResponseCotizacionActionDto setAdminCotizacion (CriteriosSimuladorDto criterios, ArrayList<CotizadorDetalleDto> cotizacionDetalleList, ArrayList<CotizadorBillPlaningDto> cotizacionBillPlanList, ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposList) throws ViviendaException {
		return simuladorDao.setAdminCotizacion(criterios, cotizacionDetalleList, cotizacionBillPlanList, cotizacionSubEquiposList);
	}	
	public ResponseObtieneCotizacionBaseDto getCotizacionGuardada (CriteriosSimuladorDto criterios) throws ViviendaException {
		return simuladorDao.getCotizacionGuardada(criterios);
	}
}
