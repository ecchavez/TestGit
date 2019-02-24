/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 01/08/2012             
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;


/**
 * Clase que encapsula los fields para Busqueda e insercion Clientes Sap
 * Fecha de creación: 01/08/2012               
 */
public class CriteriosGetInfClienteSap {
	private String id_ut_sup;
	private String usuario;
	private int accion;
	private String id_selected;
	private String nombre1;
	private String nombre2;
	private String app_pat;
	private String app_mat;
	private String rfc;
	private String ife;
	private String idClienteZ;
	private String idClienteSap;
	String strListaCriterios;
	private String xmlCliZ;
	private String xmlCliSap;
	private String xmlNom;
	private String xmlNom2;
	private String xmlApPat;
	private String xmlApMat;
	private String xmlCarteraCliente;
	
	private String arrNombre1;
	private String arrNombre2;
	private String arrApellido1;
	private String arrApellido2;

	
	
	
	
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @return the id_ut_sup
	 */
	public String getId_ut_sup() {
		return id_ut_sup;
	}
	/**
	 * @return the accion
	 */
	public int getAccion() {
		return accion;
	}
	/**
	 * @return the id_selected
	 */
	public String getId_selected() {
		return id_selected;
	}
	/**
	 * @return the nombre1
	 */
	public String getNombre1() {
		return nombre1;
	}
	/**
	 * @return the nombre2
	 */
	public String getNombre2() {
		return nombre2;
	}
	/**
	 * @return the app_pat
	 */
	public String getApp_pat() {
		return app_pat;
	}
	/**
	 * @return the app_mat
	 */
	public String getApp_mat() {
		return app_mat;
	}
	/**
	 * @return the rfc
	 */
	public String getRfc() {
		return rfc;
	}
	/**
	 * @return the ife
	 */
	public String getIfe() {
		return ife;
	}
	/**
	 * @param ife the ife to set
	 */
	public void setIfe(String ife) {
		this.ife = ife;
	}
	/**
	 * @return the idClienteZ
	 */
	public String getIdClienteZ() {
		return idClienteZ;
	}
	/**
	 * @return the idClienteSap
	 */
	public String getIdClienteSap() {
		return idClienteSap;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @param id_ut_sup the id_ut_sup to set
	 */
	public void setId_ut_sup(String id_ut_sup) {
		this.id_ut_sup = id_ut_sup;
	}
	/**
	 * @param accion the accion to set
	 */
	public void setAccion(int accion) {
		this.accion = accion;
	}
	/**
	 * @param id_selected the id_selected to set
	 */
	public void setId_selected(String id_selected) {
		this.id_selected = id_selected;
	}
	/**
	 * @param nombre1 the nombre1 to set
	 */
	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}
	/**
	 * @param nombre2 the nombre2 to set
	 */
	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}
	/**
	 * @param app_pat the app_pat to set
	 */
	public void setApp_pat(String app_pat) {
		this.app_pat = app_pat;
	}
	/**
	 * @param app_mat the app_mat to set
	 */
	public void setApp_mat(String app_mat) {
		this.app_mat = app_mat;
	}
	/**
	 * @param rfc the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	/**
	 * @param idClienteZ the idClienteZ to set
	 */
	public void setIdClienteZ(String idClienteZ) {
		this.idClienteZ = idClienteZ;
	}
	/**
	 * @param idClienteSap the idClienteSap to set
	 */
	public void setIdClienteSap(String idClienteSap) {
		this.idClienteSap = idClienteSap;
	}
	/**
	 * @return the strListaCriterios
	 */
	public String getStrListaCriterios() {
		return strListaCriterios;
	}
	/**
	 * @param strListaCriterios the strListaCriterios to set
	 */
	public void setStrListaCriterios(String strListaCriterios) {
		this.strListaCriterios = strListaCriterios;
	}
	/**
	 * @return the xmlCliZ
	 */
	public String getXmlCliZ() {
		return xmlCliZ;
	}
	/**
	 * @param xmlCliZ the xmlCliZ to set
	 */
	public void setXmlCliZ(String xmlCliZ) {
		this.xmlCliZ = xmlCliZ;
	}
	/**
	 * @return the xmlCliSap
	 */
	public String getXmlCliSap() {
		return xmlCliSap;
	}
	/**
	 * @param xmlCliSap the xmlCliSap to set
	 */
	public void setXmlCliSap(String xmlCliSap) {
		this.xmlCliSap = xmlCliSap;
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
	 * @return the xmlCarteraCliente
	 */
	public String getXmlCarteraCliente() {
		return xmlCarteraCliente;
	}
	/**
	 * @param xmlCarteraCliente the xmlCarteraCliente to set
	 */
	public void setXmlCarteraCliente(String xmlCarteraCliente) {
		this.xmlCarteraCliente = xmlCarteraCliente;
	}

	public String getArrNombre1() {
		return arrNombre1;
	}
	public void setArrNombre1(String arrNombre1) {
		this.arrNombre1 = arrNombre1;
	}
	public String getArrNombre2() {
		return arrNombre2;
	}
	public void setArrNombre2(String arrNombre2) {
		this.arrNombre2 = arrNombre2;
	}
	public String getArrApellido1() {
		return arrApellido1;
	}
	public void setArrApellido1(String arrApellido1) {
		this.arrApellido1 = arrApellido1;
	}
	public String getArrApellido2() {
		return arrApellido2;
	}
	public void setArrApellido2(String arrApellido2) {
		this.arrApellido2 = arrApellido2;
	}
	
		
}
