/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 19/07/2012               
 */
package mx.com.grupogigante.gestionvivienda.services;

import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.CriteriosDatosDigitalizacionImageDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.ResponseUploadFilesDigitDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

/**
 * Interface Service para todos los catalogos                
 */

public interface IDigitalizacionService {

	public ResponseUploadFilesDigitDto setDataDigit(CriteriosDatosDigitalizacionImageDto criterios)throws ViviendaException;
}
