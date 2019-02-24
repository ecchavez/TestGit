/**
 * @author Osvaldo Rodriguez/ Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 19/07/2012             
 */
package mx.com.grupogigante.gestionvivienda.dao;

import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.CriteriosDatosDigitalizacionImageDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.ResponseUploadFilesDigitDto;
import mx.com.grupogigante.gestionvivienda.exceptions.DaoException;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

/**
 * interface en donde se declaran los abstract methods de las operaciones de Catalogos 
 * Fecha de creación: 19/07/2012               
 */
public interface DigitalizacionDao {
	public ResponseUploadFilesDigitDto setDataDigit(CriteriosDatosDigitalizacionImageDto criterios)throws ViviendaException;
}