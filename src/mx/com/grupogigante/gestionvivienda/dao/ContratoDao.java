/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 16/08/2012              
 */
package mx.com.grupogigante.gestionvivienda.dao;

import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfContrato;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetCompPago;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfContrato;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseActualizaStatusImpresionDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

/**
 * @author WSNADM
 *
 */
public interface ContratoDao {
	public abstract ResponseGetInfContrato getContrato(CriteriosGetInfContrato criteriosContrato)throws ViviendaException;
	public abstract ResponseGetCompPago getComprobante(CriteriosGetInfContrato criteriosContrato)throws ViviendaException;
	public abstract ResponseActualizaStatusImpresionDto actualizaStatusImpresion(CriteriosGetInfCarCliente criteriosClienteDto);
}
