package mx.com.grupogigante.gestionvivienda.dao.report;

import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.report.ventas.dto.VentasReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.ventas.dto.VentasReporteResponse;

public interface VentasReporteDao {
	public VentasReporteResponse findVentas(VentasReporteRequest requestVentas) throws ReporteException;
}
