package mx.com.grupogigante.gestionvivienda.dao.report;

import java.util.ArrayList;
import java.util.List;

import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosUbicacionesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetUtInfSupDto;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.report.referencias.dto.ReferenciasBancoDto;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.Connection;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

@Repository
public class BancoDaoImpl implements BancoDao {
	private Logger log = Logger.getLogger(BancoDaoImpl.class);

	public List<ReferenciasBancoDto> toReferenciasRow (JCoTable  tablaReferencias) {
		List<ReferenciasBancoDto> lista = new ArrayList<ReferenciasBancoDto>();
		if (tablaReferencias != null){
			if (tablaReferencias.getNumRows() > 0){
				tablaReferencias.firstRow();
				for (int i = 0; i < tablaReferencias.getNumRows(); i++){
					lista.add(new ReferenciasBancoDto());
					lista.get(i).setBanka(tablaReferencias.getString("BANKA"));
					lista.get(i).setBankl(tablaReferencias.getString("BANKL"));
					tablaReferencias.nextRow();
				}
			}
		}		
		return lista;
	}

	public ResponseGetUtInfSupDto findBancos(CriteriosUbicacionesDto requestUbicaciones) throws ReporteException {
		List<ReferenciasBancoDto> lista = new ArrayList<ReferenciasBancoDto>();
		Connection                         sapConnection = null;
		String     sapStatus     = ArchLogg.leeLogg();
		String     responseCode  = "";
		String     bapiError     = "";
		ResponseGetUtInfSupDto responseReferencias = new ResponseGetUtInfSupDto();
		
		log.info(requestUbicaciones.toString());
		
		if (sapStatus.equals("OK")) 	{
			try {
				sapConnection = (Connection.getConnect() == null? new Connection(ArchLogg.getSapSystem()):Connection.getConnect());
				// Establece RFC a ejecutar en SAP
				JCoFunction function = sapConnection.getFunction("ZCSMF_0087_BANCOS");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", requestUbicaciones.getI_id_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO",   requestUbicaciones.getI_usuario());
				
				sapConnection.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				responseCode = (String) function.getExportParameterList().getString("E_SUBRC");
				bapiError    = (String) function.getExportParameterList().getString("E_MSGBAPI");
				
				log.info("BAPI MESSAGE:" + responseCode);
				log.info("BAPI Error:" + bapiError);
				
				if (responseCode.equals("00")) 	{
					
					JCoTable  tablaEquipos = function.getTableParameterList().getTable("IT_BANCO_OUT");					
					responseReferencias.setBancos(toReferenciasRow(tablaEquipos));
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
