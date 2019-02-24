/**
 *  Clase creada por Osvaldo Rodriguez Martinez (SACTI consultores) con fecha de 24/09/2012
 */
package mx.com.grupogigante.gestionvivienda.dao;

import java.util.ArrayList;
import java.util.List;

import mx.com.grupogigante.gestionvivienda.controllers.ticket.TicketController;
import mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.PedidosDetalleDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.PedidosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CotizadorBillPlaningDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CotizadorDetalleDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CotizadorHeaderDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CotizadorSubequiposDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CriteriosAccionesCotizadorDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CriteriosObtieneEquipoCotizacionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CriteriosSimuladorDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.ResponseCotizacionActionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.ResponseObtieneCotizacionBaseDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.ResponseObtieneSubEquiposCotizacionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.SubEquiposCotizacionDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.resources.ExtraProperties;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.CheckFileConnection;
import mx.com.grupogigante.gestionvivienda.utils.Connection;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

/**
 * @author osvaldo
 *
 */

@Repository
public class SimuladorDaoImpl implements SimuladorDao{
	private Logger log = Logger.getLogger(SimuladorDaoImpl.class);
	private ExtraProperties exp = new ExtraProperties();
	CheckFileConnection traerArchivo;
	
	
	public ResponseCotizacionActionDto obtieneCotizacionBase (CriteriosSimuladorDto criterios) throws ViviendaException {
		ResponseCotizacionActionDto resCot = new ResponseCotizacionActionDto();
		ResponseObtieneCotizacionBaseDto resp= new ResponseObtieneCotizacionBaseDto();
		Object respbase= new Object();
		
		traerArchivo = new CheckFileConnection();
		
		JCoTable tablaHeader;
		JCoTable tablaDetalle;
		JCoTable tablaBillPlaning;
		JCoTable tablaSubEquipos;
		
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
				JCoFunction function = connect.getFunction("ZCSMF_0041_GET_COT_BASE");
				
				function.getImportParameterList().setValue("I_WEB", "X");
				
				if(criterios.getAccion().equals("cancelar"))
				{					
					function.getImportParameterList().setValue("I_CANCL", "X");
					function.getImportParameterList().setValue("I_COTIZ_ORIG", criterios.getId_cotiz_z());
					function.getImportParameterList().setValue("I_ID_EQUNR", criterios.getId_equnr());
					tablaSubEquipos = function.getTableParameterList().getTable("IT_EQPS_ADIC");
					llenaTablaSubEquiposCancelTraspaso(criterios.getCotizacionSubEquiposList(),tablaSubEquipos,criterios);							
				}
				else if(criterios.getAccion().equals("traspasar"))
				{
					function.getImportParameterList().setValue("I_TRASP", "X");
					function.getImportParameterList().setValue("I_COTIZ_ORIG", criterios.getId_cotiz_z());
					function.getImportParameterList().setValue("I_EQUNR_ORIG", criterios.getId_equnr());
					function.getImportParameterList().setValue("I_ID_EQUNR", criterios.getEquipo_select());
					tablaSubEquipos = function.getTableParameterList().getTable("IT_EQPS_ADIC");
					llenaTablaSubEquiposCancelTraspaso(criterios.getCotizacionSubEquiposList(),tablaSubEquipos,criterios);
				}
				else
				{
					function.getImportParameterList().setValue("I_ID_EQUNR", criterios.getId_equnr());
				}
				
				function.getImportParameterList().setValue("I_ID_UT_SUP", criterios.getI_id_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", criterios.getI_usuario());
				
				
				connect.execute(function);
				
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				if (subrc.equals("00"))
				{
					tablaHeader = function.getTableParameterList().getTable("IT_COT_HDR");
					tablaDetalle = function.getTableParameterList().getTable("IT_COT_DET");
					tablaBillPlaning = function.getTableParameterList().getTable("IT_BILL_PLAN");
					tablaSubEquipos = function.getTableParameterList().getTable("IT_COT_EQPS");
					
					resp.setCotizacionHeaderList(getCotizacionHeader(tablaHeader));
					resp.setCotizacionDetalleList(getCotizacionDetalle(tablaDetalle));
					resp.setCotizacionBillPlanList(getCotizacionPlain(tablaBillPlaning));
					resp.setCotizacionSubEquiposList(getCotizacionEquipos(tablaSubEquipos));
					
					resp=setDetalleAndBplaning(resp.getCotizacionHeaderList(), resp.getCotizacionDetalleList(), resp.getCotizacionBillPlanList(), resp.getCotizacionSubEquiposList());
					respbase=resp;
					resp.setMensaje("SUCCESS");	
					resp.setDescripcion("");	
				}
				else
				{
					resp.setMensaje("FAULT");	
					resp.setDescripcion(bapierror);					
				}				
			} 
			catch (Exception excp) 
			{
				log.error("ERROR: ",excp);
				resp.setMensaje("FAULT");
				resp.setDescripcion(excp.getMessage());
				throw new ViviendaException(excp.getMessage());
			}
		}
		resCot.setResCotizacion(resp);
		resCot.setResCotizacionBase(respbase);
		return resCot;
	}
	
	private ArrayList<CotizadorHeaderDto> getCotizacionHeader(JCoTable t) 
	{
		ArrayList<CotizadorHeaderDto> a = new ArrayList<CotizadorHeaderDto>();
		if (t != null){
			if (t.getNumRows() > 0){				
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){					
					CotizadorHeaderDto chd = new CotizadorHeaderDto();						
					chd.setId_ut_sup(t.getString("ID_UT_SUP"));
					chd.setId_car_cli(t.getString("ID_CAR_CLI"));
					chd.setId_cotiz_z(t.getString("ID_COTIZ_Z"));
					chd.setVbeln_cot(t.getString("VBELN_COT"));
					chd.setVbeln_ped(t.getString("VBELN_PED"));
					chd.setId_equnr(t.getString("ID_EQUNR"));
					chd.setAuart(t.getString("AUART"));
					chd.setAedat(t.getString("AEDAT"));
					chd.setCptum(t.getString("CPUTM"));
					chd.setDatab(t.getString("DATAB"));
					chd.setDatub(t.getString("DATUB"));
					chd.setTive(t.getString("TIVE"));
					chd.setId_cli_sap(t.getString("ID_CLI_SAP"));
					chd.setNbod(t.getInt("NBOD"));
					chd.setNest(t.getInt("NEST"));
					chd.setM2tot(t.getFloat("M2TOT"));
					chd.setFech_entreg(t.getString("FECH_ENTREG"));
					chd.setDescp(t.getString("DESCP"));
					chd.setDescm(t.getString("DESCM"));
					chd.setNuml(t.getInt("NUML"));
					chd.setSi_no_pasa(t.getString("SI_NO_PASA"));
					chd.setDescp_r(t.getString("DESCP_R"));
					chd.setDescm_r(t.getString("DESCM_R"));
					a.add(chd);					
					t.nextRow();
				}
			}
		}
		return a;
	}
	
	private CotizadorHeaderDto getCotizacionStructHeader(JCoStructure s) 
	{
		CotizadorHeaderDto chd = new CotizadorHeaderDto();
		if (s != null){											
				chd.setId_ut_sup(s.getString("ID_UT_SUP"));
				chd.setId_car_cli(s.getString("ID_CAR_CLI"));
				chd.setId_cotiz_z(s.getString("ID_COTIZ_Z"));
				chd.setVbeln_cot(s.getString("VBELN_COT"));
				chd.setVbeln_ped(s.getString("VBELN_PED"));
				chd.setId_equnr(s.getString("ID_EQUNR"));
				chd.setAuart(s.getString("AUART"));
				chd.setAedat(s.getString("AEDAT"));
				chd.setCptum(s.getString("CPUTM"));
				chd.setDatab(s.getString("DATAB"));
				chd.setDatub(s.getString("DATUB"));
				chd.setTive(s.getString("TIVE"));
				chd.setId_cli_sap(s.getString("ID_CLI_SAP"));
				chd.setNbod(s.getInt("NBOD"));
				chd.setNest(s.getInt("NEST"));
				chd.setM2tot(s.getFloat("M2TOT"));
				chd.setFech_entreg(s.getString("FECH_ENTREG"));
				chd.setDescp(s.getString("DESCP"));
				chd.setDescm(s.getString("DESCM"));
				chd.setNuml(s.getInt("NUML"));
				chd.setSi_no_pasa(s.getString("SI_NO_PASA"));	
				chd.setDescp_r(s.getString("DESCP_R"));
				chd.setDescm_r(s.getString("DESCM_R"));
			}
		return chd;
	}
	
	private ArrayList<CotizadorDetalleDto> getCotizacionDetalle(JCoTable t)
	{
		ArrayList<CotizadorDetalleDto> a = new ArrayList<CotizadorDetalleDto>();
		
		if (t != null){
			if (t.getNumRows() > 0){				
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){					
					CotizadorDetalleDto cdet = new CotizadorDetalleDto();						
					cdet.setId_cotiz_z(t.getString("ID_COTIZ_Z"));
					cdet.setPosnr_z(t.getString("POSNR_Z"));
					cdet.setPosnr_cot(t.getString("POSNR_COT"));
					cdet.setPosnr_ped(t.getString("POSNR_PED"));
					cdet.setCve_web(t.getString("CVE_WEB"));
					cdet.setDescr(t.getString("DESCR"));
					cdet.setNetwr(t.getString("NETWR"));
					cdet.setNetwrp(t.getString("NETWRP"));
					cdet.setMoneda(t.getString("MONEDA"));
					cdet.setMatnr(t.getString("MATNR"));
					
					a.add(cdet);					
					t.nextRow();
				}				
			}
		}
		return a;
	}
	
	private ArrayList<CotizadorBillPlaningDto> getCotizacionPlain(JCoTable t) 
	{
		ArrayList<CotizadorBillPlaningDto> a = new ArrayList<CotizadorBillPlaningDto>();
		
		if (t != null){
			if (t.getNumRows() > 0){				
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){					
					CotizadorBillPlaningDto cplan = new CotizadorBillPlaningDto();						
					cplan.setId_cotiz_z(t.getString("ID_COTIZ_Z"));
					cplan.setPosnr_z(t.getString("POSNR_Z"));
					cplan.setConse(t.getInt("CONSE"));
					cplan.setConce(t.getString("CONCE"));
					cplan.setConcex(t.getString("CONCEX"));
					cplan.setFlim(t.getString("FLIM"));
					cplan.setTotal(t.getString("TOTAL"));
					cplan.setSim1(t.getFloat("SIM1"));
					cplan.setSim2(t.getFloat("SIM2"));			
					a.add(cplan);					
					t.nextRow();
				}				
			}
		}
		return a;
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
	
	
	private ResponseObtieneCotizacionBaseDto setDetalleAndBplaning(ArrayList<CotizadorHeaderDto> header, ArrayList<CotizadorDetalleDto> detalle, ArrayList<CotizadorBillPlaningDto> planing, ArrayList<CotizadorSubequiposDto> subequipos)
	{
		ResponseObtieneCotizacionBaseDto resp= new ResponseObtieneCotizacionBaseDto();
		
		for(int i=0; i<header.size(); i++)
		{
			for(int j=0; j<detalle.size(); j++)
			{
				if(header.get(i).getId_cotiz_z().equals(detalle.get(j).getId_cotiz_z()))
				{
					header.get(i).getCotizacionDetalleList().add(detalle.get(j));
				}
			}
		}
		
		for(int i=0; i<header.size(); i++)
		{
			for(int j=0; j<planing.size(); j++)
			{
				if(header.get(i).getId_cotiz_z().equals(planing.get(j).getId_cotiz_z()))
				{
					header.get(i).getCotizacionBillPlanList().add(planing.get(j));
				}
			}
		}
		
		for(int i=0; i<header.size(); i++)
		{
			for(int j=0; j<subequipos.size(); j++)
			{
				if(header.get(i).getId_cotiz_z().equals(subequipos.get(j).getId_cotiz_z()))
				{
					header.get(i).getCotizacionSubequipos().add(subequipos.get(j));
				}
			}
		}
		
		resp.setCotizacionHeaderList(header);
		
		return resp;
	}
	
	public ResponseObtieneSubEquiposCotizacionDto obtieneSubEquiposCotizacion (CriteriosObtieneEquipoCotizacionDto criterios) throws ViviendaException {
		ResponseObtieneSubEquiposCotizacionDto resp= new ResponseObtieneSubEquiposCotizacionDto();
		
		traerArchivo = new CheckFileConnection();
		
		JCoTable tablaSubEquipos;
		
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
				JCoFunction function = connect.getFunction("ZCSMF_0047_GET_EQS_ADICS");
				
				function.getImportParameterList().setValue("I_ID_UT_SUP", criterios.getI_id_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", criterios.getI_usuario());
				function.getImportParameterList().setValue("I_DATAB", criterios.getI_datab());
				function.getImportParameterList().setValue("I_TYPE", criterios.getI_type());
				
				connect.execute(function);
				
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				if (subrc.equals("00")) 
				{
					tablaSubEquipos = function.getTableParameterList().getTable("IT_EQPS_ADICS");					
					resp.setSubEquiposCotizacion(getCotizacionSubEquipos(tablaSubEquipos));
					
					resp.setMensaje("SUCCESS");	
					resp.setDescripcion("");	
				}
				else
				{
					resp.setMensaje("FAULT");	
					resp.setDescripcion(bapierror);					
				}				
			} 
			catch (Exception excp) 
			{
				log.error("ERROR: ",excp);
				resp.setMensaje("FAULT");
				resp.setDescripcion(excp.getMessage());
				throw new ViviendaException(excp.getMessage());
			}
		}
		return resp;
	}
	
	private ArrayList<SubEquiposCotizacionDto> getCotizacionSubEquipos(JCoTable t) 
	{
		ArrayList<SubEquiposCotizacionDto> a = new ArrayList<SubEquiposCotizacionDto>();
		
		if (t != null){
			if (t.getNumRows() > 0){				
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){					
					SubEquiposCotizacionDto sequi = new SubEquiposCotizacionDto();						
					sequi.setId_cotiz_z(t.getString("ID_COTIZ_Z"));
					sequi.setPosnr_z(t.getInt("POSNR_Z"));
					sequi.setId_equnr(t.getString("ID_EQUNR"));
					sequi.setId_equnrx(t.getString("ID_EQUNRX"));
					sequi.setTipo(t.getString("TIPO"));
					sequi.setNetwr(t.getString("NETWR"));
					sequi.setMoneda(t.getString("MONEDA"));			
					a.add(sequi);					
					t.nextRow();
				}				
			}
		}
		return a;
	}
	
	public ResponseCotizacionActionDto setAdminCotizacion (CriteriosSimuladorDto criterios, ArrayList<CotizadorDetalleDto> cotizacionDetalleList, ArrayList<CotizadorBillPlaningDto> cotizacionBillPlanList, ArrayList<CotizadorSubequiposDto> cotizacionSubEquiposList) throws ViviendaException {
		ResponseCotizacionActionDto resCot = new ResponseCotizacionActionDto();
		ResponseObtieneCotizacionBaseDto resp= new ResponseObtieneCotizacionBaseDto();
		Object respbase= new Object();
		
		traerArchivo = new CheckFileConnection();
		
		JCoStructure structHeader;
		JCoTable tablaHeader;
		JCoTable tablaDetalle;
		JCoTable tablaBillPlaning;
		JCoTable tablaSubEquipos;
		JCoTable tablaSubEquiposSel;
		
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
				JCoFunction function = connect.getFunction("ZCSMF_0042_SAVE_COT");

				function.getImportParameterList().setValue("I_USUARIO", criterios.getI_usuario());
				function.getImportParameterList().setValue("I_ID_UT_SUP", criterios.getI_id_ut_sup());
				function.getImportParameterList().setValue("I_EQPS_ADIC", criterios.getI_eqps_adic());
				function.getImportParameterList().setValue("I_SIMULA", criterios.getI_simula());
				function.getImportParameterList().setValue("I_GUARDA", criterios.getI_guarda());
				function.getImportParameterList().setValue("I_CANCL", criterios.getI_cancl());
				function.getImportParameterList().setValue("I_TRASP", criterios.getI_trasp());
				function.getImportParameterList().setValue("I_LOG", "X");
				function.getImportParameterList().setValue("I_WEB", "X");
				function.getImportParameterList().setValue("I_COTIZ_ORIG", criterios.getCotizacionZ());
				function.getImportParameterList().setValue("I_EQUNR_ORIG", criterios.getId_equnr());
				
				
				structHeader = function.getChangingParameterList().getStructure("COT_HDR");
				llenaEstructuraHeader(criterios, structHeader);
				tablaDetalle = function.getTableParameterList().getTable("IT_COT_DET");
				llenaTablaDetalleCotizador(cotizacionDetalleList,tablaDetalle);
				tablaBillPlaning = function.getTableParameterList().getTable("IT_BILL_PLAN");
				llenaTablaPlanCotizador(cotizacionBillPlanList,tablaBillPlaning);
				tablaSubEquipos = function.getTableParameterList().getTable("IT_COT_EQPS");
				llenaTablaSubEquiposCotizador(cotizacionSubEquiposList,tablaSubEquipos,criterios);
				tablaSubEquiposSel = function.getTableParameterList().getTable("IT_EQPS_ADIC");
				llenaTablaSubEquiposCancelTraspaso(criterios.getCotizacionSubEquiposList(),tablaSubEquiposSel,criterios);
				log.info("Header "+structHeader+" Detalle "+tablaDetalle+" BillinPlan "+tablaBillPlaning+" SubEquipos "+tablaSubEquipos);
				//System.out.println("OK");
				connect.execute(function);
				
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				
				if (subrc.equals("00")) 
				{
					structHeader = function.getChangingParameterList().getStructure("COT_HDR");
					tablaDetalle = function.getTableParameterList().getTable("IT_COT_DET");
					tablaBillPlaning = function.getTableParameterList().getTable("IT_BILL_PLAN");
					tablaSubEquipos = function.getTableParameterList().getTable("IT_COT_EQPS");
					
					resp.getCotizacionHeaderList().add(getCotizacionStructHeader(structHeader));
					resp.setCotizacionDetalleList(getCotizacionDetalle(tablaDetalle));
					resp.setCotizacionBillPlanList(getCotizacionPlain(tablaBillPlaning));
					resp.setCotizacionSubEquiposList(getCotizacionEquipos(tablaSubEquipos));
					
					resp=setDetalleAndBplaning(resp.getCotizacionHeaderList(), resp.getCotizacionDetalleList(), resp.getCotizacionBillPlanList(), resp.getCotizacionSubEquiposList());
					resp.setE_id_cotiz_z((String) function.getExportParameterList().getString("E_ID_COTIZ_Z"));
					resp.setE_id_cotiz_sap((String) function.getExportParameterList().getString("E_ID_COTIZ_SAP"));
					resp.setE_vpnm((String) function.getExportParameterList().getString("E_VPNM"));
					resp.setE_vpnu((String) function.getExportParameterList().getString("E_VPNU"));
					
					resp.setMensaje("SUCCESS");	
					resp.setDescripcion("");	
				}
				else
				{
					resp.setMensaje("FAULT");	
					resp.setDescripcion(bapierror);					
				}			
			} 
			catch (Exception excp) 
			{
				log.error("ERROR: ",excp);							
				resp.setMensaje("FAULT");
				resp.setDescripcion(excp.getMessage());
				throw new ViviendaException(excp.getMessage());
			}
		}
		if(!criterios.getAccion().equals("simular"))
		{
			respbase=resp;
			resCot.setResCotizacionBase(respbase);
		}
		
		resCot.setResCotizacion(resp);		
		return resCot;
	}	
	
	private void llenaEstructuraHeader(CriteriosSimuladorDto criterios, JCoStructure jcoStructHeader) {		
		jcoStructHeader.setValue("ID_UT_SUP", criterios.getI_id_ut_sup());
		jcoStructHeader.setValue("ID_CAR_CLI", criterios.getId_car_cli());
		jcoStructHeader.setValue("ID_COTIZ_Z", criterios.getId_cotiz_z());
		jcoStructHeader.setValue("VBELN_COT", criterios.getVbeln_cot());
		jcoStructHeader.setValue("VBELN_PED", criterios.getVbeln_ped());
		
		if(criterios.getFrom().equals("traspasar"))
		{
			jcoStructHeader.setValue("ID_EQUNR", criterios.getEquipo_select());
		}
		else
		{
			jcoStructHeader.setValue("ID_EQUNR", criterios.getId_equnr());
		}	
		
		jcoStructHeader.setValue("TIVE", criterios.getTive());
		jcoStructHeader.setValue("TIVEX", criterios.getTivex());
		jcoStructHeader.setValue("AUART", criterios.getAuart());
		jcoStructHeader.setValue("AEDAT", criterios.getAedat());
		jcoStructHeader.setValue("CPUTM", criterios.getCptum());
		jcoStructHeader.setValue("DATAB", criterios.getDatab());
		jcoStructHeader.setValue("DATUB", criterios.getDatub());
		jcoStructHeader.setValue("ID_CLI_SAP", criterios.getId_cli_sap());
		jcoStructHeader.setValue("NBOD", criterios.getNbod());
		jcoStructHeader.setValue("NEST", criterios.getNest());
		jcoStructHeader.setValue("M2TOT", criterios.getM2tot());
		jcoStructHeader.setValue("FECH_ENTREG", criterios.getFech_entreg());
		jcoStructHeader.setValue("DESCP", criterios.getDescp());
		jcoStructHeader.setValue("DESCM", criterios.getDescm());
		jcoStructHeader.setValue("NUML", criterios.getNuml());
		jcoStructHeader.setValue("DIAS_PAGO", criterios.getDias_pago());
		jcoStructHeader.setValue("SI_NO_PASA", criterios.getSi_no_pasa());
		jcoStructHeader.setValue("SPASO", criterios.getSpaso());
		jcoStructHeader.setValue("SPASOX", criterios.getSpasox());
		jcoStructHeader.setValue("NPASO", criterios.getNpaso());
		jcoStructHeader.setValue("NPASOX", criterios.getNpasox());
		jcoStructHeader.setValue("MSGBAPI", criterios.getMsgbapi());
		jcoStructHeader.setValue("DESCP_R", criterios.getDescp_r());
		jcoStructHeader.setValue("DESCM_R", criterios.getDescm_r());
	}
	
	private void llenaTablaDetalleCotizador(ArrayList<CotizadorDetalleDto> listaDetalleCotizador, JCoTable jcoTableDetalleCotizador) {
		if(!listaDetalleCotizador.isEmpty()){
			for(int i = 0; i < listaDetalleCotizador.size(); i++){
				jcoTableDetalleCotizador.appendRow();				
				jcoTableDetalleCotizador.setValue("ID_COTIZ_Z", listaDetalleCotizador.get(i).getId_cotiz_z());
				jcoTableDetalleCotizador.setValue("VBELN_COT", "");
				jcoTableDetalleCotizador.setValue("VBELN_PED", "");
				jcoTableDetalleCotizador.setValue("POSNR_Z", listaDetalleCotizador.get(i).getPosnr_z());
				jcoTableDetalleCotizador.setValue("POSNR_COT", listaDetalleCotizador.get(i).getPosnr_cot());
				jcoTableDetalleCotizador.setValue("POSNR_PED", listaDetalleCotizador.get(i).getPosnr_ped());
				jcoTableDetalleCotizador.setValue("CVE_WEB", listaDetalleCotizador.get(i).getCve_web());
				jcoTableDetalleCotizador.setValue("MATNR", listaDetalleCotizador.get(i).getMatnr());
				jcoTableDetalleCotizador.setValue("DESCR", listaDetalleCotizador.get(i).getDescr());
				jcoTableDetalleCotizador.setValue("NETWR", listaDetalleCotizador.get(i).getNetwr());
				jcoTableDetalleCotizador.setValue("NETWRP", listaDetalleCotizador.get(i).getNetwrp());
				jcoTableDetalleCotizador.setValue("MONEDA", listaDetalleCotizador.get(i).getMoneda());
			}
		}
	}
	
	private void llenaTablaPlanCotizador(ArrayList<CotizadorBillPlaningDto> listaPlanCotizador, JCoTable jcoTablePlanCotizador) {
		if(!listaPlanCotizador.isEmpty()){
			for(int i = 0; i < listaPlanCotizador.size(); i++){
				jcoTablePlanCotizador.appendRow();				
				jcoTablePlanCotizador.setValue("ID_COTIZ_Z", listaPlanCotizador.get(i).getId_cotiz_z());
				jcoTablePlanCotizador.setValue("POSNR_Z", listaPlanCotizador.get(i).getPosnr_z());
				jcoTablePlanCotizador.setValue("VBELN_COT", "");
				jcoTablePlanCotizador.setValue("VBELN_PED", "");
				jcoTablePlanCotizador.setValue("CONSE", listaPlanCotizador.get(i).getConse());
				jcoTablePlanCotizador.setValue("CONCE", listaPlanCotizador.get(i).getConce());
				jcoTablePlanCotizador.setValue("CONCEX", listaPlanCotizador.get(i).getConcex());
				jcoTablePlanCotizador.setValue("FLIM", listaPlanCotizador.get(i).getFlim());
				jcoTablePlanCotizador.setValue("TOTAL", listaPlanCotizador.get(i).getTotal());
				jcoTablePlanCotizador.setValue("SIM1", listaPlanCotizador.get(i).getSim1());
				jcoTablePlanCotizador.setValue("SIM2", listaPlanCotizador.get(i).getSim2());
			}
		}
	}
	
	private void llenaTablaSubEquiposCotizador(ArrayList<CotizadorSubequiposDto> listaSubEquiposCotizador, JCoTable jcoTableSubequiposCotizador, CriteriosSimuladorDto criterios) {
		if(!listaSubEquiposCotizador.isEmpty()){
			for(int i = 0; i < listaSubEquiposCotizador.size(); i++){
				jcoTableSubequiposCotizador.appendRow();				
				jcoTableSubequiposCotizador.setValue("ID_COTIZ_Z", listaSubEquiposCotizador.get(i).getId_cotiz_z());
				jcoTableSubequiposCotizador.setValue("POSNR_Z", listaSubEquiposCotizador.get(i).getPosnr_z());
				jcoTableSubequiposCotizador.setValue("ID_EQUNR", listaSubEquiposCotizador.get(i).getId_equnr());
				jcoTableSubequiposCotizador.setValue("ID_EQUNRX", listaSubEquiposCotizador.get(i).getId_equnrx());
				jcoTableSubequiposCotizador.setValue("TIPO", listaSubEquiposCotizador.get(i).getTipo());
				if(listaSubEquiposCotizador.get(i).getTipo().equals("D") || listaSubEquiposCotizador.get(i).getTipo().equals("C"))
				{
					if(criterios.getAccion().equals("addsubequipos")){						
						jcoTableSubequiposCotizador.setValue("NETWR", listaSubEquiposCotizador.get(i).getNetwr());
					}
					else
					{
						float desc = Float.parseFloat(criterios.getDescp_r());
						if(desc<=0)
						{
							jcoTableSubequiposCotizador.setValue("NETWR", listaSubEquiposCotizador.get(i).getNetwr());
						}
						else
						{
							jcoTableSubequiposCotizador.setValue("NETWR", criterios.getPreciocd());
						}
					}
				}
				else
				{
					jcoTableSubequiposCotizador.setValue("NETWR", listaSubEquiposCotizador.get(i).getNetwr());
				}
				jcoTableSubequiposCotizador.setValue("MONEDA", listaSubEquiposCotizador.get(i).getMoneda());
			}
		}
	}
	
	private void llenaTablaSubEquiposCancelTraspaso(ArrayList<CotizadorSubequiposDto> listaSubEquiposCotizador, JCoTable jcoTableSubequiposCotizador, CriteriosSimuladorDto criterios) {
		if(!listaSubEquiposCotizador.isEmpty()){
			for(int i = 0; i < listaSubEquiposCotizador.size(); i++){
				jcoTableSubequiposCotizador.appendRow();				
				jcoTableSubequiposCotizador.setValue("EQUNR", listaSubEquiposCotizador.get(i).getId_equnr());				
			}
		}
	}
	
	
	public ResponseObtieneCotizacionBaseDto getCotizacionGuardada (CriteriosSimuladorDto criterios) throws ViviendaException {
		ResponseObtieneCotizacionBaseDto resp= new ResponseObtieneCotizacionBaseDto();
		
		traerArchivo = new CheckFileConnection();
		
		JCoTable tablaCritCotiz;
		JCoTable tablaCritEquipos;
		JCoTable tablaCritCliente;
		JCoTable tablaCritPedido;
		JCoTable tablaHeader;
		JCoTable tablaDetalle;
		JCoTable tablaBillPlaning;
		JCoTable tablaSubEquipos;
		JCoTable tablaPedidosHdr;
		JCoTable tablaPedidosDet;
		
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
			    
				JCoFunction function = connect.getFunction("ZCSMF_0048_GET_COT");
				//function.getImportParameterList().setValue("I_SAVE_LOG_ERROR", exp.getExtraProperties("logs"));
				function.getImportParameterList().setValue("I_USUARIO", criterios.getI_usuario());
				function.getImportParameterList().setValue("I_USUARIO", criterios.getI_usuario());
				function.getImportParameterList().setValue("I_ID_UT_SUP", criterios.getI_id_ut_sup());
				function.getImportParameterList().setValue("I_PEDIDO", criterios.getI_pedido());
				function.getImportParameterList().setValue("I_COTIZACION", criterios.getI_cotizacion());
				function.getImportParameterList().setValue("I_GET_INFO_Z", criterios.getI_get_info_z());
				function.getImportParameterList().setValue("I_GET_PEND", criterios.getI_get_pend());
				function.getImportParameterList().setValue("I_WEB", "X");
				
				// criterios de tablas

				tablaCritCotiz = function.getTableParameterList().getTable("IT_COTIZ_IN");
				llenaTablaCriteriosCotizadorIn(criterios,tablaCritCotiz);
				/*tablaCritEquipos = function.getTableParameterList().getTable("IT_EQUNR_IN");
				llenaTablaCriteriosEquiposIn(criterios,tablaCritEquipos);
				tablaCritCliente = function.getTableParameterList().getTable("IT_CLIENT_IN");
				llenaTablaCriteriosClienteIn(criterios,tablaCritCliente);
				tablaCritPedido = function.getTableParameterList().getTable("IT_PEDIDO_IN");
				llenaTablaCriteriosPedido(criterios,tablaCritPedido);*/
				
				// criterios de tablas fin				
				
				//System.out.println("OK");
				connect.execute(function);
				
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				
				if (subrc.equals("00")) 
				{
					tablaHeader = function.getTableParameterList().getTable("IT_COTIZ_HDR_OUT");
					tablaDetalle = function.getTableParameterList().getTable("IT_COTIZ_DET_OUT");
					tablaBillPlaning = function.getTableParameterList().getTable("IT_BILL_PLAN_OUT");
					tablaSubEquipos = function.getTableParameterList().getTable("IT_COT_EQPS_OUT");
					tablaPedidosHdr = function.getTableParameterList().getTable("IT_PED_HDR_OUT");
					tablaPedidosDet = function.getTableParameterList().getTable("IT_PED_DET_OUT");
					
					
					resp.setCotizacionPedidosList(getCotizacionPedidos(tablaPedidosHdr));
					resp.setCotizacionPedidosDetalleList(getCotizacionPedidosDetalle(tablaPedidosDet));
					resp.setCotizacionHeaderList(getCotizacionHeader(tablaHeader));
					resp.setCotizacionDetalleList(getCotizacionDetalle(tablaDetalle));
					resp.setCotizacionBillPlanList(getCotizacionPlain(tablaBillPlaning));
					resp.setCotizacionSubEquiposList(getCotizacionEquipos(tablaSubEquipos));
					
					resp=setDetalleAndBplaning(resp.getCotizacionHeaderList(), resp.getCotizacionDetalleList(), resp.getCotizacionBillPlanList(), resp.getCotizacionSubEquiposList());
					
					resp.setMensaje("SUCCESS");	
					resp.setDescripcion("");	
				}
				else
				{
					resp.setMensaje("FAULT");	
					resp.setDescripcion(bapierror);					
				}			
			} 
			catch (Exception excp) 
			{
				log.error("ERROR: ",excp);
				resp.setMensaje("FAULT");
				resp.setDescripcion(excp.getMessage());
				throw new ViviendaException(excp.getMessage());
			}
		}
		return resp;
	}	
	
	private void llenaTablaCriteriosCotizadorIn (CriteriosSimuladorDto criterios, JCoTable jcoTableCotizador)
	{		
		jcoTableCotizador.appendRow();				
		jcoTableCotizador.setValue("VBELN", criterios.getVbeln_cot());
	}
	

	/*private void llenaPedidoHeader(ArrayList<PedidosDto> listaPedidosCotizador, JCoTable jcoTablePedidosCotizador) {			
		if(!listaPedidosCotizador.isEmpty()){
			for(int i = 0; i < listaPedidosCotizador.size(); i++){
				jcoTablePedidosCotizador.appendRow();				
				jcoTablePedidosCotizador.setValue("ID_UT_SUP", listaPedidosCotizador.get(i).getId_ut_sup());
				jcoTablePedidosCotizador.setValue("ID_CAR_CLI", listaPedidosCotizador.get(i).getId_car_cli());
				jcoTablePedidosCotizador.setValue("ID_COTIZ_Z", listaPedidosCotizador.get(i).getId_cotiz_z());
				jcoTablePedidosCotizador.setValue("VBELN_COT", listaPedidosCotizador.get(i).getVbeln_cot());
				jcoTablePedidosCotizador.setValue("VBELN_PED", listaPedidosCotizador.get(i).getVbeln_ped());
				jcoTablePedidosCotizador.setValue("ID_EQUNR", listaPedidosCotizador.get(i).getId_equnr());
				jcoTablePedidosCotizador.setValue("TIVE", listaPedidosCotizador.get(i).getTive());
				jcoTablePedidosCotizador.setValue("TIVEX", listaPedidosCotizador.get(i).getTivex());
				jcoTablePedidosCotizador.setValue("AUART", listaPedidosCotizador.get(i).getAuart());
				jcoTablePedidosCotizador.setValue("AEDAT", listaPedidosCotizador.get(i).getAedat());
				jcoTablePedidosCotizador.setValue("CPUTM", listaPedidosCotizador.get(i).getCputm());
				jcoTablePedidosCotizador.setValue("DATAB", listaPedidosCotizador.get(i).getDatab());
				jcoTablePedidosCotizador.setValue("ID_CLI_SAP", listaPedidosCotizador.get(i).getId_cli_sap());
				jcoTablePedidosCotizador.setValue("NBOD", listaPedidosCotizador.get(i).getNbod());
				jcoTablePedidosCotizador.setValue("NEST", listaPedidosCotizador.get(i).getNest());
				jcoTablePedidosCotizador.setValue("M2TOT", listaPedidosCotizador.get(i).getM2tot());
				jcoTablePedidosCotizador.setValue("FECH_ENTREG", listaPedidosCotizador.get(i).getFech_entreg());
				jcoTablePedidosCotizador.setValue("DESCP", listaPedidosCotizador.get(i).getDescp());
				jcoTablePedidosCotizador.setValue("DESCM", listaPedidosCotizador.get(i).getDescm());
				jcoTablePedidosCotizador.setValue("NUML", listaPedidosCotizador.get(i).getNuml());
				jcoTablePedidosCotizador.setValue("DIAS_PAGO", listaPedidosCotizador.get(i).getDias_pago());
				jcoTablePedidosCotizador.setValue("SI_NO_PASA", listaPedidosCotizador.get(i).getSi_no_pasa());
				jcoTablePedidosCotizador.setValue("SPASO", listaPedidosCotizador.get(i).getSpaso());
				jcoTablePedidosCotizador.setValue("SPASOX", listaPedidosCotizador.get(i).getSpasox());
				jcoTablePedidosCotizador.setValue("NPASO", listaPedidosCotizador.get(i).getNpaso());
				jcoTablePedidosCotizador.setValue("NPASOX", listaPedidosCotizador.get(i).getNpasox());
				jcoTablePedidosCotizador.setValue("MSGBAPI", listaPedidosCotizador.get(i).getMsgbapi());
				jcoTablePedidosCotizador.setValue("DESCP_R", listaPedidosCotizador.get(i).getDescp_r());
				jcoTablePedidosCotizador.setValue("DESCM_R", listaPedidosCotizador.get(i).getDescm_r());
			}
		}
	}*/
	
	private ArrayList<PedidosDto> getCotizacionPedidos(JCoTable t) 	{
		ArrayList<PedidosDto> a = new ArrayList<PedidosDto>();		
		if (t != null){
			if (t.getNumRows() > 0){				
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){					
					PedidosDto pedido = new PedidosDto();						
					pedido.setId_ut_sup(t.getString("ID_UT_SUP"));
					pedido.setId_car_cli(t.getString("ID_CAR_CLI"));
					pedido.setId_cotiz_z(t.getString("ID_COTIZ_Z"));
					pedido.setVbeln_cot(t.getString("VBELN_COT"));
					pedido.setVbeln_ped(t.getString("VBELN_PED"));
					pedido.setId_equnr(t.getString("ID_EQUNR"));
					pedido.setTive(t.getString("TIVE"));
					pedido.setTivex(t.getString("TIVEX"));
					pedido.setAuart(t.getString("AUART"));
					pedido.setAedat(t.getString("AEDAT"));
					pedido.setCputm(t.getString("CPUTM"));
					pedido.setDatab(t.getString("DATAB"));
					pedido.setId_cli_sap(t.getString("ID_CLI_SAP"));
					pedido.setNbod(t.getString("NBOD"));
					pedido.setNest(t.getString("NEST"));
					pedido.setM2tot(t.getString("M2TOT"));
					pedido.setFech_entreg(t.getString("FECH_ENTREG"));
					pedido.setDescp(t.getString("DESCP"));
					pedido.setDescm(t.getString("DESCM"));
					pedido.setNuml(t.getString("NUML"));
					pedido.setDias_pago(t.getString("DIAS_PAGO"));
					pedido.setSi_no_pasa(t.getString("SI_NO_PASA"));
					pedido.setSpaso(t.getString("SPASO"));
					pedido.setSpasox(t.getString("SPASOX"));
					pedido.setNpaso(t.getString("NPASO"));
					pedido.setNpasox(t.getString("NPASOX"));
					pedido.setMsgbapi(t.getString("MSGBAPI"));
					pedido.setDescp_r(t.getString("DESCP_R"));
					pedido.setDescm_r(t.getString("DESCM_R"));	
					a.add(pedido);					
					t.nextRow();
				}				
			}
		}
		return a;
	}
	
	private ArrayList<PedidosDetalleDto> getCotizacionPedidosDetalle(JCoTable t) 	{
		ArrayList<PedidosDetalleDto> a = new ArrayList<PedidosDetalleDto>();		
		if (t != null){
			if (t.getNumRows() > 0){				
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){					
					PedidosDetalleDto pedidoDetalle = new PedidosDetalleDto();						
					pedidoDetalle.setId_cotiz_z(t.getString("ID_COTIZ_Z"));
					pedidoDetalle.setVbeln_cot(t.getString("VBELN_COT"));
					pedidoDetalle.setVbeln_ped(t.getString("VBELN_PED"));
					pedidoDetalle.setPosnr_z(t.getString("POSNR_Z"));
					pedidoDetalle.setPosnr_cot(t.getString("POSNR_COT"));
					pedidoDetalle.setPosnr_ped(t.getString("POSNR_PED"));
					pedidoDetalle.setCve_web(t.getString("CVE_WEB"));
					pedidoDetalle.setMatnr(t.getString("MATNR"));
					pedidoDetalle.setDescr(t.getString("DESCR"));
					pedidoDetalle.setNetwr(t.getString("NETWR"));
					pedidoDetalle.setNetwrp(t.getString("NETWRP"));
					pedidoDetalle.setMoneda(t.getString("MONEDA"));
					a.add(pedidoDetalle);					
					t.nextRow();
				}				
			}
		}
		return a;
	}
}