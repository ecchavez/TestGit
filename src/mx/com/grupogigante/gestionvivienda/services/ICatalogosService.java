/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 19/07/2012               
 */
package mx.com.grupogigante.gestionvivienda.services;


import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.CriteriosGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.ResponseGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

/**
 * Interface Service para todos los catalogos                
 */

public interface ICatalogosService {

	public abstract ResponseGetCatalogosDto getCatalogos(CriteriosGetCatalogosDto criteriosCatalogosDto)throws ViviendaException;
	public abstract ResponseGetCatalogosDto getCatalogos2(CriteriosGetCatalogosDto criteriosCatalogosDto)throws ViviendaException;
}
