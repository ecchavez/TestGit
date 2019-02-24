package mx.com.grupogigante.gestionvivienda.dao.report;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.report.ventas.dto.VentasReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.ventas.dto.VentasReporteResponse;
import mx.com.grupogigante.gestionvivienda.report.ventas.dto.VentasReporteRowDto;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.Connection;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

@Repository
public class VentasReporteDaoImpl implements VentasReporteDao {
	private Logger log = Logger.getLogger(VentasReporteDaoImpl.class);

	public List<VentasReporteRowDto> toVentasRow (JCoTable  tablaVentas) {
		List<VentasReporteRowDto> lista = new ArrayList<VentasReporteRowDto>();
		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
		//NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
		//String formatoNumerico = "$0.00";
		double formatoNumericoDouble = 0.00;

		if (tablaVentas != null){
			if (tablaVentas.getNumRows() > 0){
				tablaVentas.firstRow();
				for (int i = 0; i < tablaVentas.getNumRows(); i++){
					lista.add(new VentasReporteRowDto());
					lista.get(i).setEqunr(tablaVentas.getString("EQUNRX"));
					lista.get(i).setStun(tablaVentas.getString("STUN"));
					lista.get(i).setStunx(tablaVentas.getString("STUNTX"));
					String fecha = tablaVentas.getString("FECHA_STUN");
					sdt.applyPattern("yyyy-MM-dd");
					Date fechaReal = null;
					
					if (fecha != null && !fecha.trim().equals("") && !fecha.trim().equals("0000-00-00")) {
						try {
							fechaReal = sdt.parse(fecha);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					sdt.applyPattern("yyyy/MM/dd");
					lista.get(i).setFechaStun(fechaReal == null? "":sdt.format(fechaReal));
					
					formatoNumericoDouble = 0.00;
					String montoReporte = tablaVentas.getString("M2PR");
					if (montoReporte != null && !montoReporte.trim().equals("")) {
						formatoNumericoDouble = new BigDecimal(montoReporte).doubleValue();
					}
					lista.get(i).setM2pr(formatoNumericoDouble);
					//lista.get(i).setM2pr(tablaVentas.getString("M2PR"));
					formatoNumericoDouble = 0;
					montoReporte = tablaVentas.getString("NEST");
					if (montoReporte != null && !montoReporte.trim().equals("")) {
						formatoNumericoDouble = new BigDecimal(montoReporte).doubleValue();
					}
					lista.get(i).setNest(formatoNumericoDouble);
					formatoNumericoDouble = 0;
					montoReporte = tablaVentas.getString("NBOD");
					if (montoReporte != null && !montoReporte.trim().equals("")) {
						formatoNumericoDouble = new BigDecimal(montoReporte).doubleValue();
					}
					lista.get(i).setNbod(formatoNumericoDouble);
					//lista.get(i).setNbod(tablaVentas.getString("NBOD"));
					formatoNumericoDouble = 0.00;
				    montoReporte = tablaVentas.getString("PRECIO_LISTA");
					if (montoReporte != null && !montoReporte.trim().equals("")) {
						formatoNumericoDouble = new BigDecimal(montoReporte).doubleValue();
					}					
					lista.get(i).setPrecioLista(formatoNumericoDouble);
					formatoNumericoDouble = 0;
				    montoReporte = tablaVentas.getString("NEST_ADIC");
					if (montoReporte != null && !montoReporte.trim().equals("")) {
						formatoNumericoDouble = new BigDecimal(montoReporte).doubleValue();
					}
					lista.get(i).setNestAdic(formatoNumericoDouble);
					//lista.get(i).setNestAdic(tablaVentas.getString("NEST_ADIC"));
					formatoNumericoDouble = 0.00;
					montoReporte = tablaVentas.getString("PRICE_EST_ADIC");
					if (montoReporte != null && !montoReporte.trim().equals("")) {
						formatoNumericoDouble = new BigDecimal(montoReporte).doubleValue();
					}
					lista.get(i).setPreciEstAdic(formatoNumericoDouble);
					formatoNumericoDouble = 0;
				    montoReporte = tablaVentas.getString("NEST_ADIC");
					if (montoReporte != null && !montoReporte.trim().equals("")) {
						formatoNumericoDouble = new BigDecimal(montoReporte).doubleValue();
					}
					lista.get(i).setNbodAdic(formatoNumericoDouble);
					//lista.get(i).setNbodAdic(tablaVentas.getString("NBOD_ADIC"));
					formatoNumericoDouble = 0.00;
					montoReporte = tablaVentas.getString("PRICE_BOD_ADIC");
					if (montoReporte != null && !montoReporte.trim().equals("")) {
						formatoNumericoDouble = new BigDecimal(montoReporte).doubleValue();
					}
					lista.get(i).setPriceBodAdic(formatoNumericoDouble);
					formatoNumericoDouble = 0.00;
					montoReporte = tablaVentas.getString("IMPORTE");
					if (montoReporte != null && !montoReporte.trim().equals("")) {
						formatoNumericoDouble = new BigDecimal(montoReporte).doubleValue();
					}					
					lista.get(i).setImporte(formatoNumericoDouble);
					formatoNumericoDouble = 0.00;
					montoReporte = tablaVentas.getString("DESCP");
					if (montoReporte != null && !montoReporte.trim().equals("")) {
						formatoNumericoDouble = new BigDecimal(montoReporte).doubleValue();
					}
					lista.get(i).setDescp(formatoNumericoDouble);
					//lista.get(i).setDescp(tablaVentas.getString("DESCP"));
					formatoNumericoDouble = 0.00;
					montoReporte = tablaVentas.getString("DESCM");
					if (montoReporte != null && !montoReporte.trim().equals("")) {
						formatoNumericoDouble = new BigDecimal(montoReporte).doubleValue();
					}					
					lista.get(i).setDescm(formatoNumericoDouble);
					formatoNumericoDouble = 0.00;
					montoReporte = tablaVentas.getString("PRICE_VENTA");
					if (montoReporte != null && !montoReporte.trim().equals("")) {
						formatoNumericoDouble = new BigDecimal(montoReporte).doubleValue();
					}					
					lista.get(i).setPriceVenta(formatoNumericoDouble);
					formatoNumericoDouble = 0.00;
					montoReporte = tablaVentas.getString("PRICE_M2");
					if (montoReporte != null && !montoReporte.trim().equals("")) {
						formatoNumericoDouble = new BigDecimal(montoReporte).doubleValue();
					}					
					lista.get(i).setPriceM2(formatoNumericoDouble);
					lista.get(i).setKunnrx(tablaVentas.getString("KUNNRX"));
					lista.get(i).setAsesor(tablaVentas.getString("ASESOR"));
					lista.get(i).setViaCon(tablaVentas.getString("VIA_CON"));
					//log.info("VIA_CON:" + tablaVentas.getString("VIA_CON"));
					fecha = tablaVentas.getString("FFCO");
					sdt.applyPattern("yyyy-MM-dd");
					fechaReal = null;
					if (fecha != null && !fecha.trim().equals("") && !fecha.trim().equals("0000-00-00")) {
						try {
							fechaReal = sdt.parse(fecha);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					sdt.applyPattern("yyyy/MM/dd");
					lista.get(i).setFfco(fechaReal == null? "":sdt.format(fechaReal));
					tablaVentas.nextRow();
				}
			}
		}		
		return lista;
	}

	public VentasReporteResponse findVentas(VentasReporteRequest requestVentas) throws ReporteException {
		List<VentasReporteRowDto>  lista         = null;
		Connection                         sapConnection = null;
		String     sapStatus     = ArchLogg.leeLogg();
		String     responseCode  = "";
		String     bapiError     = "";
		VentasReporteResponse responseVentas = new VentasReporteResponse();
		
		log.info(requestVentas.toString());
		
		if (sapStatus.equals("OK")) 	{
			try {
				sapConnection = (Connection.getConnect() == null? new Connection(ArchLogg.getSapSystem()):Connection.getConnect());
				// Establece RFC a ejecutar en SAP
				JCoFunction function = sapConnection.getFunction("ZCSMF_0084_SALES2");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", requestVentas.getUnidadTecnicaSuperior());
				function.getImportParameterList().setValue("I_USUARIO",   requestVentas.getIdUsuario());
				
				if (requestVentas.getIdFase() != null && requestVentas.getIdFase().trim().length() > 0 && !requestVentas.getIdFase().trim().equals("null")) {
					function.getImportParameterList().setValue("I_FASE",     requestVentas.getIdFase());
					log.info("PARAMETRO I_FASE:" + requestVentas.getIdFase());
				}
				if (requestVentas.getIdEstatus() != null && requestVentas.getIdEstatus().trim().length() > 0 && !requestVentas.getIdEstatus().trim().equals("null")) {
					function.getImportParameterList().setValue("I_STUN",     requestVentas.getIdEstatus());
					log.info("PARAMETRO I_STUN:" + requestVentas.getIdEstatus());
				}	
				
				if ((requestVentas.getIdEquipoInicial() != null && !requestVentas.getIdEquipoInicial().trim().equals("") && !requestVentas.getIdEquipoInicial().trim().equals("null")) || 
					(requestVentas.getIdEquipoFinal()   != null && !requestVentas.getIdEquipoFinal().trim().equals("")   && !requestVentas.getIdEquipoFinal().trim().equals("null"))) {					
					JCoTable itHeaderEquipoIn = function.getTableParameterList().getTable("IT_EQUNR_IN");
					itHeaderEquipoIn.appendRow();		
					if (requestVentas.getIdEquipoInicial() != null && !requestVentas.getIdEquipoInicial().trim().equals("")) {
						itHeaderEquipoIn.setValue("EQUNR_LOW", requestVentas.getIdEquipoInicial());
						log.info("PARAMETRO EQUNR_LOW:" + requestVentas.getIdEquipoInicial());
					} 
					if (requestVentas.getIdEquipoFinal()   != null && !requestVentas.getIdEquipoFinal().trim().equals("")) {
						itHeaderEquipoIn.setValue("EQUNR_HIGH", requestVentas.getIdEquipoFinal());					
						log.info("PARAMETRO EQUNR_HIGH:" + requestVentas.getIdEquipoFinal());
					}
				}
				
				sapConnection.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				responseCode = (String) function.getExportParameterList().getString("E_SUBRC");
				bapiError    = (String) function.getExportParameterList().getString("E_MSGBAPI");
				
				log.info("BAPI MESSAGE:" + responseCode);
				log.info("BAPI Error:" + bapiError);
				
				if (responseCode.equals("00")) 	{
					
					JCoTable  tablaVentas = function.getTableParameterList().getTable("IT_SALES_OUT");					
					responseVentas.setListaVentas(toVentasRow(tablaVentas));
					responseVentas.setMensaje("SUCCESS");	
					responseVentas.setDescripcion("");
				} else {
					responseVentas.setMensaje("FAULT");	
					responseVentas.setDescripcion(bapiError);
				}
			} 
			catch (Exception excp) {
				log.error("ERROR:", excp);
				responseVentas.setMensaje("FAULT");
				responseVentas.setDescripcion(excp.getMessage());
				throw new ReporteException(excp.getMessage(), 100);
			}
		}		

		return responseVentas;		
	}
}
