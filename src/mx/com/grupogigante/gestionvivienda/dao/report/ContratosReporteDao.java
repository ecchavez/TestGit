package mx.com.grupogigante.gestionvivienda.dao.report;

import mx.com.grupogigante.gestionvivienda.report.contratos.dto.ContratosReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.contratos.dto.ContratosReporteResponse;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;

public interface ContratosReporteDao {
	public ContratosReporteResponse findContratos(ContratosReporteRequest requestContratos) throws ReporteException;
	public ContratosReporteResponse findEstatusAll(ContratosReporteRequest requestContratos) throws ReporteException;
}
