package mx.com.grupogigante.gestionvivienda.dao;

import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosEquipoTiposDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosUbicacionesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetEquDetDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetUtInfSupDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionDatosMapaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionTecnicaDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

public interface UbicacionesDao {
	
	public abstract ResponseUbicacionTecnicaDto getUbicaciones()throws ViviendaException;
	public abstract ResponseGetUtInfSupDto getUtInfSup(CriteriosUbicacionesDto criteriosUtInfSup)throws ViviendaException;
	public abstract ResponseGetEquDetDto getEqDetail(CriteriosUbicacionesDto criteriosEqDet) throws ViviendaException;
	public abstract ResponseUbicacionDatosMapaDto setCoordenadasImagen(CriteriosUbicacionesDto criterios) throws ViviendaException;
	public abstract ResponseUbicacionDatosMapaDto getTiposEquipos(CriteriosEquipoTiposDto criterios) throws ViviendaException;
	public abstract ResponseUbicacionDatosMapaDto setReplicaPisos(CriteriosUbicacionesDto criterios) throws ViviendaException;
}
