package mx.com.grupogigante.gestionvivienda.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.grupogigante.gestionvivienda.dao.UbicacionesDao;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosEquipoTiposDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosUbicacionesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetEquDetDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetUtInfSupDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionDatosMapaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionTecnicaDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

@Service
public class UbicacionesService implements IUbicacionesService {
	
	@Autowired
	public UbicacionesDao ubicacionesDao;

	public ResponseUbicacionTecnicaDto getUbicaciones() throws ViviendaException {
		return ubicacionesDao.getUbicaciones();
	}
	public ResponseGetUtInfSupDto getUtInfSup(CriteriosUbicacionesDto criteriosUtInfSup) throws ViviendaException{
		return ubicacionesDao.getUtInfSup(criteriosUtInfSup);
	}
	public ResponseGetEquDetDto getEquDetDto(CriteriosUbicacionesDto criteriosEqDet) throws ViviendaException{
		return ubicacionesDao.getEqDetail(criteriosEqDet);	
	}
	
	public ResponseUbicacionDatosMapaDto setCoordenadasImagen(CriteriosUbicacionesDto criterios) throws ViviendaException{
		return ubicacionesDao.setCoordenadasImagen(criterios);
	}
	
	public ResponseUbicacionDatosMapaDto getTiposEquipos(CriteriosEquipoTiposDto criterios) throws ViviendaException{
		return ubicacionesDao.getTiposEquipos(criterios);
	}
	
	public ResponseUbicacionDatosMapaDto setReplicaPisos(CriteriosUbicacionesDto criterios) throws ViviendaException{
		return ubicacionesDao.setReplicaPisos(criterios);
	}
}
