package mx.com.grupogigante.gestionvivienda.resources.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.grupogigante.gestionvivienda.domain.vo.DigitDatosImagenVo;
import mx.com.grupogigante.gestionvivienda.domain.vo.EquipoDatosMapaImagenVo;
import mx.com.grupogigante.gestionvivienda.domain.vo.UbicacionTecnicaDatosMapaImagenVo;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class DigitDatosImageRSExtractorDao implements ResultSetExtractor {
	public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
		DigitDatosImagenVo datosImagen = new DigitDatosImagenVo();
		
		byte[] Imagen = rs.getBlob(4).getBytes(1, (int)rs.getBlob(4).length());
		//file_id, file_clave, file_nombre, file_datablob, file_unidad, file_tipo
		datosImagen.setFile_unidad(rs.getString(5));
		datosImagen.setFile_nombre(rs.getString(3));		
		datosImagen.setFile_id(rs.getString(1));
		datosImagen.setBlobImage(rs.getBlob(4));
		datosImagen.setIsImage(Imagen);
		datosImagen.setFile_unidad(rs.getString(5));
		datosImagen.setFile_tipo(rs.getString(6));
		datosImagen.setFile_proceso(rs.getString(7));
		datosImagen.setFile_tarchivo(rs.getString(8));
		datosImagen.setFile_estatus(rs.getString(9));
		return datosImagen;
	}
}