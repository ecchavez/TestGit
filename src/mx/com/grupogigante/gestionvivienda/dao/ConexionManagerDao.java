/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creaci�n: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.dao;


import java.io.ByteArrayOutputStream;

import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosDatosMapaImagenDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionDatosMapaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.CriteriosDatosDigitalizacionImageDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.ResponseUploadFilesDigitDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
/**
 * interface en donde se declaran los abstract methods de las operaciones de Clientes 
 * Fecha de creaci�n: XX/06/2012               
 */
public interface ConexionManagerDao {
	public abstract ResponseUbicacionDatosMapaDto getUTSImageMap(CriteriosDatosMapaImagenDto criterios)throws ViviendaException;	
	public abstract ResponseUbicacionDatosMapaDto createImageDatosMaping(CriteriosDatosMapaImagenDto criterios) throws ViviendaException;
	public abstract ResponseUbicacionDatosMapaDto deleteImageDatosMaping(CriteriosDatosMapaImagenDto criterios) throws ViviendaException;
	public abstract ResponseUbicacionDatosMapaDto getValidaDatosImagen(CriteriosDatosMapaImagenDto criterios) throws ViviendaException;
	public abstract ResponseUbicacionDatosMapaDto getUTForImage(CriteriosDatosMapaImagenDto criterios) throws ViviendaException;
	public abstract ResponseUbicacionDatosMapaDto setEquipoTipoImagen(CriteriosDatosMapaImagenDto criterios) throws ViviendaException;
	public abstract ResponseUbicacionDatosMapaDto validaExisteImagenEquipo(CriteriosDatosMapaImagenDto criterios) throws ViviendaException;
	public abstract ResponseUbicacionDatosMapaDto setUpdateEquipoTipoImagen(CriteriosDatosMapaImagenDto criterios) throws ViviendaException;
	public abstract ResponseUbicacionDatosMapaDto getImagenEquipo (CriteriosDatosMapaImagenDto criterios) throws ViviendaException;
	public abstract ResponseUploadFilesDigitDto validaExisteDigit (CriteriosDatosDigitalizacionImageDto criterios) throws ViviendaException;
	public abstract ResponseUploadFilesDigitDto setDigitFiles (CriteriosDatosDigitalizacionImageDto criterios) throws ViviendaException;
	public abstract ResponseUploadFilesDigitDto setUpdateDigit (CriteriosDatosDigitalizacionImageDto criterios) throws ViviendaException;
	public abstract ResponseUploadFilesDigitDto getContador(CriteriosDatosDigitalizacionImageDto criterios) throws ViviendaException;
	public abstract ByteArrayOutputStream getImagen(String folio) throws ViviendaException;
	public abstract ResponseUploadFilesDigitDto setFechaGarantia(CriteriosDatosDigitalizacionImageDto criterios) throws ViviendaException;
}
