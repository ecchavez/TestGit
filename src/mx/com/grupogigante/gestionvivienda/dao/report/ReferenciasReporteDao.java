package mx.com.grupogigante.gestionvivienda.dao.report;

import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.report.referencias.dto.ReferenciasReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.referencias.dto.ReferenciasReporteResponse;

public interface ReferenciasReporteDao {
	public ReferenciasReporteResponse findReferencias(ReferenciasReporteRequest requestReferencias) throws ReporteException;
}
