/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que encapsula los fields de Clientes
 * Fecha de creación: XX/06/2012               
 */

public class ClientesDto {
	private String id_car_cli;
	private String nombre1;
	private String nombre2;
	private String app_pat;
	private String app_mat;
	private String fch_ncm;
	private String telfn;
	private String tlfmo;
	private String telof;
	private String extnc;
	private String mail1;
	private String mail2;
	private int status;
	private String statusx;
	private int via_con;
	private String via_conx;
	private String act_cli;
	private List<VisitsDto> visitasClienteList = new ArrayList<VisitsDto>();
	
	/**
	 * @return the id_car_cli
	 */
	public String getId_car_cli() {
		return id_car_cli;
	}
	/**
	 * @param id_car_cli the id_car_cli to set
	 */
	public void setId_car_cli(String id_car_cli) {
		this.id_car_cli = id_car_cli;
	}
	/**
	 * @return the nombre1
	 */
	public String getNombre1() {
		return nombre1;
	}
	/**
	 * @param nombre1 the nombre1 to set
	 */
	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}
	/**
	 * @return the nombre2
	 */
	public String getNombre2() {
		return nombre2;
	}
	/**
	 * @param nombre2 the nombre2 to set
	 */
	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}
	/**
	 * @return the app_pat
	 */
	public String getApp_pat() {
		return app_pat;
	}
	/**
	 * @param app_pat the app_pat to set
	 */
	public void setApp_pat(String app_pat) {
		this.app_pat = app_pat;
	}
	/**
	 * @return the app_mat
	 */
	public String getApp_mat() {
		return app_mat;
	}
	/**
	 * @param app_mat the app_mat to set
	 */
	public void setApp_mat(String app_mat) {
		this.app_mat = app_mat;
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
	public String getTelfn() {
		return telfn;
	}
	/**
	 * @param telfn the telfn to set
	 */
	public void setTelfn(String telfn) {
		this.telfn = telfn;
	}
	/**
	 * @return the tlfmo
	 */
	public String getTlfmo() {
		return tlfmo;
	}
	/**
	 * @param tlfmo the tlfmo to set
	 */
	public void setTlfmo(String tlfmo) {
		this.tlfmo = tlfmo;
	}
	/**
	 * @return the telof
	 */
	public String getTelof() {
		return telof;
	}
	/**
	 * @param telof the telof to set
	 */
	public void setTelof(String telof) {
		this.telof = telof;
	}
	/**
	 * @return the extnc
	 */
	public String getExtnc() {
		return extnc;
	}
	/**
	 * @param extnc the extnc to set
	 */
	public void setExtnc(String extnc) {
		this.extnc = extnc;
	}
	/**
	 * @return the mail1
	 */
	public String getMail1() {
		return mail1;
	}
	/**
	 * @param mail1 the mail1 to set
	 */
	public void setMail1(String mail1) {
		this.mail1 = mail1;
	}
	/**
	 * @return the mail2
	 */
	public String getMail2() {
		return mail2;
	}
	/**
	 * @param mail2 the mail2 to set
	 */
	public void setMail2(String mail2) {
		this.mail2 = mail2;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the statusx
	 */
	public String getStatusx() {
		return statusx;
	}
	/**
	 * @param statusx the statusx to set
	 */
	public void setStatusx(String statusx) {
		this.statusx = statusx;
	}
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
	 * @return the via_conx
	 */
	public String getVia_conx() {
		return via_conx;
	}
	/**
	 * @param via_conx the via_conx to set
	 */
	public void setVia_conx(String via_conx) {
		this.via_conx = via_conx;
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
	 * @return the visitasClienteList
	 */
	public List<VisitsDto> getVisitasClienteList() {
		return visitasClienteList;
	}
	/**
	 * @param visitasClienteList the visitasClienteList to set
	 */
	public void setVisitasClienteList(List<VisitsDto> visitasClienteList) {
		this.visitasClienteList = visitasClienteList;
	}
	
}