/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 19/07/2012             
 */
package mx.com.grupogigante.gestionvivienda.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.CatalogosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.CriteriosGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.EdoCivilDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.PaisesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.RegionesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.ResponseGetCatalogosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.SexoDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.catalogos.TratamientoDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.CheckFileConnection;
import mx.com.grupogigante.gestionvivienda.utils.Connection;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

/**
 * Clase en donde se realiza la implementacion de todas las operaciones referentes a Catalogos
 * Fecha de creación: 19/07/2012
 * Fecha de Modificación : 11/02/2014
 * @author Echavez
 * @RFC Versión dos RFC Dinámico              
 */

@Repository
public class CatalogosDaoImp implements CatalogosDao {
	private Logger log = Logger.getLogger(CatalogosDaoImp.class);
	private CheckFileConnection traerArchivo;
	private ResponseGetCatalogosDto respGetCatalogos;
	
	private JCoTable tablePaises;
	private JCoTable tableEdoCivil;
	private JCoTable tableSexo;
	private JCoTable tableTratamiento;
	private JCoTable tableRegiones;
	private JCoTable tableCatalogo;
	
	
	 /**
	 * Método que regresa la consulta de Vias de contacto para el combo  
	 * 
	 * @param criteriosClienteDto 
	 * 			criterios de busqueda de clientes  
	 * @return ResponseGetInfCarCliente 
	 * 			objeto de respuesta para obtener todas las vias de contacto 
	 * @exception  Exception  
	 */	 
public ResponseGetCatalogosDto getCatalogos(CriteriosGetCatalogosDto criterios) throws ViviendaException {
	
	respGetCatalogos=new ResponseGetCatalogosDto();
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
			JCoFunction function = connect.getFunction("ZCSMF_0030_GET_PARMS_CLIENTES");
			// Establece Parametros Import
			function.getImportParameterList().setValue("I_ID_UT_SUP", criterios.getI_id_ut_sup());
			function.getImportParameterList().setValue("I_USUARIO", criterios.getI_usuario());
			function.getImportParameterList().setValue("I_PAISES", criterios.getI_paises());
			function.getImportParameterList().setValue("I_EDO_CIVIL", criterios.getI_edo_civil());
			function.getImportParameterList().setValue("I_SEXO", criterios.getI_sexo());
			function.getImportParameterList().setValue("I_TRATAMIENTOS", criterios.getI_tratamientos());

			
			//function.getImportParameterList().setValue("PAIS", criterios.getId_pais());
			function.getImportParameterList().setValue("I_REGIONES", criterios.getI_regiones());
			JCoTable itHeader = function.getTableParameterList().getTable("IT_PAISES");
			itHeader.appendRow();
			itHeader.setValue("PAIS",criterios.getId_pais());
			
			connect.execute(function);
			// Recupera el estado de determinación de la funcion RFC
			subrc = (String) function.getExportParameterList().getString("E_SUBRC");
			bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
			if (subrc.equals("00"))
			{	
				tablePaises = function.getTableParameterList().getTable("IT_PAISES");
				tableEdoCivil = function.getTableParameterList().getTable("IT_EDO_CIVIL");
				tableRegiones = function.getTableParameterList().getTable("IT_REGIONES");
				tableSexo = function.getTableParameterList().getTable("IT_SEXO");
				tableTratamiento = function.getTableParameterList().getTable("IT_TRATAMIENTOS");
				
				respGetCatalogos.setMensaje("SUCCESS");
				respGetCatalogos.setDescripcion("");
				
				respGetCatalogos.setPaisesList(getPaises(tablePaises));
				respGetCatalogos.setEdoCivilList(getEdoCivil(tableEdoCivil));
				respGetCatalogos.setRegionesList(getRegiones(tableRegiones));
				respGetCatalogos.setSexoList(getSexo(tableSexo));
				respGetCatalogos.setTratamientoList(getTratamiento(tableTratamiento));
				
				respGetCatalogos.setObjPaisesList(respGetCatalogos.getPaisesList());
				respGetCatalogos.setObjEdoCivilList(respGetCatalogos.getEdoCivilList());
				respGetCatalogos.setObjRegionesList(respGetCatalogos.getRegionesList());
				respGetCatalogos.setObjSexoList(respGetCatalogos.getSexoList());
				respGetCatalogos.setObjTratamientoList(respGetCatalogos.getTratamientoList());
				
			}
			else
			{
				respGetCatalogos.setMensaje("FAULT");	
				respGetCatalogos.setDescripcion(bapierror);
			}
		} 
		catch (Exception excp) 
		{
			log.error("ERROR: ",excp);
			respGetCatalogos.setMensaje("FAULT");
			respGetCatalogos.setDescripcion(excp.getMessage());
			throw new ViviendaException(excp.getMessage());			
		}
	}		
	return respGetCatalogos;
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
public ResponseGetCatalogosDto getCatalogos2(CriteriosGetCatalogosDto criterios) throws ViviendaException {
	
	respGetCatalogos=new ResponseGetCatalogosDto();
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
			JCoFunction function = connect.getFunction("ZCSMF_0030_GET_PARMS_CLIENTES2");
			// Establece Parametros Import
			function.getImportParameterList().setValue("I_ID_UT_SUP", criterios.getI_id_ut_sup());
			function.getImportParameterList().setValue("I_USUARIO", criterios.getI_usuario());
			
			JCoTable itHeader = function.getTableParameterList().getTable("IT_CATALOGOS");
			itHeader.appendRow();
			itHeader.setValue("ID_CAT", criterios.getI_catalogo());
			
			connect.execute(function);
			// Recupera el estado de determinación de la funcion RFC
			subrc = (String) function.getExportParameterList().getString("E_SUBRC");
			bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
			if (subrc.equals("00"))
			{	
	
				tableCatalogo = function.getTableParameterList().getTable("IT_INFO_CATS");
				
				respGetCatalogos.setMensaje("SUCCESS");
				respGetCatalogos.setDescripcion("");
				
				respGetCatalogos.setCatalogosList(getCatalogos(tableCatalogo));
				respGetCatalogos.setObjCatalogosList(respGetCatalogos.getCatalogosList());
				
			}
			else
			{
				respGetCatalogos.setMensaje("FAULT");	
				respGetCatalogos.setDescripcion(bapierror);
			}
		} 
		catch (Exception excp) 
		{
			log.error("ERROR: ",excp);
			respGetCatalogos.setMensaje("FAULT");
			respGetCatalogos.setDescripcion(excp.getMessage());
			throw new ViviendaException(excp.getMessage());			
		}
	}		
	return respGetCatalogos;
}





/**
 * Método que settea los campos de JcoTable al objeto de Paises    
 * 
 * @param JCoTable 
 * 			tabla de busqueda de paises  
 * @return ArrayList<PaisesDto> 
 * 			Lista con los paises obtenidos de la busqueda    
 * @exception  ViviendaException  
 */	
private ArrayList<PaisesDto> getPaises(JCoTable t) throws ViviendaException
{
	if (t != null){
		if (t.getNumRows() > 0){
			ArrayList<PaisesDto> a = new ArrayList<PaisesDto>();
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				a.add(new PaisesDto());
				a.get(i).setPais(t.getString("PAIS"));
				a.get(i).setPaisx(t.getString("PAISX"));
				t.nextRow();
			}
			return a;
		}
	}
	return null;
}

/**
 * Método que settea los campos de JcoTable al objeto de Estados Civil    
 * 
 * @param JCoTable 
 * 			tabla de busqueda de Estado Civil  
 * @return ArrayList<EdoCivilDto> 
 * 			Lista con los Edos Civil obtenidos de la busqueda    
 * @exception  ViviendaException  
 */	
private ArrayList<EdoCivilDto> getEdoCivil(JCoTable t) throws ViviendaException
{
	if (t != null){
		if (t.getNumRows() > 0){
			ArrayList<EdoCivilDto> a = new ArrayList<EdoCivilDto>();
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				a.add(new EdoCivilDto());
					a.get(i).setEdocvl(t.getString("EDOCVL"));
					a.get(i).setEdocvlx(t.getString("EDOCVLX"));	
				
				t.nextRow();
			}
			return a;
		}
	}
	return null;
}	

/**
 * Método que settea los campos de JcoTable al objeto de Sexo   
 * 
 * @param JCoTable 
 * 			tabla de busqueda de Sexo
 * @return ArrayList<SexoDto> 
 * 			Lista con los sexos obtenidos de la busqueda    
 * @exception  ViviendaException  
 */	
private ArrayList<SexoDto> getSexo(JCoTable t) throws ViviendaException
{
	if (t != null){
		if (t.getNumRows() > 0){
			ArrayList<SexoDto> a = new ArrayList<SexoDto>();
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				a.add(new SexoDto());
				
					a.get(i).setSexo(t.getString("SEXO"));
					a.get(i).setSexox(t.getString("SEXOX"));
				
				t.nextRow();
			}
			return a;
		}
	}
	return null;
}	

/**
 * Método que settea los campos de JcoTable al objeto de Tratamiento   
 * 
 * @param JCoTable 
 * 			tabla de busqueda de Tratamientos
 * @return ArrayList<TratamientoDto> 
 * 			Lista con los tratamientos obtenidos de la busqueda    
 * @exception  ViviendaException  
 */	
private ArrayList<TratamientoDto> getTratamiento(JCoTable t) throws ViviendaException
{
	if (t != null){
		if (t.getNumRows() > 0){
			ArrayList<TratamientoDto> a = new ArrayList<TratamientoDto>();
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				a.add(new TratamientoDto());
				
					a.get(i).setTratamiento(t.getString("TRATAMIENTO"));
					a.get(i).setTratamientox(t.getString("TRATAMIENTOX"));
				
				t.nextRow();
			}
			return a;
		}
	}
	return null;
}



/**
 * Método que settea los campos de JcoTable al objeto de Regiones   
 * 
 * @param JCoTable 
 * 			tabla de busqueda de Regiones
 * @return ArrayList<RegionesDto> 
 * 			Lista con los Regiones obtenidos de la busqueda    
 * @exception  ViviendaException  
 */	
private ArrayList<RegionesDto> getRegiones(JCoTable t) throws ViviendaException
{
	if (t != null){
		if (t.getNumRows() > 0){
			ArrayList<RegionesDto> a = new ArrayList<RegionesDto>();
			t.firstRow();
			
			for (int i = 0; i < t.getNumRows(); i++){
				if(!t.getString("REGIONX").equals("Distrito Federal")){
					RegionesDto regiones = new RegionesDto();
					regiones.setPais(t.getString("PAIS"));
					regiones.setRegion(t.getString("REGION"));
					regiones.setRegionx(t.getString("REGIONX"));
					a.add(regiones);
				}
				t.nextRow();
			}
			return a;
		}
	}
	return null;
}

/**
 * Método que regresa la descripcion del estadoCivil    
 * 
 * @param List<EdoCivilDto>,edoCivil  
 * 			lista de estados civil y edoCivil
 * @return String  
 * 			Descripcion del estado civil    
 * @exception  ViviendaException  
 */	
public String getEdoCivilDesc(List<EdoCivilDto> edoCivilList,String edoCvil) throws ViviendaException
{
	String edoCivilDesc="";
	for(int j=0;j<edoCivilList.size();j++)
	{
		if(edoCvil.equals(edoCivilList.get(j).getEdocvl()))
		{
			edoCivilDesc=edoCivilList.get(j).getEdocvlx();
			break;
		}	
	
	}
	return edoCivilDesc;
}	

/**
 * Método que regresa la descripcion del sexo    
 * 
 * @param List 
 * 			lista de List<SexoDto> , param de sexo 
 * @return String  
 * 			Descripcion del sexo    
 * @exception  ViviendaException  
 */	
public String getSexoDesc(List<SexoDto> sexoList,String sexo) throws ViviendaException
{
	String sexoDesc="";
	for(int j=0;j<sexoList.size();j++)
	{
		if(sexo.equals(sexoList.get(j).getSexo()))
		{
			sexoDesc=sexoList.get(j).getSexox();
			break;
		}	
	
	}
	return sexoDesc;
}	
/**
 * Método que regresa la descripcion del sexo    
 * 
 * @param List 
 * 			lista de List<TratamientoDto> , param de tratamiento 
 * @return String  
 * 			Descripcion del tratamiento    
 * @exception  ViviendaException  
 */	
public String getTratamientoDesc(List<TratamientoDto> tratamientoList,String tratam) throws ViviendaException
{
	String tratamientoDesc="";
	for(int j=0;j<tratamientoList.size();j++)
	{
		if(tratam.equals(tratamientoList.get(j).getTratamiento()))	
		{
			tratamientoDesc=tratamientoList.get(j).getTratamientox();
			break;
		}	
	
	}
	return tratamientoDesc;
}	
/**
 * Método que regresa la descripcion del Pais   
 * 
 * @param List 
 * 			lista de List<PaisDto> , param de pais 
 * @return String  
 * 			Descripcion del pais    
 * @exception  ViviendaException  
 */	
public String getPaisDesc(List<PaisesDto> paisList,String pais) throws ViviendaException
{
	String paisDesc="";
	for(int j=0;j<paisList.size();j++)
	{
		if(pais.equals(paisList.get(j).getPais()))
		{
			paisDesc=paisList.get(j).getPaisx();
			break;
		}	
	
	}
	return paisDesc;
}	
/**
 * Método que regresa la descripcion del Region 
 * 
 * @param List 
 * 			lista de List<RegionesDto> , param de region 
 * @return String  
 * 			Descripcion del region    
 * @exception  ViviendaException  
 */	
public String getRegionDesc(List<RegionesDto> regionesList,String region) throws ViviendaException
	{
		String regionDesc="";
		for(int j=0;j<regionesList.size();j++)
		{
			if(region.equals(regionesList.get(j).getRegion()))
			{
				regionDesc=regionesList.get(j).getRegionx();
				break;
			}	
		
		}
		return regionDesc;
	}


/**
 * Método que settea los campos de JcoTable al objeto de Tratamiento   
 * 
 * @param JCoTable 
 * 			tabla de busqueda de Tratamientos
 * @return ArrayList<TratamientoDto> 
 * 			Lista con los tratamientos obtenidos de la busqueda    
 * @exception  ViviendaException  
 */	
private ArrayList<CatalogosDto> getCatalogos(JCoTable t) throws ViviendaException
{
	if (t != null){
		if (t.getNumRows() > 0){
			ArrayList<CatalogosDto> a = new ArrayList<CatalogosDto>();
			t.firstRow();
			for (int i = 0; i < t.getNumRows(); i++){
				a.add(new CatalogosDto());
				
					a.get(i).setId_cat(t.getString("ID_CAT"));
					a.get(i).setId_val(t.getString("ID_VAL"));
					a.get(i).setValor(t.getString("VALOR"));
					a.get(i).setVal_02(t.getString("VAL_02"));
				
				t.nextRow();
			}
			return a;
		}
	}
	return null;
}


}
