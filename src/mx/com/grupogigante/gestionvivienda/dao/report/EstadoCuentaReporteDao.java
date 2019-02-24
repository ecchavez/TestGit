package mx.com.grupogigante.gestionvivienda.dao.report;

import mx.com.grupogigante.gestionvivienda.report.edocta.dto.EstadoCuentaReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.EstadoCuentaReporteResponse;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;

public interface EstadoCuentaReporteDao {
	public EstadoCuentaReporteResponse buscaReporte(EstadoCuentaReporteRequest request) throws ReporteException ;
}
