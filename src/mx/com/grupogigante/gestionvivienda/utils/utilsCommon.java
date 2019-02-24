/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.utils;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mx.com.grupogigante.gestionvivienda.domain.dto.CoordenadasDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.EquipoDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.PermisoUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetUtInfSupDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.UbicacionTecnicaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CotizadorBillPlaningDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CotizadorDetalleDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CotizadorSubequiposDto;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
/**
 * Clase con métodos reutilizables en la aplicación 
 *
 */
public class utilsCommon {

	/**
	 * Método que regresa una lista de vendedores permitidos de acuerdo al perfil 
	 * 
	 * @param resUsr 
	 * 			usuario logueado 
	 * @return List<UsuarioDto> 
	 * 			lista de vendedores permitidos  
	 */	
	public  List<UsuarioDto> vendedoresPermitidos(ResponseGetUsuariosDto resUsr){	
		UsuarioDto usr = new UsuarioDto();
		List<UsuarioDto> usuariosPermisoVenta = new ArrayList<UsuarioDto>();
	    	for (int i = 0; i < resUsr.getUsuariosList().size(); i++) {
	    		List<PermisoUsuariosDto> permisoUsuariosDto = resUsr
	    				.getUsuariosList().get(i).getPermisosUserList();
	    		int contador = 0;
	    		for (int z = 0; z < permisoUsuariosDto.size(); z++)
	    			for (int j = 0; j < resUsr.getPermisosList().size(); j++)
	    				if (resUsr.getPermisosList().get(j).getModule().equals("VENTA")	&& (resUsr.getPermisosList().get(j).getAuthr().equals("PERMISO_V")	|| resUsr.getPermisosList().get(j).getAuthr().equals("PERMISO_M") || resUsr.getPermisosList().get(j).getAuthr().equals("PERMISO_B") || resUsr.getPermisosList().get(j).getAuthr().equals("PERMISO_C")))
	    					if (permisoUsuariosDto.get(z).getId_permiso().equals(resUsr.getPermisosList().get(j).getId_permiso()))
	    						contador++;
	    	
	    		if (contador == 4)
	    			usuariosPermisoVenta.add(resUsr.getUsuariosList().get(i));
	    	}// fin for de usr
	    
	    return usuariosPermisoVenta;
	}   

	/**
 * Método que regresa una fecha con el formato q requiere SAP 
 * 
 * @param String fechaIni 
 * 			fecha como la recibe de la vista  
 * @return String fechaFin
 * 			fecha ya con formato YYYYMMDD  
 */	
public String convierteFecha(String fechaIni)
{
	String fechaFin = "",aux="";
	String [] campos = fechaIni.split("/");
	for (int i = campos.length-1; i >=0 ; i--) {
	if(campos[i].length()==1)
		campos[i]="0"+campos[i];

	}
	fechaFin=campos[2]+campos[1]+campos[0];
	return fechaFin;
}

/**
 * Método que regresa una fecha con el formato de SAP al del usuario
 * @param String fechaIni 
 * 			fecha como la recibe de la vista  
 * @return String fechaFin
 * 			fecha ya con formato DDMMYYYY 
 */	
public String convierteFechaVisual(String fechaIni)
{
	String fechaFin = "",aux="";
	String [] campos = fechaIni.split("-");
	for (int i = campos.length-1; i >=0 ; i--) {
	if(campos[i].length()==1)
		campos[i]="0"+campos[i];

	}
	fechaFin=campos[2]+"/"+campos[1]+"/"+campos[0];
	
	return fechaFin;
}

/**
 * Método que regresa una fecha con el formato q requiere SAP 
 * 
 * @param String eqCompleto
 * 			fecha como la recibe de la vista  
 * @return String
 * 			el id_equnr Descripcion 
 */	
public String convierteEqunr(String eqCompleto)
{
	
	String [] campos = eqCompleto.split("-");
	
	return campos[1];
}
	public ArrayList<UbicacionTecnicaDto> asignaDeptoPrecio(ResponseGetUtInfSupDto respGetUtInfSup)
	{
		UbicacionTecnicaDto resp = new UbicacionTecnicaDto();
		List<EquipoDto> equiposList = new ArrayList<EquipoDto>();
		List<CoordenadasDto> coordenadasList = new ArrayList<CoordenadasDto>();
		
		ArrayList<UbicacionTecnicaDto> listaPisos = new ArrayList<UbicacionTecnicaDto>();
		
		try
		{
			for(int i = 0; i < respGetUtInfSup.getUbicacionesList().size(); i++)
			{
				resp = new UbicacionTecnicaDto();
				equiposList = new ArrayList<EquipoDto>();
				for(int j = 0; j < respGetUtInfSup.getEquiposList().size(); j++)
				{
					if(respGetUtInfSup.getUbicacionesList().get(i).getUbicacionClave().equals(respGetUtInfSup.getEquiposList().get(j).getId_ubi_tec()))
					{
						resp = respGetUtInfSup.getUbicacionesList().get(i);
						for(int l = 0; l < respGetUtInfSup.getSubequiposList().size(); l++)
						{
							if(respGetUtInfSup.getEquiposList().get(j).getId_equnr().equals(respGetUtInfSup.getSubequiposList().get(l).getId_subequnr()))
							{
								respGetUtInfSup.getEquiposList().get(j).getSubequiposList().add(respGetUtInfSup.getSubequiposList().get(l));				
							}
						}
						equiposList.add(respGetUtInfSup.getEquiposList().get(j));						
					}
				}	
				
				for(int k=0; k<equiposList.size(); k++)
				{
					for(int h=0; h<respGetUtInfSup.getEqCaracteristicasList().size(); h++)
					{						
						if(equiposList.get(k).getId_equnr().equals(respGetUtInfSup.getEqCaracteristicasList().get(h).getId_equnr()))
						{
							if(respGetUtInfSup.getEqCaracteristicasList().get(h).getCharact().equals("TIPO"))
							{
								equiposList.get(k).setId_tipo(respGetUtInfSup.getEqCaracteristicasList().get(h).getValue());
							}
							
							if(respGetUtInfSup.getEqCaracteristicasList().get(h).getCharact().equals("STUN"))
							{
								equiposList.get(k).setId_estatus(respGetUtInfSup.getEqCaracteristicasList().get(h).getValue());
							}
							
							if(respGetUtInfSup.getEqCaracteristicasList().get(h).getCharact().equals("STUNX"))
							{
								equiposList.get(k).setId_estatus_det(respGetUtInfSup.getEqCaracteristicasList().get(h).getValue());
							}							
									
							if(respGetUtInfSup.getEqCaracteristicasList().get(h).getCharact().equals("Z3TOTAL_AREAS"))
							{
								equiposList.get(k).setId_total_metros(respGetUtInfSup.getEqCaracteristicasList().get(h).getValue());
							}
							
							if(respGetUtInfSup.getEqCaracteristicasList().get(h).getCharact().equals("VISA"))
							{
								equiposList.get(k).setId_vista(respGetUtInfSup.getEqCaracteristicasList().get(h).getValue());
							}
							
							equiposList.get(k).getEqCaracteristicasList().add(respGetUtInfSup.getEqCaracteristicasList().get(h));						
						}
					}
					
				}
					
				try
				{
					for(int k=0; k<respGetUtInfSup.getCoordenadasList().size(); k++)
					{
						for(int l=0; l<equiposList.size(); l++)
						{
							if(respGetUtInfSup.getCoordenadasList().get(k).getUteq().equals(equiposList.get(l).id_equnr))
							{
								respGetUtInfSup.getCoordenadasList().get(k).setEstatus(equiposList.get(l).getId_estatus());
							}
						}
					}
				}
				catch(Exception e){}
				
				/*for(int k=0; k<equiposList.size(); k++)
				{
					for(int h=0; h<respGetUtInfSup.getEqCaracteristicasList().size(); h++)
					{
						if(equiposList.get(k).getId_equnr().equals(respGetUtInfSup.getSubequiposList().get(h).getId_equnr()))
						{
							equiposList.get(k).getSubequiposList().add(respGetUtInfSup.getSubequiposList().get(h));
						}
					}
					
				}*/
				
				resp.setEquiposList(equiposList);
				listaPisos.add(resp);
			}
			
			try
			{
				for(int j = 0; j < respGetUtInfSup.getEquiposList().size(); j++)
				{				
					for(int h=0; h<respGetUtInfSup.getEqCaracteristicasList().size(); h++)
					{
						if(respGetUtInfSup.getEquiposList().get(j).getId_equnr().equals(respGetUtInfSup.getEqCaracteristicasList().get(h).getId_equnr()))
						{							
							if(respGetUtInfSup.getEqCaracteristicasList().get(h).getCharact().equals("STUN"))
							{
								respGetUtInfSup.getEquiposList().get(j).setId_estatus(respGetUtInfSup.getEqCaracteristicasList().get(h).getValue());
								break;
							}
						}
					}
				}
			}
			catch(Exception e){
				
			}
			
			try
			{
				for(int j = 0; j < respGetUtInfSup.getCoordenadasList().size(); j++)
				{				
					for(int h=0; h<respGetUtInfSup.getEqCaracteristicasList().size(); h++)
					{
						if(respGetUtInfSup.getCoordenadasList().get(j).getUteq().equals(respGetUtInfSup.getEqCaracteristicasList().get(h).getId_equnr()))
						{							
							if(respGetUtInfSup.getEqCaracteristicasList().get(h).getCharact().equals("STUN"))
							{
								respGetUtInfSup.getCoordenadasList().get(j).setEstatus(respGetUtInfSup.getEqCaracteristicasList().get(h).getValue());
								break;
							}
						}
					}
				}
			}
			catch(Exception e){
				
			}
			
		}
		catch (Exception ex)
		{
			
		}		
		return listaPisos;
	}
	
	/*ArrayList<CotizadorHeaderDto> cotizacionHeaderList = new ArrayList<CotizadorHeaderDto>();
	ArrayList<CotizadorDetalleDto> cotizacionDetalleList = new ArrayList<CotizadorDetalleDto>();
	ArrayList<CotizadorBillPlaningDto> cotizacionBillPlanList = new ArrayList<CotizadorBillPlaningDto>();
	ArrayList<CotizadorSubequipos> cotizacionSubEquiposList = new ArrayList<CotizadorSubequipos>();*/
	
	public void convierteXMLToCotizacionDetalle(String datos, ArrayList<CotizadorDetalleDto> listaReturn)
	{
		try {
			SAXReader reader = new SAXReader();
			org.dom4j.Document doc = reader.read(new StringReader(datos));		
			org.dom4j.Element root = doc.getRootElement();
		
			for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
				org.dom4j.Element element = (org.dom4j.Element) i.next();
				CotizadorDetalleDto itemCotiza = new CotizadorDetalleDto();				
				itemCotiza.setId_cotiz_z(element.elementText("id_cotiz_z"));
				itemCotiza.setVbeln_cot(element.elementText("vbeln_cot"));
				itemCotiza.setVbeln_ped(element.elementText("vbeln_ped"));
				itemCotiza.setPosnr_cot(element.elementText("posnr_cot"));
				itemCotiza.setPosnr_ped(element.elementText("posnr_ped"));
				itemCotiza.setPosnr_z(element.elementText("posnr_z"));
				itemCotiza.setCve_web(element.elementText("cve_web"));
				itemCotiza.setMatnr(element.elementText("matnr"));
				itemCotiza.setDescr(element.elementText("descr"));
				itemCotiza.setNetwr(element.elementText("netwr"));
				itemCotiza.setNetwrp(element.elementText("netwrp"));
				itemCotiza.setMoneda(element.elementText("moneda"));
				listaReturn.add(itemCotiza);
	        }
		} catch (DocumentException e) {
			listaReturn = new ArrayList<CotizadorDetalleDto>();
		}
	}
	
	public void convierteXMLToCotizacionPlan(String datos, ArrayList<CotizadorBillPlaningDto> listaReturn)
	{
		try {
			SAXReader reader = new SAXReader();
			org.dom4j.Document doc = reader.read(new StringReader(datos));		
			org.dom4j.Element root = doc.getRootElement();
			
			for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
				org.dom4j.Element element = (org.dom4j.Element) i.next();
				CotizadorBillPlaningDto itemCotiza = new CotizadorBillPlaningDto();				
				itemCotiza.setTotal(element.elementText("total"));			
				itemCotiza.setId_cotiz_z(element.elementText("id_cotiz_z"));
				itemCotiza.setFlim(element.elementText("flim"));
				itemCotiza.setConcex(element.elementText("concex"));
				itemCotiza.setVbeln_cot(element.elementText("vbeln_cot"));
				itemCotiza.setVbeln_ped(element.elementText("vbeln_ped"));				
				itemCotiza.setPosnr_z(element.elementText("posnr_z"));
				try
				{
					itemCotiza.setConse(Integer.parseInt(element.elementText("conse")));
				}
				catch(Exception e)
				{
					itemCotiza.setConse(0);
				}
				
				itemCotiza.setConce(element.elementText("conce"));
				try
				{
					itemCotiza.setSim1(Float.parseFloat(element.elementText("sim1")));
				}
				catch(Exception e)
				{
					itemCotiza.setSim1(0);
				}
				try
				{
					itemCotiza.setSim2(Float.parseFloat(element.elementText("sim2")));
				}
				catch(Exception e)
				{
					itemCotiza.setSim2(0);
				}
				listaReturn.add(itemCotiza);
	        }
		} catch (DocumentException e) {
			listaReturn = new ArrayList<CotizadorBillPlaningDto>();
		}
	}
	
	
	public void convierteXMLToCotizacionSubEquipos(String datos, ArrayList<CotizadorSubequiposDto> listaReturn)
	{
		try {
			SAXReader reader = new SAXReader();
			org.dom4j.Document doc = reader.read(new StringReader(datos));		
			org.dom4j.Element root = doc.getRootElement();
			
			for (java.util.Iterator i = root.elementIterator( "criterio" ); i.hasNext(); ) {
				org.dom4j.Element element = (org.dom4j.Element) i.next();
				CotizadorSubequiposDto itemCotiza = new CotizadorSubequiposDto();
				itemCotiza.setId_equnrx(element.elementText("id_equnrx"));
				itemCotiza.setPosnr_z(element.elementText("posnr_z"));
				itemCotiza.setId_equnr(element.elementText("id_equnr"));
				itemCotiza.setTipo(element.elementText("tipo"));
				itemCotiza.setId_cotiz_z(element.elementText("id_cotiz_z"));
				itemCotiza.setNetwr(element.elementText("netwr"));
				itemCotiza.setMoneda(element.elementText("moneda"));
				listaReturn.add(itemCotiza);
	        }
		} catch (DocumentException e) {
			listaReturn = new ArrayList<CotizadorSubequiposDto>();
		}
	}
	
		public static String getLastDayLastMonth(){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DATE, -1);
		System.out.println("lastdei lastmont ... " + dateFormat.format(cal.getTime()));
		return dateFormat.format(cal.getTime());
		}
		
		
	
}
