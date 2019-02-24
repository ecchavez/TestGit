/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 20/07/2012             
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

/**
 * Clase que encapsula los fields para Busqueda e insercion de Clientes SAP
 * Fecha de creación:20/07/2012               
 */
public class ClienteSapDto {
	private String usuario;
	private String id_ut_sup;
	private String id_cliente_z;
	private String id_cliente_sap;
	private int accion;
	private String id_pais;
	private String id_selected;
	private String rfcC;
	private String edocvl;
	private String edocvlx;
	private String id_ifeC;
	private String id_pasprtC;
	private String curpC;
	
//direccion del cliente I_DIR_CLIENTE
	private String id_cliente_zDir;
	private String calleC;
	private String noextC;
	private String nointC;
	private String colnC;
	private String cdpstC;
	private String dlmcpC;
	private String region;
	private String regionx;
	private String pais;
	private String paisx;
	private String telfnC;
	private String carcli;
	private String idPedido;
	private String regimenFiscal;
	
	public String getCarcli() {
		return carcli;
	}
	public void setCarcli(String carcli) {
		this.carcli = carcli;
	}
	/**
	 * @return the sexoC
	 */
	public String getSexoC() {
		return sexoC;
	}
	/**
	 * @param sexoC the sexoC to set
	 */
	public void setSexoC(String sexoC) {
		this.sexoC = sexoC;
	}
	/**
	 * @return the sexoCDesc
	 */
	public String getSexoCDesc() {
		return sexoCDesc;
	}
	/**
	 * @param sexoCDesc the sexoCDesc to set
	 */
	public void setSexoCDesc(String sexoCDesc) {
		this.sexoCDesc = sexoCDesc;
	}
	/**
	 * @return the tratamientoC
	 */
	public String getTratamientoC() {
		return tratamientoC;
	}
	/**
	 * @param tratamientoC the tratamientoC to set
	 */
	public void setTratamientoC(String tratamientoC) {
		this.tratamientoC = tratamientoC;
	}
	/**
	 * @return the tratamientoCDesc
	 */
	public String getTratamientoCDesc() {
		return tratamientoCDesc;
	}
	/**
	 * @param tratamientoCDesc the tratamientoCDesc to set
	 */
	public void setTratamientoCDesc(String tratamientoCDesc) {
		this.tratamientoCDesc = tratamientoCDesc;
	}
	/**
	 * @return the edo_civilC
	 */
	public String getEdo_civilC() {
		return edo_civilC;
	}
	/**
	 * @param edo_civilC the edo_civilC to set
	 */
	public void setEdo_civilC(String edo_civilC) {
		this.edo_civilC = edo_civilC;
	}
	/**
	 * @return the edo_civilCDesc
	 */
	public String getEdo_civilCDesc() {
		return edo_civilCDesc;
	}
	/**
	 * @param edo_civilCDesc the edo_civilCDesc to set
	 */
	public void setEdo_civilCDesc(String edo_civilCDesc) {
		this.edo_civilCDesc = edo_civilCDesc;
	}
	/**
	 * @return the estdoC
	 */
	public String getEstdoC() {
		return estdoC;
	}
	/**
	 * @param estdoC the estdoC to set
	 */
	public void setEstdoC(String estdoC) {
		this.estdoC = estdoC;
	}
	/**
	 * @return the estdoCDesc
	 */
	public String getEstdoCDesc() {
		return estdoCDesc;
	}
	/**
	 * @param estdoCDesc the estdoCDesc to set
	 */
	public void setEstdoCDesc(String estdoCDesc) {
		this.estdoCDesc = estdoCDesc;
	}
	/**
	 * @return the paisC
	 */
	public String getPaisC() {
		return paisC;
	}
	/**
	 * @param paisC the paisC to set
	 */
	public void setPaisC(String paisC) {
		this.paisC = paisC;
	}
	/**
	 * @return the paisCDesc
	 */
	public String getPaisCDesc() {
		return paisCDesc;
	}
	/**
	 * @param paisCDesc the paisCDesc to set
	 */
	public void setPaisCDesc(String paisCDesc) {
		this.paisCDesc = paisCDesc;
	}
	private String telofC;
	private String extncC;
	private String tlfmoC;
	private String mail1C;
	private String mail2C;
	private String sexoC;
	private String sexoCDesc;
	private String tratamientoC;
	private String tratamientoCDesc;
	private String edo_civilC;
	private String edo_civilCDesc;
	private String estdoC;
	private String estdoCDesc;
	private String paisC;
	private String paisCDesc;
//datos del contacto I_CIENTE
	private String nombre1C;
	private String nombre2C;
	private String app_patC;
	private String app_matC;
	private String fch_ncm;
	private String sexo;
	private String sexox;
	private String tratamiento;
	private String tratamientox;
	private String idCarCliente;
	
	
	
	
	/**
	 * @return the tratamiento
	 */
	public String getTratamiento() {
		return tratamiento;
	}
	/**
	 * @param tratamiento the tratamiento to set
	 */
	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}
	/**
	 * @return the tratamientox
	 */
	public String getTratamientox() {
		return tratamientox;
	}
	/**
	 * @param tratamientox the tratamientox to set
	 */
	public void setTratamientox(String tratamientox) {
		this.tratamientox = tratamientox;
	}

	/**
	 * @return the id_selected
	 */
	public String getId_selected() {
		return id_selected;
	}
	/**
	 * @param id_selected the id_selected to set
	 */
	public void setId_selected(String id_selected) {
		this.id_selected = id_selected;
	}
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the id_ut_sup
	 */
	public String getId_ut_sup() {
		return id_ut_sup;
	}
	/**
	 * @param id_ut_sup the id_ut_sup to set
	 */
	public void setId_ut_sup(String id_ut_sup) {
		this.id_ut_sup = id_ut_sup;
	}
	/**
	 * @return the id_cliente_z
	 */
	public String getId_cliente_z() {
		return id_cliente_z;
	}
	/**
	 * @param id_cliente_z the id_cliente_z to set
	 */
	public void setId_cliente_z(String id_cliente_z) {
		this.id_cliente_z = id_cliente_z;
	}
	/**
	 * @return the id_cliente_sap
	 */
	public String getId_cliente_sap() {
		return id_cliente_sap;
	}
	/**
	 * @param id_cliente_sap the id_cliente_sap to set
	 */
	public void setId_cliente_sap(String id_cliente_sap) {
		this.id_cliente_sap = id_cliente_sap;
	}
	/**
	 * @return the accion
	 */
	public int getAccion() {
		return accion;
	}
	/**
	 * @param accion the accion to set
	 */
	public void setAccion(int accion) {
		this.accion = accion;
	}
	/**
	 * @return the id_pais
	 */
	public String getId_pais() {
		return id_pais;
	}
	/**
	 * @param id_pais the id_pais to set
	 */
	public void setId_pais(String id_pais) {
		this.id_pais = id_pais;
	}
	/**
	 * @return the nombre1C
	 */
	public String getNombre1C() {
		return nombre1C;
	}
	/**
	 * @param nombre1C the nombre1C to set
	 */
	public void setNombre1C(String nombre1C) {
		this.nombre1C = nombre1C;
	}
	/**
	 * @return the nombre2C
	 */
	public String getNombre2C() {
		return nombre2C;
	}
	/**
	 * @param nombre2C the nombre2C to set
	 */
	public void setNombre2C(String nombre2C) {
		this.nombre2C = nombre2C;
	}
	/**
	 * @return the app_patC
	 */
	public String getApp_patC() {
		return app_patC;
	}
	/**
	 * @param app_patC the app_patC to set
	 */
	public void setApp_patC(String app_patC) {
		this.app_patC = app_patC;
	}
	/**
	 * @return the app_matC
	 */
	public String getApp_matC() {
		return app_matC;
	}
	/**
	 * @param app_matC the app_matC to set
	 */
	public void setApp_matC(String app_matC) {
		this.app_matC = app_matC;
	}
	/**
	 * @return the fch_ncm
	 */
	public String getFch_ncm() {
		return fch_ncm;
	}
	/**
	 * @param fch_ncm the fch_ncm to set
	 */
	public void setFch_ncm(String fch_ncm) {
		this.fch_ncm = fch_ncm;
	}
		
	/**
	 * @return the rfcC
	 */
	public String getRfcC() {
		return rfcC;
	}
	/**
	 * @param rfcC the rfcC to set
	 */
	public void setRfcC(String rfcC) {
		this.rfcC = rfcC;
	}
	
	/**
	 * @return the id_ifeC
	 */
	public String getId_ifeC() {
		return id_ifeC;
	}
	/**
	 * @param id_ifeC the id_ifeC to set
	 */
	public void setId_ifeC(String id_ifeC) {
		this.id_ifeC = id_ifeC;
	}
	/**
	 * @return the id_pasprtC
	 */
	public String getId_pasprtC() {
		return id_pasprtC;
	}
	/**
	 * @param id_pasprtC the id_pasprtC to set
	 */
	public void setId_pasprtC(String id_pasprtC) {
		this.id_pasprtC = id_pasprtC;
	}
	/**
	 * @return the curpC
	 */
	public String getCurpC() {
		return curpC;
	}
	/**
	 * @param curpC the curpC to set
	 */
	public void setCurpC(String curpC) {
		this.curpC = curpC;
	}
	/**
	 * @return the calleC
	 */
	public String getCalleC() {
		return calleC;
	}
	/**
	 * @param calleC the calleC to set
	 */
	public void setCalleC(String calleC) {
		this.calleC = calleC;
	}
	/**
	 * @return the noextC
	 */
	public String getNoextC() {
		return noextC;
	}
	/**
	 * @param noextC the noextC to set
	 */
	public void setNoextC(String noextC) {
		this.noextC = noextC;
	}
	/**
	 * @return the nointC
	 */
	public String getNointC() {
		return nointC;
	}
	/**
	 * @param nointC the nointC to set
	 */
	public void setNointC(String nointC) {
		this.nointC = nointC;
	}
	/**
	 * @return the colnC
	 */
	public String getColnC() {
		return colnC;
	}
	/**
	 * @param colnC the colnC to set
	 */
	public void setColnC(String colnC) {
		this.colnC = colnC;
	}
	/**
	 * @return the cdpstC
	 */
	public String getCdpstC() {
		return cdpstC;
	}
	/**
	 * @param cdpstC the cdpstC to set
	 */
	public void setCdpstC(String cdpstC) {
		this.cdpstC = cdpstC;
	}
	/**
	 * @return the dlmcpC
	 */
	public String getDlmcpC() {
		return dlmcpC;
	}
	/**
	 * @param dlmcpC the dlmcpC to set
	 */
	public void setDlmcpC(String dlmcpC) {
		this.dlmcpC = dlmcpC;
	}
	/**
	 * @return the estdoC
	 */

	
	
	/**
	 * @return the telfnC
	 */
	public String getTelfnC() {
		return telfnC;
	}
	/**
	 * @param telfnC the telfnC to set
	 */
	public void setTelfnC(String telfnC) {
		this.telfnC = telfnC;
	}
	/**
	 * @return the telofC
	 */
	public String getTelofC() {
		return telofC;
	}
	/**
	 * @param telofC the telofC to set
	 */
	public void setTelofC(String telofC) {
		this.telofC = telofC;
	}
	/**
	 * @return the extncC
	 */
	public String getExtncC() {
		return extncC;
	}
	/**
	 * @param extncC the extncC to set
	 */
	public void setExtncC(String extncC) {
		this.extncC = extncC;
	}
	/**
	 * @return the tlfmoC
	 */
	public String getTlfmoC() {
		return tlfmoC;
	}
	/**
	 * @param tlfmoC the tlfmoC to set
	 */
	public void setTlfmoC(String tlfmoC) {
		this.tlfmoC = tlfmoC;
	}
	/**
	 * @return the mail1C
	 */
	public String getMail1C() {
		return mail1C;
	}
	/**
	 * @param mail1C the mail1C to set
	 */
	public void setMail1C(String mail1C) {
		this.mail1C = mail1C;
	}
	/**
	 * @return the mail2C
	 */
	public String getMail2C() {
		return mail2C;
	}
	/**
	 * @param mail2C the mail2C to set
	 */
	public void setMail2C(String mail2C) {
		this.mail2C = mail2C;
	}
	/**
	 * @return the id_cliente_zDir
	 */
	public String getId_cliente_zDir() {
		return id_cliente_zDir;
	}
	/**
	 * @param id_cliente_zDir the id_cliente_zDir to set
	 */
	public void setId_cliente_zDir(String id_cliente_zDir) {
		this.id_cliente_zDir = id_cliente_zDir;
	}
	

	
	/**
	 * @return the sexo
	 */
	public String getSexo() {
		return sexo;
	}
	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	/**
	 * @return the sexox
	 */
	public String getSexox() {
		return sexox;
	}
	/**
	 * @param sexox the sexox to set
	 */
	public void setSexox(String sexox) {
		this.sexox = sexox;
	}
	/**
	 * @return the edocvl
	 */
	public String getEdocvl() {
		return edocvl;
	}
	/**
	 * @param edocvl the edocvl to set
	 */
	public void setEdocvl(String edocvl) {
		this.edocvl = edocvl;
	}
	/**
	 * @return the edocvlx
	 */
	public String getEdocvlx() {
		return edocvlx;
	}
	/**
	 * @param edocvlx the edocvlx to set
	 */
	public void setEdocvlx(String edocvlx) {
		this.edocvlx = edocvlx;
	}
	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	/**
	 * @return the regionx
	 */
	public String getRegionx() {
		return regionx;
	}
	/**
	 * @param regionx the regionx to set
	 */
	public void setRegionx(String regionx) {
		this.regionx = regionx;
	}
	/**
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}
	/**
	 * @param pais the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}
	/**
	 * @return the paisx
	 */
	public String getPaisx() {
		return paisx;
	}
	/**
	 * @param paisx the paisx to set
	 */
	public void setPaisx(String paisx) {
		this.paisx = paisx;
	}
	/**
	 * @return the regimenFiscal
	 */
	public String getRegimenFiscal() {
		return regimenFiscal;
	}
	/**
	 * @param regimenFiscal the regimenFiscal to set
	 */
	public void setRegimenFiscal(String regimenFiscal) {
		this.regimenFiscal = regimenFiscal;
	}
	/**
	 * @return the idCarCliente
	 */
	public String getIdCarCliente() {
		return idCarCliente;
	}
	/**
	 * @param idCarCliente the idCarCliente to set
	 */
	public void setIdCarCliente(String idCarCliente) {
		this.idCarCliente = idCarCliente;
	}
	public String getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}
	
}