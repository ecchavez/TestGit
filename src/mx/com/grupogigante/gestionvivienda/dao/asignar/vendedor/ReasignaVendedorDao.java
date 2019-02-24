package mx.com.grupogigante.gestionvivienda.dao.asignar.vendedor;

import mx.com.grupogigante.gestionvivienda.domain.dto.asignar.vendedor.ReasignaVendedorRequestDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.asignar.vendedor.ReasignaVendedorResponseDto;
import mx.com.grupogigante.gestionvivienda.exceptions.DaoException;

public interface ReasignaVendedorDao {
	public ReasignaVendedorResponseDto findVendedorMotivo(ReasignaVendedorRequestDto requestVendedores) throws DaoException ;
}
