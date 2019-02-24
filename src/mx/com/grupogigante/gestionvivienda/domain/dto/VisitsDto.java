/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.Date;
/**
 * Clase que encapsula los fields de Visitas Dto
 * Fecha de creación: XX/06/2012               
 */
public class VisitsDto {
	private String id_ut_sup;
	private String id_car_cli;
	private String id_visita;
	private String slsman;
	private String slsman_nombre1;
	private String slsman_app_pat;
	private String slsman_app_mat;
	private int status;
	private String statusx;
	private String aedat_vis;
	private String cputm_vis;
	
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
	 * @return the id_visita
	 */
	public String getId_visita() {
		return id_visita;
	}
	/**
	 * @param id_visita the id_visita to set
	 */
	public void setId_visita(String id_visita) {
		this.id_visita = id_visita;
	}
	/**
	 * @return the slsman
	 */
	public String getSlsman() {
		return slsman;
	}
	/**
	 * @param slsman the slsman to set
	 */
	public void setSlsman(String slsman) {
		this.slsman = slsman;
	}
	/**
	 * @return the slsman_nombre1
	 */
	public String getSlsman_nombre1() {
		return slsman_nombre1;
	}
	/**
	 * @param slsman_nombre1 the slsman_nombre1 to set
	 */
	public void setSlsman_nombre1(String slsman_nombre1) {
		this.slsman_nombre1 = slsman_nombre1;
	}
	/**
	 * @return the slsman_app_pat
	 */
	public String getSlsman_app_pat() {
		return slsman_app_pat;
	}
	/**
	 * @param slsman_app_pat the slsman_app_pat to set
	 */
	public void setSlsman_app_pat(String slsman_app_pat) {
		this.slsman_app_pat = slsman_app_pat;
	}
	/**
	 * @return the slsman_app_mat
	 */
	public String getSlsman_app_mat() {
		return slsman_app_mat;
	}
	/**
	 * @param slsman_app_mat the slsman_app_mat to set
	 */
	public void setSlsman_app_mat(String slsman_app_mat) {
		this.slsman_app_mat = slsman_app_mat;
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
	 * @return the aedat_vis
	 */
	public String getAedat_vis() {
		return aedat_vis;
	}
	/**
	 * @param aedat_vis the aedat_vis to set
	 */
	public void setAedat_vis(String aedat_vis) {
		this.aedat_vis = aedat_vis;
	}
	/**
	 * @return the cputm_vis
	 */
	public String getCputm_vis() {
		return cputm_vis;
	}
	/**
	 * @param cputm_vis the cputm_vis to set
	 */
	public void setCputm_vis(String cputm_vis) {
		this.cputm_vis = cputm_vis;
	}
	@Override
	public String toString() {
		return "VisitsDto [id_ut_sup=" + id_ut_sup + ", id_car_cli="
				+ id_car_cli + ", id_visita=" + id_visita + ", slsman="
				+ slsman + ", slsman_nombre1=" + slsman_nombre1
				+ ", slsman_app_pat=" + slsman_app_pat + ", slsman_app_mat="
				+ slsman_app_mat + ", status=" + status + ", statusx="
				+ statusx + ", aedat_vis=" + aedat_vis + ", cputm_vis="
				+ cputm_vis + "]";
	}
	
	
	
}
