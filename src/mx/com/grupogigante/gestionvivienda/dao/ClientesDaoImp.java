/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.dao;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import mx.com.grupogigante.gestionvivienda.dao.report.DisponibilidadReporteDaoImpl;
import mx.com.grupogigante.gestionvivienda.domain.dto.ClienteSapDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ClientesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ClientesReporteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ContactoByClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ContactoDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CotizacionDetDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CotizacionHdrDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosClientesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfClienteSap;
import mx.com.grupogigante.gestionvivienda.domain.dto.PermisoUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.PermisosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseAddClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseDelClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfClienteSap;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetReporteClientesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionTecnicaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUpClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.SubequipoDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ViaContactoDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.VisitasClienteDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.VisitsDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.CriteriosGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.ResponseGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.report.exception.ReporteException;
import mx.com.grupogigante.gestionvivienda.services.CatalogosService;
import mx.com.grupogigante.gestionvivienda.services.ICatalogosService;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.CheckFileConnection;
import mx.com.grupogigante.gestionvivienda.utils.Connection;
import mx.com.grupogigante.gestionvivienda.utils.utilsCommon;

import org.apache.log4j.Logger;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

/**
 * Clase en donde se realiza la implementacion de todas las operaciones referentes a Cartera 
 * de clientes
 * Fecha de creación: XX/06/2012               
 */

@Repository
public class ClientesDaoImp implements ClientesDao {
	private Logger log = Logger.getLogger(ClientesDaoImp.class);

	@Autowired
	ICatalogosService catalogosService;
	
	private CheckFileConnection traerArchivo;
	//variables de respuesta para cartera de clientes 
	private ResponseGetInfCarCliente respGetInfCarCliente;
	private JCoTable tableClientes;
	private JCoTable tableVisits;
	private JCoTable tableCotizaciones;
	private JCoTable tableSubequipos;
	private JCoTable tableViaContacto;
	public utilsCommon utils=new utilsCommon();
	private JCoTable jcoTableUsers;
	
	//variables de respuesta para cartera de clientes 
	private ResponseGetInfClienteSap respGetInfClienteSap;
	private JCoTable tableClientesSap;
	private JCoTable tableDirClientes;
	private JCoTable tablePrestanombres;
	private JCoTable tableDirPrestan;
		
	//variables de respuesta comunes para cartera y clientes sap 
	private ResponseAddClienteDto respAddCliente;
	private ResponseDelClienteDto respDelCliente;
	private ResponseUpClienteDto respUpCliente;	
	
	//variables de respuesta para cartera de clientes 
//	private ResponseGetInfClienteSap respGetInfClienteSap;
	private JCoTable tableFases;
	private JCoTable tableEquipos;
	
	/**
	 * Método que regresa una lista de vendedores permitidos de acuerdo al perfil 
	 * 
	 * @param criteriosClienteDto 
	 * 			criterios de busqueda de clientes  
	 * @return ResponseGetInfCarCliente 
	 * 			objeto de respuesta para informacion de clientes 
	 * @throws ViviendaException 
	 * @exception  Exception  
	 */		
	public ResponseGetInfCarCliente getClientes(CriteriosGetInfCarCliente criteriosClienteDto) throws ViviendaException{
		respGetInfCarCliente=new ResponseGetInfCarCliente();
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
				JCoFunction function = connect.getFunction("ZCSMF_0010_GET_INF_CAR_CLIENTE");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosClienteDto.getId_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", criteriosClienteDto.getUsuario());
				JCoTable itHeader = function.getTableParameterList().getTable("IT_CAR_CLIENTE");
				llenaItHeaderCriterios(llena(criteriosClienteDto.getXmlNom(),criteriosClienteDto.getXmlNom2(),criteriosClienteDto.getXmlApPat(),criteriosClienteDto.getXmlApMat(),criteriosClienteDto.getXmlIds()), itHeader);
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				if (subrc.equals("00")) 
				{	
					tableClientes = function.getTableParameterList().getTable("IT_CAR_CLIENTE");
					tableVisits = function.getTableParameterList().getTable("IT_VISITS_CAR_CLI");
					
					respGetInfCarCliente.setMensaje("SUCCESS");	
					respGetInfCarCliente.setDescripcion("");
					
			        respGetInfCarCliente.setClientesList(getInfoClientes(tableClientes,tableVisits));
			        respGetInfCarCliente.setObjClientesList(respGetInfCarCliente.getClientesList());
//			        respGetInfCarCliente.setVisitsList(getInfoVisits(tableVisits));
//			        respGetInfCarCliente.setObjVisitsList(respGetInfCarCliente.getVisitsList());
//			        respGetInfCarCliente.setVisitasClienteList(getInfoVisitasCliente(tableVisits));
			        respGetInfCarCliente.setObjVisitasClienteList(respGetInfCarCliente.getVisitasClienteList());					

					log.info(respGetInfCarCliente.getVisitasClienteList());
				}
				else
				{
					respGetInfCarCliente.setMensaje("FAULT");	
					respGetInfCarCliente.setDescripcion(bapierror);
				}
			} 
			catch (Exception excp) 
			{
				log.error("ERROR: ", excp);
				respGetInfCarCliente.setMensaje("FAULT");
				respGetInfCarCliente.setDescripcion(excp.getMessage());
				throw new ViviendaException(excp.getMessage());
			}
		}		
		return respGetInfCarCliente;	
	}
	
	/**
	 * Método que regresa una objeto de AddClientes cuando se agrega un nuevo cliente  
	 * 
	 * @param criteriosClienteDto 
	 * 			criterios de busqueda de clientes  
	 * @return ResponseAddClienteDto 
	 * 			objeto de respuesta para agregar  clientes 
	 * @throws ViviendaException 
	 * @exception  Exception  
	 */	
	public ResponseAddClienteDto addCliente(CriteriosGetInfCarCliente criteriosClienteDto) throws ViviendaException{
		respAddCliente=new ResponseAddClienteDto();
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
				function= connect.getFunction("ZCSMF_0011_SAVE_CAR_CLIENTE");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosClienteDto.getId_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", criteriosClienteDto.getUsuario());
				function.getImportParameterList().setValue("I_NOMBRE1", criteriosClienteDto.getNombre1C().trim());
				function.getImportParameterList().setValue("I_NOMBRE2", criteriosClienteDto.getNombre2C().trim());
				function.getImportParameterList().setValue("I_APP_PAT", criteriosClienteDto.getApp_patC().trim());
				function.getImportParameterList().setValue("I_APP_MAT", criteriosClienteDto.getApp_matC().trim());
				function.getImportParameterList().setValue("I_FCH_NCM", utils.convierteFecha(criteriosClienteDto.getFch_ncm()));
				function.getImportParameterList().setValue("I_VIA_CON", criteriosClienteDto.getVia_con());
				function.getImportParameterList().setValue("I_TEL_CASA", criteriosClienteDto.getTelfnC().trim());
				function.getImportParameterList().setValue("I_TEL_CEL", criteriosClienteDto.getTlfmoC().trim());
				function.getImportParameterList().setValue("I_TEL_OFICINA", criteriosClienteDto.getTelofC().trim());
				function.getImportParameterList().setValue("I_TEL_EXT", criteriosClienteDto.getExtncC().trim());
				function.getImportParameterList().setValue("I_MAIL_1", criteriosClienteDto.getMail1C().trim());
				function.getImportParameterList().setValue("I_MAIL_2", criteriosClienteDto.getMail2C().trim());
				function.getImportParameterList().setValue("I_ACT_CLI", criteriosClienteDto.getAct_cli());
				
				if (criteriosClienteDto.getIdVendedorActual() != null && criteriosClienteDto.getIdVendedorActual().length > 0 && !criteriosClienteDto.getIdVendedorActual()[0].equals("-1")){
					JCoTable itHeaderVendedorIn = function.getTableParameterList().getTable("IT_REAS_VEND_IN");
					for (int i = 0 ; i < criteriosClienteDto.getIdVendedorActual().length ; i++) {
						itHeaderVendedorIn.appendRow();
						itHeaderVendedorIn.setValue("ID_UT_SUP", criteriosClienteDto.getId_ut_sup());
						itHeaderVendedorIn.setValue("SLSMAN_ORIG", criteriosClienteDto.getIdVendedorActual()[i]);
						itHeaderVendedorIn.setValue("SLSMAN_DEST", criteriosClienteDto.getIdVendedorSiguiente()[i]);
						itHeaderVendedorIn.setValue("ID_MOTIVO_REA", criteriosClienteDto.getIdMotivo()[i]);
						log.info("SLSMAN_ORIG=" + criteriosClienteDto.getIdVendedorActual()[i] + ", SLSMAN_DEST=" + criteriosClienteDto.getIdVendedorSiguiente()[i] + ", ID_MOTIVO_REA=" + criteriosClienteDto.getIdMotivo()[i]);
					}
				}
				
				
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				log.info("ZCSMF_0011_SAVE_CAR_CLIENTE E_SUBRC:" + subrc);
				log.info("ZCSMF_0011_SAVE_CAR_CLIENTE E_MSGBAPI:" + bapierror);
				if (subrc.equals("00")) 
				{	
					log.info("Registando visita ...");
					respAddCliente.setDescripcion(bapierror);
					function = connect.getFunction("ZCSMF_0016_REG_VISITA_CAR_CLI");
					function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosClienteDto.getId_ut_sup());
					function.getImportParameterList().setValue("I_USUARIO", criteriosClienteDto.getUsuario());
					function.getImportParameterList().setValue("I_ID_CAR_CLI", bapierror);
					function.getImportParameterList().setValue("I_SLS_MAN", criteriosClienteDto.getVendedor());
					log.info("Ejecutando movimiento ...");
					connect.execute(function);
					log.info("movimiento ejecutado ...");
					subrc = (String) function.getExportParameterList().getString("E_SUBRC");
					bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
					
					log.info("ZCSMF_0016_REG_VISITA_CAR_CLI E_SUBRC:" + subrc);
					log.info("ZCSMF_0016_REG_VISITA_CAR_CLI E_MSGBAPI:" + bapierror);
					if(subrc.equals("00")){
					respAddCliente.setMensaje("SUCCESS");	
					     
					}else{
						respAddCliente.setMensaje("FAULT");	
						respAddCliente.setDescripcion(bapierror);
					}
						                   
				}
				else
				{
					respAddCliente.setMensaje("FAULT");	
					respAddCliente.setDescripcion(bapierror);
				}
			} 
			catch (Exception excp) 
			{
				log.error("ERROR:", excp);
				respAddCliente.setMensaje("FAULT");
				respAddCliente.setDescripcion(excp.getMessage());
				throw new ViviendaException(excp.getMessage());
			}
		}		
		return respAddCliente;	
	}
	
	/**
	 * Método que regresa una objeto de DeleteClientes cuando se elimina un nuevo cliente  
	 * 
	 * @param criteriosClienteDto 
	 * 			criterios de busqueda de clientes  
	 * @return ResponseDelClienteDto 
	 * 			objeto de respuesta para borrar  clientes 
	 * @throws ViviendaException 
	 * @exception  Exception  
	 */	
	public ResponseDelClienteDto delCliente(CriteriosGetInfCarCliente criteriosClienteDto) throws ViviendaException{
		respDelCliente=new ResponseDelClienteDto();
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
				function= connect.getFunction("ZCSMF_0012_DELE_CAR_CLIENTE");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosClienteDto.getId_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", criteriosClienteDto.getUsuario());
				function.getImportParameterList().setValue("I_ID_CAR_CLI", criteriosClienteDto.getId_selected());
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				if (subrc.equals("00")) 
				{	
					respDelCliente.setMensaje("SUCCESS");	
					respDelCliente.setDescripcion("");
				}
				else
				{
					respDelCliente.setMensaje("FAULT");	
					respDelCliente.setDescripcion(bapierror);
				}
			} 
			catch (Exception excp) 
			{
				log.error("ERROR: ", excp);
				respDelCliente.setMensaje("FAULT");
				respDelCliente.setDescripcion(excp.getMessage());				
				throw new ViviendaException(excp.getMessage());
			}
		}		
		return respDelCliente;	
	}	
	
	/**
	 * Método que regresa una objeto de UpdateClientes cuando se actualizar un nuevo cliente  
	 * 
	 * @param criteriosClienteDto 
	 * 			criterios de busqueda de clientes  
	 * @return ResponseUpdateClienteDto 
	 * 			objeto de respuesta para actualizar clientes 
	 * @throws ViviendaException 
	 * @exception  Exception  
	 */	
	public ResponseUpClienteDto upCliente(CriteriosGetInfCarCliente criteriosClienteDto) throws ViviendaException{
		respUpCliente=new ResponseUpClienteDto();
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
			    //log.info(criteriosClienteDto);
			    log.info("ejecutando ZCSMF_0011_SAVE_CAR_CLIENTE");
			    JCoFunction function;
				function= connect.getFunction("ZCSMF_0011_SAVE_CAR_CLIENTE");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosClienteDto.getId_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", criteriosClienteDto.getUsuario());
				function.getImportParameterList().setValue("I_NOMBRE1", criteriosClienteDto.getNombre1C().trim());
				function.getImportParameterList().setValue("I_NOMBRE2", criteriosClienteDto.getNombre2C().trim());
				function.getImportParameterList().setValue("I_APP_PAT", criteriosClienteDto.getApp_patC().trim());
				function.getImportParameterList().setValue("I_APP_MAT", criteriosClienteDto.getApp_matC().trim());
				function.getImportParameterList().setValue("I_FCH_NCM", utils.convierteFecha(criteriosClienteDto.getFch_ncm()));
				function.getImportParameterList().setValue("I_VIA_CON", criteriosClienteDto.getVia_con());
				function.getImportParameterList().setValue("I_TEL_CASA", criteriosClienteDto.getTelfnC().trim());
				function.getImportParameterList().setValue("I_TEL_CEL", criteriosClienteDto.getTlfmoC().trim());
				function.getImportParameterList().setValue("I_TEL_OFICINA", criteriosClienteDto.getTelofC().trim());
				function.getImportParameterList().setValue("I_TEL_EXT", criteriosClienteDto.getExtncC().trim());
				function.getImportParameterList().setValue("I_MAIL_1", criteriosClienteDto.getMail1C().trim());
				function.getImportParameterList().setValue("I_MAIL_2", criteriosClienteDto.getMail2C().trim());
				function.getImportParameterList().setValue("I_ACT_CLI", "X");
				function.getImportParameterList().setValue("I_ID_CAR_CLI",criteriosClienteDto.getId_selected());
				log.info(function.getImportParameterList().toString());
				if (criteriosClienteDto.getIdVendedorActual() != null && criteriosClienteDto.getIdVendedorActual().length > 0 && !criteriosClienteDto.getIdVendedorActual()[0].equals("-1")){
					JCoTable itHeaderVendedorIn = function.getTableParameterList().getTable("IT_REAS_VEND_IN");
					for (int i = 0 ; i < criteriosClienteDto.getIdVendedorActual().length ; i++) {
						itHeaderVendedorIn.appendRow();						
						itHeaderVendedorIn.setValue("ID_UT_SUP", criteriosClienteDto.getId_ut_sup());
						itHeaderVendedorIn.setValue("SLSMAN_ORIG", criteriosClienteDto.getIdVendedorActual()[i]);
						itHeaderVendedorIn.setValue("SLSMAN_DEST", criteriosClienteDto.getIdVendedorSiguiente()[i]);
						itHeaderVendedorIn.setValue("ID_MOTIVO_REA", criteriosClienteDto.getIdMotivo()[i]);
						log.info("SLSMAN_ORIG=" + criteriosClienteDto.getIdVendedorActual()[i] + ", SLSMAN_DEST=" + criteriosClienteDto.getIdVendedorSiguiente()[i] + ", ID_MOTIVO_REA=" + criteriosClienteDto.getIdMotivo()[i]);
					}
					log.info("IT_REAS_VEND_IN");
					log.info(itHeaderVendedorIn.toString());
				}

				connect.execute(function);

				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				
			    log.info("ZCSMF_0011_SAVE_CAR_CLIENTE ejecutado, E_SUBRC:" + subrc + ", E_MSGBAPI:" + bapierror);

			    if (subrc.equals("00")) 
				{	
					respUpCliente.setMensaje("SUCCESS");	
					respUpCliente.setDescripcion("");
					if(criteriosClienteDto.isReasignaV())
					{
					log.info("ejecutando ZCSMF_0015_CHG_USER_CLI");
					function = connect.getFunction("ZCSMF_0015_CHG_USER_CLI");
					function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosClienteDto.getId_ut_sup());
					function.getImportParameterList().setValue("I_USUARIO", criteriosClienteDto.getUsrAdm());
					function.getImportParameterList().setValue("I_ID_CAR_CLI", criteriosClienteDto.getId_selected());
					function.getImportParameterList().setValue("I_SLS_MAN_NEW", criteriosClienteDto.getVendedor());
					log.info(function.getImportParameterList().toString());
					connect.execute(function);
					subrc = (String) function.getExportParameterList().getString("E_SUBRC");
					bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				    log.info("ZCSMF_0015_CHG_USER_CLI ejecutado, E_SUBRC:" + subrc + ", E_MSGBAPI:" + bapierror);
					if(subrc.equals("00")){
						respUpCliente.setMensaje("SUCCESS");	
						respUpCliente.setDescripcion("");
					}else{
						respUpCliente.setMensaje("FAULT");	
						respUpCliente.setDescripcion(bapierror);
					}	
				}	
					
				}
				else
				{
					respUpCliente.setMensaje("FAULT");	
					respUpCliente.setDescripcion(bapierror);
				}
			} 
			catch (Exception excp) 
			{
				log.error("ERROR: ", excp);
				respUpCliente.setMensaje("FAULT");
				respUpCliente.setDescripcion(excp.getMessage());
				throw new ViviendaException(excp.getMessage());
			}
		}		
		return respUpCliente;	
	}
	
	/**
	 * Método que regresa una lista de los criterios capturados para consultar clientes   
	 * 
	 * @param criterios  
	 * 			cadena con los elementos a buscar   
	 * @return ArrayList<ClientesDto> 
	 * 			lista con los criterios a buscar  
	 */	
	public ArrayList<ClientesDto> llena(String noms,String noms2,String pats,String mats,String ids)
	{
		ArrayList<ClientesDto> carCliListDto = new ArrayList<ClientesDto>();
		try {
			if(noms!="" && noms!=null)
			{
					SAXReader reader = new SAXReader();
					org.dom4j.Document doc = reader.read(new StringReader(noms));
					org.dom4j.Element root = doc.getRootElement();

					for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
					org.dom4j.Element element = (org.dom4j.Element) i.next();
					ClientesDto cliDto = new ClientesDto();
					cliDto.setNombre1(element.node(0).getText());
					carCliListDto.add(cliDto);
				}
			}	
			if(noms2!="" && noms2!=null)
			{
					SAXReader reader = new SAXReader();
					org.dom4j.Document doc = reader.read(new StringReader(noms2));
					org.dom4j.Element root = doc.getRootElement();

					for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
					org.dom4j.Element element = (org.dom4j.Element) i.next();
					ClientesDto cliDto = new ClientesDto();
					cliDto.setNombre2(element.node(0).getText());
					carCliListDto.add(cliDto);
				}
			}
			if(pats!="" && pats!=null)
			{
					SAXReader reader = new SAXReader();
					org.dom4j.Document doc = reader.read(new StringReader(pats));
					org.dom4j.Element root = doc.getRootElement();

					for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
					org.dom4j.Element element = (org.dom4j.Element) i.next();
					ClientesDto cliDto = new ClientesDto();
					cliDto.setApp_pat(element.node(0).getText());
					carCliListDto.add(cliDto);
				}
			}
			if(mats!=""  && mats!=null)
			{
					SAXReader reader = new SAXReader();
					org.dom4j.Document doc = reader.read(new StringReader(mats));
					org.dom4j.Element root = doc.getRootElement();

					for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
					org.dom4j.Element element = (org.dom4j.Element) i.next();
					ClientesDto cliDto = new ClientesDto();
					cliDto.setApp_mat(element.node(0).getText());
					carCliListDto.add(cliDto);
				}
			}
			if(ids!="" && ids!=null)
			{
					SAXReader reader = new SAXReader();
					org.dom4j.Document doc = reader.read(new StringReader(ids));
					org.dom4j.Element root = doc.getRootElement();

					for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
					org.dom4j.Element element = (org.dom4j.Element) i.next();
					ClientesDto cliDto = new ClientesDto();
					cliDto.setId_car_cli(element.node(0).getText());
					carCliListDto.add(cliDto);
				}
			}	
			
						
		} catch (Exception e) {
			log.error("ERROR: ", e);
		}
		return carCliListDto;
  }

/**
	  * Llena el parametro tables IT_HEADER y al mismo tiempo por cada header
	  * manda agegar sus correspondientes lienas en el parametro IT_DETAIL
	  * @param listaPedido Lista de pedidos a crear
	  * @param itHeader    Parametro Table del RFC
	  * @param itDetalle   Parametro Table del RFC
	  */
	 private void llenaItHeaderCriterios(ArrayList<ClientesDto> listaParametros,JCoTable itHeader) {
	  if(!listaParametros.isEmpty()){
	   for(int i = 0; i < listaParametros.size(); i++){
	    itHeader.appendRow();
	    itHeader.setValue("ID_CAR_CLI", listaParametros.get(i).getId_car_cli());
	    itHeader.setValue("NOMBRE1", listaParametros.get(i).getNombre1());
	    itHeader.setValue("NOMBRE2", listaParametros.get(i).getNombre2());
	    itHeader.setValue("APP_PAT", listaParametros.get(i).getApp_pat());
	    itHeader.setValue("APP_MAT", listaParametros.get(i).getApp_mat());
	   }
	  }
	 }
	 
	 /**
		 * Método que regresa la consulta de Vias de contacto para el combo  
		 * 
		 * @param criteriosClienteDto 
		 * 			criterios de busqueda de clientes  
		 * @return ResponseGetInfCarCliente 
		 * 			objeto de respuesta para obtener todas las vias de contacto 
		 * @exception  Exception  
		 */	 
	public ResponseGetInfCarCliente getViaContacto(CriteriosGetInfCarCliente criteriosClienteDto) throws ViviendaException {
		respGetInfCarCliente=new ResponseGetInfCarCliente();
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
				JCoFunction function = connect.getFunction("ZCSMF_0014_GET_CAT_VIACON");
			// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosClienteDto.getId_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", criteriosClienteDto.getUsuario());
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				if (subrc.equals("00")) 
				{	
					tableViaContacto = function.getTableParameterList().getTable("IT_CAT_VIA_CON");
					respGetInfCarCliente.setMensaje("SUCCESS");	
					respGetInfCarCliente.setDescripcion("");
					
					respGetInfCarCliente.setViaContactoList(getViaContacto(tableViaContacto));
					respGetInfCarCliente.setObjViaContactoList(respGetInfCarCliente.getViaContactoList());
				}
				else
				{
					respGetInfCarCliente.setMensaje("FAULT");	
					respGetInfCarCliente.setDescripcion(bapierror);
				}
			} 
			catch (Exception excp) 
			{
				log.error("ERROR: ", excp);				
				respGetInfCarCliente.setMensaje("FAULT");
				respGetInfCarCliente.setDescripcion(excp.getMessage());
				throw new ViviendaException(excp.getMessage());
			}
		}		
		return respGetInfCarCliente;
	}

/**
	 * Método que settea los campos de JcoTable al objeto de Clientes   
	 * 
	 * @param JCoTable 
	 * 			tabla de busqueda de clientes  
	 * @return ResponseGetInfCarCliente 
	 * 			objeto de respuesta para obtener a los clientes  
	 * @exception  Exception  
*/	 
	private ArrayList<ClientesDto> getInfoClientes(JCoTable t,JCoTable visitas) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<ClientesDto> a = new ArrayList<ClientesDto>();
				ArrayList<VisitsDto> lstVisCliente = null;
				VisitsDto visitaCliente=null;
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new ClientesDto());
					a.get(i).setId_car_cli(t.getString("ID_CAR_CLI"));
					a.get(i).setNombre1(t.getString("NOMBRE1"));
					a.get(i).setNombre2(t.getString("NOMBRE2"));
					a.get(i).setApp_pat(t.getString("APP_PAT"));
					a.get(i).setApp_mat(t.getString("APP_MAT"));
					a.get(i).setFch_ncm(utils.convierteFechaVisual(t.getString("FCH_NCM")));
					a.get(i).setTelfn((t.getString("TELFN")));
					a.get(i).setTlfmo((t.getString("TLFMO")));
					a.get(i).setTelof((t.getString("TELOF")));
					a.get(i).setExtnc((t.getString("EXTNC")));
					a.get(i).setMail1((t.getString("MAIL1")));
					a.get(i).setMail2((t.getString("MAIL2")));
					a.get(i).setStatus((t.getInt("STATUS")));
					a.get(i).setStatusx((t.getString("STATUSX")));
					a.get(i).setVia_con(t.getInt("VIA_CON"));
					a.get(i).setVia_conx(t.getString("VIA_CONX"));
					if (visitas != null){
						lstVisCliente = new ArrayList<VisitsDto>();
						if (visitas.getNumRows() > 0){
							visitas.firstRow();
							for (int j = 0; j < visitas.getNumRows(); j++){
								if(a.get(i).getId_car_cli().trim().equals(visitas.getString("ID_CAR_CLI")))
								{
									visitaCliente=	new VisitsDto();
									//visCliente.add(new VisitasClienteDto());
									visitaCliente.setId_car_cli((visitas.getString("ID_CAR_CLI")));
									visitaCliente.setSlsman(visitas.getString("SLSMAN"));
									visitaCliente.setId_visita(visitas.getString("ID_VISITA"));
									visitaCliente.setSlsman_nombre1(visitas.getString("SLSMAN_NOMBRE1"));
									visitaCliente.setSlsman_app_pat(visitas.getString("SLSMAN_APP_PAT"));
									visitaCliente.setSlsman_app_mat(visitas.getString("SLSMAN_APP_MAT"));
									visitaCliente.setStatus(visitas.getInt("STATUS"));
									visitaCliente.setStatusx(visitas.getString("STATUSX"));
									visitaCliente.setAedat_vis(visitas.getString("AEDAT_VIS"));
									visitaCliente.setCputm_vis(visitas.getString("CPUTM_VIS"));
									visitaCliente.setAedat_vis(utils.convierteFechaVisual((visitas.getString("AEDAT_VIS"))));
									lstVisCliente.add(visitaCliente);
								}
								visitas.nextRow();
							}
						  }
						}
					a.get(i).setVisitasClienteList(lstVisCliente);			
					
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}
	
	 /**
	 * Método que settea los campos de JcoTable al objeto de Visitas    
	 * 
	 * @param JCoTable 
	 * 			tabla de busqueda de clientes  
	 * @return ResponseGetInfCarCliente 
	 * 			objeto de respuesta para obtener a los Visitas   
	 * @exception  Exception  
	 */	
	private ArrayList<VisitsDto> getInfoVisits(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<VisitsDto> a = new ArrayList<VisitsDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new VisitsDto());
					a.get(i).setId_ut_sup(t.getString("ID_UT_SUP"));
					a.get(i).setId_car_cli(t.getString("ID_CAR_CLI"));
					a.get(i).setId_visita(t.getString("ID_VISITA"));
					a.get(i).setSlsman(t.getString("SLSMAN"));
					a.get(i).setSlsman_nombre1(t.getString("SLSMAN_NOMBRE1"));
					a.get(i).setSlsman_app_pat(t.getString("SLSMAN_APP_PAT"));
					a.get(i).setSlsman_app_mat((t.getString("SLSMAN_APP_MAT")));
					a.get(i).setStatus((t.getInt("STATUS")));
					a.get(i).setStatusx((t.getString("STATUSX")));
					a.get(i).setAedat_vis(utils.convierteFechaVisual((t.getString("AEDAT_VIS"))));
					a.get(i).setCputm_vis((t.getString("CPUTM_VIS")));
					
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}	
	 /**
	 * Método que settea los campos de JcoTable al objeto de Visitas    
	 * 
	 * @param JCoTable 
	 * 			tabla de busqueda de vistas por cliente   
	 * @return ResponseGetInfCarCliente 
	 * 			objeto de respuesta para obtener a los Visitas   
	 * @exception  Exception  
	 */	
	
	private ArrayList<VisitasClienteDto> getInfoVisitasCliente(JCoTable v) 
	{
		if (v != null){
			if (v.getNumRows() > 0){
				ArrayList<VisitasClienteDto> vc = new ArrayList<VisitasClienteDto>();
				v.firstRow();
				List<ClientesDto> c = new ArrayList<ClientesDto>();
				c=respGetInfCarCliente.getClientesList();
				for (int i = 0; i < v.getNumRows(); i++){
					for(int j=0; j<c.size(); j++)
					{	
						if(c.get(j).getId_car_cli().trim().equals(v.getString("ID_CAR_CLI")))
						{   vc.add(new VisitasClienteDto());
							vc.get(i).setId_cliente(v.getString("ID_CAR_CLI"));
							vc.get(i).setId_vendedor(v.getString("SLSMAN"));
							vc.get(i).setFecha_visita(v.getString("AEDAT_VIS"));
							break;
						}
					}	
					
					v.nextRow();
				}
				return vc;
			}
		}
		return null;
	}
	
		
	/**
	 * Método que settea los campos de JcoTable al objeto de Cotizaciones Detalle   
	 * 
	 * @param JCoTable 
	 * 			tabla de busqueda de clientes  
	 * @return ResponseGetInfCarCliente 
	 * 			objeto de respuesta para obtener a los Visitas   
	 * @exception  Exception  
	 */	
//	private ArrayList<CotizacionDetDto> getCotDet(JCoTable t) 
//	{
//		if (t != null){
//			if (t.getNumRows() > 0){
//				ArrayList<CotizacionDetDto> a = new ArrayList<CotizacionDetDto>();
//				t.firstRow();
//				for (int i = 0; i < t.getNumRows(); i++){
//					a.add(new CotizacionDetDto());
//					a.get(i).setId_cotiz_z(t.getString("ID_COTIZ_Z"));
//					a.get(i).setPosnr_z(t.getInt("POSNR_Z"));
//					a.get(i).setConse(t.getInt("CONSE"));
//					a.get(i).setConce(t.getString("CONCE"));
//					a.get(i).setConcex(t.getString("CONCEX"));
//					a.get(i).setFlim(t.getString("FLIM"));
//					a.get(i).setTotal(t.getDouble("TOTAL"));
//					a.get(i).setSim1(t.getDouble("SIM1"));
//					a.get(i).setSim2(t.getDouble("SIM2"));
//					t.nextRow();
//				}
//				return a;
//			}
//		}
//		return null;
//	}	
	 /**
	 * Método que settea los campos de JcoTable al objeto de Vias de contacto    
	 * 
	 * @param JCoTable 
	 * 			tabla de busqueda de clientes  
	 * @return ResponseGetInfCarCliente 
	 * 			objeto de respuesta para obtener a los clientes  
	 * @exception  Exception  
	 */	
	private ArrayList<ViaContactoDto> getViaContacto(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<ViaContactoDto> a = new ArrayList<ViaContactoDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new ViaContactoDto());
					a.get(i).setVia_con(t.getInt("VIA_CON"));
					a.get(i).setVia_conx(t.getString("VIA_CONX"));
					
		
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}

	 /**
	 * Método que obtiene el Reporte de los clientes    
	 * 
	 * @param criteriosReporteClienteDto 
	 * 			tabla de busqueda de clientes  
	 * @return ResponseGetInfCarCliente 
	 * 			objeto de respuesta para obtener a el reporte de los clientes  
	 * @throws ViviendaException 
	 * @exception  Exception  
	 */	
	public ResponseGetReporteClientesDto getReporteClientes(CriteriosClientesDto criteriosReporteClienteDto) throws ViviendaException{
		ResponseGetReporteClientesDto resultReporte = new ResponseGetReporteClientesDto();
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
				JCoFunction function = connect.getFunction("ZCSMF_0013_GET_CART_BY_USER");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosReporteClienteDto.getId_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", criteriosReporteClienteDto.getUsuario());
				function.getImportParameterList().setValue("I_FECHA_VIS_B", criteriosReporteClienteDto.getFecha_vis_b());
				function.getImportParameterList().setValue("I_FECHA_VIS_E", criteriosReporteClienteDto.getFecha_vis_e());
				
				jcoTableUsers = function.getTableParameterList().getTable("IT_USERS");
				llenaTablaVendedores(criteriosReporteClienteDto.getListaVendedores(), jcoTableUsers);
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				
				if (subrc.equals("00")) 
				{	
					tableClientes = function.getTableParameterList().getTable("IT_CAR_CLIENTES");				
					
					resultReporte.setListaReporteUsuarios(getLlenaListaVendedores(tableClientes));
					resultReporte.setObjListaReporteUsuarios(resultReporte.getListaReporteUsuarios());
					
					resultReporte.setMensaje("SUCCESS");	
					resultReporte.setDescripcion("");
					
				}
				else
				{
					resultReporte.setMensaje("FAULT");	
					resultReporte.setDescripcion(bapierror);
				}
			} 
			catch (Exception excp) 
			{
				log.error("ERROR: ", excp);		
				resultReporte.setMensaje("FAULT");
				resultReporte.setDescripcion(excp.getMessage());
				throw new ViviendaException(excp.getMessage());
			}
		}		
		return resultReporte;	
	}

	 /**
	 * Método que llena la tabla de los vendedores permitidos     
	 * 
	 * @param ArrayList<UsuarioDto> 
	 * 			lista de usuarios obtenidos  
	 *  @param JCoTable 
	 * 			tabla de busqueda de usuarios      
	 */	
	private void llenaTablaVendedores(ArrayList<UsuarioDto> listaUsuarios, JCoTable jcoTableUsers) {
		if(!listaUsuarios.isEmpty()){
			for(int i = 0; i < listaUsuarios.size(); i++){
				jcoTableUsers.appendRow();
				jcoTableUsers.setValue("ID_UT_SUP", listaUsuarios.get(i).getId_ut_sup_cm());
				jcoTableUsers.setValue("USUARIO", listaUsuarios.get(i).getUsuario_cm());
			}
		}
	}
	
	 /**
	 * Método que llena la tabla de los vendedores permitidos     
	 * 
	 * @param JCoTable
	 * 			tabla de consulta para obtener los reportes  
	 *  @return ArrayList<ClientesReporteDto>
	 * 			lista de Clientes para el reporte   
	 */	
	private ArrayList<ClientesReporteDto> getLlenaListaVendedores(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<ClientesReporteDto> a = new ArrayList<ClientesReporteDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new ClientesReporteDto());

					a.get(i).setSlsman(t.getString("SLSMAN"));
					a.get(i).setSlsman_nombre1(t.getString("SLSMAN_NOMBRE1"));
					a.get(i).setSlsman_nombre2(t.getString("SLSMAN_NOMBRE2"));
					a.get(i).setSlsman_app_pat(t.getString("SLSMAN_APP_PAT"));
					a.get(i).setSlsman_app_mat(t.getString("SLSMAN_APP_MAT"));
					a.get(i).setVia_con(t.getString("VIA_CON"));
					a.get(i).setVia_conx(t.getString("VIA_CONX"));
					a.get(i).setId_ut_sup(t.getString("ID_UT_SUP"));
					a.get(i).setId_ut_supx(t.getString("ID_UT_SUPX"));
					a.get(i).setId_ut(t.getString("ID_UT"));
					a.get(i).setId_utx(t.getString("ID_UTX"));
					a.get(i).setId_equnr(t.getString("ID_EQUNR"));
					a.get(i).setId_equnrx(t.getString("ID_EQUNRX"));
					a.get(i).setId_car_cli(t.getString("ID_CAR_CLI"));
					a.get(i).setCar_cli_nombre1(t.getString("CAR_CLI_NOMBRE1"));
					a.get(i).setCar_cli_nombre2(t.getString("CAR_CLI_NOMBRE2"));
					a.get(i).setCar_cli_app_pat(t.getString("CAR_CLI_APP_PAT"));
					a.get(i).setCar_cli_app_mat(t.getString("CAR_CLI_APP_MAT"));
					a.get(i).setVis_id_visita(t.getString("VIS_ID_VISITA"));
					a.get(i).setVis_status(t.getString("VIS_STATUS"));
					a.get(i).setVis_statusx(t.getString("VIS_STATUSX"));
					a.get(i).setVis_aedat_vis(t.getString("VIS_AEDAT_VIS"));
					a.get(i).setVis_cputm_vis(t.getString("VIS_CPUTM_VIS"));
					a.get(i).setNum_visitas(t.getInt("NUM_VISITAS"));
					a.get(i).setSlsman_reas(t.getString("SLSMAN_REAS"));
					a.get(i).setAedat_reas(t.getString("AEDAT_REAS"));
		
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}
	
	/**
	 * Método que regresa una objeto de AddVisitaClientes el cliente ya existe   
	 * 
	 * @param criteriosClienteDto 
	 * 			criterios de busqueda de clientes  
	 * @return ResponseAddClienteDto 
	 * 			objeto de respuesta para agregar las visitas de los  clientes 
	 * @throws ViviendaException 
	 * @exception  Exception  
	 */	
	public ResponseAddClienteDto addVisitaCliente(CriteriosGetInfCarCliente criteriosClienteDto) throws ViviendaException{
		respAddCliente=new ResponseAddClienteDto();
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
				function= connect.getFunction("ZCSMF_0016_REG_VISITA_CAR_CLI");
				// Establece Parametros Import
				if (criteriosClienteDto.getVendedor().trim().equals("")){
					criteriosClienteDto.setVendedor(criteriosClienteDto.getUsuario());
				}
				function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosClienteDto.getId_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", criteriosClienteDto.getUsuario());
				function.getImportParameterList().setValue("I_ID_CAR_CLI", criteriosClienteDto.getId_selected());
				function.getImportParameterList().setValue("I_SLS_MAN", criteriosClienteDto.getVendedor());

				log.info(function.getImportParameterList().toString());
				if (criteriosClienteDto.getIdVendedorActual() != null && criteriosClienteDto.getIdVendedorActual().length > 0 && !criteriosClienteDto.getIdVendedorActual()[0].equals("-1")){
					JCoTable itHeaderVendedorIn = function.getTableParameterList().getTable("IT_REAS_VEND_IN");
					for (int i = 0 ; i < criteriosClienteDto.getIdVendedorActual().length ; i++) {
						itHeaderVendedorIn.appendRow();						
						itHeaderVendedorIn.setValue("ID_UT_SUP", criteriosClienteDto.getId_ut_sup());
						itHeaderVendedorIn.setValue("SLSMAN_ORIG", criteriosClienteDto.getIdVendedorActual()[i]);
						itHeaderVendedorIn.setValue("SLSMAN_DEST", criteriosClienteDto.getIdVendedorSiguiente()[i]);
						itHeaderVendedorIn.setValue("ID_MOTIVO_REA", criteriosClienteDto.getIdMotivo()[i]);
						log.info("SLSMAN_ORIG=" + criteriosClienteDto.getIdVendedorActual()[i] + ", SLSMAN_DEST=" + criteriosClienteDto.getIdVendedorSiguiente()[i] + ", ID_MOTIVO_REA=" + criteriosClienteDto.getIdMotivo()[i]);
					}
					log.info("IT_REAS_VEND_IN");
					log.info(itHeaderVendedorIn.toString());
				}
				
				
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				if (subrc.equals("00")) 
				{	
					respAddCliente.setMensaje("SUCCESS");	
					respAddCliente.setDescripcion("");
				}
				else
				{
					respAddCliente.setMensaje("FAULT");	
					respAddCliente.setDescripcion(bapierror);
				}
			} 
			catch (Exception excp) 
			{
				log.error("ERROR: "+ excp);
				respAddCliente.setMensaje("FAULT");
				respAddCliente.setDescripcion(excp.getMessage());
				throw new ViviendaException(excp.getMessage());
			}
		}		
		return respAddCliente;	
	}
	/**
	 * Método que regresa una objeto de AddClientesSAP cuando se agrega un nuevo cliente en SAP  
	 * 
	 * @param criteriosClienteDto 
	 * 			criterios de busqueda de clientes  
	 * @return ResponseAddClienteDto 
	 * 			objeto de respuesta para agregar  clientes 
	 * @throws ViviendaException 
	 * @exception  Exception  
	 */	
	public ResponseAddClienteDto addClienteSap(ClienteSapDto clientes,ContactoDto contacto) throws ViviendaException{
		respAddCliente=new ResponseAddClienteDto();
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
				function= connect.getFunction("ZCSMF_0031_SAVE_CLIENT");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", clientes.getId_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", clientes.getUsuario());
				 
				if(clientes.getCarcli()!=null)
					function.getImportParameterList().getStructure("I_CLIENTE").setValue("ID_CAR_CLI",clientes.getCarcli().trim());
				
				function.getImportParameterList().getStructure("I_CLIENTE").setValue("NOMBRE1", clientes.getNombre1C().trim());
				function.getImportParameterList().getStructure("I_CLIENTE").setValue("NOMBRE2", clientes.getNombre2C().trim());
				function.getImportParameterList().getStructure("I_CLIENTE").setValue("APP_PAT", clientes.getApp_patC().trim());
				function.getImportParameterList().getStructure("I_CLIENTE").setValue("APP_MAT", clientes.getApp_matC().trim());
				function.getImportParameterList().getStructure("I_CLIENTE").setValue("FCH_NCM", utils.convierteFecha(clientes.getFch_ncm()));
				function.getImportParameterList().getStructure("I_CLIENTE").setValue("SEXO", clientes.getSexoC().trim());
				function.getImportParameterList().getStructure("I_CLIENTE").setValue("TRATAMIENTO", clientes.getTratamientoC().trim());
				function.getImportParameterList().getStructure("I_CLIENTE").setValue("EDO_CIVIL", clientes.getEdo_civilC().trim());
				function.getImportParameterList().getStructure("I_CLIENTE").setValue("RFC", clientes.getRfcC().trim());
				function.getImportParameterList().getStructure("I_CLIENTE").setValue("ID_IFE", clientes.getId_ifeC().trim());
				function.getImportParameterList().getStructure("I_CLIENTE").setValue("ID_PASPRT", clientes.getId_pasprtC().trim());
				function.getImportParameterList().getStructure("I_CLIENTE").setValue("CURP", clientes.getCurpC().trim());
				function.getImportParameterList().getStructure("I_CLIENTE").setValue("REG_FISC", clientes.getRegimenFiscal());
		
				function.getImportParameterList().getStructure("I_DIR_CLIENTE").setValue("CALLE", clientes.getCalleC().trim());
				function.getImportParameterList().getStructure("I_DIR_CLIENTE").setValue("NOEXT", clientes.getNoextC());
				function.getImportParameterList().getStructure("I_DIR_CLIENTE").setValue("NOINT", clientes.getNointC());
				function.getImportParameterList().getStructure("I_DIR_CLIENTE").setValue("COLN", clientes.getColnC().trim());
				function.getImportParameterList().getStructure("I_DIR_CLIENTE").setValue("CDPST", clientes.getCdpstC().trim());
				function.getImportParameterList().getStructure("I_DIR_CLIENTE").setValue("DLMCP", clientes.getDlmcpC().trim());
				function.getImportParameterList().getStructure("I_DIR_CLIENTE").setValue("ESTDO", clientes.getEstdoC().trim());
				function.getImportParameterList().getStructure("I_DIR_CLIENTE").setValue("PAIS", clientes.getPaisC().trim());
				function.getImportParameterList().getStructure("I_DIR_CLIENTE").setValue("TELFN", clientes.getTelfnC().trim());
				function.getImportParameterList().getStructure("I_DIR_CLIENTE").setValue("TELOF", clientes.getTelofC().trim());
				function.getImportParameterList().getStructure("I_DIR_CLIENTE").setValue("EXTNC", clientes.getExtncC().trim());
				function.getImportParameterList().getStructure("I_DIR_CLIENTE").setValue("TLFMO", clientes.getTlfmoC().trim());
				function.getImportParameterList().getStructure("I_DIR_CLIENTE").setValue("MAIL1", clientes.getMail1C().trim());
				function.getImportParameterList().getStructure("I_DIR_CLIENTE").setValue("MAIL2", clientes.getMail2C().trim());
				
			
				function.getImportParameterList().getStructure("I_CONTACTO").setValue("NOMBRE1", contacto.getNombre1Co().trim());
				function.getImportParameterList().getStructure("I_CONTACTO").setValue("NOMBRE2", contacto.getNombre2Co().trim());
				function.getImportParameterList().getStructure("I_CONTACTO").setValue("APP_PAT", contacto.getApp_patCo().trim());
				function.getImportParameterList().getStructure("I_CONTACTO").setValue("APP_MAT", contacto.getApp_matCo().trim());
				function.getImportParameterList().getStructure("I_CONTACTO").setValue("SEXO", contacto.getSexoCo().trim());
				function.getImportParameterList().getStructure("I_CONTACTO").setValue("TRATAMIENTO", contacto.getTratamientoCo().trim());
				function.getImportParameterList().getStructure("I_CONTACTO").setValue("RFC", contacto.getRfcCo().trim());
				function.getImportParameterList().getStructure("I_CONTACTO").setValue("ID_IFE", contacto.getIfeCo().trim());
				function.getImportParameterList().getStructure("I_CONTACTO").setValue("ID_PASPRT", contacto.getPasprtCo().trim());
				function.getImportParameterList().getStructure("I_CONTACTO").setValue("CURP", contacto.getCurpCo().trim());
				if (!contacto.getFechaNacContacto().trim().equals("")){
					function.getImportParameterList().getStructure("I_CONTACTO").setValue("FCH_NCM", utils.convierteFecha(contacto.getFechaNacContacto()));	
				}				
				
				function.getImportParameterList().getStructure("I_DIR_CONTACTO").setValue("CALLE", contacto.getCalleCo().trim());
				function.getImportParameterList().getStructure("I_DIR_CONTACTO").setValue("NOEXT", contacto.getNoextCo());
				function.getImportParameterList().getStructure("I_DIR_CONTACTO").setValue("NOINT", contacto.getNointCo());
				function.getImportParameterList().getStructure("I_DIR_CONTACTO").setValue("COLN", contacto.getColnCo().trim());
				function.getImportParameterList().getStructure("I_DIR_CONTACTO").setValue("CDPST", contacto.getCdpstCo().trim());
				function.getImportParameterList().getStructure("I_DIR_CONTACTO").setValue("DLMCP", contacto.getDlmcpCo().trim());
				function.getImportParameterList().getStructure("I_DIR_CONTACTO").setValue("ESTDO", contacto.getEstdoCo().trim());
				function.getImportParameterList().getStructure("I_DIR_CONTACTO").setValue("PAIS", contacto.getPaisCo()==null ? "" :contacto.getPaisCo().trim());
				function.getImportParameterList().getStructure("I_DIR_CONTACTO").setValue("TELFN", contacto.getTelfnCo().trim());
				function.getImportParameterList().getStructure("I_DIR_CONTACTO").setValue("TELOF", contacto.getTelofCo().trim());
				function.getImportParameterList().getStructure("I_DIR_CONTACTO").setValue("EXTNC", contacto.getExtncCo().trim());
				function.getImportParameterList().getStructure("I_DIR_CONTACTO").setValue("TLFMO", contacto.getTlfmoCo().trim());
				function.getImportParameterList().getStructure("I_DIR_CONTACTO").setValue("MAIL1", contacto.getMail1Co().trim());
				function.getImportParameterList().getStructure("I_DIR_CONTACTO").setValue("MAIL2", contacto.getMail2Co().trim());
				
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				if (subrc.equals("00")) 
				{	
					respAddCliente.setMensaje("SUCCESS");
					respAddCliente.setDescripcion("");
					respAddCliente.setId_cliente_z((String) function.getExportParameterList().getString("E_ID_CLIENTE_Z"));
					respAddCliente.setId_cliente_sap((String) function.getExportParameterList().getString("E_ID_CLIENTE_SAP"));
				}
				else
				{
					respAddCliente.setMensaje("FAULT");	
					respAddCliente.setDescripcion(bapierror);
				}
			} 
			catch (Exception excp) 
			{
				log.error("ERROR: ", excp);				
				respAddCliente.setMensaje("FAULT");
				respAddCliente.setDescripcion(excp.getMessage());
				throw new ViviendaException(excp.getMessage());
			}
		}		
		return respAddCliente;	
	}

	/**
	 * Método que regresa una lista de clientes Sap encontrados de acuerdo a los criteriso de busqueda 
	 * 
	 * @param criteriosClienteSapDto 
	 * 			criterios de busqueda de clientes Sap (ife,rfc,nombres,apellidos,idClienteZ,idClienteSap)  
	 * @return ResponseGetInfClienteSap 
	 * 			objeto de respuesta para informacion de clientes Sap 
	 * @throws ViviendaException 
	 * @exception  Exception  
	 */		
	public ResponseGetInfClienteSap getClientesSap(CriteriosGetInfClienteSap criteriosClienteSapDto,ResponseGetCatalogosDto respCatalogosDTO) throws ViviendaException{
		respGetInfClienteSap=new ResponseGetInfClienteSap();
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
				JCoFunction function = connect.getFunction("ZCSMF_0032_GET_CLIENT");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosClienteSapDto.getId_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", criteriosClienteSapDto.getUsuario());
				JCoTable itHeader = function.getTableParameterList().getTable("IT_CLIENTE");
				llenaItHeaderCriteriosSap(llenaSap(criteriosClienteSapDto.getXmlCliZ(),criteriosClienteSapDto.getXmlCliSap(),criteriosClienteSapDto.getXmlNom(),criteriosClienteSapDto.getXmlNom2(),criteriosClienteSapDto.getXmlApPat(),criteriosClienteSapDto.getXmlApMat(),criteriosClienteSapDto.getXmlCarteraCliente(),criteriosClienteSapDto), itHeader);
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				if (subrc.equals("00")) 
				{	
					tableClientesSap = function.getTableParameterList().getTable("IT_CLIENTE");
					tableDirClientes = function.getTableParameterList().getTable("IT_DIR_CLIENTE");
					tablePrestanombres = function.getTableParameterList().getTable("IT_CONTACTO");
					tableDirPrestan = function.getTableParameterList().getTable("IT_DIR_CONTACTO");
	
					ArrayList<ContactoByClienteDto> clientesSAP = complementaInfo(tableClientesSap,tableDirClientes,tablePrestanombres,tableDirPrestan,respCatalogosDTO,criteriosClienteSapDto.getId_ut_sup(),criteriosClienteSapDto.getUsuario());
					respGetInfClienteSap.setClientesSapList(clientesSAP);
					respGetInfClienteSap.setObjClientesSapList(respGetInfClienteSap.getClientesSapList());

					respGetInfClienteSap.setMensaje("SUCCESS");	
					respGetInfClienteSap.setDescripcion("");
				}
				else
				{
					respGetInfClienteSap.setMensaje("FAULT");	
					respGetInfClienteSap.setDescripcion(bapierror);
				}
			} 
			catch (Exception excp) 
			{
				log.error("ERROR: ", excp);				
				respGetInfClienteSap.setMensaje("FAULT");
				respGetInfClienteSap.setDescripcion(excp.getMessage());
				throw new ViviendaException(excp.getMessage());
			}
		}		
		return respGetInfClienteSap;	
	}
	
/**
	 * Método que regresa una lista de los criterios capturados para consultar clientes   
	 * 
	 * @param criterios  
	 * 			cadena con los elementos a buscar   
	 * @return ArrayList<ClientesDto> 
	 * 			lista con los criterios a buscar  
 */	
	public ArrayList<ClienteSapDto> llenaSap(String z,String sap,String noms,String noms2,String pats,String mats, String carteraCliente, CriteriosGetInfClienteSap paramForma)
	{
		ArrayList<ClienteSapDto> cliSapListDto = new ArrayList<ClienteSapDto>();
		try {
			if (carteraCliente!=null)
			{
				if (!carteraCliente.trim().equals("")){
					SAXReader reader = new SAXReader();
					org.dom4j.Document doc = reader.read(new StringReader(carteraCliente));
					org.dom4j.Element root = doc.getRootElement();
	
					for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
						org.dom4j.Element element = (org.dom4j.Element) i.next();
						ClienteSapDto cliDto = new ClienteSapDto();
						cliDto.setIdCarCliente(element.node(0).getText());
						cliSapListDto.add(cliDto);
					}
				}
			}
			
			if (z!=null)
			{
				if (!z.trim().equals("")){
					SAXReader reader = new SAXReader();
					org.dom4j.Document doc = reader.read(new StringReader(z));
					org.dom4j.Element root = doc.getRootElement();
	
					for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
						org.dom4j.Element element = (org.dom4j.Element) i.next();
						ClienteSapDto cliDto = new ClienteSapDto();
						cliDto.setId_cliente_z(element.node(0).getText());
						cliSapListDto.add(cliDto);
					}
				}
			}
			if(sap!=null){
				if (!sap.trim().equals("")){
					SAXReader reader = new SAXReader();
					org.dom4j.Document doc = reader.read(new StringReader(sap));
					org.dom4j.Element root = doc.getRootElement();
	
					for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
						org.dom4j.Element element = (org.dom4j.Element) i.next();
						ClienteSapDto cliDto = new ClienteSapDto();
						cliDto.setId_cliente_sap(element.node(0).getText());
						cliSapListDto.add(cliDto);
					}
				}
			}
			
			if (paramForma!=null) {
					if (paramForma.getArrNombre1()!=null || paramForma.getArrNombre2()!=null ){
						if (paramForma.getArrNombre1().trim().equals("1") || paramForma.getArrNombre2().trim().equals("1") ){
							if(noms!=null){
								if(!noms.trim().equals("")){
								SAXReader reader = new SAXReader();
								org.dom4j.Document doc = reader.read(new StringReader(noms));
								org.dom4j.Element root = doc.getRootElement();
				
								for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
									org.dom4j.Element element = (org.dom4j.Element) i.next();
									ClienteSapDto cliDto = new ClienteSapDto();
									cliDto.setNombre1C(element.node(0).getText());
									cliSapListDto.add(cliDto);
								}
								}
							}
							
							if(noms2!=null ){
								if(!noms2.trim().equals("")){
								SAXReader reader = new SAXReader();
								org.dom4j.Document doc = reader.read(new StringReader(noms2));
								org.dom4j.Element root = doc.getRootElement();
				
								for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
									org.dom4j.Element element = (org.dom4j.Element) i.next();
									ClienteSapDto cliDto = new ClienteSapDto();
									cliDto.setNombre2C(element.node(0).getText());
									cliSapListDto.add(cliDto);
								}
								}
							}
						}else{
							   if (paramForma.getArrNombre1().trim().equals("0") || paramForma.getArrNombre2().trim().equals("0") ){
								   ClienteSapDto cliDto =null;
								   if(noms!=null){
										if(!noms.trim().equals("")){
											if (cliDto==null){
												cliDto = new ClienteSapDto();
											}
											SAXReader reader = new SAXReader();
											org.dom4j.Document doc = reader.read(new StringReader(noms));
											org.dom4j.Element root = doc.getRootElement();
							
											for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
												org.dom4j.Element element = (org.dom4j.Element) i.next();
												
												cliDto.setNombre1C(element.node(0).getText());
												//cliSapListDto.add(cliDto);
											}
										}
										
										
									}
									
									if(noms2!=null ){
										if(!noms2.trim().equals("")){
											if (cliDto==null){
												cliDto = new ClienteSapDto();
											}
											SAXReader reader = new SAXReader();
											org.dom4j.Document doc = reader.read(new StringReader(noms2));
											org.dom4j.Element root = doc.getRootElement();
							
											for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
												org.dom4j.Element element = (org.dom4j.Element) i.next();
												//ClienteSapDto cliDto = new ClienteSapDto();
												cliDto.setNombre2C(element.node(0).getText());
												//cliSapListDto.add(cliDto);
											}
										}
									}  
									
									if (cliDto!=null){
										cliSapListDto.add(cliDto);
									}
									
							   }
						}
				   } else {
					   if(noms!=null){
							if(!noms.trim().equals("")){
							SAXReader reader = new SAXReader();
							org.dom4j.Document doc = reader.read(new StringReader(noms));
							org.dom4j.Element root = doc.getRootElement();
			
							for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
								org.dom4j.Element element = (org.dom4j.Element) i.next();
								ClienteSapDto cliDto = new ClienteSapDto();
								cliDto.setNombre1C(element.node(0).getText());
								cliSapListDto.add(cliDto);
							}
							}
						}
						
						if(noms2!=null ){
							if(!noms2.trim().equals("")){
							SAXReader reader = new SAXReader();
							org.dom4j.Document doc = reader.read(new StringReader(noms2));
							org.dom4j.Element root = doc.getRootElement();
			
							for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
								org.dom4j.Element element = (org.dom4j.Element) i.next();
								ClienteSapDto cliDto = new ClienteSapDto();
								cliDto.setNombre2C(element.node(0).getText());
								cliSapListDto.add(cliDto);
							}
							}
						}
				   }
		   }else{
			   if(noms!=null){
					if(!noms.trim().equals("")){
					SAXReader reader = new SAXReader();
					org.dom4j.Document doc = reader.read(new StringReader(noms));
					org.dom4j.Element root = doc.getRootElement();
	
					for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
						org.dom4j.Element element = (org.dom4j.Element) i.next();
						ClienteSapDto cliDto = new ClienteSapDto();
						cliDto.setNombre1C(element.node(0).getText());
						cliSapListDto.add(cliDto);
					}
					}
				}
				
				if(noms2!=null ){
					if(!noms2.trim().equals("")){
					SAXReader reader = new SAXReader();
					org.dom4j.Document doc = reader.read(new StringReader(noms2));
					org.dom4j.Element root = doc.getRootElement();
	
					for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
						org.dom4j.Element element = (org.dom4j.Element) i.next();
						ClienteSapDto cliDto = new ClienteSapDto();
						cliDto.setNombre2C(element.node(0).getText());
						cliSapListDto.add(cliDto);
					}
					}
				}	
		   }
			
			if(pats!=null ){
				if(!pats.trim().equals("")){
				SAXReader reader = new SAXReader();
				org.dom4j.Document doc = reader.read(new StringReader(pats));
				org.dom4j.Element root = doc.getRootElement();

				for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
					org.dom4j.Element element = (org.dom4j.Element) i.next();
					ClienteSapDto cliDto = new ClienteSapDto();
					cliDto.setApp_patC(element.node(0).getText());
					cliSapListDto.add(cliDto);
				}
				}
			}
			if(mats!=null ){
				if(!mats.trim().equals("")){
				SAXReader reader = new SAXReader();
				org.dom4j.Document doc = reader.read(new StringReader(mats));
				org.dom4j.Element root = doc.getRootElement();

				for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
					org.dom4j.Element element = (org.dom4j.Element) i.next();
					ClienteSapDto cliDto = new ClienteSapDto();
					cliDto.setApp_matC(element.node(0).getText());
					cliSapListDto.add(cliDto);
				}
				}
			}
						
		} catch (Exception e) {
			log.error("ERROR: ", e);				
		}
		return cliSapListDto;
  }

/**
	  * Llena el parametro tables IT_HEADER y al mismo tiempo por cada header
	  * manda agegar sus correspondientes lienas en el parametro IT_DETAIL
	  * @param listaParametros  Lista de parametros para buscar 
	  * @param itHeader    Parametro Table del RFC
	  * @param itDetalle   Parametro Table del RFC
	  */
	 private void llenaItHeaderCriteriosSap(ArrayList<ClienteSapDto> listaParametrosSap,
	                             JCoTable itHeader) {
	  if(!listaParametrosSap.isEmpty()){
	   for(int i = 0; i < listaParametrosSap.size(); i++){
	    itHeader.appendRow();
	    itHeader.setValue("ID_CLIENTE_Z", listaParametrosSap.get(i).getId_cliente_z());
	    itHeader.setValue("ID_CLIENTE_SAP", listaParametrosSap.get(i).getId_cliente_sap());
	    itHeader.setValue("ID_CAR_CLI", listaParametrosSap.get(i).getIdCarCliente());
	    itHeader.setValue("NOMBRE1", listaParametrosSap.get(i).getNombre1C());
	    itHeader.setValue("NOMBRE2", listaParametrosSap.get(i).getNombre2C());
	    itHeader.setValue("APP_PAT", listaParametrosSap.get(i).getApp_patC());
	    itHeader.setValue("APP_MAT", listaParametrosSap.get(i).getApp_matC());
	   }
	  }
	 }
	 /**
		 * Método que settea los campos de JcoTable al objeto de Clientes SAP 
		 * 
		 * @param JCoTable 
		 * 			tabla de busqueda de clientes  Sap
		 * @return ArrayList<ClienteSapDto> 
		 * 			objeto de respuesta con los datos de clientes sap setteados   
		 * @exception  Exception  
	*/	 
		private ArrayList<ClienteSapDto> getInfoClientesSap(JCoTable t,ResponseGetCatalogosDto respCatalogosDTO) 
		{
			CatalogosDaoImp descs =new CatalogosDaoImp();
			try {
				if (t != null){
					if (t.getNumRows() > 0){
						ArrayList<ClienteSapDto> a=new ArrayList<ClienteSapDto>();
						//ArrayList<ClienteSapDto> a = new ArrayList<ClienteSapDto>();
						t.firstRow();
						for (int i = 0; i < t.getNumRows(); i++){
							
							a.add(new ClienteSapDto());
							a.get(i).setId_cliente_z(t.getString("ID_CLIENTE_Z"));
							a.get(i).setId_cliente_sap(t.getString("ID_CLIENTE_SAP"));
							a.get(i).setNombre1C(t.getString("NOMBRE1"));
							a.get(i).setNombre2C(t.getString("NOMBRE2"));
							a.get(i).setApp_patC(t.getString("APP_PAT"));
							a.get(i).setApp_matC(t.getString("APP_MAT"));
							a.get(i).setFch_ncm(utils.convierteFechaVisual((t.getString("FCH_NCM"))));
							a.get(i).setSexoC(t.getString("SEXO"));
							a.get(i).setSexoCDesc(descs.getSexoDesc(respCatalogosDTO.getSexoList(), t.getString("SEXO")));
							a.get(i).setTratamientoC(t.getString("TRATAMIENTO"));
							a.get(i).setTratamientoCDesc(descs.getTratamientoDesc(respCatalogosDTO.getTratamientoList(), t.getString("TRATAMIENTO")));
							a.get(i).setEdo_civilC((t.getString("EDO_CIVIL")));
							a.get(i).setEdo_civilCDesc(descs.getEdoCivilDesc(respCatalogosDTO.getEdoCivilList(), t.getString("EDO_CIVIL")));
							a.get(i).setRfcC((t.getString("RFC")));
							a.get(i).setId_ifeC((t.getString("ID_IFE")));
							a.get(i).setId_pasprtC((t.getString("ID_PASPRT")));
							a.get(i).setCurpC(t.getString("CURP"));
							if (t.getString("REG_FISC")!=null){
							   if (t.getString("REG_FISC").trim().equals("01")){
								   a.get(i).setRegimenFiscal("PERSONA FISICA");
							   }
							   if (t.getString("REG_FISC").trim().equals("02")){
								   a.get(i).setRegimenFiscal("PERSONA MORAL");
							   }
							}			
							
							t.nextRow();
						}
						return a;
					}
				}			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	
			return null;
		}
		 /**
		 * Método que settea los campos de JcoTable al objeto de Clientes SAP 
		 * 
		 * @param JCoTable 
		 * 			tabla de busqueda de clientes  Sap
		 * @return ArrayList<ClienteSapDto> 
		 * 			objeto de respuesta con los datos de clientes sap setteados   
		 * @exception  Exception  
	*/	 
		private ArrayList<ClientesDto> getInfoNamesClientes(JCoTable t) 
		{
			CatalogosDaoImp descs =new CatalogosDaoImp();
			try {
				if (t != null){
					if (t.getNumRows() > 0){
						ArrayList<ClientesDto> a=new ArrayList<ClientesDto>();
						
						t.firstRow();
						for (int i = 0; i < t.getNumRows(); i++){
							a.add(new ClientesDto());
							a.get(i).setNombre1(t.getString("NOMBRE1"));
							a.get(i).setNombre2(t.getString("NOMBRE2"));
							a.get(i).setApp_pat(t.getString("APP_PAT"));
							a.get(i).setApp_mat(t.getString("APP_MAT"));
							a.get(i).setFch_ncm(utils.convierteFechaVisual((t.getString("FCH_NCM"))));
							t.nextRow();
						}
						return a;
					}
				}			
			} catch (Exception e) {
				log.error("ERROR: ", e);		
				
			}
	
			return null;
		}	 
	
		 /**
		 * Método que settea los campos de JcoTable al objeto de Direccion de Clientes 
		 * 
		 * @param JCoTable 
		 * 			tabla de busqueda de direcciones de clientes  Sap
		 * @return ArrayList<ClienteSapDto>
		 * 			objeto de respuesta con los datos de direccion de clientes sap setteados   
		 * @exception  Exception  
	*/	 
		private ArrayList<ClienteSapDto> getInfoDirClientesSap(JCoTable t) 
		{
			CatalogosDaoImp descs =new CatalogosDaoImp();
			if (t != null){
				if (t.getNumRows() > 0){
					ArrayList<ClienteSapDto> a = new ArrayList<ClienteSapDto>();
					t.firstRow();
					for (int i = 0; i < t.getNumRows(); i++){
						a.add(new ClienteSapDto());
						a.get(i).setId_cliente_zDir(t.getString("ID_CLIENTE_Z"));
						a.get(i).setCalleC(t.getString("CALLE"));
						a.get(i).setNoextC(t.getString("NOEXT"));
						a.get(i).setNointC(t.getString("NOINT"));
						a.get(i).setColnC(t.getString("COLN"));
						a.get(i).setCdpstC((t.getString("CDPST")));
						a.get(i).setDlmcpC(t.getString("DLMCP"));
						a.get(i).setEstdoC(t.getString("ESTDO"));
						a.get(i).setPaisC((t.getString("PAIS")));
						a.get(i).setTelfnC((t.getString("TELFN")));
						a.get(i).setTelofC((t.getString("TELOF")));
						a.get(i).setExtncC((t.getString("EXTNC")));
						a.get(i).setTlfmoC(t.getString("TLFMO"));
						a.get(i).setMail1C(t.getString("MAIL1"));
						a.get(i).setMail2C(t.getString("MAIL2"));
						
						t.nextRow();
					}
					return a;
				}
			}
			return null;
		}	 
		
		 /**
			 * Método que settea los campos de JcoTable al objeto de Contacto del cliente SAP 
			 * 
			 * @param JCoTable 
			 * 			tabla de busqueda del contacto  del cliente SAP  
			 * @return ArrayList<ClienteSapDto> 
			 * 			objeto de respuesta con los datos de contacto setteados   
			 * @exception  Exception  
		*/	 
			private ArrayList<ContactoDto> getInfoContacto(JCoTable t,ResponseGetCatalogosDto respCatalogosDTO) 
			{
				if (t != null){
					if (t.getNumRows() > 0){
						ArrayList<ContactoDto> a=new ArrayList<ContactoDto>();
						//ArrayList<ClienteSapDto> a = new ArrayList<ClienteSapDto>();
						t.firstRow();
						for (int i = 0; i < t.getNumRows(); i++){
							a.add(new ContactoDto());
							a.get(i).setId_cliente_z(t.getString("ID_CLIENTE_Z"));
							a.get(i).setNombre1Co(t.getString("NOMBRE1"));
							a.get(i).setNombre2Co(t.getString("NOMBRE2"));
							a.get(i).setApp_patCo(t.getString("APP_PAT"));
							a.get(i).setApp_matCo(t.getString("APP_MAT"));
							a.get(i).setSexoCo(t.getString("SEXO"));
							for(int j=0;j<respCatalogosDTO.getSexoList().size();j++)
							{
								if(a.get(i).getSexoCo().equals(respCatalogosDTO.getSexoList().get(j).getSexo()))
									a.get(i).setSexoCoDesc(respCatalogosDTO.getSexoList().get(j).getSexox());
							}
							a.get(i).setTratamientoCo(t.getString("TRATAMIENTO"));
							for(int j=0;j<respCatalogosDTO.getTratamientoList().size();j++)
							{
								if(a.get(i).getTratamientoCo().equals(respCatalogosDTO.getTratamientoList().get(j).getTratamiento()))
									a.get(i).setTratamientoCoDesc(respCatalogosDTO.getTratamientoList().get(j).getTratamientox());
							}
							a.get(i).setRfcCo((t.getString("RFC")));
							a.get(i).setIfeCo((t.getString("ID_IFE")));
							a.get(i).setPasprtCo((t.getString("ID_PASPRT")));
							a.get(i).setCurpCo(t.getString("CURP"));
							if (!t.getString("FCH_NCM").trim().equals("")){
								a.get(i).setFechaNacContacto(utils.convierteFechaVisual((t.getString("FCH_NCM"))));
							}
	
							t.nextRow();
						}
						return a;
					}
				}
				return null;
			}	 
			 /**
			 * Método que settea los campos de JcoTable al objeto de Direccion del contacto del Clientes 
			 * 
			 * @param JCoTable 
			 * 			tabla de busqueda de direcciones de clientes  Sap
			 * @return ArrayList<ClienteSapDto>
			 * 			objeto de respuesta con los datos de direccion de clientes sap setteados   
			 * @exception  Exception  
		*/	 
			private ArrayList<ContactoDto> getInfoDirContacto(JCoTable t) 
			{
				if (t != null){
					if (t.getNumRows() > 0){
						ArrayList<ContactoDto> a = new ArrayList<ContactoDto>();
						t.firstRow();
						for (int i = 0; i < t.getNumRows(); i++){
							a.add(new ContactoDto());
							a.get(i).setId_cliente_zDir(t.getString("ID_CLIENTE_Z"));
							a.get(i).setCalleCo(t.getString("CALLE"));
							a.get(i).setNoextCo(t.getString("NOEXT"));
							a.get(i).setNointCo(t.getString("NOINT"));
							a.get(i).setColnCo(t.getString("COLN"));
							a.get(i).setCdpstCo((t.getString("CDPST")));
							a.get(i).setDlmcpCo(t.getString("DLMCP"));
							a.get(i).setEstdoCo(t.getString("ESTDO"));
							a.get(i).setPaisCo((t.getString("PAIS")));
							a.get(i).setTelfnCo((t.getString("TELFN")));
							a.get(i).setTelofCo((t.getString("TELOF")));
							a.get(i).setExtncCo((t.getString("EXTNC")));
							a.get(i).setTlfmoCo(t.getString("TLFMO"));
							a.get(i).setMail1Co(t.getString("MAIL1"));
							a.get(i).setMail2Co(t.getString("MAIL2"));
							t.nextRow();
						}
						return a;
					}
				}
				return null;
			}	 
			 /**
			 * Método que settea los campos de JcoTable al objeto de Visitas    
			 * 
			 * @param JCoTable 
			 * 			tabla de busqueda de vistas por cliente   
			 * @return ResponseGetInfCarCliente 
			 * 			objeto de respuesta para obtener a los Visitas   
			 * @exception  Exception  
			 */	
			
			private ArrayList<ContactoByClienteDto> complementaInfo(JCoTable cli,JCoTable dirCli,JCoTable con,JCoTable dirCon,ResponseGetCatalogosDto respCatalogosDTO,String id,String usr ) 
			{
				        CriteriosGetCatalogosDto criteriosGetCat = new CriteriosGetCatalogosDto();
				        criteriosGetCat.setI_usuario(usr);
			 			criteriosGetCat.setI_id_ut_sup(id);
			 						 			
						ArrayList<ContactoByClienteDto> listaClientesSap= new ArrayList<ContactoByClienteDto>();
						List<ClienteSapDto> clientes = new ArrayList<ClienteSapDto>();
						clientes=getInfoClientesSap(cli,respCatalogosDTO);
						ArrayList<ClienteSapDto> dirClientes = getInfoDirClientesSap(dirCli);
						
						List<ContactoDto> contactos = new ArrayList<ContactoDto>();
						contactos = getInfoContacto(con,respCatalogosDTO);
						ArrayList<ContactoDto> dirContactos = getInfoDirContacto(dirCon);
						ContactoByClienteDto contByCli;
						try {
						
							for (int i = 0; i < clientes.size(); i++){
								contByCli= new ContactoByClienteDto();
								for(int j=0; j<dirClientes.size(); j++){
									if( dirClientes.get(j).getId_cliente_zDir().equals(clientes.get(i).getId_cliente_z())){
										clientes.get(i).setCalleC(dirClientes.get(j).getCalleC());
										clientes.get(i).setNoextC(dirClientes.get(j).getNoextC());
										clientes.get(i).setNointC(dirClientes.get(j).getNointC());
										clientes.get(i).setColnC(dirClientes.get(j).getColnC());
										clientes.get(i).setCdpstC(dirClientes.get(j).getCdpstC());
										clientes.get(i).setDlmcpC(dirClientes.get(j).getDlmcpC());
										clientes.get(i).setEstdoC(dirClientes.get(j).getEstdoC());
										clientes.get(i).setPaisC(dirClientes.get(j).getPaisC());
										
										criteriosGetCat.setI_paises('X');
										criteriosGetCat.setId_pais(clientes.get(i).getPaisC());
										criteriosGetCat.setI_regiones('X');
										respCatalogosDTO = catalogosService.getCatalogos(criteriosGetCat);
										for(int k=0;k<respCatalogosDTO.getTratamientoList().size();k++)
										{
											if(clientes.get(i).getTratamientoC().equals(respCatalogosDTO.getTratamientoList().get(k).getTratamiento()))
												clientes.get(i).setTratamientoCDesc(respCatalogosDTO.getTratamientoList().get(k).getTratamientox());
										}
										for(int k=0;k<respCatalogosDTO.getSexoList().size();k++)
										{
											if(clientes.get(i).getSexoC().equals(respCatalogosDTO.getSexoList().get(k).getSexo()))
												clientes.get(i).setSexoCDesc(respCatalogosDTO.getSexoList().get(k).getSexox());
										}
										for(int k=0;k<respCatalogosDTO.getEdoCivilList().size();k++)
										{
											if(clientes.get(i).getEdo_civilC().equals(respCatalogosDTO.getEdoCivilList().get(k).getEdocvl()))
												clientes.get(i).setEdo_civilCDesc(respCatalogosDTO.getEdoCivilList().get(k).getEdocvlx());
										}
										for(int k=0;k<respCatalogosDTO.getPaisesList().size();k++)
										{
											if(clientes.get(i).getPaisC().equals(respCatalogosDTO.getPaisesList().get(k).getPais()))
												clientes.get(i).setPaisCDesc(respCatalogosDTO.getPaisesList().get(k).getPaisx());
										}
										for(int k=0;k<respCatalogosDTO.getRegionesList().size();k++)
										{
											if(clientes.get(i).getEstdoC().equals(respCatalogosDTO.getRegionesList().get(k).getRegion()))
												clientes.get(i).setEstdoCDesc(respCatalogosDTO.getRegionesList().get(k).getRegionx());
										}
										clientes.get(i).setTelfnC(dirClientes.get(j).getTelfnC());
										clientes.get(i).setTelofC(dirClientes.get(j).getTelofC());
										clientes.get(i).setExtncC(dirClientes.get(j).getExtncC());
										clientes.get(i).setTlfmoC(dirClientes.get(j).getTlfmoC());
										clientes.get(i).setMail1C(dirClientes.get(j).getMail1C());
										clientes.get(i).setMail2C(dirClientes.get(j).getMail2C());
										contByCli.setCliente((ClienteSapDto) clientes.get(i));
									}
							 }
								if(contactos!=null)
								{
									for(int z=0; z<contactos.size(); z++){
										if( clientes.get(i).getId_cliente_z().equals(contactos.get(z).getId_cliente_z())){
											for(int k=0; k<dirContactos.size(); k++){
												if( contactos.get(z).getId_cliente_z().equals(dirContactos.get(k).getId_cliente_zDir())){
													contactos.get(z).setCalleCo(dirContactos.get(k).getCalleCo());
													contactos.get(z).setNoextCo(dirContactos.get(k).getNoextCo());
													contactos.get(z).setNointCo(dirContactos.get(k).getNointCo());
													contactos.get(z).setColnCo(dirContactos.get(k).getColnCo());
													contactos.get(z).setCdpstCo(dirContactos.get(k).getCdpstCo());
													contactos.get(z).setDlmcpCo(dirContactos.get(k).getDlmcpCo());
													contactos.get(z).setEstdoCo(dirContactos.get(k).getEstdoCo());
													contactos.get(z).setPaisCo(dirContactos.get(k).getPaisCo());
													criteriosGetCat.setI_paises('X');
													criteriosGetCat.setId_pais(contactos.get(i).getPaisCo());
													criteriosGetCat.setI_regiones('X');
													respCatalogosDTO = catalogosService.getCatalogos(criteriosGetCat);
													for(int f=0;f<respCatalogosDTO.getTratamientoList().size();f++)
													{
														if(contactos.get(z).getTratamientoCo().equals(respCatalogosDTO.getTratamientoList().get(f).getTratamiento()))
															contactos.get(z).setTratamientoCoDesc(respCatalogosDTO.getTratamientoList().get(f).getTratamientox());
													}
													for(int f=0;f<respCatalogosDTO.getSexoList().size();f++)
													{
														if(contactos.get(z).getSexoCo().equals(respCatalogosDTO.getSexoList().get(f).getSexo()))
															contactos.get(z).setSexoCoDesc(respCatalogosDTO.getSexoList().get(f).getSexox());
													}
													for(int f=0;f<respCatalogosDTO.getPaisesList().size();f++)
													{
														if(contactos.get(z).getPaisCo().equals(respCatalogosDTO.getPaisesList().get(f).getPais()))
															contactos.get(z).setPaisCoDesc(respCatalogosDTO.getPaisesList().get(f).getPaisx());
													}
													for(int f=0;f<respCatalogosDTO.getRegionesList().size();f++)
													{
														if(contactos.get(i).getEstdoCo().equals(respCatalogosDTO.getRegionesList().get(f).getRegion()))
															contactos.get(i).setEstdoCoDesc(respCatalogosDTO.getRegionesList().get(f).getRegionx());
													}
													contactos.get(z).setTelfnCo(dirContactos.get(k).getTelfnCo());
													contactos.get(z).setTelofCo(dirContactos.get(k).getTelofCo());
													contactos.get(z).setExtncCo(dirContactos.get(k).getExtncCo());
													contactos.get(z).setTlfmoCo(dirContactos.get(k).getTlfmoCo());
													contactos.get(z).setMail1Co(dirContactos.get(k).getMail1Co());
													contactos.get(z).setMail2Co(dirContactos.get(k).getMail2Co());
													contByCli.setContacto((ContactoDto) contactos.get(z));
												}
												
											}
												
										}
								  }
								}
								listaClientesSap.add(contByCli);
							}
							
						} catch (ViviendaException e) {
							log.error("ERROR: ",e);							
						}
						
		return listaClientesSap;
}

			/**
			 * Método que regresa una lista de clientes Sap encontrados de acuerdo a los criteriso de busqueda 
			 * 
			 * @param criteriosClienteSapDto 
			 * 			criterios de busqueda de clientes Sap (ife,rfc,nombres,apellidos,idClienteZ,idClienteSap)  
			 * @return ResponseGetInfClienteSap 
			 * 			objeto de respuesta para informacion de clientes Sap 
			 * @throws ViviendaException 
			 * @exception  Exception  
			 */		
			public ResponseGetInfClienteSap getNamesClientesSap(CriteriosGetInfClienteSap criteriosClienteSapDto) throws ViviendaException{
				respGetInfClienteSap=new ResponseGetInfClienteSap();
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
						JCoFunction function = connect.getFunction("ZCSMF_0032_GET_CLIENT");
						// Establece Parametros Import
						function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosClienteSapDto.getId_ut_sup());
						function.getImportParameterList().setValue("I_USUARIO", criteriosClienteSapDto.getUsuario());
						JCoTable itHeader = function.getTableParameterList().getTable("IT_CLIENTE");
						llenaItHeaderCriteriosSap(llenaSap("",criteriosClienteSapDto.getXmlCliSap(),"","","","","",null), itHeader);
						connect.execute(function);
						// Recupera el estado de determinación de la funcion RFC
						subrc = (String) function.getExportParameterList().getString("E_SUBRC");
						bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
						if (subrc.equals("00")) 
						{	
							tableClientesSap = function.getTableParameterList().getTable("IT_CLIENTE");
			
							respGetInfClienteSap.setClienteInfo(getInfoNamesClientes((tableClientesSap)));
							respGetInfClienteSap.setObjClienteInfo(respGetInfClienteSap.getClienteInfo());
							respGetInfClienteSap.setMensaje("SUCCESS");	
							respGetInfClienteSap.setDescripcion("");
						}
						else
						{
							respGetInfClienteSap.setMensaje("FAULT");	
							respGetInfClienteSap.setDescripcion(bapierror);
						}
					} 
					catch (Exception excp) 
					{
						log.error("ERROR: ",excp);
						respGetInfClienteSap.setMensaje("FAULT");
						respGetInfClienteSap.setDescripcion(excp.getMessage());
						throw new ViviendaException(excp.getMessage());
					}
				}		
				return respGetInfClienteSap;	
			}
			
			/**
			 * Método que regresa una lista de vendedores permitidos de acuerdo al perfil 
			 * 
			 * @param criteriosClienteDto 
			 * 			criterios de busqueda de clientes  
			 * @return ResponseGetInfCarCliente 
			 * 			objeto de respuesta para informacion de clientes 
			 * @throws ViviendaException 
			 * @exception  Exception  
			 */		
			public ResponseGetInfCarCliente getClientePorId(CriteriosGetInfCarCliente criteriosClienteDto) throws ViviendaException{
				respGetInfCarCliente=new ResponseGetInfCarCliente();
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
						JCoFunction function = connect.getFunction("ZCSMF_0010_GET_INF_CAR_CLIENTE");
						// Establece Parametros Import
						function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosClienteDto.getId_ut_sup());
						function.getImportParameterList().setValue("I_USUARIO", criteriosClienteDto.getUsuario());
						JCoTable itHeader = function.getTableParameterList().getTable("IT_CAR_CLIENTE");
						llenaItHeaderCriterios(llena(criteriosClienteDto.getXmlNom(),criteriosClienteDto.getXmlNom2(),criteriosClienteDto.getXmlApPat(),criteriosClienteDto.getXmlApMat(),criteriosClienteDto.getXmlIds()), itHeader);
						connect.execute(function);
						// Recupera el estado de determinación de la funcion RFC
						subrc = (String) function.getExportParameterList().getString("E_SUBRC");
						bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
						if (subrc.equals("00")) 
						{	
							tableClientes = function.getTableParameterList().getTable("IT_CAR_CLIENTE");
							tableVisits = function.getTableParameterList().getTable("IT_VISITS_CAR_CLI");
							respGetInfCarCliente.setMensaje("SUCCESS");	
							respGetInfCarCliente.setDescripcion("");
							
					        respGetInfCarCliente.setClientesList(getInfoClientes(tableClientes,tableVisits));
					        respGetInfCarCliente.setObjClientesList(respGetInfCarCliente.getClientesList());
//					        respGetInfCarCliente.setVisitsList(getInfoVisits(tableVisits));
//					        respGetInfCarCliente.setObjVisitsList(respGetInfCarCliente.getVisitsList());
//					        respGetInfCarCliente.setVisitasClienteList(getInfoVisitasCliente(tableVisits));
					        respGetInfCarCliente.setObjVisitasClienteList(respGetInfCarCliente.getVisitasClienteList());					

						}
						else
						{
							respGetInfCarCliente.setMensaje("FAULT");	
							respGetInfCarCliente.setDescripcion(bapierror);
						}
					} 
					catch (Exception excp) 
					{
						log.error("ERROR: ",excp);
						respGetInfCarCliente.setMensaje("FAULT");
						respGetInfCarCliente.setDescripcion(excp.getMessage());
						throw new ViviendaException(excp.getMessage());
					}
				}		
				return respGetInfCarCliente;	
			}
			
			
			/**
			 * Método que regresa una lista de vendedores permitidos de acuerdo al perfil 
			 * 
			 * @param criteriosClienteDto 
			 * 			criterios de busqueda de clientes  
			 * @return ResponseGetInfCarCliente 
			 * 			objeto de respuesta para informacion de clientes 
			 * @throws ViviendaException 
			 * @exception  Exception  
			 */		
			public ResponseGetInfCarCliente getClientesReporte(CriteriosGetInfCarCliente criteriosClienteDto) throws ViviendaException {
				respGetInfCarCliente=new ResponseGetInfCarCliente();
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
						JCoFunction function = connect.getFunction("ZCSMF_0032_GET_CLIENT");
						// Establece Parametros Import
						function.getImportParameterList().setValue("I_ID_UT_SUP", criteriosClienteDto.getId_ut_sup());
						function.getImportParameterList().setValue("I_USUARIO", criteriosClienteDto.getUsuario());
						JCoTable itHeader = function.getTableParameterList().getTable("IT_CLIENTE");
						llenaItHeaderCriterios(llena(criteriosClienteDto.getXmlNom(),criteriosClienteDto.getXmlNom2(),criteriosClienteDto.getXmlApPat(),criteriosClienteDto.getXmlApMat(),criteriosClienteDto.getXmlIds()), itHeader);
						connect.execute(function);
						// Recupera el estado de determinación de la funcion RFC
						subrc = (String) function.getExportParameterList().getString("E_SUBRC");
						bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
						if (subrc.equals("00")) 
						{	
							tableClientes = function.getTableParameterList().getTable("IT_CLIENTE");
							respGetInfCarCliente.setMensaje("SUCCESS");	
							respGetInfCarCliente.setDescripcion("");
					        respGetInfCarCliente.setClientesList(getInfoClientesReporte(tableClientes));
						}
						else
						{
							respGetInfCarCliente.setMensaje("FAULT");	
							respGetInfCarCliente.setDescripcion(bapierror);
						}
					} 
					catch (Exception excp) 
					{
						log.error("ERROR: ",excp);
						respGetInfCarCliente.setMensaje("FAULT");
						respGetInfCarCliente.setDescripcion(excp.getMessage());
						throw new ViviendaException(excp.getMessage());
					}
				}		
				return respGetInfCarCliente;	
			}

			/**
			 * Método que settea los campos de JcoTable al objeto de Clientes   
			 * 
			 * @param JCoTable 
			 * 			tabla de busqueda de clientes  
			 * @return ResponseGetInfCarCliente 
			 * 			objeto de respuesta para obtener a los clientes  
			 * @exception  Exception  
		*/	 
			private ArrayList<ClientesDto> getInfoClientesReporte (JCoTable t) 
			{
				if (t != null){
					if (t.getNumRows() > 0){
						ArrayList<ClientesDto> a = new ArrayList<ClientesDto>();
						t.firstRow();
						for (int i = 0; i < t.getNumRows(); i++){
							a.add(new ClientesDto());
							System.out.print(t.getString("ID_CLIENTE_SAP") + "\t");
							System.out.print(t.getString("NOMBRE1") + "\t");
							System.out.print(t.getString("NOMBRE2") + "\t");
							System.out.print(t.getString("APP_PAT") + "\t");
							System.out.print(t.getString("APP_MAT") + "\t");
							System.out.println(utils.convierteFechaVisual(t.getString("FCH_NCM")));
							a.get(i).setId_car_cli(t.getString("ID_CLIENTE_SAP"));
							a.get(i).setNombre1(t.getString("NOMBRE1"));
							a.get(i).setNombre2(t.getString("NOMBRE2"));
							a.get(i).setApp_pat(t.getString("APP_PAT"));
							a.get(i).setApp_mat(t.getString("APP_MAT"));
							a.get(i).setFch_ncm(utils.convierteFechaVisual(t.getString("FCH_NCM")));
							t.nextRow();
						}
						return a;
					}
				}
				return null;
			}
			
			
}
