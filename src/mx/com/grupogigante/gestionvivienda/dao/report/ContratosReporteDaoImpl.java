package mx.com.grupogigante.gestionvivienda.dao.report;

import java.util.ArrayList;
import java.util.List;

import mx.com.grupogigante.gestionvivienda.domain.dto.EstatusFiltroReporteDto;
import mx.com.grupogigante.gestionvivienda.report.contratos.dto.ContratosReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.contratos.dto.ContratosReporteResponse;
import mx.com.grupogigante.gestionvivienda.report.contratos.dto.ContratosReporteRowDto;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.Connection;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

@Repository
public class ContratosReporteDaoImpl implements ContratosReporteDao {
	private Logger log = Logger.getLogger(ContratosReporteDaoImpl.class);
	
	public List<ContratosReporteRowDto> toContratosRow (JCoTable  tablaEquipos) {
		List<ContratosReporteRowDto> lista = new ArrayList<ContratosReporteRowDto>();
		if (tablaEquipos != null){
			if (tablaEquipos.getNumRows() > 0){
				tablaEquipos.firstRow();
				for (int i = 0; i < tablaEquipos.getNumRows(); i++){
					lista.add(new ContratosReporteRowDto());
					lista.get(i).setFase(tablaEquipos.getString("FASEX"));
					lista.get(i).setEquipo(tablaEquipos.getString("EQUNRX"));
					lista.get(i).setEstatus(tablaEquipos.getString("ST_CONTRA"));
					lista.get(i).setDescripcion(tablaEquipos.getString("ST_CONTRAX"));
					lista.get(i).setCliente(tablaEquipos.getString("KUNNR"));
					lista.get(i).setNombre(tablaEquipos.getString("NAME"));
					lista.get(i).setAppaterno(tablaEquipos.getString("APP_PAT"));
					lista.get(i).setMsj("");
					tablaEquipos.nextRow();
				}
			}
		}		
		return lista;
	}

	public List<EstatusFiltroReporteDto> toEstatusRow (JCoTable  tablaEquipos) {
		List<EstatusFiltroReporteDto> lista = new ArrayList<EstatusFiltroReporteDto>();
		if (tablaEquipos != null){
			if (tablaEquipos.getNumRows() > 0){
				tablaEquipos.firstRow();
				for (int i = 0; i < tablaEquipos.getNumRows(); i++){
					lista.add(new EstatusFiltroReporteDto());
					lista.get(i).setStun(tablaEquipos.getString("VIA_CON"));
					lista.get(i).setStunx(tablaEquipos.getString("VIA_CONX"));
					tablaEquipos.nextRow();
				}
			}
			
			EstatusFiltroReporteDto estatus = new EstatusFiltroReporteDto();
			estatus.setStun("");
			estatus.setStunx("");
			lista.add(0, estatus);
		}		
		return lista;
	}

	public ContratosReporteResponse findEstatusAll (ContratosReporteRequest requestContratos) throws ReporteException {
		List<EstatusFiltroReporteDto>  lista         = null;
		Connection                         sapConnection = null;
		String     sapStatus     = ArchLogg.leeLogg();
		String     responseCode  = "";
		String     bapiError     = "";
		ContratosReporteResponse responseContratos = new ContratosReporteResponse();
		
		log.info(requestContratos.toString());
		
		if (sapStatus.equals("OK")) 	{
			try {
				sapConnection = (Connection.getConnect() == null? new Connection(ArchLogg.getSapSystem()):Connection.getConnect());
				// Establece RFC a ejecutar en SAP
				JCoFunction function = sapConnection.getFunction("ZCSMF_0086_STAT_CONTRACT");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", requestContratos.getUnidadTecnicaSuperior());
				function.getImportParameterList().setValue("I_USUARIO",   requestContratos.getIdUsuario());
								
				sapConnection.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				responseCode = (String) function.getExportParameterList().getString("E_SUBRC");
				bapiError    = (String) function.getExportParameterList().getString("E_MSGBAPI");
				
				log.info("BAPI MESSAGE:" + responseCode);
				log.info("BAPI Error:" + bapiError);
				
				if (responseCode.equals("00")) 	{
					
					JCoTable  tablaEquipos = function.getTableParameterList().getTable("IT_CAT_STS_CONT");					
					responseContratos.setListaEstatus(toEstatusRow(tablaEquipos));
					log.info(responseContratos.getListaEstatus());
					responseContratos.setMensaje("SUCCESS");	
					responseContratos.setDescripcion("");
				} else {
					responseContratos.setMensaje("FAULT");	
					responseContratos.setDescripcion(bapiError);
				}
			} 
			catch (Exception excp) {
				log.error("ERROR:", excp);
				responseContratos.setMensaje("FAULT");
				responseContratos.setDescripcion(excp.getMessage());
				throw new ReporteException(excp.getMessage(), 100);
			}
		}		

		return responseContratos;		
	}
	public ContratosReporteResponse findContratos(ContratosReporteRequest requestContratos) throws ReporteException {
		List<ContratosReporteRowDto>  lista         = null;
		Connection                         sapConnection = null;
		String     sapStatus     = ArchLogg.leeLogg();
		String     responseCode  = "";
		String     bapiError     = "";
		ContratosReporteResponse responseContratos = new ContratosReporteResponse();
		
		log.info(requestContratos.toString());
		
		if (sapStatus.equals("OK")) 	{
			try {
				sapConnection = (Connection.getConnect() == null? new Connection(ArchLogg.getSapSystem()):Connection.getConnect());
				// Establece RFC a ejecutar en SAP
				JCoFunction function = sapConnection.getFunction("ZCSMF_0085_CONTRATO");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", requestContratos.getUnidadTecnicaSuperior());
				function.getImportParameterList().setValue("I_USUARIO",   requestContratos.getIdUsuario());
				
				if (requestContratos.getIdFase() != null && requestContratos.getIdFase().trim().length() > 0 && !requestContratos.getIdFase().trim().equals("null")) {
					function.getImportParameterList().setValue("I_FASE",     requestContratos.getIdFase());
					log.info("PARAMETRO I_FASE:" + requestContratos.getIdFase());
				}
				if (requestContratos.getIdEstatus() != null && requestContratos.getIdEstatus().trim().length() > 0 && !requestContratos.getIdEstatus().trim().equals("null")) {
					function.getImportParameterList().setValue("I_ST_CONTRA",     requestContratos.getIdEstatus());
					log.info("PARAMETRO I_STUN:" + requestContratos.getIdEstatus());
				}	
				
				if (requestContratos.getIdEquipoInicial() != null && !requestContratos.getIdEquipoInicial().trim().equals("") && !requestContratos.getIdEquipoInicial().trim().equals("null")) { 
					function.getImportParameterList().setValue("I_EQUNR_LOW", requestContratos.getIdEquipoInicial());
					log.info("PARAMETRO EQUNR_LOW:" + requestContratos.getIdEquipoInicial());
					//JCoTable itHeaderEquipoIn = function.getTableParameterList().getTable("IT_EQUNR_IN");
					//itHeaderEquipoIn.appendRow();		
				} 
				if (requestContratos.getIdEquipoFinal()   != null && !requestContratos.getIdEquipoFinal().trim().equals("")   && !requestContratos.getIdEquipoFinal().trim().equals("null")) {					
					function.getImportParameterList().setValue("I_EQUNR_HIGH", requestContratos.getIdEquipoFinal());					
					log.info("PARAMETRO EQUNR_HIGH:" + requestContratos.getIdEquipoFinal());
				}
				
				sapConnection.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				responseCode = (String) function.getExportParameterList().getString("E_SUBRC");
				bapiError    = (String) function.getExportParameterList().getString("E_MSGBAPI");
				
				log.info("BAPI MESSAGE:" + responseCode);
				log.info("BAPI Error:" + bapiError);
				
				if (responseCode.equals("00")) 	{
					
					JCoTable  tablaEquipos = function.getTableParameterList().getTable("IT_CONTRATOS_OUT");					
					responseContratos.setListaContrato(toContratosRow(tablaEquipos));
					responseContratos.setMensaje("SUCCESS");	
					responseContratos.setDescripcion("");
				} else {
					responseContratos.setMensaje("FAULT");	
					responseContratos.setDescripcion(bapiError);
				}
			} 
			catch (Exception excp) {
				log.error("ERROR:", excp);
				responseContratos.setMensaje("FAULT");
				responseContratos.setDescripcion(excp.getMessage());
				throw new ReporteException(excp.getMessage(), 100);
			}
		}		

		return responseContratos;		
	}
}
