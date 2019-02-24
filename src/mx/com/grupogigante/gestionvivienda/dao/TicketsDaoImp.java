/**
 * @author JL SACTI CONSULTORES / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.dao;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetTicket;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetTicketConstruccion;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseAddTicketDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseLogConstruccionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseTicketConstruccionDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.TicketDetailDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.TicketDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.TicketHeaderDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.TicketLogDto;
import mx.com.grupogigante.gestionvivienda.domain.vo.ClienteDatosTicketVo;
import mx.com.grupogigante.gestionvivienda.domain.vo.ListaFicheros;
import mx.com.grupogigante.gestionvivienda.domain.vo.UpdateViciosVo;
import mx.com.grupogigante.gestionvivienda.domain.vo.ViciosResponse;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.Connection;
import mx.com.grupogigante.gestionvivienda.utils.ImageResizer;
import mx.com.grupogigante.gestionvivienda.utils.utilsCommon;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

/**
 * Clase en donde se realiza la implementacion de todas las operaciones referentes a Cartera 
 * de clientes
 * Fecha de creación: XX/06/2012               
 */
@Repository
public class TicketsDaoImp implements TicketsDao {
	private Logger log = Logger.getLogger(TicketsDaoImp.class);

	
	private JCoTable tableTicketsOut;


	//variables de respuesta para cartera de clientes 
	public utilsCommon utils=new utilsCommon();
	//TODO moverlo a tickets
	public ResponseAddTicketDto addTicket(CriteriosGetTicket criteriosGetTicket) throws ViviendaException {
		ResponseAddTicketDto responseAddTicketDto = new ResponseAddTicketDto();
		
		String sStatus;
		Connection connect = null;
		sStatus = ArchLogg.leeLogg();
		String subrc = "";
		String bapierror = "";
		String enumTicket = "";
		
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
				JCoFunction function = connect.getFunction("ZCSMF_0075_SAVE_TICKET");
				
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP",   criteriosGetTicket.getId_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO",     criteriosGetTicket.getUsuario());
				function.getImportParameterList().setValue("I_ID_CAR_CLI",  criteriosGetTicket.getId_car_cli());
				function.getImportParameterList().setValue("I_ASUNTO",      criteriosGetTicket.getAsunto());
				function.getImportParameterList().setValue("I_BODY_MAIL",   criteriosGetTicket.getBody_mail());
				function.getImportParameterList().setValue("I_TICKET_AREA", criteriosGetTicket.getTicket_area());
				function.getImportParameterList().setValue("I_SLS_MAN",     criteriosGetTicket.getSls_man());
				function.getImportParameterList().setValue("I_ID_FASE",    criteriosGetTicket.getIdFase());
				function.getImportParameterList().setValue("I_ID_EQUI",     criteriosGetTicket.getIdEquipo());
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				enumTicket = (String) function.getExportParameterList().getString("E_NUM_TICKET");
				if (subrc.equals("00")) 
				{	
					//GENERAR RESPONSE
					responseAddTicketDto.setId_ticket_sap(enumTicket);
					responseAddTicketDto.setMensaje("SUCCESS");

				}
				else
				{
					responseAddTicketDto.setMensaje("FAULT");	
					responseAddTicketDto.setDescripcion(bapierror);
				}
			} 
			catch (Exception excp) 
			{
				log.error("Error:",  excp);
				responseAddTicketDto.setMensaje("FAULT");
				responseAddTicketDto.setDescripcion(excp.getMessage());
				throw new ViviendaException ("ERROR:" + excp.getMessage());
			}
		}		
		return responseAddTicketDto;	
	}
			
	private ArrayList<TicketDto> getTickets(JCoTable t) throws ViviendaException
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<TicketDto> a = new ArrayList<TicketDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new TicketDto());
					a.get(i).setIdTicket(t.getString("NTICK"));
					a.get(i).setIdUtSuperior(t.getString("ID_UT_SUP"));
					a.get(i).setIdFase(t.getString("ID_FASE"));
					a.get(i).setIdEquipo(t.getString("ID_EQUI"));
					a.get(i).setIdCliente(t.getString("ID_CAR_CLI"));
					a.get(i).setNombre1Cliente(t.getString("CAR_CLI_NOMBRE1") == null? "":t.getString("CAR_CLI_NOMBRE1"));
					a.get(i).setIdArea(t.getString("TICKET_AREA"));
					a.get(i).setNombre2Cliente(t.getString("CAR_CLI_NOMBRE2") == null? "":t.getString("CAR_CLI_NOMBRE2"));
					a.get(i).setAppaternoCliente(t.getString("CAR_CLI_APP_PAT") ==  null? "":t.getString("CAR_CLI_APP_PAT"));
					a.get(i).setApmaternoCliente(t.getString("CAR_CLI_APP_MAT") == null? "":t.getString("CAR_CLI_APP_MAT"));
					a.get(i).setAsunto(t.getString("ASUNTO"));
					a.get(i).setIdVendedor(t.getString("SLS_MAN"));
					a.get(i).setFechaTicket(t.getString("AEDAT"));
					a.get(i).setNombre1Vendedor(t.getString("SLS_MAN_NOMBRE1") == null? "":t.getString("SLS_MAN_NOMBRE1"));
					a.get(i).setNombre2Vendedor(t.getString("SLS_MAN_NOMBRE2") == null? "":t.getString("SLS_MAN_NOMBRE2"));
					a.get(i).setAppaternoVendedor(t.getString("SLS_MAN_APP_PAT") == null? "":t.getString("SLS_MAN_APP_PAT"));
					a.get(i).setApmaternoVendedor(t.getString("SLS_MAN_APP_MAT") == null? "":t.getString("SLS_MAN_APP_MAT"));
					a.get(i).setDescripcion(t.getString("BODY_MAIL") == null? "":t.getString("BODY_MAIL"));

					//AEDAT
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}

	public ResponseTicketConstruccionDto findTicketConstruccion(CriteriosGetTicketConstruccion criteriosGetTicketConstruccion) throws ViviendaException {
		ResponseTicketConstruccionDto responseTicketConstruccionDto = new ResponseTicketConstruccionDto();
		
		String sStatus;
		Connection connect = null;
		sStatus = ArchLogg.leeLogg();
		String subrc = "";
		String bapierror = "";
		JCoTable tableTicketsHeadOut;
		JCoTable tableTicketsDetailOut;

		
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
				JCoFunction function = connect.getFunction("ZCSMF_0152_GET_TICKET1");
				
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP",   criteriosGetTicketConstruccion.getId_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO",     criteriosGetTicketConstruccion.getUsuario());
				log.info("I_ID_UT_SUP:" + criteriosGetTicketConstruccion.getId_ut_sup());
				log.info("I_USUARIO:" + criteriosGetTicketConstruccion.getUsuario());

				if (criteriosGetTicketConstruccion.getIdTicket() != null && !criteriosGetTicketConstruccion.getIdTicket().trim().equals("") && !criteriosGetTicketConstruccion.getIdTicket().trim().equals("undefined")) {					
					function.getImportParameterList().setValue("I_NTICK",     criteriosGetTicketConstruccion.getIdTicket());
					log.info("Ticket: " + criteriosGetTicketConstruccion.getIdTicket());
				}
				
				if (criteriosGetTicketConstruccion.getIdFase() != null && !criteriosGetTicketConstruccion.getIdFase().trim().equals("null") && !criteriosGetTicketConstruccion.getIdFase().trim().equals("") && !criteriosGetTicketConstruccion.getIdFase().trim().equals("undefined")) {
					function.getImportParameterList().setValue("I_ID_FASE",     criteriosGetTicketConstruccion.getIdFase().substring(criteriosGetTicketConstruccion.getIdFase().length() -3, criteriosGetTicketConstruccion.getIdFase().length()));
					log.info("idFase: " + criteriosGetTicketConstruccion.getIdFase());
				}

				if (criteriosGetTicketConstruccion.getIdEquipo() != null && !criteriosGetTicketConstruccion.getIdEquipo().trim().equals("null") && !criteriosGetTicketConstruccion.getIdEquipo().trim().equals("") && !criteriosGetTicketConstruccion.getIdEquipo().trim().equals("undefined")) {
					function.getImportParameterList().setValue("I_ID_EQUI",     criteriosGetTicketConstruccion.getIdEquipo());
					log.info("idEquipoTicket: " + criteriosGetTicketConstruccion.getIdEquipo());
				}
				
				if (criteriosGetTicketConstruccion.getEstatus() != null && !criteriosGetTicketConstruccion.getEstatus().trim().equals("")
						&& !criteriosGetTicketConstruccion.getEstatus().trim().equals("null") && !criteriosGetTicketConstruccion.getEstatus().trim().equals("undefined")) {
					function.getImportParameterList().setValue("I_STATX",     criteriosGetTicketConstruccion.getEstatus());
					log.info("Estatus: " + criteriosGetTicketConstruccion.getEstatus());
				}			
				
				if (criteriosGetTicketConstruccion.getTxtArea() != null && !criteriosGetTicketConstruccion.getTxtArea().trim().equals("")
						&& !criteriosGetTicketConstruccion.getTxtArea().trim().equals("null") && !criteriosGetTicketConstruccion.getTxtArea().trim().equals("undefined")) {
					function.getImportParameterList().setValue("I_AREA",     criteriosGetTicketConstruccion.getTxtArea());
					log.info("Area: " + criteriosGetTicketConstruccion.getTxtArea());
				}	
				
				if ((criteriosGetTicketConstruccion.getFechaInicial() != null && !criteriosGetTicketConstruccion.getFechaInicial().trim().equals("") && !criteriosGetTicketConstruccion.getFechaInicial().trim().equals("undefined")) || 
					(criteriosGetTicketConstruccion.getFechaFinal()   != null && !criteriosGetTicketConstruccion.getFechaFinal().trim().equals("") && !criteriosGetTicketConstruccion.getFechaInicial().trim().equals("undefined"))) {
					
					if (criteriosGetTicketConstruccion.getFechaInicial() != null && !criteriosGetTicketConstruccion.getFechaInicial().trim().equals("") && !criteriosGetTicketConstruccion.getFechaInicial().trim().equals("undefined")) {
						String fechaLow[] = criteriosGetTicketConstruccion.getFechaInicial().split("/");
						function.getImportParameterList().setValue("I_FECHAB_LO",     fechaLow[2] + fechaLow[1] + fechaLow[0]);
						log.info("I_FECHAB_LO: " + fechaLow[2] + fechaLow[1] + fechaLow[0]);
					}
					if (criteriosGetTicketConstruccion.getFechaFinal()   != null && !criteriosGetTicketConstruccion.getFechaFinal().trim().equals("") && !criteriosGetTicketConstruccion.getFechaFinal().trim().equals("undefined")) {
						String fechaHi[] = criteriosGetTicketConstruccion.getFechaFinal().split("/");
						function.getImportParameterList().setValue("I_FECHAB_HI",     fechaHi[2] + fechaHi[1] + fechaHi[0]);
						log.info("I_FECHAB_HI: " +  fechaHi[2] + fechaHi[1] + fechaHi[0]);
					}
				}

				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				//enumTicket = (String) function.getExportParameterList().getString("E_NUM_TICKET");
				if (subrc.equals("00")) 
				{	
					//GENERAR RESPONSE
					tableTicketsHeadOut = function.getTableParameterList().getTable("IT_TICKET_HEAD_OUT");
					tableTicketsDetailOut = function.getTableParameterList().getTable("IT_TICKET_DETA_OUT");
					
					responseTicketConstruccionDto.setListTicket(getTicketsHeader(tableTicketsHeadOut,tableTicketsDetailOut));
					//responseTicketConstruccionDto.getListTicket().get(1).setListTicketDetail(getTicketsDetail(tableTicketsDetailOut));
					responseTicketConstruccionDto.setMensaje("SUCCESS");
					//responseTicketConstruccionDto.setDescripcion("Generado el número de ticket: " + enumTicket);
				}
				else
				{
					responseTicketConstruccionDto.setMensaje("FAULT");	
					responseTicketConstruccionDto.setDescripcion(bapierror);
				}
			} 
			catch (Exception excp) 
			{
				log.error("Error:",  excp);
				responseTicketConstruccionDto.setMensaje("FAULT");
				responseTicketConstruccionDto.setDescripcion(excp.getMessage());
				throw new ViviendaException ("ERROR:" + excp.getMessage());
			}
		}		
		return responseTicketConstruccionDto;	
	}
	
	public void addImagenesTicket(List<MultipartFile> files, String ticket, String path) throws ViviendaException {
        
		log.info("Implementacion SubirFichero: Inicializando proceso copia ficheros");
		
	    int consecutivo = 0;
	    String fileName = "";
	    String extension = "";
	    String ruta = "";

		   
         if(null != files && files.size() > 0) {
        	 
        	 for (MultipartFile fichero : files) {       
        		 consecutivo ++;
        		 
        		 if (!fichero.isEmpty()){
	    	     	   
 					fileName=fichero.getOriginalFilename();
					extension = fileName.substring(fileName.lastIndexOf("."));
					ruta = path + ticket + "-" +consecutivo + extension.toLowerCase();
      	    	   	
					
					ImageResizer imageResizer = new ImageResizer();
					File file1;
					
					File archivo = convert(fichero);
					
					try {
					
					BufferedImage bimg = ImageIO.read(archivo);
					int width          = bimg.getWidth();
					int height         = bimg.getHeight();
					
						if(width>640 || height>640){
						    file1 = imageResizer.multipartToFile(fichero);
							imageResizer.copyImageJPG(file1,ruta);
						}else{
							imageResizer.saveImage(bimg,ruta);
						}
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new ViviendaException("IllegalStateException Error al subir el fichero: " + ruta);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new ViviendaException("IOException Error al subir el fichero: " + ruta);
					}
					
					/*File ficheroDestino = new File(ruta);
      	           
      	    	    log.info( "Archivo: " + ruta );
      	    	   	try {
      	               fichero.transferTo(ficheroDestino);
      	    	   	} catch (IllegalStateException e) {
      	    	   		e.printStackTrace();
      	    	   		throw new ViviendaException("IllegalStateException Error al subir el fichero: " + ruta);
      	    	   	} catch (IOException e) {
      	    	   		e.printStackTrace();
      	    	   		throw new ViviendaException("IOException Error al subir el fichero: " + ruta);
      	    	   	}*/
      	                	
      	         }else {
      	        	log.info( "Archivo no existe" );
      	       }  //fin del if 
     
        	 }   //fin del for  
         }/*else{
        	 throw new ViviendaException("No se cargaron registros fotográfico"); 
         }*/
	}
	
public void addImagenTicket(List<MultipartFile> files, String ticket, String consecutivo, String path) throws ViviendaException {
        
		log.info("Implementacion SubirFichero: Inicializando proceso copia ficheros");
		
	    String fileName = "";
	    String extension = "";
	    String ruta = "";

		   
         if(null != files && files.size() > 0) {
        	 
        	 for (MultipartFile fichero : files) {       
        		 
        		 if (!fichero.isEmpty()){
	    	     	   
 					fileName=fichero.getOriginalFilename();
					extension = fileName.substring(fileName.lastIndexOf("."));
					ruta = path + ticket + "-" +consecutivo.trim() + extension.toLowerCase();
      	    	   	
					
					ImageResizer imageResizer = new ImageResizer();
					File file1;
					
					File archivo = convert(fichero);
					
					try {
					
					BufferedImage bimg = ImageIO.read(archivo);
					int width          = bimg.getWidth();
					int height         = bimg.getHeight();
					
						if(width>640 || height>640){
						    file1 = imageResizer.multipartToFile(fichero);
							imageResizer.copyImageJPG(file1,ruta);
						}else{
							imageResizer.saveImage(bimg,ruta);
						}
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.error("ERROR:" + e);
						throw new ViviendaException("IllegalStateException Error al subir el fichero: " + ruta);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.error("ERROR:" + e);
						throw new ViviendaException("IOException Error al subir el fichero: " + ruta);
					}
					
					/*File ficheroDestino = new File(ruta);
      	           
      	    	    log.info( "Archivo: " + ruta );
      	    	   	try {
      	               fichero.transferTo(ficheroDestino);
      	    	   	} catch (IllegalStateException e) {
      	    	   		e.printStackTrace();
      	    	   		throw new ViviendaException("IllegalStateException Error al subir el fichero: " + ruta);
      	    	   	} catch (IOException e) {
      	    	   		e.printStackTrace();
      	    	   		throw new ViviendaException("IOException Error al subir el fichero: " + ruta);
      	    	   	}*/
      	                	
      	         }else {
      	        	log.info( "Archivo no existe" );
      	       }  //fin del if 
     
        	 }   //fin del for  
         }/*else{
        	 throw new ViviendaException("No se cargaron registros fotográfico"); 
         }*/
	}
	
	public File convert(MultipartFile file)
	{    
	    File convFile = new File(file.getOriginalFilename());
	    try {
			convFile.createNewFile();
		    FileOutputStream fos = new FileOutputStream(convFile); 
		    fos.write(file.getBytes());
		    fos.close(); 
	    
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    
	    return convFile;
	}
	
	
	public void addPDFTicket(MultipartFile files, String ticket, String path) throws ViviendaException {
        
		log.info("Implementacion SubirFichero: Inicializando proceso copia ficheros");
		
	    String fileName = "";
	    String extension = "";
	    String ruta = "";

         if(files != null ) {
        		 
        		 //if (files.isEmpty()){
	    	     	   
 					fileName=files.getOriginalFilename();
					extension = fileName.substring(fileName.lastIndexOf("."));
					ruta = path + ticket + extension.toLowerCase();
      	    	   	File ficheroDestino = new File(ruta);
      	           
      	    	    log.info( "Archivo: " + ruta );
      	    	   	try {
      	    	   	files.transferTo(ficheroDestino);
      	    	   	} catch (IllegalStateException e) {
      	    	   		e.printStackTrace();
      	    	   		throw new ViviendaException("IllegalStateException Error al subir el fichero: " + ruta);
      	    	   	} catch (IOException e) {
      	    	   		e.printStackTrace();
      	    	   		throw new ViviendaException("IOException Error al subir el fichero: " + ruta);
      	    	   	}
      	                	
      	         //}else {
      	         //	log.info( "Archivo no existe" );
      	         //}  //fin del if 
     

         }/*else{
        	 throw new ViviendaException("No se cargaron registros fotográfico"); 
         }*/
	}
	
	
	public ResponseAddTicketDto addTicketConstruccion(ListaFicheros listaFicheros) throws ViviendaException {
		ResponseAddTicketDto responseAddTicketDto = new ResponseAddTicketDto();
		
		String sStatus;
		Connection connect = null;
		sStatus = ArchLogg.leeLogg();
		String subrc = "";
		String bapierror = "";
		String enumTicket = "";
		
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
				JCoFunction function = connect.getFunction("ZCSMF_0150_SAVE_TICKET");
				
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", listaFicheros.getId_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", listaFicheros.getUsuario());
				function.getImportParameterList().setValue("I_CLIENTE", listaFicheros.getCliente());
				function.getImportParameterList().setValue("I_ASUNTO", listaFicheros.getAsunto());
				//function.getImportParameterList().setValue("I_BODY_MAIL",   listaFicheros.getBody_mail());
				function.getImportParameterList().setValue("I_AREA", listaFicheros.getIdArea());
				function.getImportParameterList().setValue("I_PRIORI", listaFicheros.getPrioridad());
				function.getImportParameterList().setValue("I_ID_FASE", listaFicheros.getIdFase());
				function.getImportParameterList().setValue("I_ID_EQUI", listaFicheros.getIdEquipo());
				function.getImportParameterList().setValue("I_OBSGRLS", listaFicheros.getObservaciones());
				function.getImportParameterList().setValue("I_ADJUNTO", listaFicheros.getAdjunto());
				function.getImportParameterList().setValue("I_CCP", listaFicheros.getCcp());
				
				JCoTable itHeaderDet = function.getTableParameterList().getTable("IT_DETAIL_IN");
				llenaItHeaderDet(listaFicheros.getZona(), listaFicheros.getVicio(), itHeaderDet);
				
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGERR");
				enumTicket = (String) function.getExportParameterList().getString("O_NTICK");
				if (subrc.equals("00")) 
				{	
					//GENERAR RESPONSE
					responseAddTicketDto.setId_ticket_sap(enumTicket);
					responseAddTicketDto.setMensaje("SUCCESS");
					responseAddTicketDto.setDescripcion("El número de ticket " + enumTicket + " fue generado con éxito.");

				}
				else
				{
					responseAddTicketDto.setMensaje("FAULT");	
					responseAddTicketDto.setDescripcion(bapierror);
				}
			} 
			catch (Exception excp) 
			{
				log.error("Error:",  excp);
				responseAddTicketDto.setMensaje("FAULT");
				responseAddTicketDto.setDescripcion(excp.getMessage());
				throw new ViviendaException ("ERROR:" + excp.getMessage());
			}
		}		
		return responseAddTicketDto;	
	}
	
	
	public ResponseAddTicketDto updateTicketConstruccion(UpdateViciosVo updateViciosVo) throws ViviendaException {
		ResponseAddTicketDto response = new ResponseAddTicketDto();
		
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
				JCoFunction function = connect.getFunction("ZCSMF_0153_UPD_TICKET");
				
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_NTICK", updateViciosVo.getIdTicket());
				function.getImportParameterList().setValue("I_ID_UT_SUP", updateViciosVo.getId_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", updateViciosVo.getUsuario());
				function.getImportParameterList().setValue("I_ACTVD", updateViciosVo.getEstatus());
				function.getImportParameterList().setValue("I_ATIENDE", updateViciosVo.getAsignado());
				if(updateViciosVo.getFechat()!= null && !updateViciosVo.getFechat().equals("")  ){
					function.getImportParameterList().setValue("I_FECHAT", updateViciosVo.getFechat().substring(6,10) + updateViciosVo.getFechat().substring(3,5) + updateViciosVo.getFechat().substring(0,2));
					function.getImportParameterList().setValue("I_HORAAT", updateViciosVo.getFechat().substring(11,13) + updateViciosVo.getFechat().substring(14,16) + updateViciosVo.getFechat().substring(17,19));
				}else{
					function.getImportParameterList().setValue("I_FECHAT", "");
					function.getImportParameterList().setValue("I_HORAAT", "");
				}
				
				function.getImportParameterList().setValue("I_OBSVAC", updateViciosVo.getObservaciones());
				
				JCoTable itHeaderDet = function.getTableParameterList().getTable("IT_TICKET_DETA_IN");
				llenaItHeaderUpDet(updateViciosVo.getConse(), updateViciosVo.getZona(), updateViciosVo.getVicio(), updateViciosVo.getEstatusDet(), updateViciosVo.getMotivo(), updateViciosVo.getIdTicket(), itHeaderDet);
				
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				if (subrc.equals("00")) 
				{	
					//GENERAR RESPONSE
					response.setId_ticket_sap(updateViciosVo.getIdTicket());
					response.setMensaje("SUCCESS");
					response.setDescripcion("El número de ticket " + updateViciosVo.getIdTicket() + " fue actualizado con éxito.");

				}
				else
				{
					response.setMensaje("FAULT");	
					response.setDescripcion(bapierror);
					response.setId_ticket_sap(updateViciosVo.getIdTicket());
				}
			} 
			catch (Exception excp) 
			{
				log.error("Error:",  excp);
				response.setMensaje("FAULT");
				response.setDescripcion(excp.getMessage());
				throw new ViviendaException ("ERROR:" + excp.getMessage());
			}
		}		
		return response;	
	}
	
	
	public ResponseAddTicketDto findTicket(CriteriosGetTicket criteriosGetTicket) throws ViviendaException {
		ResponseAddTicketDto responseAddTicketDto = new ResponseAddTicketDto();
		
		String sStatus;
		Connection connect = null;
		sStatus = ArchLogg.leeLogg();
		String subrc = "";
		String bapierror = "";
		String enumTicket = "";
		
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
				JCoFunction function = connect.getFunction("ZCSMF_0076_GET_TICKET");
				
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP",   criteriosGetTicket.getId_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO",     criteriosGetTicket.getUsuario());
				log.info("I_ID_UT_SUP:" + criteriosGetTicket.getId_ut_sup());
				log.info("I_USUARIO:" + criteriosGetTicket.getUsuario());

				if ((criteriosGetTicket.getIdTicketInicial() != null && !criteriosGetTicket.getIdTicketInicial().trim().equals("")) || 
					(criteriosGetTicket.getIdTicketFinal()   != null && !criteriosGetTicket.getIdTicketFinal().trim().equals(""))) {					
					JCoTable itHeaderTickIn = function.getTableParameterList().getTable("IT_NTICK_IN");
					itHeaderTickIn.appendRow();		
					if (criteriosGetTicket.getIdTicketInicial() != null && !criteriosGetTicket.getIdTicketInicial().trim().equals("")) {
						itHeaderTickIn.setValue("NTICK_LOW", criteriosGetTicket.getIdTicketInicial());
					} 
					if (criteriosGetTicket.getIdTicketFinal()   != null && !criteriosGetTicket.getIdTicketFinal().trim().equals("")) {
						itHeaderTickIn.setValue("NTICK_HIGH", criteriosGetTicket.getIdTicketFinal());					
					}
				}
				
				if (criteriosGetTicket.getIdFase() != null && !criteriosGetTicket.getIdFase().trim().equals("null") && !criteriosGetTicket.getIdFase().trim().equals("")) {
					JCoTable itHeaderFaseIn = function.getTableParameterList().getTable("IT_ID_FASE_IN");
					itHeaderFaseIn.appendRow();
					itHeaderFaseIn.setValue("ID_FASE", criteriosGetTicket.getIdFase());
					log.info("idFaseTicket:" + criteriosGetTicket.getIdFase());
				}

				if (criteriosGetTicket.getIdEquipo() != null && !criteriosGetTicket.getIdEquipo().trim().equals("null") && !criteriosGetTicket.getIdEquipo().trim().equals("")) {
					JCoTable itHeaderEquipoIn = function.getTableParameterList().getTable("IT_ID_EQUNR_IN");
					itHeaderEquipoIn.appendRow();
					itHeaderEquipoIn.setValue("EQUNR", criteriosGetTicket.getIdEquipo());
					log.info("idEquipoTicket:" + criteriosGetTicket.getIdEquipo());
				}
				
				if (criteriosGetTicket.getId_car_cli() != null && !criteriosGetTicket.getId_car_cli().trim().equals("")) {
					JCoTable itHeaderCarCliIn = function.getTableParameterList().getTable("IT_CAR_CLI_IN");
					itHeaderCarCliIn.appendRow();
					itHeaderCarCliIn.setValue("ID_CAR_CLI", criteriosGetTicket.getId_car_cli());
				}					
				
				if (criteriosGetTicket.getSls_man() != null && !criteriosGetTicket.getSls_man().trim().equals("")) {
					JCoTable itHeaderSlsmManIn = function.getTableParameterList().getTable("IT_SLSM_MAN_IN");
					itHeaderSlsmManIn.appendRow();
					itHeaderSlsmManIn.setValue("USUARIO", criteriosGetTicket.getSls_man());
				}
				
				if (criteriosGetTicket.getTicket_area() != null && !criteriosGetTicket.getTicket_area().trim().equals("")) {
					JCoTable itHeaderAreaIn = function.getTableParameterList().getTable("IT_TICKET_AREA_IN");
					itHeaderAreaIn.appendRow();
					itHeaderAreaIn.setValue("TICKET_AREA", criteriosGetTicket.getTicket_area());
					log.info("idAreaTicket:" + criteriosGetTicket.getTicket_area());
				}
				
				if ((criteriosGetTicket.getFechaInicial() != null && !criteriosGetTicket.getFechaInicial().trim().equals("")) || 
					(criteriosGetTicket.getFechaFinal()   != null && !criteriosGetTicket.getFechaFinal().trim().equals(""))) {
					JCoTable itHeaderFechaIn = function.getTableParameterList().getTable("IT_AEDAT_IN");
					itHeaderFechaIn.appendRow();
					if (criteriosGetTicket.getFechaInicial() != null && !criteriosGetTicket.getFechaInicial().trim().equals("")) {
						String fechaLow[] = criteriosGetTicket.getFechaInicial().split("/");
						itHeaderFechaIn.setValue("AEDAT_LOW", fechaLow[2] + fechaLow[1] + fechaLow[0]);
					}
					if (criteriosGetTicket.getFechaFinal()   != null && !criteriosGetTicket.getFechaFinal().trim().equals("")) {
						String fechaHigh[] = criteriosGetTicket.getFechaFinal().split("/");
						itHeaderFechaIn.setValue("AEDAT_HIGH", fechaHigh[2] + fechaHigh[1] + fechaHigh[0]);
					}
				}

				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				//enumTicket = (String) function.getExportParameterList().getString("E_NUM_TICKET");
				if (subrc.equals("00")) 
				{	
					//GENERAR RESPONSE
					tableTicketsOut = function.getTableParameterList().getTable("IT_TICKET_INFO_OUT");
					responseAddTicketDto.setListTicket(getTickets(tableTicketsOut));
					responseAddTicketDto.setMensaje("SUCCESS");
					responseAddTicketDto.setDescripcion("Generado el número de ticket: " + enumTicket);
				}
				else
				{
					responseAddTicketDto.setMensaje("FAULT");	
					responseAddTicketDto.setDescripcion(bapierror);
				}
			} 
			catch (Exception excp) 
			{
				log.error("Error:",  excp);
				responseAddTicketDto.setMensaje("FAULT");
				responseAddTicketDto.setDescripcion(excp.getMessage());
				throw new ViviendaException ("ERROR:" + excp.getMessage());
			}
		}		
		return responseAddTicketDto;	
	}
			
	private ArrayList<TicketHeaderDto> getTicketsHeader(JCoTable t, JCoTable d) throws ViviendaException
	{
		if (t != null || d != null){
			if (t.getNumRows() > 0){
				ArrayList<TicketHeaderDto> a = new ArrayList<TicketHeaderDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new TicketHeaderDto());
					a.get(i).setIdTicket(t.getString("NTICK"));
					a.get(i).setIdUtSuperior(t.getString("ID_UT_SUP"));
					a.get(i).setIdFase(t.getString("ID_FASE"));
					a.get(i).setIdEquipo(t.getString("ID_EQUI"));
					a.get(i).setCliente(t.getString("CLIENTE"));
					a.get(i).setAsunto(t.getString("ASUNTO"));
					a.get(i).setTicketArea(t.getString("TICKET_AREA"));
					a.get(i).setAreatx(t.getString("AREATX"));
					a.get(i).setPrioridad(t.getString("PRIORI"));
					a.get(i).setEstatus(t.getString("STATX"));
					a.get(i).setFechab(t.getString("FECHAB"));
					a.get(i).setFechpr(t.getString("FECHPR"));
					a.get(i).setFechre(t.getString("FECHRE"));
					a.get(i).setFechat(t.getString("FECHAT") + " " + t.getString("HORAAT"));
					if(a.get(i).getFechat().equals("0000-00-00 00:00:00")){
						a.get(i).setFechat("");
					}
					a.get(i).setFechte(t.getString("FECHTE"));
					a.get(i).setFechci(t.getString("FECHCI"));
					a.get(i).setFechat_val(t.getString("FECHAT_VAL").equals("X")?"SI":"NO");
					a.get(i).setAsignado(t.getString("ATIENDE"));
					a.get(i).setRecibido(t.getString("RECIBE"));
					a.get(i).setAprobado(t.getString("APROBO"));
					a.get(i).setObservaciones(t.getString("OBSGRLS"));
					a.get(i).setCcp(t.getString("CCP"));
					
					ArrayList<TicketDetailDto> b = new ArrayList<TicketDetailDto>();
					d.firstRow();
					
					for (int h = 0; h < d.getNumRows(); h++){
						if (a.get(i).getIdTicket().equals(d.getString("NTICK"))){
							TicketDetailDto ticketDetailDto = new TicketDetailDto();
							ticketDetailDto.setIdTicket(d.getString("NTICK"));
							ticketDetailDto.setConsecutivo(d.getString("CONSE"));
							ticketDetailDto.setZona(d.getString("ZONA"));
							ticketDetailDto.setDescripcion(d.getString("DSCRP"));
							ticketDetailDto.setEstatus(d.getString("STATX"));
							ticketDetailDto.setMotivo(d.getString("MOTIVO"));
							ticketDetailDto.setIdFase(t.getString("ID_FASE"));
							ticketDetailDto.setIdEquipo(t.getString("ID_EQUI"));
							b.add(ticketDetailDto);
						}
						d.nextRow();
					}
					
					a.get(i).setListTicketDetail(b);
					
					//a.get(i).setDescripcion(t.getString("BODY_MAIL") == null? "":t.getString("BODY_MAIL"));

					//AEDAT
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}
	
	
	private void llenaItHeaderDet(ArrayList<String> zona, ArrayList<String> vicio,	JCoTable itHeader) {
		
		try {
			if(!zona.isEmpty()){
				 for(int i = 0; i < zona.size(); i++){
					   itHeader.appendRow();
					   itHeader.setValue("CONSE", i+1);
					   itHeader.setValue("ZONA", zona.get(i));
					   itHeader.setValue("DESCRP", vicio.get(i));			   
				 }
			}else{
				for(int i = 0; i < vicio.size(); i++){
					   itHeader.appendRow();
					   itHeader.setValue("CONSE", i+1);
					   itHeader.setValue("ZONA", "");
					   itHeader.setValue("DESCRP", vicio.get(i));			   
				 }
			}
		} catch (Exception e) {
			log.error("ERROR: ",e);			
		}
	}
	
	private void llenaItHeaderUpDet(ArrayList<String> conse, ArrayList<String> zona, ArrayList<String> vicio, ArrayList<String> estatusDet, ArrayList<String> motivo, String idTicket, JCoTable itHeader) {
		
		try {
			if(!zona.isEmpty()){
				 for(int i = 0; i < zona.size(); i++){
					   itHeader.appendRow();
					   itHeader.setValue("NTICK", idTicket);
					   itHeader.setValue("CONSE", i+1);
					   itHeader.setValue("ZONA", zona.get(i));
					   itHeader.setValue("DSCRP", vicio.get(i));
					   itHeader.setValue("STATX", estatusDet.get(i));
					   itHeader.setValue("MOTIVO", !motivo.isEmpty() ? motivo.get(i) : "");
				 }
			}else{
				for(int i = 0; i < vicio.size(); i++){
					   itHeader.appendRow();
					   itHeader.setValue("NTICK", idTicket);
					   itHeader.setValue("CONSE", i+1);
					   itHeader.setValue("ZONA", "");
					   itHeader.setValue("DSCRP", vicio.get(i));
					   itHeader.setValue("STATX", "");
					   itHeader.setValue("MOTIVO", "");
				 }		
			}
		} catch (Exception e) {
			log.error("ERROR: ",e);			
		}
	}
	
	
	public ClienteDatosTicketVo obtieneDatosCliente(CriteriosGetTicketConstruccion criteriosGetTicketConstruccion) throws ViviendaException {
		ClienteDatosTicketVo clienteDatosTicketVo = new ClienteDatosTicketVo();
		ClienteDatosTicketVo response = new ClienteDatosTicketVo();
		
		Connection connect = null;
		
		if (criteriosGetTicketConstruccion.getIdEquipo()!= null && !criteriosGetTicketConstruccion.getIdEquipo().equals("") ) 
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
				JCoFunction function = connect.getFunction("ZCSMF_0077_GET_INFO_GARA");
				
				function.getImportParameterList().setValue("I_ID_UT_SUP",   criteriosGetTicketConstruccion.getId_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO", "");
				
				// Establece Parametros Import
				JCoTable itHeader = function.getTableParameterList().getTable("IT_ID_EQUNR_IN");
				itHeader.appendRow();
				itHeader.setValue("SIGN", "I");
				itHeader.setValue("OPTION", "EQ");
				itHeader.setValue("LOW", criteriosGetTicketConstruccion.getIdEquipo());
				
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				
				//subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				//bapierror = (String) function.getExportParameterList().getString("E_MSGERR");

				//if (subrc.equals("00")) 
				//{	
					//GENERAR RESPONSE

				clienteDatosTicketVo = getDatosCliente(function.getTableParameterList().getTable("IT_INFO_GARA_OUT"));
				if(clienteDatosTicketVo == null){
					response.setDescripcion("No existe informacion para el equipo");
					response.setMensaje("FAULT");
					return response;
				}
					
				/*}
				else
				{
					response.setMensaje("FAULT");	
					response.setDescripcion(bapierror);
				}*/
			} 
			catch (Exception excp) 
			{
				log.error("Error:",  excp);
				response.setMensaje("FAULT");
				response.setDescripcion(excp.getMessage());
				throw new ViviendaException ("ERROR:" + excp.getMessage());
			}
		}
		clienteDatosTicketVo.setMensaje("SUCCESS");
		return clienteDatosTicketVo;	
	}

	public ViciosResponse obtieneCatalogoVicios() throws ViviendaException {
				ViciosResponse response = new ViciosResponse();
		
		Connection connect = null;
		
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
				JCoFunction function = connect.getFunction("ZCSMF_0155_CAT_VIC_OCULTOS");
					
				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				
				//subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				//bapierror = (String) function.getExportParameterList().getString("E_MSGERR");

				//if (subrc.equals("00")) 
				//{	
					//GENERAR RESPONSE

				//ZOPCION
				ArrayList<String> vicios = getCatalogoVicios(function.getTableParameterList().getTable("IT_SUBMENU"));
				if(vicios == null){
					response.setDescripcion("No existe informacion para el equipo");
					response.setMensaje("FAULT");
					return response;
				}
				else
				{
					response.setTexto(vicios);	
				}
			} 
			catch (Exception excp) 
			{
				log.error("Error:",  excp);
				response.setMensaje("FAULT");
				response.setDescripcion(excp.getMessage());
				throw new ViviendaException ("ERROR:" + excp.getMessage());
			}
			response.setMensaje("SUCCESS");
		return response;	
	}
	
	
	private ClienteDatosTicketVo getDatosCliente(JCoTable t) throws ViviendaException
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ClienteDatosTicketVo a = new ClienteDatosTicketVo();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.setDepto(t.getString("EQUNR"));
					a.setNombre(t.getString("KUNNRTX"));
					a.setCorreo(t.getString("EMAIL"));
					a.setTelefono(t.getString("TELEFONO"));
					a.setFechaInicioGarantia(t.getString("RE_FIGA").equals("0000-00-00")? "" : t.getString("RE_FIGA"));
					a.setFechaFinGarantia(t.getString("AV_FFGA").equals("0000-00-00")? "" : t.getString("AV_FFGA"));
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}
	
	
	private ArrayList<String> getCatalogoVicios(JCoTable t) throws ViviendaException
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<String> a = new ArrayList<String>();
				t.firstRow();
				a.add("|");
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(t.getString("ZOPCION"));
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}
	
	
	
	public ResponseLogConstruccionDto getLogTicketConstruccion(CriteriosGetTicketConstruccion criteriosGetTicketConstruccion) throws ViviendaException {
		ResponseLogConstruccionDto responseTicketConstruccionDto = new ResponseLogConstruccionDto();
		
		Connection connect = null;
		String subrc = "";
		String bapierror = "";
		JCoTable tableTicketsLog;

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
				JCoFunction function = connect.getFunction("ZCSMF_0152_GET_TICKET1");
				
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP",   criteriosGetTicketConstruccion.getId_ut_sup());
				function.getImportParameterList().setValue("I_USUARIO",     criteriosGetTicketConstruccion.getUsuario());
				log.info("I_ID_UT_SUP:" + criteriosGetTicketConstruccion.getId_ut_sup());
				log.info("I_USUARIO:" + criteriosGetTicketConstruccion.getUsuario());

				if (criteriosGetTicketConstruccion.getIdTicket() != null && !criteriosGetTicketConstruccion.getIdTicket().trim().equals("") && !criteriosGetTicketConstruccion.getIdTicket().trim().equals("undefined")) {					
					function.getImportParameterList().setValue("I_NTICK",     criteriosGetTicketConstruccion.getIdTicket());
					log.info("Ticket: " + criteriosGetTicketConstruccion.getIdTicket());
				}
				
				if (criteriosGetTicketConstruccion.getIdFase() != null && !criteriosGetTicketConstruccion.getIdFase().trim().equals("null") && !criteriosGetTicketConstruccion.getIdFase().trim().equals("") && !criteriosGetTicketConstruccion.getIdFase().trim().equals("undefined")) {
					function.getImportParameterList().setValue("I_ID_FASE",     criteriosGetTicketConstruccion.getIdFase());
					log.info("idFase: " + criteriosGetTicketConstruccion.getIdFase());
				}

				if (criteriosGetTicketConstruccion.getIdEquipo() != null && !criteriosGetTicketConstruccion.getIdEquipo().trim().equals("null") && !criteriosGetTicketConstruccion.getIdEquipo().trim().equals("") && !criteriosGetTicketConstruccion.getIdEquipo().trim().equals("undefined")) {
					function.getImportParameterList().setValue("I_ID_EQUI",     criteriosGetTicketConstruccion.getIdFase());
					log.info("idEquipoTicket: " + criteriosGetTicketConstruccion.getIdEquipo());
				}
				
				if (criteriosGetTicketConstruccion.getEstatus() != null && !criteriosGetTicketConstruccion.getEstatus().trim().equals("")
						&& !criteriosGetTicketConstruccion.getEstatus().trim().equals("null") && !criteriosGetTicketConstruccion.getEstatus().trim().equals("undefined")) {
					function.getImportParameterList().setValue("I_STATX",     criteriosGetTicketConstruccion.getEstatus());
					log.info("Estatus: " + criteriosGetTicketConstruccion.getEstatus());
				}			
				
				if ((criteriosGetTicketConstruccion.getFechaInicial() != null && !criteriosGetTicketConstruccion.getFechaInicial().trim().equals("") && !criteriosGetTicketConstruccion.getFechaInicial().trim().equals("undefined")) || 
					(criteriosGetTicketConstruccion.getFechaFinal()   != null && !criteriosGetTicketConstruccion.getFechaFinal().trim().equals("") && !criteriosGetTicketConstruccion.getFechaInicial().trim().equals("undefined"))) {
					
					if (criteriosGetTicketConstruccion.getFechaInicial() != null && !criteriosGetTicketConstruccion.getFechaInicial().trim().equals("") && !criteriosGetTicketConstruccion.getFechaInicial().trim().equals("undefined")) {
						String fechaLow[] = criteriosGetTicketConstruccion.getFechaInicial().split("/");
						function.getImportParameterList().setValue("I_FECHAB_LO",     fechaLow[2] + fechaLow[1] + fechaLow[0]);
						log.info("I_FECHAB_LO: " + criteriosGetTicketConstruccion.getEstatus());
					}
					if (criteriosGetTicketConstruccion.getFechaFinal()   != null && !criteriosGetTicketConstruccion.getFechaFinal().trim().equals("") && !criteriosGetTicketConstruccion.getFechaFinal().trim().equals("undefined")) {
						String fechaHi[] = criteriosGetTicketConstruccion.getFechaInicial().split("/");
						function.getImportParameterList().setValue("I_FECHAB_HI",     fechaHi[2] + fechaHi[1] + fechaHi[0]);
						log.info("I_FECHAB_HI: " +  fechaHi[2] + fechaHi[1] + fechaHi[0]);
					}
				}

				connect.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				subrc = (String) function.getExportParameterList().getString("E_SUBRC");
				bapierror = (String) function.getExportParameterList().getString("E_MSGBAPI");
				//enumTicket = (String) function.getExportParameterList().getString("E_NUM_TICKET");
				if (subrc.equals("00")) 
				{	
					//GENERAR RESPONSE
					tableTicketsLog = function.getTableParameterList().getTable("IT_TICKET_LOG");
					responseTicketConstruccionDto.setListTicket(getTicketsLog(tableTicketsLog));
					responseTicketConstruccionDto.setMensaje("SUCCESS");
					//responseTicketConstruccionDto.setDescripcion("Generado el número de ticket: " + enumTicket);
				}
				else
				{
					responseTicketConstruccionDto.setMensaje("FAULT");	
					responseTicketConstruccionDto.setDescripcion(bapierror);
				}
			} 
			catch (Exception excp) 
			{
				log.error("Error:",  excp);
				responseTicketConstruccionDto.setMensaje("FAULT");
				responseTicketConstruccionDto.setDescripcion(excp.getMessage());
				throw new ViviendaException ("ERROR:" + excp.getMessage());
			}
	
		return responseTicketConstruccionDto;	
	}
	
	
	private ArrayList<TicketLogDto> getTicketsLog(JCoTable t) throws ViviendaException
	{
		if (t != null){
			if (t.getNumRows() > 0){
				ArrayList<TicketLogDto> a = new ArrayList<TicketLogDto>();
				t.firstRow();
				for (int i = 0; i < t.getNumRows(); i++){
					a.add(new TicketLogDto());
					a.get(i).setIdTicket(t.getString("NTICK"));
					a.get(i).setFecha(t.getString("FECHUP"));
					a.get(i).setHora(t.getString("HORAUP"));
					a.get(i).setActividad(t.getString("ACTVD"));
					a.get(i).setUsuario(t.getString("USERAC"));
					a.get(i).setObservaciones(t.getString("OBSVAC") == null? "":t.getString("OBSVAC"));

					//AEDAT
					t.nextRow();
				}
				return a;
			}
		}
		return null;
	}
	
			
}
