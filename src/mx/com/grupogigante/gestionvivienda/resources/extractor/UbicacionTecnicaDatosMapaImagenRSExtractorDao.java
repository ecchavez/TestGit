package mx.com.grupogigante.gestionvivienda.resources.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.grupogigante.gestionvivienda.domain.vo.UbicacionTecnicaDatosMapaImagenVo;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class UbicacionTecnicaDatosMapaImagenRSExtractorDao implements ResultSetExtractor {
	public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
		UbicacionTecnicaDatosMapaImagenVo datosImagen = new UbicacionTecnicaDatosMapaImagenVo();
		byte[] Imagen = rs.getBlob(5).getBytes(1, (int)rs.getBlob(5).length());
		datosImagen.setIdUbicacion(rs.getString(1));
		datosImagen.setNombreImagen(rs.getString(2));		
		datosImagen.setBlobImage(rs.getBlob(5));
		datosImagen.setIsImage(Imagen);
		datosImagen.setFechaAlta(rs.getString(3));	
		datosImagen.setFechaActualizacion(rs.getString(4));
		datosImagen.setIdUTS(rs.getString(6));
		return datosImagen;
	}
}