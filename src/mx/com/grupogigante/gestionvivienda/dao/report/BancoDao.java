package mx.com.grupogigante.gestionvivienda.dao.report;

import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosUbicacionesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetUtInfSupDto;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;

public interface BancoDao {
	public ResponseGetUtInfSupDto findBancos(CriteriosUbicacionesDto requestUbicaciones) throws ReporteException;
}
