package mx.com.grupogigante.gestionvivienda.resources.maper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.grupogigante.gestionvivienda.resources.extractor.DigitContadorRSExtractorDao;
import mx.com.grupogigante.gestionvivienda.resources.extractor.DigitDatosImageRSExtractorDao;
import mx.com.grupogigante.gestionvivienda.resources.extractor.EquipoDatosMapaImagenRSExtractorDao;

import org.springframework.jdbc.core.RowMapper;

public class DigitContadorRowMaper implements RowMapper {
	public Object mapRow(ResultSet rs, int line) throws SQLException {
		// TODO Auto-generated method stub
		DigitContadorRSExtractorDao datos=new DigitContadorRSExtractorDao();
		return datos.extractData(rs);
	}
}
