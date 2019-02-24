/**
 * @author Osvaldo Rodriguez Martinez / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.dao;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import mx.com.grupogigante.gestionvivienda.domain.dto.BoletinDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosDatosMapaImagenDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosFileUploadDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUbicacionDatosMapaDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUploadFilesDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;
import mx.com.grupogigante.gestionvivienda.utils.ImageResizer;

/**
 * Clase en donde se realiza la implementacion de todas las operaciones referentes a subir archivos 
 * 
 * Fecha de creación: XX/06/2012               
 */
@Repository
public class UploadFilesDaoImp implements UploadFilesDao {
	private Logger log = Logger.getLogger(UploadFilesDaoImp.class);
	
	/**
	 * Método que regresa una lista de vendedores permitidos de acuerdo al perfil 
	 * 
	 * @param criteriosClienteDto 
	 * 			criterios de busqueda de clientes  
	 * @return ResponseGetInfCarCliente 
	 * 			objeto de respuesta para informacion de clientes 
	 * @exception  Exception  
	 */		
	public ResponseUploadFilesDto setUploadImageMaping(CriteriosFileUploadDto criterios) throws ViviendaException{
		ResponseUploadFilesDto responseUplodFilesDto=new ResponseUploadFilesDto();
		try
		{
			MultipartFile file = criterios.getFileData();

			if (file.getSize() > 0) {
				if (file.getSize() < 1000000) {
					
					/*File archivo_server = new File(criterios.getName());
					file.transferTo(archivo_server);*/

					ImageResizer imageResizer = new ImageResizer();
					File file1 = imageResizer.multipartToFile(file);
					
					imageResizer.copyImage(file1,criterios.getName());
					
					//System.out.println("fileName:" + file.getOriginalFilename());
					responseUplodFilesDto.setMensaje("SUCCESS");
					responseUplodFilesDto.setDescripcion("Archivo copiado satisfactoriamente");
				}
				else
				{
					responseUplodFilesDto.setMensaje("FAULT");
					responseUplodFilesDto.setDescripcion("Archivo supero el maximo permitido. Seleccione otro");
				}				
			}
			else
			{
				responseUplodFilesDto.setMensaje("FAULT");
				responseUplodFilesDto.setDescripcion("Archivo vacio seleccione otro");
			}
		} 
		catch (Exception excp) 
		{
			log.error("ERROR: ",excp);
			responseUplodFilesDto.setMensaje("FAULT");
			responseUplodFilesDto.setDescripcion(excp.getMessage());
			throw new ViviendaException(excp.getMessage());
		}
				
		return responseUplodFilesDto;	
	}
	
	
	public List<BoletinDto> viewContentFolder(CriteriosFileUploadDto criterios) throws ViviendaException{
		ArrayList<BoletinDto> listaboletin = new ArrayList<BoletinDto>();
		ResponseUploadFilesDto responseUplodFilesDto=new ResponseUploadFilesDto();
		try
		{
			String path = criterios.getName(); 

			//String files;
			  File folder = new File(path);
			  File[] listOfFiles = folder.listFiles(); 
			 
			  for (int i = 0; i < listOfFiles.length; i++) 
			  {
			 
			   if (listOfFiles[i].isFile()) 
			   {
			   BoletinDto dto = new BoletinDto();
			   dto.setNombreArchivo(listOfFiles[i].getName());
			   dto.setRutaArchivo(listOfFiles[i].getAbsolutePath());
			   listaboletin.add(dto);
			      }
			  }
		} 
		catch (Exception excp) 
		{
			log.error("ERROR: ",excp);
			responseUplodFilesDto.setMensaje("FAULT");
			responseUplodFilesDto.setDescripcion(excp.getMessage());
			throw new ViviendaException(excp.getMessage());
		}
				
		return listaboletin;	
	}
	
	
	public ResponseUploadFilesDto deleteContentFolder(CriteriosFileUploadDto criterios) throws ViviendaException{
		ResponseUploadFilesDto responseUplodFilesDto=new ResponseUploadFilesDto();
		try
		{
				String path = criterios.getName(); 

			String files;
			  File fichero = new File(path);
			    if (fichero.delete())
			    	responseUplodFilesDto.setDescripcion("Archivo borrado satisfactoriamente");
			        else
			        responseUplodFilesDto.setDescripcion("El archivo no puede ser borrado");
		} 
		catch (Exception excp) 
		{
			log.error("ERROR: ",excp);
			responseUplodFilesDto.setMensaje("FAULT");
			responseUplodFilesDto.setDescripcion(excp.getMessage());
			throw new ViviendaException(excp.getMessage());
		}
				
		return responseUplodFilesDto;	
	}
}
