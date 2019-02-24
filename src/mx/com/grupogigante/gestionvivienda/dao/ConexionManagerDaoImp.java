/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosDatosMapaImagenDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionDatosMapaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.CriteriosDatosDigitalizacionImageDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.digitalizacion.ResponseUploadFilesDigitDto;
import mx.com.grupogigante.gestionvivienda.domain.vo.DigitContadorDto;
import mx.com.grupogigante.gestionvivienda.domain.vo.DigitDatosImagenVo;
import mx.com.grupogigante.gestionvivienda.domain.vo.EquipoDatosMapaImagenVo;
import mx.com.grupogigante.gestionvivienda.domain.vo.UbicacionTecnicaDatosMapaImagenVo;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.resources.conexion.DBConnection;
import mx.com.grupogigante.gestionvivienda.resources.maper.DigitContadorRowMaper;
import mx.com.grupogigante.gestionvivienda.resources.maper.DigitImagenRowMaper;
import mx.com.grupogigante.gestionvivienda.resources.maper.EquipoMapaImagenRowMaper;
import mx.com.grupogigante.gestionvivienda.resources.maper.UbicacionTecnicaDatosMapaImagenRowMaper;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.Connection;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.stereotype.Repository;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;


/**
 * Clase en donde se realiza la implementacion de todas las operaciones referentes a Cartera 
 * de clientes
 * Fecha de creación: XX/06/2012               
 */
@Repository
public class ConexionManagerDaoImp implements ConexionManagerDao {
	private Logger log = Logger.getLogger(ConexionManagerDaoImp.class);
	
	public ResponseUbicacionDatosMapaDto getUTSImageMap(CriteriosDatosMapaImagenDto criterios) throws ViviendaException
	{
		DBConnection con = new DBConnection();
		JdbcTemplate jdbcTemplate = con.connectDataBase();
		List<UbicacionTecnicaDatosMapaImagenVo> mapaDatos;
		ResponseUbicacionDatosMapaDto respUTDM = new ResponseUbicacionDatosMapaDto();
		String query="";
		
		try
		{
			query="SELECT u.ubicacion_id, u.mapa_imagen_nombre, i.mapa_imagen_fecha_alta, i.mapa_imagen_fecha_up, i.mapa_imagen_blob, u.ubicacion_parent FROM vivienda.imagen_mapa i INNER JOIN vivienda.ubicaciones u ON u.mapa_imagen_nombre = i.mapa_imagen_nombre WHERE u.ubicacion_id='"+criterios.getIdUbicacion()+"'";
			//query="SELECT u.ubicacion_id, u.mapa_imagen_nombre, i.mapa_imagen_fecha_alta, i.mapa_imagen_fecha_up, i.mapa_imagen_blob, u.ubicacion_parent FROM imagen_mapa i INNER JOIN ubicaciones u ON u.mapa_imagen_nombre = i.mapa_imagen_nombre WHERE u.ubicacion_id='"+criterios.getIdUbicacion()+"'";
			/*query="SELECT ";
			query+="ubicaciones.ubicacion_id,";
			query+="ubicaciones.mapa_imagen_nombre,";
			query+="imagen_mapa.mapa_imagen_fecha_alta,";
			query+="imagen_mapa.mapa_imagen_fecha_up,";
			query+="imagen_mapa.mapa_imagen_blob, ";
			query+="ubicaciones.ubicacion_parent ";
			query+="FROM ";
			query+="vivienda.imagen_mapa ";
			query+="INNER JOIN vivienda.ubicaciones ON ubicaciones.mapa_imagen_nombre = imagen_mapa.mapa_imagen_nombre ";			
			query+="WHERE ubicaciones.ubicacion_id='"+criterios.getIdUbicacion()+"'";*/
			
			mapaDatos=jdbcTemplate.query(query,new UbicacionTecnicaDatosMapaImagenRowMaper());
			if(mapaDatos.equals(null))
			{
				respUTDM.setMensaje("FAULT");
				respUTDM.setDescripcion("No hay datos para esta consulta");
			}
			else
			{
				if(mapaDatos.size()>0)
				{
					respUTDM.setMensaje("SUCCESS");
					respUTDM.setDescripcion("Se encontraron "+mapaDatos.size()+" datos");
				}
				else
				{
					respUTDM.setMensaje("FAULT");
					respUTDM.setDescripcion("No existen imagenes vinculadas a esta Unidad Tecnica");
				}
			}
		}catch (Exception excp) {
			log.error("ERROR: ",excp);
			respUTDM.setMensaje("FAULT");
			respUTDM.setDescripcion("ERROR: "+excp.getMessage());
			throw new ViviendaException(excp.getMessage());
		}
		respUTDM.setListaImagenDatos(mapaDatos);
		return respUTDM;
	}
	
	public ResponseUbicacionDatosMapaDto createImageDatosMaping(CriteriosDatosMapaImagenDto criterios) throws ViviendaException {
		DBConnection con = new DBConnection();
		JdbcTemplate insertDatosImagenMaping = con.connectDataBase();
		ResponseUbicacionDatosMapaDto respUTDM = new ResponseUbicacionDatosMapaDto();
		LobHandler lobHandler = new DefaultLobHandler(); 
        final CriteriosDatosMapaImagenDto fcriterios = criterios;
		try
		{					
			if(criterios.getTipo().equals("insert"))
			{
				insertDatosImagenMaping.execute("INSERT INTO vivienda.ubicaciones (ubicacion_id, mapa_imagen_nombre,ubicacion_parent) VALUES (?,?,?)",
				//insertDatosImagenMaping.execute("INSERT INTO ubicaciones (ubicacion_id, mapa_imagen_nombre,ubicacion_parent) VALUES (?,?,?)", 
						new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
					protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
						ps.setString(1, fcriterios.getIdUbicacion());
						ps.setString(2, fcriterios.getNombreImagen());	
						ps.setString(3, fcriterios.getIdUTS());	
					}
				});	
				
				insertDatosImagenMaping.execute("INSERT INTO vivienda.imagen_mapa (mapa_imagen_nombre, mapa_imagen_blob, mapa_imagen_fecha_alta, mapa_imagen_fecha_up, ubicacion_parent)  VALUES (?,?, CURRENT DATE, CURRENT DATE,?)", new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
				//insertDatosImagenMaping.execute("INSERT INTO imagen_mapa (mapa_imagen_nombre, mapa_imagen_blob, mapa_imagen_fecha_alta, mapa_imagen_fecha_up, ubicacion_parent)  VALUES (?,?, CURRENT DATE, CURRENT DATE,?)", new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
					protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
						ps.setString(1, fcriterios.getNombreImagen());
						try {
							lobCreator.setBlobAsBinaryStream(ps, 2, fcriterios.getImagenMaping().getInputStream(), (int)fcriterios.getImagenMaping().getSize());
						} catch (IOException e) {
							log.error("ERROR: ",e);	
						}
						ps.setString(3, fcriterios.getIdUTS());
					}
				});	
				
				insertDatosImagenMaping.execute("UPDATE vivienda.ubicaciones SET mapa_imagen_nombre=? WHERE ubicacion_parent=?", new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
				//insertDatosImagenMaping.execute("UPDATE ubicaciones SET mapa_imagen_nombre=? WHERE ubicacion_parent=?", new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
					protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
						ps.setString(1, fcriterios.getNombreImagen());						
						ps.setString(2, fcriterios.getIdUTS());
					}
				});
				
			}
			else if(criterios.getTipo().equals("update"))
			{ 
				insertDatosImagenMaping.execute("INSERT INTO vivienda.ubicaciones (ubicacion_id, mapa_imagen_nombre,ubicacion_parent) VALUES (?,?,?)",
				//insertDatosImagenMaping.execute("INSERT INTO ubicaciones (ubicacion_id, mapa_imagen_nombre,ubicacion_parent) VALUES (?,?,?)", 
						new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
					protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
						ps.setString(1, fcriterios.getIdUbicacion());
						ps.setString(2, fcriterios.getNombreImagen());
						ps.setString(3, fcriterios.getIdUTS());
					}
				});	
				/*insertDatosImagenMaping.execute("UPDATE imagen_mapa SET mapa_imagen_id_ubicacion=?, mapa_imagen_nombre=?, mapa_imagen_fecha_up=now() WHERE mapa_imagen_id_ubicacion=?", new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
					protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
						ps.setString(1, fcriterios.getIdUbicacion());
						ps.setString(2, fcriterios.getUrlImagen());
						ps.setString(3, fcriterios.getNombreImagen());
						try {
							lobCreator.setBlobAsBinaryStream(ps, 4, fcriterios.getImagenMaping().getInputStream(), (int)fcriterios.getImagenMaping().getSize());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.out.println(e.getMessage());
							e.printStackTrace();
						}
						ps.setString(5, fcriterios.getIdUbicacion());
					}
				});*/			
			}
			
			else if(criterios.getTipo().equals("copy"))
			{
				final java.sql.Blob blobData =  com.ibm.db2.jcc.t2zos.DB2LobFactory.createBlob(fcriterios.getIsImage());
				insertDatosImagenMaping.execute("INSERT INTO vivienda.ubicaciones (ubicacion_id, mapa_imagen_nombre,ubicacion_parent) VALUES (?,?,?)",
				//insertDatosImagenMaping.execute("INSERT INTO ubicaciones (ubicacion_id, mapa_imagen_nombre,ubicacion_parent) VALUES (?,?,?)", 
						new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
					protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
						ps.setString(1, fcriterios.getIdUbicacion());
						ps.setString(2, fcriterios.getNombreImagen());	
						ps.setString(3, fcriterios.getIdUTS());	
					}
				});	
				
				insertDatosImagenMaping.execute("INSERT INTO vivienda.imagen_mapa (mapa_imagen_nombre, mapa_imagen_blob, mapa_imagen_fecha_alta, mapa_imagen_fecha_up, ubicacion_parent)  VALUES (?,?, CURRENT DATE, CURRENT DATE, ?)", new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
				//insertDatosImagenMaping.execute("INSERT INTO imagen_mapa (mapa_imagen_nombre, mapa_imagen_blob, mapa_imagen_fecha_alta, mapa_imagen_fecha_up, ubicacion_parent)  VALUES (?,?, CURRENT DATE, CURRENT DATE, ?)", new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
					protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
						ps.setString(1, fcriterios.getNombreImagen());
						try {
							lobCreator.setBlobAsBinaryStream(ps, 2, blobData.getBinaryStream(), (int)blobData.length());
						} catch (Exception e) {
							log.error("ERROR: ", e);
						}
						ps.setString(3, fcriterios.getIdUTS());
					}
				});	
			}
			
			respUTDM.setMensaje("SUCCESS");
			respUTDM.setDescripcion("Datos del mapa guardados satisfactoriamnete");
			
		}catch (Exception excp) {
			log.error("ERROR: ", excp);
			respUTDM.setMensaje("FAULT");
			respUTDM.setDescripcion("Error: "+excp.getMessage());
			throw new ViviendaException(excp.getMessage());			
		}

		return respUTDM;
	}
	
	public ResponseUbicacionDatosMapaDto deleteImageDatosMaping(CriteriosDatosMapaImagenDto criterios) throws ViviendaException {
		DBConnection con = new DBConnection();
		JdbcTemplate delete = con.connectDataBase();
		ResponseUbicacionDatosMapaDto respUTDM = new ResponseUbicacionDatosMapaDto();
		
		try
		{
			delete.update("DELETE FROM vivienda.ubicaciones WHERE ubicacion_id='"+criterios.getIdUbicacion()+"'");
		    //delete.update("DELETE FROM ubicaciones WHERE ubicacion_id='"+criterios.getIdUbicacion()+"'");
		    respUTDM.setMensaje("SUCCESS");
			respUTDM.setDescripcion("Datos del mapa eliminados satisfactoriamente");		
		}catch (Exception excp) {
			log.error("ERROR: ", excp);
			respUTDM.setMensaje("FAULT");
			respUTDM.setDescripcion("Error: "+excp.getMessage());
			throw new ViviendaException(excp.getMessage());		
		}
		return respUTDM;
	}	
	
	public ResponseUbicacionDatosMapaDto getValidaDatosImagen(CriteriosDatosMapaImagenDto criterios) throws ViviendaException
	{
		DBConnection con = new DBConnection();
		JdbcTemplate jdbcTemplate = con.connectDataBase();
		List<UbicacionTecnicaDatosMapaImagenVo> mapaDatos;
		ResponseUbicacionDatosMapaDto respUTDM = new ResponseUbicacionDatosMapaDto();
		String query="";
		
		try
		{
			query="SELECT u.ubicacion_id, u.mapa_imagen_nombre, i.mapa_imagen_fecha_alta, i.mapa_imagen_fecha_up, i.mapa_imagen_blob, u.ubicacion_parent FROM vivienda.imagen_mapa i INNER JOIN vivienda.ubicaciones u ON u.mapa_imagen_nombre = i.mapa_imagen_nombre WHERE u.mapa_imagen_nombre='"+criterios.getNombreImagen()+"'";
			//query="SELECT u.ubicacion_id, u.mapa_imagen_nombre, i.mapa_imagen_fecha_alta, i.mapa_imagen_fecha_up, i.mapa_imagen_blob, u.ubicacion_parent FROM imagen_mapa i INNER JOIN ubicaciones u ON u.mapa_imagen_nombre = i.mapa_imagen_nombre WHERE u.mapa_imagen_nombre='"+criterios.getNombreImagen()+"'";
			/*query="SELECT ";
			query+="ubicaciones.ubicacion_id,";
			query+="ubicaciones.mapa_imagen_nombre,";
			query+="imagen_mapa.mapa_imagen_fecha_alta,";
			query+="imagen_mapa.mapa_imagen_fecha_up,";
			query+="imagen_mapa.mapa_imagen_blob, ";
			query+="ubicaciones.ubicacion_parent ";
			query+="FROM ";
			query+="vivienda.imagen_mapa ";
			query+="INNER JOIN vivienda.ubicaciones ON ubicaciones.mapa_imagen_nombre = imagen_mapa.mapa_imagen_nombre ";			
			query+="WHERE ubicaciones.mapa_imagen_nombre='"+criterios.getNombreImagen()+"'";*/
			
			mapaDatos=jdbcTemplate.query(query, new UbicacionTecnicaDatosMapaImagenRowMaper());
			
			if(mapaDatos==null)
			{
				respUTDM.setMensaje("FAULT");
				respUTDM.setDescripcion("SD");
			}
			else
			{
				if(mapaDatos.size()>0)
				{
					respUTDM.setMensaje("SUCCESS");
					respUTDM.setDescripcion("Se encontraron "+mapaDatos.size()+" datos");
				}
				else
				{
					respUTDM.setMensaje("FAULT");
					respUTDM.setDescripcion("SD");
				}
			}
		}catch (Exception excp) {
			log.error("ERROR: ", excp);
			respUTDM.setMensaje("FAULT");
			respUTDM.setDescripcion("ERROR: "+excp.getMessage());
			throw new ViviendaException(excp.getMessage());
		}
		respUTDM.setListaImagenDatos(mapaDatos);
		return respUTDM;
	}
	
	public ResponseUbicacionDatosMapaDto getUTForImage(CriteriosDatosMapaImagenDto criterios) throws ViviendaException
	{
		DBConnection con = new DBConnection();
		JdbcTemplate jdbcTemplate = con.connectDataBase();
		List<UbicacionTecnicaDatosMapaImagenVo> mapaDatos = new ArrayList<UbicacionTecnicaDatosMapaImagenVo>();
		ResponseUbicacionDatosMapaDto respUTDM = new ResponseUbicacionDatosMapaDto();
		String query="";
		
		LobHandler lobHandler = new DefaultLobHandler(); 
					
		try
		{
			query="SELECT u.ubicacion_id, u.mapa_imagen_nombre, vi.mapa_imagen_fecha_alta, vi.mapa_imagen_fecha_up, vi.mapa_imagen_blob, u.ubicacion_parent FROM vivienda.imagen_mapa vi INNER JOIN vivienda.ubicaciones u ON u.mapa_imagen_nombre = vi.mapa_imagen_nombre WHERE u.ubicacion_parent='"+criterios.getIdUbicacion()+"'";
			//query="SELECT u.ubicacion_id, u.mapa_imagen_nombre, vi.mapa_imagen_fecha_alta, vi.mapa_imagen_fecha_up, vi.mapa_imagen_blob, u.ubicacion_parent FROM imagen_mapa vi INNER JOIN ubicaciones u ON u.mapa_imagen_nombre = vi.mapa_imagen_nombre WHERE u.ubicacion_parent='"+criterios.getIdUbicacion()+"'";
			/*query="SELECT ";
			query+="ubicaciones.ubicacion_id,";
			query+="ubicaciones.mapa_imagen_nombre,";
			query+="imagen_mapa.mapa_imagen_fecha_alta,";
			query+="imagen_mapa.mapa_imagen_fecha_up,";
			query+="imagen_mapa.mapa_imagen_blob, ";
			query+="ubicaciones.ubicacion_parent ";
			query+="FROM ";
			query+="vivienda.imagen_mapa ";
			query+="INNER JOIN vivienda.ubicaciones ON ubicaciones.mapa_imagen_nombre = imagen_mapa.mapa_imagen_nombre ";			
			query+="WHERE ubicaciones.ubicacion_parent='"+criterios.getIdUbicacion()+"'";*/
			
			mapaDatos=jdbcTemplate.query(query,new UbicacionTecnicaDatosMapaImagenRowMaper());
			
			try
			{
				if(mapaDatos.size()>0)
				{
					respUTDM.setMensaje("SUCCESS");
					respUTDM.setDescripcion("Se encontraron "+mapaDatos.size()+" datos");
				}
				else
				{
					respUTDM.setMensaje("FAULT");
					respUTDM.setDescripcion("No existen imagenes vinculadas a esta Unidad Tecnica");
				}
				
			}
			catch(Exception e)
			{
				log.error("ERROR: ", e);
				respUTDM.setMensaje("FAULT");
				respUTDM.setDescripcion("ERROR: "+e.getMessage());
				throw new ViviendaException(e.getMessage());
			}
		}catch (Exception excp) {
			log.error("ERROR: ", excp);
			respUTDM.setMensaje("FAULT");
			respUTDM.setDescripcion("ERROR: "+excp.getMessage());
			throw new ViviendaException(excp.getMessage());
		}
		respUTDM.setListaImagenDatos(mapaDatos);
		return respUTDM;
	}
	
	public ResponseUbicacionDatosMapaDto setEquipoTipoImagen(CriteriosDatosMapaImagenDto criterios) throws ViviendaException
	{
		DBConnection con = new DBConnection();
		JdbcTemplate insertaTiposEquipos = con.connectDataBase();
		ResponseUbicacionDatosMapaDto respUTDM = new ResponseUbicacionDatosMapaDto();
		final CriteriosDatosMapaImagenDto fcriterios = criterios;
		LobHandler lobHandler = new DefaultLobHandler(); 
		
		try
		{
			insertaTiposEquipos.execute("INSERT INTO vivienda.imagen_equipo (imagen_nombre, equipo_unidad_ts, imagen_blob, imagen_fecha_actualiza, imagen_fecha_add, imagen_tipo) VALUES (?,?,?, CURRENT DATE, CURRENT DATE, ?)", new AbstractLobCreatingPreparedStatementCallback(lobHandler) { 
			//insertaTiposEquipos.execute("INSERT INTO imagen_equipo (imagen_nombre, equipo_unidad_ts, imagen_blob, imagen_fecha_actualiza, imagen_fecha_add, imagen_tipo) VALUES (?,?,?, CURRENT DATE, CURRENT DATE, ?)", new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
				protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
					ps.setString(1, fcriterios.getNombreImagen());
					ps.setString(2, fcriterios.getIdUTS());
					try {
						lobCreator.setBlobAsBinaryStream(ps, 3, fcriterios.getImagenMaping().getInputStream(), (int)fcriterios.getImagenMaping().getSize());
					} catch (IOException e) {
						log.error("ERROR: ", e);
					}
					ps.setString(4, fcriterios.getTipo());
				}
			});	
			
			respUTDM.setMensaje("SUCCESS");
			respUTDM.setDescripcion("Datos de la imagen guardados satisfactoriamente");
		}catch (Exception excp) {
			log.error("ERROR: ", excp);
			respUTDM.setMensaje("FAULT");
			respUTDM.setDescripcion("ERROR: "+excp.getMessage());
			throw new ViviendaException(excp.getMessage());
		}
		return respUTDM;
	}
	
	public ResponseUbicacionDatosMapaDto setUpdateEquipoTipoImagen(CriteriosDatosMapaImagenDto criterios) throws ViviendaException
	{
		DBConnection con = new DBConnection();
		JdbcTemplate updateTiposEquipos = con.connectDataBase();
		ResponseUbicacionDatosMapaDto respUTDM = new ResponseUbicacionDatosMapaDto();
		final CriteriosDatosMapaImagenDto fcriterios = criterios;
		LobHandler lobHandler = new DefaultLobHandler(); 
		
		try
		{		
			updateTiposEquipos.execute("UPDATE vivienda.imagen_equipo SET imagen_blob=? WHERE imagen_tipo=? AND equipo_unidad_ts=?", new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
			//updateTiposEquipos.execute("UPDATE imagen_equipo SET imagen_blob=? WHERE imagen_tipo=? AND equipo_unidad_ts=?", new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
				protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
					try {
						lobCreator.setBlobAsBinaryStream(ps, 1, fcriterios.getImagenMaping().getInputStream(), (int)fcriterios.getImagenMaping().getSize());
					} catch (IOException e) {
						log.error("ERROR: ", e);
					}
					
					ps.setString(2, fcriterios.getTipo());						
					ps.setString(3, fcriterios.getIdUTS());
				}
			});
			
			respUTDM.setMensaje("SUCCESS");
			respUTDM.setDescripcion("Datos de la imagen guardados satisfactoriamente");
		}catch (Exception excp) {
			log.error("ERROR: ", excp);
			respUTDM.setMensaje("FAULT");
			respUTDM.setDescripcion("ERROR: "+excp.getMessage());
			throw new ViviendaException(excp.getMessage());
		}
		return respUTDM;
	}
	
	
	
	public ResponseUbicacionDatosMapaDto validaExisteImagenEquipo(CriteriosDatosMapaImagenDto criterios) throws ViviendaException
	{			
		DBConnection con = new DBConnection();
		JdbcTemplate jdbcTemplate = con.connectDataBase();
		List<EquipoDatosMapaImagenVo> mapaDatos = new ArrayList<EquipoDatosMapaImagenVo>();
		ResponseUbicacionDatosMapaDto respUTDM = new ResponseUbicacionDatosMapaDto();
		String query="";
		
		try
		{
			query="SELECT equipo_unidad_ts, imagen_nombre, imagen_tipo, imagen_blob FROM vivienda.imagen_equipo WHERE equipo_unidad_ts='"+criterios.getIdUTS()+"' AND imagen_tipo='"+criterios.getTipo()+"'";
			//query="SELECT equipo_unidad_ts, imagen_nombre, imagen_tipo, imagen_blob FROM imagen_equipo WHERE equipo_unidad_ts='"+criterios.getIdUTS()+"' AND imagen_tipo='"+criterios.getTipo()+"'";			
			
			mapaDatos=jdbcTemplate.query(query,new EquipoMapaImagenRowMaper());
		
			if(mapaDatos.size()>0)
			{
				respUTDM.setMensaje("SUCCESS");
				respUTDM.setDescripcion("Se encontraron "+mapaDatos.size()+" datos");
			}
			else
			{
				respUTDM.setMensaje("FAULT");
				respUTDM.setDescripcion("No existen imagenes vinculadas a esta Unidad Tecnica");
			}
			
		}catch (Exception excp) {
			log.error("ERROR: ", excp);
			respUTDM.setMensaje("FAULT");
			respUTDM.setDescripcion("ERROR: "+excp.getMessage());
			throw new ViviendaException(excp.getMessage());
		}
		respUTDM.setListaEquiposImagenesDatos(mapaDatos);
		return respUTDM;
			
	}
	
	public ResponseUbicacionDatosMapaDto getImagenEquipo(CriteriosDatosMapaImagenDto criterios) throws ViviendaException
	{			
		DBConnection con = new DBConnection();
		JdbcTemplate jdbcTemplate = con.connectDataBase();
		List<EquipoDatosMapaImagenVo> equiposDatos = new ArrayList<EquipoDatosMapaImagenVo>();
		ResponseUbicacionDatosMapaDto respUTDM = new ResponseUbicacionDatosMapaDto();
		String query="";
		
		try
		{
			query="SELECT equipo_unidad_ts, imagen_nombre, imagen_tipo, imagen_blob FROM vivienda.imagen_equipo WHERE equipo_unidad_ts='"+criterios.getIdUTS()+"'";
			//query="SELECT equipo_unidad_ts, imagen_nombre, imagen_tipo, imagen_blob FROM imagen_equipo WHERE equipo_unidad_ts='"+criterios.getIdUTS()+"'";
			
			equiposDatos=jdbcTemplate.query(query,new EquipoMapaImagenRowMaper());

			if(equiposDatos.size()>0)
			{
				respUTDM.setMensaje("SUCCESS");
				respUTDM.setDescripcion("Se encontraron "+equiposDatos.size()+" datos");
			}
			else
			{
				respUTDM.setMensaje("FAULT");
				respUTDM.setDescripcion("No existen imagenes vinculadas a esta Unidad Tecnica");
			}				
			
		}catch (Exception excp) {
			log.error("ERROR: ", excp);
			respUTDM.setMensaje("FAULT");
			respUTDM.setDescripcion("ERROR: "+excp.getMessage());
			throw new ViviendaException(excp.getMessage());
		}
		respUTDM.setListaEquiposImagenesDatos(equiposDatos);
		return respUTDM;			
	}
	
	
	public ResponseUploadFilesDigitDto getContador(CriteriosDatosDigitalizacionImageDto criterios) throws ViviendaException
	{			
		DBConnection con = new DBConnection();
		JdbcTemplate jdbcMssqlTemplate = con.connectMSSQLDataBase();
		List<DigitContadorDto> datosContador = new ArrayList<DigitContadorDto>();
		ResponseUploadFilesDigitDto respUTDM = new ResponseUploadFilesDigitDto();
		LobHandler lobHandler = new DefaultLobHandler();
		String query="";	
		
		try
		{			
			query="SELECT contador_id, contador_equipo, contador_proc, contador_file, contador_consecutivo FROM contadores WHERE contador_proc='"+criterios.getTipo()+"' AND contador_file='"+criterios.getSubtipo()+"' AND contador_equipo='"+criterios.getEquipo()+"'";
			
			log.info("query2:" + query);
			//System.out.println(query);		
			datosContador=jdbcMssqlTemplate.query(query,new DigitContadorRowMaper());

			if(datosContador.size()>0)
			{
				log.info("datos contador update =" + datosContador.get(0).getContador_consecutivo());
				respUTDM=updateContador(criterios);
				//respUTDM.setConsecutivo(datosContador.get(0));
				/*respUTDM.setMensaje("SUCCESS");
				respUTDM.setDescripcion("Se encontraron "+datosContador.size()+" datos");*/
			}
			else
			{		
				log.info("datos contador insert");
				respUTDM=setContador(criterios);
				respUTDM.setConsecutivo(1);
				/*respUTDM.setMensaje("FAULT");
				respUTDM.setDescripcion("No existen imagenes vinculadas a esta Unidad Tecnica");*/
				
			}
						
		}catch (Exception excp) {
			log.error("ERROR: ", excp);
			respUTDM.setMensaje("FAULT");
			respUTDM.setDescripcion("ERROR: "+excp.getMessage());
			throw new ViviendaException(excp.getMessage());			
		}
		respUTDM.setDatosContador(datosContador);
		return respUTDM;			
	}
	
	public ResponseUploadFilesDigitDto updateContador(CriteriosDatosDigitalizacionImageDto criterios) throws ViviendaException
	{			
		DBConnection con = new DBConnection();
		JdbcTemplate jdbcMssqlTemplate = con.connectMSSQLDataBase();
		List<DigitContadorDto> datosContador = new ArrayList<DigitContadorDto>();
		ResponseUploadFilesDigitDto respUTDM = new ResponseUploadFilesDigitDto();
		LobHandler lobHandler = new DefaultLobHandler();
		final CriteriosDatosDigitalizacionImageDto fcriterios = criterios;
		String query="";	
		
		try
		{	
			//query="UPDATE contadores SET contador_equipo = ?, contador_proc = ?, contador_file = ?, contador_consecutivo = contador_consecutivo+1 WHERE contador_proc=? AND contador_file=? AND contador_equipo=?";
			query="UPDATE contadores SET contador_consecutivo = contador_consecutivo+1 WHERE contador_proc=? AND contador_file=? AND contador_equipo=?";
			log.info("update contadores:" + query);
			log.info("UPDATE contadores SET contador_consecutivo = contador_consecutivo+1 WHERE contador_proc=" + fcriterios.getSubtipo() + " AND contador_file=" + fcriterios.getTipo() + " AND contador_equipo=" + fcriterios.getEquipo());
			//log.info("UPDATE contadores SET contador_equipo = " + fcriterios.getEquipo() + ", contador_proc = " + fcriterios.getTipo() + ", contador_file = " + fcriterios.getSubtipo() + ", contador_consecutivo = contador_consecutivo+1 WHERE contador_proc=" + fcriterios.getSubtipo() + " AND contador_file=" + fcriterios.getTipo() + " AND contador_equipo=" + fcriterios.getEquipo());
			jdbcMssqlTemplate.execute(query, new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
				protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
					ps.setString(1, fcriterios.getSubtipo());
					ps.setString(2, fcriterios.getTipo());
					ps.setString(3, fcriterios.getEquipo());
				}	
				});
			respUTDM.setMensaje("SUCCESS");
			respUTDM.setDescripcion("Se encontraron "+datosContador.size()+" datos");
	
		}catch (Exception excp) {
			log.error("ERROR: ", excp);
			respUTDM.setMensaje("FAULT");
			respUTDM.setDescripcion("ERROR: "+excp.getMessage());
			throw new ViviendaException(excp.getMessage());
		}
		respUTDM.setDatosContador(datosContador);
		return respUTDM;			
	}
	
	public ResponseUploadFilesDigitDto setContador(CriteriosDatosDigitalizacionImageDto criterios) throws ViviendaException
	{			
		DBConnection con = new DBConnection();
		JdbcTemplate jdbcMssqlTemplate = con.connectMSSQLDataBase();
		List<DigitContadorDto> datosContador = new ArrayList<DigitContadorDto>();
		ResponseUploadFilesDigitDto respUTDM = new ResponseUploadFilesDigitDto();
		LobHandler lobHandler = new DefaultLobHandler();
		final CriteriosDatosDigitalizacionImageDto fcriterios = criterios;
		String query="";	
		
		try
		{	
			query="INSERT INTO contadores(contador_equipo, contador_proc, contador_file, contador_consecutivo) VALUES(?, ?, ?, 1)";
			jdbcMssqlTemplate.execute(query, new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
			protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
				ps.setString(1, fcriterios.getEquipo());
				ps.setString(2, fcriterios.getTipo());
				ps.setString(3, fcriterios.getSubtipo());						
			}	
			});
			
			
			respUTDM.setMensaje("SUCCESS");
			respUTDM.setDescripcion("Contador actualizado");
	
		}catch (Exception excp) {
			log.error("ERROR: ", excp);
			respUTDM.setMensaje("FAULT");
			respUTDM.setDescripcion("ERROR: "+excp.getMessage());
			throw new ViviendaException(excp.getMessage());
		}
		respUTDM.setDatosContador(datosContador);
		return respUTDM;			
	}
	
	
	public ResponseUploadFilesDigitDto validaExisteDigit(CriteriosDatosDigitalizacionImageDto criterios) throws ViviendaException
	{			
		DBConnection con = new DBConnection();
		JdbcTemplate jdbcMssqlTemplate = con.connectMSSQLDataBase();
		List<DigitDatosImagenVo> imagesDigit = new ArrayList<DigitDatosImagenVo>();
		ResponseUploadFilesDigitDto respUTDM = new ResponseUploadFilesDigitDto();
		LobHandler lobHandler = new DefaultLobHandler();
		String query="";	
		
		try
		{			
			query="SELECT file_id, file_clave, file_nombre, file_datablob, file_unidad, file_tipo, file_proceso, file_tarchivo, file_estatus FROM archivo WHERE file_proceso='"+criterios.getSubtipo()+"' AND file_tarchivo='"+criterios.getSubtipoa()+"' AND file_unidad='"+criterios.getEquipo()+"'";
			log.info("query1" + query);
			//System.out.println(query);		
			imagesDigit=jdbcMssqlTemplate.query(query,new DigitImagenRowMaper());

			if(imagesDigit.size()>0)
			{
				respUTDM.setMensaje("SUCCESS");
				respUTDM.setDescripcion("Se encontraron "+imagesDigit.size()+" datos");
			}
			else
			{
				respUTDM.setMensaje("FAULT");
				respUTDM.setDescripcion("No existen imagenes vinculadas a esta Unidad Tecnica");
			}
						
		}catch (Exception excp) {
			log.error("ERROR: ", excp);
			respUTDM.setMensaje("FAULT");
			respUTDM.setDescripcion("ERROR: "+excp.getMessage());
			throw new ViviendaException(excp.getMessage());
		}
		respUTDM.setImagesDigit(imagesDigit);
		return respUTDM;			
	}

	
	
	public ResponseUploadFilesDigitDto setUpdateDigit(CriteriosDatosDigitalizacionImageDto criterios) throws ViviendaException
	{			
		DBConnection con = new DBConnection();
		JdbcTemplate jdbcMssqlTemplate = con.connectMSSQLDataBase();
		List<DigitImagenRowMaper> imagesDigit = new ArrayList<DigitImagenRowMaper>();
		ResponseUploadFilesDigitDto respUTDM = new ResponseUploadFilesDigitDto();
		LobHandler lobHandler = new DefaultLobHandler();
	
		final CriteriosDatosDigitalizacionImageDto fcriterios = criterios;
		
		try
		{			
			

			jdbcMssqlTemplate.execute("UPDATE archivo SET file_clave=?, file_fecha_alta=GETDATE(), file_datablob=?, file_unidad=?, file_tipo=?, file_proceso=?, file_tarchivo=?, file_estatus=? WHERE file_nombre='"+criterios.getNombreImagen()+"'", new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
				protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
					ps.setString(1, fcriterios.getFase());
					try {
						lobCreator.setBlobAsBinaryStream(ps, 2, fcriterios.getFileData().getInputStream(), (int)fcriterios.getFileData().getSize());
					} catch (IOException e) {
						log.error("ERROR: ", e);
					}
					ps.setString(3, fcriterios.getEquipo());
					ps.setString(4, fcriterios.getFase());
					ps.setString(5, fcriterios.getSubtipo());
					ps.setString(6, fcriterios.getSubtipoa());
					ps.setString(7, fcriterios.getEstatus());
				}
			});
		    
			
			respUTDM.setMensaje("SUCCESS");
			respUTDM.setDescripcion("Datos actualizados satisfactoriamente");
			
		}catch (Exception excp) {
			log.error("ERROR: ", excp);
			respUTDM.setMensaje("FAULT");
			respUTDM.setDescripcion("ERROR: "+excp.getMessage());
			throw new ViviendaException(excp.getMessage());
		}
		return respUTDM;			
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	
	public ResponseUploadFilesDigitDto setDigitFiles(CriteriosDatosDigitalizacionImageDto criterios) throws ViviendaException
	{			
		DBConnection con = new DBConnection();
		JdbcTemplate jdbcMssqlTemplate = con.connectMSSQLDataBase();
		List<DigitDatosImagenVo> imagesDigit = new ArrayList<DigitDatosImagenVo>();
		ResponseUploadFilesDigitDto respUTDM = new ResponseUploadFilesDigitDto();
		LobHandler lobHandler = new DefaultLobHandler();
		final CriteriosDatosDigitalizacionImageDto fcriterios = criterios;
		
		try {
			log.info("Archivo DAO:fcriterios.getFase()=" + fcriterios.getFase() + 
					 ", fcriterios.getFileData().getInputStream():" + fcriterios.getFileData().getInputStream() + 
					 ", (int)fcriterios.getFileData().getSize():" + (int)fcriterios.getFileData().getSize() + 
					 ", fcriterios.getEquipo():" + fcriterios.getEquipo() +
					 ", fcriterios.getNombreImagen():" + fcriterios.getNombreImagen() +
					 ", fcriterios.getSubtipo():" + fcriterios.getSubtipo() + 
					 ", fcriterios.getSubtipoa():" + fcriterios.getSubtipoa() +
					 ", fcriterios.getEstatus():" + fcriterios.getEstatus());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		try
		{
		    if(criterios.getAccion().equals("guardar"))	
		    {
			jdbcMssqlTemplate.execute("INSERT INTO archivo (file_clave, file_nombre, file_fecha_alta, file_datablob, file_unidad, file_tipo, file_proceso, file_tarchivo, file_estatus) VALUES (?,?,GETDATE(),?,?,?,?,?,?)", new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
				protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
					ps.setString(1, fcriterios.getFase());
					ps.setString(2, fcriterios.getNombreImagen());
					try {
						lobCreator.setBlobAsBinaryStream(ps, 3, fcriterios.getFileData().getInputStream(), (int)fcriterios.getFileData().getSize());
					} catch (IOException e) {
						log.error("ERROR: ", e);
					}
					ps.setString(4, fcriterios.getEquipo());
					ps.setString(5, fcriterios.getNombreImagen());
					ps.setString(6, fcriterios.getSubtipo());
					ps.setString(7, fcriterios.getSubtipoa());
					ps.setString(8, fcriterios.getEstatus());
				}
			});
		    }
				
			respUTDM.setMensaje("SUCCESS");
			respUTDM.setDescripcion("Datos insertados satisfactoriamente");	
			
		}catch (Exception excp) {
			log.error("ERROR: ", excp);
			respUTDM.setMensaje("FAULT");
			respUTDM.setDescripcion("ERROR: "+excp.getMessage());
			throw new ViviendaException(excp.getMessage());
		}
		respUTDM.setImagesDigit(imagesDigit);
		return respUTDM;			
	}
	
	
	public ResponseUploadFilesDigitDto setDeleteDigitFiles(CriteriosDatosDigitalizacionImageDto criterios) throws ViviendaException
	{			
		DBConnection con = new DBConnection();
		JdbcTemplate jdbcMssqlTemplate = con.connectMSSQLDataBase();
		List<DigitDatosImagenVo> imagesDigit = new ArrayList<DigitDatosImagenVo>();
		ResponseUploadFilesDigitDto respUTDM = new ResponseUploadFilesDigitDto();
		LobHandler lobHandler = new DefaultLobHandler();
		final CriteriosDatosDigitalizacionImageDto fcriterios = criterios;
		
		try
		{
		   
			jdbcMssqlTemplate.execute("DELETE FROM archivo  WHERE file_unidad=? and file_nombre=?", new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
				protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {			
					ps.setString(1, fcriterios.getEquipo());					
					ps.setString(2, fcriterios.getNombreImagen());					
				}
			});
		    
			
			
			respUTDM.setMensaje("SUCCESS");
			respUTDM.setDescripcion("Datos eliminados satisfactoriamente");			
			
		}catch (Exception excp) {
			log.error("ERROR: ", excp);
			respUTDM.setMensaje("FAULT");
			respUTDM.setDescripcion("ERROR: "+excp.getMessage());
			throw new ViviendaException(excp.getMessage());
		}
		respUTDM.setImagesDigit(imagesDigit);
		return respUTDM;			
	}
	
	public ByteArrayOutputStream getImagen(String nombre_file) throws ViviendaException
	{			
		DBConnection con = new DBConnection();
		JdbcTemplate jdbcMssqlTemplate = con.connectMSSQLDataBase();
		ByteArrayOutputStream blob = null;
		LobHandler lobHandler = new DefaultLobHandler();
		String query = "";
		
		try
		{			
			query="select file_datablob from archivo where file_nombre = '" + nombre_file + "'";
			log.info("query:" + query);
			
			List lista = jdbcMssqlTemplate.query(query, new RowMapper() {
				      public Object mapRow(ResultSet rs, int i) throws SQLException {
				      LobHandler lobHandler = new DefaultLobHandler();
				      byte[] blobBytes = lobHandler.getBlobAsBytes(rs, "file_datablob");
				      ByteArrayOutputStream baos = new ByteArrayOutputStream(blobBytes.length);
				      baos.write(blobBytes, 0, blobBytes.length);
				      return baos;
				      }
				    });
			
			
			//System.out.println(query);

			if(lista.size()>0)
			{
				blob = (ByteArrayOutputStream)lista.get(0);
				//respUTDM=updateContador(criterios);
				//respUTDM.setConsecutivo(datosContador.get(0));
				/*respUTDM.setMensaje("SUCCESS");
				respUTDM.setDescripcion("Se encontraron "+datosContador.size()+" datos");*/
			}
			else
			{		
				log.info("datos contador insert");
				//respUTDM=setContador(criterios);
				//respUTDM.setConsecutivo(1);
				/*respUTDM.setMensaje("FAULT");
				respUTDM.setDescripcion("No existen imagenes vinculadas a esta Unidad Tecnica");*/
				
			}
						
		}catch (Exception excp) {
			log.error("ERROR: ", excp);
			//respUTDM.setMensaje("FAULT");
			//respUTDM.setDescripcion("ERROR: "+excp.getMessage());
			throw new ViviendaException(excp.getMessage());			
		}
		//respUTDM.setDatosContador(datosContador);
		return blob;			
	}
	
	public ResponseUploadFilesDigitDto setFechaGarantia(CriteriosDatosDigitalizacionImageDto criterios) throws ViviendaException
	{			
		ResponseUploadFilesDigitDto respUTDM = new ResponseUploadFilesDigitDto();
		
		String sStatus;
		Connection connect = null;
		sStatus = ArchLogg.leeLogg();
		String subrc = "";
		String bapierror = "";
		
		if (sStatus.equals("OK")) 
		{
			try 
			{
				//if (connect == null) {
			    if (Connection.getConnect() == null) 
			    {
					//connect = new Connection(ArchLogg.getSapSystem());
			    	connect = new Connection(ArchLogg.getSapSystem());
				}
			    else
			    {
					connect = Connection.getConnect();
				}
				// Establece RFC a ejecutar en SAP
				JCoFunction function = connect.getFunction("ZCSMF_0154_SET_FEC_GARANTIA");
				
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP",   criterios.getIdUTS());
				function.getImportParameterList().setValue("I_USUARIO",     criterios.getId_usuario());
				function.getImportParameterList().setValue("I_ID_EQUNR",     criterios.getEquipo());
				log.info("I_ID_UT_SUP:" + criterios.getIdUTS());
				log.info("I_USUARIO:" + criterios.getId_usuario());
				log.info("I_ID_EQUNR:" + criterios.getEquipo());
				
				String fechaLow[] = criterios.getFechaInicio().split("/");
				function.getImportParameterList().setValue("I_RE_FIGA",     
						fechaLow[2] + String.format("%02d", Integer.parseInt(fechaLow[1])) +  String.format("%02d", Integer.parseInt(fechaLow[0])));
				log.info("I_RE_FIGA: " + 
						fechaLow[2] + String.format("%02d", Integer.parseInt(fechaLow[1])) + String.format("%02d", Integer.parseInt(fechaLow[0])));
			
				String fechaHi[] = criterios.getFechaFin().split("/");
				function.getImportParameterList().setValue("I_PRE_FFGA",    
						fechaHi[2] + String.format("%02d", Integer.parseInt(fechaHi[1])) + String.format("%02d", Integer.parseInt(fechaHi[0])));
				log.info("I_PRE_FFGA: " +  
						fechaHi[2] + String.format("%02d", Integer.parseInt(fechaHi[1])) + String.format("%02d", Integer.parseInt(fechaHi[0])));

				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				//enumTicket = (String) function.getExportParameterList().getString("E_NUM_TICKET");
				if (subrc.equals("00")) 
				{	
					//GENERAR RESPONSE
					respUTDM.setMensaje("SUCCESS");
					//responseTicketConstruccionDto.setDescripcion("Generado el número de ticket: " + enumTicket);
				}
				else
				{
					respUTDM.setMensaje("FAULT");	
					respUTDM.setDescripcion(bapierror);
				}
			} 
			catch (Exception excp) 
			{
				log.error("Error:",  excp);
				respUTDM.setMensaje("FAULT");
				respUTDM.setDescripcion(excp.getMessage());
				throw new ViviendaException ("ERROR:" + excp.getMessage());
			}
		}	
		
		
		return respUTDM;
	}
	
	
}
