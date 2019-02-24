package mx.com.grupogigante.gestionvivienda.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.PermisoUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.PermisosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseAddUsuarioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseDelUsuarioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUpdUsuariDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseValidaLoginDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.UbicacionTecnicaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.CheckFileConnection;
import mx.com.grupogigante.gestionvivienda.utils.Connection;

import org.springframework.stereotype.Repository;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

@Repository
public class UsuariosDaoImpl implements UsuariosDao {
	private Logger log = Logger.getLogger(UsuariosDaoImpl.class);
	
	private CheckFileConnection traerArchivo;
	private JCoTable tablaUsuarios;
	private JCoTable tablaUsuarioPermisos;
	private JCoTable tablaCatPermisos;
	
	private ResponseValidaLoginDto respVL;
	private ResponseGetUsuariosDto respGU;
	private ResponseDelUsuarioDto respDE; 
	
	private ArrayList<UsuarioDto> getDatosUsuarios(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<UsuarioDto> a = new ArrayList<UsuarioDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new UsuarioDto());
					List<PermisoUsuariosDto> p = new ArrayList<PermisoUsuariosDto>();
					List<PermisoUsuariosDto> permisosUsuario = new ArrayList<PermisoUsuariosDto>();
					PermisoUsuariosDto permisosUsuarioDto;
					p=respGU.getPermisosUserList();
					for(int j=0; j<p.size(); j++)
					{
						if(p.get(j).getUsuario().equals(t.getString("USUARIO")))
						{
							permisosUsuarioDto = new PermisoUsuariosDto();
							permisosUsuarioDto.setId_permiso(p.get(j).getId_permiso());
							permisosUsuarioDto.setUsuario(p.get(j).getUsuario());
							permisosUsuarioDto.setId_permisox(p.get(j).getId_permisox());
							permisosUsuario.add(permisosUsuarioDto);
						}
					}	
					a.get(i).setObjPermisosUserList(permisosUsuario);
					a.get(i).setPermisosUserList(permisosUsuario);
					a.get(i).setId_ut_sup(t.getString("ID_UT_SUP"));
					a.get(i).setNombre1(t.getString("NOMBRE1"));
					a.get(i).setNombre2(t.getString("NOMBRE2"));
					a.get(i).setApp_pat(t.getString("APP_PAT"));
					a.get(i).setApp_mat(t.getString("APP_MAT"));
					a.get(i).setUsuario(t.getString("USUARIO"));
					a.get(i).setPass(t.getString("PASS"));
					a.get(i).setTelefono(t.getString("TELFN"));
					a.get(i).setExtension(t.getString("EXTNC"));
					a.get(i).setCorreo(t.getString("MAIL1"));					
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}
	
	
	
	public ResponseValidaLoginDto validaLogin(UsuarioDto usuario) throws ViviendaException
	{
		try
		{
			respVL = new ResponseValidaLoginDto();
			traerArchivo = new CheckFileConnection();
			
			String sStatus;
			Connection connect = null;
			sStatus = ArchLogg.leeLogg();
			String subrc = "";
			String bapierror = "";
			String flagContrato="";
			
				if (sStatus.equals("OK")) 
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
					JCoFunction function = connect.getFunction("ZCSMF_0004_CHCK_USR_LOGIN");
					// Establece Parametros Import
					function.getImportParameterList().setValue("I_USUARIO", usuario.getUsuario());
					function.getImportParameterList().setValue("I_PASS", usuario.getPass());
					function.getImportParameterList().setValue("I_ID_UT_SUP", usuario.getId_ut_sup());
					connect.execute(function);
					// Recupera el estado de determinación de la funcion RFC
					subrc = (String) function.getExportParameterList().getString("E_SUBRC");
					bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
					flagContrato = (String) function.getExportParameterList().getString("E_CONT");
					if (subrc.equals("00")) 
					{					
						respVL.setMensaje("SUCCESS");	
						respVL.setDescripcion("");
						respVL.setFlagContrato(flagContrato);
					}
					else
					{
						respVL.setMensaje("FAULT");	
						respVL.setDescripcion(bapierror);
					}		
				}
		    }
			catch(Exception re)
			{
				log.error("ERROR: ",re);
				respVL.setMensaje("FAULT");	
				respVL.setDescripcion(re.getMessage());
				throw new ViviendaException(re.getMessage());				
			}			
		return respVL;
	}
	
	public ResponseGetUsuariosDto getUsuarios(CriteriosUsuariosDto usuario) throws ViviendaException
	{
		respGU = new ResponseGetUsuariosDto();
		traerArchivo = new CheckFileConnection();
		JCoTable itGetUsers;
		
		String sStatus;
		Connection connect = null;
		sStatus = ArchLogg.leeLogg();
		String subrc = "";
		String bapierror = "";
		
		try
		{
		
			if (sStatus.equals("OK")) 
			{
				
			    if (Connection.getConnect() == null) 
			    {
			    	connect = new Connection(ArchLogg.getSapSystem());
				}
			    else
			    {
					connect = Connection.getConnect();
				}
				// Establece RFC a ejecutar en SAP
				JCoFunction function = connect.getFunction("ZCSMF_0001_GET_INFO_USER");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP_OBT", usuario.getId_ut_sup());
				function.getImportParameterList().setValue("I_ID_UT_SUP", usuario.getId_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", usuario.getUsuario());
				
				if(usuario.getAccion()!=null && usuario.getAccion().equals("permisos"))
				{					
					itGetUsers = function.getTableParameterList().getTable("IT_USERS");
					itGetUsers.appendRow();
					itGetUsers.setValue("USUARIO", usuario.getUsuario());
				}
				
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				
				if (subrc.equals("00")) 
				{	
					tablaUsuarios = function.getTableParameterList().getTable("IT_USERS");
					tablaUsuarioPermisos = function.getTableParameterList().getTable("IT_PERMISOS_USER");
					tablaCatPermisos = function.getTableParameterList().getTable("IT_CAT_PERMISOS");
					
					respGU.setMensaje("SUCCESS");	
					respGU.setDescripcion("");
					
					respGU.setPermisosList(getDatosPermisos(tablaCatPermisos,""));
					respGU.setObjPermisosList(respGU.getPermisosList());
					respGU.setPermisosUserList(getDatosUsuarioPermisos(tablaUsuarioPermisos));
					respGU.setObjPermisosUserList(respGU.getPermisosUserList());				
					respGU.setUsuariosList(getDatosUsuarios(tablaUsuarios));
					respGU.setObjUsuariosList(respGU.getUsuariosList());		
					
				}
				else
				{
					respGU.setMensaje("FAULT");	
					respGU.setDescripcion(bapierror);
				}
		
			}	
		}
		catch(ViviendaException e)
		{
			log.error("ERROR: ",e);
			respGU.setMensaje("FAULT");	
			respGU.setDescripcion(e.getMessage());
			throw new ViviendaException(e.getMessage());
		}
		return respGU;
	}
	
	
	
	public ArrayList<PermisosDto> getCatalogoPermisos(String usuario, String unidad) throws ViviendaException
	{		
		String sStatus;
		Connection connect = null;
		sStatus = ArchLogg.leeLogg();
		String subrc = "";
		String bapierror = "";
		String userasinit = "";
		ArrayList<PermisosDto> returnPermisos = new ArrayList<PermisosDto>();
		
		try
		{
			if (sStatus.equals("OK")) 
			{
				
			    if (Connection.getConnect() == null) 
			    {
			    	connect = new Connection(ArchLogg.getSapSystem());
				}
			    else
			    {
					connect = Connection.getConnect();
				}
				// Establece RFC a ejecutar en SAP
				JCoFunction function = connect.getFunction("ZCSMF_0005_GET_USR_PERM");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", unidad);
				function.getImportParameterList().setValue("I_USUARIO", usuario);			
				
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				userasinit = (String) function.getExportParameterList().getString("E_USR_INIT");
				
				if (subrc.equals("00")) 
				{	
					tablaCatPermisos = function.getTableParameterList().getTable("IT_PERMISOS");				
					returnPermisos=getDatosPermisos(tablaCatPermisos, userasinit);					
				}
			}	
		}
		catch(ViviendaException e)
		{
			log.error("ERROR: ",e);
			throw new ViviendaException(e.getMessage());
		}
		return returnPermisos;
	}
	
	private ArrayList<PermisoUsuariosDto> getDatosUsuarioPermisos(JCoTable t) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<PermisoUsuariosDto> a = new ArrayList<PermisoUsuariosDto>();
				t.firstRow();
				List<PermisosDto> p = new ArrayList<PermisosDto>();
				p=respGU.getPermisosList();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new PermisoUsuariosDto());
					a.get(i).setId_permiso(t.getString("ID_PERMISO"));
					a.get(i).setUsuario(t.getString("USUARIO"));
					for(int j=0; j<p.size(); j++)
					{
						if(p.get(j).getId_permiso().equals(t.getString("ID_PERMISO")))
						{
							a.get(i).setId_permisox(p.get(j).getId_permisox());
							break;
						}
					}	
					
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}
	
	private ArrayList<PermisosDto> getDatosPermisos(JCoTable t, String usrinit) 
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<PermisosDto> a = new ArrayList<PermisosDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new PermisosDto());
					a.get(i).setModule(t.getString("MODULE"));
					a.get(i).setAuthr(t.getString("AUTHR"));
					a.get(i).setId_permiso(t.getString("ID_PERMISO"));
					a.get(i).setId_permisox(t.getString("ID_PERMISOX"));
					a.get(i).setSpecial(t.getString("SPECIAL")+"");
					a.get(i).setE_usr_init(usrinit);
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}
	
	public ResponseAddUsuarioDto addUsuario(CriteriosUsuariosDto usuario) throws ViviendaException {
		ResponseAddUsuarioDto resp = new ResponseAddUsuarioDto();
		
		List<PermisoUsuariosDto> permisosUserList = new ArrayList<PermisoUsuariosDto>();
		
		String strPermisosUser=usuario.getPermisosStr();
		strPermisosUser = strPermisosUser.substring(0, strPermisosUser.length()-1);
		String[] temp;		
		
		try
		{
			temp = strPermisosUser.split("\\|");
	
			for(int i =0; i < temp.length ; i++)
			{
				PermisoUsuariosDto permiso = new PermisoUsuariosDto();
				permiso.setId_permiso(temp[i]);
				permisosUserList.add(permiso);
			}
			
			String sStatus;
			String bapierror;
			String subrc = "";
			JCoTable itPermisosUser;
			Connection connect = null;
			sStatus = ArchLogg.leeLogg();
			if (sStatus.equals("OK")) {
				
				if (Connection.getConnect() == null) 
			    {
			    	connect = new Connection(ArchLogg.getSapSystem());
				}
			    else
			    {
					connect = Connection.getConnect();
				}
				// Establece RFC a ejecutar en SAP
				JCoFunction function = connect.getFunction("ZCSMF_0002_SAVE_USER");
				// Establece Parametros Table a enviar
				itPermisosUser = function.getTableParameterList().getTable("IT_PERMISOS_USER");
				llenaItPermisosUsuario(permisosUserList, itPermisosUser);
				
				if(!itPermisosUser.isEmpty()){
					// Manda ejecutar el RFC en el sistema SAP
					
					function.getImportParameterList().setValue("I_ID_UT_SUP", usuario.getId_ut_sup());
					function.getImportParameterList().setValue("I_USUARIO", usuario.getUsuario());
					
					function.getImportParameterList().setValue("I_PASS", usuario.getPass());
					function.getImportParameterList().setValue("I_NOMBRE1", usuario.getNombre1());
					function.getImportParameterList().setValue("I_NOMBRE2", usuario.getNombre2());
					function.getImportParameterList().setValue("I_APP_PAT", usuario.getApp_pat());
					function.getImportParameterList().setValue("I_APP_MAT", usuario.getApp_mat());
					function.getImportParameterList().setValue("I_USUARIO_CM", usuario.getUsuario_cm());
					function.getImportParameterList().setValue("I_ID_UT_SUP_CM", usuario.getId_ut_sup_cm());
					function.getImportParameterList().setValue("I_ACT_USR", usuario.getAct_usr());
					function.getImportParameterList().setValue("I_TELFN", usuario.getTelefono());
					function.getImportParameterList().setValue("I_EXTNC", usuario.getExtension());
					function.getImportParameterList().setValue("I_MAIL1", usuario.getCorreo());
					
					connect.execute(function);
					// Recupera parametros Export
					subrc = (String) function.getExportParameterList().getString("E_SUBRC");
					bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
					if(subrc.equals("00")){
						resp.setMensaje("SUCCESS");
					}
					else{
						resp.setMensaje("FAULT");
						resp.setDescripcion(bapierror);
					}
					
				}			
			}
		}
		catch(ViviendaException e)
		{
			log.error("ERROR: ",e);
			resp.setMensaje("FAULT");
			resp.setDescripcion(e.getMessage());
			throw new ViviendaException(e.getMessage());
		}
		return resp;
	}
	
	
	private void llenaItPermisosUsuario(List<PermisoUsuariosDto> listaPermisosUsuarios, JCoTable itPermisosUser) {
        if(!listaPermisosUsuarios.isEmpty()){
		    for(int i = 0; i < listaPermisosUsuarios.size(); i++){
		    	itPermisosUser.appendRow();
		    	itPermisosUser.setValue("USUARIO", listaPermisosUsuarios.get(i).getUsuario());
		    	itPermisosUser.setValue("ID_PERMISO", listaPermisosUsuarios.get(i).getId_permiso());
			}
	    }
	}
	
	
	public ResponseUpdUsuariDto updUsuario(CriteriosUsuariosDto usuario) throws ViviendaException {
		ResponseUpdUsuariDto resp = new ResponseUpdUsuariDto();
		return resp;
	}
	
	public ResponseDelUsuarioDto delUsuario(CriteriosUsuariosDto usuario) throws ViviendaException {
		respDE = new ResponseDelUsuarioDto();
		traerArchivo = new CheckFileConnection();
		
		String sStatus;
		Connection connect = null;
		sStatus = ArchLogg.leeLogg();
		String subrc = "";
		String bapierror = "";
		try
		{
			if (sStatus.equals("OK")) 
			{
				
			    if (Connection.getConnect() == null) 
			    {
			    	connect = new Connection(ArchLogg.getSapSystem());
				}
			    else
			    {
					connect = Connection.getConnect();
				}
				// Establece RFC a ejecutar en SAP
				JCoFunction function = connect.getFunction("ZCSMF_0003_DELE_USER");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_USUARIO_B", usuario.getUsuario_cm());
				function.getImportParameterList().setValue("I_ID_UT_SUP_B", usuario.getId_ut_sup_cm());
				function.getImportParameterList().setValue("I_USUARIO", usuario.getUsuario());
				function.getImportParameterList().setValue("I_ID_UT_SUP", usuario.getId_ut_sup());
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				
				if (subrc.equals("00")) 
				{	
					respDE.setMensaje("SUCCESS");	
					respDE.setDescripcion("");				
				}
				else
				{
					respDE.setMensaje("FAULT");	
					respDE.setDescripcion(bapierror);
				}
			}	
		}
		catch(ViviendaException e)
		{
			log.error("ERROR: ",e);
			respDE.setMensaje("FAULT");
			respDE.setDescripcion(e.getMessage());
			throw new ViviendaException(e.getMessage());
		}	
		return respDE;
	}
}

