/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.services;

import java.util.List;

import mx.com.grupogigante.gestionvivienda.domain.dto.BoletinDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosDatosMapaImagenDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosFileUploadDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionDatosMapaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUploadFilesDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

/**
 *  Interface que contiene los abstrac methods para la Cartera de Clientes.
 *         
 */

public interface IUploadFilesService {	
	public abstract ResponseUploadFilesDto setUploadImageMaping(CriteriosFileUploadDto criterios) throws ViviendaException;
	public abstract List<BoletinDto> viewContentFolder(CriteriosFileUploadDto criterios) throws ViviendaException;
	public abstract ResponseUploadFilesDto deleteContentFolder(CriteriosFileUploadDto criterios) throws ViviendaException;
}
