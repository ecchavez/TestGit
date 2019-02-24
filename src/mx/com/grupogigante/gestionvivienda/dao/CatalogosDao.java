/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 19/07/2012             
 */
package mx.com.grupogigante.gestionvivienda.dao;

import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.CriteriosGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.ResponseGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

/**
 * interface en donde se declaran los abstract methods de las operaciones de Catalogos 
 * Fecha de creación: 19/07/2012               
 */
public interface CatalogosDao {
	public abstract ResponseGetCatalogosDto getCatalogos(CriteriosGetCatalogosDto criteriosCatalogosDto)throws ViviendaException;
	public abstract ResponseGetCatalogosDto getCatalogos2(CriteriosGetCatalogosDto criteriosCatalogosDto)throws ViviendaException;

}
