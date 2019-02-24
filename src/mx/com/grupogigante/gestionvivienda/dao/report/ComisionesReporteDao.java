package mx.com.grupogigante.gestionvivienda.dao.report;

import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.report.comisiones.dto.ComisionesReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.comisiones.dto.ComisionesReporteResponse;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;

public interface ComisionesReporteDao {
	public ComisionesReporteResponse findComisiones(ComisionesReporteRequest requestComisiones) throws ViviendaException;
}
