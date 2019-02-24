package mx.com.grupogigante.gestionvivienda.dao;

import java.util.ArrayList;
import java.util.List;

import mx.com.grupogigante.gestionvivienda.domain.dto.CarpetasDigitDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CarpetasDigitEstatusDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CarpetasTicketDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CoordenadasDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosEquipoTiposDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosUbicacionesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.EqCaracteristicasDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.EqPriceDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.Equipo2Dto;
import mx.com.grupogigante.gestionvivienda.domain.dto.EquipoDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.EquipoTiposDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.EstatusFiltroReporteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.PermisoUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseFileConnect;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetEquDetDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetUtInfSupDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionDatosMapaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionTecnicaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.SubequipoDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.UTCaracteristicasDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.UbicacionTecnicaDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.CheckFileConnection;
import mx.com.grupogigante.gestionvivienda.utils.Connection;
import mx.com.grupogigante.gestionvivienda.utils.utilsCommon;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

@Repository
public class UbicacionesDaoImpl implements UbicacionesDao {
	private Logger log = Logger.getLogger(UbicacionesDaoImpl.class);
	
	private utilsCommon util;
	public UbicacionTecnicaDto ubicacion;
	CheckFileConnection traerArchivo;
	private ResponseGetUtInfSupDto respGetUtInfSup;
	private ResponseGetEquDetDto respGetEqDetail;
	private JCoTable tableUbicaciones;
	private JCoTable tableEquipos;
	private JCoTable tableSubequipos;
	private JCoTable tableEqCaract;
	private JCoTable tableUtCaract;
	private JCoTable tableEqPrice;
	private JCoTable tableCoords;
	private JCoTable tableDigital;
	private JCoTable tableTicket;
	private JCoTable tableDigitalEstatus;
	private JCoTable tableEstatus;
	
	
	public ResponseUbicacionTecnicaDto getUbicaciones() throws ViviendaException {
		ResponseUbicacionTecnicaDto respUbicacion = new ResponseUbicacionTecnicaDto();
		ResponseFileConnect respFile = new ResponseFileConnect();
		try
		{
			traerArchivo = new CheckFileConnection();
			respFile=traerArchivo.getFileConnection();			
			
			JCoTable tableUbicaciones;
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
					JCoFunction function = connect.getFunction("ZCSMF_0020_GET_ALL_UTS");
					connect.execute(function);
					// Recupera el estado de determinación de la funcion RFC
					subrc = (String) function.getExportParameterList().getString("E_SUBRC");
					bapierror = (String) function.getExportParameterList().getString("E_BAPI_MSG");
					if (subrc.equals("00")) 
					{
						// Recuepar la TABLA DE UBICACIONES TS
						tableUbicaciones = function.getTableParameterList().getTable("IT_UT_SUPS");
						//
						respUbicacion.setMensaje("SUCCESS");	
						respUbicacion.setUbicacionesList(getDatosUbicacionesRFC(tableUbicaciones));
						respUbicacion.setObjUbicacionesList(respUbicacion.getUbicacionesList());
					}
					else
					{
						respUbicacion.setMensaje("FAULT");	
						respUbicacion.setDescripcion(bapierror);
						respUbicacion.setUbicacionesList(null);
					}
				} 
				catch (Exception excp) 
				{
					log.error("ERROR: ",excp);
					respUbicacion.setMensaje("FAULT");
					respUbicacion.setDescripcion(excp.getMessage());
					respUbicacion.setUbicacionesList(null);
					throw new ViviendaException(excp.getMessage());
				}
			}			
		}
		catch(Exception ex)
		{
			log.error("ERROR: ",ex);
			respUbicacion.setMensaje("FAULT");
			respUbicacion.setDescripcion(ex.getMessage());
			respUbicacion.setUbicacionesList(null);
			throw new ViviendaException(ex.getMessage());				
		}
		return respUbicacion;
	}
	
	private ArrayList<UbicacionTecnicaDto> getDatosUbicacionesRFC(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<UbicacionTecnicaDto> a = new ArrayList<UbicacionTecnicaDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new UbicacionTecnicaDto());
					a.get(i).setUbicacionClave(t.getString("ID_UT_SUP"));
					a.get(i).setUbicacionDescripcion(t.getString("ID_UT_SUPX"));
					a.get(i).setTipoEstructura(t.getString("TIPO_STRUCT"));
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}
	
	private ArrayList<UbicacionTecnicaDto> getDatosUbicacionesAll(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<UbicacionTecnicaDto> a = new ArrayList<UbicacionTecnicaDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					String ut_depth="";
					ut_depth = t.getString("IND_UT");
					if(ut_depth.equals("2"))
					{
						UbicacionTecnicaDto utdto = new UbicacionTecnicaDto();						
						utdto.setUbicacionClave(t.getString("ID_UBI_TEC"));
						utdto.setUbicacionDescripcion(t.getString("ID_UBI_TECX"));
						utdto.setTipoEstructura(t.getString("TIPO_STRUCT"));
						utdto.setId_ut_sup(t.getString("ID_UT_SUP"));
						utdto.setId_ut_supx(t.getString("ID_UT_SUPX"));
						utdto.setInd_ut(t.getString("IND_UT"));
						utdto.setId(t.getString("ID_UBI_TEC"));
						utdto.setText(t.getString("ID_UBI_TECX"));						
						utdto.setSpriteCssClass("folder");
						utdto.setExpanded(true);
						a.add(utdto);
					}
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}
	
	private ArrayList<UbicacionTecnicaDto> getDatosPisos(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<UbicacionTecnicaDto> a = new ArrayList<UbicacionTecnicaDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					String ut_depth="";
					ut_depth = t.getString("IND_UT");
					if(ut_depth.equals("3"))
					{
						UbicacionTecnicaDto utdto = new UbicacionTecnicaDto();						
						utdto.setUbicacionClave(t.getString("ID_UBI_TEC"));
						utdto.setUbicacionDescripcion(t.getString("ID_UBI_TECX"));
						utdto.setTipoEstructura(t.getString("TIPO_STRUCT"));
						utdto.setId_ut_sup(t.getString("ID_UT_SUP"));
						utdto.setId_ut_supx(t.getString("ID_UT_SUPX"));
						utdto.setInd_ut(t.getString("IND_UT"));
						utdto.setId(t.getString("ID_UBI_TEC"));
						utdto.setText(t.getString("ID_UBI_TECX"));						
						utdto.setSpriteCssClass("folder");
						a.add(utdto);
					}
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}
	
	public ResponseGetUtInfSupDto getUtInfSup(CriteriosUbicacionesDto criteriosGetInfUt) throws ViviendaException {
		util = new utilsCommon();
		traerArchivo = new CheckFileConnection();
		respGetUtInfSup = new ResponseGetUtInfSupDto();
		String sStatus;
		Connection connect = null;
		sStatus = ArchLogg.leeLogg();
		String subrc = "";
		String bapierror = "";
		int max_deptos=0;
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
				JCoFunction function = connect.getFunction("ZCSMF_0021_UT_INF_SUP");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosGetInfUt.getI_id_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", criteriosGetInfUt.getI_usuario());
				function.getImportParameterList().setValue("I_ID_UT_CURRENT", criteriosGetInfUt.getI_id_ut_current());
				function.getImportParameterList().setValue("I_CHARACT", criteriosGetInfUt.getI_charact());
				function.getImportParameterList().setValue("I_DEEP_SRCH", criteriosGetInfUt.getI_deep_srch());
				function.getImportParameterList().setValue("I_EQUNR_PRICE", criteriosGetInfUt.getI_equnr_price());	
				function.getImportParameterList().setValue("I_COORDS", criteriosGetInfUt.getI_coords());
				function.getImportParameterList().setValue("I_COORDS_SUP", criteriosGetInfUt.getI_coords_sup());
				function.getImportParameterList().setValue("I_DIGIT_AREAS", criteriosGetInfUt.getI_digit_areas());
				function.getImportParameterList().setValue("I_TICKET_AREAS", criteriosGetInfUt.getI_ticket_areas());
				function.getImportParameterList().setValue("I_STUN", criteriosGetInfUt.getI_stun());
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				if (subrc.equals("00")) 
				{
					// Recuepar las TABLAS 
					tableUbicaciones = function.getTableParameterList().getTable("IT_UBI_TEC");
					tableEquipos = function.getTableParameterList().getTable("IT_EQUIPOS");
					tableSubequipos = function.getTableParameterList().getTable("IT_SUBEQUIPOS");
					tableEqCaract = function.getTableParameterList().getTable("IT_EQ_CHARACT");
					tableUtCaract = function.getTableParameterList().getTable("IT_UT_CHARACT");
					tableEqPrice = function.getTableParameterList().getTable("IT_EQUNR_PRICES");
					tableCoords = function.getTableParameterList().getTable("IT_COORDS");
					tableDigital = function.getTableParameterList().getTable("IT_DIGIT_AREAS");
					tableTicket = function.getTableParameterList().getTable("IT_TICKETS_AREAS");
					tableDigitalEstatus = function.getTableParameterList().getTable("IT_STATS_ID_FILE");
					tableEstatus = function.getTableParameterList().getTable("IT_STUN");
										
					try
					{
						criteriosGetInfUt.getAccion().equals("arbol");
					}
					catch(Exception e)
					{
						criteriosGetInfUt.setAccion("");
					}
					
					if(criteriosGetInfUt.getAccion().equals("arbol"))
					{
					   respGetUtInfSup.setUbicacionesList(getDatosUbicacionesAll(tableUbicaciones));	
					   respGetUtInfSup.setPisosList(getDatosPisos(tableUbicaciones));
					   respGetUtInfSup.setEquiposList(getEquipos(tableEquipos));
					   respGetUtInfSup.setCarpetasDigitalizacion(getDigitCarpetas(tableDigital));
					   respGetUtInfSup.setEstatusDigitalizacion(getDigitCarpetasEstatus(tableDigitalEstatus));
					   respGetUtInfSup=arbolUnidadTecnica(respGetUtInfSup);
					   
					}
					else
					{
					   respGetUtInfSup.setUbicacionesList(getDatosUbicacionesAll(tableUbicaciones));
				       respGetUtInfSup.setObjUbicacionesList(respGetUtInfSup.getUbicacionesList());
				       respGetUtInfSup.setPisosList(getDatosPisos(tableUbicaciones));
				       respGetUtInfSup.setObjPisosList(respGetUtInfSup.getPisosList());
					   respGetUtInfSup.setEquiposList(getEquipos(tableEquipos));
					   respGetUtInfSup.setObjEquiposList(respGetUtInfSup.getEquiposList());
					   respGetUtInfSup.setSubequiposList(getSubequipos(tableSubequipos));
					   respGetUtInfSup.setObjSubequiposList(respGetUtInfSup.getSubequiposList());
					   respGetUtInfSup.setEqCaracteristicasList(getEqCaracteristicas(tableEqCaract));
					   respGetUtInfSup.setObjEqCaracteristicasList(respGetUtInfSup.getEqCaracteristicasList());
					   respGetUtInfSup.setUtCaracterisiticasList(getUtCaracteristicas(tableUtCaract));
					   respGetUtInfSup.setObjUtCaracterisiticasList(respGetUtInfSup.getUtCaracterisiticasList());
					   respGetUtInfSup.setEqPriceList(getEqPrices(tableEqPrice));
					   respGetUtInfSup.setObjEqPriceList(respGetUtInfSup.getEqPriceList());	
					   respGetUtInfSup.setCoordenadasList(getCoordenadas(tableCoords));
					   respGetUtInfSup.setObjCoordenadasList(respGetUtInfSup.getCoordenadasList());
					   respGetUtInfSup.setListFiltradoCoordenadas(getFiltraCoordenadas(tableCoords,criteriosGetInfUt));
					   respGetUtInfSup.setObjFiltradoCoordenadasList(respGetUtInfSup.getListFiltradoCoordenadas());
					   respGetUtInfSup.setCarpetasDigitalizacion(getDigitCarpetas(tableDigital));
					   respGetUtInfSup.setCarpetasTicket(getTicketCarpetas(tableTicket));
					   respGetUtInfSup.setEstatus(getEstatus(tableEstatus));
					   respGetUtInfSup.setEstatusDigitalizacion(getDigitCarpetasEstatus(tableDigitalEstatus));
					}
				   respGetUtInfSup.setMensaje("SUCCESS");	
				   respGetUtInfSup.setDescripcion("");				   
				   respGetUtInfSup.setUbicacionesDetalleList(util.asignaDeptoPrecio(respGetUtInfSup));
				   				   
				   for(int i=0; i<respGetUtInfSup.getUbicacionesDetalleList().size(); i++)
				   {
					   if(respGetUtInfSup.getUbicacionesDetalleList().get(i).getEquiposList().size()>=max_deptos)
					   {
						   max_deptos=respGetUtInfSup.getUbicacionesDetalleList().get(i).getEquiposList().size();
					   }
				   }
				   respGetUtInfSup.setMaxep_piso(max_deptos); 
				}
				else
				{					
					respGetUtInfSup.setMensaje("FAULT");	
					respGetUtInfSup.setDescripcion(bapierror);					
				}
			} 
			catch (Exception excp) 
			{
				log.error("",excp);
				respGetUtInfSup.setMensaje("FAULT");
				respGetUtInfSup.setDescripcion(excp.getMessage());	
				throw new ViviendaException(excp.getMessage());
			}
		}
		return respGetUtInfSup;
	}	
	
public ResponseGetEquDetDto getEqDetail(CriteriosUbicacionesDto criteriosGetEqDet) throws ViviendaException {
		
		traerArchivo = new CheckFileConnection();
		
		JCoTable tableUbicaciones;
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
				JCoFunction function = connect.getFunction("ZCSMF_0022_GET_EQUNR");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosGetEqDet.getI_id_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", criteriosGetEqDet.getI_usuario());
				function.getImportParameterList().setValue("I_CHARACT", criteriosGetEqDet.getI_charact());
				function.getImportParameterList().setValue("I_EQUNR_PRICE", criteriosGetEqDet.getI_equnr_price());
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				if (subrc.equals("00")) 
				{
					// Recuepar las TABLAS 
					tableEquipos = function.getTableParameterList().getTable("ZCSWA_EQUIPO2");
					tableSubequipos = function.getTableParameterList().getTable("ZCSWA_SUBEQUIPO");
					tableEqCaract = function.getTableParameterList().getTable("ZCSWA_EQ_CHARACT");
					tableEqPrice = function.getTableParameterList().getTable("ZCSWA_EQUNR_PRICE");
				
				   respGetEqDetail.setEquipos2List(getEquipos2(tableEquipos));
				   respGetEqDetail.setObjEquipos2List(respGetEqDetail.getEquipos2List());
				   respGetEqDetail.setSubequiposList(getSubequipos(tableSubequipos));
				   respGetEqDetail.setObjSubequiposList(respGetEqDetail.getSubequiposList());
				   respGetEqDetail.setEqCaracteristicasList(getEqCaracteristicas(tableEqCaract));
				   respGetEqDetail.setObjEqCaracteristicasList(respGetEqDetail.getEqCaracteristicasList());
				   respGetEqDetail.setEqPriceList(getEqPrices(tableEqPrice));
				   respGetEqDetail.setObjEqPriceList(respGetEqDetail.getEqPriceList());		
				   respGetEqDetail.setMensaje("SUCCESS");	
				   respGetEqDetail.setDescripcion("");
				}
				else
				{
					respGetEqDetail.setMensaje("FAULT");	
					respGetEqDetail.setDescripcion(bapierror);
					
				}
			} 
			catch (Exception excp) 
			{
				log.error("",excp);			
				respGetEqDetail.setMensaje("FAULT");
				respGetEqDetail.setDescripcion(excp.getMessage());
				throw new ViviendaException(excp.getMessage());
			}
		}
		return respGetEqDetail;
	}
	
	private ArrayList<CarpetasDigitDto> getDigitCarpetas(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<CarpetasDigitDto> a = new ArrayList<CarpetasDigitDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new CarpetasDigitDto());
					a.get(i).setId_ticket_area(t.getString("ID_TICKET_AREA"));
					a.get(i).setId_ticket_file(t.getString("ID_TICKET_FILE"));
					a.get(i).setId_ticket_areax(t.getString("ID_TICKET_AREAX"));
					a.get(i).setId(t.getString("ID_TICKET_AREA"));
					a.get(i).setText(t.getString("ID_TICKET_AREAX"));						
					a.get(i).setSpriteCssClass("pdf");
					a.get(i).setExpanded(false);
					t.nextRow();
					}
				return a;
			}
		}
		return null;
	}
	
	private ArrayList<CarpetasTicketDto> getTicketCarpetas(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<CarpetasTicketDto> a = new ArrayList<CarpetasTicketDto>();
				t.firstRow();
				a.add(new CarpetasTicketDto());
				a.get(0).setId_ticket_area("");
				a.get(0).setId_ticket_file("");
				a.get(0).setId_ticket_areax("");
				a.get(0).setId("");
				a.get(0).setText("");						
				a.get(0).setSpriteCssClass("");
				a.get(0).setExpanded(false);
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new CarpetasTicketDto());
					a.get(i + 1).setId_ticket_area(t.getString("ID_TICKET_AREA"));
					a.get(i + 1).setId_ticket_file(t.getString("ID_TICKET_FILE"));
					a.get(i + 1).setId_ticket_areax(t.getString("ID_TICKET_AREAX"));
					a.get(i + 1).setId(t.getString("ID_TICKET_AREA"));
					a.get(i + 1).setText(t.getString("ID_TICKET_AREAX"));						
					a.get(i + 1).setSpriteCssClass("pdf");
					a.get(i + 1).setExpanded(false);
					t.nextRow();
					}
				return a;
			}
		}
		return null;
	}

	private ArrayList<EstatusFiltroReporteDto> getEstatus(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<EstatusFiltroReporteDto> a = new ArrayList<EstatusFiltroReporteDto>();
				t.firstRow();
				a.add(new EstatusFiltroReporteDto());
				a.get(0).setStun("");
				a.get(0).setStunx("");
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new EstatusFiltroReporteDto());
					a.get(i + 1).setStun(t.getString("STUN"));
					a.get(i + 1).setStunx(t.getString("STUNX"));
					t.nextRow();
					}
				return a;
			}
		}
		return null;
	}

	private ArrayList<CarpetasDigitEstatusDto> getDigitCarpetasEstatus(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<CarpetasDigitEstatusDto> a = new ArrayList<CarpetasDigitEstatusDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new CarpetasDigitEstatusDto());
					a.get(i).setId_ticket_area(t.getString("ID_TICKET_AREA"));
					a.get(i).setId_ticket_file(t.getString("ID_TICKET_FILE"));
					a.get(i).setId_ticket_statx(t.getString("ID_TICKET_STATX"));
					a.get(i).setId_ticket_stat(t.getString("ID_TICKET_STAT"));
					a.get(i).setId(t.getString("ID_TICKET_STAT"));
					a.get(i).setText(t.getString("ID_TICKET_STATX"));						
					a.get(i).setSpriteCssClass("pdf");
					a.get(i).setExpanded(false);
					t.nextRow();
					}
				return a;
			}
		}
		return null;
	}
	
	private ArrayList<EquipoDto> getEquipos(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<EquipoDto> a = new ArrayList<EquipoDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new EquipoDto());
					a.get(i).setId_ubi_tec(t.getString("ID_UBI_TEC"));
					a.get(i).setId_ubi_tecx(t.getString("ID_UBI_TECX"));
					a.get(i).setId_equnr(t.getString("ID_EQUNR"));
					a.get(i).setId_equnrx(t.getString("ID_EQUNRX"));
					a.get(i).setId(t.getString("ID_UBI_TEC"));
					a.get(i).setText(t.getString("ID_UBI_TECX"));						
					a.get(i).setSpriteCssClass("folder");
					a.get(i).setExpanded(false);
					t.nextRow();
					}
				return a;
			}
		}
		return null;
	}
	private ArrayList<Equipo2Dto> getEquipos2(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<Equipo2Dto> a = new ArrayList<Equipo2Dto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new Equipo2Dto());
					a.get(i).setId_ubi_tec(t.getString("ID_UBI_TEC"));
					a.get(i).setId_ubi_tecx(t.getString("ID_UBI_TECX"));
					a.get(i).setId_equnr(t.getString("ID_EQUNR"));
					a.get(i).setId_equnrx(t.getString("ID_EQUNRX"));
					a.get(i).setEqunr_sup(t.getString("EQUNR_SUP"));
					a.get(i).setEqunrx_sup(t.getString("EQUNRX_SUP"));
					t.nextRow();
					}
				return a;
			}
		}
		return null;
	}
	private ArrayList<SubequipoDto> getSubequipos(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<SubequipoDto> a = new ArrayList<SubequipoDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new SubequipoDto());
					a.get(i).setId_equnr(t.getString("ID_EQUNR"));
					a.get(i).setId_equnrx(t.getString("ID_EQUNRX"));
					a.get(i).setId_subequnr(t.getString("ID_SEQUNR"));
					a.get(i).setId_subequnrx(t.getString("ID_SEQUNRX"));
					t.nextRow();
					}
				return a;
			}
		}
		return null;
	}
	private ArrayList<EqCaracteristicasDto> getEqCaracteristicas(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<EqCaracteristicasDto> a = new ArrayList<EqCaracteristicasDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new EqCaracteristicasDto());
					a.get(i).setId_equnr(t.getString("ID_EQUNR"));
					a.get(i).setCharact(t.getString("CHARACT"));
					a.get(i).setCharactx(t.getString("CHARACTX"));
					a.get(i).setValue(t.getString("VALUE"));
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}
	private ArrayList<UTCaracteristicasDto> getUtCaracteristicas(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<UTCaracteristicasDto> a = new ArrayList<UTCaracteristicasDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new UTCaracteristicasDto());
					a.get(i).setId_ubi_tec(t.getString("ID_UBI_TEC"));
					a.get(i).setCharact(t.getString("CHARACT"));
					a.get(i).setCharactx(t.getString("CHARACTX"));
					a.get(i).setValue(t.getString("VALUE"));
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}
	private ArrayList<EqPriceDto> getEqPrices(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<EqPriceDto> a = new ArrayList<EqPriceDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new EqPriceDto());
					a.get(i).setId_equnr(t.getString("ID_EQUNR"));
					a.get(i).setPrice(t.getFloat("PRICE"));
					a.get(i).setCurrs(t.getString("CURRS"));
					a.get(i).setNuml(t.getInt("NUML"));
					a.get(i).setKnumh(t.getString("KNUMH"));
					a.get(i).setKopos(t.getInt("KOPOS"));
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}
	
	private ArrayList<CoordenadasDto> getCoordenadas(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<CoordenadasDto> a = new ArrayList<CoordenadasDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new CoordenadasDto());
					a.get(i).setUteq(t.getString("UTEQ"));
					a.get(i).setCoord(t.getString("COORD"));
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}
	
	private ArrayList<CoordenadasDto> getFiltraCoordenadas(JCoTable t, CriteriosUbicacionesDto criterios) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<CoordenadasDto> a = new ArrayList<CoordenadasDto>();
				t.firstRow();
				CoordenadasDto coordDto;
				String ideq="";
				for (int i = 0; i < t.getNumRows(); i++){
					
					try
					{
						ideq=criterios.getI_id_eq_curr();
						if(ideq.equals(""))
						{
							if(criterios.getI_id_ut_current().equals(t.getString("UTEQ")))
							{	
								coordDto=new CoordenadasDto();
								coordDto.setUteq(t.getString("UTEQ"));
								coordDto.setCoord(t.getString("COORD"));	
								a.add(coordDto);				
							}							
						}
						else
						{
							if(ideq.equals(t.getString("UTEQ")))
							{	
								coordDto=new CoordenadasDto();
								coordDto.setUteq(t.getString("UTEQ"));
								coordDto.setCoord(t.getString("COORD"));	
								a.add(coordDto);				
							}
						}
						
					}
					catch(Exception e){
						if(criterios.getI_id_ut_current().equals(t.getString("UTEQ")))
						{	
							coordDto=new CoordenadasDto();
							coordDto.setUteq(t.getString("UTEQ"));
							coordDto.setCoord(t.getString("COORD"));	
							a.add(coordDto);				
						}
					}
					
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}
	
	public ResponseUbicacionDatosMapaDto setCoordenadasImagen(CriteriosUbicacionesDto criterios) throws ViviendaException {
		ResponseUbicacionDatosMapaDto respUbicacion = new ResponseUbicacionDatosMapaDto();
		traerArchivo = new CheckFileConnection();
		List<CoordenadasDto> coordenadasList = new ArrayList<CoordenadasDto>();
		CoordenadasDto coordenadasDto=new CoordenadasDto();
		
		JCoTable tablaCoordenadas;
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
				JCoFunction function = connect.getFunction("ZCSMF_0025_ADM_COORDS");
				coordenadasDto.setCoord(criterios.getI_coords());
				coordenadasDto.setUteq(criterios.getI_id_ut_current());
				coordenadasList.add(coordenadasDto);
				
				tablaCoordenadas = function.getTableParameterList().getTable("IT_COORDS");
				llenaItCoordenadas(coordenadasList, tablaCoordenadas);
				
				if(!tablaCoordenadas.isEmpty()){				
					function.getImportParameterList().setValue("I_ID_UT_SUP", criterios.getI_id_ut_sup());
					function.getImportParameterList().setValue("I_USUARIO", criterios.getI_usuario());
					function.getImportParameterList().setValue("I_ACT", "X");
					function.getImportParameterList().setValue("I_ELM", "");
					connect.execute(function);
					// Recupera el estado de determinación de la funcion RFC
					subrc = (String) function.getExportParameterList().getString("E_SUBRC");
					bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
					if (subrc.equals("00")) 
					{					
						respUbicacion.setMensaje("SUCCESS");	
						respUbicacion.setDescripcion("Coordenadas actualizadas satisfactoriamente");
					}
					else
					{
						respUbicacion.setMensaje("FAULT");	
						respUbicacion.setDescripcion(bapierror);
					}
				}
				else
				{
					respUbicacion.setMensaje("FAULT");	
					respUbicacion.setDescripcion("Las coordenadas no deben de ir vacias");
				}
			} 
			catch (Exception excp) 
			{
				log.error("",excp);				
				respUbicacion.setMensaje("FAULT");
				respUbicacion.setDescripcion(excp.getMessage());
				throw new ViviendaException(excp.getMessage());
			}
		}
		return respUbicacion;
	}
	
	private void llenaItCoordenadas(List<CoordenadasDto> listaCoordenadas, JCoTable itTablaCoordenadas) {
        if(!listaCoordenadas.isEmpty()){
		    for(int i = 0; i < listaCoordenadas.size(); i++){
		    	itTablaCoordenadas.appendRow();
		    	itTablaCoordenadas.setValue("UTEQ", listaCoordenadas.get(i).getUteq());
		    	itTablaCoordenadas.setValue("COORD", listaCoordenadas.get(i).getCoord());
			}
	    }
	}
	
	public ResponseUbicacionDatosMapaDto getTiposEquipos(CriteriosEquipoTiposDto criterios) throws ViviendaException
	{
		ResponseUbicacionDatosMapaDto respTipos = new ResponseUbicacionDatosMapaDto();
		ArrayList<EquipoTiposDto> listaTipos = new ArrayList<EquipoTiposDto>();
		
		JCoTable tablaTipos;
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
				JCoFunction function = connect.getFunction("ZCSMF_0027_GET_EQUNR_TYPES");			
							
				function.getImportParameterList().setValue("I_ID_UT_SUP", criterios.getI_id_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", criterios.getI_usuario());
				function.getImportParameterList().setValue("I_ID_FASE", criterios.getI_id_fase());
				
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				if (subrc.equals("00")) 
				{					
					respTipos.setMensaje("SUCCESS");	
					respTipos.setDescripcion("");
					tablaTipos = function.getTableParameterList().getTable("IT_TYPES");
					respTipos.setListaTipos(getTiposEq(tablaTipos));
				}
				else
				{
					respTipos.setMensaje("FAULT");	
					respTipos.setDescripcion(bapierror);
				}
				
			} 
			catch (Exception excp) 
			{
				log.error("",excp);	
				respTipos.setMensaje("FAULT");
				respTipos.setDescripcion(excp.getMessage());
				throw new ViviendaException(excp.getMessage());
			}
		}
		return respTipos ;
	}
	
	private ArrayList<EquipoTiposDto> getTiposEq(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<EquipoTiposDto> a = new ArrayList<EquipoTiposDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new EquipoTiposDto());
					a.get(i).setI_tipo_eq(t.getString("VALUEX"));
					t.nextRow();
					}
				return a;
			}
		}
		return null;
	}
	
	public ResponseUbicacionDatosMapaDto setReplicaPisos(CriteriosUbicacionesDto criterios)
	{
		ResponseUbicacionDatosMapaDto respReplica = new ResponseUbicacionDatosMapaDto();	
		return respReplica;
	}
	
	private ResponseGetUtInfSupDto arbolUnidadTecnica(ResponseGetUtInfSupDto responseUT) throws ViviendaException
	{
		ResponseGetUtInfSupDto respUTS = new ResponseGetUtInfSupDto();
		List<UbicacionTecnicaDto> uts_lst=new ArrayList<UbicacionTecnicaDto>();
		List<UbicacionTecnicaDto> piso_lst=new ArrayList<UbicacionTecnicaDto>();
		List<EquipoDto> equ_lst=new ArrayList<EquipoDto>();
		respUTS.setCarpetasDigitalizacion(responseUT.getCarpetasDigitalizacion());
		try
		{
			int cnt_uts=0; 
			for(int i=0; i<responseUT.getUbicacionesList().size(); i++)
			{
				respUTS.getUbicacionesList().add(responseUT.getUbicacionesList().get(i));
				cnt_uts ++;
			}
			
			int cnt_piso=0;
			for(int i=0; i<responseUT.getPisosList().size(); i++)
			{	
				for(int j=0; j<respUTS.getUbicacionesList().size(); j++)
				{
					if(respUTS.getUbicacionesList().get(j).getUbicacionClave().equals(responseUT.getPisosList().get(i).getId_ut_sup()))
					{
						respUTS.getUbicacionesList().get(j).getPisosList().add(responseUT.getPisosList().get(i));											
						respUTS.getUbicacionesList().get(j).setItems(respUTS.getUbicacionesList().get(j).getPisosList());
						cnt_piso ++;
					}
				}				
			}
			
			int cnt_equ=0;
			for(int i=0; i<responseUT.getEquiposList().size(); i++)
			{
				for(int j=0; j<respUTS.getUbicacionesList().size(); j++)
				{				
					for(int k=0; k<respUTS.getUbicacionesList().get(j).getPisosList().size(); k++)
					{
						if(respUTS.getUbicacionesList().get(j).getPisosList().get(k).getUbicacionClave().equals(responseUT.getEquiposList().get(i).getId_ubi_tec()))
						{
							responseUT.getCarpetasDigitalizacion().get(0).setId(responseUT.getEquiposList().get(i).getId_ubi_tec());
							responseUT.getEquiposList().get(i).setItems(responseUT.getCarpetasDigitalizacion());
							respUTS.getUbicacionesList().get(j).getPisosList().get(k).getEquiposList().add(responseUT.getEquiposList().get(i));
							respUTS.getUbicacionesList().get(j).getPisosList().get(k).setItems(respUTS.getUbicacionesList().get(j).getPisosList().get(k).getEquiposList());
							cnt_equ ++;
						}
					}
				}
			}
		}
		catch(Exception excp)
		{
			log.error("",excp);	
			respUTS.setMensaje("FAULT");
			respUTS.setDescripcion(excp.getMessage());
			throw new ViviendaException(excp.getMessage());
		}
		
		return respUTS;
	}
}
