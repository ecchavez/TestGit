package mx.com.grupogigante.gestionvivienda.resources.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class BitacoraRSExtractorDao implements ResultSetExtractor {
	public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
			UsuarioDto usr = new UsuarioDto();
			usr.setApp_mat(rs.getString(1));
			usr.setApp_pat(rs.getString(2));		
			return usr;
		}
}
