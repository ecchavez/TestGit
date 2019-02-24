package mx.com.grupogigante.gestionvivienda.dao.report;

import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.report.listaprecio.dto.ListaPrecioReporteRequestDto;
import mx.com.grupogigante.gestionvivienda.report.listaprecio.dto.ListaPrecioReporteResponseDto;

public interface ListaPreciosReporteDao {
	public ListaPrecioReporteResponseDto findListaPrecios(ListaPrecioReporteRequestDto reporteRequest) throws ReporteException;
}
