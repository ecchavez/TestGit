/**
} * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 16/08/2012              
 */
package mx.com.grupogigante.gestionvivienda.services;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletResponse;

import mx.com.grupogigante.gestionvivienda.dao.ContratoDao;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfCarCliente;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosGetInfContrato;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetCompPago;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetInfContrato;
import mx.com.grupogigante.gestionvivienda.domain.dto.cierre.ResponseActualizaStatusImpresionDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WSNADM
 *
 */
@Service
public class ContratosService implements IContratosService {
	
	@Autowired
	ContratoDao contratoDao;
	
	public HttpServletResponse export(HttpServletResponse response,ByteArrayOutputStream baos) {

		String fileName = "contratoStaLucia1.pdf";
		response.setHeader("Content-Disposition", "inline; filename="+ fileName);

		// Set content type
		response.setContentType("application/pdf");
		response.setContentLength(baos.size());
		return response;
	}
	
	public ResponseGetInfContrato getContrato(CriteriosGetInfContrato criteriosContrato) throws ViviendaException{
		return contratoDao.getContrato(criteriosContrato);
	}
	public ResponseGetCompPago getComprobante(CriteriosGetInfContrato criteriosContrato) throws ViviendaException{
		return contratoDao.getComprobante(criteriosContrato);
	}
	
	public ResponseActualizaStatusImpresionDto actualizaStatusImpresion(CriteriosGetInfCarCliente criteriosClienteDto) throws ViviendaException{
		return contratoDao.actualizaStatusImpresion(criteriosClienteDto);
	}
}
