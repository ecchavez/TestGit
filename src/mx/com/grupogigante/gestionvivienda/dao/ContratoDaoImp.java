/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 16/08/2012              
 */
package mx.com.grupogigante.gestionvivienda.dao;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import mx.com.grupogigante.gestionvivienda.domain.dto.ClienteSapDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CotizacionDetDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfContrato;
import mx.com.grupogigante.gestionvivienda.domain.dto.CtaBancariaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.EquipoContratoDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.PagoDetDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.PagoHdrDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetCompPago;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfContrato;
import mx.com.grupogigante.gestionvivienda.domain.dto.SociedadDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseActualizaStatusImpresionDto;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.CheckFileConnection;
import mx.com.grupogigante.gestionvivienda.utils.Connection;
import mx.com.grupogigante.gestionvivienda.utils.utilsCommon;

import org.springframework.stereotype.Repository;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

/**
 * @author WSNADM
 *
 */
@Repository
public class ContratoDaoImp implements ContratoDao{


	NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
	
	private CheckFileConnection traerArchivo;
	//variables de respuesta para cartera de clientes
	private ResponseGetInfContrato respGetInfContrato;
	private ResponseGetCompPago respGetCompPago;
	private JCoTable tableCliente;
	private JCoTable tableDirCliente;
	private JCoTable tableEquipo;
	private JCoTable tableCuenta;
	private JCoTable tableCotizacion;
	
	private JCoTable tableDirSoc;
	private JCoTable tablePagosHdr;
	private JCoTable tablePagosDet;
	
	public utilsCommon utils=new utilsCommon();
	
	/**
	 * Método que regresa la informacion del contrato 
	 * 
	 * @param criteriosClienteDto 
	 * 			criterios  
	 * @return ResponseGetInfCarCliente 
	 * 			objeto de respuesta para informacion del contrato 
	 * @exception  Exception  
	 */		
	public ResponseGetInfContrato getContrato(CriteriosGetInfContrato criteriosContrato){
		respGetInfContrato=new ResponseGetInfContrato();
		traerArchivo = new CheckFileConnection();	
		
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
				JCoFunction function = connect.getFunction("ZCSMF_0051_GET_INFO_CONTRACT");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosContrato.getId_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", criteriosContrato.getUsuario());
				function.getImportParameterList().setValue("I_VBELN_PED", criteriosContrato.getIdPedido());
				
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				
				if (subrc.equals("00")) 
				{	
					tableCliente = function.getTableParameterList().getTable("IT_CLIENTE");
					tableDirCliente = function.getTableParameterList().getTable("IT_DIR_CLIENTE");
					tableEquipo = function.getTableParameterList().getTable("IT_EQUNR");
					tableCuenta = function.getTableParameterList().getTable("IT_DATS_CTA");
					tableCotizacion = function.getTableParameterList().getTable("IT_BILL_PLAN");
				
				
					respGetInfContrato.setMensaje("SUCCESS");	
					respGetInfContrato.setDescripcion("");
					
					respGetInfContrato.setCliente(getInfoCliente(tableCliente,tableDirCliente));
					respGetInfContrato.setEquipo(getInfoEquipo(tableEquipo));
					respGetInfContrato.setCuentasList(getInfoCtas(tableCuenta));
				    respGetInfContrato.setCotizacionDetList(getInfoCotizacion(tableCotizacion));
				  
				}
				else
				{
					respGetInfContrato.setMensaje("FAULT");	
					respGetInfContrato.setDescripcion(bapierror);
				}
			} 
			catch (Exception excp) 
			{
				respGetInfContrato.setMensaje("FAULT");
				respGetInfContrato.setDescripcion(excp.getMessage());
			}
		}		
		return respGetInfContrato;
	}
	
	
	/**
	 * Método que la info del comprobante de pago 
	 * 
	 * @param criteriosClienteDto 
	 * 			criterios de comprobante 
	 * @return ResponseGetInfCarCliente 
	 * 			objeto de respuesta para informacion del comprobante 
	 * @exception  Exception  
	 */		
	public ResponseGetCompPago getComprobante(CriteriosGetInfContrato criteriosComp){
		respGetCompPago=new ResponseGetCompPago();
		traerArchivo = new CheckFileConnection();	
		
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
				JCoFunction function = connect.getFunction("ZCSMF_0054_GET_COMP_PAGO");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosComp.getId_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", criteriosComp.getUsuario());
				JCoTable itFregi = function.getTableParameterList().getTable("IT_FREGI_IN");
				llenaItHeaderCriteriosComprobante(criteriosComp.getIdPedido(), itFregi);
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				if (subrc.equals("00")) 
				{	

					tablePagosHdr= function.getTableParameterList().getTable("IT_PAGOS_P_HDR_OUT");
					tablePagosDet= function.getTableParameterList().getTable("IT_PAGOS_P_DET_OUT");
					tableDirSoc = function.getTableParameterList().getTable("IT_DIRECCION_SOC_OUT");
					respGetCompPago.setMensaje("SUCCESS");	
					respGetCompPago.setDescripcion("");
					respGetCompPago.setSociedad(getInfoSociedad(tableDirSoc));
					respGetCompPago.setPagoDet(getInfoPagoDet(tablePagosDet));
					respGetCompPago.setPagoHdr(getInfoPagoHdr(tablePagosHdr));
				
				  
			     
				}
				else
				{
					respGetCompPago.setMensaje("FAULT");	
					respGetCompPago.setDescripcion(bapierror);
				}
			} 
			catch (Exception excp) 
			{
				respGetCompPago.setMensaje("FAULT");
				respGetCompPago.setDescripcion(excp.getMessage());
			}
		}		
		return respGetCompPago;	
	}
	
	
/**
	 * Método que settea los campos de JcoTable al objeto de Equipo   
	 * 
	 * @param JCoTable 
	 * 			tabla de busqueda de Equipo  
	 * @return ResponseGetInfContrato
	 * 			objeto de respuesta para obtener a los Equipo  
	 * @exception  Exception  
*/	 
private EquipoContratoDto getInfoEquipo(JCoTable t) 
{
		EquipoContratoDto equipo=new EquipoContratoDto();
		if (t != null){
			if (t.getNumRows() > 0){
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					
					equipo.setFase(t.getString("FASE"));
					equipo.setEqunr(t.getString("EQUNR"));
					equipo.setId_equnrx(t.getString("ID_EQUNRX"));
					equipo.setPiso(t.getString("PISO"));
					equipo.setSup_total(t.getDouble("SUP_TOTAL"));
					equipo.setPrice(t.getDouble("PRICE"));
					equipo.setEngm(t.getDouble("ENGM"));
					equipo.setAnticipo(t.getDouble("ANTICIPO"));
					equipo.setGastos_adm((t.getDouble("GASTOS_ADM")));
					equipo.setDifem((t.getDouble("DIFEM")));
					equipo.setNest((t.getInt("NEST")));
					equipo.setNbod((t.getInt("NBOD")));
					equipo.setAsign_E((t.getString("ASIGN_E")));
					equipo.setAsign_B((t.getString("ASIGN_B")));
					equipo.setM2es(t.getDouble("M2ES"));
					equipo.setFecha_entrega((t.getString("FECHA_ENTREGA")));
					equipo.setM2ja(t.getDouble("M2JA"));
					equipo.setM2bo(t.getDouble("M2BO"));
					equipo.setApoderado1(t.getString("APODERADO1"));
					equipo.setApoderado2(t.getString("APODERADO2"));
					equipo.setTipo(t.getString("TIPO"));
					equipo.setM2pr(t.getString("M2PR"));
					equipo.setEngp(t.getDouble("ENGP"));
					t.nextRow();
				}				
			}
		} 
		return equipo;
}

	/**
	 * Método que settea los campos de JcoTable al objeto de Cliente y direccionCliente   
	 * 
	 * @param JCoTable 
	 * 			tabla de busqueda de Cliente y y direccionCliente    
	 * @return ResponseGetInfContrato 
	 * 			objeto de respuesta para obtener al cliente y sus direccion  
	 * @exception  Exception  
*/	 
	private ClienteSapDto getInfoCliente(JCoTable cli,JCoTable dirCli) 
	{
		ClienteSapDto cliente=new ClienteSapDto();
		utilsCommon utils=new utilsCommon();
		if (cli != null){
			if (cli.getNumRows() > 0){
				cli.firstRow();
				for (int i = 0; i < cli.getNumRows(); i++){
					
					cliente.setId_cliente_z(cli.getString("ID_CLIENTE_Z"));
					cliente.setId_cliente_sap(cli.getString("ID_CLIENTE_SAP"));
					cliente.setNombre1C(cli.getString("NOMBRE1"));
					cliente.setNombre2C(cli.getString("NOMBRE2"));
					cliente.setApp_patC(cli.getString("APP_PAT"));
					cliente.setApp_matC(cli.getString("APP_MAT"));
					cliente.setFch_ncm(utils.convierteFechaVisual(cli.getString("FCH_NCM")));
					cliente.setSexo((cli.getString("SEXO")));
					cliente.setTratamiento((cli.getString("TRATAMIENTO")));
					cliente.setEdocvl((cli.getString("EDO_CIVIL")));
					cliente.setRfcC((cli.getString("RFC")));
					cliente.setId_ifeC((cli.getString("ID_IFE")));
					cliente.setId_pasprtC((cli.getString("ID_PASPRT")));
					cliente.setCurpC(cli.getString("CURP"));
					cli.nextRow();
				}
				
			}
		}
		
		if (dirCli != null){
			if (dirCli.getNumRows() > 0){
				dirCli.firstRow();
				for (int i = 0; i < dirCli.getNumRows(); i++){
					
					cliente.setId_cliente_z(dirCli.getString("ID_CLIENTE_Z"));
					cliente.setCalleC(dirCli.getString("CALLE"));
					cliente.setNoextC(dirCli.getString("NOEXT"));
					cliente.setNointC(dirCli.getString("NOINT"));
					cliente.setColnC(dirCli.getString("COLN"));
					cliente.setCdpstC(dirCli.getString("CDPST"));
					cliente.setDlmcpC((dirCli.getString("DLMCP")));
					cliente.setRegion((dirCli.getString("ESTDO")));
					cliente.setPais((dirCli.getString("PAIS")));
					cliente.setTelfnC((dirCli.getString("TELFN")));
					cliente.setTelofC((dirCli.getString("TELOF")));
					cliente.setExtncC((dirCli.getString("EXTNC")));
					cliente.setTlfmoC((dirCli.getString("TLFMO")));
					cliente.setMail1C(dirCli.getString("MAIL1"));
					cliente.setMail2C(dirCli.getString("MAIL2"));
					dirCli.nextRow();
				}
				
			}
		}
		return cliente;
	}

	/**
	 * Método que settea los campos de JcoTable al objeto de Equipo   
	 * 
	 * @param JCoTable 
	 * 			tabla de busqueda de Equipo  
	 * @return ResponseGetInfContrato
	 * 			objeto de respuesta para obtener a los Equipo  
	 * @exception  Exception  
*/	 
private CtaBancariaDto getInfoCuenta(JCoTable t) 
{
	CtaBancariaDto cuenta=new CtaBancariaDto();
		if (t != null){
			if (t.getNumRows() > 0){
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					
					cuenta.setBanco(t.getString("BANCO"));
					cuenta.setTipo_cuenta(t.getString("TIPO_CUENTA"));
					cuenta.setClabe(t.getString("CLABE"));
					cuenta.setReferencia(t.getString("REFERENCIA"));
					cuenta.setCiudad(t.getString("CIUDAD"));
					t.nextRow();
				}
				
			}
		}
		return cuenta;
}
/**
 * Método que settea los campos de JcoTable al objeto de Cotizacion   
 * 
 * @param JCoTable 
 * 			tabla de busqueda de Cotizacion Detalle  
 * @return ResponseGetInfContrato
 * 			objeto de respuesta para obtener a los Cotizacion  
 * @exception  Exception  
*/	 
private ArrayList<CtaBancariaDto> getInfoCtas(JCoTable t) 
{
	ArrayList<CtaBancariaDto> a = new ArrayList<CtaBancariaDto>();
	if (t != null){
		if (t.getNumRows() > 0){
			
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				a.add(new CtaBancariaDto());
				
				a.get(i).setBanco(t.getString("BANCO"));
				a.get(i).setTipo_cuenta(t.getString("TIPO_CUENTA"));
				a.get(i).setCta(t.getString("CTA"));
				a.get(i).setClabe(t.getString("CLABE"));
				a.get(i).setReferencia(t.getString("REFERENCIA"));
				a.get(i).setCiudad(t.getString("CIUDAD"));
				t.nextRow();
			}
			return a;
		}
	}
	return a;
}
/**
 * Método que settea los campos de JcoTable al objeto de Sociedad  
 * 
 * @param JCoTable 
 * 			tabla de busqueda de Sociedad Dir
 * @return ResponseGetInfContrato
 * 			objeto de respuesta para obtener a los direc de sociedad 
 * @exception  Exception  
*/	 
private ArrayList<CtaBancariaDto> geSociedad(JCoTable t) 
{
	if (t != null){
		if (t.getNumRows() > 0){
			ArrayList<CtaBancariaDto> a = new ArrayList<CtaBancariaDto>();
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				a.add(new CtaBancariaDto());
				
				a.get(i).setBanco(t.getString("BANCO"));
				a.get(i).setTipo_cuenta(t.getString("TIPO_CUENTA"));
				a.get(i).setCta(t.getString("CTA"));
				a.get(i).setClabe(t.getString("CLABE"));
				a.get(i).setReferencia(t.getString("REFERENCIA"));
				a.get(i).setCiudad(t.getString("CIUDAD"));
				t.nextRow();
			}
			return a;
		}
	}
	return null;
}


/**
 * Método que settea los campos de JcoTable al objeto de Cotizacion   
 * 
 * @param JCoTable 
 * 			tabla de busqueda de Cotizacion Detalle  
 * @return ResponseGetInfContrato
 * 			objeto de respuesta para obtener a los Cotizacion  
 * @exception  Exception  
*/	 
public ArrayList<CotizacionDetDto> getInfoCotizacion(JCoTable t) 
{   
	String myString;
	if (t != null){
		if (t.getNumRows() > 0){
			ArrayList<CotizacionDetDto> a = new ArrayList<CotizacionDetDto>();
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				a.add(new CotizacionDetDto());
				
				a.get(i).setId_cotiz_z(t.getString("ID_COTIZ_Z"));
				a.get(i).setPosnr_z(t.getInt("POSNR_Z"));
				a.get(i).setVbeln_cot(t.getString("VBELN_COT"));
				a.get(i).setVbeln_ped(t.getString("VBELN_PED"));
				a.get(i).setConse(t.getInt("CONSE"));
				a.get(i).setConce(t.getString("CONCE"));
				a.get(i).setConcex(t.getString("CONCEX"));
				a.get(i).setFlim(!t.getString("FLIM").equals("") ? utils.convierteFechaVisual(t.getString("FLIM")) : "28/02/2015");
				//myString = NumberFormat.getInstance().format(t.getFloat("TOTAL"));
				myString = nf.format((t.getBigDecimal("TOTAL")));
				a.get(i).setTotal(t.getDouble("TOTAL"));
				a.get(i).setTotalDesc(myString);
    			a.get(i).setSim1(t.getFloat("SIM1"));
				a.get(i).setSim2(t.getFloat("SIM2"));
				t.nextRow();
			}
			return a;
		}
	}
	return null;
}
/**
 * Método que settea los campos de JcoTable al objeto de Equipo   
 * 
 * @param JCoTable 
 * 			tabla de busqueda de Equipo  
 * @return ResponseGetInfContrato
 * 			objeto de respuesta para obtener a los Equipo  
 * @exception  Exception  
*/	 
private SociedadDto getInfoSociedad(JCoTable t) 
{
	SociedadDto sociedad=new SociedadDto();
	if (t != null){
		if (t.getNumRows() > 0){
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				
				sociedad.setNombre_soc(t.getString("NOMBRE_SOC"));
				sociedad.setCalle(t.getString("CALLE"));
				sociedad.setNoext(t.getString("NOEXT"));
				sociedad.setNoint(t.getInt("NOINT"));
				sociedad.setColn(t.getString("COLN"));
				sociedad.setCdpst(t.getString("CDPST"));
				sociedad.setDlmcp(t.getString("DLMCP"));
				sociedad.setEstdotx((t.getString("ESTDOTX")));
				sociedad.setPaistx((t.getString("PAISTX")));
				t.nextRow();
			}
			
		}
	}
	return sociedad;
}
/**
 * Método que settea los campos de JcoTable al objeto de Equipo   
 * 
 * @param JCoTable 
 * 			tabla de busqueda de Equipo  
 * @return ResponseGetInfContrato
 * 			objeto de respuesta para obtener a los Equipo  
 * @exception  Exception  
*/	 
private PagoHdrDto getInfoPagoHdr(JCoTable t) 
{
	PagoHdrDto pagoHdr=new PagoHdrDto();
	if (t != null){
		if (t.getNumRows() > 0){
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				
				pagoHdr.setKunnr(t.getString("KUNNR"));
				pagoHdr.setKunnrtx(t.getString("KUNNRTX"));
				pagoHdr.setId_equnr(t.getString("ID_EQUNR"));
				pagoHdr.setFregi(t.getString("FREGI"));
//				String result = utils.convierteMonto(t.getString("NETWR_X_PAGAR")); 
				pagoHdr.setNetwr_x_pagar(t.getString("NETWR_X_PAGAR"));
				pagoHdr.setNetwr_pag(t.getString("NETWR_PAG"));
				t.nextRow();
			}
			
		}
	}
	return pagoHdr;
}

/**
 * Método que settea los campos de JcoTable al objeto de Equipo   
 * 
 * @param JCoTable 
 * 			tabla de busqueda de Pagos 
 * @return ResponseGetInfContrato
 * 			objeto de respuesta para obtener a los Equipo  
 * @exception  Exception  
*/	 
private PagoDetDto getInfoPagoDet(JCoTable t) 
{
	PagoDetDto pagoDet=new PagoDetDto();
	if (t != null){
		if (t.getNumRows() > 0){
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				
				pagoDet.setFpago(t.getString("FPAGO"));
				pagoDet.setAedat_val(t.getString("AEDAT_VAL"));
				t.nextRow();
			}
			
		}
	}
	return pagoDet;
}
/**
 * Llena el parametro tables IT_HEADER y al mismo tiempo por cada header
 * manda agegar sus correspondientes lienas en el parametro IT_DETAIL
 * @param listaPedido Lista de pedidos a crear
 * @param itHeader    Parametro Table del RFC
 * @param itDetalle   Parametro Table del RFC
 */
private void llenaItHeaderCriteriosComprobante(String fregi,	JCoTable itHeader) {

	 try {
		 if(fregi!=""){
			   itHeader.appendRow();
			   itHeader.setValue("FREGI", fregi);
			  }
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
 

}

public ResponseActualizaStatusImpresionDto actualizaStatusImpresion(CriteriosGetInfCarCliente criteriosClienteDto){
	ResponseActualizaStatusImpresionDto actualizaImpresion=new ResponseActualizaStatusImpresionDto();
	traerArchivo = new CheckFileConnection();	
	
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
		    JCoFunction function;
			function= connect.getFunction("ZCSMF_0057_UPD_ST_CONTRA");
			// Establece Parametros Import
			function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosClienteDto.getId_ut_sup());
			function.getImportParameterList().setValue("I_USUARIO", criteriosClienteDto.getUsuario());
			function.getImportParameterList().setValue("I_ID_EQUNR", criteriosClienteDto.getIdEqunr().trim());
			function.getImportParameterList().setValue("I_ST_CONTRA", criteriosClienteDto.getStatusImpresion().trim());
			
			connect.execute(function);
			// Recupera el estado de determinación de la funcion RFC
			subrc = (String) function.getExportParameterList().getString("E_SUBRC");
			bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
			if (subrc.equals("00")) 
			{	
				actualizaImpresion.setMensaje("SUCCESS");	
				actualizaImpresion.setDescripcion("El estatus de impresion del pedido se actualizo con exito");						                   
			}
			else
			{
				actualizaImpresion.setMensaje("FAULT");	
				actualizaImpresion.setDescripcion(bapierror);
			}
		} 
		catch (Exception excp) 
		{
			actualizaImpresion.setMensaje("FAULT");
			actualizaImpresion.setDescripcion(excp.getMessage());
		}
	}		
	return actualizaImpresion;
}

}

