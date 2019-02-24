package mx.com.grupogigante.gestionvivienda.resources.maper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.grupogigante.gestionvivienda.resources.extractor.BitacoraRSExtractorDao;

import org.springframework.jdbc.core.RowMapper;

public class BitacoraRowMaper implements RowMapper{

	public Object mapRow(ResultSet rs, int line) throws SQLException {
		// TODO Auto-generated method stub
		BitacoraRSExtractorDao datos=new BitacoraRSExtractorDao();
		return datos.extractData(rs);
	}
}
