/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.Arrays;

import org.springframework.web.bind.annotation.RequestParam;


/**
 * Clase que encapsula los fields para Busqueda e insercion de cartera de Clientes 
 * Fecha de creación: XX/06/2012               
 */
public class CriteriosGetInfCarCliente {
	
	private String usuario;
	private String id_ut_sup;
	private int accion;
	private String usrAdm;
	private String pwdAdm;
	private String id_selected;
	private String strCriterios;
	private String nombre1C;
	private String nombre2C;
	private String app_patC;
	private String app_matC;
	private String fch_ncm;
	private String telfnC;
	private String tlfmoC;
	private String telofC;
	private String extncC;
	private String mail1C;
	private String mail2C;
	private int via_con;
	private String vendedor;
	private String act_cli;
	private boolean reasignaV;
	private String xmlNom;
	private String xmlNom2;
	private String xmlApPat;
	private String xmlApMat;
	private String xmlIds;
	
	private String idEqunr;
	private String statusImpresion;
	
	private String idVendedorActual[];
	private String idVendedorSiguiente[];
	private String idMotivo[];
	
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
	 * @return the telfn
	 */
	/**
	 * @return the via_con
	 */
	public int getVia_con() {
		return via_con;
	}
	/**
	 * @param via_con the via_con to set
	 */
	public void setVia_con(int via_con) {
		this.via_con = via_con;
	}
	/**
	 * @return the vendedor
	 */
	public String getVendedor() {
		return vendedor;
	}
	/**
	 * @param vendedor the vendedor to set
	 */
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	/**
	 * @return the act_cli
	 */
	public String getAct_cli() {
		return act_cli;
	}
	/**
	 * @param act_cli the act_cli to set
	 */
	public void setAct_cli(String act_cli) {
		this.act_cli = act_cli;
	}
	/**
	 * @return the reasignaV
	 */
	public boolean isReasignaV() {
		return reasignaV;
	}
	/**
	 * @param reasignaV the reasignaV to set
	 */
	public void setReasignaV(boolean reasignaV) {
		this.reasignaV = reasignaV;
	}
	/**
	 * @return the strCriterios
	 */
	public String getStrCriterios() {
		return strCriterios;
	}
	/**
	 * @param strCriterios the strCriterios to set
	 */
	public void setStrCriterios(String strCriterios) {
		this.strCriterios = strCriterios;
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
	 * @return the usrAdm
	 */
	public String getUsrAdm() {
		return usrAdm;
	}
	/**
	 * @param usrAdm the usrAdm to set
	 */
	public void setUsrAdm(String usrAdm) {
		this.usrAdm = usrAdm;
	}
	/**
	 * @return the pwdAdm
	 */
	public String getPwdAdm() {
		return pwdAdm;
	}
	/**
	 * @param pwdAdm the pwdAdm to set
	 */
	public void setPwdAdm(String pwdAdm) {
		this.pwdAdm = pwdAdm;
	}
	/**
	 * @return the xmlNom
	 */
	public String getXmlNom() {
		return xmlNom;
	}
	/**
	 * @param xmlNom the xmlNom to set
	 */
	public void setXmlNom(String xmlNom) {
		this.xmlNom = xmlNom;
	}
	/**
	 * @return the xmlNom2
	 */
	public String getXmlNom2() {
		return xmlNom2;
	}
	/**
	 * @param xmlNom2 the xmlNom2 to set
	 */
	public void setXmlNom2(String xmlNom2) {
		this.xmlNom2 = xmlNom2;
	}
	/**
	 * @return the xmlApPat
	 */
	public String getXmlApPat() {
		return xmlApPat;
	}
	/**
	 * @param xmlApPat the xmlApPat to set
	 */
	public void setXmlApPat(String xmlApPat) {
		this.xmlApPat = xmlApPat;
	}
	/**
	 * @return the xmlApMat
	 */
	public String getXmlApMat() {
		return xmlApMat;
	}
	/**
	 * @param xmlApMat the xmlApMat to set
	 */
	public void setXmlApMat(String xmlApMat) {
		this.xmlApMat = xmlApMat;
	}
	/**
	 * @return the xmlIds
	 */
	public String getXmlIds() {
		return xmlIds;
	}
	/**
	 * @param xmlIds the xmlIds to set
	 */
	public void setXmlIds(String xmlIds) {
		this.xmlIds = xmlIds;
	}
	public String getIdEqunr() {
		return idEqunr;
	}
	public void setIdEqunr(String idEqunr) {
		this.idEqunr = idEqunr;
	}
	public String getStatusImpresion() {
		return statusImpresion;
	}
	public void setStatusImpresion(String statusImpresion) {
		this.statusImpresion = statusImpresion;
	}
	public String[] getIdVendedorActual() {
		return idVendedorActual;
	}
	public void setIdVendedorActual(String[] idVendedorActual) {
		this.idVendedorActual = idVendedorActual;
	}
	public String[] getIdVendedorSiguiente() {
		return idVendedorSiguiente;
	}
	public void setIdVendedorSiguiente(String[] idVendedorSiguiente) {
		this.idVendedorSiguiente = idVendedorSiguiente;
	}
	public String[] getIdMotivo() {
		return idMotivo;
	}
	public void setIdMotivo(String[] idMotivo) {
		this.idMotivo = idMotivo;
	}
	@Override
	public String toString() {
		return "CriteriosGetInfCarCliente [usuario=" + usuario + ", id_ut_sup="
				+ id_ut_sup + ", accion=" + accion + ", usrAdm=" + usrAdm
				+ ", pwdAdm=" + pwdAdm + ", id_selected=" + id_selected
				+ ", strCriterios=" + strCriterios + ", nombre1C=" + nombre1C
				+ ", nombre2C=" + nombre2C + ", app_patC=" + app_patC
				+ ", app_matC=" + app_matC + ", fch_ncm=" + fch_ncm
				+ ", telfnC=" + telfnC + ", tlfmoC=" + tlfmoC + ", telofC="
				+ telofC + ", extncC=" + extncC + ", mail1C=" + mail1C
				+ ", mail2C=" + mail2C + ", via_con=" + via_con + ", vendedor="
				+ vendedor + ", act_cli=" + act_cli + ", reasignaV="
				+ reasignaV + ", xmlNom=" + xmlNom + ", xmlNom2=" + xmlNom2
				+ ", xmlApPat=" + xmlApPat + ", xmlApMat=" + xmlApMat
				+ ", xmlIds=" + xmlIds + ", idEqunr=" + idEqunr
				+ ", statusImpresion=" + statusImpresion
				+ ", idVendedorActual=" + Arrays.toString(idVendedorActual)
				+ ", idVendedorSiguiente="
				+ Arrays.toString(idVendedorSiguiente) + ", idMotivo="
				+ Arrays.toString(idMotivo) + "]";
	}
	
}
