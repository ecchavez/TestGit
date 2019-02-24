package mx.com.grupogigante.gestionvivienda.resources.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.grupogigante.gestionvivienda.domain.vo.DigitContadorDto;
import mx.com.grupogigante.gestionvivienda.domain.vo.DigitDatosImagenVo;
import mx.com.grupogigante.gestionvivienda.domain.vo.EquipoDatosMapaImagenVo;
import mx.com.grupogigante.gestionvivienda.domain.vo.UbicacionTecnicaDatosMapaImagenVo;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class DigitContadorRSExtractorDao implements ResultSetExtractor {
	public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
		DigitContadorDto datosContador = new DigitContadorDto();
		datosContador.setContador_id(rs.getInt(1));
		datosContador.setContador_equipo(rs.getString(2));		
		datosContador.setContador_proc(rs.getString(3));
		datosContador.setContador_file(rs.getString(4));
		datosContador.setContador_consecutivo(rs.getInt(5));
		return datosContador;
	}
}