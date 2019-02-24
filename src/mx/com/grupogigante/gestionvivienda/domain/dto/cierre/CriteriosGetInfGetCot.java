/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 12/09/2012              
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.cierre;

/**
 * @author WSNADM
 *
 */
public class CriteriosGetInfGetCot {

	private String usuario;
	private String id_ut_sup;
	private int accion;
	private int cotPed;
	private String pedido;
	private String fregi;
	private String fase;
	private String xmlCot;
	private String xmlPed;
	private String xmlCli;
	private String xmlEq;
	private String xmlPagos;
	
	private String xmlNoms;
	private String xmlNoms2;
	private String xmlApPats;
	private String xmlApMats;
	private String time;
	
	private String idFaseEquipo;
	private String pathrel;
	
	public String getPathrel() {
		return pathrel;
	}
	public void setPathrel(String pathrel) {
		this.pathrel = pathrel;
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
	 * @return the cotPed
	 */
	public int getCotPed() {
		return cotPed;
	}
	/**
	 * @param cotPed the cotPed to set
	 */
	public void setCotPed(int cotPed) {
		this.cotPed = cotPed;
	}
	/**
	 * @return the xmlCot
	 */
	public String getXmlCot() {
		return xmlCot;
	}
	/**
	 * @param xmlCot the xmlCot to set
	 */
	public void setXmlCot(String xmlCot) {
		this.xmlCot = xmlCot;
	}
	/**
	 * @return the xmlPed
	 */
	public String getXmlPed() {
		return xmlPed;
	}
	/**
	 * @param xmlPed the xmlPed to set
	 */
	public void setXmlPed(String xmlPed) {
		this.xmlPed = xmlPed;
	}
	/**
	 * @return the xmlCli
	 */
	public String getXmlCli() {
		return xmlCli;
	}
	/**
	 * @param xmlCli the xmlCli to set
	 */
	public void setXmlCli(String xmlCli) {
		this.xmlCli = xmlCli;
	}
	/**
	 * @return the xmlEq
	 */
	public String getXmlEq() {
		return xmlEq;
	}
	/**
	 * @param xmlEq the xmlEq to set
	 */
	public void setXmlEq(String xmlEq) {
		this.xmlEq = xmlEq;
	}
	/**
	 * @return the xmlPagos
	 */
	public String getXmlPagos() {
		return xmlPagos;
	}
	/**
	 * @param xmlPagos the xmlPagos to set
	 */
	public void setXmlPagos(String xmlPagos) {
		this.xmlPagos = xmlPagos;
	}
	/**
	 * @return the fase
	 */
	public String getFase() {
		return fase;
	}
	/**
	 * @param fase the fase to set
	 */
	public void setFase(String fase) {
		this.fase = fase;
	}
	/**
	 * @return the pedido
	 */
	public String getPedido() {
		return pedido;
	}
	/**
	 * @param pedido the pedido to set
	 */
	public void setPedido(String pedido) {
		this.pedido = pedido;
	}
	/**
	 * @return the xmlNoms
	 */
	public String getXmlNoms() {
		return xmlNoms;
	}
	/**
	 * @param xmlNoms the xmlNoms to set
	 */
	public void setXmlNoms(String xmlNoms) {
		this.xmlNoms = xmlNoms;
	}
	/**
	 * @return the xmlNoms2
	 */
	public String getXmlNoms2() {
		return xmlNoms2;
	}
	/**
	 * @param xmlNoms2 the xmlNoms2 to set
	 */
	public void setXmlNoms2(String xmlNoms2) {
		this.xmlNoms2 = xmlNoms2;
	}
	/**
	 * @return the xmlApPats
	 */
	public String getXmlApPats() {
		return xmlApPats;
	}
	/**
	 * @param xmlApPats the xmlApPats to set
	 */
	public void setXmlApPats(String xmlApPats) {
		this.xmlApPats = xmlApPats;
	}
	/**
	 * @return the xmlApMats
	 */
	public String getXmlApMats() {
		return xmlApMats;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @param xmlApMats the xmlApMats to set
	 */
	public void setXmlApMats(String xmlApMats) {
		this.xmlApMats = xmlApMats;
	}
	/**
	 * @return the fregi
	 */
	public String getFregi() {
		return fregi;
	}
	/**
	 * @param fregi the fregi to set
	 */
	public void setFregi(String fregi) {
		this.fregi = fregi;
	}
	
	
	public String getIdFaseEquipo() {
		return idFaseEquipo;
	}
	public void setIdFaseEquipo(String idFaseEquipo) {
		this.idFaseEquipo = idFaseEquipo;
	}
	@Override
	public String toString() {
		return "CriteriosGetInfGetCot [usuario=" + usuario + ", id_ut_sup="
				+ id_ut_sup + ", accion=" + accion + ", cotPed=" + cotPed
				+ ", pedido=" + pedido + ", fregi=" + fregi + ", fase=" + fase
				+ ", xmlCot=" + xmlCot + ", xmlPed=" + xmlPed + ", xmlCli="
				+ xmlCli + ", xmlEq=" + xmlEq + ", xmlPagos=" + xmlPagos
				+ ", xmlNoms=" + xmlNoms + ", xmlNoms2=" + xmlNoms2
				+ ", xmlApPats=" + xmlApPats + ", xmlApMats=" + xmlApMats
				+ ", time=" + time + ", idFaseEquipo=" + idFaseEquipo + "]";
	}
	
}
