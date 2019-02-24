/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 16/08/2012              
 */
package mx.com.grupogigante.gestionvivienda.services;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.portable.ResponseHandler;

import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

/**
 * @author WSNADM
 *
 */
public interface IContratosService {

	public abstract HttpServletResponse export(HttpServletResponse response,ByteArrayOutputStream baos) throws ViviendaException;
	
}
