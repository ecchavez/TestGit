package mx.com.grupogigante.gestionvivienda.dao.report;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.report.comisiones.dto.ComisionesReporteRequest;
import mx.com.grupogigante.gestionvivienda.report.comisiones.dto.ComisionesReporteResponse;
import mx.com.grupogigante.gestionvivienda.report.comisiones.dto.ComisionesReporteRowDto;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.Connection;

import org.springframework.stereotype.Repository;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

@Repository
public class ComisionesReporteDaoImpl implements ComisionesReporteDao {
	
	public List<ComisionesReporteRowDto> toComisionesRow (JCoTable  tablaEquipos) {
		List<ComisionesReporteRowDto> lista = new ArrayList<ComisionesReporteRowDto>();
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
		if (tablaEquipos != null){
			if (tablaEquipos.getNumRows() > 0){
				tablaEquipos.firstRow();
				for (int i = 0; i < tablaEquipos.getNumRows(); i++){
					lista.add(new ComisionesReporteRowDto());
					lista.get(i).setMaktx(tablaEquipos.getString("MAKTX"));// Casa o Depto					
					lista.get(i).setPosco(tablaEquipos.getString("POSCO"));// Pos. Contrato
					lista.get(i).setConse(tablaEquipos.getString("CONSE"));// Consecutivo
					lista.get(i).setClmovx(tablaEquipos.getString("CLMOVX"));// Clase de Mov.
					String formatoNumerico = "$0.00";
					if(tablaEquipos.getString("NETWR")!=null)
					{						
						formatoNumerico = nf.format(new BigDecimal(tablaEquipos.getString("NETWR")).doubleValue());						
					}
					
					lista.get(i).setNetwr(formatoNumerico);// Precio Cierre
					lista.get(i).setMoneda(tablaEquipos.getString("MONEDA"));// Moneda
					
					String fecha_m=tablaEquipos.getString("FCOMI");
					
					if(fecha_m!=null && fecha_m!="")
					{
						String [] fecha_r=fecha_m.split("-");
						fecha_m=fecha_r[2]+"-"+fecha_r[1]+"-"+fecha_r[0];
					}
					else
					{
						fecha_m="";
					}
					lista.get(i).setFcomi(fecha_m);// Fecha Comisión
					lista.get(i).setLaste(tablaEquipos.getString("LASTE"));// Última Exhibición
					lista.get(i).setNpaso(tablaEquipos.getString("NPASO"));// control
					lista.get(i).setNpasox(tablaEquipos.getString("NPASOX"));	//Descripcion n paso				
					// 02=Compl ok , 00 Nuevo, 01 Iniciado , 03 Error
					lista.get(i).setSpaso(tablaEquipos.getString("SPASO"));// estatus paso
					String spaso_desc=tablaEquipos.getString("SPASO");
					if(spaso_desc!=null && spaso_desc!="")
					{
						if(spaso_desc.equals("00"))
						{
							lista.get(i).setSpasox("Nuevo");
						}
						else if(spaso_desc.equals("01"))
						{
							lista.get(i).setSpasox("Iniciado");
						}
						else if(spaso_desc.equals("02"))
						{
							lista.get(i).setSpasox("Ok");
						}
						else if(spaso_desc.equals("03"))
						{
							lista.get(i).setSpasox("Error");
						}
					}
					else
					{
						lista.get(i).setSpasox("");
					}
					lista.get(i).setPorce(tablaEquipos.getString("PORCE"));// porcentaje
					String formatoNumericoc = "$0.00";
					if(tablaEquipos.getString("COMIS")!=null)
					{						
						formatoNumericoc = nf.format(new BigDecimal(tablaEquipos.getString("COMIS")).doubleValue());						
					}
					lista.get(i).setComis(formatoNumericoc);// comision $
					lista.get(i).setVbeln(tablaEquipos.getString("VBELN"));// pedido vta
					lista.get(i).setEbeln(tablaEquipos.getString("EBELN"));// pedido compra
					lista.get(i).setSlsman(tablaEquipos.getString("SLSMAN"));// vendedor
					lista.get(i).setBapim(tablaEquipos.getString("BAPIM"));// mensaje error	
					tablaEquipos.nextRow();

				}
			}
		}		
		return lista;
	}

	public ComisionesReporteResponse findComisiones(ComisionesReporteRequest requestComisiones) throws ViviendaException {
		List<ComisionesReporteRowDto>  lista         = null;
		Connection                         sapConnection = null;
		String     sapStatus     = ArchLogg.leeLogg();
		String     responseCode  = "";
		String     bapiError     = "";
		ComisionesReporteResponse responseComisiones = new ComisionesReporteResponse();
		
		System.out.println(requestComisiones.toString());
		
		if (sapStatus.equals("OK")) 	{
			try {
				sapConnection = (Connection.getConnect() == null? new Connection(ArchLogg.getSapSystem()):Connection.getConnect());
				// Establece RFC a ejecutar en SAP
				JCoFunction function = sapConnection.getFunction("ZCSMF_0086_COMISIONES");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", requestComisiones.getUnidadTecnicaSuperior());
				function.getImportParameterList().setValue("I_USUARIO",   requestComisiones.getIdUsuario());
				
				if ((requestComisiones.getIdEquipoInicial() != null && !requestComisiones.getIdEquipoInicial().trim().equals("") && !requestComisiones.getIdEquipoInicial().trim().equals("null")) || 
					(requestComisiones.getIdEquipoFinal()   != null && !requestComisiones.getIdEquipoFinal().trim().equals("")   && !requestComisiones.getIdEquipoFinal().trim().equals("null"))) {					
					JCoTable itHeaderEquipoIn = function.getTableParameterList().getTable("IT_EQUNR_IN");
					itHeaderEquipoIn.appendRow();		
					if (requestComisiones.getIdEquipoInicial() != null && !requestComisiones.getIdEquipoInicial().trim().equals("")) {
						itHeaderEquipoIn.setValue("EQUNR_LOW", requestComisiones.getIdEquipoInicial());
						System.out.println("PARAMETRO EQUNR_LOW:" + requestComisiones.getIdEquipoInicial());
					} 
					if (requestComisiones.getIdEquipoFinal()   != null && !requestComisiones.getIdEquipoFinal().trim().equals("")) {
						itHeaderEquipoIn.setValue("EQUNR_HIGH", requestComisiones.getIdEquipoFinal());					
						System.out.println("PARAMETRO EQUNR_HIGH:" + requestComisiones.getIdEquipoFinal());
					}
				}
				
				sapConnection.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				responseCode = (String) function.getExportParameterList().getString("E_SUBRC");
				bapiError    = (String) function.getExportParameterList().getString("E_MSGBAPI");
				
				System.out.println("BAPI MESSAGE:" + responseCode);
				System.out.println("BAPI Error:" + bapiError);
				
				if (responseCode.equals("00")) 	{
					
					JCoTable  tablaEquipos = function.getTableParameterList().getTable("IT_COMIS_EQUI_OUT");					
					responseComisiones.setListaComision(toComisionesRow(tablaEquipos));
					responseComisiones.setMensaje("SUCCESS");	
					responseComisiones.setDescripcion("");
				} else {
					responseComisiones.setMensaje("FAULT");	
					responseComisiones.setDescripcion(bapiError);
				}
			} 
			catch (ViviendaException excp) {
				excp.printStackTrace();
				responseComisiones.setMensaje("FAULT");
				responseComisiones.setDescripcion(excp.getMessage());
				throw new ViviendaException(excp.getMessage());
			}
		}		

		return responseComisiones;		
	}
}
