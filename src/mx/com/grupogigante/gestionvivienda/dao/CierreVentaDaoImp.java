/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 12/09/2012              
 */
package mx.com.grupogigante.gestionvivienda.dao;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mx.com.grupogigante.gestionvivienda.domain.dto.ClienteSapDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ClientesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ClientesReporteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ContactoByClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ContactoDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CotizacionDetDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CotizacionHdrDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CotizacionesDet1Dto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CotizacionesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfClienteSap;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfPag;
import mx.com.grupogigante.gestionvivienda.domain.dto.DetalleExtractoDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.DireccionSocDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.EquiposAdicionalesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.PagoDetDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.PagoHdrDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.PermisoUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.PermisosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.PoolErrorDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.RegistroDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseAddClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetConPagosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfClienteSap;
import mx.com.grupogigante.gestionvivienda.domain.dto.VisitasClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.CriteriosGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.ResponseGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.CriteriosGetInfGetCot;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseAddPagosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseCreacionPedidoDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseGetInfCotizacionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseGetInfoPagRegDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseObtieneReferenciaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CotizadorSubequiposDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.resources.ExtraProperties;
import mx.com.grupogigante.gestionvivienda.services.ClientesService;
import mx.com.grupogigante.gestionvivienda.services.IClientesService;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.CheckFileConnection;
import mx.com.grupogigante.gestionvivienda.utils.Connection;
import mx.com.grupogigante.gestionvivienda.utils.Constantes;
import mx.com.grupogigante.gestionvivienda.utils.utilsCommon;

import org.apache.log4j.Logger;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

/**
 * @author WSNADM
 *
 */
@Repository
public class CierreVentaDaoImp implements CierreVentaDao {
	private Logger log = Logger.getLogger(CierreVentaDaoImp.class);
	
	
	@Autowired
	IClientesService clientesService;
	
	public utilsCommon utils=new utilsCommon();
	private CheckFileConnection traerArchivo;
	private ResponseGetInfCotizacionDto respInfCot;
	ResponseGetInfCarCliente resGetCarClientesDTO = new ResponseGetInfCarCliente();
	CriteriosGetInfCarCliente criteriosInfCarCliente=new CriteriosGetInfCarCliente();
	ResponseGetInfClienteSap respGetInfClienteSap=new ResponseGetInfClienteSap();
	CriteriosGetInfClienteSap criteriosInfClienteSap=new CriteriosGetInfClienteSap();
	ResponseAddPagosDto resAddPagosDto;
	ResponseGetInfoPagRegDto resGetPagosDto;
	ResponseGetConPagosDto resGetConPagos;
	ResponseCreacionPedidoDto resPedido;
	ResponseObtieneReferenciaDto resReferenciaDto;
	// variables para busq cot y pedidos 
	private JCoTable tableCotHdr;
	private JCoTable tableCotDet1;
	private JCoTable tablePedHdr;
	private JCoTable tablePedDet1;
	private JCoTable tableBillPlan;
	private JCoTable tableClientes;
	//variables para monitor de pagos 
	private JCoTable tablePagHdr;
	private JCoTable tablePagDet;
	private JCoTable tableDirecSoc;
	//variables para conciliacion de pagos
	private JCoTable tablePagDetCon;
	private JCoTable tableDetBankCon;
	private JCoTable tablePoolError;
	private JCoTable tableSubEquipo;
	private ExtraProperties exp = new ExtraProperties();
	
	
	 /**
	 * Método que regresa la consulta de las cotizaciones y pedidos  
	 * 
	 * @param CriteriosGetInfGetCot 
	 * 			criterios de busqueda de cotizaciones  
	 * @return ResponseGetInfCotizacion 
	 * 			objeto de respuesta para obtener todas lista de cotizacines y/o pedidos  
	 * @exception  Exception  
	 */	 
public ResponseGetInfCotizacionDto getBusquedaCot(CriteriosGetInfGetCot criterios) throws ViviendaException {
	

	respInfCot=new ResponseGetInfCotizacionDto();
	traerArchivo = new CheckFileConnection();
	
	String sStatus;
	Connection connect = null;
	sStatus = ArchLogg.leeLogg();
	String subrc = "";
	String bapierror = "";
	
	log.info("FASE CRITERIOS:" + criterios.getFase());
	
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
			JCoFunction function = connect.getFunction("ZCSMF_0043_GET_COT");
			
			// Establece Parametros Import
			function.getImportParameterList().setValue("I_ID_UT_SUP", criterios.getId_ut_sup());
			function.getImportParameterList().setValue("I_USUARIO", criterios.getUsuario());			
			
//			function.getImportParameterList().setValue("I_GET_INFO_Z", "X");
//			function.getImportParameterList().setValue("I_GET_PEND", "X");
			
			//function.getImportParameterList().setValue("I_SAVE_LOG_ERROR", exp.getExtraProperties("logs"));
			
			if(criterios.getFase()!="" && criterios.getFase()!=null )
			{
				function.getImportParameterList().setValue("I_FASE", criterios.getFase());	
			}
			if(criterios.getCotPed()==0){
				 function.getImportParameterList().setValue("I_COTIZACION", "X");
			}else if(criterios.getCotPed()==1)
			{
				function.getImportParameterList().setValue("I_PEDIDO", "X");
			}
			if(criterios.getAccion()==1)//xq va a buscar los clientes 
			{   
				if(criterios.getXmlCot()!="" && criterios.getXmlCot()!=null)
				{	
							JCoTable itHeaderCot = function.getTableParameterList().getTable("IT_COTIZ_IN");
							llenaItHeaderCriteriosCot(extraeCotXml(criterios.getXmlCot()), itHeaderCot);
				}
				if(!criterios.getXmlPed().equals("") && criterios.getXmlPed()!=null )
				{			
				JCoTable itHeaderPed = function.getTableParameterList().getTable("IT_PEDIDO_IN");
				llenaItHeaderCriteriosPed(extraePedXml(criterios.getXmlPed()), itHeaderPed);
				}  
				if(criterios.getXmlEq()!="" && criterios.getXmlEq()!=null)
				{
				JCoTable itHeaderEqu = function.getTableParameterList().getTable("IT_EQUNR_IN");
				llenaItHeaderCriteriosEqu(extraeEquXml(criterios.getXmlEq()), itHeaderEqu);
				}
				if(!criterios.getXmlNoms().equals("")  || !criterios.getXmlNoms2().equals("") || !criterios.getXmlApPats().equals("") || !criterios.getXmlApMats().equals(""))
				{
				JCoTable itClientes = function.getTableParameterList().getTable("IT_CLIENT_IN");
				llenaItHeaderClientes(extraeClientesXml("",criterios.getXmlNoms(),criterios.getXmlNoms2(),criterios.getXmlApPats(),criterios.getXmlApMats(),""), itClientes);
				}
			}else if(criterios.getAccion()==2)//para traer las cotPed con el xml de ids Cliente q selecciono
			{
				if (criterios.getXmlPed()!=null && criterios.getXmlPed()!=""){
					JCoTable itPedidos = function.getTableParameterList().getTable("IT_PEDIDO_IN");
					llenaItHeaderPedidos(extraeClientesXml(criterios.getXmlCli(),criterios.getXmlNoms(),criterios.getXmlNoms2(),criterios.getXmlApPats(),criterios.getXmlApMats(),criterios.getXmlPed()), itPedidos);
				} else {
				    JCoTable itClientes = function.getTableParameterList().getTable("IT_CLIENT_IN");
				    llenaItHeaderClientes(extraeClientesXml(criterios.getXmlCli(),criterios.getXmlNoms(),criterios.getXmlNoms2(),criterios.getXmlApPats(),criterios.getXmlApMats(),""), itClientes);
				}
												
			}

			connect.execute(function);
			// Recupera el estado de determinación de la funcion RFC
			subrc = (String) function.getExportParameterList().getString("E_SUBRC");
			bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
 			if (subrc.equals("00")) 
			{	
				if(criterios.getCotPed()==0)
				{
				tableCotHdr	=function.getTableParameterList().getTable("IT_COTIZ_HDR_OUT");
				tableCotDet1=function.getTableParameterList().getTable("IT_COTIZ_DET_OUT");
				tableSubEquipo = function.getTableParameterList().getTable("IT_COT_EQPS_OUT");
				
				respInfCot.setCotPedHdrList(getCotHdrList(tableCotHdr));
				respInfCot.setObjCotPedHdrList(respInfCot.getCotPedHdrList());
				respInfCot.setCotPedDet1List(getCotDet1List(tableCotDet1));
				respInfCot.setObjCotPedDet1List(respInfCot.getCotPedDet1List());
				respInfCot.setCotizacionSubEquiposList(this.getCotizacionEquipos(tableSubEquipo));
				respInfCot.setObjCotizacionSubEquiposList(respInfCot.getCotizacionSubEquiposList());
				
				
				respInfCot.setCotInfoList(getInfoCotPedInfo(criterios.getAccion(),tableCotHdr,criterios.getUsuario(),criterios.getId_ut_sup(),respInfCot.getCotizacionSubEquiposList()));
				respInfCot.setObjCotInfoList(respInfCot.getCotInfoList());
				respInfCot.setMensaje("SUCCESS");	
				respInfCot.setDescripcion("");
				
				}else{   
					tablePedHdr=function.getTableParameterList().getTable("IT_PED_HDR_OUT");
					tablePedDet1=function.getTableParameterList().getTable("IT_PED_DET_OUT");
					tableSubEquipo = function.getTableParameterList().getTable("IT_COT_EQPS_OUT"); 
					
					respInfCot.setCotPedHdrList(getCotHdrList(tablePedHdr));
					respInfCot.setObjCotPedHdrList(respInfCot.getCotPedHdrList());
					respInfCot.setCotPedDet1List(getCotDet1List(tablePedDet1));
					respInfCot.setObjCotPedDet1List(respInfCot.getCotPedDet1List());
					respInfCot.setCotizacionSubEquiposList(this.getCotizacionEquipos(tableSubEquipo));
					respInfCot.setObjCotizacionSubEquiposList(respInfCot.getCotizacionSubEquiposList());
					
					respInfCot.setCotInfoList(getInfoCotPedInfo(criterios.getAccion(),tablePedHdr,criterios.getUsuario(),criterios.getId_ut_sup(),respInfCot.getCotizacionSubEquiposList()));
					
					
					//Inicia Recupera los datos del cliente(Implementacion para la obtnecion de correo)
					if (respInfCot.getCotInfoList()!=null && respInfCot.getCotInfoList().size()>0){
						 String idCarCliente = this.armaXmlIdCliente(respInfCot.getCotInfoList());//respInfCot.getCotInfoList().get(0).getIdCarCliente();
						 
						// criteriosInfCarCliente.setXmlIds("<criterios><criterio><id_car_cli>"+idCarCliente+"</id_car_cli></criterio></criterios>");
						 criteriosInfCarCliente.setXmlIds(idCarCliente);
						 criteriosInfCarCliente.setId_ut_sup(criterios.getId_ut_sup());
						 criteriosInfCarCliente.setUsuario(criterios.getUsuario());
						 resGetCarClientesDTO = clientesService.getClientes(criteriosInfCarCliente);
						 if(resGetCarClientesDTO.getClientesList().size()>0)
						 {	
							 //respInfCot.getCotInfoList().get(0).setMail(resGetCarClientesDTO.getClientesList().get(0).getMail1());
							 respInfCot.setObjCotInfoList(this.asignaCorreo(respInfCot.getCotInfoList(), resGetCarClientesDTO.getClientesList()));
							
						 }
						 
						 respInfCot.setMensaje("SUCCESS");	
						 respInfCot.setDescripcion("");
					}
					else
					{
						respInfCot.setMensaje("FAULT");	
						respInfCot.setDescripcion("No se encontraron datos");
					}
					//Fin 
					//respInfCot.setObjCotInfoList(respInfCot.getCotInfoList());
				}
			}
			else
			{
				respInfCot.setMensaje("FAULT");	
				respInfCot.setDescripcion(bapierror);
			}
		} 
		catch (ViviendaException excp) 
		{
			log.error("ERROR: ",excp);			
			respInfCot.setMensaje("FAULT");
			respInfCot.setDescripcion(excp.getMessage());
			throw new ViviendaException(excp.getMessage());
		}
	}		
	return respInfCot;
}

private String armaXmlIdCliente(List<CotizacionesDto> lstdatos){
	StringBuffer strXML = new StringBuffer();
	strXML.append("<criterios>");
	for (CotizacionesDto cotizacionesDto : lstdatos) {
		strXML.append("<criterio><id_car_cli>"+cotizacionesDto.getIdCarCliente()+"</id_car_cli></criterio>"); 
	}
	strXML.append("</criterios>");
	return strXML.toString();
}
private List<CotizacionesDto> asignaCorreo(List<CotizacionesDto>  lstOrigenCliente, List<ClientesDto> lstClientes){
	int x=0;
	for (CotizacionesDto cotizaCli : lstOrigenCliente) {
		
		for (ClientesDto clientesDto : lstClientes) {
			if (cotizaCli.getIdCarCliente().trim().equals(clientesDto.getId_car_cli().trim())){
				lstOrigenCliente.get(x).setMail(clientesDto.getMail1());
				break;
			}
			
		}
		x++;
	}
	return lstOrigenCliente;
}
/**
 * Método que llena la tabla de las lista de cotizacionesHdr    
 * 
 * @param JCoTable
 * 			tabla de consulta para obtener los Hdr
 *  @return ArrayList<CotizacionHdrDto>
 * 			lista de CotizacionesHdr  
 */	
private ArrayList<CotizacionHdrDto> getCotHdrList(JCoTable t) 
{
	if (t != null){
		if (t.getNumRows() > 0){
			ArrayList<CotizacionHdrDto> a = new ArrayList<CotizacionHdrDto>();
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				a.add(new CotizacionHdrDto());

				a.get(i).setId_ut_sup(t.getString("ID_UT_SUP"));
				a.get(i).setId_car_cli(t.getString("ID_CAR_CLI"));
				a.get(i).setId_cotiz_z(t.getString("ID_COTIZ_Z"));
				a.get(i).setVbeln_cot(t.getString("VBELN_COT"));
				a.get(i).setVbeln_ped(t.getString("VBELN_PED"));
				a.get(i).setId_equnr(t.getString("ID_EQUNR"));
				a.get(i).setTive(t.getString("TIVE"));
				a.get(i).setTivex(t.getString("TIVEX"));
				a.get(i).setAuart(t.getString("AUART"));
				a.get(i).setAedat(t.getString("AEDAT"));
				a.get(i).setCputm(t.getString("CPUTM"));
				a.get(i).setDatab(t.getString("DATAB"));
				a.get(i).setDatub(t.getString("DATUB"));
				a.get(i).setId_cli_sap(t.getString("ID_CLI_SAP"));
				a.get(i).setNbod(t.getInt("NBOD"));
				a.get(i).setNest(t.getInt("NEST"));
				a.get(i).setM2tot(t.getDouble("M2TOT"));
				a.get(i).setFech_entreg(t.getString("FECH_ENTREG"));
				a.get(i).setDescp(t.getFloat("DESCP"));
				a.get(i).setDescm(t.getFloat("DESCM"));
				a.get(i).setNuml(t.getLong("NUML"));
				a.get(i).setDias_pago(t.getInt("DIAS_PAGO"));
				a.get(i).setSi_no_pasa(t.getChar("SI_NO_PASA"));
				a.get(i).setSpaso(t.getInt("SPASO"));
				a.get(i).setSpasox(t.getString("SPASOX"));
				a.get(i).setNpaso(t.getInt("NPASO"));
				a.get(i).setNpasox(t.getString("NPASOX"));
				a.get(i).setMsgbapi(t.getString("MSGBAPI"));
				a.get(i).setSt_contra(t.getString("ST_CONTRA"));
				t.nextRow();
			}
			return a;
		}
	}
	return null;
}

/**
 * Método que llena la tabla de las lista de cotizacionesDet1    
 * 
 * @param JCoTable
 * 			tabla de consulta para obtener los Det 
 *  @return ArrayList<CotizacionesDet1Dto>
 * 			lista de CotizacionesDet1  
 */	
private ArrayList<CotizacionesDet1Dto> getCotDet1List(JCoTable t) 
{
	if (t != null){
		if (t.getNumRows() > 0){
			ArrayList<CotizacionesDet1Dto> a = new ArrayList<CotizacionesDet1Dto>();
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				a.add(new CotizacionesDet1Dto());

				a.get(i).setId_cotiz_z(t.getString("ID_COTIZ_Z"));
				a.get(i).setVbeln_cot(t.getString("VBELN_COT"));
				a.get(i).setVbeln_ped(t.getString("VBELN_PED"));
				a.get(i).setPosnr_z(t.getInt("POSNR_Z"));
				a.get(i).setPosnr_cot(t.getInt("POSNR_COT"));
				a.get(i).setPosnr_ped(t.getInt("POSNR_PED"));
				a.get(i).setCve_web(t.getString("CVE_WEB"));
				a.get(i).setMatnr(t.getString("MATNR"));
				a.get(i).setDescr(t.getString("DESCR"));
				a.get(i).setNetwr(t.getDouble("NETWR"));
				a.get(i).setNetwrp(t.getDouble("NETWRP"));
				a.get(i).setMoneda(t.getString("MONEDA"));
				t.nextRow();
			}
			return a;
		}
	}
	return null;
}


/**
 * Método que llena la tabla de las lista de cotizacionesDet1    
 * 
 * @param JCoTable
 * 			tabla de consulta para obtener los Det 
 *  @return ArrayList<CotizacionesDet1Dto>
 * 			lista de CotizacionesDet1  
 */	
private ArrayList<EquiposAdicionalesDto> geEqAdList(JCoTable t) 
{
	if (t != null){
		if (t.getNumRows() > 0){
			ArrayList<EquiposAdicionalesDto> a = new ArrayList<EquiposAdicionalesDto>();
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				a.add(new EquiposAdicionalesDto());

				a.get(i).setId_cotiz(t.getString("ID_COTIZ"));
				a.get(i).setId_equnr(t.getString("ID_EQUNR"));
				t.nextRow();
			}
			return a;
		}
	}
	return null;
}
/**
 * Método que llena la tabla de las lista de hdr de pagos   
 * 
 * @param JCoTable
 * 			tabla de consulta para obtener los Hdr
 *  @return ArrayList<PagosHdrDto>
 * 			lista de PagosHdr   
 */	
private ArrayList<PagoHdrDto> getPagosHdrList(JCoTable t) 
{
	if (t != null){
		if (t.getNumRows() > 0){
			ArrayList<PagoHdrDto> a = new ArrayList<PagoHdrDto>();
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				a.add(new PagoHdrDto());

				a.get(i).setVblen(t.getString("VBELN"));
				a.get(i).setKunnr(t.getString("KUNNR"));
				a.get(i).setKunnrtx(t.getString("KUNNRTX"));
				a.get(i).setId_equnr(t.getString("ID_EQUNR"));
				a.get(i).setDpto(utils.convierteEqunr(t.getString("ID_EQUNR")));
				a.get(i).setFregi(t.getString("FREGI"));
				a.get(i).setNetwr_x_pagar(t.getString("NETWR_X_PAGAR"));
				a.get(i).setNetwr_pag(t.getString("NETWR_PAG"));
				a.get(i).setErnam(t.getString("ERNAM"));
				a.get(i).setAedat(utils.convierteFechaVisual(t.getString("AEDAT")));
				a.get(i).setCptum(t.getString("CPTUM"));
				t.nextRow();
			}
			return a;
		}
	}
	return null;
}
private List<PagoHdrDto> getCuentaNumPagos(List<PagoHdrDto> pagoHdrList,CriteriosGetInfPag criteriosGetPagos) throws ViviendaException 

{	
	String stContra = "";
	String tamaño = Integer.toString(pagoHdrList.size());
	
	//ResponseGetInfCotizacionDto getBusquedaCot(CriteriosGetInfGetCot criterios)
	/*CriteriosGetInfGetCot param = new CriteriosGetInfGetCot();
	param.setXmlPed("<criterios><criterio><idPedido>"+pagoHdrList.get(0).getVblen()+"</idPedido></criterio></criterios>");
	param.setCotPed(1);
	param.setAccion(2);
	param.setUsuario(criteriosGetPagos.getUsuario());
	param.setId_ut_sup(criteriosGetPagos.getId_ut_sup());
		
	ResponseGetInfCotizacionDto respuesta = this.getBusquedaCot(param);
	stContra = respuesta.getCotPedHdrList().get(0).getSt_contra();*/
	
	for(int i = 0; i < pagoHdrList.size(); i++){
		pagoHdrList.get(i).setNumPagos(tamaño);
		pagoHdrList.get(i).setStContra(pagoHdrList.get(i).getStContra()!= null ? pagoHdrList.get(i).getStContra() : "00" );
	}
	
	return pagoHdrList;
}

/**
 * Método que llena la tabla de las lista de detalles de pago    
 * 
 * @param JCoTable
 * 			tabla de consulta para obtener los Hdr
 *  @return ArrayList<PagoDetDto>
 * 			lista de PagoDetDto  
 */	
private ArrayList<PagoDetDto> getPagosDetList(JCoTable t) 
{
	if (t != null){
		if (t.getNumRows() > 0){
			ArrayList<PagoDetDto> a = new ArrayList<PagoDetDto>();
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				a.add(new PagoDetDto());

				a.get(i).setFregi(t.getString("FREGI"));
				a.get(i).setConse(t.getInt("CONSE"));
				a.get(i).setFpago(utils.convierteFechaVisual(t.getString("FPAGO")));
				a.get(i).setHpago(t.getString("HPAGO"));
				a.get(i).setRefer(t.getString("REFER"));
				a.get(i).setMonto(t.getFloat("NETWR"));
				a.get(i).setFile_a(t.getString("FILE_A"));
				a.get(i).setValid(t.getString("VALID"));
				if(a.get(i).getValid().equals("X"))
					a.get(i).setValidDesc("Sí");
				else 
					a.get(i).setValidDesc("No");
					
				a.get(i).setErnam_val(t.getString("ERNAM_VAL"));
				a.get(i).setAedat_val(utils.convierteFechaVisual(t.getString("AEDAT_VAL")));
				a.get(i).setCputm_val(t.getString("CPUTM_VAL"));
				a.get(i).setFvalid(t.getString("FVALID"));
				a.get(i).setUpdate(t.getString("UPDATE_REG"));
				a.get(i).setFolioOper(t.getString("FOPER_B"));
				
				a.get(i).setConcepto(t.getString("CONCEPTO"));
				a.get(i).setMedioPago(t.getString("MED_PAGO"));
				a.get(i).setConceptoTxt(t.getString("CONCEPTOX"));
				a.get(i).setMedioPagoTxt(t.getString("MED_PAGOX"));
				a.get(i).setObservaciones(t.getString("OBSERV"));
				
				/*a.get(i).setConcepto("ENG");
				a.get(i).setConceptoTxt("ENGANCHE");				
				a.get(i).setMedioPago("CHQ");
				a.get(i).setMedioPagoTxt("CHEQUE");
				a.get(i).setObservaciones("OBSERVACION TEST");*/
				
				t.nextRow();
			}
			return a;
		}
	}
	return null;
}
/**
 * Método que llena la tabla de las lista de Direcciones de Sociedad  
 * 
 * @param JCoTable
 * 			tabla de consulta para obtener los Hdr
 *  @return ArrayList<DireccionSocDto>
 * 			lista de DireccionSocDto  
 */	
private ArrayList<DireccionSocDto> getDirSocList(JCoTable t) 
{
	if (t != null){
		if (t.getNumRows() > 0){
			ArrayList<DireccionSocDto> a = new ArrayList<DireccionSocDto>();
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				a.add(new DireccionSocDto());

				a.get(i).setVbeln_ped(t.getString("VBELN_PED"));
				a.get(i).setNombreSoc(t.getString("NOMBRE_SOC"));
				a.get(i).setCalle(t.getString("CALLE"));
				a.get(i).setNoext(String.valueOf(t.getInt("NOEXT")));
				a.get(i).setNoint(String.valueOf(t.getInt("NOINT")));
				a.get(i).setColn(t.getString("COLN"));
				a.get(i).setCdpst((t.getString("CDPST")));
				a.get(i).setDlmcp(t.getString("DLMCP"));
				a.get(i).setEstdo(t.getString("ESTDO"));
				a.get(i).setPais((t.getString("PAIS")));
				a.get(i).setTelfn((t.getString("TELFN")));
				a.get(i).setTelof((t.getString("TELOF")));
				a.get(i).setExtnc((t.getString("EXTNC")));
				a.get(i).setTlfmo(t.getString("TLFMO"));
				a.get(i).setMail1(t.getString("MAIL1"));
				a.get(i).setMail2(t.getString("MAIL2"));
				
				t.nextRow();
			}
			return a;
		}
	}
	return null;
}

/**
 * Método que llena la tabla de las lista de Direcciones de Sociedad  
 * 
 * @param JCoTable
 * 			tabla de consulta para obtener los Hdr
 *  @return ArrayList<DireccionSocDto>
 * 			lista de DireccionSocDto  
 */	
private ArrayList<ClienteSapDto> getClientesList(JCoTable t) 
{
	if (t != null){
		if (t.getNumRows() > 0){
			ArrayList<ClienteSapDto> a = new ArrayList<ClienteSapDto>();
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				a.add(new ClienteSapDto());

				a.get(i).setId_cliente_z(t.getString("ID_CAR_CLI"));
				a.get(i).setNombre1C(t.getString("NOMBRE1"));
				a.get(i).setNombre2C(t.getString("NOMBRE2"));
				a.get(i).setApp_patC(t.getString("APP_PAT"));
				a.get(i).setApp_matC(t.getString("APP_MAT"));
				a.get(i).setFch_ncm(utils.convierteFechaVisual(t.getString("FCH_NCM")));
				a.get(i).setMail1C(t.getString("MAIL1"));
				t.nextRow();
			}
			return a;
		}
	}
	return null;
}
/**
 * Método que regresa una lista de los criterios capturados para consultar la tabla de cotizaciones  
 * 
 * @param criterios  
 * 			cadena con los elementos a buscar   
 * @return ArrayList<CotizacionDetDto> 
 * 			lista con los criterios a buscar  
*/	
public ArrayList<CotizacionDetDto> extraeCotXml(String criteriosCot)
{
	ArrayList<CotizacionDetDto> cotListDto = new ArrayList<CotizacionDetDto>();
	try {
		SAXReader reader = new SAXReader();
		org.dom4j.Document doc = reader.read(new StringReader(criteriosCot));
		org.dom4j.Element root = doc.getRootElement();

		for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
			org.dom4j.Element element = (org.dom4j.Element) i.next();
			CotizacionDetDto cotDto = new CotizacionDetDto();
			if(element.node(0).getText()!="")
			{
			cotDto.setVbeln_cot(element.node(0).getText());
			cotListDto.add(cotDto);
			}
		}
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return cotListDto;
}
/**
 * Método que regresa una lista de los criterios capturados para consultar pedidos   
 * 
 * @param criterios  
 * 			cadena con los elementos a buscar   
 * @return ArrayList<CotizacionDetDto> 
 * 			lista con los criterios a buscar  
*/	
public ArrayList<CotizacionDetDto> extraePedXml(String criteriosPed)
{
	ArrayList<CotizacionDetDto> pedListDto = new ArrayList<CotizacionDetDto>();
	try {
		SAXReader reader = new SAXReader();
		org.dom4j.Document doc = reader.read(new StringReader(criteriosPed));
		org.dom4j.Element root = doc.getRootElement();

		for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
			org.dom4j.Element element = (org.dom4j.Element) i.next();
			CotizacionDetDto pedDto = new CotizacionDetDto();
			if(element.node(0).getText()!="")
			{
			pedDto.setVbeln_ped(element.node(0).getText());
			pedListDto.add(pedDto);
			}
		}
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return pedListDto;
}


/**
 * Método que regresa una lista de los criterios capturados para consultar equipos Adicionales   
 * 
 * @param criterios  
 * 			cadena con los elementos a buscar   
 * @return ArrayList<EquiposAdicionalesDto> 
 * 			lista con los criterios a buscar  
*/	
public ArrayList<EquiposAdicionalesDto> extraeEquXml(String criteriosEqu)
{
	ArrayList<EquiposAdicionalesDto> equListDto = new ArrayList<EquiposAdicionalesDto>();
	try {
		SAXReader reader = new SAXReader();
		org.dom4j.Document doc = reader.read(new StringReader(criteriosEqu));
		org.dom4j.Element root = doc.getRootElement();

		for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
			org.dom4j.Element element = (org.dom4j.Element) i.next();
			EquiposAdicionalesDto equDto = new EquiposAdicionalesDto();
			if(element.node(0).getText()!=""){
			equDto.setId_equnr(element.node(0).getText());
			equListDto.add(equDto);
			}
		}
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return equListDto;
}
/**
 * Método que regresa una lista de los criterios capturados para consultar Clientes   
 * 
 * @param criterios  
 * 			cadena con los elementos a buscar   
 * @return ArrayList<CotizacionDetDto> 
 * 			lista con los criterios a buscar  
*/	
public ArrayList<ClientesDto> extraeCliXml(String criteriosCli)
{
	ArrayList<ClientesDto> cliListDto = new ArrayList<ClientesDto>();
	try {
		SAXReader reader = new SAXReader();
		org.dom4j.Document doc = reader.read(new StringReader(criteriosCli));
		org.dom4j.Element root = doc.getRootElement();

		for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
			org.dom4j.Element element = (org.dom4j.Element) i.next();
			ClientesDto cliDto = new ClientesDto();
			if(element.node(0).getText()!="")
			{
			cliDto.setId_car_cli(element.node(0).getText());
			cliListDto.add(cliDto);
			}
		}
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return cliListDto;
}

/**
 * Método que regresa una lista de los criterios capturados para consultar pedidos   
 * 
 * @param criterios  
 * 			cadena con los elementos a buscar   
 * @return ArrayList<CotizacionDetDto> 
 * 			lista con los criterios a buscar  
*/	
public ArrayList<RegistroDto> extraeRegXml(String criteriosReg)
{
	ArrayList<RegistroDto> regListDto = new ArrayList<RegistroDto>();
	try {
		SAXReader reader = new SAXReader();
		org.dom4j.Document doc = reader.read(new StringReader(criteriosReg));
		org.dom4j.Element root = doc.getRootElement();

		for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
			org.dom4j.Element element = (org.dom4j.Element) i.next();
			RegistroDto regDto = new RegistroDto();
			if(element.node(0).getText()!="")
			{
			regDto.setFregi(element.node(0).getText());
			regListDto.add(regDto);
			}
		}
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return regListDto;
}
/**
 * Llena el parametro tables IT_HEADER y al mismo tiempo por cada header
 * manda agegar sus correspondientes lienas en el parametro IT_DETAIL
 * @param listaPedido Lista de pedidos a crear
 * @param itHeader    Parametro Table del RFC
 * @param itDetalle   Parametro Table del RFC
 */
private void llenaItHeaderCriteriosCot(ArrayList<CotizacionDetDto> listaParametros,	JCoTable itHeader) {
 if(!listaParametros.isEmpty()){
	 try {
		 for(int i = 0; i < listaParametros.size(); i++){
			   itHeader.appendRow();
			   itHeader.setValue("VBELN", listaParametros.get(i).getVbeln_cot());
			  }
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
 
 }
}
/**
 * Llena el parametro tables IT_HEADER y al mismo tiempo por cada header
 * manda agegar sus correspondientes lienas en el parametro IT_DETAIL
 * @param listaPedido Lista de pedidos a crear
 * @param itHeader    Parametro Table del RFC
 * @param itDetalle   Parametro Table del RFC
 */
private void llenaItHeaderCriteriosPed(ArrayList<CotizacionDetDto> listaPedidos,JCoTable itHeader) {
 if(!listaPedidos.isEmpty()){
	 try {
		  for(int i = 0; i < listaPedidos.size(); i++){
			   itHeader.appendRow();
			   itHeader.setValue("VBELN", listaPedidos.get(i).getVbeln_ped());
			  }
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}

 }
}
/**
 * Llena el parametro tables IT_HEADER y al mismo tiempo por cada header
 * manda agegar sus correspondientes lienas en el parametro IT_DETAIL
 * @param listaPedido Lista de pedidos a crear
 * @param itHeader    Parametro Table del RFC
 * @param itDetalle   Parametro Table del RFC
 */
private void llenaItHeaderCriteriosEqu(ArrayList<EquiposAdicionalesDto> listaParametros, JCoTable itHeader) {
 if(!listaParametros.isEmpty()){
  for(int i = 0; i < listaParametros.size(); i++){
   itHeader.appendRow();
   itHeader.setValue("ID_EQUNR", listaParametros.get(i).getId_equnr());
  }
 }
}

/**
 * Llena el parametro tables IT_HEADER y al mismo tiempo por cada header
 * manda agegar sus correspondientes lienas en el parametro IT_DETAIL
 * @param listaPedido Lista de pedidos a crear
 * @param itHeader    Parametro Table del RFC
 * @param itDetalle   Parametro Table del RFC
 */
private void llenaItHeaderCriteriosCli(ArrayList<ClientesDto> listaParametros,	                             JCoTable itHeader) {
 if(!listaParametros.isEmpty()){
  for(int i = 0; i < listaParametros.size(); i++){
   itHeader.appendRow();
   itHeader.setValue("ID_CAR_CLI", listaParametros.get(i).getId_car_cli());
  }
 }
}

/**
 * Llena el parametro tables IT_HEADER y al mismo tiempo por cada header
 * manda agegar sus correspondientes lienas en el parametro IT_DETAIL
 * @param listaPedido Lista de pedidos a crear
 * @param itHeader    Parametro Table del RFC
 * @param itDetalle   Parametro Table del RFC
 */
private void llenaItHeaderCriteriosReg(ArrayList<RegistroDto> listaParametros,	                             JCoTable itHeader) {
 if(!listaParametros.isEmpty()){
  for(int i = 0; i < listaParametros.size(); i++){
   itHeader.appendRow();
   itHeader.setValue("FREGI", listaParametros.get(i).getFregi());
  }
 }
}

/**
 * Método que settea los campos de JcoTable al objeto que trae la informacion completa de las cotizaciones 
 * @param JCoTable 
 * 			tabla de busqueda de cotizaciones CotPedHdrList  
 * @return ArrayList<CotizacionesDto> 
 * 			lista con todas las cotizaciones/pedidos rescuperados segun los criterios    
 * @exception  Exception  
 */	

private ArrayList<CotizacionesDto> getInfoCotPedInfo(int bandera,JCoTable CotPedHdrList,String usr,String idUsr,ArrayList<CotizadorSubequiposDto> subequipos) throws ViviendaException 
{
	if (CotPedHdrList != null){
		if (CotPedHdrList.getNumRows() > 0){
			ArrayList<CotizacionesDto> cotInfoList = new ArrayList<CotizacionesDto>();
			CotPedHdrList.firstRow();
			    String id_cliente="";
				for (int i = 0; i < CotPedHdrList.getNumRows(); i++){
									
					 CotizacionesDto cotizacion = new CotizacionesDto();
					 
					 			 
					 for(int j=0; j<subequipos.size(); j++) {
							if(CotPedHdrList.getString("ID_COTIZ_Z").trim().equals(subequipos.get(j).getId_cotiz_z()))
							{
								cotizacion.getCotizacionSubequipos().add(subequipos.get(j));
							}
					 }
					 
					 cotizacion.setIdCotizacion(CotPedHdrList.getString("VBELN_COT"));
					 cotizacion.setIdPedido(CotPedHdrList.getString("VBELN_PED"));
					 cotizacion.setInicioVig(utils.convierteFechaVisual(CotPedHdrList.getString("DATAB")));
					 cotizacion.setFinVig(utils.convierteFechaVisual(CotPedHdrList.getString("DATUB")));
					 cotizacion.setDpto(CotPedHdrList.getString("ID_EQUNR"));
					 cotizacion.setDptoCasa(utils.convierteEqunr(CotPedHdrList.getString("ID_EQUNR")));
					 cotizacion.setSpaso(CotPedHdrList.getString("SPASO"));
					 cotizacion.setSpasox(CotPedHdrList.getString("SPASOX"));
					 cotizacion.setNpaso(CotPedHdrList.getString("NPASO"));
					 cotizacion.setNpasox(CotPedHdrList.getString("NPASOX"));
					 cotizacion.setMje(CotPedHdrList.getString("MSGBAPI"));
					 cotizacion.setIdCotizacionZ(CotPedHdrList.getString("ID_COTIZ_Z"));
					 cotizacion.setStContra(CotPedHdrList.getString("ST_CONTRA"));
					 if(CotPedHdrList.getString("ID_CLI_SAP").equals(""))
					 {
						 cotizacion.setIdCliente(CotPedHdrList.getString("ID_CAR_CLI"));
						 cotizacion.setIdCarCliente(CotPedHdrList.getString("ID_CAR_CLI"));
						 cotizacion.setIdSapCliente(CotPedHdrList.getString("ID_CLI_SAP"));
						 criteriosInfCarCliente.setXmlIds("<criterios><criterio><id_car_cli>"+CotPedHdrList.getString("ID_CAR_CLI")+"</id_car_cli></criterio></criterios>");
						 criteriosInfCarCliente.setId_ut_sup(idUsr);
						 criteriosInfCarCliente.setUsuario(usr);
						 resGetCarClientesDTO = clientesService.getClientes(criteriosInfCarCliente);
						 if(resGetCarClientesDTO.getClientesList().size()>0)
						 {	
						 cotizacion.setNombre(resGetCarClientesDTO.getClientesList().get(0).getNombre1());
						 cotizacion.setSNombre(resGetCarClientesDTO.getClientesList().get(0).getNombre2());
						 cotizacion.setAApat(resGetCarClientesDTO.getClientesList().get(0).getApp_pat());
						 cotizacion.setAMat(resGetCarClientesDTO.getClientesList().get(0).getApp_mat());
						 cotizacion.setFchNac(resGetCarClientesDTO.getClientesList().get(0).getFch_ncm());
						 cotizacion.setMail(resGetCarClientesDTO.getClientesList().get(0).getMail1());
						 cotizacion.setMail2(resGetCarClientesDTO.getClientesList().get(0).getMail2());
						 cotizacion.setTelfn(resGetCarClientesDTO.getClientesList().get(0).getTelfn());
						 cotizacion.setTelof(resGetCarClientesDTO.getClientesList().get(0).getTelof());
						 cotizacion.setTlfmo(resGetCarClientesDTO.getClientesList().get(0).getTlfmo());
						 cotizacion.setExtnc(resGetCarClientesDTO.getClientesList().get(0).getExtnc());
						 cotizacion.setVia_con(resGetCarClientesDTO.getClientesList().get(0).getVia_con());
						 cotizacion.setVia_conx(resGetCarClientesDTO.getClientesList().get(0).getVia_conx());
						  if(resGetCarClientesDTO.getClientesList().get(0).getVisitasClienteList().size()>0)
						 {
						     cotizacion.setVendedor(resGetCarClientesDTO.getClientesList().get(0).getVisitasClienteList().get(0).getSlsman());
						 }
						 }
						 id_cliente=cotizacion.getIdCliente();
					 }
					 else{
						 cotizacion.setIdCliente(CotPedHdrList.getString("ID_CLI_SAP"));
						 cotizacion.setIdCarCliente(CotPedHdrList.getString("ID_CAR_CLI"));
						 cotizacion.setIdSapCliente(CotPedHdrList.getString("ID_CLI_SAP"));
						 criteriosInfClienteSap.setXmlCliSap("<criterios><criterio><idClieteSap>"+cotizacion.getIdSapCliente()+"</idClieteSap></criterio></criterios>");
						 criteriosInfClienteSap.setId_ut_sup(idUsr);
						 criteriosInfClienteSap.setUsuario(usr);
						 respGetInfClienteSap = clientesService.getNamesClientesSap(criteriosInfClienteSap);
						 if(respGetInfClienteSap.getClienteInfo().size()!=0)
						 {
						 cotizacion.setNombre(respGetInfClienteSap.getClienteInfo().get(0).getNombre1());
						 cotizacion.setSNombre(respGetInfClienteSap.getClienteInfo().get(0).getNombre2());
						 cotizacion.setAApat(respGetInfClienteSap.getClienteInfo().get(0).getApp_pat());
						 cotizacion.setAMat(respGetInfClienteSap.getClienteInfo().get(0).getApp_mat());
						 cotizacion.setFchNac(respGetInfClienteSap.getClienteInfo().get(0).getFch_ncm());
						 cotizacion.setMail(respGetInfClienteSap.getClienteInfo().get(0).getMail1());
						 }
						 id_cliente=cotizacion.getIdCliente();
					}
					 
					 if(bandera==1){
					 if(i==0)
					 {
						 cotInfoList.add(cotizacion);
					 }
					 else
					 {
						 Boolean existe = false;
						 for(int j=0; j<cotInfoList.size(); j++)
						 {
							 if(id_cliente.equals(cotInfoList.get(j).getIdCliente()))
							 {
								 existe=true;
								 break;
							 }
						 }
						 
						 if(!existe)
						 {
							 cotInfoList.add(cotizacion);
						 }
					 }
					 }else if(bandera==2){
						 cotInfoList.add(cotizacion);
					 }
					 
					 CotPedHdrList.nextRow();
				}
				
			return cotInfoList;
		}
	}
	return null;
}

/**
 * Método que regresa una objeto de AddVisitaClientes el cliente ya existe   
 * 
 * @param criteriosClienteDto 
 * 			criterios de busqueda de clientes  
 * @param listaNombreArchivos
 * 			Lista que contiene los nombres de los archivos cargados para 
 * 			el registro de pago
 * @return ResponseAddClienteDto 
 * 			objeto de respuesta para agregar las visitas de los  clientes 
 * @throws ViviendaException 
 * @exception  Exception  
 */	
public ResponseAddPagosDto addPagosMensuales(CriteriosGetInfGetCot criteriosInfPagos, List<String> listaNombreArchivos) throws ViviendaException{
	resAddPagosDto=new ResponseAddPagosDto();
	traerArchivo = new CheckFileConnection();	
	
	String sStatus;
	Connection connect = null;
	sStatus = ArchLogg.leeLogg();
	String subrc = "";
	String bapierror = "";
	String fregi = "";
	
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
			function= connect.getFunction("ZCSMF_0053_REG_PAGO");
			// Establece Parametros Import
			function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosInfPagos.getId_ut_sup());
			function.getImportParameterList(  ).setValue("I_USUARIO", criteriosInfPagos.getUsuario());
			
			if (criteriosInfPagos.getTime()!=null){
				if(criteriosInfPagos.getTime().equals("1")) //PARA LA BUSQUEDA POR MONITOR
				{	
					function.getImportParameterList().setValue("I_FIRST_PAY", "X");
				}
			}
			
			//else
			//{
			//	function.getImportParameterList().setValue("I_FIRST_PAY", "");
			//}
			JCoTable itDetPag = function.getTableParameterList().getTable("IT_PAGO_P_DET");
			llenaDetailCriteriosPag(extraePagosXml(criteriosInfPagos.getXmlPagos()), itDetPag, listaNombreArchivos);
			JCoTable itHdrPag = function.getTableParameterList().getTable("IT_PAGO_P_HDR");
			llenaHeaderCriteriosPag(criteriosInfPagos.getPedido(), itHdrPag,criteriosInfPagos.getFregi());
			connect.execute(function);
			// Recupera el estado de determinación de la funcion RFC
			subrc = (String) function.getExportParameterList().getString("E_SUBRC");
			bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
			fregi = (String) function.getExportParameterList().getString("E_FREGI");
			String idPedido = (String) function.getExportParameterList().getString("E_VBELN");
			String idCliente = (String) function.getExportParameterList().getString("E_KUNNR");
			if (subrc.equals("00")) 
			{	
				resAddPagosDto.setMensaje("SUCCESS");	
				resAddPagosDto.setDescripcion("");
				resAddPagosDto.setFregi(fregi);
				resAddPagosDto.setIdCliente(idCliente);
				resAddPagosDto.setIdPedido(idPedido);
				resAddPagosDto.setTipoRegistroPago(subrc);
			} else {
			
				if (subrc.equals("01")) {	//TOTO EXITOSO
					resAddPagosDto.setMensaje("SUCCESS01");	
					resAddPagosDto.setDescripcion("");
					resAddPagosDto.setFregi(fregi);
					resAddPagosDto.setTipoRegistroPago(subrc);
					resAddPagosDto.setIdCliente(idCliente);
					resAddPagosDto.setIdPedido(idPedido);
				} else {
					if (subrc.equals("02")) {	
						resAddPagosDto.setMensaje("SUCCESS02");	
						resAddPagosDto.setDescripcion(bapierror);
						resAddPagosDto.setFregi(fregi);
						resAddPagosDto.setTipoRegistroPago(subrc);
						resAddPagosDto.setIdCliente(idCliente);
						resAddPagosDto.setIdPedido(idPedido);
					} else {
						if (subrc.equals("04")) {	
							resAddPagosDto.setMensaje("FAULT");	
							resAddPagosDto.setDescripcion(bapierror);
							resAddPagosDto.setFregi(fregi);
							resAddPagosDto.setTipoRegistroPago(subrc);
						}else{
							if (subrc.equals("03")) {	//SE REGISTRA EL PRIMER PAGO PERO NO ACTUALIZO EL ESTATUS
								resAddPagosDto.setMensaje("SUCCESS03");	
							    resAddPagosDto.setDescripcion(bapierror);
							    resAddPagosDto.setFregi(fregi);
							    resAddPagosDto.setTipoRegistroPago(subrc);
							    resAddPagosDto.setIdCliente(idCliente);
								resAddPagosDto.setIdPedido(idPedido);
						    } else {
						    	if (subrc.equals("06")) {	//SE REGISTRA EL SEGUNDO PAGO PERO NO ACTUALIZO EL ESTATUS
						    	   resAddPagosDto.setMensaje("SUCCESS06");	
							       resAddPagosDto.setDescripcion(bapierror);
							       resAddPagosDto.setFregi(fregi);
							       resAddPagosDto.setTipoRegistroPago(subrc);
							       resAddPagosDto.setIdCliente(idCliente);
								   resAddPagosDto.setIdPedido(idPedido);
						         } else {
							       resAddPagosDto.setMensaje("FAULT");	
							       resAddPagosDto.setDescripcion(bapierror);
							       resAddPagosDto.setFregi(fregi);
						         }
						    } 	
						}
					}
				}
			}
			
		} 
		catch (Exception excp) 
		{
			log.error("ERROR: ",excp);			
			resAddPagosDto.setMensaje("FAULT");
			resAddPagosDto.setDescripcion(excp.getMessage());
			throw new ViviendaException(excp.getMessage());			
		}
	}		
	return resAddPagosDto;	
}
/**
 * Método que regresa una lista de los pagos capturados para insertar 
 * 
 * @param criterios  
 * 			cadena con los pagos a insertar    
 * @return ArrayList<PagoDetDto> 
 * 			lista con los pagos a insertar  
*/	
public ArrayList<PagoDetDto> extraePagosXml(String criteriosPagos)
{
	ArrayList<PagoDetDto> pagosList = new ArrayList<PagoDetDto>();
	try {
		SAXReader reader = new SAXReader();
		org.dom4j.Document doc = reader.read(new StringReader(criteriosPagos));
		org.dom4j.Element root = doc.getRootElement();

		for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
			org.dom4j.Element element = (org.dom4j.Element) i.next();
		
			PagoDetDto pagosDto = new PagoDetDto();
			if(element.node(0).getText()!=null &&  element.node(0).getText()!="")
				{pagosDto.setFpago(element.node(0).getText());}
			if(element.node(1).getText()!=null &&  element.node(1).getText()!="")
				{pagosDto.setHpago(element.node(1).getText());}
			if(element.node(2).getText()!=null &&  element.node(2).getText()!="")
				{pagosDto.setMonto(Float.parseFloat(element.node(2).getText()));}
			if(element.node(3).getText()!=null &&  element.node(3).getText()!="")
				{pagosDto.setRefer(element.node(3).getText());}
			if(element.node(4).getText()!=null &&  element.node(4).getText()!="")
				{pagosDto.setUpdate(element.node(4).getText());}
			if(element.node(5).getText()!=null &&  element.node(5).getText()!="")
				{pagosDto.setFregi(element.node(5).getText());}
			if(element.node(6).getText()!=null && element.node(6).getText()!="")
				{pagosDto.setConse(Integer.parseInt(element.node(6).getText()));}
			if(element.node(7).getText()!=null && element.node(7).getText()!="")
				{pagosDto.setFolioOper(element.node(7).getText());}
			if(element.node(8).getText()!=null && element.node(8).getText()!="")
				{pagosDto.setFile_a(element.node(8).getText());}
			if(element.node(9).getText()!=null && element.node(9).getText()!="")
				{pagosDto.setConcepto(element.node(9).getText());}
			if(element.node(10).getText()!=null && element.node(10).getText()!="")
				{pagosDto.setMedioPago(element.node(10).getText());}
			if(element.node(11).getText()!=null && element.node(11).getText()!="")
				{pagosDto.setObservaciones(element.node(11).getText());}
			pagosList.add(pagosDto);		
		   
		}
		
	} catch (Exception e) {
		log.error("ERROR: ",e);			
	}
	return pagosList;
}


/**
 * Llena el parametro tables IT_HEADER y al mismo tiempo por cada header
 * manda agegar sus correspondientes lienas en el parametro IT_DETAIL
 * @param listaPedido Lista de pedidos a crear
 * @param itHeader    Parametro Table del RFC
 * @param itDetalle   Parametro Table del RFC
 */
private void llenaHeaderCriteriosPag(String pedido,	JCoTable itHeader,String fregi) {
  try {
		 	   itHeader.appendRow();
			   itHeader.setValue("VBELN",pedido);
			   if(fregi!="" && fregi!=null)
			   {
				 itHeader.setValue("FREGI",fregi);    
			   }
		
	} catch (Exception e) {
		log.error("ERROR: ",e);			
	}
}

/**
 * Llena el parametro tables IT_HEADER y al mismo tiempo por cada header
 * manda agegar sus correspondientes lienas en el parametro IT_DETAIL
 * @param listaPagos  Lista de pagos a crear
 * @param itHeader    Parametro Table del RFC
 * @param listaNombreArchivos   Lista que contiene el nombre de los archivos cargados
 */
private void llenaDetailCriteriosPag(ArrayList<PagoDetDto> listaPagos,	JCoTable itHeader, List<String> listaNombreArchivos) {
 if(!listaPagos.isEmpty()){
	 try {
		 for(int i = 0; i < listaPagos.size(); i++){
			   itHeader.appendRow();
			   itHeader.setValue("FPAGO", utils.convierteFecha(listaPagos.get(i).getFpago()));
			   itHeader.setValue("HPAGO",listaPagos.get(i).getHpago());
			   itHeader.setValue("NETWR", listaPagos.get(i).getMonto() + "");
			   itHeader.setValue("REFER", listaPagos.get(i).getRefer());
			   
			   itHeader.setValue("CONCEPTO", listaPagos.get(i).getConcepto());
			   itHeader.setValue("MED_PAGO", listaPagos.get(i).getMedioPago());
			   itHeader.setValue("OBSERV", listaPagos.get(i).getObservaciones());
			   
			   itHeader.setValue("UPDATE_REG", listaPagos.get(i).getUpdate());
			   if(listaPagos.get(i).getFregi()!="" && listaPagos.get(i).getFregi()!=null )
			   {
				   itHeader.setValue("FREGI", listaPagos.get(i).getFregi());
				   itHeader.setValue("CONSE", listaPagos.get(i).getConse());
			   }
			   itHeader.setValue("FOPER_B",listaPagos.get(i).getFolioOper());
			   itHeader.setValue("FILE_A", listaNombreArchivos.size() > 0 ? listaNombreArchivos.get(i) : listaPagos.get(i).getFile_a());
			   			   
			   itHeader.setValue("FILENAM1", listaNombreArchivos.size() > 0 ? listaNombreArchivos.get(i) : listaPagos.get(i).getFile_a());
			   itHeader.setValue("TIPO_REG", Constantes.TIPO_REGISTRO_MANUAL);
			   
			  }
	} catch (Exception e) {
		log.error("ERROR: ",e);			
	}
 
 }
}

/**
 * Método que regresa la consulta de las cotizaciones y pedidos  
 * 
 * @param CriteriosGetInfGetCot 
 * 			criterios de busqueda de cotizaciones  
 * @return ResponseGetInfCotizacion 
 * 			objeto de respuesta para obtener todas lista de cotizacines y/o pedidos  
 * @exception  Exception  
 */	 
public ResponseGetInfoPagRegDto getBusquedaPagos(CriteriosGetInfPag criteriosGetPagos) throws ViviendaException {

resGetPagosDto=new ResponseGetInfoPagRegDto();
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
	    if (Connection.getConnect() == null)  {
			//connect = new Connection(ArchLogg.getSapSystem());
	    	connect = new Connection(ArchLogg.getSapSystem());
		}  else   {
			connect = Connection.getConnect();
		}
		// Establece RFC a ejecutar en SAP
		JCoFunction function = connect.getFunction("ZCSMF_0054_GET_COMP_PAGO");
		// Establece Parametros Import
		function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosGetPagos.getId_ut_sup());
		function.getImportParameterList().setValue("I_USUARIO", criteriosGetPagos.getUsuario());
		
		if(criteriosGetPagos.getAccion()==1){
		
			if(criteriosGetPagos.getXmlPed()!="") {			
			     JCoTable itHeaderPed = function.getTableParameterList().getTable("IT_VBELN_IN");
			     llenaItHeaderCriteriosPed(extraePedXml(criteriosGetPagos.getXmlPed()), itHeaderPed);
			}
				
	     }    
		
		if(criteriosGetPagos.getAccion()==2) {
			if(criteriosGetPagos.getXmlReg()!="") {
			    JCoTable itHeaderReg = function.getTableParameterList().getTable("IT_FREGI_IN");
			    llenaItHeaderCriteriosReg(extraeRegXml(criteriosGetPagos.getXmlReg()), itHeaderReg);
			}
	     }
		
		connect.execute(function);
		// Recupera el estado de determinación de la funcion RFC
		subrc = (String) function.getExportParameterList().getString("E_SUBRC");
		bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
			if (subrc.equals("00")) 
		{	
				tablePagHdr=function.getTableParameterList().getTable("IT_PAGOS_P_HDR_OUT");
				tablePagDet=function.getTableParameterList().getTable("IT_PAGOS_P_DET_OUT");
				tableDirecSoc=function.getTableParameterList().getTable("IT_DIRECCION_SOC_OUT");
				resGetPagosDto.setPagoHdrList(getPagosHdrList(tablePagHdr));
				resGetPagosDto.setPagoDetList(getPagosDetList(tablePagDet));
				resGetPagosDto = complementaInfoPagos(resGetPagosDto);
				resGetPagosDto.setObjPagoHdrList(this.getCuentaNumPagos(resGetPagosDto.getPagoHdrList(),criteriosGetPagos));
				resGetPagosDto.setMensaje("SUCCESS");	
				resGetPagosDto.setDescripcion("");
		
		}
		else
		{
			resGetPagosDto.setMensaje("FAULT");	
			resGetPagosDto.setDescripcion(bapierror);
		}
	} 
	catch (Exception excp) 
	{
		log.error("ERROR: ",excp);			
		resGetPagosDto.setMensaje("FAULT");
		resGetPagosDto.setDescripcion(excp.getMessage());
		throw new ViviendaException(excp.getMessage());		
	}
}		
return resGetPagosDto;
}

/**
 * Método que settea los campos de JcoTable al hdr    
 * 
 * @param JCoTable 
 * 			tabla de pagod hdr  
 * @return PagosHdrByDetailDto 
 * 			objeto de respuesta para obtener a una lista de pagos hdr con detalles   
 * @exception  Exception  
 */	
private ResponseGetInfoPagRegDto complementaInfoPagos(ResponseGetInfoPagRegDto resp) 
{
	ResponseGetInfoPagRegDto res=new ResponseGetInfoPagRegDto();
	
	List<PagoHdrDto> respHdr=resp.getPagoHdrList();
	if(respHdr!=null)
	{
		for (int i = 0; i < respHdr.size(); i++) {
			for (int j = 0; j < resp.getPagoDetList().size(); j++) {
				
				if(respHdr.get(i).getFregi().equals(resp.getPagoDetList().get(j).getFregi()))
				{
					respHdr.get(i).getPagosDetList().add(resp.getPagoDetList().get(j));
					
				}
				res.setPagoHdrList(respHdr);
			}
		}
	}
	return res;
	
}

/**
 * Método que llena la tabla de las lista de hdr de pagos   
 * 
 * @param JCoTable
 * 			tabla de consulta para obtener los Hdr
 *  @return ArrayList<PagosHdrDto>
 * 			lista de PagosHdr   
 */	
private ArrayList<DetalleExtractoDto> getExtractoList(JCoTable t) 
{
	if (t != null){
		if (t.getNumRows() > 0){
			ArrayList<DetalleExtractoDto> a = new ArrayList<DetalleExtractoDto>();
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				a.add(new DetalleExtractoDto());

				a.get(i).setFregi(t.getString("FREGI"));
				a.get(i).setConse(t.getInt("CONSE"));
				a.get(i).setFpago(utils.convierteFechaVisual(t.getString("FPAGO")));
				a.get(i).setHpago(t.getString("HPAGO"));
				a.get(i).setFextracto(t.getString("FEXTRACTO"));
				a.get(i).setDescr(t.getString("DESCR"));
				a.get(i).setCargo(t.getString("CARGO"));
				a.get(i).setAbono(t.getFloat("ABONO"));
				a.get(i).setRefer(t.getString("REFER"));
				a.get(i).setConcep(t.getString("CONCEP"));
				a.get(i).setNpaso(t.getInt("NPASO"));
				a.get(i).setSpaso(t.getInt("SPASO"));
				a.get(i).setAedat(t.getString("AEDAT"));
				a.get(i).setCputm(t.getString("CPUTM"));
				a.get(i).setErnam(t.getString("ERNAM"));
				t.nextRow();
			}
			return a;
		}
	}
	return null;
}
/**
 * Método que llena la tabla de las lista de Log errores
 * 
 * @param JCoTable
 * 			tabla de consulta para obtener los errores
 *  @return ArrayList<PoolErrorDto>
 * 			lista de PoolErrorDto   
 */	
private ArrayList<PoolErrorDto> getErrorList(JCoTable t) 
{
	if (t != null){
		if (t.getNumRows() > 0){
			ArrayList<PoolErrorDto> a = new ArrayList<PoolErrorDto>();
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				a.add(new PoolErrorDto());

				a.get(i).setFregi(t.getString("FREGI"));
				a.get(i).setConse(t.getInt("CONSE"));
				a.get(i).setExtract(t.getString("FEXTRAC"));
				a.get(i).setBapi_msg(t.getString("MENSAJE"));
				t.nextRow();
			}
			return a;
		}
	}
	return null;
}
/**
 * Método que regresa una lista de los detalles ya ligados   
 * 
 * @param criterios  
 * 			cadena con los elementos a ligar  
 * @return ArrayList<PagoDetDto> 
 * 			lista con los criterios a ligar  
*/	
public ArrayList<PagoDetDto> extraeListadoDetXml(String criteriosDet)
{
	ArrayList<PagoDetDto> pagoDetList = new ArrayList<PagoDetDto>();
	try {
		SAXReader reader = new SAXReader();
		org.dom4j.Document doc = reader.read(new StringReader(criteriosDet));
		org.dom4j.Element root = doc.getRootElement();

		for (java.util.Iterator i = root.elementIterator( "element" ); i.hasNext(); ) {
			org.dom4j.Element element = (org.dom4j.Element) i.next();
			PagoDetDto detDto = new PagoDetDto();
			detDto.setFregi(element.node(0).getText());
			detDto.setConse(Integer.parseInt(element.node(1).getText()));
			detDto.setFvalid(element.node(2).getText());
			detDto.setMonto(Float.parseFloat(element.node(3).getText()));
			pagoDetList.add(detDto);
		}
		
	} catch (Exception e) {
		log.error("ERROR: ",e);					
	}
	return pagoDetList;
}
/**
 * Método que regresa una lista de los detalles ya ligados   
 * 
 * @param criterios  
 * 			cadena con los elementos a ligar  
 * @return ArrayList<PagoDetDto> 
 * 			lista con los criterios a ligar  
*/	
public ArrayList<DetalleExtractoDto> extraeListadoExtXml(String criteriosExt)
{
	ArrayList<DetalleExtractoDto> extList = new ArrayList<DetalleExtractoDto>();
	try {
		SAXReader reader = new SAXReader();
		org.dom4j.Document doc = reader.read(new StringReader(criteriosExt));
		org.dom4j.Element root = doc.getRootElement();

		for (java.util.Iterator i = root.elementIterator( "element" ); i.hasNext(); ) {
			org.dom4j.Element element = (org.dom4j.Element) i.next();
			DetalleExtractoDto extDto = new DetalleExtractoDto();
			extDto.setFregi(element.node(0).getText());
			if(element.node(1).getText()!="")
				extDto.setConse(Integer.parseInt(element.node(1).getText()));
			
			extDto.setFextracto(element.node(2).getText());
			extDto.setAbono(Float.parseFloat(element.node(3).getText()));
			extList.add(extDto);
		}
		
	} catch (Exception e) {
		log.error("ERROR: ",e);			
	}
	return extList;
}

/**
 * Método que regresa una lista de los pagos capturados para insertar 
 * 
 * @param criterios  
 * 			cadena con los pagos a insertar    
 * @return ArrayList<PagoDetDto> 
 * 			lista con los pagos a insertar  
*/	
public ArrayList<ClienteSapDto> extraeClientesXml(String ids,String noms,String noms2,String aps,String mats,String idPedido)
{
	ArrayList<ClienteSapDto> clientesList = new ArrayList<ClienteSapDto>();
	try {
		if(noms!="" && noms!=null)
		{	
		SAXReader reader = new SAXReader();
		org.dom4j.Document doc = reader.read(new StringReader(noms));
		org.dom4j.Element root = doc.getRootElement();

		for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
			org.dom4j.Element element = (org.dom4j.Element) i.next();
			ClienteSapDto clienteDto = new ClienteSapDto();
			clienteDto.setNombre1C(element.node(0).getText());
			clientesList.add(clienteDto);
		  }
		}
		if(noms2!="" && noms2!=null)
		{	
		SAXReader reader = new SAXReader();
		org.dom4j.Document doc = reader.read(new StringReader(noms2));
		org.dom4j.Element root = doc.getRootElement();

		for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
			org.dom4j.Element element = (org.dom4j.Element) i.next();
			ClienteSapDto clienteDto = new ClienteSapDto();
			clienteDto.setNombre2C(element.node(0).getText());
			clientesList.add(clienteDto);
		  }
		}
		if(aps!="" && aps!=null)
		{	
		SAXReader reader = new SAXReader();
		org.dom4j.Document doc = reader.read(new StringReader(aps));
		org.dom4j.Element root = doc.getRootElement();

		for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
			org.dom4j.Element element = (org.dom4j.Element) i.next();
			ClienteSapDto clienteDto = new ClienteSapDto();
			clienteDto.setApp_patC(element.node(0).getText());
			clientesList.add(clienteDto);
		  }
		}
		if(mats!="" && mats!=null)
		{	
		SAXReader reader = new SAXReader();
		org.dom4j.Document doc = reader.read(new StringReader(mats));
		org.dom4j.Element root = doc.getRootElement();

		for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
			org.dom4j.Element element = (org.dom4j.Element) i.next();
			ClienteSapDto clienteDto = new ClienteSapDto();
			clienteDto.setApp_matC(element.node(0).getText());
		   clientesList.add(clienteDto);
		  }
		}
		if(ids!="" && ids!=null)
		{	
		SAXReader reader = new SAXReader();
		org.dom4j.Document doc = reader.read(new StringReader(ids));
		org.dom4j.Element root = doc.getRootElement();

		for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
			org.dom4j.Element element = (org.dom4j.Element) i.next();
			ClienteSapDto clienteDto = new ClienteSapDto();
			clienteDto.setId_cliente_z(element.node(0).getText());
		   clientesList.add(clienteDto);
		  }
		}
		
		if(idPedido!="" && idPedido!=null)
		{	
		SAXReader reader = new SAXReader();
		org.dom4j.Document doc = reader.read(new StringReader(idPedido));
		org.dom4j.Element root = doc.getRootElement();

		for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
			org.dom4j.Element element = (org.dom4j.Element) i.next();
			ClienteSapDto clienteDto = new ClienteSapDto();
			clienteDto.setIdPedido(element.node(0).getText());
		    clientesList.add(clienteDto);
		  }
		}
		
	} catch (Exception e) {
		log.error("ERROR: ",e);				
	}
	return clientesList;
}

/**
 * Llena el parametro tables IT_HEADER y al mismo tiempo por cada header
 * manda agegar sus correspondientes lienas en el parametro IT_DETAIL
 * @param listaPedido Lista de pedidos a crear
 * @param itHeader    Parametro Table del RFC
 * @param itDetalle   Parametro Table del RFC
 */
private void llenaItHeaderDet(ArrayList<PagoDetDto> listaParametros,	JCoTable itHeader) {
 if(!listaParametros.isEmpty()){
	 try {
		 for(int i = 0; i < listaParametros.size(); i++){
			 log.info("PAGO:" + listaParametros.get(i));
			   itHeader.appendRow();
			   itHeader.setValue("FREGI", listaParametros.get(i).getFregi());
			   itHeader.setValue("CONSE", listaParametros.get(i).getConse());
			   itHeader.setValue("FVALID", listaParametros.get(i).getFvalid());
			   itHeader.setValue("NETWR", listaParametros.get(i).getMonto());
			  }
	} catch (Exception e) {
		log.error("ERROR: ",e);				
	}
 
 }
}
/**
 * Llena el parametro tables IT_HEADER y al mismo tiempo por cada header
 * manda agegar sus correspondientes lienas en el parametro IT_DETAIL
 * @param listaPedido Lista de pedidos a crear
 * @param itHeader    Parametro Table del RFC
 * @param itDetalle   Parametro Table del RFC
 */
private void llenaItHeaderExt(ArrayList<DetalleExtractoDto> listaParametros,	JCoTable itHeader) {
 if(!listaParametros.isEmpty()){
	 try {
		 for(int i = 0; i < listaParametros.size(); i++){
			 log.info("DET EXTRACTO:" + listaParametros.get(i));
			   itHeader.appendRow();
			   itHeader.setValue("FREGI", listaParametros.get(i).getFregi());
			   itHeader.setValue("CONSE", listaParametros.get(i).getConse());
			   itHeader.setValue("FEXTRACTO", listaParametros.get(i).getFextracto());
			   itHeader.setValue("ABONO", listaParametros.get(i).getAbono());
			   
			  }
	} catch (Exception e) {
		log.error("ERROR: ",e);			
	}
 
 }
}
/**
 * Llena el parametro tables IT_HEADER y al mismo tiempo por cada header
 * manda agegar sus correspondientes lienas en el parametro IT_DETAIL
 * @param listaPedido Lista de pedidos a crear
 * @param itHeader    Parametro Table del RFC
 * @param itDetalle   Parametro Table del RFC
 */
private void llenaItHeaderClientes(ArrayList<ClienteSapDto> listaParametros,	JCoTable itHeader) {
 if(!listaParametros.isEmpty()){
	 try {
		 for(int i = 0; i < listaParametros.size(); i++){
			   itHeader.appendRow();
			   itHeader.setValue("ID_CAR_CLI", listaParametros.get(i).getId_cliente_z());
			   itHeader.setValue("NOMBRE1", listaParametros.get(i).getNombre1C());
			   itHeader.setValue("NOMBRE2", listaParametros.get(i).getNombre2C());
			   itHeader.setValue("APP_PAT", listaParametros.get(i).getApp_patC());
			   itHeader.setValue("APP_MAT", listaParametros.get(i).getApp_matC());
			  }
	} catch (Exception e) {
		log.error("ERROR: ",e);				
	}
 
 }
}

/**
 * Llena el parametro tables IT_HEADER y al mismo tiempo por cada header
 * manda agegar sus correspondientes lienas en el parametro IT_DETAIL
 * @param listaPedido Lista de pedidos a crear
 * @param itHeader    Parametro Table del RFC
 * @param itDetalle   Parametro Table del RFC
 */
private void llenaItHeaderPedidos(ArrayList<ClienteSapDto> listaParametros,	JCoTable itHeader) {
 if(!listaParametros.isEmpty()){
	 try {
		 for(int i = 0; i < listaParametros.size(); i++){
			   itHeader.appendRow();
			   itHeader.setValue("VBELN", listaParametros.get(i).getIdPedido());
			  
	      }
	} catch (Exception e) {
		log.error("ERROR: ",e);					
	}
 
 }
}

/**
 * Método que regresa la consulta de las cotizaciones y pedidos  
 * 
 * @param CriteriosGetInfGetCot 
 * 			criterios de busqueda de cotizaciones  
 * @return ResponseGetInfCotizacion 
 * 			objeto de respuesta para obtener todas lista de cotizacines y/o pedidos  
 * @exception  Exception  
 */	 
public ResponseGetConPagosDto getPagosConciliacion(CriteriosGetInfPag criteriosGetPagos) throws ViviendaException {

resGetConPagos=new ResponseGetConPagosDto();
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
		JCoFunction function = connect.getFunction("ZCSMF_0055_GET_SAVE_CONCIL");
		// Establece Parametros Import
		function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosGetPagos.getId_ut_sup());
		function.getImportParameterList().setValue("I_USUARIO", criteriosGetPagos.getUsuario());
		//function.getImportParameterList().setValue("I_SAVE_LOG_ERROR", exp.getExtraProperties("logs"));
		if(criteriosGetPagos.getAccion()==1)
		{	
		function.getImportParameterList().setValue("I_UPDATE","");
		}
		else
		{
		function.getImportParameterList().setValue("I_UPDATE","X");
		JCoTable itHeaderDet = function.getTableParameterList().getTable("IT_PAGOS_P_DET");
		llenaItHeaderDet(extraeListadoDetXml(criteriosGetPagos.getXmlLigadosDet()), itHeaderDet);
		JCoTable itHeaderExtr = function.getTableParameterList().getTable("IT_EXTRACTO_FI");
		llenaItHeaderExt(extraeListadoExtXml(criteriosGetPagos.getXmlLigadosExt()), itHeaderExtr);
		}	
		connect.execute(function);
		// Recupera el estado de determinación de la funcion RFC
		subrc = (String) function.getExportParameterList().getString("E_SUBRC");
		bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
			if (subrc.equals("00")) 
		{	
				if(criteriosGetPagos.getAccion()==1)
				{
				tablePagDetCon=function.getTableParameterList().getTable("IT_PAGOS_P_DET");
				tableDetBankCon=function.getTableParameterList().getTable("IT_EXTRACTO_FI");
				resGetConPagos.setExtractoPagosInfo(getPagosDetList(tablePagDetCon));
				resGetConPagos.setExtractoBankInfo(getExtractoList(tableDetBankCon));
				resGetConPagos.setObjExtractoPagosInfo(resGetConPagos.getExtractoPagosInfo());
				resGetConPagos.setObjExtractoBankInfo(resGetConPagos.getExtractoBankInfo());
				resGetConPagos.setNum_reg(function.getExportParameterList().getString("E_NUM_REG"));
				resGetConPagos.setRango_pago(function.getExportParameterList().getString("E_RANGO_PAGO"));
				}
				resGetConPagos.setMensaje("SUCCESS");	
				resGetConPagos.setDescripcion("");
				
				
		
		}
		else
		{
			tablePoolError=function.getTableParameterList().getTable("IT_SPOOL_ERROR_OUT");
			resGetConPagos.setPoolErrorInfo(getErrorList(tablePoolError));
			resGetConPagos.setObjPoolErrorInfo(resGetConPagos.getPoolErrorInfo());
			resGetConPagos.setMensaje("FAULT");	
			resGetConPagos.setDescripcion(bapierror);
		}
	} 
	catch (Exception excp) 
	{
		log.error("ERROR: ",excp);			
		resGetConPagos.setMensaje("FAULT");
		resGetConPagos.setDescripcion(excp.getMessage());
		throw new ViviendaException(excp.getMessage());				
	}
}		
return resGetConPagos;
}


/**
 * Método que regresa la consulta de las cotizaciones y pedidos  
 * 
 * @param CriteriosGetInfGetCot 
 * 			criterios de busqueda de clientes   
 * @return ResponseGetInfCotizacion 
 * 			objeto de respuesta para obtener todas lista de los clientes matchcode  
 * @exception  Exception  
 */	 
public ResponseGetInfCotizacionDto getClientesList(CriteriosGetInfGetCot criteriosGetCli) throws ViviendaException {

respInfCot=new ResponseGetInfCotizacionDto();
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
		JCoFunction function = connect.getFunction("ZCSMF_0043_GET_COT");
		// Establece Parametros Import
		//function.getImportParameterList().setValue("I_SAVE_LOG_ERROR", exp.getExtraProperties("logs"));
		function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosGetCli.getId_ut_sup());
		function.getImportParameterList().setValue("I_USUARIO", criteriosGetCli.getUsuario());
		if(criteriosGetCli.getCotPed()==0)
		{	
			function.getImportParameterList().setValue("I_COTIZACION", "X");
			if(criteriosGetCli.getXmlCot()!="" && criteriosGetCli.getXmlCot()!=null)
			{	
			JCoTable itHeaderCot = function.getTableParameterList().getTable("IT_COTIZ_IN");
			llenaItHeaderCriteriosCot(extraeCotXml(criteriosGetCli.getXmlCot()), itHeaderCot);
			}
		}
		else
		{
			function.getImportParameterList().setValue("I_PEDIDO", "X");
		}
		
		if(criteriosGetCli.getXmlEq()!="" && criteriosGetCli.getXmlEq()!=null)
		{
		JCoTable itHeaderEqu = function.getTableParameterList().getTable("IT_EQUNR_IN");
		llenaItHeaderCriteriosEqu(extraeEquXml(criteriosGetCli.getXmlEq()), itHeaderEqu);
		}
		if(criteriosGetCli.getXmlNoms()!="" ||criteriosGetCli.getXmlNoms()!=null || criteriosGetCli.getXmlNoms2()!="" ||criteriosGetCli.getXmlNoms2()!=null || criteriosGetCli.getXmlApPats()!="" ||criteriosGetCli.getXmlApPats()!=null || criteriosGetCli.getXmlApMats()!="" ||criteriosGetCli.getXmlApMats()!=null)
		{
//		JCoTable itClientes = function.getTableParameterList().getTable("IT_CLIENT_IN");
//		llenaItHeaderClientes(extraeClientesXml(criteriosGetCli.getXmlNoms(),criteriosGetCli.getXmlNoms2(),criteriosGetCli.getXmlApPats(),criteriosGetCli.getXmlApMats()), itClientes);
		}
		
		connect.execute(function);
		// Recupera el estado de determinación de la funcion RFC
		subrc = (String) function.getExportParameterList().getString("E_SUBRC");
		bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
			if (subrc.equals("00")) 
		{	
				
				if(criteriosGetCli.getCotPed()==0)
				{
					tableCotHdr	=function.getTableParameterList().getTable("IT_COTIZ_HDR_OUT");
				}
					
				tableClientes=function.getTableParameterList().getTable("IT_CLIENT_IN");
				respInfCot.setClientesList(getClientesList(tableClientes));
				respInfCot.setObjClientesList(respInfCot.getClientesList());
				respInfCot.setMensaje("SUCCESS");	
				respInfCot.setDescripcion("");
		}
		else
		{
			
			respInfCot.setMensaje("FAULT");	
			respInfCot.setDescripcion(bapierror);
		}
	} 
	catch (Exception excp) 
	{
		log.error("ERROR: ",excp);			
		respInfCot.setMensaje("FAULT");
		respInfCot.setDescripcion(excp.getMessage());
		throw new ViviendaException(excp.getMessage());			
	}
}		
return respInfCot;
}

/**
 * Método que regresa la consulta de las cotizaciones y pedidos  
 * 
 * @param CriteriosGetInfGetCot 
 * 			criterios de busqueda de clientes   
 * @return ResponseGetInfCotizacion 
 * 			objeto de respuesta para obtener todas lista de los clientes matchcode  
 * @exception  Exception  
 */	 
public ResponseCreacionPedidoDto addPedido(CriteriosGetInfGetCot criteriosGet) throws ViviendaException {

resPedido=new ResponseCreacionPedidoDto();
traerArchivo = new CheckFileConnection();	

String sStatus;
Connection connect = null;
sStatus = ArchLogg.leeLogg();
String subrc = "";
String bapierror = "";
String evbeln = "";


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
		JCoFunction function = connect.getFunction("ZCSMF_0056_CRT_PED");
		// Establece Parametros Import
		function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosGet.getId_ut_sup());
		function.getImportParameterList().setValue("I_USUARIO", criteriosGet.getUsuario());
		function.getImportParameterList().setValue("I_ID_COTIZ", criteriosGet.getCotPed());
		connect.execute(function);
		// Recupera el estado de determinación de la funcion RFC
		subrc = (String) function.getExportParameterList().getString("E_SUBRC");
		bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
		evbeln = (String) function.getExportParameterList().getString("E_VBELN");
		
		if (subrc.equals("00")) 
		{	
			resPedido.setIdPedido(evbeln);
			resPedido.setMensaje("SUCCESS");	
		}
		else
		{			
			resPedido.setMensaje("FAULT");	
			resPedido.setDescripcion(bapierror);
		}
	} 
	catch (Exception excp) 
	{
		log.error("ERROR: ",excp);			
		resPedido.setMensaje("FAULT");
		resPedido.setDescripcion(excp.getMessage());
		throw new ViviendaException(excp.getMessage());			
	}
}		
return resPedido;
}

private ArrayList<CotizadorSubequiposDto> getCotizacionEquipos(JCoTable t) 
{
	ArrayList<CotizadorSubequiposDto> a = new ArrayList<CotizadorSubequiposDto>();
	
	if (t != null){
		if (t.getNumRows() > 0){				
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){					
				CotizadorSubequiposDto cequi = new CotizadorSubequiposDto();						
				cequi.setId_cotiz_z(t.getString("ID_COTIZ_Z"));
				cequi.setPosnr_z(t.getString("POSNR_Z"));
				cequi.setId_equnr(t.getString("ID_EQUNR"));
				cequi.setId_equnrx(t.getString("ID_EQUNRX"));
				cequi.setTipo(t.getString("TIPO"));
				cequi.setNetwr(t.getString("NETWR"));
				cequi.setMoneda(t.getString("MONEDA"));			
				a.add(cequi);					
				t.nextRow();
			}				
		}
	}
	return a;
}

/**
 * Método que regresa la consulta de la referencia de un pago. 
 * @param String 
 * 			Cotizacion-Pedido  
 * @return ResponseObtieneReferenciaDto 
 * 			objeto de respuesta para obtener la referencia de un pago.  
 * @exception  ViviendaException  
 */	 
public ResponseObtieneReferenciaDto getReferencia(String conceptoReferencia) throws ViviendaException {
	resReferenciaDto = new ResponseObtieneReferenciaDto();
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
		    if (Connection.getConnect() == null)  {
				//connect = new Connection(ArchLogg.getSapSystem());
		    	connect = new Connection(ArchLogg.getSapSystem());
			}  else   {
				connect = Connection.getConnect();
			}
			// Establece RFC a ejecutar en SAP
			JCoFunction function = connect.getFunction("ZCSMF_0140_GET_PAID_REFER");
			// Establece Parametros Import
			function.getImportParameterList().setValue("I_VBELN", conceptoReferencia);
			
			connect.execute(function);
			// Recupera el estado de determinación de la funcion RFC
			subrc = (String) function.getExportParameterList().getString("E_SUBRC");
			bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
			if (subrc.equals("00")) 
			{	
				resReferenciaDto.setReferencia(function.getExportParameterList().getString("O_REFER"));
				resReferenciaDto.setMensaje("SUCCESS");
				resReferenciaDto.setDescripcion("");
			}
			else
			{
				resReferenciaDto.setMensaje("FAULT");
				resReferenciaDto.setDescripcion(bapierror);
			}
		} 
		catch (Exception excp) 
		{
			log.error("ERROR: ",excp);			
			resReferenciaDto.setMensaje("FAULT");
			resReferenciaDto.setDescripcion(excp.getMessage());
			throw new ViviendaException(excp.getMessage());		
		}
	}		
	return resReferenciaDto;
}


}