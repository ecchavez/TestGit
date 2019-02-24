/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.services;

import mx.com.grupogigante.gestionvivienda.dao.ConexionManagerDao;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosDatosMapaImagenDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionDatosMapaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.CriteriosDatosDigitalizacionImageDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.ResponseUploadFilesDigitDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase con los Response de Cartera de Clientes               
 */

@Service
public class ConexionService implements IConexionManagerService {

	@Autowired
	ConexionManagerDao conexionDao;	

	public ResponseUbicacionDatosMapaDto getUTSImageMap(CriteriosDatosMapaImagenDto criterios) throws ViviendaException{
		return conexionDao.getUTSImageMap(criterios);
	}
	public ResponseUbicacionDatosMapaDto createImageDatosMaping(CriteriosDatosMapaImagenDto criterios) throws ViviendaException{
		return conexionDao.createImageDatosMaping(criterios);
	}
	public ResponseUbicacionDatosMapaDto deleteImageDatosMaping(CriteriosDatosMapaImagenDto criterios) throws ViviendaException{
		return conexionDao.deleteImageDatosMaping(criterios);
	}	
	public ResponseUbicacionDatosMapaDto getValidaDatosImagen(CriteriosDatosMapaImagenDto criterios) throws ViviendaException{
		return conexionDao.getValidaDatosImagen(criterios);
	}	
	public ResponseUbicacionDatosMapaDto getUTForImage(CriteriosDatosMapaImagenDto criterios) throws ViviendaException{
		return conexionDao.getUTForImage(criterios);
	}
	public ResponseUbicacionDatosMapaDto setEquipoTipoImagen(CriteriosDatosMapaImagenDto criterios) throws ViviendaException{
		return conexionDao.setEquipoTipoImagen(criterios);
	}	
	public ResponseUbicacionDatosMapaDto validaExisteImagenEquipo(CriteriosDatosMapaImagenDto criterios) throws ViviendaException{
		return conexionDao.validaExisteImagenEquipo(criterios);
	}
	public ResponseUbicacionDatosMapaDto setUpdateEquipoTipoImagen(CriteriosDatosMapaImagenDto criterios) throws ViviendaException{
		return conexionDao.setUpdateEquipoTipoImagen(criterios);
	}
	public ResponseUbicacionDatosMapaDto getImagenEquipo (CriteriosDatosMapaImagenDto criterios) throws ViviendaException{
		return conexionDao.getImagenEquipo(criterios);
	}
	public ResponseUploadFilesDigitDto validaExisteDigit (CriteriosDatosDigitalizacionImageDto criterios) throws ViviendaException{
		return conexionDao.validaExisteDigit(criterios);	
	}	
	public ResponseUploadFilesDigitDto setDigitFiles (CriteriosDatosDigitalizacionImageDto criterios) throws ViviendaException{
		return conexionDao.setDigitFiles(criterios);
	}
	public ResponseUploadFilesDigitDto setUpdateDigit (CriteriosDatosDigitalizacionImageDto criterios) throws ViviendaException{
		return conexionDao.setUpdateDigit(criterios);
	}	
	public ResponseUploadFilesDigitDto getContador(CriteriosDatosDigitalizacionImageDto criterios) throws ViviendaException{
		return conexionDao.getContador(criterios);
	}
	public ResponseUploadFilesDigitDto setFechaGarantia(CriteriosDatosDigitalizacionImageDto criterios) throws ViviendaException{
		return conexionDao.setFechaGarantia(criterios);
	}
}