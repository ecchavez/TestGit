package mx.com.grupogigante.gestionvivienda.dao.report;

import java.util.ArrayList;
import java.util.List;

import mx.com.grupogigante.gestionvivienda.report.disponibilidad.dto.DisponibilidadReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.disponibilidad.dto.DisponibilidadReporteResponse;
import mx.com.grupogigante.gestionvivienda.report.disponibilidad.dto.DisponibilidadReporteRowDto;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.Connection;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

@Repository
public class DisponibilidadReporteDaoImpl implements DisponibilidadReporteDao {
	private Logger log = Logger.getLogger(DisponibilidadReporteDaoImpl.class);

	public List<DisponibilidadReporteRowDto> toDisponibilidadRow (JCoTable  tablaEquipos) {
		List<DisponibilidadReporteRowDto> lista = new ArrayList<DisponibilidadReporteRowDto>();
		if (tablaEquipos != null){
			if (tablaEquipos.getNumRows() > 0){
				tablaEquipos.firstRow();
				for (int i = 0; i < tablaEquipos.getNumRows(); i++){
					lista.add(new DisponibilidadReporteRowDto());
					lista.get(i).setApPaterno(tablaEquipos.getString("APP_PAT"));
					lista.get(i).setCasaDepto(tablaEquipos.getString("EQUNRX"));
					lista.get(i).setDescripcionEstatus(tablaEquipos.getString("STUNTX"));
					lista.get(i).setDescripcionFase(tablaEquipos.getString("FASEX"));
					lista.get(i).setDescripcionTipo(tablaEquipos.getString("TIPO"));
					lista.get(i).setIdCliente(tablaEquipos.getString("KUNNR"));
					lista.get(i).setIdEstatus(tablaEquipos.getString("STUN"));
					lista.get(i).setIdFase(tablaEquipos.getString("FASE"));
					lista.get(i).setIdTipo(tablaEquipos.getString("TIPO"));
					lista.get(i).setM2Privativos(tablaEquipos.getString("M2PR"));
					lista.get(i).setNombreCliente(tablaEquipos.getString("KUNNRTX"));
					tablaEquipos.nextRow();
				}
			}
		}		
		return lista;
	}

	public DisponibilidadReporteResponse findDisponibilidad(DisponibilidadReporteRequest requestDisponibilidad) throws ReporteException {
		List<DisponibilidadReporteRowDto>  lista         = null;
		Connection                         sapConnection = null;
		String     sapStatus     = ArchLogg.leeLogg();
		String     responseCode  = "";
		String     bapiError     = "";
		DisponibilidadReporteResponse responseDisponibilidad = new DisponibilidadReporteResponse();
		
		log.info(requestDisponibilidad.toString());
		
		if (sapStatus.equals("OK")) 	{
			try {
				sapConnection = (Connection.getConnect() == null? new Connection(ArchLogg.getSapSystem()):Connection.getConnect());
				// Establece RFC a ejecutar en SAP
				JCoFunction function = sapConnection.getFunction("ZCSMF_0081_EQUNR_AVAILABLE");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", requestDisponibilidad.getUnidadTecnicaSuperior());
				function.getImportParameterList().setValue("I_USUARIO",   requestDisponibilidad.getIdUsuario());
				
				if (requestDisponibilidad.getIdFase() != null && requestDisponibilidad.getIdFase().trim().length() > 0 && !requestDisponibilidad.getIdFase().trim().equals("null")) {
					function.getImportParameterList().setValue("I_FASE",     requestDisponibilidad.getIdFase());
					log.info("PARAMETRO I_FASE:" + requestDisponibilidad.getIdFase());
				}
				if (requestDisponibilidad.getIdEstatus() != null && requestDisponibilidad.getIdEstatus().trim().length() > 0 && !requestDisponibilidad.getIdEstatus().trim().equals("null")) {
					//function.getImportParameterList().setValue("I_STUN",     requestDisponibilidad.getIdEstatus());
					log.info("PARAMETRO I_STUN:" + requestDisponibilidad.getIdEstatus());
				}	
				
				if ((requestDisponibilidad.getIdEquipoInicial() != null && !requestDisponibilidad.getIdEquipoInicial().trim().equals("") && !requestDisponibilidad.getIdEquipoInicial().trim().equals("null")) || 
					(requestDisponibilidad.getIdEquipoFinal()   != null && !requestDisponibilidad.getIdEquipoFinal().trim().equals("")   && !requestDisponibilidad.getIdEquipoFinal().trim().equals("null"))) {					
					JCoTable itHeaderEquipoIn = function.getTableParameterList().getTable("IT_EQUNR_IN");
					itHeaderEquipoIn.appendRow();		
					if (requestDisponibilidad.getIdEquipoInicial() != null && !requestDisponibilidad.getIdEquipoInicial().trim().equals("")) {
						itHeaderEquipoIn.setValue("EQUNR_LOW", requestDisponibilidad.getIdEquipoInicial());
						log.info("PARAMETRO EQUNR_LOW:" + requestDisponibilidad.getIdEquipoInicial());
					} 
					if (requestDisponibilidad.getIdEquipoFinal()   != null && !requestDisponibilidad.getIdEquipoFinal().trim().equals("")) {
						itHeaderEquipoIn.setValue("EQUNR_HIGH", requestDisponibilidad.getIdEquipoFinal());					
						log.info("PARAMETRO EQUNR_HIGH:" + requestDisponibilidad.getIdEquipoFinal());
					}
				}
				
				sapConnection.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				responseCode = (String) function.getExportParameterList().getString("E_SUBRC");
				bapiError    = (String) function.getExportParameterList().getString("E_MSGBAPI");
				
				log.info("BAPI MESSAGE:" + responseCode);
				log.info("BAPI Error:" + bapiError);
				
				if (responseCode.equals("00")) 	{
					
					JCoTable  tablaEquipos = function.getTableParameterList().getTable("IT_EQUIPOS_OUT");					
					responseDisponibilidad.setListaDisponibilidad(toDisponibilidadRow(tablaEquipos));
					responseDisponibilidad.setMensaje("SUCCESS");	
					responseDisponibilidad.setDescripcion("");
				} else {
					responseDisponibilidad.setMensaje("FAULT");	
					responseDisponibilidad.setDescripcion(bapiError);
				}
			} 
			catch (Exception excp) {
				responseDisponibilidad.setMensaje("FAULT");
				responseDisponibilidad.setDescripcion(excp.getMessage());
				throw new ReporteException(excp.getMessage(), 100);
			}
		}		

		return responseDisponibilidad;		
	}
}
