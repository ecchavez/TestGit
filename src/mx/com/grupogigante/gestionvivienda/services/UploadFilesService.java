/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.services;


import java.util.List;

import mx.com.grupogigante.gestionvivienda.dao.UploadFilesDao;
import mx.com.grupogigante.gestionvivienda.domain.dto.BoletinDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosDatosMapaImagenDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosFileUploadDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionDatosMapaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUploadFilesDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase con los Response de Cartera de Clientes               
 */

@Service
public class UploadFilesService implements IUploadFilesService {

	@Autowired
	UploadFilesDao uploadFilesDao;
	
	public ResponseUploadFilesDto setUploadImageMaping(CriteriosFileUploadDto criterios) throws ViviendaException{
		return uploadFilesDao.setUploadImageMaping(criterios);
	}
	
	public List<BoletinDto> viewContentFolder(CriteriosFileUploadDto criterios) throws ViviendaException{
		return uploadFilesDao.viewContentFolder(criterios);
	}
	
	public ResponseUploadFilesDto deleteContentFolder(CriteriosFileUploadDto criterios) throws ViviendaException{
		return uploadFilesDao.deleteContentFolder(criterios);
	}
	
	
}