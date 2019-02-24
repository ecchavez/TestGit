/**
 *  Clase creada por Osvaldo Rodriguez Martinez (SACTI consultores) con fecha de 24/09/2012
 */
package mx.com.grupogigante.gestionvivienda.services;

import java.util.ArrayList;

import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CotizadorBillPlaningDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CotizadorDetalleDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CotizadorSubequiposDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CriteriosObtieneEquipoCotizacionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CriteriosSimuladorDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.ResponseCotizacionActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.ResponseObtieneCotizacionBaseDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.ResponseObtieneSubEquiposCotizacionDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

/**
 * @author osvaldo
 *
 */
public interface ISimuladorService {
	public abstract ResponseCotizacionActionDto obtieneCotizacionBase (CriteriosSimuladorDto criterios) throws ViviendaException;
	public abstract ResponseObtieneSubEquiposCotizacionDto obtieneSubEquiposCotizacion (CriteriosObtieneEquipoCotizacionDto criterios) throws ViviendaException;
	public abstract ResponseCotizacionActionDto setAdminCotizacion (CriteriosSimuladorDto criterios, ArrayList<CotizadorDetalleDto> cotizacionDetalleList, ArrayList<CotizadorBillPlaningDto> cotizacionBillPlanList, ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposList) throws ViviendaException;
	public abstract ResponseObtieneCotizacionBaseDto getCotizacionGuardada (CriteriosSimuladorDto criterios) throws ViviendaException ;
}
