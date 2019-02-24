/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 19/07/2012               
 */
package mx.com.grupogigante.gestionvivienda.services;

import mx.com.grupogigante.gestionvivienda.dao.DigitalizacionDao;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.CriteriosDatosDigitalizacionImageDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.ResponseUploadFilesDigitDto;
import mx.com.grupogigante.gestionvivienda.exceptions.DaoException;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Interface Service para todos los catalogos                
 */
@Service
public class DigitalizacionService implements IDigitalizacionService {
	
	@Autowired
	DigitalizacionDao digitalizacionDao;
	
	public ResponseUploadFilesDigitDto setDataDigit(CriteriosDatosDigitalizacionImageDto criterios)throws ViviendaException{
		return digitalizacionDao.setDataDigit(criterios);
	}
}

