package mx.com.grupogigante.gestionvivienda.controllers.clientes;

import javax.servlet.http.HttpSession;

import mx.com.grupogigante.gestionvivienda.dao.asignar.vendedor.ReasignaVendedorDao;
import mx.com.grupogigante.gestionvivienda.domain.dto.asignar.vendedor.ReasignaVendedorRequestDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.asignar.vendedor.ReasignaVendedorResponseDto;
import mx.com.grupogigante.gestionvivienda.exceptions.DaoException;
import mx.com.grupogigante.gestionvivienda.utils.SessionValidatorSTS;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReasingaVendedorController {
	private Logger log = Logger.getLogger(ReasingaVendedorController.class);
	
	@Autowired
	ReasignaVendedorDao vendedorDao;


	@RequestMapping(value = "/cliente/reasignacion/ReasignaVendedorFind.htm", method = RequestMethod.POST)
	public @ResponseBody ReasignaVendedorResponseDto consultaReferencias(HttpSession session) {
		    SessionValidatorSTS sesionValida = new SessionValidatorSTS();
		    ReasignaVendedorRequestDto  vendedorRequest  = new ReasignaVendedorRequestDto();
		    ReasignaVendedorResponseDto vendedorResponse = new ReasignaVendedorResponseDto(); 
		try {
			log.info("entrando a reasignacion de vendedores");
			vendedorRequest.setIusuario(sesionValida.getDatos(session,"usuario"));
			vendedorRequest.setIidUtSup(sesionValida.getDatos(session,"unidad"));
			vendedorResponse = vendedorDao.findVendedorMotivo(vendedorRequest);
			
			for (int i = 0 ; i < vendedorResponse.getListaVendedor().size() ; i++) {
				if (vendedorResponse.getListaVendedor().get(i).getUsuario().lastIndexOf(vendedorResponse.geteIdUsrCurr()) > 0) {
					log.info("Vendedor localizado en la posicion " + i + "-esima");
					vendedorResponse.setIndex(i);
					break;
				}
			}
			
			log.info("VENDEDORES:" +  vendedorResponse.getListaVendedor());
			log.info("MOTIVOS:" +  vendedorResponse.getListaMotivos());
			log.info("VENDEDOR ACTUAL:" +  vendedorResponse.geteIdUsrCurr());
		} catch (DaoException e) {
			log.error("ERROR:", e);
		}
		return vendedorResponse;
	}

}
