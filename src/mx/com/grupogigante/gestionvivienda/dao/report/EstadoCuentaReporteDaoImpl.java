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

import mx.com.grupogigante.gestionvivienda.report.edocta.dto.EstadoCuentaReporteLayout;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.EstadoCuentaReporteParamDto;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.EstadoCuentaReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.EstadoCuentaReporteResponse;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.TablaCobroDetalleDto;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.TablaDireccionClienteDto;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.TablaDireccionSociedadDto;
import mx.com.grupogigante.gestionvivienda.report.edocta.dto.TablaPagoDetalleDto;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.Connection;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

@Repository
public class EstadoCuentaReporteDaoImpl implements EstadoCuentaReporteDao {
	private Logger log = Logger.getLogger(EstadoCuentaReporteDaoImpl.class);


	private List<EstadoCuentaReporteLayout> toCliente (JCoTable  tablaHeader, JCoTable tablaCliente, JCoTable tablaCobro, JCoTable tablaPago, JCoTable tablaClienteDireccion, JCoTable tablaEmpresaDireccion) {
		List<EstadoCuentaReporteLayout> lista = null;
		
		EstadoCuentaReporteLayout clientePivote = new EstadoCuentaReporteLayout();
		if (tablaCliente != null && tablaCliente.getNumRows() > 0) {
			tablaCliente.firstRow();
			clientePivote.setIdCliente(tablaCliente.getString("ID_CLIENTE_SAP"));
			clientePivote.setApmaterno(tablaCliente.getString("APP_MAT"));
			clientePivote.setAppaterno(tablaCliente.getString("APP_PAT"));
			clientePivote.setNombre(tablaCliente.getString("NOMBRE1"));
			clientePivote.setSegundo(tablaCliente.getString("NOMBRE2"));
			tablaCliente.nextRow();
		}

		TablaDireccionClienteDto direccionPivote = new TablaDireccionClienteDto();
		if (tablaClienteDireccion != null && tablaClienteDireccion.getNumRows() > 0) {
			tablaClienteDireccion.firstRow();
			
			direccionPivote.setIdClientez(tablaClienteDireccion.getString("ID_CLIENTE_Z"));
			direccionPivote.setCalle(tablaClienteDireccion.getString("CALLE"));
			direccionPivote.setNoext(tablaClienteDireccion.getString("NOEXT"));
			direccionPivote.setNoint(tablaClienteDireccion.getString("NOINT"));
			direccionPivote.setColn(tablaClienteDireccion.getString("COLN"));
			direccionPivote.setCdpst(tablaClienteDireccion.getString("CDPST"));
			direccionPivote.setDlmcp(tablaClienteDireccion.getString("DLMCP"));
			direccionPivote.setEstdo(tablaClienteDireccion.getString("ESTDO"));
			direccionPivote.setPais(tablaClienteDireccion.getString("PAIS"));
			direccionPivote.setTelfn(tablaClienteDireccion.getString("TELFN"));
			direccionPivote.setTelof(tablaClienteDireccion.getString("TELOF"));
			direccionPivote.setExtnc(tablaClienteDireccion.getString("EXTNC"));
			direccionPivote.setTlfmo(tablaClienteDireccion.getString("TLFMO"));
			direccionPivote.setMail1(tablaClienteDireccion.getString("MAIL1"));
			direccionPivote.setMail2(tablaClienteDireccion.getString("MAIL2"));
			direccionPivote.setPaixtx(tablaClienteDireccion.getString("PAIXTX"));				

			tablaClienteDireccion.nextRow();
		}		
		
		Map<String, List<TablaCobroDetalleDto>> mapaCobro = new HashMap<String, List<TablaCobroDetalleDto>>();
		SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
		String formatoNumerico = "$0.00";

		if (tablaCobro != null && tablaCobro.getNumRows() > 0) {
			List<TablaCobroDetalleDto> cobro = new ArrayList<TablaCobroDetalleDto>();
			tablaCobro.firstRow();
			for (int i = 0; i < tablaCobro.getNumRows(); i++) {
				String numeroReferencia = tablaCobro.getString("REFERENCIA");
				TablaCobroDetalleDto cobroBuffer = new TablaCobroDetalleDto();
				String fecha = tablaCobro.getString("FECH_DOC");
				sdt.applyPattern("yyyy-MM-dd");
				Date fechaReal = null;
				if (fecha != null && !fecha.trim().equals("")) {
					try {
						fechaReal = sdt.parse(fecha);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				sdt.applyPattern("dd/MM/yyyy");
				cobroBuffer.setFechDoc(fechaReal == null? "":sdt.format(fechaReal));
				
				formatoNumerico = "$0.00";
				String montoReporte = tablaCobro.getString("NETWR");
				if (montoReporte != null && !montoReporte.trim().equals("")) {
					formatoNumerico = nf.format(new BigDecimal(montoReporte).doubleValue());
				}
				
				cobroBuffer.setNetwr(formatoNumerico);
				
				cobroBuffer.setMoneda(tablaCobro.getString("MONEDA"));
				cobroBuffer.setConcepto(tablaCobro.getString("CONCEPTO"));
				cobroBuffer.setReferencia(tablaCobro.getString("REFERENCIA"));
				cobroBuffer.setSigno(tablaCobro.getString("SIGNO"));
				cobroBuffer.setClearDate(tablaCobro.getString("CLEAR_DATE"));
				cobroBuffer.setClrDocNo(tablaCobro.getString("CLR_DOC_NO"));
				
				if (mapaCobro.containsKey(numeroReferencia)) {
					mapaCobro.get(numeroReferencia).add(cobroBuffer);
				} else {
					List<TablaCobroDetalleDto> listaBuffer = new ArrayList<TablaCobroDetalleDto>();
					listaBuffer.add (cobroBuffer);
					mapaCobro.put(numeroReferencia, listaBuffer);
				}
				tablaCobro.nextRow();
			}
		}
		
		Map<String, List<TablaPagoDetalleDto>> mapaPago = new HashMap<String, List<TablaPagoDetalleDto>>();
		if (tablaPago != null && tablaPago.getNumRows() > 0) {
			List<TablaPagoDetalleDto> pago = new ArrayList<TablaPagoDetalleDto>();
			tablaPago.firstRow();
			for (int i = 0; i < tablaPago.getNumRows(); i++) {
				String numeroReferencia = tablaPago.getString("REFERENCIA");
				TablaPagoDetalleDto pagoBuffer = new TablaPagoDetalleDto();

				String fecha = tablaPago.getString("FECH_DOC");
				sdt.applyPattern("yyyy-MM-dd");
				Date fechaReal = null;
				try {
					fechaReal = sdt.parse(fecha);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sdt.applyPattern("dd/MM/yyyy");
				pagoBuffer.setFechDoc(fechaReal == null? "":sdt.format(fechaReal));
				
				formatoNumerico = "$0.00";
				String montoReporte = tablaPago.getString("NETWR");
				if (montoReporte != null && !montoReporte.trim().equals("")) {
					formatoNumerico = nf.format(new BigDecimal(montoReporte).doubleValue());
				}
				
				pagoBuffer.setNetwr(formatoNumerico);
				pagoBuffer.setMoneda(tablaPago.getString("MONEDA"));
				pagoBuffer.setConcepto(tablaPago.getString("CONCEPTO"));
				pagoBuffer.setReferencia(tablaPago.getString("REFERENCIA"));
				pagoBuffer.setSigno(tablaPago.getString("SIGNO"));
				pagoBuffer.setClearDate(tablaPago.getString("CLEAR_DATE"));
				pagoBuffer.setClrDocNo(tablaPago.getString("CLR_DOC_NO"));
				
				if (mapaPago.containsKey(numeroReferencia)) {
					mapaPago.get(numeroReferencia).add(pagoBuffer);
				} else {
					List<TablaPagoDetalleDto> listaBuffer = new ArrayList<TablaPagoDetalleDto>();
					listaBuffer.add (pagoBuffer);
					mapaPago.put(numeroReferencia, listaBuffer);
				}
				tablaPago.nextRow();
			}
		}

		Map<String, TablaDireccionSociedadDto> mapaEmpresaDireccion = new HashMap<String, TablaDireccionSociedadDto>();
		if (tablaEmpresaDireccion != null && tablaEmpresaDireccion.getNumRows() > 0) {
			tablaEmpresaDireccion.firstRow();
			for (int i = 0; i < tablaEmpresaDireccion.getNumRows(); i++) {
				String numeroReferencia = tablaEmpresaDireccion.getString("VBELN_PED");
				
				TablaDireccionSociedadDto direccionBuffer = new TablaDireccionSociedadDto();

				direccionBuffer.setVbelnped(tablaEmpresaDireccion.getString("VBELN_PED"));
				direccionBuffer.setNombresoc(tablaEmpresaDireccion.getString("NOMBRE_SOC"));
				direccionBuffer.setCalle(tablaEmpresaDireccion.getString("CALLE"));
				direccionBuffer.setNoext(tablaEmpresaDireccion.getString("NOEXT"));
				direccionBuffer.setNoint(tablaEmpresaDireccion.getString("NOINT"));
				direccionBuffer.setColn(tablaEmpresaDireccion.getString("COLN"));
				direccionBuffer.setCdpst(tablaEmpresaDireccion.getString("CDPST"));
				direccionBuffer.setDlmcp(tablaEmpresaDireccion.getString("DLMCP"));
				direccionBuffer.setEstdo(tablaEmpresaDireccion.getString("ESTDO"));
				direccionBuffer.setEstdox(tablaEmpresaDireccion.getString("ESTDOTX"));
				direccionBuffer.setPais(tablaEmpresaDireccion.getString("PAIS"));
				direccionBuffer.setPaisx(tablaEmpresaDireccion.getString("PAISTX"));
				direccionBuffer.setTelfn(tablaEmpresaDireccion.getString("TELFN"));
				direccionBuffer.setTelof(tablaEmpresaDireccion.getString("TELOF"));
				direccionBuffer.setExtnc(tablaEmpresaDireccion.getString("EXTNC"));
				direccionBuffer.setTlfmo(tablaEmpresaDireccion.getString("TLFMO"));
				direccionBuffer.setMail1(tablaEmpresaDireccion.getString("MAIL1"));
				direccionBuffer.setMail2(tablaEmpresaDireccion.getString("MAIL2"));
				
				mapaEmpresaDireccion.put(numeroReferencia, direccionBuffer);

				tablaEmpresaDireccion.nextRow();
			}
		}

		
		if (tablaHeader != null && tablaHeader.getNumRows() > 0) {
			lista = new ArrayList<EstadoCuentaReporteLayout>();
			tablaHeader.firstRow();
			for (int i = 0; i < tablaHeader.getNumRows(); i++) {
				EstadoCuentaReporteLayout buffer = new EstadoCuentaReporteLayout();
				buffer.setDesarrollo(tablaHeader.getString("ID_UBI_TECX"));
				String equipoFase = tablaHeader.getString("EQUNR");
				buffer.setFase(equipoFase.split("-")[0].substring(7,10));
				buffer.setCasadepto(equipoFase.split("-")[1]);
				buffer.setCasa(tablaHeader.getString("EQUNRX"));
				buffer.setUnidaTecnicaDesc(tablaHeader.getString("ID_UBI_TECX"));
				buffer.setIdEquipoCompleto(equipoFase);
				buffer.setReferencia(tablaHeader.getString("REFERENCIA"));
				buffer.setReferenciaDv(tablaHeader.getString("REFERENCIA_DV"));
				buffer.setPedido(tablaHeader.getString("VBELN"));
				buffer.setMontoCouta(tablaHeader.getString("DIF_CUOTA_UNIQ"));
				buffer.setMsgCouta(tablaHeader.getString("MSG_CUOTA_UNIQ"));
				buffer.setIdCliente(clientePivote.getIdCliente());
				buffer.setApmaterno(clientePivote.getApmaterno());
				buffer.setAppaterno(clientePivote.getAppaterno());
				buffer.setNombre(clientePivote.getNombre());
				buffer.setSegundo(clientePivote.getSegundo());
				
				buffer.setCobro(mapaCobro.get(buffer.getReferencia()));
				buffer.setPago(mapaPago.get(buffer.getReferencia()));
				
				buffer.setCalle(direccionPivote.getCalle());
				buffer.setCdpst(direccionPivote.getCdpst());
				buffer.setColn(direccionPivote.getColn());
				buffer.setDlmcp(direccionPivote.getDlmcp());
				buffer.setEstdo(direccionPivote.getEstdo());
				buffer.setExtnc(direccionPivote.getExtnc());
				buffer.setMail1(direccionPivote.getMail1());
				buffer.setMail2(direccionPivote.getMail2());
				buffer.setNoext(direccionPivote.getNoext());
				buffer.setNoint(direccionPivote.getNoint());
				buffer.setPais(direccionPivote.getPais());
				buffer.setPaixtx(direccionPivote.getPaixtx());
				buffer.setTelfn(direccionPivote.getTelfn());
				buffer.setTelof(direccionPivote.getTelof());
				buffer.setTlfmo(direccionPivote.getTlfmo());	
				
				buffer.setDireccionEmpresa(mapaEmpresaDireccion.get(buffer.getPedido()));
				
				buffer.setCuenta(tablaHeader.getString("BANKN"));
				buffer.setClabe(tablaHeader.getString("CLABE"));
				buffer.setDifGastosAdmin(tablaHeader.getString("DIF_GASTOS_ADMIN"));
				buffer.setMsgGastosAdmin(tablaHeader.getString("MSG_GASTOS_ADMIN"));

				lista.add(buffer);
				tablaHeader.nextRow();
			}
		}
		return lista;
	}
				
	@Override
	public EstadoCuentaReporteResponse buscaReporte(EstadoCuentaReporteRequest request) throws ReporteException {
		Connection sapConnection = null;
		String     sapStatus     = ArchLogg.leeLogg();
		String     responseCode  = "";
		String     bapiError     = "";
		
		EstadoCuentaReporteResponse  reporteResponse  = new EstadoCuentaReporteResponse();
		EstadoCuentaReporteParamDto  paramDto         = new EstadoCuentaReporteParamDto();
		
		if (sapStatus.equals("OK")) 	{
			try {
				sapConnection = (Connection.getConnect() == null? new Connection(ArchLogg.getSapSystem()):Connection.getConnect());
				// Establece RFC a ejecutar en SAP
				JCoFunction function = sapConnection.getFunction("ZCSMF_0060_GET_EDO_CTA_2");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", request.getIdUnidadTecnicaSuperior());
				function.getImportParameterList().setValue("I_USUARIO",   request.getIdUsuario());
				function.getImportParameterList().setValue("I_FECHA_CORTE",   request.getFechaCorte());
				
				if (request.getIdCliente() != null && request.getIdCliente().trim().length() > 0 && !request.getIdCliente().trim().equals("null")) {
					function.getImportParameterList().setValue("I_KUNNR",     request.getIdCliente());
				}
				if (request.getIdEquipo() != null && request.getIdEquipo().trim().length() > 0 && !request.getIdEquipo().trim().equals("null")) {
					function.getImportParameterList().setValue("I_EQUNR",     request.getIdEquipo());
				}			
				
				sapConnection.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				responseCode = (String) function.getExportParameterList().getString("E_SUBRC");
				bapiError    = (String) function.getExportParameterList().getString("E_MSGBAPI");
				
				log.info("BAPI MESSAGE:" + responseCode);
				log.info("BAPI Error:" + bapiError);
				
				if (responseCode.equals("00")) 	{	
					JCoTable  tablaEstadoCuentaHeader = function.getTableParameterList().getTable("IT_EDO_CTA_HDR");
					JCoTable  tablaCliente            = function.getTableParameterList().getTable("IT_CLIENTE");
					JCoTable  tablaCobroDetalle       = function.getTableParameterList().getTable("IT_EDO_CTA_COBRO_DET");
					JCoTable  tablaPagoDetalle        = function.getTableParameterList().getTable("IT_EDO_CTA_PAGO_DET");
					JCoTable  tablaClienteDireccion   = function.getTableParameterList().getTable("IT_DIR_CLIENTE");
					JCoTable  tablaEmpresaDireccion   = function.getTableParameterList().getTable("IT_DIRECCION_SOC_OUT");
					
					reporteResponse.setLayout                         (toCliente          (tablaEstadoCuentaHeader, 
							                                                               tablaCliente, 
							                                                               tablaCobroDetalle, 
							                                                               tablaPagoDetalle,
							                                                               tablaClienteDireccion, 
							                                                               tablaEmpresaDireccion));
					reporteResponse.setMensaje("SUCCESS");	
					reporteResponse.setDescripcion("");
				} else {
					reporteResponse.setMensaje("FAULT");	
					reporteResponse.setDescripcion(bapiError);
				}
			} 
			catch (Exception excp) {
				log.error("ERROR SAP:", excp);
				//excp.printStackTrace();
				reporteResponse.setMensaje("FAULT");
				reporteResponse.setDescripcion(excp.getMessage());
				throw new ReporteException(excp.getMessage(), 100);
			}
		}		
		return reporteResponse;

	}

}
