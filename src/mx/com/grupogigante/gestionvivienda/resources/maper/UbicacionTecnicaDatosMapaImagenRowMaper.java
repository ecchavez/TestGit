package mx.com.grupogigante.gestionvivienda.resources.maper;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.grupogigante.gestionvivienda.resources.extractor.UbicacionTecnicaDatosMapaImagenRSExtractorDao;

import org.springframework.jdbc.core.RowMapper;

public class UbicacionTecnicaDatosMapaImagenRowMaper implements RowMapper {
	public Object mapRow(ResultSet rs, int line) throws SQLException {
		// TODO Auto-generated method stub
		UbicacionTecnicaDatosMapaImagenRSExtractorDao datos=new UbicacionTecnicaDatosMapaImagenRSExtractorDao();
		return datos.extractData(rs);
	}
}
