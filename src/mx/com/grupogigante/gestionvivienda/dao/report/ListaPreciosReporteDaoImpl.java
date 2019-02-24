package mx.com.grupogigante.gestionvivienda.dao.report;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mx.com.grupogigante.gestionvivienda.report.disponibilidad.dto.DisponibilidadReporteRowDto;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.report.listaprecio.dto.ListaPrecioReporteRequestDto;
import mx.com.grupogigante.gestionvivienda.report.listaprecio.dto.ListaPrecioReporteResponseDto;
import mx.com.grupogigante.gestionvivienda.report.listaprecio.dto.ListaPrecioRowFieldDto;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.Connection;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

@Repository
public class ListaPreciosReporteDaoImpl implements ListaPreciosReporteDao {
	private Logger log = Logger.getLogger(ListaPreciosReporteDaoImpl.class);
	
	
	public List<ListaPrecioRowFieldDto> toListaPrecioRow (JCoTable  tablaListaPrecio) {
		List<ListaPrecioRowFieldDto> lista = new ArrayList<ListaPrecioRowFieldDto>();
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
		String formatoNumerico = "$0.00";

		if (tablaListaPrecio != null){
			if (tablaListaPrecio.getNumRows() > 0){
				tablaListaPrecio.firstRow();
				for (int i = 0; i < tablaListaPrecio.getNumRows(); i++){
					lista.add(new ListaPrecioRowFieldDto());
					lista.get(i).setEqunr(tablaListaPrecio.getString("EQUNRX"));
					lista.get(i).setFase(tablaListaPrecio.getString("FASEX"));
					lista.get(i).setM2pr(tablaListaPrecio.getString("M2PR"));
					lista.get(i).setM2ja(tablaListaPrecio.getString("M2JA"));
					lista.get(i).setM2te(tablaListaPrecio.getString("M2TE"));
					lista.get(i).setM2es(tablaListaPrecio.getString("M2ES"));
					lista.get(i).setM2bo(tablaListaPrecio.getString("M2BO"));
					lista.get(i).setSumaM2(tablaListaPrecio.getString("SUMA_M2"));
					lista.get(i).setTipo(tablaListaPrecio.getString("TIPO"));
					lista.get(i).setVisa(tablaListaPrecio.getString("VISA"));
					lista.get(i).setOrie(tablaListaPrecio.getString("ORIE"));
					lista.get(i).setNest(tablaListaPrecio.getString("NEST"));
					lista.get(i).setNbod(tablaListaPrecio.getString("NBOD"));
					
					formatoNumerico = "$0.00";
					String montoReporte = tablaListaPrecio.getString("PRECIO_LISTA");
					if (montoReporte != null && !montoReporte.trim().equals("")) {
						formatoNumerico = nf.format(new BigDecimal(montoReporte).doubleValue());
					}					
					lista.get(i).setPrecioLista(formatoNumerico);					
					formatoNumerico = "$0.00";
					montoReporte = tablaListaPrecio.getString("PACH");
					if (montoReporte != null && !montoReporte.trim().equals("")) {
						formatoNumerico = nf.format(new BigDecimal(montoReporte).doubleValue());
					}
					lista.get(i).setPach(formatoNumerico);
					formatoNumerico = "$0.00";
					montoReporte = tablaListaPrecio.getString("PACO");
					if (montoReporte != null && !montoReporte.trim().equals("")) {
						formatoNumerico = nf.format(new BigDecimal(montoReporte).doubleValue());
					}
					lista.get(i).setPaco(formatoNumerico);
					montoReporte = tablaListaPrecio.getString("PAFI");
					if (montoReporte != null && !montoReporte.trim().equals("")) {
						formatoNumerico = nf.format(new BigDecimal(montoReporte).doubleValue());
					}
					lista.get(i).setPafi(formatoNumerico);
					lista.get(i).setStun(tablaListaPrecio.getString("STUNX"));
					tablaListaPrecio.nextRow();
				}
			}
		}		
		return lista;
	}
	
	public ListaPrecioReporteResponseDto findListaPrecios(ListaPrecioReporteRequestDto reporteRequest) throws ReporteException {
		List<DisponibilidadReporteRowDto>  lista         = null;
		Connection                         sapConnection = null;
		String     sapStatus     = ArchLogg.leeLogg();
		String     responseCode  = "";
		String     bapiError     = "";
		ListaPrecioReporteResponseDto responseReporte = new ListaPrecioReporteResponseDto();
		
		log.info(reporteRequest.toString());
		
		if (sapStatus.equals("OK")) 	{
			try {
				sapConnection = (Connection.getConnect() == null? new Connection(ArchLogg.getSapSystem()):Connection.getConnect());
				// Establece RFC a ejecutar en SAP
				JCoFunction function = sapConnection.getFunction("ZCSMF_0083_LIST_PRICE");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", reporteRequest.getUnidadTecnicaSuperior());
				function.getImportParameterList().setValue("I_USUARIO",   reporteRequest.getIdUsuario());
				
				if (reporteRequest.getIdFase() != null && reporteRequest.getIdFase().trim().length() > 0 && !reporteRequest.getIdFase().trim().equals("null")) {
					function.getImportParameterList().setValue("I_FASE",     reporteRequest.getIdFase());
					log.info("PARAMETRO I_FASE:" + reporteRequest.getIdFase());
				}				
				if ((reporteRequest.getIdEquipoInicial() != null && !reporteRequest.getIdEquipoInicial().trim().equals("") && !reporteRequest.getIdEquipoInicial().trim().equals("null")) || 
					(reporteRequest.getIdEquipoFinal()   != null && !reporteRequest.getIdEquipoFinal().trim().equals("")   && !reporteRequest.getIdEquipoFinal().trim().equals("null"))) {					
					JCoTable itHeaderEquipoIn = function.getTableParameterList().getTable("IT_EQUNR_IN");
					itHeaderEquipoIn.appendRow();		
					if (reporteRequest.getIdEquipoInicial() != null && !reporteRequest.getIdEquipoInicial().trim().equals("")) {
						itHeaderEquipoIn.setValue("EQUNR_LOW", reporteRequest.getIdEquipoInicial());
						log.info("PARAMETRO EQUNR_LOW:" + reporteRequest.getIdEquipoInicial());
					} 
					if (reporteRequest.getIdEquipoFinal()   != null && !reporteRequest.getIdEquipoFinal().trim().equals("")) {
						itHeaderEquipoIn.setValue("EQUNR_HIGH", reporteRequest.getIdEquipoFinal());					
						log.info("PARAMETRO EQUNR_HIGH:" + reporteRequest.getIdEquipoFinal());
					}
				}
				if (reporteRequest.getIdTipo() != null && reporteRequest.getIdTipo().trim().length() > 0 && !reporteRequest.getIdTipo().trim().equals("null")) {
					function.getImportParameterList().setValue("I_TIPO",     reporteRequest.getIdTipo());
					log.info("PARAMETRO I_TIPO:" + reporteRequest.getIdTipo());
				}	
				
				sapConnection.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				responseCode = (String) function.getExportParameterList().getString("E_SUBRC");
				bapiError    = (String) function.getExportParameterList().getString("E_MSGBAPI");
				
				log.info("BAPI MESSAGE:" + responseCode);
				log.info("BAPI Error:" + bapiError);
				
				if (responseCode.equals("00")) 	{
					
					JCoTable  tablaListaprecio = function.getTableParameterList().getTable("IT_LIST_PRECI");					
					responseReporte.setListaPrecio(toListaPrecioRow(tablaListaprecio));
					responseReporte.setMensaje("SUCCESS");	
					responseReporte.setDescripcion("");
				} else {
					responseReporte.setMensaje("FAULT");	
					responseReporte.setDescripcion(bapiError);
				}
			} 
			catch (Exception excp) {
				excp.printStackTrace();
				responseReporte.setMensaje("FAULT");
				responseReporte.setDescripcion(excp.getMessage());
				throw new ReporteException(excp.getMessage(), 100);
			}
		}		

		return responseReporte;		
	}
	
}
