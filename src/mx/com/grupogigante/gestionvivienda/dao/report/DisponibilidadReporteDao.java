package mx.com.grupogigante.gestionvivienda.dao.report;

import mx.com.grupogigante.gestionvivienda.report.disponibilidad.dto.DisponibilidadReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.disponibilidad.dto.DisponibilidadReporteResponse;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;

public interface DisponibilidadReporteDao {
	public DisponibilidadReporteResponse findDisponibilidad(DisponibilidadReporteRequest requestDisponibilidad) throws ReporteException;
}
