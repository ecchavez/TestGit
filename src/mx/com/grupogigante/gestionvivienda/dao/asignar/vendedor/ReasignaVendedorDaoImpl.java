package mx.com.grupogigante.gestionvivienda.dao.asignar.vendedor;

import java.util.ArrayList;
import java.util.List;

import mx.com.grupogigante.gestionvivienda.domain.dto.asignar.vendedor.MotivosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.asignar.vendedor.ReasignaVendedorRequestDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.asignar.vendedor.ReasignaVendedorResponseDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.asignar.vendedor.VendedorDto;
import mx.com.grupogigante.gestionvivienda.exceptions.DaoException;
import mx.com.grupogigante.gestionvivienda.report.referencias.dto.ReferenciasBancoDto;
import mx.com.grupogigante.gestionvivienda.utils.ArchLogg;
import mx.com.grupogigante.gestionvivienda.utils.Connection;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

@Repository
public class ReasignaVendedorDaoImpl implements ReasignaVendedorDao {
	private Logger log = Logger.getLogger(ReasignaVendedorDaoImpl.class);

	public List<VendedorDto> toVendedoresRow (JCoTable  tablaVendedores) {
		List<VendedorDto> lista = new ArrayList<VendedorDto>();
		if (tablaVendedores != null){
			if (tablaVendedores.getNumRows() > 0){
				tablaVendedores.firstRow();
				for (int i = 0; i < tablaVendedores.getNumRows(); i++){
					lista.add(new VendedorDto());
					lista.get(i).setUsuario(tablaVendedores.getString("USUARIO"));
					lista.get(i).setPass(tablaVendedores.getString("PASS"));
					lista.get(i).setNombre1(tablaVendedores.getString("NOMBRE1"));
					lista.get(i).setNombre2(tablaVendedores.getString("NOMBRE2"));
					lista.get(i).setAppPat(tablaVendedores.getString("APP_PAT"));
					lista.get(i).setAppMat(tablaVendedores.getString("APP_MAT"));
					lista.get(i).setIdUtSu(tablaVendedores.getString("ID_UT_SUP"));
					lista.get(i).setIdUtSux(tablaVendedores.getString("ID_UT_SUPX"));
					lista.get(i).setTelfn(tablaVendedores.getString("TELFN"));
					lista.get(i).setExtnc(tablaVendedores.getString("EXTNC"));
					lista.get(i).setMail1(tablaVendedores.getString("MAIL1"));
					tablaVendedores.nextRow();
				}
			}
		}		
		return lista;
	}
	
	public List<MotivosDto> toMotivosRow (JCoTable  tablamotivos) {
		List<MotivosDto> lista = new ArrayList<MotivosDto>();
		if (tablamotivos != null){
			if (tablamotivos.getNumRows() > 0){
				tablamotivos.firstRow();
				for (int i = 0; i < tablamotivos.getNumRows(); i++){
					lista.add(new MotivosDto());
					lista.get(i).setIdMotivoRea(tablamotivos.getString("ID_MOTIVO_REA"));
					lista.get(i).setIdMotivoReax(tablamotivos.getString("ID_MOTIVO_REAX"));
					tablamotivos.nextRow();
				}
			}
		}		
		return lista;
	}

	public ReasignaVendedorResponseDto findVendedorMotivo(ReasignaVendedorRequestDto requestVendedores) throws DaoException {
		List<ReferenciasBancoDto> lista = new ArrayList<ReferenciasBancoDto>();
		Connection                         sapConnection = null;
		String     sapStatus     = ArchLogg.leeLogg();
		String     responseCode  = "";
		String     bapiError     = "";
		ReasignaVendedorResponseDto responseVendedores = new ReasignaVendedorResponseDto();
		
		log.info(requestVendedores.toString());
		
		if (sapStatus.equals("OK")) 	{
			try {
				sapConnection = (Connection.getConnect() == null? new Connection(ArchLogg.getSapSystem()):Connection.getConnect());
				// Establece RFC a ejecutar en SAP
				JCoFunction function = sapConnection.getFunction("ZCSMF_0017_CAT_VEND_MOTIV");
				// Establece Parametros Import
				function.getImportParameterList().setValue("I_ID_UT_SUP", requestVendedores.getIidUtSup());
				function.getImportParameterList().setValue("I_USUARIO",   requestVendedores.getIusuario());
				
				sapConnection.execute(function);
				// Recupera el estado de determinación de la funcion RFC
				responseCode = (String) function.getExportParameterList().getString("E_SUBRC");
				bapiError    = (String) function.getExportParameterList().getString("E_MSGBAPI");
				
				log.info("BAPI MESSAGE:" + responseCode);
				log.info("BAPI Error:" + bapiError);
				
				if (responseCode.equals("00")) 	{
					
					JCoTable  tablaVendedores = function.getTableParameterList().getTable("IT_SLS_MAN_OUT");					
					JCoTable  tablaMotivos = function.getTableParameterList().getTable("IT_MOTIVOS_OUT");					
					responseVendedores.setListaVendedor(toVendedoresRow(tablaVendedores));
					responseVendedores.setListaMotivos(toMotivosRow(tablaMotivos));
					responseVendedores.seteIdUsrCurr((String) function.getExportParameterList().getString("E_ID_USR_CURR"));
					responseVendedores.setMensaje("SUCCESS");	
					responseVendedores.setDescripcion("");
				} else {
					responseVendedores.setMensaje("FAULT");	
					responseVendedores.setDescripcion(bapiError);
				}
			} 
			catch (Exception excp) {
				log.error("ERROR", excp);
				responseVendedores.setMensaje("FAULT");
				responseVendedores.setDescripcion(excp.getMessage());
				throw new DaoException(excp.getMessage(), 100);
			}
		}		
		return responseVendedores;		
	}
}
