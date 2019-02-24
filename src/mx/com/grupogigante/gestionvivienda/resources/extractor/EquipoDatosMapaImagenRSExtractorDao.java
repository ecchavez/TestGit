package mx.com.grupogigante.gestionvivienda.resources.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.grupogigante.gestionvivienda.domain.vo.EquipoDatosMapaImagenVo;
import mx.com.grupogigante.gestionvivienda.domain.vo.UbicacionTecnicaDatosMapaImagenVo;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class EquipoDatosMapaImagenRSExtractorDao implements ResultSetExtractor {
	public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
		EquipoDatosMapaImagenVo datosImagen = new EquipoDatosMapaImagenVo();
		byte[] Imagen = rs.getBlob(4).getBytes(1, (int)rs.getBlob(4).length());
		datosImagen.setIdUTS(rs.getString(1));
		datosImagen.setNombreImagen(rs.getString(2));		
		datosImagen.setTipo(rs.getString(3));
		datosImagen.setBlobImage(rs.getBlob(4));
		datosImagen.setIsImage(Imagen);
		return datosImagen;
	}
}