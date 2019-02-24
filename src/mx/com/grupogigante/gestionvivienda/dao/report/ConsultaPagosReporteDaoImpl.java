package mx.com.grupogigante.gestionvivienda.dao.report;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import mx.com.grupogigante.gestionvivienda.report.edocta.dto.ConsultaPagosReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.ConsultaPagosReporteResponse;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.EstadoCuentaReporteLayout;
//import mx.com.grupogigante.gestionvivienda.report.edocta.dto.EstadoCuentaReporteParamDto;
//import mx.com.grupogigante.gestionvivienda.report.edocta.dto.EstadoCuentaReporteRequest;
//import mx.com.grupogigante.gestionvivienda.report.edocta.dto.EstadoCuentaReporteResponse;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.ConsultaPagosReporteLayout;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.TablaCobroDetalleDto;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.TablaDireccionClienteDto;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.TablaDireccionSociedadDto;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.TablaPagoDetalleDto;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.Connection;
import mx.com.grupogigante.gestionvivienda.utils.utilsCommon;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

@Repository
public class ConsultaPagosReporteDaoImpl implements ConsultaPagosReporteDao {
	private Logger log = Logger.getLogger(ConsultaPagosReporteDaoImpl.class);
	public utilsCommon utils=new utilsCommon();


	private List<ConsultaPagosReporteLayout> toCliente (JCoTable  tablaConsutaPagos) {
		List<ConsultaPagosReporteLayout> lista = new ArrayList<ConsultaPagosReporteLayout>();
		double formatoNumericoDouble = 0.00;
		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
		
		if (tablaConsutaPagos != null && tablaConsutaPagos.getNumRows() > 0) {
			tablaConsutaPagos.firstRow();
				for (int i = 0; i < tablaConsutaPagos.getNumRows(); i++) {
				ConsultaPagosReporteLayout consultaPagos = new ConsultaPagosReporteLayout();
				consultaPagos.setEquipo(tablaConsutaPagos.getString("MATNR"));
				consultaPagos.setFolio(tablaConsutaPagos.getString("FREGI"));
				consultaPagos.setConsecutivo(tablaConsutaPagos.getString("CONSE"));
				consultaPagos.setReferencia(tablaConsutaPagos.getString("REFER"));
				consultaPagos.setFechaPago(tablaConsutaPagos.getString("FPAGO"));
				String fecha = tablaConsutaPagos.getString("FPAGO");
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
				consultaPagos.setFechaPago(fechaReal == null? "":sdt.format(fechaReal));
				consultaPagos.setIdConcepto(tablaConsutaPagos.getString("CONCEPTO"));
				consultaPagos.setTxtConcepto(tablaConsutaPagos.getString("CONCEPTOX"));
				consultaPagos.setIdMedioPago(tablaConsutaPagos.getString("MED_PAG"));
				consultaPagos.setTxtMedioPago(tablaConsutaPagos.getString("MED_PAGX"));
				consultaPagos.setTipoRegistro(tablaConsutaPagos.getString("TIP_REG"));
				String montoReporte = tablaConsutaPagos.getString("NETWR");
				if (montoReporte != null && !montoReporte.equals("")) {
					formatoNumericoDouble = new BigDecimal(tablaConsutaPagos.getString("NETWR")).doubleValue();
				}
				consultaPagos.setMontoPago(formatoNumericoDouble);	
				fecha = tablaConsutaPagos.getString("AEDAT_VAL");
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
				consultaPagos.setFechaVal(fechaReal == null? "":sdt.format(fechaReal));
				consultaPagos.setPagado(tablaConsutaPagos.getString("VALID"));
				consultaPagos.setObservaciones(tablaConsutaPagos.getString("OBSERV"));
				consultaPagos.setFileName(tablaConsutaPagos.getString("FILENAM1"));
				consultaPagos.setIdPedido(tablaConsutaPagos.getString("VBELN_PED"));
				lista.add(consultaPagos);
				tablaConsutaPagos.nextRow();
				}
		}
		return lista;
	}
	
	
	private List<ConsultaPagosReporteLayout> toClientePrueba() {
		List<ConsultaPagosReporteLayout> lista = new ArrayList<ConsultaPagosReporteLayout>();
		
		//Hard Code
		ConsultaPagosReporteLayout consultaPagos  = new ConsultaPagosReporteLayout();
		consultaPagos.setEquipo("D101");
		consultaPagos.setFileName("5900MYAF0101-D101_02_01_01.pdf");
		consultaPagos.setFolio("12345");
		consultaPagos.setConsecutivo("1");
		consultaPagos.setReferencia("67890");
		consultaPagos.setFechaPago("03/03/2014");
		consultaPagos.setIdConcepto("02");
		consultaPagos.setTxtConcepto("Apartado"); 	
		consultaPagos.setIdMedioPago("02");
		consultaPagos.setTxtMedioPago("Deposito");
		consultaPagos.setTipoRegistro("Manual");
		//consultaPagos.setMontoPago("$50,000.00");
		consultaPagos.setFechaVal("21/03/2014");
		consultaPagos.setPagado("X");
		consultaPagos.setObservaciones("Dummy");
		lista.add(consultaPagos);
		
		ConsultaPagosReporteLayout consultaPagos1  = new ConsultaPagosReporteLayout();
		consultaPagos1.setEquipo("D101");
		consultaPagos1.setFileName("5900MYAF0101-D101_02_01_02.pdf");
		consultaPagos1.setFolio("12346");
		consultaPagos1.setConsecutivo("1");
		consultaPagos1.setReferencia("67891");
		consultaPagos1.setFechaPago("03/03/2014");
		consultaPagos1.setIdConcepto("02");
		consultaPagos1.setTxtConcepto("Enchanche"); 	
		consultaPagos1.setIdMedioPago("02");
		consultaPagos1.setTxtMedioPago("Deposito");
		consultaPagos1.setTipoRegistro("Manual");
		//consultaPagos1.setMontoPago("$180,000.00");
		consultaPagos1.setFechaVal("28/03/2014");
		consultaPagos1.setPagado("X");
		consultaPagos1.setObservaciones("Dummy");
		lista.add(consultaPagos1);
		
		ConsultaPagosReporteLayout consultaPagos2  = new ConsultaPagosReporteLayout();
		consultaPagos2.setEquipo("D101");
		consultaPagos2.setFileName("5900MYAF0101-D101_02_01_03.pdf");
		consultaPagos2.setFolio("12347");
		consultaPagos2.setConsecutivo("1");
		consultaPagos2.setReferencia("67892");
		consultaPagos2.setFechaPago("03/03/2014");
		consultaPagos2.setIdConcepto("02");
		consultaPagos2.setTxtConcepto("Diferido"); 	
		consultaPagos2.setIdMedioPago("02");
		consultaPagos2.setTxtMedioPago("TPV");
		consultaPagos2.setTipoRegistro("Manual");
		//consultaPagos2.setMontoPago("$250,000.00");
		consultaPagos2.setFechaVal("01/04/2014");
		consultaPagos2.setPagado("");
		consultaPagos2.setObservaciones("Dummy");
		lista.add(consultaPagos2);
		
		ConsultaPagosReporteLayout consultaPagos3  = new ConsultaPagosReporteLayout();
		consultaPagos3.setEquipo("D101");
		consultaPagos3.setFileName("5900MYAF0101-D101_02_01_03.pdf");
		consultaPagos3.setFolio("12348");
		consultaPagos3.setConsecutivo("1");
		consultaPagos3.setReferencia("67893");
		consultaPagos3.setFechaPago("03/03/2014");
		consultaPagos3.setIdConcepto("02");
		consultaPagos3.setTxtConcepto("Diferido"); 	
		consultaPagos3.setIdMedioPago("02");
		consultaPagos3.setTxtMedioPago("TPV");
		consultaPagos3.setTipoRegistro("Automático");
		//consultaPagos3.setMontoPago(25000000);
		consultaPagos3.setFechaVal("01/05/2014");
		consultaPagos3.setPagado("X");
		consultaPagos3.setObservaciones("Dummy");
		lista.add(consultaPagos3);
		
		ConsultaPagosReporteLayout consultaPagos4  = new ConsultaPagosReporteLayout();
		consultaPagos4.setEquipo("D101");
		consultaPagos4.setFileName("5900MYAF0101-D101_02_01_04.pdf");
		consultaPagos4.setFolio("12349");
		consultaPagos4.setConsecutivo("1");
		consultaPagos4.setReferencia("67894");
		consultaPagos4.setFechaPago("03/03/2014");
		consultaPagos4.setIdConcepto("02");
		consultaPagos4.setTxtConcepto("Pago Final"); 	
		consultaPagos4.setIdMedioPago("02");
		consultaPagos4.setTxtMedioPago("Transferencia");
		consultaPagos4.setTipoRegistro("Manual");
		//consultaPagos4.setMontoPago("$3,000,000.00");
		consultaPagos4.setFechaVal("28/06/2014");
		consultaPagos4.setPagado("X");
		consultaPagos4.setObservaciones("Dummy");
		lista.add(consultaPagos4);
		
		return lista;
	}
				
	@Override
	public ConsultaPagosReporteResponse buscaReporte(ConsultaPagosReporteRequest request) throws ReporteException {
		Connection sapConnection = null;
		String     sapStatus     = ArchLogg.leeLogg();
		String     responseCode  = "";
		String     bapiError     = "";
		SimpleDateFormat formatoFecha = new SimpleDateFormat("ddMMyyyy");
		
		ConsultaPagosReporteResponse  reporteResponse  = new ConsultaPagosReporteResponse();
		//Inicia para pruebas
		if (sapStatus.equals("OK")) 	{
			try {
				
				sapConnection = (Connection.getConnect() == null? new Connection(ArchLogg.getSapSystem()):Connection.getConnect());
				
				// Establece RFC a ejecutar en SAP
				JCoFunction function = sapConnection.getFunction("ZCSMF_0120_GET_PAID_DETAIL");
				
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", request.getIdUnidadTecnicaSuperior());
				
				function.getImportParameterList().setValue("I_USUARIO",   request.getIdUsuario());
				
				if (request.getIdFase() != null && request.getIdFase().trim().length() > 0 && !request.getIdFase().trim().equals("null")) {
					function.getImportParameterList().setValue("I_FASE",     request.getIdFase());
				}
				
				if (request.getIdCliente() != null && request.getIdCliente().trim().length() > 0 && !request.getIdCliente().trim().equals("null")) {
					JCoTable itHeaderBancoIn = function.getTableParameterList().getTable("IT_CLNTE_IN");
					itHeaderBancoIn.appendRow();		
					itHeaderBancoIn.setValue("ID_CLI_SAP", request.getIdCliente());
				}
				
				if (request.getIdEquipo() != null && request.getIdEquipo().trim().length() > 0 && !request.getIdEquipo().trim().equals("null")) {
					JCoTable itHeaderBancoIn = function.getTableParameterList().getTable("IT_EQUI_IN");
					itHeaderBancoIn.appendRow();		
					itHeaderBancoIn.setValue("EQUI", request.getIdEquipo());
				}
				
				if ((request.getFechaIni() != null && request.getFechaIni().trim().length() > 0 && !request.getFechaIni().equals("undefined") 
					&& !request.getFechaIni().equals("")) || (request.getFechaFin() != null && request.getFechaFin().trim().length() > 0 
					&& !request.getFechaFin().equals("undefined") && !request.getFechaFin().equals(""))) {
					JCoTable itHeaderBancoIn = function.getTableParameterList().getTable("IT_FECH_IN");
					itHeaderBancoIn.appendRow();
					
					if (request.getFechaIni() != null && request.getFechaIni().trim().length() > 0 && !request.getFechaIni().equals("undefined")
							&& !request.getFechaIni().equals("")){
						String fecha = utils.convierteFecha(request.getFechaIni());
						itHeaderBancoIn.setValue("AEDAT_LOW", fecha);
					}
					
					if (request.getFechaFin() != null && request.getFechaFin().trim().length() > 0 
							&& !request.getFechaFin().equals("undefined") && !request.getFechaFin().equals("")) {
						String fecha = utils.convierteFecha(request.getFechaFin());
						itHeaderBancoIn.setValue("AEDAT_HIGH", fecha);
					}
					
				}
				
				if (!request.getIdMedioPago().trim().equals("undefined") && !request.getIdMedioPago().trim().equals("")) {
					JCoTable itHeaderBancoIn = function.getTableParameterList().getTable("IT_MEDPAG_IN");
					itHeaderBancoIn.appendRow();
					itHeaderBancoIn.setValue("MED_PAG", request.getIdMedioPago());
				}
				
				sapConnection.execute(function);
				
				// Recupera el estado de determinación de la funcion RFC
				responseCode = (String) function.getExportParameterList().getString("E_SUBRC");
				bapiError    = (String) function.getExportParameterList().getString("E_MSGBAPI");
				
				log.info("BAPI MESSAGE:" + responseCode);
				log.info("BAPI Error:" + bapiError);
				
				if (responseCode.equals("00")) 	{	
					JCoTable  tablaConsutaPagos		  = function.getTableParameterList().getTable("IT_REP_DETAIL_OUT");
					
					reporteResponse.setLayout(toCliente(tablaConsutaPagos));
					reporteResponse.setMensaje("SUCCESS");	
					reporteResponse.setDescripcion("");
				} else {
					reporteResponse.setMensaje("FAULT");	
					reporteResponse.setDescripcion(bapiError);
				}
			} 
			catch (Exception excp) {
				log.error("ERROR SAP:", excp);
				reporteResponse.setMensaje("FAULT");
				reporteResponse.setDescripcion(excp.getMessage());
				throw new ReporteException(excp.getMessage(), 100);
			}
		}
		
		/* Para prueba
		reporteResponse.setLayout(toClientePrueba());
		reporteResponse.setMensaje("SUCCESS");	
		reporteResponse.setDescripcion("");*/
		return reporteResponse;

	}

}
