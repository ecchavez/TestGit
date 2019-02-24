/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 19/07/2012               
 */
package mx.com.grupogigante.gestionvivienda.services;

import mx.com.grupogigante.gestionvivienda.dao.CatalogosDao;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.CriteriosGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.ResponseGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase cService para todos los catalogos                
 */

@Service
public class CatalogosService implements ICatalogosService{
	
	@Autowired
	CatalogosDao catalogosDao;
	
	public ResponseGetCatalogosDto getCatalogos(CriteriosGetCatalogosDto criteriosCatalogosDto) throws ViviendaException{
		return catalogosDao.getCatalogos(criteriosCatalogosDto);
	}
	
	public ResponseGetCatalogosDto getCatalogos2(CriteriosGetCatalogosDto criteriosCatalogosDto) throws ViviendaException{
		return catalogosDao.getCatalogos2(criteriosCatalogosDto);
	}
	
}
