/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 20/07/2012             
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;


/**
 * Clase que encapsula los fields para Busqueda e insercion de Contactos SAP
 * Fecha de creación:20/07/2012               
 */
public class ContactoDto {
	
	private String id_cliente_z;
	//datos del contacto i_contacto
	private String nombre1Co;
	private String nombre2Co;
	private String app_patCo;
	private String app_matCo;
	private String sexo;
	private String sexox;
	private String tratamiento;
	private String tratamientox;
	private String sexoCo;
	private String sexoCoDesc;
	private String tratamientoCo;
	private String tratamientoCoDesc;
	private String rfcCo;
	private String ifeCo;
	private String pasprtCo;
	private String curpCo;
	//direccion del contacto i_dir_contacto
	private String id_cliente_zDir;
	private String calleCo;
	private String noextCo;
	private String nointCo;
	private String colnCo;
	private String cdpstCo;
	private String dlmcpCo;
	private String region;
	private String regionx;
	private String pais;
	private String paisx;
	private String estdoCo;
	private String estdoCoDesc;
	private String fechaNacContacto;
	
	

	/**
	 * @return the sexoCo
	 */
	public String getSexoCo() {
		return sexoCo;
	}
	/**
	 * @param sexoCo the sexoCo to set
	 */
	public void setSexoCo(String sexoCo) {
		this.sexoCo = sexoCo;
	}
	/**
	 * @return the sexoCoDesc
	 */
	public String getSexoCoDesc() {
		return sexoCoDesc;
	}
	/**
	 * @param sexoCoDesc the sexoCoDesc to set
	 */
	public void setSexoCoDesc(String sexoCoDesc) {
		this.sexoCoDesc = sexoCoDesc;
	}
	/**
	 * @return the tratamientoCo
	 */
	public String getTratamientoCo() {
		return tratamientoCo;
	}
	/**
	 * @param tratamientoCo the tratamientoCo to set
	 */
	public void setTratamientoCo(String tratamientoCo) {
		this.tratamientoCo = tratamientoCo;
	}
	/**
	 * @return the tratamientoCoDesc
	 */
	public String getTratamientoCoDesc() {
		return tratamientoCoDesc;
	}
	/**
	 * @param tratamientoCoDesc the tratamientoCoDesc to set
	 */
	public void setTratamientoCoDesc(String tratamientoCoDesc) {
		this.tratamientoCoDesc = tratamientoCoDesc;
	}
	/**
	 * @return the estdoCo
	 */
	public String getEstdoCo() {
		return estdoCo;
	}
	/**
	 * @param estdoCo the estdoCo to set
	 */
	public void setEstdoCo(String estdoCo) {
		this.estdoCo = estdoCo;
	}
	/**
	 * @return the estdoCoDesc
	 */
	public String getEstdoCoDesc() {
		return estdoCoDesc;
	}
	/**
	 * @param estdoCoDesc the estdoCoDesc to set
	 */
	public void setEstdoCoDesc(String estdoCoDesc) {
		this.estdoCoDesc = estdoCoDesc;
	}
	/**
	 * @return the paisCo
	 */
	public String getPaisCo() {
		return paisCo;
	}
	/**
	 * @param paisCo the paisCo to set
	 */
	public void setPaisCo(String paisCo) {
		this.paisCo = paisCo;
	}
	/**
	 * @return the paisCoDesc
	 */
	public String getPaisCoDesc() {
		return paisCoDesc;
	}
	/**
	 * @param paisCoDesc the paisCoDesc to set
	 */
	public void setPaisCoDesc(String paisCoDesc) {
		this.paisCoDesc = paisCoDesc;
	}
	private String paisCo;
	private String paisCoDesc;
	private String telfnCo;
	private String telofCo;
	private String extncCo;
	private String tlfmoCo;
	private String mail1Co;
	private String mail2Co;
	
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
	 * @return the nombre1Co
	 */
	public String getNombre1Co() {
		return nombre1Co;
	}
	/**
	 * @param nombre1Co the nombre1Co to set
	 */
	public void setNombre1Co(String nombre1Co) {
		this.nombre1Co = nombre1Co;
	}
	/**
	 * @return the nombre2Co
	 */
	public String getNombre2Co() {
		return nombre2Co;
	}
	/**
	 * @param nombre2Co the nombre2Co to set
	 */
	public void setNombre2Co(String nombre2Co) {
		this.nombre2Co = nombre2Co;
	}
	/**
	 * @return the app_patCo
	 */
	public String getApp_patCo() {
		return app_patCo;
	}
	/**
	 * @param app_patCo the app_patCo to set
	 */
	public void setApp_patCo(String app_patCo) {
		this.app_patCo = app_patCo;
	}
	/**
	 * @return the app_matCo
	 */
	public String getApp_matCo() {
		return app_matCo;
	}
	/**
	 * @param app_matCo the app_matCo to set
	 */
	public void setApp_matCo(String app_matCo) {
		this.app_matCo = app_matCo;
	}
	
	/**
	 * @return the rfcCo
	 */
	public String getRfcCo() {
		return rfcCo;
	}
	/**
	 * @param rfcCo the rfcCo to set
	 */
	public void setRfcCo(String rfcCo) {
		this.rfcCo = rfcCo;
	}
	/**
	 * @return the ifeCo
	 */
	public String getIfeCo() {
		return ifeCo;
	}
	/**
	 * @param ifeCo the ifeCo to set
	 */
	public void setIfeCo(String ifeCo) {
		this.ifeCo = ifeCo;
	}
	/**
	 * @return the pasprtCo
	 */
	public String getPasprtCo() {
		return pasprtCo;
	}
	/**
	 * @param pasprtCo the pasprtCo to set
	 */
	public void setPasprtCo(String pasprtCo) {
		this.pasprtCo = pasprtCo;
	}
	/**
	 * @return the curpCo
	 */
	public String getCurpCo() {
		return curpCo;
	}
	/**
	 * @param curpCo the curpCo to set
	 */
	public void setCurpCo(String curpCo) {
		this.curpCo = curpCo;
	}
	/**
	 * @return the calleCo
	 */
	public String getCalleCo() {
		return calleCo;
	}
	/**
	 * @param calleCo the calleCo to set
	 */
	public void setCalleCo(String calleCo) {
		this.calleCo = calleCo;
	}
	/**
	 * @return the noextCo
	 */
	public String getNoextCo() {
		return noextCo;
	}
	/**
	 * @param noextCo the noextCo to set
	 */
	public void setNoextCo(String noextCo) {
		this.noextCo = noextCo;
	}
	/**
	 * @return the nointCo
	 */
	public String getNointCo() {
		return nointCo;
	}
	/**
	 * @param nointCo the nointCo to set
	 */
	public void setNointCo(String nointCo) {
		this.nointCo = nointCo;
	}
	/**
	 * @return the colnCo
	 */
	public String getColnCo() {
		return colnCo;
	}
	/**
	 * @param colnCo the colnCo to set
	 */
	public void setColnCo(String colnCo) {
		this.colnCo = colnCo;
	}
	/**
	 * @return the cdpstCo
	 */
	public String getCdpstCo() {
		return cdpstCo;
	}
	/**
	 * @param cdpstCo the cdpstCo to set
	 */
	public void setCdpstCo(String cdpstCo) {
		this.cdpstCo = cdpstCo;
	}
	/**
	 * @return the dlmcpCo
	 */
	public String getDlmcpCo() {
		return dlmcpCo;
	}
	/**
	 * @param dlmcpCo the dlmcpCo to set
	 */
	public void setDlmcpCo(String dlmcpCo) {
		this.dlmcpCo = dlmcpCo;
	}
	
	/**
	 * @return the paisCo
	 */
	
	/**
	 * @return the telfnCo
	 */
	public String getTelfnCo() {
		return telfnCo;
	}
	/**
	 * @param telfnCo the telfnCo to set
	 */
	public void setTelfnCo(String telfnCo) {
		this.telfnCo = telfnCo;
	}
	/**
	 * @return the telofCo
	 */
	public String getTelofCo() {
		return telofCo;
	}
	/**
	 * @param telofCo the telofCo to set
	 */
	public void setTelofCo(String telofCo) {
		this.telofCo = telofCo;
	}
	/**
	 * @return the extncCo
	 */
	public String getExtncCo() {
		return extncCo;
	}
	/**
	 * @param extncCo the extncCo to set
	 */
	public void setExtncCo(String extncCo) {
		this.extncCo = extncCo;
	}
	/**
	 * @return the tlfmoCo
	 */
	public String getTlfmoCo() {
		return tlfmoCo;
	}
	/**
	 * @param tlfmoCo the tlfmoCo to set
	 */
	public void setTlfmoCo(String tlfmoCo) {
		this.tlfmoCo = tlfmoCo;
	}
	/**
	 * @return the mail1Co
	 */
	public String getMail1Co() {
		return mail1Co;
	}
	/**
	 * @param mail1Co the mail1Co to set
	 */
	public void setMail1Co(String mail1Co) {
		this.mail1Co = mail1Co;
	}
	/**
	 * @return the mail2Co
	 */
	public String getMail2Co() {
		return mail2Co;
	}
	/**
	 * @param mail2Co the mail2Co to set
	 */
	public void setMail2Co(String mail2Co) {
		this.mail2Co = mail2Co;
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
	 * @return the fechaNacContacto
	 */
	public String getFechaNacContacto() {
		return fechaNacContacto;
	}
	/**
	 * @param fechaNacContacto the fechaNacContacto to set
	 */
	public void setFechaNacContacto(String fechaNacContacto) {
		this.fechaNacContacto = fechaNacContacto;
	}

}
