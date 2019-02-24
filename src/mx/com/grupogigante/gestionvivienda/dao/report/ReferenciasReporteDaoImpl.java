package mx.com.grupogigante.gestionvivienda.dao.report;

import java.util.ArrayList;
import java.util.List;

import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.report.referencias.dto.ReferenciasReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.referencias.dto.ReferenciasReporteResponse;
import mx.com.grupogigante.gestionvivienda.report.referencias.dto.ReferenciasReporteRowDto;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.Connection;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

@Repository
public class ReferenciasReporteDaoImpl implements ReferenciasReporteDao {
	private Logger log = Logger.getLogger(ReferenciasReporteDaoImpl.class);
	
	public List<ReferenciasReporteRowDto> toReferenciasRow (JCoTable  tablaReferencias) {
		List<ReferenciasReporteRowDto> lista = new ArrayList<ReferenciasReporteRowDto>();
		if (tablaReferencias != null){
			if (tablaReferencias.getNumRows() > 0){
				tablaReferencias.firstRow();
				for (int i = 0; i < tablaReferencias.getNumRows(); i++){
					lista.add(new ReferenciasReporteRowDto());
					lista.get(i).setMandt(tablaReferencias.getString("MANDT"));
					lista.get(i).setIdUtSup(tablaReferencias.getString("ID_UT_SUP"));
					lista.get(i).setIdFase(tablaReferencias.getString("ID_FASEX"));
					lista.get(i).setIdEqunr(tablaReferencias.getString("ID_EQUNRX"));
					lista.get(i).setIdBanco(tablaReferencias.getString("BANKA"));//ID_BANCO
					lista.get(i).setRefPago(tablaReferencias.getString("REF_PAGO"));
					lista.get(i).setRefPagonodig(tablaReferencias.getString("REF_PAGO_NO_DIG"));
					lista.get(i).setRefPagonodig(tablaReferencias.getString("BANKA"));
					lista.get(i).setCta(tablaReferencias.getString("CTA"));
					lista.get(i).setClabe(tablaReferencias.getString("CLABE"));
					tablaReferencias.nextRow();
				}
			}
		}		
		return lista;
	}

	public ReferenciasReporteResponse findReferencias(ReferenciasReporteRequest requestReferencias) throws ReporteException {
		List<ReferenciasReporteRowDto>  lista            = null;
		Connection                         sapConnection = null;
		String     sapStatus     = ArchLogg.leeLogg();
		String     responseCode  = "";
		String     bapiError     = "";
		ReferenciasReporteResponse responseReferencias = new ReferenciasReporteResponse();
		
		log.info(requestReferencias.toString());
		
		if (sapStatus.equals("OK")) 	{
			try {
				sapConnection = (Connection.getConnect() == null? new Connection(ArchLogg.getSapSystem()):Connection.getConnect());
				// Establece RFC a ejecutar en SAP
				JCoFunction function = sapConnection.getFunction("ZCSMF_0082_REFER_PAGO");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", requestReferencias.getUnidadTecnicaSuperior());
				function.getImportParameterList().setValue("I_USUARIO",   requestReferencias.getIdUsuario());

				
				if ((requestReferencias.getFaseLow() != null && !requestReferencias.getFaseLow().trim().equals("") && !requestReferencias.getFaseLow().trim().equals("null"))) {
						JCoTable itHeaderFaseIn = function.getTableParameterList().getTable("IT_FASE_IN");
						itHeaderFaseIn.appendRow();		
						if (requestReferencias.getFaseLow() != null && requestReferencias.getFaseLow().trim().length() > 0 && !requestReferencias.getFaseLow().trim().equals("null")) {
							itHeaderFaseIn.setValue("FASE_LOW", requestReferencias.getFaseLow());
							log.info("PARAMETRO FASE_LOW:" + requestReferencias.getFaseLow());
						}
					}

//				if ((requestReferencias.getFaseLow() != null && !requestReferencias.getFaseLow().trim().equals("") && !requestReferencias.getFaseLow().trim().equals("null")) || 
//					(requestReferencias.getFaseHigh()   != null && !requestReferencias.getFaseHigh().trim().equals("")   && !requestReferencias.getFaseHigh().trim().equals("null"))) {
//					JCoTable itHeaderFaseIn = function.getTableParameterList().getTable("IT_FASE_IN");
//					itHeaderFaseIn.appendRow();		
//					if (requestReferencias.getFaseLow() != null && requestReferencias.getFaseLow().trim().length() > 0 && !requestReferencias.getFaseLow().trim().equals("null")) {
//						itHeaderFaseIn.setValue("FASE_LOW", requestReferencias.getFaseLow());
//						log.info("PARAMETRO FASE_LOW:" + requestReferencias.getFaseLow());
//					}
//					if (requestReferencias.getFaseHigh() != null && requestReferencias.getFaseHigh().trim().length() > 0 && !requestReferencias.getFaseHigh().trim().equals("null")) {
//						itHeaderFaseIn.setValue("FASE_HIGH", requestReferencias.getFaseHigh());
//						log.info("PARAMETRO FASE_HIGH:" + requestReferencias.getFaseHigh());
//					}	
//				}
				
				//if (requestReferencias.getBancoHigh()   != null && !requestReferencias.getBancoHigh().trim().equals("")   && !requestReferencias.getBancoHigh().trim().equals("null")) {
					if (requestReferencias.getBancoLow() != null && requestReferencias.getBancoLow().trim().length() > 0 && !requestReferencias.getBancoLow().trim().equals("null")) {
						JCoTable itHeaderBancoIn = function.getTableParameterList().getTable("IT_BANCO_IN");
						itHeaderBancoIn.appendRow();		
						itHeaderBancoIn.setValue("BANCO_LOW", requestReferencias.getBancoLow());
						log.info("PARAMETRO BANCO_LOW:" + requestReferencias.getBancoLow());
					}
				//}

//				if ((requestReferencias.getBancoLow() != null && !requestReferencias.getBancoLow().trim().equals("") && !requestReferencias.getBancoLow().trim().equals("null")) || 
//						(requestReferencias.getBancoHigh()   != null && !requestReferencias.getBancoHigh().trim().equals("")   && !requestReferencias.getBancoHigh().trim().equals("null"))) {
//						JCoTable itHeaderBancoIn = function.getTableParameterList().getTable("IT_BANCO_IN");
//						itHeaderBancoIn.appendRow();		
//						if (requestReferencias.getBancoLow() != null && requestReferencias.getBancoLow().trim().length() > 0 && !requestReferencias.getBancoLow().trim().equals("null")) {
//							itHeaderBancoIn.setValue("BANCO_LOW", requestReferencias.getBancoLow());
//							log.info("PARAMETRO BANCO_LOW:" + requestReferencias.getBancoLow());
//						}
//						if (requestReferencias.getBancoHigh() != null && requestReferencias.getBancoHigh().trim().length() > 0 && !requestReferencias.getBancoHigh().trim().equals("null")) {
//							itHeaderBancoIn.setValue("BANCO_HIGH", requestReferencias.getBancoHigh());
//							log.info("PARAMETRO BANCO_HIGH:" + requestReferencias.getBancoHigh());
//						}	
//					}
				
				if ((requestReferencias.getIdEquipoInicial() != null && !requestReferencias.getIdEquipoInicial().trim().equals("") && !requestReferencias.getIdEquipoInicial().trim().equals("null")) || 
					(requestReferencias.getIdEquipoFinal()   != null && !requestReferencias.getIdEquipoFinal().trim().equals("")   && !requestReferencias.getIdEquipoFinal().trim().equals("null"))) {					
					JCoTable itHeaderEquipoIn = function.getTableParameterList().getTable("IT_EQUNR_IN");
					itHeaderEquipoIn.appendRow();		
					if (requestReferencias.getIdEquipoInicial() != null && !requestReferencias.getIdEquipoInicial().trim().equals("")) {
						itHeaderEquipoIn.setValue("EQUNR_LOW", requestReferencias.getIdEquipoInicial());
						log.info("PARAMETRO EQUNR_LOW:" + requestReferencias.getIdEquipoInicial());
					} 
					if (requestReferencias.getIdEquipoFinal()   != null && !requestReferencias.getIdEquipoFinal().trim().equals("")) {
						itHeaderEquipoIn.setValue("EQUNR_HIGH", requestReferencias.getIdEquipoFinal());					
						log.info("PARAMETRO EQUNR_HIGH:" + requestReferencias.getIdEquipoFinal());
					}
				}
				
				sapConnection.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				responseCode = (String) function.getExportParameterList().getString("E_SUBRC");
				bapiError    = (String) function.getExportParameterList().getString("E_MSGBAPI");
				
				log.info("BAPI MESSAGE:" + responseCode);
				log.info("BAPI Error:" + bapiError);
				
				if (responseCode.equals("00")) 	{
					
					JCoTable  tablaEquipos = function.getTableParameterList().getTable("IT_REFERENCIAS_OUT");					
					responseReferencias.setListaReferencias(toReferenciasRow(tablaEquipos));
					responseReferencias.setMensaje("SUCCESS");	
					responseReferencias.setDescripcion("");
				} else {
					responseReferencias.setMensaje("FAULT");	
					responseReferencias.setDescripcion(bapiError);
				}
			} 
			catch (Exception excp) {
				log.error("ERROR", excp);
				responseReferencias.setMensaje("FAULT");
				responseReferencias.setDescripcion(excp.getMessage());
				throw new ReporteException(excp.getMessage(), 100);
			}
		}		

		return responseReferencias;		
	}
}
