package mx.com.grupogigante.gestionvivienda.dao.report;

import mx.com.grupogigante.gestionvivienda.report.edocta.dto.ConsultaPagosReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.ConsultaPagosReporteResponse;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;

public interface ConsultaPagosReporteDao {
	public ConsultaPagosReporteResponse buscaReporte(ConsultaPagosReporteRequest request) throws ReporteException ;
}
